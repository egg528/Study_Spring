package com.example.mvc.controller;

import com.example.mvc.spring.DuplicateMemberException;
import com.example.mvc.spring.MemberRegisterService;
import com.example.mvc.spring.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final MemberRegisterService memberRegisterService;

    @RequestMapping("/step1")
    public String handleStep1(){
        return "register/step1";
    }

    @PostMapping("/step2")
    public String handleStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree){
        if(!agree) return "register/step1";

        return "register/step2";
    }

    @GetMapping("/step2")
    public String handleStep2Get(){
        return "redirect:/register/step1";
    }

    @PostMapping("/step3")
    public String handleStep3(RegisterRequest regReq){
        try{
            System.out.println(regReq.getEmail());
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (DuplicateMemberException ex){
            System.out.println("Error");
            return "register/step2";
        }
    }
}
