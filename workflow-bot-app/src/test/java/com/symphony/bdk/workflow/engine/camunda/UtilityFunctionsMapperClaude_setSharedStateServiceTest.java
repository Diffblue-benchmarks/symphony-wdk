package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class UtilityFunctionsMapperClaude_setSharedStateServiceTest {

  private SharedDataStore sharedDataStore;
  private SharedDataStore anotherSharedDataStore;

  @BeforeEach
  void setUp() {
    sharedDataStore = mock(SharedDataStore.class);
    anotherSharedDataStore = mock(SharedDataStore.class);
  }

  @AfterEach
  void tearDown() {
    // Clean up static state to avoid test pollution
    UtilityFunctionsMapper.setSharedStateService(null);
  }

  @Test
  void setSharedStateService_shouldMakeSharedDataStoreAvailableToReadSharedMethod() {
    // Given: A SharedDataStore that returns specific data
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("testKey", "testValue");
    when(sharedDataStore.getNamespaceData("testNamespace")).thenReturn(namespaceData);

    // When: setSharedStateService is called
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // Then: The readShared() method should use the set service
    Object actualValue = UtilityFunctionsMapper.readShared("testNamespace", "testKey");
    assertThat(actualValue).isEqualTo("testValue");
    verify(sharedDataStore).getNamespaceData("testNamespace");
  }

  @Test
  void setSharedStateService_shouldMakeSharedDataStoreAvailableToWriteSharedMethod() {
    // Given: A SharedDataStore
    when(sharedDataStore.getNamespaceData("writeNamespace")).thenReturn(new HashMap<>());

    // When: setSharedStateService is called
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // And: writeShared is called
    UtilityFunctionsMapper.writeShared("writeNamespace", "newKey", "newValue");

    // Then: The writeShared() method should use the set service
    verify(sharedDataStore).putNamespaceData("writeNamespace", "newKey", "newValue");
  }

  @Test
  void setSharedStateService_calledMultipleTimes_shouldUpdateToLatestService() {
    // Given: Two different SharedDataStores
    Map<String, Object> firstData = new HashMap<>();
    firstData.put("key", "firstValue");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(firstData);

    Map<String, Object> secondData = new HashMap<>();
    secondData.put("key", "secondValue");
    when(anotherSharedDataStore.getNamespaceData("ns")).thenReturn(secondData);

    // When: setSharedStateService is called twice with different services
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);
    UtilityFunctionsMapper.setSharedStateService(anotherSharedDataStore);

    // Then: The readShared() method should use the latest service
    Object actualValue = UtilityFunctionsMapper.readShared("ns", "key");
    assertThat(actualValue).isEqualTo("secondValue");
    verify(anotherSharedDataStore).getNamespaceData("ns");
  }

  @Test
  void setSharedStateService_withNull_shouldAllowNullSharedDataStore() {
    // When: setSharedStateService is called with null
    UtilityFunctionsMapper.setSharedStateService(null);

    // Then: The method should complete without throwing an exception
    // This is a valid state (though readShared/writeShared would fail if called)
  }

  @Test
  void setSharedStateService_shouldReplaceExistingService() {
    // Given: An initial SharedDataStore is set
    Map<String, Object> firstData = new HashMap<>();
    firstData.put("key", "value1");
    when(sharedDataStore.getNamespaceData("namespace")).thenReturn(firstData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: A new SharedDataStore is set
    Map<String, Object> secondData = new HashMap<>();
    secondData.put("key", "value2");
    when(anotherSharedDataStore.getNamespaceData("namespace")).thenReturn(secondData);
    UtilityFunctionsMapper.setSharedStateService(anotherSharedDataStore);

    // Then: Only the new service should be used, not the old one
    Object actualValue = UtilityFunctionsMapper.readShared("namespace", "key");
    assertThat(actualValue).isEqualTo("value2");
  }

  @Test
  void setSharedStateService_shouldWorkWithDifferentNamespaces() {
    // Given: A SharedDataStore with multiple namespaces
    Map<String, Object> namespace1Data = new HashMap<>();
    namespace1Data.put("key1", "value1");
    when(sharedDataStore.getNamespaceData("namespace1")).thenReturn(namespace1Data);

    Map<String, Object> namespace2Data = new HashMap<>();
    namespace2Data.put("key2", "value2");
    when(sharedDataStore.getNamespaceData("namespace2")).thenReturn(namespace2Data);

    // When: setSharedStateService is called
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // Then: The readShared() method should work with different namespaces
    Object value1 = UtilityFunctionsMapper.readShared("namespace1", "key1");
    Object value2 = UtilityFunctionsMapper.readShared("namespace2", "key2");
    assertThat(value1).isEqualTo("value1");
    assertThat(value2).isEqualTo("value2");
  }

  @Test
  void setSharedStateService_shouldPersistAcrossMultipleReadSharedCalls() {
    // Given: A SharedDataStore is set
    Map<String, Object> data = new HashMap<>();
    data.put("persistKey", "persistValue");
    when(sharedDataStore.getNamespaceData("namespace")).thenReturn(data);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called multiple times
    Object firstCall = UtilityFunctionsMapper.readShared("namespace", "persistKey");
    Object secondCall = UtilityFunctionsMapper.readShared("namespace", "persistKey");
    Object thirdCall = UtilityFunctionsMapper.readShared("namespace", "persistKey");

    // Then: All calls should use the same SharedDataStore
    assertThat(firstCall).isEqualTo("persistValue");
    assertThat(secondCall).isEqualTo("persistValue");
    assertThat(thirdCall).isEqualTo("persistValue");
    verify(sharedDataStore, times(3)).getNamespaceData("namespace");
  }

  @Test
  void setSharedStateService_shouldPersistAcrossMultipleWriteSharedCalls() {
    // Given: A SharedDataStore is set
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: writeShared() is called multiple times
    UtilityFunctionsMapper.writeShared("ns", "k1", "v1");
    UtilityFunctionsMapper.writeShared("ns", "k2", "v2");
    UtilityFunctionsMapper.writeShared("ns", "k3", "v3");

    // Then: All calls should use the same SharedDataStore
    verify(sharedDataStore).putNamespaceData("ns", "k1", "v1");
    verify(sharedDataStore).putNamespaceData("ns", "k2", "v2");
    verify(sharedDataStore).putNamespaceData("ns", "k3", "v3");
  }

  @Test
  void setSharedStateService_shouldWorkInConjunctionWithConstructor() {
    // Given: A SharedDataStore
    Map<String, Object> data = new HashMap<>();
    data.put("key", "constructorValue");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(data);

    // When: UtilityFunctionsMapper constructor is called (which calls setSharedStateService internally)
    new UtilityFunctionsMapper(null, sharedDataStore, null);

    // Then: The readShared() method should work with the service set by the constructor
    Object actualValue = UtilityFunctionsMapper.readShared("ns", "key");
    assertThat(actualValue).isEqualTo("constructorValue");
    verify(sharedDataStore).getNamespaceData("ns");
  }

  @Test
  void setSharedStateService_afterConstructor_shouldOverrideConstructorSetService() {
    // Given: UtilityFunctionsMapper constructor is called with first service
    Map<String, Object> firstData = new HashMap<>();
    firstData.put("key", "firstValue");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(firstData);
    new UtilityFunctionsMapper(null, sharedDataStore, null);

    // When: setSharedStateService is called with a different service
    Map<String, Object> secondData = new HashMap<>();
    secondData.put("key", "secondValue");
    when(anotherSharedDataStore.getNamespaceData("ns")).thenReturn(secondData);
    UtilityFunctionsMapper.setSharedStateService(anotherSharedDataStore);

    // Then: The latest service should be used
    Object actualValue = UtilityFunctionsMapper.readShared("ns", "key");
    assertThat(actualValue).isEqualTo("secondValue");
  }

  @Test
  void setSharedStateService_shouldWorkWithComplexDataTypes() {
    // Given: A SharedDataStore with complex data types
    Map<String, Object> complexData = new HashMap<>();
    complexData.put("list", java.util.Arrays.asList(1, 2, 3));
    complexData.put("map", java.util.Collections.singletonMap("nested", "value"));
    complexData.put("number", 42);
    when(sharedDataStore.getNamespaceData("complex")).thenReturn(complexData);

    // When: setSharedStateService is called
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // Then: The readShared() method should return the complex data correctly
    Object listValue = UtilityFunctionsMapper.readShared("complex", "list");
    Object mapValue = UtilityFunctionsMapper.readShared("complex", "map");
    Object numberValue = UtilityFunctionsMapper.readShared("complex", "number");

    assertThat(listValue).isEqualTo(java.util.Arrays.asList(1, 2, 3));
    assertThat(mapValue).isEqualTo(java.util.Collections.singletonMap("nested", "value"));
    assertThat(numberValue).isEqualTo(42);
  }

  @Test
  void setSharedStateService_shouldSupportReadWriteIntegration() {
    // Given: A SharedDataStore with actual map for integration
    Map<String, Object> actualData = new HashMap<>();
    when(sharedDataStore.getNamespaceData("integration")).thenReturn(actualData);

    // When: setSharedStateService is called
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // And: writeShared is called to store data
    UtilityFunctionsMapper.writeShared("integration", "testKey", "testValue");

    // Then: The service should have been called to store the data
    verify(sharedDataStore).putNamespaceData("integration", "testKey", "testValue");
  }

  @Test
  void setSharedStateService_shouldHandleEmptyNamespaceData() {
    // Given: A SharedDataStore returning empty data
    Map<String, Object> emptyData = new HashMap<>();
    when(sharedDataStore.getNamespaceData("empty")).thenReturn(emptyData);

    // When: setSharedStateService is called
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // Then: readShared should return null for non-existent keys
    Object value = UtilityFunctionsMapper.readShared("empty", "nonExistentKey");
    assertThat(value).isNull();
  }

  @Test
  void setSharedStateService_shouldWorkWithNullValuesInData() {
    // Given: A SharedDataStore with null values
    Map<String, Object> dataWithNull = new HashMap<>();
    dataWithNull.put("nullKey", null);
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(dataWithNull);

    // When: setSharedStateService is called
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // Then: readShared should return null for keys with null values
    Object value = UtilityFunctionsMapper.readShared("ns", "nullKey");
    assertThat(value).isNull();
  }
}
