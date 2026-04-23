package com.symphony.bdk.workflow.logs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class LogsStreamingServiceTest {

  private LogsStreamingService service;

  @BeforeEach
  void setUp() {
    service = new LogsStreamingService();
  }

  @Test
  void shouldRegisterCallbacksAndAddEmitterOnSubscribe() {
    SseEmitter emitter = mock(SseEmitter.class);

    service.subscribe(emitter);

    verify(emitter).onCompletion(any(Runnable.class));
    verify(emitter).onTimeout(any(Runnable.class));
  }

  @Test
  void shouldBroadcastEventToSubscribedEmitter() throws Exception {
    SseEmitter emitter = mock(SseEmitter.class);
    service.subscribe(emitter);

    service.broadcast(System.currentTimeMillis(), "INFO", "com.example.Logger", "test message");

    verify(emitter).send(any(SseEmitter.SseEventBuilder.class));
  }

  @Test
  void shouldBroadcastEventToMultipleEmitters() throws Exception {
    SseEmitter emitter1 = mock(SseEmitter.class);
    SseEmitter emitter2 = mock(SseEmitter.class);
    service.subscribe(emitter1);
    service.subscribe(emitter2);

    service.broadcast(System.currentTimeMillis(), "WARN", "com.example.Logger", "test");

    verify(emitter1).send(any(SseEmitter.SseEventBuilder.class));
    verify(emitter2).send(any(SseEmitter.SseEventBuilder.class));
  }

  @Test
  void shouldRemoveFailedEmitterOnBroadcast() throws Exception {
    SseEmitter emitter = mock(SseEmitter.class);
    doThrow(new IOException("connection closed")).when(emitter).send(any(SseEmitter.SseEventBuilder.class));
    service.subscribe(emitter);

    service.broadcast(System.currentTimeMillis(), "ERROR", "com.example.Logger", "test message");

    verify(emitter).completeWithError(any(IOException.class));

    // After the failed emitter is unsubscribed, a subsequent broadcast should not reach it
    service.broadcast(System.currentTimeMillis(), "INFO", "com.example.Logger", "another message");
    verify(emitter, times(1)).send(any(SseEmitter.SseEventBuilder.class));
  }

  @Test
  void shouldCompleteEmitterAndRemoveItOnTimeout() throws Exception {
    SseEmitter emitter = mock(SseEmitter.class);
    service.subscribe(emitter);

    ArgumentCaptor<Runnable> timeoutCaptor = ArgumentCaptor.forClass(Runnable.class);
    verify(emitter).onTimeout(timeoutCaptor.capture());
    timeoutCaptor.getValue().run();

    verify(emitter).complete();

    // After timeout, broadcast should not reach the removed emitter
    service.broadcast(System.currentTimeMillis(), "INFO", "logger", "data");
    verify(emitter, never()).send(any(SseEmitter.SseEventBuilder.class));
  }

  @Test
  void shouldRemoveEmitterOnCompletionCallback() throws Exception {
    SseEmitter emitter = mock(SseEmitter.class);
    service.subscribe(emitter);

    ArgumentCaptor<Runnable> completionCaptor = ArgumentCaptor.forClass(Runnable.class);
    verify(emitter).onCompletion(completionCaptor.capture());
    completionCaptor.getValue().run();

    service.broadcast(System.currentTimeMillis(), "INFO", "logger", "data");
    verify(emitter, never()).send(any(SseEmitter.SseEventBuilder.class));
  }
}
