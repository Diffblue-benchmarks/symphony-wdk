package com.symphony.bdk.workflow.logs;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogsStreamingAppenderTest {

  @Mock
  private LogsStreamingService logsStreamingService;

  @InjectMocks
  private LogsStreamingAppender logsStreamingAppender;

  @Mock
  private ILoggingEvent loggingEvent;

  @BeforeEach
  void setUp() {
    logsStreamingAppender.start();
  }

  @Test
  void shouldAppendSimpleMessageWithoutException() {
    // Arrange
    String formattedMessage = "Test log message";
    long timestamp = 123456789L;
    String loggerName = "com.example.TestLogger";

    when(loggingEvent.getFormattedMessage()).thenReturn(formattedMessage);
    when(loggingEvent.getThrowableProxy()).thenReturn(null);
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.INFO);
    when(loggingEvent.getLoggerName()).thenReturn(loggerName);

    // Act
    logsStreamingAppender.append(loggingEvent);

    // Assert
    verify(logsStreamingService).broadcast(
        eq(timestamp),
        eq("INFO"),
        eq(loggerName),
        eq(formattedMessage)
    );
  }

  @Test
  void shouldAppendMessageWithException() {
    // Arrange
    String formattedMessage = "Error occurred";
    long timestamp = 987654321L;
    String loggerName = "com.example.ErrorLogger";
    Exception exception = new RuntimeException("Test exception");
    IThrowableProxy throwableProxy = new ThrowableProxy(exception);

    when(loggingEvent.getFormattedMessage()).thenReturn(formattedMessage);
    when(loggingEvent.getThrowableProxy()).thenReturn(throwableProxy);
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.ERROR);
    when(loggingEvent.getLoggerName()).thenReturn(loggerName);

    // Act
    logsStreamingAppender.append(loggingEvent);

    // Assert
    verify(logsStreamingService).broadcast(
        eq(timestamp),
        eq("ERROR"),
        eq(loggerName),
        anyString()
    );
  }

  @Test
  void shouldReplaceNewlinesWithTabs() {
    // Arrange
    String messageWithNewlines = "Line 1\nLine 2\r\nLine 3\rLine 4";
    long timestamp = 111111111L;
    String loggerName = "com.example.MultilineLogger";

    when(loggingEvent.getFormattedMessage()).thenReturn(messageWithNewlines);
    when(loggingEvent.getThrowableProxy()).thenReturn(null);
    when(loggingEvent.getTimeStamp()).thenReturn(timestamp);
    when(loggingEvent.getLevel()).thenReturn(Level.WARN);
    when(loggingEvent.getLoggerName()).thenReturn(loggerName);

    // Act
    logsStreamingAppender.append(loggingEvent);

    // Assert
    verify(logsStreamingService).broadcast(
        eq(timestamp),
        eq("WARN"),
        eq(loggerName),
        eq("Line 1\tLine 2\tLine 3\tLine 4")
    );
  }

  @Test
  void shouldReturnTrueWhenRunning() {
    // Arrange
    logsStreamingAppender.start();

    // Act
    boolean result = logsStreamingAppender.isRunning();

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNotRunning() {
    // Arrange
    logsStreamingAppender.stop();

    // Act
    boolean result = logsStreamingAppender.isRunning();

    // Assert
    assertThat(result).isFalse();
  }
}
