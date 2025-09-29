package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class WorkflowDiffblueTest {
  /**
   * Test {@link Workflow#getFirstActivity()}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link Workflow#getFirstActivity()}
   */
  @Test
  @DisplayName(
      "Test getFirstActivity(); given Properties (default constructor) Publish is 'true'; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional Workflow.getFirstActivity()"})
  void testGetFirstActivity_givenPropertiesPublishIsTrue_thenReturnNotPresent() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act and Assert
    assertFalse(workflow.getFirstActivity().isPresent());
  }

  /**
   * Test {@link Workflow#getProperties()}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.
   *   <li>Then return {@link Properties} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Workflow#getProperties()}
   */
  @Test
  @DisplayName(
      "Test getProperties(); given Properties (default constructor) Publish is 'true'; then return Properties (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Properties Workflow.getProperties()"})
  void testGetProperties_givenPropertiesPublishIsTrue_thenReturnProperties() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    workflow.setProperties(properties);

    // Act
    Properties actualProperties = workflow.getProperties();

    // Assert
    assertTrue(actualProperties.getPublish());
    assertSame(properties, actualProperties);
  }

  /**
   * Test {@link Workflow#getProperties()}.
   *
   * <ul>
   *   <li>Given {@link Workflow} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Workflow#getProperties()}
   */
  @Test
  @DisplayName("Test getProperties(); given Workflow (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Properties Workflow.getProperties()"})
  void testGetProperties_givenWorkflow() {
    // Arrange, Act and Assert
    assertTrue(new Workflow().getProperties().getPublish());
  }

  /**
   * Test {@link Workflow#isToPublish()}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  @DisplayName(
      "Test isToPublish(); given Properties (default constructor) Publish is 'false'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Workflow.isToPublish()"})
  void testIsToPublish_givenPropertiesPublishIsFalse_thenReturnFalse() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(false);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    workflow.setProperties(properties);

    // Act and Assert
    assertFalse(workflow.isToPublish());
  }

  /**
   * Test {@link Workflow#isToPublish()}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  @DisplayName(
      "Test isToPublish(); given Properties (default constructor) Publish is 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Workflow.isToPublish()"})
  void testIsToPublish_givenPropertiesPublishIsTrue_thenReturnTrue() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    workflow.setProperties(properties);

    // Act and Assert
    assertTrue(workflow.isToPublish());
  }

  /**
   * Test {@link Workflow#isToPublish()}.
   *
   * <ul>
   *   <li>Given {@link Workflow} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  @DisplayName("Test isToPublish(); given Workflow (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Workflow.isToPublish()"})
  void testIsToPublish_givenWorkflow_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new Workflow().isToPublish());
  }
}
