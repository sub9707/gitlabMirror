package com.repomon.rocketdan.common.utils;


import com.repomon.rocketdan.common.Retries;
import com.repomon.rocketdan.domain.repo.app.GrowthFactor;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoHistoryEntity;
import org.kohsuke.github.*;
import org.kohsuke.github.GHRepositoryStatistics.CodeFrequency;
import org.kohsuke.github.GHRepositoryStatistics.ContributorStats;
import org.kohsuke.github.GHRepositoryStatistics.ContributorStats.Week;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Component
public class GHUtils {

    @Value("${github.accessToken}")
    private String accessToken;
    private GitHub gitHub;

    @PostConstruct
    private void init() throws IOException {
        gitHub = new GitHubBuilder().withOAuthToken(accessToken).build();
    }

    public Map<String, GHRepository> getRepositoriesWithName(String name){
        try {
            GHUser user = gitHub.getUser(name);
            Map<String, GHRepository> repositories = getRepositoriesInPublicOrganization(user);
            repositories.putAll(getRepositories(user));
            return repositories;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, GHRepository> getRepositories(GHUser user) throws IOException {
        String name = user.getLogin();

        Map<String, GHRepository> repositories = new HashMap<>(user.getRepositories());
        Map<String, GHRepository> repositoriesWithNodeKey = new HashMap<>();

        repositories.forEach((s, ghRepository) -> {
            if(!s.equals(name) && !ghRepository.isFork() && !s.equals(name + ".github.io")){
                repositoriesWithNodeKey.put(ghRepository.getNodeId(), ghRepository);
            }
        });

        return repositoriesWithNodeKey;
    }

    private Map<String, GHRepository> getRepositoriesInPublicOrganization(GHUser user)
        throws IOException {
        GHPersonSet<GHOrganization> organizations = user.getOrganizations();
        if(organizations != null) {
            Map<String, GHRepository> repositoriesWithNodeId = new HashMap<>();
            for(GHOrganization ghOrganization : organizations){
                Map<String, GHRepository> repositories = ghOrganization.getRepositories();
                repositories.forEach((s, ghRepository) -> repositoriesWithNodeId.put(ghRepository.getNodeId(), ghRepository));
            }
            return repositoriesWithNodeId;
        }
        return new HashMap<>();
    }


    public Collection<RepoHistoryEntity> GHCommitToHistory(GHRepository ghRepository, RepoEntity repoEntity, Date date) throws IOException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        GHRepositoryStatistics statistics = ghRepository.getStatistics();
        PagedIterable<GHRepositoryStatistics.CommitActivity> commitActivities = statistics.getCommitActivity();

        for (GHRepositoryStatistics.CommitActivity commitActivity : commitActivities) {
            long week = commitActivity.getWeek();
            List<Integer> days = commitActivity.getDays();
            for (int i = 0; i < 7; i++) {
                Integer commitCount = days.get(i);
                Date createdAt = new Date((week + i) * 1000L);
                if (createdAt.after(date)) {
                    LocalDate commitDate = createdAt.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                    configureRepoInfo(histories, commitDate, repoEntity, GrowthFactor.COMMIT, commitCount);
                }
            }
        }

        return histories.values();
    }

    public Collection<RepoHistoryEntity> GHPullRequestToHistory(GHRepository ghRepository, RepoEntity repoEntity, Date date)
        throws IOException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        PagedIterable<GHPullRequest> pullRequests = ghRepository.queryPullRequests()
            .state(GHIssueState.CLOSED)
            .direction(GHDirection.DESC)
            .list().withPageSize(100);

        for (GHPullRequest pr : pullRequests) {
            Date closedAt = pr.getClosedAt();
            if (closedAt.after(date)) {
                LocalDate prDate = pr.getClosedAt().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

                configureRepoInfo(histories, prDate, repoEntity, GrowthFactor.MERGE, 1);

                PagedIterable<GHPullRequestReviewComment> reviewComments = pr.listReviewComments()
                    .withPageSize(100);

                for (GHPullRequestReviewComment reviewComment : reviewComments) {
                    Date reviewCreatedAt = reviewComment.getCreatedAt();
                    if (reviewCreatedAt.after(date)) {
                        LocalDate reviewDate = reviewComment.getCreatedAt().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                        configureRepoInfo(histories, reviewDate, repoEntity, GrowthFactor.REVIEW,
                            1);
                    }
                }
            } else {
                return histories.values();
            }
        }

        return histories.values();
    }


