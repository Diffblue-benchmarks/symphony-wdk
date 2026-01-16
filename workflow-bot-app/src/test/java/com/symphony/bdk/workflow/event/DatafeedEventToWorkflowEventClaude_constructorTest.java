package com.symphony.bdk.workflow.event;

import com.symphony.bdk.workflow.engine.WorkflowEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Test class for DatafeedEventToWorkflowEvent constructor.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_constructorTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: creating a new DatafeedEventToWorkflowEvent instance
    DatafeedEventToWorkflowEvent instance = new DatafeedEventToWorkflowEvent(workflowEngine);

    // Then: the instance should not be null
    assertThat(instance).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: creating a new DatafeedEventToWorkflowEvent instance
    DatafeedEventToWorkflowEvent instance = new DatafeedEventToWorkflowEvent(workflowEngine);

    // Then: the instance should be of type DatafeedEventToWorkflowEvent
    assertThat(instance).isInstanceOf(DatafeedEventToWorkflowEvent.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: creating two instances of DatafeedEventToWorkflowEvent
    DatafeedEventToWorkflowEvent instance1 = new DatafeedEventToWorkflowEvent(workflowEngine);
    DatafeedEventToWorkflowEvent instance2 = new DatafeedEventToWorkflowEvent(workflowEngine);

    // Then: each call should create a distinct instance
    assertThat(instance1).isNotSameAs(instance2);
  }

  @Test
  void constructor_shouldCreateInstanceWithNoException() {
    // When/Then: creating a new instance should not throw any exception
    assertThatCode(() -> new DatafeedEventToWorkflowEvent(workflowEngine))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldAllowMultipleInstantiations() {
    // When: creating multiple instances of DatafeedEventToWorkflowEvent
    DatafeedEventToWorkflowEvent instance1 = new DatafeedEventToWorkflowEvent(workflowEngine);
    DatafeedEventToWorkflowEvent instance2 = new DatafeedEventToWorkflowEvent(workflowEngine);
    DatafeedEventToWorkflowEvent instance3 = new DatafeedEventToWorkflowEvent(workflowEngine);

    // Then: all instances should be non-null and distinct
    assertThat(instance1).isNotNull();
    assertThat(instance2).isNotNull();
    assertThat(instance3).isNotNull();
    assertThat(instance1).isNotSameAs(instance2);
    assertThat(instance2).isNotSameAs(instance3);
    assertThat(instance1).isNotSameAs(instance3);
  }

  @Test
  void constructor_withNullWorkflowEngine_shouldCreateInstance() {
    // Given: a null WorkflowEngine
    WorkflowEngine nullEngine = null;

    // When: creating a new DatafeedEventToWorkflowEvent instance with null
    DatafeedEventToWorkflowEvent instance = new DatafeedEventToWorkflowEvent(nullEngine);

    // Then: the instance should be created successfully (constructor doesn't validate null)
    assertThat(instance).isNotNull();
  }
}
