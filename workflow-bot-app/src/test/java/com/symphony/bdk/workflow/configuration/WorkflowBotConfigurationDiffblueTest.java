package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowBotConfiguration.class})
@ExtendWith(SpringExtension.class)
class WorkflowBotConfigurationDiffblueTest {
  @Autowired
  private WorkflowBotConfiguration workflowBotConfiguration;

  /**
   * Test {@link WorkflowBotConfiguration#workflowResourcesProvider()}.
   * <p>
   * Method under test:
   * {@link WorkflowBotConfiguration#workflowResourcesProvider()}
   */
  @Test
  @DisplayName("Test workflowResourcesProvider()")
  void testWorkflowResourcesProvider() {
    // Arrange, Act and Assert
    assertTrue(workflowBotConfiguration.workflowResourcesProvider() instanceof WorkflowResourcesProvider);
  }

  /**
   * Test {@link WorkflowBotConfiguration#cryptJpaVault(String)}.
   * <ul>
   *   <li>When {@code Pwd}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowBotConfiguration#cryptJpaVault(String)}
   */
  @Test
  @DisplayName("Test cryptJpaVault(String); when 'Pwd'; then throw IllegalArgumentException")
  void testCryptJpaVault_whenPwd_thenThrowIllegalArgumentException()
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new WorkflowBotConfiguration()).cryptJpaVault("Pwd"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowBotConfiguration#getManagementToken()}
   *   <li>{@link WorkflowBotConfiguration#getMonitoringToken()}
   *   <li>{@link WorkflowBotConfiguration#getWorkflowsFolderPath()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    WorkflowBotConfiguration workflowBotConfiguration = new WorkflowBotConfiguration();

    // Act
    String actualManagementToken = workflowBotConfiguration.getManagementToken();
    String actualMonitoringToken = workflowBotConfiguration.getMonitoringToken();

    // Assert
    assertNull(actualManagementToken);
    assertNull(actualMonitoringToken);
    assertNull(workflowBotConfiguration.getWorkflowsFolderPath());
  }
}
