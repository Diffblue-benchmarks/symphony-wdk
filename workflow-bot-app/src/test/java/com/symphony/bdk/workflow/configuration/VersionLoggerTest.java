package com.symphony.bdk.workflow.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class VersionLoggerTest {

  private VersionLogger versionLogger;
  private ListAppender<ILoggingEvent> listAppender;
  private Logger logger;

  @BeforeEach
  void setUp() {
    versionLogger = new VersionLogger();

    // Set up log appender to capture log events
    logger = (Logger) LoggerFactory.getLogger(VersionLogger.class);
    listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);
  }

  @AfterEach
  void tearDown() {
    logger.detachAppender(listAppender);
  }

  @Test
  void shouldLogVersionWhenVersionIsNotBlank() throws Exception {
    // Arrange
    setVersionField("1.2.3");

    // Act
    invokeLogWdkVersion();

    // Assert
    assertThat(listAppender.list).hasSize(1);
    ILoggingEvent logEvent = listAppender.list.get(0);
    assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
    assertThat(logEvent.getFormattedMessage()).isEqualTo("Running with WDK version: 1.2.3");
  }

  @Test
  void shouldNotLogVersionWhenVersionIsBlank() throws Exception {
    // Arrange
    setVersionField("");

    // Act
    invokeLogWdkVersion();

    // Assert
    assertThat(listAppender.list).isEmpty();
  }

  @Test
  void shouldNotLogVersionWhenVersionIsNull() throws Exception {
    // Arrange
    setVersionField(null);

    // Act
    invokeLogWdkVersion();

    // Assert
    assertThat(listAppender.list).isEmpty();
  }

  @Test
  void shouldNotLogVersionWhenVersionIsWhitespace() throws Exception {
    // Arrange
    setVersionField("   ");

    // Act
    invokeLogWdkVersion();

    // Assert
    assertThat(listAppender.list).isEmpty();
  }

  private void setVersionField(String value) throws Exception {
    Field versionField = VersionLogger.class.getDeclaredField("version");
    versionField.setAccessible(true);
    versionField.set(versionLogger, value);
  }

  private void invokeLogWdkVersion() throws Exception {
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);
    logWdkVersionMethod.invoke(versionLogger);
  }
}
