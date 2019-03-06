package iful.edu.bg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "iful.edu.bg")
public class BusyGuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusyGuestApplication.class, args);
	}

}