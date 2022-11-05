package com.blubank.doctorappointment.web;

import com.blubank.doctorappointment.Exception.IntervalDateException;
import com.blubank.doctorappointment.web.dto.StatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Handle all Exceptions in for All controllers
 */
@RestControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);


    @Bean
    public ErrorAttributes errorAttributes() {
        // Hide exception field in the return object
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StatusDto<Object>>  handleAccessDeniedException(AccessDeniedException ex, HttpServletResponse res) throws IOException {
        return new ResponseEntity<>(new StatusDto<>(HttpStatus.FORBIDDEN.value(),
                "Access denied", ""),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IntervalDateException.class)
    public ResponseEntity<StatusDto<Object>> handleIntervalDateException(IntervalDateException ex, HttpServletResponse res) throws IOException {
        return new ResponseEntity<>(new StatusDto<>(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), ""),
                HttpStatus.BAD_REQUEST);
    }

 @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StatusDto<Object>> handleConstraintViolationException(ConstraintViolationException ex, HttpServletResponse res) throws IOException {
        return new ResponseEntity<>(new StatusDto<>(HttpStatus.BAD_REQUEST.value(),
                ex.getConstraintViolations().toString(), ""),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<StatusDto<Object>> handleNoSuchElementException(NoSuchElementException ex, HttpServletResponse res) throws IOException {
        return new ResponseEntity<>(new StatusDto<>(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(), ""),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<StatusDto<Object>>  handleHttpServerErrorException(HttpServerErrorException ex, HttpServletResponse res) throws IOException {
        return new ResponseEntity<>(new StatusDto<>(ex.getStatusCode().value(),
                ex.getMessage(), ""),
                ex.getStatusCode());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<StatusDto<Object>>  handleInsufficientAuthenticationException(Exception ex, HttpServletResponse res) throws IOException {
        LOGGER.error("Handled Insufficient Authentication Exception", ex);
        return new ResponseEntity<>(new StatusDto<>(HttpStatus.FORBIDDEN.value(),
                "Insufficient Authentication", ""),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusDto<Object>>  handleException(Exception ex, HttpServletResponse res) throws IOException {
        LOGGER.error("Handled Internal Error Exception", ex);
        return new ResponseEntity<>(new StatusDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong", ""),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

