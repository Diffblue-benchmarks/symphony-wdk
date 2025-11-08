package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MessageReceivedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageReceivedEvent#equals(Object)}
   *   <li>{@link MessageReceivedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link MessageReceivedEvent#equals(Object)}
   *   <li>{@link MessageReceivedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link MessageReceivedEvent#equals(Object)}
   */
  @Test
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
  void testGettersAndSetters() {
    // Arrange and Act
    MessageReceivedEvent actualMessageReceivedEvent = new MessageReceivedEvent();
    actualMessageReceivedEvent.setContent("Not all who wander are lost");
    actualMessageReceivedEvent.setRequiresBotMention(true);
    String actualToStringResult = actualMessageReceivedEvent.toString();
    String actualContent = actualMessageReceivedEvent.getContent();

    // Assert that nothing has changed
    assertEquals("MessageReceivedEvent(content=Not all who wander are lost, requiresBotMention=true)",
        actualToStringResult);
    assertEquals("Not all who wander are lost", actualContent);
    assertTrue(actualMessageReceivedEvent.isRequiresBotMention());
  }
}
