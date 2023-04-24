//package com.repomon.rocketdan.common.config;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.repomon.rocketdan.common.service.JwtTokenProvider;
//import com.repomon.rocketdan.common.service.RedisService;
//import io.jsonwebtoken.JwtException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//@Log4j2
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//	public static final String AUTHORIZATION_HEADER = "authorization";
//	public static final String BEARER_PREFIX = "Bearer ";
//	private final JwtTokenProvider tokenProvider;
//	private final ObjectMapper objectMapper;
//	private final RedisService redisService;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		// 1. Request Header 에서 토큰을 꺼냄
//		String token = resolveToken(request);
//		System.out.println("token = " + token);
//
//		log.info("여기: {}", request.getRequestURI());
//		System.out.println("request.getRequestURI() = " + request.getRequestURI());
//
//		// 2. validateToken 으로 토큰 유효성 검사
//		// 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
//		if (StringUtils.hasText(token)) {
//
//			if (tokenProvider.validate(token)) {
//				// 3. Redis에 해당 accessToken 로그아웃 여부 확인
//				String isLogout = redisService.getData(token);
//				System.out.println("isLogout = " + isLogout);
////				if (ObjectUtils.isEmpty(isLogout)) {
////					Authentication authentication = tokenProvider.getAuthentication(token);
////					SecurityContextHolder.getContext().setAuthentication(authentication);
////				} else {
////					throw new JwtException("로그아웃 된 accessToken 유효하지 않음");
////				}
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
//
//	// Request Header 에서 토큰 정보를 꺼내오기
//	private String resolveToken(HttpServletRequest request) {
//		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//			return bearerToken.substring(7);
//		}
//		return null;
//	}
//}
