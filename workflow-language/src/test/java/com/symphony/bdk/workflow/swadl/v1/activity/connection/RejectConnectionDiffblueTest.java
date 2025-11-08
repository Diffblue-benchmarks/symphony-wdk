package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RejectConnectionDiffblueTest {
  /**
   * Test {@link RejectConnection#equals(Object)}, and {@link RejectConnection#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RejectConnection#equals(Object)}
   *   <li>{@link RejectConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RejectConnection.equals(Object)", "int RejectConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RejectConnection rejectConnection = new RejectConnection();
    RejectConnection rejectConnection2 = new RejectConnection();

    // Act and Assert
    assertEquals(rejectConnection, rejectConnection2);
    int expectedHashCodeResult = rejectConnection.hashCode();
    assertEquals(expectedHashCodeResult, rejectConnection2.hashCode());
  }

  /**
   * Test {@link RejectConnection#equals(Object)}, and {@link RejectConnection#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RejectConnection#equals(Object)}
   *   <li>{@link RejectConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RejectConnection.equals(Object)", "int RejectConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RejectConnection rejectConnection = new RejectConnection();

    // Act and Assert
    assertEquals(rejectConnection, rejectConnection);
    int expectedHashCodeResult = rejectConnection.hashCode();
    assertEquals(expectedHashCodeResult, rejectConnection.hashCode());
  }

  /**
   * Test {@link RejectConnection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RejectConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RejectConnection.equals(Object)", "int RejectConnection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RejectConnection rejectConnection = new RejectConnection();
    rejectConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(rejectConnection, new RejectConnection());
  }

  /**
   * Test {@link RejectConnection#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RejectConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RejectConnection.equals(Object)", "int RejectConnection.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RejectConnection(), null);
  }

  /**
   * Test {@link RejectConnection#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RejectConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RejectConnection.equals(Object)", "int RejectConnection.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RejectConnection(), "Different type to RejectConnection");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RejectConnection}
   *   <li>{@link RejectConnection#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RejectConnection.<init>()", "java.lang.String RejectConnection.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RejectConnection actualRejectConnection = new RejectConnection();

    // Assert
    assertEquals("RejectConnection()", actualRejectConnection.toString());
    assertNull(actualRejectConnection.getOn());
    assertNull(actualRejectConnection.getObo());
    assertNull(actualRejectConnection.getElseCondition());
    assertNull(actualRejectConnection.getId());
    assertNull(actualRejectConnection.getIfCondition());
    assertNull(actualRejectConnection.getUserId());
    assertTrue(actualRejectConnection.getVariableProperties().isEmpty());
  }
}
