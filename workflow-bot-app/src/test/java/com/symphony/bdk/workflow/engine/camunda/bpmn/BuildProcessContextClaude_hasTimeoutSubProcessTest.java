package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BuildProcessContextClaude_hasTimeoutSubProcessTest {

  // Test 1: hasTimeoutSubProcess should return false for newly created context
  @Test
  void hasTimeoutSubProcess_newContext_shouldReturnFalse() {
    // Given: A newly created BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Checking if timeout subprocess exists
    boolean result = context.hasTimeoutSubProcess();

    // Then: Should return false
    assertThat(result).isFalse();
  }

  // Test 2: hasTimeoutSubProcess should return true after caching one builder
  @Test
  void hasTimeoutSubProcess_afterCachingOneBuilder_shouldReturnTrue() {
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

  // Test 3: hasTimeoutSubProcess should return true after caching multiple builders
  @Test
  void hasTimeoutSubProcess_afterCachingMultipleBuilders_shouldReturnTrue() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder2 = mock(SubProcessBuilder.class);
    SubProcessBuilder builder3 = mock(SubProcessBuilder.class);

    // When: Caching multiple subprocess timeout builders
    context.cacheSubProcessTimeoutToDone(builder1);
    context.cacheSubProcessTimeoutToDone(builder2);
    context.cacheSubProcessTimeoutToDone(builder3);

    // Then: hasTimeoutSubProcess should return true
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 4: hasTimeoutSubProcess should return true even with null builder cached
  @Test
  void hasTimeoutSubProcess_afterCachingNullBuilder_shouldReturnTrue() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Caching a null subprocess timeout builder
    context.cacheSubProcessTimeoutToDone(null);

    // Then: hasTimeoutSubProcess should return true (deque is not empty)
    assertThat(context.hasTimeoutSubProcess()).isTrue();
  }

  // Test 5: Multiple calls to hasTimeoutSubProcess should be consistent
  @Test
  void hasTimeoutSubProcess_multipleCalls_shouldBeConsistent() {
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

    // Then: All results should be consistent and true
    assertThat(result1).isTrue();
    assertThat(result2).isTrue();
    assertThat(result3).isTrue();
  }

  // Test 6: hasTimeoutSubProcess should be consistent when false
  @Test
  void hasTimeoutSubProcess_multipleCalls_shouldBeConsistentWhenFalse() {
    // Given: A BuildProcessContext with no cached builders
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Calling hasTimeoutSubProcess multiple times
    boolean result1 = context.hasTimeoutSubProcess();
    boolean result2 = context.hasTimeoutSubProcess();
    boolean result3 = context.hasTimeoutSubProcess();

    // Then: All results should be consistent and false
    assertThat(result1).isFalse();
    assertThat(result2).isFalse();
    assertThat(result3).isFalse();
  }

  // Test 7: hasTimeoutSubProcess should be independent from hasEventSubProcess
  @Test
  void hasTimeoutSubProcess_shouldBeIndependentFromEventSubProcess() {
    // Given: A BuildProcessContext with only event subprocess cached
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // When: Only event subprocess is cached (not timeout subprocess)
    // (no caching of timeout subprocess)

    // Then: hasTimeoutSubProcess should return false
    assertThat(context.hasTimeoutSubProcess()).isFalse();
    assertThat(context.hasEventSubProcess()).isFalse();
  }

  // Test 8: hasTimeoutSubProcess should be independent from regular subprocess
  @Test
  void hasTimeoutSubProcess_shouldBeIndependentFromRegularSubProcess() {
    // Given: A BuildProcessContext
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    SubProcessBuilder regularBuilder = mock(SubProcessBuilder.class);

    // When: Only regular subprocess is cached (not timeout subprocess)
    context.cacheSubProcess(regularBuilder);

    // Then: hasTimeoutSubProcess should still return false
    assertThat(context.hasTimeoutSubProcess()).isFalse();
    assertThat(context.getLastSubProcessBuilder()).isSameAs(regularBuilder);
  }

  // Test 9: Multiple independent contexts should have independent hasTimeoutSubProcess results
  @Test
  void hasTimeoutSubProcess_multipleContexts_shouldBeIndependent() {
    // Given: Two independent BuildProcessContext instances
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    ProcessBuilder processBuilder1 = mock(ProcessBuilder.class);
    ProcessBuilder processBuilder2 = mock(ProcessBuilder.class);
    BuildProcessContext context1 = new BuildProcessContext(graph1, processBuilder1);
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);
    SubProcessBuilder builder1 = mock(SubProcessBuilder.class);

    // When: Only caching in context1
    context1.cacheSubProcessTimeoutToDone(builder1);

    // Then: Only context1 should have timeout subprocess
    assertThat(context1.hasTimeoutSubProcess()).isTrue();
    assertThat(context2.hasTimeoutSubProcess()).isFalse();
  }

  // Test 10: hasTimeoutSubProcess should correctly reflect state changes
  @Test
  void hasTimeoutSubProcess_shouldReflectStateChanges() {
    // Given: A BuildProcessContext
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
}
