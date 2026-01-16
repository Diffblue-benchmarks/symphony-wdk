package com.symphony.bdk.workflow.api.v1.dto;

import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class VariableViewClaudeTest {

  // ==================== Constructor with VariablesDomain Tests ====================

  @Test
  void constructor_withNullVariablesDomain_shouldHaveNullFields() {
    // Given: A null VariablesDomain
    VariablesDomain domain = null;

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: All fields should be null or default values
    assertThat(variableView.getOutputs()).isNull();
    assertThat(variableView.getRevision()).isEqualTo(0);
    assertThat(variableView.getUpdateTime()).isNull();
  }

  @Test
  void constructor_withValidVariablesDomain_shouldCopyAllFields() {
    // Given: A VariablesDomain with all fields set
    VariablesDomain domain = new VariablesDomain();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key1", "value1");
    outputs.put("key2", 42);
    domain.setOutputs(outputs);
    domain.setRevision(5);
    Instant updateTime = Instant.parse("2024-01-15T10:30:00Z");
    domain.setUpdateTime(updateTime);

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: All fields should be copied correctly
    assertThat(variableView.getOutputs()).isEqualTo(outputs);
    assertThat(variableView.getRevision()).isEqualTo(5);
    assertThat(variableView.getUpdateTime()).isEqualTo(updateTime);
  }

  @Test
  void constructor_withEmptyOutputs_shouldSetEmptyOutputs() {
    // Given: A VariablesDomain with empty outputs map
    VariablesDomain domain = new VariablesDomain();
    domain.setOutputs(Collections.emptyMap());
    domain.setRevision(1);
    Instant updateTime = Instant.now();
    domain.setUpdateTime(updateTime);

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The outputs should be an empty map
    assertThat(variableView.getOutputs()).isEmpty();
    assertThat(variableView.getRevision()).isEqualTo(1);
    assertThat(variableView.getUpdateTime()).isEqualTo(updateTime);
  }

  @Test
  void constructor_withNullOutputs_shouldSetNullOutputs() {
    // Given: A VariablesDomain with null outputs
    VariablesDomain domain = new VariablesDomain();
    domain.setOutputs(null);
    domain.setRevision(3);
    Instant updateTime = Instant.now();
    domain.setUpdateTime(updateTime);

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The outputs should be null
    assertThat(variableView.getOutputs()).isNull();
    assertThat(variableView.getRevision()).isEqualTo(3);
    assertThat(variableView.getUpdateTime()).isEqualTo(updateTime);
  }

  @Test
  void constructor_withZeroRevision_shouldSetZeroRevision() {
    // Given: A VariablesDomain with revision 0
    VariablesDomain domain = new VariablesDomain();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("test", "value");
    domain.setOutputs(outputs);
    domain.setRevision(0);
    domain.setUpdateTime(Instant.now());

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The revision should be 0
    assertThat(variableView.getRevision()).isEqualTo(0);
    assertThat(variableView.getOutputs()).isEqualTo(outputs);
  }

  @Test
  void constructor_withNegativeRevision_shouldSetNegativeRevision() {
    // Given: A VariablesDomain with negative revision
    VariablesDomain domain = new VariablesDomain();
    domain.setRevision(-1);

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The revision should be -1
    assertThat(variableView.getRevision()).isEqualTo(-1);
  }

  @Test
  void constructor_withLargeRevision_shouldSetLargeRevision() {
    // Given: A VariablesDomain with a large revision number
    VariablesDomain domain = new VariablesDomain();
    domain.setRevision(999999);

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The revision should be set correctly
    assertThat(variableView.getRevision()).isEqualTo(999999);
  }

  @Test
  void constructor_withNullUpdateTime_shouldSetNullUpdateTime() {
    // Given: A VariablesDomain with null updateTime
    VariablesDomain domain = new VariablesDomain();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key", "value");
    domain.setOutputs(outputs);
    domain.setRevision(2);
    domain.setUpdateTime(null);

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The updateTime should be null
    assertThat(variableView.getUpdateTime()).isNull();
    assertThat(variableView.getOutputs()).isEqualTo(outputs);
    assertThat(variableView.getRevision()).isEqualTo(2);
  }

  @Test
  void constructor_withComplexOutputsMap_shouldCopyComplexMap() {
    // Given: A VariablesDomain with complex outputs containing various types
    VariablesDomain domain = new VariablesDomain();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("string", "text");
    outputs.put("integer", 123);
    outputs.put("double", 45.67);
    outputs.put("boolean", true);
    outputs.put("null", null);

    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put("nested", "value");
    outputs.put("map", nestedMap);

    domain.setOutputs(outputs);
    domain.setRevision(10);
    domain.setUpdateTime(Instant.now());

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The complex outputs map should be copied correctly
    assertThat(variableView.getOutputs()).containsEntry("string", "text");
    assertThat(variableView.getOutputs()).containsEntry("integer", 123);
    assertThat(variableView.getOutputs()).containsEntry("double", 45.67);
    assertThat(variableView.getOutputs()).containsEntry("boolean", true);
    assertThat(variableView.getOutputs()).containsEntry("null", null);
    assertThat(variableView.getOutputs()).containsEntry("map", nestedMap);
  }

  @Test
  void constructor_withEpochUpdateTime_shouldSetEpochTime() {
    // Given: A VariablesDomain with epoch time
    VariablesDomain domain = new VariablesDomain();
    Instant epochTime = Instant.EPOCH;
    domain.setUpdateTime(epochTime);
    domain.setRevision(1);

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The updateTime should be epoch
    assertThat(variableView.getUpdateTime()).isEqualTo(Instant.EPOCH);
  }

  @Test
  void constructor_withFarFutureUpdateTime_shouldSetFutureTime() {
    // Given: A VariablesDomain with far future time
    VariablesDomain domain = new VariablesDomain();
    Instant futureTime = Instant.parse("2099-12-31T23:59:59Z");
    domain.setUpdateTime(futureTime);
    domain.setRevision(1);

    // When: Creating a VariableView using the constructor
    VariableView variableView = new VariableView(domain);

    // Then: The updateTime should be set correctly
    assertThat(variableView.getUpdateTime()).isEqualTo(futureTime);
  }

  // ==================== No-Args Constructor Tests ====================

  @Test
  void noArgsConstructor_shouldCreateInstanceWithNullFields() {
    // When: Creating a VariableView using no-args constructor
    VariableView variableView = new VariableView();

    // Then: All fields should be null or default values
    assertThat(variableView.getOutputs()).isNull();
    assertThat(variableView.getRevision()).isEqualTo(0);
    assertThat(variableView.getUpdateTime()).isNull();
  }

  // ==================== Setter/Getter Tests ====================

  @Test
  void settersAndGetters_shouldWorkCorrectly() {
    // Given: A VariableView created with no-args constructor
    VariableView variableView = new VariableView();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key", "value");
    Instant updateTime = Instant.now();

    // When: Setting all fields using setters
    variableView.setOutputs(outputs);
    variableView.setRevision(7);
    variableView.setUpdateTime(updateTime);

    // Then: All getters should return the set values
    assertThat(variableView.getOutputs()).isEqualTo(outputs);
    assertThat(variableView.getRevision()).isEqualTo(7);
    assertThat(variableView.getUpdateTime()).isEqualTo(updateTime);
  }

  // ==================== Equality and HashCode Tests ====================

  @Test
  void equality_withSameValues_shouldBeEqual() {
    // Given: Two VariableView instances with same values
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key", "value");
    Instant updateTime = Instant.parse("2024-01-15T10:30:00Z");

    VariablesDomain domain1 = new VariablesDomain();
    domain1.setOutputs(outputs);
    domain1.setRevision(5);
    domain1.setUpdateTime(updateTime);

    VariablesDomain domain2 = new VariablesDomain();
    domain2.setOutputs(outputs);
    domain2.setRevision(5);
    domain2.setUpdateTime(updateTime);

    VariableView view1 = new VariableView(domain1);
    VariableView view2 = new VariableView(domain2);

    // When/Then: They should be equal
    assertThat(view1).isEqualTo(view2);
    assertThat(view1.hashCode()).isEqualTo(view2.hashCode());
  }

  @Test
  void equality_withDifferentRevision_shouldNotBeEqual() {
    // Given: Two VariableView instances with different revisions
    VariablesDomain domain1 = new VariablesDomain();
    domain1.setRevision(1);

    VariablesDomain domain2 = new VariablesDomain();
    domain2.setRevision(2);

    VariableView view1 = new VariableView(domain1);
    VariableView view2 = new VariableView(domain2);

    // When/Then: They should not be equal
    assertThat(view1).isNotEqualTo(view2);
  }

  @Test
  void equality_withDifferentOutputs_shouldNotBeEqual() {
    // Given: Two VariableView instances with different outputs
    Map<String, Object> outputs1 = new HashMap<>();
    outputs1.put("key1", "value1");

    Map<String, Object> outputs2 = new HashMap<>();
    outputs2.put("key2", "value2");

    VariablesDomain domain1 = new VariablesDomain();
    domain1.setOutputs(outputs1);

    VariablesDomain domain2 = new VariablesDomain();
    domain2.setOutputs(outputs2);

    VariableView view1 = new VariableView(domain1);
    VariableView view2 = new VariableView(domain2);

    // When/Then: They should not be equal
    assertThat(view1).isNotEqualTo(view2);
  }

  @Test
  void equality_withDifferentUpdateTime_shouldNotBeEqual() {
    // Given: Two VariableView instances with different update times
    VariablesDomain domain1 = new VariablesDomain();
    domain1.setUpdateTime(Instant.parse("2024-01-15T10:30:00Z"));

    VariablesDomain domain2 = new VariablesDomain();
    domain2.setUpdateTime(Instant.parse("2024-01-15T11:30:00Z"));

    VariableView view1 = new VariableView(domain1);
    VariableView view2 = new VariableView(domain2);

    // When/Then: They should not be equal
    assertThat(view1).isNotEqualTo(view2);
  }

  // ==================== ToString Test ====================

  @Test
  void toString_shouldContainAllFields() {
    // Given: A VariableView with all fields set
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("testKey", "testValue");
    Instant updateTime = Instant.parse("2024-01-15T10:30:00Z");

    VariablesDomain domain = new VariablesDomain();
    domain.setOutputs(outputs);
    domain.setRevision(5);
    domain.setUpdateTime(updateTime);

    VariableView variableView = new VariableView(domain);

    // When: Calling toString
    String result = variableView.toString();

    // Then: The string should contain all field information
    assertThat(result).contains("5");
    assertThat(result).contains("2024-01-15T10:30:00Z");
  }
}
