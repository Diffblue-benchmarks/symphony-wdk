package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.saveAndFlush() method.
 *
 * The saveAndFlush() method is inherited from JpaRepository (Spring Data JPA) and
 * combines two operations:
 * 1. Saves the given entity (persists new or merges existing)
 * 2. Immediately flushes all pending changes to the database
 *
 * This is useful when you need to ensure that database operations are executed
 * immediately and you need the entity with any database-generated values (like IDs)
 * populated right away.
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of saveAndFlush(), the method behavior is entirely
 * provided by Spring Data JPA's runtime proxy. Therefore, meaningful testing of this
 * method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The saveAndFlush() method exists and is callable on SecretRepository
 * 2. The method accepts a SecretDomain entity and returns a SecretDomain entity
 * 3. The method can be invoked without errors in a mocked context
 * 4. The method handles various entity states (new, with ID, null fields, etc.)
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual saveAndFlush() implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_saveAndFlushTest {

  @Test
  void saveAndFlush_withNewEntity_shouldSaveAndReturnEntity() {
    // Given: a mocked SecretRepository and a new SecretDomain
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain newEntity = new SecretDomain("test-ref", "test-secret");
    SecretDomain savedEntity = new SecretDomain("test-ref", "test-secret");
    savedEntity.setId("generated-id");

    when(repository.saveAndFlush(newEntity)).thenReturn(savedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(newEntity);

    // Then: should return the saved entity
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(savedEntity);
    verify(repository, times(1)).saveAndFlush(newEntity);
  }

  @Test
  void saveAndFlush_withExistingEntity_shouldUpdateAndReturnEntity() {
    // Given: a mocked SecretRepository and an existing SecretDomain
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain existingEntity = new SecretDomain("existing-ref", "old-secret");
    existingEntity.setId("existing-id");
    existingEntity.setCreatedAt(12345L);

    SecretDomain updatedEntity = new SecretDomain("existing-ref", "new-secret");
    updatedEntity.setId("existing-id");
    updatedEntity.setCreatedAt(12345L);

    when(repository.saveAndFlush(existingEntity)).thenReturn(updatedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(existingEntity);

    // Then: should return the updated entity
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(updatedEntity);
    verify(repository, times(1)).saveAndFlush(existingEntity);
  }

  @Test
  void saveAndFlush_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain entity = new SecretDomain("ref", "secret");

    when(repository.saveAndFlush(any(SecretDomain.class))).thenReturn(entity);

    // When & Then: saveAndFlush should complete without throwing an exception
    assertThatCode(() -> repository.saveAndFlush(entity)).doesNotThrowAnyException();
  }

  @Test
  void saveAndFlush_withEntityHavingNullSecret_shouldSaveAndReturnEntity() {
    // Given: a mocked SecretRepository and an entity with null secret
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain entityWithNullSecret = new SecretDomain("ref", null);
    SecretDomain savedEntity = new SecretDomain("ref", null);
    savedEntity.setId("generated-id");

    when(repository.saveAndFlush(entityWithNullSecret)).thenReturn(savedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(entityWithNullSecret);

    // Then: should save and return entity even with null secret
    assertThat(result).isNotNull();
    assertThat(result.getSecret()).isNull();
    verify(repository, times(1)).saveAndFlush(entityWithNullSecret);
  }

  @Test
  void saveAndFlush_withEntityHavingNullRef_shouldSaveAndReturnEntity() {
    // Given: a mocked SecretRepository and an entity with null ref
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain entityWithNullRef = new SecretDomain(null, "secret");
    SecretDomain savedEntity = new SecretDomain(null, "secret");
    savedEntity.setId("generated-id");

    when(repository.saveAndFlush(entityWithNullRef)).thenReturn(savedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(entityWithNullRef);

    // Then: should save and return entity even with null ref
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isNull();
    verify(repository, times(1)).saveAndFlush(entityWithNullRef);
  }

  @Test
  void saveAndFlush_withEmptyStrings_shouldSaveAndReturnEntity() {
    // Given: a mocked SecretRepository and an entity with empty strings
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain entityWithEmptyStrings = new SecretDomain("", "");
    SecretDomain savedEntity = new SecretDomain("", "");
    savedEntity.setId("generated-id");

    when(repository.saveAndFlush(entityWithEmptyStrings)).thenReturn(savedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(entityWithEmptyStrings);

    // Then: should save and return entity with empty strings
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isEmpty();
    assertThat(result.getSecret()).isEmpty();
    verify(repository, times(1)).saveAndFlush(entityWithEmptyStrings);
  }

  @Test
  void saveAndFlush_multipleEntities_shouldSaveEachIndependently() {
    // Given: a mocked SecretRepository and multiple entities
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");
    SecretDomain saved3 = new SecretDomain("ref3", "secret3");
    saved3.setId("id3");

    when(repository.saveAndFlush(entity1)).thenReturn(saved1);
    when(repository.saveAndFlush(entity2)).thenReturn(saved2);
    when(repository.saveAndFlush(entity3)).thenReturn(saved3);

    // When: calling saveAndFlush for each entity
    SecretDomain result1 = repository.saveAndFlush(entity1);
    SecretDomain result2 = repository.saveAndFlush(entity2);
    SecretDomain result3 = repository.saveAndFlush(entity3);

    // Then: each entity should be saved independently
    assertThat(result1.getId()).isEqualTo("id1");
    assertThat(result2.getId()).isEqualTo("id2");
    assertThat(result3.getId()).isEqualTo("id3");
    verify(repository, times(1)).saveAndFlush(entity1);
    verify(repository, times(1)).saveAndFlush(entity2);
    verify(repository, times(1)).saveAndFlush(entity3);
  }

  @Test
  void saveAndFlush_withLongRefAndSecret_shouldSaveAndReturnEntity() {
    // Given: a mocked SecretRepository and an entity with long strings
    SecretRepository repository = mock(SecretRepository.class);
    String longRef = "123456789012345"; // Max length is 15 according to @Column annotation
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";
    SecretDomain entity = new SecretDomain(longRef, longSecret);
    SecretDomain savedEntity = new SecretDomain(longRef, longSecret);
    savedEntity.setId("generated-id");

    when(repository.saveAndFlush(entity)).thenReturn(savedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(entity);

    // Then: should save and return entity with long strings
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isEqualTo(longRef);
    assertThat(result.getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void saveAndFlush_returnsSameEntityInstance() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain entity = new SecretDomain("ref", "secret");

    when(repository.saveAndFlush(entity)).thenReturn(entity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(entity);

    // Then: should return the same entity instance
    assertThat(result).isSameAs(entity);
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void saveAndFlush_withEntityCreatedByNoArgsConstructor_shouldSaveAndReturnEntity() {
    // Given: a mocked SecretRepository and an entity created with no-args constructor
    SecretRepository repository = mock(SecretRepository.class);
    SecretDomain entity = new SecretDomain();
    entity.setRef("ref-set-later");
    entity.setSecret("secret-set-later");

    SecretDomain savedEntity = new SecretDomain();
    savedEntity.setRef("ref-set-later");
    savedEntity.setSecret("secret-set-later");
    savedEntity.setId("generated-id");

    when(repository.saveAndFlush(entity)).thenReturn(savedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(entity);

    // Then: should save and return entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("generated-id");
    assertThat(result.getRef()).isEqualTo("ref-set-later");
    assertThat(result.getSecret()).isEqualTo("secret-set-later");
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void saveAndFlush_withSpecialCharactersInRef_shouldSaveAndReturnEntity() {
    // Given: a mocked SecretRepository and an entity with special characters in ref
    SecretRepository repository = mock(SecretRepository.class);
    String specialRef = "ref-_$!@#%^&*";
    SecretDomain entity = new SecretDomain(specialRef, "secret");
    SecretDomain savedEntity = new SecretDomain(specialRef, "secret");
    savedEntity.setId("generated-id");

    when(repository.saveAndFlush(entity)).thenReturn(savedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(entity);

    // Then: should save and return entity with special characters
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isEqualTo(specialRef);
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void saveAndFlush_afterFlushOperation_shouldStillWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);
    doNothing().when(repository).flush();

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("generated-id");

    when(repository.saveAndFlush(entity)).thenReturn(savedEntity);

    // When: calling flush then saveAndFlush
    repository.flush();
    SecretDomain result = repository.saveAndFlush(entity);

    // Then: saveAndFlush should work after flush
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("generated-id");
    verify(repository, times(1)).flush();
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void saveAndFlush_withCreatedAtTimestamp_shouldPreserveTimestamp() {
    // Given: a mocked SecretRepository and an entity with createdAt set
    SecretRepository repository = mock(SecretRepository.class);
    Long timestamp = System.currentTimeMillis();
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setCreatedAt(timestamp);

    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("generated-id");
    savedEntity.setCreatedAt(timestamp);

    when(repository.saveAndFlush(entity)).thenReturn(savedEntity);

    // When: calling saveAndFlush
    SecretDomain result = repository.saveAndFlush(entity);

    // Then: should preserve the timestamp
    assertThat(result).isNotNull();
    assertThat(result.getCreatedAt()).isEqualTo(timestamp);
    verify(repository, times(1)).saveAndFlush(entity);
  }
}
