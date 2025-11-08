package com.symphony.bdk.workflow.swadl.v1.activity.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetStreamDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStream#equals(Object)}
   *   <li>{@link GetStream#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetStream getStream = new GetStream();
    GetStream getStream2 = new GetStream();

    // Act and Assert
    assertEquals(getStream, getStream2);
    int expectedHashCodeResult = getStream.hashCode();
    assertEquals(expectedHashCodeResult, getStream2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStream#equals(Object)}
   *   <li>{@link GetStream#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetStream getStream = new GetStream();
    getStream.setStreamId("42");

    GetStream getStream2 = new GetStream();
    getStream2.setStreamId("42");

    // Act and Assert
    assertEquals(getStream, getStream2);
    int expectedHashCodeResult = getStream.hashCode();
    assertEquals(expectedHashCodeResult, getStream2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStream#equals(Object)}
   *   <li>{@link GetStream#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetStream getStream = new GetStream();

    // Act and Assert
    assertEquals(getStream, getStream);
    int expectedHashCodeResult = getStream.hashCode();
    assertEquals(expectedHashCodeResult, getStream.hashCode());
  }

  /**
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetStream getStream = new GetStream();
    getStream.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getStream, new GetStream());
  }

  /**
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetStream getStream = new GetStream();
    getStream.add("Key", mock(GetUserStreams.class));

    // Act and Assert
    assertNotEquals(getStream, new GetStream());
  }

  /**
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetStream getStream = new GetStream();
    getStream.setStreamId("42");

    // Act and Assert
    assertNotEquals(getStream, new GetStream());
  }

  /**
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetStream getStream = new GetStream();

    GetStream getStream2 = new GetStream();
    getStream2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getStream, getStream2);
  }

  /**
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStream(), null);
  }

  /**
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStream(), "Different type to GetStream");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetStream}
   *   <li>{@link GetStream#setStreamId(String)}
   *   <li>{@link GetStream#toString()}
   *   <li>{@link GetStream#getStreamId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetStream actualGetStream = new GetStream();
    actualGetStream.setStreamId("42");
    String actualToStringResult = actualGetStream.toString();

    // Assert that nothing has changed
    assertEquals("42", actualGetStream.getStreamId());
    assertEquals("GetStream(streamId=42)", actualToStringResult);
    assertTrue(actualGetStream.getVariableProperties().isEmpty());
  }
}
