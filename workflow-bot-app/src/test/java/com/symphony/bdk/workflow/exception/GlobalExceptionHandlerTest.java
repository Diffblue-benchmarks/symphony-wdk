package com.symphony.bdk.workflow.exception;

import com.symphony.bdk.workflow.api.v1.dto.ErrorResponse;

import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @Test
  void testUnauthorizedException() {
    ErrorResponse expectedErrorResponse = new ErrorResponse("Unauthorized exception's message");
    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handle(new UnauthorizedException("Unauthorized exception's message"));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
  }

  @Test
  void testIllegalArgumentException() {
    ErrorResponse expectedErrorResponse = new ErrorResponse("Illegal argument exception's message");
    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handle(new IllegalArgumentException("Illegal argument exception's message"));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
  }

  @Test
  void testUnsupportedOperationException() {
    ErrorResponse expectedErrorResponse = new ErrorResponse("Unsupported operation exception's message");
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handle(
        new UnsupportedOperationException("Unsupported operation exception's message"));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
  }

  @Test
  void testNotFoundException() {
    ErrorResponse expectedErrorResponse = new ErrorResponse("NotFound exception's message");
    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handle(new NotFoundException("NotFound exception's message"));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
  }

  @Test
  void testDuplicateException() {
    ErrorResponse expectedErrorResponse = new ErrorResponse("Duplicate exception's message");
    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handle(new DuplicateException("Duplicate exception's message"));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
  }

  @Test
  void testOptimisticLockException() {
    ErrorResponse expectedErrorResponse =
        new ErrorResponse("Workflow being updated is outdated, please refresh then update again.");
    ResponseEntity<ErrorResponse> response =
        globalExceptionHandler.handle(new OptimisticLockException("Optimistic lock message"));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
  }

  @Test
  void testInternalServerException() {
    ErrorResponse expectedErrorResponse = new ErrorResponse("Internal server error, something went wrong.");
    Throwable throwable = new Throwable("Throwable exception's message");
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handle(throwable);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
  }

  @Test
  @SuppressWarnings("unchecked")
  void testHandleMethodArgumentNotValid() {
    // Arrange
    BindException bindException = new BindException("Target", "objectName");
    bindException.addError(new FieldError("objectName", "fieldName", "must not be blank"));
    MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindException);
    HttpHeaders headers = new HttpHeaders();
    ServletWebRequest request = new ServletWebRequest(new MockHttpServletRequest());

    // Act
    ResponseEntity<Object> response = globalExceptionHandler.handleMethodArgumentNotValid(
        ex, headers, HttpStatus.BAD_REQUEST, request);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isInstanceOf(Map.class);
    Map<String, String> errors = (Map<String, String>) response.getBody();
    assertThat(errors).containsEntry("fieldName", "must not be blank");
  }
}
