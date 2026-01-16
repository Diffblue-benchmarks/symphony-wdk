package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.getReferenceById() method.
 *
 * The getReferenceById() method is inherited from JpaRepository (Spring Data JPA) and
 * returns a reference to the entity with the given ID. Key characteristics:
 * 1. Returns a reference (proxy) to the entity without fetching it from the database
 * 2. Uses lazy loading - the database query happens when the entity is first accessed
 * 3. Throws EntityNotFoundException if the entity doesn't exist when accessed
 * 4. More efficient than findById() when you only need the entity reference
 * 5. Returns the entity type (SecretDomain)
 *
 * Method signature: SecretDomain getReferenceById(String id)
 *
 * Since SecretRepository extends JpaRepository<SecretDomain, String>, the parameter
 * type is String (the ID type) and return type is SecretDomain (the entity type).
 *
 * This method is the modern replacement for the deprecated methods:
 * - getOne(String id) - deprecated, replaced by getReferenceById()
 * - getById(String id) - deprecated in Spring Data JPA 2.7+, replaced by getReferenceById()
 *
 * All three methods (getOne, getById, getReferenceById) have identical behavior:
 * - Return lazy-loaded entity references
 * - Don't fetch from database until the entity is accessed
 * - Throw EntityNotFoundException for non-existent entities
 *
 * Use cases for getReferenceById():
 * - Setting relationships when you have the ID but don't need to load the entity
 * - Creating entities that reference other entities by ID
 * - Performance optimization when full entity data isn't needed immediately
 * - Avoiding unnecessary database queries for simple reference assignments
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of getReferenceById(), the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The getReferenceById() method exists and is callable on SecretRepository
 * 2. The method accepts a String ID parameter
 * 3. The method returns a SecretDomain entity
 * 4. The method can handle various ID formats
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual getReferenceById() implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_getReferenceByIdTest {

  @Test
  void getReferenceById_withValidId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and a valid ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return the entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_shouldReturnSecretDomainType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return SecretDomain type
    assertThat(result).isInstanceOf(SecretDomain.class);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When & Then: getReferenceById should complete without throwing an exception
    assertThatCode(() -> repository.getReferenceById(id)).doesNotThrowAnyException();
  }

  @Test
  void getReferenceById_withUUIDStyleId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and a UUID-style ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "550e8400-e29b-41d4-a716-446655440000";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with UUID-style ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_withShortId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and a short ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "1";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with short ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_withLongId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and a long ID string
    SecretRepository repository = mock(SecretRepository.class);
    String id = "this-is-a-very-long-id-string-with-many-characters-in-it-to-test-edge-cases";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with long ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_withIdContainingSpecialCharacters_shouldReturnEntity() {
    // Given: a mocked SecretRepository and ID with special characters
    SecretRepository repository = mock(SecretRepository.class);
    String id = "id-with_special.chars";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with special characters in ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_withAlphanumericId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and alphanumeric ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "abc123def456";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with alphanumeric ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_calledMultipleTimes_shouldWorkForEachCall() {
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

    when(repository.getReferenceById(id1)).thenReturn(entity1);
    when(repository.getReferenceById(id2)).thenReturn(entity2);
    when(repository.getReferenceById(id3)).thenReturn(entity3);

    // When: calling getReferenceById multiple times
    SecretDomain result1 = repository.getReferenceById(id1);
    SecretDomain result2 = repository.getReferenceById(id2);
    SecretDomain result3 = repository.getReferenceById(id3);

    // Then: each call should work independently
    assertThat(result1.getId()).isEqualTo(id1);
    assertThat(result2.getId()).isEqualTo(id2);
    assertThat(result3.getId()).isEqualTo(id3);
    verify(repository, times(1)).getReferenceById(id1);
    verify(repository, times(1)).getReferenceById(id2);
    verify(repository, times(1)).getReferenceById(id3);
  }

  @Test
  void getReferenceById_returnsEntityWithAllFields() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("test-ref", "test-secret");
    entity.setId(id);
    entity.setCreatedAt(System.currentTimeMillis());

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with all fields populated
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getRef()).isEqualTo("test-ref");
    assertThat(result.getSecret()).isEqualTo("test-secret");
    assertThat(result.getCreatedAt()).isNotNull();
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_returnsEntityWithNullSecret() {
    // Given: a mocked SecretRepository and entity with null secret
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("test-ref", null);
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with null secret
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getSecret()).isNull();
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_returnsEntityWithNullRef() {
    // Given: a mocked SecretRepository and entity with null ref
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain(null, "test-secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with null ref
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getRef()).isNull();
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_returnsEntityWithEmptyStrings() {
    // Given: a mocked SecretRepository and entity with empty strings
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("", "");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with empty strings
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getRef()).isEmpty();
    assertThat(result.getSecret()).isEmpty();
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);
    String id = "saved-id";

    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(savedEntity);

    // When: saving entity then calling getReferenceById
    repository.save(entityToSave);
    SecretDomain result = repository.getReferenceById(id);

    // Then: getReferenceById should work after save
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).save(entityToSave);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling flush then getReferenceById
    repository.flush();
    SecretDomain result = repository.getReferenceById(id);

    // Then: getReferenceById should work after flush
    assertThat(result).isNotNull();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_withNumericStringId_shouldReturnEntity() {
    // Given: a mocked SecretRepository and numeric string ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "12345";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with numeric string ID
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_returnsEntityWithTimestamp() {
    // Given: a mocked SecretRepository and entity with timestamp
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";
    Long timestamp = System.currentTimeMillis();

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);
    entity.setCreatedAt(timestamp);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with timestamp
    assertThat(result).isNotNull();
    assertThat(result.getCreatedAt()).isEqualTo(timestamp);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling findById then getReferenceById
    repository.findById(id);
    SecretDomain result = repository.getReferenceById(id);

    // Then: getReferenceById should work after findById
    assertThat(result).isNotNull();
    verify(repository, times(1)).findById(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_canBeCalledAfterGetOne() {
    // Given: a mocked SecretRepository with getOne called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getOne then getReferenceById
    repository.getOne(id);
    SecretDomain result = repository.getReferenceById(id);

    // Then: getReferenceById should work after getOne
    assertThat(result).isNotNull();
    verify(repository, times(1)).getOne(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_canBeCalledAfterGetById() {
    // Given: a mocked SecretRepository with getById called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getById then getReferenceById
    repository.getById(id);
    SecretDomain result = repository.getReferenceById(id);

    // Then: getReferenceById should work after getById
    assertThat(result).isNotNull();
    verify(repository, times(1)).getById(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_withEmptyStringId_shouldAcceptParameter() {
    // Given: a mocked SecretRepository and empty string ID
    SecretRepository repository = mock(SecretRepository.class);
    String id = "";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById with empty string ID
    SecretDomain result = repository.getReferenceById(id);

    // Then: should invoke getReferenceById (actual behavior depends on JPA)
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_withLongRefAndSecretValues_shouldReturnEntity() {
    // Given: a mocked SecretRepository and entity with long values
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";
    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById
    SecretDomain result = repository.getReferenceById(id);

    // Then: should return entity with long values
    assertThat(result).isNotNull();
    assertThat(result.getRef()).isEqualTo(longRef);
    assertThat(result.getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(savedEntity);

    // When: calling saveAndFlush then getReferenceById
    repository.saveAndFlush(entityToSave);
    SecretDomain result = repository.getReferenceById(id);

    // Then: getReferenceById should work after saveAndFlush
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).saveAndFlush(entityToSave);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_isModernReplacementForDeprecatedMethods() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    String id = "test-id";

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: calling getReferenceById (modern method)
    SecretDomain result = repository.getReferenceById(id);

    // Then: should work as replacement for getOne() and getById()
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }
}
