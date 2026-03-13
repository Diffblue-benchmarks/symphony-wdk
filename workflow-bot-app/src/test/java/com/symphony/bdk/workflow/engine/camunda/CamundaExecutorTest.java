package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.ResourceProvider;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaExecutorTest {

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private SharedDataStore sharedDataStore;

  @Mock
  private SecretKeeper secretKeeper;

  @Mock
  private AuditTrailLogAction auditTrailLogger;

  @Mock
  private ResourceProvider resourceProvider;

  @Mock
  private ApplicationContext applicationContext;

  @Mock
  private DelegateExecution execution;

  @Mock
  private ActivityExecutor<TestActivity> activityExecutor;

  @Mock
  private ProcessEngineServices processEngineServices;

  @Mock
  private RuntimeService runtimeService;

  private CamundaExecutor camundaExecutor;

  @BeforeEach
  void setUp() {
    camundaExecutor = new CamundaExecutor(bdkGateway, sharedDataStore, secretKeeper,
        auditTrailLogger, resourceProvider, applicationContext);
  }

  @Test
  void shouldInitializeCamundaExecutorWithAllDependencies() {
    assertThat(camundaExecutor).isNotNull();
  }

  @Test
  void shouldExecuteActivityWhenExecutorIsABean() throws Exception {
    String executorClass = "com.symphony.bdk.workflow.engine.camunda.CamundaExecutorTest$TestActivityExecutor";
    Map<String, Object> serializedActivity = new HashMap<>();
    serializedActivity.put("testActivity", "{\"id\":\"activity1\"}");
    EventHolder<?> eventHolder = new EventHolder<>();

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass);
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serializedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn("testActivity");
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(eventHolder);
    when(execution.getProcessInstanceId()).thenReturn("process-123");
    when(execution.getActivityInstanceId()).thenReturn("activity-456");
    doReturn(activityExecutor).when(applicationContext).getBean(Class.forName(executorClass));

    camundaExecutor.execute(execution);

    verify(applicationContext).getBean(Class.forName(executorClass));
    verify(auditTrailLogger).execute(eq(execution), eq("TestActivity"));
    verify(activityExecutor).execute(any(ActivityExecutorContext.class));
    assertThat(MDC.get("X-PROCESS-ID")).isNull();
    assertThat(MDC.get("X-ACTIVITY-ID")).isNull();
  }

  @Test
  void shouldExecuteActivityWhenExecutorIsNotABean() throws Exception {
    String executorClass = "com.symphony.bdk.workflow.engine.camunda.CamundaExecutorTest$TestActivityExecutor";
    Map<String, Object> serializedActivity = new HashMap<>();
    serializedActivity.put("testActivity", "{\"id\":\"activity1\"}");
    EventHolder<?> eventHolder = new EventHolder<>();

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass);
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serializedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn("testActivity");
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(eventHolder);
    when(execution.getProcessInstanceId()).thenReturn("process-123");
    when(execution.getActivityInstanceId()).thenReturn("activity-456");
    doThrow(new NoSuchBeanDefinitionException("Not found"))
        .when(applicationContext).getBean(Class.forName(executorClass));

    camundaExecutor.execute(execution);

    verify(applicationContext).getBean(Class.forName(executorClass));
    verify(auditTrailLogger).execute(eq(execution), eq("TestActivity"));
    assertThat(MDC.get("X-PROCESS-ID")).isNull();
    assertThat(MDC.get("X-ACTIVITY-ID")).isNull();
  }

  @Test
  void shouldThrowBpmnErrorWhenActivityExecutionFails() throws Exception {
    String executorClass = "com.symphony.bdk.workflow.engine.camunda.CamundaExecutorTest$TestActivityExecutor";
    Map<String, Object> serializedActivity = new HashMap<>();
    serializedActivity.put("testActivity", "{\"id\":\"activity1\"}");
    EventHolder<?> eventHolder = new EventHolder<>();
    RuntimeException executionException = new RuntimeException("Execution failed");

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass);
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serializedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn("testActivity");
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(eventHolder);
    when(execution.getProcessInstanceId()).thenReturn("process-123");
    when(execution.getActivityInstanceId()).thenReturn("activity-456");
    when(execution.getProcessDefinitionId()).thenReturn("process-def-1");
    when(execution.getId()).thenReturn("exec-1");
    when(execution.getProcessEngineServices()).thenReturn(processEngineServices);
    when(processEngineServices.getRuntimeService()).thenReturn(runtimeService);
    doReturn(activityExecutor).when(applicationContext).getBean(Class.forName(executorClass));
    doThrow(executionException).when(activityExecutor).execute(any(ActivityExecutorContext.class));

    assertThatThrownBy(() -> camundaExecutor.execute(execution))
        .isInstanceOf(BpmnError.class)
        .hasFieldOrPropertyWithValue("errorCode", "FAILURE")
        .hasCause(executionException);

    verify(runtimeService).setVariable(eq("exec-1"), eq(ActivityExecutorContext.ERROR), any());
    assertThat(MDC.get("X-PROCESS-ID")).isNull();
    assertThat(MDC.get("X-ACTIVITY-ID")).isNull();
  }

  @Test
  void shouldLogErrorVariablesWhenActivityFails() throws Exception {
    String executorClass = "com.symphony.bdk.workflow.engine.camunda.CamundaExecutorTest$TestActivityExecutor";
    Map<String, Object> serializedActivity = new HashMap<>();
    serializedActivity.put("testActivity", "{\"id\":\"activity1\"}");
    EventHolder<?> eventHolder = new EventHolder<>();
    RuntimeException cause = new RuntimeException("Root cause");
    RuntimeException executionException = new RuntimeException("Execution failed", cause);

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass);
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serializedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn("testActivity");
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(eventHolder);
    when(execution.getProcessInstanceId()).thenReturn("process-123");
    when(execution.getActivityInstanceId()).thenReturn("activity-456");
    when(execution.getProcessDefinitionId()).thenReturn("process-def-1");
    when(execution.getId()).thenReturn("exec-1");
    when(execution.getProcessEngineServices()).thenReturn(processEngineServices);
    when(processEngineServices.getRuntimeService()).thenReturn(runtimeService);
    doReturn(activityExecutor).when(applicationContext).getBean(Class.forName(executorClass));
    doThrow(executionException).when(activityExecutor).execute(any(ActivityExecutorContext.class));

    assertThatThrownBy(() -> camundaExecutor.execute(execution))
        .isInstanceOf(BpmnError.class);

    ArgumentCaptor<Object> errorCaptor = ArgumentCaptor.forClass(Object.class);
    verify(runtimeService).setVariable(eq("exec-1"), eq(ActivityExecutorContext.ERROR), errorCaptor.capture());
  }

  @Test
  void shouldLogErrorVariablesWithNoCauseWhenActivityFails() throws Exception {
    String executorClass = "com.symphony.bdk.workflow.engine.camunda.CamundaExecutorTest$TestActivityExecutor";
    Map<String, Object> serializedActivity = new HashMap<>();
    serializedActivity.put("testActivity", "{\"id\":\"activity1\"}");
    EventHolder<?> eventHolder = new EventHolder<>();
    RuntimeException executionException = new RuntimeException("Execution failed");

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass);
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serializedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn("testActivity");
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(eventHolder);
    when(execution.getProcessInstanceId()).thenReturn("process-123");
    when(execution.getActivityInstanceId()).thenReturn("activity-456");
    when(execution.getProcessDefinitionId()).thenReturn("process-def-1");
    when(execution.getId()).thenReturn("exec-1");
    when(execution.getProcessEngineServices()).thenReturn(processEngineServices);
    when(processEngineServices.getRuntimeService()).thenReturn(runtimeService);
    doReturn(activityExecutor).when(applicationContext).getBean(Class.forName(executorClass));
    doThrow(executionException).when(activityExecutor).execute(any(ActivityExecutorContext.class));

    assertThatThrownBy(() -> camundaExecutor.execute(execution))
        .isInstanceOf(BpmnError.class);

    verify(runtimeService).setVariable(eq("exec-1"), eq(ActivityExecutorContext.ERROR), any());
  }

  @Test
  void shouldSetAndClearMdcDuringExecution() throws Exception {
    String executorClass = "com.symphony.bdk.workflow.engine.camunda.CamundaExecutorTest$TestActivityExecutor";
    Map<String, Object> serializedActivity = new HashMap<>();
    serializedActivity.put("testActivity", "{\"id\":\"activity1\"}");
    EventHolder<?> eventHolder = new EventHolder<>();

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass);
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serializedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn("testActivity");
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(eventHolder);
    when(execution.getProcessInstanceId()).thenReturn("process-123");
    when(execution.getActivityInstanceId()).thenReturn("activity-456");
    doReturn(activityExecutor).when(applicationContext).getBean(Class.forName(executorClass));

    camundaExecutor.execute(execution);

    assertThat(MDC.get("X-PROCESS-ID")).isNull();
    assertThat(MDC.get("X-ACTIVITY-ID")).isNull();
  }

  @Test
  void shouldClearMdcEvenWhenExecutionFails() throws Exception {
    String executorClass = "com.symphony.bdk.workflow.engine.camunda.CamundaExecutorTest$TestActivityExecutor";
    Map<String, Object> serializedActivity = new HashMap<>();
    serializedActivity.put("testActivity", "{\"id\":\"activity1\"}");
    EventHolder<?> eventHolder = new EventHolder<>();

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass);
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serializedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn("testActivity");
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(eventHolder);
    when(execution.getProcessInstanceId()).thenReturn("process-123");
    when(execution.getActivityInstanceId()).thenReturn("activity-456");
    when(execution.getProcessDefinitionId()).thenReturn("process-def-1");
    when(execution.getId()).thenReturn("exec-1");
    when(execution.getProcessEngineServices()).thenReturn(processEngineServices);
    when(processEngineServices.getRuntimeService()).thenReturn(runtimeService);
    doReturn(activityExecutor).when(applicationContext).getBean(Class.forName(executorClass));
    doThrow(new RuntimeException("Test exception")).when(activityExecutor).execute(any(ActivityExecutorContext.class));

    assertThatThrownBy(() -> camundaExecutor.execute(execution))
        .isInstanceOf(BpmnError.class);

    assertThat(MDC.get("X-PROCESS-ID")).isNull();
    assertThat(MDC.get("X-ACTIVITY-ID")).isNull();
  }

  public static class TestActivity extends BaseActivity {
  }

  public static class TestActivityExecutor implements ActivityExecutor<TestActivity> {
    @Override
    public void execute(ActivityExecutorContext<TestActivity> context) {
      // Test implementation
    }
  }
}
