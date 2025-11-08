package com.symphony.bdk.workflow.engine.executor;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DebugExecutor.class})
@ExtendWith(SpringExtension.class)
class DebugExecutorDiffblueTest {
  @Autowired
  private DebugExecutor debugExecutor;

  /**
   * Method under test: {@link DebugExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() throws IOException {
    // Arrange
    Debug debug = new Debug();
    debug.setObject("Object");
    ActivityExecutorContext<Debug> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(debug);

    // Act
    debugExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
  }

  /**
   * Method under test: {@link DebugExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute2() throws IOException {
    // Arrange
    Debug debug = new Debug();
    debug.setObject(1);
    ActivityExecutorContext<Debug> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(debug);

    // Act
    debugExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
  }

  /**
   * Method under test: {@link DebugExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute3() throws IOException {
    // Arrange
    Debug debug = new Debug();
    debug.setObject(0);
    ActivityExecutorContext<Debug> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(debug);

    // Act
    debugExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
  }

  /**
   * Method under test: {@link DebugExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute4() throws IOException {
    // Arrange
    Debug debug = new Debug();
    debug.setObject("");
    ActivityExecutorContext<Debug> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(debug);

    // Act
    debugExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
  }
}
