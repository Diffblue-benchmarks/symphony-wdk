package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class SwadlParserDiffblueTest {

  private static final String VALID_WORKFLOW_YAML =
      "id: test-workflow\n"
          + "activities:\n"
          + "  - execute-script:\n"
          + "      id: aScript\n"
          + "      on:\n"
          + "        message-received:\n"
          + "          content: /start\n"
          + "      script: |\n"
          + "        \"hello\"\n";

  /**
   * Test new {@link SwadlParser} (default constructor) via reflection.
   *
   * <p>Method under test: default or parameterless constructor of {@link SwadlParser}
   */
  @Test
  @DisplayName("Test new SwadlParser (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SwadlParser.<init>()"})
  void testNewSwadlParser() throws Exception {
    // Arrange
    Constructor<SwadlParser> constructor = SwadlParser.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    // Act
    SwadlParser instance = constructor.newInstance();

    // Assert
    assertNotNull(instance);
  }

  /**
   * Test {@link SwadlParser#fromYaml(InputStream)}.
   *
   * <ul>
   *   <li>When a valid YAML InputStream is provided.
   *   <li>Then returns a {@link Workflow} with the expected id.
   * </ul>
   *
   * <p>Method under test: {@link SwadlParser#fromYaml(InputStream)}
   */
  @Test
  @DisplayName("Test fromYaml(InputStream); when valid YAML; then returns Workflow")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Workflow SwadlParser.fromYaml(InputStream)"})
  void testFromYamlWithInputStream_whenValidYaml_thenReturnsWorkflow()
      throws IOException, ProcessingException {
    // Arrange
    InputStream yaml = new ByteArrayInputStream(VALID_WORKFLOW_YAML.getBytes(StandardCharsets.UTF_8));

    // Act
    Workflow workflow = SwadlParser.fromYaml(yaml);

    // Assert
    assertNotNull(workflow);
    assertEquals("test-workflow", workflow.getId());
  }

  /**
   * Test {@link SwadlParser#fromYaml(String)}.
   *
   * <ul>
   *   <li>When a valid YAML String is provided.
   *   <li>Then returns a {@link Workflow} with the expected id.
   * </ul>
   *
   * <p>Method under test: {@link SwadlParser#fromYaml(String)}
   */
  @Test
  @DisplayName("Test fromYaml(String); when valid YAML string; then returns Workflow")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Workflow SwadlParser.fromYaml(String)"})
  void testFromYamlWithString_whenValidYamlString_thenReturnsWorkflow()
      throws IOException, ProcessingException {
    // Arrange and Act
    Workflow workflow = SwadlParser.fromYaml(VALID_WORKFLOW_YAML);

    // Assert
    assertNotNull(workflow);
    assertEquals("test-workflow", workflow.getId());
  }

  /**
   * Test {@link SwadlParser#fromYaml(File)}.
   *
   * <ul>
   *   <li>When a valid YAML File is provided.
   *   <li>Then returns a {@link Workflow} with the expected id.
   * </ul>
   *
   * <p>Method under test: {@link SwadlParser#fromYaml(File)}
   */
  @Test
  @DisplayName("Test fromYaml(File); when valid YAML file; then returns Workflow")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Workflow SwadlParser.fromYaml(File)"})
  void testFromYamlWithFile_whenValidYamlFile_thenReturnsWorkflow(@TempDir Path tempDir)
      throws IOException, ProcessingException {
    // Arrange
    Path yamlFile = tempDir.resolve("test-workflow.swadl.yaml");
    Files.writeString(yamlFile, VALID_WORKFLOW_YAML, StandardCharsets.UTF_8);

    // Act
    Workflow workflow = SwadlParser.fromYaml(yamlFile.toFile());

    // Assert
    assertNotNull(workflow);
    assertEquals("test-workflow", workflow.getId());
  }
}
