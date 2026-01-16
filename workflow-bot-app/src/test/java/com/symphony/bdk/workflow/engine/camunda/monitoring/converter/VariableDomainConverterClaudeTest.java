package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;

import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.history.HistoricVariableUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for VariableDomainConverter.
 * Tests the conversion from HistoricVariableUpdate to VariablesDomain.
 */
class VariableDomainConverterClaudeTest {

  private VariableDomainConverter converter;

  @BeforeEach
  void setUp() {
    converter = new VariableDomainConverter();
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    VariableDomainConverter newConverter = new VariableDomainConverter();

    // Then: instance should not be null
    assertThat(newConverter).isNotNull();
  }

  // ==================== apply() Tests ====================

  @Test
  void apply_withAllFieldsSet_shouldConvertCorrectly() {
    // Given: a complete HistoricVariableUpdate
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key1", "value1");
    outputs.put("key2", 42);
    outputs.put("key3", true);

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(3);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: all fields should be mapped correctly
    assertThat(result.getOutputs()).isEqualTo(outputs);
    assertThat(result.getOutputs()).containsEntry("key1", "value1");
    assertThat(result.getOutputs()).containsEntry("key2", 42);
    assertThat(result.getOutputs()).containsEntry("key3", true);
    assertThat(result.getRevision()).isEqualTo(3);
    assertThat(result.getUpdateTime()).isEqualTo(testInstant);
  }

  @Test
  void apply_withEmptyMap_shouldHandleEmptyOutputs() {
    // Given: a HistoricVariableUpdate with empty map
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> emptyMap = Collections.emptyMap();
    when(variableUpdate.getValue()).thenReturn(emptyMap);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: should have empty map
    assertThat(result.getOutputs()).isEmpty();
    assertThat(result.getRevision()).isEqualTo(1);
    assertThat(result.getUpdateTime()).isEqualTo(testInstant);
  }

  @Test
  void apply_withZeroRevision_shouldHandleZeroRevision() {
    // Given: a HistoricVariableUpdate with revision 0
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("data", "test");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(0);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: revision should be 0
    assertThat(result.getRevision()).isEqualTo(0);
    assertThat(result.getOutputs()).containsEntry("data", "test");
  }

  @Test
  void apply_withHighRevisionNumber_shouldHandleCorrectly() {
    // Given: a HistoricVariableUpdate with a high revision number
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("var", "value");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(999);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: should handle high revision number
    assertThat(result.getRevision()).isEqualTo(999);
  }

  @Test
  void apply_withVariousDataTypes_shouldHandleAllTypes() {
    // Given: a HistoricVariableUpdate with various data types in outputs
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("string", "text");
    outputs.put("integer", 123);
    outputs.put("long", 123456789L);
    outputs.put("double", 123.456);
    outputs.put("boolean", true);
    outputs.put("null", null);

    Map<String, String> nestedMap = new HashMap<>();
    nestedMap.put("nested", "value");
    outputs.put("map", nestedMap);

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: all types should be preserved
    assertThat(result.getOutputs()).containsEntry("string", "text");
    assertThat(result.getOutputs()).containsEntry("integer", 123);
    assertThat(result.getOutputs()).containsEntry("long", 123456789L);
    assertThat(result.getOutputs()).containsEntry("double", 123.456);
    assertThat(result.getOutputs()).containsEntry("boolean", true);
    assertThat(result.getOutputs()).containsEntry("null", null);
    assertThat(result.getOutputs()).containsKey("map");
    assertThat(result.getOutputs().get("map")).isEqualTo(nestedMap);
  }

