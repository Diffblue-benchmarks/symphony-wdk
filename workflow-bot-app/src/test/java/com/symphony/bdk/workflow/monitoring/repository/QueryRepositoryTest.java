package com.symphony.bdk.workflow.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class QueryRepositoryTest {

  @Test
  void findById_shouldReturnEmptyOptionalByDefault() {
    // Arrange
    QueryRepository<String, Long> repository = new QueryRepository<>() {};

    // Act
    Optional<String> result = repository.findById(1L);

    // Assert
    assertThat(result).isEmpty();
  }

  @Test
  void findAll_shouldReturnEmptyListByDefault() {
    // Arrange
    QueryRepository<String, Long> repository = new QueryRepository<>() {};

    // Act
    List<String> result = repository.findAll();

    // Assert
    assertThat(result).isEmpty();
  }
}
