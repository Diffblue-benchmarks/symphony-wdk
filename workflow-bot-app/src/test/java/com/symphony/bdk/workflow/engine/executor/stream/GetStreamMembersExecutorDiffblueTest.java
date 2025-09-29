package com.symphony.bdk.workflow.engine.executor.stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreamMembers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetStreamMembersExecutor.class})
@ExtendWith(SpringExtension.class)
class GetStreamMembersExecutorDiffblueTest {
  @Autowired private GetStreamMembersExecutor getStreamMembersExecutor;

  /**
   * Test {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreamMembers} {@link GetStreamMembers#getLimit()} return one.
   *   <li>Then calls {@link GetStreamMembers#getLimit()}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreamMembers getLimit() return one; then calls getLimit()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamMembersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamMembersGetLimitReturnOne_thenCallsGetLimit() {
    // Arrange
    GetStreamMembers getStreamMembers = mock(GetStreamMembers.class);
    when(getStreamMembers.getLimit()).thenReturn(1);
    when(getStreamMembers.getSkip()).thenReturn(1);
    when(getStreamMembers.getStreamId()).thenReturn("42");

    ActivityExecutorContext<GetStreamMembers> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(getStreamMembers);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamMembersExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(getStreamMembers).getLimit();
    verify(getStreamMembers).getSkip();
    verify(getStreamMembers).getStreamId();
  }

  /**
   * Test {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreamMembers} (default constructor) Limit is one.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreamMembers (default constructor) Limit is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamMembersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamMembersLimitIsOne() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setLimit(1);

    ActivityExecutorContext<GetStreamMembers> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getStreamMembers);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamMembersExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreamMembers} (default constructor) Skip is one.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreamMembers (default constructor) Skip is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamMembersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamMembersSkipIsOne() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setSkip(1);

    ActivityExecutorContext<GetStreamMembers> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getStreamMembers);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamMembersExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreamMembers} (default constructor).
   *   <li>Then calls {@link ActivityExecutorContext#bdk()}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreamMembers (default constructor); then calls bdk()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamMembersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamMembers_thenCallsBdk() {
    // Arrange
    ActivityExecutorContext<GetStreamMembers> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException());
    when(execution.getActivity()).thenReturn(new GetStreamMembers());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamMembersExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link ActivityExecutorContext} {@link ActivityExecutorContext#getActivity()} throw
   *       {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamMembersExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when ActivityExecutorContext getActivity() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamMembersExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenActivityExecutorContextGetActivityThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<GetStreamMembers> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamMembersExecutor.execute(execution));
    verify(execution).getActivity();
  }
}
