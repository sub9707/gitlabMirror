package com.repomon.rocketdan.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class RankService {

	public void getUserRankList(String lastName, Pageable pageable) {

		System.out.println("lastName = " + lastName);
		System.out.println("pageable = " + pageable);

	}

}
