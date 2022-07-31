package com.wugablog.meta.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class BlogControllerTest {

    @GetMapping("/test/hello")
    public String hello() {
        return "index";
    }
}
