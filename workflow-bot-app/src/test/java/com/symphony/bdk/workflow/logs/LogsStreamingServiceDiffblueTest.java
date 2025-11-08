package com.symphony.bdk.workflow.logs;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@ContextConfiguration(classes = {LogsStreamingService.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LogsStreamingServiceDiffblueTest {
  @Autowired
  private LogsStreamingService logsStreamingService;

  /**
   * Method under test: {@link LogsStreamingService#subscribe(SseEmitter)}
   */
  @Test
  void testSubscribe() {
    // Arrange
    SseEmitter emitter = mock(SseEmitter.class);
    doNothing().when(emitter).onCompletion(Mockito.<Runnable>any());
    doNothing().when(emitter).onTimeout(Mockito.<Runnable>any());

    // Act
    logsStreamingService.subscribe(emitter);

    // Assert
    verify(emitter).onCompletion(isA(Runnable.class));
    verify(emitter).onTimeout(isA(Runnable.class));
  }
}
