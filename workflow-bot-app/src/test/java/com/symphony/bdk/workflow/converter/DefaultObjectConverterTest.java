package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultObjectConverterTest {

  private DefaultObjectConverter converter;
  private TestConverter testConverter;
  private TestBiConverter testBiConverter;
  private ParentToTargetConverter parentConverter;

  @BeforeEach
  void setUp() {
    testConverter = new TestConverter();
    testBiConverter = new TestBiConverter();
    parentConverter = new ParentToTargetConverter();
  }

  @Test
  void shouldInitializeWithConvertersAndEmptyBiConverters() {
    List<Converter> converters = List.of(testConverter);

    converter = new DefaultObjectConverter(converters, Optional.empty());

    assertThat(converter).isNotNull();
  }

  @Test
  void shouldInitializeWithConvertersAndBiConverters() {
    List<Converter> converters = List.of(testConverter);
    List<BiConverter> biConverters = List.of(testBiConverter);

    converter = new DefaultObjectConverter(converters, Optional.of(biConverters));

    assertThat(converter).isNotNull();
  }

  @Test
  void shouldConvertObjectToTargetClass() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());
    var source = new SourceClass("test");

    var result = converter.convert(source, TargetClass.class);

    assertThat(result).isNotNull();
    assertThat(result.value).isEqualTo("test-converted");
  }

  @Test
  void shouldThrowExceptionWhenSourceClassMismatch() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());
    var source = new SourceClass("test");

    assertThatThrownBy(() -> converter.convert(source, String.class, TargetClass.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void shouldThrowExceptionWhenConverterNotFound() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.empty());
    var source = new SourceClass("test");

    assertThatThrownBy(() -> converter.convert(source, TargetClass.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldFindConverterFromSuperclass() {
    converter = new DefaultObjectConverter(List.of(parentConverter), Optional.empty());
    var source = new ChildClass("child");

    var result = converter.convert(source, TargetClass.class);

    assertThat(result).isNotNull();
    assertThat(result.value).isEqualTo("parent-child-converted");
  }

  @Test
  void shouldConvertWithSpecifiedSourceClass() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());
    var source = new SourceClass("test");

    var result = converter.convert(source, SourceClass.class, TargetClass.class);

    assertThat(result).isNotNull();
    assertThat(result.value).isEqualTo("test-converted");
  }

  @Test
  void shouldConvertWithBiConverter() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.of(List.of(testBiConverter)));
    var source = new SourceClass("test");
    var context = new ContextClass("context");

    var result = converter.convert(source, context, TargetClass.class);

    assertThat(result).isNotNull();
    assertThat(result.value).isEqualTo("test-context-converted");
  }

  @Test
  void shouldConvertWithBiConverterAndSourceClass() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.of(List.of(testBiConverter)));
    var source = new SourceClass("test");
    var context = new ContextClass("context");

    var result = converter.convert(source, context, SourceClass.class, TargetClass.class);

    assertThat(result).isNotNull();
    assertThat(result.value).isEqualTo("test-context-converted");
  }

  @Test
  void shouldReturnEmptyListForNullCollection() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());

    var result = converter.convertCollection(null, TargetClass.class);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnEmptyListForEmptyCollection() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());

    var result = converter.convertCollection(Collections.emptyList(), TargetClass.class);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldConvertCollection() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());
    var sources = List.of(new SourceClass("one"), new SourceClass("two"));

    var result = converter.convertCollection(sources, TargetClass.class);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).value).isEqualTo("one-converted");
    assertThat(result.get(1).value).isEqualTo("two-converted");
  }

  @Test
  void shouldThrowExceptionWhenCollectionConverterNotFound() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.empty());
    var sources = List.of(new SourceClass("one"));

    assertThatThrownBy(() -> converter.convertCollection(sources, TargetClass.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldConvertCollectionWithBiConverter() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.of(List.of(testBiConverter)));
    var sources = List.of(new SourceClass("one"), new SourceClass("two"));
    var context = new ContextClass("context");

    var result = converter.convertCollection(sources, context, TargetClass.class);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).value).isEqualTo("one-context-converted");
    assertThat(result.get(1).value).isEqualTo("two-context-converted");
  }

  @Test
  void shouldReturnEmptyListForNullCollectionWithBiConverter() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.of(List.of(testBiConverter)));
    var context = new ContextClass("context");

    var result = converter.convertCollection(null, context, TargetClass.class);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldConvertCollectionWithSourceClass() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());
    var sources = List.of(new SourceClass("one"), new SourceClass("two"));

    var result = converter.convertCollection(sources, SourceClass.class, TargetClass.class);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).value).isEqualTo("one-converted");
    assertThat(result.get(1).value).isEqualTo("two-converted");
  }

  @Test
  void shouldThrowExceptionWhenCollectionSourceClassMismatch() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());
    var sources = List.of(new SourceClass("one"));

    assertThatThrownBy(() -> converter.convertCollection(sources, String.class, TargetClass.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void shouldReturnEmptyListForEmptyCollectionWithSourceClass() {
    converter = new DefaultObjectConverter(List.of(testConverter), Optional.empty());

    var result = converter.convertCollection(Collections.emptyList(), SourceClass.class, TargetClass.class);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldConvertCollectionWithBiConverterAndSourceClass() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.of(List.of(testBiConverter)));
    var sources = List.of(new SourceClass("one"), new SourceClass("two"));
    var context = new ContextClass("context");

    var result = converter.convertCollection(sources, context, SourceClass.class, TargetClass.class);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).value).isEqualTo("one-context-converted");
    assertThat(result.get(1).value).isEqualTo("two-context-converted");
  }

  @Test
  void shouldReturnEmptyListForNullCollectionWithBiConverterAndSourceClass() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.of(List.of(testBiConverter)));
    var context = new ContextClass("context");

    var result = converter.convertCollection(null, context, SourceClass.class, TargetClass.class);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldThrowExceptionWhenBiCollectionSourceClassMismatch() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.of(List.of(testBiConverter)));
    var sources = List.of(new SourceClass("one"));
    var context = new ContextClass("context");

    assertThatThrownBy(() -> converter.convertCollection(sources, context, String.class, TargetClass.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void shouldThrowExceptionWhenBiCollectionConverterNotFound() {
    converter = new DefaultObjectConverter(Collections.emptyList(), Optional.empty());
    var sources = List.of(new SourceClass("one"));
    var context = new ContextClass("context");

    assertThatThrownBy(() -> converter.convertCollection(sources, context, SourceClass.class, TargetClass.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  // Test classes
  static class SourceClass {
    final String value;

    SourceClass(String value) {
      this.value = value;
    }
  }

  static class TargetClass {
    final String value;

    TargetClass(String value) {
      this.value = value;
    }
  }

  static class ContextClass {
    final String value;

    ContextClass(String value) {
      this.value = value;
    }
  }

  static class ParentClass {
    final String value;

    ParentClass(String value) {
      this.value = value;
    }
  }

  static class ChildClass extends ParentClass {
    ChildClass(String value) {
      super(value);
    }
  }

  // Test converters
  static class TestConverter implements Converter<SourceClass, TargetClass> {
    @Override
    public TargetClass apply(SourceClass source) {
      return new TargetClass(source.value + "-converted");
    }

    @Override
    public Class<SourceClass> getSourceClass() {
      return SourceClass.class;
    }

    @Override
    public Class<TargetClass> getTargetClass() {
      return TargetClass.class;
    }
  }

  static class TestBiConverter implements BiConverter<SourceClass, ContextClass, TargetClass> {
    @Override
    public TargetClass apply(SourceClass source, ContextClass context) {
      return new TargetClass(source.value + "-" + context.value + "-converted");
    }

    @Override
    public Class<SourceClass> getSourceClass() {
      return SourceClass.class;
    }

    @Override
    public Class<TargetClass> getTargetClass() {
      return TargetClass.class;
    }
  }

  static class ParentToTargetConverter implements Converter<ParentClass, TargetClass> {
    @Override
    public TargetClass apply(ParentClass source) {
      return new TargetClass("parent-" + source.value + "-converted");
    }

    @Override
    public Class<ParentClass> getSourceClass() {
      return ParentClass.class;
    }

    @Override
    public Class<TargetClass> getTargetClass() {
      return TargetClass.class;
    }
  }
}
