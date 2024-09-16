package com.gorges.studycardsapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping("/")
    public String index() {
        return "<strong>Hello World!</strong></br>Welcome, see the docs in <a href=\"/api-docs\" >api-docs</a>";
    }

    @GetMapping("/api-docs")
    public RedirectView apiDocs() {
        return new RedirectView("/swagger-ui.html");
    }
}