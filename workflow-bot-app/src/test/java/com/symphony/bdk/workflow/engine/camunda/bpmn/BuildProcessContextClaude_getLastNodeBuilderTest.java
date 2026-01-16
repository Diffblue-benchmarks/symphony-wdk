package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuildProcessContextClaude_getLastNodeBuilderTest {

  private BuildProcessContext context;
  private ProcessBuilder processBuilder;

  @BeforeEach
  void setUp() {
    // Create a real BpmnModelInstance and ProcessBuilder for testing
    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("testProcess").done();
    processBuilder = modelInstance.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();

    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    context = new BuildProcessContext(graph, processBuilder);
  }

  // Test 1: getLastNodeBuilder should return null when no builders are added
  @Test
  void getLastNodeBuilder_whenNoBuilders_shouldReturnNull() {
    // When: Getting last node builder without adding any
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return null
    assertThat(result).isNull();
  }

  // Test 2: getLastNodeBuilder should return the last node builder when added via addLastNodeBuilder
  @Test
  void getLastNodeBuilder_whenLastNodeBuilderAdded_shouldReturnIt() {
    // Given: A flow node builder added as last
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addLastNodeBuilder(builder);

    // When: Getting last node builder
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return the added builder
    assertThat(result).isEqualTo(builder);
  }

  // Test 3: getLastNodeBuilder should return empty string builder when last is null
  @Test
  void getLastNodeBuilder_whenLastIsNullButEmptyStringExists_shouldReturnEmptyString() {
    // Given: A builder added with empty string key (via getNodeBuilder)
    AbstractFlowNodeBuilder<?, ?> emptyStringBuilder = context.getNodeBuilder("");

    // When: Getting last node builder (without adding one with "last" key)
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return the empty string builder
    assertThat(result).isEqualTo(emptyStringBuilder);
  }

  // Test 4: getLastNodeBuilder should prefer last builder over empty string builder
  @Test
  void getLastNodeBuilder_whenBothLastAndEmptyStringExist_shouldPreferLast() {
    // Given: Both empty string builder and last builder exist
    AbstractFlowNodeBuilder<?, ?> emptyStringBuilder = context.getNodeBuilder("");
    AbstractFlowNodeBuilder<?, ?> lastBuilder = processBuilder.startEvent("lastEvent");
    context.addLastNodeBuilder(lastBuilder);

    // When: Getting last node builder
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return the last builder, not the empty string builder
    assertThat(result).isEqualTo(lastBuilder);
    assertThat(result).isNotEqualTo(emptyStringBuilder);
  }

  // Test 5: getLastNodeBuilder should return updated value after replacing last builder
  @Test
  void getLastNodeBuilder_afterReplacingLastBuilder_shouldReturnNewOne() {
    // Given: A last builder is added
    AbstractFlowNodeBuilder<?, ?> firstBuilder = processBuilder.startEvent("first");
    context.addLastNodeBuilder(firstBuilder);

    // When: Replacing it with a new last builder
    AbstractFlowNodeBuilder<?, ?> secondBuilder = processBuilder.startEvent("second");
    context.addLastNodeBuilder(secondBuilder);
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return the new builder
    assertThat(result).isEqualTo(secondBuilder);
    assertThat(result).isNotEqualTo(firstBuilder);
  }

  // Test 6: getLastNodeBuilder should return same instance on multiple calls
  @Test
  void getLastNodeBuilder_calledMultipleTimes_shouldReturnSameInstance() {
    // Given: A last builder is added
    AbstractFlowNodeBuilder<?, ?> builder = processBuilder.startEvent();
    context.addLastNodeBuilder(builder);

    // When: Getting last node builder multiple times
    AbstractFlowNodeBuilder<?, ?> result1 = context.getLastNodeBuilder();
    AbstractFlowNodeBuilder<?, ?> result2 = context.getLastNodeBuilder();
    AbstractFlowNodeBuilder<?, ?> result3 = context.getLastNodeBuilder();

    // Then: Should return the same instance each time
    assertThat(result1).isEqualTo(result2);
    assertThat(result2).isEqualTo(result3);
    assertThat(result1).isEqualTo(builder);
  }

  // Test 7: getLastNodeBuilder should work correctly with different builder types
  @Test
  void getLastNodeBuilder_withDifferentBuilderTypes_shouldWork() {
    // Given: Different types of builders (using method chaining)
    AbstractFlowNodeBuilder<?, ?> startEvent = processBuilder.startEvent("start");
    context.addLastNodeBuilder(startEvent);

    AbstractFlowNodeBuilder<?, ?> result1 = context.getLastNodeBuilder();
    assertThat(result1).isEqualTo(startEvent);

    // When: Adding a different type (service task after start event)
    AbstractFlowNodeBuilder<?, ?> serviceTask = startEvent.serviceTask("task1");
    context.addLastNodeBuilder(serviceTask);

    // Then: Should return the service task
    AbstractFlowNodeBuilder<?, ?> result2 = context.getLastNodeBuilder();
    assertThat(result2).isEqualTo(serviceTask);
  }

  // Test 8: getLastNodeBuilder interaction with addNodeBuilder should not affect last
  @Test
  void getLastNodeBuilder_afterAddingOtherNodeBuilders_shouldStillReturnLast() {
    // Given: A last builder is added
    AbstractFlowNodeBuilder<?, ?> lastBuilder = processBuilder.startEvent("last");
    context.addLastNodeBuilder(lastBuilder);

    // When: Adding other node builders with different IDs
    AbstractFlowNodeBuilder<?, ?> otherBuilder1 = processBuilder.startEvent("node1");
    AbstractFlowNodeBuilder<?, ?> otherBuilder2 = processBuilder.startEvent("node2");
    context.addNodeBuilder("node1", otherBuilder1);
    context.addNodeBuilder("node2", otherBuilder2);

    // Then: getLastNodeBuilder should still return the last builder
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();
    assertThat(result).isEqualTo(lastBuilder);
    assertThat(result).isNotEqualTo(otherBuilder1);
    assertThat(result).isNotEqualTo(otherBuilder2);
  }

  // Test 9: getLastNodeBuilder should handle null builder being added
  @Test
  void getLastNodeBuilder_whenNullBuilderAdded_shouldReturnNull() {
    // Given: A null builder is added as last
    context.addLastNodeBuilder(null);

    // When: Getting last node builder
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return null (since "last" key has null value, it falls back to "" which doesn't exist)
    assertThat(result).isNull();
  }

  // Test 10: getLastNodeBuilder should fall back correctly when last is explicitly null
  @Test
  void getLastNodeBuilder_whenLastIsNullAndEmptyStringExists_shouldReturnEmptyStringBuilder() {
    // Given: Empty string builder exists and last is set to null
    AbstractFlowNodeBuilder<?, ?> emptyStringBuilder = context.getNodeBuilder("");
    context.addLastNodeBuilder(null);

    // When: Getting last node builder
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return the empty string builder
    assertThat(result).isEqualTo(emptyStringBuilder);
  }

  // Test 11: getLastNodeBuilder behavior in fresh context matches expected default
  @Test
  void getLastNodeBuilder_inFreshContext_shouldReturnNull() {
    // Given: A fresh context (from setUp)

    // When: Getting last node builder
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return null (no builders added yet)
    assertThat(result).isNull();
  }

  // Test 12: getLastNodeBuilder should work after adding and getting empty string builder
  @Test
  void getLastNodeBuilder_afterGettingEmptyStringBuilder_shouldReturnIt() {
    // Given: Empty string builder is retrieved (which creates it)
    AbstractFlowNodeBuilder<?, ?> emptyBuilder = context.getNodeBuilder("");

    // When: Getting last node builder (no "last" was added)
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();

    // Then: Should return the empty string builder
    assertThat(result).isEqualTo(emptyBuilder);
    assertThat(result).isNotNull();
  }

  // Test 13: getLastNodeBuilder should handle sequence of operations
  @Test
  void getLastNodeBuilder_withSequenceOfOperations_shouldBehaveCorrectly() {
    // Given: Initial state - no builders
    assertThat(context.getLastNodeBuilder()).isNull();

    // When: Adding empty string builder
    AbstractFlowNodeBuilder<?, ?> emptyBuilder = context.getNodeBuilder("");
    // Then: Should return empty builder
    assertThat(context.getLastNodeBuilder()).isEqualTo(emptyBuilder);

    // When: Adding last builder
    AbstractFlowNodeBuilder<?, ?> lastBuilder = processBuilder.startEvent("last");
    context.addLastNodeBuilder(lastBuilder);
    // Then: Should return last builder
    assertThat(context.getLastNodeBuilder()).isEqualTo(lastBuilder);

    // When: Setting last to null
    context.addLastNodeBuilder(null);
    // Then: Should fall back to empty builder
    assertThat(context.getLastNodeBuilder()).isEqualTo(emptyBuilder);
  }

  // Test 14: getLastNodeBuilder should handle multiple context instances independently
  @Test
  void getLastNodeBuilder_multipleContexts_shouldBeIndependent() {
    // Given: Two different contexts
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    BpmnModelInstance modelInstance2 = Bpmn.createExecutableProcess("testProcess2").done();
    ProcessBuilder processBuilder2 = modelInstance2.getModelElementsByType(org.camunda.bpm.model.bpmn.instance.Process.class)
        .iterator().next().builder();
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);

    // When: Adding different last builders to each context
    AbstractFlowNodeBuilder<?, ?> builder1 = processBuilder.startEvent("event1");
    context.addLastNodeBuilder(builder1);

    AbstractFlowNodeBuilder<?, ?> builder2 = processBuilder2.startEvent("event2");
    context2.addLastNodeBuilder(builder2);

    // Then: Each context should return its own last builder
    assertThat(context.getLastNodeBuilder()).isEqualTo(builder1);
    assertThat(context2.getLastNodeBuilder()).isEqualTo(builder2);
    assertThat(context.getLastNodeBuilder()).isNotEqualTo(context2.getLastNodeBuilder());
  }

  // Test 15: getLastNodeBuilder should work with complex builder chain
  @Test
  void getLastNodeBuilder_withComplexBuilderChain_shouldWork() {
    // Given: A complex chain of builders
    AbstractFlowNodeBuilder<?, ?> start = processBuilder.startEvent("start");
    AbstractFlowNodeBuilder<?, ?> task1 = start.serviceTask("task1");
    AbstractFlowNodeBuilder<?, ?> gateway = task1.exclusiveGateway("gateway");
    AbstractFlowNodeBuilder<?, ?> task2 = gateway.serviceTask("task2");

    // When: Setting the end of the chain as last builder
    context.addLastNodeBuilder(task2);

    // Then: Should return the last builder in the chain
    AbstractFlowNodeBuilder<?, ?> result = context.getLastNodeBuilder();
    assertThat(result).isEqualTo(task2);
  }
}
