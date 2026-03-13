package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.camunda.CamundaExecutor;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaBpmnBuilderAddSerialisedActivityInputParamTest {

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private WorkflowNodeBpmnBuilderRegistry builderFactory;

  @Mock
  private SessionService sessionService;

  @Mock
  private WorkflowDirectedGraphService directedGraphService;

  @Mock
  private BpmnModelInstance instance;

  @Mock
  private CamundaInputOutput inputOutput;

  @Mock
  private CamundaInputParameter activityNameInputParam;

  @Mock
  private BaseActivity baseActivity;

  private CamundaBpmnBuilder camundaBpmnBuilder;

  @BeforeEach
  void setUp() {
    camundaBpmnBuilder = new CamundaBpmnBuilder(repositoryService, builderFactory, sessionService,
        directedGraphService);
  }

  @Test
  void shouldAddSerialisedActivityInputParamWhenValidInputsProvided() throws Exception {
    // Given
    String activityName = "testActivity";
    Map<String, BaseActivity> activityMap = new HashMap<>();
    activityMap.put(activityName, baseActivity);

    CamundaMap map = mock(CamundaMap.class);
    CamundaEntry entry = mock(CamundaEntry.class);
    CamundaInputParameter inputParameter = mock(CamundaInputParameter.class);
    Collection<CamundaEntry> entries = mock(Collection.class);

    when(instance.newInstance(CamundaMap.class)).thenReturn(map);
    when(instance.newInstance(CamundaEntry.class)).thenReturn(entry);
    when(instance.newInstance(CamundaInputParameter.class)).thenReturn(inputParameter);
    when(activityNameInputParam.getTextContent()).thenReturn(activityName);
    when(map.getCamundaEntries()).thenReturn(entries);

    // When
    invokeAddSerialisedActivityInputParam(instance, inputOutput, activityNameInputParam, activityMap);

    // Then
    verify(instance).newInstance(CamundaMap.class);
    verify(instance).newInstance(CamundaEntry.class);
    verify(activityNameInputParam).getTextContent();
    verify(entry).setCamundaKey(activityName);
    verify(entry).setTextContent(CamundaExecutor.OBJECT_MAPPER.writeValueAsString(baseActivity));
    verify(entries).add(entry);
    verify(instance).newInstance(CamundaInputParameter.class);
    verify(inputParameter).setCamundaName(CamundaExecutor.SERIALISED_ACTIVITY);
    verify(inputParameter).setValue(map);
    verify(inputOutput).addChildElement(inputParameter);
  }

  @Test
  void shouldCreateInputParameterWithCorrectValues() throws Exception {
    // Given
    String activityName = "myActivity";
    Map<String, BaseActivity> activityMap = new HashMap<>();
    activityMap.put(activityName, baseActivity);

    CamundaMap map = mock(CamundaMap.class);
    CamundaEntry entry = mock(CamundaEntry.class);
    CamundaInputParameter inputParameter = mock(CamundaInputParameter.class);
    Collection<CamundaEntry> entries = mock(Collection.class);

    when(instance.newInstance(CamundaMap.class)).thenReturn(map);
    when(instance.newInstance(CamundaEntry.class)).thenReturn(entry);
    when(instance.newInstance(CamundaInputParameter.class)).thenReturn(inputParameter);
    when(activityNameInputParam.getTextContent()).thenReturn(activityName);
    when(map.getCamundaEntries()).thenReturn(entries);

    // When
    invokeAddSerialisedActivityInputParam(instance, inputOutput, activityNameInputParam, activityMap);

    // Then
    verify(entry).setCamundaKey(activityName);
    verify(inputParameter).setCamundaName(CamundaExecutor.SERIALISED_ACTIVITY);
  }

  @Test
  void shouldHandleNullActivityInMapWhenActivityNotFound() throws Exception {
    // Given
    String activityName = "nonExistentActivity";
    Map<String, BaseActivity> activityMap = new HashMap<>();

    CamundaMap map = mock(CamundaMap.class);
    CamundaEntry entry = mock(CamundaEntry.class);
    CamundaInputParameter inputParameter = mock(CamundaInputParameter.class);
    Collection<CamundaEntry> entries = mock(Collection.class);

    when(instance.newInstance(CamundaMap.class)).thenReturn(map);
    when(instance.newInstance(CamundaEntry.class)).thenReturn(entry);
    when(instance.newInstance(CamundaInputParameter.class)).thenReturn(inputParameter);
    when(activityNameInputParam.getTextContent()).thenReturn(activityName);
    when(map.getCamundaEntries()).thenReturn(entries);

    // When
    invokeAddSerialisedActivityInputParam(instance, inputOutput, activityNameInputParam, activityMap);

    // Then
    verify(entry).setCamundaKey(activityName);
    verify(entry).setTextContent(CamundaExecutor.OBJECT_MAPPER.writeValueAsString(null));
    verify(entries).add(entry);
    verify(inputParameter).setValue(map);
    verify(inputOutput).addChildElement(inputParameter);
  }

  private void invokeAddSerialisedActivityInputParam(BpmnModelInstance instance,
      CamundaInputOutput inputOutput, CamundaInputParameter activityNameInputParam,
      Map<String, BaseActivity> activityMap) throws Exception {
    Method addSerialisedActivityInputParamMethod = CamundaBpmnBuilder.class.getDeclaredMethod(
        "addSerialisedActivityInputParam", BpmnModelInstance.class, CamundaInputOutput.class,
        CamundaInputParameter.class, Map.class);
    addSerialisedActivityInputParamMethod.setAccessible(true);
    addSerialisedActivityInputParamMethod.invoke(camundaBpmnBuilder, instance, inputOutput,
        activityNameInputParam, activityMap);
  }
}
