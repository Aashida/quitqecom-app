package com.quitqecom.config;

import com.quitqecom.exceptions.FileInvalidExtensionException;
import com.quitqecom.exceptions.ResourceNotFoundException;
import com.quitqecom.utility.ResponseUtility;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private ResponseUtility responseUtility;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseUtility> handleResourceNotFoundException(
            ResourceNotFoundException e){
        logger.error("Error in fetching user details {}", e.getMessage());
        responseUtility.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtility);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, Principal principal){
        logger.warn("Validation failed for user {} ", principal.getName() );
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errorList = bindingResult.getFieldErrors();

        Map<String, String> map = new HashMap<>();
        for(FieldError error : errorList){
            map.put(error.getField(), error.getDefaultMessage());
            logger.error("Field {} - message: {}", error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity
                .badRequest()
                .body(map);
    }

    @ExceptionHandler(
            FileNotFoundException.class)
    public ResponseEntity<String>
    handleFileNotFoundException(
            FileNotFoundException e){

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(
            FileInvalidExtensionException.class)
    public ResponseEntity<String>
    handleFileInvalidExtensionException(
            FileInvalidExtensionException e){

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
