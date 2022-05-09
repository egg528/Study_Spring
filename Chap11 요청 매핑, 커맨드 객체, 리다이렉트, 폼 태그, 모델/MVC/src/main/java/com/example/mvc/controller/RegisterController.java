package com.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @RequestMapping("/step1")
    public String handleStep1(){
        return "register/step1";
    }

    @RequestMapping("/step2")
    public String handleStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree){
        if(!agree) return "register/step1";

        return "register/step2";
    }

    @RequestMapping("/step3")
    public String handleStep3(){
        return "register/step3";
    }
}
