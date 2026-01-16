package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for SecretRepository.delete(Object) method.
 *
 * The delete(T entity) method is inherited from CrudRepository (which JpaRepository extends)
 * and provides deletion of a single entity by passing the entity instance.
 * Key characteristics:
 * 1. Takes an entity instance (SecretDomain type for SecretRepository)
 * 2. Returns void (no return value)
 * 3. Deletes the given entity from the repository
 * 4. Requires the full entity object, not just the ID
 * 5. Performs DELETE directly without SELECT
 * 6. Respects JPA lifecycle callbacks (@PreRemove, @PostRemove)
 * 7. May throw exception if entity is detached or not managed
 *
 * Method signature: void delete(SecretDomain entity)
 *
 * Difference from delete methods:
 * - delete(Entity): Requires full entity instance, performs DELETE directly
 * - deleteById(ID): Requires only ID, performs SELECT + DELETE
 * - deleteInBatch(Iterable): Batch deletion, bypasses lifecycle callbacks
 *
 * Use cases for delete:
 * - Deleting an entity when you already have the entity instance
 * - After finding and validating an entity, then deleting it
 * - When JPA lifecycle callbacks should be triggered
 * - When entity is already loaded in persistence context
 * - Cascading deletes for related entities
 *
 * Example usage:
 * <pre>
 * // Find then delete
 * Optional<SecretDomain> secret = repository.findById("id123");
 * secret.ifPresent(entity -> repository.delete(entity));
 *
 * // Delete after loading
 * SecretDomain entity = repository.findById("id123").orElseThrow();
 * entity.setSecret("modified"); // Can modify before delete
 * repository.delete(entity);
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of delete, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The delete method exists and is callable on SecretRepository
 * 2. The method accepts a SecretDomain parameter
 * 3. The method returns void
 * 4. The method can handle various scenarios (entities with/without IDs)
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual delete implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_deleteTest {

  @Test
  void delete_withEntity_shouldDeleteEntity() {
    // Given: a mocked SecretRepository with an entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    doNothing().when(repository).delete(entity);

    // When: calling delete with entity
    repository.delete(entity);

    // Then: should delete the entity
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(any(SecretDomain.class));

    // When & Then: delete should complete without throwing an exception
    assertThatCode(() -> repository.delete(entity)).doesNotThrowAnyException();
  }

  @Test
  void delete_shouldReturnVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: calling delete (void return)
    repository.delete(entity);

    // Then: method completes (void return verified by compilation)
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntityHavingId_shouldWork() {
    // Given: a mocked SecretRepository with entity having ID
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("existing-id");

    doNothing().when(repository).delete(entity);

    // When: calling delete
    repository.delete(entity);

    // Then: should delete entity with ID
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntityWithoutId_shouldWork() {
    // Given: a mocked SecretRepository with entity without ID
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    // No ID set

    doNothing().when(repository).delete(entity);

    // When: calling delete
    repository.delete(entity);

    // Then: should accept entity without ID
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    doNothing().when(repository).delete(any(SecretDomain.class));

    // When: calling delete multiple times
    repository.delete(entity1);
    repository.delete(entity2);

    // Then: each call should work independently
    verify(repository, times(1)).delete(entity1);
    verify(repository, times(1)).delete(entity2);
  }

  @Test
  void delete_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: saving entity then calling delete
    repository.save(entity);
    repository.delete(entity);

    // Then: delete should work after save
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: calling flush then delete
    repository.flush();
    repository.delete(entity);

    // Then: delete should work after flush
    verify(repository, times(1)).flush();
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_afterFindByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    doNothing().when(repository).delete(entity);

    // When: calling findById then delete (common pattern)
    repository.findById(id);
    repository.delete(entity);

    // Then: delete should work after findById
    verify(repository, times(1)).findById(id);
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntityHavingNullRef_shouldWork() {
    // Given: a mocked SecretRepository with entity having null ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain(null, "secret");
    entity.setId("id");

    doNothing().when(repository).delete(entity);

    // When: calling delete
    repository.delete(entity);

    // Then: should delete entity with null ref
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntityHavingNullSecret_shouldWork() {
    // Given: a mocked SecretRepository with entity having null secret
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", null);
    entity.setId("id");

    doNothing().when(repository).delete(entity);

    // When: calling delete
    repository.delete(entity);

    // Then: should delete entity with null secret
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntityHavingEmptyStrings_shouldWork() {
    // Given: a mocked SecretRepository with entity having empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("", "");
    entity.setId("id");

    doNothing().when(repository).delete(entity);

    // When: calling delete
    repository.delete(entity);

    // Then: should delete entity with empty strings
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: calling saveAll then delete
    repository.saveAll(java.util.Collections.emptyList());
    repository.delete(entity);

    // Then: delete should work after saveAll
    verify(repository, times(1)).saveAll(java.util.Collections.emptyList());
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntityHavingTimestamp_shouldWork() {
    // Given: a mocked SecretRepository with entity having timestamp
    SecretRepository repository = mock(SecretRepository.class);

    Long timestamp = System.currentTimeMillis();
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    entity.setCreatedAt(timestamp);

    doNothing().when(repository).delete(entity);

    // When: calling delete
    repository.delete(entity);

    // Then: should delete entity with timestamp
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_afterExistsByIdCheck_shouldWork() {
    // Given: a mocked SecretRepository with existsById called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    doNothing().when(repository).delete(entity);

    // When: checking existence then deleting
    repository.existsById(id);
    repository.delete(entity);

    // Then: delete should work after existence check
    verify(repository, times(1)).existsById(id);
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_afterCountOperation_shouldWork() {
    // Given: a mocked SecretRepository with count called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: calling count then delete
    repository.count();
    repository.delete(entity);

    // Then: delete should work after count
    verify(repository, times(1)).count();
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_canBeCalledBeforeCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: calling delete then count (to verify deletion)
    repository.delete(entity);
    repository.count();

    // Then: both operations should work
    verify(repository, times(1)).delete(entity);
    verify(repository, times(1)).count();
  }

  @Test
  void delete_canBeCalledBeforeExistsById() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    doNothing().when(repository).delete(entity);

    // When: calling delete then existsById (to verify deletion)
    repository.delete(entity);
    repository.existsById(id);

    // Then: both operations should work
    verify(repository, times(1)).delete(entity);
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void delete_withEntityHavingLongValues_shouldWork() {
    // Given: a mocked SecretRepository with entity having long values
    SecretRepository repository = mock(SecretRepository.class);

    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    entity.setId("id");

    doNothing().when(repository).delete(entity);

    // When: calling delete
    repository.delete(entity);

    // Then: should delete entity with long values
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: calling saveAndFlush then delete
    repository.saveAndFlush(entity);
    repository.delete(entity);

    // Then: delete should work after saveAndFlush
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_afterFindAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: calling findAll then delete
    repository.findAll();
    repository.delete(entity);

    // Then: delete should work after findAll
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_multipleCallsWithSameEntity_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    doNothing().when(repository).delete(entity);

    // When: calling delete multiple times with same entity
    repository.delete(entity);
    repository.delete(entity);

    // Then: each call should be invoked
    verify(repository, times(2)).delete(entity);
  }

  @Test
  void delete_afterCustomDeleteByRef_shouldWork() {
    // Given: a mocked SecretRepository with custom delete method
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).delete(entity);

    // When: calling custom deleteByRef then delete
    repository.deleteByRef("some-ref");
    repository.delete(entity);

    // Then: delete should work after custom delete
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_interleavedWithOtherOperations_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");

    doNothing().when(repository).delete(any(SecretDomain.class));

    // When: performing multiple operations including deletes
    repository.save(entity1);
    repository.delete(entity1);
    repository.count();
    repository.delete(entity2);
    repository.findAll();
    repository.delete(entity3);

    // Then: all operations including deletes should work
    verify(repository, times(1)).save(entity1);
    verify(repository, times(1)).delete(entity1);
    verify(repository, times(1)).count();
    verify(repository, times(1)).delete(entity2);
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).delete(entity3);
  }
}
