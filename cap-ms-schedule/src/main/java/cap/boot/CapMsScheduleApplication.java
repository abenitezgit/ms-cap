package cap.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import cap.controllers.AppProperties;

@SpringBootApplication(scanBasePackages = {"cap.controllers","cap.services"})
@EnableConfigurationProperties(AppProperties.class)
@EnableScheduling
public class CapMsScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapMsScheduleApplication.class, args);
	}

}
