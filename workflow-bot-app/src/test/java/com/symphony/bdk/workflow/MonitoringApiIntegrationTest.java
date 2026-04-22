package com.symphony.bdk.workflow;

import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.api.v1.dto.NodeView;
import com.symphony.bdk.workflow.engine.WorkflowNodeTypeHelper;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.SwadlParser;
import com.symphony.bdk.workflow.swadl.v1.Workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.symphony.bdk.workflow.api.v1.dto.NodeView.ChildView;
import static com.symphony.bdk.workflow.api.v1.dto.NodeView.builder;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
class MonitoringApiIntegrationTest extends IntegrationTest {

  private static final String LIST_WORKFLOWS_PATH = "wdk/v1/workflows";
  private static final String LIST_WORKFLOW_INSTANCES_PATH = "wdk/v1/workflows/%s/instances";
  private static final String LIST_WORKFLOW_INSTANCE_ACTIVITIES_PATH =
      "wdk/v1/workflows/%s/instances/%s/states";
  private static final String LIST_WORKFLOW_NODES_PATH = "/wdk/v1/workflows/%s/nodes";
  private static final String LIST_WORKFLOW_GLOBAL_VARIABLES = "/wdk/v1/workflows/%s/instances/%s/variables";
  private static final String X_MONITORING_TOKEN_HEADER_KEY = "X-Monitoring-Token";
  private static final String X_MONITORING_TOKEN_HEADER_VALUE = "MONITORING_TOKEN_VALUE";
  private static final String INVALID_X_MONITORING_TOKEN_EXCEPTION_MESSAGE = "Request is not authorised";
  private static final String BAD_WORKFLOW_INSTANCE_STATUS_EXCEPTION_MESSAGE =
      "Workflow instance status %s is not known. Allowed values [Completed, Pending, Failed]";
  private static final String UNKNOWN_WORKFLOW_EXCEPTION_MESSAGE =
      "Either no workflow deployed with id %s, or %s is not an instance of it";

  @Autowired VersionedWorkflowRepository versionedWorkflowRepository;

