package ru.goryacheva.project.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorUtils {
    public static void errorsTo(BindingResult error){
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> listErrors = error.getFieldErrors();

        listErrors.forEach(e -> errorMessage
                .append(e.getField())
                .append(" - ")
                .append(e.getDefaultMessage() == null ? e.getCode() : e.getDefaultMessage())
                .append("; "));
         throw new MeasurementException(errorMessage.toString());
    }
}
