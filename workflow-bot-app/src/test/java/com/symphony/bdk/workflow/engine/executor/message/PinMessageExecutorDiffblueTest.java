package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.PinMessage;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PinMessageExecutor.class})
@ExtendWith(SpringExtension.class)
class PinMessageExecutorDiffblueTest {
  @Autowired private PinMessageExecutor pinMessageExecutor;

  /**
   * Test {@link PinMessageExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link PinMessage} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#bdk()}.
   * </ul>
   *
   * <p>Method under test: {@link PinMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given PinMessage (default constructor); then calls bdk()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void PinMessageExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenPinMessage_thenCallsBdk() throws IOException {
    // Arrange
    ActivityExecutorContext<PinMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(new PinMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> pinMessageExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
  }

  /**
   * Test {@link PinMessageExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link ActivityExecutorContext} {@link ActivityExecutorContext#getActivity()} throw
   *       {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link PinMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when ActivityExecutorContext getActivity() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void PinMessageExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenActivityExecutorContextGetActivityThrowIllegalArgumentException()
      throws IOException {
    // Arrange
    ActivityExecutorContext<PinMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> pinMessageExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link PinMessageExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link PinMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Void PinMessageExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache() {
    // Arrange
    ActivityExecutorContext<PinMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> pinMessageExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