  @ParameterizedTest
  @CsvSource(value = {LIST_WORKFLOWS_PATH, LIST_WORKFLOW_INSTANCES_PATH, LIST_WORKFLOW_INSTANCE_ACTIVITIES_PATH,
      LIST_WORKFLOW_NODES_PATH
  })
  void badToken(String path) {
    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, "BAD_TOKEN")
        .contentType(ContentType.JSON)
        .when()
        .get(path)
        .then()
        .assertThat()
        .statusCode(HttpStatus.UNAUTHORIZED.value())
        .body("message", equalTo(INVALID_X_MONITORING_TOKEN_EXCEPTION_MESSAGE));
  }

  @ParameterizedTest
  @CsvSource(value = {LIST_WORKFLOWS_PATH, LIST_WORKFLOW_INSTANCES_PATH, LIST_WORKFLOW_INSTANCE_ACTIVITIES_PATH,
      LIST_WORKFLOW_NODES_PATH
  })
  void missingTokenHeader(String path) {
    given()
        .contentType(ContentType.JSON)
        .when()
        .get(path)
        .then()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void listAllWorkflows_noWorkflowDeployed() {
    engine.undeployAll();

    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(LIST_WORKFLOWS_PATH)
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("", empty());
  }

  @Test
  void listWorkflowInstances_unknownStatusFilter() {
    final String path = LIST_WORKFLOW_INSTANCES_PATH + "?status=unknownStatus";
    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(String.format(path, "testingWorkflow4"))
        .then()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo(String.format(BAD_WORKFLOW_INSTANCE_STATUS_EXCEPTION_MESSAGE, "unknownStatus")));
  }

  @Test
  void listWorkflowInstances_unknownWorkflow() {
    engine.undeployAll();

    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(String.format(LIST_WORKFLOW_INSTANCES_PATH, "testingWorkflow1"))
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("", empty());
  }

  @ParameterizedTest
  @CsvSource({"?started_before=INVALID_INSTANT", "?started_after=INVALID_INSTANT", "?finished_before=INVALID_INSTANT",
      "?finished_after=INVALID_INSTANT"})
  void listInstanceActivities_invalidFilter(String queryParam) {
    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(String.format(LIST_WORKFLOW_INSTANCE_ACTIVITIES_PATH + queryParam, "testingWorkflow4", ""))
        .then()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void listInstanceStates_unknownWorkflowId_unknownInstanceId() {
    final String unknownWorkflowId = "unknownWorkflowId";
    final String unknownInstanceId = "unknownInstanceId";
    final String expectedErrorMsg =
        String.format(UNKNOWN_WORKFLOW_EXCEPTION_MESSAGE, unknownWorkflowId, unknownInstanceId);

    engine.undeployByWorkflowId(unknownWorkflowId);

    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(String.format(LIST_WORKFLOW_INSTANCE_ACTIVITIES_PATH, unknownWorkflowId, unknownInstanceId))
        .then()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body("message", equalTo(expectedErrorMsg));
  }

  @Test
  void listWorkflowActivitiesDefinitions_unknownWorkflowId() {
    final String unknownWorkflowId = "unknownWorkflowId";
    final String expectedErrorMsg =
        String.format("No workflow with id '%s' and version 'null' is found", unknownWorkflowId);

    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(String.format(LIST_WORKFLOW_NODES_PATH, unknownWorkflowId))
        .then()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body("message", equalTo(expectedErrorMsg));
  }

  // TODO: Flaky test
  @Disabled("Flaky test: for some reason, the two first updates have the same revision and the order is not guaranteed")
  void listWorkflowGlobalVariables() throws Exception {
    final Workflow workflow =
        SwadlParser.fromYaml(getClass().getResourceAsStream("/monitoring/testing-workflow-5.swadl.yaml"));

    engine.undeployByWorkflowId(workflow.getId()); // clean any old running instance
    engine.deploy(workflow);
    engine.onEvent(messageReceived("/testingWorkflow5"));

    // Wait for the first activity to get executed
    Thread.sleep(2000);

    engine.onEvent(messageReceived("/continueTestingWorkflow5"));

    // Wait for the second activity to get executed
    Thread.sleep(2000);

    String processDefinition = this.getLastProcessInstanceId("testingWorkflow5");

    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(String.format(LIST_WORKFLOW_GLOBAL_VARIABLES, "testingWorkflow5", processDefinition))
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(3))

        .body("[1].revision", equalTo(0))
        .body("[1].outputs.key1", equalTo("value_1_updated"))
        .body("[1].outputs.key2", equalTo("value_2_initial"))
        .body("[1].outputs.key3", equalTo("value_3_added"))
        .body("[1].updateTime", not(empty()))

        .body("[0].revision", equalTo(0))
        .body("[0].outputs.key1", equalTo("value_1_initial"))
        .body("[0].outputs.key2", equalTo("value_2_initial"))
        .body("[0].updateTime", not(empty()))

        .body("[2].revision", equalTo(1))
        .body("[2].outputs.key1", equalTo("value_1_updated"))
        .body("[2].outputs.key2", equalTo("value_2_initial"))
        .body("[2].outputs.key3", equalTo("value_3_added"))
        .body("[2].outputs.key4", equalTo("value_4_added"))
        .body("[2].updateTime", not(empty()));

    engine.undeployByWorkflowId(workflow.getId());
  }

  @ParameterizedTest
  @CsvSource({"?updated_before=INVALID", "?updated_after=INVALID"})
  void listWorkflowGlobalVariables_invalidFilter(String queryParam) {
    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(String.format(LIST_WORKFLOW_GLOBAL_VARIABLES + queryParam, "testingWorkflow5", ""))
        .then()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void listWorkflowGlobalVariables_unknownWorkflowId_unknownInstanceId() {
    final String unknownWorkflowId = "unknownWorkflowId";
    final String unknownInstanceId = "unknownInstanceId";
    final String expectedErrorMsg =
        String.format(UNKNOWN_WORKFLOW_EXCEPTION_MESSAGE, unknownWorkflowId, unknownInstanceId);

    engine.undeployAll(); // clean any old running instance

    given()
        .header(X_MONITORING_TOKEN_HEADER_KEY, X_MONITORING_TOKEN_HEADER_VALUE)
        .contentType(ContentType.JSON)
        .when()
        .get(String.format(LIST_WORKFLOW_GLOBAL_VARIABLES, unknownWorkflowId, unknownInstanceId))
        .then()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body("message", equalTo(expectedErrorMsg));
  }

  private String getLastProcessInstanceId(String workflowId) {
    Optional<String> processDefinition = historyService.createHistoricProcessInstanceQuery()
        .processDefinitionKey(workflowId)
        .orderByProcessInstanceStartTime()
        .desc() // if many instances are found, always return the latest
        .list()
        .stream()
        .map(HistoricProcessInstance::getId)
        .findFirst();

    if (processDefinition.isEmpty()) {
      fail("At least one process definition should have been found.");
    }

    return processDefinition.get();
  }
}
