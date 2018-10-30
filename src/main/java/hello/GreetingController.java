package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @RequestMapping("/greeting")
    public String index() {
        return "{\"message\": \"Hello, world\"}";
    }
}