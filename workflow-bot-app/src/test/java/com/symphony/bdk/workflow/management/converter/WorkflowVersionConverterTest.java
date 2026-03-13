package com.symphony.bdk.workflow.management.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.junit.jupiter.api.Test;

class WorkflowVersionConverterTest {

  private final WorkflowVersionConverter converter = new WorkflowVersionConverter();

  @Test
  void apply_shouldParseValidYamlAndSetVersion() {
    // Arrange
    String validYaml = "id: test-workflow\n"
        + "properties:\n"
        + "  publish: false\n"
        + "activities:\n"
        + "  - send-message:\n"
        + "      id: act1\n"
        + "      content: test message\n"
        + "      on:\n"
        + "        message-received:\n"
        + "          content: /test\n";
    Long version = 5L;

    // Act
    Workflow result = converter.apply(validYaml, version);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("test-workflow");
    assertThat(result.getVersion()).isEqualTo(5L);
  }

  @Test
  void apply_shouldSetVersionToZeroWhenVersionIsZero() {
    // Arrange
    String validYaml = "id: workflow-v0\n"
        + "properties:\n"
        + "  publish: true\n"
        + "activities:\n"
        + "  - execute-script:\n"
        + "      id: script1\n"
        + "      script: |\n"
        + "        return true;\n";
    Long version = 0L;

    // Act
    Workflow result = converter.apply(validYaml, version);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(0L);
  }

  @Test
  void apply_shouldThrowExceptionForInvalidYaml() {
    // Arrange
    String invalidYaml = "invalid: yaml\n"
        + "  - [unclosed bracket\n"
        + "  malformed content\n";
    Long version = 1L;

    // Act & Assert
    assertThatThrownBy(() -> converter.apply(invalidYaml, version))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("SWADL content is not valid");
  }

  @Test
  void apply_shouldThrowExceptionForMissingRequiredFields() {
    // Arrange
    String yamlWithoutId = "properties:\n"
        + "  publish: false\n"
        + "activities:\n"
        + "  - send-message:\n"
        + "      id: act1\n"
        + "      content: test\n";
    Long version = 2L;

    // Act & Assert
    assertThatThrownBy(() -> converter.apply(yamlWithoutId, version))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("SWADL content is not valid");
  }

  @Test
  void apply_shouldThrowExceptionForUnknownActivityType() {
    // Arrange
    String yamlWithUnknownActivity = "id: test-workflow\n"
        + "properties:\n"
        + "  publish: false\n"
        + "activities:\n"
        + "  - unknown-activity-type:\n"
        + "      id: act1\n"
        + "      content: message1\n";
    Long version = 3L;

    // Act & Assert
    assertThatThrownBy(() -> converter.apply(yamlWithUnknownActivity, version))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("SWADL content is not valid");
  }

  @Test
  void apply_shouldHandleComplexWorkflowWithMultipleActivities() {
    // Arrange
    String complexYaml = "id: complex-workflow\n"
        + "properties:\n"
        + "  publish: true\n"
        + "activities:\n"
        + "  - send-message:\n"
        + "      id: msg1\n"
        + "      content: Hello\n"
        + "      on:\n"
        + "        message-received:\n"
        + "          content: /start\n"
        + "  - execute-script:\n"
        + "      id: script1\n"
        + "      script: |\n"
        + "        return true;\n";
    Long version = 10L;

    // Act
    Workflow result = converter.apply(complexYaml, version);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("complex-workflow");
    assertThat(result.getVersion()).isEqualTo(10L);
    assertThat(result.getActivities()).hasSize(2);
  }

  @Test
  void apply_shouldHandleWorkflowWithEmptyActivities() {
    // Arrange
    String yamlWithEmptyActivities = "id: empty-workflow\n"
        + "properties:\n"
        + "  publish: false\n"
        + "activities: []\n";
    Long version = 7L;

    // Act
    Workflow result = converter.apply(yamlWithEmptyActivities, version);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(7L);
    assertThat(result.getActivities()).isEmpty();
  }

  @Test
  void apply_shouldHandleNullContent() {
    // Arrange
    String nullContent = null;
    Long version = 1L;

    // Act & Assert
    assertThatThrownBy(() -> converter.apply(nullContent, version))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("SWADL content is not valid");
  }
}
