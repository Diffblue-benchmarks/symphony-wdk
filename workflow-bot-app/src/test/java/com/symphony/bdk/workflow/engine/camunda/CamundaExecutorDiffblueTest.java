package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.ResourceProvider;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;

import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class CamundaExecutorDiffblueTest {

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

  @InjectMocks
  private CamundaExecutor camundaExecutor;

  /**
   * A minimal {@link ActivityExecutor} with a no-arg constructor used in unit tests.
   */
  public static class NoOpDebugExecutor implements ActivityExecutor<Debug> {
    @Override
    public void execute(ActivityExecutorContext<Debug> context) {
      // no-op
    }
  }

  /**
   * An {@link ActivityExecutor} that always throws a {@link RuntimeException}.
   */
  public static class ThrowingDebugExecutor implements ActivityExecutor<Debug> {
    @Override
    public void execute(ActivityExecutorContext<Debug> context) {
      throw new RuntimeException("executor-failure");
    }
  }

  private DelegateExecution buildExecution(Class<?> executorClass, String activityId,
      String activityJson) {
    DelegateExecution execution = mock(DelegateExecution.class);
    Map<String, Object> serialisedActivity = new HashMap<>();
    serialisedActivity.put(activityId, activityJson);

    when(execution.getVariable(CamundaExecutor.EXECUTOR)).thenReturn(executorClass.getName());
    when(execution.getVariable(CamundaExecutor.SERIALISED_ACTIVITY)).thenReturn(serialisedActivity);
    when(execution.getVariable(CamundaExecutor.ACTIVITY)).thenReturn(activityId);
    when(execution.getVariable(ActivityExecutorContext.EVENT)).thenReturn(new EventHolder<>());
    when(execution.getProcessInstanceId()).thenReturn("proc-instance-1");
    when(execution.getActivityInstanceId()).thenReturn("act-instance-1");
    return execution;
  }

  /**
   * Test {@link CamundaExecutor#CamundaExecutor(BdkGateway, SharedDataStore, SecretKeeper,
   * AuditTrailLogAction, ResourceProvider, ApplicationContext)}.
   *
   * <p>Method under test: constructor
   */
  @Test
  @DisplayName("Test CamundaExecutor constructor sets all dependencies")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaExecutor.<init>(BdkGateway, SharedDataStore, SecretKeeper, AuditTrailLogAction, ResourceProvider, ApplicationContext)"})
  void testConstructor_setsDependencies() {
    // Arrange and Act
    CamundaExecutor executor = new CamundaExecutor(bdk, sharedDataStore, secretKeeper,
        auditTrailLogger, resourceLoader, applicationContext);

    // Assert - constructor did not throw and object was created
    // Fields are private, so we verify via execute behaviour in other tests
    assert executor != null;
  }

  /**
   * Test {@link CamundaExecutor#execute(DelegateExecution)} when executor is not a Spring bean.
   *
   * <p>Method under test: {@link CamundaExecutor#execute(DelegateExecution)}
   */
  @Test
  @DisplayName("Test execute(DelegateExecution) when executor not a bean - creates new instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaExecutor.execute(DelegateExecution)"})
  void testExecute_executorNotABean_createsNewInstance() throws Exception {
    // Arrange
    String activityId = "debug-activity";
    String activityJson = "{\"id\":\"debug-activity\"}";
    DelegateExecution execution = buildExecution(NoOpDebugExecutor.class, activityId, activityJson);

    when(applicationContext.getBean(NoOpDebugExecutor.class))
        .thenThrow(new NoSuchBeanDefinitionException(NoOpDebugExecutor.class));
    doNothing().when(auditTrailLogger).execute(any(DelegateExecution.class), anyString());

    // Act
    camundaExecutor.execute(execution);

    // Assert
    verify(auditTrailLogger).execute(eq(execution), eq("Debug"));
  }

  /**
   * Test {@link CamundaExecutor#execute(DelegateExecution)} when executor is found as a Spring bean.
   *
   * <p>Method under test: {@link CamundaExecutor#execute(DelegateExecution)}
   */
  @Test
  @DisplayName("Test execute(DelegateExecution) when executor is a Spring bean")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaExecutor.execute(DelegateExecution)"})
  void testExecute_executorIsBean_usesBean() throws Exception {
    // Arrange
    String activityId = "debug-activity";
    String activityJson = "{\"id\":\"debug-activity\"}";
    DelegateExecution execution = buildExecution(NoOpDebugExecutor.class, activityId, activityJson);

    NoOpDebugExecutor beanExecutor = new NoOpDebugExecutor();
    when(applicationContext.getBean(NoOpDebugExecutor.class)).thenReturn(beanExecutor);
    doNothing().when(auditTrailLogger).execute(any(DelegateExecution.class), anyString());

    // Act
    camundaExecutor.execute(execution);

    // Assert
    verify(auditTrailLogger).execute(eq(execution), eq("Debug"));
    verify(applicationContext).getBean(NoOpDebugExecutor.class);
  }

  /**
   * Test {@link CamundaExecutor#execute(DelegateExecution)} when executor throws an exception.
   * Verifies that a {@link BpmnError} is thrown and error variables are logged.
   *
   * <p>Method under test: {@link CamundaExecutor#execute(DelegateExecution)}
   */
  @Test
  @DisplayName("Test execute(DelegateExecution) when executor throws - wraps in BpmnError")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaExecutor.execute(DelegateExecution)"})
  void testExecute_executorThrows_throwsBpmnError() throws Exception {
    // Arrange
    String activityId = "debug-activity";
    String activityJson = "{\"id\":\"debug-activity\"}";
    DelegateExecution execution = buildExecution(ThrowingDebugExecutor.class, activityId, activityJson);

    when(applicationContext.getBean(ThrowingDebugExecutor.class))
        .thenThrow(new NoSuchBeanDefinitionException(ThrowingDebugExecutor.class));
    doNothing().when(auditTrailLogger).execute(any(DelegateExecution.class), anyString());

    ProcessEngineServices processEngineServices = mock(ProcessEngineServices.class);
    RuntimeService runtimeService = mock(RuntimeService.class);
    when(execution.getProcessEngineServices()).thenReturn(processEngineServices);
    when(processEngineServices.getRuntimeService()).thenReturn(runtimeService);
    when(execution.getId()).thenReturn("exec-id-1");
    doNothing().when(runtimeService).setVariable(anyString(), anyString(), any());

    // Act and Assert
    assertThrows(BpmnError.class, () -> camundaExecutor.execute(execution));

    verify(runtimeService).setVariable(eq("exec-id-1"), eq(ActivityExecutorContext.ERROR), any());
  }

  /**
   * Test that MDC entries are cleared after {@link CamundaExecutor#execute(DelegateExecution)}
   * completes successfully.
   *
   * <p>Methods under test: {@link CamundaExecutor#execute(DelegateExecution)},
   * clearMdc, setMdc
   */
  @Test
  @DisplayName("Test execute(DelegateExecution) clears MDC after successful execution")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaExecutor.execute(DelegateExecution)"})
  void testExecute_clearsMdcAfterSuccess() throws Exception {
    // Arrange
    String activityId = "debug-activity";
    String activityJson = "{\"id\":\"debug-activity\"}";
    DelegateExecution execution = buildExecution(NoOpDebugExecutor.class, activityId, activityJson);

    when(applicationContext.getBean(NoOpDebugExecutor.class))
        .thenThrow(new NoSuchBeanDefinitionException(NoOpDebugExecutor.class));
    doNothing().when(auditTrailLogger).execute(any(DelegateExecution.class), anyString());

    // Act
    camundaExecutor.execute(execution);

    // Assert - MDC entries should be cleared after execution
    assert MDC.get("X-PROCESS-ID") == null;
    assert MDC.get("X-ACTIVITY-ID") == null;
  }

  /**
   * Test that MDC entries are cleared after {@link CamundaExecutor#execute(DelegateExecution)}
   * throws an exception.
   *
   * <p>Methods under test: {@link CamundaExecutor#execute(DelegateExecution)},
   * clearMdc, setMdc
   */
  @Test
  @DisplayName("Test execute(DelegateExecution) clears MDC after failed execution")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaExecutor.execute(DelegateExecution)"})
  void testExecute_clearsMdcAfterFailure() throws Exception {
    // Arrange
    String activityId = "debug-activity";
    String activityJson = "{\"id\":\"debug-activity\"}";
    DelegateExecution execution = buildExecution(ThrowingDebugExecutor.class, activityId, activityJson);

    when(applicationContext.getBean(ThrowingDebugExecutor.class))
        .thenThrow(new NoSuchBeanDefinitionException(ThrowingDebugExecutor.class));
    doNothing().when(auditTrailLogger).execute(any(DelegateExecution.class), anyString());

    ProcessEngineServices processEngineServices = mock(ProcessEngineServices.class);
    RuntimeService runtimeService = mock(RuntimeService.class);
    when(execution.getProcessEngineServices()).thenReturn(processEngineServices);
    when(processEngineServices.getRuntimeService()).thenReturn(runtimeService);
    when(execution.getId()).thenReturn("exec-id-1");
    doNothing().when(runtimeService).setVariable(anyString(), anyString(), any());

    // Act
    assertThrows(BpmnError.class, () -> camundaExecutor.execute(execution));

    // Assert - MDC entries should be cleared even after failure
    assert MDC.get("X-PROCESS-ID") == null;
    assert MDC.get("X-ACTIVITY-ID") == null;
  }
}
