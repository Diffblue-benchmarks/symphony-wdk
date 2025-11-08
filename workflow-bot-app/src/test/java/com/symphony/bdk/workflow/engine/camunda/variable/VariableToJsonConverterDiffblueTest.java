package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
   * Method under test: {@link VariableToJsonConverter#coerceToString(Object)}
   */
  @Test
  void testCoerceToString() {
    // Arrange, Act and Assert
    assertEquals("Value", variableToJsonConverter.coerceToString("Value"));
    assertEquals("42", variableToJsonConverter.coerceToString(42));
    assertEquals("1", variableToJsonConverter.coerceToString(1));
    assertEquals("", variableToJsonConverter.coerceToString(null));
    assertEquals("COMMON", variableToJsonConverter.coerceToString(Character.UnicodeScript.COMMON));
  }
}
