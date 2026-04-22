package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.history.NativeHistoricVariableInstanceQuery;
import org.camunda.bpm.engine.impl.HistoryServiceImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.camunda.community.mockito.QueryMocks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ActivityCmdaApiQueryRepositoryDiffblueTest {

  @Mock
  HistoryService historyService;
  @Mock
  ObjectConverter objectConverter;
  @InjectMocks
  ActivityCmdaApiQueryRepository queryRepository;

  /**
   * Test {@link ActivityCmdaApiQueryRepository#ActivityCmdaApiQueryRepository(
   * org.camunda.bpm.engine.RepositoryService, HistoryService,
   * org.camunda.bpm.engine.RuntimeService, ObjectConverter)}.
   *
   * <p>Method under test: constructor
   */
  @Test
  @DisplayName("Test ActivityCmdaApiQueryRepository constructor")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
      "void ActivityCmdaApiQueryRepository.<init>(RepositoryService, HistoryService, RuntimeService, ObjectConverter)"
  })
  void testConstructor() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> biConverters = Optional.of(new ArrayList<>());
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(converters, biConverters);

    // Act
    ActivityCmdaApiQueryRepository repository =
        new ActivityCmdaApiQueryRepository(repositoryService, historyService, runtimeService, objectConverter);

    // Assert
    assertThat(repository).isNotNull();
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <ul>
   *   <li>Given all lifecycle filter dates are set.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName(
      "Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter); given all lifecycle filter dates set")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
      "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId_allFiltersSet() {
    // Arrange
    QueryMocks.mockHistoricActivityInstanceQuery(historyService).list(Collections.emptyList());
    when(objectConverter.convertCollection(anyList(), eq(ActivityInstanceDomain.class)))
        .thenReturn(Collections.emptyList());

    Instant now = Instant.now();
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(now, now, now, now);

    // Act
    List<ActivityInstanceDomain> result =
        queryRepository.findAllByWorkflowInstanceId("wf", "inst", filter);

    // Assert
    assertThat(result).isEmpty();
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <ul>
   *   <li>Given startedBefore filter is set.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName(
      "Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter); given startedBefore filter set")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
      "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId_startedBeforeFilter() {
    // Arrange
    QueryMocks.mockHistoricActivityInstanceQuery(historyService).list(Collections.emptyList());
    when(objectConverter.convertCollection(anyList(), eq(ActivityInstanceDomain.class)))
        .thenReturn(Collections.emptyList());

    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(Instant.now(), null, null, null);

    // Act
    List<ActivityInstanceDomain> result =
        queryRepository.findAllByWorkflowInstanceId("wf", "inst", filter);

    // Assert
    assertThat(result).isEmpty();
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <ul>
   *   <li>Given startedAfter filter is set.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName(
      "Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter); given startedAfter filter set")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
      "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId_startedAfterFilter() {
    // Arrange
    QueryMocks.mockHistoricActivityInstanceQuery(historyService).list(Collections.emptyList());
    when(objectConverter.convertCollection(anyList(), eq(ActivityInstanceDomain.class)))
        .thenReturn(Collections.emptyList());

    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, Instant.now(), null, null);

    // Act
    List<ActivityInstanceDomain> result =
        queryRepository.findAllByWorkflowInstanceId("wf", "inst", filter);

    // Assert
    assertThat(result).isEmpty();
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <ul>
   *   <li>Given finishedBefore filter is set.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName(
      "Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter); given finishedBefore filter set")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
      "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId_finishedBeforeFilter() {
    // Arrange
    QueryMocks.mockHistoricActivityInstanceQuery(historyService).list(Collections.emptyList());
    when(objectConverter.convertCollection(anyList(), eq(ActivityInstanceDomain.class)))
        .thenReturn(Collections.emptyList());

    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, Instant.now(), null);

    // Act
    List<ActivityInstanceDomain> result =
        queryRepository.findAllByWorkflowInstanceId("wf", "inst", filter);

    // Assert
    assertThat(result).isEmpty();
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <ul>
   *   <li>Given finishedAfter filter is set.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName(
      "Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter); given finishedAfter filter set")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
      "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId_finishedAfterFilter() {
    // Arrange
    QueryMocks.mockHistoricActivityInstanceQuery(historyService).list(Collections.emptyList());
    when(objectConverter.convertCollection(anyList(), eq(ActivityInstanceDomain.class)))
        .thenReturn(Collections.emptyList());

    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, Instant.now());

    // Act
    List<ActivityInstanceDomain> result =
        queryRepository.findAllByWorkflowInstanceId("wf", "inst", filter);

    // Assert
    assertThat(result).isEmpty();
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <ul>
   *   <li>Given service tasks with variables in result.
   * </ul>
   *
   * <p>Method under test: {@link
   * ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)} and private getHistoricVariableInstances
   */
  @Test
  @DisplayName(
      "Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter); given service tasks with variables")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
      "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId_withServiceTasksAndVariables() {
    // Arrange
    QueryMocks.mockHistoricActivityInstanceQuery(historyService).list(Collections.emptyList());
    ActivityInstanceDomain domain1 = ActivityInstanceDomain.builder()
        .type("serviceTask")
        .name("task1")
        .workflowId("wf")
        .procInstId("inst")
        .id("id1")
        .build();
    when(objectConverter.convertCollection(anyList(), eq(ActivityInstanceDomain.class)))
        .thenReturn(List.of(domain1));

    NativeHistoricVariableInstanceQuery nativeQuery = mock(NativeHistoricVariableInstanceQuery.class);
    HistoricVariableInstance varInst = mock(HistoricVariableInstance.class);
    when(nativeQuery.sql(contains("task1"))).thenReturn(nativeQuery);
    when(nativeQuery.list()).thenReturn(List.of(varInst));
    when(historyService.createNativeHistoricVariableInstanceQuery()).thenReturn(nativeQuery);
    when(varInst.getName()).thenReturn("task1");

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("result", "value");
    when(varInst.getValue()).thenReturn(Collections.singletonMap("outputs", outputs));
    when(varInst.getCreateTime()).thenReturn(new Date());

    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    // Act
    List<ActivityInstanceDomain> result =
        queryRepository.findAllByWorkflowInstanceId("wf", "inst", filter);

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getVariables().getOutputs()).containsKey("result");
  }
}
