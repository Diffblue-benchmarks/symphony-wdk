package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExecuteScriptDiffblueTest {
  /**
   * Test {@link ExecuteScript#getScript()}.
   *
   * <ul>
   *   <li>Given {@link ExecuteScript} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteScript#getScript()}
   */
  @Test
  @DisplayName("Test getScript(); given ExecuteScript (default constructor); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecuteScript.getScript()"})
  void testGetScript_givenExecuteScript_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new ExecuteScript().getScript());
  }

  /**
   * Test {@link ExecuteScript#getScript()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code script} is {@code foo}.
   *   <li>Then return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteScript#getScript()}
   */
  @Test
  @DisplayName("Test getScript(); given HashMap() 'script' is 'foo'; then return 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
}
