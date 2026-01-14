package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.symphony.bdk.workflow.swadl.exception.SwadlNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SwadlValidatorDiffblueTest {
  /**
   * Test {@link SwadlValidator#validateYaml(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then throw {@link SwadlNotValidException}.
   * </ul>
   *
   * <p>Method under test: {@link SwadlValidator#validateYaml(String)}
   */
  @Test
  @DisplayName("Test validateYaml(String); when '42'; then throw SwadlNotValidException")
  @Tag("MaintainedByDiffblue")
  void testValidateYaml_when42_thenThrowSwadlNotValidException()
      throws ProcessingException, SwadlNotValidException {
    // Arrange, Act and Assert
    assertThrows(SwadlNotValidException.class, () -> SwadlValidator.validateYaml("42"));
  }

  /**
   * Test {@link SwadlValidator#validateYaml(String)}.
   *
   * <ul>
   *   <li>When {@code ~}.
   *   <li>Then throw {@link SwadlNotValidException}.
   * </ul>
   *
   * <p>Method under test: {@link SwadlValidator#validateYaml(String)}
   */
  @Test
  @DisplayName("Test validateYaml(String); when '~'; then throw SwadlNotValidException")
  @Tag("MaintainedByDiffblue")
  void testValidateYaml_whenTilde_thenThrowSwadlNotValidException()
      throws ProcessingException, SwadlNotValidException {
    // Arrange, Act and Assert
    assertThrows(SwadlNotValidException.class, () -> SwadlValidator.validateYaml("~"));
  }

  /**
   * Test {@link SwadlValidator#validateYaml(String)}.
   *
   * <ul>
   *   <li>When {@code Yaml}.
   *   <li>Then throw {@link SwadlNotValidException}.
   * </ul>
   *
   * <p>Method under test: {@link SwadlValidator#validateYaml(String)}
   */
  @Test
  @DisplayName("Test validateYaml(String); when 'Yaml'; then throw SwadlNotValidException")
  @Tag("MaintainedByDiffblue")
  void testValidateYaml_whenYaml_thenThrowSwadlNotValidException()
      throws ProcessingException, SwadlNotValidException {
    // Arrange, Act and Assert
    assertThrows(SwadlNotValidException.class, () -> SwadlValidator.validateYaml("Yaml"));
  }

  /**
   * Test {@link SwadlValidator#validateYaml(String)}.
   *
   * <ul>
   *   <li>When {@code yes}.
   *   <li>Then throw {@link SwadlNotValidException}.
   * </ul>
   *
   * <p>Method under test: {@link SwadlValidator#validateYaml(String)}
   */
  @Test
  @DisplayName("Test validateYaml(String); when 'yes'; then throw SwadlNotValidException")
  @Tag("MaintainedByDiffblue")
  void testValidateYaml_whenYes_thenThrowSwadlNotValidException()
      throws ProcessingException, SwadlNotValidException {
    // Arrange, Act and Assert
    assertThrows(SwadlNotValidException.class, () -> SwadlValidator.validateYaml("yes"));
  }
}
