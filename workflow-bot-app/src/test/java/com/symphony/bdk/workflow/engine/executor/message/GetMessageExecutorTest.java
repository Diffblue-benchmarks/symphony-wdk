package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMessageExecutorTest {

  @Mock
  private ActivityExecutorContext<GetMessage> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private MessageService messageService;

  @Mock
  private V4Message message;

  private GetMessageExecutor executor;

  @BeforeEach
  void setUp() {
    executor = new GetMessageExecutor();
    lenient().when(context.bdk()).thenReturn(bdkGateway);
    lenient().when(bdkGateway.messages()).thenReturn(messageService);
  }

  @Test
  void shouldGetMessageById() {
    GetMessage activity = new GetMessage();
    activity.setMessageId("message123");
    when(context.getActivity()).thenReturn(activity);
    when(messageService.getMessage("message123")).thenReturn(message);

    executor.execute(context);

    verify(messageService).getMessage("message123");
    verify(context).setOutputVariable("message", message);
  }
}
