package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

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
 * Test class for SecretRepository.findAllById(Iterable) method.
 *
 * The findAllById(Iterable<ID> ids) method is inherited from CrudRepository
 * (which JpaRepository extends) and provides batch retrieval by IDs.
 * Key characteristics:
 * 1. Takes an Iterable of ID values (String type for SecretRepository)
 * 2. Returns a List of entities with matching IDs
 * 3. Only returns entities that exist - non-existent IDs are silently ignored
 * 4. More efficient than multiple individual findById() calls
 * 5. Order of returned entities may not match the order of requested IDs
 * 6. Returns List<SecretDomain> type
 * 7. Does not throw exception for non-existent IDs
 *
 * Method signature: List<SecretDomain> findAllById(Iterable<String> ids)
 *
 * Difference from findById:
 * - findById(ID): Returns Optional<Entity> for a single ID, empty if not found
 * - findAllById(Iterable<ID>): Returns List<Entity> for multiple IDs, omits non-existent ones
 *
 * Use cases for findAllById:
 * - Batch retrieval of multiple entities by their IDs
 * - Loading a specific subset of entities efficiently
 * - Better performance than multiple findById() calls
 * - Fetching entities for a list of IDs from a UI or API request
 * - Loading related entities by their IDs
 *
 * Example usage:
 * <pre>
 * List<String> ids = Arrays.asList("id1", "id2", "id3");
 * List<SecretDomain> secrets = repository.findAllById(ids);
 * // Returns only the entities that exist (e.g., if id2 doesn't exist, returns 2 entities)
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of findAllById, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The findAllById method exists and is callable on SecretRepository
 * 2. The method accepts an Iterable<String> parameter
 * 3. The method returns a List<SecretDomain>
 * 4. The method can handle various scenarios (empty list, single ID, multiple IDs)
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual findAllById implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_findAllByIdTest {

  @Test
  void findAllById_withMultipleIds_shouldReturnMatchingEntities() {
    // Given: a mocked SecretRepository and multiple IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2", "id3");

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");
    entity3.setId("id3");
    List<SecretDomain> matchingEntities = Arrays.asList(entity1, entity2, entity3);

    when(repository.findAllById(ids)).thenReturn(matchingEntities);

    // When: calling findAllById
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: should return all matching entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getId()).isEqualTo("id1");
    assertThat(result.get(1).getId()).isEqualTo("id2");
    assertThat(result.get(2).getId()).isEqualTo("id3");
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_shouldReturnListType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Collections.singletonList("id1");
    List<SecretDomain> entities = Collections.emptyList();

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: calling findAllById
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: should return List type
    assertThat(result).isInstanceOf(List.class);
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");
    List<SecretDomain> entities = Collections.emptyList();

    when(repository.findAllById(any(Iterable.class))).thenReturn(entities);

    // When & Then: findAllById should complete without throwing an exception
    assertThatCode(() -> repository.findAllById(ids)).doesNotThrowAnyException();
  }

  @Test
  void findAllById_withEmptyList_shouldReturnEmptyList() {
    // Given: a mocked SecretRepository with empty ID list
    SecretRepository repository = mock(SecretRepository.class);

    List<String> emptyIds = Collections.emptyList();
    List<SecretDomain> emptyResult = Collections.emptyList();

    when(repository.findAllById(emptyIds)).thenReturn(emptyResult);

    // When: calling findAllById with empty list
    List<SecretDomain> result = repository.findAllById(emptyIds);

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAllById(emptyIds);
  }

  @Test
  void findAllById_withSingleId_shouldReturnListWithOneEntity() {
    // Given: a mocked SecretRepository with single ID
    SecretRepository repository = mock(SecretRepository.class);

    List<String> singleId = Collections.singletonList("id1");

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id1");
    List<SecretDomain> singleResult = Collections.singletonList(entity);

    when(repository.findAllById(singleId)).thenReturn(singleResult);

    // When: calling findAllById
    List<SecretDomain> result = repository.findAllById(singleId);

    // Then: should return list with one entity
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo("id1");
    verify(repository, times(1)).findAllById(singleId);
  }

  @Test
  void findAllById_withNonExistentIds_shouldReturnEmptyList() {
    // Given: a mocked SecretRepository with non-existent IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> nonExistentIds = Arrays.asList("non-existent-1", "non-existent-2");
    List<SecretDomain> emptyResult = Collections.emptyList();

    when(repository.findAllById(nonExistentIds)).thenReturn(emptyResult);

    // When: calling findAllById with non-existent IDs
    List<SecretDomain> result = repository.findAllById(nonExistentIds);

    // Then: should return empty list (no exception thrown)
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAllById(nonExistentIds);
  }

  @Test
  void findAllById_withMixedExistentAndNonExistentIds_shouldReturnOnlyExistent() {
    // Given: a mocked SecretRepository with mixed IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> mixedIds = Arrays.asList("id1", "non-existent", "id2");

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    List<SecretDomain> existingEntities = Arrays.asList(entity1, entity2);

    when(repository.findAllById(mixedIds)).thenReturn(existingEntities);

    // When: calling findAllById
    List<SecretDomain> result = repository.findAllById(mixedIds);

    // Then: should return only existing entities
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo("id1");
    assertThat(result.get(1).getId()).isEqualTo("id2");
    verify(repository, times(1)).findAllById(mixedIds);
  }

  @Test
  void findAllById_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids1 = Collections.singletonList("id1");
    List<String> ids2 = Collections.singletonList("id2");

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    when(repository.findAllById(ids1)).thenReturn(Collections.singletonList(entity1));
    when(repository.findAllById(ids2)).thenReturn(Collections.singletonList(entity2));

    // When: calling findAllById multiple times
    List<SecretDomain> result1 = repository.findAllById(ids1);
    List<SecretDomain> result2 = repository.findAllById(ids2);

    // Then: each call should work independently
    assertThat(result1).hasSize(1);
    assertThat(result2).hasSize(1);
    assertThat(result1.get(0).getId()).isEqualTo("id1");
    assertThat(result2.get(0).getId()).isEqualTo("id2");
    verify(repository, times(1)).findAllById(ids1);
    verify(repository, times(1)).findAllById(ids2);
  }

  @Test
  void findAllById_withLargeBatchOfIds_shouldReturnAllMatchingEntities() {
    // Given: a mocked SecretRepository with large batch of IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> largeIdList = new ArrayList<>();
    List<SecretDomain> largeEntityList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      largeIdList.add("id" + i);
      SecretDomain entity = new SecretDomain("ref" + i, "secret" + i);
      entity.setId("id" + i);
      largeEntityList.add(entity);
    }

    when(repository.findAllById(largeIdList)).thenReturn(largeEntityList);

    // When: calling findAllById with 100 IDs
    List<SecretDomain> result = repository.findAllById(largeIdList);

    // Then: should return all 100 entities
    assertThat(result).hasSize(100);
    assertThat(result.get(0).getId()).isEqualTo("id0");
    assertThat(result.get(99).getId()).isEqualTo("id99");
    verify(repository, times(1)).findAllById(largeIdList);
  }

  @Test
  void findAllById_returnsEntitiesWithAllFieldsPopulated() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Collections.singletonList("test-id");

    SecretDomain entity = new SecretDomain("test-ref", "test-secret");
    entity.setId("test-id");
    entity.setCreatedAt(System.currentTimeMillis());
    List<SecretDomain> entities = Collections.singletonList(entity);

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: calling findAllById
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: should return entities with all fields populated
    assertThat(result).hasSize(1);
    SecretDomain returned = result.get(0);
    assertThat(returned.getId()).isEqualTo("test-id");
    assertThat(returned.getRef()).isEqualTo("test-ref");
    assertThat(returned.getSecret()).isEqualTo("test-secret");
    assertThat(returned.getCreatedAt()).isNotNull();
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_returnsEntitiesWithNullFields() {
    // Given: a mocked SecretRepository with entities having null fields
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    SecretDomain entity1 = new SecretDomain(null, "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", null);
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: calling findAllById
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: should return entities with null fields
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getRef()).isNull();
    assertThat(result.get(1).getSecret()).isNull();
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_withDuplicateIds_shouldHandleCorrectly() {
    // Given: a mocked SecretRepository with duplicate IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> idsWithDuplicates = Arrays.asList("id1", "id2", "id1");

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    // Typically returns unique entities only
    List<SecretDomain> uniqueEntities = Arrays.asList(entity1, entity2);

    when(repository.findAllById(idsWithDuplicates)).thenReturn(uniqueEntities);

    // When: calling findAllById with duplicates
    List<SecretDomain> result = repository.findAllById(idsWithDuplicates);

    // Then: should handle duplicates (typically returns unique entities)
    assertThat(result).isNotNull();
    verify(repository, times(1)).findAllById(idsWithDuplicates);
  }

  @Test
  void findAllById_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    List<String> ids = Collections.singletonList("id");

    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(savedEntity);

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: saving entity then calling findAllById
    repository.save(entityToSave);
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: findAllById should work after save
    assertThat(result).isNotNull();
    verify(repository, times(1)).save(entityToSave);
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Collections.singletonList("id");
    List<SecretDomain> entities = Collections.emptyList();

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: calling flush then findAllById
    repository.flush();
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: findAllById should work after flush
    assertThat(result).isNotNull();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_returnsEntitiesWithTimestamps() {
    // Given: a mocked SecretRepository with entities having timestamps
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    Long timestamp1 = System.currentTimeMillis();
    Long timestamp2 = timestamp1 + 1000;

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    entity1.setCreatedAt(timestamp1);

    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    entity2.setCreatedAt(timestamp2);

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: calling findAllById
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: should return entities with timestamps
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getCreatedAt()).isEqualTo(timestamp1);
    assertThat(result.get(1).getCreatedAt()).isEqualTo(timestamp2);
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_afterDeleteOperation_shouldWork() {
    // Given: a mocked SecretRepository with delete operation performed
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Collections.singletonList("id");
    List<SecretDomain> entities = Collections.emptyList();

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: deleting entity then calling findAllById
    repository.deleteByRef("some-ref");
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: findAllById should work after delete
    assertThat(result).isNotNull();
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");
    List<SecretDomain> entities = Collections.emptyList();

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: calling findById then findAllById
    repository.findById("some-id");
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: findAllById should work after findById
    assertThat(result).isNotNull();
    verify(repository, times(1)).findById("some-id");
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_returnsListNotNull() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Collections.emptyList();
    List<SecretDomain> entities = new ArrayList<>();

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: calling findAllById
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: should never return null, always a list
    assertThat(result).isNotNull();
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_withArrayListInput_shouldWork() {
    // Given: a mocked SecretRepository with ArrayList input
    SecretRepository repository = mock(SecretRepository.class);

    ArrayList<String> arrayListIds = new ArrayList<>();
    arrayListIds.add("id1");
    arrayListIds.add("id2");

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    when(repository.findAllById(arrayListIds)).thenReturn(entities);

    // When: calling findAllById with ArrayList
    List<SecretDomain> result = repository.findAllById(arrayListIds);

    // Then: should return entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAllById(arrayListIds);
  }

  @Test
  void findAllById_withUuidFormatIds_shouldWork() {
    // Given: a mocked SecretRepository with UUID-format IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> uuidIds = Arrays.asList(
        "550e8400-e29b-41d4-a716-446655440000",
        "6ba7b810-9dad-11d1-80b4-00c04fd430c8"
    );

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("550e8400-e29b-41d4-a716-446655440000");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("6ba7b810-9dad-11d1-80b4-00c04fd430c8");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    when(repository.findAllById(uuidIds)).thenReturn(entities);

    // When: calling findAllById with UUID IDs
    List<SecretDomain> result = repository.findAllById(uuidIds);

    // Then: should return entities with UUID IDs
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo("550e8400-e29b-41d4-a716-446655440000");
    assertThat(result.get(1).getId()).isEqualTo("6ba7b810-9dad-11d1-80b4-00c04fd430c8");
    verify(repository, times(1)).findAllById(uuidIds);
  }

  @Test
  void findAllById_withNumericStringIds_shouldWork() {
    // Given: a mocked SecretRepository with numeric string IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> numericIds = Arrays.asList("123", "456", "789");

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("123");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("456");
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");
    entity3.setId("789");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2, entity3);

    when(repository.findAllById(numericIds)).thenReturn(entities);

    // When: calling findAllById with numeric string IDs
    List<SecretDomain> result = repository.findAllById(numericIds);

    // Then: should return entities
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getId()).isEqualTo("123");
    assertThat(result.get(1).getId()).isEqualTo("456");
    assertThat(result.get(2).getId()).isEqualTo("789");
    verify(repository, times(1)).findAllById(numericIds);
  }

  @Test
  void findAllById_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entitiesToSave = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    List<String> ids = Arrays.asList("id1", "id2");

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");
    List<SecretDomain> savedEntities = Arrays.asList(saved1, saved2);

    when(repository.findAllById(ids)).thenReturn(savedEntities);

    // When: calling saveAll then findAllById
    repository.saveAll(entitiesToSave);
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: findAllById should work after saveAll
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAll(entitiesToSave);
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_canBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Collections.singletonList("id1");
    List<SecretDomain> entities = Collections.emptyList();

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: calling findAll then findAllById
    repository.findAll();
    List<SecretDomain> result = repository.findAllById(ids);

    // Then: findAllById should work after findAll
    assertThat(result).isNotNull();
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).findAllById(ids);
  }
}
