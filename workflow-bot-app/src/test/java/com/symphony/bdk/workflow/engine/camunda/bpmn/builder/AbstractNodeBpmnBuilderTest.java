package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;

import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractNodeBpmnBuilderTest {

    @Mock
    private WorkflowNode element;

    @Mock
    private BuildProcessContext context;

    @Mock
    private AbstractFlowNodeBuilder<?, ?> builder;

    @Test
    void connectWithAlreadyBuiltConditionalNodeShouldCallDoConditionalConnection() {
        // Arrange
        String nodeId = "node123";
        String parentId = "parent123";
        String condition = "${condition}";
        @SuppressWarnings("unchecked")
        AbstractGatewayBuilder<?, ?> mockGatewayBuilder = mock(AbstractGatewayBuilder.class);
        @SuppressWarnings("unchecked")
        AbstractGatewayBuilder<?, ?> conditionResult = mock(AbstractGatewayBuilder.class);

        when(element.getId()).thenReturn(nodeId);
        when(context.isAlreadyBuilt(nodeId)).thenReturn(true);
        when(element.isConditional()).thenReturn(true);
        when(element.getIfCondition(parentId)).thenReturn(condition);
        doReturn(conditionResult).when(mockGatewayBuilder).condition("if", condition);
        doReturn(mockGatewayBuilder).when(conditionResult).connectTo(nodeId);

        TestNodeBpmnBuilder testBuilder = new TestNodeBpmnBuilder();

        // Act
        AbstractFlowNodeBuilder<?, ?> result = testBuilder.connect(element, parentId, mockGatewayBuilder, context);

        // Assert
        verify(context).isAlreadyBuilt(nodeId);
        verify(element).isConditional();
        verify(mockGatewayBuilder).condition("if", condition);
        verify(conditionResult).connectTo(nodeId);
        assertEquals(mockGatewayBuilder, result);
    }

    @Test
    void connectWithAlreadyBuiltNonConditionalNodeShouldConnectToExistingNode() {
        // Arrange
        String nodeId = "node123";
        String parentId = "parent123";

        when(element.getId()).thenReturn(nodeId);
        when(context.isAlreadyBuilt(nodeId)).thenReturn(true);
        when(element.isConditional()).thenReturn(false);
        when(builder.connectTo(nodeId)).thenReturn(builder);

        TestNodeBpmnBuilder testBuilder = new TestNodeBpmnBuilder();

        // Act
        AbstractFlowNodeBuilder<?, ?> result = testBuilder.connect(element, parentId, builder, context);

        // Assert
        verify(context).isAlreadyBuilt(nodeId);
        verify(element).isConditional();
        verify(builder).connectTo(nodeId);
        assertEquals(builder, result);
    }

    @Test
    void connectWithNotBuiltGatewayAndConditionalShouldAddCondition() {
        // Arrange
        String nodeId = "node123";
        String parentId = "parent123";
        String condition = "${x > 5}";
        @SuppressWarnings("unchecked")
        AbstractGatewayBuilder<?, ?> mockGatewayBuilder = mock(AbstractGatewayBuilder.class);

        when(element.getId()).thenReturn(nodeId);
        when(context.isAlreadyBuilt(nodeId)).thenReturn(false);
        when(element.isConditional()).thenReturn(true);
        when(element.getIfCondition(parentId)).thenReturn(condition);
        doReturn(mockGatewayBuilder).when(mockGatewayBuilder).condition("if", condition);
        when(context.hasEventSubProcess()).thenReturn(false);

        TestNodeBpmnBuilder testBuilder = new TestNodeBpmnBuilder();

        // Act
        AbstractFlowNodeBuilder<?, ?> result = testBuilder.connect(element, parentId, mockGatewayBuilder, context);

        // Assert
        verify(context).isAlreadyBuilt(nodeId);
        verify(mockGatewayBuilder).condition("if", condition);
        assertEquals(mockGatewayBuilder, result);
    }

    @Test
    void connectWithNotBuiltAndEventSubProcessWithMultipleParentsShouldEndSubProcess() {
        // Arrange
        String nodeId = "node123";
        String parentId = "parent123";
        EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
        SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
        EndEventBuilder endEventBuilder = mock(EndEventBuilder.class);

        when(element.getId()).thenReturn(nodeId);
        when(context.isAlreadyBuilt(nodeId)).thenReturn(false);
        when(context.hasEventSubProcess()).thenReturn(true);
        when(context.getParents(nodeId)).thenReturn(Arrays.asList("parent1", "parent2"));
        when(builder.endEvent()).thenReturn(endEventBuilder);
        when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);
        when(eventSubProcessBuilder.subProcessDone()).thenReturn(subProcessBuilder);

        TestNodeBpmnBuilder testBuilder = new TestNodeBpmnBuilder();

        // Act
        AbstractFlowNodeBuilder<?, ?> result = testBuilder.connect(element, parentId, builder, context);

        // Assert
        verify(context).isAlreadyBuilt(nodeId);
        verify(context).hasEventSubProcess();
        verify(context).getParents(nodeId);
        verify(builder).endEvent();
        verify(context).removeLastEventSubProcessBuilder();
        verify(eventSubProcessBuilder).subProcessDone();
        verify(context).cacheSubProcessTimeoutToDone(subProcessBuilder);
        assertEquals(subProcessBuilder, result);
    }

    @Test
    void connectWithNotBuiltAndNoEventSubProcessShouldBuildDirectly() {
        // Arrange
        String nodeId = "node123";
        String parentId = "parent123";

        when(element.getId()).thenReturn(nodeId);
        when(context.isAlreadyBuilt(nodeId)).thenReturn(false);
        when(context.hasEventSubProcess()).thenReturn(false);

        TestNodeBpmnBuilder testBuilder = new TestNodeBpmnBuilder();

        // Act
        AbstractFlowNodeBuilder<?, ?> result = testBuilder.connect(element, parentId, builder, context);

        // Assert
        verify(context).isAlreadyBuilt(nodeId);
        verify(context).hasEventSubProcess();
        verify(context, never()).getParents(anyString());
        assertEquals(builder, result);
    }

    @Test
    void connectWithNotBuiltAndEventSubProcessButSingleParentShouldNotEndSubProcess() {
        // Arrange
        String nodeId = "node123";
        String parentId = "parent123";

        when(element.getId()).thenReturn(nodeId);
        when(context.isAlreadyBuilt(nodeId)).thenReturn(false);
        when(context.hasEventSubProcess()).thenReturn(true);
        when(context.getParents(nodeId)).thenReturn(Arrays.asList("parent1"));

        TestNodeBpmnBuilder testBuilder = new TestNodeBpmnBuilder();

        // Act
        AbstractFlowNodeBuilder<?, ?> result = testBuilder.connect(element, parentId, builder, context);

        // Assert
        verify(context).isAlreadyBuilt(nodeId);
        verify(context).hasEventSubProcess();
        verify(context).getParents(nodeId);
        verify(builder, never()).endEvent();
        assertEquals(builder, result);
    }

    @Test
    void connectWithAlreadyBuiltNodeAndSubProcessBuilderShouldNotConnectTo() {
        // Arrange
        String nodeId = "node123";
        String parentId = "parent123";
        SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);

        when(element.getId()).thenReturn(nodeId);
        when(context.isAlreadyBuilt(nodeId)).thenReturn(true);
        when(element.isConditional()).thenReturn(false);

        TestNodeBpmnBuilder testBuilder = new TestNodeBpmnBuilder();

        // Act
        AbstractFlowNodeBuilder<?, ?> result = testBuilder.connect(element, parentId, subProcessBuilder, context);

        // Assert
        verify(context).isAlreadyBuilt(nodeId);
        verify(element).isConditional();
        verify(subProcessBuilder, never()).connectTo(anyString());
        assertEquals(subProcessBuilder, result);
    }

    /**
     * Concrete test implementation of AbstractNodeBpmnBuilder for testing purposes.
     */
    private static class TestNodeBpmnBuilder extends AbstractNodeBpmnBuilder {
        @Override
        protected AbstractFlowNodeBuilder<?, ?> build(WorkflowNode element, String parentId,
                                                      AbstractFlowNodeBuilder<?, ?> builder,
                                                      BuildProcessContext context) {
            // Simple pass-through for testing
            return builder;
        }

        @Override
        public WorkflowNodeType type() {
            return WorkflowNodeType.ACTIVITY;
        }
    }
}
