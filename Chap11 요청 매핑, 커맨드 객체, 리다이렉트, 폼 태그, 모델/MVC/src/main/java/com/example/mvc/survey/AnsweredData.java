package com.example.mvc.survey;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter @Setter
public class AnsweredData {

    private List<String> reponses;
    private Respondent res;
}
