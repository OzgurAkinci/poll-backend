package com.app.pollbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class JoinResultQuestion {
    private int questionId;
    private String questionTitle;
    private List<JoinResultOption> options;
    private Integer questionType;
}
