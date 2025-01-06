package alura.forohub;

import alura.forohub.entity.Topico;
import alura.forohub.repository.TopicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class ForohubApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForohubApplication.class, args);
	}
}
