package com.repomon.rocketdan.domain.repo.service;

import com.repomon.rocketdan.domain.repo.dto.RepoListResponseDto;
import com.repomon.rocketdan.domain.repo.repository.RepoRepository;
import com.repomon.rocketdan.domain.user.entity.ActiveRepoEntity;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHPersonSet;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Service;

@Service @Slf4j
@RequiredArgsConstructor
public class RepoService {

    private final UserRepository userRepository;
    private final RepoRepository repoRepository;

    public RepoListResponseDto getUserRepoList(Long userId){
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        List<ActiveRepoEntity> activeRepoList = userEntity.getActiveRepoList();

        /**
         * TODO Github에서 Repository 가져오기
         * Organization과 개인 Repo 모두를 가져와야 한다
         * 가져오고나서 DB에 존재하지 않는경우 새로 추가, 아니면 수정
         */


        return null;
    }
}
