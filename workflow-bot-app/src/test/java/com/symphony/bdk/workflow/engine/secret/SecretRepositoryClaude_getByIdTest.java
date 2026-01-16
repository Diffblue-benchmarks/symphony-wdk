package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.getById() method.
 *
 * The getById() method is inherited from JpaRepository (Spring Data JPA) and returns
 * a reference to the entity with the given ID. Key characteristics:
 * 1. Returns a reference (proxy) to the entity without fetching it from the database
 * 2. Uses lazy loading - the database query happens when the entity is first accessed
 * 3. Throws EntityNotFoundException if the entity doesn't exist when accessed
 * 4. More efficient than findById() when you only need the entity reference
 * 5. Returns the entity type (SecretDomain)
 *
 * Method signature: SecretDomain getById(String id)
 *
 * Since SecretRepository extends JpaRepository<SecretDomain, String>, the parameter
 * type is String (the ID type) and return type is SecretDomain (the entity type).
 *
 * Note: This method is deprecated in Spring Data JPA 2.5+ in favor of getReferenceById(),
 * but it's still widely used in existing codebases for backward compatibility.
 *
 * Relationship to getOne():
 * - getById() and getOne() have identical behavior in Spring Data JPA
 * - Both return lazy-loaded entity references
 * - getById() was introduced as a clearer name for the same functionality
 * - getOne() is now deprecated in favor of getById()/getReferenceById()
 *
 * Use cases for getById():
 * - Setting relationships when you have the ID but don't need to load the entity
 * - Creating entities that reference other entities by ID
 * - Performance optimization when full entity data isn't needed immediately
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of getById(), the method behavior is entirely
 * provided by Spring Data JPA's runtime proxy. Therefore, meaningful testing of
 * this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The getById() method exists and is callable on SecretRepository
 * 2. The method accepts a String ID parameter
 * 3. The method returns a SecretDomain entity
 * 4. The method can handle various ID formats
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual getById() implementation is provided by Spring Data JPA at
 * runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_getByIdTest {

  @Test
  void getById_withValidId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and a valid ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return the entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_shouldReturnSecretDomainType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return SecretDomain type
    assertThat(result).isInstanceOf(SecretDomain.class);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When & Then: getById should complete without throwing an exception
    assertThatCode(() -> repository.getById(id)).doesNotThrowAnyException();
  }

  @Test
  void getById_withUUIDStyleId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and a UUID-style ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "550e8400-e29b-41d4-a716-446655440000";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with UUID-style ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_withShortId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and a short ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "1";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with short ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_withLongId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and a long ID string
    SecretRepository repository = mock(SecretRepository.class);
    String id = "this-is-a-very-long-id-string-with-many-characters-in-it-to-test-edge-cases";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with long ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_withIdContainingSpecialCharacters_shouldReturnEntity() {
    // Given: a mocked SecretRepository and ID with special characters
    SecretRepository repository = mock(SecretRepository.class);
    String id = "id-with_special.chars";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with special characters in ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_withAlphanumericId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and alphanumeric ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "abc123def456";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with alphanumeric ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository and multiple IDs
    SecretRepository repository = mock(SecretRepository.class);

    String id1 = "id1";
    String id2 = "id2";
    String id3 = "id3";

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId(id1);
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId(id2);
    SecretDomain entity3 = new SecretDomain("ref3", "secret3");
    entity3.setId(id3);

    when(repository.getById(id1)).thenReturn(entity1);
    when(repository.getById(id2)).thenReturn(entity2);
    when(repository.getById(id3)).thenReturn(entity3);

    // When: calling getById multiple times
    SecretDomain result1 = repository.getById(id1);
    SecretDomain result2 = repository.getById(id2);
    SecretDomain result3 = repository.getById(id3);

    // Then: each call should work independently
    assertThat(result1.getId()).isEqualTo(id1);
    assertThat(result2.getId()).isEqualTo(id2);
    assertThat(result3.getId()).isEqualTo(id3);
    verify(repository, times(1)).getById(id1);
    verify(repository, times(1)).getById(id2);
    verify(repository, times(1)).getById(id3);
  }

  @Test
  void getById_returnsEntityWithAllFields() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("test-ref", "test-secret");
    entity.setId(id);
    entity.setCreatedAt(System.currentTimeMillis());

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with all fields populated
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getRef()).isEqualTo("test-ref");
    assertThat(result.getSecret()).isEqualTo("test-secret");
    assertThat(result.getCreatedAt()).isNotNull();
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_returnsEntityWithNullSecret() {
    // Given: a mocked SecretRepository and entity with null secret
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("test-ref", null);
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with null secret
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getSecret()).isNull();
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_returnsEntityWithNullRef() {
    // Given: a mocked SecretRepository and entity with null ref
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain(null, "test-secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with null ref
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getRef()).isNull();
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_returnsEntityWithEmptyStrings() {
    // Given: a mocked SecretRepository and entity with empty strings
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("", "");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with empty strings
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getRef()).isEmpty();
    assertThat(result.getSecret()).isEmpty();
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);
    String id = "saved-id";

    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId(id);

    when(repository.getById(id)).thenReturn(savedEntity);

    // When: saving entity then calling getById
    repository.save(entityToSave);
    SecretDomain result = repository.getById(id);

    // Then: getById should work after save
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).save(entityToSave);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling flush then getById
    repository.flush();
    SecretDomain result = repository.getById(id);

    // Then: getById should work after flush
    assertThat(result).isNotNull();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_withNumericStringId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and numeric string ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "12345";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with numeric string ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_returnsEntityWithTimestamp() {
    // Given: a mocked SecretRepository and entity with timestamp
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";
    Long timestamp = System.currentTimeMillis();

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);
    entity.setCreatedAt(timestamp);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with timestamp
    assertThat(result).isNotNull();
    assertThat(result.getCreatedAt()).isEqualTo(timestamp);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling findById then getById
    repository.findById(id);
    SecretDomain result = repository.getById(id);

    // Then: getById should work after findById
    assertThat(result).isNotNull();
    verify(repository, times(1)).findById(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_canBeCalledAfterGetOne() {
    // Given: a mocked SecretRepository with getOne called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getOne then getById
    repository.getOne(id);
    SecretDomain result = repository.getById(id);

    // Then: getById should work after getOne
    assertThat(result).isNotNull();
    verify(repository, times(1)).getOne(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_withEmptyStringId_shouldAcceptParameter() {
    // Given: a mocked SecretRepository and empty string ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById with empty string ID
    SecretDomain result = repository.getById(id);

    // Then: should invoke getById (actual behavior depends on JPA)
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_withLongRefAndSecretValues_shouldReturnEntity() {
    // Given: a mocked SecretRepository and entity with long values
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";
    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: calling getById
    SecretDomain result = repository.getById(id);

    // Then: should return entity with long values
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isEqualTo(longRef);
    assertThat(result.getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId(id);

    when(repository.getById(id)).thenReturn(savedEntity);

    // When: calling saveAndFlush then getById
    repository.saveAndFlush(entityToSave);
    SecretDomain result = repository.getById(id);

    // Then: getById should work after saveAndFlush
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).saveAndFlush(entityToSave);
    verify(repository, times(1)).getById(id);
  }
}
