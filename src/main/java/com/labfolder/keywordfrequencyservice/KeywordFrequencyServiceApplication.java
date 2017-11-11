package com.labfolder.keywordfrequencyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.labfolder.controllers", "com.labfolder.business"})
public class KeywordFrequencyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeywordFrequencyServiceApplication.class, args);

	}
}
