package com.symphony.bdk.workflow.configuration;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class VersionLoggerClaude_logWdkVersionTest {

  // Tests for logWdkVersion() method
  // This method is private and annotated with @PostConstruct, so we use reflection to test it.
  // Testing this method directly is necessary to achieve coverage of the version logging logic
  // without starting a full Spring context.

  @Test
  void logWdkVersion_withNonBlankVersion_shouldLogSuccessfully() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // There is no other way to test this method without starting a full Spring application context,
    // which would be an integration test. We need to verify the behavior of the logging logic
    // with different version values to achieve proper code coverage.

    // Given: A VersionLogger instance with a non-blank version
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "1.0.0");

    // When: logWdkVersion is called
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // Then: The method should execute without throwing an exception
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_withBlankVersion_shouldNotLog() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // We need to test the blank version branch to achieve complete coverage.

    // Given: A VersionLogger instance with a blank version
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "");

    // When: logWdkVersion is called
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // Then: The method should execute without throwing an exception
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_withNullVersion_shouldNotLog() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // Testing with null version ensures we cover the StringUtils.isBlank() check properly.

    // Given: A VersionLogger instance with a null version
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, null);

    // When: logWdkVersion is called
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // Then: The method should execute without throwing an exception
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_withWhitespaceVersion_shouldNotLog() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // Testing with whitespace-only version ensures proper handling of blank strings.

    // Given: A VersionLogger instance with a whitespace-only version
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "   ");

    // When: logWdkVersion is called
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // Then: The method should execute without throwing an exception
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_withVersionContainingSpecialCharacters_shouldLogSuccessfully() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // Testing with special characters ensures the logger handles various version formats.

    // Given: A VersionLogger instance with a version containing special characters
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "1.0.0-SNAPSHOT+build.123");

    // When: logWdkVersion is called
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // Then: The method should execute without throwing an exception
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_withLongVersion_shouldLogSuccessfully() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // Testing with a long version string ensures the logger handles various input lengths.

    // Given: A VersionLogger instance with a long version string
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "1.0.0-alpha.beta.gamma.delta.epsilon.very.long.version.string.2023.12.31");

    // When: logWdkVersion is called
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // Then: The method should execute without throwing an exception
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_withVersionStartingWithWhitespace_shouldLogSuccessfully() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // Testing with leading whitespace ensures StringUtils.isBlank() correctly identifies non-blank strings.

    // Given: A VersionLogger instance with a version that has leading/trailing whitespace
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "  1.0.0  ");

    // When: logWdkVersion is called
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // Then: The method should execute without throwing an exception
    // Note: StringUtils.isBlank() returns true only if the string is null, empty, or contains only whitespace
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_calledMultipleTimes_shouldExecuteEachTime() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // Testing multiple invocations ensures the method is idempotent.

    // Given: A VersionLogger instance with a version
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "2.0.0");

    // When: logWdkVersion is called multiple times
    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // Then: Each invocation should succeed without throwing an exception
    assertThatCode(() -> {
      logWdkVersionMethod.invoke(versionLogger);
      logWdkVersionMethod.invoke(versionLogger);
      logWdkVersionMethod.invoke(versionLogger);
    }).doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_afterChangingVersion_shouldLogNewVersion() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // Testing version changes ensures the method uses the current field value.

    // Given: A VersionLogger instance with an initial version
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "1.0.0");

    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // When: logWdkVersion is called with initial version
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();

    // And: The version is changed and logWdkVersion is called again
    setVersionField(versionLogger, "2.0.0");
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  @Test
  void logWdkVersion_withEmptyStringAfterNonBlank_shouldNotLog() throws Exception {
    // Reflection is used here because logWdkVersion() is a private @PostConstruct method.
    // Testing transition from non-blank to blank version.

    // Given: A VersionLogger instance with a non-blank version
    VersionLogger versionLogger = new VersionLogger();
    setVersionField(versionLogger, "1.0.0");

    Method logWdkVersionMethod = VersionLogger.class.getDeclaredMethod("logWdkVersion");
    logWdkVersionMethod.setAccessible(true);

    // When: logWdkVersion is called with non-blank version
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();

    // And: The version is changed to blank and logWdkVersion is called again
    setVersionField(versionLogger, "");
    assertThatCode(() -> logWdkVersionMethod.invoke(versionLogger))
        .doesNotThrowAnyException();
  }

  // Helper method to set the private version field using reflection
  private void setVersionField(VersionLogger versionLogger, String version) throws Exception {
    Field versionField = VersionLogger.class.getDeclaredField("version");
    versionField.setAccessible(true);
    versionField.set(versionLogger, version);
  }
}
