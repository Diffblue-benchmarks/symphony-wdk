package com.symphony.bdk.workflow.engine.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.gen.api.model.V4Initiator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class EventHolderDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EventHolder#equals(Object)}
   *   <li>{@link EventHolder#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link EventHolder#equals(Object)}
   *   <li>{@link EventHolder#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link EventHolder#equals(Object)}
   *   <li>{@link EventHolder#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    // Act and Assert
    assertEquals(eventHolder, eventHolder);
    int expectedHashCodeResult = eventHolder.hashCode();
    assertEquals(expectedHashCodeResult, eventHolder.hashCode());
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    V4Initiator initiator = new V4Initiator();
    EventHolder<Object> eventHolder = new EventHolder<>(initiator, "Source", new HashMap<>());

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    V4Initiator initiator = new V4Initiator();

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>(initiator, "Source", new HashMap<>()));
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    eventHolder.setSource("Source");

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    eventHolder.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    V4Initiator initiator = mock(V4Initiator.class);
    EventHolder<Object> eventHolder = new EventHolder<>(initiator, "Source", new HashMap<>());

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    EventHolder<Object> eventHolder2 = new EventHolder<>();
    eventHolder2.setSource("Source");

    // Act and Assert
    assertNotEquals(eventHolder, eventHolder2);
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    EventHolder<Object> eventHolder2 = new EventHolder<>();
    eventHolder2.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(eventHolder, eventHolder2);
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();
    eventHolder.setSource(new EventHolder<>());

    // Act and Assert
    assertNotEquals(eventHolder, new EventHolder<>());
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    // Act and Assert
    assertNotEquals(eventHolder, null);
  }

  /**
   * Method under test: {@link EventHolder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    EventHolder<Object> eventHolder = new EventHolder<>();

    // Act and Assert
    assertNotEquals(eventHolder, "Different type to EventHolder");
  }

  /**
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

    // Assert that nothing has changed
    assertEquals("EventHolder(initiator=class V4Initiator {\n    user: null\n}, source=Source, args={})",
        actualToStringResult);
    assertEquals("Source", actualEventHolder.getSource());
    assertTrue(actualArgs.isEmpty());
    assertSame(initiator, actualInitiator);
    assertSame(args, actualArgs);
  }

  /**
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
  void testGettersAndSetters2() {
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

    // Assert that nothing has changed
    assertEquals("EventHolder(initiator=class V4Initiator {\n    user: null\n}, source=Source, args={})",
        actualToStringResult);
    assertEquals("Source", actualEventHolder.getSource());
    assertTrue(actualArgs.isEmpty());
    assertSame(initiator2, actualInitiator);
    assertSame(args, actualArgs);
  }
}
