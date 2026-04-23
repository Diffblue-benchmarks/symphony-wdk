package com.symphony.bdk.workflow.swadl.validator;

import com.fasterxml.jackson.core.JsonPointer;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class YamlJsonPointerTest {

  @Test
  void shouldGetLineForArrayElement() {
    String yaml = "items:\n  - foo\n  - bar\n";
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader(yaml));

    Optional<Integer> line = pointer.getLine(JsonPointer.compile("/items/0"));

    assertThat(line).isPresent();
  }

  @Test
  void shouldGetLineForSecondArrayElement() {
    String yaml = "items:\n  - foo\n  - bar\n";
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader(yaml));

    Optional<Integer> line = pointer.getLine(JsonPointer.compile("/items/1"));

    assertThat(line).isPresent();
  }

  @Test
  void shouldReturnEmptyWhenArrayIndexNotFound() {
    String yaml = "items:\n  - foo\n  - bar\n";
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader(yaml));

    Optional<Integer> line = pointer.getLine(JsonPointer.compile("/items/5"));

    assertThat(line).isEmpty();
  }
}
