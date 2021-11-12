package shop.sss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SssApplication {

	public static void main(String[] args) {
		SpringApplication.run(SssApplication.class, args);
		System.out.println("안녕");
	}

}
