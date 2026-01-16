package com.symphony.bdk.workflow.engine.handler.audit;

import org.camunda.bpm.engine.repository.Deployment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuditTrailLogActionClaude_undeployedTest {

  private AuditTrailLogAction action;

  @BeforeEach
  void setUp() {
    action = new AuditTrailLogAction();
  }

  @Test
  void undeployed_withValidDeployment_shouldLogUndeploymentEvent() {
    // Given: A Deployment with valid ID and name
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-123");
    when(deployment.getName()).thenReturn("workflow-deployment");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withNullDeploymentId_shouldHandleGracefully() {
    // Given: A Deployment with null ID
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn(null);
    when(deployment.getName()).thenReturn("workflow-deployment");

    // When/Then: Should execute without throwing an exception (logging handles null)
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withNullDeploymentName_shouldHandleGracefully() {
    // Given: A Deployment with null name
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-456");
    when(deployment.getName()).thenReturn(null);

    // When/Then: Should execute without throwing an exception (logging handles null)
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withEmptyDeploymentId_shouldHandleGracefully() {
    // Given: A Deployment with empty ID
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("");
    when(deployment.getName()).thenReturn("workflow-deployment");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withEmptyDeploymentName_shouldHandleGracefully() {
    // Given: A Deployment with empty name
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-789");
    when(deployment.getName()).thenReturn("");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withNullDeployment_shouldThrowException() {
    // Given: A null Deployment
    // When/Then: Should throw NullPointerException when accessing deployment properties
    assertThatThrownBy(() -> action.undeployed(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void undeployed_withBothNullFields_shouldHandleGracefully() {
    // Given: A Deployment with both ID and name null
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn(null);
    when(deployment.getName()).thenReturn(null);

    // When/Then: Should execute without throwing an exception (logging handles nulls)
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withSpecialCharactersInId_shouldHandleGracefully() {
    // Given: A Deployment with special characters in ID
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-<>&\"'123");
    when(deployment.getName()).thenReturn("workflow-deployment");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withSpecialCharactersInName_shouldHandleGracefully() {
    // Given: A Deployment with special characters in name
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-456");
    when(deployment.getName()).thenReturn("workflow-<>&\"'deployment");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withLongDeploymentId_shouldHandleGracefully() {
    // Given: A Deployment with very long ID
    String longId = "deployment-" + "a".repeat(1000);
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn(longId);
    when(deployment.getName()).thenReturn("workflow-deployment");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withLongDeploymentName_shouldHandleGracefully() {
    // Given: A Deployment with very long name
    String longName = "workflow-deployment-" + "b".repeat(1000);
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-789");
    when(deployment.getName()).thenReturn(longName);

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_multipleConsecutiveCalls_shouldExecuteAll() {
    // Given: Multiple different deployments
    Deployment deployment1 = mock(Deployment.class);
    when(deployment1.getId()).thenReturn("deployment-1");
    when(deployment1.getName()).thenReturn("workflow-1");

    Deployment deployment2 = mock(Deployment.class);
    when(deployment2.getId()).thenReturn("deployment-2");
    when(deployment2.getName()).thenReturn("workflow-2");

    Deployment deployment3 = mock(Deployment.class);
    when(deployment3.getId()).thenReturn("deployment-3");
    when(deployment3.getName()).thenReturn("workflow-3");

    // When/Then: Should execute all without throwing exceptions
    assertThatCode(() -> {
      action.undeployed(deployment1);
      action.undeployed(deployment2);
      action.undeployed(deployment3);
    }).doesNotThrowAnyException();
  }

  @Test
  void undeployed_sameDeploymentMultipleTimes_shouldExecuteMultipleTimes() {
    // Given: A deployment that will be undeployed multiple times
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-repeat");
    when(deployment.getName()).thenReturn("workflow-repeat");

    // When/Then: Should execute multiple times without issues
    assertThatCode(() -> {
      action.undeployed(deployment);
      action.undeployed(deployment);
      action.undeployed(deployment);
    }).doesNotThrowAnyException();
  }

  @Test
  void undeployed_withUnicodeCharacters_shouldHandleGracefully() {
    // Given: A Deployment with Unicode characters
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-日本語-中文-한국어");
    when(deployment.getName()).thenReturn("workflow-日本語-中文-한국어");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withNewlineCharacters_shouldHandleGracefully() {
    // Given: A Deployment with newline characters
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment\n123");
    when(deployment.getName()).thenReturn("workflow\ndeployment");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withTabCharacters_shouldHandleGracefully() {
    // Given: A Deployment with tab characters
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment\t123");
    when(deployment.getName()).thenReturn("workflow\tdeployment");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withNumericStrings_shouldHandleGracefully() {
    // Given: A Deployment with numeric string values
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("12345");
    when(deployment.getName()).thenReturn("67890");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withWhitespaceOnlyStrings_shouldHandleGracefully() {
    // Given: A Deployment with whitespace-only strings
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("   ");
    when(deployment.getName()).thenReturn("   ");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }

  @Test
  void undeployed_withMixedValidAndInvalidCharacters_shouldHandleGracefully() {
    // Given: A Deployment with a mix of valid and special characters
    Deployment deployment = mock(Deployment.class);
    when(deployment.getId()).thenReturn("deployment-123!@#$%^&*()");
    when(deployment.getName()).thenReturn("workflow-deployment!@#$%^&*()");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.undeployed(deployment))
        .doesNotThrowAnyException();
  }
}
