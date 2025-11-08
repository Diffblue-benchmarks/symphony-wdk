package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.AcceptConnection;
import org.junit.jupiter.api.Test;

class OboActivityDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link OboActivity#equals(Object)}
   *   <li>{@link OboActivity#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link OboActivity#equals(Object)}
   *   <li>{@link OboActivity#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link OboActivity#equals(Object)}
   *   <li>{@link OboActivity#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    OboActivity oboActivity = new OboActivity();

    // Act and Assert
    assertEquals(oboActivity, oboActivity);
    int expectedHashCodeResult = oboActivity.hashCode();
    assertEquals(expectedHashCodeResult, oboActivity.hashCode());
  }

  /**
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();

    // Act and Assert
    assertNotEquals(acceptConnection, new OboActivity());
  }

  /**
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    OboActivity oboActivity = new OboActivity();
    oboActivity.add("Key", "Value");

    // Act and Assert
    assertNotEquals(oboActivity, new OboActivity());
  }

  /**
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    OboActivity oboActivity = new OboActivity();
    oboActivity.add("Key", mock(Debug.class));

    // Act and Assert
    assertNotEquals(oboActivity, new OboActivity());
  }

  /**
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    OboActivity oboActivity = new OboActivity();

    // Act and Assert
    assertNotEquals(oboActivity, new AcceptConnection());
  }

  /**
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
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
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
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
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new OboActivity(), null);
  }

  /**
   * Method under test: {@link OboActivity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new OboActivity(), "Different type to OboActivity");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link OboActivity}
   *   <li>{@link OboActivity#setObo(Obo)}
   *   <li>{@link OboActivity#toString()}
   *   <li>{@link OboActivity#getObo()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    OboActivity actualOboActivity = new OboActivity();
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");
    actualOboActivity.setObo(obo);
    String actualToStringResult = actualOboActivity.toString();
    Obo actualObo = actualOboActivity.getObo();

    // Assert that nothing has changed
    assertEquals("OboActivity(obo=Obo(username=janedoe, userId=1))", actualToStringResult);
    assertTrue(actualOboActivity.getVariableProperties().isEmpty());
    assertSame(obo, actualObo);
  }
}
