package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MessageReceivedEventDiffblueTest {
  /**
   * Test {@link MessageReceivedEvent#equals(Object)}, and {@link MessageReceivedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageReceivedEvent#equals(Object)}
   *   <li>{@link MessageReceivedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent("Not all who wander are lost");
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(true);

    MessageReceivedEvent messageReceivedEvent2 = new MessageReceivedEvent();
    messageReceivedEvent2.setContent("Not all who wander are lost");
    messageReceivedEvent2.setId("42");
    messageReceivedEvent2.setRequiresBotMention(true);

    // Act and Assert
    assertEquals(messageReceivedEvent, messageReceivedEvent2);
    int expectedHashCodeResult = messageReceivedEvent.hashCode();
    assertEquals(expectedHashCodeResult, messageReceivedEvent2.hashCode());
  }

  /**
   * Test {@link MessageReceivedEvent#equals(Object)}, and {@link MessageReceivedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageReceivedEvent#equals(Object)}
   *   <li>{@link MessageReceivedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent(null);
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(true);

    MessageReceivedEvent messageReceivedEvent2 = new MessageReceivedEvent();
    messageReceivedEvent2.setContent(null);
    messageReceivedEvent2.setId("42");
    messageReceivedEvent2.setRequiresBotMention(true);

    // Act and Assert
    assertEquals(messageReceivedEvent, messageReceivedEvent2);
    int expectedHashCodeResult = messageReceivedEvent.hashCode();
    assertEquals(expectedHashCodeResult, messageReceivedEvent2.hashCode());
  }

  /**
   * Test {@link MessageReceivedEvent#equals(Object)}, and {@link MessageReceivedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageReceivedEvent#equals(Object)}
   *   <li>{@link MessageReceivedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent("Not all who wander are lost");
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(true);

    // Act and Assert
    assertEquals(messageReceivedEvent, messageReceivedEvent);
    int expectedHashCodeResult = messageReceivedEvent.hashCode();
    assertEquals(expectedHashCodeResult, messageReceivedEvent.hashCode());
  }

  /**
   * Test {@link MessageReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent("42");
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(true);

    MessageReceivedEvent messageReceivedEvent2 = new MessageReceivedEvent();
    messageReceivedEvent2.setContent("Not all who wander are lost");
    messageReceivedEvent2.setId("42");
    messageReceivedEvent2.setRequiresBotMention(true);

    // Act and Assert
    assertNotEquals(messageReceivedEvent, messageReceivedEvent2);
  }

  /**
   * Test {@link MessageReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent(null);
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(true);

    MessageReceivedEvent messageReceivedEvent2 = new MessageReceivedEvent();
    messageReceivedEvent2.setContent("Not all who wander are lost");
    messageReceivedEvent2.setId("42");
    messageReceivedEvent2.setRequiresBotMention(true);

    // Act and Assert
    assertNotEquals(messageReceivedEvent, messageReceivedEvent2);
  }

  /**
   * Test {@link MessageReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent("Not all who wander are lost");
    messageReceivedEvent.setId("Not all who wander are lost");
    messageReceivedEvent.setRequiresBotMention(true);

    MessageReceivedEvent messageReceivedEvent2 = new MessageReceivedEvent();
    messageReceivedEvent2.setContent("Not all who wander are lost");
    messageReceivedEvent2.setId("42");
    messageReceivedEvent2.setRequiresBotMention(true);

    // Act and Assert
    assertNotEquals(messageReceivedEvent, messageReceivedEvent2);
  }

  /**
   * Test {@link MessageReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent("Not all who wander are lost");
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(false);

    MessageReceivedEvent messageReceivedEvent2 = new MessageReceivedEvent();
    messageReceivedEvent2.setContent("Not all who wander are lost");
    messageReceivedEvent2.setId("42");
    messageReceivedEvent2.setRequiresBotMention(true);

    // Act and Assert
    assertNotEquals(messageReceivedEvent, messageReceivedEvent2);
  }

  /**
   * Test {@link MessageReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent("Not all who wander are lost");
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(true);

    // Act and Assert
    assertNotEquals(messageReceivedEvent, null);
  }

  /**
   * Test {@link MessageReceivedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MessageReceivedEvent.equals(Object)", "int MessageReceivedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    messageReceivedEvent.setContent("Not all who wander are lost");
    messageReceivedEvent.setId("42");
    messageReceivedEvent.setRequiresBotMention(true);

    // Act and Assert
    assertNotEquals(messageReceivedEvent, "Different type to MessageReceivedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link MessageReceivedEvent}
   *   <li>{@link MessageReceivedEvent#setContent(String)}
   *   <li>{@link MessageReceivedEvent#setRequiresBotMention(boolean)}
   *   <li>{@link MessageReceivedEvent#toString()}
   *   <li>{@link MessageReceivedEvent#getContent()}
   *   <li>{@link MessageReceivedEvent#isRequiresBotMention()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MessageReceivedEvent.<init>()", "String MessageReceivedEvent.getContent()",
      "boolean MessageReceivedEvent.isRequiresBotMention()", "void MessageReceivedEvent.setContent(String)",
      "void MessageReceivedEvent.setRequiresBotMention(boolean)", "String MessageReceivedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    MessageReceivedEvent actualMessageReceivedEvent = new MessageReceivedEvent();
    actualMessageReceivedEvent.setContent("Not all who wander are lost");
    actualMessageReceivedEvent.setRequiresBotMention(true);
    String actualToStringResult = actualMessageReceivedEvent.toString();
    String actualContent = actualMessageReceivedEvent.getContent();
    boolean actualIsRequiresBotMentionResult = actualMessageReceivedEvent.isRequiresBotMention();

    // Assert
    assertEquals("MessageReceivedEvent(content=Not all who wander are lost, requiresBotMention=true)",
        actualToStringResult);
    assertEquals("Not all who wander are lost", actualContent);
    assertNull(actualMessageReceivedEvent.getId());
    assertTrue(actualIsRequiresBotMentionResult);
  }
}
