package cap.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cap.controllers.AppProperties;

@SpringBootApplication(scanBasePackages = {"cap.controllers","cap.services"})
@EnableConfigurationProperties(AppProperties.class)
public class CapMsDbaccessApplication implements ApplicationRunner {

	@Autowired
	private AppProperties apProperties;
	
	public static void main(String[] args) {
		SpringApplication.run(CapMsDbaccessApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println(apProperties.getDbname());
	}


	
}
