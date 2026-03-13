package com.symphony.bdk.workflow.engine.executor.message;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetMessagesExecutorTest {

  private GetMessagesExecutor executor;

  @Mock
  private ActivityExecutorContext<GetMessages> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private MessageService messageService;

  @BeforeEach
  void setUp() {
    executor = new GetMessagesExecutor();
  }

  @Test
  void execute_shouldGetMessagesWithPagination() {
    // Arrange
    GetMessages activity = new GetMessages();
    activity.setStreamId("stream123");
    activity.setSince("2023-01-01T00:00:00Z");
    activity.setSkip(10);
    activity.setLimit(50);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
    when(messageService.listMessages(eq("stream123"), any(Instant.class), any(PaginationAttribute.class)))
        .thenReturn(List.of());

    // Act
    executor.execute(context);

    // Assert
    verify(messageService).listMessages(eq("stream123"), eq(Instant.parse("2023-01-01T00:00:00Z")),
        any(PaginationAttribute.class));
    verify(context).setOutputVariable(eq("messages"), any());
  }

  @Test
  void execute_shouldGetMessagesWithoutPagination() {
    // Arrange
    GetMessages activity = new GetMessages();
    activity.setStreamId("stream456");
    activity.setSince("2023-06-15T12:30:00Z");
    activity.setSkip(null);
    activity.setLimit(null);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
    when(messageService.listMessages(eq("stream456"), any(Instant.class)))
        .thenReturn(List.of());

    // Act
    executor.execute(context);

    // Assert
    verify(messageService).listMessages(eq("stream456"), eq(Instant.parse("2023-06-15T12:30:00Z")));
    verify(context).setOutputVariable(eq("messages"), any());
  }

  @Test
  void execute_shouldGetMessagesWithOnlySkipProvided() {
    // Arrange
    GetMessages activity = new GetMessages();
    activity.setStreamId("stream789");
    activity.setSince("2023-12-25T08:00:00Z");
    activity.setSkip(5);
    activity.setLimit(null);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
    when(messageService.listMessages(eq("stream789"), any(Instant.class)))
        .thenReturn(List.of());

    // Act
    executor.execute(context);

    // Assert
    verify(messageService).listMessages(eq("stream789"), eq(Instant.parse("2023-12-25T08:00:00Z")));
    verify(context).setOutputVariable(eq("messages"), any());
  }

  @Test
  void execute_shouldGetMessagesWithOnlyLimitProvided() {
    // Arrange
    GetMessages activity = new GetMessages();
    activity.setStreamId("streamABC");
    activity.setSince("2023-03-10T14:45:30Z");
    activity.setSkip(null);
    activity.setLimit(100);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
    when(messageService.listMessages(eq("streamABC"), any(Instant.class)))
        .thenReturn(List.of());

    // Act
    executor.execute(context);

    // Assert
    verify(messageService).listMessages(eq("streamABC"), eq(Instant.parse("2023-03-10T14:45:30Z")));
    verify(context).setOutputVariable(eq("messages"), any());
  }

  @Test
  void execute_shouldNotGetMessagesWhenStreamIdIsNull() {
    // Arrange
    GetMessages activity = new GetMessages();
    activity.setStreamId(null);
    activity.setSince("2023-01-01T00:00:00Z");
    activity.setSkip(10);
    activity.setLimit(50);

    when(context.getActivity()).thenReturn(activity);

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(bdkGateway);
    verifyNoInteractions(messageService);
  }

  @Test
  void execute_shouldNotGetMessagesWhenSinceIsNull() {
    // Arrange
    GetMessages activity = new GetMessages();
    activity.setStreamId("stream123");
    activity.setSince(null);
    activity.setSkip(10);
    activity.setLimit(50);

    when(context.getActivity()).thenReturn(activity);

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(bdkGateway);
    verifyNoInteractions(messageService);
  }

  @Test
  void execute_shouldNotGetMessagesWhenBothStreamIdAndSinceAreNull() {
    // Arrange
    GetMessages activity = new GetMessages();
    activity.setStreamId(null);
    activity.setSince(null);

    when(context.getActivity()).thenReturn(activity);

    // Act
    executor.execute(context);

    // Assert
    verifyNoInteractions(bdkGateway);
    verifyNoInteractions(messageService);
  }
}
