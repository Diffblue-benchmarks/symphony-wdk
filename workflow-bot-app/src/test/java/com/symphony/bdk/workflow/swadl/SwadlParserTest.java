package com.symphony.bdk.workflow.swadl;

import static org.assertj.core.api.Assertions.assertThat;

import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class SwadlParserTest {

  private static final String SIMPLE_WORKFLOW_YAML =
      "id: test-workflow\n"
          + "activities:\n"
          + "  - send-message:\n"
          + "      id: sendMessage\n"
          + "      on:\n"
          + "        message-received:\n"
          + "          content: /start\n"
          + "      content: hello\n";

  @Test
  void shouldParseWorkflowFromInputStream() throws Exception {
    InputStream inputStream = new ByteArrayInputStream(SIMPLE_WORKFLOW_YAML.getBytes(StandardCharsets.UTF_8));

    Workflow workflow = SwadlParser.fromYaml(inputStream);

    assertThat(workflow).isNotNull();
    assertThat(workflow.getId()).isEqualTo("test-workflow");
  }

  @Test
  void shouldParseWorkflowFromString() throws Exception {
    Workflow workflow = SwadlParser.fromYaml(SIMPLE_WORKFLOW_YAML);

    assertThat(workflow).isNotNull();
    assertThat(workflow.getId()).isEqualTo("test-workflow");
  }

  @Test
  void shouldParseWorkflowFromFile(@TempDir Path tempDir) throws Exception {
    File workflowFile = tempDir.resolve("test.swadl.yaml").toFile();
    Files.writeString(workflowFile.toPath(), SIMPLE_WORKFLOW_YAML, StandardCharsets.UTF_8);

    Workflow workflow = SwadlParser.fromYaml(workflowFile);

    assertThat(workflow).isNotNull();
    assertThat(workflow.getId()).isEqualTo("test-workflow");
  }
}
