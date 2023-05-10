package com.repomon.rocketdan.common.utils;


import com.repomon.rocketdan.domain.repo.app.GrowthFactor;
import com.repomon.rocketdan.domain.repo.app.UserCardDetail;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoHistoryEntity;
import java.util.List;

import java.util.*;
import org.kohsuke.github.*;
import org.kohsuke.github.GHRepositoryStatistics.CodeFrequency;
import org.kohsuke.github.GHRepositoryStatistics.CommitActivity;
import org.kohsuke.github.GHRepositoryStatistics.ContributorStats;
import org.kohsuke.github.GHRepositoryStatistics.ContributorStats.Week;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;


import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPersonSet;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestReviewComment;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.PagedIterable;


@Component
public class GHUtils {

    @Value("${github.accessToken}")
    private String accessToken;
    public GitHub gitHub;

    private int pageSize = 1;

    @PostConstruct
    private void init() throws IOException {
        RateLimitChecker limitChecker = RateLimitChecker.NONE;
        gitHub = new GitHubBuilder().withOAuthToken(accessToken).withRateLimitChecker(limitChecker).build();
    }

    public String getOrganizationFirstOwner(String orgName) {
        try {
            return gitHub.getOrganization(orgName).listMembersWithRole("Owner").toList().get(0).getLogin();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


    public Collection<RepoHistoryEntity> GHCommitToHistory(GHRepository ghRepository, RepoEntity repoEntity, Date date)
        throws IOException, InterruptedException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        PagedIterable<GHCommit> ghCommits = date == null
            ? ghRepository.queryCommits().list()
            : ghRepository.queryCommits()
                .since(date)
                .list();

        PagedIterator<GHCommit> iterator = ghCommits._iterator(100);

        while(iterator.hasNext()){
            List<GHCommit> commitList = iterator.nextPage();
            for(GHCommit commit : commitList){
                if(commit.getParentSHA1s().size() > 1) continue;
                Date commitDate = commit.getCommitDate();
                if(date == null || commitDate.after(date)){
                    LocalDate commitAt = DateUtils.dateToLocalDate(commitDate);
                    configureRepoInfo(histories, commitAt, repoEntity, GrowthFactor.COMMIT, 1);
                }
            }
        }
//        for(CommitActivity commitActivity : commitActivities){
//            long week = commitActivity.getWeek();
//            Date weekAt = new Date(week * 1000L);
//            weekAt = DateUtils.fewDateAgo(weekAt, 6);
//
//            if(weekAt.before(date)) continue;
//
//            List<Integer> days = commitActivity.getDays();
//            for (int i = 0; i < 7; i++) {
//                Integer commitCount = days.get(i);
//                Date createdAt = new Date(week * 1000L);
//                createdAt = DateUtils.fewDateAgo(createdAt, i);
//
//                if (createdAt.after(date) && commitCount > 0) {
//                    LocalDate commitDate = DateUtils.dateToLocalDate(createdAt);
//                    configureRepoInfo(histories, commitDate, repoEntity, GrowthFactor.COMMIT, commitCount);
//                }
//            }
//        }

        return histories.values();
    }

    /**
     * TODO Issue와 통폐합 생각해야함
     * @param ghRepository
     * @param repoEntity
     * @param date
     * @return
     * @throws IOException
     */
    public Collection<RepoHistoryEntity> GHPullRequestToHistory(GHRepository ghRepository, RepoEntity repoEntity, Date date)
        throws IOException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        PagedIterable<GHPullRequest> pullRequests = ghRepository.queryPullRequests()
            .state(GHIssueState.CLOSED)
            .direction(GHDirection.DESC)
            .list();

        for (GHPullRequest pr : pullRequests) {
            Date closedAt = pr.getClosedAt();
            if (date == null || closedAt.after(date)) {
                LocalDate prDate = DateUtils.dateToLocalDate(pr.getClosedAt());

                configureRepoInfo(histories, prDate, repoEntity, GrowthFactor.MERGE, 1);

                PagedIterable<GHPullRequestReviewComment> reviewComments = pr.listReviewComments();

                for (GHPullRequestReviewComment reviewComment : reviewComments) {
                    Date reviewCreatedAt = reviewComment.getCreatedAt();
                    if (date == null || reviewCreatedAt.after(date)) {
                        LocalDate reviewDate = DateUtils.dateToLocalDate(reviewComment.getCreatedAt());

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


    public Collection<RepoHistoryEntity> GHIssueToHistory(GHRepository ghRepository, RepoEntity repoEntity, Date date)
        throws IOException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        PagedIterable<GHIssue> issues = date == null
            ? ghRepository.queryIssues()
                .direction((GHDirection.DESC))
                .state(GHIssueState.CLOSED)
                .list()
            : ghRepository.queryIssues()
                .direction(GHDirection.DESC)
                .state(GHIssueState.CLOSED)
                .since(date)
                .list();

        for (GHIssue issue : issues) {
            Date closedAt = issue.getClosedAt();
            if(issue.isPullRequest()) continue;
            if (date == null || closedAt.after(date)) {
                LocalDate issueClosedAt = DateUtils.dateToLocalDate(issue.getClosedAt());

                configureRepoInfo(histories, issueClosedAt, repoEntity, GrowthFactor.ISSUE, 1);
            } else {
                return histories.values();
            }
        }

        return histories.values();
    }


    public Collection<RepoHistoryEntity> GHForkToHistory(GHRepository ghRepository, RepoEntity repoEntity, LocalDate fromDate)
        throws IOException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        Integer forkCnt = repoEntity.getForkCnt();
        int forksCount = ghRepository.getForksCount();

        if(forkCnt >= forksCount) return histories.values();
        forksCount -= forkCnt;

        if(forkCnt + forksCount >= 5000 && forkCnt >= 5000){
            configureRepoInfo(histories, fromDate, repoEntity, GrowthFactor.FORK, 0);
        }
        else {
            int tmp = forksCount;
            if(tmp >= 5000) tmp = 5000;
            configureRepoInfo(histories, fromDate, repoEntity, GrowthFactor.FORK, tmp);
        }

        repoEntity.updateForkCnt(forksCount);

        return histories.values();
    }

    public Collection<RepoHistoryEntity> GHStarToHistory(GHRepository ghRepository, RepoEntity repoEntity, LocalDate fromDate)
        throws IOException {
        Map<LocalDate, RepoHistoryEntity> histories = new HashMap<>();

        Integer starCnt = repoEntity.getStarCnt();
        int stargazersCount = ghRepository.getStargazersCount();

        if(starCnt >= stargazersCount) return histories.values();
        stargazersCount -= starCnt;


        if(starCnt + stargazersCount >= 5000 && starCnt >= 5000) {
            configureRepoInfo(histories, fromDate, repoEntity, GrowthFactor.STAR, 0);
        }
        else {
            int tmp = stargazersCount;
            if(tmp >= 5000) tmp = 5000;
                configureRepoInfo(histories, fromDate, repoEntity, GrowthFactor.STAR, tmp);
        }

        repoEntity.updateStarCnt(stargazersCount);
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


    public Long getTotalLineCount(GHRepositoryStatistics statistics) throws IOException, InterruptedException {
        long totalLineCount = 0L;
        List<CodeFrequency> codeFrequencies = statistics.getCodeFrequency();
        for (CodeFrequency codeFrequency : codeFrequencies) {
            totalLineCount += codeFrequency.getAdditions();
            totalLineCount += codeFrequency.getDeletions();
        }

        return totalLineCount;
    }

    public int getTotalCommitCount(GHRepositoryStatistics statistics)
        throws IOException, InterruptedException {
        List<ContributorStats> contributorStats = statistics.getContributorStats()
            .toList();

        int totalCommitCount = 0;
        for(ContributorStats contributorStat : contributorStats){
            totalCommitCount += contributorStat.getTotal();
        }

        return totalCommitCount;
    }
    public Map<String, Integer> getCommitterInfoMap(GHRepositoryStatistics statistics, Date fromDate) throws IOException, InterruptedException {
        Map<String, Integer> commitCountMap = new HashMap<>();
        List<ContributorStats> contributorStatList = statistics
            .getContributorStats().toList();

        for (ContributorStats contributorStats : contributorStatList) {
            String author = contributorStats.getAuthor().getLogin();
            int authorCommitCnt = 0;
            for(Week week : contributorStats.getWeeks()){
                Date commitDate = new Date(week.getWeekTimestamp() * 1000L);
                if(fromDate == null || commitDate.after(fromDate)){
                    authorCommitCnt += week.getNumberOfCommits();
                }
            }

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
    public Long getLineCountWithUser(GHRepositoryStatistics statistics, String userName) throws IOException, InterruptedException {
        long lineCount = 0L;
        List<ContributorStats> contributorStatList = statistics
            .getContributorStats().toList();

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
    public Long getCommitCountWithUser(GHRepositoryStatistics statistics, String userName)
        throws IOException, InterruptedException {
        Long commitCount = 0L;
        List<ContributorStats> contributorStatList = statistics.getContributorStats()
            .toList();

        for (ContributorStats contributorStats : contributorStatList) {
            String author = contributorStats.getAuthor().getLogin();
            if (author.equals(userName)) {
                commitCount = Long.valueOf(contributorStats.getTotal());
                break;
            }
        }

        return commitCount;
    }

    /**
     * 유저 이름의 총 이슈 수
     */
    public Integer getMyIssueToHistory(GHRepository ghRepository, Date date, String username) throws IOException {


        int issueCount = 0;
        List<GHIssue> allIssues = ghRepository.queryIssues().list()
            .toList();

        for (GHIssue issue : allIssues) {
            Date createdAt = issue.getCreatedAt();
            String author = issue.getUser().getLogin();
            if (author.equals(username) && (date == null || createdAt.after(date)) ) {
                issueCount++;
            }
        }
        return issueCount;
    }

    /**
     * 유저 이름의 총 머지, 리뷰 수
     */
    public List<Integer> getMyMergeToHistory(GHRepository ghRepository, Date date, String username) throws IOException {
        int mymerge = 0;
        int myreview = 0;

        PagedIterable<GHPullRequest> pullRequests = ghRepository.queryPullRequests()
            .list();

        for(GHPullRequest pr : pullRequests){
            Date prDate = pr.getMergedAt();

            if(date == null || prDate.before(date)) {
                String author = pr.getUser().getLogin();
                if (author.equals(username)){
                    mymerge += 1;
                }

                PagedIterable<GHPullRequestReviewComment> reviewComments = pr.listReviewComments();

                for(GHPullRequestReviewComment reviewComment : reviewComments){
                    author = reviewComment.getUser().getLogin();
                    if (author.equals(username)){
                        myreview += 1;
                    }
                }
            }
        }

        return List.of(mymerge, myreview);
    }


    /**
     * 유저 모든 레포지터리에서 본인의 커밋 수 총 합
     *
     * @param repos
     * @param userName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Long getTotalCommitCountByUser(Map<String, GHRepository> repos, String userName) throws IOException, InterruptedException {
        Long totalCommitCount = 0L;
        for (GHRepository repo : repos.values()) {
            GHRepositoryStatistics statistics = repo.getStatistics();
            Long commitCountWithUser = getCommitCountWithUser(statistics, userName);
            totalCommitCount += commitCountWithUser;
        }
        return totalCommitCount;
    }

        /**
         * 유저 모든 레포지터리에서 이슈 조회
         */
        public Long getTotalIssueCountByUser(String userName) throws IOException {
            GHUser user = gitHub.getUser(userName);

            Map<String, GHRepository> map = getRepositoriesInPublicOrganization(user);
            map.putAll(getRepositories(user));

            Long totalIssueCount = 0L;
            for (GHRepository repo : map.values()) {
                List<GHIssue> issues = repo.getIssues(GHIssueState.CLOSED);
                for (GHIssue issue : issues) {
                    if(issue.isPullRequest()) continue;
                    String author = issue.getAssignee().getLogin();
                    if (author.equals(userName)) {
                            totalIssueCount++;
                    }
                }
            }
            return totalIssueCount;
        }


    /**
     * 유저 모든 레포지터리에서 라인 수 조회
     *
     * @param repos
     * @param userName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Long getTotalCodeLineCountByUser(Map<String, GHRepository> repos, String userName) throws IOException, InterruptedException {
        Long totalCodeLineCount = 0L;
        for (GHRepository repo : repos.values()) {
            GHRepositoryStatistics statistics = repo.getStatistics();
            totalCodeLineCount += getLineCountWithUser(statistics, userName);
        }
        return totalCodeLineCount;
    }


    /**
     * 유저 모든 레포지터리에서 언어 조회
     */
    public List<String> getLanguagesByUser(String userName) throws IOException {
        GHUser user = gitHub.getUser(userName);
        Map<String, GHRepository> repos = getRepositoriesInPublicOrganization(user);
        repos.putAll(getRepositories(user));

        Set<String> languages = new HashSet<>();
        for (GHRepository repo : repos.values()) {
            Map<String, Long> repoLanguages = repo.listLanguages();
            languages.addAll(repoLanguages.keySet());
        }
        return new ArrayList<>(languages);
    }


    /**
     * 유저 모든 레포지터리에서 평균 기여도 조회
     *
     * @param userName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Long getAvgContributionByUser(Map<String, GHRepository> repos, String userName) throws IOException, InterruptedException {
        double avgContribution = 0;
        for (GHRepository repo : repos.values()) {
            GHRepositoryStatistics statistics = repo.getStatistics();
            double totalCommitCount = getTotalCommitCount(statistics);
            double myCommitCount = getCommitCountWithUser(statistics, userName);
            if (totalCommitCount == 0.0) {
                avgContribution += 100.0;
            } else {
                avgContribution += myCommitCount / totalCommitCount * 100;
            }
        }
        return Math.round(avgContribution / repos.size());
    }

    /**
     * 유저 모든 레포지터리에서 스타, 포크 조회
     */

    public List<Long> getStarAndForkByUser(Map<String, GHRepository> repos){
        Long StarCount = 0L;
        Long ForkCount = 0L;

        /**
         * TODO entity에 있는 스타, 포크수 쓰면됨
         */

        for (GHRepository repo : repos.values()) {
            StarCount += repo.getStargazersCount();
            ForkCount += repo.getForksCount();
        }
        return List.of(StarCount,ForkCount);
    }

    /**
     * 유저 모든 레포지터리에서 머지, 리뷰 조회
     *
     * @param repos
     * @param userName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public List<Long> getMergeAndReviewByUser(Map<String, GHRepository> repos, String userName) throws IOException, InterruptedException {
        Long totalMergeCount = 0L;
        Long totalReviewCount = 0L;
        for (GHRepository repo : repos.values()) {
            Date date = DateUtils.yearsAgo();
            /**
             * TODO DB에서 가져올껀 가져와도 될거같음
             */
            List<Integer> myMergeToHistory = getMyMergeToHistory(repo, date, userName);
            totalMergeCount += myMergeToHistory.get(0);
            totalReviewCount += myMergeToHistory.get(1);
        }
        return List.of(totalMergeCount, totalReviewCount);
    }


    /**
     * 유저 카드 정보 조회
     *
     * @param userName
     * @return
     * @throws IOException
     */
    public UserCardDetail getUserCardInfo(String userName) throws IOException, InterruptedException {
        UserCardDetail userCardInfo = new UserCardDetail();

        GHUser user = gitHub.getUser(userName);
        Map<String, GHRepository> repos = getRepositoriesInPublicOrganization(user);
        repos.putAll(getRepositories(user));

        userCardInfo.setUserName(userName);
        userCardInfo.setAvatarUrl(user.getAvatarUrl());
        userCardInfo.setIntroduce(user.getBio());

        userCardInfo.setRepoCount(repos.size());
        userCardInfo.setTotalCommitCount(getTotalCommitCountByUser(repos, userName));
        userCardInfo.setTotalCodeLineCount(getTotalCodeLineCountByUser(repos, userName));

        userCardInfo.setAvgContribution(getAvgContributionByUser(repos, userName));
        userCardInfo.setTotalIssueExp(getTotalIssueCountByUser(userName)* GrowthFactor.idxToEnum(3).getExp());
        userCardInfo.setStarExp(getStarAndForkByUser(repos).get(0)* GrowthFactor.idxToEnum(5).getExp());
        userCardInfo.setForkExp(getStarAndForkByUser(repos).get(1)* GrowthFactor.idxToEnum(6).getExp());
        userCardInfo.setTotalMergeExp(getMergeAndReviewByUser(repos, userName).get(0)* GrowthFactor.idxToEnum(2).getExp());
        userCardInfo.setTotalReviewExp(getMergeAndReviewByUser(repos, userName).get(1)* GrowthFactor.idxToEnum(4).getExp());
        userCardInfo.setTotalCommitExp(getTotalCommitCountByUser(repos, userName)* GrowthFactor.idxToEnum(1).getExp());

        return userCardInfo;
    }
}
