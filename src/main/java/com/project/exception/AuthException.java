package com.project.exception;

import com.project.model.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthException extends  RuntimeException{

    private ErrorResponse response;

    @Override
    public String getMessage() {
        return response.getMessage();
    }
}
