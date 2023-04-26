package com.repomon.rocketdan.domain.user.service;


import com.repomon.rocketdan.common.dto.AuthResponseDto;
import com.repomon.rocketdan.common.service.JwtTokenProvider;
import com.repomon.rocketdan.common.service.RedisService;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import com.repomon.rocketdan.exception.CustomException;
import com.repomon.rocketdan.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisService redisService;

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

		return user.getUserId();
	}



	/**
	 * 로그아웃: 리프레쉬 토큰 삭제
	 * @param refreshToken
	 */
	public void logout(String refreshToken) {

		redisService.deleteData(refreshToken);
	}


	/**
	 * 토큰 재발급
	 * @param accessToken
	 * @param refreshToken
	 * @return
	 */
	public AuthResponseDto refresh(String accessToken, String refreshToken) {

		if (jwtTokenProvider.validate(accessToken) && jwtTokenProvider.validate(refreshToken)) {
			log.error("액세스 토큰과 리프레쉬 토큰이 만료되지 않음");
			throw new CustomException(ErrorCode.UNAUTHORIZED_REISSUE_TOKEN);
		} else if (jwtTokenProvider.validate(accessToken)) {
			log.error("액세스 토큰이 만료되지 않음");
			throw new CustomException(ErrorCode.UNAUTHORIZED_REISSUE_TOKEN);
		} else if (jwtTokenProvider.validate(refreshToken)) {
			Long userId = redisService.getAndDelete(refreshToken);
			return jwtTokenProvider.createToken(userId);
		} else {
			log.error("리프레쉬 토큰이 만료됨");
			throw new CustomException(ErrorCode.UNAUTHORIZED_REISSUE_TOKEN);
		}
	}
}