    public Collection<RepoHistoryEntity> GHIssueToHistory(GHRepository ghRepository, RepoEntity repoEntity, Date date) {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        PagedIterable<GHIssue> issues = ghRepository.queryIssues()
            .direction(GHDirection.DESC)
            .state(GHIssueState.CLOSED)
            .since(date)
            .list()
            .withPageSize(100);

        for (GHIssue issue : issues) {
            Date closedAt = issue.getClosedAt();
            if (closedAt.after(date)) {

                LocalDate issueClosedAt = issue.getClosedAt().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

                configureRepoInfo(histories, issueClosedAt, repoEntity, GrowthFactor.ISSUE, 1);
            } else {
                return histories.values();
            }
        }

        return histories.values();
    }


    public Collection<RepoHistoryEntity> GHForkToHistory(GHRepository ghRepository, RepoEntity repoEntity, Date fromDate)
        throws IOException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        PagedIterable<GHRepository> ghRepositories = ghRepository
            .listForks()
            .withPageSize(100);

        for (GHRepository repo : ghRepositories) {
            Date createdAt = repo.getCreatedAt();
            if (createdAt.after(fromDate)) {
                LocalDate forkedAt = createdAt.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

                configureRepoInfo(histories, forkedAt, repoEntity, GrowthFactor.FORK, 1);
            } else {
                return histories.values();
            }
        }

