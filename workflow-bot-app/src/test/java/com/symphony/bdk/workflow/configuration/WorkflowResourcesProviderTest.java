package com.symphony.bdk.workflow.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class WorkflowResourcesProviderTest {

  @TempDir
  Path tempDir;

  private WorkflowResourcesProvider provider;
  private String resourcesFolder;

  @BeforeEach
  void setUp() {
    resourcesFolder = tempDir.toString();
    provider = new WorkflowResourcesProvider(resourcesFolder);
  }

  @Test
  void shouldCreateProviderWithResourcesFolder() {
    WorkflowResourcesProvider newProvider = new WorkflowResourcesProvider("/some/path");

    assertThat(newProvider).isNotNull();
  }

  @Test
  void shouldGetResourceAsInputStream() throws IOException {
    Path relativePath = Path.of("test-file.txt");
    String content = "test content";
    Files.writeString(tempDir.resolve(relativePath), content, StandardCharsets.UTF_8);

    InputStream inputStream = provider.getResource(relativePath);

    assertThat(inputStream).isNotNull();
    String actualContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    assertThat(actualContent).isEqualTo(content);
    inputStream.close();
  }

  @Test
  void shouldThrowExceptionWhenResourceNotFound() {
    Path relativePath = Path.of("non-existent.txt");

    assertThatThrownBy(() -> provider.getResource(relativePath))
        .isInstanceOf(IOException.class);
  }

  @Test
  void shouldGetResourceFile() throws IOException {
    Path relativePath = Path.of("test-file.txt");
    String content = "test content";
    Files.writeString(tempDir.resolve(relativePath), content, StandardCharsets.UTF_8);

    File file = provider.getResourceFile(relativePath);

    assertThat(file).isNotNull();
    assertThat(file.exists()).isTrue();
    assertThat(file.getAbsolutePath()).endsWith(relativePath.toString());
  }

  @Test
  void shouldGetResourceFileForNonExistentFile() {
    Path relativePath = Path.of("non-existent.txt");

    File file = provider.getResourceFile(relativePath);

    assertThat(file).isNotNull();
    assertThat(file.exists()).isFalse();
  }

  @Test
  void shouldSaveResource() throws IOException {
    Path relativePath = Path.of("saved-file.txt");
    byte[] content = "saved content".getBytes(StandardCharsets.UTF_8);

    Path savedPath = provider.saveResource(relativePath, content);

    assertThat(savedPath).isNotNull();
    assertThat(Files.exists(savedPath)).isTrue();
    String actualContent = Files.readString(savedPath, StandardCharsets.UTF_8);
    assertThat(actualContent).isEqualTo("saved content");
  }

  @Test
  void shouldSaveResourceInNestedDirectory() throws IOException {
    Path relativePath = Path.of("nested/dir/file.txt");
    byte[] content = "nested content".getBytes(StandardCharsets.UTF_8);

    Path savedPath = provider.saveResource(relativePath, content);

    assertThat(savedPath).isNotNull();
    assertThat(Files.exists(savedPath)).isTrue();
    String actualContent = Files.readString(savedPath, StandardCharsets.UTF_8);
    assertThat(actualContent).isEqualTo("nested content");
  }

  @Test
  void shouldOverwriteExistingResource() throws IOException {
    Path relativePath = Path.of("overwrite-file.txt");
    byte[] initialContent = "initial content".getBytes(StandardCharsets.UTF_8);
    byte[] newContent = "new content".getBytes(StandardCharsets.UTF_8);

    provider.saveResource(relativePath, initialContent);
    Path savedPath = provider.saveResource(relativePath, newContent);

    assertThat(Files.exists(savedPath)).isTrue();
    String actualContent = Files.readString(savedPath, StandardCharsets.UTF_8);
    assertThat(actualContent).isEqualTo("new content");
  }
}
