package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonPointer;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {YamlJsonPointer.class, Reader.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class YamlJsonPointerDiffblueTest {
  @MockBean private Reader reader;

  @Autowired private YamlJsonPointer yamlJsonPointer;

  /**
   * Test {@link YamlJsonPointer#YamlJsonPointer(Reader)}.
   *
   * <ul>
   *   <li>When {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#YamlJsonPointer(Reader)}
   */
  @Test
  @DisplayName("Test new YamlJsonPointer(Reader); when 'A'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void YamlJsonPointer.<init>(Reader)"})
  void testNewYamlJsonPointer_whenA() {
    // Arrange
    ByteArrayInputStream byteArrayInputStream =
        new ByteArrayInputStream(new byte[] {-1, 'X', 'A', 'X', 'A', 'X', 'A', 'X'});
    InputStreamReader input = new InputStreamReader(byteArrayInputStream);

    // Act
    new YamlJsonPointer(input);

    // Assert
    Stream<String> linesResult = new BufferedReader(input).lines();
    assertEquals("", linesResult.collect(Collectors.joining("\n")));
  }

  /**
   * Test {@link YamlJsonPointer#YamlJsonPointer(Reader)}.
   *
   * <ul>
   *   <li>When {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code AXAXAXAX}
   *       Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#YamlJsonPointer(Reader)}
   */
  @Test
  @DisplayName(
      "Test new YamlJsonPointer(Reader); when ByteArrayInputStream(byte[]) with 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void YamlJsonPointer.<init>(Reader)"})
  void testNewYamlJsonPointer_whenByteArrayInputStreamWithAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    InputStreamReader input =
        new InputStreamReader(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act
    new YamlJsonPointer(input);

    // Assert
    Stream<String> linesResult = new BufferedReader(input).lines();
    assertEquals("", linesResult.collect(Collectors.joining("\n")));
  }

  /**
   * Test {@link YamlJsonPointer#YamlJsonPointer(Reader)}.
   *
   * <ul>
   *   <li>When {@link ByteArrayInputStream#ByteArrayInputStream(byte[])} with {@code
   *       AXAXAXAXAXAXAXAX} Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#YamlJsonPointer(Reader)}
   */
  @Test
  @DisplayName(
      "Test new YamlJsonPointer(Reader); when ByteArrayInputStream(byte[]) with 'AXAXAXAXAXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void YamlJsonPointer.<init>(Reader)"})
  void testNewYamlJsonPointer_whenByteArrayInputStreamWithAxaxaxaxaxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    InputStreamReader input =
        new InputStreamReader(new ByteArrayInputStream("AXAXAXAXAXAXAXAX".getBytes("UTF-8")));

    // Act
    new YamlJsonPointer(input);

    // Assert
    Stream<String> linesResult = new BufferedReader(input).lines();
    assertEquals("", linesResult.collect(Collectors.joining("\n")));
  }

  /**
   * Test {@link YamlJsonPointer#getLine(JsonPointer)}.
   *
   * <ul>
   *   <li>Given empty.
   *   <li>When {@link JsonPointer} {@link JsonPointer#tail()} return empty.
   *   <li>Then calls {@link JsonPointer#tail()}.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  @DisplayName(
      "Test getLine(JsonPointer); given empty; when JsonPointer tail() return empty; then calls tail()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional YamlJsonPointer.getLine(JsonPointer)"})
  void testGetLine_givenEmpty_whenJsonPointerTailReturnEmpty_thenCallsTail() {
    // Arrange
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));

    JsonPointer pointer = mock(JsonPointer.class);
    when(pointer.tail()).thenReturn(JsonPointer.empty());

    // Act
    Optional<Integer> actualLine = yamlJsonPointer.getLine(pointer);

    // Assert
    verify(pointer).tail();
    assertFalse(actualLine.isPresent());
  }

  /**
   * Test {@link YamlJsonPointer#getLine(JsonPointer)}.
   *
   * <ul>
   *   <li>When empty.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  @DisplayName("Test getLine(JsonPointer); when empty; then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional YamlJsonPointer.getLine(JsonPointer)"})
  void testGetLine_whenEmpty_thenReturnNotPresent() {
    // Arrange
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));

    // Act and Assert
    assertFalse(yamlJsonPointer.getLine(JsonPointer.empty()).isPresent());
  }

  /**
   * Test {@link YamlJsonPointer#getLine(JsonPointer)}.
   *
   * <ul>
   *   <li>Given YAML mapping node.
   *   <li>When pointer matches a key.
   *   <li>Then return the line number of that key.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  @DisplayName("Test getLine(JsonPointer); given mapping YAML; when pointer matches key; then return line number")
  void testGetLine_givenMappingYaml_whenPointerMatchesKey_thenReturnLineNumber() {
    // Arrange
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo: bar\n"));

    // Act
    Optional<Integer> result = yamlJsonPointer.getLine(JsonPointer.compile("/foo"));

    // Assert
    assertTrue(result.isPresent());
    assertEquals(1, result.get());
  }

  /**
   * Test {@link YamlJsonPointer#getLine(JsonPointer)}.
   *
   * <ul>
   *   <li>Given YAML mapping node.
   *   <li>When pointer does not match any key.
   *   <li>Then return empty.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  @DisplayName("Test getLine(JsonPointer); given mapping YAML; when pointer does not match key; then return empty")
  void testGetLine_givenMappingYaml_whenPointerDoesNotMatchKey_thenReturnEmpty() {
    // Arrange
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo: bar\n"));

    // Act
    Optional<Integer> result = yamlJsonPointer.getLine(JsonPointer.compile("/other"));

    // Assert
    assertFalse(result.isPresent());
  }

  /**
   * Test {@link YamlJsonPointer#getLine(JsonPointer)}.
   *
   * <ul>
   *   <li>Given YAML sequence node.
   *   <li>When pointer matches index 0.
   *   <li>Then return line number of that element.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  @DisplayName("Test getLine(JsonPointer); given sequence YAML; when pointer matches index; then return line number")
  void testGetLine_givenSequenceYaml_whenPointerMatchesIndex_thenReturnLineNumber() {
    // Arrange
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("- item1\n- item2\n"));

    // Act
    Optional<Integer> result = yamlJsonPointer.getLine(JsonPointer.compile("/0"));

    // Assert
    assertTrue(result.isPresent());
    assertEquals(1, result.get());
  }

  /**
   * Test {@link YamlJsonPointer#getLine(JsonPointer)}.
   *
   * <ul>
   *   <li>Given YAML sequence node.
   *   <li>When pointer index is out of range.
   *   <li>Then return empty.
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  @DisplayName("Test getLine(JsonPointer); given sequence YAML; when pointer index out of range; then return empty")
  void testGetLine_givenSequenceYaml_whenPointerIndexOutOfRange_thenReturnEmpty() {
    // Arrange
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("- item1\n"));

    // Act
    Optional<Integer> result = yamlJsonPointer.getLine(JsonPointer.compile("/5"));

    // Assert
    assertFalse(result.isPresent());
  }

  /**
   * Test {@link YamlJsonPointer#getLine(JsonPointer)}.
   *
   * <ul>
   *   <li>Given YAML scalar node.
   *   <li>When pointer has non-null tail.
   *   <li>Then return empty (scalar cannot be navigated into).
   * </ul>
   *
   * <p>Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  @DisplayName("Test getLine(JsonPointer); given scalar YAML; when pointer has non-null tail; then return empty")
  void testGetLine_givenScalarYaml_whenPointerHasNonNullTail_thenReturnEmpty() {
    // Arrange
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("scalarValue\n"));

    // Act
    Optional<Integer> result = yamlJsonPointer.getLine(JsonPointer.compile("/foo"));

    // Assert
    assertFalse(result.isPresent());
  }
}
