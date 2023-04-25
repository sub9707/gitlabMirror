package com.repomon.rocketdan.domain.user.service;


import com.repomon.rocketdan.common.service.JwtTokenProvider;
import com.repomon.rocketdan.common.service.RedisService;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final JwtTokenProvider authTokenProvider;
	private final RedisService redisService;

//	@Value("${github.config.client_id}")
	private String client_id = "asdf";

//	@Value("${github.config.client_secret}")
	private String client_secret = "asdf";

	@Value("${jwt.expire.access}")
	private Long accessExpiry; // 토큰 만료일
	@Value("${jwt.expire.refresh}")
	private Long refreshExpiry; // 토큰 만료일


	/**
	 * 로그인: 깃허브 소셜 로그인
	 * @param userName
	 */
	public Long login(String userName) {

		UserEntity user = userRepository.findByUserName(userName).orElseGet(() -> {
			UserEntity userEntity = UserEntity.builder().userName(userName).build();
			return userRepository.save(userEntity);
		});

		System.out.println("user.getUserId() = " + user.getUserId());

		return user.getUserId();
	}



	/**
	 * 로그아웃: 리프레쉬 토큰 삭제
	 * @param accessToken
	 */
	public void logout(String accessToken) {

		redisService.deleteData(accessToken);
	}


	/**
	 * 리프레쉬: 리프레쉬 토큰 요청
	 * @param accessToken
	 */
	public void refresh(String accessToken) {

		String refreshToken = redisService.getData(accessToken);
		System.out.println("refreshToken = " + refreshToken);

		if (refreshToken == null) {
			// Todo: 토큰없을때
		} else {
			// 리프레쉬 토큰이 있을 때 access 토큰 재생성
			Long userId = authTokenProvider.getUserId(accessToken);

			// jwt 토큰 생성
			authTokenProvider.createToken(userId);

			// Todo: access 토큰 반환하기
		}
	}
}
