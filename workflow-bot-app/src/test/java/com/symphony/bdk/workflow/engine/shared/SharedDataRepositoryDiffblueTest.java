package com.symphony.bdk.workflow.engine.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SharedDataRepository.class})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.symphony.bdk.workflow.engine.shared"})
class SharedDataRepositoryDiffblueTest {
  @Autowired private SharedDataRepository sharedDataRepository;

  /**
   * Test {@link SharedDataRepository#findByNamespace(String)}.
   *
   * <p>Method under test: {@link SharedDataRepository#findByNamespace(String)}
   */
  @Test
  @DisplayName("Test findByNamespace(String)")
  @Tag("MaintainedByDiffblue")
  void testFindByNamespace() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act
    Optional<SharedData> actualFindByNamespaceResult =
        sharedDataRepository.findByNamespace("Namespace");

    // Assert
    assertTrue(actualFindByNamespaceResult.isPresent());
    assertSame(sharedData, actualFindByNamespaceResult.get());
  }

  /**
   * Test {@link SharedDataRepository#count()}.
   *
   * <p>Method under test: {@link SharedDataRepository#count()}
   */
  @Test
  @DisplayName("Test count()")
  @Tag("MaintainedByDiffblue")
  void testCount() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act and Assert
    assertEquals(2L, sharedDataRepository.count());
  }

  /**
   * Test {@link SharedDataRepository#count(Example)} with {@code Example}.
   *
   * <p>Method under test: {@link SharedDataRepository#count(Example)}
   */
  @Test
  @DisplayName("Test count(Example) with 'Example'")
  @Tag("MaintainedByDiffblue")
  void testCountWithExample() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act and Assert
    assertEquals(1L, sharedDataRepository.count(example));
  }

  /**
   * Test {@link SharedDataRepository#deleteAll()}.
   *
   * <p>Method under test: {@link SharedDataRepository#deleteAll()}
   */
  @Test
  @DisplayName("Test deleteAll()")
  @Tag("MaintainedByDiffblue")
  void testDeleteAll() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act
    sharedDataRepository.deleteAll();

    // Assert
    assertTrue(sharedDataRepository.findAll().isEmpty());
  }

  /**
   * Test {@link SharedDataRepository#deleteAllByIdInBatch(Iterable)}.
   *
   * <p>Method under test: {@link SharedDataRepository#deleteAllByIdInBatch(Iterable)}
   */
  @Test
  @DisplayName("Test deleteAllByIdInBatch(Iterable)")
  @Tag("MaintainedByDiffblue")
  void testDeleteAllByIdInBatch() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act
    sharedDataRepository.deleteAllByIdInBatch(new ArrayList<>());

    // Assert
    List<SharedData> findAllResult = sharedDataRepository.findAll();
    assertEquals(2, findAllResult.size());
    assertSame(sharedData, findAllResult.get(0));
    assertSame(sharedData2, findAllResult.get(1));
  }

  /**
   * Test {@link SharedDataRepository#deleteAllInBatch()}.
   *
   * <p>Method under test: {@link SharedDataRepository#deleteAllInBatch()}
   */
  @Test
  @DisplayName("Test deleteAllInBatch()")
  @Tag("MaintainedByDiffblue")
  void testDeleteAllInBatch() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act
    sharedDataRepository.deleteAllInBatch();

    // Assert
    assertTrue(sharedDataRepository.findAll().isEmpty());
  }

  /**
   * Test {@link SharedDataRepository#existsById(Object)}.
   *
   * <ul>
   *   <li>Given {@link SharedData} (default constructor) Namespace is empty string.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SharedDataRepository#existsById(Object)}
   */
  @Test
  @DisplayName(
      "Test existsById(Object); given SharedData (default constructor) Namespace is empty string; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testExistsById_givenSharedDataNamespaceIsEmptyString_thenReturnTrue() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertTrue(sharedDataRepository.existsById(sharedData3.getId()));
  }

  /**
   * Test {@link SharedDataRepository#exists(Example)}.
   *
   * <ul>
   *   <li>Given {@link SharedData} (default constructor) LastUpdated is one.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SharedDataRepository#exists(Example)}
   */
  @Test
  @DisplayName(
      "Test exists(Example); given SharedData (default constructor) LastUpdated is one; then return 'true'")
  @Tag("MaintainedByDiffblue")
  void testExists_givenSharedDataLastUpdatedIsOne_thenReturnTrue() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act and Assert
    assertTrue(sharedDataRepository.exists(example));
  }

  /**
   * Test {@link SharedDataRepository#exists(Example)}.
   *
   * <ul>
   *   <li>Given {@link SharedData} (default constructor) LastUpdated is zero.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SharedDataRepository#exists(Example)}
   */
  @Test
  @DisplayName(
      "Test exists(Example); given SharedData (default constructor) LastUpdated is zero; then return 'false'")
  @Tag("MaintainedByDiffblue")
  void testExists_givenSharedDataLastUpdatedIsZero_thenReturnFalse() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(0L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act and Assert
    assertFalse(sharedDataRepository.exists(example));
  }

  /**
   * Test {@link SharedDataRepository#findAll()}.
   *
   * <p>Method under test: {@link SharedDataRepository#findAll()}
   */
  @Test
  @DisplayName("Test findAll()")
  @Tag("MaintainedByDiffblue")
  void testFindAll() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act
    List<SharedData> actualFindAllResult = sharedDataRepository.findAll();

    // Assert
    assertEquals(2, actualFindAllResult.size());
    assertSame(sharedData, actualFindAllResult.get(0));
    assertSame(sharedData2, actualFindAllResult.get(1));
  }

  /**
   * Test {@link SharedDataRepository#findAll(Example)} with {@code example}.
   *
   * <p>Method under test: {@link SharedDataRepository#findAll(Example)}
   */
  @Test
  @DisplayName("Test findAll(Example) with 'example'")
  @Tag("MaintainedByDiffblue")
  void testFindAllWithExample() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act
    List<SharedData> actualFindAllResult = sharedDataRepository.findAll(example);

    // Assert
    assertEquals(1, actualFindAllResult.size());
    assertSame(sharedData, actualFindAllResult.get(0));
  }

  /**
   * Test {@link SharedDataRepository#findAll(Example, Pageable)} with {@code example}, {@code
   * pageable}.
   *
   * <p>Method under test: {@link SharedDataRepository#findAll(Example, Pageable)}
   */
  @Test
  @DisplayName("Test findAll(Example, Pageable) with 'example', 'pageable'")
  @Tag("MaintainedByDiffblue")
  void testFindAllWithExamplePageable() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act
    Page<SharedData> actualFindAllResult =
        sharedDataRepository.findAll(example, Pageable.unpaged());

    // Assert
    assertTrue(actualFindAllResult instanceof PageImpl);
    List<SharedData> toListResult = actualFindAllResult.toList();
    assertEquals(1, toListResult.size());
    assertSame(sharedData, toListResult.get(0));
  }

  /**
   * Test {@link SharedDataRepository#findAll(Example, Sort)} with {@code example}, {@code sort}.
   *
   * <ul>
   *   <li>When unsorted.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link SharedDataRepository#findAll(Example, Sort)}
   */
  @Test
  @DisplayName(
      "Test findAll(Example, Sort) with 'example', 'sort'; when unsorted; then return size is one")
  @Tag("MaintainedByDiffblue")
  void testFindAllWithExampleSort_whenUnsorted_thenReturnSizeIsOne() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act
    List<SharedData> actualFindAllResult = sharedDataRepository.findAll(example, Sort.unsorted());

    // Assert
    assertEquals(1, actualFindAllResult.size());
    assertSame(sharedData, actualFindAllResult.get(0));
  }

  /**
   * Test {@link SharedDataRepository#findAll(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link SharedDataRepository#findAll(Pageable)}
   */
  @Test
  @DisplayName("Test findAll(Pageable) with 'pageable'")
  @Tag("MaintainedByDiffblue")
  void testFindAllWithPageable() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act
    Page<SharedData> actualFindAllResult = sharedDataRepository.findAll(Pageable.unpaged());

    // Assert
    assertTrue(actualFindAllResult instanceof PageImpl);
    List<SharedData> toListResult = actualFindAllResult.toList();
    assertEquals(2, toListResult.size());
    assertSame(sharedData, toListResult.get(0));
    assertSame(sharedData2, toListResult.get(1));
  }

  /**
   * Test {@link SharedDataRepository#findAll(Sort)} with {@code sort}.
   *
   * <ul>
   *   <li>When unsorted.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link SharedDataRepository#findAll(Sort)}
   */
  @Test
  @DisplayName("Test findAll(Sort) with 'sort'; when unsorted; then return size is two")
  @Tag("MaintainedByDiffblue")
  void testFindAllWithSort_whenUnsorted_thenReturnSizeIsTwo() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act
    List<SharedData> actualFindAllResult = sharedDataRepository.findAll(Sort.unsorted());

    // Assert
    assertEquals(2, actualFindAllResult.size());
    assertSame(sharedData, actualFindAllResult.get(0));
    assertSame(sharedData2, actualFindAllResult.get(1));
  }

  /**
   * Test {@link SharedDataRepository#findById(Object)}.
   *
   * <p>Method under test: {@link SharedDataRepository#findById(Object)}
   */
  @Test
  @DisplayName("Test findById(Object)")
  @Tag("MaintainedByDiffblue")
  void testFindById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
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
   * Test {@link SharedDataRepository#findOne(Example)}.
   *
   * <p>Method under test: {@link SharedDataRepository#findOne(Example)}
   */
  @Test
  @DisplayName("Test findOne(Example)")
  @Tag("MaintainedByDiffblue")
  void testFindOne() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act
    Optional<SharedData> actualFindOneResult = sharedDataRepository.findOne(example);

    // Assert
    assertTrue(actualFindOneResult.isPresent());
    assertSame(sharedData, actualFindOneResult.get());
  }

  /**
   * Test {@link SharedDataRepository#flush()}.
   *
   * <p>Method under test: {@link SharedDataRepository#flush()}
   */
  @Test
  @DisplayName("Test flush()")
  @Tag("MaintainedByDiffblue")
  void testFlush() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act
    sharedDataRepository.flush();

    // Assert
    List<SharedData> findAllResult = sharedDataRepository.findAll();
    assertEquals(2, findAllResult.size());
    assertSame(sharedData, findAllResult.get(0));
    assertSame(sharedData2, findAllResult.get(1));
  }

  /**
   * Test {@link SharedDataRepository#getById(Object)}.
   *
   * <p>Method under test: {@link SharedDataRepository#getById(Object)}
   */
  @Test
  @DisplayName("Test getById(Object)")
  @Tag("MaintainedByDiffblue")
  void testGetById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertSame(sharedData3, sharedDataRepository.getById(sharedData3.getId()));
  }

  /**
   * Test {@link SharedDataRepository#getOne(Object)}.
   *
   * <p>Method under test: {@link SharedDataRepository#getOne(Object)}
   */
  @Test
  @DisplayName("Test getOne(Object)")
  @Tag("MaintainedByDiffblue")
  void testGetOne() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act and Assert
    assertEquals("42", sharedDataRepository.getOne("42").getId());
  }

  /**
   * Test {@link SharedDataRepository#getReferenceById(Object)}.
   *
   * <p>Method under test: {@link SharedDataRepository#getReferenceById(Object)}
   */
  @Test
  @DisplayName("Test getReferenceById(Object)")
  @Tag("MaintainedByDiffblue")
  void testGetReferenceById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertSame(sharedData3, sharedDataRepository.getReferenceById(sharedData3.getId()));
  }

  /**
   * Test {@link SharedDataRepository#save(Object)}.
   *
   * <p>Method under test: {@link SharedDataRepository#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object)")
  @Tag("MaintainedByDiffblue")
  void testSave() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act
    SharedData actualSaveResult = sharedDataRepository.save(sharedData);

    // Assert
    assertSame(sharedData, actualSaveResult);
  }

  /**
   * Test {@link SharedDataRepository#saveAll(Iterable)}.
   *
   * <p>Method under test: {@link SharedDataRepository#saveAll(Iterable)}
   */
  @Test
  @DisplayName("Test saveAll(Iterable)")
  @Tag("MaintainedByDiffblue")
  void testSaveAll() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    SharedData sharedData3 = new SharedData();
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());

    List<SharedData> entities = Arrays.asList(sharedData, sharedData2, sharedData3);

    // Act
    List<SharedData> actualSaveAllResult = sharedDataRepository.saveAll(entities);

    // Assert
    assertEquals(entities, actualSaveAllResult);
  }

  /**
   * Test {@link SharedDataRepository#saveAndFlush(Object)}.
   *
   * <p>Method under test: {@link SharedDataRepository#saveAndFlush(Object)}
   */
  @Test
  @DisplayName("Test saveAndFlush(Object)")
  @Tag("MaintainedByDiffblue")
  void testSaveAndFlush() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act
    SharedData actualSaveAndFlushResult = sharedDataRepository.saveAndFlush(sharedData);

    // Assert
    assertSame(sharedData, actualSaveAndFlushResult);
  }
}
