package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.engine.camunda.variable.FormVariableListener;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ParallelGatewayBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JoinActivityNodeBuilderTest {

  @InjectMocks
  private JoinActivityNodeBuilder joinActivityNodeBuilder;

  @Mock
  private WorkflowNode workflowNode;

  @Mock
  private AbstractFlowNodeBuilder<?, ?> flowNodeBuilder;

  @Mock
  private BuildProcessContext buildProcessContext;

  @Test
  void shouldBuildParallelGatewayWithExecutionListener() {
    // Arrange
    String elementId = "joinNode1";
    String parentId = "parentNode";
    ParallelGatewayBuilder parallelGatewayBuilder = mock(ParallelGatewayBuilder.class);

    when(workflowNode.getId()).thenReturn(elementId);
    when(buildProcessContext.isAlreadyBuilt(elementId)).thenReturn(false);
    when(flowNodeBuilder.parallelGateway(elementId)).thenReturn(parallelGatewayBuilder);
    when(parallelGatewayBuilder.camundaExecutionListenerClass(
        eq(ExecutionListener.EVENTNAME_START),
        eq(FormVariableListener.class))).thenReturn(parallelGatewayBuilder);

    // Act
    AbstractFlowNodeBuilder<?, ?> result =
        joinActivityNodeBuilder.connect(workflowNode, parentId, flowNodeBuilder, buildProcessContext);

    // Assert
    assertThat(result).isNotNull();
    verify(flowNodeBuilder).parallelGateway(elementId);
    verify(parallelGatewayBuilder).camundaExecutionListenerClass(
        ExecutionListener.EVENTNAME_START,
        FormVariableListener.class);
  }

  @Test
  void shouldReturnCorrectWorkflowNodeType() {
    // Act
    WorkflowNodeType type = joinActivityNodeBuilder.type();

    // Assert
    assertThat(type).isEqualTo(WorkflowNodeType.JOIN_ACTIVITY);
  }
}
