package cn.bblu.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "zw.tuwei.*")
public class demoApplication {

	public static void main(String[] args) {
		SpringApplication.run(demoApplication.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "index.html";
	}
}
