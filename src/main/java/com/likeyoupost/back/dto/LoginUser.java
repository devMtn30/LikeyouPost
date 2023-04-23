package com.likeyoupost.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUser {
    private Long userId;
    private String accountType;
}
