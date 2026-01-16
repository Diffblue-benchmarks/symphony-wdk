package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class BuildProcessContextClaude_cacheSubProcessTest {

  // Test 1: Cache a single subprocess builder should allow retrieval via getLastSubProcessBuilder
  @Test
  void cacheSubProcess_withSingleBuilder_shouldCacheSuccessfully() {
    // Given: A BuildProcessContext with no cached subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);

    // When: Caching a subprocess builder
    context.cacheSubProcess(subProcessBuilder);

    // Then: The builder should be retrievable via getLastSubProcessBuilder
    assertThat(context.getLastSubProcessBuilder()).isSameAs(subProcessBuilder);
  }

  // Test 2: Cache multiple subprocess builders should retrieve the last one
  @Test
  void cacheSubProcess_withMultipleBuilders_shouldReturnLastCached() {
    // Given: A BuildProcessContext with no cached subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder firstBuilder = mock(SubProcessBuilder.class);
    SubProcessBuilder secondBuilder = mock(SubProcessBuilder.class);
    SubProcessBuilder thirdBuilder = mock(SubProcessBuilder.class);

    // When: Caching multiple subprocess builders
    context.cacheSubProcess(firstBuilder);
    context.cacheSubProcess(secondBuilder);
    context.cacheSubProcess(thirdBuilder);

    // Then: getLastSubProcessBuilder should return the last cached builder
    assertThat(context.getLastSubProcessBuilder()).isSameAs(thirdBuilder);
  }

  // Test 3: Cache subprocess builder maintains FIFO order (stack-like behavior with getLast)
  @Test
  void cacheSubProcess_maintainsFIFOOrder() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);

    // When: Caching builders in order
    context.cacheSubProcess(builder1);
    context.cacheSubProcess(builder2);

    // Then: The last cached builder should be returned
    assertThat(context.getLastSubProcessBuilder()).isSameAs(builder2);
  }

  // Test 4: Attempting to get last subprocess builder when none is cached should throw exception
  @Test
  void cacheSubProcess_getLastWhenNoneCached_shouldThrowException() {
    // Given: A BuildProcessContext with no cached subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When/Then: Attempting to get last subprocess builder should throw NoSuchElementException
    assertThatThrownBy(() -> context.getLastSubProcessBuilder())
        .isInstanceOf(NoSuchElementException.class);
  }

  // Test 5: Cache the same subprocess builder multiple times
  @Test
  void cacheSubProcess_sameBuildMultipleTimes_shouldCacheEachTime() {
    // Given: A BuildProcessContext and a single subprocess builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder = mock(SubProcessBuilder.class);

    // When: Caching the same builder multiple times
    context.cacheSubProcess(builder);
    context.cacheSubProcess(builder);

    // Then: The builder should still be retrievable as the last one
    assertThat(context.getLastSubProcessBuilder()).isSameAs(builder);
  }

  // Test 6: Cache null subprocess builder should not fail (and can be retrieved)
  @Test
  void cacheSubProcess_withNullBuilder_shouldCacheNull() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Caching a null subprocess builder
    context.cacheSubProcess(null);

    // Then: Getting the last builder should return null
    assertThat(context.getLastSubProcessBuilder()).isNull();
  }

  // Test 7: Cache subprocess builders with null interspersed
  @Test
  void cacheSubProcess_withNullInterspersed_shouldHandleCorrectly() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);

    // When: Caching builders with null interspersed
    context.cacheSubProcess(builder1);
    context.cacheSubProcess(null);
    context.cacheSubProcess(builder2);

    // Then: The last cached builder should be returned
    assertThat(context.getLastSubProcessBuilder()).isSameAs(builder2);
  }

  // Test 8: Multiple contexts should maintain independent caches
  @Test
  void cacheSubProcess_multipleContexts_shouldBeIndependent() {
    // Given: Two independent BuildProcessContext instances
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    ProcessBuilder processBuilder1 = mock(ProcessBuilder.class);
    ProcessBuilder processBuilder2 = mock(ProcessBuilder.class);
    BuildProcessContext context1 = new BuildProcessContext(graph1, processBuilder1);
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);

    // When: Caching different builders in each context
    context1.cacheSubProcess(builder1);
    context2.cacheSubProcess(builder2);

    // Then: Each context should maintain its own cache
    assertThat(context1.getLastSubProcessBuilder()).isSameAs(builder1);
    assertThat(context2.getLastSubProcessBuilder()).isSameAs(builder2);
  }

  // Test 9: Cache subprocess builder after caching other types (eventSubProcess, timeout)
  @Test
  void cacheSubProcess_afterCachingOtherTypes_shouldWorkIndependently() {
    // Given: A BuildProcessContext with other cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    SubProcessBuilder timeoutBuilder = mock(SubProcessBuilder.class);

    // When: Caching subprocess and timeout subprocess builders
    context.cacheSubProcess(subProcessBuilder);
    context.cacheSubProcessTimeoutToDone(timeoutBuilder);

    // Then: Both should be maintained independently
    assertThat(context.getLastSubProcessBuilder()).isSameAs(subProcessBuilder);
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 10: Cache large number of subprocess builders
  @Test
  void cacheSubProcess_withManyBuilders_shouldHandleCorrectly() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Caching a large number of subprocess builders
    SubProcessBuilder lastBuilder = null;
    for (int i = 0; i < 100; i++) {
      lastBuilder = mock(SubProcessBuilder.class);
      context.cacheSubProcess(lastBuilder);
    }

    // Then: The last builder should be retrievable
    assertThat(context.getLastSubProcessBuilder()).isSameAs(lastBuilder);
  }
}
