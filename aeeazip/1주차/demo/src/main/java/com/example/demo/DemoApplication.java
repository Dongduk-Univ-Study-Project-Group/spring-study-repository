package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// 톰캣 웹 서버 내장하고 있는 SpringApplication 실행
		SpringApplication.run(DemoApplication.class, args);
	}
}
