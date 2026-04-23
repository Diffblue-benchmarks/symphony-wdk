package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class FormRepliedNodeBuilderTest {

  @Test
  void shouldThrowBpmnErrorWhenFormReplyTimeout() {
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate = new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    DelegateExecution execution = mock(DelegateExecution.class);

    assertThatThrownBy(() -> delegate.notify(execution))
        .isInstanceOf(BpmnError.class)
        .hasMessageContaining("Form reply event is timeout.");
  }
}
