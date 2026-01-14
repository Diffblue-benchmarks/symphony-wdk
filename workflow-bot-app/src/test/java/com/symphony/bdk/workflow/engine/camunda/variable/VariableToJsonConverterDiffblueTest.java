package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.Character.UnicodeScript;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableToJsonConverter.class})
@ExtendWith(SpringExtension.class)
class VariableToJsonConverterDiffblueTest {
  @Autowired private VariableToJsonConverter variableToJsonConverter;

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When {@code COMMON}.
   *   <li>Then return {@code COMMON}.
   * </ul>
   *
   * <p>Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'COMMON'; then return 'COMMON'")
  @Tag("MaintainedByDiffblue")
  void testCoerceToString_whenCommon_thenReturnCommon() {
    // Arrange, Act and Assert
    assertEquals("COMMON", variableToJsonConverter.coerceToString(UnicodeScript.COMMON));
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when forty-two; then return '42'")
  @Tag("MaintainedByDiffblue")
  void testCoerceToString_whenFortyTwo_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", variableToJsonConverter.coerceToString(42));
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'null'; then return empty string")
  @Tag("MaintainedByDiffblue")
  void testCoerceToString_whenNull_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", variableToJsonConverter.coerceToString(null));
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when one; then return '1'")
  @Tag("MaintainedByDiffblue")
  void testCoerceToString_whenOne_thenReturn1() {
    // Arrange, Act and Assert
    assertEquals("1", variableToJsonConverter.coerceToString(1));
  }

  /**
   * Test {@link VariableToJsonConverter#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'Value'; then return 'Value'")
  @Tag("MaintainedByDiffblue")
  void testCoerceToString_whenValue_thenReturnValue() {
    // Arrange, Act and Assert
    assertEquals("Value", variableToJsonConverter.coerceToString("Value"));
  }
}
