package com.symphony.bdk.workflow.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import camundajar.impl.scala.StringContext;
import com.symphony.bdk.workflow.api.v1.dto.ErrorResponse;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;
import jakarta.persistence.OptimisticLockException;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerDiffblueTest {
  @Autowired
  private GlobalExceptionHandler globalExceptionHandler;

  /**
   * Method under test: {@link GlobalExceptionHandler#handle(DuplicateException)}
   */
  @Test
  void testHandle() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler
        .handle(new DuplicateException("An error occurred"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("An error occurred", actualHandleResult.getBody().getMessage());
    assertEquals(400, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link GlobalExceptionHandler#handle(DuplicateException)}
   */
  @Test
  void testHandle2() {
    // Arrange
    DuplicateException exception = mock(DuplicateException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler.handle(exception);

    // Assert
    verify(exception, atLeast(1)).getMessage();
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Not all who wander are lost", actualHandleResult.getBody().getMessage());
    assertEquals(400, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link GlobalExceptionHandler#handle(NotFoundException)}
   */
  @Test
  void testHandle3() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler
        .handle(new NotFoundException("An error occurred"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("An error occurred", actualHandleResult.getBody().getMessage());
    assertEquals(404, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.NOT_FOUND, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link GlobalExceptionHandler#handle(NotFoundException)}
   */
  @Test
  void testHandle4() {
    // Arrange
    NotFoundException exception = mock(NotFoundException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler.handle(exception);

    // Assert
    verify(exception, atLeast(1)).getMessage();
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Not all who wander are lost", actualHandleResult.getBody().getMessage());
    assertEquals(404, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.NOT_FOUND, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(UnauthorizedException)}
   */
  @Test
  void testHandle5() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler
        .handle(new UnauthorizedException("An error occurred"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("An error occurred", actualHandleResult.getBody().getMessage());
    assertEquals(401, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.UNAUTHORIZED, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(UnauthorizedException)}
   */
  @Test
  void testHandle6() {
    // Arrange
    UnauthorizedException exception = mock(UnauthorizedException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler.handle(exception);

    // Assert
    verify(exception, atLeast(1)).getMessage();
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Not all who wander are lost", actualHandleResult.getBody().getMessage());
    assertEquals(401, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.UNAUTHORIZED, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(InvalidActivityException)}
   */
  @Test
  void testHandle7() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler
        .handle(new InvalidActivityException("42", "An error occurred"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Invalid activity in the workflow 42: An error occurred", actualHandleResult.getBody().getMessage());
    assertEquals(400, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(InvalidActivityException)}
   */
  @Test
  void testHandle8() {
    // Arrange
    InvalidActivityException exception = mock(InvalidActivityException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler.handle(exception);

    // Assert
    verify(exception, atLeast(1)).getMessage();
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Not all who wander are lost", actualHandleResult.getBody().getMessage());
    assertEquals(400, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(OptimisticLockException)}
   */
  @Test
  void testHandle9() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler
        .handle(new OptimisticLockException("An error occurred"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Workflow being updated is outdated, please refresh then update again.",
        actualHandleResult.getBody().getMessage());
    assertEquals(409, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.CONFLICT, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(OptimisticLockException)}
   */
  @Test
  void testHandle10() {
    // Arrange
    OptimisticLockException exception = mock(OptimisticLockException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler.handle(exception);

    // Assert
    verify(exception).getMessage();
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Workflow being updated is outdated, please refresh then update again.",
        actualHandleResult.getBody().getMessage());
    assertEquals(409, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.CONFLICT, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(IllegalArgumentException)}
   */
  @Test
  void testHandle11() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler
        .handle(new IllegalArgumentException("foo"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("foo", actualHandleResult.getBody().getMessage());
    assertEquals(400, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(IllegalArgumentException)}
   */
  @Test
  void testHandle12() {
    // Arrange
    StringContext.InvalidEscapeException exception = mock(StringContext.InvalidEscapeException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler.handle(exception);

    // Assert
    verify(exception, atLeast(1)).getMessage();
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Not all who wander are lost", actualHandleResult.getBody().getMessage());
    assertEquals(400, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test: {@link GlobalExceptionHandler#handle(Throwable)}
   */
  @Test
  void testHandle13() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler.handle(new Throwable());

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Internal server error, something went wrong.", actualHandleResult.getBody().getMessage());
    assertEquals(500, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(UnsupportedOperationException)}
   */
  @Test
  void testHandle14() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler
        .handle(new UnsupportedOperationException("foo"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("foo", actualHandleResult.getBody().getMessage());
    assertEquals(422, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Method under test:
   * {@link GlobalExceptionHandler#handle(UnsupportedOperationException)}
   */
  @Test
  void testHandle15() {
    // Arrange
    NotImplementedException exception = mock(NotImplementedException.class);
    when(exception.getMessage()).thenReturn("Not all who wander are lost");

    // Act
    ResponseEntity<ErrorResponse> actualHandleResult = globalExceptionHandler.handle(exception);

    // Assert
    verify(exception, atLeast(1)).getMessage();
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals("Not all who wander are lost", actualHandleResult.getBody().getMessage());
    assertEquals(422, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }
}