        return histories.values();
    }


    public Collection<RepoHistoryEntity> GHStarToHistory(GHRepository ghRepository, RepoEntity repoEntity, Date fromDate)
        throws IOException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        PagedIterable<GHStargazer> ghStargazers = ghRepository
            .listStargazers2()
            .withPageSize(100);

        for (GHStargazer stargazer : ghStargazers) {
            Date starredAt = stargazer.getStarredAt();
            if (starredAt.after(fromDate)) {
                LocalDate starredDate = starredAt.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

                configureRepoInfo(histories, starredDate, repoEntity, GrowthFactor.STAR, 1);
            }
        }

        return histories.values();
    }


    private void configureRepoInfo(Map<LocalDate, RepoHistoryEntity> histories, LocalDate date, RepoEntity repoEntity, GrowthFactor factor, int cnt) {
        if (histories.containsKey(date)) {
            RepoHistoryEntity repoHistoryEntity = histories.get(date);
            repoHistoryEntity.updateExp(factor.getExp());
        } else {
            histories.put(date, RepoHistoryEntity.ofGHInfo(date, repoEntity, factor, cnt));
        }
    }


    public Map<String, String> getUser(String userName) {
        try {
            GHUser githubUser = gitHub.getUser(userName);
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("username", githubUser.getLogin());
            userInfo.put("avatarUrl", githubUser.getAvatarUrl());
            userInfo.put("nickname", githubUser.getName() == null ? githubUser.getLogin() : githubUser.getName());
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Retries
    public Long getTotalLineCount(GHRepositoryStatistics statistics) throws IOException, InterruptedException {
        long totalLineCount = 0L;
        List<CodeFrequency> codeFrequencies = statistics.getCodeFrequency();
        for (CodeFrequency codeFrequency : codeFrequencies) {
            totalLineCount += codeFrequency.getAdditions();
            totalLineCount += codeFrequency.getDeletions();
        }

        return totalLineCount;
    }

    @Retries
    public Map<String, Integer> getCommitterInfoMap(GHRepositoryStatistics statistics) throws IOException, InterruptedException {
        Map<String, Integer> commitCountMap = new HashMap<>();
        List<ContributorStats> contributorStatList = statistics.getContributorStats().toList();
        for (ContributorStats contributorStats : contributorStatList) {
            String author = contributorStats.getAuthor().getLogin();
            int authorCommitCnt = contributorStats.getTotal();
            commitCountMap.put(author, authorCommitCnt);
        }

        return commitCountMap;
    }


    /**
     * 유저 이름의 총 라인 수
     *
     * @param statistics
     * @param userName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Retries
    public Long getLineCountWithUser(GHRepositoryStatistics statistics, String userName) throws IOException, InterruptedException {
        long lineCount = 0L;
        List<ContributorStats> contributorStatList = statistics.getContributorStats().toList();
        for (ContributorStats contributorStats : contributorStatList) {
            String author = contributorStats.getAuthor().getLogin();
            if (author.equals(userName)) {
                for (Week week : contributorStats.getWeeks()) {
                    lineCount += week.getNumberOfAdditions();
                    lineCount += week.getNumberOfDeletions();
                }
            }
        }
        return lineCount;
    }


    /**
     * 유저 이름의 총 커밋 수
     *
     * @param statistics
     * @param userName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Retries
    public Long getCommitCountWithUser(GHRepositoryStatistics statistics, String userName)
        throws IOException, InterruptedException {
        Long commitCount = 0L;
        List<ContributorStats> contributorStatList = statistics.getContributorStats().toList();
        for (ContributorStats contributorStats : contributorStatList) {
            String author = contributorStats.getAuthor().getLogin();
            if (author.equals(userName)) {
                commitCount = (long) contributorStats.getTotal();
                break;
            }
        }

        return commitCount;
    }


    /**
     * 깃허브 레포지터리 총 커밋 수
     *
     * @param ghRepository
     * @return
     * @throws IOException
     */
    public int getCommitCount(GHRepository ghRepository) throws IOException, InterruptedException {
        GHRepositoryStatistics statistics = ghRepository.getStatistics();
        List<ContributorStats> contributorStats = statistics.getContributorStats().toList();

        int totalCommits = 0;
        for (ContributorStats ContributorStat : contributorStats) {
            List<Week> weeks = ContributorStat.getWeeks();
            for (Week week : weeks) {
                totalCommits += week.getNumberOfCommits();
            }
        }
        return totalCommits;
    }


    /**
     * 특정 유저의 모든 레포지터리에서 본인의 커밋 수 총 합
     */
    public Long getTotalCommitCountByUser(String userName) throws IOException, InterruptedException {
        GHUser user = gitHub.getUser(userName);

        Map<String, GHRepository> map = getRepositoriesInPublicOrganization(user);
        map.putAll(getRepositories(user));

        Long totalCommitCount = 0L;
        for (GHRepository repo : map.values()) {
            GHRepositoryStatistics statistics = repo.getStatistics();
            Long commitCountWithUser = getCommitCountWithUser(statistics, userName);
            totalCommitCount += commitCountWithUser;
        }
        System.out.println("totalCommitCount = " + totalCommitCount);
        return totalCommitCount;
    }

    //    public Long getTotalIssueCountByUser(String userName) throws IOException {
    //        GHUser user = gitHub.getUser(userName);
    //
    //        Map<String, GHRepository> map = getRepositoriesInPublicOrganization(user);
    //        map.putAll(getRepositories(user));
    //
    //        Long totalIssueCount = 0L;
    //        for (GHRepository repo : map.values()) {
    //            List<GHIssue> issues = repo.getIssues(GHIssueState.CLOSED);
    //            for (GHIssue issue : issues) {
    //                if (issue.getAssignee() != null && issue.getAssignee().getLogin().equals(userName)) {
    //                    totalIssueCount++;
    //                }
    //            }
    //        }
    //        System.out.println("totalIssueCount = " + totalIssueCount);
    //        return totalIssueCount;
    //    }


    public Long getTotalCodeLineCountByUser(String userName) throws IOException, InterruptedException {
        GHUser user = gitHub.getUser(userName);

        Map<String, GHRepository> map = getRepositoriesInPublicOrganization(user);
        map.putAll(getRepositories(user));

        Long totalCodeLineCount = 0L;
        for (GHRepository repo : map.values()) {
            totalCodeLineCount += getLineCountWithUser(repo.getStatistics(), userName);
        }
        System.out.println("totalCodeLineCount = " + totalCodeLineCount);
        return totalCodeLineCount;
    }


    public Set<String> getLanguagesByUser(String userName) throws IOException {
        GHUser user = gitHub.getUser(userName);

        Map<String, GHRepository> map = getRepositoriesInPublicOrganization(user);
        map.putAll(getRepositories(user));

        Set<String> languages = new HashSet<>();
        for (GHRepository repo : map.values()) {
            Map<String, Long> repoLanguages = repo.listLanguages();

            languages.addAll(repoLanguages.keySet());
        }
        System.out.println("languages = " + languages);
        return languages;
    }


    public Long getAvgContributionByUser(String userName) throws IOException, InterruptedException {
        GHUser user = gitHub.getUser(userName);

        Map<String, GHRepository> map = getRepositoriesInPublicOrganization(user);
        map.putAll(getRepositories(user));

        double avgContribution = 0;
        for (GHRepository repo : map.values()) {
            System.out.println("repo.getName() = " + repo.getName());

            double totalCommitCount = getCommitCount(repo);
            System.out.println("totalCommitCount = " + totalCommitCount);
            double myCommitCount = getCommitCountWithUser(repo.getStatistics(), userName);
            System.out.println("myCommitCount = " + myCommitCount);

            if (totalCommitCount == 0.0) {
                avgContribution += 100.0;
            } else {
                avgContribution += myCommitCount / totalCommitCount * 100;
            }

            System.out.println("avgContribution = " + avgContribution);

        }
        System.out.println("avgContribution = " + Math.round(avgContribution / map.size()));
        return Math.round(avgContribution / map.size());
    }

}
