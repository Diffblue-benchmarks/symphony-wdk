package com.symphony.bdk.workflow.swadl.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessingMessageToSwadlErrorTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Mock
  private ProcessingMessage validationError;

  @Test
  void shouldReturnUnknownPropertyMessageWhenAdditionalPropertiesError() throws Exception {
    JsonNode errorJson = MAPPER.readTree(
        "{\"instance\":{\"pointer\":\"\"},\"keyword\":\"additionalProperties\",\"unwanted\":[\"unknownProp\"]}");
    JsonNode yamlTree = MAPPER.readTree("{\"steps\":[]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("steps: []"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("Unknown property 'unknownProp'");
  }

  @Test
  void shouldReturnMissingPropertyMessageWhenRequiredError() throws Exception {
    JsonNode errorJson = MAPPER.readTree(
        "{\"instance\":{\"pointer\":\"/activities\"},\"keyword\":\"required\",\"missing\":[\"name\"]}");
    JsonNode yamlTree = MAPPER.readTree("{\"activities\":[]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("activities: []"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("Missing property 'name' for activities object");
  }

  @Test
  void shouldReturnInvalidPatternMessageWhenPatternError() throws Exception {
    JsonNode errorJson = MAPPER.readTree(
        "{\"instance\":{\"pointer\":\"/activities/0/name\"},\"keyword\":\"pattern\",\"regex\":\"[a-z]+\"}");
    JsonNode yamlTree = MAPPER.readTree("{\"activities\":[{\"name\":\"INVALID\"}]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("activities:\n  - name: INVALID"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("Invalid property 'name', must match pattern [a-z]+");
  }

  @Test
  void shouldReturnInvalidTypeWithTextValueWhenTypeErrorAndYamlValueIsTextual() throws Exception {
    JsonNode errorJson = MAPPER.readTree(
        "{\"instance\":{\"pointer\":\"/activities/0\"},\"keyword\":\"type\","
            + "\"expected\":[\"object\"],\"found\":\"string\"}");
    JsonNode yamlTree = MAPPER.readTree("{\"activities\":[\"someTextValue\"]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("activities:\n  - someTextValue"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("Invalid property 'someTextValue', expecting object type, got string");
  }

  @Test
  void shouldReturnInvalidTypeWithPointerPropertyWhenTypeErrorAndYamlValueIsNotTextual() throws Exception {
    JsonNode errorJson = MAPPER.readTree(
        "{\"instance\":{\"pointer\":\"/activities/0\"},\"keyword\":\"type\","
            + "\"expected\":[\"object\"],\"found\":\"null\"}");
    JsonNode yamlTree = MAPPER.readTree("{\"activities\":[{\"name\":\"test\"}]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("activities:\n  - name: test"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("Invalid property '0', expecting object type, got null");
  }

  @Test
  void shouldReturnUnknownPropertyMessageWhenOneOfErrorAndYamlValueIsObject() throws Exception {
    JsonNode errorJson = MAPPER.readTree(
        "{\"instance\":{\"pointer\":\"/activities/0\"},\"keyword\":\"oneOf\",\"reports\":[]}");
    JsonNode yamlTree = MAPPER.readTree("{\"activities\":[{\"execute-script\":{}}]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("activities:\n  - execute-script: {}"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("Unknown property 'execute-script' for 0 object");
  }

  @Test
  void shouldDrillDownReportsAndReturnNestedErrorMessageWhenAllOfError() throws Exception {
    String errorJsonStr =
        "{\"instance\":{\"pointer\":\"/activities\"},\"keyword\":\"allOf\","
            + "\"reports\":[[{\"instance\":{\"pointer\":\"/activities\"},\"keyword\":\"pattern\","
            + "\"regex\":\"[a-z]+\"}]]}";
    JsonNode errorJson = MAPPER.readTree(errorJsonStr);
    JsonNode yamlTree = MAPPER.readTree("{\"activities\":[]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("activities: []"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("Invalid property 'activities', must match pattern [a-z]+");
  }

  @Test
  void shouldReturnOriginalMessageWhenErrorTypeIsUnknown() throws Exception {
    JsonNode errorJson = MAPPER.readTree(
        "{\"instance\":{\"pointer\":\"/activities\"},\"keyword\":\"unknown\"}");
    JsonNode yamlTree = MAPPER.readTree("{\"activities\":[]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error message");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("activities: []"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("raw error message");
  }

  @Test
  void shouldFallBackToRawMessageWhenExceptionOccursDuringConversion() {
    when(validationError.asJson()).thenThrow(new RuntimeException("Unexpected error"));
    when(validationError.getMessage()).thenReturn("raw validation error");
    JsonNode yamlTree = MAPPER.createObjectNode();
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("steps: []"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getLineNumber()).isEqualTo(-1);
    assertThat(result.getMessage()).isEqualTo("raw validation error");
  }

  @Test
  void shouldUseMostSpecificErrorFromNestedReportsWhenMoreSpecificLocationExists() throws Exception {
    String errorJsonStr =
        "{\"instance\":{\"pointer\":\"/activities\"},\"keyword\":\"allOf\","
            + "\"reports\":[[{\"instance\":{\"pointer\":\"/activities/0\"},\"keyword\":\"required\","
            + "\"missing\":[\"name\"]}]]}";
    JsonNode errorJson = MAPPER.readTree(errorJsonStr);
    JsonNode yamlTree = MAPPER.readTree("{\"activities\":[{}]}");
    when(validationError.asJson()).thenReturn(errorJson);
    when(validationError.getMessage()).thenReturn("raw error");
    YamlJsonPointer pointer = new YamlJsonPointer(new StringReader("activities:\n  - {}"));

    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, pointer, validationError);

    assertThat(result.getMessage()).isEqualTo("Missing property 'name' for 0 object");
  }
}
