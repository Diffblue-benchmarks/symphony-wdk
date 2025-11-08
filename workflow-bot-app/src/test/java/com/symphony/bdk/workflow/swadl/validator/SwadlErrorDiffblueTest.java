package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class SwadlErrorDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlError#equals(Object)}
   *   <li>{@link SwadlError#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link SwadlError#toString()}
   */
  @Test
  void testToString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertEquals("Line 2: Not all who wander are lost", (new SwadlError(2, "Not all who wander are lost")).toString());
    assertEquals("Not all who wander are lost", (new SwadlError(-1, "Not all who wander are lost")).toString());
  }

  /**
   * Method under test: {@link SwadlError#toString()}
   */
  @Test
  void testToString2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayNode yamlTree = mock(ArrayNode.class);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));

    // Act and Assert
    assertEquals("(no message)",
        ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, new ProcessingMessage()).toString());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlError#equals(Object)}
   *   <li>{@link SwadlError#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlError#equals(Object)}
   *   <li>{@link SwadlError#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SwadlError swadlError = new SwadlError(2, "Not all who wander are lost");

    // Act and Assert
    assertEquals(swadlError, swadlError);
    int expectedHashCodeResult = swadlError.hashCode();
    assertEquals(expectedHashCodeResult, swadlError.hashCode());
  }

  /**
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SwadlError swadlError = new SwadlError(10, "Not all who wander are lost");

    // Act and Assert
    assertNotEquals(swadlError, new SwadlError(2, "Not all who wander are lost"));
  }

  /**
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SwadlError swadlError = new SwadlError(2, "Message");

    // Act and Assert
    assertNotEquals(swadlError, new SwadlError(2, "Not all who wander are lost"));
  }

  /**
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SwadlError swadlError = new SwadlError(2, null);

    // Act and Assert
    assertNotEquals(swadlError, new SwadlError(2, "Not all who wander are lost"));
  }

  /**
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ArrayNode yamlTree = mock(ArrayNode.class);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));
    SwadlError convertResult = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer,
        new ProcessingMessage());

    // Act and Assert
    assertNotEquals(convertResult, new SwadlError(2, "Not all who wander are lost"));
  }

  /**
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SwadlError(2, "Not all who wander are lost"), null);
  }

  /**
   * Method under test: {@link SwadlError#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SwadlError(2, "Not all who wander are lost"), "Different type to SwadlError");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SwadlError#SwadlError(int, String)}
   *   <li>{@link SwadlError#getLineNumber()}
   *   <li>{@link SwadlError#getMessage()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SwadlError actualSwadlError = new SwadlError(2, "Not all who wander are lost");
    int actualLineNumber = actualSwadlError.getLineNumber();

    // Assert
    assertEquals("Not all who wander are lost", actualSwadlError.getMessage());
    assertEquals(2, actualLineNumber);
  }
}
