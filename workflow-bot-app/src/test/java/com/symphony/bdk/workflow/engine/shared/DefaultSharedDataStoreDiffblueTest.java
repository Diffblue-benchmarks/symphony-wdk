package com.symphony.bdk.workflow.engine.shared;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultSharedDataStore.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DefaultSharedDataStoreDiffblueTest {
  @Autowired
  private DefaultSharedDataStore defaultSharedDataStore;

  @MockBean
  private SharedDataRepository sharedDataRepository;

  /**
   * Method under test: {@link DefaultSharedDataStore#getNamespaceData(String)}
   */
  @Test
  void testGetNamespaceData() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    HashMap<String, Object> properties = new HashMap<>();
    sharedData.setProperties(properties);
    Optional<SharedData> ofResult = Optional.of(sharedData);
    when(sharedDataRepository.findByNamespace(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    Map<String, Object> actualNamespaceData = defaultSharedDataStore.getNamespaceData("Namespace");

    // Assert
    verify(sharedDataRepository).findByNamespace(eq("Namespace"));
    assertTrue(actualNamespaceData.isEmpty());
    assertSame(properties, actualNamespaceData);
  }

  /**
   * Method under test: {@link DefaultSharedDataStore#getNamespaceData(String)}
   */
  @Test
  void testGetNamespaceData2() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    SharedData sharedData2 = mock(SharedData.class);
    when(sharedData2.namespace(Mockito.<String>any())).thenReturn(sharedData);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(sharedData2.getProperties()).thenReturn(stringObjectMap);
    doNothing().when(sharedData2).setId(Mockito.<String>any());
    doNothing().when(sharedData2).setLastUpdated(Mockito.<Long>any());
    doNothing().when(sharedData2).setNamespace(Mockito.<String>any());
    doNothing().when(sharedData2).setProperties(Mockito.<Map<String, Object>>any());
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());
    Optional<SharedData> ofResult = Optional.of(sharedData2);
    when(sharedDataRepository.findByNamespace(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    Map<String, Object> actualNamespaceData = defaultSharedDataStore.getNamespaceData("Namespace");

    // Assert
    verify(sharedData2).getProperties();
    verify(sharedData2).namespace(eq("Namespace"));
    verify(sharedData2).setId(eq("42"));
    verify(sharedData2).setLastUpdated(eq(1L));
    verify(sharedData2).setNamespace(eq("Namespace"));
    verify(sharedData2).setProperties(isA(Map.class));
    verify(sharedDataRepository).findByNamespace(eq("Namespace"));
    assertTrue(actualNamespaceData.isEmpty());
    assertSame(stringObjectMap, actualNamespaceData);
  }

  /**
   * Method under test:
   * {@link DefaultSharedDataStore#putNamespaceData(String, String, Object)}
   */
  @Test
  void testPutNamespaceData() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
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
    verify(sharedDataRepository).findByNamespace(eq("Namespace"));
    verify(sharedDataRepository).save(isA(SharedData.class));
  }

  /**
   * Method under test:
   * {@link DefaultSharedDataStore#putNamespaceData(String, String, Object)}
   */
  @Test
  void testPutNamespaceData2() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setId("42");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setId("42");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());
    SharedData sharedData3 = mock(SharedData.class);
    when(sharedData3.namespace(Mockito.<String>any())).thenReturn(sharedData2);
    when(sharedData3.getProperties()).thenReturn(new HashMap<>());
    doNothing().when(sharedData3).setId(Mockito.<String>any());
    doNothing().when(sharedData3).setLastUpdated(Mockito.<Long>any());
    doNothing().when(sharedData3).setNamespace(Mockito.<String>any());
    doNothing().when(sharedData3).setProperties(Mockito.<Map<String, Object>>any());
    sharedData3.namespace("Namespace");
    sharedData3.setId("42");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Optional<SharedData> ofResult = Optional.of(sharedData3);
    when(sharedDataRepository.save(Mockito.<SharedData>any())).thenReturn(sharedData);
    when(sharedDataRepository.findByNamespace(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    defaultSharedDataStore.putNamespaceData("Namespace", "Key", "Data");

    // Assert
    verify(sharedData3).getProperties();
    verify(sharedData3).namespace(eq("Namespace"));
    verify(sharedData3).setId(eq("42"));
    verify(sharedData3, atLeast(1)).setLastUpdated(Mockito.<Long>any());
    verify(sharedData3).setNamespace(eq("Namespace"));
    verify(sharedData3).setProperties(isA(Map.class));
    verify(sharedDataRepository).findByNamespace(eq("Namespace"));
    verify(sharedDataRepository).save(isA(SharedData.class));
  }
}
