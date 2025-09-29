package com.symphony.bdk.workflow.engine.executor.stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetStreamsExecutor.class})
@ExtendWith(SpringExtension.class)
class GetStreamsExecutorDiffblueTest {
  @Autowired private GetStreamsExecutor getStreamsExecutor;

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreams} {@link GetStreams#getLimit()} return one.
   *   <li>Then calls {@link GetStreams#getLimit()}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams getLimit() return one; then calls getLimit()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamsGetLimitReturnOne_thenCallsGetLimit() {
    // Arrange
    GetStreams getStreams = mock(GetStreams.class);
    when(getStreams.getLimit()).thenReturn(1);
    when(getStreams.getSkip()).thenReturn(1);

    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(getStreams);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamsExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(getStreams).getLimit();
    verify(getStreams).getSkip();
  }

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreams} (default constructor) Limit is one.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams (default constructor) Limit is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamsLimitIsOne() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setLimit(1);

    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getStreams);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamsExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreams} (default constructor) Skip is one.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams (default constructor) Skip is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamsSkipIsOne() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setSkip(1);

    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getStreams);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamsExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreams} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#bdk()}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams (default constructor); then calls bdk()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreams_thenCallsBdk() {
    // Arrange
    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(new GetStreams());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamsExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link ActivityExecutorContext} {@link ActivityExecutorContext#getActivity()} throw
   *       {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when ActivityExecutorContext getActivity() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenActivityExecutorContextGetActivityThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamsExecutor.execute(execution));
    verify(execution).getActivity();
  }
}
