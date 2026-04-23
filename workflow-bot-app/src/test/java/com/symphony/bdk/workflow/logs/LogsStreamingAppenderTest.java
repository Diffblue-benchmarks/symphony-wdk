package com.symphony.bdk.workflow.logs;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogsStreamingAppenderTest {

  @Mock
  private LogsStreamingService service;

  @InjectMocks
  private LogsStreamingAppender appender;

  @Test
  void shouldAppendEventWithoutThrowable() {
    ILoggingEvent event = org.mockito.Mockito.mock(ILoggingEvent.class);
    when(event.getFormattedMessage()).thenReturn("test message");
    when(event.getThrowableProxy()).thenReturn(null);
    when(event.getTimeStamp()).thenReturn(1000L);
    when(event.getLevel()).thenReturn(Level.INFO);
    when(event.getLoggerName()).thenReturn("com.example.Logger");

    appender.append(event);

    verify(service).broadcast(1000L, "INFO", "com.example.Logger", "test message");
  }

  @Test
  void shouldAppendEventWithNewlinesReplaced() {
    ILoggingEvent event = org.mockito.Mockito.mock(ILoggingEvent.class);
    when(event.getFormattedMessage()).thenReturn("line1\nline2\r\nline3");
    when(event.getThrowableProxy()).thenReturn(null);
    when(event.getTimeStamp()).thenReturn(2000L);
    when(event.getLevel()).thenReturn(Level.WARN);
    when(event.getLoggerName()).thenReturn("com.example.Logger");

    appender.append(event);

    verify(service).broadcast(2000L, "WARN", "com.example.Logger", "line1\tline2\tline3");
  }

  @Test
  void shouldAppendEventWithThrowable() {
    ILoggingEvent event = org.mockito.Mockito.mock(ILoggingEvent.class);
    IThrowableProxy throwableProxy = org.mockito.Mockito.mock(IThrowableProxy.class);
    when(throwableProxy.getClassName()).thenReturn("java.lang.RuntimeException");
    when(throwableProxy.getMessage()).thenReturn("error");
    when(throwableProxy.getStackTraceElementProxyArray()).thenReturn(
        new ch.qos.logback.classic.spi.StackTraceElementProxy[0]);
    when(throwableProxy.getCause()).thenReturn(null);
    when(throwableProxy.getSuppressed()).thenReturn(new IThrowableProxy[0]);

    when(event.getFormattedMessage()).thenReturn("error occurred");
    when(event.getThrowableProxy()).thenReturn(throwableProxy);
    when(event.getTimeStamp()).thenReturn(3000L);
    when(event.getLevel()).thenReturn(Level.ERROR);
    when(event.getLoggerName()).thenReturn("com.example.Logger");

    appender.append(event);

    verify(service).broadcast(
        org.mockito.ArgumentMatchers.eq(3000L),
        org.mockito.ArgumentMatchers.eq("ERROR"),
        org.mockito.ArgumentMatchers.eq("com.example.Logger"),
        org.mockito.ArgumentMatchers.contains("error occurred"));
  }

  @Test
  void shouldReturnFalseWhenNotStarted() {
    assertThat(appender.isRunning()).isFalse();
  }

  @Test
  void shouldReturnTrueWhenStarted() {
    appender.start();

    assertThat(appender.isRunning()).isTrue();
  }
}
