package com.app.pollbackend.dto;

import lombok.Data;

@Data
public class PollResponseDetail {
    private String optionNo;
    private String option;
    private Integer responseCount;
}
