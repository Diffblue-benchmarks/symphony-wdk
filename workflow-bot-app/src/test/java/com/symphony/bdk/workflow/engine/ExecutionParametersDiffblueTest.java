package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class ExecutionParametersDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionParameters#equals(Object)}
   *   <li>{@link ExecutionParameters#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ExecutionParameters executionParameters = new ExecutionParameters(new HashMap<>(), "ABC123");
    ExecutionParameters executionParameters2 = new ExecutionParameters(new HashMap<>(), "ABC123");

    // Act and Assert
    assertEquals(executionParameters, executionParameters2);
    int expectedHashCodeResult = executionParameters.hashCode();
    assertEquals(expectedHashCodeResult, executionParameters2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionParameters#equals(Object)}
   *   <li>{@link ExecutionParameters#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ExecutionParameters executionParameters = new ExecutionParameters(new HashMap<>(), null);
    ExecutionParameters executionParameters2 = new ExecutionParameters(new HashMap<>(), null);

    // Act and Assert
    assertEquals(executionParameters, executionParameters2);
    int expectedHashCodeResult = executionParameters.hashCode();
    assertEquals(expectedHashCodeResult, executionParameters2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionParameters#equals(Object)}
   *   <li>{@link ExecutionParameters#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ExecutionParameters executionParameters = new ExecutionParameters(new HashMap<>(), "ABC123");

    // Act and Assert
    assertEquals(executionParameters, executionParameters);
    int expectedHashCodeResult = executionParameters.hashCode();
    assertEquals(expectedHashCodeResult, executionParameters.hashCode());
  }

  /**
   * Method under test: {@link ExecutionParameters#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashMap<String, Object> arguments = new HashMap<>();
    arguments.put("ABC123", "42");
    ExecutionParameters executionParameters = new ExecutionParameters(arguments, "ABC123");

    // Act and Assert
    assertNotEquals(executionParameters, new ExecutionParameters(new HashMap<>(), "ABC123"));
  }

  /**
   * Method under test: {@link ExecutionParameters#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> arguments = new HashMap<>();
    arguments.computeIfPresent("foo", mock(BiFunction.class));
    arguments.put("ABC123", "42");
    ExecutionParameters executionParameters = new ExecutionParameters(arguments, "ABC123");

    // Act and Assert
    assertNotEquals(executionParameters, new ExecutionParameters(new HashMap<>(), "ABC123"));
  }

  /**
   * Method under test: {@link ExecutionParameters#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ExecutionParameters executionParameters = new ExecutionParameters(new HashMap<>(), "Token");

    // Act and Assert
    assertNotEquals(executionParameters, new ExecutionParameters(new HashMap<>(), "ABC123"));
  }

  /**
   * Method under test: {@link ExecutionParameters#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ExecutionParameters executionParameters = new ExecutionParameters(new HashMap<>(), null);

    // Act and Assert
    assertNotEquals(executionParameters, new ExecutionParameters(new HashMap<>(), "ABC123"));
  }

  /**
   * Method under test: {@link ExecutionParameters#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecutionParameters(new HashMap<>(), "ABC123"), null);
  }

  /**
   * Method under test: {@link ExecutionParameters#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecutionParameters(new HashMap<>(), "ABC123"), "Different type to ExecutionParameters");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutionParameters#ExecutionParameters(Map, String)}
   *   <li>{@link ExecutionParameters#toString()}
   *   <li>{@link ExecutionParameters#getArguments()}
   *   <li>{@link ExecutionParameters#getToken()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    HashMap<String, Object> arguments = new HashMap<>();

    // Act
    ExecutionParameters actualExecutionParameters = new ExecutionParameters(arguments, "ABC123");
    String actualToStringResult = actualExecutionParameters.toString();
    Map<String, Object> actualArguments = actualExecutionParameters.getArguments();

    // Assert
    assertEquals("ABC123", actualExecutionParameters.getToken());
    assertEquals("ExecutionParameters(arguments={}, token=ABC123)", actualToStringResult);
    assertTrue(actualArguments.isEmpty());
    assertSame(arguments, actualArguments);
  }
}
