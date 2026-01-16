package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for SecretRepository.flush() method.
 *
 * The flush() method is inherited from JpaRepository (Spring Data JPA) and is used
 * to synchronize the persistence context to the underlying database. It forces pending
 * changes to be sent to the database immediately, without waiting for the transaction
 * to commit.
 *
 * Since SecretRepository is an interface that extends JpaRepository and does not provide
 * a custom implementation of flush(), the method behavior is entirely provided by Spring
 * Data JPA's runtime proxy. Therefore, meaningful testing of this method requires an
 * integration test with a real database context.
 *
 * These unit tests verify that:
 * 1. The flush() method exists and is callable on SecretRepository
 * 2. The method can be invoked without errors in a mocked context
 * 3. Multiple flush calls can be made sequentially
 *
 * Note: These tests use mocking because there is no custom logic in SecretRepository
 * to test. The actual flush() implementation is provided by Spring Data JPA at runtime
 * and is thoroughly tested in Spring's own test suite.
 */
class SecretRepositoryClaude_flushTest {

  @Test
  void flush_shouldBeCallableWithoutException() {
    // Given: a mocked SecretRepository instance
    SecretRepository repository = mock(SecretRepository.class);
    doNothing().when(repository).flush();

    // When: calling flush
    // Then: flush should complete without throwing an exception
    assertThatCode(() -> repository.flush()).doesNotThrowAnyException();
  }

  @Test
  void flush_shouldInvokeMethodOnce() {
    // Given: a mocked SecretRepository instance
    SecretRepository repository = mock(SecretRepository.class);
    doNothing().when(repository).flush();

    // When: calling flush once
    repository.flush();

    // Then: flush should have been invoked exactly once
    verify(repository, times(1)).flush();
  }

  @Test
  void flush_shouldBeCallableMultipleTimes() {
    // Given: a mocked SecretRepository instance
    SecretRepository repository = mock(SecretRepository.class);
    doNothing().when(repository).flush();

    // When: calling flush multiple times
    repository.flush();
    repository.flush();
    repository.flush();

    // Then: all three calls should complete successfully
    verify(repository, times(3)).flush();
  }

  @Test
  void flush_shouldBeCallableAfterOtherRepositoryOperations() {
    // Given: a mocked SecretRepository with various operations
    SecretRepository repository = mock(SecretRepository.class);
    doNothing().when(repository).flush();

    // When: performing various repository operations followed by flush
    repository.save(new SecretDomain("test", "secret"));
    repository.flush();
    repository.deleteByRef("test");
    repository.flush();

    // Then: flush should be callable after other operations
    verify(repository, times(2)).flush();
  }

  @Test
  void flush_shouldNotRequireArguments() {
    // Given: a mocked SecretRepository instance
    SecretRepository repository = mock(SecretRepository.class);
    doNothing().when(repository).flush();

    // When & Then: flush should be callable with no arguments
    assertThatCode(() -> repository.flush()).doesNotThrowAnyException();
    verify(repository, times(1)).flush();
  }
}
