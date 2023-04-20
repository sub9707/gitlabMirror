package com.repomon.rocketdan.domain.user.service;


import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;

	@Value("${github.config.client_id}")
	private String client_id;

	@Value("${github.config.client_secret}")
	private String client_secret;

	public void signin(String code) {

		// 깃허브에 액세스토큰 요청
		String accessToken = getAcessToken(code);

		// 깃허브에 유저 정보 요청
		String userName = getUserInfo(accessToken);

		// 요청받은 user ID 가 DB에 있는지 조회
		Optional<UserEntity> user = userRepository.findByUserName(userName);

		Long userId;

		if (user.isPresent()) {
			// 있으면 userId 반환
			userId = user.get().getUserId();

		} else {
			// 없으면 생성하고 PK 반환, 있으면 userId 반환
			UserEntity userEntity = UserEntity.builder().userName(userName).build();
			userRepository.save(userEntity);
			userId = userEntity.getUserId();
		}

		System.out.println("userId = " + userId);
	}

	/**
	 * gitgub accessToken 요청
	 *
	 * @param code
	 * @return
	 */
	private String getAcessToken(String code) {
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", client_id);
		params.add("client_secret", client_secret);
		params.add("code", code);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://github.com/login/oauth/access_token", requestEntity, String.class);
		String response = responseEntity.getBody();

		int scopeIndex = response.indexOf("&scope=");
		String accessToken = response.substring("access_token=".length(), scopeIndex);

		return accessToken;
	}


	/**
	 * github 로그인 유저정보 요청
	 *
	 * @param accessToken
	 * @return
	 */
	private String getUserInfo(String accessToken) {
		RestTemplate restTemplate = new RestTemplate();

		// MappingJackson2HttpMessageConverter를 사용하기 위해 HttpMessageConverter 리스트에 추가
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = "https://api.github.com/user";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		URI uri = builder.build().encode().toUri();

		ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
			uri,
			HttpMethod.GET,
			requestEntity,
			new ParameterizedTypeReference<Map<String, String>>() {});

		Map<String, String> responseBody = responseEntity.getBody();

		return responseBody.get("login");
	}
}
