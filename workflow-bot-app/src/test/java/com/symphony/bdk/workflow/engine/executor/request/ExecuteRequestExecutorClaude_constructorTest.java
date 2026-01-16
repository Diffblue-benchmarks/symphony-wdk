package com.symphony.bdk.workflow.engine.executor.request;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.request.client.HttpClient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;

class ExecuteRequestExecutorClaude_constructorTest {

  @Test
  void constructor_withValidHttpClient_shouldCreateInstance() {
    // Given: Valid HttpClient parameter
    HttpClient httpClient = mock(HttpClient.class);

    // When: Constructor is called
    ExecuteRequestExecutor executor = new ExecuteRequestExecutor(httpClient);

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_withValidHttpClient_shouldNotThrowException() {
    // Given: Valid HttpClient parameter
    HttpClient httpClient = mock(HttpClient.class);

    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new ExecuteRequestExecutor(httpClient))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_calledMultipleTimes_shouldCreateDistinctInstances() {
    // Given: Valid HttpClient parameter
    HttpClient httpClient = mock(HttpClient.class);

    // When: Constructor is called multiple times
    ExecuteRequestExecutor executor1 = new ExecuteRequestExecutor(httpClient);
    ExecuteRequestExecutor executor2 = new ExecuteRequestExecutor(httpClient);

    // Then: Each call should create a distinct instance
    assertThat(executor1).isNotNull();
    assertThat(executor2).isNotNull();
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_withDifferentHttpClients_shouldCreateDistinctInstances() {
    // Given: Different HttpClient instances
    HttpClient httpClient1 = mock(HttpClient.class);
    HttpClient httpClient2 = mock(HttpClient.class);

    // When: Constructor is called with different parameters
    ExecuteRequestExecutor executor1 = new ExecuteRequestExecutor(httpClient1);
    ExecuteRequestExecutor executor2 = new ExecuteRequestExecutor(httpClient2);

    // Then: Each call should create a distinct instance
    assertThat(executor1).isNotNull();
    assertThat(executor2).isNotNull();
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // Given: Valid HttpClient parameter
    HttpClient httpClient = mock(HttpClient.class);

    // When: Constructor is called
    ExecuteRequestExecutor executor = new ExecuteRequestExecutor(httpClient);

    // Then: Instance should be of ExecuteRequestExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(ExecuteRequestExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_withSameHttpClientReference_shouldCreateValidInstance() {
    // Given: Valid HttpClient parameter
    HttpClient httpClient = mock(HttpClient.class);

    // When: Constructor is called with same reference
    ExecuteRequestExecutor executor1 = new ExecuteRequestExecutor(httpClient);
    ExecuteRequestExecutor executor2 = new ExecuteRequestExecutor(httpClient);

    // Then: Both instances should be created successfully but be distinct
    assertThat(executor1).isNotNull();
    assertThat(executor2).isNotNull();
    assertThat(executor1).isNotSameAs(executor2);
  }
}
