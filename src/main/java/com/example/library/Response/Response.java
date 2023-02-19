package com.example.library.Response;

import lombok.Data;

@Data
public class Response {

    public Response(int code, String message){
        this.code=code;
        this.message=message;
    }

    int code;
    String message;
}
