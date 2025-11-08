package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SwadlErrorDiffblueTest {
  /**
   * Test {@link SwadlError#toString()}.
   * <ul>
   *   <li>Then return {@code Line 2: Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlError#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return 'Line 2: Not all who wander are lost'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String SwadlError.toString()"})
  void testToString_thenReturnLine2NotAllWhoWanderAreLost() {
    // Arrange, Act and Assert
    assertEquals("Line 2: Not all who wander are lost", (new SwadlError(2, "Not all who wander are lost")).toString());
  }

  /**
   * Test {@link SwadlError#toString()}.
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlError#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return 'Not all who wander are lost'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String SwadlError.toString()"})
  void testToString_thenReturnNotAllWhoWanderAreLost() {
    // Arrange, Act and Assert
    assertEquals("Not all who wander are lost", (new SwadlError(-1, "Not all who wander are lost")).toString());
  }

  /**
   * Test {@link SwadlError#equals(Object)}, and {@link SwadlError#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlError#equals(Object)}
   *   <li>{@link SwadlError#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlError.equals(Object)", "int SwadlError.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SwadlError swadlError = new SwadlError(2, "Not all who wander are lost");
    SwadlError swadlError2 = new SwadlError(2, "Not all who wander are lost");

    // Act and Assert
    assertEquals(swadlError, swadlError2);
    int expectedHashCodeResult = swadlError.hashCode();
    assertEquals(expectedHashCodeResult, swadlError2.hashCode());
  }

  /**
   * Test {@link SwadlError#equals(Object)}, and {@link SwadlError#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlError#equals(Object)}
   *   <li>{@link SwadlError#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlError.equals(Object)", "int SwadlError.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SwadlError swadlError = new SwadlError(2, null);
    SwadlError swadlError2 = new SwadlError(2, null);

    // Act and Assert
    assertEquals(swadlError, swadlError2);
    int expectedHashCodeResult = swadlError.hashCode();
    assertEquals(expectedHashCodeResult, swadlError2.hashCode());
  }

  /**
   * Test {@link SwadlError#equals(Object)}, and {@link SwadlError#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlError#equals(Object)}
   *   <li>{@link SwadlError#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlError.equals(Object)", "int SwadlError.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SwadlError swadlError = new SwadlError(2, "Not all who wander are lost");

    // Act and Assert
    assertEquals(swadlError, swadlError);
    int expectedHashCodeResult = swadlError.hashCode();
    assertEquals(expectedHashCodeResult, swadlError.hashCode());
  }

  /**
   * Test {@link SwadlError#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlError.equals(Object)", "int SwadlError.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SwadlError swadlError = new SwadlError(10, "Not all who wander are lost");

    // Act and Assert
    assertNotEquals(swadlError, new SwadlError(2, "Not all who wander are lost"));
  }

  /**
   * Test {@link SwadlError#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlError.equals(Object)", "int SwadlError.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SwadlError swadlError = new SwadlError(2, "Message");

    // Act and Assert
    assertNotEquals(swadlError, new SwadlError(2, "Not all who wander are lost"));
  }

  /**
   * Test {@link SwadlError#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlError.equals(Object)", "int SwadlError.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SwadlError swadlError = new SwadlError(2, null);

    // Act and Assert
    assertNotEquals(swadlError, new SwadlError(2, "Not all who wander are lost"));
  }

  /**
   * Test {@link SwadlError#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlError.equals(Object)", "int SwadlError.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SwadlError(2, "Not all who wander are lost"), null);
  }

  /**
   * Test {@link SwadlError#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SwadlError.equals(Object)", "int SwadlError.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SwadlError(2, "Not all who wander are lost"), "Different type to SwadlError");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlError#SwadlError(int, String)}
   *   <li>{@link SwadlError#getLineNumber()}
   *   <li>{@link SwadlError#getMessage()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SwadlError.<init>(int, String)", "int SwadlError.getLineNumber()",
      "String SwadlError.getMessage()"})
  void testGettersAndSetters() {
    // Arrange and Act
    SwadlError actualSwadlError = new SwadlError(2, "Not all who wander are lost");
    int actualLineNumber = actualSwadlError.getLineNumber();

    // Assert
    assertEquals("Not all who wander are lost", actualSwadlError.getMessage());
    assertEquals(2, actualLineNumber);
  }
}
