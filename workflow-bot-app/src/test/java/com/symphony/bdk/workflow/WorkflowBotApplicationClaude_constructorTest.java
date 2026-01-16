package com.symphony.bdk.workflow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowBotApplicationClaude_constructorTest {

  @Test
  void testConstructor() {
    // Test that the default constructor can be invoked successfully
    WorkflowBotApplication application = new WorkflowBotApplication();

    // Verify the instance is created
    assertThat(application).isNotNull();
  }
}
