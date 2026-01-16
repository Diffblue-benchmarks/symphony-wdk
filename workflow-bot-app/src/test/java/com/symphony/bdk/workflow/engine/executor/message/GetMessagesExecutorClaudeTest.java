package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class GetMessagesExecutorClaudeTest {

  private GetMessagesExecutor executor;
  private ActivityExecutorContext<GetMessages> context;
  private GetMessages activity;
  private BdkGateway bdkGateway;
  private MessageService messageService;

  @BeforeEach
  void setUp() {
    executor = new GetMessagesExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetMessages();
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
    GetMessagesExecutor newExecutor = new GetMessagesExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetMessagesExecutor newExecutor = new GetMessagesExecutor();

    // Then: Instance should be of GetMessagesExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(GetMessagesExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetMessagesExecutor executor1 = new GetMessagesExecutor();
    GetMessagesExecutor executor2 = new GetMessagesExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetMessagesExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - success scenarios with pagination

  @Test
  void execute_withStreamIdAndSinceAndPagination_shouldRetrieveMessagesWithPagination() {
    // Given: A valid streamId, since timestamp, skip and limit
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    int skip = 10;
    int limit = 50;
    activity.setStreamId(streamId);
    activity.setSince(since);
    activity.setSkip(skip);
    activity.setLimit(limit);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since)), any(PaginationAttribute.class)))
        .thenReturn(messages);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should be set with the retrieved messages
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)), paginationCaptor.capture());
    verify(context).setOutputVariable(eq("messages"), eq(messages));

    PaginationAttribute capturedPagination = paginationCaptor.getValue();
    assertThat(capturedPagination).isNotNull();
    assertThat(capturedPagination.getSkip()).isEqualTo(skip);
    assertThat(capturedPagination.getLimit()).isEqualTo(limit);
  }

  @Test
  void execute_withStreamIdAndSinceNoPagination_shouldRetrieveMessagesWithoutPagination() {
    // Given: A valid streamId and since timestamp, but no skip/limit
    String streamId = "stream456";
    String since = "2024-02-01T12:30:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(messages);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should be set with the retrieved messages (without pagination)
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)));
    verify(messageService, never()).listMessages(eq(streamId), eq(Instant.parse(since)), any(PaginationAttribute.class));
    verify(context).setOutputVariable(eq("messages"), eq(messages));
  }

  @Test
  void execute_withOnlySkipSet_shouldRetrieveMessagesWithoutPagination() {
    // Given: streamId, since and skip set, but limit is null
    String streamId = "stream789";
    String since = "2024-03-01T08:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);
    activity.setSkip(5);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(messages);

    // When: Execute is called
    executor.execute(context);

    // Then: Should call without pagination (since limit is null)
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)));
    verify(messageService, never()).listMessages(eq(streamId), eq(Instant.parse(since)), any(PaginationAttribute.class));
    verify(context).setOutputVariable(eq("messages"), eq(messages));
  }

  @Test
  void execute_withOnlyLimitSet_shouldRetrieveMessagesWithoutPagination() {
    // Given: streamId, since and limit set, but skip is null
    String streamId = "stream999";
    String since = "2024-04-01T16:45:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);
    activity.setLimit(100);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(messages);

    // When: Execute is called
    executor.execute(context);

    // Then: Should call without pagination (since skip is null)
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)));
    verify(messageService, never()).listMessages(eq(streamId), eq(Instant.parse(since)), any(PaginationAttribute.class));
    verify(context).setOutputVariable(eq("messages"), eq(messages));
  }

  @Test
  void execute_withPaginationZeroValues_shouldRetrieveMessagesWithPagination() {
    // Given: streamId, since, and pagination with zero values
    String streamId = "stream000";
    String since = "2024-05-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);
    activity.setSkip(0);
    activity.setLimit(0);

    List<V4Message> messages = Collections.emptyList();
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since)), any(PaginationAttribute.class)))
        .thenReturn(messages);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still call with pagination (0 is valid value)
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)), paginationCaptor.capture());
    verify(context).setOutputVariable(eq("messages"), eq(messages));

    PaginationAttribute capturedPagination = paginationCaptor.getValue();
    assertThat(capturedPagination.getSkip()).isEqualTo(0);
    assertThat(capturedPagination.getLimit()).isEqualTo(0);
  }

  // Tests for execute method - null/missing required fields

  @Test
  void execute_withNullStreamId_shouldNotCallMessageService() {
    // Given: since is set but streamId is null
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(null);
    activity.setSince(since);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Message service should not be called
    verifyNoInteractions(messageService);
    verify(context, never()).setOutputVariable(any(), any());
  }

  @Test
  void execute_withNullSince_shouldNotCallMessageService() {
    // Given: streamId is set but since is null
    String streamId = "stream123";
    activity.setStreamId(streamId);
    activity.setSince(null);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Message service should not be called
    verifyNoInteractions(messageService);
    verify(context, never()).setOutputVariable(any(), any());
  }

  @Test
  void execute_withBothStreamIdAndSinceNull_shouldNotCallMessageService() {
    // Given: Both streamId and since are null
    activity.setStreamId(null);
    activity.setSince(null);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Message service should not be called
    verifyNoInteractions(messageService);
    verify(context, never()).setOutputVariable(any(), any());
  }

  // Tests for execute method - edge cases

  @Test
  void execute_withDifferentInstantFormats_shouldParseCorrectly() {
    // Given: A valid streamId and since in ISO-8601 format with milliseconds
    String streamId = "stream123";
    String since = "2024-01-15T10:30:45.123Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(messages);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should parse and call correctly
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)));
    verify(context).setOutputVariable(eq("messages"), eq(messages));
  }

  @Test
  void execute_withInvalidSinceFormat_shouldThrowException() {
    // Given: streamId is valid but since has invalid format
    String streamId = "stream123";
    String since = "invalid-date-format";
    activity.setStreamId(streamId);
    activity.setSince(since);

    // When/Then: Execute should throw exception when parsing the date
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(Exception.class);
  }

  @Test
  void execute_withEmptyMessages_shouldSetEmptyListInOutputVariable() {
    // Given: Valid parameters that return empty list
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    List<V4Message> emptyMessages = Collections.emptyList();
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(emptyMessages);

    // When: Execute is called
    executor.execute(context);

    // Then: Empty list should be set in output variable
    verify(context).setOutputVariable(eq("messages"), eq(emptyMessages));
  }

  @Test
  void execute_withMultipleMessages_shouldSetAllMessagesInOutputVariable() {
    // Given: Valid parameters that return multiple messages
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    V4Message msg1 = new V4Message();
    msg1.setMessageId("msg1");
    V4Message msg2 = new V4Message();
    msg2.setMessageId("msg2");
    V4Message msg3 = new V4Message();
    msg3.setMessageId("msg3");
    List<V4Message> messages = List.of(msg1, msg2, msg3);

    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(messages);

    // When: Execute is called
    executor.execute(context);

    // Then: All messages should be set in output variable
    verify(context).setOutputVariable(eq("messages"), eq(messages));
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid setup
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(messages);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(messageService, times(2)).listMessages(eq(streamId), eq(Instant.parse(since)));
    verify(context, times(2)).setOutputVariable(eq("messages"), eq(messages));
  }

  @Test
  void execute_withSpecialCharactersInStreamId_shouldHandleCorrectly() {
    // Given: Stream ID with special characters
    String streamId = "stream-with-special_chars/123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(messages);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)));
    verify(context).setOutputVariable(eq("messages"), eq(messages));
  }

  @Test
  void execute_withLargeSkipAndLimit_shouldHandleCorrectly() {
    // Given: Large values for skip and limit
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);
    activity.setSkip(10000);
    activity.setLimit(5000);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since)), any(PaginationAttribute.class)))
        .thenReturn(messages);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)), paginationCaptor.capture());
    verify(context).setOutputVariable(eq("messages"), eq(messages));

    PaginationAttribute capturedPagination = paginationCaptor.getValue();
    assertThat(capturedPagination.getSkip()).isEqualTo(10000);
    assertThat(capturedPagination.getLimit()).isEqualTo(5000);
  }

  // Tests for execute method - error scenarios

  @Test
  void execute_withServiceThrowingRuntimeException_shouldPropagateException() {
    // Given: Message service throws RuntimeException
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenThrow(new RuntimeException("Failed to get messages"));

    // When/Then: Execute should propagate the exception
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Failed to get messages");
  }

  @Test
  void execute_withServiceReturningNull_shouldSetNullInOutputVariable() {
    // Given: Message service returns null
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(null);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Null should be set in output variable
    verify(messageService).listMessages(eq(streamId), eq(Instant.parse(since)));
    verify(context).setOutputVariable(eq("messages"), eq(null));
  }

  @Test
  void execute_shouldUseCorrectOutputVariableKey() {
    // Given: Valid setup
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);

    List<V4Message> messages = Collections.singletonList(new V4Message());
    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since))))
        .thenReturn(messages);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should use the key "messages"
    verify(context).setOutputVariable(eq("messages"), eq(messages));
  }

  @Test
  void execute_withPaginationAndServiceThrowingException_shouldPropagateException() {
    // Given: Message service throws exception with pagination
    String streamId = "stream123";
    String since = "2024-01-01T00:00:00Z";
    activity.setStreamId(streamId);
    activity.setSince(since);
    activity.setSkip(10);
    activity.setLimit(50);

    when(messageService.listMessages(eq(streamId), eq(Instant.parse(since)), any(PaginationAttribute.class)))
        .thenThrow(new RuntimeException("Pagination error"));

    // When/Then: Execute should propagate the exception
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Pagination error");
  }
}
