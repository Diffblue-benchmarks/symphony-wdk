package com.symphony.bdk.workflow.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.message.exception.PresentationMLParserException;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.event.EventType;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.EventSubscriptionQuery;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class V4MessageSentEventProcessorTest {

  private V4MessageSentEventProcessor processor;

  @Mock
  private RuntimeService runtimeService;

  @Mock
  private MessageCorrelationBuilder messageCorrelationBuilder;

  @Mock
  private SignalEventReceivedBuilder signalEventReceivedBuilder;

  @Mock
  private EventSubscriptionQuery eventSubscriptionQuery;

  @BeforeEach
  void setUp() {
    processor = new V4MessageSentEventProcessor(runtimeService);
  }

  @Test
  void constructor_shouldInitializeProcessorWithRuntimeService() {
    // Arrange
    RuntimeService runtimeService = mock(RuntimeService.class);

    // Act
    V4MessageSentEventProcessor processor = new V4MessageSentEventProcessor(runtimeService);

    // Assert
    assertThat(processor).isNotNull();
  }

  @Test
  void processEventSource_shouldProcessMessageAndCorrelateWithContent() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-123");
    message.setMessage("<messageML>Hello</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of());
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService).createMessageCorrelation("message-received_Hello");
    verify(messageCorrelationBuilder).setVariables(variables);
    verify(messageCorrelationBuilder).correlateAll();
    verify(runtimeService).createEventSubscriptionQuery();
    verify(eventSubscriptionQuery).eventType(EventType.SIGNAL.name());
    verify(runtimeService).createSignalEvent("message-received_");
    verify(signalEventReceivedBuilder, times(1)).send();
  }

  @Test
  void processEventSource_shouldSendSignalForMatchingSubscription() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-456");
    message.setMessage("<messageML>/help</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    EventSubscription subscription = mock(EventSubscription.class);
    when(subscription.getEventName()).thenReturn("message-received_/help");

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of(subscription));
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService).createSignalEvent("message-received_/help");
    verify(runtimeService).createSignalEvent("message-received_");
    verify(signalEventReceivedBuilder, times(2)).send();
  }

  @Test
  void processEventSource_shouldMatchWildcardPatterns() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-789");
    message.setMessage("<messageML>/command arg1 arg2</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    EventHolder<V4MessageSent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    EventSubscription subscription = mock(EventSubscription.class);
    when(subscription.getEventName()).thenReturn("message-received_/command *");

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of(subscription));
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService).createSignalEvent("message-received_/command *");
    verify(runtimeService).createSignalEvent("message-received_");
    verify(signalEventReceivedBuilder, times(2)).send();
    assertThat(eventHolder.getArgs()).containsKey("eventName");
  }

  @Test
  void processEventSource_shouldExtractUriTemplateVariables() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-101");
    message.setMessage("<messageML>/user john</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    EventHolder<V4MessageSent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
    variables.put(ActivityExecutorContext.EVENT, eventHolder);

    EventSubscription subscription = mock(EventSubscription.class);
    when(subscription.getEventName()).thenReturn("message-received_/user {username}");

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of(subscription));
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService).createSignalEvent("message-received_/user {username}");
    verify(runtimeService).createSignalEvent("message-received_");
    assertThat(eventHolder.getArgs()).containsEntry("username", "john");
    assertThat(eventHolder.getArgs()).containsEntry("eventName", "message-received_/user {username}");
  }

  @Test
  void processEventSource_shouldAvoidDuplicateSignals() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-102");
    message.setMessage("<messageML>/duplicate</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    EventSubscription subscription1 = mock(EventSubscription.class);
    when(subscription1.getEventName()).thenReturn("message-received_/duplicate");

    EventSubscription subscription2 = mock(EventSubscription.class);
    when(subscription2.getEventName()).thenReturn("message-received_/duplicate");

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of(subscription1, subscription2));
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService, times(1)).createSignalEvent("message-received_/duplicate");
    verify(runtimeService).createSignalEvent("message-received_");
    verify(signalEventReceivedBuilder, times(2)).send();
  }

  @Test
  void processEventSource_shouldNotSendSignalForNonMatchingSubscription() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-103");
    message.setMessage("<messageML>/hello</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    EventSubscription subscription = mock(EventSubscription.class);
    when(subscription.getEventName()).thenReturn("message-received_/goodbye");

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of(subscription));
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService, never()).createSignalEvent("message-received_/goodbye");
    verify(runtimeService).createSignalEvent("message-received_");
    verify(signalEventReceivedBuilder, times(1)).send();
  }

  @Test
  void processEventSource_shouldHandleMultipleMatchingSubscriptions() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-104");
    message.setMessage("<messageML>/status ok</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    EventSubscription subscription1 = mock(EventSubscription.class);
    when(subscription1.getEventName()).thenReturn("message-received_/status *");

    EventSubscription subscription2 = mock(EventSubscription.class);
    when(subscription2.getEventName()).thenReturn("message-received_/status ok");

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of(subscription1, subscription2));
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService).createSignalEvent("message-received_/status *");
    verify(runtimeService).createSignalEvent("message-received_/status ok");
    verify(runtimeService).createSignalEvent("message-received_");
    verify(signalEventReceivedBuilder, times(3)).send();
  }

  @Test
  void processEventSource_shouldHandleNullMessage() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    eventSource.setMessage(null);

    Map<String, Object> variables = new HashMap<>();

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService, never()).createMessageCorrelation(anyString());
    verify(runtimeService, never()).createSignalEvent(anyString());
  }

  @Test
  void processEventSource_shouldHandleEmptySubscriptionList() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-105");
    message.setMessage("<messageML>test</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of());
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService).createMessageCorrelation("message-received_test");
    verify(runtimeService).createSignalEvent("message-received_");
    verify(signalEventReceivedBuilder, times(1)).send();
  }

  @Test
  void processEventSource_shouldParsePresentationML() throws Exception {
    // Arrange
    V4MessageSent eventSource = new V4MessageSent();
    V4Message message = new V4Message();
    message.setMessageId("msg-106");
    message.setMessage("<messageML><b>Bold</b> text</messageML>");
    eventSource.setMessage(message);

    Map<String, Object> variables = new HashMap<>();
    variables.put(ActivityExecutorContext.EVENT, new EventHolder<>(null, eventSource, new HashMap<>()));

    when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
    when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);
    when(runtimeService.createEventSubscriptionQuery()).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.eventType(anyString())).thenReturn(eventSubscriptionQuery);
    when(eventSubscriptionQuery.list()).thenReturn(List.of());
    when(runtimeService.createSignalEvent(anyString())).thenReturn(signalEventReceivedBuilder);
    when(signalEventReceivedBuilder.setVariables(any())).thenReturn(signalEventReceivedBuilder);

    // Act
    processor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService).createMessageCorrelation(eq("message-received_Bold text"));
    verify(messageCorrelationBuilder).correlateAll();
  }
}
