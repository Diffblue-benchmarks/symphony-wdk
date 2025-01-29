package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableToJsonConverter.class})
@ExtendWith(SpringExtension.class)
class VariableToJsonConverterDiffblueTest {
  @Autowired
  private VariableToJsonConverter variableToJsonConverter;

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   * <ul>
   *   <li>When {@code COMMON}.</li>
   *   <li>Then return {@code COMMON}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'COMMON'; then return 'COMMON'")
  void testCoerceToString_whenCommon_thenReturnCommon() {
    // Arrange, Act and Assert
    assertEquals("COMMON", variableToJsonConverter.coerceToString(Character.UnicodeScript.COMMON));
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when forty-two; then return '42'")
  void testCoerceToString_whenFortyTwo_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", variableToJsonConverter.coerceToString(42));
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'null'; then return empty string")
  void testCoerceToString_whenNull_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", variableToJsonConverter.coerceToString(null));
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when one; then return '1'")
  void testCoerceToString_whenOne_thenReturn1() {
    // Arrange, Act and Assert
    assertEquals("1", variableToJsonConverter.coerceToString(1));
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then return {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'Value'; then return 'Value'")
  void testCoerceToString_whenValue_thenReturnValue() {
    // Arrange, Act and Assert
    assertEquals("Value", variableToJsonConverter.coerceToString("Value"));
  }
}
