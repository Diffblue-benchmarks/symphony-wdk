package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.symphony.bdk.workflow.swadl.validator.SwadlError;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;

@DisabledInAotMode
class SwadlNotValidExceptionDiffblueTest {
  @MockBean
  private SwadlNotValidException swadlNotValidException;

  /**
   * Test {@link SwadlNotValidException#SwadlNotValidException(List, String)}.
   * <p>
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  @DisplayName("Test new SwadlNotValidException(List, String)")
  void testNewSwadlNotValidException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    SwadlNotValidException actualSwadlNotValidException = new SwadlNotValidException(new ArrayList<>(), "Full Details");

    // Assert
    assertEquals("SWADL content is not valid: [], full details: Full Details",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals("SWADL content is not valid: [], full details: Full Details",
        actualSwadlNotValidException.getMessage());
    assertNull(actualSwadlNotValidException.getCause());
    assertEquals(0, actualSwadlNotValidException.getSuppressed().length);
    assertTrue(actualSwadlNotValidException.getErrors().isEmpty());
  }

  /**
   * Test {@link SwadlNotValidException#SwadlNotValidException(List, String)}.
   * <p>
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  @DisplayName("Test new SwadlNotValidException(List, String)")
  void testNewSwadlNotValidException2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ArrayList<SwadlError> errors = new ArrayList<>();
    errors.add(new SwadlError(2, "Not all who wander are lost"));

    // Act
    SwadlNotValidException actualSwadlNotValidException = new SwadlNotValidException(errors, "Full Details");

    // Assert
    assertEquals("SWADL content is not valid: [Line 2: Not all who wander are lost], full details: Full Details",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals("SWADL content is not valid: [Line 2: Not all who wander are lost], full details: Full Details",
        actualSwadlNotValidException.getMessage());
    assertSame(errors, actualSwadlNotValidException.getErrors());
  }

  /**
   * Test
   * {@link SwadlNotValidException#SwadlNotValidException(JsonProcessingException)}.
   * <ul>
   *   <li>Given {@link Throwable#Throwable()}.</li>
   *   <li>Then return Errors size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(JsonProcessingException)}
   */
  @Test
  @DisplayName("Test new SwadlNotValidException(JsonProcessingException); given Throwable(); then return Errors size is one")
  void testNewSwadlNotValidException_givenThrowable_thenReturnErrorsSizeIsOne() {
    // Arrange
    JsonParseException exception = new JsonParseException(
        new JsonParserDelegate(new TreeTraversingParser(MissingNode.getInstance())),
        "SWADL content is not valid YAML at line %d, full details: %s");
    exception.addSuppressed(new Throwable());

    // Act
    SwadlNotValidException actualSwadlNotValidException = new SwadlNotValidException(exception);

    // Assert
    List<SwadlError> errors = actualSwadlNotValidException.getErrors();
    assertEquals(1, errors.size());
    SwadlError getResult = errors.get(0);
    assertEquals("SWADL content is not valid YAML at line %d, full details: %s\n"
        + " at [Source: UNKNOWN; byte offset: #UNKNOWN]", getResult.getMessage());
    assertEquals(
        "SWADL content is not valid YAML at line -1, full details: SWADL content is not valid YAML at line %d,"
            + " full details: %s\n" + " at [Source: UNKNOWN; byte offset: #UNKNOWN]",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals(
        "SWADL content is not valid YAML at line -1, full details: SWADL content is not valid YAML at line %d,"
            + " full details: %s\n" + " at [Source: UNKNOWN; byte offset: #UNKNOWN]",
        actualSwadlNotValidException.getMessage());
    assertNull(actualSwadlNotValidException.getCause());
    assertEquals(-1, getResult.getLineNumber());
    assertEquals(0, actualSwadlNotValidException.getSuppressed().length);
  }

  /**
   * Test {@link SwadlNotValidException#SwadlNotValidException(List, String)}.
   * <ul>
   *   <li>Then return LocalizedMessage is a string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  @DisplayName("Test new SwadlNotValidException(List, String); then return LocalizedMessage is a string")
  void testNewSwadlNotValidException_thenReturnLocalizedMessageIsAString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ArrayList<SwadlError> errors = new ArrayList<>();
    errors.add(new SwadlError(2, "Not all who wander are lost"));
    SwadlError swadlError = new SwadlError(2, "Not all who wander are lost");

    errors.add(swadlError);

    // Act
    SwadlNotValidException actualSwadlNotValidException = new SwadlNotValidException(errors, "Full Details");

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
