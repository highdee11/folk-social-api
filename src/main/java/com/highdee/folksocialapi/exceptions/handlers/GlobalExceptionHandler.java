package com.highdee.folksocialapi.exceptions.handlers;

import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<HashMap<String, String>>> handleValidationError(MethodArgumentNotValidException ex){
        HashMap<String, String> error = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((err)-> {
            error.put(((FieldError) err).getField(), err.getDefaultMessage());
        });

        ResponseCode resp = ResponseCode.VALIDATION_ERROR;

        return ResponseEntity
                .status(400)
                .body(new RestResponse<>(resp.getCode(), resp.getMessage(), error));
    }

    @ExceptionHandler
    public ResponseEntity<RestResponse<Object>> handleOtherError(Exception ex){
        System.out.println(ex.getMessage());
        ResponseCode responseCode = ResponseCode.SERVER_ERROR;
        RestResponse<Object> response = new RestResponse<Object>(responseCode.getCode(), ex.getMessage(), null);
        return ResponseEntity.status(400).body(response);
    }
}
