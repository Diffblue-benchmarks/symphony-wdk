package com.symphony.bdk.workflow.engine.camunda.bpmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.w3c.dom.Document;

@ContextConfiguration(classes = {BuildProcessContext.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
class BuildProcessContextDiffblueTest {
  @Autowired
  private BuildProcessContext buildProcessContext;

  @MockBean
  private ProcessBuilder processBuilder;

  @MockBean
  private WorkflowDirectedGraph workflowDirectedGraph;

  /**
   * Method under test:
   * {@link BuildProcessContext#addLastNodeBuilder(AbstractFlowNodeBuilder)}
   */
  @Test
  void testAddLastNodeBuilder() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);

    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    BoundaryEventBuilder builder = new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(
        new ModelTypeInstanceContext(domElement, model2, new ModelElementTypeImpl(null, "Name", instanceType))));

    // Act
    buildProcessContext.addLastNodeBuilder(builder);

    // Assert
    assertSame(builder, buildProcessContext.getLastNodeBuilder());
  }

  /**
   * Method under test: {@link BuildProcessContext#getLastNodeBuilder()}
   */
  @Test
  void testGetLastNodeBuilder() {
    // Arrange, Act and Assert
    assertNull(buildProcessContext.getLastNodeBuilder());
  }

  /**
   * Method under test: {@link BuildProcessContext#getNodeBuilder(String)}
   */
  @Test
  void testGetNodeBuilder() {
    // Arrange, Act and Assert
    assertNull(buildProcessContext.getNodeBuilder("42"));
  }

  /**
   * Method under test: {@link BuildProcessContext#getNodeBuilder(String)}
   */
  @Test
  void testGetNodeBuilder2() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode("foo"));
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");
    ModelInstanceImpl model3 = new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(mock(Document.class)));

    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    StartEventBuilder startEventBuilder = new StartEventBuilder(modelInstance, new StartEventImpl(
        new ModelTypeInstanceContext(domElement, model3, new ModelElementTypeImpl(model4, "Name", instanceType))));

    when(processBuilder.startEvent()).thenReturn(startEventBuilder);

    // Act
    AbstractFlowNodeBuilder<?, ?> actualNodeBuilder = buildProcessContext.getNodeBuilder("");

    // Assert
    verify(processBuilder).startEvent();
    assertSame(startEventBuilder, buildProcessContext.getLastNodeBuilder());
    assertSame(startEventBuilder, actualNodeBuilder);
  }

  /**
   * Method under test: {@link BuildProcessContext#isAlreadyBuilt(String)}
   */
  @Test
  void testIsAlreadyBuilt() {
    // Arrange, Act and Assert
    assertFalse(buildProcessContext.isAlreadyBuilt("42"));
  }

  /**
   * Method under test:
   * {@link BuildProcessContext#cacheSubProcess(SubProcessBuilder)}
   */
  @Test
  void testCacheSubProcess() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);

    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    SubProcessBuilder builder = new SubProcessBuilder(modelInstance, new SubProcessImpl(
        new ModelTypeInstanceContext(domElement, model2, new ModelElementTypeImpl(null, "Name", instanceType))));

    // Act
    buildProcessContext.cacheSubProcess(builder);

    // Assert
    assertSame(builder, buildProcessContext.getLastSubProcessBuilder());
  }

  /**
   * Method under test:
   * {@link BuildProcessContext#cacheEventSubProcessToDone(EventSubProcessBuilder)}
   */
  @Test
  void testCacheEventSubProcessToDone() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);

    Class<ModelElementInstance> instanceType = ModelElementInstance.class;

    // Act
    buildProcessContext.cacheEventSubProcessToDone(new EventSubProcessBuilder(modelInstance, new SubProcessImpl(
        new ModelTypeInstanceContext(domElement, model2, new ModelElementTypeImpl(null, "Name", instanceType)))));

    // Assert
    assertTrue(buildProcessContext.hasEventSubProcess());
  }

  /**
   * Method under test:
   * {@link BuildProcessContext#cacheSubProcessTimeoutToDone(SubProcessBuilder)}
   */
  @Test
  void testCacheSubProcessTimeoutToDone() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);

    Class<ModelElementInstance> instanceType = ModelElementInstance.class;

    // Act
    buildProcessContext.cacheSubProcessTimeoutToDone(new SubProcessBuilder(modelInstance, new SubProcessImpl(
        new ModelTypeInstanceContext(domElement, model2, new ModelElementTypeImpl(null, "Name", instanceType)))));

    // Assert
    assertTrue(buildProcessContext.hasTimeoutSubProcess());
  }

  /**
   * Method under test: {@link BuildProcessContext#hasEventSubProcess()}
   */
  @Test
  void testHasEventSubProcess() {
    // Arrange, Act and Assert
    assertFalse(buildProcessContext.hasEventSubProcess());
  }

  /**
   * Method under test: {@link BuildProcessContext#hasTimeoutSubProcess()}
   */
  @Test
  void testHasTimeoutSubProcess() {
    // Arrange, Act and Assert
    assertFalse(buildProcessContext.hasTimeoutSubProcess());
  }

  /**
   * Method under test: {@link BuildProcessContext#addParent(String, String)}
   */
  @Test
  void testAddParent() {
    // Arrange
    doNothing().when(workflowDirectedGraph).addParent(Mockito.<String>any(), Mockito.<String>any());

    // Act
    buildProcessContext.addParent("42", "Parent");

    // Assert that nothing has changed
    verify(workflowDirectedGraph).addParent(eq("42"), eq("Parent"));
  }

  /**
   * Method under test: {@link BuildProcessContext#addStartEvent(String)}
   */
  @Test
  void testAddStartEvent() {
    // Arrange
    doNothing().when(workflowDirectedGraph).addStartEvent(Mockito.<String>any());

    // Act
    buildProcessContext.addStartEvent("Start Event");

    // Assert that nothing has changed
    verify(workflowDirectedGraph).addStartEvent(eq("Start Event"));
  }

  /**
   * Method under test: {@link BuildProcessContext#getChildren(String)}
   */
  @Test
  void testGetChildren() {
    // Arrange
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(nodeChildren);

    // Act
    WorkflowDirectedGraph.NodeChildren actualChildren = buildProcessContext.getChildren("42");

    // Assert
    verify(workflowDirectedGraph).getChildren(eq("42"));
    assertSame(nodeChildren, actualChildren);
  }

  /**
   * Method under test: {@link BuildProcessContext#getDictionary()}
   */
  @Test
  void testGetDictionary() {
    // Arrange
    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);

    // Act
    Map<String, WorkflowNode> actualDictionary = buildProcessContext.getDictionary();

    // Assert
    verify(workflowDirectedGraph).getDictionary();
    assertTrue(actualDictionary.isEmpty());
    assertSame(stringWorkflowNodeMap, actualDictionary);
  }

  /**
   * Method under test: {@link BuildProcessContext#getParents()}
   */
  @Test
  void testGetParents() {
    // Arrange
    HashMap<String, Set<String>> stringSetMap = new HashMap<>();
    when(workflowDirectedGraph.getParents()).thenReturn(stringSetMap);

    // Act
    Map<String, Set<String>> actualParents = buildProcessContext.getParents();

    // Assert
    verify(workflowDirectedGraph).getParents();
    assertTrue(actualParents.isEmpty());
    assertSame(stringSetMap, actualParents);
  }

  /**
   * Method under test: {@link BuildProcessContext#getParents(String)}
   */
  @Test
  void testGetParents2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(stringList);

    // Act
    List<String> actualParents = buildProcessContext.getParents("42");

    // Assert
    verify(workflowDirectedGraph).getParents(eq("42"));
    assertTrue(actualParents.isEmpty());
    assertSame(stringList, actualParents);
  }

  /**
   * Method under test: {@link BuildProcessContext#getStartEvents()}
   */
  @Test
  void testGetStartEvents() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    when(workflowDirectedGraph.getStartEvents()).thenReturn(stringList);

    // Act
    List<String> actualStartEvents = buildProcessContext.getStartEvents();

    // Assert
    verify(workflowDirectedGraph).getStartEvents();
    assertTrue(actualStartEvents.isEmpty());
    assertSame(stringList, actualStartEvents);
  }

  /**
   * Method under test: {@link BuildProcessContext#getVariables()}
   */
  @Test
  void testGetVariables() {
    // Arrange
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);

    // Act
    Map<String, Object> actualVariables = buildProcessContext.getVariables();

    // Assert
    verify(workflowDirectedGraph).getVariables();
    assertTrue(actualVariables.isEmpty());
    assertSame(stringObjectMap, actualVariables);
  }

  /**
   * Method under test: {@link BuildProcessContext#getVersion()}
   */
  @Test
  void testGetVersion() {
    // Arrange
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);

    // Act
    Long actualVersion = buildProcessContext.getVersion();

    // Assert
    verify(workflowDirectedGraph).getVersion();
    assertEquals(1L, actualVersion.longValue());
  }

  /**
   * Method under test: {@link BuildProcessContext#getWorkflowId()}
   */
  @Test
  void testGetWorkflowId() {
    // Arrange
    when(workflowDirectedGraph.getWorkflowId()).thenReturn("42");

    // Act
    String actualWorkflowId = buildProcessContext.getWorkflowId();

    // Assert
    verify(workflowDirectedGraph).getWorkflowId();
    assertEquals("42", actualWorkflowId);
  }

  /**
   * Method under test: {@link BuildProcessContext#hasSeenBefore(String)}
   */
  @Test
  void testHasSeenBefore() {
    // Arrange
    when(workflowDirectedGraph.hasSeenBefore(Mockito.<String>any())).thenReturn(true);

    // Act
    boolean actualHasSeenBeforeResult = buildProcessContext.hasSeenBefore("42");

    // Assert
    verify(workflowDirectedGraph).hasSeenBefore(eq("42"));
    assertTrue(actualHasSeenBeforeResult);
  }

  /**
   * Method under test: {@link BuildProcessContext#hasSeenBefore(String)}
   */
  @Test
  void testHasSeenBefore2() {
    // Arrange
    when(workflowDirectedGraph.hasSeenBefore(Mockito.<String>any())).thenReturn(false);

    // Act
    boolean actualHasSeenBeforeResult = buildProcessContext.hasSeenBefore("42");

    // Assert
    verify(workflowDirectedGraph).hasSeenBefore(eq("42"));
    assertFalse(actualHasSeenBeforeResult);
  }

  /**
   * Method under test: {@link BuildProcessContext#isRegistered(String)}
   */
  @Test
  void testIsRegistered() {
    // Arrange
    when(workflowDirectedGraph.isRegistered(Mockito.<String>any())).thenReturn(true);

    // Act
    boolean actualIsRegisteredResult = buildProcessContext.isRegistered("42");

    // Assert
    verify(workflowDirectedGraph).isRegistered(eq("42"));
    assertTrue(actualIsRegisteredResult);
  }

  /**
   * Method under test: {@link BuildProcessContext#isRegistered(String)}
   */
  @Test
  void testIsRegistered2() {
    // Arrange
    when(workflowDirectedGraph.isRegistered(Mockito.<String>any())).thenReturn(false);

    // Act
    boolean actualIsRegisteredResult = buildProcessContext.isRegistered("42");

    // Assert
    verify(workflowDirectedGraph).isRegistered(eq("42"));
    assertFalse(actualIsRegisteredResult);
  }

  /**
   * Method under test:
   * {@link BuildProcessContext#BuildProcessContext(WorkflowDirectedGraph, ProcessBuilder)}
   */
  @Test
  void testNewBuildProcessContext() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);

    Class<ModelElementInstance> instanceType = ModelElementInstance.class;

    // Act
    BuildProcessContext actualBuildProcessContext = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance, new ProcessImpl(
            new ModelTypeInstanceContext(domElement, model2, new ModelElementTypeImpl(null, "Name", instanceType)))));

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
   * Method under test: {@link BuildProcessContext#readChildren(String)}
   */
  @Test
  void testReadChildren() {
    // Arrange
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    when(workflowDirectedGraph.readChildren(Mockito.<String>any())).thenReturn(nodeChildren);

    // Act
    WorkflowDirectedGraph.NodeChildren actualReadChildrenResult = buildProcessContext.readChildren("42");

    // Assert
    verify(workflowDirectedGraph).readChildren(eq("42"));
    assertSame(nodeChildren, actualReadChildrenResult);
  }

  /**
   * Method under test: {@link BuildProcessContext#readWorkflowNode(String)}
   */
  @Test
  void testReadWorkflowNode() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    when(workflowDirectedGraph.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);

    // Act
    WorkflowNode actualReadWorkflowNodeResult = buildProcessContext.readWorkflowNode("42");

    // Assert
    verify(workflowDirectedGraph).readWorkflowNode(eq("42"));
    assertSame(workflowNode, actualReadWorkflowNodeResult);
  }

  /**
   * Method under test:
   * {@link BuildProcessContext#registerToDictionary(String, WorkflowNode)}
   */
  @Test
  void testRegisterToDictionary() {
    // Arrange
    doNothing().when(workflowDirectedGraph).registerToDictionary(Mockito.<String>any(), Mockito.<WorkflowNode>any());

    // Act
    buildProcessContext.registerToDictionary("42", new WorkflowNode());

    // Assert that nothing has changed
    verify(workflowDirectedGraph).registerToDictionary(eq("42"), isA(WorkflowNode.class));
  }
}
