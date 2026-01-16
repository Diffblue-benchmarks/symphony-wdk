package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityNodeBuilderClaude_typeTest {

  // Tests for type() method

  @Test
  void type_shouldReturnActivity() {
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return ACTIVITY
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void type_shouldReturnNonNullValue() {
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return a non-null value
    assertThat(result).isNotNull();
  }

  @Test
  void type_shouldReturnConsistentValue() {
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

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
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should return an enum constant of WorkflowNodeType
    assertThat(result).isInstanceOf(WorkflowNodeType.class);
  }

  @Test
  void type_shouldReturnSpecificEnumValue() {
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should specifically be ACTIVITY and not any other enum value
    assertThat(result).isNotEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(result).isNotEqualTo(WorkflowNodeType.JOIN_ACTIVITY);
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void type_multipleInstances_shouldReturnSameEnumConstant() {
    // Given: Multiple instances of ActivityNodeBuilder
    ActivityNodeBuilder builder1 = new ActivityNodeBuilder();
    ActivityNodeBuilder builder2 = new ActivityNodeBuilder();

    // When: Calling type() on both instances
    WorkflowNodeType result1 = builder1.type();
    WorkflowNodeType result2 = builder2.type();

    // Then: Should return the same enum constant (reference equality)
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void type_shouldBeIdempotent() {
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

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
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: Should have the correct ordinal (7th position in the enum)
    assertThat(result.ordinal()).isEqualTo(7);
  }

  @Test
  void type_shouldHaveCorrectName() {
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // When: Calling type()
    WorkflowNodeType result = builder.type();

    // Then: The enum name should be "ACTIVITY"
    assertThat(result.name()).isEqualTo("ACTIVITY");
  }

  @Test
  void type_shouldBeCompatibleWithEnumValueOf() {
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // When: Calling type() and comparing with valueOf
    WorkflowNodeType result = builder.type();
    WorkflowNodeType fromValueOf = WorkflowNodeType.valueOf("ACTIVITY");

    // Then: Should be the same enum constant
    assertThat(result).isSameAs(fromValueOf);
  }

  @Test
  void type_shouldBeInValuesArray() {
    // Given: An instance of ActivityNodeBuilder
    ActivityNodeBuilder builder = new ActivityNodeBuilder();

    // When: Calling type() and checking against values()
    WorkflowNodeType result = builder.type();
    WorkflowNodeType[] allValues = WorkflowNodeType.values();

    // Then: The returned type should be in the values array
    assertThat(allValues).contains(result);
  }
}
