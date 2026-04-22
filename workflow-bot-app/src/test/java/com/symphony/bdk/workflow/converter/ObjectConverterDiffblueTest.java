package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class ObjectConverterDiffblueTest {

  static class StringToStringConverter implements Converter<String, String> {
    @Override
    public String apply(String s) {
      return s.toUpperCase();
    }
  }

  static class StringToStringBiConverter implements BiConverter<String, String, String> {
    @Override
    public String apply(String s, String suffix) {
      return s + suffix;
    }
  }

  private ObjectConverter objectConverter;

  @BeforeEach
  void setUp() {
    objectConverter = new DefaultObjectConverter(
        List.of(new StringToStringConverter()),
        Optional.of(List.of(new StringToStringBiConverter()))
    );
  }

  /**
   * Test {@link ObjectConverter#convert(Object, Class)}.
   *
   * <p>Method under test: {@link ObjectConverter#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class)")
  void testConvert_sourceAndTargetClass() {
    // Arrange and Act
    String result = objectConverter.convert("hello", String.class);

    // Assert
    assertEquals("HELLO", result);
  }

  /**
   * Test {@link ObjectConverter#convert(Object, Object, Class)}.
   *
   * <p>Method under test: {@link ObjectConverter#convert(Object, Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class)")
  void testConvert_sourceObjectAndTargetClass() {
    // Arrange and Act
    String result = objectConverter.convert("hello", "_world", String.class);

    // Assert
    assertEquals("hello_world", result);
  }

  /**
   * Test {@link ObjectConverter#convert(Object, Class, Class)}.
   *
   * <p>Method under test: {@link ObjectConverter#convert(Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class, Class)")
  void testConvert_sourceClassAndTargetClass() {
    // Arrange and Act
    String result = objectConverter.convert("hello", String.class, String.class);

    // Assert
    assertEquals("HELLO", result);
  }

  /**
   * Test {@link ObjectConverter#convert(Object, Object, Class, Class)}.
   *
   * <p>Method under test: {@link ObjectConverter#convert(Object, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class, Class)")
  void testConvert_sourceObjectClassAndTargetClass() {
    // Arrange and Act
    String result = objectConverter.convert("hello", "_world", String.class, String.class);

    // Assert
    assertEquals("hello_world", result);
  }

  /**
   * Test {@link ObjectConverter#convertCollection(List, Class)}.
   *
   * <p>Method under test: {@link ObjectConverter#convertCollection(List, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class)")
  void testConvertCollection_listAndTargetClass() {
    // Arrange and Act
    List<String> result = objectConverter.convertCollection(List.of("hello", "world"), String.class);

    // Assert
    assertEquals(List.of("HELLO", "WORLD"), result);
  }

  /**
   * Test {@link ObjectConverter#convertCollection(List, Object, Class)}.
   *
   * <p>Method under test: {@link ObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class)")
  void testConvertCollection_listObjectAndTargetClass() {
    // Arrange and Act
    List<String> result = objectConverter.convertCollection(List.of("hello", "world"), "!", String.class);

    // Assert
    assertEquals(List.of("hello!", "world!"), result);
  }

  /**
   * Test {@link ObjectConverter#convertCollection(List, Class, Class)}.
   *
   * <p>Method under test: {@link ObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class)")
  void testConvertCollection_listSourceClassAndTargetClass() {
    // Arrange and Act
    List<String> result = objectConverter.convertCollection(List.of("hello", "world"), String.class, String.class);

    // Assert
    assertEquals(List.of("HELLO", "WORLD"), result);
  }

  /**
   * Test {@link ObjectConverter#convertCollection(List, Object, Class, Class)}.
   *
   * <p>Method under test: {@link ObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class)")
  void testConvertCollection_listObjectSourceClassAndTargetClass() {
    // Arrange and Act
    List<String> result = objectConverter.convertCollection(List.of("hello", "world"), "!", String.class, String.class);

    // Assert
    assertEquals(List.of("hello!", "world!"), result);
  }
}
