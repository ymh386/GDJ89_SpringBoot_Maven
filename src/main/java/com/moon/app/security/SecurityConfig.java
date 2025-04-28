package com.moon.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.moon.app.user.UserService;
import com.moon.app.user.UserSocialService;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	@Autowired
	private SecurityLoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private SecurityLoginFailHandler loginFailHandler;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSocialService userSocialService;
	
	@Autowired
	private SecurityLogoutHandler securityLogoutHandler;
	
	@Autowired
	private SecurityLogoutSuccessHandler logoutSuccessHandler;
	
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
			.formLogin(formLogin ->{
				formLogin
				.loginPage("/user/login")
				//파라미터 이름을 지정할 수 있음.
				//.usernameParameter("userID")
				//.passwordParameter("pw")
				//.defaultSuccessUrl("/")
				.successHandler(loginSuccessHandler)
//				.failureUrl("/user/login")
				.failureHandler(loginFailHandler)
				.permitAll();
			})
			
			//Logout 관련 설정
			.logout(logout ->{
				logout
				.logoutUrl("/user/logout")
//				.logoutSuccessUrl("/")
				.addLogoutHandler(securityLogoutHandler)
				//.logoutSuccessHandler(logoutSuccessHandler)
				.invalidateHttpSession(true)
				.permitAll();
			})
			.rememberMe(rememberme->{
				rememberme
				.rememberMeParameter("remember-me")
				.tokenValiditySeconds(60)
				.key("rememberKey")
				.userDetailsService(userService)
				.authenticationSuccessHandler(loginSuccessHandler)
				.useSecureCookie(false);
			})
			.sessionManagement(s->{
				s
				.sessionFixation().changeSessionId()
				.invalidSessionUrl("/")
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false)
				.expiredUrl("/");
			})
			.oauth2Login(oauth2Login->{
				oauth2Login
				.userInfoEndpoint(use->{
					use.userService(userSocialService);
				});
			})
			;
			
			
			return security.build();
		}

}
