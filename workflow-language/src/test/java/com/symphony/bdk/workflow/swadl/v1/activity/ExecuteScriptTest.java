package com.symphony.bdk.workflow.swadl.v1.activity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExecuteScriptTest {

  @Test
  void shouldReturnScriptFromVariablePropertiesWhenScriptIsNull() {
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.add("script", "myGroovyScript");

    String result = executeScript.getScript();

    assertEquals("myGroovyScript", result);
  }

  @Test
  void shouldReturnNullFromVariablePropertiesWhenScriptIsNullAndPropertyNotSet() {
    ExecuteScript executeScript = new ExecuteScript();

    String result = executeScript.getScript();

    assertNull(result);
  }

  @Test
  void shouldReturnScriptWhenScriptIsSet() {
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.setScript("directScript");

    String result = executeScript.getScript();

    assertEquals("directScript", result);
  }
}
