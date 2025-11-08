package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import java.io.StringReader;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessingMessageToSwadlErrorDiffblueTest {
  /**
   * Method under test:
   * {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  void testConvert() {
    // Arrange
    MissingNode yamlTree = MissingNode.getInstance();
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));

    // Act
    SwadlError actualConvertResult = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer,
        new ProcessingMessage());

    // Assert
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Method under test:
   * {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  void testConvert2() {
    // Arrange
    ArrayNode yamlTree = mock(ArrayNode.class);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));

    // Act
    SwadlError actualConvertResult = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer,
        new ProcessingMessage());

    // Assert
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Method under test:
   * {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  void testConvert3() {
    // Arrange
    MissingNode yamlTree = MissingNode.getInstance();

    // Act
    SwadlError actualConvertResult = ProcessingMessageToSwadlError.convert(yamlTree, null, new ProcessingMessage());

    // Assert
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Method under test:
   * {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  void testConvert4() {
    // Arrange
    MissingNode yamlTree = MissingNode.getInstance();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    Optional<Integer> ofResult = Optional.<Integer>of(1);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(ofResult);

    // Act
    SwadlError actualConvertResult = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer,
        new ProcessingMessage());

    // Assert
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(1, actualConvertResult.getLineNumber());
  }

  /**
   * Method under test:
   * {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  void testConvert5() {
    // Arrange
    MissingNode yamlTree = MissingNode.getInstance();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);
    when(yamlJsonPointer.getLine(Mockito.<JsonPointer>any())).thenReturn(null);

    // Act
    SwadlError actualConvertResult = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer,
        new ProcessingMessage());

    // Assert
    verify(yamlJsonPointer).getLine(isA(JsonPointer.class));
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }
}
