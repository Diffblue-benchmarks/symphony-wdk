package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.NodeView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.monitoring.service.MonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowsApiControllerClaude_getWorkflowGraphNodesTest {

  @Mock
  private MonitoringService monitoringService;

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  private WorkflowsApiController controller;

  @BeforeEach
  void setUp() {
    controller = new WorkflowsApiController(monitoringService, workflowEngine);
  }

  // ==================== Basic Functionality Tests ====================

  @Test
  void getWorkflowGraphNodes_withMinimalParameters_shouldReturnWorkflowNodes() {
    // Given: Only required parameters provided
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .flowNodes(Collections.emptyList())
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes with null version
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return OK with workflow nodes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isSameAs(expectedView);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, null);
  }

  @Test
  void getWorkflowGraphNodes_withAllParameters_shouldReturnWorkflowNodes() {
    // Given: All parameters provided
    String workflowId = "workflow-1";
    Long version = 5L;
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(version)
        .flowNodes(Collections.emptyList())
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, version))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes with version
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", version);

    // Then: Should return OK with workflow nodes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isSameAs(expectedView);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, version);
  }

  @Test
  void getWorkflowGraphNodes_withEmptyFlowNodes_shouldReturnEmptyList() {
    // Given: Workflow with empty flow nodes
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .flowNodes(Collections.emptyList())
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return workflow with empty flow nodes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).isEmpty();
  }

  @Test
  void getWorkflowGraphNodes_withMultipleFlowNodes_shouldReturnAllNodes() {
    // Given: Workflow with multiple flow nodes
    String workflowId = "workflow-1";

    NodeView node1 = NodeView.builder()
        .nodeId("node-1")
        .type("activity")
        .group("group-1")
        .parents(Collections.emptyList())
        .children(Collections.emptyList())
        .build();

    NodeView node2 = NodeView.builder()
        .nodeId("node-2")
        .type("gateway")
        .group("group-2")
        .parents(Arrays.asList("node-1"))
        .children(Collections.emptyList())
        .build();

    NodeView node3 = NodeView.builder()
        .nodeId("node-3")
        .type("event")
        .group("group-3")
        .parents(Arrays.asList("node-2"))
        .children(Collections.emptyList())
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .flowNodes(Arrays.asList(node1, node2, node3))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return all flow nodes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).hasSize(3);
    assertThat(response.getBody().getFlowNodes().get(0).getNodeId()).isEqualTo("node-1");
    assertThat(response.getBody().getFlowNodes().get(1).getNodeId()).isEqualTo("node-2");
    assertThat(response.getBody().getFlowNodes().get(2).getNodeId()).isEqualTo("node-3");
  }

  // ==================== WorkflowId Parameter Tests ====================

  @Test
  void getWorkflowGraphNodes_withValidWorkflowId_shouldPassToMonitoringService() {
    // Given: Valid workflow ID
    String workflowId = "my-workflow-123";
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should pass workflow ID to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, null);
  }

  @Test
  void getWorkflowGraphNodes_withWorkflowIdContainingSpecialCharacters_shouldWork() {
    // Given: Workflow ID with special characters
    String workflowId = "workflow-with-dashes_and_underscores.and.dots";
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should work correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, null);
  }

  @Test
  void getWorkflowGraphNodes_withNullWorkflowId_shouldPassNullToMonitoringService() {
    // Given: Null workflow ID
    String workflowId = null;
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should pass null to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).getWorkflowDefinition(null, null);
  }

  @Test
  void getWorkflowGraphNodes_withEmptyWorkflowId_shouldPassEmptyStringToMonitoringService() {
    // Given: Empty workflow ID
    String workflowId = "";
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should pass empty string to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).getWorkflowDefinition("", null);
  }

  // ==================== Token Parameter Tests ====================

  @Test
  void getWorkflowGraphNodes_withNullToken_shouldStillCallMonitoringService() {
    // Given: Token is null
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes with null token
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, null, null);

    // Then: Should still call monitoring service (authorization handled by @Authorized)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, null);
  }

  @Test
  void getWorkflowGraphNodes_withEmptyToken_shouldStillCallMonitoringService() {
    // Given: Token is empty
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes with empty token
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "", null);

    // Then: Should still call monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, null);
  }

  @Test
  void getWorkflowGraphNodes_withValidToken_shouldCallMonitoringService() {
    // Given: Valid token
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes with valid token
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "valid-token-12345", null);

    // Then: Should call monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, null);
  }

  // ==================== Version Parameter Tests ====================

  @Test
  void getWorkflowGraphNodes_withNullVersion_shouldPassNullToMonitoringService() {
    // Given: Version is null
    String workflowId = "workflow-1";
    Long version = null;
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, version))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", version);

    // Then: Should pass null version to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, null);
  }

  @Test
  void getWorkflowGraphNodes_withVersion1_shouldPassToMonitoringService() {
    // Given: Version is 1
    String workflowId = "workflow-1";
    Long version = 1L;
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .version(version)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, version))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", version);

    // Then: Should pass version 1 to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getVersion()).isEqualTo(1L);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, 1L);
  }

  @Test
  void getWorkflowGraphNodes_withLargeVersion_shouldPassToMonitoringService() {
    // Given: Version is a large number
    String workflowId = "workflow-1";
    Long version = 999999L;
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .version(version)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, version))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", version);

    // Then: Should pass large version to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getVersion()).isEqualTo(999999L);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, 999999L);
  }

  @Test
  void getWorkflowGraphNodes_withVersionZero_shouldPassToMonitoringService() {
    // Given: Version is 0
    String workflowId = "workflow-1";
    Long version = 0L;
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .version(version)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, version))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", version);

    // Then: Should pass version 0 to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getVersion()).isEqualTo(0L);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, 0L);
  }

  @Test
  void getWorkflowGraphNodes_withMaxLongVersion_shouldPassToMonitoringService() {
    // Given: Version is Long.MAX_VALUE
    String workflowId = "workflow-1";
    Long version = Long.MAX_VALUE;
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .version(version)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, version))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", version);

    // Then: Should pass Long.MAX_VALUE to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getVersion()).isEqualTo(Long.MAX_VALUE);
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, Long.MAX_VALUE);
  }

  // ==================== NodeView Tests ====================

  @Test
  void getWorkflowGraphNodes_withNodeHavingAllFields_shouldReturnNodeWithAllFields() {
    // Given: Node with all fields populated
    String workflowId = "workflow-1";

    NodeView.ChildView child1 = NodeView.ChildView.of("child-1", "condition-1");
    NodeView.ChildView child2 = NodeView.ChildView.of("child-2");

    NodeView node = NodeView.builder()
        .nodeId("node-1")
        .type("activity")
        .group("group-1")
        .parents(Arrays.asList("parent-1", "parent-2"))
        .children(Arrays.asList(child1, child2))
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .flowNodes(Collections.singletonList(node))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return node with all fields
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).hasSize(1);

    NodeView returnedNode = response.getBody().getFlowNodes().get(0);
    assertThat(returnedNode.getNodeId()).isEqualTo("node-1");
    assertThat(returnedNode.getType()).isEqualTo("activity");
    assertThat(returnedNode.getGroup()).isEqualTo("group-1");
    assertThat(returnedNode.getParents()).containsExactly("parent-1", "parent-2");
    assertThat(returnedNode.getChildren()).hasSize(2);
    assertThat(returnedNode.getChildren().get(0).getNodeId()).isEqualTo("child-1");
    assertThat(returnedNode.getChildren().get(0).getCondition()).isEqualTo("condition-1");
    assertThat(returnedNode.getChildren().get(1).getNodeId()).isEqualTo("child-2");
  }

  @Test
  void getWorkflowGraphNodes_withNodeHavingNullFields_shouldReturnNodeWithNullFields() {
    // Given: Node with null fields
    String workflowId = "workflow-1";

    NodeView node = NodeView.builder()
        .nodeId(null)
        .type(null)
        .group(null)
        .parents(null)
        .children(null)
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .flowNodes(Collections.singletonList(node))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return node with null fields
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).hasSize(1);

    NodeView returnedNode = response.getBody().getFlowNodes().get(0);
    assertThat(returnedNode.getNodeId()).isNull();
    assertThat(returnedNode.getType()).isNull();
    assertThat(returnedNode.getGroup()).isNull();
    assertThat(returnedNode.getParents()).isNull();
    assertThat(returnedNode.getChildren()).isNull();
  }

  @Test
  void getWorkflowGraphNodes_withNodeHavingEmptyParentsAndChildren_shouldReturnEmptyLists() {
    // Given: Node with empty parents and children lists
    String workflowId = "workflow-1";

    NodeView node = NodeView.builder()
        .nodeId("node-1")
        .type("activity")
        .parents(Collections.emptyList())
        .children(Collections.emptyList())
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .flowNodes(Collections.singletonList(node))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return node with empty lists
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).hasSize(1);

    NodeView returnedNode = response.getBody().getFlowNodes().get(0);
    assertThat(returnedNode.getParents()).isEmpty();
    assertThat(returnedNode.getChildren()).isEmpty();
  }

  @Test
  void getWorkflowGraphNodes_withNodeHavingMultipleParents_shouldReturnAllParents() {
    // Given: Node with multiple parents
    String workflowId = "workflow-1";

    NodeView node = NodeView.builder()
        .nodeId("node-1")
        .type("gateway")
        .parents(Arrays.asList("parent-1", "parent-2", "parent-3"))
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .flowNodes(Collections.singletonList(node))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return node with all parents
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).hasSize(1);

    NodeView returnedNode = response.getBody().getFlowNodes().get(0);
    assertThat(returnedNode.getParents()).hasSize(3);
    assertThat(returnedNode.getParents()).containsExactly("parent-1", "parent-2", "parent-3");
  }

  @Test
  void getWorkflowGraphNodes_withNodeHavingMultipleChildren_shouldReturnAllChildren() {
    // Given: Node with multiple children
    String workflowId = "workflow-1";

    NodeView.ChildView child1 = NodeView.ChildView.of("child-1");
    NodeView.ChildView child2 = NodeView.ChildView.of("child-2", "condition-2");
    NodeView.ChildView child3 = NodeView.ChildView.of("child-3");

    NodeView node = NodeView.builder()
        .nodeId("node-1")
        .type("gateway")
        .children(Arrays.asList(child1, child2, child3))
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .flowNodes(Collections.singletonList(node))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return node with all children
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).hasSize(1);

    NodeView returnedNode = response.getBody().getFlowNodes().get(0);
    assertThat(returnedNode.getChildren()).hasSize(3);
    assertThat(returnedNode.getChildren().get(0).getNodeId()).isEqualTo("child-1");
    assertThat(returnedNode.getChildren().get(1).getNodeId()).isEqualTo("child-2");
    assertThat(returnedNode.getChildren().get(1).getCondition()).isEqualTo("condition-2");
    assertThat(returnedNode.getChildren().get(2).getNodeId()).isEqualTo("child-3");
  }

  @Test
  void getWorkflowGraphNodes_withChildViewHavingCondition_shouldReturnCondition() {
    // Given: Child with condition
    String workflowId = "workflow-1";

    NodeView.ChildView child = NodeView.ChildView.of("child-1", "x > 10");

    NodeView node = NodeView.builder()
        .nodeId("node-1")
        .type("gateway")
        .children(Collections.singletonList(child))
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .flowNodes(Collections.singletonList(node))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return child with condition
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getFlowNodes().get(0).getChildren().get(0).getCondition())
        .isEqualTo("x > 10");
  }

  @Test
  void getWorkflowGraphNodes_withChildViewWithoutCondition_shouldReturnNullCondition() {
    // Given: Child without condition
    String workflowId = "workflow-1";

    NodeView.ChildView child = NodeView.ChildView.of("child-1");

    NodeView node = NodeView.builder()
        .nodeId("node-1")
        .type("activity")
        .children(Collections.singletonList(child))
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .flowNodes(Collections.singletonList(node))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return child with null condition
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getFlowNodes().get(0).getChildren().get(0).getCondition())
        .isNull();
  }

  // ==================== Variables Tests ====================

  @Test
  void getWorkflowGraphNodes_withVariables_shouldReturnVariables() {
    // Given: Workflow with variables
    String workflowId = "workflow-1";

    Map<String, Object> variables = new HashMap<>();
    variables.put("var1", "value1");
    variables.put("var2", 42);
    variables.put("var3", true);

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .variables(variables)
        .flowNodes(Collections.emptyList())
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return workflow with variables
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getVariables()).isNotNull();
    assertThat(response.getBody().getVariables()).containsEntry("var1", "value1");
    assertThat(response.getBody().getVariables()).containsEntry("var2", 42);
    assertThat(response.getBody().getVariables()).containsEntry("var3", true);
  }

  @Test
  void getWorkflowGraphNodes_withNullVariables_shouldReturnNullVariables() {
    // Given: Workflow with null variables
    String workflowId = "workflow-1";

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .variables(null)
        .flowNodes(Collections.emptyList())
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return workflow with null variables
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getVariables()).isNull();
  }

  @Test
  void getWorkflowGraphNodes_withEmptyVariables_shouldReturnEmptyVariablesMap() {
    // Given: Workflow with empty variables map
    String workflowId = "workflow-1";

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .variables(Collections.emptyMap())
        .flowNodes(Collections.emptyList())
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return workflow with empty variables
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getVariables()).isEmpty();
  }

  // ==================== Large Dataset Tests ====================

  @Test
  void getWorkflowGraphNodes_withLargeNumberOfNodes_shouldReturnAllNodes() {
    // Given: Workflow with many nodes
    String workflowId = "workflow-1";

    List<NodeView> nodes = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      nodes.add(NodeView.builder()
          .nodeId("node-" + i)
          .type("activity")
          .group("group-" + (i % 10))
          .build());
    }

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .flowNodes(nodes)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return all 100 nodes
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).hasSize(100);
    assertThat(response.getBody().getFlowNodes().get(0).getNodeId()).isEqualTo("node-0");
    assertThat(response.getBody().getFlowNodes().get(99).getNodeId()).isEqualTo("node-99");
  }

  // ==================== Service Interaction Tests ====================

  @Test
  void getWorkflowGraphNodes_shouldCallMonitoringServiceOnce() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should call monitoringService exactly once
    verify(monitoringService, times(1)).getWorkflowDefinition(workflowId, null);
  }

  @Test
  void getWorkflowGraphNodes_shouldNotInteractWithWorkflowEngine() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should not interact with workflowEngine at all
    verifyNoInteractions(workflowEngine);
  }

  // ==================== Response Entity Tests ====================

  @Test
  void getWorkflowGraphNodes_shouldReturnHttpStatusOK() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return HTTP 200 OK
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getWorkflowGraphNodes_shouldReturnNonNullBody() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder().build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Body should not be null
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  void getWorkflowGraphNodes_shouldReturnSameViewAsMonitoringService() {
    // Given: MonitoringService returns specific view
    String workflowId = "workflow-1";
    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Body should be the same view returned by monitoringService
    assertThat(response.getBody()).isSameAs(expectedView);
  }

  // ==================== Edge Cases ====================

  @Test
  void getWorkflowGraphNodes_withComplexWorkflowGraph_shouldReturnCompleteStructure() {
    // Given: Complex workflow graph with interconnected nodes
    String workflowId = "complex-workflow";

    NodeView startNode = NodeView.builder()
        .nodeId("start")
        .type("event")
        .group("start-events")
        .parents(Collections.emptyList())
        .children(Arrays.asList(NodeView.ChildView.of("activity-1")))
        .build();

    NodeView activity1 = NodeView.builder()
        .nodeId("activity-1")
        .type("activity")
        .group("activities")
        .parents(Collections.singletonList("start"))
        .children(Arrays.asList(NodeView.ChildView.of("gateway-1")))
        .build();

    NodeView gateway1 = NodeView.builder()
        .nodeId("gateway-1")
        .type("gateway")
        .group("gateways")
        .parents(Collections.singletonList("activity-1"))
        .children(Arrays.asList(
            NodeView.ChildView.of("activity-2", "condition-A"),
            NodeView.ChildView.of("activity-3", "condition-B")
        ))
        .build();

    NodeView activity2 = NodeView.builder()
        .nodeId("activity-2")
        .type("activity")
        .group("activities")
        .parents(Collections.singletonList("gateway-1"))
        .children(Arrays.asList(NodeView.ChildView.of("end")))
        .build();

    NodeView activity3 = NodeView.builder()
        .nodeId("activity-3")
        .type("activity")
        .group("activities")
        .parents(Collections.singletonList("gateway-1"))
        .children(Arrays.asList(NodeView.ChildView.of("end")))
        .build();

    NodeView endNode = NodeView.builder()
        .nodeId("end")
        .type("event")
        .group("end-events")
        .parents(Arrays.asList("activity-2", "activity-3"))
        .children(Collections.emptyList())
        .build();

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .version(1L)
        .flowNodes(Arrays.asList(startNode, activity1, gateway1, activity2, activity3, endNode))
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return complete complex structure
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getFlowNodes()).hasSize(6);

    // Verify gateway with conditions
    NodeView gatewayNode = response.getBody().getFlowNodes().get(2);
    assertThat(gatewayNode.getNodeId()).isEqualTo("gateway-1");
    assertThat(gatewayNode.getChildren()).hasSize(2);
    assertThat(gatewayNode.getChildren().get(0).getCondition()).isEqualTo("condition-A");
    assertThat(gatewayNode.getChildren().get(1).getCondition()).isEqualTo("condition-B");

    // Verify end node with multiple parents
    NodeView endNodeView = response.getBody().getFlowNodes().get(5);
    assertThat(endNodeView.getNodeId()).isEqualTo("end");
    assertThat(endNodeView.getParents()).containsExactly("activity-2", "activity-3");
  }

  @Test
  void getWorkflowGraphNodes_withNodeTypesVariety_shouldReturnAllTypes() {
    // Given: Workflow with various node types
    String workflowId = "workflow-1";

    List<NodeView> nodes = Arrays.asList(
        NodeView.builder().nodeId("n1").type("event").build(),
        NodeView.builder().nodeId("n2").type("activity").build(),
        NodeView.builder().nodeId("n3").type("gateway").build(),
        NodeView.builder().nodeId("n4").type("subprocess").build(),
        NodeView.builder().nodeId("n5").type("task").build()
    );

    WorkflowNodesView expectedView = WorkflowNodesView.builder()
        .workflowId(workflowId)
        .flowNodes(nodes)
        .build();

    when(monitoringService.getWorkflowDefinition(workflowId, null))
        .thenReturn(expectedView);

    // When: Calling getWorkflowGraphNodes
    ResponseEntity<WorkflowNodesView> response =
        controller.getWorkflowGraphNodes(workflowId, "test-token", null);

    // Then: Should return all different node types
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getFlowNodes()).hasSize(5);
    assertThat(response.getBody().getFlowNodes().get(0).getType()).isEqualTo("event");
    assertThat(response.getBody().getFlowNodes().get(1).getType()).isEqualTo("activity");
    assertThat(response.getBody().getFlowNodes().get(2).getType()).isEqualTo("gateway");
    assertThat(response.getBody().getFlowNodes().get(3).getType()).isEqualTo("subprocess");
    assertThat(response.getBody().getFlowNodes().get(4).getType()).isEqualTo("task");
  }
}
