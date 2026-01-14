package com.symphony.bdk.workflow.expiration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WorkflowExpirationServiceDiffblueTest {
  /**
   * Test {@link WorkflowExpirationService#scheduleWorkflowExpiration(String, Instant)}.
   *
   * <p>Method under test: {@link WorkflowExpirationService#scheduleWorkflowExpiration(String,
   * Instant)}
   */
  @Test
  @DisplayName("Test scheduleWorkflowExpiration(String, Instant)")
  @Tag("MaintainedByDiffblue")
  void testScheduleWorkflowExpiration() {
    // Arrange
    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            versioningRepository,
            mock(WorkflowExpirationPlanner.class));

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowExpirationService.scheduleWorkflowExpiration(
                "42", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    verify(versioningRepository).findByWorkflowId("42");
  }

  /**
   * Test {@link WorkflowExpirationService#scheduleWorkflowExpiration(String, Instant)}.
   *
   * <p>Method under test: {@link WorkflowExpirationService#scheduleWorkflowExpiration(String,
   * Instant)}
   */
  @Test
  @DisplayName("Test scheduleWorkflowExpiration(String, Instant)")
  @Tag("MaintainedByDiffblue")
  void testScheduleWorkflowExpiration2() {
    // Arrange
    WorkflowExpirationJobRepository expirationJobRepository =
        mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Workflow %s does not exist.");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow);

    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(versionedWorkflowList);

    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            expirationJobRepository, versioningRepository, mock(WorkflowExpirationPlanner.class));

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowExpirationService.scheduleWorkflowExpiration(
                "42", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    verify(versioningRepository).findByWorkflowId("42");
    verify(expirationJobRepository).saveAll(isA(Iterable.class));
  }

  /**
   * Test {@link WorkflowExpirationService#scheduleWorkflowExpiration(String, Instant)}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowExpirationService#scheduleWorkflowExpiration(String,
   * Instant)}
   */
  @Test
  @DisplayName(
      "Test scheduleWorkflowExpiration(String, Instant); given VersionedWorkflow (default constructor) Active is 'false'")
  @Tag("MaintainedByDiffblue")
  void testScheduleWorkflowExpiration_givenVersionedWorkflowActiveIsFalse() {
    // Arrange
    WorkflowExpirationJobRepository expirationJobRepository =
        mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any()))
        .thenReturn(new ArrayList<>());

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Workflow %s does not exist.");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(false);
    versionedWorkflow2.setCreatedBy(0L);
    versionedWorkflow2.setDeploymentId("Deployment Id");
    versionedWorkflow2.setDescription("Description");
    versionedWorkflow2.setEtag(0L);
    versionedWorkflow2.setId("Id");
    versionedWorkflow2.setPublished(false);
    versionedWorkflow2.setSwadl(
        "com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow");
    versionedWorkflow2.setVersion(0L);
    versionedWorkflow2.setWorkflowId("Workflow Id");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow2);
    versionedWorkflowList.add(versionedWorkflow);

    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(versionedWorkflowList);

    WorkflowExpirationPlanner workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    doNothing()
        .when(workflowExpirationPlanner)
        .planExpiration(Mockito.<WorkflowExpirationJob>any());

    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            expirationJobRepository, versioningRepository, workflowExpirationPlanner);

    // Act
    workflowExpirationService.scheduleWorkflowExpiration(
        "42", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(workflowExpirationPlanner, atLeast(1))
        .planExpiration(Mockito.<WorkflowExpirationJob>any());
    verify(versioningRepository).findByWorkflowId("42");
    verify(expirationJobRepository).saveAll(isA(Iterable.class));
  }

  /**
   * Test {@link WorkflowExpirationService#scheduleWorkflowExpiration(String, Instant)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowExpirationPlanner#planExpiration(WorkflowExpirationJob)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowExpirationService#scheduleWorkflowExpiration(String,
   * Instant)}
   */
  @Test
  @DisplayName(
      "Test scheduleWorkflowExpiration(String, Instant); then calls planExpiration(WorkflowExpirationJob)")
  @Tag("MaintainedByDiffblue")
  void testScheduleWorkflowExpiration_thenCallsPlanExpiration() {
    // Arrange
    WorkflowExpirationJobRepository expirationJobRepository =
        mock(WorkflowExpirationJobRepository.class);
    when(expirationJobRepository.saveAll(Mockito.<Iterable<WorkflowExpirationJob>>any()))
        .thenReturn(new ArrayList<>());

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Workflow %s does not exist.");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");

    ArrayList<VersionedWorkflow> versionedWorkflowList = new ArrayList<>();
    versionedWorkflowList.add(versionedWorkflow);

    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(versionedWorkflowList);

    WorkflowExpirationPlanner workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    doNothing()
        .when(workflowExpirationPlanner)
        .planExpiration(Mockito.<WorkflowExpirationJob>any());

    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            expirationJobRepository, versioningRepository, workflowExpirationPlanner);

    // Act
    workflowExpirationService.scheduleWorkflowExpiration(
        "42", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(workflowExpirationPlanner).planExpiration(isA(WorkflowExpirationJob.class));
    verify(versioningRepository).findByWorkflowId("42");
    verify(expirationJobRepository).saveAll(isA(Iterable.class));
  }

  /**
   * Test {@link WorkflowExpirationService#scheduleWorkflowExpiration(String, Instant)}.
   *
   * <ul>
   *   <li>Then throw {@link NotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowExpirationService#scheduleWorkflowExpiration(String,
   * Instant)}
   */
  @Test
  @DisplayName("Test scheduleWorkflowExpiration(String, Instant); then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  void testScheduleWorkflowExpiration_thenThrowNotFoundException() {
    // Arrange
    VersionedWorkflowRepository versioningRepository = mock(VersionedWorkflowRepository.class);
    when(versioningRepository.findByWorkflowId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    WorkflowExpirationService workflowExpirationService =
        new WorkflowExpirationService(
            mock(WorkflowExpirationJobRepository.class),
            versioningRepository,
            mock(WorkflowExpirationPlanner.class));

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            workflowExpirationService.scheduleWorkflowExpiration(
                "42", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    verify(versioningRepository).findByWorkflowId("42");
  }
}
