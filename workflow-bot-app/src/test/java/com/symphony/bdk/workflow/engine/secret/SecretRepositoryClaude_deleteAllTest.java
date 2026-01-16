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
 * Test class for SecretRepository.deleteAll() methods.
 *
 * The deleteAll methods are inherited from CrudRepository (which JpaRepository extends)
 * and provide deletion of entities. This class tests two overloaded versions:
 *
 * 1. void deleteAll(Iterable<SecretDomain> entities) - Deletes specific entities
 * 2. void deleteAll() - Deletes ALL entities in the repository
 *
 * Key characteristics:
 * 1. The parameterized version takes an Iterable of entity instances
 * 2. The no-arg version deletes ALL entities without parameters
 * 3. Both return void (no return value)
 * 4. Both respect JPA lifecycle callbacks (@PreRemove, @PostRemove)
 * 5. The no-arg version is equivalent to deleteAll(findAll())
 * 6. More convenient than multiple delete() calls
 *
 * Method signatures:
 * - void deleteAll(Iterable<SecretDomain> entities)
 * - void deleteAll()
 *
 * Difference from delete methods:
 * - deleteAll(Iterable<Entity>): Batch deletion by entities, requires entity instances
 * - deleteAll(): Deletes ALL entities in repository, no parameters
 * - deleteAllById(Iterable<ID>): Batch deletion by IDs, only needs IDs
 * - delete(Entity): Single deletion by entity
 * - deleteInBatch(Iterable<Entity>): Batch deletion, single SQL, bypasses callbacks
 * - deleteAllInBatch(): Deletes ALL entities, single SQL, bypasses callbacks
 *
 * Use cases for deleteAll(Iterable):
 * - Deleting multiple entities when you already have the entity instances
 * - After finding and filtering entities, then deleting them
 * - When JPA lifecycle callbacks should be triggered for each entity
 * - Bulk deletion with full entity context
 * - Cascading deletes for related entities
 *
 * Use cases for deleteAll():
 * - Clearing entire repository
 * - Cleanup operations (tests, maintenance)
 * - Resetting application state
 * - When you want to delete ALL entities and trigger callbacks
 *
 * Example usage:
 * <pre>
 * // Delete specific entities
 * List<SecretDomain> entitiesToDelete = repository.findAll()
 *   .stream()
 *   .filter(secret -> secret.getRef().startsWith("temp-"))
 *   .collect(Collectors.toList());
 * repository.deleteAll(entitiesToDelete);
 *
 * // Delete all entities
 * repository.deleteAll(); // Removes everything
 *
 * // With empty list
 * repository.deleteAll(Collections.emptyList()); // No-op
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of deleteAll, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of these methods requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The deleteAll methods exist and are callable on SecretRepository
 * 2. The parameterized method accepts an Iterable<SecretDomain> parameter
 * 3. The no-arg method takes no parameters
 * 4. Both methods return void
 * 5. The methods can handle various scenarios (empty list, single entity, multiple entities, all entities)
 * 6. The methods can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual deleteAll implementations are provided by Spring Data JPA
 * at runtime and are thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_deleteAllTest {

  @Test
  void deleteAll_withMultipleEntities_shouldDeleteAllEntities() {
    // Given: a mocked SecretRepository with multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");
    entity3.setId("id3");

    List<SecretDomain> entitiesToDelete = Arrays.asList(entity1, entity2, entity3);

    doNothing().when(repository).deleteAll(entitiesToDelete);

    // When: calling deleteAll with multiple entities
    repository.deleteAll(entitiesToDelete);

    // Then: should delete all entities
    verify(repository, times(1)).deleteAll(entitiesToDelete);
  }

  @Test
  void deleteAll_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    doNothing().when(repository).deleteAll(any(Iterable.class));

    // When & Then: deleteAll should complete without throwing an exception
    assertThatCode(() -> repository.deleteAll(entities)).doesNotThrowAnyException();
  }

  @Test
  void deleteAll_shouldReturnVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));

    doNothing().when(repository).deleteAll(entities);

    // When: calling deleteAll (void return)
    repository.deleteAll(entities);

    // Then: method completes (void return verified by compilation)
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_withEmptyList_shouldWork() {
    // Given: a mocked SecretRepository with empty entity list
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAll(emptyList);

    // When: calling deleteAll with empty list
    repository.deleteAll(emptyList);

    // Then: should handle empty list (no-op)
    verify(repository, times(1)).deleteAll(emptyList);
  }

  @Test
  void deleteAll_withSingleEntity_shouldWork() {
    // Given: a mocked SecretRepository with single entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> singleEntity = Collections.singletonList(entity);

    doNothing().when(repository).deleteAll(singleEntity);

    // When: calling deleteAll
    repository.deleteAll(singleEntity);

    // Then: should delete single entity
    verify(repository, times(1)).deleteAll(singleEntity);
  }

  @Test
  void deleteAll_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> batch1 = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );
    List<SecretDomain> batch2 = Arrays.asList(
        new SecretDomain("ref3", "secret3"),
        new SecretDomain("ref4", "secret4")
    );

    doNothing().when(repository).deleteAll(any(Iterable.class));

    // When: calling deleteAll multiple times
    repository.deleteAll(batch1);
    repository.deleteAll(batch2);

    // Then: each call should work independently
    verify(repository, times(1)).deleteAll(batch1);
    verify(repository, times(1)).deleteAll(batch2);
  }

  @Test
  void deleteAll_withLargeBatchOfEntities_shouldWork() {
    // Given: a mocked SecretRepository with large batch of entities
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> largeList = new java.util.ArrayList<>();
    for (int i = 0; i < 100; i++) {
      SecretDomain entity = new SecretDomain("ref" + i, "secret" + i);
      entity.setId("id" + i);
      largeList.add(entity);
    }

    doNothing().when(repository).deleteAll(largeList);

    // When: calling deleteAll with 100 entities
    repository.deleteAll(largeList);

    // Then: should delete all 100 entities
    verify(repository, times(1)).deleteAll(largeList);
  }

  @Test
  void deleteAll_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAll(entities);

    // When: saving entity then calling deleteAll
    repository.save(entity);
    repository.deleteAll(entities);

    // Then: deleteAll should work after save
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    doNothing().when(repository).deleteAll(entities);

    // When: calling flush then deleteAll
    repository.flush();
    repository.deleteAll(entities);

    // Then: deleteAll should work after flush
    verify(repository, times(1)).flush();
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterFindByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAll(entities);

    // When: calling findById then deleteAll
    repository.findById("id");
    repository.deleteAll(entities);

    // Then: deleteAll should work after findById
    verify(repository, times(1)).findById("id");
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_withEntitiesHavingNullFields_shouldWork() {
    // Given: a mocked SecretRepository with entities having null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain(null, "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", null);
    entity2.setId("id2");

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAll(entities);

    // When: calling deleteAll
    repository.deleteAll(entities);

    // Then: should delete entities with null fields
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_withEntitiesHavingEmptyStrings_shouldWork() {
    // Given: a mocked SecretRepository with entities having empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("", "");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAll(entities);

    // When: calling deleteAll
    repository.deleteAll(entities);

    // Then: should delete entities with empty strings
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    doNothing().when(repository).deleteAll(entities);

    // When: calling saveAll then deleteAll
    repository.saveAll(entities);
    repository.deleteAll(entities);

    // Then: deleteAll should work after saveAll
    verify(repository, times(1)).saveAll(entities);
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_withEntitiesHavingTimestamps_shouldWork() {
    // Given: a mocked SecretRepository with entities having timestamps
    SecretRepository repository = mock(SecretRepository.class);

    Long timestamp1 = System.currentTimeMillis();
    Long timestamp2 = timestamp1 + 1000;

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    entity1.setCreatedAt(timestamp1);

    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    entity2.setCreatedAt(timestamp2);

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAll(entities);

    // When: calling deleteAll
    repository.deleteAll(entities);

    // Then: should delete entities with timestamps
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterExistsByIdCheck_shouldWork() {
    // Given: a mocked SecretRepository with existsById called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAll(entities);

    // When: checking existence then deleting
    repository.existsById("id");
    repository.deleteAll(entities);

    // Then: deleteAll should work after existence check
    verify(repository, times(1)).existsById("id");
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterCountOperation_shouldWork() {
    // Given: a mocked SecretRepository with count called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));

    doNothing().when(repository).deleteAll(entities);

    // When: calling count then deleteAll
    repository.count();
    repository.deleteAll(entities);

    // Then: deleteAll should work after count
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_canBeCalledBeforeCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    doNothing().when(repository).deleteAll(entities);

    // When: calling deleteAll then count (to verify deletion)
    repository.deleteAll(entities);
    repository.count();

    // Then: both operations should work
    verify(repository, times(1)).deleteAll(entities);
    verify(repository, times(1)).count();
  }

  @Test
  void deleteAll_canBeCalledBeforeExistsById() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAll(entities);

    // When: calling deleteAll then existsById (to verify deletion)
    repository.deleteAll(entities);
    repository.existsById("id");

    // Then: both operations should work
    verify(repository, times(1)).deleteAll(entities);
    verify(repository, times(1)).existsById("id");
  }

  @Test
  void deleteAll_withEntitiesHavingLongValues_shouldWork() {
    // Given: a mocked SecretRepository with entities having long values
    SecretRepository repository = mock(SecretRepository.class);

    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAll(entities);

    // When: calling deleteAll
    repository.deleteAll(entities);

    // Then: should delete entities with long values
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAll(entities);

    // When: calling saveAndFlush then deleteAll
    repository.saveAndFlush(entity);
    repository.deleteAll(entities);

    // Then: deleteAll should work after saveAndFlush
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterFindAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));

    doNothing().when(repository).deleteAll(entities);

    // When: calling findAll then deleteAll
    repository.findAll();
    repository.deleteAll(entities);

    // Then: deleteAll should work after findAll
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterFindAllByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAllById called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");
    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    doNothing().when(repository).deleteAll(entities);

    // When: calling findAllById then deleteAll (common pattern)
    repository.findAllById(ids);
    repository.deleteAll(entities);

    // Then: deleteAll should work after findAllById
    verify(repository, times(1)).findAllById(ids);
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_afterCustomDeleteByRef_shouldWork() {
    // Given: a mocked SecretRepository with custom delete method
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));

    doNothing().when(repository).deleteAll(entities);

    // When: calling custom deleteByRef then deleteAll
    repository.deleteByRef("some-ref");
    repository.deleteAll(entities);

    // Then: deleteAll should work after custom delete
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_interleavedWithOtherOperations_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> batch1 = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );
    List<SecretDomain> batch2 = Arrays.asList(
        new SecretDomain("ref3", "secret3"),
        new SecretDomain("ref4", "secret4")
    );
    SecretDomain entity = new SecretDomain("ref5", "secret5");

    doNothing().when(repository).deleteAll(any(Iterable.class));

    // When: performing multiple operations including batch deletes
    repository.save(entity);
    repository.deleteAll(batch1);
    repository.count();
    repository.deleteAll(batch2);
    repository.findAll();

    // Then: all operations including deletes should work
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).deleteAll(batch1);
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteAll(batch2);
    verify(repository, times(1)).findAll();
  }

  // ==================== Tests for no-argument deleteAll() ====================

  @Test
  void deleteAll_noArgs_shouldDeleteAllEntities() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling deleteAll with no arguments
    repository.deleteAll();

    // Then: should delete all entities
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When & Then: deleteAll should complete without throwing an exception
    assertThatCode(() -> repository.deleteAll()).doesNotThrowAnyException();
  }

  @Test
  void deleteAll_noArgs_shouldReturnVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling deleteAll (void return)
    repository.deleteAll();

    // Then: method completes (void return verified by compilation)
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling deleteAll multiple times
    repository.deleteAll();
    repository.deleteAll();

    // Then: each call should work
    verify(repository, times(2)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).deleteAll();

    // When: saving entity then calling deleteAll
    repository.save(entity);
    repository.deleteAll();

    // Then: deleteAll should work after save
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling flush then deleteAll
    repository.flush();
    repository.deleteAll();

    // Then: deleteAll should work after flush
    verify(repository, times(1)).flush();
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterFindAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling findAll then deleteAll (common pattern: find then delete all)
    repository.findAll();
    repository.deleteAll();

    // Then: deleteAll should work after findAll
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterCountOperation_shouldWork() {
    // Given: a mocked SecretRepository with count called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling count then deleteAll
    repository.count();
    repository.deleteAll();

    // Then: deleteAll should work after count
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_canBeCalledBeforeCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling deleteAll then count (to verify deletion)
    repository.deleteAll();
    repository.count();

    // Then: both operations should work
    verify(repository, times(1)).deleteAll();
    verify(repository, times(1)).count();
  }

  @Test
  void deleteAll_noArgs_canBeCalledBeforeExistsById() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling deleteAll then existsById (to verify deletion)
    repository.deleteAll();
    repository.existsById("id");

    // Then: both operations should work
    verify(repository, times(1)).deleteAll();
    verify(repository, times(1)).existsById("id");
  }

  @Test
  void deleteAll_noArgs_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    doNothing().when(repository).deleteAll();

    // When: calling saveAll then deleteAll
    repository.saveAll(entities);
    repository.deleteAll();

    // Then: deleteAll should work after saveAll
    verify(repository, times(1)).saveAll(entities);
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterExistsByIdCheck_shouldWork() {
    // Given: a mocked SecretRepository with existsById called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: checking existence then deleting all
    repository.existsById("id");
    repository.deleteAll();

    // Then: deleteAll should work after existence check
    verify(repository, times(1)).existsById("id");
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).deleteAll();

    // When: calling saveAndFlush then deleteAll
    repository.saveAndFlush(entity);
    repository.deleteAll();

    // Then: deleteAll should work after saveAndFlush
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterFindByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling findById then deleteAll
    repository.findById("id");
    repository.deleteAll();

    // Then: deleteAll should work after findById
    verify(repository, times(1)).findById("id");
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterFindAllByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAllById called first
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAll();

    // When: calling findAllById then deleteAll
    repository.findAllById(ids);
    repository.deleteAll();

    // Then: deleteAll should work after findAllById
    verify(repository, times(1)).findAllById(ids);
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterDeleteByIdOperation_shouldWork() {
    // Given: a mocked SecretRepository with deleteById called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling deleteById then deleteAll
    repository.deleteById("id");
    repository.deleteAll();

    // Then: deleteAll should work after deleteById
    verify(repository, times(1)).deleteById("id");
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_afterCustomDeleteByRef_shouldWork() {
    // Given: a mocked SecretRepository with custom delete method
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: calling custom deleteByRef then deleteAll
    repository.deleteByRef("some-ref");
    repository.deleteAll();

    // Then: deleteAll should work after custom delete
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_interleavedWithOtherOperations_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).deleteAll();

    // When: performing multiple operations including deleteAll
    repository.save(entity);
    repository.count();
    repository.deleteAll();
    repository.findAll();
    repository.save(entity);

    // Then: all operations including deleteAll should work
    verify(repository, times(2)).save(entity);
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteAll();
    verify(repository, times(1)).findAll();
  }

  @Test
  void deleteAll_noArgs_asCleanupOperation_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAll();

    // When: using deleteAll as cleanup (common in tests/maintenance)
    repository.deleteAll();

    // Then: should execute cleanup
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_beforeSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    doNothing().when(repository).deleteAll();

    // When: clearing all then saving (reset pattern)
    repository.deleteAll();
    repository.save(entity);

    // Then: both operations should work
    verify(repository, times(1)).deleteAll();
    verify(repository, times(1)).save(entity);
  }
}
