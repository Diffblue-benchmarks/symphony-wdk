package com.symphony.bdk.workflow.engine.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSharedDataStoreClaudeTest {

  @Mock
  private SharedDataRepository repository;

  private DefaultSharedDataStore sharedDataStore;

  @BeforeEach
  void setUp() {
    sharedDataStore = new DefaultSharedDataStore(repository);
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_withValidRepository_shouldInitializeCorrectly() {
    // Given: Valid repository
    SharedDataRepository repo = org.mockito.Mockito.mock(SharedDataRepository.class);

    // When: Creating a new DefaultSharedDataStore
    DefaultSharedDataStore store = new DefaultSharedDataStore(repo);

    // Then: Object should be created successfully
    assertThat(store).isNotNull();
  }

  // ==================== getNamespaceData() Tests ====================

  @Test
  void getNamespaceData_withExistingNamespace_shouldReturnProperties() {
    // Given: An existing namespace with data
    String namespace = "test-namespace";
    Map<String, Object> properties = new HashMap<>();
    properties.put("key1", "value1");
    properties.put("key2", 42);
    properties.put("key3", true);

    SharedData sharedData = new SharedData();
    sharedData.setNamespace(namespace);
    sharedData.setProperties(properties);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(sharedData));

    // When: Getting namespace data
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Then: Should return the properties
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get("key1")).isEqualTo("value1");
    assertThat(result.get("key2")).isEqualTo(42);
    assertThat(result.get("key3")).isEqualTo(true);
    verify(repository, times(1)).findByNamespace(namespace);
  }

  @Test
  void getNamespaceData_withNonExistingNamespace_shouldReturnEmptyMap() {
    // Given: A non-existing namespace
    String namespace = "non-existing-namespace";

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Getting namespace data
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Then: Should return an empty map (from new SharedData())
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByNamespace(namespace);
  }

  @Test
  void getNamespaceData_withExistingNamespaceButEmptyProperties_shouldReturnEmptyMap() {
    // Given: An existing namespace with no properties
    String namespace = "empty-namespace";
    SharedData sharedData = new SharedData();
    sharedData.setNamespace(namespace);
    sharedData.setProperties(new HashMap<>());

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(sharedData));

    // When: Getting namespace data
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Then: Should return an empty map
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByNamespace(namespace);
  }

  @Test
  void getNamespaceData_withNullNamespace_shouldQueryRepository() {
    // Given: A null namespace
    String namespace = null;

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Getting namespace data
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Then: Should query repository and return empty map
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByNamespace(null);
  }

  @Test
  void getNamespaceData_withSpecialCharactersInNamespace_shouldReturnProperties() {
    // Given: A namespace with special characters
    String namespace = "test-ns_123";
    Map<String, Object> properties = new HashMap<>();
    properties.put("data", "value");

    SharedData sharedData = new SharedData();
    sharedData.setNamespace(namespace);
    sharedData.setProperties(properties);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(sharedData));

    // When: Getting namespace data
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Then: Should return the properties
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get("data")).isEqualTo("value");
  }

  @Test
  void getNamespaceData_withComplexObjectValues_shouldReturnAllValues() {
    // Given: A namespace with complex object values
    String namespace = "complex-namespace";
    Map<String, Object> properties = new HashMap<>();
    properties.put("string", "text");
    properties.put("integer", 123);
    properties.put("double", 45.67);
    properties.put("boolean", false);
    properties.put("null", null);
    Map<String, String> nestedMap = new HashMap<>();
    nestedMap.put("nested", "value");
    properties.put("map", nestedMap);

    SharedData sharedData = new SharedData();
    sharedData.setNamespace(namespace);
    sharedData.setProperties(properties);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(sharedData));

    // When: Getting namespace data
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Then: Should return all property values
    assertThat(result).hasSize(6);
    assertThat(result.get("string")).isEqualTo("text");
    assertThat(result.get("integer")).isEqualTo(123);
    assertThat(result.get("double")).isEqualTo(45.67);
    assertThat(result.get("boolean")).isEqualTo(false);
    assertThat(result.get("null")).isNull();
    assertThat(result.get("map")).isEqualTo(nestedMap);
  }

  // ==================== putNamespaceData() Tests ====================

  @Test
  void putNamespaceData_withNewNamespace_shouldCreateAndSaveNewSharedData() {
    // Given: A new namespace and data
    String namespace = "new-namespace";
    String key = "key1";
    Object data = "value1";

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Putting namespace data
    sharedDataStore.putNamespaceData(namespace, key, data);

    // Then: Should create new SharedData, set properties, and save
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).findByNamespace(namespace);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getNamespace()).isEqualTo(namespace);
    assertThat(savedData.getProperties()).containsEntry(key, data);
    assertThat(savedData.getLastUpdated()).isNotNull();
    assertThat(savedData.getLastUpdated()).isLessThanOrEqualTo(Instant.now().toEpochMilli());
  }

  @Test
  void putNamespaceData_withExistingNamespace_shouldUpdateAndSave() {
    // Given: An existing namespace
    String namespace = "existing-namespace";
    String key = "newKey";
    Object data = "newValue";

    Map<String, Object> existingProperties = new HashMap<>();
    existingProperties.put("oldKey", "oldValue");

    SharedData existingData = new SharedData();
    existingData.setNamespace(namespace);
    existingData.setProperties(existingProperties);
    existingData.setLastUpdated(1000000L);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(existingData));

    // When: Putting namespace data
    sharedDataStore.putNamespaceData(namespace, key, data);

    // Then: Should update existing SharedData and save
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).findByNamespace(namespace);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getNamespace()).isEqualTo(namespace);
    assertThat(savedData.getProperties()).hasSize(2);
    assertThat(savedData.getProperties()).containsEntry("oldKey", "oldValue");
    assertThat(savedData.getProperties()).containsEntry(key, data);
    assertThat(savedData.getLastUpdated()).isGreaterThan(1000000L);
  }

  @Test
  void putNamespaceData_withExistingKey_shouldOverwriteValue() {
    // Given: An existing namespace with an existing key
    String namespace = "namespace";
    String key = "existingKey";
    Object oldValue = "oldValue";
    Object newValue = "newValue";

    Map<String, Object> properties = new HashMap<>();
    properties.put(key, oldValue);

    SharedData existingData = new SharedData();
    existingData.setNamespace(namespace);
    existingData.setProperties(properties);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(existingData));

    // When: Putting new data with the same key
    sharedDataStore.putNamespaceData(namespace, key, newValue);

    // Then: Should overwrite the existing value
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties()).hasSize(1);
    assertThat(savedData.getProperties().get(key)).isEqualTo(newValue);
  }

  @Test
  void putNamespaceData_withNullValue_shouldStoreNullValue() {
    // Given: A namespace and null data
    String namespace = "namespace";
    String key = "nullKey";
    Object data = null;

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Putting null data
    sharedDataStore.putNamespaceData(namespace, key, data);

    // Then: Should store null value
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties()).containsKey(key);
    assertThat(savedData.getProperties().get(key)).isNull();
  }

  @Test
  void putNamespaceData_withComplexObject_shouldStoreComplexObject() {
    // Given: A namespace and complex object data
    String namespace = "namespace";
    String key = "complexKey";
    Map<String, Object> complexData = new HashMap<>();
    complexData.put("nested1", "value1");
    complexData.put("nested2", 123);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Putting complex object
    sharedDataStore.putNamespaceData(namespace, key, complexData);

    // Then: Should store the complex object
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties().get(key)).isEqualTo(complexData);
  }

  @Test
  void putNamespaceData_shouldUpdateLastUpdatedTimestamp() {
    // Given: An existing namespace
    String namespace = "namespace";
    String key = "key";
    Object data = "value";
    long oldTimestamp = 1000000L;

    SharedData existingData = new SharedData();
    existingData.setNamespace(namespace);
    existingData.setProperties(new HashMap<>());
    existingData.setLastUpdated(oldTimestamp);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(existingData));

    // When: Putting namespace data
    long beforePut = Instant.now().toEpochMilli();
    sharedDataStore.putNamespaceData(namespace, key, data);
    long afterPut = Instant.now().toEpochMilli();

    // Then: Should update lastUpdated timestamp
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getLastUpdated()).isGreaterThan(oldTimestamp);
    assertThat(savedData.getLastUpdated()).isGreaterThanOrEqualTo(beforePut);
    assertThat(savedData.getLastUpdated()).isLessThanOrEqualTo(afterPut);
  }

  @Test
  void putNamespaceData_withEmptyKey_shouldStoreWithEmptyKey() {
    // Given: A namespace and empty key
    String namespace = "namespace";
    String key = "";
    Object data = "value";

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Putting data with empty key
    sharedDataStore.putNamespaceData(namespace, key, data);

    // Then: Should store with empty key
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties()).containsEntry("", data);
  }

  @Test
  void putNamespaceData_withSpecialCharactersInKey_shouldStore() {
    // Given: A namespace and key with special characters
    String namespace = "namespace";
    String key = "key-with_$pecial.chars";
    Object data = "value";

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Putting data with special characters in key
    sharedDataStore.putNamespaceData(namespace, key, data);

    // Then: Should store successfully
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties()).containsEntry(key, data);
  }

  @Test
  void putNamespaceData_withNumericValue_shouldStoreNumericValue() {
    // Given: A namespace and numeric data
    String namespace = "namespace";
    String key = "numericKey";
    Integer data = 12345;

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Putting numeric data
    sharedDataStore.putNamespaceData(namespace, key, data);

    // Then: Should store numeric value
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties().get(key)).isEqualTo(data);
  }

  @Test
  void putNamespaceData_withBooleanValue_shouldStoreBooleanValue() {
    // Given: A namespace and boolean data
    String namespace = "namespace";
    String key = "boolKey";
    Boolean data = true;

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Putting boolean data
    sharedDataStore.putNamespaceData(namespace, key, data);

    // Then: Should store boolean value
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties().get(key)).isEqualTo(data);
  }

  // ==================== Integration-style Tests ====================

  @Test
  void putAndGet_shouldRoundTripCorrectly() {
    // Given: A namespace, key, and data
    String namespace = "integration-namespace";
    String key = "integration-key";
    Object data = "integration-value";

    // Setup for put operation
    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: Putting data
    sharedDataStore.putNamespaceData(namespace, key, data);

    // Capture the saved SharedData
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor.capture());
    SharedData savedData = captor.getValue();

    // Setup for get operation
    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(savedData));

    // When: Getting data
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Then: Should retrieve the stored data
    assertThat(result).containsEntry(key, data);
  }

  @Test
  void putMultipleTimes_shouldAccumulateData() {
    // Given: A namespace and multiple key-value pairs
    String namespace = "multi-namespace";

    SharedData sharedData = new SharedData();
    sharedData.setNamespace(namespace);
    sharedData.setProperties(new HashMap<>());

    // First put
    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());
    sharedDataStore.putNamespaceData(namespace, "key1", "value1");

    // Update mock to return the data with key1
    ArgumentCaptor<SharedData> captor1 = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(1)).save(captor1.capture());
    SharedData afterFirst = captor1.getValue();

    // Second put
    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(afterFirst));
    sharedDataStore.putNamespaceData(namespace, "key2", "value2");

    // Verify accumulated data
    ArgumentCaptor<SharedData> captor2 = ArgumentCaptor.forClass(SharedData.class);
    verify(repository, times(2)).save(captor2.capture());
    SharedData afterSecond = captor2.getAllValues().get(1);

    assertThat(afterSecond.getProperties()).hasSize(2);
    assertThat(afterSecond.getProperties()).containsEntry("key1", "value1");
    assertThat(afterSecond.getProperties()).containsEntry("key2", "value2");
  }

  @Test
  void putAndGetWithMultipleKeys_shouldReturnAllKeys() {
    // Given: A namespace with multiple key-value pairs
    String namespace = "multi-key-namespace";

    SharedData sharedData = new SharedData();
    sharedData.setNamespace(namespace);
    Map<String, Object> properties = new HashMap<>();
    properties.put("key1", "value1");
    properties.put("key2", 42);
    properties.put("key3", true);
    sharedData.setProperties(properties);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(sharedData));

    // When: Getting namespace data
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Then: Should return all keys
    assertThat(result).hasSize(3);
    assertThat(result).containsEntry("key1", "value1");
    assertThat(result).containsEntry("key2", 42);
    assertThat(result).containsEntry("key3", true);
  }
}
