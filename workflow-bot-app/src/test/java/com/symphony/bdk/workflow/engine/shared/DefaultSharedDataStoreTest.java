package com.symphony.bdk.workflow.engine.shared;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DefaultSharedDataStoreTest {

  @Mock
  private SharedDataRepository repository;

  @InjectMocks
  private DefaultSharedDataStore sharedDataStore;

  @Test
  void getNamespaceData_shouldReturnPropertiesWhenNamespaceExists() {
    // Arrange
    String namespace = "test-namespace";
    Map<String, Object> expectedProperties = new HashMap<>();
    expectedProperties.put("key1", "value1");
    expectedProperties.put("key2", 42);

    SharedData sharedData = new SharedData();
    sharedData.setNamespace(namespace);
    sharedData.setProperties(expectedProperties);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(sharedData));

    // Act
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get("key1")).isEqualTo("value1");
    assertThat(result.get("key2")).isEqualTo(42);
    verify(repository).findByNamespace(namespace);
  }

  @Test
  void getNamespaceData_shouldReturnEmptyMapWhenNamespaceDoesNotExist() {
    // Arrange
    String namespace = "non-existent-namespace";
    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // Act
    Map<String, Object> result = sharedDataStore.getNamespaceData(namespace);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository).findByNamespace(namespace);
  }

  @Test
  void putNamespaceData_shouldUpdateExistingNamespace() {
    // Arrange
    String namespace = "existing-namespace";
    String key = "test-key";
    Object value = "test-value";

    Map<String, Object> existingProperties = new HashMap<>();
    existingProperties.put("old-key", "old-value");

    SharedData existingSharedData = new SharedData();
    existingSharedData.setNamespace(namespace);
    existingSharedData.setProperties(existingProperties);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(existingSharedData));
    when(repository.save(any(SharedData.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    sharedDataStore.putNamespaceData(namespace, key, value);

    // Assert
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties()).containsEntry(key, value);
    assertThat(savedData.getProperties()).containsEntry("old-key", "old-value");
    assertThat(savedData.getLastUpdated()).isNotNull();
    verify(repository).findByNamespace(namespace);
  }

  @Test
  void putNamespaceData_shouldCreateNewNamespaceWhenNotExists() {
    // Arrange
    String namespace = "new-namespace";
    String key = "new-key";
    Object value = "new-value";

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());
    when(repository.save(any(SharedData.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    sharedDataStore.putNamespaceData(namespace, key, value);

    // Assert
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getNamespace()).isEqualTo(namespace);
    assertThat(savedData.getProperties()).containsEntry(key, value);
    assertThat(savedData.getLastUpdated()).isNotNull();
    verify(repository).findByNamespace(namespace);
  }

  @Test
  void putNamespaceData_shouldUpdateLastUpdatedTimestamp() {
    // Arrange
    String namespace = "timestamp-test";
    String key = "key";
    Object value = "value";

    SharedData existingSharedData = new SharedData();
    existingSharedData.setNamespace(namespace);
    existingSharedData.setLastUpdated(1000000L);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(existingSharedData));
    when(repository.save(any(SharedData.class))).thenAnswer(invocation -> invocation.getArgument(0));

    long beforeTimestamp = System.currentTimeMillis();

    // Act
    sharedDataStore.putNamespaceData(namespace, key, value);

    long afterTimestamp = System.currentTimeMillis();

    // Assert
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getLastUpdated()).isGreaterThanOrEqualTo(beforeTimestamp);
    assertThat(savedData.getLastUpdated()).isLessThanOrEqualTo(afterTimestamp);
  }

  @Test
  void putNamespaceData_shouldHandleComplexObjectValues() {
    // Arrange
    String namespace = "complex-namespace";
    String key = "complex-key";
    Map<String, Object> complexValue = new HashMap<>();
    complexValue.put("nested-key", "nested-value");
    complexValue.put("nested-number", 123);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());
    when(repository.save(any(SharedData.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    sharedDataStore.putNamespaceData(namespace, key, complexValue);

    // Assert
    ArgumentCaptor<SharedData> captor = ArgumentCaptor.forClass(SharedData.class);
    verify(repository).save(captor.capture());

    SharedData savedData = captor.getValue();
    assertThat(savedData.getProperties().get(key)).isEqualTo(complexValue);
  }
}
