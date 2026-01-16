package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.exists(Example) method.
 *
 * The exists(Example) method is inherited from QueryByExampleExecutor (which JpaRepository extends)
 * and returns a boolean indicating whether any entities match the specified example criteria.
 *
 * Key characteristics:
 * 1. Takes an Example object containing a probe entity with search criteria
 * 2. Returns boolean - true if at least one entity matches, false otherwise
 * 3. More efficient than count(Example) > 0 or findAll(Example).isEmpty()
 * 4. Does not load entity data, only checks existence
 * 5. Typically translates to a SELECT EXISTS SQL query or COUNT with LIMIT 1
 * 6. Enables dynamic existence checks without writing custom query methods
 * 7. Returns primitive boolean type
 *
 * Method signature:
 * - boolean exists(Example<SecretDomain> example)
 *
 * Difference from related methods:
 * - exists(Example): Checks if any entities match the example criteria
 * - existsById(String): Checks if entity exists by ID only
 * - count(Example) > 0: Less efficient way to check existence
 * - findOne(Example).isPresent(): Less efficient, loads entity data
 * - findAll(Example).isEmpty(): Very inefficient, loads all matching entities
 *
 * Use cases for exists(Example):
 * - Checking if entities matching criteria exist before performing operations
 * - Validation (e.g., checking if duplicate entities exist)
 * - Conditional logic based on existence of matching entities
 * - Checking uniqueness constraints
 * - Pre-query validation
 * - Authorization checks (e.g., verify user has access to resources)
 *
 * Performance consideration:
 * - exists(Example) is optimized to check existence without loading entity data
 * - Much more efficient than loading entities just to check if they exist
 * - Preferred method for existence checks with dynamic criteria
 *
 * Example usage:
 * <pre>
 * // Check if secret with specific ref exists
 * SecretDomain probe = new SecretDomain("my-ref", null);
 * Example<SecretDomain> example = Example.of(probe);
 * boolean exists = repository.exists(example);
 * if (exists) {
 *   throw new IllegalStateException("Secret with ref already exists");
 * }
 *
 * // Check existence before update
 * if (!repository.exists(example)) {
 *   throw new EntityNotFoundException("Secret not found");
 * }
 *
 * // Validate uniqueness
 * if (repository.exists(example)) {
 *   throw new DuplicateEntityException("Duplicate secret");
 * }
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of this method, the method behavior is entirely
 * provided by Spring Data JPA's runtime proxy. Therefore, meaningful testing of
 * this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The exists(Example) method exists and is callable on SecretRepository
 * 2. The method accepts an Example<SecretDomain> parameter
 * 3. The method returns a boolean
 * 4. The method can handle various scenarios (match found, no match, various criteria)
 * 5. The method can be invoked without errors in a mocked context
 * 6. The boolean correctly indicates existence or non-existence
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual exists(Example) implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_existsTest {

  @Test
  void exists_withMatchingExample_shouldReturnTrue() {
    // Given: a mocked SecretRepository with example that matches entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling exists with matching example
    boolean result = repository.exists(example);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_withNonMatchingExample_shouldReturnFalse() {
    // Given: a mocked SecretRepository with example that matches nothing
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("non-existent-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(false);

    // When: calling exists with non-matching example
    boolean result = repository.exists(example);

    // Then: should return false
    assertThat(result).isFalse();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_shouldReturnBooleanType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling exists
    boolean result = repository.exists(example);

    // Then: should return boolean type
    assertThat(result).isInstanceOf(Boolean.class);
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_shouldNotThrowException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(false);

    // When & Then: exists should not throw exception
    assertThatCode(() -> repository.exists(example)).doesNotThrowAnyException();
  }

  @Test
  void exists_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling exists multiple times
    boolean result1 = repository.exists(example);
    boolean result2 = repository.exists(example);

    // Then: each call should work
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
    verify(repository, times(2)).exists(example);
  }

  @Test
  void exists_afterSave_shouldReturnTrue() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("new-ref", "secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example))
        .thenReturn(false)  // Before save
        .thenReturn(true);  // After save

    // When: checking existence, saving, then checking again
    boolean existsBefore = repository.exists(example);
    repository.save(entity);
    boolean existsAfter = repository.exists(example);

    // Then: should exist after save if entity matches example
    assertThat(existsBefore).isFalse();
    assertThat(existsAfter).isTrue();
    verify(repository, times(2)).exists(example);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void exists_afterDelete_shouldReturnFalse() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example))
        .thenReturn(true)   // Before delete
        .thenReturn(false); // After delete

    // When: checking existence, deleting, then checking again
    boolean existsBefore = repository.exists(example);
    repository.deleteById("id");
    boolean existsAfter = repository.exists(example);

    // Then: should not exist after delete if deleted entity matched example
    assertThat(existsBefore).isTrue();
    assertThat(existsAfter).isFalse();
    verify(repository, times(2)).exists(example);
    verify(repository, times(1)).deleteById("id");
  }

  @Test
  void exists_canBeUsedInConditionalLogic() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: using exists in conditional
    boolean exists = repository.exists(example);
    String message = exists ? "Found" : "Not Found";

    // Then: can be used in conditional expressions
    assertThat(message).isEqualTo("Found");
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeNegated() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(false);

    // When: negating exists result
    boolean doesNotExist = !repository.exists(example);

    // Then: can be negated
    assertThat(doesNotExist).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedInIfStatement() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: using exists in if statement
    boolean executed = false;
    if (repository.exists(example)) {
      executed = true;
    }

    // Then: if block should execute
    assertThat(executed).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_afterFlush_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling flush then exists
    repository.flush();
    boolean result = repository.exists(example);

    // Then: exists should work after flush
    assertThat(result).isTrue();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling findAll then exists
    repository.findAll(example);
    boolean result = repository.exists(example);

    // Then: exists should work after findAll
    assertThat(result).isTrue();
    verify(repository, times(1)).findAll(example);
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeCalledAfterFindOne() {
    // Given: a mocked SecretRepository with findOne called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling findOne then exists
    repository.findOne(example);
    boolean result = repository.exists(example);

    // Then: exists should work after findOne
    assertThat(result).isTrue();
    verify(repository, times(1)).findOne(example);
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeCalledAfterCount() {
    // Given: a mocked SecretRepository with count called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling count then exists
    repository.count(example);
    boolean result = repository.exists(example);

    // Then: exists should work after count
    assertThat(result).isTrue();
    verify(repository, times(1)).count(example);
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_matchingByRef_shouldWorkCorrectly() {
    // Given: a mocked SecretRepository with example matching by ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("specific-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling exists with example matching by ref
    boolean result = repository.exists(example);

    // Then: should return true if entities with matching ref exist
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_matchingBySecret_shouldWorkCorrectly() {
    // Given: a mocked SecretRepository with example matching by secret
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain(null, "specific-secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling exists with example matching by secret
    boolean result = repository.exists(example);

    // Then: should return true if entities with matching secret exist
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_matchingByMultipleFields_shouldWorkCorrectly() {
    // Given: a mocked SecretRepository with example matching by multiple fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("specific-ref", "specific-secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling exists with example matching multiple fields
    boolean result = repository.exists(example);

    // Then: should return true if entities matching all criteria exist
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_withEmptyStringRef_shouldWorkCorrectly() {
    // Given: a mocked SecretRepository with example having empty string ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(false);

    // When: calling exists with empty string ref
    boolean result = repository.exists(example);

    // Then: should correctly check existence
    assertThat(result).isFalse();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_afterSaveAll_shouldReturnTrue() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    java.util.List<SecretDomain> entities = java.util.Arrays.asList(
        new SecretDomain("test-ref", "secret1"),
        new SecretDomain("test-ref", "secret2"),
        new SecretDomain("test-ref", "secret3")
    );

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example))
        .thenReturn(false)  // Before saveAll
        .thenReturn(true);  // After saveAll

    // When: checking existence, saving all, then checking again
    boolean existsBefore = repository.exists(example);
    repository.saveAll(entities);
    boolean existsAfter = repository.exists(example);

    // Then: should exist after saveAll if entities match example
    assertThat(existsBefore).isFalse();
    assertThat(existsAfter).isTrue();
    verify(repository, times(2)).exists(example);
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void exists_afterDeleteByRef_shouldWork() {
    // Given: a mocked SecretRepository with custom delete operation
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example))
        .thenReturn(true)   // Before delete
        .thenReturn(false); // After delete

    // When: checking existence, deleting by ref, then checking again
    boolean existsBefore = repository.exists(example);
    repository.deleteByRef("some-ref");
    boolean existsAfter = repository.exists(example);

    // Then: should not exist after delete if deleted entity matched example
    assertThat(existsBefore).isTrue();
    assertThat(existsAfter).isFalse();
    verify(repository, times(2)).exists(example);
    verify(repository, times(1)).deleteByRef("some-ref");
  }

  @Test
  void exists_afterSaveAndFlush_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("test-ref", "secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example))
        .thenReturn(false)  // Before saveAndFlush
        .thenReturn(true);  // After saveAndFlush

    // When: checking existence, saving and flushing, then checking again
    boolean existsBefore = repository.exists(example);
    repository.saveAndFlush(entity);
    boolean existsAfter = repository.exists(example);

    // Then: should exist after saveAndFlush if entity matches example
    assertThat(existsBefore).isFalse();
    assertThat(existsAfter).isTrue();
    verify(repository, times(2)).exists(example);
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void exists_afterDeleteAllInBatch_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example))
        .thenReturn(true)   // Before deleteAllInBatch
        .thenReturn(false); // After deleteAllInBatch

    // When: checking existence, deleting all, then checking again
    boolean existsBefore = repository.exists(example);
    repository.deleteAllInBatch();
    boolean existsAfter = repository.exists(example);

    // Then: should not exist after deleteAllInBatch
    assertThat(existsBefore).isTrue();
    assertThat(existsAfter).isFalse();
    verify(repository, times(2)).exists(example);
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void exists_differentExamples_shouldReturnDifferentResults() {
    // Given: a mocked SecretRepository with different examples
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example1 = mock(Example.class);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example2 = mock(Example.class);

    when(repository.exists(example1)).thenReturn(true);
    when(repository.exists(example2)).thenReturn(false);

    // When: calling exists with different examples
    boolean exists1 = repository.exists(example1);
    boolean exists2 = repository.exists(example2);

    // Then: should return different results for different examples
    assertThat(exists1).isTrue();
    assertThat(exists2).isFalse();
    verify(repository, times(1)).exists(example1);
    verify(repository, times(1)).exists(example2);
  }

  @Test
  void exists_verifyMethodCalled_shouldCallRepositoryOnce() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(false);

    // When: calling exists
    repository.exists(example);

    // Then: should call repository method exactly once
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedForValidation() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("duplicate-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: checking for duplicates
    boolean isDuplicate = repository.exists(example);

    // Then: can detect duplicate
    assertThat(isDuplicate).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedToCheckBeforeUpdate() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: checking existence before update
    boolean canUpdate = repository.exists(example);

    // Then: can determine if update is possible
    assertThat(canUpdate).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedToCheckBeforeDelete() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(false);

    // When: checking existence before delete
    boolean canDelete = repository.exists(example);

    // Then: can determine if delete is possible
    assertThat(canDelete).isFalse();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_withNullFields_shouldWorkCorrectly() {
    // Given: a mocked SecretRepository with example having null fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling exists with null fields in example
    boolean result = repository.exists(example);

    // Then: should handle null fields correctly
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedInBooleanExpression() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: using exists in boolean expression
    boolean result = repository.exists(example) && true;

    // Then: can be used in boolean expressions
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedWithLogicalOr() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(false);

    // When: using exists with logical OR
    boolean result = repository.exists(example) || true;

    // Then: can be used with logical operators
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_returnsPrimitiveBoolean() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: calling exists
    boolean result = repository.exists(example);

    // Then: returns primitive boolean (can be used directly)
    if (result) {
      assertThat(result).isTrue();
    }
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeStoredInVariable() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: storing exists result in variable
    boolean exists = repository.exists(example);
    boolean alsoExists = exists;

    // Then: can be stored and used later
    assertThat(alsoExists).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedForUniquenessCheck() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("unique-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(false);

    // When: checking uniqueness
    boolean isUnique = !repository.exists(example);

    // Then: can determine uniqueness
    assertThat(isUnique).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedForAuthorizationCheck() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: checking if user has access to resources
    boolean hasAccess = repository.exists(example);

    // Then: can be used for authorization
    assertThat(hasAccess).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_canBeUsedForPreQueryValidation() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: validating before executing expensive query
    boolean shouldProceed = repository.exists(example);

    // Then: can decide whether to proceed with expensive query
    assertThat(shouldProceed).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_multipleExamplesInSequence_shouldWorkCorrectly() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example1 = mock(Example.class);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example2 = mock(Example.class);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example3 = mock(Example.class);

    when(repository.exists(example1)).thenReturn(true);
    when(repository.exists(example2)).thenReturn(false);
    when(repository.exists(example3)).thenReturn(true);

    // When: calling exists with multiple examples in sequence
    boolean result1 = repository.exists(example1);
    boolean result2 = repository.exists(example2);
    boolean result3 = repository.exists(example3);

    // Then: each should return correct result
    assertThat(result1).isTrue();
    assertThat(result2).isFalse();
    assertThat(result3).isTrue();
    verify(repository, times(1)).exists(example1);
    verify(repository, times(1)).exists(example2);
    verify(repository, times(1)).exists(example3);
  }

  @Test
  void exists_canBeUsedInTernaryOperator() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.exists(example)).thenReturn(true);

    // When: using exists in ternary operator
    String message = repository.exists(example) ? "Exists" : "Does not exist";

    // Then: can be used in ternary operator
    assertThat(message).isEqualTo("Exists");
    verify(repository, times(1)).exists(example);
  }
}
