package com.app.pollbackend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JoinResultOption implements Serializable {
    private int optionId;
    private String optionTitle;
    private Boolean selected = false;
}
