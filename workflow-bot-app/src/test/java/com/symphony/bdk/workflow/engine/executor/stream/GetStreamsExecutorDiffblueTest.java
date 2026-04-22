package com.symphony.bdk.workflow.engine.executor.stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2AdminStreamList;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreams;
import java.util.Arrays;
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
   *   <li>Given {@link GetStreams} {@link GetStreams#getId()} throw {@link
   *       IllegalArgumentException#IllegalArgumentException()}.
   *   <li>Then calls {@link GetStreams#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams getId() throw IllegalArgumentException(); then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamsGetIdThrowIllegalArgumentException_thenCallsGetId() {
    // Arrange
    GetStreams getStreams = mock(GetStreams.class);
    when(getStreams.getId()).thenThrow(new IllegalArgumentException());
    when(getStreams.getLimit()).thenReturn(null);
    when(getStreams.getSkip()).thenReturn(1);

    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getStreams);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getStreamsExecutor.execute(execution));
    verify(execution).getActivity();
    verify(getStreams).getId();
    verify(getStreams, atLeast(1)).getLimit();
    verify(getStreams).getSkip();
  }

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreams} {@link GetStreams#getLimit()} return one.
   *   <li>Then calls {@link ActivityExecutorContext#bdk()}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams getLimit() return one; then calls bdk()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamsGetLimitReturnOne_thenCallsBdk() {
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

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreams} (default constructor) with both limit and skip null; then calls
   *       listStreamsAdmin with filter and sets output variable.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams with both limit and skip null; then sets output variable")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamsWithBothLimitAndSkipNull_thenSetsOutputVariable() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    StreamService streamService = mock(StreamService.class);
    when(streamService.listStreamsAdmin(any())).thenReturn(new V2AdminStreamList());

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getStreams);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    getStreamsExecutor.execute(execution);

    // Assert
    verify(execution).bdk();
    verify(streamService).listStreamsAdmin(any());
    verify(execution).setOutputVariable("streams", new V2AdminStreamList());
  }

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreams} with both limit and skip set; then calls listStreamsAdmin with
   *       pagination and sets output variable.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams with limit and skip set; then calls listStreamsAdmin with pagination")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamsWithLimitAndSkipSet_thenCallsListStreamsAdminWithPagination() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setLimit(10);
    getStreams.setSkip(5);

    StreamService streamService = mock(StreamService.class);
    when(streamService.listStreamsAdmin(any(), any())).thenReturn(new V2AdminStreamList());

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getStreams);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    getStreamsExecutor.execute(execution);

    // Assert
    verify(execution).bdk();
    verify(streamService).listStreamsAdmin(any(), any());
    verify(execution).setOutputVariable("streams", new V2AdminStreamList());
  }

  /**
   * Test {@link GetStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link GetStreams} with types set; then toFilter includes stream types.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given GetStreams with types set; then toFilter includes stream types")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenGetStreamsWithTypesSet_thenToFilterIncludesStreamTypes() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setTypes(Arrays.asList("IM", "ROOM"));
    getStreams.setScope("INTERNAL");
    getStreams.setOrigin("INTERNAL");
    getStreams.setStatus("ACTIVE");
    getStreams.setPrivacy("PUBLIC");

    StreamService streamService = mock(StreamService.class);
    when(streamService.listStreamsAdmin(any())).thenReturn(new V2AdminStreamList());

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<GetStreams> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(getStreams);
    when(execution.bdk()).thenReturn(bdk);

    // Act
    getStreamsExecutor.execute(execution);

    // Assert
    verify(execution).bdk();
    verify(streamService).listStreamsAdmin(any());
    verify(execution).setOutputVariable(any(), any());
  }
}
