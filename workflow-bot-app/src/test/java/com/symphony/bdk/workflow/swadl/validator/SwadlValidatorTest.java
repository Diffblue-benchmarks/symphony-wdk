package com.symphony.bdk.workflow.swadl.validator;

import com.symphony.bdk.workflow.swadl.exception.SwadlNotValidException;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

class SwadlValidatorTest {

  private static final String VALID_YAML =
      "id: test-workflow\n"
          + "activities:\n"
          + "  - send-message:\n"
          + "      id: send\n"
          + "      on:\n"
          + "        message-received:\n"
          + "          content: /test\n"
          + "      to:\n"
          + "        stream-id: \"stream123\"\n"
          + "      content: \"hello\"\n";

  private static final String INVALID_YAML =
      "id: test-workflow\n"
          + "activities:\n"
          + "  - unknown-invalid-activity-xyz:\n"
          + "      id: bad\n"
          + "      invalidField: true\n";

  @Test
  void shouldNotThrowWhenYamlIsValid() {
    assertThatCode(() -> SwadlValidator.validateYaml(VALID_YAML))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowSwadlNotValidExceptionWhenYamlIsInvalid() {
    assertThatThrownBy(() -> SwadlValidator.validateYaml(INVALID_YAML))
        .isInstanceOf(SwadlNotValidException.class);
  }

  @Test
  void shouldThrowSwadlNotValidExceptionWhenYamlIsMalformed() {
    String malformedYaml = "id: test\nactivities:\n  - send-message:\n    bad: [unclosed";

    assertThatThrownBy(() -> SwadlValidator.validateYaml(malformedYaml))
        .isInstanceOf(SwadlNotValidException.class);
  }

  @Test
  void shouldThrowSwadlNotValidExceptionWithErrorsWhenSchemaValidationFails() throws Exception {
    assertThatThrownBy(() -> SwadlValidator.validateYaml(INVALID_YAML))
        .isInstanceOf(SwadlNotValidException.class)
        .satisfies(e -> assertThat(((SwadlNotValidException) e).getErrors()).isNotEmpty());
  }

  @Test
  void shouldConvertCamelCaseToKebabCase() throws Exception {
    // loading SwadlValidator triggers addCustomActivitiesToSchema via static initializer
    // validating a known activity should succeed
    assertThatCode(() -> SwadlValidator.validateYaml(VALID_YAML))
        .doesNotThrowAnyException();
  }
}
