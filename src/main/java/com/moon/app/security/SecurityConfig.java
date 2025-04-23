package com.moon.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
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
				.defaultSuccessUrl("/")
				.failureUrl("/user/login")
				.permitAll();
			})
			
			//Logout 관련 설정
			.logout(logout ->{
				logout
				.logoutUrl("/user/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.permitAll();
			})
			
			;
			
			
			return security.build();
		}

}
