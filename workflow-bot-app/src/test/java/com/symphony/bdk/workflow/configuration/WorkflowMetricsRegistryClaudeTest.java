package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.workflow.engine.WorkflowEngineMetrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkflowMetricsRegistryClaudeTest {

  private MeterRegistry meterRegistry;
  private WorkflowEngineMetrics workflowEngineMetrics;

  @BeforeEach
  void setUp() {
    meterRegistry = new SimpleMeterRegistry();
    workflowEngineMetrics = mock(WorkflowEngineMetrics.class);
  }

  // Tests for constructor <init>(MeterRegistry, WorkflowEngineMetrics)

  @Test
  void constructor_shouldRegisterWorkflowDeployedGauge() {
    // Given: WorkflowEngineMetrics returns a specific count
    when(workflowEngineMetrics.countDeployedWorkflows()).thenReturn(5L);

    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: The gauge should be registered and return the correct value
    Gauge gauge = meterRegistry.find("workflow.deployed").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(5.0);
  }

  @Test
  void constructor_shouldRegisterWorkflowProcessRunningGauge() {
    // Given: WorkflowEngineMetrics returns a specific count
    when(workflowEngineMetrics.countRunningProcesses()).thenReturn(3L);

    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: The gauge should be registered and return the correct value
    Gauge gauge = meterRegistry.find("workflow.process.running").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(3.0);
  }

  @Test
  void constructor_shouldRegisterWorkflowProcessCompletedGauge() {
    // Given: WorkflowEngineMetrics returns a specific count
    when(workflowEngineMetrics.countCompletedProcesses()).thenReturn(10L);

    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: The gauge should be registered and return the correct value
    Gauge gauge = meterRegistry.find("workflow.process.completed").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(10.0);
  }

  @Test
  void constructor_shouldRegisterWorkflowActivityRunningGauge() {
    // Given: WorkflowEngineMetrics returns a specific count
    when(workflowEngineMetrics.countRunningActivities()).thenReturn(7L);

    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: The gauge should be registered and return the correct value
    Gauge gauge = meterRegistry.find("workflow.activity.running").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(7.0);
  }

  @Test
  void constructor_shouldRegisterWorkflowActivityCompletedGauge() {
    // Given: WorkflowEngineMetrics returns a specific count
    when(workflowEngineMetrics.countCompletedActivities()).thenReturn(15L);

    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: The gauge should be registered and return the correct value
    Gauge gauge = meterRegistry.find("workflow.activity.completed").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(15.0);
  }

  @Test
  void constructor_shouldRegisterAllFiveGauges() {
    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: All five gauges should be registered
    assertThat(meterRegistry.find("workflow.deployed").gauge()).isNotNull();
    assertThat(meterRegistry.find("workflow.process.running").gauge()).isNotNull();
    assertThat(meterRegistry.find("workflow.process.completed").gauge()).isNotNull();
    assertThat(meterRegistry.find("workflow.activity.running").gauge()).isNotNull();
    assertThat(meterRegistry.find("workflow.activity.completed").gauge()).isNotNull();
  }

  @Test
  void constructor_shouldCreateDynamicGaugesThatReflectCurrentMetrics() {
    // Given: WorkflowEngineMetrics initially returns 5
    when(workflowEngineMetrics.countDeployedWorkflows()).thenReturn(5L);

    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);
    Gauge gauge = meterRegistry.find("workflow.deployed").gauge();

    // Then: Initial value should be 5
    assertThat(gauge.value()).isEqualTo(5.0);

    // When: Metrics change
    when(workflowEngineMetrics.countDeployedWorkflows()).thenReturn(10L);

    // Then: Gauge should reflect the new value
    assertThat(gauge.value()).isEqualTo(10.0);
  }

  @Test
  void constructor_shouldHandleZeroMetrics() {
    // Given: All metrics return 0
    when(workflowEngineMetrics.countDeployedWorkflows()).thenReturn(0L);
    when(workflowEngineMetrics.countRunningProcesses()).thenReturn(0L);
    when(workflowEngineMetrics.countCompletedProcesses()).thenReturn(0L);
    when(workflowEngineMetrics.countRunningActivities()).thenReturn(0L);
    when(workflowEngineMetrics.countCompletedActivities()).thenReturn(0L);

    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: All gauges should return 0.0
    assertThat(meterRegistry.find("workflow.deployed").gauge().value()).isEqualTo(0.0);
    assertThat(meterRegistry.find("workflow.process.running").gauge().value()).isEqualTo(0.0);
    assertThat(meterRegistry.find("workflow.process.completed").gauge().value()).isEqualTo(0.0);
    assertThat(meterRegistry.find("workflow.activity.running").gauge().value()).isEqualTo(0.0);
    assertThat(meterRegistry.find("workflow.activity.completed").gauge().value()).isEqualTo(0.0);
  }

  @Test
  void constructor_shouldHandleLargeMetricValues() {
    // Given: Metrics return large values
    when(workflowEngineMetrics.countDeployedWorkflows()).thenReturn(1000000L);
    when(workflowEngineMetrics.countRunningProcesses()).thenReturn(999999L);
    when(workflowEngineMetrics.countCompletedProcesses()).thenReturn(5000000L);
    when(workflowEngineMetrics.countRunningActivities()).thenReturn(2500000L);
    when(workflowEngineMetrics.countCompletedActivities()).thenReturn(10000000L);

    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: Gauges should return the correct values
    assertThat(meterRegistry.find("workflow.deployed").gauge().value()).isEqualTo(1000000.0);
    assertThat(meterRegistry.find("workflow.process.running").gauge().value()).isEqualTo(999999.0);
    assertThat(meterRegistry.find("workflow.process.completed").gauge().value()).isEqualTo(5000000.0);
    assertThat(meterRegistry.find("workflow.activity.running").gauge().value()).isEqualTo(2500000.0);
    assertThat(meterRegistry.find("workflow.activity.completed").gauge().value()).isEqualTo(10000000.0);
  }

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: Creating a new instance of WorkflowMetricsRegistry
    WorkflowMetricsRegistry registry = new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: The instance should not be null
    assertThat(registry).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance of WorkflowMetricsRegistry
    WorkflowMetricsRegistry registry = new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: The instance should be of type WorkflowMetricsRegistry
    assertThat(registry).isInstanceOf(WorkflowMetricsRegistry.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating two instances of WorkflowMetricsRegistry
    WorkflowMetricsRegistry registry1 = new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);
    WorkflowMetricsRegistry registry2 = new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: Each call should create a distinct instance
    assertThat(registry1).isNotSameAs(registry2);
  }

  @Test
  void constructor_shouldNotThrowExceptionWithValidParameters() {
    // When/Then: Creating instance should not throw any exception
    org.assertj.core.api.Assertions.assertThatCode(() ->
        new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldWorkWithDifferentMeterRegistryImplementations() {
    // Given: A different MeterRegistry implementation
    MeterRegistry anotherRegistry = new SimpleMeterRegistry();
    when(workflowEngineMetrics.countDeployedWorkflows()).thenReturn(42L);

    // When: Creating WorkflowMetricsRegistry with different registry
    new WorkflowMetricsRegistry(anotherRegistry, workflowEngineMetrics);

    // Then: Gauges should be registered in the new registry
    Gauge gauge = anotherRegistry.find("workflow.deployed").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(42.0);
  }

  @Test
  void constructor_gaugesShouldHaveEmptyTags() {
    // When: Creating WorkflowMetricsRegistry
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Then: All gauges should have empty tags
    Gauge deployedGauge = meterRegistry.find("workflow.deployed").gauge();
    assertThat(deployedGauge.getId().getTags()).isEmpty();

    Gauge processRunningGauge = meterRegistry.find("workflow.process.running").gauge();
    assertThat(processRunningGauge.getId().getTags()).isEmpty();

    Gauge processCompletedGauge = meterRegistry.find("workflow.process.completed").gauge();
    assertThat(processCompletedGauge.getId().getTags()).isEmpty();

    Gauge activityRunningGauge = meterRegistry.find("workflow.activity.running").gauge();
    assertThat(activityRunningGauge.getId().getTags()).isEmpty();

    Gauge activityCompletedGauge = meterRegistry.find("workflow.activity.completed").gauge();
    assertThat(activityCompletedGauge.getId().getTags()).isEmpty();
  }
}
