package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class UtilityFunctionsMapperClaude_readSharedTest {

  private SharedDataStore sharedDataStore;

  @BeforeEach
  void setUp() {
    sharedDataStore = mock(SharedDataStore.class);
  }

  @AfterEach
  void tearDown() {
    // Clean up static state to avoid test pollution
    UtilityFunctionsMapper.setSharedStateService(null);
  }

  @Test
  void readShared_withValidNamespaceAndKey_shouldReturnValue() {
    // Given: A SharedDataStore with data in a namespace
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key1", "value1");
    when(sharedDataStore.getNamespaceData("namespace1")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("namespace1", "key1");

    // Then: Should return the value
    assertThat(result).isEqualTo("value1");
    verify(sharedDataStore).getNamespaceData("namespace1");
  }

  @Test
  void readShared_withNonExistentKey_shouldReturnNull() {
    // Given: A SharedDataStore with data that doesn't contain the key
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("existingKey", "value");
    when(sharedDataStore.getNamespaceData("namespace")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with non-existent key
    Object result = UtilityFunctionsMapper.readShared("namespace", "nonExistentKey");

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void readShared_withEmptyNamespace_shouldReturnNull() {
    // Given: A SharedDataStore with empty namespace data
    Map<String, Object> emptyData = new HashMap<>();
    when(sharedDataStore.getNamespaceData("emptyNamespace")).thenReturn(emptyData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called on empty namespace
    Object result = UtilityFunctionsMapper.readShared("emptyNamespace", "anyKey");

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void readShared_withNullValue_shouldReturnNull() {
    // Given: A SharedDataStore with null value for a key
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("nullKey", null);
    when(sharedDataStore.getNamespaceData("namespace")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("namespace", "nullKey");

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void readShared_withStringValue_shouldReturnString() {
    // Given: A SharedDataStore with string value
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("stringKey", "Hello World");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "stringKey");

    // Then: Should return the string value
    assertThat(result).isEqualTo("Hello World");
    assertThat(result).isInstanceOf(String.class);
  }

  @Test
  void readShared_withIntegerValue_shouldReturnInteger() {
    // Given: A SharedDataStore with integer value
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("intKey", 42);
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "intKey");

    // Then: Should return the integer value
    assertThat(result).isEqualTo(42);
    assertThat(result).isInstanceOf(Integer.class);
  }

  @Test
  void readShared_withListValue_shouldReturnList() {
    // Given: A SharedDataStore with list value
    List<String> list = Arrays.asList("item1", "item2", "item3");
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("listKey", list);
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "listKey");

    // Then: Should return the list
    assertThat(result).isEqualTo(list);
    assertThat(result).isInstanceOf(List.class);
    @SuppressWarnings("unchecked")
    List<String> resultList = (List<String>) result;
    assertThat(resultList).containsExactly("item1", "item2", "item3");
  }

  @Test
  void readShared_withMapValue_shouldReturnMap() {
    // Given: A SharedDataStore with map value
    Map<String, String> innerMap = new HashMap<>();
    innerMap.put("nested", "nestedValue");
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("mapKey", innerMap);
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "mapKey");

    // Then: Should return the map
    assertThat(result).isEqualTo(innerMap);
    assertThat(result).isInstanceOf(Map.class);
    @SuppressWarnings("unchecked")
    Map<String, String> resultMap = (Map<String, String>) result;
    assertThat(resultMap.get("nested")).isEqualTo("nestedValue");
  }

  @Test
  void readShared_withBooleanValue_shouldReturnBoolean() {
    // Given: A SharedDataStore with boolean value
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("boolKey", true);
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "boolKey");

    // Then: Should return the boolean value
    assertThat(result).isEqualTo(true);
    assertThat(result).isInstanceOf(Boolean.class);
  }

  @Test
  void readShared_withDoubleValue_shouldReturnDouble() {
    // Given: A SharedDataStore with double value
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("doubleKey", 3.14);
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "doubleKey");

    // Then: Should return the double value
    assertThat(result).isEqualTo(3.14);
    assertThat(result).isInstanceOf(Double.class);
  }

  @Test
  void readShared_withMultipleKeys_shouldReturnCorrectValues() {
    // Given: A SharedDataStore with multiple keys
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key1", "value1");
    namespaceData.put("key2", "value2");
    namespaceData.put("key3", "value3");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with different keys
    Object result1 = UtilityFunctionsMapper.readShared("ns", "key1");
    Object result2 = UtilityFunctionsMapper.readShared("ns", "key2");
    Object result3 = UtilityFunctionsMapper.readShared("ns", "key3");

    // Then: Should return correct values for each key
    assertThat(result1).isEqualTo("value1");
    assertThat(result2).isEqualTo("value2");
    assertThat(result3).isEqualTo("value3");
  }

  @Test
  void readShared_withMultipleNamespaces_shouldReturnCorrectData() {
    // Given: A SharedDataStore with multiple namespaces
    Map<String, Object> namespace1Data = new HashMap<>();
    namespace1Data.put("key", "namespace1Value");
    when(sharedDataStore.getNamespaceData("namespace1")).thenReturn(namespace1Data);

    Map<String, Object> namespace2Data = new HashMap<>();
    namespace2Data.put("key", "namespace2Value");
    when(sharedDataStore.getNamespaceData("namespace2")).thenReturn(namespace2Data);

    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called on different namespaces
    Object result1 = UtilityFunctionsMapper.readShared("namespace1", "key");
    Object result2 = UtilityFunctionsMapper.readShared("namespace2", "key");

    // Then: Should return values from correct namespaces
    assertThat(result1).isEqualTo("namespace1Value");
    assertThat(result2).isEqualTo("namespace2Value");
  }

  @Test
  void readShared_calledMultipleTimes_shouldInvokeGetNamespaceDataEachTime() {
    // Given: A SharedDataStore with data
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key", "value");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called multiple times
    Object result1 = UtilityFunctionsMapper.readShared("ns", "key");
    Object result2 = UtilityFunctionsMapper.readShared("ns", "key");
    Object result3 = UtilityFunctionsMapper.readShared("ns", "key");

    // Then: Should invoke getNamespaceData each time
    assertThat(result1).isEqualTo("value");
    assertThat(result2).isEqualTo("value");
    assertThat(result3).isEqualTo("value");
    verify(sharedDataStore, times(3)).getNamespaceData("ns");
  }

  @Test
  void readShared_withEmptyStringKey_shouldWork() {
    // Given: A SharedDataStore with empty string as key
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("", "emptyKeyValue");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with empty string key
    Object result = UtilityFunctionsMapper.readShared("ns", "");

    // Then: Should return the value
    assertThat(result).isEqualTo("emptyKeyValue");
  }

  @Test
  void readShared_withEmptyStringNamespace_shouldWork() {
    // Given: A SharedDataStore with empty string as namespace
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key", "value");
    when(sharedDataStore.getNamespaceData("")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with empty string namespace
    Object result = UtilityFunctionsMapper.readShared("", "key");

    // Then: Should return the value
    assertThat(result).isEqualTo("value");
    verify(sharedDataStore).getNamespaceData("");
  }

  @Test
  void readShared_withNullKey_shouldPassThroughToMap() {
    // Given: A SharedDataStore with null as key
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put(null, "nullKeyValue");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with null key
    Object result = UtilityFunctionsMapper.readShared("ns", null);

    // Then: Should return the value (Map allows null keys)
    assertThat(result).isEqualTo("nullKeyValue");
  }

  @Test
  void readShared_withNullNamespace_shouldInvokeGetNamespaceDataWithNull() {
    // Given: A SharedDataStore configured to handle null namespace
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key", "value");
    when(sharedDataStore.getNamespaceData(null)).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with null namespace
    Object result = UtilityFunctionsMapper.readShared(null, "key");

    // Then: Should pass null to getNamespaceData
    assertThat(result).isEqualTo("value");
    verify(sharedDataStore).getNamespaceData(null);
  }

  @Test
  void readShared_whenSharedDataStoreIsNull_shouldThrowNullPointerException() {
    // Given: No SharedDataStore is set (null)
    UtilityFunctionsMapper.setSharedStateService(null);

    // When/Then: readShared() is called, should throw NullPointerException
    assertThatThrownBy(() -> UtilityFunctionsMapper.readShared("ns", "key"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void readShared_whenGetNamespaceDataReturnsNull_shouldThrowNullPointerException() {
    // Given: A SharedDataStore that returns null for getNamespaceData
    when(sharedDataStore.getNamespaceData("nonExistentNamespace")).thenReturn(null);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When/Then: readShared() is called, should throw NPE when calling get() on null
    assertThatThrownBy(() -> UtilityFunctionsMapper.readShared("nonExistentNamespace", "key"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void readShared_withSpecialCharactersInKey_shouldWork() {
    // Given: A SharedDataStore with special characters in key
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key-with-dash", "value1");
    namespaceData.put("key.with.dot", "value2");
    namespaceData.put("key_with_underscore", "value3");
    namespaceData.put("key:with:colon", "value4");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with special character keys
    Object result1 = UtilityFunctionsMapper.readShared("ns", "key-with-dash");
    Object result2 = UtilityFunctionsMapper.readShared("ns", "key.with.dot");
    Object result3 = UtilityFunctionsMapper.readShared("ns", "key_with_underscore");
    Object result4 = UtilityFunctionsMapper.readShared("ns", "key:with:colon");

    // Then: Should return correct values
    assertThat(result1).isEqualTo("value1");
    assertThat(result2).isEqualTo("value2");
    assertThat(result3).isEqualTo("value3");
    assertThat(result4).isEqualTo("value4");
  }

  @Test
  void readShared_withSpecialCharactersInNamespace_shouldWork() {
    // Given: A SharedDataStore with special characters in namespace
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key", "value");
    when(sharedDataStore.getNamespaceData("namespace-with-dash")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with special character namespace
    Object result = UtilityFunctionsMapper.readShared("namespace-with-dash", "key");

    // Then: Should return the value
    assertThat(result).isEqualTo("value");
    verify(sharedDataStore).getNamespaceData("namespace-with-dash");
  }

  @Test
  void readShared_withCaseSensitiveKeys_shouldDistinguishCases() {
    // Given: A SharedDataStore with case-sensitive keys
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key", "lowercase");
    namespaceData.put("Key", "PascalCase");
    namespaceData.put("KEY", "UPPERCASE");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with different cases
    Object result1 = UtilityFunctionsMapper.readShared("ns", "key");
    Object result2 = UtilityFunctionsMapper.readShared("ns", "Key");
    Object result3 = UtilityFunctionsMapper.readShared("ns", "KEY");

    // Then: Should distinguish between cases
    assertThat(result1).isEqualTo("lowercase");
    assertThat(result2).isEqualTo("PascalCase");
    assertThat(result3).isEqualTo("UPPERCASE");
  }

  @Test
  void readShared_withCaseSensitiveNamespaces_shouldDistinguishCases() {
    // Given: A SharedDataStore with case-sensitive namespaces
    Map<String, Object> namespace1Data = new HashMap<>();
    namespace1Data.put("key", "lowercase");
    when(sharedDataStore.getNamespaceData("namespace")).thenReturn(namespace1Data);

    Map<String, Object> namespace2Data = new HashMap<>();
    namespace2Data.put("key", "PascalCase");
    when(sharedDataStore.getNamespaceData("Namespace")).thenReturn(namespace2Data);

    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with different namespace cases
    Object result1 = UtilityFunctionsMapper.readShared("namespace", "key");
    Object result2 = UtilityFunctionsMapper.readShared("Namespace", "key");

    // Then: Should distinguish between namespace cases
    assertThat(result1).isEqualTo("lowercase");
    assertThat(result2).isEqualTo("PascalCase");
  }

  @Test
  void readShared_withLongKey_shouldWork() {
    // Given: A SharedDataStore with a very long key
    String longKey = "k".repeat(1000);
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put(longKey, "longKeyValue");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with long key
    Object result = UtilityFunctionsMapper.readShared("ns", longKey);

    // Then: Should return the value
    assertThat(result).isEqualTo("longKeyValue");
  }

  @Test
  void readShared_withLongNamespace_shouldWork() {
    // Given: A SharedDataStore with a very long namespace
    String longNamespace = "n".repeat(1000);
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key", "value");
    when(sharedDataStore.getNamespaceData(longNamespace)).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with long namespace
    Object result = UtilityFunctionsMapper.readShared(longNamespace, "key");

    // Then: Should return the value
    assertThat(result).isEqualTo("value");
    verify(sharedDataStore).getNamespaceData(longNamespace);
  }

  @Test
  void readShared_withComplexNestedStructure_shouldReturnCompleteStructure() {
    // Given: A SharedDataStore with complex nested structure
    Map<String, Object> innerMap = new HashMap<>();
    innerMap.put("level2", Arrays.asList(1, 2, 3));

    Map<String, Object> complexStructure = new HashMap<>();
    complexStructure.put("nested", innerMap);

    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("complexKey", complexStructure);
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "complexKey");

    // Then: Should return the complete nested structure
    assertThat(result).isEqualTo(complexStructure);
    @SuppressWarnings("unchecked")
    Map<String, Object> resultMap = (Map<String, Object>) result;
    @SuppressWarnings("unchecked")
    Map<String, Object> nestedMap = (Map<String, Object>) resultMap.get("nested");
    assertThat(nestedMap.get("level2")).isEqualTo(Arrays.asList(1, 2, 3));
  }

  @Test
  void readShared_withEmptyStringValue_shouldReturnEmptyString() {
    // Given: A SharedDataStore with empty string value
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("emptyKey", "");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "emptyKey");

    // Then: Should return empty string
    assertThat(result).isEqualTo("");
  }

  @Test
  void readShared_withNumericStringKey_shouldWork() {
    // Given: A SharedDataStore with numeric string as key
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("12345", "numericKeyValue");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with numeric string key
    Object result = UtilityFunctionsMapper.readShared("ns", "12345");

    // Then: Should return the value
    assertThat(result).isEqualTo("numericKeyValue");
  }

  @Test
  void readShared_afterSharedDataStoreReplacement_shouldUseNewStore() {
    // Given: First SharedDataStore is set
    SharedDataStore firstStore = mock(SharedDataStore.class);
    Map<String, Object> firstData = new HashMap<>();
    firstData.put("key", "firstValue");
    when(firstStore.getNamespaceData("ns")).thenReturn(firstData);
    UtilityFunctionsMapper.setSharedStateService(firstStore);

    // When: First call to readShared()
    Object firstResult = UtilityFunctionsMapper.readShared("ns", "key");

    // Then: Should return first value
    assertThat(firstResult).isEqualTo("firstValue");

    // Given: SharedDataStore is replaced
    SharedDataStore secondStore = mock(SharedDataStore.class);
    Map<String, Object> secondData = new HashMap<>();
    secondData.put("key", "secondValue");
    when(secondStore.getNamespaceData("ns")).thenReturn(secondData);
    UtilityFunctionsMapper.setSharedStateService(secondStore);

    // When: Second call to readShared()
    Object secondResult = UtilityFunctionsMapper.readShared("ns", "key");

    // Then: Should return second value from new store
    assertThat(secondResult).isEqualTo("secondValue");
  }

  @Test
  void readShared_integrationWithConstructor_shouldWorkCorrectly() {
    // Given: A SharedDataStore configured with data
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("testKey", "testValue");
    when(sharedDataStore.getNamespaceData("testNamespace")).thenReturn(namespaceData);

    // When: UtilityFunctionsMapper is instantiated via constructor
    new UtilityFunctionsMapper(null, sharedDataStore, null);

    // Then: readShared() should work with the SharedDataStore set by constructor
    Object result = UtilityFunctionsMapper.readShared("testNamespace", "testKey");
    assertThat(result).isEqualTo("testValue");
    verify(sharedDataStore).getNamespaceData("testNamespace");
  }

  @Test
  void readShared_withUnicodeCharacters_shouldWork() {
    // Given: A SharedDataStore with Unicode characters
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("unicodeKey", "Hello 世界 🌍");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called
    Object result = UtilityFunctionsMapper.readShared("ns", "unicodeKey");

    // Then: Should return Unicode correctly
    assertThat(result).isEqualTo("Hello 世界 🌍");
  }

  @Test
  void readShared_withWhitespaceInKey_shouldWork() {
    // Given: A SharedDataStore with whitespace in key
    Map<String, Object> namespaceData = new HashMap<>();
    namespaceData.put("key with spaces", "value");
    when(sharedDataStore.getNamespaceData("ns")).thenReturn(namespaceData);
    UtilityFunctionsMapper.setSharedStateService(sharedDataStore);

    // When: readShared() is called with whitespace in key
    Object result = UtilityFunctionsMapper.readShared("ns", "key with spaces");

    // Then: Should return the value
    assertThat(result).isEqualTo("value");
  }
}
