package com.symphony.bdk.workflow.event;

import com.symphony.bdk.core.service.message.exception.PresentationMLParserException;
import com.symphony.bdk.core.service.message.util.PresentationMLParser;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.EventSubscriptionQuery;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class V4MessageSentEventProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  private V4MessageSentEventProcessor processor;

  @BeforeEach
  void setUp() {
    processor = new V4MessageSentEventProcessor(runtimeService);
  }

  @Test
  void shouldInitializeWithMessageReceivedEventName() {
    assertThat(processor).isNotNull();
  }

  @Test
  void shouldNotProcessWhenMessageIsNull() throws Exception {
    V4MessageSent eventSource = new V4MessageSent();
    Map<String, Object> variables = new HashMap<>();

    processor.processEventSource(eventSource, variables);

    verify(runtimeService, never()).createMessageCorrelation(anyString());
    verify(runtimeService, never()).createSignalEvent(anyString());
  }

  @Test
  void shouldCorrelateMessageAndSendGeneralSignalWhenNoSubscriptions() throws Exception {
    String receivedContent = "hello world";

    try (MockedStatic<PresentationMLParser> parserMock = Mockito.mockStatic(PresentationMLParser.class)) {
      parserMock.when(() -> PresentationMLParser.getTextContent(anyString())).thenReturn(receivedContent);

      V4Message message = new V4Message();
      message.setMessage("<messageML>hello world</messageML>");
      message.setMessageId("msg-123");

      V4MessageSent eventSource = new V4MessageSent();
      eventSource.setMessage(message);

      EventHolder<V4MessageSent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
      Map<String, Object> variables = new HashMap<>();
      variables.put(ActivityExecutorContext.EVENT, eventHolder);

      MessageCorrelationBuilder messageCorrelationBuilder = mock(MessageCorrelationBuilder.class);
      when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
      when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);

      EventSubscriptionQuery query = mock(EventSubscriptionQuery.class);
      when(runtimeService.createEventSubscriptionQuery()).thenReturn(query);
      when(query.eventType(anyString())).thenReturn(query);
      when(query.list()).thenReturn(Collections.emptyList());

      SignalEventReceivedBuilder signalBuilder = mock(SignalEventReceivedBuilder.class);
      when(runtimeService.createSignalEvent(anyString())).thenReturn(signalBuilder);
      when(signalBuilder.setVariables(any())).thenReturn(signalBuilder);

      processor.processEventSource(eventSource, variables);

      String eventName = WorkflowEventType.MESSAGE_RECEIVED.getEventName();
      verify(runtimeService).createMessageCorrelation(eventName + receivedContent);
      verify(messageCorrelationBuilder).correlateAll();
      verify(runtimeService).createSignalEvent(eventName);
      verify(signalBuilder).send();
    }
  }

  @Test
  void shouldSendSignalForMatchingSubscription() throws Exception {
    String receivedContent = "/command";

    try (MockedStatic<PresentationMLParser> parserMock = Mockito.mockStatic(PresentationMLParser.class)) {
      parserMock.when(() -> PresentationMLParser.getTextContent(anyString())).thenReturn(receivedContent);

      V4Message message = new V4Message();
      message.setMessage("<messageML>/command</messageML>");
      message.setMessageId("msg-456");

      V4MessageSent eventSource = new V4MessageSent();
      eventSource.setMessage(message);

      EventHolder<V4MessageSent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
      Map<String, Object> variables = new HashMap<>();
      variables.put(ActivityExecutorContext.EVENT, eventHolder);

      String eventName = WorkflowEventType.MESSAGE_RECEIVED.getEventName();
      String signalName = eventName + receivedContent;

      MessageCorrelationBuilder messageCorrelationBuilder = mock(MessageCorrelationBuilder.class);
      when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
      when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);

      EventSubscription subscription = mock(EventSubscription.class);
      when(subscription.getEventName()).thenReturn(signalName);

      EventSubscriptionQuery query = mock(EventSubscriptionQuery.class);
      when(runtimeService.createEventSubscriptionQuery()).thenReturn(query);
      when(query.eventType(anyString())).thenReturn(query);
      when(query.list()).thenReturn(List.of(subscription));

      SignalEventReceivedBuilder signalBuilder = mock(SignalEventReceivedBuilder.class);
      when(runtimeService.createSignalEvent(anyString())).thenReturn(signalBuilder);
      when(signalBuilder.setVariables(any())).thenReturn(signalBuilder);

      processor.processEventSource(eventSource, variables);

      verify(runtimeService).createSignalEvent(signalName);
      verify(runtimeService).createSignalEvent(eventName);
    }
  }

  @Test
  void shouldNotSendDuplicateSignalsForSameSubscription() throws Exception {
    String receivedContent = "/command";

    try (MockedStatic<PresentationMLParser> parserMock = Mockito.mockStatic(PresentationMLParser.class)) {
      parserMock.when(() -> PresentationMLParser.getTextContent(anyString())).thenReturn(receivedContent);

      V4Message message = new V4Message();
      message.setMessage("<messageML>/command</messageML>");
      message.setMessageId("msg-789");

      V4MessageSent eventSource = new V4MessageSent();
      eventSource.setMessage(message);

      EventHolder<V4MessageSent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
      Map<String, Object> variables = new HashMap<>();
      variables.put(ActivityExecutorContext.EVENT, eventHolder);

      String eventName = WorkflowEventType.MESSAGE_RECEIVED.getEventName();
      String signalName = eventName + receivedContent;

      MessageCorrelationBuilder messageCorrelationBuilder = mock(MessageCorrelationBuilder.class);
      when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
      when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);

      EventSubscription subscription1 = mock(EventSubscription.class);
      when(subscription1.getEventName()).thenReturn(signalName);
      EventSubscription subscription2 = mock(EventSubscription.class);
      when(subscription2.getEventName()).thenReturn(signalName);

      EventSubscriptionQuery query = mock(EventSubscriptionQuery.class);
      when(runtimeService.createEventSubscriptionQuery()).thenReturn(query);
      when(query.eventType(anyString())).thenReturn(query);
      when(query.list()).thenReturn(List.of(subscription1, subscription2));

      SignalEventReceivedBuilder signalBuilder = mock(SignalEventReceivedBuilder.class);
      when(runtimeService.createSignalEvent(anyString())).thenReturn(signalBuilder);
      when(signalBuilder.setVariables(any())).thenReturn(signalBuilder);

      processor.processEventSource(eventSource, variables);

      verify(runtimeService, times(1)).createSignalEvent(signalName);
      verify(runtimeService, times(1)).createSignalEvent(eventName);
    }
  }

  @Test
  void shouldNotSendSignalForNonMatchingSubscription() throws Exception {
    String receivedContent = "/hello";

    try (MockedStatic<PresentationMLParser> parserMock = Mockito.mockStatic(PresentationMLParser.class)) {
      parserMock.when(() -> PresentationMLParser.getTextContent(anyString())).thenReturn(receivedContent);

      V4Message message = new V4Message();
      message.setMessage("<messageML>/hello</messageML>");
      message.setMessageId("msg-999");

      V4MessageSent eventSource = new V4MessageSent();
      eventSource.setMessage(message);

      EventHolder<V4MessageSent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
      Map<String, Object> variables = new HashMap<>();
      variables.put(ActivityExecutorContext.EVENT, eventHolder);

      String eventName = WorkflowEventType.MESSAGE_RECEIVED.getEventName();
      String nonMatchingSignalName = eventName + "/other-command";

      MessageCorrelationBuilder messageCorrelationBuilder = mock(MessageCorrelationBuilder.class);
      when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
      when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);

      EventSubscription subscription = mock(EventSubscription.class);
      when(subscription.getEventName()).thenReturn(nonMatchingSignalName);

      EventSubscriptionQuery query = mock(EventSubscriptionQuery.class);
      when(runtimeService.createEventSubscriptionQuery()).thenReturn(query);
      when(query.eventType(anyString())).thenReturn(query);
      when(query.list()).thenReturn(List.of(subscription));

      SignalEventReceivedBuilder signalBuilder = mock(SignalEventReceivedBuilder.class);
      when(runtimeService.createSignalEvent(anyString())).thenReturn(signalBuilder);
      when(signalBuilder.setVariables(any())).thenReturn(signalBuilder);

      processor.processEventSource(eventSource, variables);

      verify(runtimeService, never()).createSignalEvent(nonMatchingSignalName);
      verify(runtimeService).createSignalEvent(eventName);
    }
  }

  @Test
  void shouldExtractArgsFromWildcardPatternInSubscription() throws Exception {
    String receivedContent = "/command arg1";

    try (MockedStatic<PresentationMLParser> parserMock = Mockito.mockStatic(PresentationMLParser.class)) {
      parserMock.when(() -> PresentationMLParser.getTextContent(anyString())).thenReturn(receivedContent);

      V4Message message = new V4Message();
      message.setMessage("<messageML>/command arg1</messageML>");
      message.setMessageId("msg-111");

      V4MessageSent eventSource = new V4MessageSent();
      eventSource.setMessage(message);

      EventHolder<V4MessageSent> eventHolder = new EventHolder<>(null, eventSource, new HashMap<>());
      Map<String, Object> variables = new HashMap<>();
      variables.put(ActivityExecutorContext.EVENT, eventHolder);

      String eventName = WorkflowEventType.MESSAGE_RECEIVED.getEventName();
      String patternSignalName = eventName + "/command {arg}";

      MessageCorrelationBuilder messageCorrelationBuilder = mock(MessageCorrelationBuilder.class);
      when(runtimeService.createMessageCorrelation(anyString())).thenReturn(messageCorrelationBuilder);
      when(messageCorrelationBuilder.setVariables(any())).thenReturn(messageCorrelationBuilder);

      EventSubscription subscription = mock(EventSubscription.class);
      when(subscription.getEventName()).thenReturn(patternSignalName);

      EventSubscriptionQuery query = mock(EventSubscriptionQuery.class);
      when(runtimeService.createEventSubscriptionQuery()).thenReturn(query);
      when(query.eventType(anyString())).thenReturn(query);
      when(query.list()).thenReturn(List.of(subscription));

      SignalEventReceivedBuilder signalBuilder = mock(SignalEventReceivedBuilder.class);
      when(runtimeService.createSignalEvent(anyString())).thenReturn(signalBuilder);
      when(signalBuilder.setVariables(any())).thenReturn(signalBuilder);

      processor.processEventSource(eventSource, variables);

      verify(runtimeService).createSignalEvent(patternSignalName);
      assertThat(eventHolder.getArgs()).containsKey(RealTimeEventProcessor.EVENT_NAME_KEY);
    }
  }
}
