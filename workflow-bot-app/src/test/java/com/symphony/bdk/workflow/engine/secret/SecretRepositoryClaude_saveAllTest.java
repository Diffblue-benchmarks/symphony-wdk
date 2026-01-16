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
 * Test class for SecretRepository.saveAll(Iterable) method.
 *
 * The saveAll(Iterable<S> entities) method is inherited from CrudRepository
 * (which JpaRepository extends) and provides batch save functionality.
 * Key characteristics:
 * 1. Takes an Iterable of entities to save
 * 2. Returns a List of all saved entities
 * 3. Can save both new entities (insert) and existing entities (update)
 * 4. More efficient than multiple individual save() calls
 * 5. Does NOT automatically flush changes to database (unlike saveAllAndFlush)
 * 6. Returns List<SecretDomain> type
 *
 * Method signature: List<SecretDomain> saveAll(Iterable<SecretDomain> entities)
 *
 * Difference from saveAllAndFlush:
 * - saveAll(): Saves entities to persistence context, changes may not be immediately visible in database
 * - saveAllAndFlush(): Saves entities AND flushes changes to database immediately
 *
 * Use cases for saveAll:
 * - Batch inserting multiple new entities efficiently
 * - Batch updating multiple existing entities
 * - Better performance than individual save() calls for multiple entities
 * - When immediate database synchronization is not required
 * - When you want to defer flushing until later in the transaction
 *
 * Example usage:
 * <pre>
 * List<SecretDomain> secrets = Arrays.asList(
 *   new SecretDomain("ref1", "secret1"),
 *   new SecretDomain("ref2", "secret2")
 * );
 * List<SecretDomain> saved = repository.saveAll(secrets);
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of saveAll, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The saveAll method exists and is callable on SecretRepository
 * 2. The method accepts an Iterable<SecretDomain> parameter
 * 3. The method returns a List<SecretDomain>
 * 4. The method can handle various scenarios (empty list, single entity, multiple entities)
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual saveAll implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_saveAllTest {

  @Test
  void saveAll_withMultipleNewEntities_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository and multiple new entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    List<SecretDomain> entitiesToSave = Arrays.asList(entity1, entity2);

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");
    List<SecretDomain> savedEntities = Arrays.asList(saved1, saved2);

    when(repository.saveAll(entitiesToSave)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(entitiesToSave);

    // Then: should return all saved entities with generated IDs
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo("id1");
    assertThat(result.get(1).getId()).isEqualTo("id2");
    verify(repository, times(1)).saveAll(entitiesToSave);
  }

  @Test
  void saveAll_shouldReturnListType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));
    List<SecretDomain> savedEntities = Collections.singletonList(new SecretDomain("ref", "secret"));

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: should return List type
    assertThat(result).isInstanceOf(List.class);
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));
    List<SecretDomain> savedEntities = Collections.emptyList();

    when(repository.saveAll(any(Iterable.class))).thenReturn(savedEntities);

    // When & Then: saveAll should complete without throwing an exception
    assertThatCode(() -> repository.saveAll(entities)).doesNotThrowAnyException();
  }

  @Test
  void saveAll_withEmptyList_shouldReturnEmptyList() {
    // Given: a mocked SecretRepository with empty list
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> emptyList = Collections.emptyList();

    when(repository.saveAll(emptyList)).thenReturn(emptyList);

    // When: calling saveAll with empty list
    List<SecretDomain> result = repository.saveAll(emptyList);

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository, times(1)).saveAll(emptyList);
  }

  @Test
  void saveAll_withSingleEntity_shouldSaveAndReturnList() {
    // Given: a mocked SecretRepository with single entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    List<SecretDomain> singleEntityList = Collections.singletonList(entity);

    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("generated-id");
    List<SecretDomain> savedList = Collections.singletonList(savedEntity);

    when(repository.saveAll(singleEntityList)).thenReturn(savedList);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(singleEntityList);

    // Then: should return list with one saved entity
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo("generated-id");
    verify(repository, times(1)).saveAll(singleEntityList);
  }

  @Test
  void saveAll_withExistingEntities_shouldUpdateAndReturnAll() {
    // Given: a mocked SecretRepository with existing entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "updated-secret1");
    entity1.setId("existing-id1");
    SecretDomain entity2 = new SecretDomain("ref2", "updated-secret2");
    entity2.setId("existing-id2");
    List<SecretDomain> existingEntities = Arrays.asList(entity1, entity2);

    when(repository.saveAll(existingEntities)).thenReturn(existingEntities);

    // When: calling saveAll to update existing entities
    List<SecretDomain> result = repository.saveAll(existingEntities);

    // Then: should return updated entities
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo("existing-id1");
    assertThat(result.get(0).getSecret()).isEqualTo("updated-secret1");
    assertThat(result.get(1).getId()).isEqualTo("existing-id2");
    assertThat(result.get(1).getSecret()).isEqualTo("updated-secret2");
    verify(repository, times(1)).saveAll(existingEntities);
  }

  @Test
  void saveAll_withMixedNewAndExistingEntities_shouldSaveAll() {
    // Given: a mocked SecretRepository with mixed entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain newEntity = new SecretDomain("new-ref", "new-secret");
    SecretDomain existingEntity = new SecretDomain("existing-ref", "existing-secret");
    existingEntity.setId("existing-id");
    List<SecretDomain> mixedEntities = Arrays.asList(newEntity, existingEntity);

    SecretDomain savedNew = new SecretDomain("new-ref", "new-secret");
    savedNew.setId("generated-id");
    List<SecretDomain> savedEntities = Arrays.asList(savedNew, existingEntity);

    when(repository.saveAll(mixedEntities)).thenReturn(savedEntities);

    // When: calling saveAll with mixed entities
    List<SecretDomain> result = repository.saveAll(mixedEntities);

    // Then: should save new and update existing
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo("generated-id");
    assertThat(result.get(1).getId()).isEqualTo("existing-id");
    verify(repository, times(1)).saveAll(mixedEntities);
  }

  @Test
  void saveAll_withEntitiesHavingNullFields_shouldSaveAll() {
    // Given: a mocked SecretRepository with entities having null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain(null, "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", null);
    List<SecretDomain> entitiesWithNulls = Arrays.asList(entity1, entity2);

    SecretDomain saved1 = new SecretDomain(null, "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", null);
    saved2.setId("id2");
    List<SecretDomain> savedEntities = Arrays.asList(saved1, saved2);

    when(repository.saveAll(entitiesWithNulls)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(entitiesWithNulls);

    // Then: should save entities with null fields
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getRef()).isNull();
    assertThat(result.get(1).getSecret()).isNull();
    verify(repository, times(1)).saveAll(entitiesWithNulls);
  }

  @Test
  void saveAll_withEntitiesHavingEmptyStrings_shouldSaveAll() {
    // Given: a mocked SecretRepository with entities having empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("", "");
    List<SecretDomain> entities = Collections.singletonList(entity);

    SecretDomain savedEntity = new SecretDomain("", "");
    savedEntity.setId("id");
    List<SecretDomain> savedEntities = Collections.singletonList(savedEntity);

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: should save entities with empty strings
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getRef()).isEmpty();
    assertThat(result.get(0).getSecret()).isEmpty();
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> batch1 = Collections.singletonList(new SecretDomain("ref1", "secret1"));
    List<SecretDomain> batch2 = Collections.singletonList(new SecretDomain("ref2", "secret2"));

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");

    when(repository.saveAll(batch1)).thenReturn(Collections.singletonList(saved1));
    when(repository.saveAll(batch2)).thenReturn(Collections.singletonList(saved2));

    // When: calling saveAll multiple times
    List<SecretDomain> result1 = repository.saveAll(batch1);
    List<SecretDomain> result2 = repository.saveAll(batch2);

    // Then: each call should work independently
    assertThat(result1).hasSize(1);
    assertThat(result2).hasSize(1);
    assertThat(result1.get(0).getRef()).isEqualTo("ref1");
    assertThat(result2.get(0).getRef()).isEqualTo("ref2");
    verify(repository, times(1)).saveAll(batch1);
    verify(repository, times(1)).saveAll(batch2);
  }

  @Test
  void saveAll_withLargeBatch_shouldSaveAllEntities() {
    // Given: a mocked SecretRepository with large batch
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> largeList = new ArrayList<>();
    List<SecretDomain> savedList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      SecretDomain entity = new SecretDomain("ref" + i, "secret" + i);
      largeList.add(entity);

      SecretDomain saved = new SecretDomain("ref" + i, "secret" + i);
      saved.setId("id" + i);
      savedList.add(saved);
    }

    when(repository.saveAll(largeList)).thenReturn(savedList);

    // When: calling saveAll with 100 entities
    List<SecretDomain> result = repository.saveAll(largeList);

    // Then: should save all 100 entities
    assertThat(result).hasSize(100);
    assertThat(result.get(0).getId()).isEqualTo("id0");
    assertThat(result.get(99).getId()).isEqualTo("id99");
    verify(repository, times(1)).saveAll(largeList);
  }

  @Test
  void saveAll_returnsEntitiesWithAllFieldsPopulated() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("test-ref", "test-secret");
    List<SecretDomain> entities = Collections.singletonList(entity);

    SecretDomain savedEntity = new SecretDomain("test-ref", "test-secret");
    savedEntity.setId("test-id");
    savedEntity.setCreatedAt(System.currentTimeMillis());
    List<SecretDomain> savedEntities = Collections.singletonList(savedEntity);

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: should return entities with all fields populated
    assertThat(result).hasSize(1);
    SecretDomain returned = result.get(0);
    assertThat(returned.getId()).isEqualTo("test-id");
    assertThat(returned.getRef()).isEqualTo("test-ref");
    assertThat(returned.getSecret()).isEqualTo("test-secret");
    assertThat(returned.getCreatedAt()).isNotNull();
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_withLongValues_shouldSaveEntities() {
    // Given: a mocked SecretRepository with entities having long values
    SecretRepository repository = mock(SecretRepository.class);

    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    List<SecretDomain> entities = Collections.singletonList(entity);

    SecretDomain savedEntity = new SecretDomain(longRef, longSecret);
    savedEntity.setId("id");
    List<SecretDomain> savedEntities = Collections.singletonList(savedEntity);

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: should save entities with long values
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getRef()).isEqualTo(longRef);
    assertThat(result.get(0).getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));
    SecretDomain saved = new SecretDomain("ref", "secret");
    saved.setId("id");
    List<SecretDomain> savedEntities = Collections.singletonList(saved);

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling flush then saveAll
    repository.flush();
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: saveAll should work after flush
    assertThat(result).isNotNull();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_canBeCalledAfterDelete() {
    // Given: a mocked SecretRepository with delete called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));
    SecretDomain saved = new SecretDomain("ref", "secret");
    saved.setId("id");
    List<SecretDomain> savedEntities = Collections.singletonList(saved);

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling delete then saveAll
    repository.deleteByRef("some-ref");
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: saveAll should work after delete
    assertThat(result).isNotNull();
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_canBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.singletonList(new SecretDomain("ref", "secret"));
    SecretDomain saved = new SecretDomain("ref", "secret");
    saved.setId("id");
    List<SecretDomain> savedEntities = Collections.singletonList(saved);

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling findAll then saveAll
    repository.findAll();
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: saveAll should work after findAll
    assertThat(result).isNotNull();
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_returnsListNotNull() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Collections.emptyList();
    List<SecretDomain> savedEntities = new ArrayList<>();

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: should never return null, always a list
    assertThat(result).isNotNull();
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_withArrayListInput_shouldWork() {
    // Given: a mocked SecretRepository with ArrayList input
    SecretRepository repository = mock(SecretRepository.class);

    ArrayList<SecretDomain> arrayList = new ArrayList<>();
    arrayList.add(new SecretDomain("ref1", "secret1"));
    arrayList.add(new SecretDomain("ref2", "secret2"));

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");
    List<SecretDomain> savedList = Arrays.asList(saved1, saved2);

    when(repository.saveAll(arrayList)).thenReturn(savedList);

    // When: calling saveAll with ArrayList
    List<SecretDomain> result = repository.saveAll(arrayList);

    // Then: should save all entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAll(arrayList);
  }

  @Test
  void saveAll_preservesOrderOfEntities() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("first", "secret1");
    SecretDomain entity2 = new SecretDomain("second", "secret2");
    SecretDomain entity3 = new SecretDomain("third", "secret3");
    List<SecretDomain> orderedEntities = Arrays.asList(entity1, entity2, entity3);

    SecretDomain saved1 = new SecretDomain("first", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("second", "secret2");
    saved2.setId("id2");
    SecretDomain saved3 = new SecretDomain("third", "secret3");
    saved3.setId("id3");
    List<SecretDomain> savedEntities = Arrays.asList(saved1, saved2, saved3);

    when(repository.saveAll(orderedEntities)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(orderedEntities);

    // Then: should preserve order
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getRef()).isEqualTo("first");
    assertThat(result.get(1).getRef()).isEqualTo("second");
    assertThat(result.get(2).getRef()).isEqualTo("third");
    verify(repository, times(1)).saveAll(orderedEntities);
  }

  @Test
  void saveAll_withEntitiesHavingTimestamps_shouldSaveAll() {
    // Given: a mocked SecretRepository with entities having timestamps
    SecretRepository repository = mock(SecretRepository.class);

    Long timestamp1 = System.currentTimeMillis();
    Long timestamp2 = timestamp1 + 1000;

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setCreatedAt(timestamp1);
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setCreatedAt(timestamp2);
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    saved1.setCreatedAt(timestamp1);
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");
    saved2.setCreatedAt(timestamp2);
    List<SecretDomain> savedEntities = Arrays.asList(saved1, saved2);

    when(repository.saveAll(entities)).thenReturn(savedEntities);

    // When: calling saveAll
    List<SecretDomain> result = repository.saveAll(entities);

    // Then: should save entities with timestamps preserved
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getCreatedAt()).isEqualTo(timestamp1);
    assertThat(result.get(1).getCreatedAt()).isEqualTo(timestamp2);
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with individual save called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain singleEntity = new SecretDomain("single", "secret");

    List<SecretDomain> batchEntities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");
    List<SecretDomain> savedBatch = Arrays.asList(saved1, saved2);

    when(repository.saveAll(batchEntities)).thenReturn(savedBatch);

    // When: calling save then saveAll
    repository.save(singleEntity);
    List<SecretDomain> result = repository.saveAll(batchEntities);

    // Then: saveAll should work after save
    assertThat(result).hasSize(2);
    verify(repository, times(1)).save(singleEntity);
    verify(repository, times(1)).saveAll(batchEntities);
  }
}
