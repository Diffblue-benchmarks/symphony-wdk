package com.symphony.bdk.workflow.swadl.v1.activity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExecuteScriptTest {

  @Test
  void getScript_returnsScriptWhenSet() {
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.setScript("print 'hello world'");

    String result = executeScript.getScript();

    assertEquals("print 'hello world'", result);
  }

  @Test
  void getScript_returnsScriptFromVariablePropertiesWhenScriptIsNull() {
    ExecuteScript executeScript = new ExecuteScript();
    executeScript.add("script", "print 'from properties'");

    String result = executeScript.getScript();

    assertEquals("print 'from properties'", result);
  }
}
