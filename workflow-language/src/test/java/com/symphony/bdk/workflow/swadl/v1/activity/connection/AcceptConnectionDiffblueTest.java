package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AcceptConnectionDiffblueTest {
  /**
   * Test {@link AcceptConnection#equals(Object)}, and {@link AcceptConnection#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AcceptConnection#equals(Object)}
   *   <li>{@link AcceptConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AcceptConnection.equals(Object)", "int AcceptConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();
    AcceptConnection acceptConnection2 = new AcceptConnection();

    // Act and Assert
    assertEquals(acceptConnection, acceptConnection2);
    int expectedHashCodeResult = acceptConnection.hashCode();
    assertEquals(expectedHashCodeResult, acceptConnection2.hashCode());
  }

  /**
   * Test {@link AcceptConnection#equals(Object)}, and {@link AcceptConnection#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AcceptConnection#equals(Object)}
   *   <li>{@link AcceptConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AcceptConnection.equals(Object)", "int AcceptConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();

    // Act and Assert
    assertEquals(acceptConnection, acceptConnection);
    int expectedHashCodeResult = acceptConnection.hashCode();
    assertEquals(expectedHashCodeResult, acceptConnection.hashCode());
  }

  /**
   * Test {@link AcceptConnection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcceptConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AcceptConnection.equals(Object)", "int AcceptConnection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();
    acceptConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(acceptConnection, new AcceptConnection());
  }

  /**
   * Test {@link AcceptConnection#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcceptConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AcceptConnection.equals(Object)", "int AcceptConnection.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AcceptConnection(), null);
  }

  /**
   * Test {@link AcceptConnection#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AcceptConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AcceptConnection.equals(Object)", "int AcceptConnection.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AcceptConnection(), "Different type to AcceptConnection");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AcceptConnection}
   *   <li>{@link AcceptConnection#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AcceptConnection.<init>()", "java.lang.String AcceptConnection.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    AcceptConnection actualAcceptConnection = new AcceptConnection();

    // Assert
    assertEquals("AcceptConnection()", actualAcceptConnection.toString());
    assertNull(actualAcceptConnection.getOn());
    assertNull(actualAcceptConnection.getObo());
    assertNull(actualAcceptConnection.getElseCondition());
    assertNull(actualAcceptConnection.getId());
    assertNull(actualAcceptConnection.getIfCondition());
    assertNull(actualAcceptConnection.getUserId());
    assertTrue(actualAcceptConnection.getVariableProperties().isEmpty());
  }
}
