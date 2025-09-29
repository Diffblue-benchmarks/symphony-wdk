package com.symphony.bdk.workflow.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.ErrorResponse;
import com.symphony.bdk.workflow.swadl.exception.InvalidActivityException;
import jakarta.persistence.OptimisticLockException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerDiffblueTest {
  @Autowired private GlobalExceptionHandler globalExceptionHandler;

  /**
   * Test {@link GlobalExceptionHandler#handle(DuplicateException)} with {@code DuplicateException}.
   *
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link GlobalExceptionHandler#handle(DuplicateException)}
   */
  @Test
  @DisplayName(
      "Test handle(DuplicateException) with 'DuplicateException'; then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity GlobalExceptionHandler.handle(DuplicateException)"})
  void testHandleWithDuplicateException_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult =
        globalExceptionHandler.handle(new DuplicateException("An error occurred"));

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
   * Test {@link GlobalExceptionHandler#handle(IllegalArgumentException)} with {@code
   * IllegalArgumentException}.
   *
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link GlobalExceptionHandler#handle(IllegalArgumentException)}
   */
  @Test
  @DisplayName(
      "Test handle(IllegalArgumentException) with 'IllegalArgumentException'; then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity GlobalExceptionHandler.handle(IllegalArgumentException)"})
  void testHandleWithIllegalArgumentException_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult =
        globalExceptionHandler.handle(new IllegalArgumentException());

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualHandleResult.getBody().getMessage());
    assertEquals(400, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link GlobalExceptionHandler#handle(InvalidActivityException)} with {@code
   * InvalidActivityException}.
   *
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link GlobalExceptionHandler#handle(InvalidActivityException)}
   */
  @Test
  @DisplayName(
      "Test handle(InvalidActivityException) with 'InvalidActivityException'; then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity GlobalExceptionHandler.handle(InvalidActivityException)"})
  void testHandleWithInvalidActivityException_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult =
        globalExceptionHandler.handle(new InvalidActivityException("42", "An error occurred"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(
        "Invalid activity in the workflow 42: An error occurred",
        actualHandleResult.getBody().getMessage());
    assertEquals(400, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.BAD_REQUEST, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link GlobalExceptionHandler#handle(NotFoundException)} with {@code NotFoundException}.
   *
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link GlobalExceptionHandler#handle(NotFoundException)}
   */
  @Test
  @DisplayName(
      "Test handle(NotFoundException) with 'NotFoundException'; then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity GlobalExceptionHandler.handle(NotFoundException)"})
  void testHandleWithNotFoundException_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult =
        globalExceptionHandler.handle(new NotFoundException("An error occurred"));

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
   * Test {@link GlobalExceptionHandler#handle(OptimisticLockException)} with {@code
   * OptimisticLockException}.
   *
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link GlobalExceptionHandler#handle(OptimisticLockException)}
   */
  @Test
  @DisplayName(
      "Test handle(OptimisticLockException) with 'OptimisticLockException'; then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity GlobalExceptionHandler.handle(OptimisticLockException)"})
  void testHandleWithOptimisticLockException_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult =
        globalExceptionHandler.handle(new OptimisticLockException("An error occurred"));

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(
        "Workflow being updated is outdated, please refresh then update again.",
        actualHandleResult.getBody().getMessage());
    assertEquals(409, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.CONFLICT, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link GlobalExceptionHandler#handle(Throwable)} with {@code Throwable}.
   *
   * <ul>
   *   <li>When {@link Throwable#Throwable()}.
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link GlobalExceptionHandler#handle(Throwable)}
   */
  @Test
  @DisplayName(
      "Test handle(Throwable) with 'Throwable'; when Throwable(); then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity GlobalExceptionHandler.handle(Throwable)"})
  void testHandleWithThrowable_whenThrowable_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult =
        globalExceptionHandler.handle(new Throwable());

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(
        "Internal server error, something went wrong.", actualHandleResult.getBody().getMessage());
    assertEquals(500, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link GlobalExceptionHandler#handle(UnauthorizedException)} with {@code
   * UnauthorizedException}.
   *
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link GlobalExceptionHandler#handle(UnauthorizedException)}
   */
  @Test
  @DisplayName(
      "Test handle(UnauthorizedException) with 'UnauthorizedException'; then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity GlobalExceptionHandler.handle(UnauthorizedException)"})
  void testHandleWithUnauthorizedException_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult =
        globalExceptionHandler.handle(new UnauthorizedException("An error occurred"));

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
   * Test {@link GlobalExceptionHandler#handle(UnsupportedOperationException)} with {@code
   * UnsupportedOperationException}.
   *
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.
   * </ul>
   *
   * <p>Method under test: {@link GlobalExceptionHandler#handle(UnsupportedOperationException)}
   */
  @Test
  @DisplayName(
      "Test handle(UnsupportedOperationException) with 'UnsupportedOperationException'; then StatusCode return HttpStatus")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity GlobalExceptionHandler.handle(UnsupportedOperationException)"})
  void testHandleWithUnsupportedOperationException_thenStatusCodeReturnHttpStatus() {
    // Arrange and Act
    ResponseEntity<ErrorResponse> actualHandleResult =
        globalExceptionHandler.handle(new UnsupportedOperationException());

    // Assert
    HttpStatusCode statusCode = actualHandleResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualHandleResult.getBody().getMessage());
    assertEquals(422, actualHandleResult.getStatusCodeValue());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
    assertTrue(actualHandleResult.hasBody());
    assertTrue(actualHandleResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link
   * GlobalExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException,
   * HttpHeaders, HttpStatus, WebRequest)} with {@code MethodArgumentNotValidException}, {@code
   * HttpHeaders}, {@code HttpStatus}, {@code WebRequest}.
   *
   * <p>Method under test: {@link
   * GlobalExceptionHandler#handleMethodArgumentNotValid(MethodArgumentNotValidException,
   * HttpHeaders, HttpStatus, WebRequest)}
   */
  @Test
  @DisplayName(
      "Test handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatus, WebRequest) with 'MethodArgumentNotValidException', 'HttpHeaders', 'HttpStatus', 'WebRequest'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity GlobalExceptionHandler.handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatus, WebRequest)"
  })
  void
      testHandleMethodArgumentNotValidWithMethodArgumentNotValidExceptionHttpHeadersHttpStatusWebRequest() {
    // Arrange
    MethodArgumentNotValidException ex =
        new MethodArgumentNotValidException(null, new BindException("Target", "Object Name"));
    HttpHeaders headers = new HttpHeaders();

    // Act
    ResponseEntity<Object> actualHandleMethodArgumentNotValidResult =
        globalExceptionHandler.handleMethodArgumentNotValid(
            ex, headers, HttpStatus.OK, new ServletWebRequest(new MockHttpServletRequest()));

    // Assert
    Object body = actualHandleMethodArgumentNotValidResult.getBody();
    assertTrue(body instanceof Map);
    HttpStatusCode statusCode = actualHandleMethodArgumentNotValidResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualHandleMethodArgumentNotValidResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(((Map<Object, Object>) body).isEmpty());
    assertTrue(actualHandleMethodArgumentNotValidResult.hasBody());
    assertEquals(headers, actualHandleMethodArgumentNotValidResult.getHeaders());
  }
}
