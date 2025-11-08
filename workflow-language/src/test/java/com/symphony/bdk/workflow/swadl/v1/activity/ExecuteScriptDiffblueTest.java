package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExecuteScriptDiffblueTest {
  /**
   * Test {@link ExecuteScript#getScript()}.
   * <ul>
   *   <li>Given {@link ExecuteScript} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteScript#getScript()}
   */
  @Test
  @DisplayName("Test getScript(); given ExecuteScript (default constructor); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ExecuteScript.getScript()"})
  void testGetScript_givenExecuteScript_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new ExecuteScript()).getScript());
  }

  /**
   * Test {@link ExecuteScript#getScript()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code script} is {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteScript#getScript()}
   */
  @Test
  @DisplayName("Test getScript(); given HashMap() 'script' is 'foo'; then return 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ExecuteScript.getScript()"})
  void testGetScript_givenHashMapScriptIsFoo_thenReturnFoo() {
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
   * Test {@link ExecuteScript#equals(Object)}, and {@link ExecuteScript#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteScript#equals(Object)}
   *   <li>{@link ExecuteScript#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteScript.equals(Object)", "int ExecuteScript.hashCode()"})
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
   * Test {@link ExecuteScript#equals(Object)}, and {@link ExecuteScript#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteScript#equals(Object)}
   *   <li>{@link ExecuteScript#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteScript.equals(Object)", "int ExecuteScript.hashCode()"})
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
   * Test {@link ExecuteScript#equals(Object)}, and {@link ExecuteScript#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecuteScript#equals(Object)}
   *   <li>{@link ExecuteScript#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteScript.equals(Object)", "int ExecuteScript.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();

    // Act and Assert
    assertEquals(executeScript, executeScript);
    int expectedHashCodeResult = executeScript.hashCode();
    assertEquals(expectedHashCodeResult, executeScript.hashCode());
  }

  /**
   * Test {@link ExecuteScript#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteScript.equals(Object)", "int ExecuteScript.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.add("script", "Value");

    // Act and Assert
    assertNotEquals(executeScript, new ExecuteScript());
  }

  /**
   * Test {@link ExecuteScript#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteScript.equals(Object)", "int ExecuteScript.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.setScript("script");

    // Act and Assert
    assertNotEquals(executeScript, new ExecuteScript());
  }

  /**
   * Test {@link ExecuteScript#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteScript.equals(Object)", "int ExecuteScript.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ExecuteScript executeScript = new ExecuteScript();

    ExecuteScript executeScript2 = new ExecuteScript();
    executeScript2.setScript("script");

    // Act and Assert
    assertNotEquals(executeScript, executeScript2);
  }

  /**
   * Test {@link ExecuteScript#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteScript.equals(Object)", "int ExecuteScript.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecuteScript(), null);
  }

  /**
   * Test {@link ExecuteScript#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteScript#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ExecuteScript.equals(Object)", "int ExecuteScript.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ExecuteScript(), "Different type to ExecuteScript");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ExecuteScript}
   *   <li>{@link ExecuteScript#setScript(String)}
   *   <li>{@link ExecuteScript#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExecuteScript.<init>()", "void ExecuteScript.setScript(String)",
      "String ExecuteScript.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    ExecuteScript actualExecuteScript = new ExecuteScript();
    actualExecuteScript.setScript("Script");

    // Assert
    assertEquals("ExecuteScript(script=Script)", actualExecuteScript.toString());
    assertNull(actualExecuteScript.getOn());
    assertNull(actualExecuteScript.getElseCondition());
    assertNull(actualExecuteScript.getId());
    assertNull(actualExecuteScript.getIfCondition());
    assertTrue(actualExecuteScript.getVariableProperties().isEmpty());
  }
}
