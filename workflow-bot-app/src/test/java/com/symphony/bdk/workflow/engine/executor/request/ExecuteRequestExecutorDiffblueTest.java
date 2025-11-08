package com.symphony.bdk.workflow.engine.executor.request;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.request.client.HttpClient;
import com.symphony.bdk.workflow.engine.executor.request.client.Response;
import com.symphony.bdk.workflow.swadl.v1.activity.request.ExecuteRequest;
import java.io.IOException;
import java.util.HashMap;
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
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ExecuteRequestExecutorDiffblueTest {
  @Autowired
  private ExecuteRequestExecutor executeRequestExecutor;

  @MockBean
  private HttpClient httpClient;

  /**
   * Test {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link ExecuteRequest} {@link ExecuteRequest#isEncodeQueryParams()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given ExecuteRequest isEncodeQueryParams() return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExecuteRequestExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenExecuteRequestIsEncodeQueryParamsReturnFalse() throws IOException {
    // Arrange
    when(httpClient.execute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any(),
        Mockito.<Map<String, String>>any())).thenReturn(new Response(1, "Content"));
    ExecuteRequest executeRequest = mock(ExecuteRequest.class);
    when(executeRequest.isEncodeQueryParams()).thenReturn(false);
    when(executeRequest.getBody()).thenReturn("Body");
    when(executeRequest.getMethod()).thenReturn("Method");
    when(executeRequest.getUrl()).thenReturn("https://example.org/example");
    when(executeRequest.getHeaders()).thenReturn(new HashMap<>());
    ActivityExecutorContext<ExecuteRequest> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(executeRequest);

    // Act
    executeRequestExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(httpClient).execute(eq("Method"), eq("https://example.org/example"), isA(Object.class), isA(Map.class));
    verify(executeRequest).getBody();
    verify(executeRequest).getHeaders();
    verify(executeRequest, atLeast(1)).getMethod();
    verify(executeRequest, atLeast(1)).getUrl();
    verify(executeRequest).isEncodeQueryParams();
  }

  /**
   * Test {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code U://U@[9U]:{UU?U#U} is {@code 42}.</li>
   *   <li>Then calls {@link ExecuteRequest#setUrl(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given HashMap() 'U://U@[9U]:{UU?U#U' is '42'; then calls setUrl(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExecuteRequestExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenHashMapUU9uUuUUIs42_thenCallsSetUrl() throws IOException {
    // Arrange
    when(httpClient.execute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any(),
        Mockito.<Map<String, String>>any())).thenReturn(new Response(1, "Content"));

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("U://U@[9U]:{UU?U#U", "42");
    ExecuteRequest executeRequest = mock(ExecuteRequest.class);
    doNothing().when(executeRequest).setUrl(Mockito.<String>any());
    when(executeRequest.isEncodeQueryParams()).thenReturn(true);
    when(executeRequest.getBody()).thenReturn("Body");
    when(executeRequest.getMethod()).thenReturn("Method");
    when(executeRequest.getUrl()).thenReturn("https://example.org/example");
    when(executeRequest.getHeaders()).thenReturn(stringObjectMap);
    ActivityExecutorContext<ExecuteRequest> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(executeRequest);

    // Act
    executeRequestExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(httpClient).execute(eq("Method"), eq("https://example.org/example"), isA(Object.class), isA(Map.class));
    verify(executeRequest).getBody();
    verify(executeRequest).getHeaders();
    verify(executeRequest, atLeast(1)).getMethod();
    verify(executeRequest, atLeast(1)).getUrl();
    verify(executeRequest).isEncodeQueryParams();
    verify(executeRequest).setUrl(eq("https://example.org/example"));
  }

  /**
   * Test {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then calls {@link ExecuteRequest#setUrl(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecuteRequestExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls setUrl(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExecuteRequestExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsSetUrl() throws IOException {
    // Arrange
    when(httpClient.execute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any(),
        Mockito.<Map<String, String>>any())).thenReturn(new Response(1, "Content"));
    ExecuteRequest executeRequest = mock(ExecuteRequest.class);
    doNothing().when(executeRequest).setUrl(Mockito.<String>any());
    when(executeRequest.isEncodeQueryParams()).thenReturn(true);
    when(executeRequest.getBody()).thenReturn("Body");
    when(executeRequest.getMethod()).thenReturn("Method");
    when(executeRequest.getUrl()).thenReturn("https://example.org/example");
    when(executeRequest.getHeaders()).thenReturn(new HashMap<>());
    ActivityExecutorContext<ExecuteRequest> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(execution.getActivity()).thenReturn(executeRequest);

    // Act
    executeRequestExecutor.execute(execution);

    // Assert
    verify(execution).getActivity();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(httpClient).execute(eq("Method"), eq("https://example.org/example"), isA(Object.class), isA(Map.class));
    verify(executeRequest).getBody();
    verify(executeRequest).getHeaders();
    verify(executeRequest, atLeast(1)).getMethod();
    verify(executeRequest, atLeast(1)).getUrl();
    verify(executeRequest).isEncodeQueryParams();
    verify(executeRequest).setUrl(eq("https://example.org/example"));
  }
}
