package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.ContentReference;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.symphony.bdk.workflow.swadl.validator.SwadlError;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;

@DisabledInAotMode
class SwadlNotValidExceptionDiffblueTest {
  @MockBean
  private SwadlNotValidException swadlNotValidException;

  /**
   * Method under test: {@link SwadlNotValidException#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    JsonLocation jsonLocation = mock(JsonLocation.class);
    when(jsonLocation.getLineNr()).thenReturn(2);
    JsonGenerationException exception = mock(JsonGenerationException.class);
    when(exception.getMessage()).thenReturn("An error occurred");
    when(exception.getLocation()).thenReturn(jsonLocation);
    SwadlNotValidException swadlNotValidException = new SwadlNotValidException(exception);

    // Act and Assert
    assertNotEquals(swadlNotValidException, new SwadlNotValidException(
        new JsonParseException(new JsonParserDelegate(new TreeTraversingParser(MissingNode.getInstance())), "Msg")));
  }

  /**
   * Method under test: {@link SwadlNotValidException#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    JsonLocation jsonLocation = mock(JsonLocation.class);
    when(jsonLocation.getLineNr()).thenReturn(2);
    JsonGenerationException exception = mock(JsonGenerationException.class);
    when(exception.getMessage()).thenReturn("An error occurred");
    when(exception.getLocation()).thenReturn(jsonLocation);

    // Act and Assert
    assertNotEquals(new SwadlNotValidException(exception), "42");
  }

  /**
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(JsonProcessingException)}
   */
  @Test
  void testNewSwadlNotValidException() {
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
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(JsonProcessingException)}
   */
  @Test
  void testNewSwadlNotValidException2() {
    // Arrange
    JsonGenerationException exception = mock(JsonGenerationException.class);
    when(exception.getMessage()).thenReturn("An error occurred");
    when(exception.getLocation()).thenReturn(new JsonLocation(ContentReference.redacted(), 1L, 2, 1));
    doNothing().when(exception).addSuppressed(Mockito.<Throwable>any());
    exception.addSuppressed(new Throwable());

    // Act
    SwadlNotValidException actualSwadlNotValidException = new SwadlNotValidException(exception);

    // Assert
    verify(exception, atLeast(1)).getLocation();
    verify(exception, atLeast(1)).getMessage();
    verify(exception).addSuppressed(isA(Throwable.class));
    List<SwadlError> errors = actualSwadlNotValidException.getErrors();
    assertEquals(1, errors.size());
    SwadlError getResult = errors.get(0);
    assertEquals("An error occurred", getResult.getMessage());
    assertEquals("SWADL content is not valid YAML at line 2, full details: An error occurred",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals("SWADL content is not valid YAML at line 2, full details: An error occurred",
        actualSwadlNotValidException.getMessage());
    assertNull(actualSwadlNotValidException.getCause());
    assertEquals(0, actualSwadlNotValidException.getSuppressed().length);
    assertEquals(2, getResult.getLineNumber());
  }

  /**
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  void testNewSwadlNotValidException3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<SwadlError> errors = new ArrayList<>();

    // Act
    SwadlNotValidException actualSwadlNotValidException = new SwadlNotValidException(errors, "Full Details");

    // Assert
    assertEquals("SWADL content is not valid: [], full details: Full Details",
        actualSwadlNotValidException.getLocalizedMessage());
    assertEquals("SWADL content is not valid: [], full details: Full Details",
        actualSwadlNotValidException.getMessage());
    assertNull(actualSwadlNotValidException.getCause());
    assertEquals(0, actualSwadlNotValidException.getSuppressed().length);
    List<SwadlError> errors2 = actualSwadlNotValidException.getErrors();
    assertTrue(errors2.isEmpty());
    assertSame(errors, errors2);
  }

  /**
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  void testNewSwadlNotValidException4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
    assertNull(actualSwadlNotValidException.getCause());
    assertEquals(0, actualSwadlNotValidException.getSuppressed().length);
    assertSame(errors, actualSwadlNotValidException.getErrors());
  }

  /**
   * Method under test:
   * {@link SwadlNotValidException#SwadlNotValidException(List, String)}
   */
  @Test
  void testNewSwadlNotValidException5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<SwadlError> errors = new ArrayList<>();
    errors.add(new SwadlError(2, "Not all who wander are lost"));
    errors.add(new SwadlError(2, "Not all who wander are lost"));

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
    assertNull(actualSwadlNotValidException.getCause());
    assertEquals(0, actualSwadlNotValidException.getSuppressed().length);
    assertSame(errors, actualSwadlNotValidException.getErrors());
  }
}
