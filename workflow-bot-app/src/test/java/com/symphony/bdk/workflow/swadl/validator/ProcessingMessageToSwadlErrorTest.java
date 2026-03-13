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

  @Test
  void shouldDrillDownReportsForAllOfError() {
    // Arrange
    ObjectNode yamlTree = mapper.createObjectNode();
    yamlTree.put("testField", "testValue");
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/testProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "allOf");

    // Create reports array with nested reports to trigger drillDownReports
    ArrayNode reportsArray = mapper.createArrayNode();
    ArrayNode nestedReportArray = mapper.createArrayNode();

    ObjectNode nestedError = mapper.createObjectNode();
    ObjectNode nestedInstanceNode = mapper.createObjectNode();
    nestedInstanceNode.put("pointer", "/testProperty/field");
    nestedError.set("instance", nestedInstanceNode);
    nestedError.put("keyword", "required");
    ArrayNode missingArray = mapper.createArrayNode();
    missingArray.add("missingField");
    nestedError.set("missing", missingArray);

    nestedReportArray.add(nestedError);
    reportsArray.add(nestedReportArray);
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(40));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(40);
    assertThat(result.getMessage()).contains("Missing property");
    assertThat(result.getMessage()).contains("missingField");
  }

  @Test
  void shouldDrillDownReportsForOneOfError() {
    // Arrange
    ObjectNode yamlTree = mapper.createObjectNode();
    yamlTree.put("testField", "testValue");
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/testProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "oneOf");

    // Create reports array with nested reports to trigger drillDownReports
    ArrayNode reportsArray = mapper.createArrayNode();
    ArrayNode nestedReportArray = mapper.createArrayNode();

    ObjectNode nestedError = mapper.createObjectNode();
    ObjectNode nestedInstanceNode = mapper.createObjectNode();
    nestedInstanceNode.put("pointer", "/testProperty");
    nestedError.set("instance", nestedInstanceNode);
    nestedError.put("keyword", "pattern");
    nestedError.put("regex", "^[a-zA-Z]+$");

    nestedReportArray.add(nestedError);
    reportsArray.add(nestedReportArray);
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(45));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(45);
    assertThat(result.getMessage()).contains("Invalid property");
    assertThat(result.getMessage()).contains("must match pattern");
  }

  @Test
  void shouldReturnOriginalMessageWhenDrillDownReportsHasEmptyReports() {
    // Arrange
    ObjectNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/testProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "allOf");

    // Create empty reports array - drillDownReports should return original message
    ArrayNode reportsArray = mapper.createArrayNode();
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Fallback error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(50));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(50);
    assertThat(result.getMessage()).contains("Fallback error message");
  }

  @Test
  void shouldReturnOriginalMessageWhenReportHasNoNestedReports() {
    // Arrange
    ObjectNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/testProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "oneOf");

    // Create reports array with an empty nested array - covers the case where inner loop doesn't execute
    ArrayNode reportsArray = mapper.createArrayNode();
    ArrayNode emptyNestedArray = mapper.createArrayNode();
    reportsArray.add(emptyNestedArray);
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(55));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(55);
    // drillDownReports returns the toErrorMessage result when no nested reports found
    assertThat(result.getMessage()).contains("Unknown property");
    assertThat(result.getMessage()).contains("testProperty");
  }

  @Test
  void toErrorMessage_shouldHandleAdditionalPropertiesError() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/testProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "additionalProperties");
    ArrayNode unwantedArray = mapper.createArrayNode();
    unwantedArray.add("unwantedField");
    errorJson.set("unwanted", unwantedArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(60));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo("Unknown property 'unwantedField'");
  }

  @Test
  void toErrorMessage_shouldHandleRequiredError() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/myObject");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "required");
    ArrayNode missingArray = mapper.createArrayNode();
    missingArray.add("requiredField");
    errorJson.set("missing", missingArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(65));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo("Missing property 'requiredField' for myObject object");
  }

  @Test
  void toErrorMessage_shouldHandlePatternError() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/myProperty/fieldName");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "pattern");
    errorJson.put("regex", "^[a-zA-Z0-9]+$");

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(70));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo("Invalid property 'fieldName', must match pattern ^[a-zA-Z0-9]+$");
  }

  @Test
  void toErrorMessage_shouldHandleTypeErrorWithNonTextualNode() {
    // Arrange
    ObjectNode yamlTree = mapper.createObjectNode();
    ObjectNode nestedObject = mapper.createObjectNode();
    nestedObject.put("field", "value");
    yamlTree.set("myProperty", nestedObject);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/myProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "type");
    ArrayNode expectedArray = mapper.createArrayNode();
    expectedArray.add("string");
    errorJson.set("expected", expectedArray);
    errorJson.put("found", "object");

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(75));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo("Invalid property 'myProperty', expecting string type, got object");
  }

  @Test
  void toErrorMessage_shouldHandleTypeErrorWithTextualNode() {
    // Arrange
    ObjectNode yamlTree = mapper.createObjectNode();
    yamlTree.put("myProperty", "invalidValue");
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/myProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "type");
    ArrayNode expectedArray = mapper.createArrayNode();
    expectedArray.add("object");
    errorJson.set("expected", expectedArray);
    errorJson.put("found", "string");

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(80));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo("Invalid property 'invalidValue', expecting object type, got string");
  }

  @Test
  void toErrorMessage_shouldHandleOneOfErrorWithObjectNode() {
    // Arrange
    ObjectNode yamlTree = mapper.createObjectNode();
    ObjectNode myPropertyNode = mapper.createObjectNode();
    myPropertyNode.put("unknownField", "value");
    yamlTree.set("myProperty", myPropertyNode);
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/myProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "oneOf");
    // Add empty reports array to avoid NullPointerException in drillDownReports
    ArrayNode reportsArray = mapper.createArrayNode();
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(85));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo("Unknown property 'unknownField' for myProperty object");
  }

  @Test
  void toErrorMessage_shouldHandleOneOfErrorWithNonObjectNode() {
    // Arrange
    ObjectNode yamlTree = mapper.createObjectNode();
    yamlTree.put("myProperty", "simpleValue");
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/myProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "oneOf");
    // Add empty reports array to avoid NullPointerException in drillDownReports
    ArrayNode reportsArray = mapper.createArrayNode();
    errorJson.set("reports", reportsArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(90));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo("Unknown property  for myProperty object");
  }

  @Test
  void toErrorMessage_shouldReturnOriginalMessageForUnknownErrorType() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/myProperty");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "unknownKeyword");

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Fallback error message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(95));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo("Fallback error message");
  }

  @Test
  void convert_shouldReturnFallbackErrorWhenExceptionOccurs() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    when(processingMessage.asJson()).thenThrow(new RuntimeException("Test exception"));
    when(processingMessage.getMessage()).thenReturn("Fallback error message");

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(-1);
    assertThat(result.getMessage()).isEqualTo("Fallback error message");
  }

  @Test
  void convert_shouldHandleSimpleErrorSuccessfully() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/property");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "required");
    ArrayNode missingArray = mapper.createArrayNode();
    missingArray.add("field");
    errorJson.set("missing", missingArray);

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Original message");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(100));

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(100);
    assertThat(result.getMessage()).contains("Missing property");
  }

  @Test
  void convert_shouldHandleErrorWhenLineNumberNotFound() {
    // Arrange
    JsonNode yamlTree = mapper.createObjectNode();
    YamlJsonPointer yamlJsonPointer = mock(YamlJsonPointer.class);

    ProcessingMessage processingMessage = mock(ProcessingMessage.class);
    ObjectNode errorJson = mapper.createObjectNode();
    ObjectNode instanceNode = mapper.createObjectNode();
    instanceNode.put("pointer", "/property");
    errorJson.set("instance", instanceNode);
    errorJson.put("keyword", "type");
    ArrayNode expectedArray = mapper.createArrayNode();
    expectedArray.add("string");
    errorJson.set("expected", expectedArray);
    errorJson.put("found", "number");

    when(processingMessage.asJson()).thenReturn(errorJson);
    when(processingMessage.getMessage()).thenReturn("Type mismatch");
    when(yamlJsonPointer.getLine(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.empty());

    // Act
    SwadlError result = ProcessingMessageToSwadlError.convert(yamlTree, yamlJsonPointer, processingMessage);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getLineNumber()).isEqualTo(-1);
    assertThat(result.getMessage()).contains("Invalid property");
  }
}
