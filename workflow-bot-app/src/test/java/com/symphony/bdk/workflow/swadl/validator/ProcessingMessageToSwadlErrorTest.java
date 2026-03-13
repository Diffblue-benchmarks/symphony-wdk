package com.symphony.bdk.workflow.swadl.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessingMessageToSwadlErrorTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void shouldHandleErrorWithEmptyLocationsMap() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/testProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "required");
    ArrayNode missingArray = mapper.createArrayNode();
    missingArray.add("requiredField");
    errorJson.set("missing", missingArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Test error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(10));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(10);
  }

  @Test
  void shouldHandleErrorWithEmptyLocationString() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "additionalProperties");

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Test error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(5));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(5);
  }

  @Test
  void shouldHandleErrorWithUnwantedProperty() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "additionalProperties");
    ArrayNode unwantedArray = mapper.createArrayNode();
    unwantedArray.add("unknownProperty");
    errorJson.set("unwanted", unwantedArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Test error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(15));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(15);
    assertThat(result.getMessage()).contains("unknownProperty");
  }

  @Test
  void shouldHandleNestedReportsWithArrays() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/root");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "oneOf");

    // Create nested reports structure
    ArrayNode reportsArray = mapper.createArrayNode();
    ArrayNode nestedReportArray = mapper.createArrayNode();

    ObjectNode nestedError = mapper.createObjectNode();
    ObjectNode nestedInstanceNode = mapper.createObjectNode();
    nestedInstanceNode.put("pointer", "/nested/property");
    nestedError.set("instance", nestedInstanceNode);
    nestedError.put("keyword", "type");
    ArrayNode expectedArray = mapper.createArrayNode();
    expectedArray.add("string");
    nestedError.set("expected", expectedArray);
    ObjectNode foundNode = mapper.createObjectNode();
    foundNode.put("found", "number");
    nestedError.set("found", foundNode);

    nestedReportArray.add(nestedError);
    reportsArray.add(nestedReportArray);
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Test error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(20));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(20);
  }

  @Test
  void shouldHandleNestedReportsWithMultipleLevels() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/root");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "allOf");

    // Create nested reports with multiple levels
    ArrayNode reportsArray = mapper.createArrayNode();
    ArrayNode firstLevelArray = mapper.createArrayNode();

    // First level nested error
    ObjectNode firstLevelError = mapper.createObjectNode();
    ObjectNode firstLevelInstance = mapper.createObjectNode();
    firstLevelInstance.put("pointer", "/first/level");
    firstLevelError.set("instance", firstLevelInstance);
    firstLevelError.put("keyword", "required");

    // Add second level of nesting
    ArrayNode secondLevelReports = mapper.createArrayNode();
    ArrayNode secondLevelArray = mapper.createArrayNode();

    ObjectNode secondLevelError = mapper.createObjectNode();
    ObjectNode secondLevelInstance = mapper.createObjectNode();
    secondLevelInstance.put("pointer", "/second/level");
    secondLevelError.set("instance", secondLevelInstance);
    secondLevelError.put("keyword", "pattern");
    secondLevelError.put("regex", "^[a-z]+$");

    secondLevelArray.add(secondLevelError);
    secondLevelReports.add(secondLevelArray);
    firstLevelError.set("reports", secondLevelReports);

    firstLevelArray.add(firstLevelError);
    reportsArray.add(firstLevelArray);
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Test error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(25));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(25);
  }

  @Test
  void shouldHandleReportsWithMissingPointer() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/root");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "type");

    // Create nested reports with missing pointer node
    ArrayNode reportsArray = mapper.createArrayNode();
    ArrayNode nestedReportArray = mapper.createArrayNode();

    ObjectNode nestedError = mapper.createObjectNode();
    // No instance/pointer node - should be treated as missing
    nestedError.put("keyword", "required");

    nestedReportArray.add(nestedError);
    reportsArray.add(nestedReportArray);
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Test error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(30));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(30);
  }

  @Test
  void shouldHandleReportsWithDuplicateLocations() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/root");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "oneOf");

    // Create nested reports with same location
    ArrayNode reportsArray = mapper.createArrayNode();
    ArrayNode nestedReportArray = mapper.createArrayNode();

    ObjectNode nestedError1 = mapper.createObjectNode();
    ObjectNode nestedInstance1 = mapper.createObjectNode();
    nestedInstance1.put("pointer", "/same/location");
    nestedError1.set("instance", nestedInstance1);
    nestedError1.put("keyword", "type");

    ObjectNode nestedError2 = mapper.createObjectNode();
    ObjectNode nestedInstance2 = mapper.createObjectNode();
    nestedInstance2.put("pointer", "/same/location");
    nestedError2.set("instance", nestedInstance2);
    nestedError2.put("keyword", "required");

    nestedReportArray.add(nestedError1);
    nestedReportArray.add(nestedError2);
    reportsArray.add(nestedReportArray);
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Test error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(35));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(35);
  }
}
