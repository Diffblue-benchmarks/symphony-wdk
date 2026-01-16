package com.symphony.bdk.workflow.exception;

import com.symphony.bdk.workflow.api.v1.dto.ErrorResponse;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import jakarta.persistence.OptimisticLockException;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerClaudeTest {

  private GlobalExceptionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GlobalExceptionHandler();
  }

  @Test
  void testConstructor() {
    // Test that constructor properly initializes the handler
    GlobalExceptionHandler newHandler = new GlobalExceptionHandler();

    assertThat(newHandler).isNotNull();
    assertThat(newHandler).isInstanceOf(GlobalExceptionHandler.class);
  }

  @Test
  void testHandleThrowable_withRuntimeException() {
    // Test handling of generic Throwable - returns 500 INTERNAL_SERVER_ERROR
    Throwable exception = new RuntimeException("Something went wrong");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Internal server error, something went wrong.");
  }

  @Test
  void testHandleThrowable_withError() {
    // Test handling of Error type - returns 500 INTERNAL_SERVER_ERROR
    Throwable exception = new Error("Critical error occurred");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Internal server error, something went wrong.");
  }

  @Test
  void testHandleThrowable_withNullMessage() {
    // Test handling of Throwable with null message
    Throwable exception = new RuntimeException((String) null);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Internal server error, something went wrong.");
  }

  @Test
  void testHandleUnauthorizedException_withMessage() {
    // Test handling of UnauthorizedException - returns 401 UNAUTHORIZED
    String errorMessage = "User not authorized to access this resource";
    UnauthorizedException exception = new UnauthorizedException(errorMessage);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
  }

  @Test
  void testHandleUnauthorizedException_withNullMessage() {
    // Test handling of UnauthorizedException with null message
    UnauthorizedException exception = new UnauthorizedException(null);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isNull();
  }

  @Test
  void testHandleDuplicateException_withMessage() {
    // Test handling of DuplicateException - returns 400 BAD_REQUEST
    String errorMessage = "Workflow with this ID already exists";
    DuplicateException exception = new DuplicateException(errorMessage);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
  }

  @Test
  void testHandleDuplicateException_withNullMessage() {
    // Test handling of DuplicateException with null message
    DuplicateException exception = new DuplicateException(null);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isNull();
  }

  @Test
  void testHandleNotFoundException_withMessage() {
    // Test handling of NotFoundException - returns 404 NOT_FOUND
    String errorMessage = "Workflow not found with ID: workflow123";
    NotFoundException exception = new NotFoundException(errorMessage);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
  }

  @Test
  void testHandleNotFoundException_withNullMessage() {
    // Test handling of NotFoundException with null message
    NotFoundException exception = new NotFoundException(null);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isNull();
  }

  @Test
  void testHandleIllegalArgumentException_withMessage() {
    // Test handling of IllegalArgumentException - returns 400 BAD_REQUEST
    String errorMessage = "Invalid argument provided: workflowId cannot be empty";
    IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
  }

  @Test
  void testHandleIllegalArgumentException_withNullMessage() {
    // Test handling of IllegalArgumentException with null message
    IllegalArgumentException exception = new IllegalArgumentException((String) null);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isNull();
  }

  @Test
  void testHandleUnsupportedOperationException_withMessage() {
    // Test handling of UnsupportedOperationException - returns 422 UNPROCESSABLE_ENTITY
    String errorMessage = "This operation is not supported for workflow type: SWADL";
    UnsupportedOperationException exception = new UnsupportedOperationException(errorMessage);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
  }

  @Test
  void testHandleUnsupportedOperationException_withNullMessage() {
    // Test handling of UnsupportedOperationException with null message
    UnsupportedOperationException exception = new UnsupportedOperationException((String) null);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isNull();
  }

  @Test
  void testHandleOptimisticLockException_withMessage() {
    // Test handling of OptimisticLockException - returns 409 CONFLICT
    OptimisticLockException exception = new OptimisticLockException("Entity was updated by another transaction");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage())
        .isEqualTo("Workflow being updated is outdated, please refresh then update again.");
  }

  @Test
  void testHandleOptimisticLockException_withNullMessage() {
    // Test handling of OptimisticLockException with null message
    OptimisticLockException exception = new OptimisticLockException((String) null);

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage())
        .isEqualTo("Workflow being updated is outdated, please refresh then update again.");
  }

  @Test
  void testHandleInvalidActivityException_withMessage() {
    // Test handling of InvalidActivityException - returns 400 BAD_REQUEST
    InvalidActivityException exception = new InvalidActivityException("workflow123", "Invalid gateway configuration");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage())
        .isEqualTo("Invalid activity in the workflow workflow123: Invalid gateway configuration");
  }

  @Test
  void testHandleInvalidActivityException_withNullWorkflowId() {
    // Test handling of InvalidActivityException with null workflowId
    InvalidActivityException exception = new InvalidActivityException(null, "Missing required field");

    ResponseEntity<ErrorResponse> response = handler.handle(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage())
        .contains("Invalid activity in the workflow null: Missing required field");
  }

  @Test
  void testHandleMethodArgumentNotValid_withSingleFieldError() {
    // Test handling of MethodArgumentNotValidException with single field error
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("workflow", "name", "Name is required");

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    WebRequest request = mock(WebRequest.class);

    ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception, headers, status, request);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, String> errors = (Map<String, String>) response.getBody();
    assertThat(errors).containsEntry("name", "Name is required");
  }

  @Test
  void testHandleMethodArgumentNotValid_withMultipleFieldErrors() {
    // Test handling of MethodArgumentNotValidException with multiple field errors
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError1 = new FieldError("workflow", "name", "Name is required");
    FieldError fieldError2 = new FieldError("workflow", "version", "Version must be positive");
    FieldError fieldError3 = new FieldError("workflow", "description", "Description is too long");

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2, fieldError3));

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    WebRequest request = mock(WebRequest.class);

    ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception, headers, status, request);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, String> errors = (Map<String, String>) response.getBody();
    assertThat(errors).hasSize(3);
    assertThat(errors).containsEntry("name", "Name is required");
    assertThat(errors).containsEntry("version", "Version must be positive");
    assertThat(errors).containsEntry("description", "Description is too long");
  }

  @Test
  void testHandleMethodArgumentNotValid_withDuplicateFieldErrors() {
    // Test handling of MethodArgumentNotValidException with duplicate field errors
    // When same field has multiple errors, they should be concatenated with newline
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError1 = new FieldError("workflow", "name", "Name is required");
    FieldError fieldError2 = new FieldError("workflow", "name", "Name must be at least 3 characters");

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    WebRequest request = mock(WebRequest.class);

    ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception, headers, status, request);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, String> errors = (Map<String, String>) response.getBody();
    assertThat(errors).hasSize(1);
    assertThat(errors.get("name")).contains("Name is required");
    assertThat(errors.get("name")).contains("Name must be at least 3 characters");
  }

  @Test
  void testHandleMethodArgumentNotValid_withEmptyFieldErrors() {
    // Test handling of MethodArgumentNotValidException with no field errors
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList());

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    WebRequest request = mock(WebRequest.class);

    ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception, headers, status, request);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, String> errors = (Map<String, String>) response.getBody();
    assertThat(errors).isEmpty();
  }

  @Test
  void testHandleMethodArgumentNotValid_withDifferentHttpStatus() {
    // Test handling of MethodArgumentNotValidException with different HTTP status
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("workflow", "id", "Invalid ID format");

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));

    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    WebRequest request = mock(WebRequest.class);

    ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception, headers, status, request);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(response.getBody()).isInstanceOf(Map.class);

    @SuppressWarnings("unchecked")
    Map<String, String> errors = (Map<String, String>) response.getBody();
    assertThat(errors).containsEntry("id", "Invalid ID format");
  }

  @Test
  void testHandleMethodArgumentNotValid_withCustomHeaders() {
    // Test handling of MethodArgumentNotValidException with custom headers
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("workflow", "status", "Invalid status");

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));

    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Custom-Header", "CustomValue");
    HttpStatus status = HttpStatus.BAD_REQUEST;
    WebRequest request = mock(WebRequest.class);

    ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception, headers, status, request);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getHeaders()).isNotNull();
  }

  @Test
  void testMultipleExceptionTypes_returnDifferentStatusCodes() {
    // Test that different exception types return different HTTP status codes
    UnauthorizedException unauthorized = new UnauthorizedException("Not authorized");
    DuplicateException duplicate = new DuplicateException("Already exists");
    NotFoundException notFound = new NotFoundException("Not found");
    IllegalArgumentException illegalArg = new IllegalArgumentException("Bad argument");
    UnsupportedOperationException unsupported = new UnsupportedOperationException("Not supported");
    OptimisticLockException optimisticLock = new OptimisticLockException("Lock conflict");
    InvalidActivityException invalidActivity = new InvalidActivityException("wf1", "Invalid");

    ResponseEntity<ErrorResponse> unauthorizedResp = handler.handle(unauthorized);
    ResponseEntity<ErrorResponse> duplicateResp = handler.handle(duplicate);
    ResponseEntity<ErrorResponse> notFoundResp = handler.handle(notFound);
    ResponseEntity<ErrorResponse> illegalArgResp = handler.handle(illegalArg);
    ResponseEntity<ErrorResponse> unsupportedResp = handler.handle(unsupported);
    ResponseEntity<ErrorResponse> optimisticLockResp = handler.handle(optimisticLock);
    ResponseEntity<ErrorResponse> invalidActivityResp = handler.handle(invalidActivity);

    assertThat(unauthorizedResp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(duplicateResp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(notFoundResp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(illegalArgResp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(unsupportedResp.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    assertThat(optimisticLockResp.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    assertThat(invalidActivityResp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void testAllExceptionHandlers_returnNonNullResponseEntity() {
    // Test that all exception handlers return non-null ResponseEntity with body
    ResponseEntity<ErrorResponse> throwableResp = handler.handle(new RuntimeException("error"));
    ResponseEntity<ErrorResponse> unauthorizedResp = handler.handle(new UnauthorizedException("error"));
    ResponseEntity<ErrorResponse> duplicateResp = handler.handle(new DuplicateException("error"));
    ResponseEntity<ErrorResponse> notFoundResp = handler.handle(new NotFoundException("error"));
    ResponseEntity<ErrorResponse> illegalArgResp = handler.handle(new IllegalArgumentException("error"));
    ResponseEntity<ErrorResponse> unsupportedResp = handler.handle(new UnsupportedOperationException("error"));
    ResponseEntity<ErrorResponse> optimisticLockResp = handler.handle(new OptimisticLockException("error"));
    ResponseEntity<ErrorResponse> invalidActivityResp = handler.handle(new InvalidActivityException("wf", "error"));

    assertThat(throwableResp).isNotNull();
    assertThat(throwableResp.getBody()).isNotNull();
    assertThat(unauthorizedResp).isNotNull();
    assertThat(unauthorizedResp.getBody()).isNotNull();
    assertThat(duplicateResp).isNotNull();
    assertThat(duplicateResp.getBody()).isNotNull();
    assertThat(notFoundResp).isNotNull();
    assertThat(notFoundResp.getBody()).isNotNull();
    assertThat(illegalArgResp).isNotNull();
    assertThat(illegalArgResp.getBody()).isNotNull();
    assertThat(unsupportedResp).isNotNull();
    assertThat(unsupportedResp.getBody()).isNotNull();
    assertThat(optimisticLockResp).isNotNull();
    assertThat(optimisticLockResp.getBody()).isNotNull();
    assertThat(invalidActivityResp).isNotNull();
    assertThat(invalidActivityResp.getBody()).isNotNull();
  }
}
