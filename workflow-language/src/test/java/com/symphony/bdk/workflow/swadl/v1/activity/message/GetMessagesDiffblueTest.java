package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetMessagesDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessages#equals(Object)}
   *   <li>{@link GetMessages#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    GetMessages getMessages2 = new GetMessages();

    // Act and Assert
    assertEquals(getMessages, getMessages2);
    int expectedHashCodeResult = getMessages.hashCode();
    assertEquals(expectedHashCodeResult, getMessages2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessages#equals(Object)}
   *   <li>{@link GetMessages#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setStreamId("42");

    GetMessages getMessages2 = new GetMessages();
    getMessages2.setStreamId("42");

    // Act and Assert
    assertEquals(getMessages, getMessages2);
    int expectedHashCodeResult = getMessages.hashCode();
    assertEquals(expectedHashCodeResult, getMessages2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessages#equals(Object)}
   *   <li>{@link GetMessages#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setSince("Since");

    GetMessages getMessages2 = new GetMessages();
    getMessages2.setSince("Since");

    // Act and Assert
    assertEquals(getMessages, getMessages2);
    int expectedHashCodeResult = getMessages.hashCode();
    assertEquals(expectedHashCodeResult, getMessages2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessages#equals(Object)}
   *   <li>{@link GetMessages#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setSkip(1);

    GetMessages getMessages2 = new GetMessages();
    getMessages2.setSkip(1);

    // Act and Assert
    assertEquals(getMessages, getMessages2);
    int expectedHashCodeResult = getMessages.hashCode();
    assertEquals(expectedHashCodeResult, getMessages2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessages#equals(Object)}
   *   <li>{@link GetMessages#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setLimit(1);

    GetMessages getMessages2 = new GetMessages();
    getMessages2.setLimit(1);

    // Act and Assert
    assertEquals(getMessages, getMessages2);
    int expectedHashCodeResult = getMessages.hashCode();
    assertEquals(expectedHashCodeResult, getMessages2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessages#equals(Object)}
   *   <li>{@link GetMessages#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetMessages getMessages = new GetMessages();

    // Act and Assert
    assertEquals(getMessages, getMessages);
    int expectedHashCodeResult = getMessages.hashCode();
    assertEquals(expectedHashCodeResult, getMessages.hashCode());
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getMessages, new GetMessages());
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.add("Key", mock(GetMessage.class));

    // Act and Assert
    assertNotEquals(getMessages, new GetMessages());
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setStreamId("42");

    // Act and Assert
    assertNotEquals(getMessages, new GetMessages());
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setSince("Since");

    // Act and Assert
    assertNotEquals(getMessages, new GetMessages());
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setSkip(1);

    // Act and Assert
    assertNotEquals(getMessages, new GetMessages());
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetMessages getMessages = new GetMessages();
    getMessages.setLimit(1);

    // Act and Assert
    assertNotEquals(getMessages, new GetMessages());
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetMessages getMessages = new GetMessages();

    GetMessages getMessages2 = new GetMessages();
    getMessages2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getMessages, getMessages2);
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetMessages getMessages = new GetMessages();

    GetMessages getMessages2 = new GetMessages();
    getMessages2.setSince("Since");

    // Act and Assert
    assertNotEquals(getMessages, getMessages2);
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetMessages getMessages = new GetMessages();

    GetMessages getMessages2 = new GetMessages();
    getMessages2.setSkip(1);

    // Act and Assert
    assertNotEquals(getMessages, getMessages2);
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    GetMessages getMessages = new GetMessages();

    GetMessages getMessages2 = new GetMessages();
    getMessages2.setLimit(1);

    // Act and Assert
    assertNotEquals(getMessages, getMessages2);
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetMessages(), null);
  }

  /**
   * Method under test: {@link GetMessages#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetMessages(), "Different type to GetMessages");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetMessages}
   *   <li>{@link GetMessages#setLimit(Integer)}
   *   <li>{@link GetMessages#setSince(String)}
   *   <li>{@link GetMessages#setSkip(Integer)}
   *   <li>{@link GetMessages#setStreamId(String)}
   *   <li>{@link GetMessages#toString()}
   *   <li>{@link GetMessages#getLimit()}
   *   <li>{@link GetMessages#getSince()}
   *   <li>{@link GetMessages#getSkip()}
   *   <li>{@link GetMessages#getStreamId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetMessages actualGetMessages = new GetMessages();
    actualGetMessages.setLimit(1);
    actualGetMessages.setSince("Since");
    actualGetMessages.setSkip(1);
    actualGetMessages.setStreamId("42");
    String actualToStringResult = actualGetMessages.toString();
    Integer actualLimit = actualGetMessages.getLimit();
    String actualSince = actualGetMessages.getSince();
    Integer actualSkip = actualGetMessages.getSkip();

    // Assert that nothing has changed
    assertEquals("42", actualGetMessages.getStreamId());
    assertEquals("GetMessages(streamId=42, since=Since, skip=1, limit=1)", actualToStringResult);
    assertEquals("Since", actualSince);
    assertEquals(1, actualLimit.intValue());
    assertEquals(1, actualSkip.intValue());
    assertTrue(actualGetMessages.getVariableProperties().isEmpty());
  }
}
