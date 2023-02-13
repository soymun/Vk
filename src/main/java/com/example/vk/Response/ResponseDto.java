package com.example.vk.Response;

import lombok.Builder;

@Builder
public class ResponseDto {

    private Object data;

    private String error;
}
