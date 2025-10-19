package com.paste_bin_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PasteBinCloneApplication {
	public static void main(String[] args) {
		SpringApplication.run(PasteBinCloneApplication.class, args);
	}
}
