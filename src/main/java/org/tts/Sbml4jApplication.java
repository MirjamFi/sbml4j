package org.tts;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.tts.config.DataSourceConfig;
import org.tts.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
			FileStorageProperties.class
})
public class Sbml4jApplication {

	DataSourceConfig dataSourceConfig;
	
	
	@Autowired
	public Sbml4jApplication(DataSourceConfig dataSourceConfig) {
		super();
		this.dataSourceConfig = dataSourceConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(Sbml4jApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
            dataSourceConfig.setup();
            

        };
	}
}
