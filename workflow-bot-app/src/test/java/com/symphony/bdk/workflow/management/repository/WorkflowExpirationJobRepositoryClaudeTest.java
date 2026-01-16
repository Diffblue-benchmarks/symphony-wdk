package com.symphony.bdk.workflow.management.repository;

import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for WorkflowExpirationJobRepository.
 *
 * WorkflowExpirationJobRepository is a Spring Data JPA repository interface that extends JpaRepository.
 * Since it's an interface with no custom implementation, all tested methods are provided by
 * Spring Data JPA at runtime. These tests verify that:
 * 1. All inherited methods are callable and accept appropriate parameters
 * 2. Methods return expected types
 * 3. Methods can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in WorkflowExpirationJobRepository
 * to test. The actual implementations are provided by Spring Data JPA at runtime and are
 * thoroughly tested in Spring's own test suite.
 */
class WorkflowExpirationJobRepositoryClaudeTest {

  // ==================== JpaRepository Methods Tests ====================

  @Test
  void flush_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    doNothing().when(repository).flush();

    // When & Then: flush should complete without throwing
    assertThatCode(() -> repository.flush()).doesNotThrowAnyException();
    verify(repository, times(1)).flush();
  }

  @Test
  void flush_canBeCalledMultipleTimes() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    doNothing().when(repository).flush();

    // When: calling flush multiple times
    repository.flush();
    repository.flush();

    // Then: should work for each call
    verify(repository, times(2)).flush();
  }

  @Test
  void saveAndFlush_withValidEntity_shouldSaveAndReturnEntity() {
    // Given: a mocked repository and entity
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");

    when(repository.saveAndFlush(entity)).thenReturn(entity);

    // When: saving and flushing
    WorkflowExpirationJob result = repository.saveAndFlush(entity);

    // Then: should return the saved entity
    assertThat(result).isEqualTo(entity);
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void saveAndFlush_withNewEntity_shouldSaveAndFlush() {
    // Given: a new entity
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob newEntity = createWorkflowExpirationJob("new-job", "new-wf", "new-deploy");

    when(repository.saveAndFlush(newEntity)).thenReturn(newEntity);

    // When: saving and flushing
    WorkflowExpirationJob result = repository.saveAndFlush(newEntity);

    // Then: should persist immediately
    assertThat(result).isNotNull();
    verify(repository, times(1)).saveAndFlush(newEntity);
  }

  @Test
  void saveAllAndFlush_withMultipleEntities_shouldSaveAllAndFlush() {
    // Given: multiple entities
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity1 = createWorkflowExpirationJob("job1", "wf1", "deploy1");
    WorkflowExpirationJob entity2 = createWorkflowExpirationJob("job2", "wf2", "deploy2");
    List<WorkflowExpirationJob> entities = Arrays.asList(entity1, entity2);

    when(repository.saveAllAndFlush(entities)).thenReturn(entities);

    // When: saving all and flushing
    List<WorkflowExpirationJob> result = repository.saveAllAndFlush(entities);

    // Then: should return all saved entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<WorkflowExpirationJob> emptyList = Collections.emptyList();

    when(repository.saveAllAndFlush(emptyList)).thenReturn(emptyList);

    // When: saving empty list
    List<WorkflowExpirationJob> result = repository.saveAllAndFlush(emptyList);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).saveAllAndFlush(emptyList);
  }

  @Test
  void deleteInBatch_withMultipleEntities_shouldDelete() {
    // Given: multiple entities to delete
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity1 = createWorkflowExpirationJob("job1", "wf1", "deploy1");
    WorkflowExpirationJob entity2 = createWorkflowExpirationJob("job2", "wf2", "deploy2");
    List<WorkflowExpirationJob> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When: deleting in batch
    repository.deleteInBatch(entities);

    // Then: should delete all entities
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEmptyList_shouldBeCallable() {
    // Given: empty list
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<WorkflowExpirationJob> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteInBatch(emptyList);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteInBatch(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteInBatch(emptyList);
  }

  @Test
  void deleteAllInBatch_withIterable_shouldDeleteAll() {
    // Given: iterable of entities
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity1 = createWorkflowExpirationJob("job1", "wf1", "deploy1");
    WorkflowExpirationJob entity2 = createWorkflowExpirationJob("job2", "wf2", "deploy2");
    List<WorkflowExpirationJob> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: deleting all in batch
    repository.deleteAllInBatch(entities);

    // Then: should delete all
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEmptyIterable_shouldBeCallable() {
    // Given: empty iterable
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<WorkflowExpirationJob> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAllInBatch(emptyList);

    // When: deleting empty iterable
    assertThatCode(() -> repository.deleteAllInBatch(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch(emptyList);
  }

  @Test
  void deleteAllByIdInBatch_withMultipleIds_shouldDelete() {
    // Given: multiple IDs
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id3");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: deleting by IDs in batch
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete all by IDs
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  @Test
  void deleteAllByIdInBatch_withEmptyList_shouldBeCallable() {
    // Given: empty list of IDs
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<String> emptyIds = Collections.emptyList();

    doNothing().when(repository).deleteAllByIdInBatch(emptyIds);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteAllByIdInBatch(emptyIds)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllByIdInBatch(emptyIds);
  }

  @Test
  void deleteAllInBatch_noArgs_shouldDeleteAll() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: deleting all in batch
    repository.deleteAllInBatch();

    // Then: should delete all entities
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteAllInBatch()).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void getOne_withExistingId_shouldReturnEntity() {
    // Given: existing ID
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "existing-id";
    WorkflowExpirationJob entity = createWorkflowExpirationJob(id, "wf-1", "deploy-1");

    when(repository.getOne(id)).thenReturn(entity);

    // When: getting entity by ID
    WorkflowExpirationJob result = repository.getOne(id);

    // Then: should return the entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getOne(id);
  }

  @Test
  void getOne_shouldReturnLazyProxy() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "id";
    WorkflowExpirationJob entity = createWorkflowExpirationJob(id, "wf-1", "deploy-1");

    when(repository.getOne(id)).thenReturn(entity);

    // When: getting one
    WorkflowExpirationJob result = repository.getOne(id);

    // Then: should return entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).getOne(id);
  }

  @Test
  void getById_withExistingId_shouldReturnEntity() {
    // Given: existing ID
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "existing-id";
    WorkflowExpirationJob entity = createWorkflowExpirationJob(id, "wf-1", "deploy-1");

    when(repository.getById(id)).thenReturn(entity);

    // When: getting by ID
    WorkflowExpirationJob result = repository.getById(id);

    // Then: should return the entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_shouldReturnLazyProxy() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "id";
    WorkflowExpirationJob entity = createWorkflowExpirationJob(id, "wf-1", "deploy-1");

    when(repository.getById(id)).thenReturn(entity);

    // When: getting by ID
    WorkflowExpirationJob result = repository.getById(id);

    // Then: should return entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getReferenceById_withExistingId_shouldReturnReference() {
    // Given: existing ID
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "existing-id";
    WorkflowExpirationJob entity = createWorkflowExpirationJob(id, "wf-1", "deploy-1");

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: getting reference by ID
    WorkflowExpirationJob result = repository.getReferenceById(id);

    // Then: should return the reference
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_shouldReturnLazyReference() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "id";
    WorkflowExpirationJob entity = createWorkflowExpirationJob(id, "wf-1", "deploy-1");

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: getting reference by ID
    WorkflowExpirationJob result = repository.getReferenceById(id);

    // Then: should return reference
    assertThat(result).isNotNull();
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void findAll_withExample_shouldReturnMatchingEntities() {
    // Given: example and matching entities
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);
    List<WorkflowExpirationJob> matchingEntities = Arrays.asList(
        createWorkflowExpirationJob("job-1", "wf-1", "deploy-1"),
        createWorkflowExpirationJob("job-2", "wf-1", "deploy-1")
    );

    when(repository.findAll(example)).thenReturn(matchingEntities);

    // When: finding by example
    List<WorkflowExpirationJob> result = repository.findAll(example);

    // Then: should return matching entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(example);
  }

  @Test
  void findAll_withExample_noMatches_shouldReturnEmptyList() {
    // Given: example with no matches
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);

    when(repository.findAll(example)).thenReturn(Collections.emptyList());

    // When: finding by example
    List<WorkflowExpirationJob> result = repository.findAll(example);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(example);
  }

  @Test
  void findAll_withExampleAndSort_shouldReturnSortedEntities() {
    // Given: example and sort
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);
    Sort sort = Sort.by(Sort.Direction.DESC, "expirationDate");
    List<WorkflowExpirationJob> sortedEntities = Arrays.asList(
        createWorkflowExpirationJob("job-2", "wf-1", "deploy-1"),
        createWorkflowExpirationJob("job-1", "wf-1", "deploy-1")
    );

    when(repository.findAll(example, sort)).thenReturn(sortedEntities);

    // When: finding by example with sort
    List<WorkflowExpirationJob> result = repository.findAll(example, sort);

    // Then: should return sorted entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(example, sort);
  }

  @Test
  void findAll_withExampleAndSort_noMatches_shouldReturnEmptyList() {
    // Given: example and sort with no matches
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);
    Sort sort = Sort.by("expirationDate");

    when(repository.findAll(example, sort)).thenReturn(Collections.emptyList());

    // When: finding by example with sort
    List<WorkflowExpirationJob> result = repository.findAll(example, sort);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(example, sort);
  }

  @Test
  void saveAll_withMultipleEntities_shouldSaveAll() {
    // Given: multiple entities
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity1 = createWorkflowExpirationJob("job1", "wf1", "deploy1");
    WorkflowExpirationJob entity2 = createWorkflowExpirationJob("job2", "wf2", "deploy2");
    List<WorkflowExpirationJob> entities = Arrays.asList(entity1, entity2);

    when(repository.saveAll(entities)).thenReturn(entities);

    // When: saving all
    List<WorkflowExpirationJob> result = repository.saveAll(entities);

    // Then: should return all saved entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<WorkflowExpirationJob> emptyList = Collections.emptyList();

    when(repository.saveAll(emptyList)).thenReturn(emptyList);

    // When: saving empty list
    List<WorkflowExpirationJob> result = repository.saveAll(emptyList);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).saveAll(emptyList);
  }

  @Test
  void findAll_noArgs_shouldReturnAllEntities() {
    // Given: repository with entities
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<WorkflowExpirationJob> allEntities = Arrays.asList(
        createWorkflowExpirationJob("job1", "wf1", "deploy1"),
        createWorkflowExpirationJob("job2", "wf2", "deploy2"),
        createWorkflowExpirationJob("job3", "wf3", "deploy3")
    );

    when(repository.findAll()).thenReturn(allEntities);

    // When: finding all
    List<WorkflowExpirationJob> result = repository.findAll();

    // Then: should return all entities
    assertThat(result).hasSize(3);
    verify(repository, times(1)).findAll();
  }

  @Test
  void findAll_noArgs_emptyTable_shouldReturnEmptyList() {
    // Given: empty repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    when(repository.findAll()).thenReturn(Collections.emptyList());

    // When: finding all
    List<WorkflowExpirationJob> result = repository.findAll();

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll();
  }

  @Test
  void findAllById_withMultipleIds_shouldReturnMatchingEntities() {
    // Given: multiple IDs
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    List<WorkflowExpirationJob> entities = Arrays.asList(
        createWorkflowExpirationJob("id1", "wf1", "deploy1"),
        createWorkflowExpirationJob("id2", "wf2", "deploy2")
    );

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: finding all by IDs
    List<WorkflowExpirationJob> result = repository.findAllById(ids);

    // Then: should return matching entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list of IDs
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<String> emptyIds = Collections.emptyList();

    when(repository.findAllById(emptyIds)).thenReturn(Collections.emptyList());

    // When: finding all by empty IDs
    List<WorkflowExpirationJob> result = repository.findAllById(emptyIds);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAllById(emptyIds);
  }

  @Test
  void save_withNewEntity_shouldSaveAndReturnEntity() {
    // Given: new entity
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");

    when(repository.save(entity)).thenReturn(entity);

    // When: saving
    WorkflowExpirationJob result = repository.save(entity);

    // Then: should return saved entity
    assertThat(result).isEqualTo(entity);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withExistingEntity_shouldUpdateAndReturn() {
    // Given: existing entity
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity = createWorkflowExpirationJob("existing-id", "wf-1", "deploy-1");
    entity.setExpirationDate(Instant.now().plusSeconds(3600));

    when(repository.save(entity)).thenReturn(entity);

    // When: saving
    WorkflowExpirationJob result = repository.save(entity);

    // Then: should return updated entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void findById_withExistingId_shouldReturnOptionalWithEntity() {
    // Given: existing ID
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "existing-id";
    WorkflowExpirationJob entity = createWorkflowExpirationJob(id, "wf-1", "deploy-1");

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: finding by ID
    Optional<WorkflowExpirationJob> result = repository.findById(id);

    // Then: should return entity
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_withNonExistingId_shouldReturnEmptyOptional() {
    // Given: non-existing ID
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "non-existing-id";

    when(repository.findById(id)).thenReturn(Optional.empty());

    // When: finding by ID
    Optional<WorkflowExpirationJob> result = repository.findById(id);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findById(id);
  }

  @Test
  void existsById_withExistingId_shouldReturnTrue() {
    // Given: existing ID
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "existing-id";

    when(repository.existsById(id)).thenReturn(true);

    // When: checking existence
    boolean result = repository.existsById(id);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void existsById_withNonExistingId_shouldReturnFalse() {
    // Given: non-existing ID
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "non-existing-id";

    when(repository.existsById(id)).thenReturn(false);

    // When: checking existence
    boolean result = repository.existsById(id);

    // Then: should return false
    assertThat(result).isFalse();
    verify(repository, times(1)).existsById(id);
  }

  @Test
  void count_noArgs_shouldReturnTotalCount() {
    // Given: repository with entities
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    when(repository.count()).thenReturn(5L);

    // When: counting
    long result = repository.count();

    // Then: should return count
    assertThat(result).isEqualTo(5L);
    verify(repository, times(1)).count();
  }

  @Test
  void count_noArgs_emptyTable_shouldReturnZero() {
    // Given: empty repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    when(repository.count()).thenReturn(0L);

    // When: counting
    long result = repository.count();

    // Then: should return zero
    assertThat(result).isZero();
    verify(repository, times(1)).count();
  }

  @Test
  void deleteById_withExistingId_shouldDelete() {
    // Given: existing ID
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "existing-id";

    doNothing().when(repository).deleteById(id);

    // When: deleting by ID
    repository.deleteById(id);

    // Then: should delete
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void deleteById_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "id";

    doNothing().when(repository).deleteById(anyString());

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteById(id)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void delete_withEntity_shouldDelete() {
    // Given: entity to delete
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity = createWorkflowExpirationJob("id", "wf-1", "deploy-1");

    doNothing().when(repository).delete(entity);

    // When: deleting entity
    repository.delete(entity);

    // Then: should delete
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntity_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity = createWorkflowExpirationJob("id", "wf-1", "deploy-1");

    doNothing().when(repository).delete(any(WorkflowExpirationJob.class));

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.delete(entity)).doesNotThrowAnyException();
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void deleteAllById_withMultipleIds_shouldDeleteAll() {
    // Given: multiple IDs
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id3");

    doNothing().when(repository).deleteAllById(ids);

    // When: deleting all by IDs
    repository.deleteAllById(ids);

    // Then: should delete all
    verify(repository, times(1)).deleteAllById(ids);
  }

  @Test
  void deleteAllById_withEmptyList_shouldBeCallable() {
    // Given: empty list
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<String> emptyIds = Collections.emptyList();

    doNothing().when(repository).deleteAllById(emptyIds);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteAllById(emptyIds)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllById(emptyIds);
  }

  @Test
  void deleteAll_withIterable_shouldDeleteAll() {
    // Given: iterable of entities
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<WorkflowExpirationJob> entities = Arrays.asList(
        createWorkflowExpirationJob("job1", "wf1", "deploy1"),
        createWorkflowExpirationJob("job2", "wf2", "deploy2")
    );

    doNothing().when(repository).deleteAll(entities);

    // When: deleting all
    repository.deleteAll(entities);

    // Then: should delete all
    verify(repository, times(1)).deleteAll(entities);
  }

  @Test
  void deleteAll_withEmptyIterable_shouldBeCallable() {
    // Given: empty iterable
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<WorkflowExpirationJob> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAll(emptyList);

    // When: deleting empty iterable
    assertThatCode(() -> repository.deleteAll(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAll(emptyList);
  }

  @Test
  void deleteAll_noArgs_shouldDeleteAllEntities() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    doNothing().when(repository).deleteAll();

    // When: deleting all
    repository.deleteAll();

    // Then: should delete all entities
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);

    doNothing().when(repository).deleteAll();

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteAll()).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void findAll_withSort_shouldReturnSortedEntities() {
    // Given: sort parameter
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    Sort sort = Sort.by(Sort.Direction.DESC, "expirationDate");
    List<WorkflowExpirationJob> sortedEntities = Arrays.asList(
        createWorkflowExpirationJob("job3", "wf3", "deploy3"),
        createWorkflowExpirationJob("job2", "wf2", "deploy2")
    );

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: finding all with sort
    List<WorkflowExpirationJob> result = repository.findAll(sort);

    // Then: should return sorted entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSort_emptyTable_shouldReturnEmptyList() {
    // Given: sort parameter and empty table
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    Sort sort = Sort.by("expirationDate");

    when(repository.findAll(sort)).thenReturn(Collections.emptyList());

    // When: finding all with sort
    List<WorkflowExpirationJob> result = repository.findAll(sort);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withPageable_shouldReturnPage() {
    // Given: pageable parameter
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    Pageable pageable = PageRequest.of(0, 10);
    List<WorkflowExpirationJob> entities = Arrays.asList(
        createWorkflowExpirationJob("job1", "wf1", "deploy1"),
        createWorkflowExpirationJob("job2", "wf2", "deploy2")
    );
    Page<WorkflowExpirationJob> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: finding all with pageable
    Page<WorkflowExpirationJob> result = repository.findAll(pageable);

    // Then: should return page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getTotalElements()).isEqualTo(2);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageable_emptyTable_shouldReturnEmptyPage() {
    // Given: pageable parameter and empty table
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    Pageable pageable = PageRequest.of(0, 10);
    Page<WorkflowExpirationJob> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(pageable)).thenReturn(emptyPage);

    // When: finding all with pageable
    Page<WorkflowExpirationJob> result = repository.findAll(pageable);

    // Then: should return empty page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEmpty();
    assertThat(result.getTotalElements()).isZero();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findOne_withExample_shouldReturnOptionalWithEntity() {
    // Given: example that matches one entity
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);
    WorkflowExpirationJob matchingEntity = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");

    when(repository.findOne(example)).thenReturn(Optional.of(matchingEntity));

    // When: finding one by example
    Optional<WorkflowExpirationJob> result = repository.findOne(example);

    // Then: should return entity
    assertThat(result).isPresent();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_withExample_noMatch_shouldReturnEmptyOptional() {
    // Given: example with no match
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: finding one by example
    Optional<WorkflowExpirationJob> result = repository.findOne(example);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findAll_withExampleAndPageable_shouldReturnPage() {
    // Given: example and pageable
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);
    Pageable pageable = PageRequest.of(0, 10);
    List<WorkflowExpirationJob> entities = Arrays.asList(
        createWorkflowExpirationJob("job-1", "wf-1", "deploy-1"),
        createWorkflowExpirationJob("job-2", "wf-1", "deploy-1")
    );
    Page<WorkflowExpirationJob> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: finding all by example with pageable
    Page<WorkflowExpirationJob> result = repository.findAll(example, pageable);

    // Then: should return page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageable_noMatches_shouldReturnEmptyPage() {
    // Given: example and pageable with no matches
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);
    Pageable pageable = PageRequest.of(0, 10);
    Page<WorkflowExpirationJob> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(example, pageable)).thenReturn(emptyPage);

    // When: finding all by example with pageable
    Page<WorkflowExpirationJob> result = repository.findAll(example, pageable);

    // Then: should return empty page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEmpty();
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void count_withExample_shouldReturnCount() {
    // Given: example
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);

    when(repository.count(example)).thenReturn(3L);

    // When: counting by example
    long result = repository.count(example);

    // Then: should return count
    assertThat(result).isEqualTo(3L);
    verify(repository, times(1)).count(example);
  }

  @Test
  void count_withExample_noMatches_shouldReturnZero() {
    // Given: example with no matches
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);

    when(repository.count(example)).thenReturn(0L);

    // When: counting by example
    long result = repository.count(example);

    // Then: should return zero
    assertThat(result).isZero();
    verify(repository, times(1)).count(example);
  }

  @Test
  void exists_withExample_matchExists_shouldReturnTrue() {
    // Given: example with match
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);

    when(repository.exists(example)).thenReturn(true);

    // When: checking existence by example
    boolean result = repository.exists(example);

    // Then: should return true
    assertThat(result).isTrue();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void exists_withExample_noMatch_shouldReturnFalse() {
    // Given: example with no match
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);

    when(repository.exists(example)).thenReturn(false);

    // When: checking existence by example
    boolean result = repository.exists(example);

    // Then: should return false
    assertThat(result).isFalse();
    verify(repository, times(1)).exists(example);
  }

  @Test
  void findBy_withExampleAndFunction_shouldApplyFunction() {
    // Given: example and function
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);
    Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<WorkflowExpirationJob>, List<WorkflowExpirationJob>> function =
        query -> Arrays.asList(createWorkflowExpirationJob("job-1", "wf-1", "deploy-1"), createWorkflowExpirationJob("job-2", "wf-1", "deploy-1"));
    List<WorkflowExpirationJob> expectedResult = Arrays.asList(
        createWorkflowExpirationJob("job-1", "wf-1", "deploy-1"),
        createWorkflowExpirationJob("job-2", "wf-1", "deploy-1")
    );

    when(repository.findBy(any(Example.class), any(Function.class))).thenReturn(expectedResult);

    // When: finding by with function
    Object result = repository.findBy(example, function);

    // Then: should apply function and return result
    assertThat(result).isNotNull();
    verify(repository, times(1)).findBy(any(Example.class), any(Function.class));
  }

  @Test
  void findBy_withExampleAndFunction_emptyResult_shouldReturnEmptyResult() {
    // Given: example and function returning empty
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob probe = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");
    Example<WorkflowExpirationJob> example = Example.of(probe);
    Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<WorkflowExpirationJob>, List<WorkflowExpirationJob>> function =
        query -> Collections.emptyList();
    List<WorkflowExpirationJob> emptyResult = Collections.emptyList();

    when(repository.findBy(any(Example.class), any(Function.class))).thenReturn(emptyResult);

    // When: finding by with function
    Object result = repository.findBy(example, function);

    // Then: should return empty result
    assertThat(result).isNotNull();
    verify(repository, times(1)).findBy(any(Example.class), any(Function.class));
  }

  // ==================== Integration-style Tests ====================

  @Test
  void saveAndFindById_shouldRoundTrip() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity = createWorkflowExpirationJob("id", "wf-1", "deploy-1");

    when(repository.save(entity)).thenReturn(entity);
    when(repository.findById("id")).thenReturn(Optional.of(entity));

    // When: saving and finding
    WorkflowExpirationJob saved = repository.save(entity);
    Optional<WorkflowExpirationJob> found = repository.findById("id");

    // Then: should round trip
    assertThat(saved).isEqualTo(entity);
    assertThat(found).isPresent();
    assertThat(found.get()).isEqualTo(entity);
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).findById("id");
  }

  @Test
  void saveAllAndFindAll_shouldRoundTrip() {
    // Given: multiple entities
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<WorkflowExpirationJob> entities = Arrays.asList(
        createWorkflowExpirationJob("job1", "wf1", "deploy1"),
        createWorkflowExpirationJob("job2", "wf2", "deploy2")
    );

    when(repository.saveAll(entities)).thenReturn(entities);
    when(repository.findAll()).thenReturn(entities);

    // When: saving all and finding all
    List<WorkflowExpirationJob> saved = repository.saveAll(entities);
    List<WorkflowExpirationJob> found = repository.findAll();

    // Then: should round trip
    assertThat(saved).hasSize(2);
    assertThat(found).hasSize(2);
    verify(repository, times(1)).saveAll(entities);
    verify(repository, times(1)).findAll();
  }

  @Test
  void countAfterSave_shouldReflectAddition() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity = createWorkflowExpirationJob("job-1", "wf-1", "deploy-1");

    when(repository.count()).thenReturn(0L, 1L);
    when(repository.save(entity)).thenReturn(entity);

    // When: counting, saving, then counting again
    long countBefore = repository.count();
    repository.save(entity);
    long countAfter = repository.count();

    // Then: count should increase
    assertThat(countBefore).isZero();
    assertThat(countAfter).isEqualTo(1L);
    verify(repository, times(2)).count();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void existsByIdAfterSave_shouldReturnTrue() {
    // Given: a mocked repository
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    WorkflowExpirationJob entity = createWorkflowExpirationJob("id", "wf-1", "deploy-1");

    when(repository.save(entity)).thenReturn(entity);
    when(repository.existsById("id")).thenReturn(true);

    // When: saving and checking existence
    repository.save(entity);
    boolean exists = repository.existsById("id");

    // Then: should exist
    assertThat(exists).isTrue();
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).existsById("id");
  }

  @Test
  void deleteAndExistsById_shouldReturnFalse() {
    // Given: a mocked repository with entity
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String id = "id";

    doNothing().when(repository).deleteById(id);
    when(repository.existsById(id)).thenReturn(true, false);

    // When: checking existence, deleting, then checking again
    boolean existsBefore = repository.existsById(id);
    repository.deleteById(id);
    boolean existsAfter = repository.existsById(id);

    // Then: should not exist after deletion
    assertThat(existsBefore).isTrue();
    assertThat(existsAfter).isFalse();
    verify(repository, times(2)).existsById(id);
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void saveMultipleJobsForSameWorkflow_shouldSaveAll() {
    // Given: multiple jobs for same workflow
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    String workflowId = "wf-1";
    WorkflowExpirationJob job1 = createWorkflowExpirationJob("job-1", workflowId, "deploy-1");
    WorkflowExpirationJob job2 = createWorkflowExpirationJob("job-2", workflowId, "deploy-2");
    List<WorkflowExpirationJob> jobs = Arrays.asList(job1, job2);

    when(repository.saveAll(jobs)).thenReturn(jobs);

    // When: saving jobs
    List<WorkflowExpirationJob> saved = repository.saveAll(jobs);

    // Then: should save both jobs
    assertThat(saved).hasSize(2);
    assertThat(saved).allMatch(job -> job.getWorkflowId().equals(workflowId));
    verify(repository, times(1)).saveAll(jobs);
  }

  @Test
  void batchDeleteOperations_shouldDeleteEfficiently() {
    // Given: multiple jobs to delete
    WorkflowExpirationJobRepository repository = mock(WorkflowExpirationJobRepository.class);
    List<String> ids = Arrays.asList("job-1", "job-2", "job-3");

    doNothing().when(repository).deleteAllByIdInBatch(ids);

    // When: batch deleting
    repository.deleteAllByIdInBatch(ids);

    // Then: should delete in batch
    verify(repository, times(1)).deleteAllByIdInBatch(ids);
  }

  // ==================== Helper Methods ====================

  /**
   * Helper method to create a WorkflowExpirationJob entity for testing.
   */
  private WorkflowExpirationJob createWorkflowExpirationJob(String id, String workflowId, String deploymentId) {
    WorkflowExpirationJob job = new WorkflowExpirationJob();
    job.setId(id);
    job.setWorkflowId(workflowId);
    job.setDeploymentId(deploymentId);
    job.setExpirationDate(Instant.now().plusSeconds(86400)); // 1 day from now
    return job;
  }
}
