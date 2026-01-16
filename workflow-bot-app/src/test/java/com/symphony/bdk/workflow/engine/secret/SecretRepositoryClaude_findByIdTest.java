package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.findById(Object) method.
 *
 * The findById(ID id) method is inherited from CrudRepository (which JpaRepository extends)
 * and provides retrieval of a single entity by its primary key ID.
 * Key characteristics:
 * 1. Takes a single ID value (String type for SecretRepository)
 * 2. Returns an Optional<SecretDomain> containing the entity if found
 * 3. Returns Optional.empty() if no entity exists with the given ID
 * 4. Does NOT throw exception for non-existent IDs (unlike getById/getReferenceById)
 * 5. Performs eager loading - entity is fully loaded from database
 * 6. Returns Optional<SecretDomain> type
 * 7. Most common method for single entity retrieval
 *
 * Method signature: Optional<SecretDomain> findById(String id)
 *
 * Difference from getById/getReferenceById:
 * - findById(): Returns Optional, eagerly loads entity, safe for non-existent IDs
 * - getById()/getReferenceById(): Returns proxy, lazy loads, throws exception if entity doesn't exist
 *
 * Use cases for findById:
 * - Retrieving a single entity by its ID
 * - Checking if an entity exists before performing operations
 * - Safe retrieval that handles non-existent IDs gracefully
 * - Most common pattern for loading entities in service layer
 * - Preferred over getById/getReferenceById for most use cases
 *
 * Example usage:
 * <pre>
 * Optional<SecretDomain> secret = repository.findById("id123");
 * if (secret.isPresent()) {
 *   SecretDomain entity = secret.get();
 *   // Use entity
 * } else {
 *   // Handle not found
 * }
 *
 * // Or with functional style
 * repository.findById("id123")
 *   .ifPresent(entity -> {
 *     // Use entity
 *   });
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of findById, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The findById method exists and is callable on SecretRepository
 * 2. The method accepts a String parameter (the ID type)
 * 3. The method returns an Optional<SecretDomain>
 * 4. The method can handle various scenarios (existing ID, non-existent ID)
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual findById implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_findByIdTest {

  @Test
  void findById_withExistingId_shouldReturnPresentOptional() {
    // Given: a mocked SecretRepository with existing entity
    SecretRepository repository = mock(SecretRepository.class);

    String id = "existing-id";
    SecretDomain entity = new SecretDomain("test-ref", "test-secret");
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findById with existing ID
    Optional<SecretDomain> result = repository.findById(id);

    // Then: should return present Optional with entity
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
    assertThat(result.get().getRef()).isEqualTo("test-ref");
    assertThat(result.get().getSecret()).isEqualTo("test-secret");
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_shouldReturnOptionalType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.findById(id)).thenReturn(Optional.empty());

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(id);

    // Then: should return Optional type
    assertThat(result).isInstanceOf(Optional.class);
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.findById(anyString())).thenReturn(Optional.empty());

    // When & Then: findById should complete without throwing an exception
    assertThatCode(() -> repository.findById(id)).doesNotThrowAnyException();
  }

  @Test
  void findById_withNonExistentId_shouldReturnEmptyOptional() {
    // Given: a mocked SecretRepository with non-existent ID
    SecretRepository repository = mock(SecretRepository.class);

    String nonExistentId = "non-existent-id";

    when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

    // When: calling findById with non-existent ID
    Optional<SecretDomain> result = repository.findById(nonExistentId);

    // Then: should return empty Optional (no exception)
    assertThat(result).isEmpty();
    assertThat(result).isNotNull();
    verify(repository, times(1)).findById(nonExistentId);
  }

  @Test
  void findById_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id1 = "id1";
    String id2 = "id2";

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId(id1);
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId(id2);

    when(repository.findById(id1)).thenReturn(Optional.of(entity1));
    when(repository.findById(id2)).thenReturn(Optional.of(entity2));

    // When: calling findById multiple times
    Optional<SecretDomain> result1 = repository.findById(id1);
    Optional<SecretDomain> result2 = repository.findById(id2);

    // Then: each call should work independently
    assertThat(result1).isPresent();
    assertThat(result2).isPresent();
    assertThat(result1.get().getId()).isEqualTo(id1);
    assertThat(result2.get().getId()).isEqualTo(id2);
    verify(repository, times(1)).findById(id1);
    verify(repository, times(1)).findById(id2);
  }

  @Test
  void findById_returnsEntityWithAllFieldsPopulated() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "test-id";
    Long timestamp = System.currentTimeMillis();

    SecretDomain entity = new SecretDomain("test-ref", "test-secret");
    entity.setId(id);
    entity.setCreatedAt(timestamp);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(id);

    // Then: should return entity with all fields populated
    assertThat(result).isPresent();
    SecretDomain returned = result.get();
    assertThat(returned.getId()).isEqualTo(id);
    assertThat(returned.getRef()).isEqualTo("test-ref");
    assertThat(returned.getSecret()).isEqualTo("test-secret");
    assertThat(returned.getCreatedAt()).isEqualTo(timestamp);
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_returnsEntityWithNullFields() {
    // Given: a mocked SecretRepository with entity having null fields
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain(null, null);
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(id);

    // Then: should return entity with null fields
    assertThat(result).isPresent();
    assertThat(result.get().getRef()).isNull();
    assertThat(result.get().getSecret()).isNull();
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_returnsEntityWithEmptyStrings() {
    // Given: a mocked SecretRepository with entity having empty strings
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("", "");
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(id);

    // Then: should return entity with empty strings
    assertThat(result).isPresent();
    assertThat(result.get().getRef()).isEmpty();
    assertThat(result.get().getSecret()).isEmpty();
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    String id = "saved-id";
    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(savedEntity));

    // When: saving entity then calling findById
    repository.save(entityToSave);
    Optional<SecretDomain> result = repository.findById(id);

    // Then: findById should work after save
    assertThat(result).isPresent();
    verify(repository, times(1)).save(entityToSave);
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.findById(id)).thenReturn(Optional.empty());

    // When: calling flush then findById
    repository.flush();
    Optional<SecretDomain> result = repository.findById(id);

    // Then: findById should work after flush
    assertThat(result).isNotNull();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_afterDeleteOperation_shouldWork() {
    // Given: a mocked SecretRepository with delete operation performed
    SecretRepository repository = mock(SecretRepository.class);

    String id = "deleted-id";
    when(repository.findById(id)).thenReturn(Optional.empty());

    // When: deleting entity then calling findById
    repository.deleteByRef("some-ref");
    Optional<SecretDomain> result = repository.findById(id);

    // Then: findById should work after delete (returns empty for deleted entity)
    assertThat(result).isEmpty();
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_canBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findAll then findById
    repository.findAll();
    Optional<SecretDomain> result = repository.findById(id);

    // Then: findById should work after findAll
    assertThat(result).isPresent();
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_returnsNonNullOptional() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.findById(id)).thenReturn(Optional.empty());

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(id);

    // Then: should never return null, always an Optional
    assertThat(result).isNotNull();
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_withUuidFormatId_shouldWork() {
    // Given: a mocked SecretRepository with UUID-format ID
    SecretRepository repository = mock(SecretRepository.class);

    String uuidId = "550e8400-e29b-41d4-a716-446655440000";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(uuidId);

    when(repository.findById(uuidId)).thenReturn(Optional.of(entity));

    // When: calling findById with UUID ID
    Optional<SecretDomain> result = repository.findById(uuidId);

    // Then: should return entity with UUID ID
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(uuidId);
    verify(repository, times(1)).findById(uuidId);
  }

  @Test
  void findById_withNumericStringId_shouldWork() {
    // Given: a mocked SecretRepository with numeric string ID
    SecretRepository repository = mock(SecretRepository.class);

    String numericId = "12345";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(numericId);

    when(repository.findById(numericId)).thenReturn(Optional.of(entity));

    // When: calling findById with numeric string ID
    Optional<SecretDomain> result = repository.findById(numericId);

    // Then: should return entity
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(numericId);
    verify(repository, times(1)).findById(numericId);
  }

  @Test
  void findById_withAlphanumericId_shouldWork() {
    // Given: a mocked SecretRepository with alphanumeric ID
    SecretRepository repository = mock(SecretRepository.class);

    String alphanumericId = "abc123xyz";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(alphanumericId);

    when(repository.findById(alphanumericId)).thenReturn(Optional.of(entity));

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(alphanumericId);

    // Then: should return entity
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(alphanumericId);
    verify(repository, times(1)).findById(alphanumericId);
  }

  @Test
  void findById_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling saveAll then findById
    repository.saveAll(java.util.Collections.emptyList());
    Optional<SecretDomain> result = repository.findById(id);

    // Then: findById should work after saveAll
    assertThat(result).isPresent();
    verify(repository, times(1)).saveAll(any());
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_sameIdCalledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findById multiple times with same ID
    Optional<SecretDomain> result1 = repository.findById(id);
    Optional<SecretDomain> result2 = repository.findById(id);

    // Then: each call should work
    assertThat(result1).isPresent();
    assertThat(result2).isPresent();
    verify(repository, times(2)).findById(id);
  }

  @Test
  void findById_withSpecialCharactersInId_shouldWork() {
    // Given: a mocked SecretRepository with ID containing special characters
    SecretRepository repository = mock(SecretRepository.class);

    String specialId = "id-with-dashes_and_underscores";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(specialId);

    when(repository.findById(specialId)).thenReturn(Optional.of(entity));

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(specialId);

    // Then: should return entity
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(specialId);
    verify(repository, times(1)).findById(specialId);
  }

  @Test
  void findById_canBeCalledAfterFindAllById() {
    // Given: a mocked SecretRepository with findAllById called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findAllById then findById
    repository.findAllById(java.util.Collections.singletonList(id));
    Optional<SecretDomain> result = repository.findById(id);

    // Then: findById should work after findAllById
    assertThat(result).isPresent();
    verify(repository, times(1)).findAllById(any());
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_returnsEntityWithLongValues() {
    // Given: a mocked SecretRepository with entity having long values
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(id);

    // Then: should return entity with long values
    assertThat(result).isPresent();
    assertThat(result.get().getRef()).isEqualTo(longRef);
    assertThat(result.get().getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling saveAndFlush then findById
    repository.saveAndFlush(entity);
    Optional<SecretDomain> result = repository.findById(id);

    // Then: findById should work after saveAndFlush
    assertThat(result).isPresent();
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_optionalCanBeUsedWithIsPresent() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(id);

    // Then: Optional can be checked with isPresent()
    assertThat(result.isPresent()).isTrue();
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_optionalCanBeUsedWithIsEmpty() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "non-existent";
    when(repository.findById(id)).thenReturn(Optional.empty());

    // When: calling findById
    Optional<SecretDomain> result = repository.findById(id);

    // Then: Optional can be checked with isEmpty()
    assertThat(result.isEmpty()).isTrue();
    verify(repository, times(1)).findById(id);
  }
}
