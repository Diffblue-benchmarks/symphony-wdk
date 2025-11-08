package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PostSharedEventDiffblueTest {
  /**
   * Test {@link PostSharedEvent#equals(Object)}, and {@link PostSharedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PostSharedEvent#equals(Object)}
   *   <li>{@link PostSharedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PostSharedEvent.equals(Object)", "int PostSharedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");

    PostSharedEvent postSharedEvent2 = new PostSharedEvent();
    postSharedEvent2.setId("42");

    // Act and Assert
    assertEquals(postSharedEvent, postSharedEvent2);
    int expectedHashCodeResult = postSharedEvent.hashCode();
    assertEquals(expectedHashCodeResult, postSharedEvent2.hashCode());
  }

  /**
   * Test {@link PostSharedEvent#equals(Object)}, and {@link PostSharedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PostSharedEvent#equals(Object)}
   *   <li>{@link PostSharedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PostSharedEvent.equals(Object)", "int PostSharedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");

    // Act and Assert
    assertEquals(postSharedEvent, postSharedEvent);
    int expectedHashCodeResult = postSharedEvent.hashCode();
    assertEquals(expectedHashCodeResult, postSharedEvent.hashCode());
  }

  /**
   * Test {@link PostSharedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PostSharedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PostSharedEvent.equals(Object)", "int PostSharedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("Id");

    PostSharedEvent postSharedEvent2 = new PostSharedEvent();
    postSharedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(postSharedEvent, postSharedEvent2);
  }

  /**
   * Test {@link PostSharedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PostSharedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PostSharedEvent.equals(Object)", "int PostSharedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");

    // Act and Assert
    assertNotEquals(postSharedEvent, null);
  }

  /**
   * Test {@link PostSharedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PostSharedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PostSharedEvent.equals(Object)", "int PostSharedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");

    // Act and Assert
    assertNotEquals(postSharedEvent, "Different type to PostSharedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PostSharedEvent}
   *   <li>{@link PostSharedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void PostSharedEvent.<init>()", "java.lang.String PostSharedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    PostSharedEvent actualPostSharedEvent = new PostSharedEvent();

    // Assert
    assertEquals("PostSharedEvent()", actualPostSharedEvent.toString());
    assertNull(actualPostSharedEvent.getId());
  }
}
