package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.builder.EventBasedGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ExclusiveGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaEntry;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaInputOutput;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaInputParameter;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaBpmnBuilderTest {

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private WorkflowNodeBpmnBuilderRegistry builderFactory;

  @Mock
  private SessionService sessionService;

  @Mock
  private WorkflowDirectedGraphService directedGraphService;

  @Mock
  private BuildProcessContext context;

  @Mock
  private AbstractFlowNodeBuilder<?, ?> flowNodeBuilder;

  @Mock
  private EndEventBuilder endEventBuilder;

  @Mock
  private SubProcessBuilder subProcessBuilder;

  private CamundaBpmnBuilder camundaBpmnBuilder;

  @BeforeEach
  void setUp() {
    camundaBpmnBuilder = new CamundaBpmnBuilder(repositoryService, builderFactory, sessionService,
        directedGraphService);
  }

  @Test
  void shouldAddEndEventWhenBuilderIsNotSubProcessBuilder() throws Exception {
    // Given
    String nodeId = "testNode";
    when(flowNodeBuilder.endEvent()).thenReturn(endEventBuilder);

    // When
    invokeLeafNode(nodeId, flowNodeBuilder, context);

    // Then
    verify(flowNodeBuilder).endEvent();
    verify(context).addNodeBuilder(eq(nodeId), any(AbstractFlowNodeBuilder.class));
    verify(context).addLastNodeBuilder(any(AbstractFlowNodeBuilder.class));
  }

  @Test
  void shouldNotAddEndEventWhenBuilderIsSubProcessBuilder() throws Exception {
    // Given
    String nodeId = "testNode";

    // When
    invokeLeafNode(nodeId, subProcessBuilder, context);

    // Then
    verify(subProcessBuilder, never()).endEvent();
    verify(context, never()).addNodeBuilder(eq(nodeId), any(AbstractFlowNodeBuilder.class));
    verify(context, never()).addLastNodeBuilder(any(AbstractFlowNodeBuilder.class));
  }

  private void invokeLeafNode(String nodeId, AbstractFlowNodeBuilder<?, ?> builder, BuildProcessContext context)
      throws Exception {
    Method leafNodeMethod = CamundaBpmnBuilder.class.getDeclaredMethod("leafNode", String.class,
        AbstractFlowNodeBuilder.class, BuildProcessContext.class);
    leafNodeMethod.setAccessible(true);
    leafNodeMethod.invoke(camundaBpmnBuilder, nodeId, builder, context);
  }

  @Test
  void shouldAddExclusiveGatewayWhenActivitiesAndConditional() throws Exception {
    // Given
    String nodeId = "test/node";
    ExclusiveGatewayBuilder exclusiveGatewayBuilder = org.mockito.Mockito.mock(ExclusiveGatewayBuilder.class);
    when(flowNodeBuilder.exclusiveGateway("testnode_exclusive_gateway")).thenReturn(exclusiveGatewayBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeAddGateway(nodeId, flowNodeBuilder, true, true, 2);

    // Then
    verify(flowNodeBuilder).exclusiveGateway("testnode_exclusive_gateway");
    assertThat(result).isEqualTo(exclusiveGatewayBuilder);
  }

  @Test
  void shouldAddEventBasedGatewayWhenNotActivitiesAndConditional() throws Exception {
    // Given
    String nodeId = "testNode";
    EventBasedGatewayBuilder eventGatewayBuilder = org.mockito.Mockito.mock(EventBasedGatewayBuilder.class);
    when(flowNodeBuilder.eventBasedGateway()).thenReturn(eventGatewayBuilder);
    when(eventGatewayBuilder.id(nodeId + "_event_gateway")).thenReturn(eventGatewayBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeAddGateway(nodeId, flowNodeBuilder, false, true, 1);

    // Then
    verify(flowNodeBuilder).eventBasedGateway();
    verify(eventGatewayBuilder).id("testNode_event_gateway");
    assertThat(result).isEqualTo(eventGatewayBuilder);
  }

  @Test
  void shouldAddEventBasedGatewayWhenNotActivitiesAndMultipleChildren() throws Exception {
    // Given
    String nodeId = "testNode";
    EventBasedGatewayBuilder eventGatewayBuilder = org.mockito.Mockito.mock(EventBasedGatewayBuilder.class);
    when(flowNodeBuilder.eventBasedGateway()).thenReturn(eventGatewayBuilder);
    when(eventGatewayBuilder.id(nodeId + "_event_gateway")).thenReturn(eventGatewayBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeAddGateway(nodeId, flowNodeBuilder, false, false, 2);

    // Then
    verify(flowNodeBuilder).eventBasedGateway();
    verify(eventGatewayBuilder).id("testNode_event_gateway");
    assertThat(result).isEqualTo(eventGatewayBuilder);
  }

  @Test
  void shouldReturnBuilderUnchangedWhenNoGatewayNeeded() throws Exception {
    // Given
    String nodeId = "testNode";

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeAddGateway(nodeId, flowNodeBuilder, false, false, 1);

    // Then
    verify(flowNodeBuilder, never()).exclusiveGateway(any());
    verify(flowNodeBuilder, never()).eventBasedGateway();
    assertThat(result).isEqualTo(flowNodeBuilder);
  }

  private AbstractFlowNodeBuilder<?, ?> invokeAddGateway(String nodeId, AbstractFlowNodeBuilder<?, ?> builder,
      boolean activities, boolean conditional, int childrenSize) throws Exception {
    Method addGatewayMethod = CamundaBpmnBuilder.class.getDeclaredMethod("addGateway", String.class,
        AbstractFlowNodeBuilder.class, boolean.class, boolean.class, int.class);
    addGatewayMethod.setAccessible(true);
    return (AbstractFlowNodeBuilder<?, ?>) addGatewayMethod.invoke(camundaBpmnBuilder, nodeId, builder, activities,
        conditional, childrenSize);
  }

  @Test
  void shouldReturnBuilderUnchangedWhenNoSubProcesses() throws Exception {
    // Given
    when(context.hasEventSubProcess()).thenReturn(false);
    when(context.hasTimeoutSubProcess()).thenReturn(false);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    assertThat(result).isEqualTo(flowNodeBuilder);
    verify(context, never()).removeLastEventSubProcessBuilder();
    verify(context, never()).removeLastSubProcessTimeoutBuilder();
  }

  @Test
  void shouldCloseEventSubProcessesWhenPresent() throws Exception {
    // Given
    EventSubProcessBuilder eventSubProcessBuilder = org.mockito.Mockito.mock(EventSubProcessBuilder.class);
    SubProcessBuilder resultSubProcessBuilder = org.mockito.Mockito.mock(SubProcessBuilder.class);
    EndEventBuilder resultEndEventBuilder = org.mockito.Mockito.mock(EndEventBuilder.class);

    when(context.hasEventSubProcess()).thenReturn(true, false);
    when(context.hasTimeoutSubProcess()).thenReturn(false);
    when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(resultSubProcessBuilder);
    when(resultSubProcessBuilder.endEvent()).thenReturn(resultEndEventBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    verify(context).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder).subProcessDone();
    verify(context).cacheSubProcessTimeoutToDone(resultSubProcessBuilder);
    verify(resultSubProcessBuilder).endEvent();
    assertThat(result).isEqualTo(resultEndEventBuilder);
  }

  @Test
  void shouldCloseTimeoutSubProcessesWhenPresent() throws Exception {
    // Given
    AbstractFlowNodeBuilder<?, ?> timeoutSubProcessBuilder = org.mockito.Mockito.mock(AbstractFlowNodeBuilder.class);

    when(context.hasEventSubProcess()).thenReturn(false);
    when(context.hasTimeoutSubProcess()).thenReturn(true, false);
    doReturn(timeoutSubProcessBuilder).when(context).removeLastSubProcessTimeoutBuilder();
    when(timeoutSubProcessBuilder.endEvent()).thenReturn(endEventBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    verify(context).removeLastSubProcessTimeoutBuilder();
    verify(timeoutSubProcessBuilder).endEvent();
    assertThat(result).isEqualTo(flowNodeBuilder);
  }

  @Test
  void shouldCloseBothEventAndTimeoutSubProcessesWhenPresent() throws Exception {
    // Given
    EventSubProcessBuilder eventSubProcessBuilder = org.mockito.Mockito.mock(EventSubProcessBuilder.class);
    SubProcessBuilder resultSubProcessBuilder = org.mockito.Mockito.mock(SubProcessBuilder.class);
    EndEventBuilder eventEndEventBuilder = org.mockito.Mockito.mock(EndEventBuilder.class);
    AbstractFlowNodeBuilder<?, ?> timeoutSubProcessBuilder = org.mockito.Mockito.mock(AbstractFlowNodeBuilder.class);

    when(context.hasEventSubProcess()).thenReturn(true, false);
    when(context.hasTimeoutSubProcess()).thenReturn(true, false);
    when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(resultSubProcessBuilder);
    when(resultSubProcessBuilder.endEvent()).thenReturn(eventEndEventBuilder);
    doReturn(timeoutSubProcessBuilder).when(context).removeLastSubProcessTimeoutBuilder();
    when(timeoutSubProcessBuilder.endEvent()).thenReturn(endEventBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    verify(context).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder).subProcessDone();
    verify(context).cacheSubProcessTimeoutToDone(resultSubProcessBuilder);
    verify(resultSubProcessBuilder).endEvent();
    verify(context).removeLastSubProcessTimeoutBuilder();
    verify(timeoutSubProcessBuilder).endEvent();
    assertThat(result).isEqualTo(eventEndEventBuilder);
  }

  @Test
  void shouldCloseMultipleEventSubProcessesWhenPresent() throws Exception {
    // Given
    EventSubProcessBuilder eventSubProcessBuilder1 = org.mockito.Mockito.mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder eventSubProcessBuilder2 = org.mockito.Mockito.mock(EventSubProcessBuilder.class);
    SubProcessBuilder resultSubProcessBuilder1 = org.mockito.Mockito.mock(SubProcessBuilder.class);
    SubProcessBuilder resultSubProcessBuilder2 = org.mockito.Mockito.mock(SubProcessBuilder.class);
    EndEventBuilder eventEndEventBuilder1 = org.mockito.Mockito.mock(EndEventBuilder.class);
    EndEventBuilder eventEndEventBuilder2 = org.mockito.Mockito.mock(EndEventBuilder.class);

    when(context.hasEventSubProcess()).thenReturn(true, true, false);
    when(context.hasTimeoutSubProcess()).thenReturn(false);
    when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder1, eventSubProcessBuilder2);
    when(eventSubProcessBuilder1.subProcessDone()).thenReturn(resultSubProcessBuilder1);
    when(eventSubProcessBuilder2.subProcessDone()).thenReturn(resultSubProcessBuilder2);
    when(resultSubProcessBuilder1.endEvent()).thenReturn(eventEndEventBuilder1);
    when(resultSubProcessBuilder2.endEvent()).thenReturn(eventEndEventBuilder2);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    verify(context, org.mockito.Mockito.times(2)).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder1).subProcessDone();
    verify(eventSubProcessBuilder2).subProcessDone();
    verify(context).cacheSubProcessTimeoutToDone(resultSubProcessBuilder1);
    verify(context).cacheSubProcessTimeoutToDone(resultSubProcessBuilder2);
    assertThat(result).isEqualTo(eventEndEventBuilder2);
  }

  private AbstractFlowNodeBuilder<?, ?> invokeCloseUpSubProcessesIfAny(BuildProcessContext context,
      AbstractFlowNodeBuilder<?, ?> builder) throws Exception {
    Method closeUpSubProcessesIfAnyMethod = CamundaBpmnBuilder.class.getDeclaredMethod("closeUpSubProcessesIfAny",
        BuildProcessContext.class, AbstractFlowNodeBuilder.class);
    closeUpSubProcessesIfAnyMethod.setAccessible(true);
    return (AbstractFlowNodeBuilder<?, ?>) closeUpSubProcessesIfAnyMethod.invoke(camundaBpmnBuilder, context, builder);
  }

  @Test
  void shouldHandleEmptyActivitiesListWhenInjectingActivityDefAsInput() throws Exception {
    // Given
    BpmnModelInstance instance = mock(BpmnModelInstance.class);
    List<Activity> activities = new ArrayList<>();
    Collection<CamundaInputOutput> emptyCollection = new ArrayList<>();
    when(instance.getModelElementsByType(CamundaInputOutput.class)).thenReturn(emptyCollection);

    // When
    invokeInjectActivityDefAsInput(instance, activities);

    // Then
    verify(instance).getModelElementsByType(CamundaInputOutput.class);
  }

  @Test
  void shouldInjectActivityDefAsInputForSingleActivity() throws Exception {
    // Given
    BpmnModelInstance instance = mock(BpmnModelInstance.class);

    BaseActivity baseActivity = mock(BaseActivity.class);
    when(baseActivity.getId()).thenReturn("testActivity1");

    Activity activity = new Activity();
    activity.setImplementation(baseActivity);

    List<Activity> activities = Arrays.asList(activity);

    CamundaInputOutput inputOutput = mock(CamundaInputOutput.class);
    CamundaInputParameter activityNameParam = mock(CamundaInputParameter.class);
    when(activityNameParam.getCamundaName()).thenReturn("activity");
    when(activityNameParam.getTextContent()).thenReturn("testActivity1");

    Collection<CamundaInputParameter> inputParams = Arrays.asList(activityNameParam);
    when(inputOutput.getChildElementsByType(CamundaInputParameter.class)).thenReturn(inputParams);

    Collection<CamundaInputOutput> inputOutputCollection = Arrays.asList(inputOutput);
    when(instance.getModelElementsByType(CamundaInputOutput.class)).thenReturn(inputOutputCollection);

    setupBpmnModelInstanceMocks(instance, inputOutput);

    // When
    invokeInjectActivityDefAsInput(instance, activities);

    // Then
    verify(instance).getModelElementsByType(CamundaInputOutput.class);
    verify(inputOutput).getChildElementsByType(CamundaInputParameter.class);
    verify(activityNameParam).getCamundaName();
  }

  @Test
  void shouldInjectActivityDefAsInputForMultipleActivities() throws Exception {
    // Given
    BpmnModelInstance instance = mock(BpmnModelInstance.class);

    BaseActivity baseActivity1 = mock(BaseActivity.class);
    when(baseActivity1.getId()).thenReturn("activity1");
    Activity activity1 = new Activity();
    activity1.setImplementation(baseActivity1);

    BaseActivity baseActivity2 = mock(BaseActivity.class);
    when(baseActivity2.getId()).thenReturn("activity2");
    Activity activity2 = new Activity();
    activity2.setImplementation(baseActivity2);

    List<Activity> activities = Arrays.asList(activity1, activity2);

    CamundaInputOutput inputOutput1 = mock(CamundaInputOutput.class);
    CamundaInputParameter activityNameParam1 = mock(CamundaInputParameter.class);
    when(activityNameParam1.getCamundaName()).thenReturn("activity");
    when(activityNameParam1.getTextContent()).thenReturn("activity1");
    Collection<CamundaInputParameter> inputParams1 = Arrays.asList(activityNameParam1);
    when(inputOutput1.getChildElementsByType(CamundaInputParameter.class)).thenReturn(inputParams1);

    CamundaInputOutput inputOutput2 = mock(CamundaInputOutput.class);
    CamundaInputParameter activityNameParam2 = mock(CamundaInputParameter.class);
    when(activityNameParam2.getCamundaName()).thenReturn("activity");
    when(activityNameParam2.getTextContent()).thenReturn("activity2");
    Collection<CamundaInputParameter> inputParams2 = Arrays.asList(activityNameParam2);
    when(inputOutput2.getChildElementsByType(CamundaInputParameter.class)).thenReturn(inputParams2);

    Collection<CamundaInputOutput> inputOutputCollection = Arrays.asList(inputOutput1, inputOutput2);
    when(instance.getModelElementsByType(CamundaInputOutput.class)).thenReturn(inputOutputCollection);

    setupBpmnModelInstanceMocksForMultiple(instance, inputOutput1, inputOutput2);

    // When
    invokeInjectActivityDefAsInput(instance, activities);

    // Then
    verify(instance).getModelElementsByType(CamundaInputOutput.class);
    verify(inputOutput1).getChildElementsByType(CamundaInputParameter.class);
    verify(inputOutput2).getChildElementsByType(CamundaInputParameter.class);
    verify(activityNameParam1).getCamundaName();
    verify(activityNameParam2).getCamundaName();
  }

  private void setupBpmnModelInstanceMocks(BpmnModelInstance instance, CamundaInputOutput inputOutput) {
    org.camunda.bpm.model.bpmn.instance.camunda.CamundaMap map =
        mock(org.camunda.bpm.model.bpmn.instance.camunda.CamundaMap.class);
    org.camunda.bpm.model.bpmn.instance.camunda.CamundaEntry entry =
        mock(org.camunda.bpm.model.bpmn.instance.camunda.CamundaEntry.class);
    CamundaInputParameter inputParameter = mock(CamundaInputParameter.class);
    Collection<org.camunda.bpm.model.bpmn.instance.camunda.CamundaEntry> entries = new ArrayList<>();

    when(instance.newInstance(org.camunda.bpm.model.bpmn.instance.camunda.CamundaMap.class)).thenReturn(map);
    when(instance.newInstance(org.camunda.bpm.model.bpmn.instance.camunda.CamundaEntry.class)).thenReturn(entry);
    when(instance.newInstance(CamundaInputParameter.class)).thenReturn(inputParameter);
    when(map.getCamundaEntries()).thenReturn(entries);
  }

  private void setupBpmnModelInstanceMocksForMultiple(BpmnModelInstance instance, CamundaInputOutput... inputOutputs) {
    when(instance.newInstance(org.camunda.bpm.model.bpmn.instance.camunda.CamundaMap.class)).thenAnswer(
        invocation -> {
          org.camunda.bpm.model.bpmn.instance.camunda.CamundaMap map =
              mock(org.camunda.bpm.model.bpmn.instance.camunda.CamundaMap.class);
          when(map.getCamundaEntries()).thenReturn(new ArrayList<>());
          return map;
        });
    when(instance.newInstance(org.camunda.bpm.model.bpmn.instance.camunda.CamundaEntry.class)).thenAnswer(
        invocation -> mock(org.camunda.bpm.model.bpmn.instance.camunda.CamundaEntry.class));
    when(instance.newInstance(CamundaInputParameter.class)).thenAnswer(
        invocation -> mock(CamundaInputParameter.class));
  }

  private void invokeInjectActivityDefAsInput(BpmnModelInstance instance, List<Activity> activities)
      throws Exception {
    Method injectActivityDefAsInputMethod = CamundaBpmnBuilder.class.getDeclaredMethod("injectActivityDefAsInput",
        BpmnModelInstance.class, List.class);
    injectActivityDefAsInputMethod.setAccessible(true);
    injectActivityDefAsInputMethod.invoke(camundaBpmnBuilder, instance, activities);
  }
}
