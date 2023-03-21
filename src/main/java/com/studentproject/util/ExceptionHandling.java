package com.studentproject.util;

    import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

    @RestControllerAdvice
    public class ExceptionHandling {
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        @ExceptionHandler(NoSuchElementException.class)
        public Map<String,String> handleNoSuchElementException(NoSuchElementException noSuchElementException){
            Map<String,String>error =new HashMap<>();
            error.put("error message",noSuchElementException.getMessage());
            return error;
        }

        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        @ExceptionHandler(DataNotFoundException.class)
        public Map<String,String> handleDataNotFoundException(DataNotFoundException dataNotFoundException){
            Map<String,String>error =new HashMap<>();
            error.put("error message",dataNotFoundException.getMessage());
            return error;
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(InvalidDataAccessApiUsageException.class)
        public Map<String,String> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException invalidDataAccessApiUsageException){
            Map<String,String>error =new HashMap<>();
            error.put("error message",invalidDataAccessApiUsageException.getMessage());
            return error;
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(DuplicateFormatFlagsException.class)
        public Map<String,String> handleDuplicateFormatFlagsException(DuplicateFormatFlagsException duplicateFormatFlagsException){
            Map<String,String>error =new HashMap<>();
            error.put("error message",duplicateFormatFlagsException.getMessage());
            return error;
        }


    }

