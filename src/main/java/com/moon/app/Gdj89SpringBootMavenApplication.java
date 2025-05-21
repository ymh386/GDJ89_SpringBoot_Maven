package com.moon.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication 
//@EnableAspectJAutoProxy
//@EnableTransactionManagement
@EnableScheduling
public class Gdj89SpringBootMavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gdj89SpringBootMavenApplication.class, args);
	}

}
