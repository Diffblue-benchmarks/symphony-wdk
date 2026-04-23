package com.symphony.bdk.workflow.monitoring.repository;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class QueryRepositoryTest {

  private final QueryRepository<String, String> repository = new QueryRepository<String, String>() {};

  @Test
  void shouldReturnEmptyOptionalWhenFindById() {
    Optional<String> result = repository.findById("id");
    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenFindAll() {
    List<String> result = repository.findAll();
    assertThat(result).isEmpty();
  }
}
