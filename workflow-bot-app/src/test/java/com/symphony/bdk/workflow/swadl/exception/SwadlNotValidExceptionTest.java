package com.symphony.bdk.workflow.swadl.exception;

import com.symphony.bdk.workflow.swadl.validator.SwadlError;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SwadlNotValidExceptionTest {

  @Test
  void shouldCreateExceptionWithErrorsAndFullDetails() {
    List<SwadlError> errors = List.of(new SwadlError(1, "some error"));
    String fullDetails = "full details here";

    SwadlNotValidException exception = new SwadlNotValidException(errors, fullDetails);

    assertThat(exception.getErrors()).isEqualTo(errors);
    assertThat(exception.getMessage()).contains("SWADL content is not valid");
    assertThat(exception.getMessage()).contains(fullDetails);
  }

  @Test
  void shouldCreateExceptionFromJsonProcessingException() {
    JsonProcessingException jsonEx = mock(JsonProcessingException.class);
    JsonLocation location = mock(JsonLocation.class);
    when(location.getLineNr()).thenReturn(5);
    when(jsonEx.getLocation()).thenReturn(location);
    when(jsonEx.getMessage()).thenReturn("unexpected token");

    SwadlNotValidException exception = new SwadlNotValidException(jsonEx);

    assertThat(exception.getErrors()).hasSize(1);
    assertThat(exception.getErrors().get(0).getLineNumber()).isEqualTo(5);
    assertThat(exception.getErrors().get(0).getMessage()).isEqualTo("unexpected token");
    assertThat(exception.getMessage()).contains("line 5");
    assertThat(exception.getMessage()).contains("unexpected token");
  }
}
