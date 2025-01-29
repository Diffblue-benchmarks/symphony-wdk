package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
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
   * Test {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   * with {@code source}, {@code object}, {@code sourceClass},
   * {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class, Class) with 'source', 'object', 'sourceClass', 'targetClass'")
  void testConvertWithSourceObjectSourceClassTargetClass() {
    // Arrange
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convert("Source", "Object", sourceClass, targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   * with {@code source}, {@code object}, {@code sourceClass},
   * {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class, Class) with 'source', 'object', 'sourceClass', 'targetClass'")
  void testConvertWithSourceObjectSourceClassTargetClass2() {
    // Arrange
    Class<BiConverter> sourceClass = BiConverter.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convert("Source", "Object", sourceClass, targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   * with {@code source}, {@code object}, {@code sourceClass},
   * {@code targetClass}.
   * <ul>
   *   <li>Then return {@code Apply}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class, Class) with 'source', 'object', 'sourceClass', 'targetClass'; then return 'Apply'")
  void testConvertWithSourceObjectSourceClassTargetClass_thenReturnApply() {
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
   * Test {@link DefaultObjectConverter#convert(Object, Object, Class)} with
   * {@code source}, {@code object}, {@code targetClass}.
   * <ul>
   *   <li>Then return {@code Apply}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class) with 'source', 'object', 'targetClass'; then return 'Apply'")
  void testConvertWithSourceObjectTargetClass_thenReturnApply() {
    // Arrange
    when(biConverter.apply(Mockito.<Object>any(), Mockito.<Object>any())).thenReturn("Apply");

    // Act
    Object actualConvertResult = defaultObjectConverter.convert("Source", "Object", null);

    // Assert
    verify(biConverter).apply(isA(Object.class), isA(Object.class));
    assertEquals("Apply", actualConvertResult);
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Object, Class)} with
   * {@code source}, {@code object}, {@code targetClass}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class) with 'source', 'object', 'targetClass'; then throw IllegalArgumentException")
  void testConvertWithSourceObjectTargetClass_thenThrowIllegalArgumentException() {
    // Arrange
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultObjectConverter.convert("Source", "Object", targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Class, Class)} with
   * {@code source}, {@code sourceClass}, {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class, Class) with 'source', 'sourceClass', 'targetClass'")
  void testConvertWithSourceSourceClassTargetClass() {
    // Arrange
    Class<BiConverter> sourceClass = BiConverter.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convert("Source", sourceClass, targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Class, Class)} with
   * {@code source}, {@code sourceClass}, {@code targetClass}.
   * <ul>
   *   <li>Then return {@code Apply}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class, Class) with 'source', 'sourceClass', 'targetClass'; then return 'Apply'")
  void testConvertWithSourceSourceClassTargetClass_thenReturnApply() {
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
   * Test {@link DefaultObjectConverter#convert(Object, Class, Class)} with
   * {@code source}, {@code sourceClass}, {@code targetClass}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convert(Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class, Class) with 'source', 'sourceClass', 'targetClass'; then throw IllegalArgumentException")
  void testConvertWithSourceSourceClassTargetClass_thenThrowIllegalArgumentException() {
    // Arrange
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convert("Source", sourceClass, targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Class)} with
   * {@code source}, {@code targetClass}.
   * <ul>
   *   <li>Given {@link Converter} {@link Function#apply(Object)} return
   * {@code Apply}.</li>
   *   <li>Then return {@code Apply}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultObjectConverter#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class) with 'source', 'targetClass'; given Converter apply(Object) return 'Apply'; then return 'Apply'")
  void testConvertWithSourceTargetClass_givenConverterApplyReturnApply_thenReturnApply() {
    // Arrange
    when(converter.apply(Mockito.<Object>any())).thenReturn("Apply");

    // Act
    Object actualConvertResult = defaultObjectConverter.convert("Source", null);

    // Assert
    verify(converter).apply(isA(Object.class));
    assertEquals("Apply", actualConvertResult);
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Class)} with
   * {@code source}, {@code targetClass}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultObjectConverter#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class) with 'source', 'targetClass'; then throw IllegalArgumentException")
  void testConvertWithSourceTargetClass_thenThrowIllegalArgumentException() {
    // Arrange
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultObjectConverter.convert("Source", targetClass));
  }

  /**
   * Test
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   * with {@code source}, {@code object}, {@code sourceClass},
   * {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) with 'source', 'object', 'sourceClass', 'targetClass'")
  void testConvertCollectionWithSourceObjectSourceClassTargetClass() {
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
   * Test
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   * with {@code source}, {@code object}, {@code sourceClass},
   * {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) with 'source', 'object', 'sourceClass', 'targetClass'")
  void testConvertCollectionWithSourceObjectSourceClassTargetClass2() {
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

  /**
   * Test
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   * with {@code source}, {@code object}, {@code sourceClass},
   * {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) with 'source', 'object', 'sourceClass', 'targetClass'")
  void testConvertCollectionWithSourceObjectSourceClassTargetClass3() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    Class<BiConverter> sourceClass = BiConverter.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, "Object", sourceClass, targetClass));
  }

  /**
   * Test
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   * with {@code source}, {@code object}, {@code sourceClass},
   * {@code targetClass}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) with 'source', 'object', 'sourceClass', 'targetClass'; then return Empty")
  void testConvertCollectionWithSourceObjectSourceClassTargetClass_thenReturnEmpty() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertTrue(defaultObjectConverter.convertCollection(source, "Object", sourceClass, targetClass).isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   * with {@code source}, {@code object}, {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class) with 'source', 'object', 'targetClass'")
  void testConvertCollectionWithSourceObjectTargetClass() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, "Object", targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   * with {@code source}, {@code object}, {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class) with 'source', 'object', 'targetClass'")
  void testConvertCollectionWithSourceObjectTargetClass2() {
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
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   * with {@code source}, {@code object}, {@code targetClass}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class) with 'source', 'object', 'targetClass'; when ArrayList(); then return Empty")
  void testConvertCollectionWithSourceObjectTargetClass_whenArrayList_thenReturnEmpty() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertTrue(defaultObjectConverter.convertCollection(source, "Object", targetClass).isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   * with {@code source}, {@code sourceClass}, {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) with 'source', 'sourceClass', 'targetClass'")
  void testConvertCollectionWithSourceSourceClassTargetClass() {
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
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   * with {@code source}, {@code sourceClass}, {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) with 'source', 'sourceClass', 'targetClass'")
  void testConvertCollectionWithSourceSourceClassTargetClass2() {
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
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   * with {@code source}, {@code sourceClass}, {@code targetClass}.
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) with 'source', 'sourceClass', 'targetClass'")
  void testConvertCollectionWithSourceSourceClassTargetClass3() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    Class<BiConverter> sourceClass = BiConverter.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> defaultObjectConverter.convertCollection(source, sourceClass, targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   * with {@code source}, {@code sourceClass}, {@code targetClass}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) with 'source', 'sourceClass', 'targetClass'; then return Empty")
  void testConvertCollectionWithSourceSourceClassTargetClass_thenReturnEmpty() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    Class<Object> sourceClass = Object.class;
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertTrue(defaultObjectConverter.convertCollection(source, sourceClass, targetClass).isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class)} with
   * {@code source}, {@code targetClass}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class) with 'source', 'targetClass'; then throw IllegalArgumentException")
  void testConvertCollectionWithSourceTargetClass_thenThrowIllegalArgumentException() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultObjectConverter.convertCollection(source, targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class)} with
   * {@code source}, {@code targetClass}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class) with 'source', 'targetClass'; then throw IllegalArgumentException")
  void testConvertCollectionWithSourceTargetClass_thenThrowIllegalArgumentException2() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> defaultObjectConverter.convertCollection(source, targetClass));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class)} with
   * {@code source}, {@code targetClass}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class) with 'source', 'targetClass'; when ArrayList(); then return Empty")
  void testConvertCollectionWithSourceTargetClass_whenArrayList_thenReturnEmpty() {
    // Arrange
    ArrayList<Object> source = new ArrayList<>();
    Class<Object> targetClass = Object.class;

    // Act and Assert
    assertTrue(defaultObjectConverter.convertCollection(source, targetClass).isEmpty());
  }
}
