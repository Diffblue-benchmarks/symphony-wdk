package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class ExecuteScriptDiffblueTest {
  /**
   * Method under test: {@link ExecuteScript#getScript()}
   */
  @Test
  void testGetScript() {
    // Arrange, Act and Assert
    assertNull((new ExecuteScript()).getScript());
  }

  /**
   * Method under test: {@link ExecuteScript#getScript()}
   */
  @Test
  void testGetScript2() {
    // Arrange
    HashMap<String, Object> variableProperties = new HashMap<>();
    variableProperties.put("script", "foo");

    ExecuteScript executeScript = new ExecuteScript();
    executeScript.setScript("foo");
    executeScript.setVariableProperties(variableProperties);

    // Act and Assert
    assertEquals("foo", executeScript.getScript());
  }

  /**
   * Method under test: {@link ExecuteScript#getScript()}
   */
  @Test
  void testGetScript3() {
    // Arrange
    HashMap<String, Object> variableProperties = new HashMap<>();
    variableProperties.computeIfPresent("foo", mock(BiFunction.class));
    variableProperties.put("script", "foo");

    ExecuteScript executeScript = new ExecuteScript();
    executeScript.setScript("foo");
    executeScript.setVariableProperties(variableProperties);

    // Act and Assert
    assertEquals("foo", executeScript.getScript());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteScript#equals(Object)}
   *   <li>{@link ExecuteScript#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();
    ExecuteScript executeScript2 = new ExecuteScript();

    // Act and Assert
    assertEquals(executeScript, executeScript2);
    int expectedHashCodeResult = executeScript.hashCode();
    assertEquals(expectedHashCodeResult, executeScript2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteScript#equals(Object)}
   *   <li>{@link ExecuteScript#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.setScript("script");

    ExecuteScript executeScript2 = new ExecuteScript();
    executeScript2.setScript("script");

    // Act and Assert
    assertEquals(executeScript, executeScript2);
    int expectedHashCodeResult = executeScript.hashCode();
    assertEquals(expectedHashCodeResult, executeScript2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteScript#equals(Object)}
   *   <li>{@link ExecuteScript#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();

    // Act and Assert
    assertEquals(executeScript, executeScript);
    int expectedHashCodeResult = executeScript.hashCode();
    assertEquals(expectedHashCodeResult, executeScript.hashCode());
  }

  /**
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.add("script", "Value");

    // Act and Assert
    assertNotEquals(executeScript, new ExecuteScript());
  }

  /**
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.add("script", mock(Debug.class));

    // Act and Assert
    assertNotEquals(executeScript, new ExecuteScript());
  }

  /**
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.setScript("script");

    // Act and Assert
    assertNotEquals(executeScript, new ExecuteScript());
  }

  /**
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();

    ExecuteScript executeScript2 = new ExecuteScript();
    executeScript2.setScript("script");

    // Act and Assert
    assertNotEquals(executeScript, executeScript2);
  }

  /**
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecuteScript(), null);
  }

  /**
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecuteScript(), "Different type to ExecuteScript");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ExecuteScript}
   *   <li>{@link ExecuteScript#setScript(String)}
   *   <li>{@link ExecuteScript#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ExecuteScript actualExecuteScript = new ExecuteScript();
    actualExecuteScript.setScript("Script");

    // Assert that nothing has changed
    assertEquals("ExecuteScript(script=Script)", actualExecuteScript.toString());
    assertTrue(actualExecuteScript.getVariableProperties().isEmpty());
  }
}
