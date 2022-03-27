package me.nes0x;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.io.File;

@SpringBootApplication
public class ImageUploaderApplication {

	public static void main(String[] args) {
		File file = new File("./user_images");

		if (!file.exists()) {
			file.mkdir();
			System.out.println("User images dir created!");
		}


		SpringApplication.run(ImageUploaderApplication.class, args);
	}

	@Bean
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}
}
