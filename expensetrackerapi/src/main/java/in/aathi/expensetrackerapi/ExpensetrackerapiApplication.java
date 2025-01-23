package in.aathi.expensetrackerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "in.aathi.expensetrackerapi")
public class ExpensetrackerapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensetrackerapiApplication.class, args);
	}

}
