package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import org.junit.jupiter.api.Test;

class UpdateSystemUserDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateSystemUser#equals(Object)}
   *   <li>{@link UpdateSystemUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();
    UpdateSystemUser updateSystemUser2 = new UpdateSystemUser();

    // Act and Assert
    assertEquals(updateSystemUser, updateSystemUser2);
    int expectedHashCodeResult = updateSystemUser.hashCode();
    assertEquals(expectedHashCodeResult, updateSystemUser2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateSystemUser#equals(Object)}
   *   <li>{@link UpdateSystemUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();

    // Act and Assert
    assertEquals(updateSystemUser, updateSystemUser);
    int expectedHashCodeResult = updateSystemUser.hashCode();
    assertEquals(expectedHashCodeResult, updateSystemUser.hashCode());
  }

  /**
   * Method under test: {@link UpdateSystemUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();
    updateSystemUser.add("SYSTEM", "Value");

    // Act and Assert
    assertNotEquals(updateSystemUser, new UpdateSystemUser());
  }

  /**
   * Method under test: {@link UpdateSystemUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();
    updateSystemUser.add("SYSTEM", mock(CreateSystemUser.class));

    // Act and Assert
    assertNotEquals(updateSystemUser, new UpdateSystemUser());
  }

  /**
   * Method under test: {@link UpdateSystemUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateSystemUser(), null);
  }

  /**
   * Method under test: {@link UpdateSystemUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateSystemUser(), "Different type to UpdateSystemUser");
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link UpdateSystemUser}
   */
  @Test
  void testNewUpdateSystemUser() {
    // Arrange and Act
    UpdateSystemUser actualUpdateSystemUser = new UpdateSystemUser();

    // Assert
    assertEquals("SYSTEM", actualUpdateSystemUser.getType());
    assertNull(actualUpdateSystemUser.getOn());
    assertNull(actualUpdateSystemUser.getBusiness());
    assertNull(actualUpdateSystemUser.getContact());
    assertNull(actualUpdateSystemUser.getKeys());
    assertNull(actualUpdateSystemUser.getPassword());
    assertNull(actualUpdateSystemUser.getElseCondition());
    assertNull(actualUpdateSystemUser.getId());
    assertNull(actualUpdateSystemUser.getIfCondition());
    RelationalEvents events = actualUpdateSystemUser.getEvents();
    assertNull(events.getParentId());
    assertNull(actualUpdateSystemUser.getDisplayName());
    assertNull(actualUpdateSystemUser.getEmail());
    assertNull(actualUpdateSystemUser.getFirstname());
    assertNull(actualUpdateSystemUser.getLastname());
    assertNull(actualUpdateSystemUser.getRecommendedLanguage());
    assertNull(actualUpdateSystemUser.getStatus());
    assertNull(actualUpdateSystemUser.getUsername());
    assertNull(actualUpdateSystemUser.getUserId());
    assertNull(actualUpdateSystemUser.getRoles());
    assertNull(actualUpdateSystemUser.getEntitlements());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualUpdateSystemUser.getVariableProperties().isEmpty());
  }

  /**
   * Method under test: {@link UpdateSystemUser#toString()}
   */
  @Test
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("UpdateSystemUser()", (new UpdateSystemUser()).toString());
  }
}
