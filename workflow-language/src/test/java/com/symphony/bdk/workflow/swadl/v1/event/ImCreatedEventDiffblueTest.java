package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ImCreatedEventDiffblueTest {
  /**
   * Test {@link ImCreatedEvent#equals(Object)}, and {@link ImCreatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ImCreatedEvent#equals(Object)}
   *   <li>{@link ImCreatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImCreatedEvent.equals(Object)", "int ImCreatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");

    ImCreatedEvent imCreatedEvent2 = new ImCreatedEvent();
    imCreatedEvent2.setId("42");

    // Act and Assert
    assertEquals(imCreatedEvent, imCreatedEvent2);
    int expectedHashCodeResult = imCreatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, imCreatedEvent2.hashCode());
  }

  /**
   * Test {@link ImCreatedEvent#equals(Object)}, and {@link ImCreatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ImCreatedEvent#equals(Object)}
   *   <li>{@link ImCreatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImCreatedEvent.equals(Object)", "int ImCreatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");

    // Act and Assert
    assertEquals(imCreatedEvent, imCreatedEvent);
    int expectedHashCodeResult = imCreatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, imCreatedEvent.hashCode());
  }

  /**
   * Test {@link ImCreatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ImCreatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImCreatedEvent.equals(Object)", "int ImCreatedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("Id");

    ImCreatedEvent imCreatedEvent2 = new ImCreatedEvent();
    imCreatedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(imCreatedEvent, imCreatedEvent2);
  }

  /**
   * Test {@link ImCreatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ImCreatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImCreatedEvent.equals(Object)", "int ImCreatedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(imCreatedEvent, null);
  }

  /**
   * Test {@link ImCreatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ImCreatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ImCreatedEvent.equals(Object)", "int ImCreatedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(imCreatedEvent, "Different type to ImCreatedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ImCreatedEvent}
   *   <li>{@link ImCreatedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ImCreatedEvent.<init>()", "java.lang.String ImCreatedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    ImCreatedEvent actualImCreatedEvent = new ImCreatedEvent();

    // Assert
    assertEquals("ImCreatedEvent()", actualImCreatedEvent.toString());
    assertNull(actualImCreatedEvent.getId());
  }
}
