package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowVersionConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowVersionConverterDiffblueTest {
  @Autowired
  private WorkflowVersionConverter workflowVersionConverter;

  /**
   * Test {@link WorkflowVersionConverter#apply(String, Long)} with
   * {@code String}, {@code Long}.
   * <ul>
   *   <li>When {@code '}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowVersionConverter#apply(String, Long)}
   */
  @Test
  @DisplayName("Test apply(String, Long) with 'String', 'Long'; when '''")
  void testApplyWithStringLong_whenApostrophe() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowVersionConverter.apply("'", 1L));
  }

  /**
   * Test {@link WorkflowVersionConverter#apply(String, Long)} with
   * {@code String}, {@code Long}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowVersionConverter#apply(String, Long)}
   */
  @Test
  @DisplayName("Test apply(String, Long) with 'String', 'Long'; when empty string")
  void testApplyWithStringLong_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowVersionConverter.apply("", 1L));
  }

  /**
   * Test {@link WorkflowVersionConverter#apply(String, Long)} with
   * {@code String}, {@code Long}.
   * <ul>
   *   <li>When {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowVersionConverter#apply(String, Long)}
   */
  @Test
  @DisplayName("Test apply(String, Long) with 'String', 'Long'; when 'Not all who wander are lost'")
  void testApplyWithStringLong_whenNotAllWhoWanderAreLost() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> workflowVersionConverter.apply("Not all who wander are lost", 1L));
  }
}
