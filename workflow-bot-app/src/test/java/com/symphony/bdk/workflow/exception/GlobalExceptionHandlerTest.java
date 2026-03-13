package com.symphony.bdk.workflow.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.api.v1.dto.ErrorResponse;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import jakarta.persistence.OptimisticLockException;

import java.util.List;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GlobalExceptionHandler();
  }

  @Test
  void shouldHandleGenericThrowableWithInternalServerError() {
    Throwable exception = new RuntimeException("Something went wrong");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Internal server error, something went wrong.");
  }

  @Test
  void shouldHandleUnauthorizedExceptionWithUnauthorizedStatus() {
    UnauthorizedException exception = new UnauthorizedException("Invalid credentials");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid credentials");
  }

  @Test
  void shouldHandleDuplicateExceptionWithBadRequestStatus() {
    DuplicateException exception = new DuplicateException("Resource already exists");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Resource already exists");
  }

  @Test
  void shouldHandleNotFoundExceptionWithNotFoundStatus() {
    NotFoundException exception = new NotFoundException("Resource not found");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Resource not found");
  }

  @Test
  void shouldHandleIllegalArgumentExceptionWithBadRequestStatus() {
    IllegalArgumentException exception = new IllegalArgumentException("Invalid parameter");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid parameter");
  }

  @Test
  void shouldHandleUnsupportedOperationExceptionWithUnprocessableEntityStatus() {
    UnsupportedOperationException exception = new UnsupportedOperationException("Operation not supported");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Operation not supported");
  }

  @Test
  void shouldHandleOptimisticLockExceptionWithConflictStatus() {
    OptimisticLockException exception = new OptimisticLockException("Lock conflict");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage())
        .isEqualTo("Workflow being updated is outdated, please refresh then update again.");
  }

  @Test
  void shouldHandleInvalidActivityExceptionWithBadRequestStatus() {
    InvalidActivityException exception = new InvalidActivityException("workflow123", "Invalid activity");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage())
        .contains("Invalid activity in the workflow workflow123: Invalid activity");
  }

  @Test
  void shouldHandleMethodArgumentNotValidWithFieldErrors() {
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError1 = new FieldError("object", "field1", "Error message 1");
    FieldError fieldError2 = new FieldError("object", "field2", "Error message 2");

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    WebRequest request = mock(WebRequest.class);

    ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception, headers, status, request);

    assertThat(response).isNotNull();
  }
}
