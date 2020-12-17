package com.banger.bangerapi.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class RunTimeException extends RuntimeException{

    private HttpStatus status;

    public RunTimeException(String message,HttpStatus status){
        super(message);
        this.status=status;
    }
}
