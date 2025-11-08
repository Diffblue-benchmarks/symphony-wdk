package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WorkflowBotConfiguration.class})
@ExtendWith(SpringExtension.class)
class WorkflowBotConfigurationDiffblueTest {
  @InjectMocks
  private String string;

  @InjectMocks
  private WorkflowBotConfiguration workflowBotConfiguration;

  /**
   * Test {@link WorkflowBotConfiguration#workflowResourcesProvider()}.
   * <p>
   * Method under test: {@link WorkflowBotConfiguration#workflowResourcesProvider()}
   */
  @Test
  @DisplayName("Test workflowResourcesProvider()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "com.symphony.bdk.workflow.engine.ResourceProvider WorkflowBotConfiguration.workflowResourcesProvider()"})
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "com.symphony.bdk.workflow.engine.secret.SecretCryptVault WorkflowBotConfiguration.cryptJpaVault(String)"})
  void testCryptJpaVault_whenPwd_thenThrowIllegalArgumentException()
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowBotConfiguration.cryptJpaVault("Pwd"));
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String WorkflowBotConfiguration.getManagementToken()",
      "String WorkflowBotConfiguration.getMonitoringToken()",
      "String WorkflowBotConfiguration.getWorkflowsFolderPath()"})
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
