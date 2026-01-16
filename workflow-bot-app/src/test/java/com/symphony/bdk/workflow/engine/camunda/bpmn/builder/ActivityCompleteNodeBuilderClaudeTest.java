package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityCompleteNodeBuilderClaudeTest {

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // Then: Instance should be created successfully
    assertThat(builder).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // Then: Instance should be of ActivityCompleteNodeBuilder type
    assertThat(builder).isInstanceOf(ActivityCompleteNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingActivityNodeBuilder() {
    // When: Creating a new instance
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // Then: Instance should extend ActivityNodeBuilder
    assertThat(builder).isInstanceOf(ActivityNodeBuilder.class);
  }

  @Test
  void constructor_shouldCreateInstanceExtendingAbstractNodeBpmnBuilder() {
    // When: Creating a new instance
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // Then: Instance should extend AbstractNodeBpmnBuilder
    assertThat(builder).isInstanceOf(AbstractNodeBpmnBuilder.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    ActivityCompleteNodeBuilder builder1 = new ActivityCompleteNodeBuilder();
    ActivityCompleteNodeBuilder builder2 = new ActivityCompleteNodeBuilder();

    // Then: Each instance should be distinct
    assertThat(builder1).isNotSameAs(builder2);
  }

  @Test
  void constructor_multipleInstances_shouldAllHaveSameType() {
    // When: Creating multiple instances
    ActivityCompleteNodeBuilder builder1 = new ActivityCompleteNodeBuilder();
    ActivityCompleteNodeBuilder builder2 = new ActivityCompleteNodeBuilder();
    ActivityCompleteNodeBuilder builder3 = new ActivityCompleteNodeBuilder();

    // Then: All instances should return the same type
    assertThat(builder1.type()).isEqualTo(builder2.type());
    assertThat(builder2.type()).isEqualTo(builder3.type());
  }

  @Test
  void constructor_shouldCreateInstanceWithNonNullType() {
    // When: Creating a new instance
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // Then: The type() method should not return null
    assertThat(builder.type()).isNotNull();
  }

  // Tests for type() method

  @Test
  void type_shouldReturnActivityCompletedEvent() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return ACTIVITY_COMPLETED_EVENT
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
  }

  @Test
  void type_shouldReturnNonNullValue() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return a non-null value
    assertThat(result).isNotNull();
  }

  @Test
  void type_shouldReturnConsistentValue() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

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
  void type_shouldReturnDifferentTypeFromParentClass() {
    // Given: Instances of both ActivityCompleteNodeBuilder and its parent
    ActivityCompleteNodeBuilder completeBuilder = new ActivityCompleteNodeBuilder();
    ActivityNodeBuilder activityBuilder = new ActivityNodeBuilder();

    // When: Calling type() on both
    WorkflowNodeType completeType = completeBuilder.type();
    WorkflowNodeType activityType = activityBuilder.type();

    // Then: Should return different types
    assertThat(completeType).isNotEqualTo(activityType);
    assertThat(completeType).isEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    assertThat(activityType).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void type_shouldReturnEnumConstant() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return an enum constant of WorkflowNodeType
    assertThat(result).isInstanceOf(WorkflowNodeType.class);
  }

  @Test
  void type_shouldReturnSpecificEnumValue() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should specifically be ACTIVITY_COMPLETED_EVENT and not any other enum value
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.JOIN_ACTIVITY);
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
  }

  @Test
  void type_multipleInstances_shouldReturnSameEnumConstant() {
    // Given: Multiple instances of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder1 = new ActivityCompleteNodeBuilder();
    ActivityCompleteNodeBuilder builder2 = new ActivityCompleteNodeBuilder();

    // When: Calling type() on both instances
    WorkflowNodeType result1 = builder1.type();
    WorkflowNodeType result2 = builder2.type();

    // Then: Should return the same enum constant (reference equality)
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void type_shouldBeIdempotent() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type() repeatedly
    WorkflowNodeType firstCall = builder.type();
    WorkflowNodeType secondCall = builder.type();
    WorkflowNodeType thirdCall = builder.type();

    // Then: All calls should return identical results
    assertThat(firstCall).isEqualTo(secondCall);
    assertThat(secondCall).isEqualTo(thirdCall);
  }

  @Test
  void type_shouldHaveCorrectOrdinal() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should have the correct ordinal (3rd position in the enum)
    assertThat(result.ordinal()).isEqualTo(3);
  }

  @Test
  void type_shouldHaveCorrectName() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: The enum name should be "ACTIVITY_COMPLETED_EVENT"
    assertThat(result.name()).isEqualTo("ACTIVITY_COMPLETED_EVENT");
  }

  @Test
  void type_shouldBeCompatibleWithEnumValueOf() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type() and comparing with valueOf
    WorkflowNodeType result = builder.type();
    WorkflowNodeType fromValueOf = WorkflowNodeType.valueOf("ACTIVITY_COMPLETED_EVENT");

    // Then: Should be the same enum constant
    assertThat(result).isSameAs(fromValueOf);
  }

  @Test
  void type_shouldBeInValuesArray() {
    // Given: An instance of ActivityCompleteNodeBuilder
    ActivityCompleteNodeBuilder builder = new ActivityCompleteNodeBuilder();

    // When: Calling type() and checking against values()
    WorkflowNodeType result = builder.type();
    WorkflowNodeType[] allValues = WorkflowNodeType.values();

    // Then: The returned type should be in the values array
    assertThat(allValues).contains(result);
  }
}
