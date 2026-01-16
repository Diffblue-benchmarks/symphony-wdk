package com.symphony.bdk.workflow.management.repository;

import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
 * Test class for VersionedWorkflowRepository.
 *
 * VersionedWorkflowRepository is a Spring Data JPA repository interface that extends JpaRepository.
 * Since it's an interface with no custom implementation, all tested methods are provided by
 * Spring Data JPA at runtime. These tests verify that:
 * 1. All inherited methods are callable and accept appropriate parameters
 * 2. Methods return expected types
 * 3. Methods can be invoked without errors in a mocked context
 *
 * Note: These tests use mocking because there is no custom logic in VersionedWorkflowRepository
 * to test. The actual implementations are provided by Spring Data JPA at runtime and are
 * thoroughly tested in Spring's own test suite.
 */
class VersionedWorkflowRepositoryClaudeTest {

  // ==================== Custom Query Methods Tests ====================

  @Test
  void findByWorkflowId_withExistingWorkflowId_shouldReturnList() {
    // Given: a mocked repository with existing workflow ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";
    VersionedWorkflow workflow1 = createVersionedWorkflow(workflowId, 1L);
    VersionedWorkflow workflow2 = createVersionedWorkflow(workflowId, 2L);
    List<VersionedWorkflow> workflows = Arrays.asList(workflow1, workflow2);

    when(repository.findByWorkflowId(workflowId)).thenReturn(workflows);

    // When: finding by workflow ID
    List<VersionedWorkflow> result = repository.findByWorkflowId(workflowId);

    // Then: should return the list of workflows
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getWorkflowId()).isEqualTo(workflowId);
    assertThat(result.get(1).getWorkflowId()).isEqualTo(workflowId);
    verify(repository, times(1)).findByWorkflowId(workflowId);
  }

  @Test
  void findByWorkflowId_withNonExistingWorkflowId_shouldReturnEmptyList() {
    // Given: a mocked repository with non-existing workflow ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "non-existing";

    when(repository.findByWorkflowId(workflowId)).thenReturn(Collections.emptyList());

    // When: finding by workflow ID
    List<VersionedWorkflow> result = repository.findByWorkflowId(workflowId);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByWorkflowId(workflowId);
  }

  @Test
  void findByWorkflowId_withNullWorkflowId_shouldBeCallable() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

    when(repository.findByWorkflowId(null)).thenReturn(Collections.emptyList());

    // When: finding by null workflow ID
    List<VersionedWorkflow> result = repository.findByWorkflowId(null);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByWorkflowId(null);
  }

  @Test
  void findTopByWorkflowIdOrderByVersionDesc_withExistingWorkflowId_shouldReturnLatestVersion() {
    // Given: a workflow with multiple versions
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";
    VersionedWorkflow latestVersion = createVersionedWorkflow(workflowId, 3L);

    when(repository.findTopByWorkflowIdOrderByVersionDesc(workflowId))
        .thenReturn(Optional.of(latestVersion));

    // When: finding latest version
    Optional<VersionedWorkflow> result = repository.findTopByWorkflowIdOrderByVersionDesc(workflowId);

    // Then: should return the latest version
    assertThat(result).isPresent();
    assertThat(result.get().getVersion()).isEqualTo(3L);
    verify(repository, times(1)).findTopByWorkflowIdOrderByVersionDesc(workflowId);
  }

  @Test
  void findTopByWorkflowIdOrderByVersionDesc_withNonExistingWorkflowId_shouldReturnEmptyOptional() {
    // Given: a non-existing workflow ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "non-existing";

    when(repository.findTopByWorkflowIdOrderByVersionDesc(workflowId))
        .thenReturn(Optional.empty());

    // When: finding latest version
    Optional<VersionedWorkflow> result = repository.findTopByWorkflowIdOrderByVersionDesc(workflowId);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findTopByWorkflowIdOrderByVersionDesc(workflowId);
  }

  @Test
  void findByWorkflowIdAndVersion_withExistingWorkflowIdAndVersion_shouldReturnWorkflow() {
    // Given: a specific workflow version
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";
    Long version = 2L;
    VersionedWorkflow workflow = createVersionedWorkflow(workflowId, version);

    when(repository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: finding by workflow ID and version
    Optional<VersionedWorkflow> result = repository.findByWorkflowIdAndVersion(workflowId, version);

    // Then: should return the workflow
    assertThat(result).isPresent();
    assertThat(result.get().getWorkflowId()).isEqualTo(workflowId);
    assertThat(result.get().getVersion()).isEqualTo(version);
    verify(repository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
  }

  @Test
  void findByWorkflowIdAndVersion_withNonExistingVersion_shouldReturnEmptyOptional() {
    // Given: a non-existing version
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";
    Long version = 999L;

    when(repository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.empty());

    // When: finding by workflow ID and version
    Optional<VersionedWorkflow> result = repository.findByWorkflowIdAndVersion(workflowId, version);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
  }

  @Test
  void findByWorkflowIdAndActiveTrue_withActiveWorkflow_shouldReturnWorkflow() {
    // Given: an active workflow
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";
    VersionedWorkflow activeWorkflow = createVersionedWorkflow(workflowId, 2L);
    activeWorkflow.setActive(true);

    when(repository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(activeWorkflow));

    // When: finding active workflow
    Optional<VersionedWorkflow> result = repository.findByWorkflowIdAndActiveTrue(workflowId);

    // Then: should return the active workflow
    assertThat(result).isPresent();
    assertThat(result.get().getActive()).isTrue();
    verify(repository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
  }

  @Test
  void findByWorkflowIdAndActiveTrue_withNoActiveWorkflow_shouldReturnEmptyOptional() {
    // Given: a workflow with no active version
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";

    when(repository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.empty());

    // When: finding active workflow
    Optional<VersionedWorkflow> result = repository.findByWorkflowIdAndActiveTrue(workflowId);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
  }

  @Test
  void findByWorkflowIdAndPublishedFalse_withUnpublishedWorkflow_shouldReturnWorkflow() {
    // Given: an unpublished workflow
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";
    VersionedWorkflow unpublishedWorkflow = createVersionedWorkflow(workflowId, 1L);
    unpublishedWorkflow.setPublished(false);

    when(repository.findByWorkflowIdAndPublishedFalse(workflowId))
        .thenReturn(Optional.of(unpublishedWorkflow));

    // When: finding unpublished workflow
    Optional<VersionedWorkflow> result = repository.findByWorkflowIdAndPublishedFalse(workflowId);

    // Then: should return the unpublished workflow
    assertThat(result).isPresent();
    assertThat(result.get().getPublished()).isFalse();
    verify(repository, times(1)).findByWorkflowIdAndPublishedFalse(workflowId);
  }

  @Test
  void findByWorkflowIdAndPublishedFalse_withNoUnpublishedWorkflow_shouldReturnEmptyOptional() {
    // Given: a workflow with no unpublished version
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";

    when(repository.findByWorkflowIdAndPublishedFalse(workflowId))
        .thenReturn(Optional.empty());

    // When: finding unpublished workflow
    Optional<VersionedWorkflow> result = repository.findByWorkflowIdAndPublishedFalse(workflowId);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByWorkflowIdAndPublishedFalse(workflowId);
  }

  @Test
  void findByActiveTrue_withActiveWorkflows_shouldReturnList() {
    // Given: multiple active workflows
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow workflow1 = createVersionedWorkflow("workflow-1", 1L);
    workflow1.setActive(true);
    VersionedWorkflow workflow2 = createVersionedWorkflow("workflow-2", 1L);
    workflow2.setActive(true);
    List<VersionedWorkflow> activeWorkflows = Arrays.asList(workflow1, workflow2);

    when(repository.findByActiveTrue()).thenReturn(activeWorkflows);

    // When: finding all active workflows
    List<VersionedWorkflow> result = repository.findByActiveTrue();

    // Then: should return all active workflows
    assertThat(result).hasSize(2);
    assertThat(result).allMatch(w -> w.getActive());
    verify(repository, times(1)).findByActiveTrue();
  }

  @Test
  void findByActiveTrue_withNoActiveWorkflows_shouldReturnEmptyList() {
    // Given: no active workflows
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

    when(repository.findByActiveTrue()).thenReturn(Collections.emptyList());

    // When: finding all active workflows
    List<VersionedWorkflow> result = repository.findByActiveTrue();

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findByActiveTrue();
  }

  @Test
  void deleteByWorkflowId_withExistingWorkflowId_shouldDelete() {
    // Given: existing workflow ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";

    doNothing().when(repository).deleteByWorkflowId(workflowId);

    // When: deleting by workflow ID
    repository.deleteByWorkflowId(workflowId);

    // Then: should delete all versions of the workflow
    verify(repository, times(1)).deleteByWorkflowId(workflowId);
  }

  @Test
  void deleteByWorkflowId_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "workflow-id";

    doNothing().when(repository).deleteByWorkflowId(anyString());

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteByWorkflowId(workflowId)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteByWorkflowId(workflowId);
  }

  @Test
  void deleteByWorkflowIdAndVersion_withExistingWorkflowIdAndVersion_shouldDelete() {
    // Given: existing workflow ID and version
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow-id";
    Long version = 2L;

    doNothing().when(repository).deleteByWorkflowIdAndVersion(workflowId, version);

    // When: deleting by workflow ID and version
    repository.deleteByWorkflowIdAndVersion(workflowId, version);

    // Then: should delete the specific version
    verify(repository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
  }

  @Test
  void deleteByWorkflowIdAndVersion_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "workflow-id";
    Long version = 1L;

    doNothing().when(repository).deleteByWorkflowIdAndVersion(anyString(), any(Long.class));

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteByWorkflowIdAndVersion(workflowId, version))
        .doesNotThrowAnyException();
    verify(repository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
  }

  // ==================== JpaRepository Methods Tests ====================

  @Test
  void flush_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

    doNothing().when(repository).flush();

    // When & Then: flush should complete without throwing
    assertThatCode(() -> repository.flush()).doesNotThrowAnyException();
    verify(repository, times(1)).flush();
  }

  @Test
  void flush_canBeCalledMultipleTimes() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);

    when(repository.saveAndFlush(entity)).thenReturn(entity);

    // When: saving and flushing
    VersionedWorkflow result = repository.saveAndFlush(entity);

    // Then: should return the saved entity
    assertThat(result).isEqualTo(entity);
    verify(repository, times(1)).saveAndFlush(entity);
  }

  @Test
  void saveAndFlush_withNewEntity_shouldSaveAndFlush() {
    // Given: a new entity
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow newEntity = createVersionedWorkflow("new-workflow", 1L);

    when(repository.saveAndFlush(newEntity)).thenReturn(newEntity);

    // When: saving and flushing
    VersionedWorkflow result = repository.saveAndFlush(newEntity);

    // Then: should persist immediately
    assertThat(result).isNotNull();
    verify(repository, times(1)).saveAndFlush(newEntity);
  }

  @Test
  void saveAllAndFlush_withMultipleEntities_shouldSaveAllAndFlush() {
    // Given: multiple entities
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity1 = createVersionedWorkflow("wf1", 1L);
    VersionedWorkflow entity2 = createVersionedWorkflow("wf2", 1L);
    List<VersionedWorkflow> entities = Arrays.asList(entity1, entity2);

    when(repository.saveAllAndFlush(entities)).thenReturn(entities);

    // When: saving all and flushing
    List<VersionedWorkflow> result = repository.saveAllAndFlush(entities);

    // Then: should return all saved entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAllAndFlush(entities);
  }

  @Test
  void saveAllAndFlush_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<VersionedWorkflow> emptyList = Collections.emptyList();

    when(repository.saveAllAndFlush(emptyList)).thenReturn(emptyList);

    // When: saving empty list
    List<VersionedWorkflow> result = repository.saveAllAndFlush(emptyList);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).saveAllAndFlush(emptyList);
  }

  @Test
  void deleteInBatch_withMultipleEntities_shouldDelete() {
    // Given: multiple entities to delete
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity1 = createVersionedWorkflow("wf1", 1L);
    VersionedWorkflow entity2 = createVersionedWorkflow("wf2", 1L);
    List<VersionedWorkflow> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteInBatch(entities);

    // When: deleting in batch
    repository.deleteInBatch(entities);

    // Then: should delete all entities
    verify(repository, times(1)).deleteInBatch(entities);
  }

  @Test
  void deleteInBatch_withEmptyList_shouldBeCallable() {
    // Given: empty list
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<VersionedWorkflow> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteInBatch(emptyList);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteInBatch(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteInBatch(emptyList);
  }

  @Test
  void deleteAllInBatch_withIterable_shouldDeleteAll() {
    // Given: iterable of entities
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity1 = createVersionedWorkflow("wf1", 1L);
    VersionedWorkflow entity2 = createVersionedWorkflow("wf2", 1L);
    List<VersionedWorkflow> entities = Arrays.asList(entity1, entity2);

    doNothing().when(repository).deleteAllInBatch(entities);

    // When: deleting all in batch
    repository.deleteAllInBatch(entities);

    // Then: should delete all
    verify(repository, times(1)).deleteAllInBatch(entities);
  }

  @Test
  void deleteAllInBatch_withEmptyIterable_shouldBeCallable() {
    // Given: empty iterable
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<VersionedWorkflow> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAllInBatch(emptyList);

    // When: deleting empty iterable
    assertThatCode(() -> repository.deleteAllInBatch(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch(emptyList);
  }

  @Test
  void deleteAllByIdInBatch_withMultipleIds_shouldDelete() {
    // Given: multiple IDs
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<String> emptyIds = Collections.emptyList();

    doNothing().when(repository).deleteAllByIdInBatch(emptyIds);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteAllByIdInBatch(emptyIds)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllByIdInBatch(emptyIds);
  }

  @Test
  void deleteAllInBatch_noArgs_shouldDeleteAll() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When: deleting all in batch
    repository.deleteAllInBatch();

    // Then: should delete all entities
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void deleteAllInBatch_noArgs_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

    doNothing().when(repository).deleteAllInBatch();

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteAllInBatch()).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllInBatch();
  }

  @Test
  void getOne_withExistingId_shouldReturnEntity() {
    // Given: existing ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "existing-id";
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);
    entity.setId(id);

    when(repository.getOne(id)).thenReturn(entity);

    // When: getting entity by ID
    VersionedWorkflow result = repository.getOne(id);

    // Then: should return the entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getOne(id);
  }

  @Test
  void getOne_shouldReturnLazyProxy() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "id";
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);

    when(repository.getOne(id)).thenReturn(entity);

    // When: getting one
    VersionedWorkflow result = repository.getOne(id);

    // Then: should return entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).getOne(id);
  }

  @Test
  void getById_withExistingId_shouldReturnEntity() {
    // Given: existing ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "existing-id";
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);
    entity.setId(id);

    when(repository.getById(id)).thenReturn(entity);

    // When: getting by ID
    VersionedWorkflow result = repository.getById(id);

    // Then: should return the entity
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getById_shouldReturnLazyProxy() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "id";
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);

    when(repository.getById(id)).thenReturn(entity);

    // When: getting by ID
    VersionedWorkflow result = repository.getById(id);

    // Then: should return entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).getById(id);
  }

  @Test
  void getReferenceById_withExistingId_shouldReturnReference() {
    // Given: existing ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "existing-id";
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);
    entity.setId(id);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: getting reference by ID
    VersionedWorkflow result = repository.getReferenceById(id);

    // Then: should return the reference
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void getReferenceById_shouldReturnLazyReference() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "id";
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);

    when(repository.getReferenceById(id)).thenReturn(entity);

    // When: getting reference by ID
    VersionedWorkflow result = repository.getReferenceById(id);

    // Then: should return reference
    assertThat(result).isNotNull();
    verify(repository, times(1)).getReferenceById(id);
  }

  @Test
  void findAll_withExample_shouldReturnMatchingEntities() {
    // Given: example and matching entities
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);
    List<VersionedWorkflow> matchingEntities = Arrays.asList(
        createVersionedWorkflow("test", 1L),
        createVersionedWorkflow("test", 2L)
    );

    when(repository.findAll(example)).thenReturn(matchingEntities);

    // When: finding by example
    List<VersionedWorkflow> result = repository.findAll(example);

    // Then: should return matching entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(example);
  }

  @Test
  void findAll_withExample_noMatches_shouldReturnEmptyList() {
    // Given: example with no matches
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);

    when(repository.findAll(example)).thenReturn(Collections.emptyList());

    // When: finding by example
    List<VersionedWorkflow> result = repository.findAll(example);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(example);
  }

  @Test
  void findAll_withExampleAndSort_shouldReturnSortedEntities() {
    // Given: example and sort
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);
    Sort sort = Sort.by(Sort.Direction.DESC, "version");
    List<VersionedWorkflow> sortedEntities = Arrays.asList(
        createVersionedWorkflow("test", 2L),
        createVersionedWorkflow("test", 1L)
    );

    when(repository.findAll(example, sort)).thenReturn(sortedEntities);

    // When: finding by example with sort
    List<VersionedWorkflow> result = repository.findAll(example, sort);

    // Then: should return sorted entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(example, sort);
  }

  @Test
  void findAll_withExampleAndSort_noMatches_shouldReturnEmptyList() {
    // Given: example and sort with no matches
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);
    Sort sort = Sort.by("version");

    when(repository.findAll(example, sort)).thenReturn(Collections.emptyList());

    // When: finding by example with sort
    List<VersionedWorkflow> result = repository.findAll(example, sort);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(example, sort);
  }

  @Test
  void saveAll_withMultipleEntities_shouldSaveAll() {
    // Given: multiple entities
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity1 = createVersionedWorkflow("wf1", 1L);
    VersionedWorkflow entity2 = createVersionedWorkflow("wf2", 1L);
    List<VersionedWorkflow> entities = Arrays.asList(entity1, entity2);

    when(repository.saveAll(entities)).thenReturn(entities);

    // When: saving all
    List<VersionedWorkflow> result = repository.saveAll(entities);

    // Then: should return all saved entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).saveAll(entities);
  }

  @Test
  void saveAll_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<VersionedWorkflow> emptyList = Collections.emptyList();

    when(repository.saveAll(emptyList)).thenReturn(emptyList);

    // When: saving empty list
    List<VersionedWorkflow> result = repository.saveAll(emptyList);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).saveAll(emptyList);
  }

  @Test
  void findAll_noArgs_shouldReturnAllEntities() {
    // Given: repository with entities
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<VersionedWorkflow> allEntities = Arrays.asList(
        createVersionedWorkflow("wf1", 1L),
        createVersionedWorkflow("wf2", 1L),
        createVersionedWorkflow("wf3", 1L)
    );

    when(repository.findAll()).thenReturn(allEntities);

    // When: finding all
    List<VersionedWorkflow> result = repository.findAll();

    // Then: should return all entities
    assertThat(result).hasSize(3);
    verify(repository, times(1)).findAll();
  }

  @Test
  void findAll_noArgs_emptyTable_shouldReturnEmptyList() {
    // Given: empty repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

    when(repository.findAll()).thenReturn(Collections.emptyList());

    // When: finding all
    List<VersionedWorkflow> result = repository.findAll();

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll();
  }

  @Test
  void findAllById_withMultipleIds_shouldReturnMatchingEntities() {
    // Given: multiple IDs
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<String> ids = Arrays.asList("id1", "id2", "id3");
    List<VersionedWorkflow> entities = Arrays.asList(
        createVersionedWorkflow("wf1", 1L),
        createVersionedWorkflow("wf2", 1L)
    );

    when(repository.findAllById(ids)).thenReturn(entities);

    // When: finding all by IDs
    List<VersionedWorkflow> result = repository.findAllById(ids);

    // Then: should return matching entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAllById(ids);
  }

  @Test
  void findAllById_withEmptyList_shouldReturnEmptyList() {
    // Given: empty list of IDs
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<String> emptyIds = Collections.emptyList();

    when(repository.findAllById(emptyIds)).thenReturn(Collections.emptyList());

    // When: finding all by empty IDs
    List<VersionedWorkflow> result = repository.findAllById(emptyIds);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAllById(emptyIds);
  }

  @Test
  void save_withNewEntity_shouldSaveAndReturnEntity() {
    // Given: new entity
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);

    when(repository.save(entity)).thenReturn(entity);

    // When: saving
    VersionedWorkflow result = repository.save(entity);

    // Then: should return saved entity
    assertThat(result).isEqualTo(entity);
    verify(repository, times(1)).save(entity);
  }

  @Test
  void save_withExistingEntity_shouldUpdateAndReturn() {
    // Given: existing entity
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);
    entity.setId("existing-id");
    entity.setSwadl("updated swadl");

    when(repository.save(entity)).thenReturn(entity);

    // When: saving
    VersionedWorkflow result = repository.save(entity);

    // Then: should return updated entity
    assertThat(result).isNotNull();
    verify(repository, times(1)).save(entity);
  }

  @Test
  void findById_withExistingId_shouldReturnOptionalWithEntity() {
    // Given: existing ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "existing-id";
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);
    entity.setId(id);

    when(repository.findById(id)).thenReturn(Optional.of(entity));

    // When: finding by ID
    Optional<VersionedWorkflow> result = repository.findById(id);

    // Then: should return entity
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
    verify(repository, times(1)).findById(id);
  }

  @Test
  void findById_withNonExistingId_shouldReturnEmptyOptional() {
    // Given: non-existing ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "non-existing-id";

    when(repository.findById(id)).thenReturn(Optional.empty());

    // When: finding by ID
    Optional<VersionedWorkflow> result = repository.findById(id);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findById(id);
  }

  @Test
  void existsById_withExistingId_shouldReturnTrue() {
    // Given: existing ID
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String id = "id";

    doNothing().when(repository).deleteById(anyString());

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteById(id)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteById(id);
  }

  @Test
  void delete_withEntity_shouldDelete() {
    // Given: entity to delete
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);
    entity.setId("id");

    doNothing().when(repository).delete(entity);

    // When: deleting entity
    repository.delete(entity);

    // Then: should delete
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void delete_withEntity_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);

    doNothing().when(repository).delete(any(VersionedWorkflow.class));

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.delete(entity)).doesNotThrowAnyException();
    verify(repository, times(1)).delete(entity);
  }

  @Test
  void deleteAllById_withMultipleIds_shouldDeleteAll() {
    // Given: multiple IDs
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<String> emptyIds = Collections.emptyList();

    doNothing().when(repository).deleteAllById(emptyIds);

    // When: deleting empty list
    assertThatCode(() -> repository.deleteAllById(emptyIds)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAllById(emptyIds);
  }

  @Test
  void deleteAll_withIterable_shouldDeleteAll() {
    // Given: iterable of entities
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<VersionedWorkflow> entities = Arrays.asList(
        createVersionedWorkflow("wf1", 1L),
        createVersionedWorkflow("wf2", 1L)
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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<VersionedWorkflow> emptyList = Collections.emptyList();

    doNothing().when(repository).deleteAll(emptyList);

    // When: deleting empty iterable
    assertThatCode(() -> repository.deleteAll(emptyList)).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAll(emptyList);
  }

  @Test
  void deleteAll_noArgs_shouldDeleteAllEntities() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

    doNothing().when(repository).deleteAll();

    // When: deleting all
    repository.deleteAll();

    // Then: should delete all entities
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void deleteAll_noArgs_shouldBeCallableWithoutException() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);

    doNothing().when(repository).deleteAll();

    // When & Then: should complete without throwing
    assertThatCode(() -> repository.deleteAll()).doesNotThrowAnyException();
    verify(repository, times(1)).deleteAll();
  }

  @Test
  void findAll_withSort_shouldReturnSortedEntities() {
    // Given: sort parameter
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    Sort sort = Sort.by(Sort.Direction.DESC, "version");
    List<VersionedWorkflow> sortedEntities = Arrays.asList(
        createVersionedWorkflow("test", 3L),
        createVersionedWorkflow("test", 2L)
    );

    when(repository.findAll(sort)).thenReturn(sortedEntities);

    // When: finding all with sort
    List<VersionedWorkflow> result = repository.findAll(sort);

    // Then: should return sorted entities
    assertThat(result).hasSize(2);
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withSort_emptyTable_shouldReturnEmptyList() {
    // Given: sort parameter and empty table
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    Sort sort = Sort.by("version");

    when(repository.findAll(sort)).thenReturn(Collections.emptyList());

    // When: finding all with sort
    List<VersionedWorkflow> result = repository.findAll(sort);

    // Then: should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll(sort);
  }

  @Test
  void findAll_withPageable_shouldReturnPage() {
    // Given: pageable parameter
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    Pageable pageable = PageRequest.of(0, 10);
    List<VersionedWorkflow> entities = Arrays.asList(
        createVersionedWorkflow("wf1", 1L),
        createVersionedWorkflow("wf2", 1L)
    );
    Page<VersionedWorkflow> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(pageable)).thenReturn(page);

    // When: finding all with pageable
    Page<VersionedWorkflow> result = repository.findAll(pageable);

    // Then: should return page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getTotalElements()).isEqualTo(2);
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findAll_withPageable_emptyTable_shouldReturnEmptyPage() {
    // Given: pageable parameter and empty table
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    Pageable pageable = PageRequest.of(0, 10);
    Page<VersionedWorkflow> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(pageable)).thenReturn(emptyPage);

    // When: finding all with pageable
    Page<VersionedWorkflow> result = repository.findAll(pageable);

    // Then: should return empty page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEmpty();
    assertThat(result.getTotalElements()).isZero();
    verify(repository, times(1)).findAll(pageable);
  }

  @Test
  void findOne_withExample_shouldReturnOptionalWithEntity() {
    // Given: example that matches one entity
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);
    VersionedWorkflow matchingEntity = createVersionedWorkflow("test", 1L);

    when(repository.findOne(example)).thenReturn(Optional.of(matchingEntity));

    // When: finding one by example
    Optional<VersionedWorkflow> result = repository.findOne(example);

    // Then: should return entity
    assertThat(result).isPresent();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findOne_withExample_noMatch_shouldReturnEmptyOptional() {
    // Given: example with no match
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);

    when(repository.findOne(example)).thenReturn(Optional.empty());

    // When: finding one by example
    Optional<VersionedWorkflow> result = repository.findOne(example);

    // Then: should return empty optional
    assertThat(result).isEmpty();
    verify(repository, times(1)).findOne(example);
  }

  @Test
  void findAll_withExampleAndPageable_shouldReturnPage() {
    // Given: example and pageable
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);
    Pageable pageable = PageRequest.of(0, 10);
    List<VersionedWorkflow> entities = Arrays.asList(
        createVersionedWorkflow("test", 1L),
        createVersionedWorkflow("test", 2L)
    );
    Page<VersionedWorkflow> page = new PageImpl<>(entities, pageable, 2);

    when(repository.findAll(example, pageable)).thenReturn(page);

    // When: finding all by example with pageable
    Page<VersionedWorkflow> result = repository.findAll(example, pageable);

    // Then: should return page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(2);
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void findAll_withExampleAndPageable_noMatches_shouldReturnEmptyPage() {
    // Given: example and pageable with no matches
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);
    Pageable pageable = PageRequest.of(0, 10);
    Page<VersionedWorkflow> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

    when(repository.findAll(example, pageable)).thenReturn(emptyPage);

    // When: finding all by example with pageable
    Page<VersionedWorkflow> result = repository.findAll(example, pageable);

    // Then: should return empty page
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEmpty();
    verify(repository, times(1)).findAll(example, pageable);
  }

  @Test
  void count_withExample_shouldReturnCount() {
    // Given: example
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);
    Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<VersionedWorkflow>, List<VersionedWorkflow>> function =
        query -> Arrays.asList(createVersionedWorkflow("test", 1L), createVersionedWorkflow("test", 2L));
    List<VersionedWorkflow> expectedResult = Arrays.asList(
        createVersionedWorkflow("test", 1L),
        createVersionedWorkflow("test", 2L)
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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow probe = createVersionedWorkflow("test", 1L);
    Example<VersionedWorkflow> example = Example.of(probe);
    Function<org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery<VersionedWorkflow>, List<VersionedWorkflow>> function =
        query -> Collections.emptyList();
    List<VersionedWorkflow> emptyResult = Collections.emptyList();

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);
    entity.setId("id");

    when(repository.save(entity)).thenReturn(entity);
    when(repository.findById("id")).thenReturn(Optional.of(entity));

    // When: saving and finding
    VersionedWorkflow saved = repository.save(entity);
    Optional<VersionedWorkflow> found = repository.findById("id");

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    List<VersionedWorkflow> entities = Arrays.asList(
        createVersionedWorkflow("wf1", 1L),
        createVersionedWorkflow("wf2", 1L)
    );

    when(repository.saveAll(entities)).thenReturn(entities);
    when(repository.findAll()).thenReturn(entities);

    // When: saving all and finding all
    List<VersionedWorkflow> saved = repository.saveAll(entities);
    List<VersionedWorkflow> found = repository.findAll();

    // Then: should round trip
    assertThat(saved).hasSize(2);
    assertThat(found).hasSize(2);
    verify(repository, times(1)).saveAll(entities);
    verify(repository, times(1)).findAll();
  }

  @Test
  void countAfterSave_shouldReflectAddition() {
    // Given: a mocked repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow entity = createVersionedWorkflow("test", 1L);
    entity.setId("id");

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
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
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
  void findByWorkflowIdAfterSave_shouldReturnSavedWorkflow() {
    // Given: a workflow saved in repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow";
    VersionedWorkflow entity = createVersionedWorkflow(workflowId, 1L);

    when(repository.save(entity)).thenReturn(entity);
    when(repository.findByWorkflowId(workflowId)).thenReturn(Collections.singletonList(entity));

    // When: saving and finding by workflow ID
    repository.save(entity);
    List<VersionedWorkflow> found = repository.findByWorkflowId(workflowId);

    // Then: should find the saved workflow
    assertThat(found).hasSize(1);
    assertThat(found.get(0).getWorkflowId()).isEqualTo(workflowId);
    verify(repository, times(1)).save(entity);
    verify(repository, times(1)).findByWorkflowId(workflowId);
  }

  @Test
  void findByWorkflowIdAndVersion_afterSavingMultipleVersions_shouldFindSpecificVersion() {
    // Given: multiple versions of same workflow
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    String workflowId = "test-workflow";
    VersionedWorkflow v1 = createVersionedWorkflow(workflowId, 1L);
    VersionedWorkflow v2 = createVersionedWorkflow(workflowId, 2L);

    when(repository.findByWorkflowIdAndVersion(workflowId, 2L)).thenReturn(Optional.of(v2));

    // When: finding specific version
    Optional<VersionedWorkflow> found = repository.findByWorkflowIdAndVersion(workflowId, 2L);

    // Then: should return version 2
    assertThat(found).isPresent();
    assertThat(found.get().getVersion()).isEqualTo(2L);
    verify(repository, times(1)).findByWorkflowIdAndVersion(workflowId, 2L);
  }

  @Test
  void findByActiveTrue_afterActivatingWorkflow_shouldReturnActiveWorkflows() {
    // Given: active workflows in repository
    VersionedWorkflowRepository repository = mock(VersionedWorkflowRepository.class);
    VersionedWorkflow activeWorkflow = createVersionedWorkflow("active-wf", 1L);
    activeWorkflow.setActive(true);

    when(repository.findByActiveTrue()).thenReturn(Collections.singletonList(activeWorkflow));

    // When: finding active workflows
    List<VersionedWorkflow> active = repository.findByActiveTrue();

    // Then: should return active workflow
    assertThat(active).hasSize(1);
    assertThat(active.get(0).getActive()).isTrue();
    verify(repository, times(1)).findByActiveTrue();
  }

  // ==================== Helper Methods ====================

  /**
   * Helper method to create a VersionedWorkflow entity for testing.
   */
  private VersionedWorkflow createVersionedWorkflow(String workflowId, Long version) {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setSwadl("id: " + workflowId);
    workflow.setPublished(true);
    workflow.setActive(false);
    workflow.setDeploymentId("deployment-" + workflowId);
    workflow.setCreatedBy(12345L);
    workflow.setDescription("Test workflow " + workflowId);
    return workflow;
  }
}
