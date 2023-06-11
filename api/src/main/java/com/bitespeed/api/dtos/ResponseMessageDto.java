package com.bitespeed.api.dtos;

public class ResponseMessageDto implements ResponseDto{

    public ResponseMessageDto() {}
    
    public ResponseMessageDto(String message) {
        this.message = message;
    }
    
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
        
}
