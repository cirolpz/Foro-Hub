package alura.forohub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ForohubApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForohubApplication.class, args);
	}
}
