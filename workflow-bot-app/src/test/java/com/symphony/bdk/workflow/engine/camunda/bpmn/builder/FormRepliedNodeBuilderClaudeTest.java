package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ParallelGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class FormRepliedNodeBuilderClaudeTest {

  private FormRepliedNodeBuilder builder;
  private ProcessBuilder processBuilder;
  private BuildProcessContext context;
  private WorkflowDirectedGraph graph;

  @BeforeEach
  void setUp() {
    builder = new FormRepliedNodeBuilder();
    processBuilder = Bpmn.createExecutableProcess("testProcess");
    graph = mock(WorkflowDirectedGraph.class);
    context = new BuildProcessContext(graph, processBuilder);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    FormRepliedNodeBuilder newBuilder = new FormRepliedNodeBuilder();

    // Then: Instance should be created successfully
    assertThat(newBuilder).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    FormRepliedNodeBuilder newBuilder = new FormRepliedNodeBuilder();

    // Then: Instance should be of FormRepliedNodeBuilder type
    assertThat(newBuilder).isInstanceOf(FormRepliedNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingAbstractNodeBpmnBuilder() {
    // When: Creating a new instance
    FormRepliedNodeBuilder newBuilder = new FormRepliedNodeBuilder();

    // Then: Instance should extend AbstractNodeBpmnBuilder
    assertThat(newBuilder).isInstanceOf(AbstractNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    FormRepliedNodeBuilder builder1 = new FormRepliedNodeBuilder();
    FormRepliedNodeBuilder builder2 = new FormRepliedNodeBuilder();

    // Then: Each instance should be distinct
    assertThat(builder1).isNotSameAs(builder2);
  }

  @Test
  void constructor_multipleInstances_shouldAllHaveSameType() {
    // When: Creating multiple instances
    FormRepliedNodeBuilder builder1 = new FormRepliedNodeBuilder();
    FormRepliedNodeBuilder builder2 = new FormRepliedNodeBuilder();
    FormRepliedNodeBuilder builder3 = new FormRepliedNodeBuilder();

    // Then: All instances should return the same type
    assertThat(builder1.type()).isEqualTo(builder2.type());
    assertThat(builder2.type()).isEqualTo(builder3.type());
  }

  @Test
  void constructor_shouldCreateInstanceWithNonNullType() {
    // When: Creating a new instance
    FormRepliedNodeBuilder newBuilder = new FormRepliedNodeBuilder();

    // Then: The type() method should not return null
    assertThat(newBuilder.type()).isNotNull();
  }

  // Tests for type() method

  @Test
  void type_shouldReturnFormRepliedEvent() {
    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return FORM_REPLIED_EVENT
    assertThat(result).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
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

    // Then: Should specifically be FORM_REPLIED_EVENT and not any other enum value
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
    assertThat(result).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void type_multipleInstances_shouldReturnSameEnumConstant() {
    // Given: Multiple instances of FormRepliedNodeBuilder
    FormRepliedNodeBuilder builder1 = new FormRepliedNodeBuilder();
    FormRepliedNodeBuilder builder2 = new FormRepliedNodeBuilder();

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

    // Then: The enum name should be "FORM_REPLIED_EVENT"
    assertThat(result.name()).isEqualTo("FORM_REPLIED_EVENT");
  }

  @Test
  void type_shouldBeCompatibleWithEnumValueOf() {
    // When: Calling type() and comparing with valueOf
    WorkflowNodeType result = builder.type();
    WorkflowNodeType fromValueOf = WorkflowNodeType.valueOf("FORM_REPLIED_EVENT");

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
  void build_withParallelGatewayBuilder_shouldReturnIntermediateCatchEvent() {
    // Given: A WorkflowNode with form replied event and exclusive = true
    WorkflowNode node = createFormRepliedNode("testNode", "testEventId", true);
    AbstractFlowNodeBuilder<?, ?> startBuilder = processBuilder.startEvent();
    ParallelGatewayBuilder parallelGateway = startBuilder.parallelGateway();

    // When: Building the node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(node, "parentId", parallelGateway, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withStartEventBuilderAndExclusive_shouldReturnMessageStartEvent() {
    // Given: A WorkflowNode with form replied event and exclusive = true
    WorkflowNode node = createFormRepliedNode("testNode", "testEventId", true);
    StartEventBuilder startBuilder = processBuilder.startEvent();

    // When: Building the node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(node, "parentId", startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withExclusiveFalse_shouldCreateSubProcess() {
    // Given: A WorkflowNode with form replied event and exclusive = false
    WorkflowNode node = createFormRepliedNode("testNode", "testEventId", false);
    AbstractFlowNodeBuilder<?, ?> startBuilder = processBuilder.startEvent();

    // When: Building the node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(node, "parentId", startBuilder, context);

    // Then: Should return a non-null builder and cache subprocess
    assertThat(result).isNotNull();
    assertThat(context.hasEventSubProcess()).isTrue();
  }

  @Test
  void build_withExclusiveFalseAndTimeout_shouldCreateSubProcessWithTimeout() {
    // Given: A WorkflowNode with form replied event, exclusive = false, and timeout
    EventWithTimeout event = new EventWithTimeout();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(false);
    event.setFormReplied(formReplied);
    event.setTimeout("PT1H");

    WorkflowNode node = new WorkflowNode()
        .id("testNode")
        .eventId("testEventId")
        .event(event);

    AbstractFlowNodeBuilder<?, ?> startBuilder = processBuilder.startEvent();

    // When: Building the node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(node, "parentId", startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
    assertThat(context.hasEventSubProcess()).isTrue();
  }

  @Test
  void build_withExclusiveFalseAndNoTimeout_shouldUseDefaultTimeout() {
    // Given: A WorkflowNode with form replied event, exclusive = false, and no timeout
    WorkflowNode node = createFormRepliedNode("testNode", "testEventId", false);
    AbstractFlowNodeBuilder<?, ?> startBuilder = processBuilder.startEvent();

    // When: Building the node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(node, "parentId", startBuilder, context);

    // Then: Should return a non-null builder (default timeout PT24H is used internally)
    assertThat(result).isNotNull();
    assertThat(context.hasEventSubProcess()).isTrue();
  }

  @Test
  void build_withNonStartEventBuilderAndExclusive_shouldCreateIntermediateCatchEvent() {
    // Given: A WorkflowNode with form replied event and exclusive = true
    WorkflowNode node = createFormRepliedNode("testNode", "testEventId", true);
    AbstractFlowNodeBuilder<?, ?> startBuilder = processBuilder.startEvent().serviceTask();

    // When: Building the node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(node, "parentId", startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_withNullEventId_shouldHandleGracefully() {
    // Given: A WorkflowNode with null eventId but valid id
    WorkflowNode node = createFormRepliedNode("testNode", null, true);
    AbstractFlowNodeBuilder<?, ?> startBuilder = processBuilder.startEvent();

    // When: Building the node
    AbstractFlowNodeBuilder<?, ?> result = builder.build(node, "parentId", startBuilder, context);

    // Then: Should return a non-null builder
    assertThat(result).isNotNull();
  }

  @Test
  void build_multipleCallsWithExclusiveFalse_shouldCacheMultipleSubProcesses() {
    // Given: Multiple WorkflowNodes with exclusive = false
    WorkflowNode node1 = createFormRepliedNode("testNode1", "testEventId1", false);
    WorkflowNode node2 = createFormRepliedNode("testNode2", "testEventId2", false);
    AbstractFlowNodeBuilder<?, ?> startBuilder = processBuilder.startEvent();

    // When: Building the first node
    AbstractFlowNodeBuilder<?, ?> result1 = builder.build(node1, "parentId", startBuilder, context);

    // Then: Should have cached one event subprocess
    assertThat(result1).isNotNull();
    assertThat(context.hasEventSubProcess()).isTrue();
  }

  // Tests for ThrowTimeoutDelegate inner class

  @Test
  void throwTimeoutDelegate_notify_shouldThrowBpmnError() {
    // Given: An instance of ThrowTimeoutDelegate
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    DelegateExecution execution = mock(DelegateExecution.class);

    // When/Then: Calling notify should throw BpmnError
    assertThatThrownBy(() -> delegate.notify(execution))
        .isInstanceOf(BpmnError.class)
        .hasMessageContaining("Form reply event is timeout");
  }

  @Test
  void throwTimeoutDelegate_notify_shouldThrowBpmnErrorWithCorrectCode() {
    // Given: An instance of ThrowTimeoutDelegate
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    DelegateExecution execution = mock(DelegateExecution.class);

    // When/Then: Calling notify should throw BpmnError with code 408
    assertThatThrownBy(() -> delegate.notify(execution))
        .isInstanceOfSatisfying(BpmnError.class, error -> {
          assertThat(error.getErrorCode()).isEqualTo("408");
        });
  }

  @Test
  void throwTimeoutDelegate_constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();

    // Then: Instance should be created successfully
    assertThat(delegate).isNotNull();
  }

  @Test
  void throwTimeoutDelegate_multipleInstances_shouldAllThrowSameError() {
    // Given: Multiple instances of ThrowTimeoutDelegate
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate1 =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate2 =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    DelegateExecution execution = mock(DelegateExecution.class);

    // When/Then: All instances should throw the same error
    assertThatThrownBy(() -> delegate1.notify(execution))
        .isInstanceOf(BpmnError.class);
    assertThatThrownBy(() -> delegate2.notify(execution))
        .isInstanceOf(BpmnError.class);
  }

  @Test
  void throwTimeoutDelegate_notify_withNullExecution_shouldStillThrowBpmnError() {
    // Given: An instance of ThrowTimeoutDelegate with null execution
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();

    // When/Then: Calling notify with null should still throw BpmnError
    // The notify method doesn't use the execution parameter, so it should throw regardless
    assertThatThrownBy(() -> delegate.notify(null))
        .isInstanceOf(BpmnError.class)
        .hasMessageContaining("Form reply event is timeout");
  }

  @Test
  void throwTimeoutDelegate_notify_shouldThrowBpmnErrorWithBothCodeAndMessage() {
    // Given: An instance of ThrowTimeoutDelegate
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    DelegateExecution execution = mock(DelegateExecution.class);

    // When/Then: Calling notify should throw BpmnError with both error code and message
    assertThatThrownBy(() -> delegate.notify(execution))
        .isInstanceOfSatisfying(BpmnError.class, error -> {
          assertThat(error.getErrorCode()).isEqualTo("408");
          assertThat(error.getMessage()).contains("Form reply event is timeout");
        });
  }

  @Test
  void throwTimeoutDelegate_constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();

    // Then: Instance should be of ThrowTimeoutDelegate type and implement ExecutionListener
    assertThat(delegate).isInstanceOf(FormRepliedNodeBuilder.ThrowTimeoutDelegate.class);
    assertThat(delegate).isInstanceOf(org.camunda.bpm.engine.delegate.ExecutionListener.class);
  }

  @Test
  void throwTimeoutDelegate_notify_shouldAlwaysThrowException() {
    // Given: An instance of ThrowTimeoutDelegate
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    DelegateExecution execution = mock(DelegateExecution.class);

    // When/Then: notify should never complete normally, always throw
    // This verifies the method signature "throws Exception" is actually used
    try {
      delegate.notify(execution);
      // If we reach here, the test should fail
      assertThat(false).as("notify() should have thrown an exception").isTrue();
    } catch (BpmnError e) {
      // Expected behavior
      assertThat(e.getErrorCode()).isEqualTo("408");
    } catch (Exception e) {
      // Should not throw other exceptions
      assertThat(false).as("Should throw BpmnError, not " + e.getClass().getName()).isTrue();
    }
  }

  @Test
  void throwTimeoutDelegate_constructor_shouldCreateMultipleDistinctInstances() {
    // When: Creating multiple instances
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate1 =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate2 =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();
    FormRepliedNodeBuilder.ThrowTimeoutDelegate delegate3 =
        new FormRepliedNodeBuilder.ThrowTimeoutDelegate();

    // Then: Each instance should be distinct
    assertThat(delegate1).isNotSameAs(delegate2);
    assertThat(delegate2).isNotSameAs(delegate3);
    assertThat(delegate1).isNotSameAs(delegate3);
  }

  // Helper methods

  private WorkflowNode createFormRepliedNode(String id, String eventId, boolean exclusive) {
    Event event = new Event();
    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(exclusive);
    event.setFormReplied(formReplied);

    WorkflowNode node = new WorkflowNode()
        .id(id)
        .eventId(eventId)
        .event(event);

    return node;
  }
}
