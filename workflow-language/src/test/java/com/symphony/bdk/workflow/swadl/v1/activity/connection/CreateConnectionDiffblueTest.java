package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateConnectionDiffblueTest {
  /**
   * Test {@link CreateConnection#equals(Object)}, and {@link CreateConnection#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateConnection#equals(Object)}
   *   <li>{@link CreateConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateConnection.equals(Object)", "int CreateConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateConnection createConnection = new CreateConnection();
    CreateConnection createConnection2 = new CreateConnection();

    // Act and Assert
    assertEquals(createConnection, createConnection2);
    int expectedHashCodeResult = createConnection.hashCode();
    assertEquals(expectedHashCodeResult, createConnection2.hashCode());
  }

  /**
   * Test {@link CreateConnection#equals(Object)}, and {@link CreateConnection#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateConnection#equals(Object)}
   *   <li>{@link CreateConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateConnection.equals(Object)", "int CreateConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateConnection createConnection = new CreateConnection();

    // Act and Assert
    assertEquals(createConnection, createConnection);
    int expectedHashCodeResult = createConnection.hashCode();
    assertEquals(expectedHashCodeResult, createConnection.hashCode());
  }

  /**
   * Test {@link CreateConnection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateConnection.equals(Object)", "int CreateConnection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateConnection createConnection = new CreateConnection();
    createConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(createConnection, new CreateConnection());
  }

  /**
   * Test {@link CreateConnection#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateConnection.equals(Object)", "int CreateConnection.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateConnection(), null);
  }

  /**
   * Test {@link CreateConnection#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateConnection.equals(Object)", "int CreateConnection.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateConnection(), "Different type to CreateConnection");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateConnection}
   *   <li>{@link CreateConnection#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CreateConnection.<init>()", "java.lang.String CreateConnection.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    CreateConnection actualCreateConnection = new CreateConnection();

    // Assert
    assertEquals("CreateConnection()", actualCreateConnection.toString());
    assertNull(actualCreateConnection.getOn());
    assertNull(actualCreateConnection.getObo());
    assertNull(actualCreateConnection.getElseCondition());
    assertNull(actualCreateConnection.getId());
    assertNull(actualCreateConnection.getIfCondition());
    assertNull(actualCreateConnection.getUserId());
    assertTrue(actualCreateConnection.getVariableProperties().isEmpty());
  }
}
