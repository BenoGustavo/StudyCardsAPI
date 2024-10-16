package com.gorges.studycardsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class StudyCardsApiApplication {

	@GetMapping("/")
	public String index() {
		return "<strong>Hello World!</strong></br>Welcome, see the docs in <a href=\"/api-docs\" >api-docs</a>";
	}

	@GetMapping("/api-docs")
	public RedirectView apiDocs() {
		return new RedirectView("/swagger-ui.html");
	}

	public static void main(String[] args) {
		SpringApplication.run(StudyCardsApiApplication.class, args);
	}

}
