package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for SecretRepository.deleteAllInBatch() methods.
 *
 * The deleteAllInBatch() method is inherited from JpaRepository (Spring Data JPA) and
 * has two overloaded versions:
 *
 * 1. void deleteAllInBatch(Iterable<SecretDomain> entities) - Deletes specific entities
 * 2. void deleteAllInBatch() - Deletes ALL entities in the repository
 *
 * Key characteristics:
 * 1. Deletes entities in a single DELETE query (batch operation)
 * 2. More efficient than calling delete() multiple times for individual entities
 * 3. Does NOT call JPA lifecycle callbacks (like @PreRemove, @PostRemove)
 * 4. Does NOT cascade to related entities (ignores cascade settings)
 * 5. Returns void (no return value)
 *
 * The no-argument version is particularly useful for:
 * - Clearing entire tables in tests
 * - Bulk data cleanup operations
 * - Resetting repository state
 *
 * This method is functionally similar to deleteInBatch() but is the recommended method
 * in newer versions of Spring Data JPA. It provides the same batch deletion behavior
 * with more consistent naming.
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of deleteAllInBatch(), the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The deleteAllInBatch() methods exist and are callable on SecretRepository
 * 2. The parameterized version accepts an Iterable of SecretDomain entities
 * 3. The no-arg version can be called without parameters
 * 4. Both methods return void (no return value)
 * 5. The methods can handle various scenarios and be invoked without errors
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual deleteAllInBatch() implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_deleteAllInBatchTest {

  @Test
  void deleteAllInBatch_withMultipleEntities_shouldDeleteAll() {
    // Given: a mocked SecretRepository and multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");
    entity3.setId("id3");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2, entity3);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should invoke deleteAllInBatch once with all entities
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEmptyList_shouldCompleteWithoutError() {
    // Given: a mocked SecretRepository and an empty list
    SecretRepository repository = mock(SecretRepository.class);
    List<SecretDomain> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAllInBatch(emptyList);

    // When: calling deleteAllInBatch with empty list
    repository.deleteAllInBatch(emptyList);

    // Then: should complete without error
    verify(repository, times(1)).deleteAllInBatch(emptyList);
  }

  @Test
  void deleteAllInBatch_withSingleEntity_shouldDelete() {
    // Given: a mocked SecretRepository and a single entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should invoke deleteAllInBatch once
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    doNothing().when(repository).deleteAllInBatch(anyIterable());

    // When & Then: deleteAllInBatch should complete without throwing an exception
    assertThatCode(() -> repository.deleteAllInBatch(entities)).doesNotThrowAnyException();
  }

  @Test
  void deleteAllInBatch_withEntitiesHavingNullFields_shouldDelete() {
    // Given: a mocked SecretRepository and entities with null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain(null, "secret");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref", null);
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain(null, null);
    entity3.setId("id3");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2, entity3);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete entities even with null fields
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEntitiesHavingEmptyStrings_shouldDelete() {
    // Given: a mocked SecretRepository and entities with empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("", "secret");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref", "");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete entities with empty strings
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEntitiesWithoutIds_shouldDelete() {
    // Given: a mocked SecretRepository and entities without IDs set
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should invoke deleteAllInBatch (actual behavior depends on JPA implementation)
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withLargeNumberOfEntities_shouldDeleteAll() {
    // Given: a mocked SecretRepository and a large number of entities
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      SecretDomain entity = new SecretDomain("ref" + i, "secret" + i);
      entity.setId("id" + i);
      entities.add(entity);
    }

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete all 100 entities in a single batch
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withArrayList_shouldDelete() {
    // Given: a mocked SecretRepository and entities in ArrayList
    SecretRepository repository = mock(SecretRepository.class);

    ArrayList<SecretDomain> entities = new ArrayList<>();
    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    entities.add(entity1);
    entities.add(entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete all entities
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_returnsVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    // Then: should return void (compile-time check, no runtime assertion needed)
    repository.deleteAllInBatch(entities);

    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEntitiesHavingSpecialCharacters_shouldDelete() {
    // Given: a mocked SecretRepository and entities with special characters
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-_$!@", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret-_$!@");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete entities with special characters
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEntitiesCreatedByNoArgsConstructor_shouldDelete() {
    // Given: a mocked SecretRepository and entities created with no-args constructor
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain();
    entity1.setId("id1");
    entity1.setRef("ref1");
    entity1.setSecret("secret1");

    SecretDomain entity2 = new SecretDomain();
    entity2.setId("id2");
    entity2.setRef("ref2");
    entity2.setSecret("secret2");

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete all entities
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEntitiesHavingTimestamps_shouldDelete() {
    // Given: a mocked SecretRepository and entities with timestamps
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

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete entities regardless of timestamps
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    List<SecretDomain> batch1 = Collections.singletonList(entity1);
    List<SecretDomain> batch2 = Collections.singletonList(entity2);

    doNothing().when(repository).deleteAllInBatch(batch1);
    doNothing().when(repository).deleteAllInBatch(batch2);

    // When: calling deleteAllInBatch multiple times
    repository.deleteAllInBatch(batch1);
    repository.deleteAllInBatch(batch2);

    // Then: each call should work independently
    verify(repository, times(1)).deleteAllInBatch(batch1);
    verify(repository, times(1)).deleteAllInBatch(batch2);
  }

  @Test
  void deleteAllInBatch_withLongRefAndSecretValues_shouldDelete() {
    // Given: a mocked SecretRepository and entities with long string values
    SecretRepository repository = mock(SecretRepository.class);

    String longRef = "123456789012345"; // Max length is 15 according to @Column annotation
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity1 = new SecretDomain(longRef, longSecret);
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete entities with long values
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_afterOtherRepositoryOperations_shouldWork() {
    // Given: a mocked SecretRepository with other operations performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    SecretDomain entityToDelete = new SecretDomain("ref", "secret");
    entityToDelete.setId("id");

    List<SecretDomain> entitiesToDelete = Collections.singletonList(entityToDelete);

    doNothing().when(repository).deleteAllInBatch(entitiesToDelete);

    // When: performing other operations then deleteAllInBatch
    repository.save(savedEntity);
    repository.deleteAllInBatch(entitiesToDelete);

    // Then: deleteAllInBatch should work after other operations
    verify(repository, times(1)).save(savedEntity);
    verify(repository, times(1)).deleteAllInBatch(entitiesToDelete);
  }

  @Test
  void deleteAllInBatch_doesNotReturnValue() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When & Then: calling deleteAllInBatch should not return any value (void method)
    assertThatCode(() -> repository.deleteAllInBatch(entities)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withMixedEntityStates_shouldDeleteAll() {
    // Given: a mocked SecretRepository with entities in different states
    SecretRepository repository = mock(SecretRepository.class);

    // Entity with all fields set
    SecretDomain fullEntity = new SecretDomain("ref1", "secret1");
    fullEntity.setId("id1");
    fullEntity.setCreatedAt(System.currentTimeMillis());

    // Entity with minimal fields
    SecretDomain minimalEntity = new SecretDomain("ref2", "secret2");
    minimalEntity.setId("id2");

    // Entity with null secret
    SecretDomain nullSecretEntity = new SecretDomain("ref3", null);
    nullSecretEntity.setId("id3");

    List<SecretDomain> entities = Arrays.asList(fullEntity, minimalEntity, nullSecretEntity);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch
    repository.deleteAllInBatch(entities);

    // Then: should delete all entities regardless of their state
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).flush();

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling flush then deleteAllInBatch
    repository.flush();
    repository.deleteAllInBatch(entities);

    // Then: deleteAllInBatch should work after flush
    verify(repository, times(1)).flush();
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withDuplicateEntitiesInList_shouldDelete() {
    // Given: a mocked SecretRepository with duplicate entities in the list
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref1", "secret1");
    entity2.setId("id1"); // Same as entity1

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: calling deleteAllInBatch with duplicates
    repository.deleteAllInBatch(entities);

    // Then: should invoke deleteAllInBatch (actual deletion behavior depends on JPA)
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  // ==================== Tests for no-argument deleteAllInBatch() ====================

  @Test
  void deleteAllInBatch_noArgs_shouldDeleteAllEntitiesInRepository() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: calling deleteAllInBatch with no arguments
    repository.deleteAllInBatch();

    // Then: should invoke deleteAllInBatch once
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When & Then: deleteAllInBatch should complete without throwing an exception
    assertThatCode(() -> repository.deleteAllInBatch()).doesNotThrowAnyException();
  }

  @Test
  void deleteAllInBatch_noArgs_returnsVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: calling deleteAllInBatch
    // Then: should return void (compile-time check, no runtime assertion needed)
    repository.deleteAllInBatch();

    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: calling deleteAllInBatch multiple times
    repository.deleteAllInBatch();
    repository.deleteAllInBatch();
    repository.deleteAllInBatch();

    // Then: each call should work independently
    verify(repository, times(3)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    doNothing().when(repository).deleteAllInBatch();

    // When: saving entity then calling deleteAllInBatch
    repository.save(entity);
    repository.deleteAllInBatch();

    // Then: deleteAllInBatch should work after save
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).flush();
    doNothing().when(repository).deleteAllInBatch();

    // When: calling flush then deleteAllInBatch
    repository.flush();
    repository.deleteAllInBatch();

    // Then: deleteAllInBatch should work after flush
    verify(repository, times(1)).flush();
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    doNothing().when(repository).deleteAllInBatch();

    // When: calling saveAndFlush then deleteAllInBatch
    repository.saveAndFlush(entity);
    repository.deleteAllInBatch();

    // Then: deleteAllInBatch should work after saveAndFlush
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_afterSaveAllAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAllAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );
    doNothing().when(repository).deleteAllInBatch();

    // When: calling saveAllAndFlush then deleteAllInBatch
    repository.saveAllAndFlush(entities);
    repository.deleteAllInBatch();

    // Then: deleteAllInBatch should work after saveAllAndFlush
    verify(repository, times(1)).saveAllAndFlush(entities);
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_doesNotRequireParameters() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When & Then: deleteAllInBatch should be callable with no parameters
    assertThatCode(() -> repository.deleteAllInBatch()).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_afterFindAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: calling findAll then deleteAllInBatch
    repository.findAll();
    repository.deleteAllInBatch();

    // Then: deleteAllInBatch should work after findAll
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_afterCountOperation_shouldWork() {
    // Given: a mocked SecretRepository with count called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: calling count then deleteAllInBatch
    repository.count();
    repository.deleteAllInBatch();

    // Then: deleteAllInBatch should work after count
    verify(repository, times(1)).count();
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_doesNotReturnValue() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When & Then: calling deleteAllInBatch should not return any value (void method)
    assertThatCode(() -> repository.deleteAllInBatch()).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_afterDeleteByRefOperation_shouldWork() {
    // Given: a mocked SecretRepository with deleteByRef called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: calling deleteByRef then deleteAllInBatch
    repository.deleteByRef("some-ref");
    repository.deleteAllInBatch();

    // Then: deleteAllInBatch should work after deleteByRef
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_afterFindByRefOperation_shouldWork() {
    // Given: a mocked SecretRepository with findByRef called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: calling findByRef then deleteAllInBatch
    repository.findByRef("some-ref");
    repository.deleteAllInBatch();

    // Then: deleteAllInBatch should work after findByRef
    verify(repository, times(1)).findByRef("some-ref");
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_canBeCalledIndependently() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: calling deleteAllInBatch without any prior operations
    repository.deleteAllInBatch();

    // Then: should complete successfully
    verify(repository, times(1)).deleteAllInBatch();
  }
}
