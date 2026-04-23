package com.symphony.bdk.workflow.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.api.v1.dto.ErrorResponse;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;

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

  private final GlobalExceptionHandler underTest = new GlobalExceptionHandler();

  @Test
  void shouldReturnInternalServerErrorWhenHandlingGenericThrowable() {
    Throwable exception = new RuntimeException("unexpected error");

    ResponseEntity<ErrorResponse> response = underTest.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Internal server error, something went wrong.");
  }

  @Test
  void shouldReturnUnauthorizedWhenHandlingUnauthorizedException() {
    UnauthorizedException exception = new UnauthorizedException("not authorized");

    ResponseEntity<ErrorResponse> response = underTest.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("not authorized");
  }

  @Test
  void shouldReturnBadRequestWhenHandlingDuplicateException() {
    DuplicateException exception = new DuplicateException("duplicate entry");

    ResponseEntity<ErrorResponse> response = underTest.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("duplicate entry");
  }

  @Test
  void shouldReturnNotFoundWhenHandlingNotFoundException() {
    NotFoundException exception = new NotFoundException("resource not found");

    ResponseEntity<ErrorResponse> response = underTest.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("resource not found");
  }

  @Test
  void shouldReturnBadRequestWhenHandlingIllegalArgumentException() {
    IllegalArgumentException exception = new IllegalArgumentException("bad argument");

    ResponseEntity<ErrorResponse> response = underTest.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("bad argument");
  }

  @Test
  void shouldReturnUnprocessableEntityWhenHandlingUnsupportedOperationException() {
    UnsupportedOperationException exception = new UnsupportedOperationException("not supported");

    ResponseEntity<ErrorResponse> response = underTest.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("not supported");
  }

  @Test
  void shouldReturnConflictWhenHandlingOptimisticLockException() {
    OptimisticLockException exception = new OptimisticLockException("lock conflict");

    ResponseEntity<ErrorResponse> response = underTest.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo(
        "Workflow being updated is outdated, please refresh then update again.");
  }

  @Test
  void shouldReturnBadRequestWhenHandlingInvalidActivityException() {
    InvalidActivityException exception = new InvalidActivityException("wf1", "bad activity");

    ResponseEntity<ErrorResponse> response = underTest.handle(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo(exception.getMessage());
  }

  @Test
  void shouldReturnFieldErrorsWhenHandlingMethodArgumentNotValid() {
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("obj", "field1", "must not be null");
    when(ex.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
    HttpHeaders headers = new HttpHeaders();
    WebRequest request = mock(WebRequest.class);

    ResponseEntity<Object> response =
        underTest.handleMethodArgumentNotValid(ex, headers, HttpStatus.BAD_REQUEST, request);

    assertThat(response).isNotNull();
  }
}
