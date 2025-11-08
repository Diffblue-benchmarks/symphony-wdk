package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
   * Method under test:
   * {@link WorkflowBotConfiguration#workflowResourcesProvider()}
   */
  @Test
  void testWorkflowResourcesProvider() {
    // Arrange, Act and Assert
    assertTrue(workflowBotConfiguration.workflowResourcesProvider() instanceof WorkflowResourcesProvider);
  }

  /**
   * Method under test: {@link WorkflowBotConfiguration#cryptJpaVault(String)}
   */
  @Test
  void testCryptJpaVault() throws NoSuchAlgorithmException, InvalidKeySpecException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new WorkflowBotConfiguration()).cryptJpaVault("Pwd"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowBotConfiguration#getManagementToken()}
   *   <li>{@link WorkflowBotConfiguration#getMonitoringToken()}
   *   <li>{@link WorkflowBotConfiguration#getWorkflowsFolderPath()}
   * </ul>
   */
  @Test
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
