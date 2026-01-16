package com.symphony.bdk.workflow.engine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowNodeTypeHelperClaude_toTypeTest {

  // ==================== toType() Method Tests ====================

  @Test
  void toType_withEventSuffix_shouldRemoveEventSuffix() {
    // Given: A name ending with _EVENT
    String name = "FORM_REPLIED_EVENT";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _EVENT suffix should be removed
    assertThat(result).isEqualTo("FORM_REPLIED");
  }

  @Test
  void toType_withGatewaySuffix_shouldRemoveGatewaySuffix() {
    // Given: A name ending with _GATEWAY
    String name = "EXCLUSIVE_GATEWAY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _GATEWAY suffix should be removed
    assertThat(result).isEqualTo("EXCLUSIVE");
  }

  @Test
  void toType_withoutSuffix_shouldReturnOriginalName() {
    // Given: A name without _EVENT or _GATEWAY suffix
    String name = "ACTIVITY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The original name should be returned
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toType_withEmptyString_shouldReturnEmptyString() {
    // Given: An empty string
    String name = "";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The empty string should be returned
    assertThat(result).isEmpty();
  }

  @Test
  void toType_withOnlyEventSuffix_shouldReturnEmptyString() {
    // Given: A name that is exactly "_EVENT"
    String name = "_EVENT";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: An empty string should be returned (suffix removed)
    assertThat(result).isEmpty();
  }

  @Test
  void toType_withOnlyGatewaySuffix_shouldReturnEmptyString() {
    // Given: A name that is exactly "_GATEWAY"
    String name = "_GATEWAY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: An empty string should be returned (suffix removed)
    assertThat(result).isEmpty();
  }

  @Test
  void toType_withEventSuffixInMiddle_shouldNotRemoveSuffix() {
    // Given: A name with _EVENT in the middle but not at the end
    String name = "SOME_EVENT_ACTIVITY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The name should be returned unchanged
    assertThat(result).isEqualTo("SOME_EVENT_ACTIVITY");
  }

  @Test
  void toType_withGatewaySuffixInMiddle_shouldNotRemoveSuffix() {
    // Given: A name with _GATEWAY in the middle but not at the end
    String name = "SOME_GATEWAY_ACTIVITY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The name should be returned unchanged
    assertThat(result).isEqualTo("SOME_GATEWAY_ACTIVITY");
  }

  @Test
  void toType_withPartialEventSuffix_shouldNotRemoveSuffix() {
    // Given: A name with a partial _EVENT suffix (e.g., _EVEN)
    String name = "FORM_REPLIED_EVEN";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The name should be returned unchanged
    assertThat(result).isEqualTo("FORM_REPLIED_EVEN");
  }

  @Test
  void toType_withPartialGatewaySuffix_shouldNotRemoveSuffix() {
    // Given: A name with a partial _GATEWAY suffix (e.g., _GATEWA)
    String name = "EXCLUSIVE_GATEWA";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The name should be returned unchanged
    assertThat(result).isEqualTo("EXCLUSIVE_GATEWA");
  }

  @Test
  void toType_withLowercaseEventSuffix_shouldNotRemoveSuffix() {
    // Given: A name with lowercase _event suffix
    String name = "form_replied_event";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The name should be returned unchanged (case-sensitive)
    assertThat(result).isEqualTo("form_replied_event");
  }

  @Test
  void toType_withLowercaseGatewaySuffix_shouldNotRemoveSuffix() {
    // Given: A name with lowercase _gateway suffix
    String name = "exclusive_gateway";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The name should be returned unchanged (case-sensitive)
    assertThat(result).isEqualTo("exclusive_gateway");
  }

  @Test
  void toType_withMultipleEventSuffixes_shouldRemoveOnlyLastOne() {
    // Given: A name with multiple _EVENT suffixes
    String name = "EVENT_EVENT";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: Only the last _EVENT suffix should be removed
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toType_withMultipleGatewaySuffixes_shouldRemoveOnlyLastOne() {
    // Given: A name with multiple _GATEWAY suffixes
    String name = "GATEWAY_GATEWAY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: Only the last _GATEWAY suffix should be removed
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toType_withBothSuffixes_shouldRemoveGatewaySuffix() {
    // Given: A name ending with both _EVENT and _GATEWAY (ending with _GATEWAY)
    String name = "SOME_EVENT_GATEWAY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _GATEWAY suffix should be removed
    assertThat(result).isEqualTo("SOME_EVENT");
  }

  @Test
  void toType_withLongEventName_shouldRemoveEventSuffix() {
    // Given: A long name ending with _EVENT
    String name = "VERY_LONG_COMPLICATED_NODE_NAME_EVENT";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _EVENT suffix should be removed
    assertThat(result).isEqualTo("VERY_LONG_COMPLICATED_NODE_NAME");
  }

  @Test
  void toType_withLongGatewayName_shouldRemoveGatewaySuffix() {
    // Given: A long name ending with _GATEWAY
    String name = "VERY_LONG_COMPLICATED_NODE_NAME_GATEWAY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _GATEWAY suffix should be removed
    assertThat(result).isEqualTo("VERY_LONG_COMPLICATED_NODE_NAME");
  }

  @Test
  void toType_withSingleCharacterBeforeEventSuffix_shouldRemoveEventSuffix() {
    // Given: A name with a single character before _EVENT suffix
    String name = "A_EVENT";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _EVENT suffix should be removed
    assertThat(result).isEqualTo("A");
  }

  @Test
  void toType_withSingleCharacterBeforeGatewaySuffix_shouldRemoveGatewaySuffix() {
    // Given: A name with a single character before _GATEWAY suffix
    String name = "X_GATEWAY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _GATEWAY suffix should be removed
    assertThat(result).isEqualTo("X");
  }

  @Test
  void toType_withWhitespaceAndEventSuffix_shouldRemoveEventSuffix() {
    // Given: A name with whitespace and _EVENT suffix
    String name = "SOME NAME_EVENT";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _EVENT suffix should be removed
    assertThat(result).isEqualTo("SOME NAME");
  }

  @Test
  void toType_withWhitespaceAndGatewaySuffix_shouldRemoveGatewaySuffix() {
    // Given: A name with whitespace and _GATEWAY suffix
    String name = "SOME NAME_GATEWAY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _GATEWAY suffix should be removed
    assertThat(result).isEqualTo("SOME NAME");
  }

  @Test
  void toType_withSpecialCharactersAndEventSuffix_shouldRemoveEventSuffix() {
    // Given: A name with special characters and _EVENT suffix
    String name = "NODE-123_EVENT";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _EVENT suffix should be removed
    assertThat(result).isEqualTo("NODE-123");
  }

  @Test
  void toType_withSpecialCharactersAndGatewaySuffix_shouldRemoveGatewaySuffix() {
    // Given: A name with special characters and _GATEWAY suffix
    String name = "NODE@123_GATEWAY";

    // When: Calling toType
    String result = WorkflowNodeTypeHelper.toType(name);

    // Then: The _GATEWAY suffix should be removed
    assertThat(result).isEqualTo("NODE@123");
  }
}
