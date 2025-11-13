package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SendMessageExecutor.class})
@ExtendWith(SpringExtension.class)
class SendMessageExecutorDiffblueTest {
  @Autowired private SendMessageExecutor sendMessageExecutor;

  /**
   * Test {@link SendMessageExecutor#execute(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link SendMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendMessageExecutor.execute(ActivityExecutorContext)"})
  void testExecute() throws IOException {
    // Arrange
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.gen.api.model.V4Message SendMessageExecutor.doOboWithCache(ActivityExecutorContext)"
  })
  void testDoOboWithCache() throws IOException {
    // Arrange
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> sendMessageExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
