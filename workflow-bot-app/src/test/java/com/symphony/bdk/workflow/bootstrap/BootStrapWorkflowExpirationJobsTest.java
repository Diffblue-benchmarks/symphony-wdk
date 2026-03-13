package com.symphony.bdk.workflow.bootstrap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.expiration.WorkflowExpirationPlanner;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

class BootStrapWorkflowExpirationJobsTest {

  private WorkflowExpirationJobRepository expirationJobRepository;
  private WorkflowExpirationPlanner workflowExpirationPlanner;
  private BootStrapWorkflowExpirationJobs bootStrapWorkflowExpirationJobs;

  @BeforeEach
  void setUp() {
    expirationJobRepository = mock(WorkflowExpirationJobRepository.class);
    workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    bootStrapWorkflowExpirationJobs = new BootStrapWorkflowExpirationJobs(
        expirationJobRepository, workflowExpirationPlanner);
  }

  @Test
  void shouldPlanExpirationForAllJobsWhenSetupWorkflowExpirationJobs() {
    WorkflowExpirationJob job1 = new WorkflowExpirationJob("id1", "workflow1", "deployment1", Instant.now());
    WorkflowExpirationJob job2 = new WorkflowExpirationJob("id2", "workflow2", "deployment2", Instant.now());
    when(expirationJobRepository.findAll()).thenReturn(Arrays.asList(job1, job2));

    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    verify(workflowExpirationPlanner).planExpiration(job1);
    verify(workflowExpirationPlanner).planExpiration(job2);
  }

  @Test
  void shouldHandleEmptyJobListWhenSetupWorkflowExpirationJobs() {
    when(expirationJobRepository.findAll()).thenReturn(Collections.emptyList());

    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    verify(expirationJobRepository).findAll();
  }
}
