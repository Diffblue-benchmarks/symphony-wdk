package com.symphony.bdk.workflow.logs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LogsStreamingAppender.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class LogsStreamingAppenderDiffblueTest {
  @Autowired private LogsStreamingAppender logsStreamingAppender;

  @MockBean private LogsStreamingService logsStreamingService;

  /**
   * Test {@link LogsStreamingAppender#isRunning()}.
   *
   * <ul>
   *   <li>Given {@link LogsStreamingService}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LogsStreamingAppender#isRunning()}
   */
  @Test
  @DisplayName("Test isRunning(); given LogsStreamingService; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LogsStreamingAppender.isRunning()"})
  void testIsRunning_givenLogsStreamingService_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(logsStreamingAppender.isRunning());
  }

  /**
   * Test {@link LogsStreamingAppender#isRunning()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LogsStreamingAppender#isRunning()}
   */
  @Test
  @DisplayName("Test isRunning(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LogsStreamingAppender.isRunning()"})
  void testIsRunning_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new LogsStreamingAppender(new LogsStreamingService()).isRunning());
  }

  /**
   * Test {@link LogsStreamingAppender#append(ILoggingEvent)}.
   *
   * <ul>
   *   <li>Given event with no throwable proxy.
   *   <li>Then service broadcast is called with formatted message.
   * </ul>
   *
   * <p>Method under test: {@link LogsStreamingAppender#append(ILoggingEvent)}
   */
  @Test
  @DisplayName("Test append(ILoggingEvent); given event with no throwable; then broadcast is called")
  void testAppend_givenEventWithNoThrowable_thenBroadcastIsCalled() {
    // Arrange
    ILoggingEvent event = mock(ILoggingEvent.class);
    when(event.getFormattedMessage()).thenReturn("test message");
    when(event.getThrowableProxy()).thenReturn(null);
    when(event.getTimeStamp()).thenReturn(1000L);
    when(event.getLevel()).thenReturn(Level.INFO);
    when(event.getLoggerName()).thenReturn("com.example.Logger");

    // Act
    logsStreamingAppender.doAppend(event);

    // Assert
    verify(logsStreamingService).broadcast(1000L, "INFO", "com.example.Logger", "test message");
  }

  /**
   * Test {@link LogsStreamingAppender#append(ILoggingEvent)}.
   *
   * <ul>
   *   <li>Given event with throwable proxy.
   *   <li>Then service broadcast is called and includes throwable info.
   * </ul>
   *
   * <p>Method under test: {@link LogsStreamingAppender#append(ILoggingEvent)}
   */
  @Test
  @DisplayName("Test append(ILoggingEvent); given event with throwable; then broadcast includes throwable")
  void testAppend_givenEventWithThrowable_thenBroadcastIncludesThrowable() {
    // Arrange
    ILoggingEvent event = mock(ILoggingEvent.class);
    ThrowableProxy throwableProxy = new ThrowableProxy(new RuntimeException("test error"));
    when(event.getFormattedMessage()).thenReturn("test message");
    when(event.getThrowableProxy()).thenReturn(throwableProxy);
    when(event.getTimeStamp()).thenReturn(2000L);
    when(event.getLevel()).thenReturn(Level.ERROR);
    when(event.getLoggerName()).thenReturn("com.example.Logger");

    // Act
    logsStreamingAppender.doAppend(event);

    // Assert
    verify(logsStreamingService).broadcast(eq(2000L), eq("ERROR"), eq("com.example.Logger"), anyString());
  }

  /**
   * Test {@link LogsStreamingAppender#append(ILoggingEvent)}.
   *
   * <ul>
   *   <li>Given event with multiline message.
   *   <li>Then newlines in broadcast message are replaced with tabs.
   * </ul>
   *
   * <p>Method under test: {@link LogsStreamingAppender#append(ILoggingEvent)}
   */
  @Test
  @DisplayName("Test append(ILoggingEvent); given multiline message; then newlines replaced with tabs")
  void testAppend_givenMultilineMessage_thenNewlinesReplacedWithTabs() {
    // Arrange
    ILoggingEvent event = mock(ILoggingEvent.class);
    when(event.getFormattedMessage()).thenReturn("line1\nline2\r\nline3");
    when(event.getThrowableProxy()).thenReturn(null);
    when(event.getTimeStamp()).thenReturn(3000L);
    when(event.getLevel()).thenReturn(Level.WARN);
    when(event.getLoggerName()).thenReturn("com.example.Logger");

    // Act
    logsStreamingAppender.doAppend(event);

    // Assert
    verify(logsStreamingService).broadcast(3000L, "WARN", "com.example.Logger", "line1\tline2\tline3");
  }
}
