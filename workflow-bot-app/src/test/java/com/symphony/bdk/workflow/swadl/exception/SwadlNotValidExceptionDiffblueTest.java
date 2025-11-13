package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.symphony.bdk.workflow.swadl.validator.SwadlError;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SwadlNotValidExceptionDiffblueTest {
  /**
   * Test {@link SwadlNotValidException#SwadlNotValidException(List, String)}.
   *
   * <p>Method under test: {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  @DisplayName("Test new SwadlNotValidException(List, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SwadlNotValidException.<init>(List, String)"})
  void testNewSwadlNotValidException() {
    // Arrange and Act
    SwadlNotValidException actualSwadlNotValidException =
        new SwadlNotValidException(new ArrayList<>(), "Full Details");

    // Assert
    assertEquals(
        "SWADL content is not valid: [], full details: Full Details",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals(
        "SWADL content is not valid: [], full details: Full Details",
        actualSwadlNotValidException.getMessage());
    assertNull(actualSwadlNotValidException.getCause());
    assertEquals(0, actualSwadlNotValidException.getSuppressed().length);
    assertTrue(actualSwadlNotValidException.getErrors().isEmpty());
  }

  /**
   * Test {@link SwadlNotValidException#SwadlNotValidException(List, String)}.
   *
   * <p>Method under test: {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  @DisplayName("Test new SwadlNotValidException(List, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SwadlNotValidException.<init>(List, String)"})
  void testNewSwadlNotValidException2() {
    // Arrange
    ArrayList<SwadlError> errors = new ArrayList<>();
    errors.add(new SwadlError(2, "Not all who wander are lost"));

    // Act
    SwadlNotValidException actualSwadlNotValidException =
        new SwadlNotValidException(errors, "Full Details");

    // Assert
    assertEquals(
        "SWADL content is not valid: [Line 2: Not all who wander are lost], full details: Full Details",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals(
        "SWADL content is not valid: [Line 2: Not all who wander are lost], full details: Full Details",
        actualSwadlNotValidException.getMessage());
    assertSame(errors, actualSwadlNotValidException.getErrors());
  }

  /**
   * Test {@link SwadlNotValidException#SwadlNotValidException(JsonProcessingException)}.
   *
   * <ul>
   *   <li>Then return Errors size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * SwadlNotValidException#SwadlNotValidException(JsonProcessingException)}
   */
  @Test
  @DisplayName(
      "Test new SwadlNotValidException(JsonProcessingException); then return Errors size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SwadlNotValidException.<init>(JsonProcessingException)"})
  void testNewSwadlNotValidException_thenReturnErrorsSizeIsOne() {
    // Arrange
    JsonParserDelegate p = new JsonParserDelegate(new TreeTraversingParser(BooleanNode.getFalse()));
    JsonParseException exception =
        new JsonParseException(p, "SWADL content is not valid YAML at line %d, full details: %s");

    // Act
    SwadlNotValidException actualSwadlNotValidException = new SwadlNotValidException(exception);

    // Assert
    List<SwadlError> errors = actualSwadlNotValidException.getErrors();
    assertEquals(1, errors.size());
    SwadlError getResult = errors.get(0);
    assertEquals(
        "SWADL content is not valid YAML at line %d, full details: %s\n"
            + " at [Source: UNKNOWN; byte offset: #UNKNOWN]",
        getResult.getMessage());
    assertEquals(
        "SWADL content is not valid YAML at line -1, full details: SWADL content is not valid YAML at line %d,"
            + " full details: %s\n"
            + " at [Source: UNKNOWN; byte offset: #UNKNOWN]",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals(
        "SWADL content is not valid YAML at line -1, full details: SWADL content is not valid YAML at line %d,"
            + " full details: %s\n"
            + " at [Source: UNKNOWN; byte offset: #UNKNOWN]",
        actualSwadlNotValidException.getMessage());
    assertNull(actualSwadlNotValidException.getCause());
    assertEquals(-1, getResult.getLineNumber());
    assertEquals(0, actualSwadlNotValidException.getSuppressed().length);
  }

  /**
   * Test {@link SwadlNotValidException#SwadlNotValidException(List, String)}.
   *
   * <ul>
   *   <li>Then return LocalizedMessage is a string.
   * </ul>
   *
   * <p>Method under test: {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  @DisplayName(
      "Test new SwadlNotValidException(List, String); then return LocalizedMessage is a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SwadlNotValidException.<init>(List, String)"})
  void testNewSwadlNotValidException_thenReturnLocalizedMessageIsAString() {
    // Arrange
    ArrayList<SwadlError> errors = new ArrayList<>();
    errors.add(new SwadlError(2, "Not all who wander are lost"));
    SwadlError swadlError = new SwadlError(2, "Not all who wander are lost");
    errors.add(swadlError);

    // Act
    SwadlNotValidException actualSwadlNotValidException =
        new SwadlNotValidException(errors, "Full Details");

    // Assert
    assertEquals(
        "SWADL content is not valid: [Line 2: Not all who wander are lost, Line 2: Not all who wander are lost],"
            + " full details: Full Details",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals(
        "SWADL content is not valid: [Line 2: Not all who wander are lost, Line 2: Not all who wander are lost],"
            + " full details: Full Details",
        actualSwadlNotValidException.getMessage());
    List<SwadlError> errors2 = actualSwadlNotValidException.getErrors();
    assertEquals(2, errors2.size());
    assertSame(swadlError, errors2.get(1));
  }
}
