package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import org.junit.jupiter.api.Test;

class CreateSystemUserDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateSystemUser#equals(Object)}
   *   <li>{@link CreateSystemUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();
    CreateSystemUser createSystemUser2 = new CreateSystemUser();

    // Act and Assert
    assertEquals(createSystemUser, createSystemUser2);
    int expectedHashCodeResult = createSystemUser.hashCode();
    assertEquals(expectedHashCodeResult, createSystemUser2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateSystemUser#equals(Object)}
   *   <li>{@link CreateSystemUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();

    // Act and Assert
    assertEquals(createSystemUser, createSystemUser);
    int expectedHashCodeResult = createSystemUser.hashCode();
    assertEquals(expectedHashCodeResult, createSystemUser.hashCode());
  }

  /**
   * Method under test: {@link CreateSystemUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();
    createSystemUser.add("SYSTEM", "Value");

    // Act and Assert
    assertNotEquals(createSystemUser, new CreateSystemUser());
  }

  /**
   * Method under test: {@link CreateSystemUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();
    createSystemUser.add("SYSTEM", mock(AddUserRole.class));

    // Act and Assert
    assertNotEquals(createSystemUser, new CreateSystemUser());
  }

  /**
   * Method under test: {@link CreateSystemUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateSystemUser(), null);
  }

  /**
   * Method under test: {@link CreateSystemUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateSystemUser(), "Different type to CreateSystemUser");
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link CreateSystemUser}
   */
  @Test
  void testNewCreateSystemUser() {
    // Arrange and Act
    CreateSystemUser actualCreateSystemUser = new CreateSystemUser();

    // Assert
    assertEquals("SYSTEM", actualCreateSystemUser.getType());
    assertNull(actualCreateSystemUser.getOn());
    assertNull(actualCreateSystemUser.getBusiness());
    assertNull(actualCreateSystemUser.getContact());
    assertNull(actualCreateSystemUser.getKeys());
    assertNull(actualCreateSystemUser.getPassword());
    assertNull(actualCreateSystemUser.getElseCondition());
    assertNull(actualCreateSystemUser.getId());
    assertNull(actualCreateSystemUser.getIfCondition());
    RelationalEvents events = actualCreateSystemUser.getEvents();
    assertNull(events.getParentId());
    assertNull(actualCreateSystemUser.getDisplayName());
    assertNull(actualCreateSystemUser.getEmail());
    assertNull(actualCreateSystemUser.getFirstname());
    assertNull(actualCreateSystemUser.getLastname());
    assertNull(actualCreateSystemUser.getRecommendedLanguage());
    assertNull(actualCreateSystemUser.getStatus());
    assertNull(actualCreateSystemUser.getUsername());
    assertNull(actualCreateSystemUser.getRoles());
    assertNull(actualCreateSystemUser.getEntitlements());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualCreateSystemUser.getVariableProperties().isEmpty());
  }

  /**
   * Method under test: {@link CreateSystemUser#toString()}
   */
  @Test
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("CreateSystemUser()", (new CreateSystemUser()).toString());
  }
}
