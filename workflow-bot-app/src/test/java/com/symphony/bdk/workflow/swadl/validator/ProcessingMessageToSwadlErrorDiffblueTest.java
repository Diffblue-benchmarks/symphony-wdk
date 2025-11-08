package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import java.io.StringReader;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessingMessageToSwadlErrorDiffblueTest {
  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link YamlJsonPointer} {@link YamlJsonPointer#getLine(JsonPointer)} return {@code null}.</li>
   *   <li>Then calls {@link YamlJsonPointer#getLine(JsonPointer)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  @DisplayName("Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given 'null'; when YamlJsonPointer getLine(JsonPointer) return 'null'; then calls getLine(JsonPointer)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"SwadlError ProcessingMessageToSwadlError.convert(JsonNode, YamlJsonPointer, ProcessingMessage)"})
  void testConvert_givenNull_whenYamlJsonPointerGetLineReturnNull_thenCallsGetLine() {
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

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}.
   * <ul>
   *   <li>Given {@link Optional} with one.</li>
   *   <li>Then return LineNumber is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  @DisplayName("Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); given Optional with one; then return LineNumber is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"SwadlError ProcessingMessageToSwadlError.convert(JsonNode, YamlJsonPointer, ProcessingMessage)"})
  void testConvert_givenOptionalWithOne_thenReturnLineNumberIsOne() {
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
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return LineNumber is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  @DisplayName("Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); when 'null'; then return LineNumber is minus one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"SwadlError ProcessingMessageToSwadlError.convert(JsonNode, YamlJsonPointer, ProcessingMessage)"})
  void testConvert_whenNull_thenReturnLineNumberIsMinusOne() {
    // Arrange
    MissingNode yamlTree = MissingNode.getInstance();

    // Act
    SwadlError actualConvertResult = ProcessingMessageToSwadlError.convert(yamlTree, null, new ProcessingMessage());

    // Assert
    assertEquals("(no message)", actualConvertResult.getMessage());
    assertEquals(-1, actualConvertResult.getLineNumber());
  }

  /**
   * Test {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}.
   * <ul>
   *   <li>When {@link StringReader#StringReader(String)} with {@code foo}.</li>
   *   <li>Then return LineNumber is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessingMessageToSwadlError#convert(JsonNode, YamlJsonPointer, ProcessingMessage)}
   */
  @Test
  @DisplayName("Test convert(JsonNode, YamlJsonPointer, ProcessingMessage); when StringReader(String) with 'foo'; then return LineNumber is minus one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"SwadlError ProcessingMessageToSwadlError.convert(JsonNode, YamlJsonPointer, ProcessingMessage)"})
  void testConvert_whenStringReaderWithFoo_thenReturnLineNumberIsMinusOne() {
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
}
