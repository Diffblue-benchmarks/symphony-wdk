package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MessageSuppressedEventDiffblueTest {
  /**
   * Test {@link MessageSuppressedEvent#equals(Object)}, and {@link MessageSuppressedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSuppressedEvent#equals(Object)}
   *   <li>{@link MessageSuppressedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageSuppressedEvent.equals(Object)", "int MessageSuppressedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");

    MessageSuppressedEvent messageSuppressedEvent2 = new MessageSuppressedEvent();
    messageSuppressedEvent2.setId("42");

    // Act and Assert
    assertEquals(messageSuppressedEvent, messageSuppressedEvent2);
    int expectedHashCodeResult = messageSuppressedEvent.hashCode();
    assertEquals(expectedHashCodeResult, messageSuppressedEvent2.hashCode());
  }

  /**
   * Test {@link MessageSuppressedEvent#equals(Object)}, and {@link MessageSuppressedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSuppressedEvent#equals(Object)}
   *   <li>{@link MessageSuppressedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageSuppressedEvent.equals(Object)", "int MessageSuppressedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");

    // Act and Assert
    assertEquals(messageSuppressedEvent, messageSuppressedEvent);
    int expectedHashCodeResult = messageSuppressedEvent.hashCode();
    assertEquals(expectedHashCodeResult, messageSuppressedEvent.hashCode());
  }

  /**
   * Test {@link MessageSuppressedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSuppressedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageSuppressedEvent.equals(Object)", "int MessageSuppressedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("Id");

    MessageSuppressedEvent messageSuppressedEvent2 = new MessageSuppressedEvent();
    messageSuppressedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(messageSuppressedEvent, messageSuppressedEvent2);
  }

  /**
   * Test {@link MessageSuppressedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSuppressedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageSuppressedEvent.equals(Object)", "int MessageSuppressedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");

    // Act and Assert
    assertNotEquals(messageSuppressedEvent, null);
  }

  /**
   * Test {@link MessageSuppressedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageSuppressedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageSuppressedEvent.equals(Object)", "int MessageSuppressedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");

    // Act and Assert
    assertNotEquals(messageSuppressedEvent, "Different type to MessageSuppressedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link MessageSuppressedEvent}
   *   <li>{@link MessageSuppressedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MessageSuppressedEvent.<init>()", "java.lang.String MessageSuppressedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    MessageSuppressedEvent actualMessageSuppressedEvent = new MessageSuppressedEvent();

    // Assert
    assertEquals("MessageSuppressedEvent()", actualMessageSuppressedEvent.toString());
    assertNull(actualMessageSuppressedEvent.getId());
  }
}
