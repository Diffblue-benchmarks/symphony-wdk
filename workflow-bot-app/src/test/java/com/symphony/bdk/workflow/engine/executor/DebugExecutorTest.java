package com.symphony.bdk.workflow.engine.executor;

import com.symphony.bdk.workflow.swadl.v1.activity.Debug;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DebugExecutorTest {

  @Test
  void executeShouldHandleValidJsonObject() throws Exception {
    ActivityExecutorContext<Debug> context = mock(ActivityExecutorContext.class);
    Debug activity = mock(Debug.class);
    String validJson = "{\"key\":\"value\",\"number\":123}";

    when(context.getActivity()).thenReturn(activity);
    when(activity.getObject()).thenReturn(validJson);

    DebugExecutor executor = new DebugExecutor();
    executor.execute(context);

    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void executeShouldHandleInvalidJsonObject() throws Exception {
    ActivityExecutorContext<Debug> context = mock(ActivityExecutorContext.class);
    Debug activity = mock(Debug.class);
    String invalidJson = "not a valid json";

    when(context.getActivity()).thenReturn(activity);
    when(activity.getObject()).thenReturn(invalidJson);

    DebugExecutor executor = new DebugExecutor();
    executor.execute(context);

    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void executeShouldHandleComplexJsonObject() throws Exception {
    ActivityExecutorContext<Debug> context = mock(ActivityExecutorContext.class);
    Debug activity = mock(Debug.class);
    String complexJson = "{\"nested\":{\"array\":[1,2,3],\"bool\":true}}";

    when(context.getActivity()).thenReturn(activity);
    when(activity.getObject()).thenReturn(complexJson);

    DebugExecutor executor = new DebugExecutor();
    executor.execute(context);

    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void executeShouldHandleNonStringObject() throws Exception {
    ActivityExecutorContext<Debug> context = mock(ActivityExecutorContext.class);
    Debug activity = mock(Debug.class);
    Object nonStringObject = new Object();

    when(context.getActivity()).thenReturn(activity);
    when(activity.getObject()).thenReturn(nonStringObject);

    DebugExecutor executor = new DebugExecutor();
    executor.execute(context);

    verify(context).setOutputVariables(any(Map.class));
  }
}
