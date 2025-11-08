package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UserJoinedRoomEventDiffblueTest {
  /**
   * Test {@link UserJoinedRoomEvent#equals(Object)}, and {@link UserJoinedRoomEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UserJoinedRoomEvent#equals(Object)}
   *   <li>{@link UserJoinedRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserJoinedRoomEvent.equals(Object)", "int UserJoinedRoomEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");

    UserJoinedRoomEvent userJoinedRoomEvent2 = new UserJoinedRoomEvent();
    userJoinedRoomEvent2.setId("42");

    // Act and Assert
    assertEquals(userJoinedRoomEvent, userJoinedRoomEvent2);
    int expectedHashCodeResult = userJoinedRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userJoinedRoomEvent2.hashCode());
  }

  /**
   * Test {@link UserJoinedRoomEvent#equals(Object)}, and {@link UserJoinedRoomEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UserJoinedRoomEvent#equals(Object)}
   *   <li>{@link UserJoinedRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserJoinedRoomEvent.equals(Object)", "int UserJoinedRoomEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");

    // Act and Assert
    assertEquals(userJoinedRoomEvent, userJoinedRoomEvent);
    int expectedHashCodeResult = userJoinedRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userJoinedRoomEvent.hashCode());
  }

  /**
   * Test {@link UserJoinedRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserJoinedRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserJoinedRoomEvent.equals(Object)", "int UserJoinedRoomEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("Id");

    UserJoinedRoomEvent userJoinedRoomEvent2 = new UserJoinedRoomEvent();
    userJoinedRoomEvent2.setId("42");

    // Act and Assert
    assertNotEquals(userJoinedRoomEvent, userJoinedRoomEvent2);
  }

  /**
   * Test {@link UserJoinedRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserJoinedRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserJoinedRoomEvent.equals(Object)", "int UserJoinedRoomEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userJoinedRoomEvent, null);
  }

  /**
   * Test {@link UserJoinedRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserJoinedRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserJoinedRoomEvent.equals(Object)", "int UserJoinedRoomEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userJoinedRoomEvent, "Different type to UserJoinedRoomEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UserJoinedRoomEvent}
   *   <li>{@link UserJoinedRoomEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UserJoinedRoomEvent.<init>()", "java.lang.String UserJoinedRoomEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    UserJoinedRoomEvent actualUserJoinedRoomEvent = new UserJoinedRoomEvent();

    // Assert
    assertEquals("UserJoinedRoomEvent()", actualUserJoinedRoomEvent.toString());
    assertNull(actualUserJoinedRoomEvent.getId());
  }
}
