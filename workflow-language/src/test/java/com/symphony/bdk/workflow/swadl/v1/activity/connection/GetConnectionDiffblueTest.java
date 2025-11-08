package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetConnectionDiffblueTest {
  /**
   * Test {@link GetConnection#equals(Object)}, and {@link GetConnection#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnection#equals(Object)}
   *   <li>{@link GetConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnection.equals(Object)", "int GetConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetConnection getConnection = new GetConnection();
    GetConnection getConnection2 = new GetConnection();

    // Act and Assert
    assertEquals(getConnection, getConnection2);
    int expectedHashCodeResult = getConnection.hashCode();
    assertEquals(expectedHashCodeResult, getConnection2.hashCode());
  }

  /**
   * Test {@link GetConnection#equals(Object)}, and {@link GetConnection#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnection#equals(Object)}
   *   <li>{@link GetConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnection.equals(Object)", "int GetConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetConnection getConnection = new GetConnection();

    // Act and Assert
    assertEquals(getConnection, getConnection);
    int expectedHashCodeResult = getConnection.hashCode();
    assertEquals(expectedHashCodeResult, getConnection.hashCode());
  }

  /**
   * Test {@link GetConnection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnection.equals(Object)", "int GetConnection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetConnection getConnection = new GetConnection();
    getConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getConnection, new GetConnection());
  }

  /**
   * Test {@link GetConnection#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnection.equals(Object)", "int GetConnection.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetConnection(), null);
  }

  /**
   * Test {@link GetConnection#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnection.equals(Object)", "int GetConnection.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetConnection(), "Different type to GetConnection");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetConnection}
   *   <li>{@link GetConnection#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetConnection.<init>()", "java.lang.String GetConnection.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetConnection actualGetConnection = new GetConnection();

    // Assert
    assertEquals("GetConnection()", actualGetConnection.toString());
    assertNull(actualGetConnection.getOn());
    assertNull(actualGetConnection.getObo());
    assertNull(actualGetConnection.getElseCondition());
    assertNull(actualGetConnection.getId());
    assertNull(actualGetConnection.getIfCondition());
    assertNull(actualGetConnection.getUserId());
    assertTrue(actualGetConnection.getVariableProperties().isEmpty());
  }
}
