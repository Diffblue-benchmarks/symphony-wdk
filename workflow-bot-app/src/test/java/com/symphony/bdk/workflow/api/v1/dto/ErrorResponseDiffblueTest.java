package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorResponseDiffblueTest {
  /**
   * Test {@link ErrorResponse#equals(Object)}, and
   * {@link ErrorResponse#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorResponse#equals(Object)}
   *   <li>{@link ErrorResponse#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
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
   * Test {@link ErrorResponse#equals(Object)}, and
   * {@link ErrorResponse#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorResponse#equals(Object)}
   *   <li>{@link ErrorResponse#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
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
   * Test {@link ErrorResponse#equals(Object)}, and
   * {@link ErrorResponse#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorResponse#equals(Object)}
   *   <li>{@link ErrorResponse#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ErrorResponse errorResponse = new ErrorResponse("Not all who wander are lost");

    // Act and Assert
    assertEquals(errorResponse, errorResponse);
    int expectedHashCodeResult = errorResponse.hashCode();
    assertEquals(expectedHashCodeResult, errorResponse.hashCode());
  }

  /**
   * Test {@link ErrorResponse#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorResponse#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ErrorResponse errorResponse = new ErrorResponse("Message");

    // Act and Assert
    assertNotEquals(errorResponse, new ErrorResponse("Not all who wander are lost"));
  }

  /**
   * Test {@link ErrorResponse#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorResponse#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ErrorResponse errorResponse = new ErrorResponse(null);

    // Act and Assert
    assertNotEquals(errorResponse, new ErrorResponse("Not all who wander are lost"));
  }

  /**
   * Test {@link ErrorResponse#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorResponse#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ErrorResponse("Not all who wander are lost"), null);
  }

  /**
   * Test {@link ErrorResponse#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorResponse#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ErrorResponse("Not all who wander are lost"), "Different type to ErrorResponse");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ErrorResponse#ErrorResponse(String)}
   *   <li>{@link ErrorResponse#toString()}
   *   <li>{@link ErrorResponse#getMessage()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ErrorResponse actualErrorResponse = new ErrorResponse("Not all who wander are lost");
    String actualToStringResult = actualErrorResponse.toString();

    // Assert
    assertEquals("ErrorResponse(message=Not all who wander are lost)", actualToStringResult);
    assertEquals("Not all who wander are lost", actualErrorResponse.getMessage());
  }
}
