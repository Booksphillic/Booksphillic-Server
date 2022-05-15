package com.booksphillic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
public class BooksphillicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksphillicApplication.class, args);
	}

}
