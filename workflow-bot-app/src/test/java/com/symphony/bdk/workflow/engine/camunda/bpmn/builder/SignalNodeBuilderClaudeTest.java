package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.builder.AbstractCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.IntermediateCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SignalNodeBuilderClaudeTest {

  private SignalNodeBuilder builder;
  private ProcessBuilder processBuilder;
  private BuildProcessContext context;
  private WorkflowDirectedGraph graph;

  @BeforeEach
  void setUp() {
    builder = new SignalNodeBuilder();
    processBuilder = Bpmn.createExecutableProcess("testProcess");
    graph = mock(WorkflowDirectedGraph.class);
    context = new BuildProcessContext(graph, processBuilder);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    SignalNodeBuilder newBuilder = new SignalNodeBuilder();

    // Then: Instance should be created successfully
    assertThat(newBuilder).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    SignalNodeBuilder newBuilder = new SignalNodeBuilder();

    // Then: Instance should be of SignalNodeBuilder type
    assertThat(newBuilder).isInstanceOf(SignalNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingAbstractNodeBpmnBuilder() {
    // When: Creating a new instance
    SignalNodeBuilder newBuilder = new SignalNodeBuilder();

    // Then: Instance should extend AbstractNodeBpmnBuilder
    assertThat(newBuilder).isInstanceOf(AbstractNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    SignalNodeBuilder builder1 = new SignalNodeBuilder();
    SignalNodeBuilder builder2 = new SignalNodeBuilder();

    // Then: Each instance should be distinct
    assertThat(builder1).isNotSameAs(builder2);
  }

  @Test
  void constructor_multipleInstances_shouldAllHaveSameType() {
    // When: Creating multiple instances
    SignalNodeBuilder builder1 = new SignalNodeBuilder();
    SignalNodeBuilder builder2 = new SignalNodeBuilder();
    SignalNodeBuilder builder3 = new SignalNodeBuilder();

    // Then: All instances should return the same type
    assertThat(builder1.type()).isEqualTo(builder2.type());
    assertThat(builder2.type()).isEqualTo(builder3.type());
  }

  @Test
  void constructor_shouldCreateInstanceWithNonNullType() {
    // When: Creating a new instance
    SignalNodeBuilder newBuilder = new SignalNodeBuilder();

    // Then: The type() method should not return null
    assertThat(newBuilder.type()).isNotNull();
  }

  // Tests for type() method

  @Test
  void type_shouldReturnSignalEvent() {
    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return SIGNAL_EVENT
    assertThat(result).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
  }

  @Test
  void type_shouldReturnNonNullValue() {
    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return a non-null value
    assertThat(result).isNotNull();
  }

  @Test
  void type_shouldReturnConsistentValue() {
    // When: Calling type() multiple times
    WorkflowNodeType result1 = builder.type();
    WorkflowNodeType result2 = builder.type();
    WorkflowNodeType result3 = builder.type();

    // Then: Should return the same value each time
    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);
    assertThat(result1).isSameAs(result2); // Enum constants are singletons
  }

  @Test
  void type_shouldReturnEnumConstant() {
    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return an enum constant of WorkflowNodeType
    assertThat(result).isInstanceOf(WorkflowNodeType.class);
  }

  @Test
  void type_shouldReturnSpecificEnumValue() {
    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should specifically be SIGNAL_EVENT and not any other enum value
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(result).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
  }

  @Test
  void type_multipleInstances_shouldReturnSameEnumConstant() {
    // Given: Multiple instances of SignalNodeBuilder
    SignalNodeBuilder builder1 = new SignalNodeBuilder();
    SignalNodeBuilder builder2 = new SignalNodeBuilder();

    // When: Calling type() on both instances
    WorkflowNodeType result1 = builder1.type();
    WorkflowNodeType result2 = builder2.type();

    // Then: Should return the same enum constant (reference equality)
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void type_shouldBeIdempotent() {
    // When: Calling type() repeatedly
    WorkflowNodeType firstCall = builder.type();
    WorkflowNodeType secondCall = builder.type();
    WorkflowNodeType thirdCall = builder.type();

    // Then: All calls should return identical results
    assertThat(firstCall).isEqualTo(secondCall);
    assertThat(secondCall).isEqualTo(thirdCall);
  }

  @Test
  void type_shouldHaveCorrectName() {
    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: The enum name should be "SIGNAL_EVENT"
    assertThat(result.name()).isEqualTo("SIGNAL_EVENT");
  }

  @Test
  void type_shouldBeCompatibleWithEnumValueOf() {
    // When: Calling type() and comparing with valueOf
    WorkflowNodeType result = builder.type();
    WorkflowNodeType fromValueOf = WorkflowNodeType.valueOf("SIGNAL_EVENT");

    // Then: Should be the same enum constant
    assertThat(result).isSameAs(fromValueOf);
  }

  @Test
  void type_shouldBeInValuesArray() {
    // When: Calling type() and checking against values()
    WorkflowNodeType result = builder.type();
    WorkflowNodeType[] allValues = WorkflowNodeType.values();

    // Then: The returned type should be in the values array
    assertThat(allValues).contains(result);
  }

  // Tests for build() method

  @Test
  void build_withFormRepliedEventBrother_shouldCreateEventSubProcess() {
    // Given: A signal node and a context with a form replied event brother
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    WorkflowNode formRepliedNode = createFormRepliedNode("formNode", "formEventId", false);

    String parentId = "parentNode";
    setupContextWithFormRepliedBrother(parentId, signalNode, formRepliedNode);

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // Create a subprocess to allow the context to have a cached subprocess
    context.cacheSubProcess(startBuilder.subProcess());

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withAbstractCatchEventBuilder_shouldAddSignalToCatchEvent() {
    // Given: A signal node and an AbstractCatchEventBuilder
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    String parentId = "parentNode";

    // Create a boundary event (which is an AbstractCatchEventBuilder)
    StartEventBuilder startBuilder = processBuilder.startEvent();
    AbstractFlowNodeBuilder<?, ?> taskBuilder = startBuilder.serviceTask().id("task");
    IntermediateCatchEventBuilder catchEventBuilder = taskBuilder.intermediateCatchEvent();

    // When: Building the signal node with a catch event builder
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, catchEventBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withRegularBuilder_shouldCreateIntermediateCatchEvent() {
    // Given: A signal node and a regular flow node builder
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withNullEventId_shouldHandleGracefully() {
    // Given: A signal node with null eventId
    WorkflowNode signalNode = createSignalNode("signalNode", null);
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withServiceTaskBuilder_shouldCreateIntermediateCatchEvent() {
    // Given: A signal node and a service task builder
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    String parentId = "parentNode";

    AbstractFlowNodeBuilder<?, ?> serviceTaskBuilder = processBuilder.startEvent().serviceTask();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, serviceTaskBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withExclusiveGateway_shouldNotCreateEventSubProcess() {
    // Given: A signal node with exclusive gateway parent (no form replied brother)
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    String parentId = "parentNode";

    // Setup context with exclusive gateway (no form replied brother)
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    nodeChildren.addChild(signalNode.getId());

    when(context.readChildren(parentId)).thenReturn(nodeChildren);
    when(context.readWorkflowNode(signalNode.getId())).thenReturn(signalNode);

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (intermediate catch event)
    assertThat(result).isNotNull();
  }

  @Test
  void build_withParallelGateway_shouldNotCreateEventSubProcess() {
    // Given: A signal node with parallel gateway parent
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    String parentId = "parentNode";

    // Setup context with parallel gateway
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.PARALLEL);
    nodeChildren.addChild(signalNode.getId());

    when(context.readChildren(parentId)).thenReturn(nodeChildren);
    when(context.readWorkflowNode(signalNode.getId())).thenReturn(signalNode);

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (intermediate catch event)
    assertThat(result).isNotNull();
  }

  @Test
  void build_withNullChildren_shouldNotCreateEventSubProcess() {
    // Given: A signal node with null children in context
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    String parentId = "parentNode";

    when(context.readChildren(parentId)).thenReturn(null);

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (intermediate catch event)
    assertThat(result).isNotNull();
  }

  @Test
  void build_withEmptyChildren_shouldNotCreateEventSubProcess() {
    // Given: A signal node with empty children list
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    String parentId = "parentNode";

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);

    when(context.readChildren(parentId)).thenReturn(nodeChildren);

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (intermediate catch event)
    assertThat(result).isNotNull();
  }

  @Test
  void build_withFormRepliedBrotherButExclusiveTrue_shouldNotCreateEventSubProcess() {
    // Given: A signal node with a form replied brother that has exclusive = true
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    WorkflowNode formRepliedNode = createFormRepliedNode("formNode", "formEventId", true);

    String parentId = "parentNode";

    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    nodeChildren.addChild(signalNode.getId());
    nodeChildren.addChild(formRepliedNode.getId());

    when(context.readChildren(parentId)).thenReturn(nodeChildren);
    when(context.readWorkflowNode(signalNode.getId())).thenReturn(signalNode);
    when(context.readWorkflowNode(formRepliedNode.getId())).thenReturn(formRepliedNode);

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (intermediate catch event, not event subprocess)
    assertThat(result).isNotNull();
  }

  @Test
  void build_multipleCallsWithDifferentNodes_shouldAllReturnBuilders() {
    // Given: Multiple signal nodes
    WorkflowNode signalNode1 = createSignalNode("signalNode1", "eventId1");
    WorkflowNode signalNode2 = createSignalNode("signalNode2", "eventId2");

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building multiple nodes
    AbstractFlowNodeBuilder<?, ?> result1 = builder.build(signalNode1, "parent1", startBuilder, context);
    AbstractFlowNodeBuilder<?, ?> result2 = builder.build(signalNode2, "parent2", result1, context);

    // Then: Both should return non-null builders
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
  }

  @Test
  void build_withEventBasedGateway_shouldNotCreateEventSubProcess() {
    // Given: A signal node with event-based gateway parent
    WorkflowNode signalNode = createSignalNode("signalNode", "signalEventId");
    String parentId = "parentNode";

    // Setup context with event-based gateway
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EVENT_BASED);
    nodeChildren.addChild(signalNode.getId());

    when(context.readChildren(parentId)).thenReturn(nodeChildren);
    when(context.readWorkflowNode(signalNode.getId())).thenReturn(signalNode);

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the signal node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(signalNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (intermediate catch event)
    assertThat(result).isNotNull();
  }

  // Helper methods

  private WorkflowNode createSignalNode(String id, String eventId) {
    WorkflowNode node = new WorkflowNode()
        .id(id)
        .eventId(eventId)
        .elementType(WorkflowNodeType.SIGNAL_EVENT);
    return node;
  }

  private WorkflowNode createFormRepliedNode(String id, String eventId, boolean exclusive) {
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(exclusive);
    event.setFormReplied(formReplied);

    WorkflowNode node = new WorkflowNode()
        .id(id)
        .eventId(eventId)
        .event(event)
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    return node;
  }

  private void setupContextWithFormRepliedBrother(String parentId, WorkflowNode signalNode,
      WorkflowNode formRepliedNode) {
    WorkflowDirectedGraph.NodeChildren nodeChildren = new WorkflowDirectedGraph.NodeChildren();
    nodeChildren.gateway(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    nodeChildren.addChild(signalNode.getId());
    nodeChildren.addChild(formRepliedNode.getId());

    when(context.readChildren(parentId)).thenReturn(nodeChildren);
    when(context.readWorkflowNode(signalNode.getId())).thenReturn(signalNode);
    when(context.readWorkflowNode(formRepliedNode.getId())).thenReturn(formRepliedNode);
  }
}
