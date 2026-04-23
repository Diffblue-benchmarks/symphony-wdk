package com.symphony.bdk.workflow.swadl.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniqueIdViolationExceptionTest {

  @Test
  void shouldContainDuplicatedIdsAndWorkflowIdInMessage() {
    List<String> duplicatedIds = Arrays.asList("id1", "id2");
    String workflowId = "my-workflow";

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception.getMessage())
        .contains("id1", "id2", "my-workflow");
  }
}
