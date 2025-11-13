package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.ProcessDefinitionQueryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CamundaMetricsDiffblueTest {
  @InjectMocks private CamundaMetrics camundaMetrics;

  @Mock private RepositoryService repositoryService;

  /**
   * Test {@link CamundaMetrics#countDeployedWorkflows()}.
   *
   * <ul>
   *   <li>Given array of {@link String} with {@code Ids}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link CamundaMetrics#countDeployedWorkflows()}
   */
  @Test
  @DisplayName("Test countDeployedWorkflows(); given array of String with 'Ids'; then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long CamundaMetrics.countDeployedWorkflows()"})
  void testCountDeployedWorkflows_givenArrayOfStringWithIds_thenReturnZero() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();
    processDefinitionQueryImpl.processDefinitionIdIn("Ids");
    processDefinitionQueryImpl.processDefinitionId("42");
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl);

    // Act
    long actualCountDeployedWorkflowsResult = camundaMetrics.countDeployedWorkflows();

    // Assert
    verify(repositoryService).createProcessDefinitionQuery();
    assertEquals(0L, actualCountDeployedWorkflowsResult);
  }
}
