package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UserRequestedToJoinRoomEventDiffblueTest {
  /**
   * Test {@link UserRequestedToJoinRoomEvent#equals(Object)}, and {@link UserRequestedToJoinRoomEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UserRequestedToJoinRoomEvent#equals(Object)}
   *   <li>{@link UserRequestedToJoinRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserRequestedToJoinRoomEvent.equals(Object)",
      "int UserRequestedToJoinRoomEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");

    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent2 = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent2.setId("42");

    // Act and Assert
    assertEquals(userRequestedToJoinRoomEvent, userRequestedToJoinRoomEvent2);
    int expectedHashCodeResult = userRequestedToJoinRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userRequestedToJoinRoomEvent2.hashCode());
  }

  /**
   * Test {@link UserRequestedToJoinRoomEvent#equals(Object)}, and {@link UserRequestedToJoinRoomEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UserRequestedToJoinRoomEvent#equals(Object)}
   *   <li>{@link UserRequestedToJoinRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserRequestedToJoinRoomEvent.equals(Object)",
      "int UserRequestedToJoinRoomEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");

    // Act and Assert
    assertEquals(userRequestedToJoinRoomEvent, userRequestedToJoinRoomEvent);
    int expectedHashCodeResult = userRequestedToJoinRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userRequestedToJoinRoomEvent.hashCode());
  }

  /**
   * Test {@link UserRequestedToJoinRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserRequestedToJoinRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserRequestedToJoinRoomEvent.equals(Object)",
      "int UserRequestedToJoinRoomEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("Id");

    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent2 = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent2.setId("42");

    // Act and Assert
    assertNotEquals(userRequestedToJoinRoomEvent, userRequestedToJoinRoomEvent2);
  }

  /**
   * Test {@link UserRequestedToJoinRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserRequestedToJoinRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserRequestedToJoinRoomEvent.equals(Object)",
      "int UserRequestedToJoinRoomEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userRequestedToJoinRoomEvent, null);
  }

  /**
   * Test {@link UserRequestedToJoinRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserRequestedToJoinRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserRequestedToJoinRoomEvent.equals(Object)",
      "int UserRequestedToJoinRoomEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userRequestedToJoinRoomEvent, "Different type to UserRequestedToJoinRoomEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UserRequestedToJoinRoomEvent}
   *   <li>{@link UserRequestedToJoinRoomEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UserRequestedToJoinRoomEvent.<init>()",
      "java.lang.String UserRequestedToJoinRoomEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    UserRequestedToJoinRoomEvent actualUserRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();

    // Assert
    assertEquals("UserRequestedToJoinRoomEvent()", actualUserRequestedToJoinRoomEvent.toString());
    assertNull(actualUserRequestedToJoinRoomEvent.getId());
  }
}
