package com.symphony.bdk.workflow.api.v1.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowMgtActionClaudeTest {

  // Tests for values() method

  @Test
  void values_shouldReturnAllEnumConstants() {
    // When: Calling values() method
    WorkflowMgtAction[] values = WorkflowMgtAction.values();

    // Then: All three enum constants should be present
    assertThat(values).hasSize(3);
    assertThat(values).containsExactly(WorkflowMgtAction.DEPLOY, WorkflowMgtAction.UPDATE, WorkflowMgtAction.DELETE);
  }

  @Test
  void values_shouldReturnArrayContainingDeploy() {
    // When: Calling values() method
    WorkflowMgtAction[] values = WorkflowMgtAction.values();

    // Then: The array should contain DEPLOY
    assertThat(values).contains(WorkflowMgtAction.DEPLOY);
  }

  @Test
  void values_shouldReturnArrayContainingUpdate() {
    // When: Calling values() method
    WorkflowMgtAction[] values = WorkflowMgtAction.values();

    // Then: The array should contain UPDATE
    assertThat(values).contains(WorkflowMgtAction.UPDATE);
  }

  @Test
  void values_shouldReturnArrayContainingDelete() {
    // When: Calling values() method
    WorkflowMgtAction[] values = WorkflowMgtAction.values();

    // Then: The array should contain DELETE
    assertThat(values).contains(WorkflowMgtAction.DELETE);
  }

  @Test
  void values_shouldReturnNonNullArray() {
    // When: Calling values() method
    WorkflowMgtAction[] values = WorkflowMgtAction.values();

    // Then: The returned array should not be null
    assertThat(values).isNotNull();
  }

  @Test
  void values_shouldReturnNewArrayInstance() {
    // When: Calling values() method twice
    WorkflowMgtAction[] values1 = WorkflowMgtAction.values();
    WorkflowMgtAction[] values2 = WorkflowMgtAction.values();

    // Then: Different array instances should be returned (defensive copy)
    assertThat(values1).isNotSameAs(values2);
    assertThat(values1).containsExactly(values2);
  }

  @Test
  void values_shouldReturnArrayInDeclarationOrder() {
    // When: Calling values() method
    WorkflowMgtAction[] values = WorkflowMgtAction.values();

    // Then: The enum constants should be in the order they are declared
    assertThat(values[0]).isEqualTo(WorkflowMgtAction.DEPLOY);
    assertThat(values[1]).isEqualTo(WorkflowMgtAction.UPDATE);
    assertThat(values[2]).isEqualTo(WorkflowMgtAction.DELETE);
  }

  @Test
  void values_modifyingReturnedArray_shouldNotAffectSubsequentCalls() {
    // Given: First call to values()
    WorkflowMgtAction[] values1 = WorkflowMgtAction.values();

    // When: Modifying the returned array
    values1[0] = WorkflowMgtAction.DELETE;

    // And: Calling values() again
    WorkflowMgtAction[] values2 = WorkflowMgtAction.values();

    // Then: The second call should return unmodified array
    assertThat(values2[0]).isEqualTo(WorkflowMgtAction.DEPLOY);
    assertThat(values2).containsExactly(WorkflowMgtAction.DEPLOY, WorkflowMgtAction.UPDATE, WorkflowMgtAction.DELETE);
  }

  // Tests for valueOf() method

  @Test
  void valueOf_withDeploy_shouldReturnDeployEnum() {
    // When: Calling valueOf with "DEPLOY"
    WorkflowMgtAction result = WorkflowMgtAction.valueOf("DEPLOY");

    // Then: Should return the DEPLOY enum constant
    assertThat(result).isEqualTo(WorkflowMgtAction.DEPLOY);
  }

  @Test
  void valueOf_withUpdate_shouldReturnUpdateEnum() {
    // When: Calling valueOf with "UPDATE"
    WorkflowMgtAction result = WorkflowMgtAction.valueOf("UPDATE");

    // Then: Should return the UPDATE enum constant
    assertThat(result).isEqualTo(WorkflowMgtAction.UPDATE);
  }

  @Test
  void valueOf_withDelete_shouldReturnDeleteEnum() {
    // When: Calling valueOf with "DELETE"
    WorkflowMgtAction result = WorkflowMgtAction.valueOf("DELETE");

    // Then: Should return the DELETE enum constant
    assertThat(result).isEqualTo(WorkflowMgtAction.DELETE);
  }

  @Test
  void valueOf_withLowercaseDeploy_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase "deploy" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("deploy"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowMgtAction.deploy");
  }

  @Test
  void valueOf_withLowercaseUpdate_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase "update" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("update"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowMgtAction.update");
  }

  @Test
  void valueOf_withLowercaseDelete_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with lowercase "delete" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("delete"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowMgtAction.delete");
  }

  @Test
  void valueOf_withMixedCaseDeploy_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with mixed case "Deploy" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("Deploy"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowMgtAction.Deploy");
  }

  @Test
  void valueOf_withMixedCaseUpdate_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with mixed case "Update" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("Update"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowMgtAction.Update");
  }

  @Test
  void valueOf_withMixedCaseDelete_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with mixed case "Delete" should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("Delete"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowMgtAction.Delete");
  }

  @Test
  void valueOf_withNull_shouldThrowNullPointerException() {
    // When/Then: Calling valueOf with null should throw NullPointerException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void valueOf_withEmptyString_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with empty string should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowMgtAction.");
  }

  @Test
  void valueOf_withInvalidValue_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with invalid value should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("INVALID"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant")
        .hasMessageContaining("WorkflowMgtAction.INVALID");
  }

  @Test
  void valueOf_withWhitespace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with whitespace should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_withDeployAndLeadingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with leading space should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf(" DEPLOY"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_withDeployAndTrailingSpace_shouldThrowIllegalArgumentException() {
    // When/Then: Calling valueOf with trailing space should throw IllegalArgumentException
    assertThatThrownBy(() -> WorkflowMgtAction.valueOf("DEPLOY "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant");
  }

  @Test
  void valueOf_returnsSameInstance_whenCalledMultipleTimes() {
    // When: Calling valueOf multiple times with the same value
    WorkflowMgtAction result1 = WorkflowMgtAction.valueOf("DEPLOY");
    WorkflowMgtAction result2 = WorkflowMgtAction.valueOf("DEPLOY");

    // Then: Should return the same instance (enums are singletons)
    assertThat(result1).isSameAs(result2);
  }

  @Test
  void valueOf_shouldBeConsistentWithEnumName() {
    // Given: All enum values
    WorkflowMgtAction[] values = WorkflowMgtAction.values();

    // When/Then: valueOf should return the same enum when passed the enum's name
    for (WorkflowMgtAction value : values) {
      assertThat(WorkflowMgtAction.valueOf(value.name())).isSameAs(value);
    }
  }
}
