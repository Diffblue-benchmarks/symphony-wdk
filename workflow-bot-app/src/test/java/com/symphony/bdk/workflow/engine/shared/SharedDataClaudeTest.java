package com.symphony.bdk.workflow.engine.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SharedDataClaudeTest {

  // ==================== namespace() Method Tests ====================

  @Test
  void namespace_withValidNamespace_shouldSetNamespaceAndReturnThis() {
    // Given: A SharedData instance and a namespace
    SharedData sharedData = new SharedData();
    String namespace = "test-namespace";

    // When: Calling namespace() method
    SharedData result = sharedData.namespace(namespace);

    // Then: Should set the namespace and return the same instance
    assertThat(result).isSameAs(sharedData);
    assertThat(result.getNamespace()).isEqualTo(namespace);
    assertThat(sharedData.getNamespace()).isEqualTo(namespace);
  }

  @Test
  void namespace_withNullNamespace_shouldSetNullNamespaceAndReturnThis() {
    // Given: A SharedData instance with null namespace
    SharedData sharedData = new SharedData();

    // When: Calling namespace() with null
    SharedData result = sharedData.namespace(null);

    // Then: Should set null namespace and return the same instance
    assertThat(result).isSameAs(sharedData);
    assertThat(result.getNamespace()).isNull();
  }

  @Test
  void namespace_withEmptyString_shouldSetEmptyNamespaceAndReturnThis() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();

    // When: Calling namespace() with empty string
    SharedData result = sharedData.namespace("");

    // Then: Should set empty namespace and return the same instance
    assertThat(result).isSameAs(sharedData);
    assertThat(result.getNamespace()).isEqualTo("");
  }

  @Test
  void namespace_withSpecialCharacters_shouldSetNamespaceAndReturnThis() {
    // Given: A SharedData instance and a namespace with special characters
    SharedData sharedData = new SharedData();
    String namespace = "test_namespace-123";

    // When: Calling namespace() method
    SharedData result = sharedData.namespace(namespace);

    // Then: Should set the namespace and return the same instance
    assertThat(result).isSameAs(sharedData);
    assertThat(result.getNamespace()).isEqualTo(namespace);
  }

  @Test
  void namespace_calledMultipleTimes_shouldOverwritePreviousValue() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();

    // When: Calling namespace() multiple times
    sharedData.namespace("first-namespace");
    SharedData result = sharedData.namespace("second-namespace");

    // Then: Should have the last set value
    assertThat(result).isSameAs(sharedData);
    assertThat(result.getNamespace()).isEqualTo("second-namespace");
  }

  @Test
  void namespace_fluent_shouldAllowMethodChaining() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();

    // When: Using fluent API to set namespace and other properties
    SharedData result = sharedData
        .namespace("chained-namespace");

    // Then: Should allow chaining and have correct values
    assertThat(result).isSameAs(sharedData);
    assertThat(result.getNamespace()).isEqualTo("chained-namespace");
  }

  // ==================== Lombok Generated Methods Tests ====================

  @Test
  void setAndGetId_shouldWorkCorrectly() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();
    String id = "test-id-123";

    // When: Setting id
    sharedData.setId(id);

    // Then: Should retrieve the same id
    assertThat(sharedData.getId()).isEqualTo(id);
  }

  @Test
  void setAndGetNamespace_shouldWorkCorrectly() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();
    String namespace = "test-namespace";

    // When: Setting namespace using setter
    sharedData.setNamespace(namespace);

    // Then: Should retrieve the same namespace
    assertThat(sharedData.getNamespace()).isEqualTo(namespace);
  }

  @Test
  void setAndGetProperties_shouldWorkCorrectly() {
    // Given: A SharedData instance and properties map
    SharedData sharedData = new SharedData();
    Map<String, Object> properties = new HashMap<>();
    properties.put("key1", "value1");
    properties.put("key2", 42);

    // When: Setting properties
    sharedData.setProperties(properties);

    // Then: Should retrieve the same properties
    assertThat(sharedData.getProperties()).isEqualTo(properties);
    assertThat(sharedData.getProperties()).hasSize(2);
    assertThat(sharedData.getProperties().get("key1")).isEqualTo("value1");
    assertThat(sharedData.getProperties().get("key2")).isEqualTo(42);
  }

  @Test
  void setAndGetLastUpdated_shouldWorkCorrectly() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();
    Long lastUpdated = 1234567890L;

    // When: Setting lastUpdated
    sharedData.setLastUpdated(lastUpdated);

    // Then: Should retrieve the same lastUpdated
    assertThat(sharedData.getLastUpdated()).isEqualTo(lastUpdated);
  }

  @Test
  void defaultConstructor_shouldInitializePropertiesMapAsEmpty() {
    // Given & When: Creating a new SharedData instance
    SharedData sharedData = new SharedData();

    // Then: Properties map should be initialized and empty
    assertThat(sharedData.getProperties()).isNotNull();
    assertThat(sharedData.getProperties()).isEmpty();
  }

  @Test
  void defaultConstructor_shouldHaveNullFields() {
    // Given & When: Creating a new SharedData instance
    SharedData sharedData = new SharedData();

    // Then: All fields except properties should be null
    assertThat(sharedData.getId()).isNull();
    assertThat(sharedData.getNamespace()).isNull();
    assertThat(sharedData.getLastUpdated()).isNull();
  }

  @Test
  void equals_withSameValues_shouldReturnTrue() {
    // Given: Two SharedData instances with same values
    SharedData sharedData1 = new SharedData();
    sharedData1.setId("id1");
    sharedData1.setNamespace("namespace1");
    sharedData1.setLastUpdated(1000L);
    Map<String, Object> props1 = new HashMap<>();
    props1.put("key", "value");
    sharedData1.setProperties(props1);

    SharedData sharedData2 = new SharedData();
    sharedData2.setId("id1");
    sharedData2.setNamespace("namespace1");
    sharedData2.setLastUpdated(1000L);
    Map<String, Object> props2 = new HashMap<>();
    props2.put("key", "value");
    sharedData2.setProperties(props2);

    // When & Then: Should be equal
    assertThat(sharedData1).isEqualTo(sharedData2);
    assertThat(sharedData1.hashCode()).isEqualTo(sharedData2.hashCode());
  }

  @Test
  void equals_withDifferentValues_shouldReturnFalse() {
    // Given: Two SharedData instances with different values
    SharedData sharedData1 = new SharedData();
    sharedData1.setId("id1");
    sharedData1.setNamespace("namespace1");

    SharedData sharedData2 = new SharedData();
    sharedData2.setId("id2");
    sharedData2.setNamespace("namespace2");

    // When & Then: Should not be equal
    assertThat(sharedData1).isNotEqualTo(sharedData2);
  }

  @Test
  void equals_withSameInstance_shouldReturnTrue() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();

    // When & Then: Should be equal to itself
    assertThat(sharedData).isEqualTo(sharedData);
  }

  @Test
  void equals_withNull_shouldReturnFalse() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();

    // When & Then: Should not be equal to null
    assertThat(sharedData).isNotEqualTo(null);
  }

  @Test
  void toString_shouldContainAllFields() {
    // Given: A SharedData instance with values
    SharedData sharedData = new SharedData();
    sharedData.setId("test-id");
    sharedData.setNamespace("test-namespace");
    sharedData.setLastUpdated(123456L);
    Map<String, Object> props = new HashMap<>();
    props.put("key", "value");
    sharedData.setProperties(props);

    // When: Calling toString
    String result = sharedData.toString();

    // Then: Should contain all field values
    assertThat(result).contains("test-id");
    assertThat(result).contains("test-namespace");
    assertThat(result).contains("123456");
  }

  // ==================== Properties Map Manipulation Tests ====================

  @Test
  void properties_canBeModified_afterRetrieval() {
    // Given: A SharedData instance with properties
    SharedData sharedData = new SharedData();
    Map<String, Object> properties = new HashMap<>();
    properties.put("key1", "value1");
    sharedData.setProperties(properties);

    // When: Modifying properties through getter
    sharedData.getProperties().put("key2", "value2");

    // Then: Properties should be updated
    assertThat(sharedData.getProperties()).hasSize(2);
    assertThat(sharedData.getProperties()).containsEntry("key1", "value1");
    assertThat(sharedData.getProperties()).containsEntry("key2", "value2");
  }

  @Test
  void properties_withNullValue_shouldStoreNull() {
    // Given: A SharedData instance
    SharedData sharedData = new SharedData();

    // When: Setting properties to null
    sharedData.setProperties(null);

    // Then: Should store null
    assertThat(sharedData.getProperties()).isNull();
  }

  @Test
  void properties_withComplexObjects_shouldStore() {
    // Given: A SharedData instance and complex properties
    SharedData sharedData = new SharedData();
    Map<String, Object> properties = new HashMap<>();
    properties.put("string", "text");
    properties.put("integer", 123);
    properties.put("double", 45.67);
    properties.put("boolean", true);
    properties.put("null", null);
    Map<String, String> nestedMap = new HashMap<>();
    nestedMap.put("nested", "value");
    properties.put("map", nestedMap);

    // When: Setting complex properties
    sharedData.setProperties(properties);

    // Then: Should store all complex values
    assertThat(sharedData.getProperties()).hasSize(6);
    assertThat(sharedData.getProperties().get("string")).isEqualTo("text");
    assertThat(sharedData.getProperties().get("integer")).isEqualTo(123);
    assertThat(sharedData.getProperties().get("double")).isEqualTo(45.67);
    assertThat(sharedData.getProperties().get("boolean")).isEqualTo(true);
    assertThat(sharedData.getProperties().get("null")).isNull();
    assertThat(sharedData.getProperties().get("map")).isEqualTo(nestedMap);
  }

  // ==================== Integration Tests ====================

  @Test
  void fullObjectLifecycle_shouldWorkCorrectly() {
    // Given: Creating and configuring a SharedData object
    SharedData sharedData = new SharedData();

    // When: Setting all properties
    sharedData.setId("lifecycle-id");
    sharedData.namespace("lifecycle-namespace");
    Map<String, Object> properties = new HashMap<>();
    properties.put("key1", "value1");
    properties.put("key2", 42);
    sharedData.setProperties(properties);
    sharedData.setLastUpdated(9876543210L);

    // Then: All properties should be set correctly
    assertThat(sharedData.getId()).isEqualTo("lifecycle-id");
    assertThat(sharedData.getNamespace()).isEqualTo("lifecycle-namespace");
    assertThat(sharedData.getProperties()).hasSize(2);
    assertThat(sharedData.getProperties()).containsEntry("key1", "value1");
    assertThat(sharedData.getProperties()).containsEntry("key2", 42);
    assertThat(sharedData.getLastUpdated()).isEqualTo(9876543210L);
  }

  @Test
  void namespace_withMaxLengthNamespace_shouldSetAndReturnThis() {
    // Given: A SharedData instance and a 15-character namespace (max length per @Column(length = 15))
    SharedData sharedData = new SharedData();
    String namespace = "123456789012345"; // exactly 15 characters

    // When: Calling namespace() method
    SharedData result = sharedData.namespace(namespace);

    // Then: Should set the namespace and return the same instance
    assertThat(result).isSameAs(sharedData);
    assertThat(result.getNamespace()).isEqualTo(namespace);
    assertThat(result.getNamespace()).hasSize(15);
  }
}
