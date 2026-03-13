package com.symphony.bdk.workflow.engine.executor.stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2StreamAttributes;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStream;
import com.symphony.bdk.core.OboServices;

import org.junit.jupiter.api.Test;

class GetStreamExecutorTest {

  @Test
  void shouldGetStreamWhenExecuteCalledWithoutObo() {
    // Arrange
    GetStreamExecutor executor = new GetStreamExecutor();
    ActivityExecutorContext<GetStream> context = mock(ActivityExecutorContext.class);
    GetStream activity = mock(GetStream.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    StreamService streamService = mock(StreamService.class);
    V2StreamAttributes streamAttributes = mock(V2StreamAttributes.class);

    when(context.getActivity()).thenReturn(activity);
    when(activity.getStreamId()).thenReturn("test-stream-id");
    when(activity.getObo()).thenReturn(null);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
    when(streamService.getStream("test-stream-id")).thenReturn(streamAttributes);

    // Act
    executor.execute(context);

    // Assert
    verify(context).setOutputVariable(eq("stream"), eq(streamAttributes));
  }

  @Test
  void shouldGetStreamWhenExecuteCalledWithObo() {
    // Arrange
    GetStreamExecutor executor = new GetStreamExecutor();
    ActivityExecutorContext<GetStream> context = mock(ActivityExecutorContext.class);
    GetStream activity = mock(GetStream.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    V2StreamAttributes streamAttributes = mock(V2StreamAttributes.class);
    AuthSession authSession = mock(AuthSession.class);

    when(context.getActivity()).thenReturn(activity);
    when(activity.getStreamId()).thenReturn("test-stream-id");
    when(activity.getObo()).thenReturn(mock(com.symphony.bdk.workflow.swadl.v1.activity.Obo.class));
    when(activity.getObo().getUsername()).thenReturn("test-user");
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.obo("test-user")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);
    when(streamService.getStream("test-stream-id")).thenReturn(streamAttributes);

    // Act
    executor.execute(context);

    // Assert
    verify(context).setOutputVariable(eq("stream"), eq(streamAttributes));
  }
}
