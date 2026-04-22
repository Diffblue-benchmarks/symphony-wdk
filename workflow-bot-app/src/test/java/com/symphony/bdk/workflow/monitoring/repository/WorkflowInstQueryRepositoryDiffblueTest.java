package com.symphony.bdk.workflow.monitoring.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.camunda.monitoring.repository.WorkflowInstCmdaApiQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.HistoricProcessInstanceQueryImpl;
import org.camunda.bpm.engine.impl.ProcessDefinitionQueryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = {WorkflowInstCmdaApiQueryRepository.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class WorkflowInstQueryRepositoryDiffblueTest {
  @MockBean private HistoryService historyService;

  @MockBean private ObjectConverter objectConverter;

  @MockBean private RepositoryService repositoryService;

  @MockBean private RuntimeService runtimeService;

  @Autowired private WorkflowInstQueryRepository workflowInstQueryRepository;

  private void setupEmptyHistoryAndRepository() {
    ProcessDefinitionQueryImpl processDefinitionQuery = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQuery.list()).thenReturn(new ArrayList<>());

    ProcessDefinitionQueryImpl rootProcessDefinitionQuery = mock(ProcessDefinitionQueryImpl.class);
    when(rootProcessDefinitionQuery.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQuery);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(rootProcessDefinitionQuery);

    HistoricProcessInstanceQueryImpl ascQuery = mock(HistoricProcessInstanceQueryImpl.class);
    when(ascQuery.list()).thenReturn(new ArrayList<>());

    HistoricProcessInstanceQueryImpl orderQuery = mock(HistoricProcessInstanceQueryImpl.class);
    when(orderQuery.asc()).thenReturn(ascQuery);

    // afterKeyQuery supports both the status-filtering path (.finished()/.unfinished())
    // and the direct ordering path (.orderByProcessInstanceStartTime())
    HistoricProcessInstanceQueryImpl afterKeyQuery = mock(HistoricProcessInstanceQueryImpl.class);
    when(afterKeyQuery.orderByProcessInstanceStartTime()).thenReturn(orderQuery);
    when(afterKeyQuery.finished()).thenReturn(afterKeyQuery);
    when(afterKeyQuery.unfinished()).thenReturn(afterKeyQuery);

    HistoricProcessInstanceQueryImpl rootHistoryQuery = mock(HistoricProcessInstanceQueryImpl.class);
    when(rootHistoryQuery.processDefinitionKey(Mockito.<String>any())).thenReturn(afterKeyQuery);
    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(rootHistoryQuery);

    when(objectConverter.convertCollection(any(List.class), any(Class.class)))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(any(List.class), any(Object.class), any(Class.class)))
        .thenReturn(new ArrayList<>());
  }

  /**
   * Test {@link WorkflowInstQueryRepository#findAllById(String)}.
   *
   * <p>Method under test: {@link WorkflowInstQueryRepository#findAllById(String)}
   */
  @Test
  @DisplayName("Test findAllById(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List WorkflowInstQueryRepository.findAllById(String)"})
  void testFindAllById() {
    // Arrange
    setupEmptyHistoryAndRepository();

    // Act
    List<WorkflowInstanceDomain> result = workflowInstQueryRepository.findAllById("42");

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link WorkflowInstQueryRepository#findAllByIdAndStatus(String, StatusEnum)}.
   *
   * <p>Method under test: {@link WorkflowInstQueryRepository#findAllByIdAndStatus(String, StatusEnum)}
   */
  @Test
  @DisplayName("Test findAllByIdAndStatus(String, StatusEnum)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List WorkflowInstQueryRepository.findAllByIdAndStatus(String, StatusEnum)"})
  void testFindAllByIdAndStatus() {
    // Arrange
    setupEmptyHistoryAndRepository();

    // Act
    List<WorkflowInstanceDomain> result =
        workflowInstQueryRepository.findAllByIdAndStatus("42", StatusEnum.COMPLETED);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link WorkflowInstQueryRepository#findAllByIdAndVersion(String, String)}.
   *
   * <p>Method under test: {@link WorkflowInstQueryRepository#findAllByIdAndVersion(String, String)}
   */
  @Test
  @DisplayName("Test findAllByIdAndVersion(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List WorkflowInstQueryRepository.findAllByIdAndVersion(String, String)"})
  void testFindAllByIdAndVersion() {
    // Arrange
    setupEmptyHistoryAndRepository();

    // Act
    List<WorkflowInstanceDomain> result =
        workflowInstQueryRepository.findAllByIdAndVersion("42", "1.0.0");

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link WorkflowInstQueryRepository#findAllByIdAndStatusAndVersion(String, StatusEnum, String)}.
   *
   * <p>Method under test: {@link WorkflowInstQueryRepository#findAllByIdAndStatusAndVersion(String, StatusEnum, String)}
   */
  @Test
  @DisplayName("Test findAllByIdAndStatusAndVersion(String, StatusEnum, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstQueryRepository.findAllByIdAndStatusAndVersion(String, StatusEnum, String)"
  })
  void testFindAllByIdAndStatusAndVersion() {
    // Arrange
    setupEmptyHistoryAndRepository();

    // Act
    List<WorkflowInstanceDomain> result =
        workflowInstQueryRepository.findAllByIdAndStatusAndVersion("42", StatusEnum.PENDING, "1.0.0");

    // Assert
    assertTrue(result.isEmpty());
  }
}
