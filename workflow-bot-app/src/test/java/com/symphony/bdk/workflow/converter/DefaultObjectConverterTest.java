package com.symphony.bdk.workflow.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultObjectConverterTest {

  static class Source {
    String value;

    Source(String value) {
      this.value = value;
    }
  }

  static class Target {
    String value;

    Target(String value) {
      this.value = value;
    }
  }

  static class SourceToTargetConverter implements Converter<Source, Target> {
    @Override
    public Target apply(Source source) {
      return new Target(source.value);
    }
  }

  static class SourceToTargetBiConverter implements BiConverter<Source, String, Target> {
    @Override
    public Target apply(Source source, String extra) {
      return new Target(source.value + extra);
    }
  }

  private DefaultObjectConverter objectConverter;

  @BeforeEach
  void setUp() {
    objectConverter = new DefaultObjectConverter(
        List.of(new SourceToTargetConverter()),
        Optional.of(List.of(new SourceToTargetBiConverter()))
    );
  }

  @Test
  void shouldConstructWithEmptyOptionalBiConverters() {
    DefaultObjectConverter c = new DefaultObjectConverter(
        List.of(new SourceToTargetConverter()),
        Optional.empty()
    );
    assertThat(c).isNotNull();
  }

  @Test
  void shouldConvertSourceToTarget() {
    Source source = new Source("hello");
    Target result = objectConverter.convert(source, Target.class);
    assertThat(result.value).isEqualTo("hello");
  }

  @Test
  void shouldThrowWhenNoConverterFoundForConvert() {
    DefaultObjectConverter c = new DefaultObjectConverter(List.of(), Optional.empty());
    Source source = new Source("hello");
    assertThatThrownBy(() -> c.convert(source, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldConvertWithBiConverter() {
    Source source = new Source("hello");
    Target result = objectConverter.convert(source, "-world", Target.class);
    assertThat(result.value).isEqualTo("hello-world");
  }

  @Test
  void shouldThrowWhenNoConverterFoundForConvertWithObject() {
    DefaultObjectConverter c = new DefaultObjectConverter(List.of(), Optional.empty());
    Source source = new Source("hello");
    assertThatThrownBy(() -> c.convert(source, "-extra", Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldConvertWithExplicitSourceClass() {
    Source source = new Source("hi");
    Target result = objectConverter.convert(source, Source.class, Target.class);
    assertThat(result.value).isEqualTo("hi");
  }

  @Test
  void shouldThrowWhenSourceTypeMismatchForConvertWithSourceClass() {
    assertThatThrownBy(() -> objectConverter.convert("hello", Source.class, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void shouldThrowWhenNoConverterFoundForConvertWithSourceClass() {
    DefaultObjectConverter c = new DefaultObjectConverter(List.of(), Optional.empty());
    Source source = new Source("hi");
    assertThatThrownBy(() -> c.convert(source, Source.class, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldConvertWithObjectAndExplicitSourceClass() {
    Source source = new Source("hi");
    Target result = objectConverter.convert(source, "-extra", Source.class, Target.class);
    assertThat(result.value).isEqualTo("hi-extra");
  }

  @Test
  void shouldThrowWhenNoConverterFoundForConvertWithObjectAndSourceClass() {
    DefaultObjectConverter c = new DefaultObjectConverter(List.of(), Optional.empty());
    Source source = new Source("hi");
    assertThatThrownBy(() -> c.convert(source, "-extra", Source.class, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldReturnEmptyListWhenConvertCollectionWithNullSource() {
    List<Target> result = objectConverter.convertCollection(null, Target.class);
    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenConvertCollectionWithEmptySource() {
    List<Target> result = objectConverter.convertCollection(Collections.emptyList(), Target.class);
    assertThat(result).isEmpty();
  }

  @Test
  void shouldConvertCollection() {
    List<Source> sources = List.of(new Source("a"), new Source("b"));
    List<Target> results = objectConverter.convertCollection(sources, Target.class);
    assertThat(results).hasSize(2);
    assertThat(results.get(0).value).isEqualTo("a");
    assertThat(results.get(1).value).isEqualTo("b");
  }

  @Test
  void shouldThrowWhenNoConverterFoundForConvertCollection() {
    DefaultObjectConverter c = new DefaultObjectConverter(List.of(), Optional.empty());
    List<Source> sources = List.of(new Source("a"));
    assertThatThrownBy(() -> c.convertCollection(sources, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldReturnEmptyListWhenConvertCollectionWithObjectAndNullSource() {
    List<Target> result = objectConverter.convertCollection(null, "-extra", Target.class);
    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenConvertCollectionWithObjectAndEmptySource() {
    List<Target> result = objectConverter.convertCollection(Collections.emptyList(), "-extra", Target.class);
    assertThat(result).isEmpty();
  }

  @Test
  void shouldConvertCollectionWithBiConverter() {
    List<Source> sources = List.of(new Source("a"), new Source("b"));
    List<Target> results = objectConverter.convertCollection(sources, "-extra", Target.class);
    assertThat(results).hasSize(2);
    assertThat(results.get(0).value).isEqualTo("a-extra");
  }

  @Test
  void shouldThrowWhenNoConverterFoundForConvertCollectionWithObject() {
    DefaultObjectConverter c = new DefaultObjectConverter(List.of(), Optional.empty());
    List<Source> sources = List.of(new Source("a"));
    assertThatThrownBy(() -> c.convertCollection(sources, "-extra", Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldReturnEmptyListWhenConvertCollectionWithSourceClassAndNullSource() {
    List<Target> result = objectConverter.convertCollection(null, Source.class, Target.class);
    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenConvertCollectionWithSourceClassAndEmptySource() {
    List<Target> result = objectConverter.convertCollection(Collections.emptyList(), Source.class, Target.class);
    assertThat(result).isEmpty();
  }

  @Test
  void shouldConvertCollectionWithSourceClass() {
    List<Source> sources = List.of(new Source("a"), new Source("b"));
    List<Target> results = objectConverter.convertCollection(sources, Source.class, Target.class);
    assertThat(results).hasSize(2);
    assertThat(results.get(0).value).isEqualTo("a");
  }

  @Test
  void shouldThrowWhenSourceTypeMismatchForConvertCollectionWithSourceClass() {
    List<String> sources = List.of("a");
    assertThatThrownBy(() -> objectConverter.convertCollection(sources, Source.class, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void shouldThrowWhenNoConverterFoundForConvertCollectionWithSourceClass() {
    DefaultObjectConverter c = new DefaultObjectConverter(List.of(), Optional.empty());
    List<Source> sources = List.of(new Source("a"));
    assertThatThrownBy(() -> c.convertCollection(sources, Source.class, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }

  @Test
  void shouldReturnEmptyListWhenConvertCollectionWithObjectAndSourceClassAndNullSource() {
    List<Target> result = objectConverter.convertCollection(null, "-extra", Source.class, Target.class);
    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenConvertCollectionWithObjectAndSourceClassAndEmptySource() {
    List<Target> result = objectConverter.convertCollection(Collections.emptyList(), "-extra", Source.class, Target.class);
    assertThat(result).isEmpty();
  }

  @Test
  void shouldConvertCollectionWithObjectAndSourceClass() {
    List<Source> sources = List.of(new Source("a"), new Source("b"));
    List<Target> results = objectConverter.convertCollection(sources, "-extra", Source.class, Target.class);
    assertThat(results).hasSize(2);
    assertThat(results.get(0).value).isEqualTo("a-extra");
  }

  @Test
  void shouldThrowWhenSourceTypeMismatchForConvertCollectionWithObjectAndSourceClass() {
    List<String> sources = List.of("a");
    assertThatThrownBy(() -> objectConverter.convertCollection(sources, "-extra", Source.class, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for the given source type");
  }

  @Test
  void shouldThrowWhenNoConverterFoundForConvertCollectionWithObjectAndSourceClass() {
    DefaultObjectConverter c = new DefaultObjectConverter(List.of(), Optional.empty());
    List<Source> sources = List.of(new Source("a"));
    assertThatThrownBy(() -> c.convertCollection(sources, "-extra", Source.class, Target.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot find converter for");
  }
}
