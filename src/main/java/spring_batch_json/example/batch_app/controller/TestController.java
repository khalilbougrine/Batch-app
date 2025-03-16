package spring_batch_json.example.batch_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class TestController {

    @GetMapping("/hello")
    public String publicEndpoint() {
        return "Hello, this is a public endpoint!";
    }
}