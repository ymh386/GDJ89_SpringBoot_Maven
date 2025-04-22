package com.moon.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@EnableAspectJAutoProxy
//@EnableTransactionManagement
@ServletComponentScan
public class Gdj89SpringBootMavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gdj89SpringBootMavenApplication.class, args);
	}

}
