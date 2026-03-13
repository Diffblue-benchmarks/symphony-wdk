package com.symphony.bdk.workflow.engine.executor.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.request.client.HttpClient;
import com.symphony.bdk.workflow.engine.executor.request.client.Response;
import com.symphony.bdk.workflow.swadl.v1.activity.request.ExecuteRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ExecuteRequestExecutorTest {

  private HttpClient httpClient;
  private ExecuteRequestExecutor executor;
  private ActivityExecutorContext<ExecuteRequest> context;

  @BeforeEach
  void setUp() {
    httpClient = mock(HttpClient.class);
    executor = new ExecuteRequestExecutor(httpClient);
    context = mock(ActivityExecutorContext.class);
  }

  @Test
  void constructorShouldInitializeHttpClient() {
    ExecuteRequestExecutor newExecutor = new ExecuteRequestExecutor(httpClient);
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void executeShouldHandleRequestWithoutEncodingQueryParams() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setUrl("http://example.com/api?key=value");
    activity.setMethod("GET");
    activity.setBody(null);
    activity.setEncodeQueryParams(false);
    Map<String, Object> headers = new HashMap<>();
    activity.setHeaders(headers);

    Response mockResponse = new Response(200, "success");
    when(context.getActivity()).thenReturn(activity);
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(mockResponse);

    executor.execute(context);

    verify(httpClient, times(1)).execute(eq("GET"), eq("http://example.com/api?key=value"), eq(null), anyMap());

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("status", 200);
    assertThat(outputs).containsEntry("body", "success");
  }

  @Test
  void executeShouldHandleRequestWithEncodingQueryParams() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setUrl("http://example.com/api?key=value with spaces");
    activity.setMethod("POST");
    activity.setBody("{\"data\":\"test\"}");
    activity.setEncodeQueryParams(true);
    Map<String, Object> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    activity.setHeaders(headers);

    Response mockResponse = new Response(201, Map.of("result", "created"));
    when(context.getActivity()).thenReturn(activity);
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(mockResponse);

    executor.execute(context);

    ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
    verify(httpClient, times(1)).execute(eq("POST"), urlCaptor.capture(), eq("{\"data\":\"test\"}"), anyMap());

    String capturedUrl = urlCaptor.getValue();
    assertThat(capturedUrl).contains("http://example.com/api");

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsKey("status");
    assertThat(outputs).containsKey("body");
  }

  @Test
  void executeShouldHandleRequestWithListHeaders() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setUrl("http://example.com/api");
    activity.setMethod("GET");
    activity.setBody(null);
    activity.setEncodeQueryParams(false);

    Map<String, Object> headers = new HashMap<>();
    List<String> acceptValues = new ArrayList<>();
    acceptValues.add("application/json");
    acceptValues.add("text/plain");
    headers.put("Accept", acceptValues);
    headers.put("Authorization", "Bearer token123");
    activity.setHeaders(headers);

    Response mockResponse = new Response(200, "data");
    when(context.getActivity()).thenReturn(activity);
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(mockResponse);

    executor.execute(context);

    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    verify(httpClient, times(1)).execute(eq("GET"), eq("http://example.com/api"), eq(null), headersCaptor.capture());

    Map<String, String> capturedHeaders = headersCaptor.getValue();
    assertThat(capturedHeaders).containsEntry("Accept", "application/json,text/plain");
    assertThat(capturedHeaders).containsEntry("Authorization", "Bearer token123");
  }

  @Test
  void executeShouldHandleEmptyHeaders() throws IOException {
    ExecuteRequest activity = new ExecuteRequest();
    activity.setUrl("http://example.com/test");
    activity.setMethod("DELETE");
    activity.setBody(null);
    activity.setEncodeQueryParams(false);
    activity.setHeaders(new HashMap<>());

    Response mockResponse = new Response(204, null);
    when(context.getActivity()).thenReturn(activity);
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(mockResponse);

    executor.execute(context);

    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    verify(httpClient, times(1)).execute(eq("DELETE"), eq("http://example.com/test"), eq(null), headersCaptor.capture());

    Map<String, String> capturedHeaders = headersCaptor.getValue();
    assertThat(capturedHeaders).isEmpty();

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("status", 204);
    assertThat(outputs).containsEntry("body", null);
  }
}
