package com.symphony.bdk.workflow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.assertThat;

class WorkflowBotApplicationClaude_mainTest {
  private String originalNashornProperty;

  @BeforeEach
  void setUp() {
    // Save the original property value
    originalNashornProperty = System.getProperty("nashorn.args");
  }

  @AfterEach
  void tearDown() {
    // Restore the original property value
    if (originalNashornProperty != null) {
      System.setProperty("nashorn.args", originalNashornProperty);
    } else {
      System.clearProperty("nashorn.args");
    }
  }

  @Test
  void testMainMethodExists() {
    // Verify that the main method exists and has the correct signature
    // This ensures the class can be used as a Spring Boot entry point
    try {
      WorkflowBotApplication.class.getDeclaredMethod("main", String[].class);
    } catch (NoSuchMethodException e) {
      throw new AssertionError("main method should exist", e);
    }
  }

  @Test
  void testClassHasSpringBootAnnotations() {
    // Verify that WorkflowBotApplication has the necessary Spring Boot annotations
    assertThat(WorkflowBotApplication.class.isAnnotationPresent(SpringBootApplication.class))
        .isTrue();
    assertThat(WorkflowBotApplication.class.isAnnotationPresent(EnableScheduling.class)).isTrue();
    assertThat(WorkflowBotApplication.class.isAnnotationPresent(EnableCaching.class)).isTrue();
    assertThat(WorkflowBotApplication.class.isAnnotationPresent(EnableAsync.class)).isTrue();
    assertThat(WorkflowBotApplication.class.isAnnotationPresent(EnableTransactionManagement.class))
        .isTrue();
  }

  @Test
  void testDisableNashornDeprecationWarning() throws Exception {
    // Reflection is used here because the disableNashornDeprecationWarning() method is private.
    // There is no other way to test this specific functionality of the main method without
    // starting the entire Spring Boot application, which would be an integration test rather
    // than a unit test. This method has a side effect (setting a system property) that we can
    // verify without needing the full application context.

    // Clear the property first
    System.clearProperty("nashorn.args");
    assertThat(System.getProperty("nashorn.args")).isNull();

    // Get the private method using reflection
    Method disableNashornMethod =
        WorkflowBotApplication.class.getDeclaredMethod("disableNashornDeprecationWarning");
    disableNashornMethod.setAccessible(true);

    // Invoke the method
    disableNashornMethod.invoke(null);

    // Verify the system property is set correctly
    assertThat(System.getProperty("nashorn.args")).isEqualTo("--no-deprecation-warning");
  }
}
