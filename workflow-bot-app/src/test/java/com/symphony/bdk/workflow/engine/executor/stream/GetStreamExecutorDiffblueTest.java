package com.symphony.bdk.workflow.engine.executor.stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.V2StreamAttributes;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetStreamExecutorDiffblueTest {
  /**
   * Test {@link GetStreamExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link StreamService#getStream(String)}.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls getStream(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void GetStreamExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsGetStream() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetStreamExecutor getStreamExecutor = new GetStreamExecutor();

    StreamService streamService = mock(StreamService.class);
    when(streamService.getStream(Mockito.<String>any())).thenReturn(new V2StreamAttributes());
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

    ActivityExecutorContext<GetStream> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new GetStream());

    // Act
    getStreamExecutor.execute(execution);

    // Assert
    verify(streamService).getStream(null);
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(execution).setOutputVariable(eq("stream"), isA(Object.class));
  }

  /**
   * Test {@link GetStreamExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When OBO is configured with a username, then calls OBO stream getStream.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); when OBO username configured; then calls OBO getStream")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"void GetStreamExecutor.execute(ActivityExecutorContext)"})
  void testExecute_withOboUsername_thenCallsOboGetStream() {
    // Arrange
    GetStreamExecutor getStreamExecutor = new GetStreamExecutor();

    Obo obo = new Obo();
    obo.setUsername("testUser");

    GetStream activity = new GetStream();
    activity.setStreamId("streamId123");
    activity.setObo(obo);

    V2StreamAttributes streamAttributes = new V2StreamAttributes();
    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(oboStreamService.getStream(Mockito.<String>any())).thenReturn(streamAttributes);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo(any(String.class))).thenReturn(authSession);
    when(bdkGateway.obo(any(AuthSession.class))).thenReturn(oboServices);

    ActivityExecutorContext<GetStream> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getActivity()).thenReturn(activity);

    // Act
    getStreamExecutor.execute(execution);

    // Assert
    verify(oboStreamService).getStream("streamId123");
    verify(bdkGateway).obo(authSession);
    verify(oboServices).streams();
    verify(execution).setOutputVariable(eq("stream"), isA(V2StreamAttributes.class));
  }

  /**
   * Test {@link GetStreamExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls OBO stream getStream.
   * </ul>
   *
   * <p>Method under test: {@link GetStreamExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then calls OBO getStream")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"com.symphony.bdk.gen.api.model.V2StreamAttributes GetStreamExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache_thenCallsOboGetStream() throws Exception {
    // Arrange
    GetStreamExecutor getStreamExecutor = new GetStreamExecutor();

    Obo obo = new Obo();
    obo.setUsername("testUser");

    GetStream activity = new GetStream();
    activity.setStreamId("streamId456");
    activity.setObo(obo);

    V2StreamAttributes streamAttributes = new V2StreamAttributes();
    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(oboStreamService.getStream(Mockito.<String>any())).thenReturn(streamAttributes);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.streams()).thenReturn(oboStreamService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo(any(String.class))).thenReturn(authSession);
    when(bdkGateway.obo(any(AuthSession.class))).thenReturn(oboServices);

    ActivityExecutorContext<GetStream> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getActivity()).thenReturn(activity);

    // Act
    getStreamExecutor.doOboWithCache(execution);

    // Assert
    verify(oboStreamService).getStream("streamId456");
    verify(bdkGateway).obo(authSession);
    verify(oboServices).streams();
  }
}
