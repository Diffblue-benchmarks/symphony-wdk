package com.symphony.bdk.workflow.engine.executor.stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.StreamFilter;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetUserStreams;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetUserStreamsExecutorDiffblueTest {
  /**
   * Test {@link GetUserStreamsExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link StreamService} {@link StreamService#listStreams(StreamFilter)} return {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link StreamService#listStreams(StreamFilter)}.
   * </ul>
   *
   * <p>Method under test: {@link GetUserStreamsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given StreamService listStreams(StreamFilter) return ArrayList(); then calls listStreams(StreamFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetUserStreamsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenStreamServiceListStreamsReturnArrayList_thenCallsListStreams() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetUserStreamsExecutor getUserStreamsExecutor = new GetUserStreamsExecutor();

    StreamService streamService = mock(StreamService.class);
    when(streamService.listStreams(Mockito.<StreamFilter>any())).thenReturn(new ArrayList<>());
    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            streamService,
            mock(UserService.class),
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    ActivityExecutorContext<GetUserStreams> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new GetUserStreams());

    // Act
    getUserStreamsExecutor.execute(execution);

    // Assert
    verify(streamService).listStreams(isA(StreamFilter.class));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(execution).setOutputVariable(eq("streams"), isA(Object.class));
  }
}
