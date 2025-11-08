package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.symphony.bdk.workflow.swadl.exception.SwadlNotValidException;
import org.junit.jupiter.api.Test;

class SwadlValidatorDiffblueTest {
  /**
   * Method under test: {@link SwadlValidator#validateYaml(String)}
   */
  @Test
  void testValidateYaml() throws ProcessingException, SwadlNotValidException {
    // Arrange, Act and Assert
    assertThrows(SwadlNotValidException.class, () -> SwadlValidator.validateYaml("Yaml"));
    assertThrows(SwadlNotValidException.class, () -> SwadlValidator.validateYaml("yes"));
    assertThrows(SwadlNotValidException.class, () -> SwadlValidator.validateYaml("~"));
    assertThrows(SwadlNotValidException.class, () -> SwadlValidator.validateYaml("42"));
  }
}
