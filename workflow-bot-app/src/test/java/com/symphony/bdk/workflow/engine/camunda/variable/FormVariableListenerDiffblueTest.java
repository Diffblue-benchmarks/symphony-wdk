package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.pvm.runtime.ExecutionImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FormVariableListener.class})
@ExtendWith(SpringExtension.class)
class FormVariableListenerDiffblueTest {
  @Autowired
  private FormVariableListener formVariableListener;

  /**
   * Method under test: {@link FormVariableListener#notify(DelegateExecution)}
   */
  @Test
  void testNotify() {
    // Arrange
    ExecutionImpl execution = mock(ExecutionImpl.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn("Variable");

    // Act
    formVariableListener.notify(execution);

    // Assert that nothing has changed
    verify(execution).getVariable(eq("form"));
  }

  /**
   * Method under test: {@link FormVariableListener#notify(DelegateExecution)}
   */
  @Test
  void testNotify2() {
    // Arrange
    ExecutionImpl execution = mock(ExecutionImpl.class);
    doNothing().when(execution).removeVariable(Mockito.<String>any());
    when(execution.getVariable(Mockito.<String>any())).thenReturn(new HashMap<>());

    // Act
    formVariableListener.notify(execution);

    // Assert that nothing has changed
    verify(execution).getVariable(eq("form"));
    verify(execution).removeVariable(eq("form"));
  }
}
