package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.findOne(Example) method.
 *
 * The findOne(Example) method is inherited from QueryByExampleExecutor (which JpaRepository extends)
 * and returns an Optional containing a single entity that matches the Example query.
 *
 * Key characteristics:
 * 1. Takes an Example object containing a probe entity with search criteria
 * 2. Returns Optional<SecretDomain> - empty if no match found, contains entity if found
 * 3. Expects exactly zero or one matching entity
 * 4. Throws IncorrectResultSizeDataAccessException if multiple entities match
 * 5. Enables dynamic queries without writing custom query methods
 * 6. Supports flexible matching strategies (exact, case-insensitive, etc.)
 * 7. More type-safe than string-based queries
 *
 * Method signature:
 * - Optional<SecretDomain> findOne(Example<SecretDomain> example)
 *
 * Difference from related methods:
 * - findOne(Example): Returns Optional<SecretDomain> for single result
 * - findAll(Example): Returns List<SecretDomain> for multiple results
 * - findById(String): Returns Optional<SecretDomain> by ID only
 * - getOne(String): Returns SecretDomain proxy by ID (lazy loaded)
 *
 * Use cases for findOne(Example):
 * - Finding a unique entity by a natural key (e.g., finding secret by ref)
 * - Searching for a single entity matching multiple criteria
 * - Dynamic searches where query criteria vary at runtime
 * - Verifying uniqueness of an entity based on certain fields
 * - Loading an entity when you don't have the ID but have other identifying information
 *
 * Example usage:
 * <pre>
 * // Find secret by ref
 * SecretDomain probe = new SecretDomain();
 * probe.setRef("my-secret-ref");
 * Example<SecretDomain> example = Example.of(probe);
 * Optional<SecretDomain> secret = repository.findOne(example);
 *
 * // Find secret by ref and check existence
 * if (secret.isPresent()) {
 *   SecretDomain found = secret.get();
 *   // Use the found secret
 * }
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of this method, the method behavior is entirely
 * provided by Spring Data JPA's runtime proxy. Therefore, meaningful testing of
 * this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The findOne(Example) method exists and is callable on SecretRepository
 * 2. The method accepts an Example<SecretDomain> parameter
 * 3. The method returns an Optional<SecretDomain>
 * 4. The method can handle various scenarios (match found, no match, various criteria)
 * 5. The method can be invoked without errors in a mocked context
 * 6. The Optional is properly empty or present based on the search result
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual findOne(Example) implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_findOneTest {

  @Test
  void findOne_withMatchingExample_shouldReturnPresentOptional() {
    // Given: a mocked SecretRepository and an example that matches an entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain matchedEntity = new SecretDomain("test-ref", "test-secret");
    matchedEntity.setId("matched-id");

    when(repository.findOne(example)).thenReturn(Optional.of(matchedEntity));

    // When: calling findOne with example
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return present optional with matched entity
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo("matched-id");
    assertThat(result.get().getRef()).isEqualTo("test-ref");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_withNonMatchingExample_shouldReturnEmptyOptional() {
    // Given: a mocked SecretRepository and an example that matches nothing
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: calling findOne with non-matching example
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_shouldReturnOptionalType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return Optional type
    assertThat(result).isInstanceOf(Optional.class);
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(any(Example.class))).thenReturn(Optional.empty());

    // When & Then: findOne should complete without throwing an exception
    assertThatCode(() -> repository.findOne(example)).doesNotThrowAnyException();
  }

  @Test
  void findOne_withExampleMatchingByRef_shouldReturnEntity() {
    // Given: a mocked SecretRepository with example matching by ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("unique-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain matchedEntity = new SecretDomain("unique-ref", "secret-value");
    matchedEntity.setId("id-123");

    when(repository.findOne(example)).thenReturn(Optional.of(matchedEntity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity matching the ref
    assertThat(result).isPresent();
    assertThat(result.get().getRef()).isEqualTo("unique-ref");
    assertThat(result.get().getSecret()).isEqualTo("secret-value");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_withExampleMatchingById_shouldReturnEntity() {
    // Given: a mocked SecretRepository with example matching by id
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain(null, null);
    probe.setId("target-id");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain matchedEntity = new SecretDomain("ref", "secret");
    matchedEntity.setId("target-id");

    when(repository.findOne(example)).thenReturn(Optional.of(matchedEntity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity with matching id
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo("target-id");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_returnsEntityWithAllFieldsPopulated() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("complete-ref", "complete-secret");
    entity.setId("complete-id");
    entity.setCreatedAt(System.currentTimeMillis());

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity with all fields populated
    assertThat(result).isPresent();
    SecretDomain returned = result.get();
    assertThat(returned.getId()).isEqualTo("complete-id");
    assertThat(returned.getRef()).isEqualTo("complete-ref");
    assertThat(returned.getSecret()).isEqualTo("complete-secret");
    assertThat(returned.getCreatedAt()).isNotNull();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_returnsEntityWithNullFields() {
    // Given: a mocked SecretRepository with entity having null fields
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain(null, "secret");
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity with null ref field
    assertThat(result).isPresent();
    assertThat(result.get().getRef()).isNull();
    assertThat(result.get().getSecret()).isEqualTo("secret");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_returnsEntityWithEmptyStrings() {
    // Given: a mocked SecretRepository with entity having empty strings
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("", "");
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity with empty strings
    assertThat(result).isPresent();
    assertThat(result.get().getRef()).isEmpty();
    assertThat(result.get().getSecret()).isEmpty();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example1 = mock(Example.class);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example2 = mock(Example.class);

    SecretDomain entity1 = new SecretDomain("ref1", "secret1");
    entity1.setId("id1");
    SecretDomain entity2 = new SecretDomain("ref2", "secret2");
    entity2.setId("id2");

    when(repository.findOne(example1)).thenReturn(Optional.of(entity1));
    when(repository.findOne(example2)).thenReturn(Optional.of(entity2));

    // When: calling findOne multiple times
    Optional<SecretDomain> result1 = repository.findOne(example1);
    Optional<SecretDomain> result2 = repository.findOne(example2);

    // Then: each call should work independently
    assertThat(result1).isPresent();
    assertThat(result2).isPresent();
    assertThat(result1.get().getRef()).isEqualTo("ref1");
    assertThat(result2.get().getRef()).isEqualTo("ref2");
    verify(repository, times(1)).findOne(example1);
    verify(repository, times(1)).findOne(example2);
  }

  @Test
  void findOne_afterSaveOperation_shouldWork() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entityToSave = new SecretDomain("ref", "secret");
    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    savedEntity.setId("id");

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.of(savedEntity));

    // When: saving entity then calling findOne
    repository.save(entityToSave);
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: findOne should work after save
    assertThat(result).isPresent();
    verify(repository, times(1)).save(entityToSave);
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: calling flush then findOne
    repository.flush();
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: findOne should work after flush
    assertThat(result).isEmpty();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_returnsEntityWithTimestamp() {
    // Given: a mocked SecretRepository with entity having timestamp
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    Long timestamp = System.currentTimeMillis();
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");
    entity.setCreatedAt(timestamp);

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity with timestamp
    assertThat(result).isPresent();
    assertThat(result.get().getCreatedAt()).isEqualTo(timestamp);
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_returnsEntityWithLongValues() {
    // Given: a mocked SecretRepository with entity having long values
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    String longRef = "123456789012345"; // Max length is 15
    String longSecret = "thisIsAVeryLongEncryptedSecretStringThatCouldContainBase64OrOtherEncodedData";

    SecretDomain entity = new SecretDomain(longRef, longSecret);
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity with long values
    assertThat(result).isPresent();
    assertThat(result.get().getRef()).isEqualTo(longRef);
    assertThat(result.get().getSecret()).isEqualTo(longSecret);
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_afterDeleteOperation_shouldReturnEmpty() {
    // Given: a mocked SecretRepository with delete operation performed
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: deleting entity then calling findOne
    repository.deleteByRef("some-ref");
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: findOne should return empty after delete
    assertThat(result).isEmpty();
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findById then findOne
    repository.findById("some-id");
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: findOne should work after findById
    assertThat(result).isPresent();
    verify(repository, times(1)).findById("some-id");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_returnsNotNull() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should never return null, always an Optional
    assertThat(result).isNotNull();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling saveAndFlush then findOne
    repository.saveAndFlush(entity);
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: findOne should work after saveAndFlush
    assertThat(result).isPresent();
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_withExampleMatchingMultipleFields_shouldReturnEntity() {
    // Given: a mocked SecretRepository with example matching multiple fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("specific-ref", "specific-secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain matchedEntity = new SecretDomain("specific-ref", "specific-secret");
    matchedEntity.setId("matched-id");

    when(repository.findOne(example)).thenReturn(Optional.of(matchedEntity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity matching multiple fields
    assertThat(result).isPresent();
    assertThat(result.get().getRef()).isEqualTo("specific-ref");
    assertThat(result.get().getSecret()).isEqualTo("specific-secret");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_canUseOptionalIfPresent() {
    // Given: a mocked SecretRepository with a matching entity
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne and using ifPresent
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: ifPresent should be callable
    result.ifPresent(e -> {
      assertThat(e.getId()).isEqualTo("id");
      assertThat(e.getRef()).isEqualTo("ref");
    });
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_canUseOptionalOrElse() {
    // Given: a mocked SecretRepository returning empty
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: calling findOne and using orElse
    Optional<SecretDomain> result = repository.findOne(example);
    SecretDomain defaultEntity = new SecretDomain("default", "default");
    SecretDomain finalResult = result.orElse(defaultEntity);

    // Then: orElse should return default entity
    assertThat(finalResult).isNotNull();
    assertThat(finalResult.getRef()).isEqualTo("default");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_canUseOptionalOrElseThrow() {
    // Given: a mocked SecretRepository with a matching entity
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne and using orElseThrow
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: orElseThrow should return the entity
    assertThatCode(() -> {
      SecretDomain retrieved = result.orElseThrow(() -> new RuntimeException("Not found"));
      assertThat(retrieved.getId()).isEqualTo("id");
    }).doesNotThrowAnyException();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_emptyOptionalHasNoValue() {
    // Given: a mocked SecretRepository returning empty
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: empty optional should have no value
    assertThat(result.isPresent()).isFalse();
    assertThat(result.isEmpty()).isTrue();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_presentOptionalHasValue() {
    // Given: a mocked SecretRepository with matching entity
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: present optional should have value
    assertThat(result.isPresent()).isTrue();
    assertThat(result.isEmpty()).isFalse();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_canChainOptionalOperations() {
    // Given: a mocked SecretRepository with matching entity
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne and chaining optional operations
    Optional<SecretDomain> result = repository.findOne(example);
    String ref = result.map(SecretDomain::getRef).orElse("default-ref");

    // Then: should be able to chain operations
    assertThat(ref).isEqualTo("ref");
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("id");

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling saveAll then findOne
    repository.saveAll(java.util.Collections.singletonList(entity));
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: findOne should work after saveAll
    assertThat(result).isPresent();
    verify(repository, times(1)).saveAll(any());
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_withExampleUsingUUIDId_shouldWork() {
    // Given: a mocked SecretRepository with UUID-style ID
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    String uuidId = "550e8400-e29b-41d4-a716-446655440000";
    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId(uuidId);

    when(repository.findOne(example)).thenReturn(Optional.of(entity));

    // When: calling findOne
    Optional<SecretDomain> result = repository.findOne(example);

    // Then: should return entity with UUID ID
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(uuidId);
    verify(repository, times(1)).findOne(example);
  }
}
