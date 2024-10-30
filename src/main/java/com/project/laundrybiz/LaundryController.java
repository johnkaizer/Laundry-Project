package com.project.laundrybiz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LaundryController {

    @GetMapping("/")
    public String login() {
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    @GetMapping("/fragments/{page}")
    public String loadPage(@PathVariable String page) {
        return "fragments/" + page;
    }

}
