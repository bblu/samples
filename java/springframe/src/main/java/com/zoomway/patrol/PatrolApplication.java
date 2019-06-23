package com.zoomway.patrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
public class PatrolApplication {

	private final static Logger logger = LoggerFactory
			.getLogger(PatrolApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(PatrolApplication.class, args);
	}

}
