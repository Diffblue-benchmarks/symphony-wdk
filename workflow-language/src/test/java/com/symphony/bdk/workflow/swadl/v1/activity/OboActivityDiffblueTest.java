package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.AcceptConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class OboActivityDiffblueTest {
  /**
   * Test {@link OboActivity#equals(Object)}, and {@link OboActivity#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link OboActivity#equals(Object)}
   *   <li>{@link OboActivity#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    OboActivity oboActivity = new OboActivity();
    OboActivity oboActivity2 = new OboActivity();

    // Act and Assert
    assertEquals(oboActivity, oboActivity2);
    int expectedHashCodeResult = oboActivity.hashCode();
    assertEquals(expectedHashCodeResult, oboActivity2.hashCode());
  }

  /**
   * Test {@link OboActivity#equals(Object)}, and {@link OboActivity#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();
    AcceptConnection acceptConnection2 = new AcceptConnection();

    // Act and Assert
    assertEquals(acceptConnection, acceptConnection2);
    int expectedHashCodeResult = acceptConnection.hashCode();
    assertEquals(expectedHashCodeResult, acceptConnection2.hashCode());
  }

  /**
   * Test {@link OboActivity#equals(Object)}, and {@link OboActivity#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link OboActivity#equals(Object)}
   *   <li>{@link OboActivity#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    OboActivity oboActivity = new OboActivity();
    oboActivity.setObo(obo);

    Obo obo2 = new Obo();
    obo2.setUserId(1L);
    obo2.setUsername("janedoe");

    OboActivity oboActivity2 = new OboActivity();
    oboActivity2.setObo(obo2);

    // Act and Assert
    assertEquals(oboActivity, oboActivity2);
    int expectedHashCodeResult = oboActivity.hashCode();
    assertEquals(expectedHashCodeResult, oboActivity2.hashCode());
  }

  /**
   * Test {@link OboActivity#equals(Object)}, and {@link OboActivity#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link OboActivity#equals(Object)}
   *   <li>{@link OboActivity#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    OboActivity oboActivity = new OboActivity();

    // Act and Assert
    assertEquals(oboActivity, oboActivity);
    int expectedHashCodeResult = oboActivity.hashCode();
    assertEquals(expectedHashCodeResult, oboActivity.hashCode());
  }

  /**
   * Test {@link OboActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();

    // Act and Assert
    assertNotEquals(acceptConnection, new OboActivity());
  }

  /**
   * Test {@link OboActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    OboActivity oboActivity = new OboActivity();
    oboActivity.add("Key", "Value");

    // Act and Assert
    assertNotEquals(oboActivity, new OboActivity());
  }

  /**
   * Test {@link OboActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    OboActivity oboActivity = new OboActivity();

    // Act and Assert
    assertNotEquals(oboActivity, new AcceptConnection());
  }

  /**
   * Test {@link OboActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    OboActivity oboActivity = new OboActivity();
    oboActivity.setObo(obo);

    // Act and Assert
    assertNotEquals(oboActivity, new OboActivity());
  }

  /**
   * Test {@link OboActivity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    OboActivity oboActivity = new OboActivity();

    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    OboActivity oboActivity2 = new OboActivity();
    oboActivity2.setObo(obo);

    // Act and Assert
    assertNotEquals(oboActivity, oboActivity2);
  }

  /**
   * Test {@link OboActivity#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new OboActivity(), null);
  }

  /**
   * Test {@link OboActivity#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean OboActivity.equals(Object)", "int OboActivity.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new OboActivity(), "Different type to OboActivity");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link OboActivity}
   *   <li>{@link OboActivity#setObo(Obo)}
   *   <li>{@link OboActivity#toString()}
   *   <li>{@link OboActivity#getObo()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void OboActivity.<init>()", "Obo OboActivity.getObo()", "void OboActivity.setObo(Obo)",
      "String OboActivity.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    OboActivity actualOboActivity = new OboActivity();
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");
    actualOboActivity.setObo(obo);
    String actualToStringResult = actualOboActivity.toString();
    Obo actualObo = actualOboActivity.getObo();

    // Assert
    assertEquals("OboActivity(obo=Obo(username=janedoe, userId=1))", actualToStringResult);
    assertNull(actualOboActivity.getOn());
    assertNull(actualOboActivity.getElseCondition());
    assertNull(actualOboActivity.getId());
    assertNull(actualOboActivity.getIfCondition());
    assertTrue(actualOboActivity.getVariableProperties().isEmpty());
    assertSame(obo, actualObo);
  }
}
