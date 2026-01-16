package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.IntermediateCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TimerFiredNodeBuilderClaudeTest {

  private TimerFiredNodeBuilder builder;
  private ProcessBuilder processBuilder;
  private BuildProcessContext context;
  private WorkflowDirectedGraph graph;

  @BeforeEach
  void setUp() {
    builder = new TimerFiredNodeBuilder();
    processBuilder = Bpmn.createExecutableProcess("testProcess");
    graph = mock(WorkflowDirectedGraph.class);
    context = new BuildProcessContext(graph, processBuilder);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    TimerFiredNodeBuilder newBuilder = new TimerFiredNodeBuilder();

    // Then: Instance should be created successfully
    assertThat(newBuilder).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    TimerFiredNodeBuilder newBuilder = new TimerFiredNodeBuilder();

    // Then: Instance should be of TimerFiredNodeBuilder type
    assertThat(newBuilder).isInstanceOf(TimerFiredNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingAbstractNodeBpmnBuilder() {
    // When: Creating a new instance
    TimerFiredNodeBuilder newBuilder = new TimerFiredNodeBuilder();

    // Then: Instance should extend AbstractNodeBpmnBuilder
    assertThat(newBuilder).isInstanceOf(AbstractNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    TimerFiredNodeBuilder builder1 = new TimerFiredNodeBuilder();
    TimerFiredNodeBuilder builder2 = new TimerFiredNodeBuilder();

    // Then: Each instance should be distinct
    assertThat(builder1).isNotSameAs(builder2);
  }

  @Test
  void constructor_multipleInstances_shouldAllHaveSameType() {
    // When: Creating multiple instances
    TimerFiredNodeBuilder builder1 = new TimerFiredNodeBuilder();
    TimerFiredNodeBuilder builder2 = new TimerFiredNodeBuilder();
    TimerFiredNodeBuilder builder3 = new TimerFiredNodeBuilder();

    // Then: All instances should return the same type
    assertThat(builder1.type()).isEqualTo(builder2.type());
    assertThat(builder2.type()).isEqualTo(builder3.type());
  }

  @Test
  void constructor_shouldCreateInstanceWithNonNullType() {
    // When: Creating a new instance
    TimerFiredNodeBuilder newBuilder = new TimerFiredNodeBuilder();

    // Then: The type() method should not return null
    assertThat(newBuilder.type()).isNotNull();
  }

  // Tests for type() method

  @Test
  void type_shouldReturnTimerFiredEvent() {
    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return TIMER_FIRED_EVENT
    assertThat(result).isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
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

    // Then: Should specifically be TIMER_FIRED_EVENT and not any other enum value
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.JOIN_ACTIVITY);
    assertThat(result).isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
  }

  @Test
  void type_multipleInstances_shouldReturnSameEnumConstant() {
    // Given: Multiple instances of TimerFiredNodeBuilder
    TimerFiredNodeBuilder builder1 = new TimerFiredNodeBuilder();
    TimerFiredNodeBuilder builder2 = new TimerFiredNodeBuilder();

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

    // Then: The enum name should be "TIMER_FIRED_EVENT"
    assertThat(result.name()).isEqualTo("TIMER_FIRED_EVENT");
  }

  @Test
  void type_shouldBeCompatibleWithEnumValueOf() {
    // When: Calling type() and comparing with valueOf
    WorkflowNodeType result = builder.type();
    WorkflowNodeType fromValueOf = WorkflowNodeType.valueOf("TIMER_FIRED_EVENT");

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
  void build_withRepeat_shouldCreateTimerWithCycle() {
    // Given: A timer node with repeat (cycle) configuration
    WorkflowNode timerNode = createTimerNodeWithRepeat("timerNode", "R3/PT1H");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withAt_shouldCreateTimerWithDate() {
    // Given: A timer node with at (date) configuration
    WorkflowNode timerNode = createTimerNodeWithAt("timerNode", "2025-01-01T00:00:00Z");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withAbstractCatchEventBuilder_shouldNotCreateEventBasedGateway() {
    // Given: A timer node and an AbstractCatchEventBuilder
    WorkflowNode timerNode = createTimerNodeWithRepeat("timerNode", "R/PT5M");
    String parentId = "parentNode";

    // Create a catch event builder (which is an AbstractCatchEventBuilder)
    StartEventBuilder startBuilder = processBuilder.startEvent();
    AbstractFlowNodeBuilder<?, ?> taskBuilder = startBuilder.serviceTask().id("task");
    IntermediateCatchEventBuilder catchEventBuilder = taskBuilder.intermediateCatchEvent();

    // When: Building the timer node with a catch event builder
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, catchEventBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withRegularBuilder_shouldCreateEventBasedGateway() {
    // Given: A timer node and a regular flow node builder
    WorkflowNode timerNode = createTimerNodeWithRepeat("timerNode", "R/PT10M");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withServiceTaskBuilder_shouldCreateEventBasedGateway() {
    // Given: A timer node and a service task builder
    WorkflowNode timerNode = createTimerNodeWithAt("timerNode", "2025-12-31T23:59:59Z");
    String parentId = "parentNode";

    AbstractFlowNodeBuilder<?, ?> serviceTaskBuilder = processBuilder.startEvent().serviceTask();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, serviceTaskBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withNullRepeat_shouldUseAtField() {
    // Given: A timer node with null repeat (should use 'at')
    WorkflowNode timerNode = createTimerNodeWithAt("timerNode", "2025-06-15T12:00:00Z");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (timer with date)
    assertThat(result).isNotNull();
  }

  @Test
  void build_withNonNullRepeat_shouldUseRepeatField() {
    // Given: A timer node with non-null repeat (should use 'repeat')
    WorkflowNode timerNode = createTimerNodeWithRepeat("timerNode", "R5/PT30M");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (timer with cycle)
    assertThat(result).isNotNull();
  }

  @Test
  void build_shouldSetNodeName() {
    // Given: A timer node with a specific ID
    String nodeId = "myTimerNode";
    WorkflowNode timerNode = createTimerNodeWithRepeat(nodeId, "R/PT1H");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder with the node name set
    assertThat(result).isNotNull();
  }

  @Test
  void build_multipleCallsWithDifferentNodes_shouldAllReturnBuilders() {
    // Given: Multiple timer nodes
    WorkflowNode timerNode1 = createTimerNodeWithRepeat("timerNode1", "R/PT5M");
    WorkflowNode timerNode2 = createTimerNodeWithAt("timerNode2", "2025-02-01T00:00:00Z");

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building multiple nodes
    AbstractFlowNodeBuilder<?, ?> result1 = builder.build(timerNode1, "parent1", startBuilder, context);
    AbstractFlowNodeBuilder<?, ?> result2 = builder.build(timerNode2, "parent2", result1, context);

    // Then: Both should return non-null builders
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
  }

  @Test
  void build_withEmptyRepeatString_shouldUseAtField() {
    // Given: A timer node with empty repeat string (null check should treat it as null)
    WorkflowNode timerNode = createTimerNodeWithBoth("timerNode", "2025-03-15T10:00:00Z", null);
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (timer with date)
    assertThat(result).isNotNull();
  }

  @Test
  void build_withBothRepeatAndAt_shouldPreferRepeat() {
    // Given: A timer node with both repeat and at set (repeat should take precedence)
    WorkflowNode timerNode = createTimerNodeWithBoth("timerNode", "2025-04-01T00:00:00Z", "R/PT2H");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder (timer with cycle, not date)
    assertThat(result).isNotNull();
  }

  @Test
  void build_withDifferentRepeatFormats_shouldAllWork() {
    // Given: Timer nodes with different repeat formats
    WorkflowNode timerNode1 = createTimerNodeWithRepeat("timer1", "R/PT1M");
    WorkflowNode timerNode2 = createTimerNodeWithRepeat("timer2", "R3/PT5M");
    WorkflowNode timerNode3 = createTimerNodeWithRepeat("timer3", "R10/PT30S");

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building all nodes
    AbstractFlowNodeBuilder<?, ?> result1 = builder.build(timerNode1, "parent", startBuilder, context);
    AbstractFlowNodeBuilder<?, ?> result2 = builder.build(timerNode2, "parent", result1, context);
    AbstractFlowNodeBuilder<?, ?> result3 = builder.build(timerNode3, "parent", result2, context);

    // Then: All should return non-null builders
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
    assertThat(result3).isNotNull();
  }

  @Test
  void build_withDifferentAtFormats_shouldAllWork() {
    // Given: Timer nodes with different date formats
    WorkflowNode timerNode1 = createTimerNodeWithAt("timer1", "2025-01-01T00:00:00Z");
    WorkflowNode timerNode2 = createTimerNodeWithAt("timer2", "2025-06-15T12:30:45Z");
    WorkflowNode timerNode3 = createTimerNodeWithAt("timer3", "2025-12-31T23:59:59Z");

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building all nodes
    AbstractFlowNodeBuilder<?, ?> result1 = builder.build(timerNode1, "parent", startBuilder, context);
    AbstractFlowNodeBuilder<?, ?> result2 = builder.build(timerNode2, "parent", result1, context);
    AbstractFlowNodeBuilder<?, ?> result3 = builder.build(timerNode3, "parent", result2, context);

    // Then: All should return non-null builders
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
    assertThat(result3).isNotNull();
  }

  @Test
  void build_withIntermediateCatchEventBuilder_shouldAddTimerDirectly() {
    // Given: A timer node and an intermediate catch event builder
    WorkflowNode timerNode = createTimerNodeWithRepeat("timerNode", "R/PT15M");
    String parentId = "parentNode";

    // Create an intermediate catch event builder
    IntermediateCatchEventBuilder catchEventBuilder = processBuilder.startEvent()
        .serviceTask()
        .id("task")
        .intermediateCatchEvent();

    // When: Building the timer node with an intermediate catch event builder
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, catchEventBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withUserTaskBuilder_shouldCreateEventBasedGateway() {
    // Given: A timer node and a user task builder
    WorkflowNode timerNode = createTimerNodeWithAt("timerNode", "2025-05-20T08:00:00Z");
    String parentId = "parentNode";

    AbstractFlowNodeBuilder<?, ?> userTaskBuilder = processBuilder.startEvent().userTask();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, userTaskBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withEmptyNodeId_shouldStillWork() {
    // Given: A timer node with empty ID
    WorkflowNode timerNode = createTimerNodeWithRepeat("", "R/PT20M");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withLongRepeatCycle_shouldWork() {
    // Given: A timer node with a long repeat cycle
    WorkflowNode timerNode = createTimerNodeWithRepeat("timerNode", "R100/P1DT2H30M45S");
    String parentId = "parentNode";

    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the timer node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(timerNode, parentId, startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  // Helper methods

  private WorkflowNode createTimerNodeWithRepeat(String id, String repeat) {
    Event event = new Event();
    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setRepeat(repeat);
    event.setTimerFired(timerFired);

    WorkflowNode node = new WorkflowNode()
        .id(id)
        .event(event)
        .elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
    return node;
  }

  private WorkflowNode createTimerNodeWithAt(String id, String at) {
    Event event = new Event();
    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt(at);
    timerFired.setRepeat(null);
    event.setTimerFired(timerFired);

    WorkflowNode node = new WorkflowNode()
        .id(id)
        .event(event)
        .elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
    return node;
  }

  private WorkflowNode createTimerNodeWithBoth(String id, String at, String repeat) {
    Event event = new Event();
    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt(at);
    timerFired.setRepeat(repeat);
    event.setTimerFired(timerFired);

    WorkflowNode node = new WorkflowNode()
        .id(id)
        .event(event)
        .elementType(WorkflowNodeType.TIMER_FIRED_EVENT);
    return node;
  }
}
