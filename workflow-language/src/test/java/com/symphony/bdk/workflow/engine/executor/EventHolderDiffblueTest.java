package com.symphony.bdk.workflow.engine.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.gen.api.model.V4Initiator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EventHolderDiffblueTest {
  /**
   * Test {@link EventHolder#equals(Object)}, and {@link EventHolder#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EventHolder#equals(Object)}
   *   <li>{@link EventHolder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    EventHolder<Object> eventHolder2 = new EventHolder<>();

    // Act and Assert
    assertEquals(eventHolder, eventHolder2);
    int expectedHashCodeResult = eventHolder.hashCode();
    assertEquals(expectedHashCodeResult, eventHolder2.hashCode());
  }

  /**
   * Test {@link EventHolder#equals(Object)}, and {@link EventHolder#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EventHolder#equals(Object)}
   *   <li>{@link EventHolder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    V4Initiator initiator = new V4Initiator();
    EventHolder<Object> eventHolder = new EventHolder<>(initiator, "Source", new HashMap<>());
    V4Initiator initiator2 = new V4Initiator();
    EventHolder<Object> eventHolder2 = new EventHolder<>(initiator2, "Source", new HashMap<>());

    // Act and Assert
    assertEquals(eventHolder, eventHolder2);
    int expectedHashCodeResult = eventHolder.hashCode();
    assertEquals(expectedHashCodeResult, eventHolder2.hashCode());
  }

  /**
   * Test {@link EventHolder#equals(Object)}, and {@link EventHolder#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EventHolder#equals(Object)}
   *   <li>{@link EventHolder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    // Act and Assert
    assertEquals(eventHolder, eventHolder);
    int expectedHashCodeResult = eventHolder.hashCode();
    assertEquals(expectedHashCodeResult, eventHolder.hashCode());
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    V4Initiator initiator = new V4Initiator();
    EventHolder<Object> eventHolder = new EventHolder<>(initiator, "Source", new HashMap<>());

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    V4Initiator initiator = new V4Initiator();

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>(initiator, "Source", new HashMap<>()));
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    eventHolder.setSource("Source");

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    eventHolder.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    EventHolder<Object> eventHolder2 = new EventHolder<>();
    eventHolder2.setSource("Source");

    // Act and Assert
    assertNotEquals(eventHolder, eventHolder2);
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    EventHolder<Object> eventHolder2 = new EventHolder<>();
    eventHolder2.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(eventHolder, eventHolder2);
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    eventHolder.setSource(new EventHolder<>());

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    // Act and Assert
    assertNotEquals(eventHolder, null);
  }

  /**
   * Test {@link EventHolder#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean EventHolder.equals(Object)", "int EventHolder.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    // Act and Assert
    assertNotEquals(eventHolder, "Different type to EventHolder");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EventHolder#EventHolder()}
   *   <li>{@link EventHolder#setArgs(Map)}
   *   <li>{@link EventHolder#setInitiator(V4Initiator)}
   *   <li>{@link EventHolder#setSource(Object)}
   *   <li>{@link EventHolder#toString()}
   *   <li>{@link EventHolder#getArgs()}
   *   <li>{@link EventHolder#getInitiator()}
   *   <li>{@link EventHolder#getSource()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EventHolder.<init>()", "void EventHolder.<init>(V4Initiator, Object, Map)",
      "Map EventHolder.getArgs()", "V4Initiator EventHolder.getInitiator()", "Object EventHolder.getSource()",
      "void EventHolder.setArgs(Map)", "void EventHolder.setInitiator(V4Initiator)",
      "void EventHolder.setSource(Object)", "String EventHolder.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    EventHolder<Object> actualEventHolder = new EventHolder<>();
    HashMap<String, Object> args = new HashMap<>();
    actualEventHolder.setArgs(args);
    V4Initiator initiator = new V4Initiator();
    actualEventHolder.setInitiator(initiator);
    actualEventHolder.setSource("Source");
    String actualToStringResult = actualEventHolder.toString();
    Map<String, Object> actualArgs = actualEventHolder.getArgs();
    V4Initiator actualInitiator = actualEventHolder.getInitiator();

    // Assert
    assertEquals("EventHolder(initiator=class V4Initiator {\n    user: null\n}, source=Source, args={})",
        actualToStringResult);
    assertEquals("Source", actualEventHolder.getSource());
    assertNull(actualInitiator.getUser());
    assertTrue(actualArgs.isEmpty());
    assertSame(initiator, actualInitiator);
    assertSame(args, actualArgs);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link V4Initiator} (default constructor).</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EventHolder#EventHolder(V4Initiator, Object, Map)}
   *   <li>{@link EventHolder#setArgs(Map)}
   *   <li>{@link EventHolder#setInitiator(V4Initiator)}
   *   <li>{@link EventHolder#setSource(Object)}
   *   <li>{@link EventHolder#toString()}
   *   <li>{@link EventHolder#getArgs()}
   *   <li>{@link EventHolder#getInitiator()}
   *   <li>{@link EventHolder#getSource()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when V4Initiator (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EventHolder.<init>()", "void EventHolder.<init>(V4Initiator, Object, Map)",
      "Map EventHolder.getArgs()", "V4Initiator EventHolder.getInitiator()", "Object EventHolder.getSource()",
      "void EventHolder.setArgs(Map)", "void EventHolder.setInitiator(V4Initiator)",
      "void EventHolder.setSource(Object)", "String EventHolder.toString()"})
  void testGettersAndSetters_whenV4Initiator() {
    // Arrange
    V4Initiator initiator = new V4Initiator();

    // Act
    EventHolder<Object> actualEventHolder = new EventHolder<>(initiator, "Source", new HashMap<>());
    HashMap<String, Object> args = new HashMap<>();
    actualEventHolder.setArgs(args);
    V4Initiator initiator2 = new V4Initiator();
    actualEventHolder.setInitiator(initiator2);
    actualEventHolder.setSource("Source");
    String actualToStringResult = actualEventHolder.toString();
    Map<String, Object> actualArgs = actualEventHolder.getArgs();
    V4Initiator actualInitiator = actualEventHolder.getInitiator();

    // Assert
    assertEquals("EventHolder(initiator=class V4Initiator {\n    user: null\n}, source=Source, args={})",
        actualToStringResult);
    assertEquals("Source", actualEventHolder.getSource());
    assertNull(actualInitiator.getUser());
    assertTrue(actualArgs.isEmpty());
    assertSame(initiator2, actualInitiator);
    assertSame(args, actualArgs);
  }
}
