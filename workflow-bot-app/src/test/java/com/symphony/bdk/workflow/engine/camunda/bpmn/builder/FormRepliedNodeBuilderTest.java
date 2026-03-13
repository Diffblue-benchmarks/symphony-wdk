package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;

class FormRepliedNodeBuilderTest {

  @Test
  void shouldThrowBpmnErrorWhenNotifyIsCalled() throws Exception {
    // Arrange
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate = new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    DelegateExecution execution = mock(DelegateExecution.class);

    // Act & Assert
    BpmnError error = assertThrows(BpmnError.class, () -> delegate.notify(execution));

    assertThat(error.getErrorCode()).isEqualTo("408");
    assertThat(error.getMessage()).isEqualTo("Form reply event is timeout.");
  }
}
