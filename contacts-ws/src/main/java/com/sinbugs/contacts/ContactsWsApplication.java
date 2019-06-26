package com.sinbugs.contacts;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = {"com.sinbugs.controllers"})
@EnableScheduling
public class ContactsWsApplication implements ApplicationRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(ContactsWsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ContactsWsApplication.class, args);
	}

	
	/**
	 * Habilitanto Application Runners
	 * 
	 * Esta funcionalidad es para ejecutar código después que se haya lanzado el Boot normal de spring
	 * con tomcat.
	 * 
	 * Se debe incorporar a la clase "implements ApplicationRunner" para que reconozca el run()
	 * 
	 */
	@Override
	   public void run(ApplicationArguments arg0) throws Exception {
	      System.out.println("Hello World from Application Runner");
	      logger.info("Hello World from Application Runner by Logger...");
	      
	   }
}
