package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class FormRepliedEventDiffblueTest {
  /**
   * Test {@link FormRepliedEvent#equals(Object)}, and {@link FormRepliedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FormRepliedEvent#equals(Object)}
   *   <li>{@link FormRepliedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("42");

    FormRepliedEvent formRepliedEvent2 = new FormRepliedEvent();
    formRepliedEvent2.setExclusive(true);
    formRepliedEvent2.setFormId("42");
    formRepliedEvent2.setId("42");

    // Act and Assert
    assertEquals(formRepliedEvent, formRepliedEvent2);
    int expectedHashCodeResult = formRepliedEvent.hashCode();
    assertEquals(expectedHashCodeResult, formRepliedEvent2.hashCode());
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}, and {@link FormRepliedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FormRepliedEvent#equals(Object)}
   *   <li>{@link FormRepliedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(null);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("42");

    FormRepliedEvent formRepliedEvent2 = new FormRepliedEvent();
    formRepliedEvent2.setExclusive(null);
    formRepliedEvent2.setFormId("42");
    formRepliedEvent2.setId("42");

    // Act and Assert
    assertEquals(formRepliedEvent, formRepliedEvent2);
    int expectedHashCodeResult = formRepliedEvent.hashCode();
    assertEquals(expectedHashCodeResult, formRepliedEvent2.hashCode());
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}, and {@link FormRepliedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FormRepliedEvent#equals(Object)}
   *   <li>{@link FormRepliedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId(null);
    formRepliedEvent.setId("42");

    FormRepliedEvent formRepliedEvent2 = new FormRepliedEvent();
    formRepliedEvent2.setExclusive(true);
    formRepliedEvent2.setFormId(null);
    formRepliedEvent2.setId("42");

    // Act and Assert
    assertEquals(formRepliedEvent, formRepliedEvent2);
    int expectedHashCodeResult = formRepliedEvent.hashCode();
    assertEquals(expectedHashCodeResult, formRepliedEvent2.hashCode());
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}, and {@link FormRepliedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link FormRepliedEvent#equals(Object)}
   *   <li>{@link FormRepliedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("42");

    // Act and Assert
    assertEquals(formRepliedEvent, formRepliedEvent);
    int expectedHashCodeResult = formRepliedEvent.hashCode();
    assertEquals(expectedHashCodeResult, formRepliedEvent.hashCode());
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(false);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("42");

    FormRepliedEvent formRepliedEvent2 = new FormRepliedEvent();
    formRepliedEvent2.setExclusive(true);
    formRepliedEvent2.setFormId("42");
    formRepliedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(formRepliedEvent, formRepliedEvent2);
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(null);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("42");

    FormRepliedEvent formRepliedEvent2 = new FormRepliedEvent();
    formRepliedEvent2.setExclusive(true);
    formRepliedEvent2.setFormId("42");
    formRepliedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(formRepliedEvent, formRepliedEvent2);
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId("Form Id");
    formRepliedEvent.setId("42");

    FormRepliedEvent formRepliedEvent2 = new FormRepliedEvent();
    formRepliedEvent2.setExclusive(true);
    formRepliedEvent2.setFormId("42");
    formRepliedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(formRepliedEvent, formRepliedEvent2);
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId(null);
    formRepliedEvent.setId("42");

    FormRepliedEvent formRepliedEvent2 = new FormRepliedEvent();
    formRepliedEvent2.setExclusive(true);
    formRepliedEvent2.setFormId("42");
    formRepliedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(formRepliedEvent, formRepliedEvent2);
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("Id");

    FormRepliedEvent formRepliedEvent2 = new FormRepliedEvent();
    formRepliedEvent2.setExclusive(true);
    formRepliedEvent2.setFormId("42");
    formRepliedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(formRepliedEvent, formRepliedEvent2);
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("42");

    // Act and Assert
    assertNotEquals(formRepliedEvent, null);
  }

  /**
   * Test {@link FormRepliedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean FormRepliedEvent.equals(Object)", "int FormRepliedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(true);
    formRepliedEvent.setFormId("42");
    formRepliedEvent.setId("42");

    // Act and Assert
    assertNotEquals(formRepliedEvent, "Different type to FormRepliedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link FormRepliedEvent}
   *   <li>{@link FormRepliedEvent#setExclusive(Boolean)}
   *   <li>{@link FormRepliedEvent#setFormId(String)}
   *   <li>{@link FormRepliedEvent#toString()}
   *   <li>{@link FormRepliedEvent#getExclusive()}
   *   <li>{@link FormRepliedEvent#getFormId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FormRepliedEvent.<init>()", "Boolean FormRepliedEvent.getExclusive()",
      "String FormRepliedEvent.getFormId()", "void FormRepliedEvent.setExclusive(Boolean)",
      "void FormRepliedEvent.setFormId(String)", "String FormRepliedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    FormRepliedEvent actualFormRepliedEvent = new FormRepliedEvent();
    actualFormRepliedEvent.setExclusive(true);
    actualFormRepliedEvent.setFormId("42");
    String actualToStringResult = actualFormRepliedEvent.toString();
    Boolean actualExclusive = actualFormRepliedEvent.getExclusive();

    // Assert
    assertEquals("42", actualFormRepliedEvent.getFormId());
    assertEquals("FormRepliedEvent(formId=42, exclusive=true)", actualToStringResult);
    assertNull(actualFormRepliedEvent.getId());
    assertTrue(actualExclusive);
  }
}
