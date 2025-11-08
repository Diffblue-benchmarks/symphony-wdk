package com.symphony.bdk.workflow.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.xml.impl.ModelBuilderImpl;
import org.camunda.bpm.model.xml.impl.ModelImpl;
import org.camunda.bpm.model.xml.impl.instance.DomDocumentImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.Document;

@ExtendWith(MockitoExtension.class)
class WorkflowManagementServiceDiffblueTest {
  @Mock
  private VersionedWorkflowRepository versionedWorkflowRepository;

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @InjectMocks
  private WorkflowManagementService workflowManagementService;

  @Mock
  private ObjectConverter objectConverter;

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   * <p>
   * Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
  void testDeploy() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException("foo"));

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowManagementService.deploy(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndPublishedFalse(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#deploy(SwadlView)}.
   * <ul>
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code true}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#deploy(SwadlView)}
   */
  @Test
  @DisplayName("Test deploy(SwadlView); given VersionedWorkflow (default constructor) Active is 'true'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.deploy(SwadlView)"})
  void testDeploy_givenVersionedWorkflowActiveIsTrue_thenThrowIllegalArgumentException() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    when(versionedWorkflowRepository.findByWorkflowIdAndPublishedFalse(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowManagementService.deploy(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndPublishedFalse(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with workflowId is {@code 42}.</li>
   *   <li>Then calls {@link WorkflowEngine#deploy(TranslatedWorkflowContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView); given WorkflowDirectedGraph(String) with workflowId is '42'; then calls deploy(TranslatedWorkflowContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate_givenWorkflowDirectedGraphWithWorkflowIdIs42_thenCallsDeploy() {
    // Arrange
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    when(workflowEngine.translate(Mockito.<Workflow>any()))
        .thenReturn(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))));
    when(workflowEngine.deploy(Mockito.<CamundaTranslatedWorkflowContext>any())).thenReturn("Deploy");
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getPublished()).thenReturn(false);
    doNothing().when(versionedWorkflow).setActive(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setCreatedBy(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setDeploymentId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setDescription(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setEtag(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setPublished(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setSwadl(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setVersion(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setWorkflowId(Mockito.<String>any());
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setCreatedBy(1L);
    versionedWorkflow2.setDeploymentId("42");
    versionedWorkflow2.setDescription("The characteristics of someone or something");
    versionedWorkflow2.setEtag(1L);
    versionedWorkflow2.setId("42");
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("Swadl");
    versionedWorkflow2.setVersion(1L);
    versionedWorkflow2.setWorkflowId("42");

    VersionedWorkflow versionedWorkflow3 = new VersionedWorkflow();
    versionedWorkflow3.setActive(true);
    versionedWorkflow3.setCreatedBy(1L);
    versionedWorkflow3.setDeploymentId("42");
    versionedWorkflow3.setDescription("The characteristics of someone or something");
    versionedWorkflow3.setEtag(1L);
    versionedWorkflow3.setId("42");
    versionedWorkflow3.setPublished(true);
    versionedWorkflow3.setSwadl("Swadl");
    versionedWorkflow3.setVersion(1L);
    versionedWorkflow3.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow3);

    VersionedWorkflow versionedWorkflow4 = new VersionedWorkflow();
    versionedWorkflow4.setActive(true);
    versionedWorkflow4.setCreatedBy(1L);
    versionedWorkflow4.setDeploymentId("42");
    versionedWorkflow4.setDescription("The characteristics of someone or something");
    versionedWorkflow4.setEtag(1L);
    versionedWorkflow4.setId("42");
    versionedWorkflow4.setPublished(true);
    versionedWorkflow4.setSwadl("Swadl");
    versionedWorkflow4.setVersion(1L);
    versionedWorkflow4.setWorkflowId("42");
    when(versionedWorkflowRepository.saveAndFlush(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow4);
    when(versionedWorkflowRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(ofResult2);
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow2);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act
    workflowManagementService.update(swadlView);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(CamundaTranslatedWorkflowContext.class));
    verify(workflowEngine).translate(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
    verify(versionedWorkflow).getPublished();
    verify(versionedWorkflow, atLeast(1)).setActive(eq(true));
    verify(versionedWorkflow).setCreatedBy(eq(1L));
    verify(versionedWorkflow, atLeast(1)).setDeploymentId(Mockito.<String>any());
    verify(versionedWorkflow, atLeast(1)).setDescription(eq("The characteristics of someone or something"));
    verify(versionedWorkflow).setEtag(eq(1L));
    verify(versionedWorkflow).setId(eq("42"));
    verify(versionedWorkflow, atLeast(1)).setPublished(eq(true));
    verify(versionedWorkflow, atLeast(1)).setSwadl(eq("Swadl"));
    verify(versionedWorkflow, atLeast(1)).setVersion(eq(1L));
    verify(versionedWorkflow).setWorkflowId(eq("42"));
    verify(versionedWorkflowRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView); then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate_thenThrowNotFoundException() {
    // Arrange
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.update(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#update(SwadlView)}.
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#update(SwadlView)}
   */
  @Test
  @DisplayName("Test update(SwadlView); then throw UnsupportedOperationException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.update(SwadlView)"})
  void testUpdate_thenThrowUnsupportedOperationException() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    when(versionedWorkflowRepository.findTopByWorkflowIdOrderByVersionDesc(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<Workflow>>any())).thenReturn(workflow);
    SwadlView swadlView = SwadlView.builder()
        .createdBy(1L)
        .description("The characteristics of someone or something")
        .swadl("Swadl")
        .build();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> workflowManagementService.update(swadlView));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findTopByWorkflowIdOrderByVersionDesc(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#get(String, Long)} with {@code id}, {@code version}.
   * <ul>
   *   <li>Then return {@link Optional#get()} DeploymentId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#get(String, Long)}
   */
  @Test
  @DisplayName("Test get(String, Long) with 'id', 'version'; then return get() DeploymentId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String, Long)"})
  void testGetWithIdVersion_thenReturnGetDeploymentIdIs42() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    VersionedWorkflowView buildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
        .thenReturn(buildResult);

    // Act
    Optional<VersionedWorkflowView> actualGetResult = workflowManagementService.get("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    VersionedWorkflowView getResult = actualGetResult.get();
    assertEquals("42", getResult.getDeploymentId());
    assertEquals("42", getResult.getId());
    assertEquals("42", getResult.getWorkflowId());
    assertEquals("Swadl", getResult.getSwadl());
    assertEquals("The characteristics of someone or something", getResult.getDescription());
    assertEquals(1L, getResult.getCreatedBy().longValue());
    assertEquals(1L, getResult.getVersion().longValue());
    assertTrue(getResult.getActive());
    assertTrue(getResult.getPublished());
    assertTrue(actualGetResult.isPresent());
  }

  /**
   * Test {@link WorkflowManagementService#get(String, Long)} with {@code id}, {@code version}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#get(String, Long)}
   */
  @Test
  @DisplayName("Test get(String, Long) with 'id', 'version'; then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String, Long)"})
  void testGetWithIdVersion_thenThrowNotFoundException() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.get("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }

  /**
   * Test {@link WorkflowManagementService#get(String)} with {@code id}.
   * <ul>
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code true}.</li>
   *   <li>Then return {@link Optional#get()} DeploymentId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName("Test get(String) with 'id'; given VersionedWorkflow (default constructor) Active is 'true'; then return get() DeploymentId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String)"})
  void testGetWithId_givenVersionedWorkflowActiveIsTrue_thenReturnGetDeploymentIdIs42() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(ofResult);
    VersionedWorkflowView buildResult = VersionedWorkflowView.builder()
        .active(true)
        .createdBy(1L)
        .deploymentId("42")
        .description("The characteristics of someone or something")
        .id("42")
        .published(true)
        .swadl("Swadl")
        .version(1L)
        .workflowId("42")
        .build();
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
        .thenReturn(buildResult);

    // Act
    Optional<VersionedWorkflowView> actualGetResult = workflowManagementService.get("42");

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    VersionedWorkflowView getResult = actualGetResult.get();
    assertEquals("42", getResult.getDeploymentId());
    assertEquals("42", getResult.getId());
    assertEquals("42", getResult.getWorkflowId());
    assertEquals("Swadl", getResult.getSwadl());
    assertEquals("The characteristics of someone or something", getResult.getDescription());
    assertEquals(1L, getResult.getCreatedBy().longValue());
    assertEquals(1L, getResult.getVersion().longValue());
    assertTrue(getResult.getActive());
    assertTrue(getResult.getPublished());
    assertTrue(actualGetResult.isPresent());
  }

  /**
   * Test {@link WorkflowManagementService#get(String)} with {@code id}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#get(String)}
   */
  @Test
  @DisplayName("Test get(String) with 'id'; then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional WorkflowManagementService.get(String)"})
  void testGetWithId_thenThrowNotFoundException() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(ofResult);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Class<VersionedWorkflowView>>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.get("42"));
    verify(objectConverter).convert(isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#getAllVersions(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  @DisplayName("Test getAllVersions(String); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List WorkflowManagementService.getAllVersions(String)"})
  void testGetAllVersions_thenReturnEmpty() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<VersionedWorkflowView> actualAllVersions = workflowManagementService.getAllVersions("42");

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowId(eq("42"));
    assertTrue(actualAllVersions.isEmpty());
  }

  /**
   * Test {@link WorkflowManagementService#getAllVersions(String)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#getAllVersions(String)}
   */
  @Test
  @DisplayName("Test getAllVersions(String); then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List WorkflowManagementService.getAllVersions(String)"})
  void testGetAllVersions_thenThrowNotFoundException() {
    // Arrange
    when(versionedWorkflowRepository.findByWorkflowId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.getAllVersions("42"));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowId(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   * <p>
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    doThrow(new NotFoundException("An error occurred")).when(versionedWorkflowRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42", 1L));
    verify(versionedWorkflowRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   * <p>
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion2() {
    // Arrange
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);
    new NotFoundException("An error occurred");

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   * <ul>
   *   <li>Given {@link VersionedWorkflow} {@link VersionedWorkflow#getActive()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'; given VersionedWorkflow getActive() return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion_givenVersionedWorkflowGetActiveReturnFalse() {
    // Arrange
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getActive()).thenReturn(false);
    doNothing().when(versionedWorkflow).setActive(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setCreatedBy(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setDeploymentId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setDescription(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setEtag(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setPublished(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setSwadl(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setVersion(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setWorkflowId(Mockito.<String>any());
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    doNothing().when(versionedWorkflowRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
    verify(versionedWorkflowRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflow).getActive();
    verify(versionedWorkflow).setActive(eq(true));
    verify(versionedWorkflow).setCreatedBy(eq(1L));
    verify(versionedWorkflow).setDeploymentId(eq("42"));
    verify(versionedWorkflow).setDescription(eq("The characteristics of someone or something"));
    verify(versionedWorkflow).setEtag(eq(1L));
    verify(versionedWorkflow).setId(eq("42"));
    verify(versionedWorkflow).setPublished(eq(true));
    verify(versionedWorkflow).setSwadl(eq("Swadl"));
    verify(versionedWorkflow).setVersion(eq(1L));
    verify(versionedWorkflow).setWorkflowId(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   * <ul>
   *   <li>Then calls {@link VersionedWorkflow#getDeploymentId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'; then calls getDeploymentId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion_thenCallsGetDeploymentId() {
    // Arrange
    VersionedWorkflow versionedWorkflow = mock(VersionedWorkflow.class);
    when(versionedWorkflow.getDeploymentId()).thenThrow(new NotFoundException("An error occurred"));
    when(versionedWorkflow.getActive()).thenReturn(true);
    doNothing().when(versionedWorkflow).setActive(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setCreatedBy(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setDeploymentId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setDescription(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setEtag(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setId(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setPublished(Mockito.<Boolean>any());
    doNothing().when(versionedWorkflow).setSwadl(Mockito.<String>any());
    doNothing().when(versionedWorkflow).setVersion(Mockito.<Long>any());
    doNothing().when(versionedWorkflow).setWorkflowId(Mockito.<String>any());
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    doNothing().when(versionedWorkflowRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42", 1L));
    verify(versionedWorkflowRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflow).getActive();
    verify(versionedWorkflow).getDeploymentId();
    verify(versionedWorkflow).setActive(eq(true));
    verify(versionedWorkflow).setCreatedBy(eq(1L));
    verify(versionedWorkflow).setDeploymentId(eq("42"));
    verify(versionedWorkflow).setDescription(eq("The characteristics of someone or something"));
    verify(versionedWorkflow).setEtag(eq(1L));
    verify(versionedWorkflow).setId(eq("42"));
    verify(versionedWorkflow).setPublished(eq(true));
    verify(versionedWorkflow).setSwadl(eq("Swadl"));
    verify(versionedWorkflow).setVersion(eq(1L));
    verify(versionedWorkflow).setWorkflowId(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#delete(String, Long)} with {@code id}, {@code version}.
   * <ul>
   *   <li>Then calls {@link WorkflowEngine#undeployByDeploymentId(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#delete(String, Long)}
   */
  @Test
  @DisplayName("Test delete(String, Long) with 'id', 'version'; then calls undeployByDeploymentId(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.delete(String, Long)"})
  void testDeleteWithIdVersion_thenCallsUndeployByDeploymentId() {
    // Arrange
    doNothing().when(workflowEngine).undeployByDeploymentId(Mockito.<String>any());

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    doNothing().when(versionedWorkflowRepository)
        .deleteByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any());
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    // Act
    workflowManagementService.delete("42", 1L);

    // Assert
    verify(workflowEngine).undeployByDeploymentId(eq("42"));
    verify(versionedWorkflowRepository).deleteByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }

  /**
   * Test {@link WorkflowManagementService#delete(String)} with {@code id}.
   * <ul>
   *   <li>Then calls {@link WorkflowEngine#undeployByWorkflowId(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'id'; then calls undeployByWorkflowId(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.delete(String)"})
  void testDeleteWithId_thenCallsUndeployByWorkflowId() {
    // Arrange
    doNothing().when(workflowEngine).undeployByWorkflowId(Mockito.<String>any());
    doNothing().when(versionedWorkflowRepository).deleteByWorkflowId(Mockito.<String>any());

    // Act
    workflowManagementService.delete("42");

    // Assert
    verify(workflowEngine).undeployByWorkflowId(eq("42"));
    verify(versionedWorkflowRepository).deleteByWorkflowId(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#delete(String)} with {@code id}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'id'; then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.delete(String)"})
  void testDeleteWithId_thenThrowNotFoundException() {
    // Arrange
    doThrow(new NotFoundException("An error occurred")).when(versionedWorkflowRepository)
        .deleteByWorkflowId(Mockito.<String>any());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.delete("42"));
    verify(versionedWorkflowRepository).deleteByWorkflowId(eq("42"));
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   * <p>
   * Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> workflowManagementService.setActiveVersion("42", 1L));
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   * <p>
   * Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion2() {
    // Arrange
    when(workflowEngine.deploy(Mockito.<Workflow>any())).thenReturn("Deploy");

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setCreatedBy(1L);
    versionedWorkflow2.setDeploymentId("42");
    versionedWorkflow2.setDescription("The characteristics of someone or something");
    versionedWorkflow2.setEtag(1L);
    versionedWorkflow2.setId("42");
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("Swadl");
    versionedWorkflow2.setVersion(1L);
    versionedWorkflow2.setWorkflowId("42");
    when(versionedWorkflowRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(emptyResult);
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

    // Act
    workflowManagementService.setActiveVersion("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
  }

  /**
   * Test {@link WorkflowManagementService#setActiveVersion(String, Long)}.
   * <ul>
   *   <li>Then calls {@link JpaRepository#saveAndFlush(Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowManagementService#setActiveVersion(String, Long)}
   */
  @Test
  @DisplayName("Test setActiveVersion(String, Long); then calls saveAndFlush(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowManagementService.setActiveVersion(String, Long)"})
  void testSetActiveVersion_thenCallsSaveAndFlush() {
    // Arrange
    when(workflowEngine.deploy(Mockito.<Workflow>any())).thenReturn("Deploy");

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setCreatedBy(1L);
    versionedWorkflow2.setDeploymentId("42");
    versionedWorkflow2.setDescription("The characteristics of someone or something");
    versionedWorkflow2.setEtag(1L);
    versionedWorkflow2.setId("42");
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("Swadl");
    versionedWorkflow2.setVersion(1L);
    versionedWorkflow2.setWorkflowId("42");

    VersionedWorkflow versionedWorkflow3 = new VersionedWorkflow();
    versionedWorkflow3.setActive(true);
    versionedWorkflow3.setCreatedBy(1L);
    versionedWorkflow3.setDeploymentId("42");
    versionedWorkflow3.setDescription("The characteristics of someone or something");
    versionedWorkflow3.setEtag(1L);
    versionedWorkflow3.setId("42");
    versionedWorkflow3.setPublished(true);
    versionedWorkflow3.setSwadl("Swadl");
    versionedWorkflow3.setVersion(1L);
    versionedWorkflow3.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow3);

    VersionedWorkflow versionedWorkflow4 = new VersionedWorkflow();
    versionedWorkflow4.setActive(true);
    versionedWorkflow4.setCreatedBy(1L);
    versionedWorkflow4.setDeploymentId("42");
    versionedWorkflow4.setDescription("The characteristics of someone or something");
    versionedWorkflow4.setEtag(1L);
    versionedWorkflow4.setId("42");
    versionedWorkflow4.setPublished(true);
    versionedWorkflow4.setSwadl("Swadl");
    versionedWorkflow4.setVersion(1L);
    versionedWorkflow4.setWorkflowId("42");
    when(versionedWorkflowRepository.saveAndFlush(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow4);
    when(versionedWorkflowRepository.save(Mockito.<VersionedWorkflow>any())).thenReturn(versionedWorkflow2);
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(ofResult2);
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

    // Act
    workflowManagementService.setActiveVersion("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(workflowEngine).deploy(isA(Workflow.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    verify(versionedWorkflowRepository).saveAndFlush(isA(VersionedWorkflow.class));
    verify(versionedWorkflowRepository).save(isA(VersionedWorkflow.class));
  }
}
