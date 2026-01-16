package com.symphony.bdk.workflow.logs;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogsStreamingAppenderClaudeTest {

  @Mock
  private LogsStreamingService service;

  @Mock
  private ILoggingEvent loggingEvent;

  private LogsStreamingAppender appender;

  @BeforeEach
  void setUp() {
    appender = new LogsStreamingAppender(service);
  }

  // ==================== append() Tests ====================

  @Test
  void append_withSimpleMessage_shouldBroadcastFormattedMessage() {
    // Given: A simple logging event with no throwable
    long timestamp = 1234567890L;
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.TestLogger");
    when(loggingEvent.getFormattedMessage()).thenReturn("Test log message");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should broadcast the message with correct parameters
    verify(service, times(1)).broadcast(
        eq(timestamp),
        eq("INFO"),
        eq("com.example.TestLogger"),
        eq("Test log message")
    );
  }

  @Test
  void append_withThrowable_shouldIncludeStackTrace() {
    // Given: A logging event with a throwable
    long timestamp = 1234567890L;
    IThrowableProxy throwableProxy = mock(IThrowableProxy.class);

    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.ERROR);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.ErrorLogger");
    when(loggingEvent.getFormattedMessage()).thenReturn("Error occurred");
    when(loggingEvent.getThrowableProxy()).thenReturn(throwableProxy);

    when(throwableProxy.getClassName()).thenReturn("java.lang.RuntimeException");
    when(throwableProxy.getMessage()).thenReturn("Test exception");
    when(throwableProxy.getStackTraceElementProxyArray()).thenReturn(new StackTraceElementProxy[0]);
    when(throwableProxy.getCause()).thenReturn(null);
    when(throwableProxy.getSuppressed()).thenReturn(new IThrowableProxy[0]);
    when(throwableProxy.getCommonFrames()).thenReturn(0);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should broadcast message with throwable information
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
    verify(service, times(1)).broadcast(
        eq(timestamp),
        eq("ERROR"),
        eq("com.example.ErrorLogger"),
        messageCaptor.capture()
    );

    String broadcastedMessage = messageCaptor.getValue();
    assertTrue(broadcastedMessage.contains("Error occurred"));
    assertTrue(broadcastedMessage.contains("java.lang.RuntimeException"));
  }

  @Test
  void append_withNewlineInMessage_shouldReplaceWithTab() {
    // Given: A logging event with newlines in the message
    long timestamp = 1234567890L;
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.DEBUG);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn("Line 1\nLine 2\nLine 3");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should replace newlines with tabs
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
    verify(service, times(1)).broadcast(
        anyLong(),
        anyString(),
        anyString(),
        messageCaptor.capture()
    );

    String broadcastedMessage = messageCaptor.getValue();
    assertEquals("Line 1\tLine 2\tLine 3", broadcastedMessage);
    assertFalse(broadcastedMessage.contains("\n"));
  }

  @Test
  void append_withCarriageReturnInMessage_shouldReplaceWithTab() {
    // Given: A logging event with carriage returns in the message
    long timestamp = 1234567890L;
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.WARN);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn("Line 1\rLine 2\rLine 3");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should replace carriage returns with tabs
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
    verify(service, times(1)).broadcast(
        anyLong(),
        anyString(),
        anyString(),
        messageCaptor.capture()
    );

    String broadcastedMessage = messageCaptor.getValue();
    assertEquals("Line 1\tLine 2\tLine 3", broadcastedMessage);
    assertFalse(broadcastedMessage.contains("\r"));
  }

  @Test
  void append_withCRLFInMessage_shouldReplaceWithTab() {
    // Given: A logging event with CRLF (Windows line endings) in the message
    long timestamp = 1234567890L;
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn("Line 1\r\nLine 2\r\nLine 3");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should replace CRLF with single tab
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
    verify(service, times(1)).broadcast(
        anyLong(),
        anyString(),
        anyString(),
        messageCaptor.capture()
    );

    String broadcastedMessage = messageCaptor.getValue();
    assertEquals("Line 1\tLine 2\tLine 3", broadcastedMessage);
    assertFalse(broadcastedMessage.contains("\r"));
    assertFalse(broadcastedMessage.contains("\n"));
  }

  @Test
  void append_withMultipleConsecutiveNewlines_shouldReplaceWithSingleTab() {
    // Given: A logging event with multiple consecutive newlines
    long timestamp = 1234567890L;
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn("Line 1\n\n\nLine 2");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should replace multiple consecutive newlines with single tab
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
    verify(service, times(1)).broadcast(
        anyLong(),
        anyString(),
        anyString(),
        messageCaptor.capture()
    );

    String broadcastedMessage = messageCaptor.getValue();
    assertEquals("Line 1\tLine 2", broadcastedMessage);
  }

  @Test
  void append_withEmptyMessage_shouldBroadcastEmptyString() {
    // Given: A logging event with empty message
    long timestamp = 1234567890L;
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn("");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should broadcast empty string
    verify(service, times(1)).broadcast(
        eq(timestamp),
        eq("INFO"),
        eq("com.example.Logger"),
        eq("")
    );
  }

  @Test
  void append_withDifferentLogLevels_shouldPreserveLevel() {
    // Given & When & Then: Test each log level
    Level[] levels = {Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR};

    for (Level level : levels) {
      ILoggingEvent event = mock(ILoggingEvent.class);
      when(event.getTimeStamp()).thenReturn(1234567890L);
      when(event.getLevel()).thenReturn(level);
      when(event.getLoggerName()).thenReturn("com.example.Logger");
      when(event.getFormattedMessage()).thenReturn("Test message");
      when(event.getThrowableProxy()).thenReturn(null);

      appender.append(event);

      verify(service, times(1)).broadcast(
          anyLong(),
          eq(level.toString()),
          anyString(),
          anyString()
      );
    }
  }

  @Test
  void append_withLongLoggerName_shouldPreserveFullName() {
    // Given: A logging event with a very long logger name
    long timestamp = 1234567890L;
    String longLoggerName = "com.symphony.bdk.workflow.very.long.package.name.with.many.nested.packages.TestClass";

    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn(longLoggerName);
    when(loggingEvent.getFormattedMessage()).thenReturn("Test message");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should preserve the full logger name
    verify(service, times(1)).broadcast(
        eq(timestamp),
        eq("INFO"),
        eq(longLoggerName),
        eq("Test message")
    );
  }

  @Test
  void append_withSpecialCharacters_shouldPreserveCharacters() {
    // Given: A logging event with special characters
    long timestamp = 1234567890L;
    String messageWithSpecialChars = "Test: ü, ñ, €, ©, ™, 中文, 日本語";

    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn(messageWithSpecialChars);
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should preserve special characters
    verify(service, times(1)).broadcast(
        eq(timestamp),
        eq("INFO"),
        eq("com.example.Logger"),
        eq(messageWithSpecialChars)
    );
  }

  @Test
  void append_withVeryLongMessage_shouldHandleCorrectly() {
    // Given: A logging event with a very long message
    long timestamp = 1234567890L;
    StringBuilder longMessage = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      longMessage.append("This is a very long message. ");
    }

    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn(longMessage.toString());
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called
    appender.append(loggingEvent);

    // Then: Should broadcast the full message
    verify(service, times(1)).broadcast(
        eq(timestamp),
        eq("INFO"),
        eq("com.example.Logger"),
        eq(longMessage.toString())
    );
  }

  @Test
  void append_multipleInvocations_shouldBroadcastEachTime() {
    // Given: Multiple logging events
    when(loggingEvent.getTimeStamp()).thenReturn(1234567890L);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn("Test message");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called multiple times
    appender.append(loggingEvent);
    appender.append(loggingEvent);
    appender.append(loggingEvent);

    // Then: Should broadcast each time
    verify(service, times(3)).broadcast(
        anyLong(),
        anyString(),
        anyString(),
        anyString()
    );
  }

  // ==================== isRunning() Tests ====================

  @Test
  void isRunning_whenNotStarted_shouldReturnFalse() {
    // Given: Appender is not started
    // (default state)

    // When: isRunning is called
    boolean result = appender.isRunning();

    // Then: Should return false
    assertFalse(result);
  }

  @Test
  void isRunning_whenStarted_shouldReturnTrue() {
    // Given: Appender is started
    appender.start();

    // When: isRunning is called
    boolean result = appender.isRunning();

    // Then: Should return true
    assertTrue(result);
  }

  @Test
  void isRunning_whenStopped_shouldReturnFalse() {
    // Given: Appender is started then stopped
    appender.start();
    appender.stop();

    // When: isRunning is called
    boolean result = appender.isRunning();

    // Then: Should return false
    assertFalse(result);
  }

  @Test
  void isRunning_multipleStarts_shouldReturnTrue() {
    // Given: Appender is started multiple times
    appender.start();
    appender.start();
    appender.start();

    // When: isRunning is called
    boolean result = appender.isRunning();

    // Then: Should return true
    assertTrue(result);
  }

  @Test
  void isRunning_afterStartStopStart_shouldReturnTrue() {
    // Given: Appender is started, stopped, then started again
    appender.start();
    appender.stop();
    appender.start();

    // When: isRunning is called
    boolean result = appender.isRunning();

    // Then: Should return true
    assertTrue(result);
  }

  @Test
  void append_whenNotStarted_shouldStillBroadcast() {
    // Given: A logging event and appender is not started
    long timestamp = 1234567890L;
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn("com.example.Logger");
    when(loggingEvent.getFormattedMessage()).thenReturn("Test message");
    when(loggingEvent.getThrowableProxy()).thenReturn(null);

    // When: append is called without starting
    appender.append(loggingEvent);

    // Then: Should still broadcast (UnsynchronizedAppenderBase doesn't prevent this)
    verify(service, times(1)).broadcast(
        eq(timestamp),
        eq("INFO"),
        eq("com.example.Logger"),
        eq("Test message")
    );
  }
}
