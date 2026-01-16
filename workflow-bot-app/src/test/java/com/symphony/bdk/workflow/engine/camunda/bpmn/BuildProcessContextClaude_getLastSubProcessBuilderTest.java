package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class BuildProcessContextClaude_getLastSubProcessBuilderTest {

  // Test 1: Get last subprocess builder when none is cached should throw exception
  @Test
  void getLastSubProcessBuilder_whenNoneCached_shouldThrowNoSuchElementException() {
    // Given: A BuildProcessContext with no cached subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When/Then: Attempting to get last subprocess builder should throw NoSuchElementException
    assertThatThrownBy(() -> context.getLastSubProcessBuilder())
        .isInstanceOf(NoSuchElementException.class);
  }

  // Test 2: Get last subprocess builder with a single cached builder
  @Test
  void getLastSubProcessBuilder_withSingleBuilder_shouldReturnThatBuilder() {
    // Given: A BuildProcessContext with one cached subprocess builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    context.cacheSubProcess(subProcessBuilder);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the cached builder
    assertThat(result).isSameAs(subProcessBuilder);
  }

  // Test 3: Get last subprocess builder with multiple cached builders should return the last one
  @Test
  void getLastSubProcessBuilder_withMultipleBuilders_shouldReturnLastCached() {
    // Given: A BuildProcessContext with multiple cached subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder firstBuilder = mock(SubProcessBuilder.class);
    SubProcessBuilder secondBuilder = mock(SubProcessBuilder.class);
    SubProcessBuilder thirdBuilder = mock(SubProcessBuilder.class);
    context.cacheSubProcess(firstBuilder);
    context.cacheSubProcess(secondBuilder);
    context.cacheSubProcess(thirdBuilder);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the last cached builder
    assertThat(result).isSameAs(thirdBuilder);
  }

  // Test 4: Get last subprocess builder does not remove the builder from cache
  @Test
  void getLastSubProcessBuilder_shouldNotRemoveFromCache() {
    // Given: A BuildProcessContext with a cached subprocess builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    context.cacheSubProcess(subProcessBuilder);

    // When: Getting the last subprocess builder multiple times
    SubProcessBuilder result1 = context.getLastSubProcessBuilder();
    SubProcessBuilder result2 = context.getLastSubProcessBuilder();
    SubProcessBuilder result3 = context.getLastSubProcessBuilder();

    // Then: All results should be the same builder (not removed)
    assertThat(result1).isSameAs(subProcessBuilder);
    assertThat(result2).isSameAs(subProcessBuilder);
    assertThat(result3).isSameAs(subProcessBuilder);
  }

  // Test 5: Get last subprocess builder returns null when null was cached
  @Test
  void getLastSubProcessBuilder_withNullCached_shouldReturnNull() {
    // Given: A BuildProcessContext with a null subprocess builder cached
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    context.cacheSubProcess(null);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return null
    assertThat(result).isNull();
  }

  // Test 6: Get last subprocess builder when null is interspersed with other builders
  @Test
  void getLastSubProcessBuilder_withNullInterspersed_shouldReturnLastCached() {
    // Given: A BuildProcessContext with builders including null
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    context.cacheSubProcess(builder1);
    context.cacheSubProcess(null);
    context.cacheSubProcess(builder2);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the last cached builder
    assertThat(result).isSameAs(builder2);
  }

  // Test 7: Get last subprocess builder when last cached is null
  @Test
  void getLastSubProcessBuilder_whenLastIsNull_shouldReturnNull() {
    // Given: A BuildProcessContext with null as the last cached builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    context.cacheSubProcess(builder1);
    context.cacheSubProcess(null);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return null
    assertThat(result).isNull();
  }

  // Test 8: Get last subprocess builder with same builder cached multiple times
  @Test
  void getLastSubProcessBuilder_sameBuildMultipleTimes_shouldReturnThatBuilder() {
    // Given: A BuildProcessContext with the same builder cached multiple times
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder = mock(SubProcessBuilder.class);
    context.cacheSubProcess(builder);
    context.cacheSubProcess(builder);
    context.cacheSubProcess(builder);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the builder
    assertThat(result).isSameAs(builder);
  }

  // Test 9: Get last subprocess builder from multiple independent contexts
  @Test
  void getLastSubProcessBuilder_multipleContexts_shouldBeIndependent() {
    // Given: Two independent BuildProcessContext instances with different builders
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    ProcessBuilder processBuilder1 = mock(ProcessBuilder.class);
    ProcessBuilder processBuilder2 = mock(ProcessBuilder.class);
    BuildProcessContext context1 = new BuildProcessContext(graph1, processBuilder1);
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    context1.cacheSubProcess(builder1);
    context2.cacheSubProcess(builder2);

    // When: Getting the last subprocess builder from each context
    SubProcessBuilder result1 = context1.getLastSubProcessBuilder();
    SubProcessBuilder result2 = context2.getLastSubProcessBuilder();

    // Then: Each context should return its own cached builder
    assertThat(result1).isSameAs(builder1);
    assertThat(result2).isSameAs(builder2);
  }

  // Test 10: Get last subprocess builder is independent from event subprocess cache
  @Test
  void getLastSubProcessBuilder_independentFromEventSubProcessCache() {
    // Given: A BuildProcessContext with both subprocess and event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    context.cacheSubProcess(subProcessBuilder);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the subprocess builder, unaffected by event subprocess cache
    assertThat(result).isSameAs(subProcessBuilder);
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 11: Get last subprocess builder is independent from timeout subprocess cache
  @Test
  void getLastSubProcessBuilder_independentFromTimeoutSubProcessCache() {
    // Given: A BuildProcessContext with both subprocess and timeout subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    SubProcessBuilder timeoutBuilder = mock(SubProcessBuilder.class);
    context.cacheSubProcess(subProcessBuilder);
    context.cacheSubProcessTimeoutToDone(timeoutBuilder);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the subprocess builder, unaffected by timeout subprocess cache
    assertThat(result).isSameAs(subProcessBuilder);
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 12: Get last subprocess builder with large number of cached builders
  @Test
  void getLastSubProcessBuilder_withManyBuilders_shouldReturnLast() {
    // Given: A BuildProcessContext with many cached subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder lastBuilder = null;
    for (int i = 0; i < 100; i++) {
      lastBuilder = mock(SubProcessBuilder.class);
      context.cacheSubProcess(lastBuilder);
    }

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the last cached builder
    assertThat(result).isSameAs(lastBuilder);
  }

  // Test 13: Get last subprocess builder returns correct builder after sequential caching
  @Test
  void getLastSubProcessBuilder_afterSequentialCaching_shouldReturnCorrect() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder3 = mock(SubProcessBuilder.class);

    // When: Caching builders sequentially and checking after each cache
    context.cacheSubProcess(builder1);
    assertThat(context.getLastSubProcessBuilder()).isSameAs(builder1);

    context.cacheSubProcess(builder2);
    assertThat(context.getLastSubProcessBuilder()).isSameAs(builder2);

    context.cacheSubProcess(builder3);
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the last cached builder
    assertThat(result).isSameAs(builder3);
  }

  // Test 14: Get last subprocess builder with alternating null and non-null builders
  @Test
  void getLastSubProcessBuilder_withAlternatingNullAndNonNull_shouldReturnLast() {
    // Given: A BuildProcessContext with alternating null and non-null builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    context.cacheSubProcess(builder1);
    context.cacheSubProcess(null);
    context.cacheSubProcess(builder2);
    context.cacheSubProcess(null);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return null (the last cached)
    assertThat(result).isNull();
  }

  // Test 15: Get last subprocess builder is consistent across multiple calls
  @Test
  void getLastSubProcessBuilder_multipleConsecutiveCalls_shouldBeConsistent() {
    // Given: A BuildProcessContext with a cached builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder = mock(SubProcessBuilder.class);
    context.cacheSubProcess(builder);

    // When: Calling getLastSubProcessBuilder multiple times consecutively
    SubProcessBuilder result1 = context.getLastSubProcessBuilder();
    SubProcessBuilder result2 = context.getLastSubProcessBuilder();
    SubProcessBuilder result3 = context.getLastSubProcessBuilder();
    SubProcessBuilder result4 = context.getLastSubProcessBuilder();
    SubProcessBuilder result5 = context.getLastSubProcessBuilder();

    // Then: All results should be identical
    assertThat(result1).isSameAs(builder);
    assertThat(result2).isSameAs(builder);
    assertThat(result3).isSameAs(builder);
    assertThat(result4).isSameAs(builder);
    assertThat(result5).isSameAs(builder);
  }

  // Test 16: Get last subprocess builder with complex caching scenario
  @Test
  void getLastSubProcessBuilder_complexCachingScenario_shouldReturnLast() {
    // Given: A BuildProcessContext with a complex caching scenario
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder3 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder4 = mock(SubProcessBuilder.class);

    // Cache in various orders with different types
    context.cacheSubProcess(builder1);
    context.cacheSubProcessTimeoutToDone(builder2);
    context.cacheSubProcess(builder3);
    context.cacheSubProcess(builder4);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return the last subprocess builder (not timeout builder)
    assertThat(result).isSameAs(builder4);
  }

  // Test 17: Get last subprocess builder returns type SubProcessBuilder
  @Test
  void getLastSubProcessBuilder_shouldReturnCorrectType() {
    // Given: A BuildProcessContext with a cached subprocess builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    context.cacheSubProcess(subProcessBuilder);

    // When: Getting the last subprocess builder
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: Should return a SubProcessBuilder type
    assertThat(result).isInstanceOf(SubProcessBuilder.class);
    assertThat(result).isSameAs(subProcessBuilder);
  }

  // Test 18: Get last subprocess builder after caching in interleaved manner
  @Test
  void getLastSubProcessBuilder_afterInterleavedCaching_shouldReturnCorrect() {
    // Given: A BuildProcessContext with interleaved caching
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder3 = mock(SubProcessBuilder.class);

    context.cacheSubProcess(builder1);
    context.cacheSubProcess(builder2);
    SubProcessBuilder firstCheck = context.getLastSubProcessBuilder();
    context.cacheSubProcess(builder3);

    // When: Getting the last subprocess builder after interleaved operations
    SubProcessBuilder result = context.getLastSubProcessBuilder();

    // Then: First check should return builder2, final check should return builder3
    assertThat(firstCheck).isSameAs(builder2);
    assertThat(result).isSameAs(builder3);
  }

  // Test 19: Get last subprocess builder in fresh context after creating new context
  @Test
  void getLastSubProcessBuilder_inFreshContext_shouldThrowException() {
    // Given: A fresh BuildProcessContext with no cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("freshWorkflow", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When/Then: Getting last subprocess builder should throw exception
    assertThatThrownBy(() -> context.getLastSubProcessBuilder())
        .isInstanceOf(NoSuchElementException.class);
  }

  // Test 20: Get last subprocess builder maintains isolation from other contexts
  @Test
  void getLastSubProcessBuilder_maintainsIsolationBetweenContexts() {
    // Given: Multiple BuildProcessContext instances
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    WorkflowDirectedGraph graph3 = new WorkflowDirectedGraph("workflow3", 3L);
    ProcessBuilder processBuilder1 = mock(ProcessBuilder.class);
    ProcessBuilder processBuilder2 = mock(ProcessBuilder.class);
    ProcessBuilder processBuilder3 = mock(ProcessBuilder.class);
    BuildProcessContext context1 = new BuildProcessContext(graph1, processBuilder1);
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);
    BuildProcessContext context3 = new BuildProcessContext(graph3, processBuilder3);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder3 = mock(SubProcessBuilder.class);

    context1.cacheSubProcess(builder1);
    context2.cacheSubProcess(builder2);
    context3.cacheSubProcess(builder3);

    // When: Getting last subprocess builder from each context
    SubProcessBuilder result1 = context1.getLastSubProcessBuilder();
    SubProcessBuilder result2 = context2.getLastSubProcessBuilder();
    SubProcessBuilder result3 = context3.getLastSubProcessBuilder();

    // Then: Each context should return its own builder
    assertThat(result1).isSameAs(builder1);
    assertThat(result2).isSameAs(builder2);
    assertThat(result3).isSameAs(builder3);
  }
}
