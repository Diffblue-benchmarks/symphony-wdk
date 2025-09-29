package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowBotConfiguration.class})
@ExtendWith(SpringExtension.class)
class WorkflowBotConfigurationDiffblueTest {
  @Autowired private WorkflowBotConfiguration workflowBotConfiguration;

  /**
   * Test {@link WorkflowBotConfiguration#workflowResourcesProvider()}.
   *
   * <ul>
   *   <li>Given {@link WorkflowBotConfiguration}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowBotConfiguration#workflowResourcesProvider()}
   */
  @Test
  @DisplayName("Test workflowResourcesProvider(); given WorkflowBotConfiguration")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.engine.ResourceProvider WorkflowBotConfiguration.workflowResourcesProvider()"
  })
  void testWorkflowResourcesProvider_givenWorkflowBotConfiguration() {
    // Arrange, Act and Assert
    assertTrue(
        workflowBotConfiguration.workflowResourcesProvider() instanceof WorkflowResourcesProvider);
  }

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
  void testWorkflowResourcesProvider_givenWorkflowBotConfiguration2() {
    // Arrange, Act and Assert
    assertTrue(
        new WorkflowBotConfiguration().workflowResourcesProvider()
            instanceof WorkflowResourcesProvider);
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
