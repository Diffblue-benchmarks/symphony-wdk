package com.symphony.bdk.workflow.engine.executor.message;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetMessageExecutorTest {

  @Test
  @DisplayName("Test execute(ActivityExecutorContext); retrieves message by id and sets output variable")
  void testExecute_retrievesMessageAndSetsOutput() {
    // Arrange
    GetMessageExecutor getMessageExecutor = new GetMessageExecutor();

    GetMessage activity = new GetMessage();
    activity.setMessageId("testMessageId");

    V4Message v4Message = new V4Message();

    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(eq("testMessageId"))).thenReturn(v4Message);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.messages()).thenReturn(messageService);

    ActivityExecutorContext<GetMessage> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    getMessageExecutor.execute(context);

    // Assert
    verify(messageService).getMessage("testMessageId");
    verify(context).setOutputVariable(eq("message"), isA(V4Message.class));
  }

  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given null messageId; calls getMessage with null")
  void testExecute_givenNullMessageId_callsGetMessageWithNull() {
    // Arrange
    GetMessageExecutor getMessageExecutor = new GetMessageExecutor();

    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(any())).thenReturn(new V4Message());

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.messages()).thenReturn(messageService);

    ActivityExecutorContext<GetMessage> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(new GetMessage());
    when(context.bdk()).thenReturn(bdk);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    getMessageExecutor.execute(context);

    // Assert
    verify(messageService).getMessage(null);
    verify(context).setOutputVariable(eq("message"), isA(Object.class));
  }
}
