package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultObjectConverterDiffblueTest {

  @Mock
  Converter<Source, Target> converter;
  @Mock
  BiConverter<Source, Object, Target> biConverter;

  // ---------------------------------------------------------------------------
  // Helper inner classes
  // ---------------------------------------------------------------------------
  private static class Source {}

  private static class Target {}

  // ---------------------------------------------------------------------------
  // Constructor tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#DefaultObjectConverter(List, Optional)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#DefaultObjectConverter(List, Optional)}
   */
  @Test
  @DisplayName("Test constructor with Optional.empty() bi-converters")
  @Tag("ContributionFromDiffblue")
  void testConstructorWithEmptyOptionalBiConverters() {
    // Arrange
    Converter<Source, Target> conv = mock(Converter.class);
    when(conv.getSourceClass()).thenReturn(Source.class);
    when(conv.getTargetClass()).thenReturn(Target.class);

    // Act
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(List.of(conv), Optional.empty());

    // Assert
    // No exception means construction succeeded; the biConverterMap defaults to empty
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convert(new Source(), new Object(), Target.class));
  }

  /**
   * Test {@link DefaultObjectConverter#DefaultObjectConverter(List, Optional)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#DefaultObjectConverter(List, Optional)}
   */
  @Test
  @DisplayName("Test constructor with non-empty converters and non-empty bi-converters")
  @Tag("ContributionFromDiffblue")
  void testConstructorWithConverters() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);

    // Act and Assert (no exception on construction)
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.of(List.of(biConverter)));
    assertTrue(objectConverter != null);
  }

  // ---------------------------------------------------------------------------
  // convert(Object, Class<T>) tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class) - success")
  @Tag("ContributionFromDiffblue")
  void testConvert_sourceToTarget_success() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    Target expected = new Target();
    when(converter.apply(any(Source.class))).thenReturn(expected);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act
    Target result = objectConverter.convert(new Source(), Target.class);

    // Assert
    assertSame(expected, result);
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class) - no converter throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvert_sourceToTarget_noConverter() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convert(new Target(), Source.class));
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class) - wrong source type throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvert_wrongSourceType_throwsIllegalArgumentException() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convert(new Target(), Source.class, Target.class));
  }

  // ---------------------------------------------------------------------------
  // convert(Object, Object, Class<T>) tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convert(Object, Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class) - success")
  @Tag("ContributionFromDiffblue")
  void testConvertWithObject_success() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    Target expected = new Target();
    when(biConverter.apply(any(Source.class), any())).thenReturn(expected);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act
    Target result = objectConverter.convert(new Source(), new Object(), Target.class);

    // Assert
    assertSame(expected, result);
  }

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convert(Object, Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class) - no converter throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvertWithObject_noConverter() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convert(new Target(), new Object(), Source.class));
  }

  // ---------------------------------------------------------------------------
  // convert(Object, Class<?>, Class<T>) tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convert(Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class, Class) - success")
  @Tag("ContributionFromDiffblue")
  void testConvertWithSourceClass_success() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    Target expected = new Target();
    when(converter.apply(any(Source.class))).thenReturn(expected);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act
    Target result = objectConverter.convert(new Source(), Source.class, Target.class);

    // Assert
    assertSame(expected, result);
  }

  // ---------------------------------------------------------------------------
  // convert(Object, Object, Class<?>, Class<T>) tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convert(Object, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Object, Class, Class) - success")
  @Tag("ContributionFromDiffblue")
  void testConvertWithObjectAndSourceClass_success() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    Target expected = new Target();
    when(biConverter.apply(any(Source.class), any())).thenReturn(expected);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act
    Target result = objectConverter.convert(new Source(), new Object(), Source.class, Target.class);

    // Assert
    assertSame(expected, result);
  }

  // ---------------------------------------------------------------------------
  // convertCollection(List<?>, Class<T>) tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class) - null source returns empty list")
  @Tag("ContributionFromDiffblue")
  void testConvertCollection_nullSource_returnsEmptyList() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act
    List<Target> result = objectConverter.convertCollection(null, Target.class);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class) - empty source returns empty list")
  @Tag("ContributionFromDiffblue")
  void testConvertCollection_emptySource_returnsEmptyList() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act
    List<Target> result = objectConverter.convertCollection(Collections.emptyList(), Target.class);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class) - success")
  @Tag("ContributionFromDiffblue")
  void testConvertCollection_success() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    Target expected = new Target();
    when(converter.apply(any(Source.class))).thenReturn(expected);
    doCallRealMethod().when(converter).applyCollection(anyList());
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act
    List<Target> result = objectConverter.convertCollection(Collections.singletonList(new Source()), Target.class);

    // Assert
    assertEquals(1, result.size());
    assertSame(expected, result.get(0));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class) - no converter throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvertCollection_noConverter_throwsIllegalArgumentException() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convertCollection(Collections.singletonList(new Target()), Source.class));
  }

  // ---------------------------------------------------------------------------
  // convertCollection(List<?>, Object, Class<T>) tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class) - null source returns empty list")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObject_nullSource_returnsEmptyList() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act
    List<Target> result = objectConverter.convertCollection(null, new Object(), Target.class);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class) - empty source returns empty list")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObject_emptySource_returnsEmptyList() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act
    List<Target> result = objectConverter.convertCollection(Collections.emptyList(), new Object(), Target.class);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class) - success")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObject_success() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    Target expected = new Target();
    when(biConverter.apply(any(Source.class), any())).thenReturn(expected);
    doCallRealMethod().when(biConverter).applyCollection(anyList(), any());
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act
    List<Target> result =
        objectConverter.convertCollection(Collections.singletonList(new Source()), new Object(), Target.class);

    // Assert
    assertEquals(1, result.size());
    assertSame(expected, result.get(0));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class) - no converter throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObject_noConverter_throwsIllegalArgumentException() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convertCollection(Collections.singletonList(new Target()), new Object(), Source.class));
  }

  // ---------------------------------------------------------------------------
  // convertCollection(List<?>, Class<?>, Class<T>) tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) - null source returns empty list")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithSourceClass_nullSource_returnsEmptyList() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act
    List<Target> result = objectConverter.convertCollection(null, Source.class, Target.class);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) - empty source returns empty list")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithSourceClass_emptySource_returnsEmptyList() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act
    List<Target> result = objectConverter.convertCollection(Collections.emptyList(), Source.class, Target.class);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) - wrong source type throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithSourceClass_wrongSourceType_throwsIllegalArgumentException() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convertCollection(Collections.singletonList(new Source()), Target.class, Target.class));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) - no converter throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithSourceClass_noConverter_throwsIllegalArgumentException() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convertCollection(Collections.singletonList(new Source()), Source.class, Source.class));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Class, Class) - success")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithSourceClass_success() {
    // Arrange
    when(converter.getSourceClass()).thenReturn(Source.class);
    when(converter.getTargetClass()).thenReturn(Target.class);
    Target expected = new Target();
    when(converter.apply(any(Source.class))).thenReturn(expected);
    doCallRealMethod().when(converter).applyCollection(anyList());
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(converter), Optional.empty());

    // Act
    List<Target> result =
        objectConverter.convertCollection(Collections.singletonList(new Source()), Source.class, Target.class);

    // Assert
    assertEquals(1, result.size());
    assertSame(expected, result.get(0));
  }

  // ---------------------------------------------------------------------------
  // convertCollection(List<?>, Object, Class<?>, Class<T>) tests
  // ---------------------------------------------------------------------------

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) - null source returns empty list")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObjectAndSourceClass_nullSource_returnsEmptyList() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act
    List<Target> result = objectConverter.convertCollection(null, new Object(), Source.class, Target.class);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) - empty source returns empty list")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObjectAndSourceClass_emptySource_returnsEmptyList() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act
    List<Target> result =
        objectConverter.convertCollection(Collections.emptyList(), new Object(), Source.class, Target.class);

    // Assert
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) - wrong source type throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObjectAndSourceClass_wrongSourceType_throwsIllegalArgumentException() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convertCollection(Collections.singletonList(new Source()), new Object(), Target.class,
            Target.class));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) - no converter throws IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObjectAndSourceClass_noConverter_throwsIllegalArgumentException() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> objectConverter.convertCollection(Collections.singletonList(new Source()), new Object(), Source.class,
            Source.class));
  }

  /**
   * Test {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}.
   *
   * <p>Method under test: {@link DefaultObjectConverter#convertCollection(List, Object, Class, Class)}
   */
  @Test
  @DisplayName("Test convertCollection(List, Object, Class, Class) - success")
  @Tag("ContributionFromDiffblue")
  void testConvertCollectionWithObjectAndSourceClass_success() {
    // Arrange
    when(biConverter.getSourceClass()).thenReturn(Source.class);
    when(biConverter.getTargetClass()).thenReturn(Target.class);
    Target expected = new Target();
    when(biConverter.apply(any(Source.class), any())).thenReturn(expected);
    doCallRealMethod().when(biConverter).applyCollection(anyList(), any());
    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(List.of(), Optional.of(List.of(biConverter)));

    // Act
    List<Target> result =
        objectConverter.convertCollection(Collections.singletonList(new Source()), new Object(), Source.class,
            Target.class);

    // Assert
    assertEquals(1, result.size());
    assertSame(expected, result.get(0));
  }
}
