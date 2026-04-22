package com.symphony.bdk.workflow.management.converter;

import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WorkflowConverterTest {

  private static final String VALID_SWADL = "id: test-workflow\n"
      + "activities:\n"
      + "  - execute-script:\n"
      + "      id: aScript\n"
      + "      on:\n"
      + "        message-received:\n"
      + "          content: /test\n"
      + "      script: |\n"
      + "        messageService.send(\"123\", \"hello\")\n";

  @Test
  void apply_validSwadl_returnsWorkflowWithVersion() {
    WorkflowConverter converter = new WorkflowConverter();

    Workflow result = converter.apply(VALID_SWADL);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("test-workflow");
    assertThat(result.getVersion()).isGreaterThan(0L);
  }

  @Test
  void apply_invalidSwadl_throwsIllegalArgumentException() {
    WorkflowConverter converter = new WorkflowConverter();

    assertThrows(IllegalArgumentException.class, () -> converter.apply("invalid yaml: :::"));
  }
}
