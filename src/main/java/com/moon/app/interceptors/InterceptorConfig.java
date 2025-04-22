package com.moon.app.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(testInterceptor);
		registry
		.addInterceptor(new TestInterceptor())
		.addPathPatterns("/notice/*")
		.excludePathPatterns("/notice/detail");
	}

}
