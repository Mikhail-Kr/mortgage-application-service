package ru.mkr.mortgageapplicationservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ru.mkr.mortgageapplicationservice.customer.CustomerRepository;


@OpenAPIDefinition(
		info = @Info(
				title = "Mortgage Application",
				description = "API ипотечных заявок",
				version = "1.0"
		)
)
@SpringBootApplication
public class MortgageApplicationServiceApplication extends SpringBootServletInitializer {

	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(MortgageApplicationServiceApplication.class, args);
	}

}
