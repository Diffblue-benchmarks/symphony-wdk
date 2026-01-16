package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class BuildProcessContextClaude_cacheEventSubProcessToDoneTest {

  // Test 1: Cache a single event subprocess builder should allow retrieval via removeLastEventSubProcessBuilder
  @Test
  void cacheEventSubProcessToDone_withSingleBuilder_shouldCacheSuccessfully() {
    // Given: A BuildProcessContext with no cached event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);

    // When: Caching an event subprocess builder
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Then: The builder should be retrievable and hasEventSubProcess should return true
    assertThat(context.hasEventSubProcess()).isTrue();
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(eventSubProcessBuilder);
  }

  // Test 2: Cache multiple event subprocess builders should retrieve the last one
  @Test
  void cacheEventSubProcessToDone_withMultipleBuilders_shouldReturnLastCached() {
    // Given: A BuildProcessContext with no cached event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder firstBuilder = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder secondBuilder = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder thirdBuilder = mock(EventSubProcessBuilder.class);

    // When: Caching multiple event subprocess builders
    context.cacheEventSubProcessToDone(firstBuilder);
    context.cacheEventSubProcessToDone(secondBuilder);
    context.cacheEventSubProcessToDone(thirdBuilder);

    // Then: removeLastEventSubProcessBuilder should return the last cached builder
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(thirdBuilder);
  }

  // Test 3: Cache event subprocess builder maintains LIFO order (stack-like behavior)
  @Test
  void cacheEventSubProcessToDone_maintainsLIFOOrder() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder3 = mock(EventSubProcessBuilder.class);

    // When: Caching builders in order and removing them
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(builder2);
    context.cacheEventSubProcessToDone(builder3);

    // Then: Should return them in LIFO order (last-in, first-out)
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder3);
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder2);
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder1);
  }

  // Test 4: hasEventSubProcess should return false when none is cached
  @Test
  void cacheEventSubProcessToDone_hasEventSubProcess_shouldReturnFalseInitially() {
    // Given: A BuildProcessContext with no cached event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Checking if event subprocess exists
    boolean result = context.hasEventSubProcess();

    // Then: Should return false
    assertThat(result).isFalse();
  }

  // Test 5: hasEventSubProcess should return true after caching
  @Test
  void cacheEventSubProcessToDone_hasEventSubProcess_shouldReturnTrueAfterCaching() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder = mock(EventSubProcessBuilder.class);

    // When: Caching an event subprocess builder
    context.cacheEventSubProcessToDone(builder);

    // Then: hasEventSubProcess should return true
    assertThat(context.hasEventSubProcess()).isTrue();
  }

  // Test 6: hasEventSubProcess should return false after removing all cached builders
  @Test
  void cacheEventSubProcessToDone_hasEventSubProcess_shouldReturnFalseAfterRemovingAll() {
    // Given: A BuildProcessContext with cached event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(builder2);

    // When: Removing all cached builders
    context.removeLastEventSubProcessBuilder();
    context.removeLastEventSubProcessBuilder();

    // Then: hasEventSubProcess should return false
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 7: Attempting to remove last event subprocess builder when none is cached should throw exception
  @Test
  void cacheEventSubProcessToDone_removeLastWhenNoneCached_shouldThrowException() {
    // Given: A BuildProcessContext with no cached event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When/Then: Attempting to remove last event subprocess builder should throw NoSuchElementException
    assertThatThrownBy(() -> context.removeLastEventSubProcessBuilder())
        .isInstanceOf(NoSuchElementException.class);
  }

  // Test 8: Cache the same event subprocess builder multiple times
  @Test
  void cacheEventSubProcessToDone_sameBuildMultipleTimes_shouldCacheEachTime() {
    // Given: A BuildProcessContext and a single event subprocess builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder = mock(EventSubProcessBuilder.class);

    // When: Caching the same builder multiple times
    context.cacheEventSubProcessToDone(builder);
    context.cacheEventSubProcessToDone(builder);
    context.cacheEventSubProcessToDone(builder);

    // Then: All three instances should be cached and removable
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder);
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder);
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder);
  }

  // Test 9: Cache null event subprocess builder should not fail (and can be retrieved)
  @Test
  void cacheEventSubProcessToDone_withNullBuilder_shouldCacheNull() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Caching a null event subprocess builder
    context.cacheEventSubProcessToDone(null);

    // Then: hasEventSubProcess should return true and removing should return null
    assertThat(context.hasEventSubProcess()).isTrue();
    assertThat(context.removeLastEventSubProcessBuilder()).isNull();
  }

  // Test 10: Cache event subprocess builders with null interspersed
  @Test
  void cacheEventSubProcessToDone_withNullInterspersed_shouldHandleCorrectly() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);

    // When: Caching builders with null interspersed
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(null);
    context.cacheEventSubProcessToDone(builder2);

    // Then: Should return in LIFO order
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder2);
    assertThat(context.removeLastEventSubProcessBuilder()).isNull();
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder1);
  }

  // Test 11: Multiple contexts should maintain independent caches
  @Test
  void cacheEventSubProcessToDone_multipleContexts_shouldBeIndependent() {
    // Given: Two independent BuildProcessContext instances
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    ProcessBuilder processBuilder1 = mock(ProcessBuilder.class);
    ProcessBuilder processBuilder2 = mock(ProcessBuilder.class);
    BuildProcessContext context1 = new BuildProcessContext(graph1, processBuilder1);
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);

    // When: Caching different builders in each context
    context1.cacheEventSubProcessToDone(builder1);
    context2.cacheEventSubProcessToDone(builder2);

    // Then: Each context should maintain its own cache
    assertThat(context1.hasEventSubProcess()).isTrue();
    assertThat(context2.hasEventSubProcess()).isTrue();
    assertThat(context1.removeLastEventSubProcessBuilder()).isSameAs(builder1);
    assertThat(context2.removeLastEventSubProcessBuilder()).isSameAs(builder2);
  }

  // Test 12: Cache event subprocess builder independently from other cache types
  @Test
  void cacheEventSubProcessToDone_independentFromOtherCaches_shouldWorkCorrectly() {
    // Given: A BuildProcessContext with builders in different caches
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);

    // When: Caching event subprocess builder
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // Then: Should have event subprocess but not timeout subprocess
    assertThat(context.hasEventSubProcess()).isTrue();
    assertThat(context.hasTimeoutSubProcess()).isFalse();
  }

  // Test 13: Cache large number of event subprocess builders
  @Test
  void cacheEventSubProcessToDone_withManyBuilders_shouldHandleCorrectly() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Caching a large number of event subprocess builders
    EventSubProcessBuilder lastBuilder = null;
    for (int i = 0; i < 100; i++) {
      lastBuilder = mock(EventSubProcessBuilder.class);
      context.cacheEventSubProcessToDone(lastBuilder);
    }

    // Then: The last builder should be retrievable
    assertThat(context.hasEventSubProcess()).isTrue();
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(lastBuilder);
  }

  // Test 14: hasEventSubProcess should consistently return true with multiple cached builders
  @Test
  void cacheEventSubProcessToDone_hasEventSubProcess_shouldReturnTrueWithMultipleBuilders() {
    // Given: A BuildProcessContext with multiple cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder3 = mock(EventSubProcessBuilder.class);

    // When: Caching multiple builders
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(builder2);
    context.cacheEventSubProcessToDone(builder3);

    // Then: hasEventSubProcess should return true throughout removal process
    assertThat(context.hasEventSubProcess()).isTrue();
    context.removeLastEventSubProcessBuilder();
    assertThat(context.hasEventSubProcess()).isTrue();
    context.removeLastEventSubProcessBuilder();
    assertThat(context.hasEventSubProcess()).isTrue();
    context.removeLastEventSubProcessBuilder();
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 15: Remove and re-cache should work correctly
  @Test
  void cacheEventSubProcessToDone_removeAndRecache_shouldWorkCorrectly() {
    // Given: A BuildProcessContext with a cached builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);

    // When: Caching, removing, and re-caching
    context.cacheEventSubProcessToDone(builder1);
    context.removeLastEventSubProcessBuilder();
    context.cacheEventSubProcessToDone(builder2);

    // Then: The new builder should be retrievable
    assertThat(context.hasEventSubProcess()).isTrue();
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder2);
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 16: Partial removal should maintain correct state
  @Test
  void cacheEventSubProcessToDone_partialRemoval_shouldMaintainCorrectState() {
    // Given: A BuildProcessContext with multiple cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder3 = mock(EventSubProcessBuilder.class);

    // When: Caching multiple builders and removing only the last one
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(builder2);
    context.cacheEventSubProcessToDone(builder3);
    EventSubProcessBuilder removed = context.removeLastEventSubProcessBuilder();

    // Then: Should have removed the last one and still have more cached
    assertThat(removed).isSameAs(builder3);
    assertThat(context.hasEventSubProcess()).isTrue();
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder2);
  }

  // Test 17: Cache and remove operations should not affect other cache types
  @Test
  void cacheEventSubProcessToDone_shouldNotAffectOtherCacheTypes() {
    // Given: A BuildProcessContext with builders in multiple cache types
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventBuilder = mock(EventSubProcessBuilder.class);

    // When: Caching and removing event subprocess builder
    context.cacheEventSubProcessToDone(eventBuilder);
    assertThat(context.hasEventSubProcess()).isTrue();
    context.removeLastEventSubProcessBuilder();

    // Then: Other cache types should remain unaffected
    assertThat(context.hasEventSubProcess()).isFalse();
    assertThat(context.hasTimeoutSubProcess()).isFalse();
  }

  // Test 18: Multiple calls to hasEventSubProcess should be consistent
  @Test
  void cacheEventSubProcessToDone_hasEventSubProcess_shouldBeConsistent() {
    // Given: A BuildProcessContext with a cached builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(builder);

    // When: Calling hasEventSubProcess multiple times
    boolean result1 = context.hasEventSubProcess();
    boolean result2 = context.hasEventSubProcess();
    boolean result3 = context.hasEventSubProcess();

    // Then: All results should be consistent
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
    assertThat(result3).isTrue();
  }

  // Test 19: Empty cache after removing all should allow re-adding
  @Test
  void cacheEventSubProcessToDone_afterRemovingAll_shouldAllowReAdding() {
    // Given: A BuildProcessContext that had builders cached and removed
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(builder1);
    context.removeLastEventSubProcessBuilder();

    // When: Re-adding after complete removal
    context.cacheEventSubProcessToDone(builder2);

    // Then: Should work correctly
    assertThat(context.hasEventSubProcess()).isTrue();
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder2);
  }

  // Test 20: Interleaved cache and remove operations
  @Test
  void cacheEventSubProcessToDone_interleavedOperations_shouldWorkCorrectly() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder3 = mock(EventSubProcessBuilder.class);

    // When: Interleaving cache and remove operations
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(builder2);
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder2);
    context.cacheEventSubProcessToDone(builder3);
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder3);
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder1);

    // Then: Should have no more cached builders
    assertThat(context.hasEventSubProcess()).isFalse();
  }
}
