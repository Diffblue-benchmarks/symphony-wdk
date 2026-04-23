package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.ResourceProvider;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaActivityExecutorContextTest {

  @Mock
  private DelegateExecution execution;

  @Mock
  private BaseActivity activity;

  @Mock
  private EventHolder<Object> event;

  @Mock
  private ResourceProvider resourceProvider;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private SharedDataStore sharedDataStore;

  @Mock
  private SecretKeeper secretKeeper;

  @SuppressWarnings("unchecked")
  private ActivityExecutorContext<BaseActivity> context;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() throws Exception {
    Class<?> innerClass =
        Class.forName("com.symphony.bdk.workflow.engine.camunda.CamundaExecutor$CamundaActivityExecutorContext");
    Constructor<?> constructor = innerClass.getDeclaredConstructor(
        DelegateExecution.class, BaseActivity.class, EventHolder.class,
        ResourceProvider.class, BdkGateway.class, SharedDataStore.class, SecretKeeper.class);
    constructor.setAccessible(true);
    context = (ActivityExecutorContext<BaseActivity>) constructor.newInstance(
        execution, activity, event, resourceProvider, bdkGateway, sharedDataStore, secretKeeper);
  }

  @Test
  void shouldReturnBdkGateway() {
    // when
    BdkGateway result = context.bdk();

    // then
    assertThat(result).isSameAs(bdkGateway);
  }

  @Test
  void shouldReturnSharedDataStore() {
    // when
    SharedDataStore result = context.sharedDataStore();

    // then
    assertThat(result).isSameAs(sharedDataStore);
  }

  @Test
  void shouldReturnSecretKeeper() {
    // when
    SecretKeeper result = context.secretKeeper();

    // then
    assertThat(result).isSameAs(secretKeeper);
  }

  @Test
  void shouldReturnActivity() {
    // when
    BaseActivity result = context.getActivity();

    // then
    assertThat(result).isSameAs(activity);
  }

  @Test
  void shouldReturnEvent() {
    // when
    EventHolder<Object> result = context.getEvent();

    // then
    assertThat(result).isSameAs(event);
  }

  @Test
  void shouldReturnProcessInstanceId() {
    // given
    when(execution.getProcessInstanceId()).thenReturn("proc-123");

    // when
    String result = context.getProcessInstanceId();

    // then
    assertThat(result).isEqualTo("proc-123");
  }

  @Test
  void shouldReturnCurrentActivityId() {
    // given
    when(execution.getCurrentActivityId()).thenReturn("activity-abc");

    // when
    String result = context.getCurrentActivityId();

    // then
    assertThat(result).isEqualTo("activity-abc");
  }

  @Test
  void shouldReturnVariables() {
    // given
    Map<String, Object> variables = new HashMap<>();
    variables.put("key1", "value1");
    variables.put("key2", 42);
    when(execution.getVariables()).thenReturn(variables);

    // when
    Map<String, Object> result = context.getVariables();

    // then
    assertThat(result).containsEntry("key1", "value1").containsEntry("key2", 42);
  }

  @Test
  void shouldSetOutputVariablesWithSerializableValues() {
    // given
    when(activity.getId()).thenReturn("myActivity");
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("result", "someString");
    outputs.put("count", 5L);

    // when
    context.setOutputVariables(outputs);

    // then: 1 call for the aggregated output + 1 per flattened output key (2 keys)
    verify(execution, times(3)).setVariable(anyString(), any());
  }

  @Test
  void shouldSetOutputVariablesWithNonSerializableValue() {
    // given
    when(activity.getId()).thenReturn("myActivity");
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("items", List.of("a", "b"));

    // when
    context.setOutputVariables(outputs);

    // then: 1 call for the aggregated output + 1 per flattened output key (1 key)
    verify(execution, times(2)).setVariable(anyString(), any());
  }

  @Test
  void shouldSetOutputVariable() {
    // given
    when(activity.getId()).thenReturn("myActivity");

    // when
    context.setOutputVariable("myKey", "myValue");

    // then: 1 call for the aggregated output + 1 for the single flattened key
    verify(execution, times(2)).setVariable(anyString(), any());
  }

  @Test
  void shouldGetResource() throws IOException {
    // given
    Path resourcePath = Path.of("some/resource.txt");
    InputStream expectedStream = mock(InputStream.class);
    when(resourceProvider.getResource(resourcePath)).thenReturn(expectedStream);

    // when
    InputStream result = context.getResource(resourcePath);

    // then
    assertThat(result).isSameAs(expectedStream);
  }

  @Test
  void shouldGetResourceFile() throws IOException {
    // given
    Path resourcePath = Path.of("some/resource.txt");
    File expectedFile = mock(File.class);
    when(resourceProvider.getResourceFile(resourcePath)).thenReturn(expectedFile);

    // when
    File result = context.getResourceFile(resourcePath);

    // then
    assertThat(result).isSameAs(expectedFile);
  }

  @Test
  void shouldSaveResource() throws IOException {
    // given
    Path resourcePath = Path.of("some/resource.txt");
    byte[] content = "data".getBytes();
    Path savedPath = Path.of("some/saved.txt");
    when(resourceProvider.saveResource(resourcePath, content)).thenReturn(savedPath);

    // when
    Path result = context.saveResource(resourcePath, content);

    // then
    assertThat(result).isEqualTo(savedPath);
  }
}
