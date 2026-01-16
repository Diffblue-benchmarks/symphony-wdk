package com.symphony.bdk.workflow.swadl.exception;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.symphony.bdk.workflow.swadl.validator.SwadlError;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SwadlNotValidExceptionClaude_constructorTest {

  @Test
  void testConstructor_withValidParameters() {
    // Test that constructor properly initializes exception with formatted message
    List<SwadlError> errors = Arrays.asList(
        new SwadlError(1, "Missing required field"),
        new SwadlError(5, "Invalid type")
    );
    String fullDetails = "Detailed error information";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage())
        .contains("SWADL content is not valid")
        .contains(errors.toString())
        .contains(fullDetails);
    assertThat(exception.getErrors()).isEqualTo(errors);
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyErrorList() {
    // Test that constructor handles empty error list
    List<SwadlError> errors = Collections.emptyList();
    String fullDetails = "No specific errors";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("SWADL content is not valid");
    assertThat(exception.getErrors()).isEmpty();
  }

  @Test
  void testConstructor_withSingleError() {
    // Test that constructor handles a single error in the list
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(10, "Syntax error")
    );
    String fullDetails = "Single error details";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getErrors()).hasSize(1);
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(10);
    assertThat(exception.getErrors().get(0).getMessage()).isEqualTo("Syntax error");
  }

  @Test
  void testConstructor_withMultipleErrors() {
    // Test that constructor handles multiple errors
    List<SwadlError> errors = Arrays.asList(
        new SwadlError(1, "Error 1"),
        new SwadlError(2, "Error 2"),
        new SwadlError(3, "Error 3"),
        new SwadlError(4, "Error 4")
    );
    String fullDetails = "Multiple errors found";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getErrors()).hasSize(4);
    assertThat(exception.getMessage()).contains(errors.toString());
  }

  @Test
  void testConstructor_withNullErrorList() {
    // Test that constructor handles null error list
    String fullDetails = "Null error list";

    SwadlNotValidException exception = new SwadlNotValidException(null, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("null");
    assertThat(exception.getErrors()).isNull();
  }

  @Test
  void testConstructor_withNullFullDetails() {
    // Test that constructor handles null full details
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(5, "Test error")
    );

    SwadlNotValidException exception = new SwadlNotValidException(errors, null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("null");
    assertThat(exception.getErrors()).isEqualTo(errors);
  }

  @Test
  void testConstructor_withBothParametersNull() {
    // Test that constructor handles both parameters being null
    SwadlNotValidException exception = new SwadlNotValidException(null, null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("SWADL content is not valid");
    assertThat(exception.getMessage()).contains("null");
    assertThat(exception.getErrors()).isNull();
  }

  @Test
  void testConstructor_withEmptyFullDetails() {
    // Test that constructor handles empty string for full details
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(3, "Test error")
    );
    String fullDetails = "";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(errors.toString());
    assertThat(exception.getErrors()).isEqualTo(errors);
  }

  @Test
  void testConstructor_isIOException() {
    // Test that SwadlNotValidException is an IOException
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(1, "Error")
    );
    String fullDetails = "Details";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isInstanceOf(IOException.class);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    List<SwadlError> errors1 = Collections.singletonList(
        new SwadlError(1, "First error")
    );
    List<SwadlError> errors2 = Collections.singletonList(
        new SwadlError(2, "Second error")
    );
    String fullDetails1 = "First details";
    String fullDetails2 = "Second details";

    SwadlNotValidException exception1 = new SwadlNotValidException(errors1, fullDetails1);
    SwadlNotValidException exception2 = new SwadlNotValidException(errors2, fullDetails2);

    assertThat(exception1).isNotNull();
    assertThat(exception2).isNotNull();
    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getErrors()).isEqualTo(errors1);
    assertThat(exception2.getErrors()).isEqualTo(errors2);
  }

  @Test
  void testConstructor_canBeThrown() {
    // Test that the exception can actually be thrown and caught
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(7, "Validation failed")
    );
    String fullDetails = "Throwable test";

    try {
      throw new SwadlNotValidException(errors, fullDetails);
    } catch (SwadlNotValidException e) {
      assertThat(e.getMessage()).contains("SWADL content is not valid");
      assertThat(e.getErrors()).isEqualTo(errors);
      assertThat(e.getCause()).isNull();
    }
  }

  @Test
  void testConstructor_canBeCaughtAsIOException() {
    // Test that the exception can be caught as IOException
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(8, "IO error")
    );
    String fullDetails = "IO test";

    try {
      throw new SwadlNotValidException(errors, fullDetails);
    } catch (IOException e) {
      assertThat(e).isInstanceOf(SwadlNotValidException.class);
      assertThat(e.getMessage()).contains("SWADL content is not valid");
    }
  }

  @Test
  void testConstructor_withLongFullDetails() {
    // Test that constructor handles long full details string
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(15, "Error message")
    );
    String fullDetails = "This is a very long full details string that contains a lot of information "
        + "about the validation errors, including specific context about what went wrong, "
        + "where it went wrong, and potentially how to fix the issue. It may span multiple "
        + "lines and contain various special characters and formatting.";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(fullDetails);
    assertThat(exception.getErrors()).isEqualTo(errors);
  }

  @Test
  void testConstructor_withSpecialCharacters() {
    // Test that constructor handles special characters in parameters
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(20, "Error: @#$%^&*()")
    );
    String fullDetails = "Details with special chars: \n\t\r \"quotes\" 'single'";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(fullDetails);
  }

  @Test
  void testConstructor_messageFormatting() {
    // Test that the message formatting follows the expected pattern
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(25, "Format test")
    );
    String fullDetails = "Format details";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception.getMessage())
        .startsWith("SWADL content is not valid: ")
        .contains(errors.toString())
        .contains("full details: ")
        .contains(fullDetails);
  }

  @Test
  void testConstructor_withErrorsContainingNegativeLineNumbers() {
    // Test that constructor handles errors with negative line numbers
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(-1, "Global error")
    );
    String fullDetails = "Negative line number";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(-1);
  }

  @Test
  void testConstructor_withMutableList() {
    // Test that constructor handles mutable list
    List<SwadlError> errors = new ArrayList<>();
    errors.add(new SwadlError(30, "First error"));
    String fullDetails = "Mutable list test";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getErrors()).hasSize(1);

    // Add more errors to the original list
    errors.add(new SwadlError(31, "Second error"));

    // The exception's error list should reflect the change (since it's the same reference)
    assertThat(exception.getErrors()).hasSize(2);
  }

  @Test
  void testConstructor_withFullDetailsContainingFormatSpecifiers() {
    // Test that the constructor handles format specifiers in fullDetails
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(35, "Test error")
    );
    String fullDetails = "Details with format specifiers: %s %d %f";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    // String.format should include these literally in the output
    assertThat(exception.getMessage()).contains(fullDetails);
  }

  @Test
  void testConstructor_errorsFieldAccessibility() {
    // Test that the errors field is accessible via getter
    List<SwadlError> errors = Arrays.asList(
        new SwadlError(40, "Error A"),
        new SwadlError(41, "Error B")
    );
    String fullDetails = "Field access test";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    // The @Value annotation from Lombok should generate a getter
    List<SwadlError> retrievedErrors = exception.getErrors();
    assertThat(retrievedErrors).isEqualTo(errors);
    assertThat(retrievedErrors).hasSize(2);
  }

  @Test
  void testConstructor_withErrorsContainingZeroLineNumber() {
    // Test that constructor handles errors with zero line number
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(0, "Line zero error")
    );
    String fullDetails = "Zero line test";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(0);
  }

  @Test
  void testConstructor_withErrorsContainingEmptyMessage() {
    // Test that constructor handles errors with empty message
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(45, "")
    );
    String fullDetails = "Empty error message test";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getErrors().get(0).getMessage()).isEmpty();
  }

  @Test
  void testConstructor_withErrorsContainingNullMessage() {
    // Test that constructor handles errors with null message
    List<SwadlError> errors = Collections.singletonList(
        new SwadlError(50, null)
    );
    String fullDetails = "Null error message test";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception).isNotNull();
    assertThat(exception.getErrors().get(0).getMessage()).isNull();
  }

  // Tests for JsonProcessingException constructor

  @Test
  void testJsonProcessingExceptionConstructor_withValidException() {
    // Test that constructor properly initializes from JsonProcessingException
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(10);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Invalid YAML syntax");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage())
        .contains("SWADL content is not valid YAML at line 10")
        .contains("full details: Invalid YAML syntax");
    assertThat(exception.getErrors()).hasSize(1);
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(10);
    assertThat(exception.getErrors().get(0).getMessage()).isEqualTo("Invalid YAML syntax");
  }

  @Test
  void testJsonProcessingExceptionConstructor_withLineNumberZero() {
    // Test that constructor handles line number 0
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(0);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Parse error at start");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("line 0");
    assertThat(exception.getErrors()).hasSize(1);
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(0);
  }

  @Test
  void testJsonProcessingExceptionConstructor_withNegativeLineNumber() {
    // Test that constructor handles negative line number
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(-1);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Unknown location error");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("line -1");
    assertThat(exception.getErrors()).hasSize(1);
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(-1);
  }

  @Test
  void testJsonProcessingExceptionConstructor_withLargeLineNumber() {
    // Test that constructor handles large line numbers
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(999999);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Error in large file");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("line 999999");
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(999999);
  }

  @Test
  void testJsonProcessingExceptionConstructor_withNullMessage() {
    // Test that constructor handles null message from JsonProcessingException
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(5);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn(null);

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("line 5");
    assertThat(exception.getMessage()).contains("null");
    assertThat(exception.getErrors().get(0).getMessage()).isNull();
  }

  @Test
  void testJsonProcessingExceptionConstructor_withEmptyMessage() {
    // Test that constructor handles empty message from JsonProcessingException
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(7);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("line 7");
    assertThat(exception.getErrors().get(0).getMessage()).isEmpty();
  }

  @Test
  void testJsonProcessingExceptionConstructor_withLongMessage() {
    // Test that constructor handles long messages
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(15);
    when(jsonException.getLocation()).thenReturn(location);
    String longMessage = "This is a very long error message that contains detailed information about "
        + "what went wrong during YAML parsing, including specific syntax errors, "
        + "context about the problematic section, and suggestions for how to fix it. "
        + "The message continues with more details about the parsing failure.";
    when(jsonException.getMessage()).thenReturn(longMessage);

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(longMessage);
    assertThat(exception.getErrors().get(0).getMessage()).isEqualTo(longMessage);
  }

  @Test
  void testJsonProcessingExceptionConstructor_withSpecialCharactersInMessage() {
    // Test that constructor handles special characters in message
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(20);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Error: @#$%^&*() \n\t\r special chars");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("Error: @#$%^&*() \n\t\r special chars");
    assertThat(exception.getErrors().get(0).getMessage()).isEqualTo("Error: @#$%^&*() \n\t\r special chars");
  }

  @Test
  void testJsonProcessingExceptionConstructor_isIOException() {
    // Test that SwadlNotValidException created from JsonProcessingException is an IOException
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(1);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Test error");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isInstanceOf(IOException.class);
  }

  @Test
  void testJsonProcessingExceptionConstructor_errorsListIsSingleton() {
    // Test that errors list contains exactly one error
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(25);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Single error");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception.getErrors()).isNotNull();
    assertThat(exception.getErrors()).hasSize(1);
    assertThat(exception.getErrors()).isInstanceOf(List.class);
  }

  @Test
  void testJsonProcessingExceptionConstructor_canBeThrown() {
    // Test that the exception can be thrown and caught
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(30);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Throwable test");

    try {
      throw new SwadlNotValidException(jsonException);
    } catch (SwadlNotValidException e) {
      assertThat(e.getMessage()).contains("line 30");
      assertThat(e.getErrors()).hasSize(1);
    }
  }

  @Test
  void testJsonProcessingExceptionConstructor_canBeCaughtAsIOException() {
    // Test that exception can be caught as IOException
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(35);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("IO test");

    try {
      throw new SwadlNotValidException(jsonException);
    } catch (IOException e) {
      assertThat(e).isInstanceOf(SwadlNotValidException.class);
      assertThat(e.getMessage()).contains("line 35");
    }
  }

  @Test
  void testJsonProcessingExceptionConstructor_messageFormatting() {
    // Test that message follows expected format
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(42);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Format test");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception.getMessage())
        .startsWith("SWADL content is not valid YAML at line ")
        .contains("42")
        .contains("full details: ")
        .contains("Format test");
  }

  @Test
  void testJsonProcessingExceptionConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    JsonProcessingException jsonException1 = mock(JsonProcessingException.class);
    JsonLocation location1 = mock(JsonLocation.class);
    when(location1.getLineNr()).thenReturn(50);
    when(jsonException1.getLocation()).thenReturn(location1);
    when(jsonException1.getMessage()).thenReturn("First error");

    JsonProcessingException jsonException2 = mock(JsonProcessingException.class);
    JsonLocation location2 = mock(JsonLocation.class);
    when(location2.getLineNr()).thenReturn(60);
    when(jsonException2.getLocation()).thenReturn(location2);
    when(jsonException2.getMessage()).thenReturn("Second error");

    SwadlNotValidException exception1 = new SwadlNotValidException(jsonException1);
    SwadlNotValidException exception2 = new SwadlNotValidException(jsonException2);

    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getMessage()).contains("line 50");
    assertThat(exception2.getMessage()).contains("line 60");
    assertThat(exception1.getErrors().get(0).getLineNumber()).isEqualTo(50);
    assertThat(exception2.getErrors().get(0).getLineNumber()).isEqualTo(60);
  }

  @Test
  void testJsonProcessingExceptionConstructor_withMessageContainingFormatSpecifiers() {
    // Test that constructor handles format specifiers in message
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(70);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Error with specifiers: %s %d %f");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("Error with specifiers: %s %d %f");
  }

  @Test
  void testJsonProcessingExceptionConstructor_errorsFieldContainsCorrectData() {
    // Test that the errors field is properly populated with data from JsonProcessingException
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(80);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("Data validation test");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    List<SwadlError> errors = exception.getErrors();
    assertThat(errors).hasSize(1);
    SwadlError error = errors.get(0);
    assertThat(error.getLineNumber()).isEqualTo(80);
    assertThat(error.getMessage()).isEqualTo("Data validation test");
  }

  @Test
  void testJsonProcessingExceptionConstructor_noCauseSet() {
    // Test that the cause is not set by this constructor
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(90);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn("No cause test");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testJsonProcessingExceptionConstructor_withTypicalYamlError() {
    // Test with a typical YAML parsing error message
    JsonProcessingException jsonException = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(12);
    when(jsonException.getLocation()).thenReturn(location);
    when(jsonException.getMessage()).thenReturn(
        "Unexpected character (':' (code 58)): expected a single space after '//' but found ':'");

    SwadlNotValidException exception = new SwadlNotValidException(jsonException);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage())
        .contains("line 12")
        .contains("Unexpected character");
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(12);
  }
}
