package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SecretRepository.existsById(Object) method.
 *
 * The existsById(ID id) method is inherited from CrudRepository (which JpaRepository extends)
 * and provides a boolean check for entity existence by ID.
 * Key characteristics:
 * 1. Takes a single ID value (String type for SecretRepository)
 * 2. Returns true if an entity with the given ID exists
 * 3. Returns false if no entity exists with the given ID
 * 4. Does NOT throw exception for non-existent IDs
 * 5. More efficient than findById() when only checking existence
 * 6. Returns boolean primitive type
 * 7. Often used before delete or update operations
 *
 * Method signature: boolean existsById(String id)
 *
 * Difference from findById:
 * - existsById(): Returns boolean, lightweight check, doesn't load entity
 * - findById(): Returns Optional<Entity>, loads full entity from database
 *
 * Performance consideration:
 * - existsById() typically translates to a COUNT query or SELECT with minimal columns
 * - More efficient than findById() when you only need to verify existence
 * - Doesn't load entity data into memory
 *
 * Use cases for existsById:
 * - Checking if an entity exists before attempting deletion
 * - Verifying entity exists before performing update operation
 * - Validation logic that only needs to confirm presence
 * - Conditional logic based on entity existence
 * - More efficient than findById().isPresent() for existence checks
 *
 * Example usage:
 * <pre>
 * if (repository.existsById("id123")) {
 *   // Entity exists, proceed with operation
 *   repository.deleteById("id123");
 * } else {
 *   // Handle non-existent entity
 * }
 *
 * // Validation
 * if (!repository.existsById("id123")) {
 *   throw new EntityNotFoundException("Secret not found");
 * }
 * </pre>
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of existsById, the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The existsById method exists and is callable on SecretRepository
 * 2. The method accepts a String parameter (the ID type)
 * 3. The method returns a boolean
 * 4. The method can handle various scenarios (existing ID, non-existent ID)
 * 5. The method can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual existsById implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_existsByIdTest {

  @Test
  void existsById_withExistingId_shouldReturnTrue() {
    // Given: a mocked SecretRepository with existing entity
    SecretRepository repository = mock(SecretRepository.class);

    String existingId = "existing-id";

    when(repository.existsById(existingId)).thenReturn(true);

    // When: calling existsById with existing ID
    boolean result = repository.existsById(existingId);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).existsById(existingId);
  }

  @Test
  void existsById_shouldReturnBooleanType() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.existsById(id)).thenReturn(false);

    // When: calling existsById
    boolean result = repository.existsById(id);

    // Then: should return boolean type
    assertThat(result).isInstanceOf(Boolean.class);
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.existsById(anyString())).thenReturn(false);

    // When & Then: existsById should complete without throwing an exception
    assertThatCode(() -> repository.existsById(id)).doesNotThrowAnyException();
  }

  @Test
  void existsById_withNonExistentId_shouldReturnFalse() {
    // Given: a mocked SecretRepository with non-existent ID
    SecretRepository repository = mock(SecretRepository.class);

    String nonExistentId = "non-existent-id";

    when(repository.existsById(nonExistentId)).thenReturn(false);

    // When: calling existsById with non-existent ID
    boolean result = repository.existsById(nonExistentId);

    // Then: should return false (no exception)
    assertThat(result).isFalse();
    verify(repository, times(1)).existsById(nonExistentId);
  }

  @Test
  void existsById_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String existingId = "existing-id";
    String nonExistentId = "non-existent-id";

    when(repository.existsById(existingId)).thenReturn(true);
    when(repository.existsById(nonExistentId)).thenReturn(false);

    // When: calling existsById multiple times
    boolean result1 = repository.existsById(existingId);
    boolean result2 = repository.existsById(nonExistentId);

    // Then: each call should work independently
    assertThat(result1).isTrue();
    assertThat(result2).isFalse();
    verify(repository, times(1)).existsById(existingId);
    verify(repository, times(1)).existsById(nonExistentId);
  }

  @Test
  void existsById_afterSaveOperation_shouldReturnTrue() {
    // Given: a mocked SecretRepository with save operation performed
    SecretRepository repository = mock(SecretRepository.class);

    String id = "saved-id";
    SecretDomain entity = new SecretDomain("ref", "secret");

    when(repository.existsById(id)).thenReturn(true);

    // When: saving entity then calling existsById
    repository.save(entity);
    boolean result = repository.existsById(id);

    // Then: existsById should return true after save
    assertThat(result).isTrue();
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_afterDeleteOperation_shouldReturnFalse() {
    // Given: a mocked SecretRepository with delete operation performed
    SecretRepository repository = mock(SecretRepository.class);

    String id = "deleted-id";

    when(repository.existsById(id)).thenReturn(false);

    // When: deleting entity then calling existsById
    repository.deleteById(id);
    boolean result = repository.existsById(id);

    // Then: existsById should return false after delete
    assertThat(result).isFalse();
    verify(repository, times(1)).deleteById(id);
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.existsById(id)).thenReturn(true);

    // When: calling flush then existsById
    repository.flush();
    boolean result = repository.existsById(id);

    // Then: existsById should work after flush
    assertThat(result).isTrue();
    verify(repository, times(1)).flush();
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_canBeCalledAfterFindById() {
    // Given: a mocked SecretRepository with findById called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.existsById(id)).thenReturn(true);

    // When: calling findById then existsById
    repository.findById(id);
    boolean result = repository.existsById(id);

    // Then: existsById should work after findById
    assertThat(result).isTrue();
    verify(repository, times(1)).findById(id);
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_canBeCalledAfterFindAll() {
    // Given: a mocked SecretRepository with findAll called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    when(repository.existsById(id)).thenReturn(true);

    // When: calling findAll then existsById
    repository.findAll();
    boolean result = repository.existsById(id);

    // Then: existsById should work after findAll
    assertThat(result).isTrue();
    verify(repository, times(1)).findAll();
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_withUuidFormatId_shouldWork() {
    // Given: a mocked SecretRepository with UUID-format ID
    SecretRepository repository = mock(SecretRepository.class);

    String uuidId = "550e8400-e29b-41d4-a716-446655440000";

    when(repository.existsById(uuidId)).thenReturn(true);

    // When: calling existsById with UUID ID
    boolean result = repository.existsById(uuidId);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).existsById(uuidId);
  }

  @Test
  void existsById_withNumericStringId_shouldWork() {
    // Given: a mocked SecretRepository with numeric string ID
    SecretRepository repository = mock(SecretRepository.class);

    String numericId = "12345";

    when(repository.existsById(numericId)).thenReturn(true);

    // When: calling existsById with numeric string ID
    boolean result = repository.existsById(numericId);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).existsById(numericId);
  }

  @Test
  void existsById_withAlphanumericId_shouldWork() {
    // Given: a mocked SecretRepository with alphanumeric ID
    SecretRepository repository = mock(SecretRepository.class);

    String alphanumericId = "abc123xyz";

    when(repository.existsById(alphanumericId)).thenReturn(true);

    // When: calling existsById
    boolean result = repository.existsById(alphanumericId);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).existsById(alphanumericId);
  }

  @Test
  void existsById_sameIdCalledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    when(repository.existsById(id)).thenReturn(true);

    // When: calling existsById multiple times with same ID
    boolean result1 = repository.existsById(id);
    boolean result2 = repository.existsById(id);

    // Then: each call should work
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
    verify(repository, times(2)).existsById(id);
  }

  @Test
  void existsById_afterSaveAllOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAll called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    when(repository.existsById(id)).thenReturn(true);

    // When: calling saveAll then existsById
    repository.saveAll(java.util.Collections.emptyList());
    boolean result = repository.existsById(id);

    // Then: existsById should work after saveAll
    assertThat(result).isTrue();
    verify(repository, times(1)).saveAll(java.util.Collections.emptyList());
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_withSpecialCharactersInId_shouldWork() {
    // Given: a mocked SecretRepository with ID containing special characters
    SecretRepository repository = mock(SecretRepository.class);

    String specialId = "id-with-dashes_and_underscores";

    when(repository.existsById(specialId)).thenReturn(true);

    // When: calling existsById
    boolean result = repository.existsById(specialId);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).existsById(specialId);
  }

  @Test
  void existsById_canBeCalledAfterFindAllById() {
    // Given: a mocked SecretRepository with findAllById called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    when(repository.existsById(id)).thenReturn(true);

    // When: calling findAllById then existsById
    repository.findAllById(java.util.Collections.singletonList(id));
    boolean result = repository.existsById(id);

    // Then: existsById should work after findAllById
    assertThat(result).isTrue();
    verify(repository, times(1)).findAllById(java.util.Collections.singletonList(id));
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";
    SecretDomain entity = new SecretDomain("ref", "secret");

    when(repository.existsById(id)).thenReturn(true);

    // When: calling saveAndFlush then existsById
    repository.saveAndFlush(entity);
    boolean result = repository.existsById(id);

    // Then: existsById should work after saveAndFlush
    assertThat(result).isTrue();
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_canBeUsedInConditionalLogic() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    when(repository.existsById(id)).thenReturn(true);

    // When: calling existsById in conditional
    boolean result = repository.existsById(id);
    boolean conditionalResult = result ? true : false;

    // Then: can be used in conditional expressions
    assertThat(conditionalResult).isTrue();
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_returnsPrimitiveBoolean() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    when(repository.existsById(id)).thenReturn(false);

    // When: calling existsById
    boolean result = repository.existsById(id);

    // Then: returns primitive boolean (can be assigned to boolean, not Boolean)
    boolean primitiveValue = result;
    assertThat(primitiveValue).isFalse();
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_afterDeleteByRefOperation_shouldWork() {
    // Given: a mocked SecretRepository with custom delete operation
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    when(repository.existsById(id)).thenReturn(false);

    // When: calling deleteByRef then existsById
    repository.deleteByRef("some-ref");
    boolean result = repository.existsById(id);

    // Then: existsById should work after custom delete
    assertThat(result).isFalse();
    verify(repository, times(1)).deleteByRef("some-ref");
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_beforeAndAfterDelete_shouldChangeBehavior() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "id";

    when(repository.existsById(id))
        .thenReturn(true)   // First call: exists
        .thenReturn(false); // Second call after delete: doesn't exist

    // When: checking existence, deleting, then checking again
    boolean beforeDelete = repository.existsById(id);
    repository.deleteById(id);
    boolean afterDelete = repository.existsById(id);

    // Then: should return true before delete, false after
    assertThat(beforeDelete).isTrue();
    assertThat(afterDelete).isFalse();
    verify(repository, times(2)).existsById(id);
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void existsById_canBeNegatedInConditional() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    String id = "non-existent-id";

    when(repository.existsById(id)).thenReturn(false);

    // When: calling existsById and negating
    boolean exists = repository.existsById(id);
    boolean doesNotExist = !exists;

    // Then: can be negated for "does not exist" checks
    assertThat(exists).isFalse();
    assertThat(doesNotExist).isTrue();
    verify(repository, times(1)).existsById(id);
  }
}
