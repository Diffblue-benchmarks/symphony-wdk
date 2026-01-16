package com.symphony.bdk.workflow.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowFolderWatcherClaude_constructorTest {

  @Mock
  private WorkflowDeployer workflowDeployer;

  @Mock
  private WorkflowBotConfiguration workflowBotConfiguration;

  // Tests for WorkflowFolderWatcher(WorkflowDeployer, WorkflowBotConfiguration) constructor

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // Given: Mock configuration with a valid workflows folder path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("./workflows");

    // When: Creating a new instance of WorkflowFolderWatcher
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: The instance should not be null
    assertThat(watcher).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // Given: Mock configuration with a valid workflows folder path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("./workflows");

    // When: Creating a new instance of WorkflowFolderWatcher
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: The instance should be of type WorkflowFolderWatcher
    assertThat(watcher).isInstanceOf(WorkflowFolderWatcher.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // Given: Mock configuration with a valid workflows folder path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("./workflows");

    // When: Creating two instances of WorkflowFolderWatcher
    WorkflowFolderWatcher watcher1 = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);
    WorkflowFolderWatcher watcher2 = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: Each call should create a distinct instance
    assertThat(watcher1).isNotSameAs(watcher2);
  }

  @Test
  void constructor_shouldCreateInstanceWithNoException() {
    // Given: Mock configuration with a valid workflows folder path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("./workflows");

    // When/Then: Creating a new instance should not throw any exception
    assertThatCode(() -> new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldCallGetWorkflowsFolderPath() {
    // Given: Mock configuration with a valid workflows folder path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("./workflows");

    // When: Creating a new instance of WorkflowFolderWatcher
    new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: Should call getWorkflowsFolderPath to initialize the workflows folder
    verify(workflowBotConfiguration).getWorkflowsFolderPath();
  }

  @Test
  void constructor_shouldHandleAbsolutePath() {
    // Given: Mock configuration with an absolute path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("/opt/workflows");

    // When: Creating a new instance of WorkflowFolderWatcher
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: The instance should be created successfully
    assertThat(watcher).isNotNull();
  }

  @Test
  void constructor_shouldHandleRelativePath() {
    // Given: Mock configuration with a relative path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("./workflows");

    // When: Creating a new instance of WorkflowFolderWatcher
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: The instance should be created successfully
    assertThat(watcher).isNotNull();
  }

  @Test
  void constructor_shouldHandlePathWithMultipleSegments() {
    // Given: Mock configuration with a path containing multiple segments
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("./config/workflows/main");

    // When: Creating a new instance of WorkflowFolderWatcher
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: The instance should be created successfully
    assertThat(watcher).isNotNull();
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // Given: Mock configuration with a valid workflows folder path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("./workflows");

    // When: Creating multiple instances of WorkflowFolderWatcher
    WorkflowFolderWatcher watcher1 = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);
    WorkflowFolderWatcher watcher2 = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);
    WorkflowFolderWatcher watcher3 = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: All instances should be non-null and distinct
    assertThat(watcher1).isNotNull();
    assertThat(watcher2).isNotNull();
    assertThat(watcher3).isNotNull();
    assertThat(watcher1).isNotSameAs(watcher2);
    assertThat(watcher2).isNotSameAs(watcher3);
    assertThat(watcher1).isNotSameAs(watcher3);
  }

  @Test
  void constructor_shouldHandleEmptyPath() {
    // Given: Mock configuration with an empty path
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn("");

    // When: Creating a new instance of WorkflowFolderWatcher
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);

    // Then: The instance should be created successfully (Path.get("") is valid)
    assertThat(watcher).isNotNull();
  }
}
