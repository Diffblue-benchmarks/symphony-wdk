package com.symphony.bdk.workflow.swadl.validator;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonPointer;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Optional;

class YamlJsonPointerTest {

  @Test
  void shouldCreateYamlJsonPointerWithValidYaml() {
    String yaml = "key: value\n";
    StringReader reader = new StringReader(yaml);

    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    assertThat(yamlJsonPointer).isNotNull();
  }

  @Test
  void shouldReturnLineNumberForRootPointer() {
    String yaml = "key: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/key"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void shouldReturnEmptyOptionalForNonExistentPointer() {
    String yaml = "key: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/nonexistent"));

    assertThat(line).isEmpty();
  }

  @Test
  void shouldReturnLineNumberForNestedPointer() {
    String yaml = "parent:\n  child: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/parent/child"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(2);
  }

  @Test
  void shouldReturnLineNumberForArrayElement() {
    String yaml = "list:\n  - first\n  - second\n  - third\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/list/0"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(2);
  }

  @Test
  void shouldReturnLineNumberForSecondArrayElement() {
    String yaml = "list:\n  - first\n  - second\n  - third\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/list/1"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(3);
  }

  @Test
  void shouldReturnEmptyOptionalForInvalidArrayIndex() {
    String yaml = "list:\n  - first\n  - second\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/list/5"));

    assertThat(line).isEmpty();
  }

  @Test
  void shouldReturnLineNumberForComplexNestedStructure() {
    String yaml = "root:\n  level1:\n    level2:\n      key: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/root/level1/level2/key"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(4);
  }

  @Test
  void shouldHandleMixedStructuresWithArraysAndMaps() {
    String yaml = "data:\n  items:\n    - name: item1\n      value: 10\n    - name: item2\n      value: 20\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/data/items/0/name"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(3);
  }

  @Test
  void shouldHandleMultipleProperties() {
    String yaml = "property1: value1\nproperty2: value2\nproperty3: value3\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line1 = yamlJsonPointer.getLine(JsonPointer.compile("/property1"));
    Optional<Integer> line2 = yamlJsonPointer.getLine(JsonPointer.compile("/property2"));
    Optional<Integer> line3 = yamlJsonPointer.getLine(JsonPointer.compile("/property3"));

    assertThat(line1).isPresent();
    assertThat(line1.get()).isEqualTo(1);
    assertThat(line2).isPresent();
    assertThat(line2.get()).isEqualTo(2);
    assertThat(line3).isPresent();
    assertThat(line3.get()).isEqualTo(3);
  }

  @Test
  void shouldReturnEmptyOptionalForEmptyYaml() {
    String yaml = "";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/key"));

    assertThat(line).isEmpty();
  }

  @Test
  void shouldReturnLineNumberForRootLevelArray() {
    String yaml = "- first\n- second\n- third\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/0"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void shouldHandleNestedArrays() {
    String yaml = "matrix:\n  - - 1\n    - 2\n  - - 3\n    - 4\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlJsonPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlJsonPointer.getLine(JsonPointer.compile("/matrix/0/1"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(3);
  }
}
