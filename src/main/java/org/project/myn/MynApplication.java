package org.project.myn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MynApplication extends SpringBootServletInitializer {

	// 외장 Tomcat으로 서비스를 제공하기 위해 SpringBootServletInitializer 상속이 필요함
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(MynApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MynApplication.class, args);
	}

}
