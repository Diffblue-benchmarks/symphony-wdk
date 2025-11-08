package com.symphony.bdk.workflow.swadl.v1.activity.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetStreamsDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreams#equals(Object)}
   *   <li>{@link GetStreams#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    GetStreams getStreams2 = new GetStreams();

    // Act and Assert
    assertEquals(getStreams, getStreams2);
    int expectedHashCodeResult = getStreams.hashCode();
    assertEquals(expectedHashCodeResult, getStreams2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreams#equals(Object)}
   *   <li>{@link GetStreams#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    // Act and Assert
    assertEquals(getStreams, getStreams);
    int expectedHashCodeResult = getStreams.hashCode();
    assertEquals(expectedHashCodeResult, getStreams.hashCode());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.add("Key", mock(GetStream.class));

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setTypes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setScope("Scope");

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setOrigin("Origin");

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setPrivacy("Privacy");

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setStatus("Status");

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setStartDate("2020-03-01");

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setEndDate("2020-03-01");

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setLimit(1);

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    GetStreams getStreams = new GetStreams();
    getStreams.setSkip(1);

    // Act and Assert
    assertNotEquals(getStreams, new GetStreams());
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setTypes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setScope("Scope");

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setOrigin("Origin");

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setPrivacy("Privacy");

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setStatus("Status");

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setStartDate("2020-03-01");

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setEndDate("2020-03-01");

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual19() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setLimit(1);

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual20() {
    // Arrange
    GetStreams getStreams = new GetStreams();

    GetStreams getStreams2 = new GetStreams();
    getStreams2.setSkip(1);

    // Act and Assert
    assertNotEquals(getStreams, getStreams2);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStreams(), null);
  }

  /**
   * Method under test: {@link GetStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStreams(), "Different type to GetStreams");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetStreams}
   *   <li>{@link GetStreams#setEndDate(String)}
   *   <li>{@link GetStreams#setLimit(Integer)}
   *   <li>{@link GetStreams#setOrigin(String)}
   *   <li>{@link GetStreams#setPrivacy(String)}
   *   <li>{@link GetStreams#setScope(String)}
   *   <li>{@link GetStreams#setSkip(Integer)}
   *   <li>{@link GetStreams#setStartDate(String)}
   *   <li>{@link GetStreams#setStatus(String)}
   *   <li>{@link GetStreams#setTypes(List)}
   *   <li>{@link GetStreams#toString()}
   *   <li>{@link GetStreams#getEndDate()}
   *   <li>{@link GetStreams#getLimit()}
   *   <li>{@link GetStreams#getOrigin()}
   *   <li>{@link GetStreams#getPrivacy()}
   *   <li>{@link GetStreams#getScope()}
   *   <li>{@link GetStreams#getSkip()}
   *   <li>{@link GetStreams#getStartDate()}
   *   <li>{@link GetStreams#getStatus()}
   *   <li>{@link GetStreams#getTypes()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetStreams actualGetStreams = new GetStreams();
    actualGetStreams.setEndDate("2020-03-01");
    actualGetStreams.setLimit(1);
    actualGetStreams.setOrigin("Origin");
    actualGetStreams.setPrivacy("Privacy");
    actualGetStreams.setScope("Scope");
    actualGetStreams.setSkip(1);
    actualGetStreams.setStartDate("2020-03-01");
    actualGetStreams.setStatus("Status");
    ArrayList<String> types = new ArrayList<>();
    actualGetStreams.setTypes(types);
    String actualToStringResult = actualGetStreams.toString();
    String actualEndDate = actualGetStreams.getEndDate();
    Integer actualLimit = actualGetStreams.getLimit();
    String actualOrigin = actualGetStreams.getOrigin();
    String actualPrivacy = actualGetStreams.getPrivacy();
    String actualScope = actualGetStreams.getScope();
    Integer actualSkip = actualGetStreams.getSkip();
    String actualStartDate = actualGetStreams.getStartDate();
    String actualStatus = actualGetStreams.getStatus();
    List<String> actualTypes = actualGetStreams.getTypes();

    // Assert that nothing has changed
    assertEquals("2020-03-01", actualEndDate);
    assertEquals("2020-03-01", actualStartDate);
    assertEquals(
        "GetStreams(types=[], scope=Scope, origin=Origin, privacy=Privacy, status=Status, startDate=2020-03-01,"
            + " endDate=2020-03-01, limit=1, skip=1)",
        actualToStringResult);
    assertEquals("Origin", actualOrigin);
    assertEquals("Privacy", actualPrivacy);
    assertEquals("Scope", actualScope);
    assertEquals("Status", actualStatus);
    assertEquals(1, actualLimit.intValue());
    assertEquals(1, actualSkip.intValue());
    assertTrue(actualTypes.isEmpty());
    assertTrue(actualGetStreams.getVariableProperties().isEmpty());
    assertSame(types, actualTypes);
  }
}
