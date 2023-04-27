package com.repomon.rocketdan.common.handler;


import com.repomon.rocketdan.common.dto.AuthResponseDto;
import com.repomon.rocketdan.common.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtTokenProvider authTokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

		Long userId = oAuth2User.getAttribute("userId");
		AuthResponseDto authResponseDto = authTokenProvider.createToken(userId);

		setDefaultTargetUrl("http://localhost:3000?access-token=" + authResponseDto.getAccessToken() + "&refresh-token=" + authResponseDto.getRefreshToken());
		super.onAuthenticationSuccess(request, response, authentication);

	}
}
