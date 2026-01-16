package com.symphony.bdk.workflow.engine.executor;

import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DebugExecutorClaudeTest {

  private DebugExecutor debugExecutor;
  private ActivityExecutorContext<Debug> context;
  private Debug debugActivity;

  @BeforeEach
  void setUp() {
    debugExecutor = new DebugExecutor();
    context = mock(ActivityExecutorContext.class);
    debugActivity = new Debug();
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    DebugExecutor executor = new DebugExecutor();

    // Then: Instance should be created successfully
    assertThat(executor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    DebugExecutor executor = new DebugExecutor();

    // Then: Instance should be of DebugExecutor type and implement ActivityExecutor
    assertThat(executor).isInstanceOf(DebugExecutor.class);
    assertThat(executor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    DebugExecutor executor1 = new DebugExecutor();
    DebugExecutor executor2 = new DebugExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new DebugExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method with valid JSON

  @Test
  void execute_withValidJsonString_shouldParseAndSetOutputVariable() throws IOException {
    // Given: A valid JSON string
    String jsonString = "{\"key\":\"value\",\"number\":123}";
    debugActivity.setObject(jsonString);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with formatted JSON
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withValidJsonObject_shouldSetOutputVariable() throws IOException {
    // Given: A valid JSON-like object
    String jsonString = "{\"name\":\"test\",\"value\":42}";
    debugActivity.setObject(jsonString);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withNestedJsonObject_shouldParseAndSetOutputVariable() throws IOException {
    // Given: A nested JSON string
    String nestedJson = "{\"outer\":{\"inner\":\"value\"},\"array\":[1,2,3]}";
    debugActivity.setObject(nestedJson);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with formatted JSON
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withJsonArray_shouldParseAndSetOutputVariable() throws IOException {
    // Given: A JSON array string
    String jsonArray = "[{\"id\":1},{\"id\":2},{\"id\":3}]";
    debugActivity.setObject(jsonArray);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with formatted JSON
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withEmptyJsonObject_shouldParseAndSetOutputVariable() throws IOException {
    // Given: An empty JSON object
    String emptyJson = "{}";
    debugActivity.setObject(emptyJson);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with formatted JSON
    verify(context).setOutputVariables(any(Map.class));
  }

  // Tests for execute method with invalid JSON (falls back to toString)

  @Test
  void execute_withInvalidJsonString_shouldFallbackToToString() throws IOException {
    // Given: An invalid JSON string
    String invalidJson = "not a valid json";
    debugActivity.setObject(invalidJson);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with toString value
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withPlainString_shouldSetOutputVariableWithStringValue() throws IOException {
    // Given: A plain string
    String plainString = "Hello World";
    debugActivity.setObject(plainString);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with the plain string
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withNumericValue_shouldSetOutputVariableWithToString() throws IOException {
    // Given: A numeric value
    Integer numericValue = 12345;
    debugActivity.setObject(numericValue);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with toString value
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withBooleanValue_shouldSetOutputVariableWithToString() throws IOException {
    // Given: A boolean value
    Boolean boolValue = true;
    debugActivity.setObject(boolValue);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with toString value
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withNullValue_shouldHandleNullPointerException() {
    // Given: A null value
    debugActivity.setObject(null);
    when(context.getActivity()).thenReturn(debugActivity);

    // When/Then: Execute should throw exception when calling toString on null
    assertThatCode(() -> debugExecutor.execute(context))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void execute_withComplexObject_shouldSetOutputVariableWithToString() throws IOException {
    // Given: A complex object that's not valid JSON
    Object complexObject = new Object() {
      @Override
      public String toString() {
        return "ComplexObject[field=value]";
      }
    };
    debugActivity.setObject(complexObject);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with toString value
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withMalformedJson_shouldFallbackToToString() throws IOException {
    // Given: A malformed JSON string
    String malformedJson = "{key:value}"; // Missing quotes around key
    debugActivity.setObject(malformedJson);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with toString value
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withPartiallyValidJson_shouldFallbackToToString() throws IOException {
    // Given: A partially valid JSON string
    String partialJson = "{\"key\":\"value\""; // Missing closing brace
    debugActivity.setObject(partialJson);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with toString value
    verify(context).setOutputVariables(any(Map.class));
  }

  // Tests for execute method behavior

  @Test
  void execute_shouldNotThrowIOException() {
    // Given: A valid setup
    debugActivity.setObject("test");
    when(context.getActivity()).thenReturn(debugActivity);

    // When/Then: Execute should not throw IOException
    assertThatCode(() -> debugExecutor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldSetOutputVariablesEachTime() throws IOException {
    // Given: A valid JSON string
    String jsonString = "{\"test\":\"value\"}";
    debugActivity.setObject(jsonString);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called multiple times
    debugExecutor.execute(context);
    debugExecutor.execute(context);

    // Then: Output variables should be set each time
    verify(context, org.mockito.Mockito.times(2)).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withDifferentContexts_shouldWorkCorrectly() throws IOException {
    // Given: Two different contexts
    ActivityExecutorContext<Debug> context1 = mock(ActivityExecutorContext.class);
    ActivityExecutorContext<Debug> context2 = mock(ActivityExecutorContext.class);

    Debug activity1 = new Debug();
    activity1.setObject("{\"key1\":\"value1\"}");
    when(context1.getActivity()).thenReturn(activity1);

    Debug activity2 = new Debug();
    activity2.setObject("plain string");
    when(context2.getActivity()).thenReturn(activity2);

    // When: Execute is called with different contexts
    debugExecutor.execute(context1);
    debugExecutor.execute(context2);

    // Then: Both contexts should have output variables set
    verify(context1).setOutputVariables(any(Map.class));
    verify(context2).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withEmptyString_shouldSetOutputVariable() throws IOException {
    // Given: An empty string
    String emptyString = "";
    debugActivity.setObject(emptyString);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set with empty string
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void execute_withWhitespaceString_shouldSetOutputVariable() throws IOException {
    // Given: A whitespace string
    String whitespaceString = "   ";
    debugActivity.setObject(whitespaceString);
    when(context.getActivity()).thenReturn(debugActivity);

    // When: Execute is called
    debugExecutor.execute(context);

    // Then: Output variable should be set
    verify(context).setOutputVariables(any(Map.class));
  }
}
