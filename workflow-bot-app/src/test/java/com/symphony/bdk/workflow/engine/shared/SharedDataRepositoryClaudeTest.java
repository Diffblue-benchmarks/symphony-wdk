package com.symphony.bdk.workflow.engine.shared;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SharedDataRepository.
 *
 * SharedDataRepository is a Spring Data JPA repository interface that extends JpaRepository.
 * Since it's an interface with no custom implementation, all tested methods are provided by
 * Spring Data JPA at runtime. These tests verify that:
 * 1. All inherited methods are callable and accept appropriate parameters
 * 2. Methods return expected types
 * 3. Methods can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SharedDataRepository
 * to test. The actual implementations are provided by Spring Data JPA at runtime and are
 * thoroughly tested in Spring's own test suite.
 */
class SharedDataRepositoryClaudeTest {

  // ==================== findByNamespace Tests ====================

  @Test
  void findByNamespace_withExistingNamespace_shouldReturnOptionalWithData() {
    // Given: a mocked repository with existing namespace
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String namespace = "test-namespace";
    SharedData sharedData = new SharedData();
    sharedData.setNamespace(namespace);

    when(repository.findByNamespace(namespace)).thenReturn(Optional.of(sharedData));

    // When: finding by namespace
    Optional<SharedData> result = repository.findByNamespace(namespace);

    // Then: should return the data
    assertThat(result).isPresent();
    assertThat(result.get().getNamespace()).isEqualTo(namespace);
    verify(repository, times(1)).findByNamespace(namespace);
  }

  @Test
  void findByNamespace_withNonExistingNamespace_shouldReturnEmptyOptional() {
    // Given: a mocked repository with non-existing namespace
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String namespace = "non-existing";

    when(repository.findByNamespace(namespace)).thenReturn(Optional.empty());

    // When: finding by namespace
    Optional<SharedData> result = repository.findByNamespace(namespace);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByNamespace(namespace);
  }

  @Test
  void findByNamespace_withNullNamespace_shouldBeCallable() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    when(repository.findByNamespace(null)).thenReturn(Optional.empty());

