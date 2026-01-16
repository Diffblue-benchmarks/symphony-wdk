package com.symphony.bdk.workflow.api.v1.controller;

import com.symphony.bdk.workflow.api.v1.dto.VariableView;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
class WorkflowsApiControllerClaude_listWorkflowGlobalVariablesTest {

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
  void listWorkflowGlobalVariables_withMinimalParameters_shouldReturnVariables() {
    // Given: Only required parameters provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    List<VariableView> expectedVariables = Collections.emptyList();

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(expectedVariables);

    // When: Calling listWorkflowGlobalVariables with null Instant parameters
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return OK with variables
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEmpty();
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withAllParameters_shouldReturnVariables() {
    // Given: All parameters provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedBefore = Instant.now();
    Instant updatedAfter = updatedBefore.minus(1, ChronoUnit.HOURS);
    List<VariableView> expectedVariables = Collections.emptyList();

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, updatedAfter))
        .thenReturn(expectedVariables);

    // When: Calling listWorkflowGlobalVariables with all parameters
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", updatedBefore, updatedAfter);

    // Then: Should return OK with variables
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, updatedAfter);
  }

  @Test
  void listWorkflowGlobalVariables_withEmptyList_shouldReturnEmptyList() {
    // Given: MonitoringService returns empty list
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return empty list
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).isEmpty();
  }

  @Test
  void listWorkflowGlobalVariables_withSingleVariable_shouldReturnSingleVariable() {
    // Given: MonitoringService returns a single variable
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView variable = new VariableView();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key1", "value1");
    variable.setOutputs(outputs);
    variable.setRevision(1);
    variable.setUpdateTime(Instant.now());

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return single variable
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getOutputs()).containsEntry("key1", "value1");
    assertThat(response.getBody().get(0).getRevision()).isEqualTo(1);
  }

  @Test
  void listWorkflowGlobalVariables_withMultipleVariables_shouldReturnAllVariables() {
    // Given: MonitoringService returns multiple variables
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView var1 = new VariableView();
    var1.setRevision(1);
    var1.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    VariableView var2 = new VariableView();
    var2.setRevision(2);
    var2.setUpdateTime(Instant.parse("2024-01-15T11:00:00Z"));

    VariableView var3 = new VariableView();
    var3.setRevision(3);
    var3.setUpdateTime(Instant.parse("2024-01-15T12:00:00Z"));

    List<VariableView> variables = Arrays.asList(var1, var2, var3);

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(variables);

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return all variables
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(3);
    assertThat(response.getBody().get(0).getRevision()).isEqualTo(1);
    assertThat(response.getBody().get(1).getRevision()).isEqualTo(2);
    assertThat(response.getBody().get(2).getRevision()).isEqualTo(3);
  }

  // ==================== WorkflowId Parameter Tests ====================

  @Test
  void listWorkflowGlobalVariables_withValidWorkflowId_shouldPassToMonitoringService() {
    // Given: Valid workflow ID
    String workflowId = "my-workflow-123";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should pass workflow ID to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withWorkflowIdContainingSpecialCharacters_shouldWork() {
    // Given: Workflow ID with special characters
    String workflowId = "workflow-with-dashes_and_underscores.and.dots";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should work correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withNullWorkflowId_shouldPassNullToMonitoringService() {
    // Given: Null workflow ID
    String workflowId = null;
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should pass null to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(null, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withEmptyWorkflowId_shouldPassEmptyStringToMonitoringService() {
    // Given: Empty workflow ID
    String workflowId = "";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should pass empty string to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars("", instanceId, null, null);
  }

  // ==================== InstanceId Parameter Tests ====================

  @Test
  void listWorkflowGlobalVariables_withValidInstanceId_shouldPassToMonitoringService() {
    // Given: Valid instance ID
    String workflowId = "workflow-1";
    String instanceId = "my-instance-456";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should pass instance ID to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withInstanceIdContainingSpecialCharacters_shouldWork() {
    // Given: Instance ID with special characters
    String workflowId = "workflow-1";
    String instanceId = "instance-with-dashes_and_underscores.and.dots";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should work correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withNullInstanceId_shouldPassNullToMonitoringService() {
    // Given: Null instance ID
    String workflowId = "workflow-1";
    String instanceId = null;

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should pass null to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, null, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withEmptyInstanceId_shouldPassEmptyStringToMonitoringService() {
    // Given: Empty instance ID
    String workflowId = "workflow-1";
    String instanceId = "";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should pass empty string to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, "", null, null);
  }

  // ==================== Token Parameter Tests ====================

  @Test
  void listWorkflowGlobalVariables_withNullToken_shouldStillCallMonitoringService() {
    // Given: Token is null
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with null token
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, null, null, null);

    // Then: Should still call monitoring service (authorization handled by @Authorized)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withEmptyToken_shouldStillCallMonitoringService() {
    // Given: Token is empty
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with empty token
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "", null, null);

    // Then: Should still call monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withValidToken_shouldCallMonitoringService() {
    // Given: Valid token
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with valid token
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "valid-token-12345", null, null);

    // Then: Should call monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  // ==================== Instant Parameters Tests (updatedBefore, updatedAfter) ====================

  @Test
  void listWorkflowGlobalVariables_withBothNullInstants_shouldPassNullsToMonitoringService() {
    // Given: Both Instant parameters are null
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with null instants
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should pass nulls to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_withOnlyUpdatedBefore_shouldPassToMonitoringService() {
    // Given: Only updatedBefore is provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedBefore = Instant.parse("2024-01-15T10:00:00Z");

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with only updatedBefore
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", updatedBefore, null);

    // Then: Should pass updatedBefore to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, null);
  }

  @Test
  void listWorkflowGlobalVariables_withOnlyUpdatedAfter_shouldPassToMonitoringService() {
    // Given: Only updatedAfter is provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedAfter = Instant.parse("2024-01-15T09:00:00Z");

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, updatedAfter))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with only updatedAfter
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, updatedAfter);

    // Then: Should pass updatedAfter to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, updatedAfter);
  }

  @Test
  void listWorkflowGlobalVariables_withBothInstantsProvided_shouldPassBothToMonitoringService() {
    // Given: Both Instant parameters are provided
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedBefore = Instant.parse("2024-01-15T10:00:00Z");
    Instant updatedAfter = Instant.parse("2024-01-15T09:00:00Z");

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, updatedAfter))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with both instants
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", updatedBefore, updatedAfter);

    // Then: Should pass both instants to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, updatedAfter);
  }

  @Test
  void listWorkflowGlobalVariables_withUpdatedBeforeBeforeUpdatedAfter_shouldPassToMonitoringService() {
    // Given: updatedBefore is chronologically before updatedAfter (logically reversed but valid)
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedBefore = Instant.parse("2024-01-15T09:00:00Z");
    Instant updatedAfter = Instant.parse("2024-01-15T10:00:00Z");

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, updatedAfter))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", updatedBefore, updatedAfter);

    // Then: Should pass both values (validation is MonitoringService's responsibility)
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, updatedAfter);
  }

  @Test
  void listWorkflowGlobalVariables_withSameUpdatedBeforeAndAfter_shouldPassToMonitoringService() {
    // Given: Same instant for updatedBefore and updatedAfter
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant sameInstant = Instant.parse("2024-01-15T10:00:00Z");

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, sameInstant, sameInstant))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with same instant
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", sameInstant, sameInstant);

    // Then: Should pass same instant to monitoring service
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, sameInstant, sameInstant);
  }

  // ==================== VariableView Content Tests ====================

  @Test
  void listWorkflowGlobalVariables_withVariableHavingOutputs_shouldReturnVariableWithOutputs() {
    // Given: Variable with outputs
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("stringVar", "hello");
    outputs.put("intVar", 42);
    outputs.put("boolVar", true);
    outputs.put("doubleVar", 3.14);

    VariableView variable = new VariableView();
    variable.setOutputs(outputs);
    variable.setRevision(1);
    variable.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return variable with all outputs
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    VariableView result = response.getBody().get(0);
    assertThat(result.getOutputs()).containsEntry("stringVar", "hello");
    assertThat(result.getOutputs()).containsEntry("intVar", 42);
    assertThat(result.getOutputs()).containsEntry("boolVar", true);
    assertThat(result.getOutputs()).containsEntry("doubleVar", 3.14);
  }

  @Test
  void listWorkflowGlobalVariables_withVariableHavingNullOutputs_shouldReturnVariableWithNullOutputs() {
    // Given: Variable with null outputs
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView variable = new VariableView();
    variable.setOutputs(null);
    variable.setRevision(1);
    variable.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return variable with null outputs
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getOutputs()).isNull();
  }

  @Test
  void listWorkflowGlobalVariables_withVariableHavingEmptyOutputs_shouldReturnVariableWithEmptyOutputs() {
    // Given: Variable with empty outputs map
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView variable = new VariableView();
    variable.setOutputs(Collections.emptyMap());
    variable.setRevision(1);
    variable.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return variable with empty outputs
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getOutputs()).isEmpty();
  }

  @Test
  void listWorkflowGlobalVariables_withVariableHavingComplexOutputs_shouldReturnVariableWithComplexOutputs() {
    // Given: Variable with complex outputs (nested structures)
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put("nestedKey", "nestedValue");

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("simpleVar", "value");
    outputs.put("listVar", Arrays.asList("item1", "item2", "item3"));
    outputs.put("mapVar", nestedMap);
    outputs.put("nullVar", null);

    VariableView variable = new VariableView();
    variable.setOutputs(outputs);
    variable.setRevision(1);
    variable.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return variable with complex outputs
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    VariableView result = response.getBody().get(0);
    assertThat(result.getOutputs()).containsEntry("simpleVar", "value");
    assertThat(result.getOutputs()).containsKey("listVar");
    assertThat(result.getOutputs()).containsKey("mapVar");
    assertThat(result.getOutputs()).containsEntry("nullVar", null);
  }

  @Test
  void listWorkflowGlobalVariables_withVariableHavingDifferentRevisions_shouldReturnAllRevisions() {
    // Given: Variables with different revisions
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView var1 = new VariableView();
    var1.setRevision(1);
    var1.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    VariableView var2 = new VariableView();
    var2.setRevision(2);
    var2.setUpdateTime(Instant.parse("2024-01-15T11:00:00Z"));

    VariableView var3 = new VariableView();
    var3.setRevision(100);
    var3.setUpdateTime(Instant.parse("2024-01-15T12:00:00Z"));

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Arrays.asList(var1, var2, var3));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return all revisions
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(3);
    assertThat(response.getBody().get(0).getRevision()).isEqualTo(1);
    assertThat(response.getBody().get(1).getRevision()).isEqualTo(2);
    assertThat(response.getBody().get(2).getRevision()).isEqualTo(100);
  }

  @Test
  void listWorkflowGlobalVariables_withVariableHavingZeroRevision_shouldReturnVariableWithZeroRevision() {
    // Given: Variable with revision 0
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView variable = new VariableView();
    variable.setRevision(0);
    variable.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return variable with revision 0
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getRevision()).isEqualTo(0);
  }

  @Test
  void listWorkflowGlobalVariables_withVariableHavingNullUpdateTime_shouldReturnVariableWithNullUpdateTime() {
    // Given: Variable with null updateTime
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView variable = new VariableView();
    variable.setRevision(1);
    variable.setUpdateTime(null);

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return variable with null updateTime
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getUpdateTime()).isNull();
  }

  @Test
  void listWorkflowGlobalVariables_withVariableHavingDifferentUpdateTimes_shouldReturnAllUpdateTimes() {
    // Given: Variables with different update times
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    Instant time1 = Instant.parse("2024-01-15T10:00:00Z");
    Instant time2 = Instant.parse("2024-01-15T11:30:00Z");
    Instant time3 = Instant.parse("2024-01-15T12:45:30Z");

    VariableView var1 = new VariableView();
    var1.setRevision(1);
    var1.setUpdateTime(time1);

    VariableView var2 = new VariableView();
    var2.setRevision(2);
    var2.setUpdateTime(time2);

    VariableView var3 = new VariableView();
    var3.setRevision(3);
    var3.setUpdateTime(time3);

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Arrays.asList(var1, var2, var3));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return all update times
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(3);
    assertThat(response.getBody().get(0).getUpdateTime()).isEqualTo(time1);
    assertThat(response.getBody().get(1).getUpdateTime()).isEqualTo(time2);
    assertThat(response.getBody().get(2).getUpdateTime()).isEqualTo(time3);
  }

  // ==================== Large Dataset Tests ====================

  @Test
  void listWorkflowGlobalVariables_withLargeNumberOfVariables_shouldReturnAllVariables() {
    // Given: MonitoringService returns many variables
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    List<VariableView> variables = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      VariableView var = new VariableView();
      var.setRevision(i);
      var.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z").plusSeconds(i));
      variables.add(var);
    }

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(variables);

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return all 100 variables
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(100);
    assertThat(response.getBody().get(0).getRevision()).isEqualTo(0);
    assertThat(response.getBody().get(99).getRevision()).isEqualTo(99);
  }

  // ==================== Service Interaction Tests ====================

  @Test
  void listWorkflowGlobalVariables_shouldCallMonitoringServiceOnce() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should call monitoringService exactly once
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);
  }

  @Test
  void listWorkflowGlobalVariables_shouldNotInteractWithWorkflowEngine() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should not interact with workflowEngine at all
    verifyNoInteractions(workflowEngine);
  }

  // ==================== Response Entity Tests ====================

  @Test
  void listWorkflowGlobalVariables_shouldReturnHttpStatusOK() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return HTTP 200 OK
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void listWorkflowGlobalVariables_shouldReturnNonNullBody() {
    // Given: MonitoringService setup
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Body should not be null
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  void listWorkflowGlobalVariables_shouldReturnSameListAsMonitoringService() {
    // Given: MonitoringService returns specific variables
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView var1 = new VariableView();
    var1.setRevision(1);

    VariableView var2 = new VariableView();
    var2.setRevision(2);

    List<VariableView> expectedVariables = Arrays.asList(var1, var2);

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(expectedVariables);

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Body should be the same list returned by monitoringService
    assertThat(response.getBody()).isSameAs(expectedVariables);
  }

  // ==================== Edge Cases ====================

  @Test
  void listWorkflowGlobalVariables_withInstantAtEpoch_shouldHandleCorrectly() {
    // Given: Instant at epoch (1970-01-01T00:00:00Z)
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant epoch = Instant.EPOCH;

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, epoch, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with epoch instant
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", epoch, null);

    // Then: Should handle epoch correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, epoch, null);
  }

  @Test
  void listWorkflowGlobalVariables_withVeryFarFutureInstant_shouldHandleCorrectly() {
    // Given: Very far future instant
    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant farFuture = Instant.parse("2999-12-31T23:59:59Z");

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, farFuture, null))
        .thenReturn(Collections.emptyList());

    // When: Calling listWorkflowGlobalVariables with far future instant
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", farFuture, null);

    // Then: Should handle far future correctly
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(monitoringService, times(1)).listWorkflowInstanceGlobalVars(workflowId, instanceId, farFuture, null);
  }

  @Test
  void listWorkflowGlobalVariables_withVariableHavingAllNullFields_shouldReturnVariable() {
    // Given: Variable with all null fields
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView variable = new VariableView();
    variable.setOutputs(null);
    variable.setRevision(0);
    variable.setUpdateTime(null);

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return variable with null fields
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getOutputs()).isNull();
    assertThat(response.getBody().get(0).getRevision()).isEqualTo(0);
    assertThat(response.getBody().get(0).getUpdateTime()).isNull();
  }

  @Test
  void listWorkflowGlobalVariables_withNegativeRevision_shouldReturnVariableWithNegativeRevision() {
    // Given: Variable with negative revision (edge case)
    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    VariableView variable = new VariableView();
    variable.setRevision(-1);
    variable.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .thenReturn(Collections.singletonList(variable));

    // When: Calling listWorkflowGlobalVariables
    ResponseEntity<List<VariableView>> response =
        controller.listWorkflowGlobalVariables(workflowId, instanceId, "test-token", null, null);

    // Then: Should return variable with negative revision
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody().get(0).getRevision()).isEqualTo(-1);
  }
}
