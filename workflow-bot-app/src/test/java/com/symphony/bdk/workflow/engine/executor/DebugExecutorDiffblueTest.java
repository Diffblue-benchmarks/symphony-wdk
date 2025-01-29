package com.symphony.bdk.workflow.engine.executor;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
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
   * Test {@link DebugExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link Debug} (default constructor) Object is empty string.</li>
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given Debug (default constructor) Object is empty string; then calls getActivity()")
  void testExecute_givenDebugObjectIsEmptyString_thenCallsGetActivity() throws IOException {
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

  /**
   * Test {@link DebugExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link Debug} (default constructor) Object is {@code Object}.</li>
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given Debug (default constructor) Object is 'Object'; then calls getActivity()")
  void testExecute_givenDebugObjectIsObject_thenCallsGetActivity() throws IOException {
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
   * Test {@link DebugExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link Debug} (default constructor) Object is one.</li>
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given Debug (default constructor) Object is one; then calls getActivity()")
  void testExecute_givenDebugObjectIsOne_thenCallsGetActivity() throws IOException {
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
   * Test {@link DebugExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link Debug} (default constructor) Object is zero.</li>
   *   <li>Then calls {@link ActivityExecutorContext#getActivity()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DebugExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given Debug (default constructor) Object is zero; then calls getActivity()")
  void testExecute_givenDebugObjectIsZero_thenCallsGetActivity() throws IOException {
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
}
