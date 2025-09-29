package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.camunda.bpm.engine.impl.HistoryServiceImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ActivityCmdaApiQueryRepositoryDiffblueTest {
  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <p>Method under test: {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String,
   * String, WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName("Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    HistoryServiceImpl historyService = new HistoryServiceImpl();
    historyService.setCommandExecutor(commandExecutor);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    ActivityCmdaApiQueryRepository activityCmdaApiQueryRepository =
        new ActivityCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter);

    WorkflowInstLifeCycleFilter lifeCycleFilter = mock(WorkflowInstLifeCycleFilter.class);
    when(lifeCycleFilter.getFinishedAfter())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(lifeCycleFilter.getFinishedBefore()).thenReturn(null);
    when(lifeCycleFilter.getStartedAfter()).thenReturn(null);
    when(lifeCycleFilter.getStartedBefore()).thenReturn(null);

    // Act
    List<ActivityInstanceDomain> actualFindAllByWorkflowInstanceIdResult =
        activityCmdaApiQueryRepository.findAllByWorkflowInstanceId("42", "42", lifeCycleFilter);

    // Assert
    verify(lifeCycleFilter, atLeast(1)).getFinishedAfter();
    verify(lifeCycleFilter).getFinishedBefore();
    verify(lifeCycleFilter).getStartedAfter();
    verify(lifeCycleFilter).getStartedBefore();
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(actualFindAllByWorkflowInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <p>Method under test: {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String,
   * String, WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName("Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId2() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    HistoryServiceImpl historyService = new HistoryServiceImpl();
    historyService.setCommandExecutor(commandExecutor);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    ActivityCmdaApiQueryRepository activityCmdaApiQueryRepository =
        new ActivityCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter);

    WorkflowInstLifeCycleFilter lifeCycleFilter = mock(WorkflowInstLifeCycleFilter.class);
    when(lifeCycleFilter.getFinishedAfter()).thenReturn(null);
    when(lifeCycleFilter.getFinishedBefore())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(lifeCycleFilter.getStartedAfter()).thenReturn(null);
    when(lifeCycleFilter.getStartedBefore()).thenReturn(null);

    // Act
    List<ActivityInstanceDomain> actualFindAllByWorkflowInstanceIdResult =
        activityCmdaApiQueryRepository.findAllByWorkflowInstanceId("42", "42", lifeCycleFilter);

    // Assert
    verify(lifeCycleFilter).getFinishedAfter();
    verify(lifeCycleFilter, atLeast(1)).getFinishedBefore();
    verify(lifeCycleFilter).getStartedAfter();
    verify(lifeCycleFilter).getStartedBefore();
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(actualFindAllByWorkflowInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <p>Method under test: {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String,
   * String, WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName("Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId3() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    HistoryServiceImpl historyService = new HistoryServiceImpl();
    historyService.setCommandExecutor(commandExecutor);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    ActivityCmdaApiQueryRepository activityCmdaApiQueryRepository =
        new ActivityCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter);

    WorkflowInstLifeCycleFilter lifeCycleFilter = mock(WorkflowInstLifeCycleFilter.class);
    when(lifeCycleFilter.getFinishedAfter()).thenReturn(null);
    when(lifeCycleFilter.getFinishedBefore()).thenReturn(null);
    when(lifeCycleFilter.getStartedAfter())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(lifeCycleFilter.getStartedBefore()).thenReturn(null);

    // Act
    List<ActivityInstanceDomain> actualFindAllByWorkflowInstanceIdResult =
        activityCmdaApiQueryRepository.findAllByWorkflowInstanceId("42", "42", lifeCycleFilter);

    // Assert
    verify(lifeCycleFilter).getFinishedAfter();
    verify(lifeCycleFilter).getFinishedBefore();
    verify(lifeCycleFilter, atLeast(1)).getStartedAfter();
    verify(lifeCycleFilter).getStartedBefore();
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(actualFindAllByWorkflowInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <p>Method under test: {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String,
   * String, WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName("Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId4() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    HistoryServiceImpl historyService = new HistoryServiceImpl();
    historyService.setCommandExecutor(commandExecutor);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    ActivityCmdaApiQueryRepository activityCmdaApiQueryRepository =
        new ActivityCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter);

    WorkflowInstLifeCycleFilter lifeCycleFilter = mock(WorkflowInstLifeCycleFilter.class);
    when(lifeCycleFilter.getFinishedAfter()).thenReturn(null);
    when(lifeCycleFilter.getFinishedBefore()).thenReturn(null);
    when(lifeCycleFilter.getStartedAfter()).thenReturn(null);
    when(lifeCycleFilter.getStartedBefore())
        .thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    List<ActivityInstanceDomain> actualFindAllByWorkflowInstanceIdResult =
        activityCmdaApiQueryRepository.findAllByWorkflowInstanceId("42", "42", lifeCycleFilter);

    // Assert
    verify(lifeCycleFilter).getFinishedAfter();
    verify(lifeCycleFilter).getFinishedBefore();
    verify(lifeCycleFilter).getStartedAfter();
    verify(lifeCycleFilter, atLeast(1)).getStartedBefore();
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(actualFindAllByWorkflowInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ActivityCmdaApiQueryRepository#findAllByWorkflowInstanceId(String,
   * String, WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName(
      "Test findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List ActivityCmdaApiQueryRepository.findAllByWorkflowInstanceId(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testFindAllByWorkflowInstanceId_thenReturnEmpty() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    HistoryServiceImpl historyService = new HistoryServiceImpl();
    historyService.setCommandExecutor(commandExecutor);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    ActivityCmdaApiQueryRepository activityCmdaApiQueryRepository =
        new ActivityCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter);

    WorkflowInstLifeCycleFilter lifeCycleFilter = mock(WorkflowInstLifeCycleFilter.class);
    when(lifeCycleFilter.getFinishedAfter()).thenReturn(null);
    when(lifeCycleFilter.getFinishedBefore()).thenReturn(null);
    when(lifeCycleFilter.getStartedAfter()).thenReturn(null);
    when(lifeCycleFilter.getStartedBefore()).thenReturn(null);

    // Act
    List<ActivityInstanceDomain> actualFindAllByWorkflowInstanceIdResult =
        activityCmdaApiQueryRepository.findAllByWorkflowInstanceId("42", "42", lifeCycleFilter);

    // Assert
    verify(lifeCycleFilter).getFinishedAfter();
    verify(lifeCycleFilter).getFinishedBefore();
    verify(lifeCycleFilter).getStartedAfter();
    verify(lifeCycleFilter).getStartedBefore();
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(actualFindAllByWorkflowInstanceIdResult.isEmpty());
  }
}
