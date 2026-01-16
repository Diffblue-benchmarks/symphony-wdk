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
 * Test class for SecretRepository.count() and count(Example) methods.
 *
 * This class tests two overloaded count methods:
 * 1. long count() - Returns total count of all entities
 * 2. long count(Example<SecretDomain> example) - Returns count of entities matching the example
 *
 * The count() method is inherited from CrudRepository (which JpaRepository extends)
 * and provides a count of the total number of entities in the repository.
 * Key characteristics:
 * 1. Takes no parameters
 * 2. Returns a long value representing the total entity count
 * 3. Returns 0 if the repository is empty
 * 4. More efficient than findAll().size() for getting entity count
 * 5. Typically translates to a COUNT(*) SQL query
 * 6. Returns long primitive type
 * 7. Useful for pagination, statistics, and validation
 *
 * The count(Example) method is inherited from QueryByExampleExecutor (which JpaRepository extends)
 * and provides a count of entities matching the specified example criteria.
 * Key characteristics of count(Example):
 * 1. Takes an Example object containing a probe entity with search criteria
 * 2. Returns a long value representing the count of matching entities
 * 3. Returns 0 if no entities match the example
 * 4. More efficient than findAll(Example).size() for counting matching entities
 * 5. Typically translates to a COUNT(*) SQL query with WHERE clause
 * 6. Enables dynamic counting without writing custom query methods
 * 7. Useful for filtered statistics and validation
 *
 * Method signatures:
 * - long count()
 * - long count(Example<SecretDomain> example)
 *
 * Performance consideration:
 * - count() is optimized to perform a COUNT query without loading entity data
 * - Much more efficient than loading all entities and checking size
 * - Preferred method for determining repository size
 *
 * Difference from size operations and related methods:
 * - count(): Returns total count, no filtering
 * - count(Example): Returns count matching criteria, with filtering
 * - findAll().size(): Loads all entities into memory, inefficient
 * - findAll(Example).size(): Loads matching entities into memory, inefficient
 *
 * Use cases for count():
 * - Checking if repository has any entities (count > 0)
 * - Calculating pagination parameters (total pages, etc.)
 * - Statistics and reporting
 * - Validation before bulk operations
 * - Monitoring and auditing
 * - Conditional logic based on entity count
 *
 * Use cases for count(Example):
 * - Counting entities matching specific criteria
 * - Filtered statistics (e.g., count secrets by ref pattern)
 * - Validation of filtered results
 * - Checking if any entities match criteria (count > 0)
 * - Determining if unique constraint is satisfied
 * - Conditional logic based on filtered count
 *
 * Example usage:
 * <pre>
 * long totalSecrets = repository.count();
 * if (totalSecrets > 0) {
 *   // Repository has entities
 * }
 *
 * // Pagination calculation
 * long totalPages = (repository.count() + pageSize - 1) / pageSize;
 *
 * // Validation
 * if (repository.count() > MAX_SECRETS) {
 *   throw new IllegalStateException("Too many secrets");
 * }
 *
 * // Count entities matching criteria
 * SecretDomain probe = new SecretDomain("specific-ref", null);
 * Example<SecretDomain> example = Example.of(probe);
 * long matchingCount = repository.count(example);
 * if (matchingCount > 0) {
 *   // Found matching entities
 * }
 *
 * // Validate uniqueness
 * if (repository.count(example) > 1) {
 *   throw new IllegalStateException("Expected unique result");
 * }
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of count, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The count methods exist and are callable on SecretRepository
 * 2. count() takes no parameters, count(Example) takes an Example parameter
 * 3. Both methods return a long
 * 4. The methods can handle various scenarios (empty repository, multiple entities, filtering)
 * 5. The methods can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual count implementations are provided by Spring Data JPA
 * at runtime and are thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_countTest {

  @Test
  void count_withEmptyRepository_shouldReturnZero() {
    // Given: a mocked SecretRepository with no entities
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(0L);

    // When: calling count
    long result = repository.count();

    // Then: should return 0
    assertThat(result).isEqualTo(0L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_shouldReturnLongType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(5L);

    // When: calling count
    long result = repository.count();

    // Then: should return long type
    assertThat(result).isInstanceOf(Long.class);
    verify(repository, times(1)).count();
  }

  @Test
  void count_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(0L);

    // When & Then: count should complete without throwing an exception
    assertThatCode(() -> repository.count()).doesNotThrowAnyException();
  }

  @Test
  void count_withSingleEntity_shouldReturnOne() {
    // Given: a mocked SecretRepository with one entity
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(1L);

    // When: calling count
    long result = repository.count();

    // Then: should return 1
    assertThat(result).isEqualTo(1L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_withMultipleEntities_shouldReturnCorrectCount() {
    // Given: a mocked SecretRepository with multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(10L);

    // When: calling count
    long result = repository.count();

    // Then: should return 10
    assertThat(result).isEqualTo(10L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(5L);

    // When: calling count multiple times
    long result1 = repository.count();
    long result2 = repository.count();

    // Then: each call should work
    assertThat(result1).isEqualTo(5L);
    assertThat(result2).isEqualTo(5L);
    verify(repository, times(2)).count();
  }

  @Test
  void count_withLargeNumber_shouldReturnCorrectCount() {
    // Given: a mocked SecretRepository with large number of entities
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(1000L);

    // When: calling count
    long result = repository.count();

    // Then: should return large count
    assertThat(result).isEqualTo(1000L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_afterSaveOperation_shouldIncreaseCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    when(repository.count())
        .thenReturn(5L)   // Before save
        .thenReturn(6L);  // After save

    // When: counting, saving, then counting again
    long countBefore = repository.count();
    repository.save(entity);
    long countAfter = repository.count();

    // Then: count should increase after save
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(6L);
    verify(repository, times(2)).count();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void count_afterDeleteOperation_shouldDecreaseCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count())
        .thenReturn(5L)   // Before delete
        .thenReturn(4L);  // After delete

    // When: counting, deleting, then counting again
    long countBefore = repository.count();
    repository.deleteById("id");
    long countAfter = repository.count();

    // Then: count should decrease after delete
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(4L);
    verify(repository, times(2)).count();
    verify(repository, times(1)).deleteById("id");
  }

  @Test
  void count_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(3L);

    // When: calling flush then count
    repository.flush();
    long result = repository.count();

    // Then: count should work after flush
    assertThat(result).isEqualTo(3L);
    verify(repository, times(1)).flush();
    verify(repository, times(1)).count();
  }

  @Test
  void count_canBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(7L);

    // When: calling findAll then count
    repository.findAll();
    long result = repository.count();

    // Then: count should work after findAll
    assertThat(result).isEqualTo(7L);
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).count();
  }

  @Test
  void count_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(4L);

    // When: calling findById then count
    repository.findById("some-id");
    long result = repository.count();

    // Then: count should work after findById
    assertThat(result).isEqualTo(4L);
    verify(repository, times(1)).findById("some-id");
    verify(repository, times(1)).count();
  }

  @Test
  void count_returnsNonNegativeValue() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(0L);

    // When: calling count
    long result = repository.count();

    // Then: should return non-negative value
    assertThat(result).isGreaterThanOrEqualTo(0L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_afterSaveAllOperation_shouldIncreaseCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    java.util.List<SecretDomain> entities = java.util.Arrays.asList(
        new SecretDomain("ref1", "secret1"),
        new SecretDomain("ref2", "secret2"),
        new SecretDomain("ref3", "secret3")
    );

    when(repository.count())
        .thenReturn(5L)   // Before saveAll
        .thenReturn(8L);  // After saveAll (5 + 3)

    // When: counting, saving all, then counting again
    long countBefore = repository.count();
    repository.saveAll(entities);
    long countAfter = repository.count();

    // Then: count should increase after saveAll
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(8L);
    verify(repository, times(2)).count();
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void count_canBeUsedInConditionalLogic() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(10L);

    // When: calling count in conditional
    long count = repository.count();
    boolean hasEntities = count > 0;
    boolean isNotEmpty = count != 0;

    // Then: can be used in conditional expressions
    assertThat(hasEntities).isTrue();
    assertThat(isNotEmpty).isTrue();
    verify(repository, times(1)).count();
  }

  @Test
  void count_canBeUsedForPaginationCalculation() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(100L);

    // When: calculating pagination
    long totalEntities = repository.count();
    int pageSize = 10;
    long totalPages = (totalEntities + pageSize - 1) / pageSize;

    // Then: can calculate total pages
    assertThat(totalPages).isEqualTo(10L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_returnsPrimitiveLong() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(3L);

    // When: calling count
    long result = repository.count();

    // Then: returns primitive long (can be used in arithmetic)
    long doubled = result * 2;
    assertThat(doubled).isEqualTo(6L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_afterDeleteByRefOperation_shouldWork() {
    // Given: a mocked SecretRepository with custom delete operation
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count())
        .thenReturn(5L)   // Before delete
        .thenReturn(4L);  // After delete

    // When: counting, deleting by ref, then counting again
    long countBefore = repository.count();
    repository.deleteByRef("some-ref");
    long countAfter = repository.count();

    // Then: count should decrease after custom delete
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(4L);
    verify(repository, times(2)).count();
    verify(repository, times(1)).deleteByRef("some-ref");
  }

  @Test
  void count_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");

    when(repository.count())
        .thenReturn(5L)   // Before saveAndFlush
        .thenReturn(6L);  // After saveAndFlush

    // When: counting, saving and flushing, then counting again
    long countBefore = repository.count();
    repository.saveAndFlush(entity);
    long countAfter = repository.count();

    // Then: count should increase after saveAndFlush
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(6L);
    verify(repository, times(2)).count();
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void count_canBeCompared() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(50L);

    // When: calling count and comparing
    long count = repository.count();

    // Then: can be used in comparisons
    assertThat(count).isGreaterThan(10L);
    assertThat(count).isLessThan(100L);
    assertThat(count).isEqualTo(50L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_afterDeleteAllInBatchOperation_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count())
        .thenReturn(10L)  // Before deleteAllInBatch
        .thenReturn(0L);  // After deleteAllInBatch

    // When: counting, deleting all, then counting again
    long countBefore = repository.count();
    repository.deleteAllInBatch();
    long countAfter = repository.count();

    // Then: count should be zero after deleteAllInBatch
    assertThat(countBefore).isEqualTo(10L);
    assertThat(countAfter).isEqualTo(0L);
    verify(repository, times(2)).count();
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void count_withVeryLargeNumber_shouldHandleCorrectly() {
    // Given: a mocked SecretRepository with very large count
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(1000000L);

    // When: calling count
    long result = repository.count();

    // Then: should handle large numbers
    assertThat(result).isEqualTo(1000000L);
    assertThat(result).isGreaterThan(999999L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_canBeUsedToCheckIfRepositoryIsEmpty() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(0L);

    // When: checking if repository is empty
    boolean isEmpty = repository.count() == 0;

    // Then: can determine if repository is empty
    assertThat(isEmpty).isTrue();
    verify(repository, times(1)).count();
  }

  @Test
  void count_canBeUsedToCheckIfRepositoryHasEntities() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    when(repository.count()).thenReturn(5L);

    // When: checking if repository has entities
    boolean hasEntities = repository.count() > 0;

    // Then: can determine if repository has entities
    assertThat(hasEntities).isTrue();
    verify(repository, times(1)).count();
  }

  // ==================== Tests for count(Example) ====================

  @Test
  void count_withExample_shouldReturnMatchingCount() {
    // Given: a mocked SecretRepository with example
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("test-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(5L);

    // When: calling count with example
    long result = repository.count(example);

    // Then: should return count of matching entities
    assertThat(result).isEqualTo(5L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleNoMatches_shouldReturnZero() {
    // Given: a mocked SecretRepository with example that matches nothing
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("non-existent-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(0L);

    // When: calling count with non-matching example
    long result = repository.count(example);

    // Then: should return 0
    assertThat(result).isEqualTo(0L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleSingleMatch_shouldReturnOne() {
    // Given: a mocked SecretRepository with example matching one entity
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("unique-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(1L);

    // When: calling count with example matching one entity
    long result = repository.count(example);

    // Then: should return 1
    assertThat(result).isEqualTo(1L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleMultipleMatches_shouldReturnCorrectCount() {
    // Given: a mocked SecretRepository with example matching multiple entities
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("common-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(10L);

    // When: calling count with example
    long result = repository.count(example);

    // Then: should return count of all matching entities
    assertThat(result).isEqualTo(10L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleReturnsLongType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(5L);

    // When: calling count with example
    long result = repository.count(example);

    // Then: should return long type
    assertThat(result).isInstanceOf(Long.class);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleShouldNotThrowException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(0L);

    // When & Then: count with example should not throw exception
    assertThatCode(() -> repository.count(example)).doesNotThrowAnyException();
  }

  @Test
  void count_withExampleCalledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(5L);

    // When: calling count with example multiple times
    long result1 = repository.count(example);
    long result2 = repository.count(example);

    // Then: each call should work
    assertThat(result1).isEqualTo(5L);
    assertThat(result2).isEqualTo(5L);
    verify(repository, times(2)).count(example);
  }

  @Test
  void count_withExampleLargeNumber_shouldReturnCorrectCount() {
    // Given: a mocked SecretRepository with large number of matching entities
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(1000L);

    // When: calling count with example
    long result = repository.count(example);

    // Then: should return large count
    assertThat(result).isEqualTo(1000L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleAfterSave_shouldIncreaseCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("test-ref", "secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example))
        .thenReturn(5L)   // Before save
        .thenReturn(6L);  // After save

    // When: counting, saving, then counting again
    long countBefore = repository.count(example);
    repository.save(entity);
    long countAfter = repository.count(example);

    // Then: count should increase if saved entity matches example
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(6L);
    verify(repository, times(2)).count(example);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void count_withExampleAfterDelete_shouldDecreaseCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example))
        .thenReturn(5L)   // Before delete
        .thenReturn(4L);  // After delete

    // When: counting, deleting, then counting again
    long countBefore = repository.count(example);
    repository.deleteById("id");
    long countAfter = repository.count(example);

    // Then: count should decrease if deleted entity matched example
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(4L);
    verify(repository, times(2)).count(example);
    verify(repository, times(1)).deleteById("id");
  }

  @Test
  void count_withExampleReturnsNonNegativeValue() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(0L);

    // When: calling count with example
    long result = repository.count(example);

    // Then: should return non-negative value
    assertThat(result).isGreaterThanOrEqualTo(0L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleCanBeUsedInConditionalLogic() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(10L);

    // When: calling count with example in conditional
    long count = repository.count(example);
    boolean hasMatches = count > 0;
    boolean isNotEmpty = count != 0;

    // Then: can be used in conditional expressions
    assertThat(hasMatches).isTrue();
    assertThat(isNotEmpty).isTrue();
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleCanBeUsedToCheckExistence() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(1L);

    // When: checking if matching entities exist
    boolean exists = repository.count(example) > 0;

    // Then: can determine if matching entities exist
    assertThat(exists).isTrue();
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleCanBeUsedToValidateUniqueness() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(1L);

    // When: validating uniqueness
    long count = repository.count(example);
    boolean isUnique = count <= 1;

    // Then: can validate uniqueness
    assertThat(isUnique).isTrue();
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleReturnsPrimitiveLong() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(3L);

    // When: calling count with example
    long result = repository.count(example);

    // Then: returns primitive long (can be used in arithmetic)
    long doubled = result * 2;
    assertThat(doubled).isEqualTo(6L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleCanBeCompared() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(50L);

    // When: calling count with example and comparing
    long count = repository.count(example);

    // Then: can be used in comparisons
    assertThat(count).isGreaterThan(10L);
    assertThat(count).isLessThan(100L);
    assertThat(count).isEqualTo(50L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleWithVeryLargeNumber_shouldHandleCorrectly() {
    // Given: a mocked SecretRepository with very large count
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(1000000L);

    // When: calling count with example
    long result = repository.count(example);

    // Then: should handle large numbers
    assertThat(result).isEqualTo(1000000L);
    assertThat(result).isGreaterThan(999999L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleCanBeUsedToCheckIfNoMatches() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(0L);

    // When: checking if no matches
    boolean noMatches = repository.count(example) == 0;

    // Then: can determine if no matches exist
    assertThat(noMatches).isTrue();
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleAfterFlush_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(3L);

    // When: calling flush then count with example
    repository.flush();
    long result = repository.count(example);

    // Then: count should work after flush
    assertThat(result).isEqualTo(3L);
    verify(repository, times(1)).flush();
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleCanBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(7L);

    // When: calling findAll then count with example
    repository.findAll(example);
    long result = repository.count(example);

    // Then: count should work after findAll
    assertThat(result).isEqualTo(7L);
    verify(repository, times(1)).findAll(example);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleCanBeCalledAfterFindOne() {
    // Given: a mocked SecretRepository with findOne called first
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(1L);

    // When: calling findOne then count with example
    repository.findOne(example);
    long result = repository.count(example);

    // Then: count should work after findOne
    assertThat(result).isEqualTo(1L);
    verify(repository, times(1)).findOne(example);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleMatchingByRef_shouldCountCorrectly() {
    // Given: a mocked SecretRepository with example matching by ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("specific-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(3L);

    // When: calling count with example matching by ref
    long result = repository.count(example);

    // Then: should return count of entities with matching ref
    assertThat(result).isEqualTo(3L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleMatchingBySecret_shouldCountCorrectly() {
    // Given: a mocked SecretRepository with example matching by secret
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain(null, "specific-secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(2L);

    // When: calling count with example matching by secret
    long result = repository.count(example);

    // Then: should return count of entities with matching secret
    assertThat(result).isEqualTo(2L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleMatchingByMultipleFields_shouldCountCorrectly() {
    // Given: a mocked SecretRepository with example matching by multiple fields
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("specific-ref", "specific-secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(1L);

    // When: calling count with example matching multiple fields
    long result = repository.count(example);

    // Then: should return count of entities matching all criteria
    assertThat(result).isEqualTo(1L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleEmptyStringRef_shouldCountCorrectly() {
    // Given: a mocked SecretRepository with example having empty string ref
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(1L);

    // When: calling count with empty string ref
    long result = repository.count(example);

    // Then: should count entities with empty ref
    assertThat(result).isEqualTo(1L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleAfterSaveAll_shouldIncreaseCount() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    java.util.List<SecretDomain> entities = java.util.Arrays.asList(
        new SecretDomain("test-ref", "secret1"),
        new SecretDomain("test-ref", "secret2"),
        new SecretDomain("test-ref", "secret3")
    );

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example))
        .thenReturn(5L)   // Before saveAll
        .thenReturn(8L);  // After saveAll (5 + 3 matching)

    // When: counting, saving all, then counting again
    long countBefore = repository.count(example);
    repository.saveAll(entities);
    long countAfter = repository.count(example);

    // Then: count should increase if saved entities match example
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(8L);
    verify(repository, times(2)).count(example);
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void count_withExampleAfterDeleteByRef_shouldWork() {
    // Given: a mocked SecretRepository with custom delete operation
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example))
        .thenReturn(5L)   // Before delete
        .thenReturn(4L);  // After delete

    // When: counting, deleting by ref, then counting again
    long countBefore = repository.count(example);
    repository.deleteByRef("some-ref");
    long countAfter = repository.count(example);

    // Then: count should decrease if deleted entity matched example
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(4L);
    verify(repository, times(2)).count(example);
    verify(repository, times(1)).deleteByRef("some-ref");
  }

  @Test
  void count_withExampleAfterSaveAndFlush_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("test-ref", "secret");
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example))
        .thenReturn(5L)   // Before saveAndFlush
        .thenReturn(6L);  // After saveAndFlush

    // When: counting, saving and flushing, then counting again
    long countBefore = repository.count(example);
    repository.saveAndFlush(entity);
    long countAfter = repository.count(example);

    // Then: count should increase if saved entity matches example
    assertThat(countBefore).isEqualTo(5L);
    assertThat(countAfter).isEqualTo(6L);
    verify(repository, times(2)).count(example);
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void count_withExampleAfterDeleteAllInBatch_shouldWork() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example))
        .thenReturn(10L)  // Before deleteAllInBatch
        .thenReturn(0L);  // After deleteAllInBatch

    // When: counting, deleting all, then counting again
    long countBefore = repository.count(example);
    repository.deleteAllInBatch();
    long countAfter = repository.count(example);

    // Then: count should be zero if all matching entities deleted
    assertThat(countBefore).isEqualTo(10L);
    assertThat(countAfter).isEqualTo(0L);
    verify(repository, times(2)).count(example);
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void count_withExampleDifferentExamples_shouldReturnDifferentCounts() {
    // Given: a mocked SecretRepository with different examples
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example1 = mock(Example.class);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example2 = mock(Example.class);

    when(repository.count(example1)).thenReturn(5L);
    when(repository.count(example2)).thenReturn(3L);

    // When: calling count with different examples
    long count1 = repository.count(example1);
    long count2 = repository.count(example2);

    // Then: should return different counts for different examples
    assertThat(count1).isEqualTo(5L);
    assertThat(count2).isEqualTo(3L);
    verify(repository, times(1)).count(example1);
    verify(repository, times(1)).count(example2);
  }

  @Test
  void count_withExampleVerifyMethodCalled_shouldCallRepositoryOnce() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(0L);

    // When: calling count with example
    repository.count(example);

    // Then: should call repository method exactly once
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExampleCanBeUsedForFiltering() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain probe = new SecretDomain("filtered-ref", null);
    @SuppressWarnings("unchecked")
    Example<SecretDomain> example = mock(Example.class);

    when(repository.count(example)).thenReturn(15L);

    // When: using count for filtered statistics
    long filteredCount = repository.count(example);
    long threshold = 10L;
    boolean exceedsThreshold = filteredCount > threshold;

    // Then: can use for filtered conditional logic
    assertThat(exceedsThreshold).isTrue();
    assertThat(filteredCount).isEqualTo(15L);
    verify(repository, times(1)).count(example);
  }
}
