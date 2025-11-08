package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WorkflowDiffblueTest {
  /**
   * Method under test: {@link Workflow#getFirstActivity()}
   */
  @Test
  void testGetFirstActivity() {
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
   * Method under test: {@link Workflow#getFirstActivity()}
   */
  @Test
  void testGetFirstActivity2() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(variables);
    workflow.setVersion(1L);

    // Act and Assert
    assertFalse(workflow.getFirstActivity().isPresent());
  }

  /**
   * Method under test: {@link Workflow#getProperties()}
   */
  @Test
  void testGetProperties() {
    // Arrange, Act and Assert
    assertTrue((new Workflow()).getProperties().getPublish());
  }

  /**
   * Method under test: {@link Workflow#getProperties()}
   */
  @Test
  void testGetProperties2() {
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
   * Method under test: {@link Workflow#getProperties()}
   */
  @Test
  void testGetProperties3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setVariables(variables);
    workflow.setVersion(1L);
    workflow.setProperties(properties);

    // Act
    Properties actualProperties = workflow.getProperties();

    // Assert
    assertTrue(actualProperties.getPublish());
    assertSame(properties, actualProperties);
  }

  /**
   * Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  void testIsToPublish() {
    // Arrange, Act and Assert
    assertTrue((new Workflow()).isToPublish());
  }

  /**
   * Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  void testIsToPublish2() {
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
   * Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  void testIsToPublish3() {
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
   * Method under test: {@link Workflow#isToPublish()}
   */
  @Test
  void testIsToPublish4() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setVariables(variables);
    workflow.setVersion(1L);
    workflow.setProperties(properties);

    // Act and Assert
    assertTrue(workflow.isToPublish());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#equals(Object)}
   *   <li>{@link Workflow#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#equals(Object)}
   *   <li>{@link Workflow#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
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

    // Assert that nothing has changed
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
   * Method under test: default or parameterless constructor of {@link Workflow}
   */
  @Test
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
