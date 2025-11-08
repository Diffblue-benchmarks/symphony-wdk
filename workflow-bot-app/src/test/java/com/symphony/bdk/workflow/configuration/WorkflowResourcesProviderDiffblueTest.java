package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mockStatic;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link WorkflowResourcesProvider#getResourceFile(Path)}.
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is {@code test.txt}.</li>
   *   <li>Then return Name is {@code test.txt}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowResourcesProvider#getResourceFile(Path)}
   */
  @Test
  @DisplayName("Test getResourceFile(Path); when Property is 'java.io.tmpdir' is 'test.txt'; then return Name is 'test.txt'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"File WorkflowResourcesProvider.getResourceFile(Path)"})
  void testGetResourceFile_whenPropertyIsJavaIoTmpdirIsTestTxt_thenReturnNameIsTestTxt() {
    // Arrange and Act
    File actualResourceFile = workflowResourcesProvider
        .getResourceFile(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));

    // Assert
    assertEquals("test.txt", actualResourceFile.getName());
    assertTrue(actualResourceFile.isAbsolute());
  }

  /**
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   * <ul>
   *   <li>Given {@link Files} {@link Files#exists(Path, LinkOption[])} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName("Test saveResource(Path, byte[]); given Files exists(Path, LinkOption[]) return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Path WorkflowResourcesProvider.saveResource(Path, byte[])"})
  void testSaveResource_givenFilesExistsReturnTrue() throws IOException {
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
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   * <ul>
   *   <li>Given {@link Files} {@link Files#readSymbolicLink(Path)} return {@code null}.</li>
   *   <li>Then calls {@link Files#isSymbolicLink(Path)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName("Test saveResource(Path, byte[]); given Files readSymbolicLink(Path) return 'null'; then calls isSymbolicLink(Path)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Path WorkflowResourcesProvider.saveResource(Path, byte[])"})
  void testSaveResource_givenFilesReadSymbolicLinkReturnNull_thenCallsIsSymbolicLink() throws IOException {
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

  /**
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   * <ul>
   *   <li>Then calls {@link Files#createDirectories(Path, FileAttribute[])}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName("Test saveResource(Path, byte[]); then calls createDirectories(Path, FileAttribute[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Path WorkflowResourcesProvider.saveResource(Path, byte[])"})
  void testSaveResource_thenCallsCreateDirectories() throws IOException {
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
}
