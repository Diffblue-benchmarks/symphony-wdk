package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UserLeftRoomEventDiffblueTest {
  /**
   * Test {@link UserLeftRoomEvent#equals(Object)}, and {@link UserLeftRoomEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UserLeftRoomEvent#equals(Object)}
   *   <li>{@link UserLeftRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserLeftRoomEvent.equals(Object)", "int UserLeftRoomEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");

    UserLeftRoomEvent userLeftRoomEvent2 = new UserLeftRoomEvent();
    userLeftRoomEvent2.setId("42");

    // Act and Assert
    assertEquals(userLeftRoomEvent, userLeftRoomEvent2);
    int expectedHashCodeResult = userLeftRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userLeftRoomEvent2.hashCode());
  }

  /**
   * Test {@link UserLeftRoomEvent#equals(Object)}, and {@link UserLeftRoomEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UserLeftRoomEvent#equals(Object)}
   *   <li>{@link UserLeftRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserLeftRoomEvent.equals(Object)", "int UserLeftRoomEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");

    // Act and Assert
    assertEquals(userLeftRoomEvent, userLeftRoomEvent);
    int expectedHashCodeResult = userLeftRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userLeftRoomEvent.hashCode());
  }

  /**
   * Test {@link UserLeftRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserLeftRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserLeftRoomEvent.equals(Object)", "int UserLeftRoomEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("Id");

    UserLeftRoomEvent userLeftRoomEvent2 = new UserLeftRoomEvent();
    userLeftRoomEvent2.setId("42");

    // Act and Assert
    assertNotEquals(userLeftRoomEvent, userLeftRoomEvent2);
  }

  /**
   * Test {@link UserLeftRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserLeftRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserLeftRoomEvent.equals(Object)", "int UserLeftRoomEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userLeftRoomEvent, null);
  }

  /**
   * Test {@link UserLeftRoomEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserLeftRoomEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UserLeftRoomEvent.equals(Object)", "int UserLeftRoomEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userLeftRoomEvent, "Different type to UserLeftRoomEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UserLeftRoomEvent}
   *   <li>{@link UserLeftRoomEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UserLeftRoomEvent.<init>()", "java.lang.String UserLeftRoomEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    UserLeftRoomEvent actualUserLeftRoomEvent = new UserLeftRoomEvent();

    // Assert
    assertEquals("UserLeftRoomEvent()", actualUserLeftRoomEvent.toString());
    assertNull(actualUserLeftRoomEvent.getId());
  }
}
