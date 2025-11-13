package com.symphony.bdk.workflow.engine.camunda.bpmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import javax.imageio.metadata.IIOMetadataNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.bpmn.impl.instance.BoundaryEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.ProcessImpl;
import org.camunda.bpm.model.bpmn.impl.instance.StartEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.SubProcessImpl;
import org.camunda.bpm.model.xml.impl.ModelBuilderImpl;
import org.camunda.bpm.model.xml.impl.ModelImpl;
import org.camunda.bpm.model.xml.impl.ModelInstanceImpl;
import org.camunda.bpm.model.xml.impl.instance.DomDocumentImpl;
import org.camunda.bpm.model.xml.impl.instance.DomElementImpl;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BuildProcessContext.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class BuildProcessContextDiffblueTest {
  @Autowired private BuildProcessContext buildProcessContext;

  @MockBean private ProcessBuilder processBuilder;

  @MockBean private WorkflowDirectedGraph workflowDirectedGraph;

  /**
   * Test {@link BuildProcessContext#BuildProcessContext(WorkflowDirectedGraph, ProcessBuilder)}.
   *
   * <p>Method under test: {@link BuildProcessContext#BuildProcessContext(WorkflowDirectedGraph,
   * ProcessBuilder)}
   */
  @Test
  @DisplayName("Test new BuildProcessContext(WorkflowDirectedGraph, ProcessBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BuildProcessContext.<init>(WorkflowDirectedGraph, ProcessBuilder)"})
  void testNewBuildProcessContext() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    // Act
    BuildProcessContext actualBuildProcessContext = new BuildProcessContext(workflowGraph, process);

    // Assert
    assertEquals("42", actualBuildProcessContext.getWorkflowId());
    assertNull(actualBuildProcessContext.getVersion());
    assertNull(actualBuildProcessContext.getLastNodeBuilder());
    assertFalse(actualBuildProcessContext.hasEventSubProcess());
    assertFalse(actualBuildProcessContext.hasTimeoutSubProcess());
    assertTrue(actualBuildProcessContext.getStartEvents().isEmpty());
    assertTrue(actualBuildProcessContext.getDictionary().isEmpty());
    assertTrue(actualBuildProcessContext.getParents().isEmpty());
    assertTrue(actualBuildProcessContext.getVariables().isEmpty());
  }

  /**
   * Test {@link BuildProcessContext#addLastNodeBuilder(AbstractFlowNodeBuilder)}.
   *
   * <p>Method under test: {@link BuildProcessContext#addLastNodeBuilder(AbstractFlowNodeBuilder)}
   */
  @Test
  @DisplayName("Test addLastNodeBuilder(AbstractFlowNodeBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BuildProcessContext.addLastNodeBuilder(AbstractFlowNodeBuilder)"})
  void testAddLastNodeBuilder() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));

    // Act
    buildProcessContext.addLastNodeBuilder(builder);

    // Assert
    assertSame(builder, buildProcessContext.getLastNodeBuilder());
  }

  /**
   * Test {@link BuildProcessContext#getLastNodeBuilder()}.
   *
   * <p>Method under test: {@link BuildProcessContext#getLastNodeBuilder()}
   */
  @Test
  @DisplayName("Test getLastNodeBuilder()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractFlowNodeBuilder BuildProcessContext.getLastNodeBuilder()"})
  void testGetLastNodeBuilder() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    ModelInstanceImpl model3 =
        new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(null));
    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;

    ModelElementTypeImpl modelType = new ModelElementTypeImpl(model4, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model3, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));
    buildProcessContext.addNodeBuilder("last", builder);
    buildProcessContext.addNodeBuilder("", null);

    // Act and Assert
    assertSame(builder, buildProcessContext.getLastNodeBuilder());
  }

  /**
   * Test {@link BuildProcessContext#getNodeBuilder(String)}.
   *
   * <ul>
   *   <li>Given {@link ProcessBuilder}.
   *   <li>When {@code 42}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BuildProcessContext#getNodeBuilder(String)}
   */
  @Test
  @DisplayName("Test getNodeBuilder(String); given ProcessBuilder; when '42'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractFlowNodeBuilder BuildProcessContext.getNodeBuilder(String)"})
  void testGetNodeBuilder_givenProcessBuilder_when42_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(buildProcessContext.getNodeBuilder("42"));
  }

  /**
   * Test {@link BuildProcessContext#getNodeBuilder(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BuildProcessContext#getNodeBuilder(String)}
   */
  @Test
  @DisplayName("Test getNodeBuilder(String); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AbstractFlowNodeBuilder BuildProcessContext.getNodeBuilder(String)"})
  void testGetNodeBuilder_whenNull() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    ModelInstanceImpl model3 =
        new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(null));
    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;

    ModelElementTypeImpl modelType = new ModelElementTypeImpl(model4, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model3, modelType);
    StartEventBuilder startEventBuilder =
        new StartEventBuilder(modelInstance, new StartEventImpl(context));
    when(processBuilder.startEvent()).thenReturn(startEventBuilder);
    buildProcessContext.addNodeBuilder("", null);

    // Act
    AbstractFlowNodeBuilder<?, ?> actualNodeBuilder = buildProcessContext.getNodeBuilder(null);

    // Assert
    verify(processBuilder).startEvent();
    assertSame(startEventBuilder, buildProcessContext.getLastNodeBuilder());
    assertSame(startEventBuilder, actualNodeBuilder);
  }

  /**
   * Test {@link BuildProcessContext#isAlreadyBuilt(String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with workflowId is
   *       {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BuildProcessContext#isAlreadyBuilt(String)}
   */
  @Test
  @DisplayName(
      "Test isAlreadyBuilt(String); given WorkflowDirectedGraph(String) with workflowId is '42'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BuildProcessContext.isAlreadyBuilt(String)"})
  void testIsAlreadyBuilt_givenWorkflowDirectedGraphWithWorkflowIdIs42_thenReturnTrue() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext buildProcessContext = new BuildProcessContext(workflowGraph, process);
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance2 =
        new BpmnModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model3 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context2 = new ModelTypeInstanceContext(domElement, model3, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance2, new BoundaryEventImpl(context2));
    buildProcessContext.addNodeBuilder("42", builder);

    // Act and Assert
    assertTrue(buildProcessContext.isAlreadyBuilt("42"));
  }

  /**
   * Test {@link BuildProcessContext#isAlreadyBuilt(String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowDirectedGraph}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BuildProcessContext#isAlreadyBuilt(String)}
   */
  @Test
  @DisplayName("Test isAlreadyBuilt(String); given WorkflowDirectedGraph; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BuildProcessContext.isAlreadyBuilt(String)"})
  void testIsAlreadyBuilt_givenWorkflowDirectedGraph_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(buildProcessContext.isAlreadyBuilt("42"));
  }

  /**
   * Test {@link BuildProcessContext#cacheSubProcess(SubProcessBuilder)}.
   *
   * <p>Method under test: {@link BuildProcessContext#cacheSubProcess(SubProcessBuilder)}
   */
  @Test
  @DisplayName("Test cacheSubProcess(SubProcessBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BuildProcessContext.cacheSubProcess(SubProcessBuilder)"})
  void testCacheSubProcess() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    SubProcessBuilder builder = new SubProcessBuilder(modelInstance, new SubProcessImpl(context));

    // Act
    buildProcessContext.cacheSubProcess(builder);

    // Assert
    assertSame(builder, buildProcessContext.getLastSubProcessBuilder());
  }

  /**
   * Test {@link BuildProcessContext#cacheEventSubProcessToDone(EventSubProcessBuilder)}.
   *
   * <p>Method under test: {@link
   * BuildProcessContext#cacheEventSubProcessToDone(EventSubProcessBuilder)}
   */
  @Test
  @DisplayName("Test cacheEventSubProcessToDone(EventSubProcessBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BuildProcessContext.cacheEventSubProcessToDone(EventSubProcessBuilder)"})
  void testCacheEventSubProcessToDone() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    EventSubProcessBuilder builder =
        new EventSubProcessBuilder(modelInstance, new SubProcessImpl(context));

    // Act
    buildProcessContext.cacheEventSubProcessToDone(builder);

    // Assert
    assertTrue(buildProcessContext.hasEventSubProcess());
  }

  /**
   * Test {@link BuildProcessContext#cacheSubProcessTimeoutToDone(SubProcessBuilder)}.
   *
   * <p>Method under test: {@link
   * BuildProcessContext#cacheSubProcessTimeoutToDone(SubProcessBuilder)}
   */
  @Test
  @DisplayName("Test cacheSubProcessTimeoutToDone(SubProcessBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BuildProcessContext.cacheSubProcessTimeoutToDone(SubProcessBuilder)"})
  void testCacheSubProcessTimeoutToDone() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    SubProcessBuilder builder = new SubProcessBuilder(modelInstance, new SubProcessImpl(context));

    // Act
    buildProcessContext.cacheSubProcessTimeoutToDone(builder);

    // Assert
    assertTrue(buildProcessContext.hasTimeoutSubProcess());
  }

  /**
   * Test {@link BuildProcessContext#hasEventSubProcess()}.
   *
   * <p>Method under test: {@link BuildProcessContext#hasEventSubProcess()}
   */
  @Test
  @DisplayName("Test hasEventSubProcess()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BuildProcessContext.hasEventSubProcess()"})
  void testHasEventSubProcess() {
    // Arrange, Act and Assert
    assertFalse(buildProcessContext.hasEventSubProcess());
  }

  /**
   * Test {@link BuildProcessContext#hasTimeoutSubProcess()}.
   *
   * <p>Method under test: {@link BuildProcessContext#hasTimeoutSubProcess()}
   */
  @Test
  @DisplayName("Test hasTimeoutSubProcess()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BuildProcessContext.hasTimeoutSubProcess()"})
  void testHasTimeoutSubProcess() {
    // Arrange, Act and Assert
    assertFalse(buildProcessContext.hasTimeoutSubProcess());
  }
}
