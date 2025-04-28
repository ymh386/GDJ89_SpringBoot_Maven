package com.moon.app.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.moon.app.user.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestSchedule {
	
	@Autowired
	private UserDAO userDAO;
	
	//@Scheduled(fixedRateString = "1000", initialDelay = 3000)
	public void useFixRate() {
		log.info("USE FixRate");
		//언제 종료 되는지 상관하지 않고 1초 후 바로 실행
	}
	
	//@Scheduled(fixedDelay = 1000, initialDelayString = "4000")
	public void useFixDelay() {
		log.info("USE FixDelay");
		
	}
	
	/**
	 * 		*(초 0~59) *(분 0~59) *(시 0~23) *(일 1-31) *(월 1-12) *(요일)
	 * 
	 * 		요일 : 0, 7 (일요일) 1 (월) ,,, 
	 *      10 	  *    *    *     *     *
	 * 
	 * */
	
	//@Scheduled(cron = "0 0 0 * * *")
	//@Scheduled(cron = "0 0 9,10 * * 1,3")
	@Scheduled(cron = "0 0 9-18/4 * * * ")	
	public void useCron() {
		log.info("USE Cron");
		
	}
	
	@Scheduled(cron = "0 0 1 1 1 * ")	
	public void useCron2() {
		log.info("USE Cron");
		
	}
		
		

}
