package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.ext.group.SymphonyGroupBdkExtension;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class WorkflowBotConfigurationDiffblueTest {
  /**
   * Test {@link WorkflowBotConfiguration#workflowResourcesProvider()}.
   *
   * <ul>
   *   <li>Given {@link WorkflowBotConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WorkflowBotConfiguration#workflowResourcesProvider()}
   */
  @Test
  @DisplayName(
      "Test workflowResourcesProvider(); given WorkflowBotConfiguration (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.engine.ResourceProvider WorkflowBotConfiguration.workflowResourcesProvider()"
  })
  void testWorkflowResourcesProvider_givenWorkflowBotConfiguration() {
    // Arrange, Act and Assert
    assertTrue(
        new WorkflowBotConfiguration().workflowResourcesProvider()
            instanceof WorkflowResourcesProvider);
  }

  /**
   * Test {@link WorkflowBotConfiguration#groupExtension()}.
   *
   * <ul>
   *   <li>Given {@link WorkflowBotConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link WorkflowBotConfiguration#groupExtension()}
   */
  @Test
  @DisplayName("Test groupExtension(); given WorkflowBotConfiguration (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.ext.group.SymphonyGroupBdkExtension WorkflowBotConfiguration.groupExtension()"
  })
  void testGroupExtension_givenWorkflowBotConfiguration() {
    // Arrange, Act and Assert
    assertNotNull(new WorkflowBotConfiguration().groupExtension());
    assertInstanceOf(SymphonyGroupBdkExtension.class,
        new WorkflowBotConfiguration().groupExtension());
  }

  /**
   * Test {@link WorkflowBotConfiguration#cryptJpaVault(String)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowBotConfiguration#cryptJpaVault(String)}
   */
  @Test
  @DisplayName("Test cryptJpaVault(String); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.engine.secret.SecretCryptVault WorkflowBotConfiguration.cryptJpaVault(String)"
  })
  void testCryptJpaVault_thenDoesNotThrow()
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    // Arrange, Act and Assert
    assertDoesNotThrow(
        () ->
            new WorkflowBotConfiguration()
                .cryptJpaVault(
                    "The encryption passphrase length must have at least 16 characters."));
  }

  /**
   * Test {@link WorkflowBotConfiguration#cryptJpaVault(String)}.
   *
   * <ul>
   *   <li>When {@code Pwd}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowBotConfiguration#cryptJpaVault(String)}
   */
  @Test
  @DisplayName("Test cryptJpaVault(String); when 'Pwd'; then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.engine.secret.SecretCryptVault WorkflowBotConfiguration.cryptJpaVault(String)"
  })
  void testCryptJpaVault_whenPwd_thenThrowIllegalArgumentException()
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new WorkflowBotConfiguration().cryptJpaVault("Pwd"));
  }
}
