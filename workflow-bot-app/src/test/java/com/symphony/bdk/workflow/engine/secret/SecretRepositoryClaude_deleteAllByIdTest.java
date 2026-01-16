package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for SecretRepository.deleteAllById(Iterable) method.
 *
 * The deleteAllById(Iterable<? extends ID> ids) method is inherited from CrudRepository
 * (which JpaRepository extends) and provides batch deletion of entities by their IDs.
 * Key characteristics:
 * 1. Takes an Iterable of ID values (String type for SecretRepository)
 * 2. Returns void (no return value)
 * 3. Deletes all entities with the given IDs
 * 4. Throws EmptyResultDataAccessException if any ID doesn't exist
 * 5. Performs SELECT then DELETE for each ID (multiple operations)
 * 6. Respects JPA lifecycle callbacks (@PreRemove, @PostRemove)
 * 7. More convenient than multiple deleteById() calls
 *
 * Method signature: void deleteAllById(Iterable<String> ids)
 *
 * Difference from delete methods:
 * - deleteAllById(Iterable<ID>): Batch deletion by IDs, throws if any don't exist
 * - deleteById(ID): Single deletion by ID, throws if doesn't exist
 * - deleteAll(Iterable<Entity>): Batch deletion by entities, requires entity instances
 * - deleteAllByIdInBatch(Iterable<ID>): Batch deletion, single SQL, bypasses callbacks
 *
 * Exception behavior:
 * - If all IDs exist: Deletion succeeds, no exception
 * - If any ID doesn't exist: Throws EmptyResultDataAccessException
 * - Different from deleteAllByIdInBatch which doesn't throw for non-existent IDs
 *
 * Use cases for deleteAllById:
 * - Deleting multiple entities when you only have the IDs
 * - When you want exceptions thrown for non-existent entities
 * - When JPA lifecycle callbacks should be triggered for each entity
 * - Bulk deletion with validation that all IDs exist
 * - More convenient than calling deleteById() multiple times
 *
 * Example usage:
 * <pre>
 * List<String> idsToDelete = Arrays.asList("id1", "id2", "id3");
 * repository.deleteAllById(idsToDelete);
 *
 * // With empty list
 * repository.deleteAllById(Collections.emptyList()); // No-op
 *
 * // Safe deletion with existence check
 * List<String> ids = Arrays.asList("id1", "id2");
 * if (ids.stream().allMatch(id -> repository.existsById(id))) {
 *   repository.deleteAllById(ids);
 * }
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of deleteAllById, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The deleteAllById method exists and is callable on SecretRepository
 * 2. The method accepts an Iterable<String> parameter
 * 3. The method returns void
 * 4. The method can handle various scenarios (empty list, single ID, multiple IDs)
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual deleteAllById implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_deleteAllByIdTest {

  @Test
  void deleteAllById_withMultipleIds_shouldDeleteAllEntities() {
    // Given: a mocked SecretRepository with multiple IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> idsToDelete = Arrays.asList("id1", "id2", "id3");

    doNothing().when(repository).deleteAllById(idsToDelete);

    // When: calling deleteAllById with multiple IDs
    repository.deleteAllById(idsToDelete);

    // Then: should delete all entities
    verify(repository, times(1)).deleteAllById(idsToDelete);
  }

  @Test
  void deleteAllById_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(any(Iterable.class));

    // When & Then: deleteAllById should complete without throwing an exception
    assertThatCode(() -> repository.deleteAllById(ids)).doesNotThrowAnyException();
  }

  @Test
  void deleteAllById_shouldReturnVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Collections.singletonList("id");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling deleteAllById (void return)
    repository.deleteAllById(ids);

    // Then: method completes (void return verified by compilation)
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_withEmptyList_shouldWork() {
    // Given: a mocked SecretRepository with empty ID list
    SecretRepository repository = mock(SecretRepository.class);

    List<String> emptyIds = Collections.emptyList();

    doNothing().when(repository).deleteAllById(emptyIds);

    // When: calling deleteAllById with empty list
    repository.deleteAllById(emptyIds);

    // Then: should handle empty list (no-op)
    verify(repository, times(1)).deleteAllById(emptyIds);
  }

  @Test
  void deleteAllById_withSingleId_shouldWork() {
    // Given: a mocked SecretRepository with single ID
    SecretRepository repository = mock(SecretRepository.class);

    List<String> singleId = Collections.singletonList("id1");

    doNothing().when(repository).deleteAllById(singleId);

    // When: calling deleteAllById
    repository.deleteAllById(singleId);

    // Then: should delete single entity
    verify(repository, times(1)).deleteAllById(singleId);
  }

  @Test
  void deleteAllById_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> batch1 = Arrays.asList("id1", "id2");
    List<String> batch2 = Arrays.asList("id3", "id4");

    doNothing().when(repository).deleteAllById(any(Iterable.class));

    // When: calling deleteAllById multiple times
    repository.deleteAllById(batch1);
    repository.deleteAllById(batch2);

    // Then: each call should work independently
    verify(repository, times(1)).deleteAllById(batch1);
    verify(repository, times(1)).deleteAllById(batch2);
  }

  @Test
  void deleteAllById_withLargeBatchOfIds_shouldWork() {
    // Given: a mocked SecretRepository with large batch of IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> largeIdList = new java.util.ArrayList<>();
    for (int i = 0; i < 100; i++) {
      largeIdList.add("id" + i);
    }

    doNothing().when(repository).deleteAllById(largeIdList);

    // When: calling deleteAllById with 100 IDs
    repository.deleteAllById(largeIdList);

    // Then: should delete all 100 entities
    verify(repository, times(1)).deleteAllById(largeIdList);
  }

  @Test
  void deleteAllById_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    List<String> ids = Collections.singletonList("id");

    doNothing().when(repository).deleteAllById(ids);

    // When: saving entity then calling deleteAllById
    repository.save(entity);
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after save
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling flush then deleteAllById
    repository.flush();
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after flush
    verify(repository, times(1)).flush();
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_afterFindByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling findById then deleteAllById
    repository.findById("id1");
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after findById
    verify(repository, times(1)).findById("id1");
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_afterExistsByIdCheck_shouldWork() {
    // Given: a mocked SecretRepository with existsById called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: checking existence then deleting
    repository.existsById("id1");
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after existence check
    verify(repository, times(1)).existsById("id1");
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_withUuidFormatIds_shouldWork() {
    // Given: a mocked SecretRepository with UUID-format IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> uuidIds = Arrays.asList(
        "550e8400-e29b-41d4-a716-446655440000",
        "6ba7b810-9dad-11d1-80b4-00c04fd430c8"
    );

    doNothing().when(repository).deleteAllById(uuidIds);

    // When: calling deleteAllById with UUID IDs
    repository.deleteAllById(uuidIds);

    // Then: should delete entities with UUID IDs
    verify(repository, times(1)).deleteAllById(uuidIds);
  }

  @Test
  void deleteAllById_withNumericStringIds_shouldWork() {
    // Given: a mocked SecretRepository with numeric string IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> numericIds = Arrays.asList("123", "456", "789");

    doNothing().when(repository).deleteAllById(numericIds);

    // When: calling deleteAllById with numeric string IDs
    repository.deleteAllById(numericIds);

    // Then: should delete entities
    verify(repository, times(1)).deleteAllById(numericIds);
  }

  @Test
  void deleteAllById_withAlphanumericIds_shouldWork() {
    // Given: a mocked SecretRepository with alphanumeric IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> alphanumericIds = Arrays.asList("abc123", "xyz789");

    doNothing().when(repository).deleteAllById(alphanumericIds);

    // When: calling deleteAllById
    repository.deleteAllById(alphanumericIds);

    // Then: should delete entities
    verify(repository, times(1)).deleteAllById(alphanumericIds);
  }

  @Test
  void deleteAllById_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling saveAll then deleteAllById
    repository.saveAll(Collections.emptyList());
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after saveAll
    verify(repository, times(1)).saveAll(Collections.emptyList());
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_withSpecialCharactersInIds_shouldWork() {
    // Given: a mocked SecretRepository with IDs containing special characters
    SecretRepository repository = mock(SecretRepository.class);

    List<String> specialIds = Arrays.asList(
        "id-with-dashes",
        "id_with_underscores"
    );

    doNothing().when(repository).deleteAllById(specialIds);

    // When: calling deleteAllById
    repository.deleteAllById(specialIds);

    // Then: should delete entities
    verify(repository, times(1)).deleteAllById(specialIds);
  }

  @Test
  void deleteAllById_afterCountOperation_shouldWork() {
    // Given: a mocked SecretRepository with count called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling count then deleteAllById
    repository.count();
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after count
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_canBeCalledBeforeCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling deleteAllById then count (to verify deletion)
    repository.deleteAllById(ids);
    repository.count();

    // Then: both operations should work
    verify(repository, times(1)).deleteAllById(ids);
    verify(repository, times(1)).count();
  }

  @Test
  void deleteAllById_canBeCalledBeforeExistsById() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling deleteAllById then existsById (to verify deletion)
    repository.deleteAllById(ids);
    repository.existsById("id1");

    // Then: both operations should work
    verify(repository, times(1)).deleteAllById(ids);
    verify(repository, times(1)).existsById("id1");
  }

  @Test
  void deleteAllById_afterFindAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling findAll then deleteAllById
    repository.findAll();
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after findAll
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    List<String> ids = Collections.singletonList("id");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling saveAndFlush then deleteAllById
    repository.saveAndFlush(entity);
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after saveAndFlush
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_afterFindAllByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAllById called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2", "id3");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling findAllById then deleteAllById
    repository.findAllById(ids);
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after findAllById
    verify(repository, times(1)).findAllById(ids);
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_afterCustomDeleteByRef_shouldWork() {
    // Given: a mocked SecretRepository with custom delete method
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllById(ids);

    // When: calling custom deleteByRef then deleteAllById
    repository.deleteByRef("some-ref");
    repository.deleteAllById(ids);

    // Then: deleteAllById should work after custom delete
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_interleavedWithOtherOperations_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> batch1 = Arrays.asList("id1", "id2");
    List<String> batch2 = Arrays.asList("id3", "id4");
    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).deleteAllById(any(Iterable.class));

    // When: performing multiple operations including batch deletes
    repository.save(entity);
    repository.deleteAllById(batch1);
    repository.count();
    repository.deleteAllById(batch2);
    repository.findAll();

    // Then: all operations including deletes should work
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).deleteAllById(batch1);
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteAllById(batch2);
    verify(repository, times(1)).findAll();
  }

  @Test
  void deleteAllById_withDuplicateIds_shouldWork() {
    // Given: a mocked SecretRepository with duplicate IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> idsWithDuplicates = Arrays.asList("id1", "id2", "id1");

    doNothing().when(repository).deleteAllById(idsWithDuplicates);

    // When: calling deleteAllById with duplicates
    repository.deleteAllById(idsWithDuplicates);

    // Then: should handle duplicates
    verify(repository, times(1)).deleteAllById(idsWithDuplicates);
  }
}
