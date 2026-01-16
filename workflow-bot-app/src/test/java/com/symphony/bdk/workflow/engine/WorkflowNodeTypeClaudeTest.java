package com.symphony.bdk.workflow.engine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowNodeTypeClaudeTest {

  // Tests for values() method

  @Test
  void values_shouldReturnAllEnumConstants() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: All eight enum constants should be present
    assertThat(values).hasSize(8);
    assertThat(values).containsExactly(
        WorkflowNodeType.TIMER_FIRED_EVENT,
        WorkflowNodeType.SIGNAL_EVENT,
        WorkflowNodeType.FORM_REPLIED_EVENT,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT,
        WorkflowNodeType.ACTIVITY_FAILED_EVENT,
        WorkflowNodeType.ACTIVITY_EXPIRED_EVENT,
        WorkflowNodeType.JOIN_ACTIVITY,
        WorkflowNodeType.ACTIVITY
    );
  }

  @Test
  void values_shouldReturnArrayContainingTimerFiredEvent() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The array should contain TIMER_FIRED_EVENT
    assertThat(values).contains(WorkflowNodeType.TIMER_FIRED_EVENT);
  }

  @Test
  void values_shouldReturnArrayContainingSignalEvent() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The array should contain SIGNAL_EVENT
    assertThat(values).contains(WorkflowNodeType.SIGNAL_EVENT);
  }

  @Test
  void values_shouldReturnArrayContainingFormRepliedEvent() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The array should contain FORM_REPLIED_EVENT
    assertThat(values).contains(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void values_shouldReturnArrayContainingActivityCompletedEvent() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The array should contain ACTIVITY_COMPLETED_EVENT
    assertThat(values).contains(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
  }

  @Test
  void values_shouldReturnArrayContainingActivityFailedEvent() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The array should contain ACTIVITY_FAILED_EVENT
    assertThat(values).contains(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
  }

  @Test
  void values_shouldReturnArrayContainingActivityExpiredEvent() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The array should contain ACTIVITY_EXPIRED_EVENT
    assertThat(values).contains(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
  }

  @Test
  void values_shouldReturnArrayContainingJoinActivity() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The array should contain JOIN_ACTIVITY
    assertThat(values).contains(WorkflowNodeType.JOIN_ACTIVITY);
  }

  @Test
  void values_shouldReturnArrayContainingActivity() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The array should contain ACTIVITY
    assertThat(values).contains(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void values_shouldReturnNonNullArray() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The returned array should not be null
    assertThat(values).isNotNull();
  }

  @Test
  void values_shouldReturnNewArrayInstance() {
    // When: Calling values() method twice
    WorkflowNodeType[] values1 = WorkflowNodeType.values();
    WorkflowNodeType[] values2 = WorkflowNodeType.values();

    // Then: Different array instances should be returned (defensive copy)
    assertThat(values1).isNotSameAs(values2);
    assertThat(values1).containsExactly(values2);
  }

  @Test
  void values_shouldReturnArrayInDeclarationOrder() {
    // When: Calling values() method
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // Then: The enum constants should be in the order they are declared
    assertThat(values[0]).isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
    assertThat(values[1]).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
    assertThat(values[2]).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
    assertThat(values[3]).isEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
    assertThat(values[4]).isEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    assertThat(values[5]).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    assertThat(values[6]).isEqualTo(WorkflowNodeType.JOIN_ACTIVITY);
    assertThat(values[7]).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void values_modifyingReturnedArray_shouldNotAffectSubsequentCalls() {
    // Given: First call to values()
    WorkflowNodeType[] values1 = WorkflowNodeType.values();

    // When: Modifying the returned array
    values1[0] = WorkflowNodeType.ACTIVITY;

    // And: Calling values() again
    WorkflowNodeType[] values2 = WorkflowNodeType.values();

    // Then: The second call should return unmodified array
    assertThat(values2[0]).isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
    assertThat(values2).containsExactly(
        WorkflowNodeType.TIMER_FIRED_EVENT,
        WorkflowNodeType.SIGNAL_EVENT,
        WorkflowNodeType.FORM_REPLIED_EVENT,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT,
        WorkflowNodeType.ACTIVITY_FAILED_EVENT,
        WorkflowNodeType.ACTIVITY_EXPIRED_EVENT,
        WorkflowNodeType.JOIN_ACTIVITY,
        WorkflowNodeType.ACTIVITY
    );
  }

  // Tests for valueOf() method

  @Test
  void valueOf_withTimerFiredEvent_shouldReturnTimerFiredEventEnum() {
    // When: Calling valueOf with "TIMER_FIRED_EVENT"
    WorkflowNodeType result = WorkflowNodeType.valueOf("TIMER_FIRED_EVENT");

    // Then: Should return the TIMER_FIRED_EVENT enum constant
    assertThat(result).isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
  }

  @Test
  void valueOf_withSignalEvent_shouldReturnSignalEventEnum() {
    // When: Calling valueOf with "SIGNAL_EVENT"
    WorkflowNodeType result = WorkflowNodeType.valueOf("SIGNAL_EVENT");

    // Then: Should return the SIGNAL_EVENT enum constant
    assertThat(result).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
  }

  @Test
  void valueOf_withFormRepliedEvent_shouldReturnFormRepliedEventEnum() {
    // When: Calling valueOf with "FORM_REPLIED_EVENT"
    WorkflowNodeType result = WorkflowNodeType.valueOf("FORM_REPLIED_EVENT");

    // Then: Should return the FORM_REPLIED_EVENT enum constant
    assertThat(result).isEqualTo(WorkflowNodeType.FORM_REPLIED_EVENT);
  }

  @Test
  void valueOf_withActivityCompletedEvent_shouldReturnActivityCompletedEventEnum() {
    // When: Calling valueOf with "ACTIVITY_COMPLETED_EVENT"
    WorkflowNodeType result = WorkflowNodeType.valueOf("ACTIVITY_COMPLETED_EVENT");

    // Then: Should return the ACTIVITY_COMPLETED_EVENT enum constant
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);
  }

  @Test
  void valueOf_withActivityFailedEvent_shouldReturnActivityFailedEventEnum() {
    // When: Calling valueOf with "ACTIVITY_FAILED_EVENT"
    WorkflowNodeType result = WorkflowNodeType.valueOf("ACTIVITY_FAILED_EVENT");

    // Then: Should return the ACTIVITY_FAILED_EVENT enum constant
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
  }

  @Test
  void valueOf_withActivityExpiredEvent_shouldReturnActivityExpiredEventEnum() {
    // When: Calling valueOf with "ACTIVITY_EXPIRED_EVENT"
    WorkflowNodeType result = WorkflowNodeType.valueOf("ACTIVITY_EXPIRED_EVENT");

    // Then: Should return the ACTIVITY_EXPIRED_EVENT enum constant
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
  }

  @Test
  void valueOf_withJoinActivity_shouldReturnJoinActivityEnum() {
    // When: Calling valueOf with "JOIN_ACTIVITY"
    WorkflowNodeType result = WorkflowNodeType.valueOf("JOIN_ACTIVITY");

    // Then: Should return the JOIN_ACTIVITY enum constant
    assertThat(result).isEqualTo(WorkflowNodeType.JOIN_ACTIVITY);
  }

  @Test
  void valueOf_withActivity_shouldReturnActivityEnum() {
    // When: Calling valueOf with "ACTIVITY"
    WorkflowNodeType result = WorkflowNodeType.valueOf("ACTIVITY");

    // Then: Should return the ACTIVITY enum constant
    assertThat(result).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void valueOf_withLowercaseActivity_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase "activity" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf("activity"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowNodeType.activity");
  }

  @Test
  void valueOf_withLowercaseTimerFiredEvent_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase "timer_fired_event" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf("timer_fired_event"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowNodeType.timer_fired_event");
  }

  @Test
  void valueOf_withMixedCaseActivity_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with mixed case "Activity" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf("Activity"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowNodeType.Activity");
  }

  @Test
  void valueOf_withNull_shouldThrowNullPointerException() {
    // When/Then: Calling valueOf with null should throw NullPointerException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void valueOf_withEmptyString_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with empty string should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowNodeType.");
  }

  @Test
  void valueOf_withInvalidValue_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with invalid value should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf("INVALID"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowNodeType.INVALID");
  }

  @Test
  void valueOf_withWhitespace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with whitespace should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_withActivityAndLeadingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with leading space should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf(" ACTIVITY"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_withActivityAndTrailingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with trailing space should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowNodeType.valueOf("ACTIVITY "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_returnsSameInstance_whenCalledMultipleTimes() {
    // When: Calling valueOf multiple times with the same value
    WorkflowNodeType result1 = WorkflowNodeType.valueOf("ACTIVITY");
    WorkflowNodeType result2 = WorkflowNodeType.valueOf("ACTIVITY");

    // Then: Should return the same instance (enums are singletons)
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void valueOf_shouldBeConsistentWithEnumName() {
    // Given: All enum values
    WorkflowNodeType[] values = WorkflowNodeType.values();

    // When/Then: valueOf should return the same enum when passed the enum's name
    for (WorkflowNodeType value : values) {
      assertThat(WorkflowNodeType.valueOf(value.name())).isSameAs(value);
    }
  }
}
