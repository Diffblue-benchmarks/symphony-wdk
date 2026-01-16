package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkflowNodeBpmnBuilderRegistryClaudeTest {

  private WorkflowNodeBpmnBuilderRegistry registry;

  @BeforeEach
  void setUp() {
    // Default setup with empty list for tests that override it
    registry = null;
  }

  // Tests for constructor

  @Test
  void constructor_withEmptyList_shouldCreateInstanceSuccessfully() {
    // Given: An empty list of builders
    List<WorkflowNodeBpmnBuilder> builders = Collections.emptyList();

    // When: Creating registry with empty list
    WorkflowNodeBpmnBuilderRegistry result = new WorkflowNodeBpmnBuilderRegistry(builders);

    // Then: Instance should be created successfully
    assertThat(result).isNotNull();
  }

  @Test
  void constructor_withSingleBuilder_shouldCreateInstanceSuccessfully() {
    // Given: A list with a single builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY);
    List<WorkflowNodeBpmnBuilder> builders = Collections.singletonList(builder);

    // When: Creating registry
    WorkflowNodeBpmnBuilderRegistry result = new WorkflowNodeBpmnBuilderRegistry(builders);

    // Then: Instance should be created successfully
    assertThat(result).isNotNull();
  }

  @Test
  void constructor_withMultipleBuilders_shouldCreateInstanceSuccessfully() {
    // Given: A list with multiple builders
    WorkflowNodeBpmnBuilder builder1 = createMockBuilder(WorkflowNodeType.ACTIVITY);
    WorkflowNodeBpmnBuilder builder2 = createMockBuilder(WorkflowNodeType.TIMER_FIRED_EVENT);
    WorkflowNodeBpmnBuilder builder3 = createMockBuilder(WorkflowNodeType.SIGNAL_EVENT);
    List<WorkflowNodeBpmnBuilder> builders = Arrays.asList(builder1, builder2, builder3);

    // When: Creating registry
    WorkflowNodeBpmnBuilderRegistry result = new WorkflowNodeBpmnBuilderRegistry(builders);

    // Then: Instance should be created successfully
    assertThat(result).isNotNull();
  }

  @Test
  void constructor_withAllWorkflowNodeTypes_shouldCreateInstanceSuccessfully() {
    // Given: Builders for all WorkflowNodeType enum values
    List<WorkflowNodeBpmnBuilder> builders = new ArrayList<>();
    for (WorkflowNodeType type : WorkflowNodeType.values()) {
      builders.add(createMockBuilder(type));
    }

    // When: Creating registry
    WorkflowNodeBpmnBuilderRegistry result = new WorkflowNodeBpmnBuilderRegistry(builders);

    // Then: Instance should be created successfully
    assertThat(result).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // Given: A list of builders
    List<WorkflowNodeBpmnBuilder> builders = Collections.emptyList();

    // When: Creating registry
    WorkflowNodeBpmnBuilderRegistry result = new WorkflowNodeBpmnBuilderRegistry(builders);

    // Then: Instance should be of WorkflowNodeBpmnBuilderRegistry type
    assertThat(result).isInstanceOf(WorkflowNodeBpmnBuilderRegistry.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // Given: A list of builders
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY);
    List<WorkflowNodeBpmnBuilder> builders = Collections.singletonList(builder);

    // When: Creating multiple registry instances
    WorkflowNodeBpmnBuilderRegistry registry1 = new WorkflowNodeBpmnBuilderRegistry(builders);
    WorkflowNodeBpmnBuilderRegistry registry2 = new WorkflowNodeBpmnBuilderRegistry(builders);

    // Then: Each instance should be distinct
    assertThat(registry1).isNotSameAs(registry2);
  }

  @Test
  void constructor_withDuplicateBuilderTypes_shouldUseLastBuilder() {
    // Given: Multiple builders for the same type (last one should win)
    WorkflowNodeBpmnBuilder builder1 = createMockBuilder(WorkflowNodeType.ACTIVITY);
    WorkflowNodeBpmnBuilder builder2 = createMockBuilder(WorkflowNodeType.ACTIVITY);
    List<WorkflowNodeBpmnBuilder> builders = Arrays.asList(builder1, builder2);

    // When: Creating registry
    WorkflowNodeBpmnBuilderRegistry result = new WorkflowNodeBpmnBuilderRegistry(builders);

    // Then: Instance should be created and last builder should be registered
    assertThat(result).isNotNull();
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY);
    assertThat(result.getBuilder(node)).isSameAs(builder2);
  }

  @Test
  void constructor_withMutableList_shouldNotThrowException() {
    // Given: A mutable list of builders
    List<WorkflowNodeBpmnBuilder> builders = new ArrayList<>();
    builders.add(createMockBuilder(WorkflowNodeType.ACTIVITY));

    // When: Creating registry
    WorkflowNodeBpmnBuilderRegistry result = new WorkflowNodeBpmnBuilderRegistry(builders);

    // Then: Instance should be created successfully
    assertThat(result).isNotNull();
  }

  // Tests for getBuilder() method

  @Test
  void getBuilder_withRegisteredType_shouldReturnCorrectBuilder() {
    // Given: A registry with a registered builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY);

    // When: Getting builder for registered type
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_withUnregisteredType_shouldReturnNull() {
    // Given: A registry with no builders
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.emptyList());
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY);

    // When: Getting builder for unregistered type
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void getBuilder_withTimerFiredEvent_shouldReturnCorrectBuilder() {
    // Given: A registry with a TIMER_FIRED_EVENT builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.TIMER_FIRED_EVENT);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.TIMER_FIRED_EVENT);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_withSignalEvent_shouldReturnCorrectBuilder() {
    // Given: A registry with a SIGNAL_EVENT builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.SIGNAL_EVENT);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.SIGNAL_EVENT);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_withFormRepliedEvent_shouldReturnCorrectBuilder() {
    // Given: A registry with a FORM_REPLIED_EVENT builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.FORM_REPLIED_EVENT);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.FORM_REPLIED_EVENT);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_withActivityCompletedEvent_shouldReturnCorrectBuilder() {
    // Given: A registry with an ACTIVITY_COMPLETED_EVENT builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_withActivityFailedEvent_shouldReturnCorrectBuilder() {
    // Given: A registry with an ACTIVITY_FAILED_EVENT builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY_FAILED_EVENT);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_withActivityExpiredEvent_shouldReturnCorrectBuilder() {
    // Given: A registry with an ACTIVITY_EXPIRED_EVENT builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_withJoinActivity_shouldReturnCorrectBuilder() {
    // Given: A registry with a JOIN_ACTIVITY builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.JOIN_ACTIVITY);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.JOIN_ACTIVITY);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_withActivity_shouldReturnCorrectBuilder() {
    // Given: A registry with an ACTIVITY builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should return the correct builder
    assertThat(result).isSameAs(builder);
  }

  @Test
  void getBuilder_multipleCallsWithSameType_shouldReturnSameBuilder() {
    // Given: A registry with a builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY);

    // When: Getting builder multiple times
    WorkflowNodeBpmnBuilder result1 = registry.getBuilder(node);
    WorkflowNodeBpmnBuilder result2 = registry.getBuilder(node);
    WorkflowNodeBpmnBuilder result3 = registry.getBuilder(node);

    // Then: Should return the same builder instance each time
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
    assertThat(result1).isSameAs(builder);
  }

  @Test
  void getBuilder_withAllTypesRegistered_shouldReturnCorrectBuilderForEach() {
    // Given: A registry with builders for all types
    List<WorkflowNodeBpmnBuilder> builders = new ArrayList<>();
    for (WorkflowNodeType type : WorkflowNodeType.values()) {
      builders.add(createMockBuilder(type));
    }
    registry = new WorkflowNodeBpmnBuilderRegistry(builders);

    // When/Then: Each type should return its corresponding builder
    for (int i = 0; i < WorkflowNodeType.values().length; i++) {
      WorkflowNodeType type = WorkflowNodeType.values()[i];
      WorkflowNode node = createWorkflowNode(type);
      WorkflowNodeBpmnBuilder result = registry.getBuilder(node);
      assertThat(result).isSameAs(builders.get(i));
    }
  }

  @Test
  void getBuilder_withDifferentNodesOfSameType_shouldReturnSameBuilder() {
    // Given: A registry with a builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node1 = createWorkflowNode(WorkflowNodeType.ACTIVITY);
    WorkflowNode node2 = createWorkflowNode(WorkflowNodeType.ACTIVITY);

    // When: Getting builder for different nodes of the same type
    WorkflowNodeBpmnBuilder result1 = registry.getBuilder(node1);
    WorkflowNodeBpmnBuilder result2 = registry.getBuilder(node2);

    // Then: Should return the same builder for both
    assertThat(result1).isSameAs(result2);
    assertThat(result1).isSameAs(builder);
  }

  @Test
  void getBuilder_withMixOfRegisteredAndUnregisteredTypes_shouldHandleCorrectly() {
    // Given: A registry with only some types registered
    WorkflowNodeBpmnBuilder builder1 = createMockBuilder(WorkflowNodeType.ACTIVITY);
    WorkflowNodeBpmnBuilder builder2 = createMockBuilder(WorkflowNodeType.SIGNAL_EVENT);
    registry = new WorkflowNodeBpmnBuilderRegistry(Arrays.asList(builder1, builder2));

    // When/Then: Registered types should return builders, unregistered should return null
    WorkflowNode registeredNode1 = createWorkflowNode(WorkflowNodeType.ACTIVITY);
    WorkflowNode registeredNode2 = createWorkflowNode(WorkflowNodeType.SIGNAL_EVENT);
    WorkflowNode unregisteredNode = createWorkflowNode(WorkflowNodeType.TIMER_FIRED_EVENT);

    assertThat(registry.getBuilder(registeredNode1)).isSameAs(builder1);
    assertThat(registry.getBuilder(registeredNode2)).isSameAs(builder2);
    assertThat(registry.getBuilder(unregisteredNode)).isNull();
  }

  @Test
  void getBuilder_afterRegistrationOfMultipleTypes_shouldMaintainSeparateBuilders() {
    // Given: A registry with multiple different builders
    WorkflowNodeBpmnBuilder activityBuilder = createMockBuilder(WorkflowNodeType.ACTIVITY);
    WorkflowNodeBpmnBuilder timerBuilder = createMockBuilder(WorkflowNodeType.TIMER_FIRED_EVENT);
    WorkflowNodeBpmnBuilder signalBuilder = createMockBuilder(WorkflowNodeType.SIGNAL_EVENT);
    registry = new WorkflowNodeBpmnBuilderRegistry(Arrays.asList(activityBuilder, timerBuilder, signalBuilder));

    // When: Getting builders for different types
    WorkflowNode activityNode = createWorkflowNode(WorkflowNodeType.ACTIVITY);
    WorkflowNode timerNode = createWorkflowNode(WorkflowNodeType.TIMER_FIRED_EVENT);
    WorkflowNode signalNode = createWorkflowNode(WorkflowNodeType.SIGNAL_EVENT);

    // Then: Each type should return its own builder
    assertThat(registry.getBuilder(activityNode)).isSameAs(activityBuilder);
    assertThat(registry.getBuilder(timerNode)).isSameAs(timerBuilder);
    assertThat(registry.getBuilder(signalNode)).isSameAs(signalBuilder);
    assertThat(registry.getBuilder(activityNode)).isNotSameAs(timerBuilder);
    assertThat(registry.getBuilder(timerNode)).isNotSameAs(signalBuilder);
  }

  @Test
  void getBuilder_withNonNullNode_shouldNotReturnNull() {
    // Given: A registry with a registered builder
    WorkflowNodeBpmnBuilder builder = createMockBuilder(WorkflowNodeType.ACTIVITY);
    registry = new WorkflowNodeBpmnBuilderRegistry(Collections.singletonList(builder));
    WorkflowNode node = createWorkflowNode(WorkflowNodeType.ACTIVITY);

    // When: Getting builder
    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    // Then: Should not return null
    assertThat(result).isNotNull();
  }

  // Helper methods

  private WorkflowNodeBpmnBuilder createMockBuilder(WorkflowNodeType type) {
    WorkflowNodeBpmnBuilder builder = mock(WorkflowNodeBpmnBuilder.class);
    when(builder.type()).thenReturn(type);
    return builder;
  }

  private WorkflowNode createWorkflowNode(WorkflowNodeType type) {
    return new WorkflowNode().elementType(type);
  }
}
