package com.symphony.bdk.workflow.swadl.validator;

import com.fasterxml.jackson.core.JsonPointer;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class YamlJsonPointerClaudeTest {

  @Test
  void testConstructor_withValidYaml() {
    // Test that constructor successfully parses valid YAML
    String yaml = "key: value\n";
    StringReader reader = new StringReader(yaml);

    YamlJsonPointer pointer = new YamlJsonPointer(reader);

    assertThat(pointer).isNotNull();
  }

  @Test
  void testConstructor_withEmptyYaml() {
    // Test that constructor handles empty YAML
    String yaml = "";
    StringReader reader = new StringReader(yaml);

    YamlJsonPointer pointer = new YamlJsonPointer(reader);

    assertThat(pointer).isNotNull();
  }

  @Test
  void testConstructor_withComplexYaml() {
    // Test that constructor handles complex nested YAML
    String yaml = "root:\n  child:\n    grandchild: value\n  list:\n    - item1\n    - item2\n";
    StringReader reader = new StringReader(yaml);

    YamlJsonPointer pointer = new YamlJsonPointer(reader);

    assertThat(pointer).isNotNull();
  }

  @Test
  void testGetLine_withRootPointer() {
    // Test getLine with root pointer returns empty
    String yaml = "key: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isEmpty();
  }

  @Test
  void testGetLine_withSimplePropertyPointer() {
    // Test getLine with simple property pointer
    String yaml = "key: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/key");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withNestedPropertyPointer() {
    // Test getLine with nested property pointer
    String yaml = "root:\n  child:\n    grandchild: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/root/child/grandchild");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(3);
  }

  @Test
  void testGetLine_withNonExistentProperty() {
    // Test getLine with non-existent property returns empty
    String yaml = "key: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/nonexistent");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isEmpty();
  }

  @Test
  void testGetLine_withArrayElementPointer() {
    // Test getLine with array element pointer
    String yaml = "list:\n  - item1\n  - item2\n  - item3\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/list/1");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(3);
  }

  @Test
  void testGetLine_withFirstArrayElement() {
    // Test getLine with first array element (index 0)
    String yaml = "list:\n  - item1\n  - item2\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/list/0");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(2);
  }

  @Test
  void testGetLine_withLastArrayElement() {
    // Test getLine with last array element
    String yaml = "list:\n  - item1\n  - item2\n  - item3\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/list/2");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(4);
  }

  @Test
  void testGetLine_withOutOfBoundsArrayIndex() {
    // Test getLine with out of bounds array index returns empty
    String yaml = "list:\n  - item1\n  - item2\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/list/5");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isEmpty();
  }

  @Test
  void testGetLine_withNestedArrays() {
    // Test getLine with nested arrays
    String yaml = "root:\n  - item1\n  - - nested1\n    - nested2\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/root/1/0");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(3);
  }

  @Test
  void testGetLine_withMixedObjectAndArray() {
    // Test getLine with mixed object and array structure
    String yaml = "root:\n  items:\n    - name: first\n      value: 1\n    - name: second\n      value: 2\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/root/items/0/name");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(3);
  }

  @Test
  void testGetLine_withMixedObjectAndArray_secondElement() {
    // Test getLine with second element in mixed structure
    String yaml = "root:\n  items:\n    - name: first\n      value: 1\n    - name: second\n      value: 2\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/root/items/1/value");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(6);
  }

  @Test
  void testGetLine_withMultilineYaml() {
    // Test getLine correctly counts lines in multiline YAML
    String yaml = "first: value1\nsecond: value2\nthird: value3\nfourth: value4\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/fourth");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(4);
  }

  @Test
  void testGetLine_withDeepNesting() {
    // Test getLine with deeply nested structure
    String yaml = "level1:\n  level2:\n    level3:\n      level4:\n        level5: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/level1/level2/level3/level4/level5");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(5);
  }

  @Test
  void testGetLine_withIntermediateProperty() {
    // Test getLine with intermediate property in nested structure
    String yaml = "level1:\n  level2:\n    level3: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/level1/level2");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(2);
  }

  @Test
  void testGetLine_withSpecialCharactersInPropertyName() {
    // Test getLine with special characters in property name
    String yaml = "special-key: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/special-key");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withUnderscoreInPropertyName() {
    // Test getLine with underscore in property name
    String yaml = "property_name: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/property_name");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withNumbersInPropertyName() {
    // Test getLine with numbers in property name
    String yaml = "key123: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/key123");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withEmptyArray() {
    // Test getLine with empty array
    String yaml = "list: []\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/list/0");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isEmpty();
  }

  @Test
  void testGetLine_withMultiplePropertiesAtSameLevel() {
    // Test getLine with multiple properties at same level
    String yaml = "first: value1\nsecond: value2\nthird: value3\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);

    Optional<Integer> line1 = yamlPointer.getLine(JsonPointer.compile("/first"));
    Optional<Integer> line2 = yamlPointer.getLine(JsonPointer.compile("/second"));
    Optional<Integer> line3 = yamlPointer.getLine(JsonPointer.compile("/third"));

    assertThat(line1).isPresent();
    assertThat(line1.get()).isEqualTo(1);
    assertThat(line2).isPresent();
    assertThat(line2.get()).isEqualTo(2);
    assertThat(line3).isPresent();
    assertThat(line3.get()).isEqualTo(3);
  }

  @Test
  void testGetLine_withPartialPath() {
    // Test getLine returns correct line for partial path
    String yaml = "root:\n  child:\n    grandchild: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/root");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withArrayOfObjects() {
    // Test getLine with array of objects
    String yaml = "items:\n  - id: 1\n    name: first\n  - id: 2\n    name: second\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/items/1/id");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(4);
  }

  @Test
  void testGetLine_withInvalidPath() {
    // Test getLine with path that doesn't match structure
    String yaml = "root:\n  child: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/root/wrongchild");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isEmpty();
  }

  @Test
  void testGetLine_withScalarValue() {
    // Test getLine with path pointing to scalar value
    String yaml = "key: scalar_value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/key");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_multipleCallsSamePointer() {
    // Test that multiple calls with same pointer return consistent results
    String yaml = "key: value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/key");

    Optional<Integer> line1 = yamlPointer.getLine(pointer);
    Optional<Integer> line2 = yamlPointer.getLine(pointer);
    Optional<Integer> line3 = yamlPointer.getLine(pointer);

    assertThat(line1).isEqualTo(line2);
    assertThat(line2).isEqualTo(line3);
    assertThat(line1).isPresent();
    assertThat(line1.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withInlineArray() {
    // Test getLine with inline array notation
    String yaml = "list: [item1, item2, item3]\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/list/1");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withInlineObject() {
    // Test getLine with inline object notation
    String yaml = "obj: {key: value}\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/obj/key");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withBooleanValue() {
    // Test getLine with boolean value
    String yaml = "enabled: true\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/enabled");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withNumericValue() {
    // Test getLine with numeric value
    String yaml = "count: 42\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/count");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withNullValue() {
    // Test getLine with null value
    String yaml = "nullable: null\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/nullable");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withEmptyStringValue() {
    // Test getLine with empty string value
    String yaml = "empty: \"\"\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/empty");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withQuotedKey() {
    // Test getLine with quoted key
    String yaml = "\"quoted key\": value\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/quoted key");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(1);
  }

  @Test
  void testGetLine_withComplexWorkflowStructure() {
    // Test getLine with complex workflow-like structure
    String yaml = "id: workflow1\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: msg1\n" +
        "      on:\n" +
        "        message-received:\n" +
        "          content: /hello\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);

    Optional<Integer> line = yamlPointer.getLine(JsonPointer.compile("/activities/0/send-message/on/message-received/content"));

    assertThat(line).isPresent();
    assertThat(line.get()).isEqualTo(7);
  }

  @Test
  void testConstructor_withNullReader() {
    // Test that constructor with null reader throws exception
    assertThatThrownBy(() -> new YamlJsonPointer(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void testGetLine_withNegativeArrayIndex() {
    // Test getLine with negative array index returns empty
    String yaml = "list:\n  - item1\n  - item2\n";
    StringReader reader = new StringReader(yaml);
    YamlJsonPointer yamlPointer = new YamlJsonPointer(reader);
    JsonPointer pointer = JsonPointer.compile("/list/-1");

    Optional<Integer> line = yamlPointer.getLine(pointer);

    // Negative indices should not match any element
    assertThat(line).isEmpty();
  }
}
