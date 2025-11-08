package com.symphony.bdk.workflow.engine.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {SharedData.class})
@ExtendWith(SpringExtension.class)
class SharedDataDiffblueTest {
  @Autowired
  private SharedData sharedData;

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SharedData#equals(Object)}
   *   <li>{@link SharedData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertEquals(sharedData, sharedData2);
    int expectedHashCodeResult = sharedData.hashCode();
    assertEquals(expectedHashCodeResult, sharedData2.hashCode());
  }

  /**
   * Method under test: {@link SharedData#namespace(String)}
   */
  @Test
  void testNamespace() {
    // Arrange
    SharedData sharedData2 = new SharedData();

    // Act
    SharedData actualNamespaceResult = sharedData2.namespace("Namespace");

    // Assert
    assertEquals("Namespace", sharedData2.getNamespace());
    assertSame(sharedData2, actualNamespaceResult);
  }

  /**
   * Method under test: {@link SharedData#namespace(String)}
   */
  @Test
  void testNamespace2() {
    // Arrange
    HashMap<String, Object> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    SharedData sharedData2 = new SharedData();
    sharedData2.setProperties(properties);

    // Act
    SharedData actualNamespaceResult = sharedData2.namespace("Namespace");

    // Assert
    assertEquals("Namespace", sharedData2.getNamespace());
    assertSame(sharedData2, actualNamespaceResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SharedData#equals(Object)}
   *   <li>{@link SharedData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId(null);
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId(null);
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertEquals(sharedData, sharedData2);
    int expectedHashCodeResult = sharedData.hashCode();
    assertEquals(expectedHashCodeResult, sharedData2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SharedData#equals(Object)}
   *   <li>{@link SharedData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(null);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(null);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertEquals(sharedData, sharedData2);
    int expectedHashCodeResult = sharedData.hashCode();
    assertEquals(expectedHashCodeResult, sharedData2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SharedData#equals(Object)}
   *   <li>{@link SharedData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace(null);
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace(null);
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertEquals(sharedData, sharedData2);
    int expectedHashCodeResult = sharedData.hashCode();
    assertEquals(expectedHashCodeResult, sharedData2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SharedData#equals(Object)}
   *   <li>{@link SharedData#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act and Assert
    assertEquals(sharedData, sharedData);
    int expectedHashCodeResult = sharedData.hashCode();
    assertEquals(expectedHashCodeResult, sharedData.hashCode());
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, sharedData2);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId(null);
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, sharedData2);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(3L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, sharedData2);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(null);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, sharedData2);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("42");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, sharedData2);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace(null);
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, sharedData2);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    HashMap<String, Object> properties = new HashMap<>();
    properties.put("42", "42");

    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(properties);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, sharedData2);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    HashMap<String, Object> properties = new HashMap<>();
    properties.computeIfPresent("42", mock(BiFunction.class));
    properties.put("42", "42");

    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(properties);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, sharedData2);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, null);
  }

  /**
   * Method under test: {@link SharedData#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act and Assert
    assertNotEquals(sharedData, "Different type to SharedData");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SharedData}
   *   <li>{@link SharedData#setId(String)}
   *   <li>{@link SharedData#setLastUpdated(Long)}
   *   <li>{@link SharedData#setNamespace(String)}
   *   <li>{@link SharedData#setProperties(Map)}
   *   <li>{@link SharedData#toString()}
   *   <li>{@link SharedData#getId()}
   *   <li>{@link SharedData#getLastUpdated()}
   *   <li>{@link SharedData#getNamespace()}
   *   <li>{@link SharedData#getProperties()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SharedData actualSharedData = new SharedData();
    actualSharedData.setId("42");
    actualSharedData.setLastUpdated(1L);
    actualSharedData.setNamespace("Namespace");
    HashMap<String, Object> properties = new HashMap<>();
    actualSharedData.setProperties(properties);
    String actualToStringResult = actualSharedData.toString();
    String actualId = actualSharedData.getId();
    Long actualLastUpdated = actualSharedData.getLastUpdated();
    String actualNamespace = actualSharedData.getNamespace();
    Map<String, Object> actualProperties = actualSharedData.getProperties();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Namespace", actualNamespace);
    assertEquals("SharedData(id=42, namespace=Namespace, properties={}, lastUpdated=1)", actualToStringResult);
    assertEquals(1L, actualLastUpdated.longValue());
    assertTrue(actualProperties.isEmpty());
    assertSame(properties, actualProperties);
  }
}