  @Test
  void apply_withInstantAtEpoch_shouldHandleEpochTime() {
    // Given: a HistoricVariableUpdate with epoch time
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("data", "test");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant epochInstant = Instant.EPOCH;
    when(variableUpdate.getTime()).thenReturn(Date.from(epochInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: should handle epoch time correctly
    assertThat(result.getUpdateTime()).isEqualTo(Instant.EPOCH);
    assertThat(result.getUpdateTime()).isEqualTo(Instant.parse("1970-01-01T00:00:00Z"));
  }

  @Test
  void apply_withFutureTimestamp_shouldHandleFutureTime() {
    // Given: a HistoricVariableUpdate with a future timestamp
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("future", "data");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(2);

    Instant futureInstant = Instant.parse("2030-12-31T23:59:59Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(futureInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: should handle future time correctly
    assertThat(result.getUpdateTime()).isEqualTo(futureInstant);
  }

  @Test
  void apply_withMillisecondPrecision_shouldPreserveMillisecondPrecision() {
    // Given: a HistoricVariableUpdate with millisecond precision
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("data", "test");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant preciseInstant = Instant.parse("2024-01-15T10:30:00.123Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(preciseInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: millisecond precision should be preserved
    assertThat(result.getUpdateTime()).isEqualTo(preciseInstant);
    assertThat(result.getUpdateTime().toEpochMilli() % 1000).isEqualTo(123);
  }

  @Test
  void apply_withSingleEntry_shouldHandleSingleEntry() {
    // Given: a HistoricVariableUpdate with a single entry
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("onlyKey", "onlyValue");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: should have exactly one entry
    assertThat(result.getOutputs()).hasSize(1);
    assertThat(result.getOutputs()).containsEntry("onlyKey", "onlyValue");
  }

  @Test
  void apply_withMultipleEntries_shouldHandleMultipleEntries() {
    // Given: a HistoricVariableUpdate with multiple entries
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      outputs.put("key" + i, "value" + i);
    }

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(5);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: should have all entries
    assertThat(result.getOutputs()).hasSize(10);
    for (int i = 0; i < 10; i++) {
      assertThat(result.getOutputs()).containsEntry("key" + i, "value" + i);
    }
  }

  @Test
  void apply_withSpecialCharactersInKeys_shouldHandleSpecialCharacters() {
    // Given: a HistoricVariableUpdate with special characters in keys
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key-with-dashes", "value1");
    outputs.put("key_with_underscores", "value2");
    outputs.put("key.with.dots", "value3");
    outputs.put("key:with:colons", "value4");
    outputs.put("key with spaces", "value5");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: special characters should be preserved
    assertThat(result.getOutputs()).containsEntry("key-with-dashes", "value1");
    assertThat(result.getOutputs()).containsEntry("key_with_underscores", "value2");
    assertThat(result.getOutputs()).containsEntry("key.with.dots", "value3");
    assertThat(result.getOutputs()).containsEntry("key:with:colons", "value4");
    assertThat(result.getOutputs()).containsEntry("key with spaces", "value5");
  }

  @Test
  void apply_withLongStrings_shouldHandleLongStrings() {
    // Given: a HistoricVariableUpdate with long string values
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    String longString = "This is a very long string value that might be used in real-world scenarios " +
        "where variable values contain detailed information, logs, error messages, or other lengthy " +
        "text content that needs to be preserved during the conversion process.";

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("longValue", longString);

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: long string should be preserved
    assertThat(result.getOutputs()).containsEntry("longValue", longString);
  }

  @Test
  void apply_withEmptyStringValues_shouldHandleEmptyStrings() {
    // Given: a HistoricVariableUpdate with empty string values
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("emptyString", "");
    outputs.put("normalString", "value");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: empty string should be preserved
    assertThat(result.getOutputs()).containsEntry("emptyString", "");
    assertThat(result.getOutputs()).containsEntry("normalString", "value");
  }

  @Test
  void apply_withNegativeRevision_shouldHandleNegativeRevision() {
    // Given: a HistoricVariableUpdate with negative revision (edge case)
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("data", "test");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(-1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: negative revision should be handled
    assertThat(result.getRevision()).isEqualTo(-1);
  }

  @Test
  void apply_withMaxIntegerRevision_shouldHandleMaxInteger() {
    // Given: a HistoricVariableUpdate with Integer.MAX_VALUE revision
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("data", "test");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(Integer.MAX_VALUE);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: should handle MAX_VALUE
    assertThat(result.getRevision()).isEqualTo(Integer.MAX_VALUE);
  }

  @Test
  void apply_withMinIntegerRevision_shouldHandleMinInteger() {
    // Given: a HistoricVariableUpdate with Integer.MIN_VALUE revision
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("data", "test");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(Integer.MIN_VALUE);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: should handle MIN_VALUE
    assertThat(result.getRevision()).isEqualTo(Integer.MIN_VALUE);
  }

  @Test
  void apply_withUnicodeCharacters_shouldHandleUnicode() {
    // Given: a HistoricVariableUpdate with Unicode characters
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("unicode", "Ñoño 用户 タスク 😀");
    outputs.put("emoji", "🎉🎊🎈");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: Unicode characters should be preserved
    assertThat(result.getOutputs()).containsEntry("unicode", "Ñoño 用户 タスク 😀");
    assertThat(result.getOutputs()).containsEntry("emoji", "🎉🎊🎈");
  }

  @Test
  void apply_multipleConversions_shouldBeStateless() {
    // Given: two different HistoricVariableUpdate instances
    HistoricVariableUpdate variableUpdate1 = mock(HistoricVariableUpdate.class);
    Map<String, Object> outputs1 = new HashMap<>();
    outputs1.put("key1", "value1");
    when(variableUpdate1.getValue()).thenReturn(outputs1);
    when(variableUpdate1.getRevision()).thenReturn(1);
    when(variableUpdate1.getTime()).thenReturn(Date.from(Instant.parse("2024-01-15T10:00:00Z")));

    HistoricVariableUpdate variableUpdate2 = mock(HistoricVariableUpdate.class);
    Map<String, Object> outputs2 = new HashMap<>();
    outputs2.put("key2", "value2");
    when(variableUpdate2.getValue()).thenReturn(outputs2);
    when(variableUpdate2.getRevision()).thenReturn(2);
    when(variableUpdate2.getTime()).thenReturn(Date.from(Instant.parse("2024-01-15T11:00:00Z")));

    // When: applying the converter to both
    VariablesDomain result1 = converter.apply((HistoricDetail) variableUpdate1);
    VariablesDomain result2 = converter.apply((HistoricDetail) variableUpdate2);

    // Then: results should be independent and correct
    assertThat(result1.getOutputs()).containsEntry("key1", "value1");
    assertThat(result1.getRevision()).isEqualTo(1);
    assertThat(result1.getUpdateTime()).isEqualTo(Instant.parse("2024-01-15T10:00:00Z"));

    assertThat(result2.getOutputs()).containsEntry("key2", "value2");
    assertThat(result2.getRevision()).isEqualTo(2);
    assertThat(result2.getUpdateTime()).isEqualTo(Instant.parse("2024-01-15T11:00:00Z"));

    // Verify they are different objects
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void apply_withNullValuesInMap_shouldHandleNullValues() {
    // Given: a HistoricVariableUpdate with null values in the map
    HistoricVariableUpdate variableUpdate = mock(HistoricVariableUpdate.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("nullValue", null);
    outputs.put("nonNullValue", "value");

    when(variableUpdate.getValue()).thenReturn(outputs);
    when(variableUpdate.getRevision()).thenReturn(1);

    Instant testInstant = Instant.parse("2024-01-15T10:30:00Z");
    when(variableUpdate.getTime()).thenReturn(Date.from(testInstant));

    // When: applying the converter
    VariablesDomain result = converter.apply((HistoricDetail) variableUpdate);

    // Then: null values should be preserved
    assertThat(result.getOutputs()).containsKey("nullValue");
    assertThat(result.getOutputs().get("nullValue")).isNull();
    assertThat(result.getOutputs()).containsEntry("nonNullValue", "value");
  }
}
