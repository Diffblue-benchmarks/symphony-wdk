package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessages;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMessagesExecutorTest {

  @InjectMocks
  private GetMessagesExecutor executor;

  @Mock
  private ActivityExecutorContext<GetMessages> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private MessageService messageService;

  private void setUpContextMocks(GetMessages activity) {
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.messages()).thenReturn(messageService);
  }

  @Test
  void shouldListMessagesWithPaginationWhenSkipAndLimitAreSet() {
    GetMessages activity = new GetMessages();
    activity.setStreamId("stream123");
    activity.setSince("2023-01-01T00:00:00Z");
    activity.setSkip(5);
    activity.setLimit(10);
    when(messageService.listMessages(eq("stream123"), any(Instant.class), any())).thenReturn(Collections.emptyList());
    setUpContextMocks(activity);

    executor.execute(context);

    verify(messageService).listMessages(eq("stream123"), any(Instant.class), any());
    verify(context).setOutputVariable(eq("messages"), any());
  }

  @Test
  void shouldListMessagesWithoutPaginationWhenSkipAndLimitAreNull() {
    GetMessages activity = new GetMessages();
    activity.setStreamId("stream456");
    activity.setSince("2023-06-15T12:00:00Z");
    when(messageService.listMessages(eq("stream456"), any(Instant.class))).thenReturn(Collections.emptyList());
    setUpContextMocks(activity);

    executor.execute(context);

    verify(messageService).listMessages(eq("stream456"), any(Instant.class));
    verify(context).setOutputVariable(eq("messages"), any());
  }

  @Test
  void shouldNotListMessagesWhenStreamIdIsNull() {
    GetMessages activity = new GetMessages();
    activity.setStreamId(null);
    activity.setSince("2023-01-01T00:00:00Z");
    when(context.getActivity()).thenReturn(activity);

    executor.execute(context);

    verify(bdk, org.mockito.Mockito.never()).messages();
  }

  @Test
  void shouldNotListMessagesWhenSinceIsNull() {
    GetMessages activity = new GetMessages();
    activity.setStreamId("stream789");
    activity.setSince(null);
    when(context.getActivity()).thenReturn(activity);

    executor.execute(context);

    verify(bdk, org.mockito.Mockito.never()).messages();
  }
}
