package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.test.eventlogger.EventLogger;

@EnableJpaRepositories( "com.test.eventlogger.model.repo" )
@EntityScan( "com.test.eventlogger.model.entity" )
@EnableTransactionManagement
@SpringBootApplication
public class App implements CommandLineRunner {

	static final Logger logger = LoggerFactory.getLogger(App.class);

	@Autowired
	EventLogger eventLogger;

	public static void main(String[] args) {
		SpringApplication.run( App.class, args );
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		logger.info("Starting Event Logger");
		eventLogger.processFiles( args );
	}
}