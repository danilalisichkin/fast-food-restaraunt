package com.fastfoodrestaraunt.backend.exception;

import com.fastfoodrestaraunt.backend.core.constant.ErrorCauses;
import com.fastfoodrestaraunt.backend.core.dto.error.ErrorResponse;
import com.fastfoodrestaraunt.backend.core.dto.error.MultiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ErrorCauses.BAD_REQUEST,
                        e.getMessage()));
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrorException(ValidationErrorException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ErrorCauses.VALIDATION,
                        e.getMessage()));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ErrorCauses.BAD_REQUEST,
                        ErrorCauses.CANT_READ_REQUEST));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<MultiErrorResponse> handleValidationException(Exception e) {
        Map<String, List<String>> errorMap = new HashMap<>();

        getValidationErrors(errorMap, e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MultiErrorResponse(
                        ErrorCauses.VALIDATION,
                        errorMap));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        ErrorCauses.UNAUTHORIZED,
                        e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        ErrorCauses.FORBIDDEN,
                        e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ErrorCauses.NOT_FOUND,
                        e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ErrorCauses.NOT_FOUND,
                        e.getMessage()));
    }

    @ExceptionHandler(DataUniquenessConflictException.class)
    public ResponseEntity<ErrorResponse> handleDataUniquenessConflictException(DataUniquenessConflictException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        ErrorCauses.UNIQUENESS_CONFLICT,
                        e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(Exception e) {
        log.error("internal server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        ErrorCauses.CONTACT_DEVELOPERS,
                        ErrorCauses.INTERNAL));
    }

    private void getValidationErrors(Map<String, List<String>> errorMap, Exception e) {
        BiConsumer<String, String> addError = (field, message) ->
                errorMap.computeIfAbsent(field, k -> new ArrayList<>()).add(message);

        if (e instanceof MethodArgumentNotValidException validationEx) {
            validationEx.getBindingResult()
                    .getFieldErrors().forEach(error ->
                            addError.accept(error.getField(), error.getDefaultMessage()));
        } else if (e instanceof ConstraintViolationException constraintEx) {
            constraintEx.getConstraintViolations()
                    .forEach(violation ->
                            addError.accept(violation.getPropertyPath().toString(), violation.getMessage()));
        }
    }
}
