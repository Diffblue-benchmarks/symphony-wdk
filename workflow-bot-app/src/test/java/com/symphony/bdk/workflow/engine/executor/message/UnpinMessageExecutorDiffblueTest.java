package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UnpinMessage;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UnpinMessageExecutor.class})
@ExtendWith(SpringExtension.class)
class UnpinMessageExecutorDiffblueTest {
  @Autowired
  private UnpinMessageExecutor unpinMessageExecutor;

  /**
   * Test {@link UnpinMessageExecutor#execute(ActivityExecutorContext)}.
   * <p>
   * Method under test:
   * {@link UnpinMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  void testExecute() throws IOException {
    // Arrange
    ActivityExecutorContext<UnpinMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException("foo"));
    when(execution.getActivity()).thenReturn(new UnpinMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> unpinMessageExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
  }

  /**
   * Test {@link UnpinMessageExecutor#doOboWithCache(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UnpinMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then throw IllegalArgumentException")
  void testDoOboWithCache_thenThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<UnpinMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> unpinMessageExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
