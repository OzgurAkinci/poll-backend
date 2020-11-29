package com.app.pollbackend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JoinResultQuestion implements Serializable {
    private int questionId;
    private String questionTitle;
    private List<JoinResultOption> options;
    private Integer questionType;
}
