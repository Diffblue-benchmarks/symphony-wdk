package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetMessageExecutorClaudeTest {

  private GetMessageExecutor executor;
  private ActivityExecutorContext<GetMessage> context;
  private GetMessage activity;
  private BdkGateway bdkGateway;
  private MessageService messageService;

  @BeforeEach
  void setUp() {
    executor = new GetMessageExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetMessage();
    bdkGateway = mock(BdkGateway.class);
    messageService = mock(MessageService.class);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetMessageExecutor newExecutor = new GetMessageExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetMessageExecutor newExecutor = new GetMessageExecutor();

    // Then: Instance should be of GetMessageExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(GetMessageExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetMessageExecutor executor1 = new GetMessageExecutor();
    GetMessageExecutor executor2 = new GetMessageExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetMessageExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - success scenarios

  @Test
  void execute_withValidMessageId_shouldRetrieveMessageAndSetOutputVariable() {
    // Given: A valid message ID
    String messageId = "msg123";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setMessage("<messageML>Hello World</messageML>");

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should be set with the retrieved message
    verify(messageService).getMessage(messageId);
    verify(context).setOutputVariable(eq("message"), eq(message));
  }

  @Test
  void execute_withDifferentMessageId_shouldRetrieveCorrectMessage() {
    // Given: A different message ID
    String messageId = "msg-xyz-789";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setMessage("<messageML>Different message</messageML>");

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: The correct message should be retrieved
    verify(messageService).getMessage(messageId);
    verify(context).setOutputVariable(eq("message"), eq(message));
  }

  @Test
  void execute_withMessageContainingMetadata_shouldRetrieveCompleteMessage() {
    // Given: A message ID for a message with full metadata
    String messageId = "msg456";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setMessage("<messageML>Message with metadata</messageML>");
    message.setTimestamp(1234567890L);

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: The complete message with all metadata should be retrieved
    verify(messageService).getMessage(messageId);
    verify(context).setOutputVariable(eq("message"), eq(message));
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid setup
    String messageId = "msg123";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(messageService, org.mockito.Mockito.times(2)).getMessage(messageId);
    verify(context, org.mockito.Mockito.times(2)).setOutputVariable(eq("message"), eq(message));
  }

  @Test
  void execute_withEmptyMessageContent_shouldHandleCorrectly() {
    // Given: A message with empty content
    String messageId = "msg789";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setMessage("");

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(context).setOutputVariable(eq("message"), eq(message));
  }

  @Test
  void execute_withNullMessageContent_shouldHandleCorrectly() {
    // Given: A message with null content
    String messageId = "msg999";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setMessage(null);

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(context).setOutputVariable(eq("message"), eq(message));
  }

  // Tests for execute method - error scenarios

  @Test
  void execute_withNullMessage_shouldSetNullInOutputVariable() {
    // Given: Message service returns null
    String messageId = "msg123";
    activity.setMessageId(messageId);

    when(messageService.getMessage(messageId)).thenReturn(null);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Null should be set in output variable
    verify(messageService).getMessage(messageId);
    verify(context).setOutputVariable(eq("message"), eq(null));
  }

  @Test
  void execute_withServiceThrowingRuntimeException_shouldPropagateException() {
    // Given: Message service throws RuntimeException
    String messageId = "msg123";
    activity.setMessageId(messageId);

    when(messageService.getMessage(messageId)).thenThrow(new RuntimeException("Failed to get message"));

    // When/Then: Execute should propagate the exception
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Failed to get message");
  }

  @Test
  void execute_withNullMessageId_shouldCallServiceWithNull() {
    // Given: Activity has null message ID
    activity.setMessageId(null);

    V4Message message = new V4Message();
    when(messageService.getMessage(null)).thenReturn(message);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Service should be called with null
    verify(messageService).getMessage(null);
    verify(context).setOutputVariable(eq("message"), eq(message));
  }

  // Tests for execute method - edge cases

  @Test
  void execute_withSpecialCharactersInMessageId_shouldHandleCorrectly() {
    // Given: Message ID with special characters
    String messageId = "msg-with-special-chars_123!@#";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(messageService).getMessage(messageId);
    verify(context).setOutputVariable(eq("message"), eq(message));
  }

  @Test
  void execute_withLongMessageId_shouldHandleCorrectly() {
    // Given: A very long message ID
    String messageId = "msg" + "1234567890".repeat(20);
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(messageService).getMessage(messageId);
    verify(context).setOutputVariable(eq("message"), eq(message));
  }

  @Test
  void execute_shouldUseCorrectOutputVariableKey() {
    // Given: Valid setup
    String messageId = "msg123";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should use the key "message"
    verify(context).setOutputVariable(eq("message"), eq(message));
  }
}
