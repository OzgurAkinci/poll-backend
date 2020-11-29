package com.app.pollbackend.dto;

import lombok.Data;

@Data
public class JoinResultOption {
    private int optionId;
    private String optionTitle;
    private Boolean selected = false;
}
