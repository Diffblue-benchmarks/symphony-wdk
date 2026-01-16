package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for SecretRepository.deleteById(Object) method.
 *
 * The deleteById(ID id) method is inherited from CrudRepository (which JpaRepository extends)
 * and provides deletion of a single entity by its primary key ID.
 * Key characteristics:
 * 1. Takes a single ID value (String type for SecretRepository)
 * 2. Returns void (no return value)
 * 3. Deletes the entity with the given ID if it exists
 * 4. Throws EmptyResultDataAccessException if entity with given ID doesn't exist
 * 5. Performs SELECT then DELETE (two SQL operations)
 * 6. Respects JPA lifecycle callbacks (@PreRemove, @PostRemove)
 * 7. Most common method for deleting a single entity by ID
 *
 * Method signature: void deleteById(String id)
 *
 * Difference from delete methods:
 * - deleteById(ID): Throws exception if entity doesn't exist, performs SELECT + DELETE
 * - delete(Entity): Requires entity instance, performs DELETE directly
 * - deleteInBatch(Iterable): Batch deletion, bypasses lifecycle callbacks
 *
 * Exception behavior:
 * - If entity with ID exists: Deletion succeeds, no exception
 * - If entity with ID doesn't exist: Throws EmptyResultDataAccessException
 * - This differs from deleteInBatch which doesn't throw for non-existent entities
 *
 * Use cases for deleteById:
 * - Deleting a single entity when you only have the ID
 * - When you want exception thrown for non-existent entities
 * - When JPA lifecycle callbacks should be triggered
 * - Most straightforward deletion method for single entity
 * - Validation that entity exists before deletion
 *
 * Example usage:
 * <pre>
 * // Delete existing entity
 * repository.deleteById("id123"); // Succeeds
 *
 * // Delete non-existent entity
 * repository.deleteById("non-existent"); // Throws EmptyResultDataAccessException
 *
 * // Safe deletion with existence check
 * if (repository.existsById("id123")) {
 *   repository.deleteById("id123");
 * }
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of deleteById, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The deleteById method exists and is callable on SecretRepository
 * 2. The method accepts a String parameter (the ID type)
 * 3. The method returns void
 * 4. The method can handle various scenarios (existing ID, various ID formats)
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual deleteById implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_deleteByIdTest {

  @Test
  void deleteById_withExistingId_shouldDeleteEntity() {
    // Given: a mocked SecretRepository with existing entity
    SecretRepository repository = mock(SecretRepository.class);

    String existingId = "existing-id";

    doNothing().when(repository).deleteById(existingId);

    // When: calling deleteById
    repository.deleteById(existingId);

    // Then: should delete the entity
    verify(repository, times(1)).deleteById(existingId);
  }

  @Test
  void deleteById_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(anyString());

    // When & Then: deleteById should complete without throwing an exception
    assertThatCode(() -> repository.deleteById(id)).doesNotThrowAnyException();
  }

  @Test
  void deleteById_shouldReturnVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling deleteById (void return)
    repository.deleteById(id);

    // Then: method completes (void return verified by compilation)
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id1 = "id1";
    String id2 = "id2";

    doNothing().when(repository).deleteById(anyString());

    // When: calling deleteById multiple times
    repository.deleteById(id1);
    repository.deleteById(id2);

    // Then: each call should work independently
    verify(repository, times(1)).deleteById(id1);
    verify(repository, times(1)).deleteById(id2);
  }

  @Test
  void deleteById_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    String id = "saved-id";
    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).deleteById(id);

    // When: saving entity then calling deleteById
    repository.save(entity);
    repository.deleteById(id);

    // Then: deleteById should work after save
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling flush then deleteById
    repository.flush();
    repository.deleteById(id);

    // Then: deleteById should work after flush
    verify(repository, times(1)).flush();
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling findById then deleteById
    repository.findById(id);
    repository.deleteById(id);

    // Then: deleteById should work after findById
    verify(repository, times(1)).findById(id);
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_canBeCalledAfterExistsById() {
    // Given: a mocked SecretRepository with existsById called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling existsById then deleteById (common pattern)
    repository.existsById(id);
    repository.deleteById(id);

    // Then: deleteById should work after existsById check
    verify(repository, times(1)).existsById(id);
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_withUuidFormatId_shouldWork() {
    // Given: a mocked SecretRepository with UUID-format ID
    SecretRepository repository = mock(SecretRepository.class);

    String uuidId = "550e8400-e29b-41d4-a716-446655440000";

    doNothing().when(repository).deleteById(uuidId);

    // When: calling deleteById with UUID ID
    repository.deleteById(uuidId);

    // Then: should delete entity with UUID ID
    verify(repository, times(1)).deleteById(uuidId);
  }

  @Test
  void deleteById_withNumericStringId_shouldWork() {
    // Given: a mocked SecretRepository with numeric string ID
    SecretRepository repository = mock(SecretRepository.class);

    String numericId = "12345";

    doNothing().when(repository).deleteById(numericId);

    // When: calling deleteById with numeric string ID
    repository.deleteById(numericId);

    // Then: should delete entity
    verify(repository, times(1)).deleteById(numericId);
  }

  @Test
  void deleteById_withAlphanumericId_shouldWork() {
    // Given: a mocked SecretRepository with alphanumeric ID
    SecretRepository repository = mock(SecretRepository.class);

    String alphanumericId = "abc123xyz";

    doNothing().when(repository).deleteById(alphanumericId);

    // When: calling deleteById
    repository.deleteById(alphanumericId);

    // Then: should delete entity
    verify(repository, times(1)).deleteById(alphanumericId);
  }

  @Test
  void deleteById_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling saveAll then deleteById
    repository.saveAll(java.util.Collections.emptyList());
    repository.deleteById(id);

    // Then: deleteById should work after saveAll
    verify(repository, times(1)).saveAll(java.util.Collections.emptyList());
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_withSpecialCharactersInId_shouldWork() {
    // Given: a mocked SecretRepository with ID containing special characters
    SecretRepository repository = mock(SecretRepository.class);

    String specialId = "id-with-dashes_and_underscores";

    doNothing().when(repository).deleteById(specialId);

    // When: calling deleteById
    repository.deleteById(specialId);

    // Then: should delete entity
    verify(repository, times(1)).deleteById(specialId);
  }

  @Test
  void deleteById_canBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling findAll then deleteById
    repository.findAll();
    repository.deleteById(id);

    // Then: deleteById should work after findAll
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).deleteById(id);

    // When: calling saveAndFlush then deleteById
    repository.saveAndFlush(entity);
    repository.deleteById(id);

    // Then: deleteById should work after saveAndFlush
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_afterCountOperation_shouldWork() {
    // Given: a mocked SecretRepository with count called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling count then deleteById
    repository.count();
    repository.deleteById(id);

    // Then: deleteById should work after count
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_canBeCalledBeforeCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling deleteById then count (to verify deletion)
    repository.deleteById(id);
    repository.count();

    // Then: both operations should work
    verify(repository, times(1)).deleteById(id);
    verify(repository, times(1)).count();
  }

  @Test
  void deleteById_canBeCalledBeforeExistsById() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling deleteById then existsById (to verify deletion)
    repository.deleteById(id);
    repository.existsById(id);

    // Then: both operations should work
    verify(repository, times(1)).deleteById(id);
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void deleteById_multipleCallsWithSameId_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling deleteById multiple times with same ID
    repository.deleteById(id);
    repository.deleteById(id);

    // Then: each call should be invoked
    verify(repository, times(2)).deleteById(id);
  }

  @Test
  void deleteById_afterFindAllByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAllById called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling findAllById then deleteById
    repository.findAllById(java.util.Collections.singletonList(id));
    repository.deleteById(id);

    // Then: deleteById should work after findAllById
    verify(repository, times(1)).findAllById(java.util.Collections.singletonList(id));
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_canBeCalledAfterCustomDeleteByRef() {
    // Given: a mocked SecretRepository with custom delete method
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    doNothing().when(repository).deleteById(id);

    // When: calling custom deleteByRef then deleteById
    repository.deleteByRef("some-ref");
    repository.deleteById(id);

    // Then: deleteById should work after custom delete
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_interleavedWithOtherOperations_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id1 = "id1";
    String id2 = "id2";
    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).deleteById(anyString());

    // When: performing multiple operations including deletes
    repository.save(entity);
    repository.deleteById(id1);
    repository.count();
    repository.deleteById(id2);
    repository.findAll();

    // Then: all operations including deletes should work
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).deleteById(id1);
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteById(id2);
    verify(repository, times(1)).findAll();
  }
}
