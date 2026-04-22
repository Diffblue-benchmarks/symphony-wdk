package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.symphony.bdk.workflow.configuration.WorkflowResourcesProvider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

class ResourceProviderDiffblueTest {

  /**
   * Test {@link ResourceProvider#getResource(Path)}.
   *
   * <ul>
   *   <li>Then return a non-null InputStream with the saved content.
   * </ul>
   *
   * <p>Method under test: {@link ResourceProvider#getResource(Path)}
   */
  @Test
  @DisplayName("Test getResource(Path); then return non-null InputStream with saved content")
  void testGetResource_thenReturnNonNullInputStream(@TempDir Path tempDir) throws IOException {
    // Arrange
    ResourceProvider resourceProvider = new WorkflowResourcesProvider(tempDir.toString());
    Path relativePath = Path.of("resource.bin");
    byte[] content = new byte[] {1, 2, 3, 4, 5};
    resourceProvider.saveResource(relativePath, content);

    // Act
    InputStream result = resourceProvider.getResource(relativePath);

    // Assert
    assertNotNull(result);
    assertArrayEquals(content, result.readAllBytes());
  }

  /**
   * Test {@link ResourceProvider#getResourceFile(Path)}.
   *
   * <ul>
   *   <li>Then return a File that exists with the expected name.
   * </ul>
   *
   * <p>Method under test: {@link ResourceProvider#getResourceFile(Path)}
   */
  @Test
  @DisplayName("Test getResourceFile(Path); then return a File that exists with the expected name")
  void testGetResourceFile_thenReturnExistingFile(@TempDir Path tempDir) throws IOException {
    // Arrange
    ResourceProvider resourceProvider = new WorkflowResourcesProvider(tempDir.toString());
    Path relativePath = Path.of("resource.txt");
    byte[] content = new byte[] {10, 20, 30};
    resourceProvider.saveResource(relativePath, content);

    // Act
    File result = resourceProvider.getResourceFile(relativePath);

    // Assert
    assertNotNull(result);
    assertTrue(result.exists());
    assertTrue(result.isFile());
  }

  /**
   * Test {@link ResourceProvider#saveResource(Path, byte[])}.
   *
   * <ul>
   *   <li>Then return an absolute Path pointing to the saved file.
   * </ul>
   *
   * <p>Method under test: {@link ResourceProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName("Test saveResource(Path, byte[]); then return an absolute Path pointing to saved file")
  void testSaveResource_thenReturnAbsolutePath(@TempDir Path tempDir) throws IOException {
    // Arrange
    ResourceProvider resourceProvider = new WorkflowResourcesProvider(tempDir.toString());
    Path relativePath = Path.of("saved.bin");
    byte[] content = new byte[] {7, 8, 9};

    // Act
    Path result = resourceProvider.saveResource(relativePath, content);

    // Assert
    assertNotNull(result);
    assertTrue(result.isAbsolute());
    assertTrue(result.toFile().exists());
  }
}
