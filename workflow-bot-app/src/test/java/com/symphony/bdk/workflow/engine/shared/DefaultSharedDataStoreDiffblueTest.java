package com.symphony.bdk.workflow.engine.shared;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultSharedDataStore.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DefaultSharedDataStoreDiffblueTest {
  @Autowired private DefaultSharedDataStore defaultSharedDataStore;

  @MockBean private SharedDataRepository sharedDataRepository;

  /**
   * Test {@link DefaultSharedDataStore#getNamespaceData(String)}.
   *
   * <p>Method under test: {@link DefaultSharedDataStore#getNamespaceData(String)}
   */
  @Test
  @DisplayName("Test getNamespaceData(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map DefaultSharedDataStore.getNamespaceData(String)"})
  void testGetNamespaceData() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    Optional<SharedData> ofResult = Optional.of(sharedData);
    when(sharedDataRepository.findByNamespace(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    Map<String, Object> actualNamespaceData = defaultSharedDataStore.getNamespaceData("Namespace");

    // Assert
    verify(sharedDataRepository).findByNamespace("Namespace");
    assertTrue(actualNamespaceData.isEmpty());
  }

  /**
   * Test {@link DefaultSharedDataStore#putNamespaceData(String, String, Object)}.
   *
   * <p>Method under test: {@link DefaultSharedDataStore#putNamespaceData(String, String, Object)}
   */
  @Test
  @DisplayName("Test putNamespaceData(String, String, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultSharedDataStore.putNamespaceData(String, String, Object)"})
  void testPutNamespaceData() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());
    Optional<SharedData> ofResult = Optional.of(sharedData2);
    when(sharedDataRepository.save(Mockito.<SharedData>any())).thenReturn(sharedData);
    when(sharedDataRepository.findByNamespace(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    defaultSharedDataStore.putNamespaceData("Namespace", "Key", "Data");

    // Assert
    verify(sharedDataRepository).findByNamespace("Namespace");
    verify(sharedDataRepository).save(isA(SharedData.class));
  }

  /**
   * Test {@link DefaultSharedDataStore#getNamespaceData(String)} when namespace not found.
   *
   * <p>Method under test: {@link DefaultSharedDataStore#getNamespaceData(String)}
   */
  @Test
  @DisplayName("Test getNamespaceData(String) when namespace not found returns empty map")
  void testGetNamespaceData_whenNamespaceNotFound() {
    // Arrange
    when(sharedDataRepository.findByNamespace(Mockito.<String>any())).thenReturn(Optional.empty());

    // Act
    Map<String, Object> actualNamespaceData = defaultSharedDataStore.getNamespaceData("UnknownNamespace");

    // Assert
    verify(sharedDataRepository).findByNamespace("UnknownNamespace");
    assertNotNull(actualNamespaceData);
    assertTrue(actualNamespaceData.isEmpty());
  }

  /**
   * Test {@link DefaultSharedDataStore#putNamespaceData(String, String, Object)} when namespace not found.
   *
   * <p>Method under test: {@link DefaultSharedDataStore#putNamespaceData(String, String, Object)}
   */
  @Test
  @DisplayName("Test putNamespaceData(String, String, Object) when namespace not found creates new entry")
  void testPutNamespaceData_whenNamespaceNotFound() {
    // Arrange
    SharedData savedSharedData = new SharedData();
    savedSharedData.setId("42");
    savedSharedData.setLastUpdated(1L);
    savedSharedData.setNamespace("NewNamespace");
    savedSharedData.setProperties(new HashMap<>());
    when(sharedDataRepository.save(Mockito.<SharedData>any())).thenReturn(savedSharedData);
    when(sharedDataRepository.findByNamespace(Mockito.<String>any())).thenReturn(Optional.empty());

    // Act
    defaultSharedDataStore.putNamespaceData("NewNamespace", "Key", "Data");

    // Assert
    verify(sharedDataRepository).findByNamespace("NewNamespace");
    verify(sharedDataRepository).save(isA(SharedData.class));
  }
}
