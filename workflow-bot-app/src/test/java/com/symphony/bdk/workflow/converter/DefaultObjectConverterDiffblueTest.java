package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultObjectConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DefaultObjectConverterDiffblueTest {
  @MockBean
  private BiConverter biConverter;

  @MockBean
  private Converter converter;

  @Autowired
  private DefaultObjectConverter defaultObjectConverter;

  @Autowired
  private List<Converter> list;

  @Autowired
  private List<BiConverter> list2;

  /**
   * Method under test: {@link DefaultObjectConverter#convert(Object, Class)}
   */
  @Test
  void testConvert() {
    // Arrange
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultObjectConverter.convert("Source", targetClass));
  }

  /**
   * Method under test: {@link DefaultObjectConverter#convert(Object, Class)}
   */
  @Test
  void testConvert2() {
    // Arrange
    when(converter.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act
    Object actualConvertResult = defaultObjectConverter.convert("Source", null);

    // Assert
    verify(converter).apply(isA(Object.class));
    assertEquals("Apply", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Class, Class)}
   */
  @Test
  void testConvert3() {
    // Arrange
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convert("Source", sourceClass, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Class, Class)}
   */
  @Test
  void testConvert4() {
    // Arrange
    Class<BiConverter> sourceClass = BiConverter.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convert("Source", sourceClass, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Class, Class)}
   */
  @Test
  void testConvert5() {
    // Arrange
    when(converter.apply(Mockito.<Object>any())).thenReturn("Apply");
    Class<Object> sourceClass = Object.class;

    // Act
    Object actualConvertResult = defaultObjectConverter.convert("Source", sourceClass, null);

    // Assert
    verify(converter).apply(isA(Object.class));
    assertEquals("Apply", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class)}
   */
  @Test
  void testConvert6() {
    // Arrange
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultObjectConverter.convert("Source", "Object", targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class)}
   */
  @Test
  void testConvert7() {
    // Arrange
    when(biConverter.apply(Mockito.<Object>any(), Mockito.<Object>any())).thenReturn("Apply");

    // Act
    Object actualConvertResult = defaultObjectConverter.convert("Source", "Object", null);

    // Assert
    verify(biConverter).apply(isA(Object.class), isA(Object.class));
    assertEquals("Apply", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   */
  @Test
  void testConvert8() {
    // Arrange
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convert("Source", "Object", sourceClass, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   */
  @Test
  void testConvert9() {
    // Arrange
    Class<BiConverter> sourceClass = BiConverter.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convert("Source", "Object", sourceClass, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   */
  @Test
  void testConvert10() {
    // Arrange
    when(biConverter.apply(Mockito.<Object>any(), Mockito.<Object>any())).thenReturn("Apply");
    Class<Object> sourceClass = Object.class;

    // Act
    Object actualConvertResult = defaultObjectConverter.convert("Source", "Object", sourceClass, null);

    // Assert
    verify(biConverter).apply(isA(Object.class), isA(Object.class));
    assertEquals("Apply", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  void testConvertCollection() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertTrue(defaultObjectConverter.convertCollection(source, targetClass).isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  void testConvertCollection2() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultObjectConverter.convertCollection(source, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  void testConvertCollection3() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultObjectConverter.convertCollection(source, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  void testConvertCollection4() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertTrue(defaultObjectConverter.convertCollection(source, sourceClass, targetClass).isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  void testConvertCollection5() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, sourceClass, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  void testConvertCollection6() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, sourceClass, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  void testConvertCollection7() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertTrue(defaultObjectConverter.convertCollection(source, "Object", targetClass).isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  void testConvertCollection8() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, "Object", targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  void testConvertCollection9() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, "Object", targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  void testConvertCollection10() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertTrue(defaultObjectConverter.convertCollection(source, "Object", sourceClass, targetClass).isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  void testConvertCollection11() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, "Object", sourceClass, targetClass));
  }

  /**
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  void testConvertCollection12() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, "Object", sourceClass, targetClass));
  }
}
