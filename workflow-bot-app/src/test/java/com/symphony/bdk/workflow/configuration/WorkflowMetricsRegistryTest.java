package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.workflow.engine.WorkflowEngineMetrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.ToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowMetricsRegistryTest {

  @Mock
  private MeterRegistry meterRegistry;

  @Mock
  private WorkflowEngineMetrics workflowEngineMetrics;

  @Test
  void shouldRegisterAllMetricsWhenConstructorIsCalled() {
    // Arrange & Act
    new WorkflowMetricsRegistry(meterRegistry, workflowEngineMetrics);

    // Assert
    verify(meterRegistry, times(5)).gauge(any(String.class), any(Tags.class), any(ToDoubleFunction.class));
    verify(meterRegistry).gauge(eq("workflow.deployed"), any(Tags.class), any(ToDoubleFunction.class));
    verify(meterRegistry).gauge(eq("workflow.process.running"), any(Tags.class), any(ToDoubleFunction.class));
    verify(meterRegistry).gauge(eq("workflow.process.completed"), any(Tags.class), any(ToDoubleFunction.class));
    verify(meterRegistry).gauge(eq("workflow.activity.running"), any(Tags.class), any(ToDoubleFunction.class));
    verify(meterRegistry).gauge(eq("workflow.activity.completed"), any(Tags.class), any(ToDoubleFunction.class));
  }

  @Test
  void shouldRegisterDeployedWorkflowsGaugeWithCorrectValue() {
    // Arrange
    SimpleMeterRegistry registry = new SimpleMeterRegistry();
    when(workflowEngineMetrics.countDeployedWorkflows()).thenReturn(5L);

    // Act
    new WorkflowMetricsRegistry(registry, workflowEngineMetrics);

    // Assert
    Gauge gauge = registry.find("workflow.deployed").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(5.0);
  }

  @Test
  void shouldRegisterRunningProcessesGaugeWithCorrectValue() {
    // Arrange
    SimpleMeterRegistry registry = new SimpleMeterRegistry();
    when(workflowEngineMetrics.countRunningProcesses()).thenReturn(10L);

    // Act
    new WorkflowMetricsRegistry(registry, workflowEngineMetrics);

    // Assert
    Gauge gauge = registry.find("workflow.process.running").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(10.0);
  }

  @Test
  void shouldRegisterCompletedProcessesGaugeWithCorrectValue() {
    // Arrange
    SimpleMeterRegistry registry = new SimpleMeterRegistry();
    when(workflowEngineMetrics.countCompletedProcesses()).thenReturn(15L);

    // Act
    new WorkflowMetricsRegistry(registry, workflowEngineMetrics);

    // Assert
    Gauge gauge = registry.find("workflow.process.completed").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(15.0);
  }

  @Test
  void shouldRegisterRunningActivitiesGaugeWithCorrectValue() {
    // Arrange
    SimpleMeterRegistry registry = new SimpleMeterRegistry();
    when(workflowEngineMetrics.countRunningActivities()).thenReturn(20L);

    // Act
    new WorkflowMetricsRegistry(registry, workflowEngineMetrics);

    // Assert
    Gauge gauge = registry.find("workflow.activity.running").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(20.0);
  }

  @Test
  void shouldRegisterCompletedActivitiesGaugeWithCorrectValue() {
    // Arrange
    SimpleMeterRegistry registry = new SimpleMeterRegistry();
    when(workflowEngineMetrics.countCompletedActivities()).thenReturn(25L);

    // Act
    new WorkflowMetricsRegistry(registry, workflowEngineMetrics);

    // Assert
    Gauge gauge = registry.find("workflow.activity.completed").gauge();
    assertThat(gauge).isNotNull();
    assertThat(gauge.value()).isEqualTo(25.0);
  }

  @Test
  void shouldHandleZeroMetricsValues() {
    // Arrange
    SimpleMeterRegistry registry = new SimpleMeterRegistry();
    when(workflowEngineMetrics.countDeployedWorkflows()).thenReturn(0L);
    when(workflowEngineMetrics.countRunningProcesses()).thenReturn(0L);
    when(workflowEngineMetrics.countCompletedProcesses()).thenReturn(0L);
    when(workflowEngineMetrics.countRunningActivities()).thenReturn(0L);
    when(workflowEngineMetrics.countCompletedActivities()).thenReturn(0L);

    // Act
    new WorkflowMetricsRegistry(registry, workflowEngineMetrics);

    // Assert
    assertThat(registry.find("workflow.deployed").gauge().value()).isEqualTo(0.0);
    assertThat(registry.find("workflow.process.running").gauge().value()).isEqualTo(0.0);
    assertThat(registry.find("workflow.process.completed").gauge().value()).isEqualTo(0.0);
    assertThat(registry.find("workflow.activity.running").gauge().value()).isEqualTo(0.0);
    assertThat(registry.find("workflow.activity.completed").gauge().value()).isEqualTo(0.0);
  }
}
