package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import java.util.ArrayList;
import java.util.List;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.impl.HistoricProcessInstanceQueryImpl;
import org.camunda.bpm.engine.impl.ProcessDefinitionQueryImpl;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.community.mockito.process.ProcessDefinitionFake;
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

@ContextConfiguration(classes = {WorkflowInstCmdaApiQueryRepository.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class WorkflowInstCmdaApiQueryRepositoryDiffblueTest {
  @MockBean private HistoryService historyService;

  @MockBean private ObjectConverter objectConverter;

  @MockBean private RepositoryService repositoryService;

  @MockBean private RuntimeService runtimeService;

  @Autowired private WorkflowInstCmdaApiQueryRepository workflowInstCmdaApiQueryRepository;

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatus(String, StatusEnum)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link HistoricProcessInstanceEntity} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatus(String,
   * StatusEnum)}
   */
  @Test
  @DisplayName(
      "Test findAllByIdAndStatus(String, StatusEnum); given ArrayList() add HistoricProcessInstanceEntity (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatus(String, StatusEnum)"
  })
  void testFindAllByIdAndStatus_givenArrayListAddHistoricProcessInstanceEntity() {
    // Arrange
    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(
        ProcessDefinitionFake.builder()
            .category("Category")
            .deploymentId("42")
            .description("The characteristics of someone or something")
            .diagramResourceName("Diagram Resource Name")
            .historyTimeToLive(1)
            .id("42")
            .key("Key")
            .name("Name")
            .resourceName("Resource Name")
            .suspended(true)
            .tenantId("42")
            .version(1)
            .versionTag("1.0.2")
            .build());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(processDefinitionList);

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    ArrayList<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
    historicProcessInstanceList.add(new HistoricProcessInstanceEntity());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(historicProcessInstanceList);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(
            Mockito.<List<?>>any(), Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatus("42", StatusEnum.FAILED);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Object.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    assertTrue(actualFindAllByIdAndStatusResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatus(String, StatusEnum)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link HistoricProcessInstanceEntity} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatus(String,
   * StatusEnum)}
   */
  @Test
  @DisplayName(
      "Test findAllByIdAndStatus(String, StatusEnum); given ArrayList() add HistoricProcessInstanceEntity (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatus(String, StatusEnum)"
  })
  void testFindAllByIdAndStatus_givenArrayListAddHistoricProcessInstanceEntity2() {
    // Arrange
    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(
        ProcessDefinitionFake.builder()
            .category("Category")
            .deploymentId("42")
            .description("The characteristics of someone or something")
            .diagramResourceName("Diagram Resource Name")
            .historyTimeToLive(1)
            .id("42")
            .key("Key")
            .name("Name")
            .resourceName("Resource Name")
            .suspended(true)
            .tenantId("42")
            .version(1)
            .versionTag("1.0.2")
            .build());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(processDefinitionList);

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    ArrayList<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
    historicProcessInstanceList.add(new HistoricProcessInstanceEntity());
    historicProcessInstanceList.add(new HistoricProcessInstanceEntity());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(historicProcessInstanceList);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(
            Mockito.<List<?>>any(), Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatus("42", StatusEnum.FAILED);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Object.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    assertTrue(actualFindAllByIdAndStatusResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatus(String, StatusEnum)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convertCollection(List, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatus(String,
   * StatusEnum)}
   */
  @Test
  @DisplayName(
      "Test findAllByIdAndStatus(String, StatusEnum); then calls convertCollection(List, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatus(String, StatusEnum)"
  })
  void testFindAllByIdAndStatus_thenCallsConvertCollection() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(new ArrayList<>());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(new ArrayList<>());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatus("42", StatusEnum.FAILED);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    assertTrue(actualFindAllByIdAndStatusResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatus(String, StatusEnum)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convertCollection(List, Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatus(String,
   * StatusEnum)}
   */
  @Test
  @DisplayName(
      "Test findAllByIdAndStatus(String, StatusEnum); then calls convertCollection(List, Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatus(String, StatusEnum)"
  })
  void testFindAllByIdAndStatus_thenCallsConvertCollection2() {
    // Arrange
    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(
        ProcessDefinitionFake.builder()
            .category("Category")
            .deploymentId("42")
            .description("The characteristics of someone or something")
            .diagramResourceName("Diagram Resource Name")
            .historyTimeToLive(1)
            .id("42")
            .key("Key")
            .name("Name")
            .resourceName("Resource Name")
            .suspended(true)
            .tenantId("42")
            .version(1)
            .versionTag("1.0.2")
            .build());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(processDefinitionList);

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(new ArrayList<>());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(
            Mockito.<List<?>>any(), Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatus("42", StatusEnum.FAILED);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Object.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    assertTrue(actualFindAllByIdAndStatusResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String,
   * StatusEnum, String)}.
   *
   * <p>Method under test: {@link
   * WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String, StatusEnum, String)}
   */
  @Test
  @DisplayName("Test findAllByIdAndStatusAndVersion(String, StatusEnum, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(String, StatusEnum, String)"
  })
  void testFindAllByIdAndStatusAndVersion() {
    // Arrange
    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(
        ProcessDefinitionFake.builder()
            .category("Category")
            .deploymentId("42")
            .description("The characteristics of someone or something")
            .diagramResourceName("Diagram Resource Name")
            .historyTimeToLive(1)
            .id("42")
            .key("Key")
            .name("Name")
            .resourceName("Resource Name")
            .suspended(true)
            .tenantId("42")
            .version(1)
            .versionTag("")
            .build());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(processDefinitionList);
    when(processDefinitionQueryImpl.versionTag(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionQueryImpl());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(new ArrayList<>());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusAndVersionResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(
            "42", StatusEnum.FAILED, "1.0.2");

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    verify(processDefinitionQueryImpl).versionTag("1.0.2");
    assertTrue(actualFindAllByIdAndStatusAndVersionResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String,
   * StatusEnum, String)}.
   *
   * <p>Method under test: {@link
   * WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String, StatusEnum, String)}
   */
  @Test
  @DisplayName("Test findAllByIdAndStatusAndVersion(String, StatusEnum, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(String, StatusEnum, String)"
  })
  void testFindAllByIdAndStatusAndVersion2() {
    // Arrange
    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(
        ProcessDefinitionFake.builder()
            .category("Category")
            .deploymentId("42")
            .description("The characteristics of someone or something")
            .diagramResourceName("Diagram Resource Name")
            .historyTimeToLive(1)
            .id("42")
            .key("Key")
            .name("Name")
            .resourceName("Resource Name")
            .suspended(true)
            .tenantId("42")
            .version(1)
            .versionTag(null)
            .build());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(processDefinitionList);
    when(processDefinitionQueryImpl.versionTag(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionQueryImpl());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(new ArrayList<>());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusAndVersionResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(
            "42", StatusEnum.FAILED, "1.0.2");

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    verify(processDefinitionQueryImpl).versionTag("1.0.2");
    assertTrue(actualFindAllByIdAndStatusAndVersionResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String,
   * StatusEnum, String)}.
   *
   * <p>Method under test: {@link
   * WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String, StatusEnum, String)}
   */
  @Test
  @DisplayName("Test findAllByIdAndStatusAndVersion(String, StatusEnum, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(String, StatusEnum, String)"
  })
  void testFindAllByIdAndStatusAndVersion3() {
    // Arrange
    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(
        ProcessDefinitionFake.builder()
            .category("Category")
            .deploymentId("42")
            .description("The characteristics of someone or something")
            .diagramResourceName("Diagram Resource Name")
            .historyTimeToLive(1)
            .id("42")
            .key("Key")
            .name("Name")
            .resourceName("Resource Name")
            .suspended(true)
            .tenantId("42")
            .version(1)
            .versionTag("1.0.2")
            .build());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(processDefinitionList);
    when(processDefinitionQueryImpl.versionTag(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionQueryImpl());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    ArrayList<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
    historicProcessInstanceList.add(new HistoricProcessInstanceEntity());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(historicProcessInstanceList);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(
            Mockito.<List<?>>any(), Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusAndVersionResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(
            "42", StatusEnum.FAILED, "1.0.2");

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Object.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    verify(processDefinitionQueryImpl).versionTag("1.0.2");
    assertTrue(actualFindAllByIdAndStatusAndVersionResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String,
   * StatusEnum, String)}.
   *
   * <p>Method under test: {@link
   * WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String, StatusEnum, String)}
   */
  @Test
  @DisplayName("Test findAllByIdAndStatusAndVersion(String, StatusEnum, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(String, StatusEnum, String)"
  })
  void testFindAllByIdAndStatusAndVersion4() {
    // Arrange
    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(
        ProcessDefinitionFake.builder()
            .category("Category")
            .deploymentId("42")
            .description("The characteristics of someone or something")
            .diagramResourceName("Diagram Resource Name")
            .historyTimeToLive(1)
            .id("42")
            .key("Key")
            .name("Name")
            .resourceName("Resource Name")
            .suspended(true)
            .tenantId("42")
            .version(1)
            .versionTag("1.0.2")
            .build());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(processDefinitionList);
    when(processDefinitionQueryImpl.versionTag(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionQueryImpl());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    ArrayList<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
    historicProcessInstanceList.add(new HistoricProcessInstanceEntity());
    historicProcessInstanceList.add(new HistoricProcessInstanceEntity());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(historicProcessInstanceList);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(
            Mockito.<List<?>>any(), Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusAndVersionResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(
            "42", StatusEnum.FAILED, "1.0.2");

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Object.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    verify(processDefinitionQueryImpl).versionTag("1.0.2");
    assertTrue(actualFindAllByIdAndStatusAndVersionResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String,
   * StatusEnum, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convertCollection(List, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String, StatusEnum, String)}
   */
  @Test
  @DisplayName(
      "Test findAllByIdAndStatusAndVersion(String, StatusEnum, String); then calls convertCollection(List, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(String, StatusEnum, String)"
  })
  void testFindAllByIdAndStatusAndVersion_thenCallsConvertCollection() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(new ArrayList<>());
    when(processDefinitionQueryImpl.versionTag(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionQueryImpl());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(new ArrayList<>());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusAndVersionResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(
            "42", StatusEnum.FAILED, "1.0.2");

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    verify(processDefinitionQueryImpl).versionTag("1.0.2");
    assertTrue(actualFindAllByIdAndStatusAndVersionResult.isEmpty());
  }

  /**
   * Test {@link WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String,
   * StatusEnum, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convertCollection(List, Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * WorkflowInstCmdaApiQueryRepository#findAllByIdAndStatusAndVersion(String, StatusEnum, String)}
   */
  @Test
  @DisplayName(
      "Test findAllByIdAndStatusAndVersion(String, StatusEnum, String); then calls convertCollection(List, Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List WorkflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(String, StatusEnum, String)"
  })
  void testFindAllByIdAndStatusAndVersion_thenCallsConvertCollection2() {
    // Arrange
    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(
        ProcessDefinitionFake.builder()
            .category("Category")
            .deploymentId("42")
            .description("The characteristics of someone or something")
            .diagramResourceName("Diagram Resource Name")
            .historyTimeToLive(1)
            .id("42")
            .key("Key")
            .name("Name")
            .resourceName("Resource Name")
            .suspended(true)
            .tenantId("42")
            .version(1)
            .versionTag("1.0.2")
            .build());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl.list()).thenReturn(processDefinitionList);
    when(processDefinitionQueryImpl.versionTag(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionQueryImpl());

    ProcessDefinitionQueryImpl processDefinitionQueryImpl2 = mock(ProcessDefinitionQueryImpl.class);
    when(processDefinitionQueryImpl2.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(processDefinitionQueryImpl);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl.list()).thenReturn(new ArrayList<>());

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl2.asc()).thenReturn(historicProcessInstanceQueryImpl);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl3 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl3.orderByProcessInstanceStartTime())
        .thenReturn(historicProcessInstanceQueryImpl2);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl4 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl4.finished())
        .thenReturn(historicProcessInstanceQueryImpl3);

    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl5 =
        mock(HistoricProcessInstanceQueryImpl.class);
    when(historicProcessInstanceQueryImpl5.processDefinitionKey(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceQueryImpl4);
    when(historyService.createHistoricProcessInstanceQuery())
        .thenReturn(historicProcessInstanceQueryImpl5);
    when(objectConverter.convertCollection(
            Mockito.<List<?>>any(), Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstanceDomain> actualFindAllByIdAndStatusAndVersionResult =
        workflowInstCmdaApiQueryRepository.findAllByIdAndStatusAndVersion(
            "42", StatusEnum.FAILED, "1.0.2");

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Object.class), isA(Class.class));
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(historicProcessInstanceQueryImpl2).asc();
    verify(historicProcessInstanceQueryImpl).list();
    verify(processDefinitionQueryImpl).list();
    verify(historicProcessInstanceQueryImpl4).finished();
    verify(historicProcessInstanceQueryImpl3).orderByProcessInstanceStartTime();
    verify(historicProcessInstanceQueryImpl5).processDefinitionKey("42");
    verify(processDefinitionQueryImpl2).processDefinitionKey("42");
    verify(processDefinitionQueryImpl).versionTag("1.0.2");
    assertTrue(actualFindAllByIdAndStatusAndVersionResult.isEmpty());
  }
}
