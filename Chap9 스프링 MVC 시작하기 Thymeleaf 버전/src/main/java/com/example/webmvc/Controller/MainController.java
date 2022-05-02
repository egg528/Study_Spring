package com.example.webmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping("/")
    public String hello(Model model, @RequestParam(value = "name", required = false) String name){
        model.addAttribute("name", name);

        return "hello";
    }

}
