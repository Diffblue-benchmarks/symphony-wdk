package com.symphony.bdk.workflow.monitoring.repository;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for QueryRepository interface.
 * Tests the default method implementations for findById and findAll.
 */
class QueryRepositoryClaudeTest {

  /**
   * Test that the default findById implementation returns an empty Optional.
   */
  @Test
  void testFindById_DefaultImplementation_ReturnsEmptyOptional() {
    // Create a simple implementation that uses the default methods
    QueryRepository<String, Long> repository = new QueryRepository<String, Long>() {
      // Using default implementations
    };

    // When: calling findById with any id
    Optional<String> result = repository.findById(1L);

    // Then: should return empty Optional
    assertThat(result).isEmpty();
  }

  /**
   * Test that the default findById implementation returns an empty Optional with null id.
   */
  @Test
  void testFindById_WithNullId_ReturnsEmptyOptional() {
    // Create a simple implementation that uses the default methods
    QueryRepository<String, Long> repository = new QueryRepository<String, Long>() {
      // Using default implementations
    };

    // When: calling findById with null id
    Optional<String> result = repository.findById(null);

    // Then: should return empty Optional
    assertThat(result).isEmpty();
  }

  /**
   * Test that the default findById implementation returns an empty Optional with different id types.
   */
  @Test
  void testFindById_WithStringId_ReturnsEmptyOptional() {
    // Create a simple implementation using String as the ID type
    QueryRepository<Integer, String> repository = new QueryRepository<Integer, String>() {
      // Using default implementations
    };

    // When: calling findById with a String id
    Optional<Integer> result = repository.findById("test-id");

    // Then: should return empty Optional
    assertThat(result).isEmpty();
  }

  /**
   * Test that the default findAll implementation returns an empty list.
   */
  @Test
  void testFindAll_DefaultImplementation_ReturnsEmptyList() {
    // Create a simple implementation that uses the default methods
    QueryRepository<String, Long> repository = new QueryRepository<String, Long>() {
      // Using default implementations
    };

    // When: calling findAll
    List<String> result = repository.findAll();

    // Then: should return empty list
    assertThat(result).isEmpty();
    assertThat(result).isEqualTo(Collections.emptyList());
  }

  /**
   * Test that the default findAll implementation returns an immutable empty list.
   */
  @Test
  void testFindAll_DefaultImplementation_ReturnsImmutableList() {
    // Create a simple implementation that uses the default methods
    QueryRepository<String, Long> repository = new QueryRepository<String, Long>() {
      // Using default implementations
    };

    // When: calling findAll
    List<String> result = repository.findAll();

    // Then: should return an immutable empty list (Collections.emptyList())
    assertThat(result).isEmpty();
    assertThat(result).isSameAs(Collections.emptyList());
  }

  /**
   * Test that implementations can override findById to provide custom behavior.
   */
  @Test
  void testFindById_CanBeOverridden() {
    // Create an implementation that overrides findById
    QueryRepository<String, Long> repository = new QueryRepository<String, Long>() {
      @Override
      public Optional<String> findById(Long id) {
        if (id != null && id == 1L) {
          return Optional.of("found");
        }
        return Optional.empty();
      }
    };

    // When: calling findById with id=1
    Optional<String> result = repository.findById(1L);

    // Then: should return the overridden value
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo("found");
  }

  /**
   * Test that implementations can override findAll to provide custom behavior.
   */
  @Test
  void testFindAll_CanBeOverridden() {
    // Create an implementation that overrides findAll
    QueryRepository<String, Long> repository = new QueryRepository<String, Long>() {
      @Override
      public List<String> findAll() {
        return List.of("item1", "item2", "item3");
      }
    };

    // When: calling findAll
    List<String> result = repository.findAll();

    // Then: should return the overridden list
    assertThat(result).hasSize(3);
    assertThat(result).containsExactly("item1", "item2", "item3");
  }

  /**
   * Test multiple calls to findById return consistent empty results.
   */
  @Test
  void testFindById_MultipleCalls_ReturnConsistentEmptyOptionals() {
    // Create a simple implementation that uses the default methods
    QueryRepository<String, Long> repository = new QueryRepository<String, Long>() {
      // Using default implementations
    };

    // When: calling findById multiple times
    Optional<String> result1 = repository.findById(1L);
    Optional<String> result2 = repository.findById(2L);
    Optional<String> result3 = repository.findById(100L);

    // Then: all should return empty Optionals
    assertThat(result1).isEmpty();
    assertThat(result2).isEmpty();
    assertThat(result3).isEmpty();
  }

  /**
   * Test multiple calls to findAll return consistent empty results.
   */
  @Test
  void testFindAll_MultipleCalls_ReturnConsistentEmptyLists() {
    // Create a simple implementation that uses the default methods
    QueryRepository<String, Long> repository = new QueryRepository<String, Long>() {
      // Using default implementations
    };

    // When: calling findAll multiple times
    List<String> result1 = repository.findAll();
    List<String> result2 = repository.findAll();
    List<String> result3 = repository.findAll();

    // Then: all should return empty lists
    assertThat(result1).isEmpty();
    assertThat(result2).isEmpty();
    assertThat(result3).isEmpty();

    // And they should all be the same instance (Collections.emptyList())
    assertThat(result1).isSameAs(result2);
    assertThat(result2).isSameAs(result3);
  }

  /**
   * Test that repository works with complex generic types.
   */
  @Test
  void testQueryRepository_WithComplexGenericTypes() {
    // Create a simple implementation with complex types
    QueryRepository<List<String>, Map<String, Integer>> repository =
        new QueryRepository<List<String>, Map<String, Integer>>() {
      // Using default implementations
    };

    // When: calling methods with complex types
    Optional<List<String>> resultById = repository.findById(Collections.singletonMap("key", 1));
    List<List<String>> resultAll = repository.findAll();

    // Then: should still return empty results
    assertThat(resultById).isEmpty();
    assertThat(resultAll).isEmpty();
  }
}
