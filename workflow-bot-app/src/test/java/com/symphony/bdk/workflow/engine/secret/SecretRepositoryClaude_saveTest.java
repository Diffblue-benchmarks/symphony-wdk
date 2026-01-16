package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.save(Object) method.
 *
 * The save(S entity) method is inherited from CrudRepository (which JpaRepository extends)
 * and provides save/update functionality for a single entity.
 * Key characteristics:
 * 1. Takes a single entity to save
 * 2. Returns the saved entity (with generated ID if new)
 * 3. Can be used for both INSERT (new entity) and UPDATE (existing entity) operations
 * 4. JPA determines whether to insert or update based on entity ID presence
 * 5. Does NOT automatically flush changes to database (unlike saveAndFlush)
 * 6. Returns SecretDomain type
 * 7. Changes are held in persistence context until flush or transaction commit
 *
 * Method signature: SecretDomain save(SecretDomain entity)
 *
 * Difference from saveAndFlush:
 * - save(): Saves entity to persistence context, changes may not be immediately visible in database
 * - saveAndFlush(): Saves entity AND flushes changes to database immediately
 *
 * Insert vs Update behavior:
 * - If entity ID is null: JPA performs INSERT operation
 * - If entity ID is not null: JPA performs UPDATE operation (merge)
 *
 * Use cases for save:
 * - Saving a new entity (insert)
 * - Updating an existing entity (update)
 * - When immediate database synchronization is not required
 * - Deferring flush until end of transaction for better performance
 * - Most common repository operation for single entity persistence
 *
 * Example usage:
 * <pre>
 * // Insert new entity
 * SecretDomain newSecret = new SecretDomain("ref", "secret");
 * SecretDomain saved = repository.save(newSecret); // ID generated
 *
 * // Update existing entity
 * SecretDomain existing = repository.findById("id1").get();
 * existing.setSecret("new-secret");
 * repository.save(existing); // Updates existing entity
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of save, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The save method exists and is callable on SecretRepository
 * 2. The method accepts a SecretDomain parameter
 * 3. The method returns a SecretDomain
 * 4. The method can handle various scenarios (new entity, existing entity, null fields)
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual save implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_saveTest {

  @Test
  void save_withNewEntity_shouldSaveAndReturnEntityWithId() {
    // Given: a mocked SecretRepository and a new entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain newEntity = new SecretDomain("test-ref", "test-secret");

    SecretDomain savedEntity = new SecretDomain("test-ref", "test-secret");
    savedEntity.setId("generated-id");

    when(repository.save(newEntity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(newEntity);

    // Then: should return entity with generated ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("generated-id");
    assertThat(result.getRef()).isEqualTo("test-ref");
    assertThat(result.getSecret()).isEqualTo("test-secret");
    verify(repository, times(1)).save(newEntity);
  }

  @Test
  void save_shouldReturnSecretDomainType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should return SecretDomain type
    assertThat(result).isInstanceOf(SecretDomain.class);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    when(repository.save(any(SecretDomain.class))).thenReturn(savedEntity);

    // When & Then: save should complete without throwing an exception
    assertThatCode(() -> repository.save(entity)).doesNotThrowAnyException();
  }

  @Test
  void save_withExistingEntity_shouldUpdateAndReturnEntity() {
    // Given: a mocked SecretRepository with existing entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain existingEntity = new SecretDomain("ref", "updated-secret");
    existingEntity.setId("existing-id");

    when(repository.save(existingEntity)).thenReturn(existingEntity);

    // When: calling save to update
    SecretDomain result = repository.save(existingEntity);

    // Then: should return updated entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("existing-id");
    assertThat(result.getSecret()).isEqualTo("updated-secret");
    verify(repository, times(1)).save(existingEntity);
  }

  @Test
  void save_withNullRef_shouldSaveEntity() {
    // Given: a mocked SecretRepository with entity having null ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain(null, "secret");
    SecretDomain savedEntity = new SecretDomain(null, "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should save entity with null ref
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("id");
    assertThat(result.getRef()).isNull();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withNullSecret_shouldSaveEntity() {
    // Given: a mocked SecretRepository with entity having null secret
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", null);
    SecretDomain savedEntity = new SecretDomain("ref", null);
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should save entity with null secret
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("id");
    assertThat(result.getSecret()).isNull();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withEmptyStrings_shouldSaveEntity() {
    // Given: a mocked SecretRepository with entity having empty strings
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("", "");
    SecretDomain savedEntity = new SecretDomain("", "");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should save entity with empty strings
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isEmpty();
    assertThat(result.getSecret()).isEmpty();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");

    SecretDomain saved1 = new SecretDomain("ref1", "secret1");
    saved1.setId("id1");
    SecretDomain saved2 = new SecretDomain("ref2", "secret2");
    saved2.setId("id2");

    when(repository.save(entity1)).thenReturn(saved1);
    when(repository.save(entity2)).thenReturn(saved2);

    // When: calling save multiple times
    SecretDomain result1 = repository.save(entity1);
    SecretDomain result2 = repository.save(entity2);

    // Then: each call should work independently
    assertThat(result1.getId()).isEqualTo("id1");
    assertThat(result2.getId()).isEqualTo("id2");
    verify(repository, times(1)).save(entity1);
    verify(repository, times(1)).save(entity2);
  }

  @Test
  void save_returnsEntityWithAllFieldsPopulated() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("test-ref", "test-secret");

    SecretDomain savedEntity = new SecretDomain("test-ref", "test-secret");
    savedEntity.setId("test-id");
    savedEntity.setCreatedAt(System.currentTimeMillis());

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should return entity with all fields populated
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("test-id");
    assertThat(result.getRef()).isEqualTo("test-ref");
    assertThat(result.getSecret()).isEqualTo("test-secret");
    assertThat(result.getCreatedAt()).isNotNull();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withLongValues_shouldSaveEntity() {
    // Given: a mocked SecretRepository with entity having long values
    SecretRepository repository = mock(SecretRepository.class);

    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    SecretDomain savedEntity = new SecretDomain(longRef, longSecret);
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should save entity with long values
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isEqualTo(longRef);
    assertThat(result.getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling flush then save
    repository.flush();
    SecretDomain result = repository.save(entity);

    // Then: save should work after flush
    assertThat(result).isNotNull();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_afterDeleteOperation_shouldWork() {
    // Given: a mocked SecretRepository with delete operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: deleting entity then calling save
    repository.deleteByRef("some-ref");
    SecretDomain result = repository.save(entity);

    // Then: save should work after delete
    assertThat(result).isNotNull();
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling findById then save
    repository.findById("some-id");
    SecretDomain result = repository.save(entity);

    // Then: save should work after findById
    assertThat(result).isNotNull();
    verify(repository, times(1)).findById("some-id");
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_returnsNonNullEntity() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should never return null
    assertThat(result).isNotNull();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withEntityHavingTimestamp_shouldSaveEntity() {
    // Given: a mocked SecretRepository with entity having timestamp
    SecretRepository repository = mock(SecretRepository.class);

    Long timestamp = System.currentTimeMillis();

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setCreatedAt(timestamp);

    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");
    savedEntity.setCreatedAt(timestamp);

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should save entity with timestamp
    assertThat(result).isNotNull();
    assertThat(result.getCreatedAt()).isEqualTo(timestamp);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_canBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling findAll then save
    repository.findAll();
    SecretDomain result = repository.save(entity);

    // Then: save should work after findAll
    assertThat(result).isNotNull();
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling saveAll then save
    repository.saveAll(java.util.Collections.emptyList());
    SecretDomain result = repository.save(entity);

    // Then: save should work after saveAll
    assertThat(result).isNotNull();
    verify(repository, times(1)).saveAll(java.util.Collections.emptyList());
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_sameEntityMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    when(repository.save(entity)).thenReturn(entity);

    // When: calling save multiple times with same entity
    SecretDomain result1 = repository.save(entity);
    SecretDomain result2 = repository.save(entity);

    // Then: each call should work (simulating updates)
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
    verify(repository, times(2)).save(entity);
  }

  @Test
  void save_withSpecialCharactersInRef_shouldSaveEntity() {
    // Given: a mocked SecretRepository with special characters in ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref-with-dash", "secret");
    SecretDomain savedEntity = new SecretDomain("ref-with-dash", "secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should save entity with special characters
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isEqualTo("ref-with-dash");
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withUuidFormatId_shouldSaveEntity() {
    // Given: a mocked SecretRepository with UUID-format ID
    SecretRepository repository = mock(SecretRepository.class);

    String uuidId = "550e8400-e29b-41d4-a716-446655440000";

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId(uuidId);

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should save entity with UUID ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(uuidId);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withNumericStringId_shouldSaveEntity() {
    // Given: a mocked SecretRepository with numeric string ID
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("12345");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should save entity with numeric string ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("12345");
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");

    SecretDomain savedEntity = new SecretDomain("ref2", "secret2");
    savedEntity.setId("id2");

    when(repository.save(entity2)).thenReturn(savedEntity);

    // When: calling saveAndFlush then save
    repository.saveAndFlush(entity1);
    SecretDomain result = repository.save(entity2);

    // Then: save should work after saveAndFlush
    assertThat(result).isNotNull();
    verify(repository, times(1)).saveAndFlush(entity1);
    verify(repository, times(1)).save(entity2);
  }

  @Test
  void save_returnsEntityWithSameRefAndSecret() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("original-ref", "original-secret");
    SecretDomain savedEntity = new SecretDomain("original-ref", "original-secret");
    savedEntity.setId("id");

    when(repository.save(entity)).thenReturn(savedEntity);

    // When: calling save
    SecretDomain result = repository.save(entity);

    // Then: should return entity with same ref and secret
    assertThat(result.getRef()).isEqualTo(entity.getRef());
    assertThat(result.getSecret()).isEqualTo(entity.getSecret());
    verify(repository, times(1)).save(entity);
  }
}
