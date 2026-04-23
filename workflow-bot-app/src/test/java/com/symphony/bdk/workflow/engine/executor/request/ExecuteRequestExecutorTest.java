package com.symphony.bdk.workflow.engine.executor.request;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.request.client.HttpClient;
import com.symphony.bdk.workflow.engine.executor.request.client.Response;
import com.symphony.bdk.workflow.swadl.v1.activity.request.ExecuteRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExecuteRequestExecutorTest {

  @InjectMocks
  private ExecuteRequestExecutor executor;

  @Mock
  private HttpClient httpClient;

  @Mock
  private ActivityExecutorContext<ExecuteRequest> context;

  @Test
  void shouldExecuteRequestWithEncodeQueryParamsTrue() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setMethod("GET");
    activity.setUrl("http://example.com/api?q=hello world");
    activity.setEncodeQueryParams(true);
    activity.setHeaders(Collections.emptyMap());
    Response response = new Response(200, "ok");

    when(context.getActivity()).thenReturn(activity);
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    executor.execute(context);

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs.get("status")).isEqualTo(200);
    assertThat(outputs.get("body")).isEqualTo("ok");
  }

  @Test
  void shouldExecuteRequestWithEncodeQueryParamsFalse() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setMethod("POST");
    activity.setUrl("http://example.com/api");
    activity.setEncodeQueryParams(false);
    activity.setHeaders(Collections.emptyMap());
    Response response = new Response(201, "created");

    when(context.getActivity()).thenReturn(activity);
    when(httpClient.execute(eq("POST"), eq("http://example.com/api"), any(), anyMap())).thenReturn(response);

    executor.execute(context);

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs.get("status")).isEqualTo(201);
    assertThat(outputs.get("body")).isEqualTo("created");
  }

  @Test
  void shouldConvertStringHeaderValuesToString() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setMethod("GET");
    activity.setUrl("http://example.com/api");
    activity.setEncodeQueryParams(false);
    Map<String, Object> headers = new HashMap<>();
    headers.put("Authorization", "Bearer token");
    activity.setHeaders(headers);
    Response response = new Response(200, "body");

    when(context.getActivity()).thenReturn(activity);
    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    when(httpClient.execute(anyString(), anyString(), any(), headersCaptor.capture())).thenReturn(response);

    executor.execute(context);

    Map<String, String> capturedHeaders = headersCaptor.getValue();
    assertThat(capturedHeaders).containsEntry("Authorization", "Bearer token");
  }

  @Test
  void shouldJoinListHeaderValuesWithComma() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setMethod("GET");
    activity.setUrl("http://example.com/api");
    activity.setEncodeQueryParams(false);
    Map<String, Object> headers = new HashMap<>();
    headers.put("Accept", Arrays.asList("application/json", "text/plain"));
    activity.setHeaders(headers);
    Response response = new Response(200, "body");

    when(context.getActivity()).thenReturn(activity);
    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    when(httpClient.execute(anyString(), anyString(), any(), headersCaptor.capture())).thenReturn(response);

    executor.execute(context);

    Map<String, String> capturedHeaders = headersCaptor.getValue();
    assertThat(capturedHeaders).containsEntry("Accept", "application/json,text/plain");
  }

  @Test
  void shouldHandleResponseBodyAsObject() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setMethod("GET");
    activity.setUrl("http://example.com/api");
    activity.setEncodeQueryParams(false);
    activity.setHeaders(Collections.emptyMap());
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("key", "value");
    Response response = new Response(200, responseBody);

    when(context.getActivity()).thenReturn(activity);
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    executor.execute(context);

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs.get("body")).isEqualTo(responseBody);
  }
}
