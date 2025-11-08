package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WorkflowDiffblueTest {
  /**
   * Test {@link Workflow#getFirstActivity()}.
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#getFirstActivity()}
   */
  @Test
  @DisplayName("Test getFirstActivity(); given Properties (default constructor) Publish is 'true'; then return not Present")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.</li>
   *   <li>Then return {@link Properties} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#getProperties()}
   */
  @Test
  @DisplayName("Test getProperties(); given Properties (default constructor) Publish is 'true'; then return Properties (default constructor)")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Workflow} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#getProperties()}
   */
  @Test
  @DisplayName("Test getProperties(); given Workflow (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Properties Workflow.getProperties()"})
  void testGetProperties_givenWorkflow() {
    // Arrange, Act and Assert
    assertTrue((new Workflow()).getProperties().getPublish());
  }

  /**
   * Test {@link Workflow#isToPublish()}.
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  @DisplayName("Test isToPublish(); given Properties (default constructor) Publish is 'false'; then return 'false'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  @DisplayName("Test isToPublish(); given Properties (default constructor) Publish is 'true'; then return 'true'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Workflow} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  @DisplayName("Test isToPublish(); given Workflow (default constructor); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.isToPublish()"})
  void testIsToPublish_givenWorkflow_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new Workflow()).isToPublish());
  }

  /**
   * Test {@link Workflow#equals(Object)}, and {@link Workflow#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#equals(Object)}
   *   <li>{@link Workflow#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act and Assert
    assertEquals(workflow, workflow2);
    int expectedHashCodeResult = workflow.hashCode();
    assertEquals(expectedHashCodeResult, workflow2.hashCode());
  }

  /**
   * Test {@link Workflow#equals(Object)}, and {@link Workflow#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#equals(Object)}
   *   <li>{@link Workflow#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
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
    assertEquals(workflow, workflow);
    int expectedHashCodeResult = workflow.hashCode();
    assertEquals(expectedHashCodeResult, workflow.hashCode());
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    ArrayList<Activity> activities = new ArrayList<>();
    activities.add(activity);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(activities);
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("Id");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId(null);
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Properties properties = mock(Properties.class);
    doNothing().when(properties).setPublish(Mockito.<Boolean>any());
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Properties properties = mock(Properties.class);
    doNothing().when(properties).setPublish(Mockito.<Boolean>any());
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(3L);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Properties properties = mock(Properties.class);
    doNothing().when(properties).setPublish(Mockito.<Boolean>any());
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(null);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Properties properties = mock(Properties.class);
    doNothing().when(properties).setPublish(Mockito.<Boolean>any());
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(null);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties2);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(null);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
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
    assertNotEquals(workflow, null);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
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
    assertNotEquals(workflow, "Different type to Workflow");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#setActivities(List)}
   *   <li>{@link Workflow#setId(String)}
   *   <li>{@link Workflow#setProperties(Properties)}
   *   <li>{@link Workflow#setVariables(Map)}
   *   <li>{@link Workflow#setVersion(Long)}
   *   <li>{@link Workflow#toString()}
   *   <li>{@link Workflow#getActivities()}
   *   <li>{@link Workflow#getId()}
   *   <li>{@link Workflow#getVariables()}
   *   <li>{@link Workflow#getVersion()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List Workflow.getActivities()", "String Workflow.getId()", "Map Workflow.getVariables()",
      "Long Workflow.getVersion()", "void Workflow.setActivities(List)", "void Workflow.setId(String)",
      "void Workflow.setProperties(Properties)", "void Workflow.setVariables(Map)", "void Workflow.setVersion(Long)",
      "String Workflow.toString()"})
  void testGettersAndSetters() {
    // Arrange
    Workflow workflow = new Workflow();
    ArrayList<Activity> activities = new ArrayList<>();

    // Act
    workflow.setActivities(activities);
    workflow.setId("42");
    Properties properties = new Properties();
    properties.setPublish(true);
    workflow.setProperties(properties);
    HashMap<String, Object> variables = new HashMap<>();
    workflow.setVariables(variables);
    workflow.setVersion(1L);
    String actualToStringResult = workflow.toString();
    List<Activity> actualActivities = workflow.getActivities();
    String actualId = workflow.getId();
    Map<String, Object> actualVariables = workflow.getVariables();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Workflow(id=42, properties=Properties(publish=true), variables={}, activities=[], version=1)",
        actualToStringResult);
    assertEquals(1L, workflow.getVersion().longValue());
    assertTrue(actualActivities.isEmpty());
    assertTrue(actualVariables.isEmpty());
    assertSame(activities, actualActivities);
    assertSame(variables, actualVariables);
  }

  /**
   * Test new {@link Workflow} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Workflow}
   */
  @Test
  @DisplayName("Test new Workflow (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Workflow.<init>()"})
  void testNewWorkflow() {
    // Arrange and Act
    Workflow actualWorkflow = new Workflow();

    // Assert
    assertNull(actualWorkflow.getVersion());
    assertNull(actualWorkflow.getId());
    assertNull(actualWorkflow.getActivities());
    assertTrue(actualWorkflow.getProperties().getPublish());
    assertTrue(actualWorkflow.getVariables().isEmpty());
  }
}
