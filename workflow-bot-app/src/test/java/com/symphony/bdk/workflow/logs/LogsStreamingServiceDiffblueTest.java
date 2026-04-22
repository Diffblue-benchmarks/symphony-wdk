package com.symphony.bdk.workflow.logs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Instant;

class LogsStreamingServiceDiffblueTest {

  /**
   * Test {@link LogsStreamingService#subscribe(SseEmitter)}.
   *
   * <p>Method under test: {@link LogsStreamingService#subscribe(SseEmitter)}
   */
  @Test
  @DisplayName("Test subscribe(SseEmitter); registers callbacks on emitter")
  void testSubscribe_registersCallbacks() {
    // Arrange
    LogsStreamingService service = new LogsStreamingService();
    SseEmitter emitter = mock(SseEmitter.class);

    // Act
    service.subscribe(emitter);

    // Assert
    verify(emitter).onCompletion(any(Runnable.class));
    verify(emitter).onTimeout(any(Runnable.class));
  }

  /**
   * Test {@link LogsStreamingService#subscribe(SseEmitter)} onCompletion callback removes emitter.
   *
   * <p>Method under test: {@link LogsStreamingService#subscribe(SseEmitter)}
   */
  @Test
  @DisplayName("Test subscribe(SseEmitter); onCompletion callback removes emitter from list")
  void testSubscribe_onCompletion_removesEmitter() throws IOException {
    // Arrange
    LogsStreamingService service = new LogsStreamingService();
    SseEmitter emitter = mock(SseEmitter.class);
    ArgumentCaptor<Runnable> completionCaptor = ArgumentCaptor.forClass(Runnable.class);
    service.subscribe(emitter);
    verify(emitter).onCompletion(completionCaptor.capture());

    // Act - trigger completion callback
    completionCaptor.getValue().run();

    // Assert - after removal, broadcast should not send to emitter
    service.broadcast(Instant.now().toEpochMilli(), "INFO", "logger", "msg");
    verify(emitter, times(0)).send(any(SseEmitter.SseEventBuilder.class));
  }

  /**
   * Test {@link LogsStreamingService#subscribe(SseEmitter)} onTimeout callback removes emitter.
   *
   * <p>Method under test: {@link LogsStreamingService#subscribe(SseEmitter)}
   */
  @Test
  @DisplayName("Test subscribe(SseEmitter); onTimeout callback completes and removes emitter")
  void testSubscribe_onTimeout_completesAndRemovesEmitter() throws IOException {
    // Arrange
    LogsStreamingService service = new LogsStreamingService();
    SseEmitter emitter = mock(SseEmitter.class);
    ArgumentCaptor<Runnable> timeoutCaptor = ArgumentCaptor.forClass(Runnable.class);
    service.subscribe(emitter);
    verify(emitter).onTimeout(timeoutCaptor.capture());

    // Act - trigger timeout callback
    timeoutCaptor.getValue().run();

    // Assert - complete() was called and emitter removed from list
    verify(emitter).complete();
    service.broadcast(Instant.now().toEpochMilli(), "INFO", "logger", "msg");
    verify(emitter, times(0)).send(any(SseEmitter.SseEventBuilder.class));
  }

  /**
   * Test {@link LogsStreamingService#broadcast(long, String, String, String)} with no emitters.
   *
   * <p>Method under test: {@link LogsStreamingService#broadcast(long, String, String, String)}
   */
  @Test
  @DisplayName("Test broadcast(); with no emitters does not throw")
  void testBroadcast_noEmitters() {
    // Arrange
    LogsStreamingService service = new LogsStreamingService();

    // Act and Assert
    assertDoesNotThrow(() -> service.broadcast(1000L, "INFO", "TestLogger", "test message"));
  }

  /**
   * Test {@link LogsStreamingService#broadcast(long, String, String, String)} with successful send.
   *
   * <p>Method under test: {@link LogsStreamingService#broadcast(long, String, String, String)}
   */
  @Test
  @DisplayName("Test broadcast(); with subscribed emitter sends event successfully")
  void testBroadcast_withEmitter_sendsSuccessfully() throws IOException {
    // Arrange
    LogsStreamingService service = new LogsStreamingService();
    SseEmitter emitter = mock(SseEmitter.class);
    doNothing().when(emitter).send(any(SseEmitter.SseEventBuilder.class));
    service.subscribe(emitter);

    // Act
    service.broadcast(Instant.now().toEpochMilli(), "DEBUG", "com.example.Logger", "log data");

    // Assert
    verify(emitter).send(any(SseEmitter.SseEventBuilder.class));
  }

  /**
   * Test {@link LogsStreamingService#broadcast(long, String, String, String)} when send fails.
   *
   * <p>Method under test: {@link LogsStreamingService#broadcast(long, String, String, String)}
   */
  @Test
  @DisplayName("Test broadcast(); when send throws, emitter is completed with error and unsubscribed")
  void testBroadcast_withEmitter_sendFails_unsubscribes() throws IOException {
    // Arrange
    LogsStreamingService service = new LogsStreamingService();
    SseEmitter emitter = mock(SseEmitter.class);
    IOException ioException = new IOException("connection closed");
    doThrow(ioException).when(emitter).send(any(SseEmitter.SseEventBuilder.class));
    service.subscribe(emitter);

    // Act
    service.broadcast(Instant.now().toEpochMilli(), "ERROR", "com.example.Logger", "error data");

    // Assert - emitter was completed with error
    verify(emitter).send(any(SseEmitter.SseEventBuilder.class));
    verify(emitter).completeWithError(any(IOException.class));
  }

  /**
   * Test {@link LogsStreamingService#broadcast(long, String, String, String)} verifies unsubscribe removes failed emitter.
   *
   * <p>Method under test: {@link LogsStreamingService#broadcast(long, String, String, String)}
   */
  @Test
  @DisplayName("Test broadcast(); failed emitter is removed and not called on subsequent broadcast")
  void testBroadcast_failedEmitterRemovedOnSubsequentCall() throws IOException {
    // Arrange
    LogsStreamingService service = new LogsStreamingService();
    SseEmitter emitter = mock(SseEmitter.class);
    doThrow(new IOException("closed")).when(emitter).send(any(SseEmitter.SseEventBuilder.class));
    service.subscribe(emitter);

    // Act - first broadcast causes failure and removal
    service.broadcast(Instant.now().toEpochMilli(), "ERROR", "logger", "msg1");
    // Act - second broadcast should not call the failed emitter
    service.broadcast(Instant.now().toEpochMilli(), "ERROR", "logger", "msg2");

    // Assert - send called only once (first broadcast)
    verify(emitter, times(1)).send(any(SseEmitter.SseEventBuilder.class));
  }
}
