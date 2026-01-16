package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityExpiredNodeBuilderClaude_typeTest {

  // Tests for type() method

  @Test
  void type_shouldReturnActivityExpiredEvent() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return ACTIVITY_EXPIRED_EVENT
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
  }

  @Test
  void type_shouldReturnNonNullValue() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return a non-null value
    assertThat(result).isNotNull();
  }

  @Test
  void type_shouldReturnConsistentValue() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

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
    // Given: Instances of both ActivityExpiredNodeBuilder and its parent
    ActivityExpiredNodeBuilder expiredBuilder = new ActivityExpiredNodeBuilder();
    ActivityNodeBuilder activityBuilder = new ActivityNodeBuilder();

    // When: Calling type() on both
    WorkflowNodeType expiredType = expiredBuilder.type();
    WorkflowNodeType activityType = activityBuilder.type();

    // Then: Should return different types
    assertThat(expiredType).isNotEqualTo(activityType);
    assertThat(expiredType).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(activityType).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void type_shouldReturnEnumConstant() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return an enum constant of WorkflowNodeType
    assertThat(result).isInstanceOf(WorkflowNodeType.class);
  }

  @Test
  void type_shouldReturnSpecificEnumValue() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should specifically be ACTIVITY_EXPIRED_EVENT and not any other enum value
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.JOIN_ACTIVITY);
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
  }

  @Test
  void type_multipleInstances_shouldReturnSameEnumConstant() {
    // Given: Multiple instances of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder1 = new ActivityExpiredNodeBuilder();
    ActivityExpiredNodeBuilder builder2 = new ActivityExpiredNodeBuilder();

    // When: Calling type() on both instances
    WorkflowNodeType result1 = builder1.type();
    WorkflowNodeType result2 = builder2.type();

    // Then: Should return the same enum constant (reference equality)
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void type_shouldBeIdempotent() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

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
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should have the correct ordinal (5th position in the enum)
    assertThat(result.ordinal()).isEqualTo(5);
  }

  @Test
  void type_shouldHaveCorrectName() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: The enum name should be "ACTIVITY_EXPIRED_EVENT"
    assertThat(result.name()).isEqualTo("ACTIVITY_EXPIRED_EVENT");
  }

  @Test
  void type_shouldBeCompatibleWithEnumValueOf() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // When: Calling type() and comparing with valueOf
    WorkflowNodeType result = builder.type();
    WorkflowNodeType fromValueOf = WorkflowNodeType.valueOf("ACTIVITY_EXPIRED_EVENT");

    // Then: Should be the same enum constant
    assertThat(result).isSameAs(fromValueOf);
  }

  @Test
  void type_shouldBeInValuesArray() {
    // Given: An instance of ActivityExpiredNodeBuilder
    ActivityExpiredNodeBuilder builder = new ActivityExpiredNodeBuilder();

    // When: Calling type() and checking against values()
    WorkflowNodeType result = builder.type();
    WorkflowNodeType[] allValues = WorkflowNodeType.values();

    // Then: The returned type should be in the values array
    assertThat(allValues).contains(result);
  }
}
