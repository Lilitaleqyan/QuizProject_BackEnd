package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlayerDTO {
    private Long id;
    private String userName;
    private String email;
    private int score;

}
