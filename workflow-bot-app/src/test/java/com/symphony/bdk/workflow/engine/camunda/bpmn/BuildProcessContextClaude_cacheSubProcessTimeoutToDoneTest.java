package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class BuildProcessContextClaude_cacheSubProcessTimeoutToDoneTest {

  // Test 1: Cache a single subprocess timeout builder should allow retrieval via removeLastSubProcessTimeoutBuilder
  @Test
  void cacheSubProcessTimeoutToDone_withSingleBuilder_shouldCacheSuccessfully() {
    // Given: A BuildProcessContext with no cached subprocess timeout builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);

    // When: Caching a subprocess timeout builder
    context.cacheSubProcessTimeoutToDone(subProcessBuilder);

    // Then: The builder should be cached and hasTimeoutSubProcess should return true
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 2: Cache multiple subprocess timeout builders should maintain stack
  @Test
  void cacheSubProcessTimeoutToDone_withMultipleBuilders_shouldMaintainStack() {
    // Given: A BuildProcessContext with no cached subprocess timeout builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder firstBuilder = mock(SubProcessBuilder.class);
    SubProcessBuilder secondBuilder = mock(SubProcessBuilder.class);
    SubProcessBuilder thirdBuilder = mock(SubProcessBuilder.class);

    // When: Caching multiple subprocess timeout builders
    context.cacheSubProcessTimeoutToDone(firstBuilder);
    context.cacheSubProcessTimeoutToDone(secondBuilder);
    context.cacheSubProcessTimeoutToDone(thirdBuilder);

    // Then: All builders should be cached
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 3: hasTimeoutSubProcess should return false when none is cached
  @Test
  void cacheSubProcessTimeoutToDone_hasTimeoutSubProcess_shouldReturnFalseInitially() {
    // Given: A BuildProcessContext with no cached subprocess timeout builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Checking if timeout subprocess exists
    boolean result = context.hasTimeoutSubProcess();

    // Then: Should return false
    assertThat(result).isFalse();
  }

  // Test 4: hasTimeoutSubProcess should return true after caching
  @Test
  void cacheSubProcessTimeoutToDone_hasTimeoutSubProcess_shouldReturnTrueAfterCaching() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder = mock(SubProcessBuilder.class);

    // When: Caching a subprocess timeout builder
    context.cacheSubProcessTimeoutToDone(builder);

    // Then: hasTimeoutSubProcess should return true
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 5: Cache the same subprocess timeout builder multiple times
  @Test
  void cacheSubProcessTimeoutToDone_sameBuilderMultipleTimes_shouldCacheEachTime() {
    // Given: A BuildProcessContext and a single subprocess timeout builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder = mock(SubProcessBuilder.class);

    // When: Caching the same builder multiple times
    context.cacheSubProcessTimeoutToDone(builder);
    context.cacheSubProcessTimeoutToDone(builder);
    context.cacheSubProcessTimeoutToDone(builder);

    // Then: hasTimeoutSubProcess should still return true
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 6: Cache null subprocess timeout builder should not fail (and can be cached)
  @Test
  void cacheSubProcessTimeoutToDone_withNullBuilder_shouldCacheNull() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Caching a null subprocess timeout builder
    context.cacheSubProcessTimeoutToDone(null);

    // Then: hasTimeoutSubProcess should return true
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 7: Cache subprocess timeout builders with null interspersed
  @Test
  void cacheSubProcessTimeoutToDone_withNullInterspersed_shouldHandleCorrectly() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);

    // When: Caching builders with null interspersed
    context.cacheSubProcessTimeoutToDone(builder1);
    context.cacheSubProcessTimeoutToDone(null);
    context.cacheSubProcessTimeoutToDone(builder2);

    // Then: hasTimeoutSubProcess should return true
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 8: Multiple contexts should maintain independent caches
  @Test
  void cacheSubProcessTimeoutToDone_multipleContexts_shouldBeIndependent() {
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
    context1.cacheSubProcessTimeoutToDone(builder1);
    context2.cacheSubProcessTimeoutToDone(builder2);

    // Then: Each context should maintain its own cache
    assertThat(context1.hasTimeoutSubProcess()).isTrue();
    assertThat(context2.hasTimeoutSubProcess()).isTrue();
  }

  // Test 9: Cache subprocess timeout builder independently from other cache types
  @Test
  void cacheSubProcessTimeoutToDone_independentFromOtherCaches_shouldWorkCorrectly() {
    // Given: A BuildProcessContext with builders in different caches
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder subProcessTimeoutBuilder = mock(SubProcessBuilder.class);

    // When: Caching subprocess timeout builder
    context.cacheSubProcessTimeoutToDone(subProcessTimeoutBuilder);

    // Then: Should have timeout subprocess but not event subprocess
    assertThat(context.hasTimeoutSubProcess()).isTrue();
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 10: Cache large number of subprocess timeout builders
  @Test
  void cacheSubProcessTimeoutToDone_withManyBuilders_shouldHandleCorrectly() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Caching a large number of subprocess timeout builders
    for (int i = 0; i < 100; i++) {
      SubProcessBuilder builder = mock(SubProcessBuilder.class);
      context.cacheSubProcessTimeoutToDone(builder);
    }

    // Then: hasTimeoutSubProcess should return true
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 11: hasTimeoutSubProcess should consistently return true with multiple cached builders
  @Test
  void cacheSubProcessTimeoutToDone_hasTimeoutSubProcess_shouldReturnTrueWithMultipleBuilders() {
    // Given: A BuildProcessContext with multiple cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder3 = mock(SubProcessBuilder.class);

    // When: Caching multiple builders
    context.cacheSubProcessTimeoutToDone(builder1);
    context.cacheSubProcessTimeoutToDone(builder2);
    context.cacheSubProcessTimeoutToDone(builder3);

    // Then: hasTimeoutSubProcess should return true
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 12: Cache and remove operations should not affect other cache types
  @Test
  void cacheSubProcessTimeoutToDone_shouldNotAffectOtherCacheTypes() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder timeoutBuilder = mock(SubProcessBuilder.class);

    // When: Caching subprocess timeout builder
    context.cacheSubProcessTimeoutToDone(timeoutBuilder);

    // Then: Other cache types should remain unaffected
    assertThat(context.hasTimeoutSubProcess()).isTrue();
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 13: Multiple calls to hasTimeoutSubProcess should be consistent
  @Test
  void cacheSubProcessTimeoutToDone_hasTimeoutSubProcess_shouldBeConsistent() {
    // Given: A BuildProcessContext with a cached builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder = mock(SubProcessBuilder.class);
    context.cacheSubProcessTimeoutToDone(builder);

    // When: Calling hasTimeoutSubProcess multiple times
    boolean result1 = context.hasTimeoutSubProcess();
    boolean result2 = context.hasTimeoutSubProcess();
    boolean result3 = context.hasTimeoutSubProcess();

    // Then: All results should be consistent
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
    assertThat(result3).isTrue();
  }

  // Test 14: Cache after caching other types (subprocess, eventSubProcess)
  @Test
  void cacheSubProcessTimeoutToDone_afterCachingOtherTypes_shouldWorkIndependently() {
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

  // Test 15: Verify cacheSubProcessTimeoutToDone maintains order for later removal
  @Test
  void cacheSubProcessTimeoutToDone_maintainsOrderForLaterRemoval() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);

    // When: Caching multiple builders
    context.cacheSubProcessTimeoutToDone(builder1);
    context.cacheSubProcessTimeoutToDone(builder2);

    // Then: hasTimeoutSubProcess should indicate builders are present
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 16: Cache builder and verify state before and after
  @Test
  void cacheSubProcessTimeoutToDone_verifyStateBeforeAndAfter() {
    // Given: A BuildProcessContext with no cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder = mock(SubProcessBuilder.class);

    // When: Checking state before and after caching
    boolean beforeCaching = context.hasTimeoutSubProcess();
    context.cacheSubProcessTimeoutToDone(builder);
    boolean afterCaching = context.hasTimeoutSubProcess();

    // Then: State should change from false to true
    assertThat(beforeCaching).isFalse();
    assertThat(afterCaching).isTrue();
  }

  // Test 17: Multiple sequential cache operations
  @Test
  void cacheSubProcessTimeoutToDone_multipleSequentialOperations_shouldWorkCorrectly() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Performing multiple sequential cache operations
    for (int i = 0; i < 10; i++) {
      SubProcessBuilder builder = mock(SubProcessBuilder.class);
      context.cacheSubProcessTimeoutToDone(builder);
      assertThat(context.hasTimeoutSubProcess()).isTrue();
    }

    // Then: All operations should succeed and maintain state
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 18: Cache with varying builder types (all SubProcessBuilder instances)
  @Test
  void cacheSubProcessTimeoutToDone_withVaryingBuilderInstances_shouldCacheAll() {
    // Given: A BuildProcessContext and multiple different builder instances
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Caching different builder instances
    for (int i = 0; i < 5; i++) {
      SubProcessBuilder builder = mock(SubProcessBuilder.class);
      context.cacheSubProcessTimeoutToDone(builder);
    }

    // Then: All should be cached successfully
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 19: Verify method accepts SubProcessBuilder type
  @Test
  void cacheSubProcessTimeoutToDone_acceptsSubProcessBuilderType() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder = mock(SubProcessBuilder.class);

    // When: Caching a SubProcessBuilder instance
    context.cacheSubProcessTimeoutToDone(builder);

    // Then: The builder should be cached without type errors
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 20: Verify caching in a complex workflow scenario
  @Test
  void cacheSubProcessTimeoutToDone_inComplexWorkflowScenario() {
    // Given: A BuildProcessContext simulating a complex workflow
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("complexWorkflow", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder normalSubProcess = mock(SubProcessBuilder.class);
    SubProcessBuilder timeoutSubProcess1 = mock(SubProcessBuilder.class);
    SubProcessBuilder timeoutSubProcess2 = mock(SubProcessBuilder.class);

    // When: Building a complex process with multiple subprocess types
    context.cacheSubProcess(normalSubProcess);
    context.cacheSubProcessTimeoutToDone(timeoutSubProcess1);
    context.cacheSubProcessTimeoutToDone(timeoutSubProcess2);

    // Then: All cache types should be maintained independently
    assertThat(context.getLastSubProcessBuilder()).isSameAs(normalSubProcess);
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }
}
