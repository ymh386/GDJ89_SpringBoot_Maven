package com.moon.app.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.moon.app.user.UserService;
import com.moon.app.user.UserSocialService;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSocialService userSocialService;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	@Autowired
	private JwtTokenManager jwtTokenManager;
	
	@Bean
	HttpFirewall firewall() {
		return new DefaultHttpFirewall();
	}
	
	//정적 자원들을 Securuty에서 제외
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
			security.cors(cors-> cors.configurationSource(this.corsConfigurationSource()))
			.csrf(csrf-> csrf.disable())
			//권한 적용
			.authorizeHttpRequests(authorizeRequest->{
				authorizeRequest
//				.requestMatchers("/notice/add", "/notice/update", "/notice/delete").hasRole("ADMIN")
//				.requestMatchers("/user/myPage", "/user/update", "/user/logout").authenticated()
//				.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
				.anyRequest().permitAll();
			})
			
			//Form 관련 설정
			.formLogin(formLogin ->{
				formLogin.disable()
				
				;
			})
			.sessionManagement(s->{
				s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				
				;
			})
			.httpBasic(httpBasic-> httpBasic.disable())
			
			
			
			
			
//			.oauth2Login(oauth2Login->{
//				oauth2Login
//				.userInfoEndpoint(use->{
//					use.userService(userSocialService);
//				});
//			})
			
			.addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenManager))
			.addFilter(new JwtLoginFilter(authenticationConfiguration.getAuthenticationManager(), jwtTokenManager))
			;
			
			
			return security.build();
		}
		
		CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			
			//GET메서드 허용
			corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5173/ws/chat"));
			
			//corsConfiguration.setAllowedOrigins(List.of("*"));
			//corsConfiguration.setAllowCredentials(true);
			//추가 메서드 허용
			corsConfiguration.setAllowedMethods(List.of("POST", "DELETE", "PATCH" ,"PUT", "GET"));
			
			corsConfiguration.setAllowedHeaders(List.of("*"));
			corsConfiguration.setExposedHeaders(List.of("Authorization", "AccessToken", "RefreshToken"));
			
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", corsConfiguration);
			return source;
		}

}
