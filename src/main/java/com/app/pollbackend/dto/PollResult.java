package com.app.pollbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PollResult {
    private String question;
    private List<PollResponseDetail> response;
}
