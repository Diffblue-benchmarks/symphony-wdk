package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowConverterDiffblueTest {
  @Autowired
  private WorkflowConverter workflowConverter;

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   * <ul>
   *   <li>When {@code '}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName("Test apply(String) with 'String'; when '''")
  void testApplyWithString_whenApostrophe() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply("'"));
  }

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   * <ul>
   *   <li>When {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName("Test apply(String) with 'String'; when 'Not all who wander are lost'")
  void testApplyWithString_whenNotAllWhoWanderAreLost() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply("Not all who wander are lost"));
  }

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   * <ul>
   *   <li>When {@code .properties}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName("Test apply(String) with 'String'; when '.properties'")
  void testApplyWithString_whenProperties() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply(".properties"));
  }

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   * <ul>
   *   <li>When space.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName("Test apply(String) with 'String'; when space")
  void testApplyWithString_whenSpace() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply(" "));
  }
}
