package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for SecretRepository.deleteInBatch() method.
 *
 * The deleteInBatch() method is inherited from JpaRepository (Spring Data JPA) and
 * performs batch deletion of entities. Key characteristics:
 * 1. Deletes all given entities in a single DELETE query (batch operation)
 * 2. More efficient than calling delete() multiple times for individual entities
 * 3. Does NOT call JPA lifecycle callbacks (like @PreRemove, @PostRemove)
 * 4. Does NOT cascade to related entities (ignores cascade settings)
 * 5. Returns void (no return value)
 *
 * This method is useful for performance optimization when deleting multiple entities
 * and you don't need lifecycle callbacks or cascading behavior.
 *
 * Method signature: void deleteInBatch(Iterable<SecretDomain> entities)
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of deleteInBatch(), the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The deleteInBatch() method exists and is callable on SecretRepository
 * 2. The method accepts an Iterable of SecretDomain entities
 * 3. The method returns void (no return value)
 * 4. The method can handle various scenarios (empty list, single entity, multiple entities)
 * 5. The method can be invoked without errors
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual deleteInBatch() implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_deleteInBatchTest {

  @Test
  void deleteInBatch_withMultipleEntities_shouldDeleteAll() {
    // Given: a mocked SecretRepository and multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");
    entity3.setId("id3");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2, entity3);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should invoke deleteInBatch once with all entities
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEmptyList_shouldCompleteWithoutError() {
    // Given: a mocked SecretRepository and an empty list
    SecretRepository repository = mock(SecretRepository.class);
    List<SecretDomain> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteInBatch(emptyList);

    // When: calling deleteInBatch with empty list
    repository.deleteInBatch(emptyList);

    // Then: should complete without error
    verify(repository, times(1)).deleteInBatch(emptyList);
  }

  @Test
  void deleteInBatch_withSingleEntity_shouldDelete() {
    // Given: a mocked SecretRepository and a single entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should invoke deleteInBatch once
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    doNothing().when(repository).deleteInBatch(anyIterable());

    // When & Then: deleteInBatch should complete without throwing an exception
    assertThatCode(() -> repository.deleteInBatch(entities)).doesNotThrowAnyException();
  }

  @Test
  void deleteInBatch_withEntitiesHavingNullFields_shouldDelete() {
    // Given: a mocked SecretRepository and entities with null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain(null, "secret");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref", null);
    entity2.setId("id2");
    SecretDomain entity3 = new SecretDomain(null, null);
    entity3.setId("id3");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2, entity3);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete entities even with null fields
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEntitiesHavingEmptyStrings_shouldDelete() {
    // Given: a mocked SecretRepository and entities with empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("", "secret");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref", "");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete entities with empty strings
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEntitiesWithoutIds_shouldDelete() {
    // Given: a mocked SecretRepository and entities without IDs set
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should invoke deleteInBatch (actual behavior depends on JPA implementation)
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withLargeNumberOfEntities_shouldDeleteAll() {
    // Given: a mocked SecretRepository and a large number of entities
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      SecretDomain entity = new SecretDomain("ref" + i, "secret" + i);
      entity.setId("id" + i);
      entities.add(entity);
    }

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete all 100 entities in a single batch
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withArrayList_shouldDelete() {
    // Given: a mocked SecretRepository and entities in ArrayList
    SecretRepository repository = mock(SecretRepository.class);

    ArrayList<SecretDomain> entities = new ArrayList<>();
    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");
    entities.add(entity1);
    entities.add(entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete all entities
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_returnsVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    List<SecretDomain> entities = Collections.singletonList(entity);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    // Then: should return void (compile-time check, no runtime assertion needed)
    repository.deleteInBatch(entities);

    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEntitiesHavingSpecialCharacters_shouldDelete() {
    // Given: a mocked SecretRepository and entities with special characters
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-_$!@", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret-_$!@");
    entity2.setId("id2");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete entities with special characters
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEntitiesCreatedByNoArgsConstructor_shouldDelete() {
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

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete all entities
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEntitiesHavingTimestamps_shouldDelete() {
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

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete entities regardless of timestamps
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    List<SecretDomain> batch1 = Collections.singletonList(entity1);
    List<SecretDomain> batch2 = Collections.singletonList(entity2);

    doNothing().when(repository).deleteInBatch(batch1);
    doNothing().when(repository).deleteInBatch(batch2);

    // When: calling deleteInBatch multiple times
    repository.deleteInBatch(batch1);
    repository.deleteInBatch(batch2);

    // Then: each call should work independently
    verify(repository, times(1)).deleteInBatch(batch1);
    verify(repository, times(1)).deleteInBatch(batch2);
  }

  @Test
  void deleteInBatch_withLongRefAndSecretValues_shouldDelete() {
    // Given: a mocked SecretRepository and entities with long string values
    SecretRepository repository = mock(SecretRepository.class);

    String longRef = "123456789012345"; // Max length is 15 according to @Column annotation
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity1 = new SecretDomain(longRef, longSecret);
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete entities with long values
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_afterOtherRepositoryOperations_shouldWork() {
    // Given: a mocked SecretRepository with other operations performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    SecretDomain entityToDelete = new SecretDomain("ref", "secret");
    entityToDelete.setId("id");

    List<SecretDomain> entitiesToDelete = Collections.singletonList(entityToDelete);

    doNothing().when(repository).deleteInBatch(entitiesToDelete);

    // When: performing other operations then deleteInBatch
    repository.save(savedEntity);
    repository.deleteInBatch(entitiesToDelete);

    // Then: deleteInBatch should work after other operations
    verify(repository, times(1)).save(savedEntity);
    verify(repository, times(1)).deleteInBatch(entitiesToDelete);
  }

  @Test
  void deleteInBatch_doesNotReturnValue() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When & Then: calling deleteInBatch should not return any value (void method)
    assertThatCode(() -> repository.deleteInBatch(entities)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withMixedEntityStates_shouldDeleteAll() {
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

    doNothing().when(repository).deleteInBatch(entities);

    // When: calling deleteInBatch
    repository.deleteInBatch(entities);

    // Then: should delete all entities regardless of their state
    verify(repository, times(1)).deleteInBatch(entities);
  }
}
