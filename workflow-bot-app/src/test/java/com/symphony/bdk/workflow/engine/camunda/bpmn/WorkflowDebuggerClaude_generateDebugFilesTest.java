package com.symphony.bdk.workflow.engine.camunda.bpmn;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowDebuggerClaude_generateDebugFilesTest {

  private File originalBuildDir;
  private File testBuildDir;
  private BpmnModelInstance modelInstance;

  @BeforeEach
  void setUp(@TempDir Path tempDir) {
    // Create a test build directory
    testBuildDir = tempDir.resolve("build").toFile();
    testBuildDir.mkdirs();

    // Save original build directory if it exists
    originalBuildDir = new File("./build");

    // Create a simple BPMN model instance for testing
    modelInstance = Bpmn.createExecutableProcess("testProcess")
        .startEvent("start")
        .endEvent("end")
        .done();
  }

  @AfterEach
  void tearDown() {
    // Clean up any generated files in the test directory
    if (testBuildDir != null && testBuildDir.exists()) {
      cleanDirectory(testBuildDir);
    }

    // Clean up any files generated in current directory during tests
    deleteFileIfExists(new File(".", "testWorkflow.bpmn"));
    deleteFileIfExists(new File(".", "testWorkflow.png"));
  }

  private void cleanDirectory(File dir) {
    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          cleanDirectory(file);
        }
        file.delete();
      }
    }
  }

  private void deleteFileIfExists(File file) {
    if (file.exists()) {
      file.delete();
    }
  }

  // Test 1: generateDebugFiles should create BPMN file when build directory exists
  @Test
  void generateDebugFiles_withExistingBuildDirectory_shouldCreateBpmnFile() throws IOException {
    // Given: A workflow name and model instance, with build directory existing
    String workflowName = "testWorkflow";

    // Create a temporary build directory in the working directory
    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      // When: Generating debug files
      WorkflowDebugger.generateDebugFiles(workflowName, modelInstance);

      // Then: BPMN file should be created in build directory
      File bpmnFile = new File(buildDir, workflowName + ".bpmn");
      assertThat(bpmnFile).exists();
      assertThat(bpmnFile).isFile();

      // Verify the file contains valid BPMN content
      String content = Files.readString(bpmnFile.toPath());
      assertThat(content).contains("testProcess");
      assertThat(content).contains("bpmn");

      // Clean up
      bpmnFile.delete();
    } finally {
      // Clean up build directory if we created it
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, workflowName + ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }

  // Test 2: generateDebugFiles should create BPMN file in current directory when build directory doesn't exist
  @Test
  void generateDebugFiles_withoutBuildDirectory_shouldCreateBpmnFileInCurrentDirectory() throws IOException {
    // Given: A workflow name and model instance, ensuring build directory doesn't exist
    String workflowName = "testWorkflow";
    File buildDir = new File("./build");

    // Skip this test if build directory exists and we can't control it
    if (buildDir.exists() && buildDir.isDirectory()) {
      // Test would create file in build directory, so we skip
      return;
    }

    try {
      // When: Generating debug files without build directory
      WorkflowDebugger.generateDebugFiles(workflowName, modelInstance);

      // Then: BPMN file should be created in current directory
      File bpmnFile = new File(".", workflowName + ".bpmn");
      assertThat(bpmnFile).exists();
      assertThat(bpmnFile).isFile();

      // Verify the file contains valid BPMN content
      String content = Files.readString(bpmnFile.toPath());
      assertThat(content).contains("testProcess");

      // Clean up
      bpmnFile.delete();
    } catch (Exception e) {
      // Clean up in case of failure
      new File(".", workflowName + ".bpmn").delete();
      throw e;
    }
  }

  // Test 3: generateDebugFiles should handle workflow names with special characters
  @Test
  void generateDebugFiles_withSpecialCharactersInName_shouldCreateFile() throws IOException {
    // Given: A workflow name with underscores and numbers
    String workflowName = "test_workflow_123";
    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      // When: Generating debug files
      WorkflowDebugger.generateDebugFiles(workflowName, modelInstance);

      // Then: BPMN file should be created with the special characters in name
      File bpmnFile = new File(buildDir, workflowName + ".bpmn");
      assertThat(bpmnFile).exists();
      assertThat(bpmnFile.getName()).isEqualTo("test_workflow_123.bpmn");

      // Clean up
      bpmnFile.delete();
    } finally {
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, workflowName + ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }

  // Test 4: generateDebugFiles should handle simple workflow name
  @Test
  void generateDebugFiles_withSimpleWorkflowName_shouldCreateFile() throws IOException {
    // Given: A simple workflow name
    String workflowName = "simple";
    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      // When: Generating debug files
      WorkflowDebugger.generateDebugFiles(workflowName, modelInstance);

      // Then: BPMN file should be created
      File bpmnFile = new File(buildDir, workflowName + ".bpmn");
      assertThat(bpmnFile).exists();

      // Clean up
      bpmnFile.delete();
    } finally {
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, workflowName + ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }

  // Test 5: generateDebugFiles should handle complex BPMN model with multiple elements
  @Test
  void generateDebugFiles_withComplexBpmnModel_shouldCreateValidFile() throws IOException {
    // Given: A more complex BPMN model
    String workflowName = "complexWorkflow";
    BpmnModelInstance complexModel = Bpmn.createExecutableProcess("complexProcess")
        .startEvent("start")
        .serviceTask("task1")
        .name("Task 1")
        .serviceTask("task2")
        .name("Task 2")
        .endEvent("end")
        .done();

    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      // When: Generating debug files with complex model
      WorkflowDebugger.generateDebugFiles(workflowName, complexModel);

      // Then: BPMN file should be created with all elements
      File bpmnFile = new File(buildDir, workflowName + ".bpmn");
      assertThat(bpmnFile).exists();

      String content = Files.readString(bpmnFile.toPath());
      assertThat(content).contains("complexProcess");
      assertThat(content).contains("task1");
      assertThat(content).contains("task2");

      // Clean up
      bpmnFile.delete();
    } finally {
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, workflowName + ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }

  // Test 6: generateDebugFiles should overwrite existing BPMN file
  @Test
  void generateDebugFiles_withExistingFile_shouldOverwriteFile() throws IOException {
    // Given: An existing BPMN file with different content
    String workflowName = "overwriteTest";
    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      File bpmnFile = new File(buildDir, workflowName + ".bpmn");
      Files.writeString(bpmnFile.toPath(), "old content");
      long originalSize = bpmnFile.length();

      // When: Generating debug files with new model
      WorkflowDebugger.generateDebugFiles(workflowName, modelInstance);

      // Then: File should be overwritten with new content
      assertThat(bpmnFile).exists();
      String newContent = Files.readString(bpmnFile.toPath());
      assertThat(newContent).contains("testProcess");
      assertThat(newContent).doesNotContain("old content");
      assertThat(bpmnFile.length()).isNotEqualTo(originalSize);

      // Clean up
      bpmnFile.delete();
    } finally {
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, workflowName + ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }

  // Test 7: generateDebugFiles with empty workflow name should create file with .bpmn extension
  @Test
  void generateDebugFiles_withEmptyWorkflowName_shouldCreateFileWithBpmnExtension() throws IOException {
    // Given: An empty workflow name
    String workflowName = "";
    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      // When: Generating debug files
      WorkflowDebugger.generateDebugFiles(workflowName, modelInstance);

      // Then: BPMN file should be created with just .bpmn extension
      File bpmnFile = new File(buildDir, ".bpmn");
      assertThat(bpmnFile).exists();

      // Clean up
      bpmnFile.delete();
    } finally {
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }

  // Test 8: generateDebugFiles should create file with correct extension
  @Test
  void generateDebugFiles_shouldCreateFileWithBpmnExtension() throws IOException {
    // Given: A workflow name without extension
    String workflowName = "myWorkflow";
    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      // When: Generating debug files
      WorkflowDebugger.generateDebugFiles(workflowName, modelInstance);

      // Then: File should have .bpmn extension
      File bpmnFile = new File(buildDir, workflowName + ".bpmn");
      assertThat(bpmnFile.getName()).endsWith(".bpmn");
      assertThat(bpmnFile).exists();

      // Clean up
      bpmnFile.delete();
    } finally {
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, workflowName + ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }

  // Test 9: generateDebugFiles should handle BPMN model with single start event
  @Test
  void generateDebugFiles_withSingleStartEvent_shouldCreateValidFile() throws IOException {
    // Given: A BPMN model with only a start event
    String workflowName = "singleEvent";
    BpmnModelInstance simpleModel = Bpmn.createExecutableProcess("simpleProcess")
        .startEvent("start")
        .done();

    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      // When: Generating debug files
      WorkflowDebugger.generateDebugFiles(workflowName, simpleModel);

      // Then: BPMN file should be created and contain the start event
      File bpmnFile = new File(buildDir, workflowName + ".bpmn");
      assertThat(bpmnFile).exists();

      String content = Files.readString(bpmnFile.toPath());
      assertThat(content).contains("startEvent");
      assertThat(content).contains("simpleProcess");

      // Clean up
      bpmnFile.delete();
    } finally {
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, workflowName + ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }

  // Test 10: generateDebugFiles should create readable XML content
  @Test
  void generateDebugFiles_shouldCreateReadableXmlContent() throws IOException {
    // Given: A workflow name and model
    String workflowName = "xmlTest";
    File buildDir = new File("./build");
    boolean buildDirCreated = false;
    if (!buildDir.exists()) {
      buildDirCreated = buildDir.mkdirs();
    }

    try {
      // When: Generating debug files
      WorkflowDebugger.generateDebugFiles(workflowName, modelInstance);

      // Then: File should contain valid XML
      File bpmnFile = new File(buildDir, workflowName + ".bpmn");
      String content = Files.readString(bpmnFile.toPath());
      assertThat(content).startsWith("<?xml");
      assertThat(content).contains("definitions");
      assertThat(content).contains("xmlns");

      // Clean up
      bpmnFile.delete();
    } finally {
      if (buildDirCreated) {
        File bpmnFile = new File(buildDir, workflowName + ".bpmn");
        bpmnFile.delete();
        buildDir.delete();
      }
    }
  }
}
