package imd.ufrn.com.br.smart_space_booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SmartSpaceBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartSpaceBookingApplication.class, args);
	}

}
