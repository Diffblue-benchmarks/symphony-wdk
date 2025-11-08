package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class ErrorResponseDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorResponse#equals(Object)}
   *   <li>{@link ErrorResponse#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ErrorResponse errorResponse = new ErrorResponse("Not all who wander are lost");
    ErrorResponse errorResponse2 = new ErrorResponse("Not all who wander are lost");

    // Act and Assert
    assertEquals(errorResponse, errorResponse2);
    int expectedHashCodeResult = errorResponse.hashCode();
    assertEquals(expectedHashCodeResult, errorResponse2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorResponse#equals(Object)}
   *   <li>{@link ErrorResponse#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ErrorResponse errorResponse = new ErrorResponse(null);
    ErrorResponse errorResponse2 = new ErrorResponse(null);

    // Act and Assert
    assertEquals(errorResponse, errorResponse2);
    int expectedHashCodeResult = errorResponse.hashCode();
    assertEquals(expectedHashCodeResult, errorResponse2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorResponse#equals(Object)}
   *   <li>{@link ErrorResponse#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ErrorResponse errorResponse = new ErrorResponse("Not all who wander are lost");

    // Act and Assert
    assertEquals(errorResponse, errorResponse);
    int expectedHashCodeResult = errorResponse.hashCode();
    assertEquals(expectedHashCodeResult, errorResponse.hashCode());
  }

  /**
   * Method under test: {@link ErrorResponse#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ErrorResponse errorResponse = new ErrorResponse("Message");

    // Act and Assert
    assertNotEquals(errorResponse, new ErrorResponse("Not all who wander are lost"));
  }

  /**
   * Method under test: {@link ErrorResponse#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ErrorResponse errorResponse = new ErrorResponse(null);

    // Act and Assert
    assertNotEquals(errorResponse, new ErrorResponse("Not all who wander are lost"));
  }

  /**
   * Method under test: {@link ErrorResponse#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ErrorResponse("Not all who wander are lost"), null);
  }

  /**
   * Method under test: {@link ErrorResponse#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ErrorResponse("Not all who wander are lost"), "Different type to ErrorResponse");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorResponse#ErrorResponse(String)}
   *   <li>{@link ErrorResponse#toString()}
   *   <li>{@link ErrorResponse#getMessage()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ErrorResponse actualErrorResponse = new ErrorResponse("Not all who wander are lost");
    String actualToStringResult = actualErrorResponse.toString();

    // Assert
    assertEquals("ErrorResponse(message=Not all who wander are lost)", actualToStringResult);
    assertEquals("Not all who wander are lost", actualErrorResponse.getMessage());
  }
}
