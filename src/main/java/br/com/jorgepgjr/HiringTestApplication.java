package br.com.jorgepgjr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The only Configuration class
 * @author jorge
 *
 */
@SpringBootApplication
public class HiringTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiringTestApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
