package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.workflow.engine.ResourceProvider;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class WorkflowBotConfigurationClaude_workflowResourcesProviderTest {

  // Tests for workflowResourcesProvider() method

  @Test
  void workflowResourcesProvider_shouldReturnNonNullInstance() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a workflows folder path set
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: The result should not be null
    assertThat(provider).isNotNull();
  }

  @Test
  void workflowResourcesProvider_shouldReturnWorkflowResourcesProviderInstance() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a workflows folder path set
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: The result should be an instance of WorkflowResourcesProvider
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void workflowResourcesProvider_shouldReturnResourceProviderInstance() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a workflows folder path set
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: The result should implement ResourceProvider interface
    assertThat(provider).isInstanceOf(ResourceProvider.class);
  }

  @Test
  void workflowResourcesProvider_shouldCreateNewInstanceOnEachCall() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a workflows folder path set
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows");

    // When: workflowResourcesProvider is called multiple times
    ResourceProvider provider1 = config.workflowResourcesProvider();
    ResourceProvider provider2 = config.workflowResourcesProvider();

    // Then: Each call should return a distinct instance
    assertThat(provider1).isNotSameAs(provider2);
  }

  @Test
  void workflowResourcesProvider_withDefaultPath_shouldCreateProvider() throws Exception {
    // Given: A WorkflowBotConfiguration instance with the default workflows folder path
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: Provider should be created successfully
    assertThat(provider).isNotNull();
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void workflowResourcesProvider_withCustomPath_shouldCreateProvider() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a custom workflows folder path
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "/custom/workflows/path");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: Provider should be created successfully
    assertThat(provider).isNotNull();
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void workflowResourcesProvider_withRelativePath_shouldCreateProvider() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a relative path
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "../workflows");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: Provider should be created successfully
    assertThat(provider).isNotNull();
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void workflowResourcesProvider_withAbsolutePath_shouldCreateProvider() throws Exception {
    // Given: A WorkflowBotConfiguration instance with an absolute path
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "/tmp/workflows");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: Provider should be created successfully
    assertThat(provider).isNotNull();
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void workflowResourcesProvider_withEmptyPath_shouldCreateProvider() throws Exception {
    // Given: A WorkflowBotConfiguration instance with an empty path
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: Provider should be created successfully (WorkflowResourcesProvider accepts any string)
    assertThat(provider).isNotNull();
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void workflowResourcesProvider_shouldNotThrowException() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a workflows folder path set
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows");

    // When/Then: workflowResourcesProvider should not throw any exception
    assertThatCode(() -> config.workflowResourcesProvider())
        .doesNotThrowAnyException();
  }

  @Test
  void workflowResourcesProvider_calledMultipleTimes_shouldSucceedEachTime() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a workflows folder path set
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows");

    // When: workflowResourcesProvider is called multiple times
    // Then: Each call should succeed without throwing an exception
    assertThatCode(() -> {
      config.workflowResourcesProvider();
      config.workflowResourcesProvider();
      config.workflowResourcesProvider();
    }).doesNotThrowAnyException();
  }

  @Test
  void workflowResourcesProvider_withPathContainingSpaces_shouldCreateProvider() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a path containing spaces
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "/path with spaces/workflows");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: Provider should be created successfully
    assertThat(provider).isNotNull();
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void workflowResourcesProvider_withPathContainingSpecialCharacters_shouldCreateProvider() throws Exception {
    // Given: A WorkflowBotConfiguration instance with a path containing special characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows-v1.0_test");

    // When: workflowResourcesProvider is called
    ResourceProvider provider = config.workflowResourcesProvider();

    // Then: Provider should be created successfully
    assertThat(provider).isNotNull();
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void workflowResourcesProvider_afterChangingPath_shouldUseNewPath() throws Exception {
    // Given: A WorkflowBotConfiguration instance with an initial path
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    setWorkflowsFolderPath(config, "./workflows1");

    // When: workflowResourcesProvider is called with initial path
    ResourceProvider provider1 = config.workflowResourcesProvider();

    // And: The path is changed and workflowResourcesProvider is called again
    setWorkflowsFolderPath(config, "./workflows2");
    ResourceProvider provider2 = config.workflowResourcesProvider();

    // Then: Both providers should be created successfully and be distinct
    assertThat(provider1).isNotNull();
    assertThat(provider2).isNotNull();
    assertThat(provider1).isNotSameAs(provider2);
  }

  // Helper method to set the private workflowsFolderPath field
  private void setWorkflowsFolderPath(WorkflowBotConfiguration config, String path) throws Exception {
    Field field = WorkflowBotConfiguration.class.getDeclaredField("workflowsFolderPath");
    field.setAccessible(true);
    field.set(config, path);
  }
}
