package com.symphony.bdk.workflow.swadl.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.symphony.bdk.workflow.swadl.exception.SwadlNotValidException;

import org.junit.jupiter.api.Test;

class SwadlValidatorTest {

  @Test
  void validateYaml_shouldPassForValidYaml() throws Exception {
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

    // Act & Assert
    SwadlValidator.validateYaml(validYaml);
  }

  @Test
  void validateYaml_shouldThrowExceptionForUnknownActivity() {
    // Arrange
    String invalidYaml = "id: test-workflow\n"
        + "properties:\n"
        + "  publish: false\n"
        + "activities:\n"
        + "  - unknown-activity-type:\n"
        + "      id: act1\n"
        + "      content: message1\n";

    // Act & Assert
    assertThatThrownBy(() -> SwadlValidator.validateYaml(invalidYaml))
        .isInstanceOf(SwadlNotValidException.class)
        .hasMessageContaining("SWADL content is not valid");
  }

  @Test
  void validateYaml_shouldThrowExceptionForMalformedYaml() {
    // Arrange
    String malformedYaml = "id: test\n"
        + "  invalid:\n"
        + "    - this is not valid yaml syntax\n"
        + "  - [unclosed bracket\n";

    // Act & Assert
    assertThatThrownBy(() -> SwadlValidator.validateYaml(malformedYaml))
        .isInstanceOf(SwadlNotValidException.class);
  }

  @Test
  void validateYaml_shouldThrowExceptionForMissingRequiredField() {
    // Arrange
    String yamlWithoutId = "properties:\n"
        + "  publish: false\n"
        + "activities:\n"
        + "  - send-message:\n"
        + "      id: act1\n"
        + "      content: test\n";

    // Act & Assert
    assertThatThrownBy(() -> SwadlValidator.validateYaml(yamlWithoutId))
        .isInstanceOf(SwadlNotValidException.class)
        .hasMessageContaining("SWADL content is not valid");
  }

  @Test
  void validateYaml_shouldThrowExceptionForInvalidActivityStructure() {
    // Arrange
    String invalidActivityYaml = "id: test-workflow\n"
        + "properties:\n"
        + "  publish: false\n"
        + "activities:\n"
        + "  - send-message:\n"
        + "      id: act1\n"
        + "      invalidField: value\n";

    // Act & Assert
    assertThatThrownBy(() -> SwadlValidator.validateYaml(invalidActivityYaml))
        .isInstanceOf(SwadlNotValidException.class);
  }

  @Test
  void validateYaml_shouldHandleEmptyActivitiesList() throws Exception {
    // Arrange
    String yamlWithEmptyActivities = "id: test-workflow\n"
        + "properties:\n"
        + "  publish: false\n"
        + "activities: []\n";

    // Act & Assert
    SwadlValidator.validateYaml(yamlWithEmptyActivities);
  }

  @Test
  void validateYaml_shouldHandleComplexNestedStructure() throws Exception {
    // Arrange
    String complexYaml = "id: complex-workflow\n"
        + "properties:\n"
        + "  publish: false\n"
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

    // Act & Assert
    SwadlValidator.validateYaml(complexYaml);
  }

  @Test
  void validateYaml_shouldThrowExceptionWithDetailedErrorsForMultipleIssues() {
    // Arrange
    String yamlWithMultipleIssues = "id: test-workflow\n"
        + "properties:\n"
        + "  publish: not-a-boolean\n"
        + "activities:\n"
        + "  - send-message:\n"
        + "      content: missing id field\n";

    // Act & Assert
    assertThatThrownBy(() -> SwadlValidator.validateYaml(yamlWithMultipleIssues))
        .isInstanceOf(SwadlNotValidException.class)
        .satisfies(exception -> {
          SwadlNotValidException swadlException = (SwadlNotValidException) exception;
          assertThat(swadlException.getErrors()).isNotEmpty();
        });
  }

  @Test
  void validateYaml_shouldHandleYamlWithCustomActivity() throws Exception {
    // Arrange
    String customActivityYaml = "id: custom-workflow\n"
        + "properties:\n"
        + "  publish: false\n"
        + "activities:\n"
        + "  - do-something:\n"
        + "      id: custom1\n"
        + "      on:\n"
        + "        message-received:\n"
        + "          content: /custom\n"
        + "      my-parameter: test\n";

    // Act & Assert
    SwadlValidator.validateYaml(customActivityYaml);
  }
}
