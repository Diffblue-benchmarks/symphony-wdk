package com.symphony.bdk.workflow.engine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowNodeTypeHelperClaude_toGroupTest {

  // ==================== toGroup() Method Tests ====================

  @Test
  void toGroup_withEventSuffix_shouldReturnEvent() {
    // Given: A name ending with _EVENT
    String name = "FORM_REPLIED_EVENT";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: EVENT should be returned
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toGroup_withGatewaySuffix_shouldReturnGateway() {
    // Given: A name ending with _GATEWAY
    String name = "EXCLUSIVE_GATEWAY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: GATEWAY should be returned
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_withoutSuffix_shouldReturnActivity() {
    // Given: A name without _EVENT or _GATEWAY suffix
    String name = "ACTIVITY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withEmptyString_shouldReturnActivity() {
    // Given: An empty string
    String name = "";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (default)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withOnlyEventSuffix_shouldReturnEvent() {
    // Given: A name that is exactly "_EVENT"
    String name = "_EVENT";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: EVENT should be returned
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toGroup_withOnlyGatewaySuffix_shouldReturnGateway() {
    // Given: A name that is exactly "_GATEWAY"
    String name = "_GATEWAY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: GATEWAY should be returned
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_withEventSuffixInMiddle_shouldReturnActivity() {
    // Given: A name with _EVENT in the middle but not at the end
    String name = "SOME_EVENT_ACTIVITY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (no suffix match)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withGatewaySuffixInMiddle_shouldReturnActivity() {
    // Given: A name with _GATEWAY in the middle but not at the end
    String name = "SOME_GATEWAY_ACTIVITY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (no suffix match)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withPartialEventSuffix_shouldReturnActivity() {
    // Given: A name with a partial _EVENT suffix (e.g., _EVEN)
    String name = "FORM_REPLIED_EVEN";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (no suffix match)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withPartialGatewaySuffix_shouldReturnActivity() {
    // Given: A name with a partial _GATEWAY suffix (e.g., _GATEWA)
    String name = "EXCLUSIVE_GATEWA";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (no suffix match)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withLowercaseEventSuffix_shouldReturnActivity() {
    // Given: A name with lowercase _event suffix
    String name = "form_replied_event";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (case-sensitive)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withLowercaseGatewaySuffix_shouldReturnActivity() {
    // Given: A name with lowercase _gateway suffix
    String name = "exclusive_gateway";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (case-sensitive)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withMultipleEventSuffixes_shouldReturnEvent() {
    // Given: A name with multiple _EVENT suffixes
    String name = "EVENT_EVENT";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: EVENT should be returned (matches suffix)
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toGroup_withMultipleGatewaySuffixes_shouldReturnGateway() {
    // Given: A name with multiple _GATEWAY suffixes
    String name = "GATEWAY_GATEWAY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: GATEWAY should be returned (matches suffix)
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_withBothSuffixes_shouldReturnGateway() {
    // Given: A name ending with both _EVENT and _GATEWAY (ending with _GATEWAY)
    String name = "SOME_EVENT_GATEWAY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: GATEWAY should be returned (checks _GATEWAY in else-if)
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_withLongEventName_shouldReturnEvent() {
    // Given: A long name ending with _EVENT
    String name = "VERY_LONG_COMPLICATED_NODE_NAME_EVENT";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: EVENT should be returned
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toGroup_withLongGatewayName_shouldReturnGateway() {
    // Given: A long name ending with _GATEWAY
    String name = "VERY_LONG_COMPLICATED_NODE_NAME_GATEWAY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: GATEWAY should be returned
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_withLongActivityName_shouldReturnActivity() {
    // Given: A long name without suffix
    String name = "VERY_LONG_COMPLICATED_NODE_NAME_ACTIVITY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withSingleCharacterBeforeEventSuffix_shouldReturnEvent() {
    // Given: A name with a single character before _EVENT suffix
    String name = "A_EVENT";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: EVENT should be returned
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toGroup_withSingleCharacterBeforeGatewaySuffix_shouldReturnGateway() {
    // Given: A name with a single character before _GATEWAY suffix
    String name = "X_GATEWAY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: GATEWAY should be returned
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_withWhitespaceAndEventSuffix_shouldReturnEvent() {
    // Given: A name with whitespace and _EVENT suffix
    String name = "SOME NAME_EVENT";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: EVENT should be returned
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toGroup_withWhitespaceAndGatewaySuffix_shouldReturnGateway() {
    // Given: A name with whitespace and _GATEWAY suffix
    String name = "SOME NAME_GATEWAY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: GATEWAY should be returned
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_withSpecialCharactersAndEventSuffix_shouldReturnEvent() {
    // Given: A name with special characters and _EVENT suffix
    String name = "NODE-123_EVENT";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: EVENT should be returned
    assertThat(result).isEqualTo("EVENT");
  }

  @Test
  void toGroup_withSpecialCharactersAndGatewaySuffix_shouldReturnGateway() {
    // Given: A name with special characters and _GATEWAY suffix
    String name = "NODE@123_GATEWAY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: GATEWAY should be returned
    assertThat(result).isEqualTo("GATEWAY");
  }

  @Test
  void toGroup_withSpecialCharactersNoSuffix_shouldReturnActivity() {
    // Given: A name with special characters but no suffix
    String name = "NODE@123";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withNumericName_shouldReturnActivity() {
    // Given: A numeric name
    String name = "12345";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withMixedCaseEventSuffix_shouldReturnActivity() {
    // Given: A name with mixed case _EvEnT suffix
    String name = "FORM_REPLIED_EvEnT";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (case-sensitive)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withMixedCaseGatewaySuffix_shouldReturnActivity() {
    // Given: A name with mixed case _GaTeWaY suffix
    String name = "EXCLUSIVE_GaTeWaY";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned (case-sensitive)
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withUnderscoreOnly_shouldReturnActivity() {
    // Given: A name that is just underscores
    String name = "___";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned
    assertThat(result).isEqualTo("ACTIVITY");
  }

  @Test
  void toGroup_withSingleCharacter_shouldReturnActivity() {
    // Given: A single character name
    String name = "A";

    // When: Calling toGroup
    String result = WorkflowNodeTypeHelper.toGroup(name);

    // Then: ACTIVITY should be returned
    assertThat(result).isEqualTo("ACTIVITY");
  }
}
