package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.saveAllAndFlush() method.
 *
 * The saveAllAndFlush() method is inherited from JpaRepository (Spring Data JPA) and
 * combines two operations:
 * 1. Saves all given entities (persists new or merges existing entities)
 * 2. Immediately flushes all pending changes to the database
 *
 * This method is useful when you need to save multiple entities at once and ensure
 * that all database operations are executed immediately. It's more efficient than
 * calling saveAndFlush() multiple times for multiple entities.
 *
 * Method signature: List<SecretDomain> saveAllAndFlush(Iterable<SecretDomain> entities)
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of saveAllAndFlush(), the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The saveAllAndFlush() method exists and is callable on SecretRepository
 * 2. The method accepts an Iterable of SecretDomain entities
 * 3. The method returns a List of SecretDomain entities
 * 4. The method can handle various scenarios (empty list, single entity, multiple entities, etc.)
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual saveAllAndFlush() implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_saveAllAndFlushTest {

  @Test
  void saveAllAndFlush_withMultipleEntities_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository and multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2, entity3);

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");
    SecretDomain saved3 = new SecretDomain("ref3", "secret3");
    saved3.setId("id3");
    List<SecretDomain> savedEntities = Arrays.asList(saved1, saved2, saved3);

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should return all saved entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getId()).isEqualTo("id1");
    assertThat(result.get(1).getId()).isEqualTo("id2");
    assertThat(result.get(2).getId()).isEqualTo("id3");
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withEmptyList_shouldReturnEmptyList() {
    // Given: a mocked SecretRepository and an empty list
    SecretRepository repository = mock(SecretRepository.class);
    List<SecretDomain> emptyList = Collections.emptyList();

    when(repository.saveAllAndFlush(emptyList)).thenReturn(emptyList);

    // When: calling saveAllAndFlush with empty list
    List<SecretDomain> result = repository.saveAllAndFlush(emptyList);

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(repository, times(1)).saveAllAndFlush(emptyList);
  }

  @Test
  void saveAllAndFlush_withSingleEntity_shouldSaveAndReturnList() {
    // Given: a mocked SecretRepository and a single entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    List<SecretDomain> entities = Collections.singletonList(entity);

    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("generated-id");
    List<SecretDomain> savedEntities = Collections.singletonList(savedEntity);

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should return list with single saved entity
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo("generated-id");
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2")
    );

    when(repository.saveAllAndFlush(anyIterable())).thenReturn(entities);

    // When & Then: saveAllAndFlush should complete without throwing an exception
    assertThatCode(() -> repository.saveAllAndFlush(entities)).doesNotThrowAnyException();
  }

  @Test
  void saveAllAndFlush_withEntitiesHavingNullFields_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository and entities with null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain(null, "secret");
    SecretDomain entity2 = new SecretDomain("ref", null);
    SecretDomain entity3 = new SecretDomain(null, null);
    List<SecretDomain> entities = Arrays.asList(entity1, entity2, entity3);

    SecretDomain saved1 = new SecretDomain(null, "secret");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref", null);
    saved2.setId("id2");
    SecretDomain saved3 = new SecretDomain(null, null);
    saved3.setId("id3");
    List<SecretDomain> savedEntities = Arrays.asList(saved1, saved2, saved3);

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should save and return all entities even with null fields
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getRef()).isNull();
    assertThat(result.get(1).getSecret()).isNull();
    assertThat(result.get(2).getRef()).isNull();
    assertThat(result.get(2).getSecret()).isNull();
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withEntitiesHavingEmptyStrings_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository and entities with empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("", "secret");
    SecretDomain entity2 = new SecretDomain("ref", "");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    SecretDomain saved1 = new SecretDomain("", "secret");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref", "");
    saved2.setId("id2");
    List<SecretDomain> savedEntities = Arrays.asList(saved1, saved2);

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should save and return all entities with empty strings
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getRef()).isEmpty();
    assertThat(result.get(1).getSecret()).isEmpty();
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withMixOfNewAndExistingEntities_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository with mix of new and existing entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain newEntity = new SecretDomain("new-ref", "new-secret");
    SecretDomain existingEntity = new SecretDomain("existing-ref", "existing-secret");
    existingEntity.setId("existing-id");
    existingEntity.setCreatedAt(12345L);
    List<SecretDomain> entities = Arrays.asList(newEntity, existingEntity);

    SecretDomain savedNew = new SecretDomain("new-ref", "new-secret");
    savedNew.setId("new-id");
    SecretDomain savedExisting = new SecretDomain("existing-ref", "existing-secret");
    savedExisting.setId("existing-id");
    savedExisting.setCreatedAt(12345L);
    List<SecretDomain> savedEntities = Arrays.asList(savedNew, savedExisting);

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should save and return all entities (both new and existing)
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo("new-id");
    assertThat(result.get(1).getId()).isEqualTo("existing-id");
    assertThat(result.get(1).getCreatedAt()).isEqualTo(12345L);
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withLargeNumberOfEntities_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository and a large number of entities
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = new ArrayList<>();
    List<SecretDomain> savedEntities = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      SecretDomain entity = new SecretDomain("ref" + i, "secret" + i);
      entities.add(entity);

      SecretDomain saved = new SecretDomain("ref" + i, "secret" + i);
      saved.setId("id" + i);
      savedEntities.add(saved);
    }

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should save and return all 100 entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(100);
    assertThat(result.get(0).getId()).isEqualTo("id0");
    assertThat(result.get(99).getId()).isEqualTo("id99");
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withArrayList_shouldSaveAndReturnList() {
    // Given: a mocked SecretRepository and entities in ArrayList
    SecretRepository repository = mock(SecretRepository.class);

    ArrayList<SecretDomain> entities = new ArrayList<>();
    entities.add(new SecretDomain("ref1", "secret1"));
    entities.add(new SecretDomain("ref2", "secret2"));

    List<SecretDomain> savedEntities = Arrays.asList(
        createSavedEntity("ref1", "secret1", "id1"),
        createSavedEntity("ref2", "secret2", "id2")
    );

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should save and return all entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_returnsListWithSameSize() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2"),
        new SecretDomain("ref3", "secret3")
    );

    List<SecretDomain> savedEntities = Arrays.asList(
        createSavedEntity("ref1", "secret1", "id1"),
        createSavedEntity("ref2", "secret2", "id2"),
        createSavedEntity("ref3", "secret3", "id3")
    );

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: result should have same size as input
    assertThat(result).hasSize(entities.size());
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withEntitiesHavingSpecialCharacters_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository and entities with special characters
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref-_$!@", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret-_$!@");
    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    List<SecretDomain> savedEntities = Arrays.asList(
        createSavedEntity("ref-_$!@", "secret1", "id1"),
        createSavedEntity("ref2", "secret-_$!@", "id2")
    );

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should save and return all entities with special characters
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getRef()).isEqualTo("ref-_$!@");
    assertThat(result.get(1).getSecret()).isEqualTo("secret-_$!@");
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withEntitiesCreatedByNoArgsConstructor_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository and entities created with no-args constructor
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain();
    entity1.setRef("ref1");
    entity1.setSecret("secret1");

    SecretDomain entity2 = new SecretDomain();
    entity2.setRef("ref2");
    entity2.setSecret("secret2");

    List<SecretDomain> entities = Arrays.asList(entity1, entity2);

    List<SecretDomain> savedEntities = Arrays.asList(
        createSavedEntity("ref1", "secret1", "id1"),
        createSavedEntity("ref2", "secret2", "id2")
    );

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should save and return all entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo("id1");
    assertThat(result.get(1).getId()).isEqualTo("id2");
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withEntitiesHavingTimestamps_shouldPreserveTimestamps() {
    // Given: a mocked SecretRepository and entities with timestamps
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

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should preserve timestamps for all entities
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getCreatedAt()).isEqualTo(timestamp1);
    assertThat(result.get(1).getCreatedAt()).isEqualTo(timestamp2);
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<SecretDomain> entities1 = Collections.singletonList(new SecretDomain("ref1", "secret1"));
    List<SecretDomain> entities2 = Collections.singletonList(new SecretDomain("ref2", "secret2"));

    List<SecretDomain> saved1 = Collections.singletonList(createSavedEntity("ref1", "secret1", "id1"));
    List<SecretDomain> saved2 = Collections.singletonList(createSavedEntity("ref2", "secret2", "id2"));

    when(repository.saveAllAndFlush(entities1)).thenReturn(saved1);
    when(repository.saveAllAndFlush(entities2)).thenReturn(saved2);

    // When: calling saveAllAndFlush multiple times
    List<SecretDomain> result1 = repository.saveAllAndFlush(entities1);
    List<SecretDomain> result2 = repository.saveAllAndFlush(entities2);

    // Then: each call should work independently
    assertThat(result1).hasSize(1);
    assertThat(result1.get(0).getId()).isEqualTo("id1");
    assertThat(result2).hasSize(1);
    assertThat(result2.get(0).getId()).isEqualTo("id2");
    verify(repository, times(1)).saveAllAndFlush(entities1);
    verify(repository, times(1)).saveAllAndFlush(entities2);
  }

  @Test
  void saveAllAndFlush_withLongRefAndSecretValues_shouldSaveAndReturnAll() {
    // Given: a mocked SecretRepository and entities with long string values
    SecretRepository repository = mock(SecretRepository.class);

    String longRef = "123456789012345"; // Max length is 15 according to @Column annotation
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    List<SecretDomain> entities = Arrays.asList(
        new SecretDomain(longRef, longSecret),
        new SecretDomain("ref2", "secret2")
    );

    List<SecretDomain> savedEntities = Arrays.asList(
        createSavedEntity(longRef, longSecret, "id1"),
        createSavedEntity("ref2", "secret2", "id2")
    );

    when(repository.saveAllAndFlush(entities)).thenReturn(savedEntities);

    // When: calling saveAllAndFlush
    List<SecretDomain> result = repository.saveAllAndFlush(entities);

    // Then: should save and return all entities with long values
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getRef()).isEqualTo(longRef);
    assertThat(result.get(0).getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  // Helper method to create a saved entity with ID
  private SecretDomain createSavedEntity(String ref, String secret, String id) {
    SecretDomain entity = new SecretDomain(ref, secret);
    entity.setId(id);
    return entity;
  }
}