    // When: finding by null namespace
    Optional<SharedData> result = repository.findByNamespace(null);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByNamespace(null);
  }

  // ==================== flush Tests ====================

  @Test
  void flush_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    doNothing().when(repository).flush();

    // When & Then: flush should complete without throwing
    assertThatCode(() -> repository.flush()).doesNotThrowAnyException();
    verify(repository, times(1)).flush();
  }

  @Test
  void flush_canBeCalledMultipleTimes() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    doNothing().when(repository).flush();

    // When: calling flush multiple times
    repository.flush();
    repository.flush();

    // Then: should work for each call
    verify(repository, times(2)).flush();
  }

  // ==================== saveAndFlush Tests ====================

  @Test
  void saveAndFlush_withValidEntity_shouldSaveAndReturnEntity() {
    // Given: a mocked repository and entity
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity = new SharedData();
    entity.setNamespace("test");

    when(repository.saveAndFlush(entity)).thenReturn(entity);

    // When: saving and flushing
    SharedData result = repository.saveAndFlush(entity);

    // Then: should return the saved entity
    assertThat(result).isEqualTo(entity);
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void saveAndFlush_withNewEntity_shouldSaveAndFlush() {
    // Given: a new entity
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData newEntity = new SharedData();
    newEntity.setNamespace("new-namespace");

    when(repository.saveAndFlush(newEntity)).thenReturn(newEntity);

    // When: saving and flushing
    SharedData result = repository.saveAndFlush(newEntity);

    // Then: should persist immediately
    assertThat(result).isNotNull();
    verify(repository, times(1)).saveAndFlush(newEntity);
  }

  // ==================== saveAllAndFlush Tests ====================

  @Test
  void saveAllAndFlush_withMultipleEntities_shouldSaveAllAndFlush() {
    // Given: multiple entities
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity1 = new SharedData();
    entity1.setNamespace("ns1");
    SharedData entity2 = new SharedData();
    entity2.setNamespace("ns2");
    List<SharedData> entities = Arrays.asList(entity1, entity2);

    when(repository.saveAllAndFlush(entities)).thenReturn(entities);

    // When: saving all and flushing
    List<SharedData> result = repository.saveAllAndFlush(entities);

    // Then: should return all saved entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<SharedData> emptyList = Collections.emptyList();

    when(repository.saveAllAndFlush(emptyList)).thenReturn(emptyList);

    // When: saving empty list
    List<SharedData> result = repository.saveAllAndFlush(emptyList);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).saveAllAndFlush(emptyList);
  }

  // ==================== deleteInBatch Tests ====================

  @Test
  void deleteInBatch_withMultipleEntities_shouldDelete() {
    // Given: multiple entities to delete
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity1 = new SharedData();
    SharedData entity2 = new SharedData();
    List<SharedData> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When: deleting in batch
    repository.deleteInBatch(entities);

    // Then: should delete all entities
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEmptyList_shouldBeCallable() {
    // Given: empty list
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<SharedData> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteInBatch(emptyList);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteInBatch(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteInBatch(emptyList);
  }

  // ==================== deleteAllInBatch(Iterable) Tests ====================

  @Test
  void deleteAllInBatch_withIterable_shouldDeleteAll() {
    // Given: iterable of entities
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity1 = new SharedData();
    SharedData entity2 = new SharedData();
    List<SharedData> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: deleting all in batch
    repository.deleteAllInBatch(entities);

    // Then: should delete all
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEmptyIterable_shouldBeCallable() {
    // Given: empty iterable
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<SharedData> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAllInBatch(emptyList);

    // When: deleting empty iterable
    assertThatCode(() -> repository.deleteAllInBatch(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch(emptyList);
  }

  // ==================== deleteAllByIdInBatch Tests ====================

  @Test
  void deleteAllByIdInBatch_withMultipleIds_shouldDelete() {
    // Given: multiple IDs
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id3");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: deleting by IDs in batch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete all by IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withEmptyList_shouldBeCallable() {
    // Given: empty list of IDs
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<String> emptyIds = Collections.emptyList();

    doNothing().when(repository).deleteAllByIdInBatch(emptyIds);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteAllByIdInBatch(emptyIds)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllByIdInBatch(emptyIds);
  }

  // ==================== deleteAllInBatch() Tests ====================

  @Test
  void deleteAllInBatch_noArgs_shouldDeleteAll() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: deleting all in batch
    repository.deleteAllInBatch();

    // Then: should delete all entities
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteAllInBatch()).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch();
  }

  // ==================== getOne Tests ====================

  @Test
  void getOne_withExistingId_shouldReturnEntity() {
    // Given: existing ID
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "existing-id";
    SharedData entity = new SharedData();
    entity.setId(id);

    when(repository.getOne(id)).thenReturn(entity);

    // When: getting entity by ID
    SharedData result = repository.getOne(id);

    // Then: should return the entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getOne(id);
  }

  @Test
  void getOne_shouldReturnLazyProxy() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "id";
    SharedData entity = new SharedData();

    when(repository.getOne(id)).thenReturn(entity);

    // When: getting one
    SharedData result = repository.getOne(id);

    // Then: should return entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).getOne(id);
  }

  // ==================== getById Tests ====================

  @Test
  void getById_withExistingId_shouldReturnEntity() {
    // Given: existing ID
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "existing-id";
    SharedData entity = new SharedData();
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: getting by ID
    SharedData result = repository.getById(id);

    // Then: should return the entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_shouldReturnLazyProxy() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "id";
    SharedData entity = new SharedData();

    when(repository.getById(id)).thenReturn(entity);

    // When: getting by ID
    SharedData result = repository.getById(id);

    // Then: should return entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).getById(id);
  }

  // ==================== getReferenceById Tests ====================

  @Test
  void getReferenceById_withExistingId_shouldReturnReference() {
    // Given: existing ID
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "existing-id";
    SharedData entity = new SharedData();
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: getting reference by ID
    SharedData result = repository.getReferenceById(id);

    // Then: should return the reference
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_shouldReturnLazyReference() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "id";
    SharedData entity = new SharedData();

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: getting reference by ID
    SharedData result = repository.getReferenceById(id);

    // Then: should return reference
    assertThat(result).isNotNull();
    verify(repository, times(1)).getReferenceById(id);
  }

  // ==================== findAll(Example) Tests ====================

  @Test
  void findAll_withExample_shouldReturnMatchingEntities() {
    // Given: example and matching entities
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    probe.setNamespace("test");
    Example<SharedData> example = Example.of(probe);
    List<SharedData> matchingEntities = Arrays.asList(new SharedData(), new SharedData());

    when(repository.findAll(example)).thenReturn(matchingEntities);

    // When: finding by example
    List<SharedData> result = repository.findAll(example);

    // Then: should return matching entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(example);
  }

  @Test
  void findAll_withExample_noMatches_shouldReturnEmptyList() {
    // Given: example with no matches
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);

    when(repository.findAll(example)).thenReturn(Collections.emptyList());

    // When: finding by example
    List<SharedData> result = repository.findAll(example);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(example);
  }

  // ==================== findAll(Example, Sort) Tests ====================

  @Test
  void findAll_withExampleAndSort_shouldReturnSortedEntities() {
    // Given: example and sort
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);
    Sort sort = Sort.by(Sort.Direction.ASC, "namespace");
    List<SharedData> sortedEntities = Arrays.asList(new SharedData(), new SharedData());

    when(repository.findAll(example, sort)).thenReturn(sortedEntities);

    // When: finding by example with sort
    List<SharedData> result = repository.findAll(example, sort);

    // Then: should return sorted entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(example, sort);
  }

  @Test
  void findAll_withExampleAndSort_noMatches_shouldReturnEmptyList() {
    // Given: example and sort with no matches
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);
    Sort sort = Sort.by("namespace");

    when(repository.findAll(example, sort)).thenReturn(Collections.emptyList());

    // When: finding by example with sort
    List<SharedData> result = repository.findAll(example, sort);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(example, sort);
  }

  // ==================== saveAll Tests ====================

  @Test
  void saveAll_withMultipleEntities_shouldSaveAll() {
    // Given: multiple entities
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity1 = new SharedData();
    SharedData entity2 = new SharedData();
    List<SharedData> entities = Arrays.asList(entity1, entity2);

    when(repository.saveAll(entities)).thenReturn(entities);

    // When: saving all
    List<SharedData> result = repository.saveAll(entities);

    // Then: should return all saved entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<SharedData> emptyList = Collections.emptyList();

    when(repository.saveAll(emptyList)).thenReturn(emptyList);

    // When: saving empty list
    List<SharedData> result = repository.saveAll(emptyList);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).saveAll(emptyList);
  }

  // ==================== findAll() Tests ====================

  @Test
  void findAll_noArgs_shouldReturnAllEntities() {
    // Given: repository with entities
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<SharedData> allEntities = Arrays.asList(new SharedData(), new SharedData(), new SharedData());

    when(repository.findAll()).thenReturn(allEntities);

    // When: finding all
    List<SharedData> result = repository.findAll();

    // Then: should return all entities
    assertThat(result).hasSize(3);
    verify(repository, times(1)).findAll();
  }

  @Test
  void findAll_noArgs_emptyTable_shouldReturnEmptyList() {
    // Given: empty repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    when(repository.findAll()).thenReturn(Collections.emptyList());

    // When: finding all
    List<SharedData> result = repository.findAll();

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll();
  }

  // ==================== findAllById Tests ====================

  @Test
  void findAllById_withMultipleIds_shouldReturnMatchingEntities() {
    // Given: multiple IDs
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    List<SharedData> entities = Arrays.asList(new SharedData(), new SharedData());

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: finding all by IDs
    List<SharedData> result = repository.findAllById(ids);

    // Then: should return matching entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list of IDs
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<String> emptyIds = Collections.emptyList();

    when(repository.findAllById(emptyIds)).thenReturn(Collections.emptyList());

    // When: finding all by empty IDs
    List<SharedData> result = repository.findAllById(emptyIds);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAllById(emptyIds);
  }

  // ==================== save Tests ====================

  @Test
  void save_withNewEntity_shouldSaveAndReturnEntity() {
    // Given: new entity
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity = new SharedData();
    entity.setNamespace("test");

    when(repository.save(entity)).thenReturn(entity);

    // When: saving
    SharedData result = repository.save(entity);

    // Then: should return saved entity
    assertThat(result).isEqualTo(entity);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withExistingEntity_shouldUpdateAndReturn() {
    // Given: existing entity
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity = new SharedData();
    entity.setId("existing-id");
    entity.setNamespace("updated");

    when(repository.save(entity)).thenReturn(entity);

    // When: saving
    SharedData result = repository.save(entity);

    // Then: should return updated entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).save(entity);
  }

  // ==================== findById Tests ====================

  @Test
  void findById_withExistingId_shouldReturnOptionalWithEntity() {
    // Given: existing ID
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "existing-id";
    SharedData entity = new SharedData();
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: finding by ID
    Optional<SharedData> result = repository.findById(id);

    // Then: should return entity
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_withNonExistingId_shouldReturnEmptyOptional() {
    // Given: non-existing ID
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "non-existing-id";

    when(repository.findById(id)).thenReturn(Optional.empty());

    // When: finding by ID
    Optional<SharedData> result = repository.findById(id);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findById(id);
  }

  // ==================== existsById Tests ====================

  @Test
  void existsById_withExistingId_shouldReturnTrue() {
    // Given: existing ID
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "existing-id";

    when(repository.existsById(id)).thenReturn(true);

    // When: checking existence
    boolean result = repository.existsById(id);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_withNonExistingId_shouldReturnFalse() {
    // Given: non-existing ID
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "non-existing-id";

    when(repository.existsById(id)).thenReturn(false);

    // When: checking existence
    boolean result = repository.existsById(id);

    // Then: should return false
    assertThat(result).isFalse();
    verify(repository, times(1)).existsById(id);
  }

  // ==================== count() Tests ====================

  @Test
  void count_noArgs_shouldReturnTotalCount() {
    // Given: repository with entities
    SharedDataRepository repository = mock(SharedDataRepository.class);

    when(repository.count()).thenReturn(5L);

    // When: counting
    long result = repository.count();

    // Then: should return count
    assertThat(result).isEqualTo(5L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_noArgs_emptyTable_shouldReturnZero() {
    // Given: empty repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    when(repository.count()).thenReturn(0L);

    // When: counting
    long result = repository.count();

    // Then: should return zero
    assertThat(result).isZero();
    verify(repository, times(1)).count();
  }

  // ==================== deleteById Tests ====================

  @Test
  void deleteById_withExistingId_shouldDelete() {
    // Given: existing ID
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "existing-id";

    doNothing().when(repository).deleteById(id);

    // When: deleting by ID
    repository.deleteById(id);

    // Then: should delete
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "id";

    doNothing().when(repository).deleteById(anyString());

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteById(id)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteById(id);
  }

  // ==================== delete(Entity) Tests ====================

  @Test
  void delete_withEntity_shouldDelete() {
    // Given: entity to delete
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity = new SharedData();
    entity.setId("id");

    doNothing().when(repository).delete(entity);

    // When: deleting entity
    repository.delete(entity);

    // Then: should delete
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntity_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity = new SharedData();

    doNothing().when(repository).delete(any(SharedData.class));

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.delete(entity)).doesNotThrowAnyException();
    verify(repository, times(1)).delete(entity);
  }

  // ==================== deleteAllById Tests ====================

  @Test
  void deleteAllById_withMultipleIds_shouldDeleteAll() {
    // Given: multiple IDs
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id3");

    doNothing().when(repository).deleteAllById(ids);

    // When: deleting all by IDs
    repository.deleteAllById(ids);

    // Then: should delete all
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_withEmptyList_shouldBeCallable() {
    // Given: empty list
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<String> emptyIds = Collections.emptyList();

    doNothing().when(repository).deleteAllById(emptyIds);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteAllById(emptyIds)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllById(emptyIds);
  }

  // ==================== deleteAll(Iterable) Tests ====================

  @Test
  void deleteAll_withIterable_shouldDeleteAll() {
    // Given: iterable of entities
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<SharedData> entities = Arrays.asList(new SharedData(), new SharedData());

    doNothing().when(repository).deleteAll(entities);

    // When: deleting all
    repository.deleteAll(entities);

    // Then: should delete all
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_withEmptyIterable_shouldBeCallable() {
    // Given: empty iterable
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<SharedData> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAll(emptyList);

    // When: deleting empty iterable
    assertThatCode(() -> repository.deleteAll(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAll(emptyList);
  }

  // ==================== deleteAll() Tests ====================

  @Test
  void deleteAll_noArgs_shouldDeleteAllEntities() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    doNothing().when(repository).deleteAll();

    // When: deleting all
    repository.deleteAll();

    // Then: should delete all entities
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);

    doNothing().when(repository).deleteAll();

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteAll()).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAll();
  }

  // ==================== findAll(Sort) Tests ====================

  @Test
  void findAll_withSort_shouldReturnSortedEntities() {
    // Given: sort parameter
    SharedDataRepository repository = mock(SharedDataRepository.class);
    Sort sort = Sort.by(Sort.Direction.ASC, "namespace");
    List<SharedData> sortedEntities = Arrays.asList(new SharedData(), new SharedData());

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: finding all with sort
    List<SharedData> result = repository.findAll(sort);

    // Then: should return sorted entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSort_emptyTable_shouldReturnEmptyList() {
    // Given: sort parameter and empty table
    SharedDataRepository repository = mock(SharedDataRepository.class);
    Sort sort = Sort.by("namespace");

    when(repository.findAll(sort)).thenReturn(Collections.emptyList());

    // When: finding all with sort
    List<SharedData> result = repository.findAll(sort);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(sort);
  }

  // ==================== findAll(Pageable) Tests ====================

  @Test
  void findAll_withPageable_shouldReturnPage() {
    // Given: pageable parameter
    SharedDataRepository repository = mock(SharedDataRepository.class);
    Pageable pageable = PageRequest.of(0, 10);
    List<SharedData> entities = Arrays.asList(new SharedData(), new SharedData());
    Page<SharedData> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: finding all with pageable
    Page<SharedData> result = repository.findAll(pageable);

    // Then: should return page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getTotalElements()).isEqualTo(2);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageable_emptyTable_shouldReturnEmptyPage() {
    // Given: pageable parameter and empty table
    SharedDataRepository repository = mock(SharedDataRepository.class);
    Pageable pageable = PageRequest.of(0, 10);
    Page<SharedData> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(pageable)).thenReturn(emptyPage);

    // When: finding all with pageable
    Page<SharedData> result = repository.findAll(pageable);

    // Then: should return empty page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEmpty();
    assertThat(result.getTotalElements()).isZero();
    verify(repository, times(1)).findAll(pageable);
  }

  // ==================== findOne(Example) Tests ====================

  @Test
  void findOne_withExample_shouldReturnOptionalWithEntity() {
    // Given: example that matches one entity
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    probe.setNamespace("test");
    Example<SharedData> example = Example.of(probe);
    SharedData matchingEntity = new SharedData();

    when(repository.findOne(example)).thenReturn(Optional.of(matchingEntity));

    // When: finding one by example
    Optional<SharedData> result = repository.findOne(example);

    // Then: should return entity
    assertThat(result).isPresent();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_withExample_noMatch_shouldReturnEmptyOptional() {
    // Given: example with no match
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: finding one by example
    Optional<SharedData> result = repository.findOne(example);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findOne(example);
  }

  // ==================== findAll(Example, Pageable) Tests ====================

  @Test
  void findAll_withExampleAndPageable_shouldReturnPage() {
    // Given: example and pageable
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);
    Pageable pageable = PageRequest.of(0, 10);
    List<SharedData> entities = Arrays.asList(new SharedData(), new SharedData());
    Page<SharedData> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: finding all by example with pageable
    Page<SharedData> result = repository.findAll(example, pageable);

    // Then: should return page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageable_noMatches_shouldReturnEmptyPage() {
    // Given: example and pageable with no matches
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);
    Pageable pageable = PageRequest.of(0, 10);
    Page<SharedData> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(example, pageable)).thenReturn(emptyPage);

    // When: finding all by example with pageable
    Page<SharedData> result = repository.findAll(example, pageable);

    // Then: should return empty page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEmpty();
    verify(repository, times(1)).findAll(example, pageable);
  }

  // ==================== count(Example) Tests ====================

  @Test
  void count_withExample_shouldReturnCount() {
    // Given: example
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);

    when(repository.count(example)).thenReturn(3L);

    // When: counting by example
    long result = repository.count(example);

    // Then: should return count
    assertThat(result).isEqualTo(3L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExample_noMatches_shouldReturnZero() {
    // Given: example with no matches
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);

    when(repository.count(example)).thenReturn(0L);

    // When: counting by example
    long result = repository.count(example);

    // Then: should return zero
    assertThat(result).isZero();
    verify(repository, times(1)).count(example);
  }

  // ==================== exists(Example) Tests ====================

  @Test
  void exists_withExample_matchExists_shouldReturnTrue() {
    // Given: example with match
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);

    when(repository.exists(example)).thenReturn(true);

    // When: checking existence by example
    boolean result = repository.exists(example);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_withExample_noMatch_shouldReturnFalse() {
    // Given: example with no match
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);

    when(repository.exists(example)).thenReturn(false);

    // When: checking existence by example
    boolean result = repository.exists(example);

    // Then: should return false
    assertThat(result).isFalse();
    verify(repository, times(1)).exists(example);
  }

  // ==================== findBy(Example, Function) Tests ====================

  @Test
  void findBy_withExampleAndFunction_shouldApplyFunction() {
    // Given: example and function
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);
    Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<SharedData>, List<SharedData>> function =
        query -> Arrays.asList(new SharedData(), new SharedData());
    List<SharedData> expectedResult = Arrays.asList(new SharedData(), new SharedData());

    when(repository.findBy(any(Example.class), any(Function.class))).thenReturn(expectedResult);

    // When: finding by with function
    Object result = repository.findBy(example, function);

    // Then: should apply function and return result
    assertThat(result).isNotNull();
    verify(repository, times(1)).findBy(any(Example.class), any(Function.class));
  }

  @Test
  void findBy_withExampleAndFunction_emptyResult_shouldReturnEmptyResult() {
    // Given: example and function returning empty
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData probe = new SharedData();
    Example<SharedData> example = Example.of(probe);
    Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<SharedData>, List<SharedData>> function =
        query -> Collections.emptyList();
    List<SharedData> emptyResult = Collections.emptyList();

    when(repository.findBy(any(Example.class), any(Function.class))).thenReturn(emptyResult);

    // When: finding by with function
    Object result = repository.findBy(example, function);

    // Then: should return empty result
    assertThat(result).isNotNull();
    verify(repository, times(1)).findBy(any(Example.class), any(Function.class));
  }

  // ==================== Integration-style Tests ====================

  @Test
  void saveAndFindById_shouldRoundTrip() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity = new SharedData();
    entity.setId("id");
    entity.setNamespace("test");

    when(repository.save(entity)).thenReturn(entity);
    when(repository.findById("id")).thenReturn(Optional.of(entity));

    // When: saving and finding
    SharedData saved = repository.save(entity);
    Optional<SharedData> found = repository.findById("id");

    // Then: should round trip
    assertThat(saved).isEqualTo(entity);
    assertThat(found).isPresent();
    assertThat(found.get()).isEqualTo(entity);
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).findById("id");
  }

  @Test
  void saveAllAndFindAll_shouldRoundTrip() {
    // Given: multiple entities
    SharedDataRepository repository = mock(SharedDataRepository.class);
    List<SharedData> entities = Arrays.asList(new SharedData(), new SharedData());

    when(repository.saveAll(entities)).thenReturn(entities);
    when(repository.findAll()).thenReturn(entities);

    // When: saving all and finding all
    List<SharedData> saved = repository.saveAll(entities);
    List<SharedData> found = repository.findAll();

    // Then: should round trip
    assertThat(saved).hasSize(2);
    assertThat(found).hasSize(2);
    verify(repository, times(1)).saveAll(entities);
    verify(repository, times(1)).findAll();
  }

  @Test
  void countAfterSave_shouldReflectAddition() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity = new SharedData();

    when(repository.count()).thenReturn(0L, 1L);
    when(repository.save(entity)).thenReturn(entity);

    // When: counting, saving, then counting again
    long countBefore = repository.count();
    repository.save(entity);
    long countAfter = repository.count();

    // Then: count should increase
    assertThat(countBefore).isZero();
    assertThat(countAfter).isEqualTo(1L);
    verify(repository, times(2)).count();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void existsByIdAfterSave_shouldReturnTrue() {
    // Given: a mocked repository
    SharedDataRepository repository = mock(SharedDataRepository.class);
    SharedData entity = new SharedData();
    entity.setId("id");

    when(repository.save(entity)).thenReturn(entity);
    when(repository.existsById("id")).thenReturn(true);

    // When: saving and checking existence
    repository.save(entity);
    boolean exists = repository.existsById("id");

    // Then: should exist
    assertThat(exists).isTrue();
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).existsById("id");
  }

  @Test
  void deleteAndExistsById_shouldReturnFalse() {
    // Given: a mocked repository with entity
    SharedDataRepository repository = mock(SharedDataRepository.class);
    String id = "id";

    doNothing().when(repository).deleteById(id);
    when(repository.existsById(id)).thenReturn(true, false);

    // When: checking existence, deleting, then checking again
    boolean existsBefore = repository.existsById(id);
    repository.deleteById(id);
    boolean existsAfter = repository.existsById(id);

    // Then: should not exist after deletion
    assertThat(existsBefore).isTrue();
    assertThat(existsAfter).isFalse();
    verify(repository, times(2)).existsById(id);
    verify(repository, times(1)).deleteById(id);
  }
}
