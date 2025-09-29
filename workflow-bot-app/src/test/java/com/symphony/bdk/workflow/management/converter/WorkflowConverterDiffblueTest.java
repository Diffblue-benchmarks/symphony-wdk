package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.symphony.bdk.workflow.engine.camunda.CamundaExecutor;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowConverterDiffblueTest {
  @Autowired private WorkflowConverter workflowConverter;

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName(
      "Test apply(String) with 'String'; given Properties (default constructor) Publish is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Workflow WorkflowConverter.apply(String)"})
  void testApplyWithString_givenPropertiesPublishIsTrue() throws JsonProcessingException {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    String content = CamundaExecutor.OBJECT_MAPPER.writeValueAsString(workflow);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply(content));
  }

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link WorkflowConverter} (default constructor).
   *   <li>When {@code '}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName(
      "Test apply(String) with 'String'; given WorkflowConverter (default constructor); when '''")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Workflow WorkflowConverter.apply(String)"})
  void testApplyWithString_givenWorkflowConverter_whenApostrophe() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> new WorkflowConverter().apply("'"));
  }

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link WorkflowConverter}.
   *   <li>When {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName(
      "Test apply(String) with 'String'; given WorkflowConverter; when 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Workflow WorkflowConverter.apply(String)"})
  void testApplyWithString_givenWorkflowConverter_whenNotAllWhoWanderAreLost() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> workflowConverter.apply("Not all who wander are lost"));
  }

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link WorkflowConverter}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName("Test apply(String) with 'String'; given WorkflowConverter; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Workflow WorkflowConverter.apply(String)"})
  void testApplyWithString_givenWorkflowConverter_whenNull() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply(null));
  }

  /**
   * Test {@link WorkflowConverter#apply(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link WorkflowConverter}.
   *   <li>When space.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  @DisplayName("Test apply(String) with 'String'; given WorkflowConverter; when space")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Workflow WorkflowConverter.apply(String)"})
  void testApplyWithString_givenWorkflowConverter_whenSpace() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply(" "));
  }
}
