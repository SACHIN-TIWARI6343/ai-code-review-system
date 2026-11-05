package com.sachin.aicodereview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    @NotBlank
    private  String language;

    @NotBlank
    private  String code;

}
