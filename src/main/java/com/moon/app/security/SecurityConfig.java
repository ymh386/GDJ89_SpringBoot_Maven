package com.moon.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.moon.app.security.jwt.JwtAuthenticationFilter;
import com.moon.app.security.jwt.JwtLoginFilter;
import com.moon.app.security.jwt.JwtTokenManager;
import com.moon.app.user.UserService;
import com.moon.app.user.UserSocialService;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	@Autowired
	private JwtTokenManager jwtTokenManager;
	
	@Bean
	HttpFirewall firewall() {
		return new DefaultHttpFirewall();
	}
	
	//정적 자원들을 Securuty에서 제외
	//.anyRequest().permitAll(); 권장
		@Bean
		WebSecurityCustomizer custom() {
			
			return (web)->{
				web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/vendor/**", "/img/**");
			};
		}
		
		//인증과 권한의 관한 설정
		@Bean
		SecurityFilterChain filter(HttpSecurity security) throws Exception {
			
			//다른 서버에서 오는 것을 허용
			//CORS 허용, Filter에서 사용 가능
			security.cors(cors-> cors.disable())
			.csrf(csrf-> csrf.disable())
			//권한 적용
			.authorizeHttpRequests(authorizeRequest->{
				authorizeRequest
				.requestMatchers("/notice/add", "/notice/update", "/notice/delete").hasRole("ADMIN")
				.requestMatchers("/user/myPage", "/user/update", "/user/logout").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
				.anyRequest().permitAll();
			})
			
			//Form 관련 설정
			.formLogin(formLogin -> formLogin.disable())
			/**
			 * Session 인증 방식이 아닌
			 * Token 기반 인증 방식을 사용 하기 때문에
			 * Session을 사용 하지 않음
			 * 클라이언트에서 Token값을 서버에 전달 하지 않더라도 로그인 됨
			 */
			.sessionManagement(session ->{
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			})
			
			/**
			 * 쿠키와 세션을 이용하는 방식이 아니라
			 * Request Header에 ID, PW를 직접 보내는 방식이라 보안에 취약
			 * HTTPBasic방식은 해제
			 */
			.httpBasic(httpBasic -> httpBasic.disable())
			
			.addFilter(new JwtLoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenManager))
			.addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenManager))
			
			;
			
			
			return security.build();
		}

}
