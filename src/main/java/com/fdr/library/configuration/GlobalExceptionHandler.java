package com.fdr.library.configuration;


import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> catchAny(Exception ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(pd.getStatus()).body(pd);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> catchBadRequest(BadRequestException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(pd.getStatus()).body(pd);
    }

    // Throwable
    // -> Error
    // -> Exception
    // --> RuntimeException
    // --> IOException
    // ---> BadRequestException


}
