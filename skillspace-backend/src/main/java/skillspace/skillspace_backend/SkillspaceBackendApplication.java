package skillspace.skillspace_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SkillspaceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillspaceBackendApplication.class, args);
	}

}
