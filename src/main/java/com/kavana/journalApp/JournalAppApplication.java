package com.kavana.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAppApplication {

	public static void main(String[] args) {

	ConfigurableApplicationContext configurableApplicationContext =SpringApplication.run(JournalAppApplication.class, args);
	ConfigurableEnvironment environment= configurableApplicationContext.getEnvironment();
	System.out.println(Arrays.toString(environment.getActiveProfiles()));
	}
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

}
