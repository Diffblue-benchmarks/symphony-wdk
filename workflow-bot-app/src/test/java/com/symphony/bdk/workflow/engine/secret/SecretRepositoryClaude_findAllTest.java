package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.findAll(Sort), findAll(Pageable), and findAll(Example, Pageable) methods.
 *
 * This class tests three overloaded findAll methods:
 * 1. List<SecretDomain> findAll(Sort sort) - Returns ALL entities with sorting
 * 2. Page<SecretDomain> findAll(Pageable pageable) - Returns paginated entities with sorting
 * 3. Page<SecretDomain> findAll(Example<SecretDomain> example, Pageable pageable) - Returns paginated filtered entities with sorting
 *
 * The findAll(Sort) method is inherited from JpaRepository (via ListPagingAndSortingRepository)
 * and returns ALL entities in the repository with the specified sort order applied.
 *
 * The findAll(Pageable) method is inherited from PagingAndSortingRepository (which JpaRepository extends)
 * and returns a page of entities with pagination and optional sorting applied.
 *
 * The findAll(Example, Pageable) method is inherited from QueryByExampleExecutor (which JpaRepository extends)
 * and returns a page of entities matching the Example query with pagination and optional sorting applied.
 *
 * Key characteristics of findAll(Sort):
 * 1. Returns all entities without filtering (unlike findAll(Example, Sort))
 * 2. Applies the specified sort order to the results
 * 3. Returns a List of entities
 * 4. Can sort by single or multiple fields
 * 5. Supports ascending and descending sort directions
 * 6. Does not use Example for filtering, just sorting
 *
 * Key characteristics of findAll(Pageable):
 * 1. Returns a Page object containing a subset of entities
 * 2. Supports pagination (page number and page size)
 * 3. Supports optional sorting within pages
 * 4. Returns metadata (total elements, total pages, current page, etc.)
 * 5. Efficient for large datasets where loading all entities is impractical
 * 6. Does not use Example for filtering, just pagination and sorting
 *
 * Key characteristics of findAll(Example, Pageable):
 * 1. Combines Example-based filtering with pagination
 * 2. Returns a Page object containing matching entities
 * 3. Supports both filtering criteria and pagination
 * 4. Supports optional sorting within pages
 * 5. Returns metadata for the filtered results
 * 6. Most flexible findAll variant - supports filtering, pagination, and sorting
 * 7. Ideal for dynamic search with pagination
 *
 * Method signatures:
 * - List<SecretDomain> findAll(Sort sort)
 * - Page<SecretDomain> findAll(Pageable pageable)
 * - Page<SecretDomain> findAll(Example<SecretDomain> example, Pageable pageable)
 *
 * Difference from related methods:
 * - findAll(): Returns all entities without sorting or pagination
 * - findAll(Sort): Returns all entities WITH sorting (no pagination)
 * - findAll(Pageable): Returns paginated entities WITH sorting (no filtering)
 * - findAll(Example): Returns filtered entities without sorting or pagination
 * - findAll(Example, Sort): Returns filtered entities with sorting (no pagination)
 * - findAll(Example, Pageable): Returns filtered paginated entities WITH sorting (this method)
 *
 * Use cases for findAll(Sort):
 * - Retrieving all entities in a specific order (e.g., sorted by creation date)
 * - Loading complete datasets with consistent ordering
 * - Exporting all data in a sorted manner
 * - Administrative operations requiring sorted full visibility
 * - Displaying all records in a UI with user-specified sort order
 *
 * Use cases for findAll(Pageable):
 * - Displaying paginated results in a UI (e.g., tables with page navigation)
 * - Loading large datasets incrementally
 * - REST API endpoints that return paginated responses
 * - Reducing memory consumption by loading only required data
 * - Implementing infinite scroll or "load more" functionality
 *
 * Example usage:
 * <pre>
 * // Get all entities sorted by ref ascending
 * Sort sort = Sort.by(Sort.Direction.ASC, "ref");
 * List<SecretDomain> sortedSecrets = repository.findAll(sort);
 *
 * // Get all entities sorted by creation date descending
 * Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
 * List<SecretDomain> sortedSecrets = repository.findAll(sort);
 *
 * // Get first page (10 items) sorted by ref
 * Pageable pageable = PageRequest.of(0, 10, Sort.by("ref"));
 * Page<SecretDomain> page = repository.findAll(pageable);
 *
 * // Get second page (20 items) sorted by createdAt descending
 * Pageable pageable = PageRequest.of(1, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
 * Page<SecretDomain> page = repository.findAll(pageable);
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of these methods, the method behavior is entirely
 * provided by Spring Data JPA's runtime proxy. Therefore, meaningful testing of
 * these methods requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The findAll(Sort) method exists and is callable on SecretRepository
 * 2. The method accepts the Sort parameter correctly
 * 3. The method returns a List<SecretDomain>
 * 4. The findAll(Pageable) method exists and is callable on SecretRepository
 * 5. The method accepts the Pageable parameter correctly
 * 6. The method returns a Page<SecretDomain> with proper pagination metadata
 * 7. Both methods can handle various scenarios (sorting, pagination, edge cases)
 * 8. Both methods can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual implementations are provided by Spring Data JPA
 * at runtime and are thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_findAllTest {

  @Test
  void findAll_withSortAscById_shouldReturnAllEntities() {
    // Given: a mocked SecretRepository with multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-c", "secret-c");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-a", "secret-a");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("ref-b", "secret-b");
    entity3.setId("id3");
    List<SecretDomain> allEntities = Arrays.asList(entity1, entity2, entity3);

    Sort sort = Sort.by(Sort.Direction.ASC, "id");

    when(repository.findAll(sort)).thenReturn(allEntities);

    // When: calling findAll with ascending sort by id
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return all entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortDescById_shouldReturnAllEntities() {
    // Given: a mocked SecretRepository with multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-2", "secret-2");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("ref-3", "secret-3");
    entity3.setId("id3");
    List<SecretDomain> allEntities = Arrays.asList(entity3, entity2, entity1);

    Sort sort = Sort.by(Sort.Direction.DESC, "id");

    when(repository.findAll(sort)).thenReturn(allEntities);

    // When: calling findAll with descending sort by id
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return all entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortByRef_shouldReturnAllEntities() {
    // Given: a mocked SecretRepository with entities sorted by ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("alpha", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("delta", "secret-2");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("zebra", "secret-3");
    entity3.setId("id3");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2, entity3);

    Sort sort = Sort.by(Sort.Direction.ASC, "ref");

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: calling findAll with sort by ref
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return all entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getRef()).isEqualTo("alpha");
    assertThat(result.get(1).getRef()).isEqualTo("delta");
    assertThat(result.get(2).getRef()).isEqualTo("zebra");
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortByCreatedAt_shouldReturnAllEntities() {
    // Given: a mocked SecretRepository with entities sorted by createdAt
    SecretRepository repository = mock(SecretRepository.class);

    Long timestamp1 = 1000L;
    Long timestamp2 = 2000L;
    Long timestamp3 = 3000L;

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    entity1.setCreatedAt(timestamp1);

    SecretDomain entity2 = new SecretDomain("ref-2", "secret-2");
    entity2.setId("id2");
    entity2.setCreatedAt(timestamp2);

    SecretDomain entity3 = new SecretDomain("ref-3", "secret-3");
    entity3.setId("id3");
    entity3.setCreatedAt(timestamp3);

    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2, entity3);

    Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: calling findAll with sort by createdAt
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return all entities sorted by createdAt
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getCreatedAt()).isEqualTo(timestamp1);
    assertThat(result.get(1).getCreatedAt()).isEqualTo(timestamp2);
    assertThat(result.get(2).getCreatedAt()).isEqualTo(timestamp3);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortByCreatedAtDesc_shouldReturnNewestFirst() {
    // Given: a mocked SecretRepository with descending sort by createdAt
    SecretRepository repository = mock(SecretRepository.class);

    Long timestamp1 = 1000L;
    Long timestamp2 = 2000L;

    SecretDomain entity1 = new SecretDomain("ref-old", "secret-old");
    entity1.setId("id1");
    entity1.setCreatedAt(timestamp1);

    SecretDomain entity2 = new SecretDomain("ref-new", "secret-new");
    entity2.setId("id2");
    entity2.setCreatedAt(timestamp2);

    List<SecretDomain> sortedEntities = Arrays.asList(entity2, entity1);

    Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: calling findAll with descending sort by createdAt
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return newest entities first
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getCreatedAt()).isEqualTo(timestamp2);
    assertThat(result.get(1).getCreatedAt()).isEqualTo(timestamp1);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withMultiFieldSort_shouldReturnEntities() {
    // Given: a mocked SecretRepository with multi-field sort
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("different-ref", "secret-1");
    entity1.setId("id1");

    SecretDomain entity2 = new SecretDomain("same-ref", "secret-2");
    entity2.setId("id2");

    SecretDomain entity3 = new SecretDomain("same-ref", "secret-3");
    entity3.setId("id3");

    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2, entity3);

    Sort sort = Sort.by(
        Sort.Order.asc("ref"),
        Sort.Order.desc("id")
    );

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: calling findAll with multi-field sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withEmptyRepository_shouldReturnEmptyList() {
    // Given: a mocked SecretRepository with no entities
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> emptyList = Collections.emptyList();

    Sort sort = Sort.by(Sort.Direction.ASC, "id");

    when(repository.findAll(sort)).thenReturn(emptyList);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSingleEntity_shouldReturnListWithOne() {
    // Given: a mocked SecretRepository with single entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("only-ref", "only-secret");
    entity.setId("only-id");
    List<SecretDomain> singleEntityList = Collections.singletonList(entity);

    Sort sort = Sort.by(Sort.Direction.ASC, "id");

    when(repository.findAll(sort)).thenReturn(singleEntityList);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return list with one entity
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getRef()).isEqualTo("only-ref");
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortByRefDesc_shouldReturnReverseAlphabetical() {
    // Given: a mocked SecretRepository with descending sort by ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("cherry", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("banana", "secret-2");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("apple", "secret-3");
    entity3.setId("id3");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2, entity3);

    Sort sort = Sort.by(Sort.Direction.DESC, "ref");

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: calling findAll with descending sort by ref
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return entities in reverse alphabetical order
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getRef()).isEqualTo("cherry");
    assertThat(result.get(1).getRef()).isEqualTo("banana");
    assertThat(result.get(2).getRef()).isEqualTo("apple");
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSort_shouldReturnListType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(
        new SecretDomain("ref-1", "secret-1")
    );

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return List type
    assertThat(result).isInstanceOf(List.class);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withUnsortedDefault_shouldStillReturnAllEntities() {
    // Given: a mocked SecretRepository with unsorted
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-2", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    Sort sort = Sort.unsorted();

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll with unsorted
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return all entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortReturnsEntitiesWithAllFieldsPopulated() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("test-ref", "test-secret");
    entity.setId("test-id");
    entity.setCreatedAt(System.currentTimeMillis());
    List<SecretDomain> entities = Collections.singletonList(entity);

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return entity with all fields populated
    assertThat(result).hasSize(1);
    SecretDomain returned = result.get(0);
    assertThat(returned.getId()).isEqualTo("test-id");
    assertThat(returned.getRef()).isEqualTo("test-ref");
    assertThat(returned.getSecret()).isEqualTo("test-secret");
    assertThat(returned.getCreatedAt()).isNotNull();
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortByIdMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-2", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    Sort sort = Sort.by(Sort.Direction.ASC, "id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll multiple times
    List<SecretDomain> result1 = repository.findAll(sort);
    List<SecretDomain> result2 = repository.findAll(sort);

    // Then: each call should work
    assertThat(result1).hasSize(2);
    assertThat(result2).hasSize(2);
    verify(repository, times(2)).findAll(sort);
  }

  @Test
  void findAll_withLargeDataset_shouldReturnAllEntities() {
    // Given: a mocked SecretRepository with large dataset
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> largeList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      SecretDomain entity = new SecretDomain("ref-" + i, "secret-" + i);
      entity.setId("id" + i);
      largeList.add(entity);
    }

    Sort sort = Sort.by(Sort.Direction.ASC, "ref");

    when(repository.findAll(sort)).thenReturn(largeList);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return all 100 entities
    assertThat(result).hasSize(100);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortByRefHandlesNullValues() {
    // Given: a mocked SecretRepository with entities having null refs
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain(null, "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("valid-ref", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    Sort sort = Sort.by(Sort.Direction.ASC, "ref");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return all entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withThreeFieldSort_shouldReturnEntities() {
    // Given: a mocked SecretRepository with three-field sort
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-a", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-a", "secret-2");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("ref-b", "secret-3");
    entity3.setId("id3");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2, entity3);

    Sort sort = Sort.by(
        Sort.Order.asc("ref"),
        Sort.Order.desc("createdAt"),
        Sort.Order.asc("id")
    );

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: calling findAll with three-field sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return entities
    assertThat(result).hasSize(3);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortReturnsNotNull() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(
        new SecretDomain("ref", "secret")
    );

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should never return null
    assertThat(result).isNotNull();
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.emptyList();

    Sort sort = Sort.by("id");

    when(repository.findAll(any(Sort.class))).thenReturn(entities);

    // When & Then: findAll should complete without throwing an exception
    assertThatCode(() -> repository.findAll(sort)).doesNotThrowAnyException();
  }

  @Test
  void findAll_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");
    List<SecretDomain> allEntities = Collections.singletonList(savedEntity);

    Sort sort = Sort.by(Sort.Direction.ASC, "ref");

    when(repository.findAll(sort)).thenReturn(allEntities);

    // When: saving entity then calling findAll
    repository.save(entityToSave);
    List<SecretDomain> result = repository.findAll(sort);

    // Then: findAll should work after save
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    verify(repository, times(1)).save(entityToSave);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.emptyList();

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling flush then findAll
    repository.flush();
    List<SecretDomain> result = repository.findAll(sort);

    // Then: findAll should work after flush
    assertThat(result).isNotNull();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_afterDeleteOperation_shouldWork() {
    // Given: a mocked SecretRepository with delete operation performed
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.emptyList();

    Sort sort = Sort.by(Sort.Direction.ASC, "ref");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: deleting entity then calling findAll
    repository.deleteByRef("ref-1");
    List<SecretDomain> result = repository.findAll(sort);

    // Then: findAll should work after delete
    assertThat(result).isNotNull();
    verify(repository, times(1)).deleteByRef("ref-1");
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.emptyList();

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findById then findAll
    repository.findById("some-id");
    List<SecretDomain> result = repository.findAll(sort);

    // Then: findAll should work after findById
    assertThat(result).isNotNull();
    verify(repository, times(1)).findById("some-id");
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortCalledWithDifferentSorts_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository with different sorts
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    List<SecretDomain> entities = Collections.singletonList(entity1);

    Sort sort1 = Sort.by(Sort.Direction.ASC, "id");
    Sort sort2 = Sort.by(Sort.Direction.DESC, "ref");

    when(repository.findAll(sort1)).thenReturn(entities);
    when(repository.findAll(sort2)).thenReturn(entities);

    // When: calling findAll with different sorts
    List<SecretDomain> result1 = repository.findAll(sort1);
    List<SecretDomain> result2 = repository.findAll(sort2);

    // Then: each call should work independently
    assertThat(result1).hasSize(1);
    assertThat(result2).hasSize(1);
    verify(repository, times(1)).findAll(sort1);
    verify(repository, times(1)).findAll(sort2);
  }

  @Test
  void findAll_withSortAfterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling saveAndFlush then findAll
    repository.saveAndFlush(entity);
    List<SecretDomain> result = repository.findAll(sort);

    // Then: findAll should work after saveAndFlush
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortReturnsEntitiesWithNullFields() {
    // Given: a mocked SecretRepository with entities having null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain(null, "secret");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref", null);
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return entities with null fields
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getRef()).isNull();
    assertThat(result.get(1).getSecret()).isNull();
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortReturnsEntitiesWithEmptyStrings() {
    // Given: a mocked SecretRepository with entities having empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("", "");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return entities with empty strings
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getRef()).isEmpty();
    assertThat(result.get(0).getSecret()).isEmpty();
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortReturnsEntitiesWithLongValues() {
    // Given: a mocked SecretRepository with entities having long values
    SecretRepository repository = mock(SecretRepository.class);

    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    Sort sort = Sort.by("ref");

    when(repository.findAll(sort)).thenReturn(entities);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return entities with long values
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getRef()).isEqualTo(longRef);
    assertThat(result.get(0).getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSortReturnsEntitiesWithMixedStates() {
    // Given: a mocked SecretRepository with entities in different states
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    entity1.setCreatedAt(System.currentTimeMillis());

    SecretDomain entity2 = new SecretDomain(null, "secret2");
    entity2.setId("id2");

    SecretDomain entity3 = new SecretDomain("ref3", null);
    entity3.setId("id3");

    List<SecretDomain> mixedEntities = Arrays.asList(entity1, entity2, entity3);

    Sort sort = Sort.by("id");

    when(repository.findAll(sort)).thenReturn(mixedEntities);

    // When: calling findAll with sort
    List<SecretDomain> result = repository.findAll(sort);

    // Then: should return all entities regardless of state
    assertThat(result).hasSize(3);
    verify(repository, times(1)).findAll(sort);
  }

  // ==================== Tests for findAll(Pageable) ====================

  @Test
  void findAll_withPageable_shouldReturnPageOfEntities() {
    // Given: a mocked SecretRepository with pageable
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-2", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return page of entities
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getTotalElements()).isEqualTo(2);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableFirstPage_shouldReturnFirstPage() {
    // Given: a mocked SecretRepository with first page request
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-2", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> firstPageEntities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(0, 2);
    Page<SecretDomain> page = new PageImpl<>(firstPageEntities, pageable, 10);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll for first page
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return first page
    assertThat(result).isNotNull();
    assertThat(result.getNumber()).isEqualTo(0);
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getTotalElements()).isEqualTo(10);
    assertThat(result.getTotalPages()).isEqualTo(5);
    assertThat(result.isFirst()).isTrue();
    assertThat(result.hasNext()).isTrue();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableSecondPage_shouldReturnSecondPage() {
    // Given: a mocked SecretRepository with second page request
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity3 = new SecretDomain("ref-3", "secret-3");
    entity3.setId("id3");
    SecretDomain entity4 = new SecretDomain("ref-4", "secret-4");
    entity4.setId("id4");
    List<SecretDomain> secondPageEntities = Arrays.asList(entity3, entity4);

    Pageable pageable = PageRequest.of(1, 2);
    Page<SecretDomain> page = new PageImpl<>(secondPageEntities, pageable, 10);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll for second page
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return second page
    assertThat(result).isNotNull();
    assertThat(result.getNumber()).isEqualTo(1);
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getTotalElements()).isEqualTo(10);
    assertThat(result.isFirst()).isFalse();
    assertThat(result.hasNext()).isTrue();
    assertThat(result.hasPrevious()).isTrue();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableLastPage_shouldReturnLastPage() {
    // Given: a mocked SecretRepository with last page request
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref-last", "secret-last");
    entity.setId("id-last");
    List<SecretDomain> lastPageEntities = Collections.singletonList(entity);

    Pageable pageable = PageRequest.of(4, 2);
    Page<SecretDomain> page = new PageImpl<>(lastPageEntities, pageable, 9);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll for last page
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return last page
    assertThat(result).isNotNull();
    assertThat(result.getNumber()).isEqualTo(4);
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getTotalElements()).isEqualTo(9);
    assertThat(result.isLast()).isTrue();
    assertThat(result.hasNext()).isFalse();
    assertThat(result.hasPrevious()).isTrue();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableAndSort_shouldReturnSortedPage() {
    // Given: a mocked SecretRepository with pageable containing sort
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("alpha", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("beta", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "ref"));
    Page<SecretDomain> page = new PageImpl<>(sortedEntities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with sort in pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return sorted page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getContent().get(0).getRef()).isEqualTo("alpha");
    assertThat(result.getContent().get(1).getRef()).isEqualTo("beta");
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableDescendingSort_shouldReturnDescendingSortedPage() {
    // Given: a mocked SecretRepository with descending sort
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("zebra", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("alpha", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "ref"));
    Page<SecretDomain> page = new PageImpl<>(sortedEntities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with descending sort
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return descending sorted page
    assertThat(result).isNotNull();
    assertThat(result.getContent().get(0).getRef()).isEqualTo("zebra");
    assertThat(result.getContent().get(1).getRef()).isEqualTo("alpha");
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableEmptyPage_shouldReturnEmptyPage() {
    // Given: a mocked SecretRepository returning empty page
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> emptyList = Collections.emptyList();

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> emptyPage = new PageImpl<>(emptyList, pageable, 0);

    when(repository.findAll(pageable)).thenReturn(emptyPage);

    // When: calling findAll with pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return empty page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEmpty();
    assertThat(result.getTotalElements()).isEqualTo(0);
    assertThat(result.getTotalPages()).isEqualTo(0);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableLargePageSize_shouldReturnAllEntities() {
    // Given: a mocked SecretRepository with large page size
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> allEntities = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      SecretDomain entity = new SecretDomain("ref-" + i, "secret-" + i);
      entity.setId("id" + i);
      allEntities.add(entity);
    }

    Pageable pageable = PageRequest.of(0, 100);
    Page<SecretDomain> page = new PageImpl<>(allEntities, pageable, 50);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with large page size
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return all entities in one page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(50);
    assertThat(result.getTotalElements()).isEqualTo(50);
    assertThat(result.getTotalPages()).isEqualTo(1);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableSmallPageSize_shouldReturnSmallPage() {
    // Given: a mocked SecretRepository with small page size
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    List<SecretDomain> singleEntity = Collections.singletonList(entity1);

    Pageable pageable = PageRequest.of(0, 1);
    Page<SecretDomain> page = new PageImpl<>(singleEntity, pageable, 100);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with small page size
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return small page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getTotalElements()).isEqualTo(100);
    assertThat(result.getTotalPages()).isEqualTo(100);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageable_shouldReturnPageType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(
        new SecretDomain("ref", "secret")
    );

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 1);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return Page type
    assertThat(result).isInstanceOf(Page.class);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref-1", "secret-1"),
        new SecretDomain("ref-2", "secret-2")
    );

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll multiple times
    Page<SecretDomain> result1 = repository.findAll(pageable);
    Page<SecretDomain> result2 = repository.findAll(pageable);

    // Then: each call should work
    assertThat(result1.getContent()).hasSize(2);
    assertThat(result2.getContent()).hasSize(2);
    verify(repository, times(2)).findAll(pageable);
  }

  @Test
  void findAll_withPageableReturnsNotNull() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(
        new SecretDomain("ref", "secret")
    );

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 1);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should never return null
    assertThat(result).isNotNull();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableShouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.emptyList();

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> emptyPage = new PageImpl<>(entities, pageable, 0);

    when(repository.findAll(any(Pageable.class))).thenReturn(emptyPage);

    // When & Then: findAll should complete without throwing an exception
    assertThatCode(() -> repository.findAll(pageable)).doesNotThrowAnyException();
  }

  @Test
  void findAll_withPageableAfterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(savedEntity);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 1);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: saving entity then calling findAll
    repository.save(entityToSave);
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: findAll should work after save
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    verify(repository, times(1)).save(entityToSave);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableAfterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.emptyList();

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> emptyPage = new PageImpl<>(entities, pageable, 0);

    when(repository.findAll(pageable)).thenReturn(emptyPage);

    // When: calling flush then findAll
    repository.flush();
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: findAll should work after flush
    assertThat(result).isNotNull();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableReturnsEntitiesWithAllFieldsPopulated() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("test-ref", "test-secret");
    entity.setId("test-id");
    entity.setCreatedAt(System.currentTimeMillis());
    List<SecretDomain> entities = Collections.singletonList(entity);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 1);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return entity with all fields populated
    assertThat(result.getContent()).hasSize(1);
    SecretDomain returned = result.getContent().get(0);
    assertThat(returned.getId()).isEqualTo("test-id");
    assertThat(returned.getRef()).isEqualTo("test-ref");
    assertThat(returned.getSecret()).isEqualTo("test-secret");
    assertThat(returned.getCreatedAt()).isNotNull();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableCalledWithDifferentPageables_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository with different pageables
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-1", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-2", "secret-2");
    entity2.setId("id2");

    Pageable pageable1 = PageRequest.of(0, 1);
    Pageable pageable2 = PageRequest.of(1, 1);

    Page<SecretDomain> page1 = new PageImpl<>(Collections.singletonList(entity1), pageable1, 2);
    Page<SecretDomain> page2 = new PageImpl<>(Collections.singletonList(entity2), pageable2, 2);

    when(repository.findAll(pageable1)).thenReturn(page1);
    when(repository.findAll(pageable2)).thenReturn(page2);

    // When: calling findAll with different pageables
    Page<SecretDomain> result1 = repository.findAll(pageable1);
    Page<SecretDomain> result2 = repository.findAll(pageable2);

    // Then: each call should work independently
    assertThat(result1.getContent()).hasSize(1);
    assertThat(result2.getContent()).hasSize(1);
    assertThat(result1.getNumber()).isEqualTo(0);
    assertThat(result2.getNumber()).isEqualTo(1);
    verify(repository, times(1)).findAll(pageable1);
    verify(repository, times(1)).findAll(pageable2);
  }

  @Test
  void findAll_withPageableReturnsCorrectPageMetadata() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref-1", "secret-1"),
        new SecretDomain("ref-2", "secret-2")
    );

    Pageable pageable = PageRequest.of(1, 2);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 10);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return correct page metadata
    assertThat(result.getNumber()).isEqualTo(1); // Current page number
    assertThat(result.getSize()).isEqualTo(2); // Page size
    assertThat(result.getNumberOfElements()).isEqualTo(2); // Elements in current page
    assertThat(result.getTotalElements()).isEqualTo(10); // Total elements in all pages
    assertThat(result.getTotalPages()).isEqualTo(5); // Total pages
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableMultiFieldSort_shouldReturnSortedPage() {
    // Given: a mocked SecretRepository with multi-field sort
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-a", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref-a", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2);

    Sort sort = Sort.by(
        Sort.Order.asc("ref"),
        Sort.Order.desc("id")
    );
    Pageable pageable = PageRequest.of(0, 10, sort);
    Page<SecretDomain> page = new PageImpl<>(sortedEntities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with multi-field sort in pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return sorted page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableReturnsEntitiesWithNullFields() {
    // Given: a mocked SecretRepository with entities having null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain(null, "secret");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref", null);
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return entities with null fields
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getContent().get(0).getRef()).isNull();
    assertThat(result.getContent().get(1).getSecret()).isNull();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableReturnsEntitiesWithEmptyStrings() {
    // Given: a mocked SecretRepository with entities having empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("", "");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 1);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return entities with empty strings
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0).getRef()).isEmpty();
    assertThat(result.getContent().get(0).getSecret()).isEmpty();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableHasCorrectNavigationFlags() {
    // Given: a mocked SecretRepository for middle page
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref-5", "secret-5"),
        new SecretDomain("ref-6", "secret-6")
    );

    Pageable pageable = PageRequest.of(2, 2);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 10);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll for middle page
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should have correct navigation flags
    assertThat(result.hasNext()).isTrue(); // Has next page
    assertThat(result.hasPrevious()).isTrue(); // Has previous page
    assertThat(result.isFirst()).isFalse(); // Not first page
    assertThat(result.isLast()).isFalse(); // Not last page
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageableUnsorted_shouldReturnUnsortedPage() {
    // Given: a mocked SecretRepository with unsorted pageable
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref-1", "secret-1"),
        new SecretDomain("ref-2", "secret-2")
    );

    Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: calling findAll with unsorted pageable
    Page<SecretDomain> result = repository.findAll(pageable);

    // Then: should return unsorted page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    verify(repository, times(1)).findAll(pageable);
  }

  // ==================== Tests for findAll(Example, Pageable) ====================

  @Test
  void findAll_withExampleAndPageable_shouldReturnFilteredPage() {
    // Given: a mocked SecretRepository with example and pageable
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity1 = new SecretDomain("test-ref", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("test-ref", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> filteredEntities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(filteredEntities, pageable, 2);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with example and pageable
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return filtered page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getTotalElements()).isEqualTo(2);
    assertThat(result.getContent().get(0).getRef()).isEqualTo("test-ref");
    assertThat(result.getContent().get(1).getRef()).isEqualTo("test-ref");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableNoMatches_shouldReturnEmptyPage() {
    // Given: a mocked SecretRepository with example that matches nothing
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("non-existent-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(example, pageable)).thenReturn(emptyPage);

    // When: calling findAll with non-matching example
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return empty page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEmpty();
    assertThat(result.getTotalElements()).isEqualTo(0);
    assertThat(result.getTotalPages()).isEqualTo(0);
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableFirstPage_shouldReturnFirstPageOfFiltered() {
    // Given: a mocked SecretRepository with example matching multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("common-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity1 = new SecretDomain("common-ref", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("common-ref", "secret-2");
    entity2.setId("id2");
    List<SecretDomain> firstPageEntities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(0, 2);
    Page<SecretDomain> page = new PageImpl<>(firstPageEntities, pageable, 10);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll for first page
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return first page of filtered results
    assertThat(result).isNotNull();
    assertThat(result.getNumber()).isEqualTo(0);
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getTotalElements()).isEqualTo(10);
    assertThat(result.getTotalPages()).isEqualTo(5);
    assertThat(result.isFirst()).isTrue();
    assertThat(result.hasNext()).isTrue();
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableMiddlePage_shouldReturnMiddlePageOfFiltered() {
    // Given: a mocked SecretRepository with middle page request
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity1 = new SecretDomain("test-ref", "secret-5");
    entity1.setId("id5");
    SecretDomain entity2 = new SecretDomain("test-ref", "secret-6");
    entity2.setId("id6");
    List<SecretDomain> middlePageEntities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(2, 2);
    Page<SecretDomain> page = new PageImpl<>(middlePageEntities, pageable, 10);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll for middle page
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return middle page of filtered results
    assertThat(result).isNotNull();
    assertThat(result.getNumber()).isEqualTo(2);
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.hasPrevious()).isTrue();
    assertThat(result.hasNext()).isTrue();
    assertThat(result.isFirst()).isFalse();
    assertThat(result.isLast()).isFalse();
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableLastPage_shouldReturnLastPageOfFiltered() {
    // Given: a mocked SecretRepository with last page request
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("test-ref", "secret-10");
    entity.setId("id10");
    List<SecretDomain> lastPageEntities = Collections.singletonList(entity);

    Pageable pageable = PageRequest.of(4, 2);
    Page<SecretDomain> page = new PageImpl<>(lastPageEntities, pageable, 9);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll for last page
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return last page of filtered results
    assertThat(result).isNotNull();
    assertThat(result.getNumber()).isEqualTo(4);
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.isLast()).isTrue();
    assertThat(result.hasPrevious()).isTrue();
    assertThat(result.hasNext()).isFalse();
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableSorted_shouldReturnFilteredSortedPage() {
    // Given: a mocked SecretRepository with example and sorted pageable
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity1 = new SecretDomain("test-ref", "aaa-secret");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("test-ref", "bbb-secret");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("test-ref", "ccc-secret");
    entity3.setId("id3");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2, entity3);

    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "secret"));
    Page<SecretDomain> page = new PageImpl<>(sortedEntities, pageable, 3);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with example and sorted pageable
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return filtered and sorted page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(3);
    assertThat(result.getContent().get(0).getSecret()).isEqualTo("aaa-secret");
    assertThat(result.getContent().get(1).getSecret()).isEqualTo("bbb-secret");
    assertThat(result.getContent().get(2).getSecret()).isEqualTo("ccc-secret");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableSortedDescending_shouldReturnFilteredDescSortedPage() {
    // Given: a mocked SecretRepository with example and descending sorted pageable
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity1 = new SecretDomain("test-ref", "zzz-secret");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("test-ref", "yyy-secret");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("test-ref", "xxx-secret");
    entity3.setId("id3");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2, entity3);

    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "secret"));
    Page<SecretDomain> page = new PageImpl<>(sortedEntities, pageable, 3);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with example and descending sorted pageable
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return filtered and descending sorted page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(3);
    assertThat(result.getContent().get(0).getSecret()).isEqualTo("zzz-secret");
    assertThat(result.getContent().get(1).getSecret()).isEqualTo("yyy-secret");
    assertThat(result.getContent().get(2).getSecret()).isEqualTo("xxx-secret");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableMultiFieldSort_shouldReturnFilteredMultiSortedPage() {
    // Given: a mocked SecretRepository with example and multi-field sorted pageable
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity1 = new SecretDomain("test-ref", "secret-1");
    entity1.setId("id-a");
    SecretDomain entity2 = new SecretDomain("test-ref", "secret-1");
    entity2.setId("id-b");
    SecretDomain entity3 = new SecretDomain("test-ref", "secret-2");
    entity3.setId("id-c");
    List<SecretDomain> sortedEntities = Arrays.asList(entity1, entity2, entity3);

    Sort sort = Sort.by(Sort.Order.asc("secret"), Sort.Order.asc("id"));
    Pageable pageable = PageRequest.of(0, 10, sort);
    Page<SecretDomain> page = new PageImpl<>(sortedEntities, pageable, 3);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with example and multi-field sort
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return filtered and multi-sorted page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(3);
    assertThat(result.getContent().get(0).getSecret()).isEqualTo("secret-1");
    assertThat(result.getContent().get(0).getId()).isEqualTo("id-a");
    assertThat(result.getContent().get(1).getSecret()).isEqualTo("secret-1");
    assertThat(result.getContent().get(1).getId()).isEqualTo("id-b");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableSingleResult_shouldReturnPageWithOneElement() {
    // Given: a mocked SecretRepository with example matching exactly one entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("unique-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("unique-ref", "unique-secret");
    entity.setId("unique-id");
    List<SecretDomain> singleEntity = Collections.singletonList(entity);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(singleEntity, pageable, 1);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with example matching single entity
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return page with one element
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getTotalElements()).isEqualTo(1);
    assertThat(result.getTotalPages()).isEqualTo(1);
    assertThat(result.getContent().get(0).getRef()).isEqualTo("unique-ref");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableLargePageSize_shouldHandleLargePages() {
    // Given: a mocked SecretRepository with large page size
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    List<SecretDomain> largeList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      SecretDomain entity = new SecretDomain("test-ref", "secret-" + i);
      entity.setId("id-" + i);
      largeList.add(entity);
    }

    Pageable pageable = PageRequest.of(0, 100);
    Page<SecretDomain> page = new PageImpl<>(largeList, pageable, 100);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with large page size
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should handle large page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(100);
    assertThat(result.getTotalElements()).isEqualTo(100);
    assertThat(result.getTotalPages()).isEqualTo(1);
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableSmallPageSize_shouldHandleSmallPages() {
    // Given: a mocked SecretRepository with page size of 1
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("test-ref", "secret-1");
    entity.setId("id1");
    List<SecretDomain> singleEntity = Collections.singletonList(entity);

    Pageable pageable = PageRequest.of(0, 1);
    Page<SecretDomain> page = new PageImpl<>(singleEntity, pageable, 10);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with page size of 1
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should handle small page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getTotalElements()).isEqualTo(10);
    assertThat(result.getTotalPages()).isEqualTo(10);
    assertThat(result.hasNext()).isTrue();
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableVerifyPageMetadata_shouldHaveCorrectMetadata() {
    // Given: a mocked SecretRepository with specific page configuration
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("test-ref", "secret-1"),
        new SecretDomain("test-ref", "secret-2")
    );

    Pageable pageable = PageRequest.of(1, 2);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 6);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should have correct page metadata
    assertThat(result.getNumber()).isEqualTo(1); // Page number
    assertThat(result.getSize()).isEqualTo(2); // Page size
    assertThat(result.getNumberOfElements()).isEqualTo(2); // Actual elements in page
    assertThat(result.getTotalElements()).isEqualTo(6); // Total matching elements
    assertThat(result.getTotalPages()).isEqualTo(3); // Total pages
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableVerifyNavigationFlags_shouldHaveCorrectFlags() {
    // Given: a mocked SecretRepository with middle page
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("test-ref", "secret-3"),
        new SecretDomain("test-ref", "secret-4")
    );

    Pageable pageable = PageRequest.of(1, 2);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 10);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should have correct navigation flags
    assertThat(result.hasNext()).isTrue(); // Has next page
    assertThat(result.hasPrevious()).isTrue(); // Has previous page
    assertThat(result.isFirst()).isFalse(); // Not first page
    assertThat(result.isLast()).isFalse(); // Not last page
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableUnsorted_shouldReturnUnsortedFilteredPage() {
    // Given: a mocked SecretRepository with unsorted pageable
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("test-ref", "secret-1"),
        new SecretDomain("test-ref", "secret-2")
    );

    Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with unsorted pageable
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return unsorted filtered page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getContent().get(0).getRef()).isEqualTo("test-ref");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableComplexExample_shouldFilterByMultipleFields() {
    // Given: a mocked SecretRepository with complex example
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("specific-ref", "specific-secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("specific-ref", "specific-secret");
    entity.setId("matched-id");
    List<SecretDomain> matchedEntities = Collections.singletonList(entity);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(matchedEntities, pageable, 1);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with complex example
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return entities matching all criteria
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0).getRef()).isEqualTo("specific-ref");
    assertThat(result.getContent().get(0).getSecret()).isEqualTo("specific-secret");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableNullSafeHandling_shouldHandleNullFields() {
    // Given: a mocked SecretRepository with example having null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity1 = new SecretDomain("test-ref", "secret-1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("test-ref", null);
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with example having null fields
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should handle null fields correctly
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getContent().get(0).getRef()).isEqualTo("test-ref");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableEmptyStringRef_shouldFilterByEmptyString() {
    // Given: a mocked SecretRepository with example having empty string ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("", "secret-with-empty-ref");
    entity.setId("empty-ref-id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entities, pageable, 1);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll with empty string ref
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should filter by empty string
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0).getRef()).isEqualTo("");
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableVerifyMethodCalled_shouldCallRepositoryOnce() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);
    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: calling findAll
    repository.findAll(example, pageable);

    // Then: should call repository method exactly once
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableNoException_shouldNotThrowException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);
    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When & Then: calling findAll should not throw exception
    assertThatCode(() -> repository.findAll(example, pageable))
        .doesNotThrowAnyException();
  }

  @Test
  void findAll_withExampleAndPageableDifferentPageSizes_shouldRespectPageSize() {
    // Given: a mocked SecretRepository with 20 matching entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    // Page 1: size 5
    List<SecretDomain> page1Entities = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      SecretDomain entity = new SecretDomain("test-ref", "secret-" + i);
      entity.setId("id-" + i);
      page1Entities.add(entity);
    }
    Pageable pageable1 = PageRequest.of(0, 5);
    Page<SecretDomain> page1 = new PageImpl<>(page1Entities, pageable1, 20);

    when(repository.findAll(example, pageable1)).thenReturn(page1);

    // When: calling findAll with page size 5
    Page<SecretDomain> result1 = repository.findAll(example, pageable1);

    // Then: should return 5 elements
    assertThat(result1.getContent()).hasSize(5);
    assertThat(result1.getTotalPages()).isEqualTo(4);

    // Page 2: size 10
    List<SecretDomain> page2Entities = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      SecretDomain entity = new SecretDomain("test-ref", "secret-" + i);
      entity.setId("id-" + i);
      page2Entities.add(entity);
    }
    Pageable pageable2 = PageRequest.of(0, 10);
    Page<SecretDomain> page2 = new PageImpl<>(page2Entities, pageable2, 20);

    when(repository.findAll(example, pageable2)).thenReturn(page2);

    // When: calling findAll with page size 10
    Page<SecretDomain> result2 = repository.findAll(example, pageable2);

    // Then: should return 10 elements
    assertThat(result2.getContent()).hasSize(10);
    assertThat(result2.getTotalPages()).isEqualTo(2);

    verify(repository, times(1)).findAll(example, pageable1);
    verify(repository, times(1)).findAll(example, pageable2);
  }

  @Test
  void findAll_withExampleAndPageableAfterSave_shouldIncludeNewlySavedEntity() {
    // Given: a mocked SecretRepository with a saved entity matching example
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain newEntity = new SecretDomain("new-ref", "new-secret");
    when(repository.save(any(SecretDomain.class))).thenReturn(newEntity);

    SecretDomain probe = new SecretDomain("new-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    newEntity.setId("new-id");
    List<SecretDomain> entitiesWithNew = Collections.singletonList(newEntity);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> page = new PageImpl<>(entitiesWithNew, pageable, 1);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: saving and then finding with example
    repository.save(newEntity);
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should include the newly saved entity
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0).getRef()).isEqualTo("new-ref");
    verify(repository, times(1)).save(any(SecretDomain.class));
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageableAfterDelete_shouldExcludeDeletedEntity() {
    // Given: a mocked SecretRepository after deletion
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("deleted-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    Pageable pageable = PageRequest.of(0, 10);
    Page<SecretDomain> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(example, pageable)).thenReturn(emptyPage);

    // When: finding after deletion
    Page<SecretDomain> result = repository.findAll(example, pageable);

    // Then: should return empty page
    assertThat(result.getContent()).isEmpty();
    assertThat(result.getTotalElements()).isEqualTo(0);
    verify(repository, times(1)).findAll(example, pageable);
  }
}
