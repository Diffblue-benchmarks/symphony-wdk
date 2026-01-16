package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BuildProcessContextClaude_constructorTest {

  // Test 1: Constructor with valid parameters should create instance successfully
  @Test
  void constructor_withValidParameters_shouldCreateInstance() {
    // Given: A workflow graph and process builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Then: The instance should be created successfully
    assertThat(context).isNotNull();
  }

  // Test 2: Constructor should allow access to WorkflowDirectedGraph methods via @Delegate
  @Test
  void constructor_shouldDelegateWorkflowGraphMethods() {
    // Given: A workflow graph with workflowId and version
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("testWorkflow", 42L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Then: Should be able to access delegated methods
    assertThat(context.getWorkflowId()).isEqualTo("testWorkflow");
    assertThat(context.getVersion()).isEqualTo(42L);
  }

  // Test 3: Constructor should delegate dictionary operations
  @Test
  void constructor_shouldDelegateDictionaryOperations() {
    // Given: A workflow graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    WorkflowNode node = new WorkflowNode()
        .id("node1")
        .elementType(WorkflowNodeType.ACTIVITY);

    // When: Creating a BuildProcessContext and using delegated dictionary methods
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    context.registerToDictionary("node1", node);

    // Then: Should be able to access the registered node via delegated methods
    assertThat(context.isRegistered("node1")).isTrue();
    assertThat(context.readWorkflowNode("node1")).isEqualTo(node);
  }

  // Test 4: Constructor should delegate parent tracking operations
  @Test
  void constructor_shouldDelegateParentOperations() {
    // Given: A workflow graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext and using delegated parent methods
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    context.addParent("child1", "parent1");
    context.addParent("child1", "parent2");

    // Then: Should be able to access parents via delegated methods
    assertThat(context.hasSeenBefore("child1")).isTrue();
    assertThat(context.getParents("child1")).containsExactlyInAnyOrder("parent1", "parent2");
  }

  // Test 5: Constructor should delegate start event operations
  @Test
  void constructor_shouldDelegateStartEventOperations() {
    // Given: A workflow graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext and using delegated start event methods
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    context.addStartEvent("startEvent1");
    context.addStartEvent("startEvent2");

    // Then: Should be able to access start events via delegated methods
    assertThat(context.getStartEvents()).containsExactly("startEvent1", "startEvent2");
  }

  // Test 6: Constructor should delegate children operations
  @Test
  void constructor_shouldDelegateChildrenOperations() {
    // Given: A workflow graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext and using delegated children methods
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    WorkflowDirectedGraph.NodeChildren children = context.getChildren("parent1");
    children.addChild("child1").addChild("child2");

    // Then: Should be able to access children via delegated methods
    assertThat(context.readChildren("parent1")).isNotNull();
    assertThat(context.readChildren("parent1").getChildren()).containsExactly("child1", "child2");
  }

  // Test 7: Constructor with null version should work
  @Test
  void constructor_withNullVersion_shouldWork() {
    // Given: A workflow graph with null version
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1");
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Then: Should work and version should be null
    assertThat(context).isNotNull();
    assertThat(context.getVersion()).isNull();
    assertThat(context.getWorkflowId()).isEqualTo("workflow1");
  }

  // Test 8: Constructor should delegate variables operations
  @Test
  void constructor_shouldDelegateVariablesOperations() {
    // Given: A workflow graph
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext and using delegated variables methods
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);
    context.getVariables().put("key1", "value1");
    context.getVariables().put("key2", 42);

    // Then: Should be able to access variables via delegated methods
    assertThat(context.getVariables()).hasSize(2);
    assertThat(context.getVariables().get("key1")).isEqualTo("value1");
    assertThat(context.getVariables().get("key2")).isEqualTo(42);
  }

  // Test 9: Constructor should delegate dictionary getter
  @Test
  void constructor_shouldDelegateDictionaryGetter() {
    // Given: A workflow graph with registered nodes
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    WorkflowNode node1 = new WorkflowNode().id("node1").elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode node2 = new WorkflowNode().id("node2").elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("node1", node1);
    graph.registerToDictionary("node2", node2);

    // When: Creating a BuildProcessContext
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Then: Should be able to access dictionary via delegated method
    assertThat(context.getDictionary()).hasSize(2);
    assertThat(context.getDictionary()).containsKeys("node1", "node2");
    assertThat(context.getDictionary().get("node1")).isEqualTo(node1);
  }

  // Test 10: Constructor should delegate parents getter
  @Test
  void constructor_shouldDelegateParentsGetter() {
    // Given: A workflow graph with parent relationships
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    graph.addParent("child1", "parent1");
    graph.addParent("child2", "parent2");

    // When: Creating a BuildProcessContext
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Then: Should be able to access parents map via delegated method
    assertThat(context.getParents()).hasSize(2);
    assertThat(context.getParents()).containsKeys("child1", "child2");
  }

  // Test 11: Multiple BuildProcessContext instances with different graphs should be independent
  @Test
  void constructor_multipleInstances_shouldBeIndependent() {
    // Given: Two different workflow graphs
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    ProcessBuilder processBuilder1 = mock(ProcessBuilder.class);
    ProcessBuilder processBuilder2 = mock(ProcessBuilder.class);

    // When: Creating two BuildProcessContext instances
    BuildProcessContext context1 = new BuildProcessContext(graph1, processBuilder1);
    BuildProcessContext context2 = new BuildProcessContext(graph2, processBuilder2);

    context1.addStartEvent("start1");
    context2.addStartEvent("start2");

    // Then: Each context should maintain its own state
    assertThat(context1.getWorkflowId()).isEqualTo("workflow1");
    assertThat(context2.getWorkflowId()).isEqualTo("workflow2");
    assertThat(context1.getStartEvents()).containsExactly("start1");
    assertThat(context2.getStartEvents()).containsExactly("start2");
  }

  // Test 12: Constructor with empty workflow ID should work
  @Test
  void constructor_withEmptyWorkflowId_shouldWork() {
    // Given: A workflow graph with empty workflow ID
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Then: Should work with empty workflow ID
    assertThat(context).isNotNull();
    assertThat(context.getWorkflowId()).isEmpty();
    assertThat(context.getVersion()).isEqualTo(1L);
  }

  // Test 13: Constructor should allow using both native and delegated methods
  @Test
  void constructor_shouldAllowBothNativeAndDelegatedMethods() {
    // Given: A workflow graph and process builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    WorkflowNode node = new WorkflowNode().id("node1").elementType(WorkflowNodeType.ACTIVITY);

    // When: Creating a BuildProcessContext and using both types of methods
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Using delegated method
    context.registerToDictionary("node1", node);

    // Using native method (addNodeBuilder is defined in BuildProcessContext itself)
    context.addNodeBuilder("node1", null);

    // Then: Both should work
    assertThat(context.isRegistered("node1")).isTrue();
    assertThat(context.isAlreadyBuilt("node1")).isTrue();
  }

  // Test 14: Constructor should initialize empty collections for camunda builders
  @Test
  void constructor_shouldInitializeEmptyCollections() {
    // Given: A workflow graph and process builder
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Then: BuildProcessContext-specific collections should be initialized
    assertThat(context.hasEventSubProcess()).isFalse();
    assertThat(context.hasTimeoutSubProcess()).isFalse();
  }

  // Test 15: Constructor with version zero should work
  @Test
  void constructor_withVersionZero_shouldWork() {
    // Given: A workflow graph with version 0
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 0L);
    ProcessBuilder processBuilder = mock(ProcessBuilder.class);

    // When: Creating a BuildProcessContext
    BuildProcessContext context = new BuildProcessContext(graph, processBuilder);

    // Then: Should work with version 0
    assertThat(context).isNotNull();
    assertThat(context.getVersion()).isEqualTo(0L);
  }
}
