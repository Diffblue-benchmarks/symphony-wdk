package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UnpinMessage;
import java.io.IOException;
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
   * Method under test:
   * {@link UnpinMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
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
   * Method under test:
   * {@link UnpinMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  void testDoOboWithCache() {
    // Arrange
    ActivityExecutorContext<UnpinMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> unpinMessageExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
