package com.example.mvc.controller;


import com.example.mvc.survey.AnsweredData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/survey")
public class SurveyController {

    @GetMapping
    public String form(){
        return "survey/surveyForm";
    }

    @PostMapping
    public String submit(@ModelAttribute("ansData")AnsweredData data){

        for(String str : data.getResponses()) System.out.println(str);

        return "survey/submitted";
    }
}