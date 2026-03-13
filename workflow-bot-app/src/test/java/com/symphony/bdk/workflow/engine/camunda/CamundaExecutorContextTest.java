package com.symphony.bdk.workflow.engine.camunda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.engine.ResourceProvider;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CamundaExecutorContextTest {

  private DelegateExecution execution;
  private BaseActivity activity;
  private EventHolder<Object> event;
  private ResourceProvider resourceProvider;
  private BdkGateway bdkGateway;
  private SharedDataStore sharedDataStore;
  private SecretKeeper secretKeeper;
  private Class<?> contextClass;
  private Constructor<?> contextConstructor;

  @BeforeEach
  void setUp() throws Exception {
    execution = mock(DelegateExecution.class);
    activity = mock(BaseActivity.class);
    event = mock(EventHolder.class);
    resourceProvider = mock(ResourceProvider.class);
    bdkGateway = mock(BdkGateway.class);
    sharedDataStore = mock(SharedDataStore.class);
    secretKeeper = mock(SecretKeeper.class);

    // Get the private inner class using reflection
    Class<?>[] innerClasses = CamundaExecutor.class.getDeclaredClasses();
    for (Class<?> innerClass : innerClasses) {
      if (innerClass.getSimpleName().equals("CamundaActivityExecutorContext")) {
        contextClass = innerClass;
        break;
      }
    }

    // Get the constructor
    contextConstructor = contextClass.getDeclaredConstructor(
        DelegateExecution.class,
        BaseActivity.class,
        EventHolder.class,
        ResourceProvider.class,
        BdkGateway.class,
        SharedDataStore.class,
        SecretKeeper.class);
    contextConstructor.setAccessible(true);
  }

  private Object createContext() throws Exception {
    return contextConstructor.newInstance(
        execution, activity, event, resourceProvider, bdkGateway, sharedDataStore, secretKeeper);
  }

  @Test
  void shouldInitializeAllFieldsWhenConstructed() throws Exception {
    Object context = createContext();

    assertThat(context).isNotNull();

    Method getActivity = contextClass.getMethod("getActivity");
    Method getEvent = contextClass.getMethod("getEvent");
    Method bdk = contextClass.getMethod("bdk");
    Method sharedDataStoreMethod = contextClass.getMethod("sharedDataStore");
    Method secretKeeperMethod = contextClass.getMethod("secretKeeper");

    assertThat(getActivity.invoke(context)).isEqualTo(activity);
    assertThat(getEvent.invoke(context)).isEqualTo(event);
    assertThat(bdk.invoke(context)).isEqualTo(bdkGateway);
    assertThat(sharedDataStoreMethod.invoke(context)).isEqualTo(sharedDataStore);
    assertThat(secretKeeperMethod.invoke(context)).isEqualTo(secretKeeper);
  }

  @Test
  void shouldReturnBdkGatewayWhenBdkCalled() throws Exception {
    Object context = createContext();
    Method bdk = contextClass.getMethod("bdk");

    BdkGateway result = (BdkGateway) bdk.invoke(context);

    assertThat(result).isSameAs(bdkGateway);
  }

  @Test
  void shouldReturnSharedDataStoreWhenSharedDataStoreCalled() throws Exception {
    Object context = createContext();
    Method sharedDataStoreMethod = contextClass.getMethod("sharedDataStore");

    SharedDataStore result = (SharedDataStore) sharedDataStoreMethod.invoke(context);

    assertThat(result).isSameAs(sharedDataStore);
  }

  @Test
  void shouldReturnSecretKeeperWhenSecretKeeperCalled() throws Exception {
    Object context = createContext();
    Method secretKeeperMethod = contextClass.getMethod("secretKeeper");

    SecretKeeper result = (SecretKeeper) secretKeeperMethod.invoke(context);

    assertThat(result).isSameAs(secretKeeper);
  }

  @Test
  void shouldReturnActivityWhenGetActivityCalled() throws Exception {
    Object context = createContext();
    Method getActivity = contextClass.getMethod("getActivity");

    BaseActivity result = (BaseActivity) getActivity.invoke(context);

    assertThat(result).isSameAs(activity);
  }

  @Test
  void shouldReturnEventWhenGetEventCalled() throws Exception {
    Object context = createContext();
    Method getEvent = contextClass.getMethod("getEvent");

    EventHolder<?> result = (EventHolder<?>) getEvent.invoke(context);

    assertThat(result).isSameAs(event);
  }

  @Test
  void shouldReturnProcessInstanceIdWhenGetProcessInstanceIdCalled() throws Exception {
    when(execution.getProcessInstanceId()).thenReturn("process-123");

    Object context = createContext();
    Method getProcessInstanceId = contextClass.getMethod("getProcessInstanceId");

    String result = (String) getProcessInstanceId.invoke(context);

    assertThat(result).isEqualTo("process-123");
    verify(execution).getProcessInstanceId();
  }

  @Test
  void shouldReturnCurrentActivityIdWhenGetCurrentActivityIdCalled() throws Exception {
    when(execution.getCurrentActivityId()).thenReturn("activity-456");

    Object context = createContext();
    Method getCurrentActivityId = contextClass.getMethod("getCurrentActivityId");

    String result = (String) getCurrentActivityId.invoke(context);

    assertThat(result).isEqualTo("activity-456");
    verify(execution).getCurrentActivityId();
  }

  @Test
  void shouldReturnVariablesCopyWhenGetVariablesCalled() throws Exception {
    Map<String, Object> variables = new HashMap<>();
    variables.put("key1", "value1");
    variables.put("key2", "value2");
    when(execution.getVariables()).thenReturn(variables);

    Object context = createContext();
    Method getVariables = contextClass.getMethod("getVariables");

    @SuppressWarnings("unchecked")
    Map<String, Object> result = (Map<String, Object>) getVariables.invoke(context);

    assertThat(result).containsEntry("key1", "value1");
    assertThat(result).containsEntry("key2", "value2");
    verify(execution).getVariables();
  }

  @Test
  void shouldSetOutputVariablesWithSerializableValues() throws Exception {
    when(activity.getId()).thenReturn("test-activity");

    Object context = createContext();
    Method setOutputVariables = contextClass.getMethod("setOutputVariables", Map.class);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("output1", "value1");
    outputs.put("output2", 123);

    setOutputVariables.invoke(context, outputs);

    verify(execution).setVariable(eq("test-activity"), any(ObjectValue.class));
    verify(execution).setVariable(eq("test-activity.outputs.output1"), eq("value1"));
    verify(execution).setVariable(eq("test-activity.outputs.output2"), eq(123));
  }

  @Test
  void shouldSetOutputVariablesWithCollectionAsObjectValue() throws Exception {
    when(activity.getId()).thenReturn("test-activity");

    Object context = createContext();
    Method setOutputVariables = contextClass.getMethod("setOutputVariables", Map.class);

    Map<String, Object> outputs = new HashMap<>();
    List<String> collection = new ArrayList<>();
    collection.add("item1");
    collection.add("item2");
    outputs.put("list", collection);

    setOutputVariables.invoke(context, outputs);

    verify(execution).setVariable(eq("test-activity"), any(ObjectValue.class));
    ArgumentCaptor<ObjectValue> captor = ArgumentCaptor.forClass(ObjectValue.class);
    verify(execution).setVariable(eq("test-activity.outputs.list"), captor.capture());
    assertThat(captor.getValue()).isNotNull();
  }

  @Test
  void shouldSetOutputVariablesWithNonSerializableValuesAsObjectValue() throws Exception {
    when(activity.getId()).thenReturn("test-activity");

    Object context = createContext();
    Method setOutputVariables = contextClass.getMethod("setOutputVariables", Map.class);

    Map<String, Object> outputs = new HashMap<>();
    NonSerializableClass nonSerializable = new NonSerializableClass();
    outputs.put("nonSerializable", nonSerializable);

    setOutputVariables.invoke(context, outputs);

    verify(execution).setVariable(eq("test-activity"), any(ObjectValue.class));
    ArgumentCaptor<ObjectValue> captor = ArgumentCaptor.forClass(ObjectValue.class);
    verify(execution).setVariable(eq("test-activity.outputs.nonSerializable"), captor.capture());
    assertThat(captor.getValue()).isNotNull();
  }

  @Test
  void shouldSetOutputVariableWithSingleValue() throws Exception {
    when(activity.getId()).thenReturn("test-activity");

    Object context = createContext();
    Method setOutputVariable = contextClass.getMethod("setOutputVariable", String.class, Object.class);

    setOutputVariable.invoke(context, "singleOutput", "singleValue");

    verify(execution).setVariable(eq("test-activity"), any(ObjectValue.class));
    verify(execution).setVariable(eq("test-activity.outputs.singleOutput"), eq("singleValue"));
  }

  @Test
  void shouldGetResourceFromResourceProvider() throws Exception {
    Path resourcePath = Path.of("test/resource.txt");
    InputStream expectedStream = new ByteArrayInputStream("test content".getBytes());
    when(resourceProvider.getResource(resourcePath)).thenReturn(expectedStream);

    Object context = createContext();
    Method getResource = contextClass.getMethod("getResource", Path.class);

    InputStream result = (InputStream) getResource.invoke(context, resourcePath);

    assertThat(result).isSameAs(expectedStream);
    verify(resourceProvider).getResource(resourcePath);
  }

  @Test
  void shouldGetResourceFileFromResourceProvider() throws Exception {
    Path resourcePath = Path.of("test/resource.txt");
    File expectedFile = new File("/tmp/resource.txt");
    when(resourceProvider.getResourceFile(resourcePath)).thenReturn(expectedFile);

    Object context = createContext();
    Method getResourceFile = contextClass.getMethod("getResourceFile", Path.class);

    File result = (File) getResourceFile.invoke(context, resourcePath);

    assertThat(result).isSameAs(expectedFile);
    verify(resourceProvider).getResourceFile(resourcePath);
  }

  @Test
  void shouldSaveResourceToResourceProvider() throws Exception {
    Path resourcePath = Path.of("test/resource.txt");
    byte[] content = "test content".getBytes();
    Path expectedPath = Path.of("/tmp/saved/resource.txt");
    when(resourceProvider.saveResource(resourcePath, content)).thenReturn(expectedPath);

    Object context = createContext();
    Method saveResource = contextClass.getMethod("saveResource", Path.class, byte[].class);

    Path result = (Path) saveResource.invoke(context, resourcePath, content);

    assertThat(result).isSameAs(expectedPath);
    verify(resourceProvider).saveResource(resourcePath, content);
  }

  private static class NonSerializableClass {
    private String data = "test";
  }
}
