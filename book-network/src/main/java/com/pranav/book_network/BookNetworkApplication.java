package com.pranav.book_network;

import com.pranav.book_network.role.Role;
import com.pranav.book_network.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync

public class BookNetworkApplication {

	public static void main(String[] args) {



		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/book_social_network",
					"postgres",
					"1234"
			);
			System.out.println("Connected to the database!");
		} catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(BookNetworkApplication.class, args);


	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(Role.builder().name("USER").build());
			}
		};
	}

}



