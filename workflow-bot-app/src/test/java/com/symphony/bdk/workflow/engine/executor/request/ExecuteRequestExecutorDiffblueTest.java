package com.symphony.bdk.workflow.engine.executor.request;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.request.client.HttpClient;
import com.symphony.bdk.workflow.engine.executor.request.client.Response;
import com.symphony.bdk.workflow.swadl.v1.activity.request.ExecuteRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExecuteRequestExecutor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ExecuteRequestExecutorDiffblueTest {
  @Autowired private ExecuteRequestExecutor executeRequestExecutor;

  @MockBean private HttpClient httpClient;

  /**
   * Test {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link ExecuteRequest} {@link ExecuteRequest#setUrl(String)} does nothing.
   *   <li>Then calls {@link ExecuteRequest#getBody()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given ExecuteRequest setUrl(String) does nothing; then calls getBody()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteRequestExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenExecuteRequestSetUrlDoesNothing_thenCallsGetBody() throws IOException {
    // Arrange
    when(httpClient.execute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<Object>any(),
            Mockito.<Map<String, String>>any()))
        .thenReturn(new Response(1, "Content"));

    ExecuteRequest executeRequest = mock(ExecuteRequest.class);
    doNothing().when(executeRequest).setUrl(Mockito.<String>any());
    when(executeRequest.isEncodeQueryParams()).thenReturn(true);
    when(executeRequest.getBody()).thenReturn("Body");
    when(executeRequest.getMethod()).thenReturn("Method");
    when(executeRequest.getUrl()).thenReturn("https://example.org/example");
    when(executeRequest.getHeaders()).thenReturn(new HashMap<>());
    doNothing().when(executeRequest).setEncodeQueryParams(anyBoolean());
    executeRequest.setEncodeQueryParams(false);

    ActivityExecutorContext<ExecuteRequest> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(executeRequest);

    // Act
    executeRequestExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(httpClient)
        .execute(
            eq("Method"), eq("https://example.org/example"), isA(Object.class), isA(Map.class));
    verify(executeRequest).getBody();
    verify(executeRequest).getHeaders();
    verify(executeRequest, atLeast(1)).getMethod();
    verify(executeRequest, atLeast(1)).getUrl();
    verify(executeRequest).isEncodeQueryParams();
    verify(executeRequest).setEncodeQueryParams(false);
    verify(executeRequest).setUrl("https://example.org/example");
  }

  /**
   * Test {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code U://U@[9U]:{UU?U#U} is {@code Value}.</li>
   *   <li>Then calls {@link ExecuteRequest#getBody()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given HashMap() 'U://U@[9U]:{UU?U#U' is 'Value'; then calls getBody()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteRequestExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenHashMapUU9uUuUUIsValue_thenCallsGetBody() throws IOException {
    // Arrange
    when(httpClient.execute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<Object>any(),
            Mockito.<Map<String, String>>any()))
        .thenReturn(new Response(1, "Content"));

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("U://U@[9U]:{UU?U#U", "Value");

    ExecuteRequest executeRequest = mock(ExecuteRequest.class);
    doNothing().when(executeRequest).setUrl(Mockito.<String>any());
    when(executeRequest.isEncodeQueryParams()).thenReturn(true);
    when(executeRequest.getBody()).thenReturn("Body");
    when(executeRequest.getMethod()).thenReturn("Method");
    when(executeRequest.getUrl()).thenReturn("https://example.org/example");
    when(executeRequest.getHeaders()).thenReturn(stringObjectMap);
    doNothing().when(executeRequest).setEncodeQueryParams(anyBoolean());
    executeRequest.setEncodeQueryParams(false);

    ActivityExecutorContext<ExecuteRequest> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(executeRequest);

    // Act
    executeRequestExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(httpClient)
        .execute(
            eq("Method"), eq("https://example.org/example"), isA(Object.class), isA(Map.class));
    verify(executeRequest).getBody();
    verify(executeRequest).getHeaders();
    verify(executeRequest, atLeast(1)).getMethod();
    verify(executeRequest, atLeast(1)).getUrl();
    verify(executeRequest).isEncodeQueryParams();
    verify(executeRequest).setEncodeQueryParams(false);
    verify(executeRequest).setUrl("https://example.org/example");
  }

  /**
   * Test {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link HttpClient} {@link HttpClient#execute(String, String, Object, Map)} throw
   *       {@link IOException#IOException()}.
   *   <li>Then throw {@link IOException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given HttpClient execute(String, String, Object, Map) throw IOException(); then throw IOException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteRequestExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenHttpClientExecuteThrowIOException_thenThrowIOException()
      throws IOException {
    // Arrange
    when(httpClient.execute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<Object>any(),
            Mockito.<Map<String, String>>any()))
        .thenThrow(new IOException());

    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setEncodeQueryParams(false);

    ActivityExecutorContext<ExecuteRequest> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenReturn(executeRequest);

    // Act and Assert
    assertThrows(IOException.class, () -> executeRequestExecutor.execute(execution));
    verify(execution).getActivity();
    verify(httpClient).execute(eq("GET"), isNull(), isNull(), isA(Map.class));
  }

  /**
   * Test {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given headers contain a {@link List} value.
   *   <li>Then joins header list values with a comma.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given headers contain a List value; then joins header values with comma")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteRequestExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenHeadersWithListValue_thenJoinsHeaderValues() throws IOException {
    // Arrange
    when(httpClient.execute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<Object>any(),
            Mockito.<Map<String, String>>any()))
        .thenReturn(new Response(1, "Content"));

    HashMap<String, Object> headers = new HashMap<>();
    headers.put("Accept", Arrays.asList("application/json", "text/plain"));

    ExecuteRequest executeRequest = mock(ExecuteRequest.class);
    when(executeRequest.isEncodeQueryParams()).thenReturn(false);
    when(executeRequest.getBody()).thenReturn("Body");
    when(executeRequest.getMethod()).thenReturn("GET");
    when(executeRequest.getUrl()).thenReturn("https://example.org/example");
    when(executeRequest.getHeaders()).thenReturn(headers);

    ActivityExecutorContext<ExecuteRequest> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(executeRequest);

    // Act
    executeRequestExecutor.execute(execution);

    // Assert
    verify(execution).setOutputVariables(isA(Map.class));
    verify(httpClient)
        .execute(
            eq("GET"),
            eq("https://example.org/example"),
            isA(Object.class),
            isA(Map.class));
  }

  /**
   * Test {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivityExecutorContext#setOutputVariables(Map)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls setOutputVariables(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecuteRequestExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsSetOutputVariables() throws IOException {
    // Arrange
    when(httpClient.execute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<Object>any(),
            Mockito.<Map<String, String>>any()))
        .thenReturn(new Response(1, "Content"));

    ExecuteRequest executeRequest = new ExecuteRequest();
    executeRequest.setEncodeQueryParams(false);

    ActivityExecutorContext<ExecuteRequest> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(executeRequest);

    // Act
    executeRequestExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(httpClient).execute(eq("GET"), isNull(), isNull(), isA(Map.class));
  }
}
