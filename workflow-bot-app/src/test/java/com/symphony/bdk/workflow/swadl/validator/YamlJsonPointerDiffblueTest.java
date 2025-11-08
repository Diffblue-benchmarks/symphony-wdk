package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {YamlJsonPointer.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class YamlJsonPointerDiffblueTest {
  @MockBean
  private Reader reader;

  @Autowired
  private YamlJsonPointer yamlJsonPointer;

  /**
   * Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  void testGetLine() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(new StringReader("foo"));

    // Act and Assert
    assertFalse(yamlJsonPointer.getLine(JsonPointer.empty()).isPresent());
  }

  /**
   * Method under test: {@link YamlJsonPointer#getLine(JsonPointer)}
   */
  @Test
  void testGetLine2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link YamlJsonPointer#YamlJsonPointer(Reader)}
   */
  @Test
  void testNewYamlJsonPointer() throws UnsupportedEncodingException {
    // Arrange
    InputStreamReader input = new InputStreamReader(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Act
    new YamlJsonPointer(input);

    // Assert
    Stream<String> linesResult = (new BufferedReader(input)).lines();
    assertEquals("", linesResult.collect(Collectors.joining("\n")));
  }

  /**
   * Method under test: {@link YamlJsonPointer#YamlJsonPointer(Reader)}
   */
  @Test
  void testNewYamlJsonPointer2() {
    // Arrange
    InputStreamReader input = new InputStreamReader(
        new ByteArrayInputStream(new byte[]{-1, 'X', 'A', 'X', 'A', 'X', 'A', 'X'}));

    // Act
    new YamlJsonPointer(input);

    // Assert
    Stream<String> linesResult = (new BufferedReader(input)).lines();
    assertEquals("", linesResult.collect(Collectors.joining("\n")));
  }

  /**
   * Method under test: {@link YamlJsonPointer#YamlJsonPointer(Reader)}
   */
  @Test
  void testNewYamlJsonPointer3() throws UnsupportedEncodingException {
    // Arrange
    InputStreamReader input = new InputStreamReader(new ByteArrayInputStream("AXAXAXAXAXAXAXAX".getBytes("UTF-8")));

    // Act
    new YamlJsonPointer(input);

    // Assert
    Stream<String> linesResult = (new BufferedReader(input)).lines();
    assertEquals("", linesResult.collect(Collectors.joining("\n")));
  }
}
