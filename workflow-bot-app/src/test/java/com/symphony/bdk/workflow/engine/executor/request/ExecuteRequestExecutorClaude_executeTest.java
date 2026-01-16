package com.symphony.bdk.workflow.engine.executor.request;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.request.client.HttpClient;
import com.symphony.bdk.workflow.engine.executor.request.client.Response;
import com.symphony.bdk.workflow.swadl.v1.activity.request.ExecuteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExecuteRequestExecutorClaude_executeTest {

  private ExecuteRequestExecutor executor;
  private ActivityExecutorContext<ExecuteRequest> context;
  private ExecuteRequest activity;
  private HttpClient httpClient;

  @BeforeEach
  void setUp() {
    httpClient = mock(HttpClient.class);
    executor = new ExecuteRequestExecutor(httpClient);
    context = mock(ActivityExecutorContext.class);
    activity = new ExecuteRequest();

    when(context.getActivity()).thenReturn(activity);
  }

  // Basic execution tests

  @Test
  void execute_withSimpleGetRequest_shouldExecuteAndSetOutputs() throws IOException {
    // Given: A simple GET request
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "response body");
    when(httpClient.execute(eq("GET"), eq("https://api.example.com/data"), any(), anyMap()))
        .thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should execute HTTP request and set output variables
    verify(httpClient).execute(eq("GET"), eq("https://api.example.com/data"), any(), anyMap());

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("status", 200);
    assertThat(outputs).containsEntry("body", "response body");
  }

  @Test
  void execute_withPostRequest_shouldExecuteWithCorrectMethod() throws IOException {
    // Given: A POST request
    activity.setMethod("POST");
    activity.setUrl("https://api.example.com/create");
    activity.setBody("{\"name\": \"test\"}");
    activity.setEncodeQueryParams(false);

    Response response = new Response(201, "created");
    when(httpClient.execute(eq("POST"), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should execute POST request with correct method
    verify(httpClient).execute(eq("POST"), eq("https://api.example.com/create"),
        eq("{\"name\": \"test\"}"), anyMap());
  }

  @Test
  void execute_withPutRequest_shouldExecuteWithCorrectMethod() throws IOException {
    // Given: A PUT request
    activity.setMethod("PUT");
    activity.setUrl("https://api.example.com/update/123");
    activity.setBody("{\"name\": \"updated\"}");
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "updated");
    when(httpClient.execute(eq("PUT"), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should execute PUT request
    verify(httpClient).execute(eq("PUT"), eq("https://api.example.com/update/123"),
        eq("{\"name\": \"updated\"}"), anyMap());
  }

  @Test
  void execute_withDeleteRequest_shouldExecuteWithCorrectMethod() throws IOException {
    // Given: A DELETE request
    activity.setMethod("DELETE");
    activity.setUrl("https://api.example.com/delete/123");
    activity.setEncodeQueryParams(false);

    Response response = new Response(204, "");
    when(httpClient.execute(eq("DELETE"), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should execute DELETE request
    verify(httpClient).execute(eq("DELETE"), eq("https://api.example.com/delete/123"),
        any(), anyMap());
  }

  // Tests for query parameter encoding

  @Test
  void execute_withEncodeQueryParamsTrue_shouldEncodeUrl() throws IOException {
    // Given: A request with encodeQueryParams enabled
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/search?q=hello world&filter=test value");
    activity.setEncodeQueryParams(true);

    Response response = new Response(200, "results");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should encode the URL parameters
    ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
    verify(httpClient).execute(eq("GET"), urlCaptor.capture(), any(), anyMap());

    String capturedUrl = urlCaptor.getValue();
    assertThat(capturedUrl).contains("hello+world");
    assertThat(capturedUrl).contains("test+value");
  }

  @Test
  void execute_withEncodeQueryParamsFalse_shouldNotEncodeUrl() throws IOException {
    // Given: A request with encodeQueryParams disabled
    String originalUrl = "https://api.example.com/search?q=hello&filter=test";
    activity.setMethod("GET");
    activity.setUrl(originalUrl);
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "results");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use the original URL without encoding
    verify(httpClient).execute(eq("GET"), eq(originalUrl), any(), anyMap());
  }

  // Tests for headers

  @Test
  void execute_withEmptyHeaders_shouldPassEmptyHeaderMap() throws IOException {
    // Given: A request with empty headers
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setHeaders(Collections.emptyMap());
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "data");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass empty header map
    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    verify(httpClient).execute(eq("GET"), anyString(), any(), headersCaptor.capture());

    assertThat(headersCaptor.getValue()).isEmpty();
  }

  @Test
  void execute_withStringHeaders_shouldConvertToStringMap() throws IOException {
    // Given: A request with string headers
    Map<String, Object> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("Authorization", "Bearer token123");

    activity.setMethod("POST");
    activity.setUrl("https://api.example.com/data");
    activity.setHeaders(headers);
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should convert headers to string map
    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    verify(httpClient).execute(eq("POST"), anyString(), any(), headersCaptor.capture());

    Map<String, String> capturedHeaders = headersCaptor.getValue();
    assertThat(capturedHeaders).containsEntry("Content-Type", "application/json");
    assertThat(capturedHeaders).containsEntry("Authorization", "Bearer token123");
  }

  @Test
  void execute_withListHeaders_shouldJoinWithComma() throws IOException {
    // Given: A request with list-valued headers
    Map<String, Object> headers = new HashMap<>();
    headers.put("Accept", List.of("application/json", "application/xml"));
    headers.put("Cache-Control", List.of("no-cache", "no-store", "must-revalidate"));

    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setHeaders(headers);
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should join list values with comma
    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    verify(httpClient).execute(eq("GET"), anyString(), any(), headersCaptor.capture());

    Map<String, String> capturedHeaders = headersCaptor.getValue();
    assertThat(capturedHeaders.get("Accept")).isEqualTo("application/json,application/xml");
    assertThat(capturedHeaders.get("Cache-Control")).isEqualTo("no-cache,no-store,must-revalidate");
  }

  @Test
  void execute_withMixedHeaders_shouldHandleAllTypes() throws IOException {
    // Given: A request with mixed header types (string and list)
    Map<String, Object> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("Accept", List.of("application/json", "text/plain"));
    headers.put("X-Custom-Header", "custom-value");

    activity.setMethod("POST");
    activity.setUrl("https://api.example.com/data");
    activity.setHeaders(headers);
    activity.setEncodeQueryParams(false);

    Response response = new Response(201, "created");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle all header types correctly
    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    verify(httpClient).execute(eq("POST"), anyString(), any(), headersCaptor.capture());

    Map<String, String> capturedHeaders = headersCaptor.getValue();
    assertThat(capturedHeaders).containsEntry("Content-Type", "application/json");
    assertThat(capturedHeaders.get("Accept")).isEqualTo("application/json,text/plain");
    assertThat(capturedHeaders).containsEntry("X-Custom-Header", "custom-value");
  }

  // Tests for request body

  @Test
  void execute_withStringBody_shouldPassBodyToHttpClient() throws IOException {
    // Given: A request with string body
    String body = "{\"key\": \"value\"}";
    activity.setMethod("POST");
    activity.setUrl("https://api.example.com/data");
    activity.setBody(body);
    activity.setEncodeQueryParams(false);

    Response response = new Response(201, "created");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass body to HTTP client
    verify(httpClient).execute(eq("POST"), anyString(), eq(body), anyMap());
  }

  @Test
  void execute_withMapBody_shouldPassBodyToHttpClient() throws IOException {
    // Given: A request with map body
    Map<String, Object> body = new HashMap<>();
    body.put("name", "test");
    body.put("value", 123);

    activity.setMethod("POST");
    activity.setUrl("https://api.example.com/data");
    activity.setBody(body);
    activity.setEncodeQueryParams(false);

    Response response = new Response(201, "created");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass body to HTTP client
    verify(httpClient).execute(eq("POST"), anyString(), eq(body), anyMap());
  }

  @Test
  void execute_withNullBody_shouldPassNullToHttpClient() throws IOException {
    // Given: A request with null body
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setBody(null);
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass null body to HTTP client
    ArgumentCaptor<Object> bodyCaptor = ArgumentCaptor.forClass(Object.class);
    verify(httpClient).execute(eq("GET"), anyString(), bodyCaptor.capture(), anyMap());

    assertThat(bodyCaptor.getValue()).isNull();
  }

  // Tests for response handling

  @Test
  void execute_with200Response_shouldSetCorrectOutputs() throws IOException {
    // Given: A request that returns 200
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "success data");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set correct output variables
    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("status", 200);
    assertThat(outputs).containsEntry("body", "success data");
  }

  @Test
  void execute_with404Response_shouldSetCorrectOutputs() throws IOException {
    // Given: A request that returns 404
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/notfound");
    activity.setEncodeQueryParams(false);

    Response response = new Response(404, "Not Found");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set correct output variables with 404 status
    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("status", 404);
    assertThat(outputs).containsEntry("body", "Not Found");
  }

  @Test
  void execute_with500Response_shouldSetCorrectOutputs() throws IOException {
    // Given: A request that returns 500
    activity.setMethod("POST");
    activity.setUrl("https://api.example.com/error");
    activity.setEncodeQueryParams(false);

    Response response = new Response(500, "Internal Server Error");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set correct output variables with 500 status
    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("status", 500);
    assertThat(outputs).containsEntry("body", "Internal Server Error");
  }

  @Test
  void execute_withJsonResponseBody_shouldSetJsonObjectAsBody() throws IOException {
    // Given: A request that returns JSON object
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setEncodeQueryParams(false);

    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("id", 123);
    jsonResponse.put("name", "test");

    Response response = new Response(200, jsonResponse);
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set JSON object as body in outputs
    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("status", 200);
    assertThat(outputs.get("body")).isEqualTo(jsonResponse);
  }

  @Test
  void execute_withEmptyResponseBody_shouldSetEmptyBody() throws IOException {
    // Given: A request that returns empty body
    activity.setMethod("DELETE");
    activity.setUrl("https://api.example.com/delete/123");
    activity.setEncodeQueryParams(false);

    Response response = new Response(204, "");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set empty body in outputs
    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("status", 204);
    assertThat(outputs).containsEntry("body", "");
  }

  // Tests for exception handling

  @Test
  void execute_whenHttpClientThrowsIOException_shouldPropagateException() throws IOException {
    // Given: HttpClient throws IOException
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setEncodeQueryParams(false);

    IOException exception = new IOException("Connection timeout");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenThrow(exception);

    // When/Then: Execute should propagate the IOException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IOException.class)
        .hasMessage("Connection timeout");
  }

  @Test
  void execute_whenHttpClientThrowsRuntimeException_shouldPropagateException() throws IOException {
    // Given: HttpClient throws RuntimeException
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setEncodeQueryParams(false);

    RuntimeException exception = new RuntimeException("Invalid request");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenThrow(exception);

    // When/Then: Execute should propagate the RuntimeException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Invalid request");
  }

  // Tests for edge cases

  @Test
  void execute_withVeryLongUrl_shouldHandleCorrectly() throws IOException {
    // Given: A request with very long URL
    String longUrl = "https://api.example.com/data?" + "param=value&".repeat(100);
    activity.setMethod("GET");
    activity.setUrl(longUrl);
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should handle long URL correctly
    verify(httpClient).execute(eq("GET"), eq(longUrl), any(), anyMap());
  }

  @Test
  void execute_withSpecialCharactersInUrl_shouldHandleCorrectly() throws IOException {
    // Given: A request with special characters in URL
    String urlWithSpecialChars = "https://api.example.com/search?q=hello&world#fragment";
    activity.setMethod("GET");
    activity.setUrl(urlWithSpecialChars);
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should handle special characters correctly
    verify(httpClient).execute(eq("GET"), eq(urlWithSpecialChars), any(), anyMap());
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() throws IOException {
    // Given: A valid request setup
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly all times
    verify(httpClient, org.mockito.Mockito.times(3))
        .execute(eq("GET"), eq("https://api.example.com/data"), any(), anyMap());
    verify(context, org.mockito.Mockito.times(3)).setOutputVariables(any());
  }

  @Test
  void execute_withDifferentMethodsCaseInsensitive_shouldPreserveMethodCase() throws IOException {
    // Given: A request with lowercase method
    activity.setMethod("get");
    activity.setUrl("https://api.example.com/data");
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should preserve the method case as provided
    verify(httpClient).execute(eq("get"), anyString(), any(), anyMap());
  }

  @Test
  void execute_withNumericHeaderValues_shouldConvertToString() throws IOException {
    // Given: A request with numeric header values
    Map<String, Object> headers = new HashMap<>();
    headers.put("X-Request-Id", 12345);
    headers.put("X-Retry-Count", 3);

    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/data");
    activity.setHeaders(headers);
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should convert numeric values to strings
    ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);
    verify(httpClient).execute(eq("GET"), anyString(), any(), headersCaptor.capture());

    Map<String, String> capturedHeaders = headersCaptor.getValue();
    assertThat(capturedHeaders).containsEntry("X-Request-Id", "12345");
    assertThat(capturedHeaders).containsEntry("X-Retry-Count", "3");
  }

  @Test
  void execute_withComplexUrlAndEncodingEnabled_shouldEncodeCorrectly() throws IOException {
    // Given: A request with complex URL and encoding enabled
    activity.setMethod("GET");
    activity.setUrl("https://api.example.com/search?name=John Doe&email=test@example.com");
    activity.setEncodeQueryParams(true);

    Response response = new Response(200, "results");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should encode special characters in query params
    ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
    verify(httpClient).execute(eq("GET"), urlCaptor.capture(), any(), anyMap());

    String capturedUrl = urlCaptor.getValue();
    assertThat(capturedUrl).doesNotContain("John Doe");
    assertThat(capturedUrl).contains("John+Doe");
    assertThat(capturedUrl).contains("%40"); // @ encoded
  }

  @Test
  void execute_withDefaultMethod_shouldUseGet() throws IOException {
    // Given: A request with default method (GET)
    activity.setUrl("https://api.example.com/data");
    activity.setEncodeQueryParams(false);
    // method not explicitly set, should default to GET

    Response response = new Response(200, "ok");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use default GET method
    verify(httpClient).execute(eq("GET"), anyString(), any(), anyMap());
  }

  @Test
  void execute_withPatchMethod_shouldExecuteWithPatchMethod() throws IOException {
    // Given: A PATCH request
    activity.setMethod("PATCH");
    activity.setUrl("https://api.example.com/update/123");
    activity.setBody("{\"status\": \"active\"}");
    activity.setEncodeQueryParams(false);

    Response response = new Response(200, "updated");
    when(httpClient.execute(anyString(), anyString(), any(), anyMap())).thenReturn(response);

    // When: Execute is called
    executor.execute(context);

    // Then: Should execute PATCH request
    verify(httpClient).execute(eq("PATCH"), eq("https://api.example.com/update/123"),
        eq("{\"status\": \"active\"}"), anyMap());
  }
}
