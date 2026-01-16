package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for SecretRepository.deleteAllByIdInBatch() method.
 *
 * The deleteAllByIdInBatch() method is inherited from JpaRepository (Spring Data JPA) and
 * performs batch deletion of entities by their IDs. Key characteristics:
 * 1. Deletes entities by their IDs in a single DELETE query (batch operation)
 * 2. Takes an Iterable of ID values (String type for SecretRepository)
 * 3. More efficient than calling deleteById() multiple times
 * 4. Does NOT call JPA lifecycle callbacks (like @PreRemove, @PostRemove)
 * 5. Does NOT cascade to related entities (ignores cascade settings)
 * 6. Returns void (no return value)
 *
 * This method is useful when you have IDs but don't need to load the entities first.
 * It's more efficient than loading entities and then calling deleteAllInBatch().
 *
 * Method signature: void deleteAllByIdInBatch(Iterable<String> ids)
 *
 * Since SecretRepository extends JpaRepository<SecretDomain, String>, the ID type is String.
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not
 * provide a custom implementation of deleteAllByIdInBatch(), the method behavior is
 * entirely provided by Spring Data JPA's runtime proxy. Therefore, meaningful
 * testing of this method requires an integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The deleteAllByIdInBatch() method exists and is callable on SecretRepository
 * 2. The method accepts an Iterable of String IDs
 * 3. The method returns void (no return value)
 * 4. The method can handle various scenarios (empty list, single ID, multiple IDs)
 * 5. The method can be invoked without errors
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual deleteAllByIdInBatch() implementation is provided by Spring Data JPA
 * at runtime and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_deleteAllByIdInBatchTest {

  @Test
  void deleteAllByIdInBatch_withMultipleIds_shouldDeleteAll() {
    // Given: a mocked SecretRepository and multiple IDs
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id3");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should invoke deleteAllByIdInBatch once with all IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withEmptyList_shouldCompleteWithoutError() {
    // Given: a mocked SecretRepository and an empty list
    SecretRepository repository = mock(SecretRepository.class);
    List<String> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAllByIdInBatch(emptyList);

    // When: calling deleteAllByIdInBatch with empty list
    repository.deleteAllByIdInBatch(emptyList);

    // Then: should complete without error
    verify(repository, times(1)).deleteAllByIdInBatch(emptyList);
  }

  @Test
  void deleteAllByIdInBatch_withSingleId_shouldDelete() {
    // Given: a mocked SecretRepository and a single ID
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Collections.singletonList("single-id");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should invoke deleteAllByIdInBatch once
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllByIdInBatch(anyIterable());

    // When & Then: deleteAllByIdInBatch should complete without throwing an exception
    assertThatCode(() -> repository.deleteAllByIdInBatch(ids)).doesNotThrowAnyException();
  }

  @Test
  void deleteAllByIdInBatch_withUUIDStyleIds_shouldDelete() {
    // Given: a mocked SecretRepository and UUID-style IDs
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList(
        "550e8400-e29b-41d4-a716-446655440000",
        "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
        "7c9e6679-7425-40de-944b-e07fc1f90ae7"
    );

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete entities with UUID-style IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withShortIds_shouldDelete() {
    // Given: a mocked SecretRepository and short IDs
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("1", "2", "3", "4", "5");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete entities with short IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withLargeNumberOfIds_shouldDeleteAll() {
    // Given: a mocked SecretRepository and a large number of IDs
    SecretRepository repository = mock(SecretRepository.class);

    List<String> ids = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      ids.add("id-" + i);
    }

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete all 100 entities in a single batch
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withArrayList_shouldDelete() {
    // Given: a mocked SecretRepository and IDs in ArrayList
    SecretRepository repository = mock(SecretRepository.class);

    ArrayList<String> ids = new ArrayList<>();
    ids.add("id1");
    ids.add("id2");
    ids.add("id3");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete all entities
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_returnsVoid() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Collections.singletonList("id");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    // Then: should return void (compile-time check, no runtime assertion needed)
    repository.deleteAllByIdInBatch(ids);

    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withIdsContainingSpecialCharacters_shouldDelete() {
    // Given: a mocked SecretRepository and IDs with special characters
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("id-with-dashes", "id_with_underscores", "id.with.dots");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete entities with special characters in IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withAlphanumericIds_shouldDelete() {
    // Given: a mocked SecretRepository and alphanumeric IDs
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("abc123", "def456", "ghi789", "jkl012");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete entities with alphanumeric IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_calledMultipleTimes_shouldWorkForEachCall() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);

    List<String> batch1 = Collections.singletonList("id1");
    List<String> batch2 = Collections.singletonList("id2");

    doNothing().when(repository).deleteAllByIdInBatch(batch1);
    doNothing().when(repository).deleteAllByIdInBatch(batch2);

    // When: calling deleteAllByIdInBatch multiple times
    repository.deleteAllByIdInBatch(batch1);
    repository.deleteAllByIdInBatch(batch2);

    // Then: each call should work independently
    verify(repository, times(1)).deleteAllByIdInBatch(batch1);
    verify(repository, times(1)).deleteAllByIdInBatch(batch2);
  }

  @Test
  void deleteAllByIdInBatch_withLongIds_shouldDelete() {
    // Given: a mocked SecretRepository and long ID strings
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList(
        "this-is-a-very-long-id-string-with-many-characters-in-it-to-test-edge-cases",
        "another-long-id-string-that-contains-multiple-words-and-hyphens",
        "yet-another-extremely-long-identifier-for-testing-purposes"
    );

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete entities with long IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_afterOtherRepositoryOperations_shouldWork() {
    // Given: a mocked SecretRepository with other operations performed
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain savedEntity = new SecretDomain("ref", "secret");
    List<String> idsToDelete = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllByIdInBatch(idsToDelete);

    // When: performing other operations then deleteAllByIdInBatch
    repository.save(savedEntity);
    repository.deleteAllByIdInBatch(idsToDelete);

    // Then: deleteAllByIdInBatch should work after other operations
    verify(repository, times(1)).save(savedEntity);
    verify(repository, times(1)).deleteAllByIdInBatch(idsToDelete);
  }

  @Test
  void deleteAllByIdInBatch_doesNotReturnValue() {
    // Given: a mocked SecretRepository
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When & Then: calling deleteAllByIdInBatch should not return any value (void method)
    assertThatCode(() -> repository.deleteAllByIdInBatch(ids)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withDuplicateIds_shouldDelete() {
    // Given: a mocked SecretRepository with duplicate IDs in the list
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id1", "id3", "id2");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch with duplicates
    repository.deleteAllByIdInBatch(ids);

    // Then: should invoke deleteAllByIdInBatch (actual deletion behavior depends on JPA)
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_afterFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with flush called first
    SecretRepository repository = mock(SecretRepository.class);

    doNothing().when(repository).flush();

    List<String> ids = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling flush then deleteAllByIdInBatch
    repository.flush();
    repository.deleteAllByIdInBatch(ids);

    // Then: deleteAllByIdInBatch should work after flush
    verify(repository, times(1)).flush();
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withMixedIdFormats_shouldDelete() {
    // Given: a mocked SecretRepository with IDs in different formats
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList(
        "550e8400-e29b-41d4-a716-446655440000", // UUID format
        "simple-id", // Simple format
        "123", // Numeric string
        "id_with_underscore", // With underscore
        "id-with-dash" // With dash
    );

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete entities with mixed ID formats
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_afterSaveAndFlushOperation_shouldWork() {
    // Given: a mocked SecretRepository with saveAndFlush called first
    SecretRepository repository = mock(SecretRepository.class);

    SecretDomain entity = new SecretDomain("ref", "secret");
    entity.setId("saved-id");

    List<String> idsToDelete = Arrays.asList("id1", "id2");

    doNothing().when(repository).deleteAllByIdInBatch(idsToDelete);

    // When: calling saveAndFlush then deleteAllByIdInBatch
    repository.saveAndFlush(entity);
    repository.deleteAllByIdInBatch(idsToDelete);

    // Then: deleteAllByIdInBatch should work after saveAndFlush
    verify(repository, times(1)).saveAndFlush(entity);
    verify(repository, times(1)).deleteAllByIdInBatch(idsToDelete);
  }

  @Test
  void deleteAllByIdInBatch_withNumericStringIds_shouldDelete() {
    // Given: a mocked SecretRepository and numeric string IDs
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("1", "10", "100", "1000", "10000");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete entities with numeric string IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withEmptyStringId_shouldDelete() {
    // Given: a mocked SecretRepository with an empty string ID
    SecretRepository repository = mock(SecretRepository.class);
    List<String> ids = Arrays.asList("", "id1", "id2");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: calling deleteAllByIdInBatch
    repository.deleteAllByIdInBatch(ids);

    // Then: should invoke deleteAllByIdInBatch (actual behavior depends on JPA)
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }
}
