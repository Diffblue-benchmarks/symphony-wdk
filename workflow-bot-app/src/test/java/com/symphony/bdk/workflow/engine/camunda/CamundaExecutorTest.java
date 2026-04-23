package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.ResourceProvider;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUser;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaExecutorTest {

  @Mock
  private BdkGateway bdk;

  @Mock
  private SharedDataStore sharedDataStore;

  @Mock
  private SecretKeeper secretKeeper;

  @Mock
  private AuditTrailLogAction auditTrailLogger;

  @Mock
  private ResourceProvider resourceLoader;

  @Mock
  private ApplicationContext applicationContext;

  public static class TestGetUserExecutor implements ActivityExecutor<GetUser> {
    @Override
    public void execute(ActivityExecutorContext<GetUser> context) throws IOException {
      // no-op for testing
    }
  }

  public static class FailingGetUserExecutor implements ActivityExecutor<GetUser> {
    @Override
    public void execute(ActivityExecutorContext<GetUser> context) throws IOException {
      throw new RuntimeException("executor test failure");
    }
  }

  private CamundaExecutor createCamundaExecutor() {
    return new CamundaExecutor(bdk, sharedDataStore, secretKeeper, auditTrailLogger, resourceLoader, applicationContext);
  }

  private DelegateExecution createMockExecution(Class<?> executorClass) {
    DelegateExecution execution = mock(DelegateExecution.class);
    String activityId = "testActivity";
    Map<String, Object> serialisedActivity = new HashMap<>();
    serialisedActivity.put(activityId, "{\"id\":\"" + activityId + "\"}");

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass.getName());
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serialisedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn(activityId);
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(null);
    when(execution.getProcessInstanceId()).thenReturn("process-instance-1");
    when(execution.getActivityInstanceId()).thenReturn("activity-instance-1");
    return execution;
  }

  @Test
  void shouldConstructWithAllDependencies() {
    CamundaExecutor executor = createCamundaExecutor();

    assertThat(executor).isNotNull();
  }

  @Test
  void shouldExecuteWhenExecutorIsNotASpringBean() throws Exception {
    CamundaExecutor camundaExecutor = createCamundaExecutor();
    DelegateExecution execution = createMockExecution(TestGetUserExecutor.class);
    when(applicationContext.getBean(TestGetUserExecutor.class))
        .thenThrow(new NoSuchBeanDefinitionException(TestGetUserExecutor.class));

    assertThatCode(() -> camundaExecutor.execute(execution)).doesNotThrowAnyException();
  }

  @Test
  void shouldExecuteWhenExecutorIsASpringBean() throws Exception {
    CamundaExecutor camundaExecutor = createCamundaExecutor();
    DelegateExecution execution = createMockExecution(TestGetUserExecutor.class);
    when(applicationContext.getBean(TestGetUserExecutor.class)).thenReturn(new TestGetUserExecutor());

    assertThatCode(() -> camundaExecutor.execute(execution)).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowBpmnErrorAndLogErrorVariablesWhenExecutorFails() throws Exception {
    CamundaExecutor camundaExecutor = createCamundaExecutor();
    DelegateExecution execution = createMockExecution(FailingGetUserExecutor.class);
    when(applicationContext.getBean(FailingGetUserExecutor.class))
        .thenThrow(new NoSuchBeanDefinitionException(FailingGetUserExecutor.class));

    ProcessEngineServices processEngineServices = mock(ProcessEngineServices.class);
    RuntimeService runtimeService = mock(RuntimeService.class);
    when(execution.getProcessEngineServices()).thenReturn(processEngineServices);
    when(processEngineServices.getRuntimeService()).thenReturn(runtimeService);
    when(execution.getId()).thenReturn("execution-id-1");

    assertThatThrownBy(() -> camundaExecutor.execute(execution))
        .isInstanceOf(BpmnError.class);
  }
}
