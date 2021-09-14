package com.zip.weather.tracker.config;

import com.zip.weather.tracker.exception.ApplicationException;
import com.zip.weather.tracker.exception.ErrorCodes;
import com.zip.weather.tracker.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import static com.zip.weather.tracker.exception.ErrorCodes.*;
import static java.util.stream.Collectors.joining;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(ApplicationException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleApplicationException(final ApplicationException ape) {
    return ResponseEntity.status(ape.getErrorCodes().getHttpStatus())
        .body(buildErrorResponse(ape.getErrorCodes(), ape.getErrorCodes().getErrorDescription()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleBadRequestException(
      final ConstraintViolationException cve) {
    String errorMessage =
        cve.getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));

    return ResponseEntity.status(BAD_REQUEST.getHttpStatus())
        .body(buildErrorResponse(BAD_REQUEST, errorMessage));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleBadRequestException(
      final MethodArgumentNotValidException manve) {
    String errorMessage =
        manve
            .getBindingResult()
            .getAllErrors()
            .stream()
            .peek(error -> log.error(error.getDefaultMessage()))
            .map(ObjectError::getDefaultMessage)
            .collect(joining(", "));
    return ResponseEntity.status(BAD_REQUEST.getHttpStatus())
        .body(buildErrorResponse(BAD_REQUEST, errorMessage));
  }

  @ExceptionHandler(ServletRequestBindingException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleServerBindingException(
      final ServletRequestBindingException srbe) {
    return ResponseEntity.status(OPERATION_NOT_FOUND.getHttpStatus())
        .body(buildErrorResponse(OPERATION_NOT_FOUND, srbe.getMessage()));
  }

  @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleMediaTypeNotSupportedException(
      final HttpMediaTypeNotSupportedException e) {
    return ResponseEntity.status(MEDIA_TYPE_NOT_SUPPORTED.getHttpStatus())
        .body(
            buildErrorResponse(
                MEDIA_TYPE_NOT_SUPPORTED, MEDIA_TYPE_NOT_SUPPORTED.getErrorDescription()));
  }

  @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleHttpMethodNotSupportedException(
      final HttpRequestMethodNotSupportedException hrmnse) {
    return ResponseEntity.status(REQUESTED_METHOD_NOT_ALLOWED.getHttpStatus())
        .body(
            buildErrorResponse(
                REQUESTED_METHOD_NOT_ALLOWED, REQUESTED_METHOD_NOT_ALLOWED.getErrorDescription()));
  }

  private ErrorResponse buildErrorResponse(
      final ErrorCodes errorCodes, final String errorDescription) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .errorCode(errorCodes.getErrorCode())
            .errorMessage(errorCodes.name())
            .errorDescription(errorDescription)
            .build();
    return errorResponse;
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleOtherExceptions(final Exception e) {
    log.error("HandleOtherExceptions:", e);
    return ResponseEntity.status(ErrorCodes.SYSTEM_EXCEPTION.getHttpStatus())
        .body(
            ErrorResponse.builder()
                .errorCode(ErrorCodes.SYSTEM_EXCEPTION.getErrorCode())
                .errorMessage(ErrorCodes.SYSTEM_EXCEPTION.name())
                .errorDescription(ErrorCodes.SYSTEM_EXCEPTION.getErrorDescription())
                .build());
  }
}
