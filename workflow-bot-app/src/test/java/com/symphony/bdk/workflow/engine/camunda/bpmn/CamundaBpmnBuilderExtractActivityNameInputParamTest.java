package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.camunda.CamundaExecutor;

import org.camunda.bpm.model.bpmn.instance.camunda.CamundaInputOutput;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaInputParameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaBpmnBuilderExtractActivityNameInputParamTest {

  @Mock
  private CamundaInputOutput inputOutput;

  @Mock
  private CamundaInputParameter activityInputParameter;

  @Mock
  private CamundaInputParameter otherInputParameter;

  @Test
  void shouldReturnActivityInputParameterWhenActivityNameExists() throws Exception {
    // Given
    when(activityInputParameter.getCamundaName()).thenReturn(CamundaExecutor.ACTIVITY);
    when(inputOutput.getChildElementsByType(CamundaInputParameter.class))
        .thenReturn(Collections.singletonList(activityInputParameter));

    // When
    CamundaInputParameter result = invokeExtractActivityNameInputParam(inputOutput);

    // Then
    assertThat(result).isEqualTo(activityInputParameter);
  }

  @Test
  void shouldReturnFirstMatchingActivityInputParameterWhenMultipleParametersExist() throws Exception {
    // Given
    when(otherInputParameter.getCamundaName()).thenReturn("otherParam");
    when(activityInputParameter.getCamundaName()).thenReturn(CamundaExecutor.ACTIVITY);
    when(inputOutput.getChildElementsByType(CamundaInputParameter.class))
        .thenReturn(Arrays.asList(otherInputParameter, activityInputParameter));

    // When
    CamundaInputParameter result = invokeExtractActivityNameInputParam(inputOutput);

    // Then
    assertThat(result).isEqualTo(activityInputParameter);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenActivityNameIsMissing() throws Exception {
    // Given
    when(inputOutput.getChildElementsByType(CamundaInputParameter.class))
        .thenReturn(Collections.emptyList());

    // When / Then
    assertThatThrownBy(() -> invokeExtractActivityNameInputParam(inputOutput))
        .cause()
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Activity missing its name");
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenNoMatchingParameterExists() throws Exception {
    // Given
    when(otherInputParameter.getCamundaName()).thenReturn("otherParam");
    when(inputOutput.getChildElementsByType(CamundaInputParameter.class))
        .thenReturn(Collections.singletonList(otherInputParameter));

    // When / Then
    assertThatThrownBy(() -> invokeExtractActivityNameInputParam(inputOutput))
        .cause()
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Activity missing its name");
  }

  private CamundaInputParameter invokeExtractActivityNameInputParam(CamundaInputOutput inputOutput)
      throws Exception {
    Method extractActivityNameInputParamMethod = CamundaBpmnBuilder.class.getDeclaredMethod(
        "extractActivityNameInputParam", CamundaInputOutput.class);
    extractActivityNameInputParamMethod.setAccessible(true);
    return (CamundaInputParameter) extractActivityNameInputParamMethod.invoke(null, inputOutput);
  }
}
