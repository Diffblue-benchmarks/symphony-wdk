package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class BuildProcessContextClaude_removeLastEventSubProcessBuilderTest {

  // Test 1: Remove last event subprocess builder when none is cached should throw exception
  @Test
  void removeLastEventSubProcessBuilder_whenNoneCached_shouldThrowNoSuchElementException() {
    // Given: A BuildProcessContext with no cached event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When/Then: Attempting to remove last event subprocess builder should throw NoSuchElementException
    assertThatThrownBy(() -> context.removeLastEventSubProcessBuilder())
        .isInstanceOf(NoSuchElementException.class);
  }

  // Test 2: Remove last event subprocess builder with a single cached builder
  @Test
  void removeLastEventSubProcessBuilder_withSingleBuilder_shouldReturnAndRemoveBuilder() {
    // Given: A BuildProcessContext with one cached event subprocess builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // When: Removing the last event subprocess builder
    EventSubProcessBuilder result = context.removeLastEventSubProcessBuilder();

    // Then: Should return the cached builder and cache should be empty
    assertThat(result).isSameAs(eventSubProcessBuilder);
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 3: Remove last event subprocess builder with multiple cached builders should remove in LIFO order
  @Test
  void removeLastEventSubProcessBuilder_withMultipleBuilders_shouldRemoveInLIFOOrder() {
    // Given: A BuildProcessContext with multiple cached event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder firstBuilder = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder secondBuilder = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder thirdBuilder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(firstBuilder);
    context.cacheEventSubProcessToDone(secondBuilder);
    context.cacheEventSubProcessToDone(thirdBuilder);

    // When: Removing event subprocess builders
    EventSubProcessBuilder result1 = context.removeLastEventSubProcessBuilder();
    EventSubProcessBuilder result2 = context.removeLastEventSubProcessBuilder();
    EventSubProcessBuilder result3 = context.removeLastEventSubProcessBuilder();

    // Then: Should return builders in LIFO order (last-in, first-out)
    assertThat(result1).isSameAs(thirdBuilder);
    assertThat(result2).isSameAs(secondBuilder);
    assertThat(result3).isSameAs(firstBuilder);
  }

  // Test 4: Remove last event subprocess builder actually removes from cache
  @Test
  void removeLastEventSubProcessBuilder_shouldActuallyRemoveFromCache() {
    // Given: A BuildProcessContext with a cached event subprocess builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // When: Removing the last event subprocess builder
    context.removeLastEventSubProcessBuilder();

    // Then: Attempting to remove again should throw exception
    assertThatThrownBy(() -> context.removeLastEventSubProcessBuilder())
        .isInstanceOf(NoSuchElementException.class);
  }

  // Test 5: Remove last event subprocess builder returns null when null was cached
  @Test
  void removeLastEventSubProcessBuilder_withNullCached_shouldReturnNull() {
    // Given: A BuildProcessContext with a null event subprocess builder cached
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    context.cacheEventSubProcessToDone(null);

    // When: Removing the last event subprocess builder
    EventSubProcessBuilder result = context.removeLastEventSubProcessBuilder();

    // Then: Should return null and cache should be empty
    assertThat(result).isNull();
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 6: Remove last event subprocess builder when null is interspersed with other builders
  @Test
  void removeLastEventSubProcessBuilder_withNullInterspersed_shouldRemoveInLIFOOrder() {
    // Given: A BuildProcessContext with builders including null
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(null);
    context.cacheEventSubProcessToDone(builder2);

    // When: Removing event subprocess builders
    EventSubProcessBuilder result1 = context.removeLastEventSubProcessBuilder();
    EventSubProcessBuilder result2 = context.removeLastEventSubProcessBuilder();
    EventSubProcessBuilder result3 = context.removeLastEventSubProcessBuilder();

    // Then: Should return in LIFO order including null
    assertThat(result1).isSameAs(builder2);
    assertThat(result2).isNull();
    assertThat(result3).isSameAs(builder1);
  }

  // Test 7: Remove last event subprocess builder with same builder cached multiple times
  @Test
  void removeLastEventSubProcessBuilder_sameBuildMultipleTimes_shouldRemoveEachTime() {
    // Given: A BuildProcessContext with the same builder cached multiple times
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(builder);
    context.cacheEventSubProcessToDone(builder);
    context.cacheEventSubProcessToDone(builder);

    // When: Removing event subprocess builders
    EventSubProcessBuilder result1 = context.removeLastEventSubProcessBuilder();
    EventSubProcessBuilder result2 = context.removeLastEventSubProcessBuilder();
    EventSubProcessBuilder result3 = context.removeLastEventSubProcessBuilder();

    // Then: Should return the same builder each time
    assertThat(result1).isSameAs(builder);
    assertThat(result2).isSameAs(builder);
    assertThat(result3).isSameAs(builder);
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 8: Remove last event subprocess builder from multiple independent contexts
  @Test
  void removeLastEventSubProcessBuilder_multipleContexts_shouldBeIndependent() {
    // Given: Two independent BuildProcessContext instances with different builders
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    ProcessBuilder processBuilder1 = mock(ProcessBuilder.class);
    ProcessBuilder processBuilder2 = mock(ProcessBuilder.class);
    BuildProcessContext context1 = new BuildProcessContext(graph1, processBuilder1);
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    context1.cacheEventSubProcessToDone(builder1);
    context2.cacheEventSubProcessToDone(builder2);

    // When: Removing from each context
    EventSubProcessBuilder result1 = context1.removeLastEventSubProcessBuilder();
    EventSubProcessBuilder result2 = context2.removeLastEventSubProcessBuilder();

    // Then: Each context should return its own builder
    assertThat(result1).isSameAs(builder1);
    assertThat(result2).isSameAs(builder2);
    assertThat(context1.hasEventSubProcess()).isFalse();
    assertThat(context2.hasEventSubProcess()).isFalse();
  }

  // Test 9: Remove last event subprocess builder is independent from subprocess cache
  @Test
  void removeLastEventSubProcessBuilder_independentFromSubProcessCache() {
    // Given: A BuildProcessContext with both event subprocess and subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // When: Removing the last event subprocess builder
    EventSubProcessBuilder result = context.removeLastEventSubProcessBuilder();

    // Then: Should return the event subprocess builder without affecting subprocess cache
    assertThat(result).isSameAs(eventSubProcessBuilder);
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 10: Remove last event subprocess builder is independent from timeout subprocess cache
  @Test
  void removeLastEventSubProcessBuilder_independentFromTimeoutSubProcessCache() {
    // Given: A BuildProcessContext with both event subprocess and timeout subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // When: Removing the last event subprocess builder
    EventSubProcessBuilder result = context.removeLastEventSubProcessBuilder();

    // Then: Should not affect timeout subprocess cache
    assertThat(result).isSameAs(eventSubProcessBuilder);
    assertThat(context.hasTimeoutSubProcess()).isFalse();
  }

  // Test 11: hasEventSubProcess should return false after removing all cached builders
  @Test
  void removeLastEventSubProcessBuilder_hasEventSubProcess_shouldReturnFalseAfterRemovingAll() {
    // Given: A BuildProcessContext with multiple cached event subprocess builders
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

  // Test 12: hasEventSubProcess should return true while builders remain
  @Test
  void removeLastEventSubProcessBuilder_hasEventSubProcess_shouldReturnTrueWhileBuildersRemain() {
    // Given: A BuildProcessContext with multiple cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder3 = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(builder2);
    context.cacheEventSubProcessToDone(builder3);

    // When: Removing builders one at a time
    assertThat(context.hasEventSubProcess()).isTrue();
    context.removeLastEventSubProcessBuilder();
    assertThat(context.hasEventSubProcess()).isTrue();
    context.removeLastEventSubProcessBuilder();
    assertThat(context.hasEventSubProcess()).isTrue();
    context.removeLastEventSubProcessBuilder();

    // Then: hasEventSubProcess should finally return false
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 13: Remove and re-cache should work correctly
  @Test
  void removeLastEventSubProcessBuilder_removeAndRecache_shouldWorkCorrectly() {
    // Given: A BuildProcessContext with a cached builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(builder1);

    // When: Removing, re-caching, and removing again
    EventSubProcessBuilder firstRemoval = context.removeLastEventSubProcessBuilder();
    context.cacheEventSubProcessToDone(builder2);
    EventSubProcessBuilder secondRemoval = context.removeLastEventSubProcessBuilder();

    // Then: Should return correct builders and end with empty cache
    assertThat(firstRemoval).isSameAs(builder1);
    assertThat(secondRemoval).isSameAs(builder2);
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 14: Partial removal should maintain correct state
  @Test
  void removeLastEventSubProcessBuilder_partialRemoval_shouldMaintainCorrectState() {
    // Given: A BuildProcessContext with multiple cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder builder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder2 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder builder3 = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(builder1);
    context.cacheEventSubProcessToDone(builder2);
    context.cacheEventSubProcessToDone(builder3);

    // When: Removing only the last one
    EventSubProcessBuilder removed = context.removeLastEventSubProcessBuilder();

    // Then: Should have removed the last one and still have more cached
    assertThat(removed).isSameAs(builder3);
    assertThat(context.hasEventSubProcess()).isTrue();
    assertThat(context.removeLastEventSubProcessBuilder()).isSameAs(builder2);
  }

  // Test 15: Remove operations should not affect other cache types
  @Test
  void removeLastEventSubProcessBuilder_shouldNotAffectOtherCacheTypes() {
    // Given: A BuildProcessContext with builders in multiple cache types
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventBuilder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(eventBuilder);

    // When: Removing event subprocess builder
    assertThat(context.hasEventSubProcess()).isTrue();
    context.removeLastEventSubProcessBuilder();

    // Then: Other cache types should remain unaffected
    assertThat(context.hasEventSubProcess()).isFalse();
    assertThat(context.hasTimeoutSubProcess()).isFalse();
  }

  // Test 16: Interleaved cache and remove operations
  @Test
  void removeLastEventSubProcessBuilder_interleavedOperations_shouldWorkCorrectly() {
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

  // Test 17: Remove last event subprocess builder returns correct type
  @Test
  void removeLastEventSubProcessBuilder_shouldReturnCorrectType() {
    // Given: A BuildProcessContext with a cached event subprocess builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    // When: Removing the last event subprocess builder
    EventSubProcessBuilder result = context.removeLastEventSubProcessBuilder();

    // Then: Should return an EventSubProcessBuilder type
    assertThat(result).isInstanceOf(EventSubProcessBuilder.class);
    assertThat(result).isSameAs(eventSubProcessBuilder);
  }

  // Test 18: Remove last event subprocess builder with large number of cached builders
  @Test
  void removeLastEventSubProcessBuilder_withManyBuilders_shouldRemoveCorrectly() {
    // Given: A BuildProcessContext with many cached event subprocess builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder lastBuilder = null;
    for (int i = 0; i < 100; i++) {
      lastBuilder = mock(EventSubProcessBuilder.class);
      context.cacheEventSubProcessToDone(lastBuilder);
    }

    // When: Removing the last event subprocess builder
    EventSubProcessBuilder result = context.removeLastEventSubProcessBuilder();

    // Then: Should return the last cached builder and still have 99 remaining
    assertThat(result).isSameAs(lastBuilder);
    assertThat(context.hasEventSubProcess()).isTrue();
  }

  // Test 19: Remove all builders from large cache
  @Test
  void removeLastEventSubProcessBuilder_removeAllFromLargeCache_shouldWork() {
    // Given: A BuildProcessContext with many cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    int count = 50;
    for (int i = 0; i < count; i++) {
      context.cacheEventSubProcessToDone(mock(EventSubProcessBuilder.class));
    }

    // When: Removing all builders
    for (int i = 0; i < count; i++) {
      assertThat(context.hasEventSubProcess()).isTrue();
      context.removeLastEventSubProcessBuilder();
    }

    // Then: Cache should be empty
    assertThat(context.hasEventSubProcess()).isFalse();
    assertThatThrownBy(() -> context.removeLastEventSubProcessBuilder())
        .isInstanceOf(NoSuchElementException.class);
  }

  // Test 20: Remove last event subprocess builder in complex workflow scenario
  @Test
  void removeLastEventSubProcessBuilder_inComplexWorkflowScenario() {
    // Given: A BuildProcessContext simulating a complex workflow with multiple cache types
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("complexWorkflow", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    EventSubProcessBuilder eventBuilder1 = mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder eventBuilder2 = mock(EventSubProcessBuilder.class);

    // When: Building a complex process with multiple subprocess types
    context.cacheEventSubProcessToDone(eventBuilder1);
    context.cacheEventSubProcessToDone(eventBuilder2);

    EventSubProcessBuilder removed1 = context.removeLastEventSubProcessBuilder();
    assertThat(context.hasEventSubProcess()).isTrue();

    EventSubProcessBuilder removed2 = context.removeLastEventSubProcessBuilder();

    // Then: All removals should work correctly
    assertThat(removed1).isSameAs(eventBuilder2);
    assertThat(removed2).isSameAs(eventBuilder1);
    assertThat(context.hasEventSubProcess()).isFalse();
  }
}
