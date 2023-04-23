package com.likeyoupost.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@AllArgsConstructor
public class PostUpdateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
