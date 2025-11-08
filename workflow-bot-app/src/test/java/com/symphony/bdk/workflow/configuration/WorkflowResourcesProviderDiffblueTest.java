package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mockStatic;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowResourcesProvider.class, String.class})
@ExtendWith(SpringExtension.class)
class WorkflowResourcesProviderDiffblueTest {
  @Autowired
  private WorkflowResourcesProvider workflowResourcesProvider;

  /**
   * Method under test: {@link WorkflowResourcesProvider#getResourceFile(Path)}
   */
  @Test
  void testGetResourceFile() {
    // Arrange and Act
    File actualResourceFile = workflowResourcesProvider
        .getResourceFile(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));

    // Assert
    assertEquals("test.txt", actualResourceFile.getName());
    assertTrue(actualResourceFile.isAbsolute());
  }

  /**
   * Method under test:
   * {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  void testSaveResource() throws IOException {
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      // Arrange
      mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(true);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream(1));
      mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      Path relativePath = Paths.get(System.getProperty("java.io.tmpdir"), "");

      // Act
      Path actualSaveResourceResult = workflowResourcesProvider.saveResource(relativePath,
          "AXAXAXAX".getBytes("UTF-8"));

      // Assert
      mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
      assertSame(relativePath, actualSaveResourceResult);
    }
  }

  /**
   * Method under test:
   * {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  void testSaveResource2() throws IOException {
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      // Arrange
      mockFiles.when(() -> Files.readSymbolicLink(Mockito.<Path>any()))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(false);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream(1));
      mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      Path relativePath = Paths.get(System.getProperty("java.io.tmpdir"), "");

      // Act
      Path actualSaveResourceResult = workflowResourcesProvider.saveResource(relativePath,
          "AXAXAXAX".getBytes("UTF-8"));

      // Assert
      mockFiles.verify(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
      mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)), atLeast(1));
      mockFiles.verify(() -> Files.isSymbolicLink(Mockito.<Path>any()));
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
      mockFiles.verify(() -> Files.readSymbolicLink(Mockito.<Path>any()));
      assertSame(relativePath, actualSaveResourceResult);
    }
  }

  /**
   * Method under test:
   * {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  void testSaveResource3() throws IOException {
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      // Arrange
      mockFiles.when(() -> Files.readSymbolicLink(Mockito.<Path>any())).thenReturn(null);
      mockFiles.when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class))).thenReturn(false);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream(1));
      mockFiles.when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      System.getProperty("java.io.tmpdir");
      Path relativePath = Paths.get(System.getProperty("java.io.tmpdir"), "");

      // Act
      Path actualSaveResourceResult = workflowResourcesProvider.saveResource(relativePath,
          "AXAXAXAX".getBytes("UTF-8"));

      // Assert
      mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
      mockFiles.verify(() -> Files.isSymbolicLink(Mockito.<Path>any()));
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
      mockFiles.verify(() -> Files.readSymbolicLink(Mockito.<Path>any()));
      assertSame(relativePath, actualSaveResourceResult);
    }
  }
}
