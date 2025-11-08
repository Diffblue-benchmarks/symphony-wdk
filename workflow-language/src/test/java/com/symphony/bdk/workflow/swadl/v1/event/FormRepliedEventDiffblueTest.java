package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class FormRepliedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link FormRepliedEvent#equals(Object)}
   *   <li>{@link FormRepliedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link FormRepliedEvent#equals(Object)}
   *   <li>{@link FormRepliedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link FormRepliedEvent#equals(Object)}
   */
  @Test
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
  void testGettersAndSetters() {
    // Arrange and Act
    FormRepliedEvent actualFormRepliedEvent = new FormRepliedEvent();
    actualFormRepliedEvent.setExclusive(true);
    actualFormRepliedEvent.setFormId("42");
    String actualToStringResult = actualFormRepliedEvent.toString();
    Boolean actualExclusive = actualFormRepliedEvent.getExclusive();

    // Assert that nothing has changed
    assertEquals("42", actualFormRepliedEvent.getFormId());
    assertEquals("FormRepliedEvent(formId=42, exclusive=true)", actualToStringResult);
    assertTrue(actualExclusive);
  }
}
