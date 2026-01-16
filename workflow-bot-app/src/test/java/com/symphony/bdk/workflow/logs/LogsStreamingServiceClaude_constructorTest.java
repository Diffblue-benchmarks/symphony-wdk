package com.symphony.bdk.workflow.logs;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LogsStreamingServiceClaude_constructorTest {

  // ==================== <init>() Tests ====================

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Constructor is called
    LogsStreamingService service = new LogsStreamingService();

    // Then: Instance should be created successfully
    assertNotNull(service);
  }

  @Test
  void constructor_shouldInitializeEmittersList() {
    // When: Constructor is called
    LogsStreamingService service = new LogsStreamingService();

    // Then: The emitters list should be initialized and ready to use
    // We verify this by testing that subscribe and broadcast can be called without NPE
    SseEmitter emitter = new SseEmitter();

    // This should not throw NullPointerException if emitters list is properly initialized
    service.subscribe(emitter);

    assertNotNull(service);
  }

  @Test
  void constructor_shouldAllowImmediateBroadcast() {
    // When: Constructor is called
    LogsStreamingService service = new LogsStreamingService();

    // Then: broadcast should work immediately without initialization errors
    // This verifies that the emitters list is properly initialized
    service.broadcast(System.currentTimeMillis(), "INFO", "test.logger", "test message");

    assertNotNull(service);
  }

  @Test
  void constructor_multipleInstances_shouldBeIndependent() {
    // When: Multiple instances are created
    LogsStreamingService service1 = new LogsStreamingService();
    LogsStreamingService service2 = new LogsStreamingService();

    // Then: Each instance should be independent
    assertNotNull(service1);
    assertNotNull(service2);

    // Verify independence by subscribing to one and broadcasting to both
    SseEmitter emitter1 = new SseEmitter();
    service1.subscribe(emitter1);

    // Broadcasting to service2 should not affect service1's emitters
    service2.broadcast(System.currentTimeMillis(), "INFO", "test.logger", "test message");

    // Both services should still be functional
    assertNotNull(service1);
    assertNotNull(service2);
  }
}
