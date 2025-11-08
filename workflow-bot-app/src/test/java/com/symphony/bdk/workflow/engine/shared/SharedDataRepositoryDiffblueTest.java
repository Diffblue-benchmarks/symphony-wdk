package com.symphony.bdk.workflow.engine.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SharedDataRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.symphony.bdk.workflow.engine.shared"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class SharedDataRepositoryDiffblueTest {
  @Autowired
  private SharedDataRepository sharedDataRepository;

  /**
   * Method under test: {@link SharedDataRepository#exists(Example)}
   */
  @Test
  void testExists() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act and Assert
    assertTrue(sharedDataRepository.exists(example));
  }

  /**
   * Method under test: {@link SharedDataRepository#findAllById(Iterable)}
   */
  @Test
  void testFindAllById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());

    SharedData sharedData4 = new SharedData();
    sharedData4.namespace("Namespace");
    sharedData4.setLastUpdated(1L);
    sharedData4.setNamespace("Namespace");
    sharedData4.setProperties(new HashMap<>());

    SharedData sharedData5 = new SharedData();
    sharedData5.namespace("Namespace");
    sharedData5.setLastUpdated(1L);
    sharedData5.setNamespace("Namespace");
    sharedData5.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);
    sharedDataRepository.save(sharedData3);
    sharedDataRepository.save(sharedData4);
    sharedDataRepository.save(sharedData5);
    Iterable<String> ids = mock(Iterable.class);

    ArrayList<String> stringList = new ArrayList<>();
    when(ids.iterator()).thenReturn(stringList.iterator());

    // Act
    List<SharedData> actualFindAllByIdResult = sharedDataRepository.findAllById(ids);

    // Assert
    verify(ids).iterator();
    assertTrue(actualFindAllByIdResult.isEmpty());
  }

  /**
   * Method under test: {@link SharedDataRepository#findBy(Example, Function)}
   */
  @Test
  void testFindBy() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);
    Function<FluentQuery.FetchableFluentQuery<SharedData>, Object> queryFunction = mock(Function.class);
    when(queryFunction.apply(Mockito.<FluentQuery.FetchableFluentQuery<SharedData>>any())).thenReturn("Apply");

    // Act
    Object actualFindByResult = sharedDataRepository.findBy(example, queryFunction);

    // Assert
    verify(queryFunction).apply(isA(FluentQuery.FetchableFluentQuery.class));
    assertEquals("Apply", actualFindByResult);
  }

  /**
   * Method under test: {@link SharedDataRepository#findById(Object)}
   */
  @Test
  void testFindById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act
    Optional<SharedData> actualFindByIdResult = sharedDataRepository.findById(sharedData3.getId());

    // Assert
    assertTrue(actualFindByIdResult.isPresent());
    assertSame(sharedData3, actualFindByIdResult.get());
  }

  /**
   * Method under test: {@link SharedDataRepository#findById(Object)}
   */
  @Test
  void testFindById2() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");

    HashMap<String, Object> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));
    sharedData.setProperties(properties);
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act
    Optional<SharedData> actualFindByIdResult = sharedDataRepository.findById(sharedData3.getId());

    // Assert
    assertTrue(actualFindByIdResult.isPresent());
    assertSame(sharedData3, actualFindByIdResult.get());
  }

  /**
   * Method under test: {@link SharedDataRepository#getById(Object)}
   */
  @Test
  void testGetById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertSame(sharedData3, sharedDataRepository.getById(sharedData3.getId()));
  }

  /**
   * Method under test: {@link SharedDataRepository#getById(Object)}
   */
  @Test
  void testGetById2() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");

    HashMap<String, Object> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));
    sharedData.setProperties(properties);
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertSame(sharedData3, sharedDataRepository.getById(sharedData3.getId()));
  }

  /**
   * Method under test: {@link SharedDataRepository#getOne(Object)}
   */
  @Test
  void testGetOne() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act and Assert
    assertEquals("42", sharedDataRepository.getOne("42").getId());
  }

  /**
   * Method under test: {@link SharedDataRepository#getOne(Object)}
   */
  @Test
  void testGetOne2() {
    // Arrange
    HashMap<String, Object> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));

    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(properties);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act and Assert
    assertEquals("42", sharedDataRepository.getOne("42").getId());
  }

  /**
   * Method under test: {@link SharedDataRepository#getReferenceById(Object)}
   */
  @Test
  void testGetReferenceById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertSame(sharedData3, sharedDataRepository.getReferenceById(sharedData3.getId()));
  }

  /**
   * Method under test: {@link SharedDataRepository#getReferenceById(Object)}
   */
  @Test
  void testGetReferenceById2() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");

    HashMap<String, Object> properties = new HashMap<>();
    properties.computeIfPresent("foo", mock(BiFunction.class));
    sharedData.setProperties(properties);
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertSame(sharedData3, sharedDataRepository.getReferenceById(sharedData3.getId()));
  }

  /**
   * Method under test: {@link SharedDataRepository#save(Object)}
   */
  @Test
  void testSave() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act and Assert
    assertSame(sharedData, sharedDataRepository.save(sharedData));
  }

  /**
   * Method under test: {@link SharedDataRepository#saveAll(Iterable)}
   */
  @Test
  void testSaveAll() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    List<SharedData> entities = Arrays.asList(sharedData, sharedData2, sharedData3);

    // Act and Assert
    assertEquals(entities, sharedDataRepository.saveAll(entities));
  }

  /**
   * Method under test: {@link SharedDataRepository#saveAndFlush(Object)}
   */
  @Test
  void testSaveAndFlush() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act and Assert
    assertSame(sharedData, sharedDataRepository.saveAndFlush(sharedData));
  }
}
