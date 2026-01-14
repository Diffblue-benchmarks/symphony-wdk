package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import org.glassfish.jaxb.runtime.v2.util.ByteArrayOutputStreamEx;
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
  @Autowired private WorkflowResourcesProvider workflowResourcesProvider;

  /**
   * Test {@link WorkflowResourcesProvider#getResourceFile(Path)}.
   *
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is {@code test.txt}.
   *   <li>Then return Name is {@code test.txt}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowResourcesProvider#getResourceFile(Path)}
   */
  @Test
  @DisplayName(
      "Test getResourceFile(Path); when Property is 'java.io.tmpdir' is 'test.txt'; then return Name is 'test.txt'")
  @Tag("MaintainedByDiffblue")
  void testGetResourceFile_whenPropertyIsJavaIoTmpdirIsTestTxt_thenReturnNameIsTestTxt() {
    // Arrange and Act
    File actualResourceFile =
        workflowResourcesProvider.getResourceFile(
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));

    // Assert
    assertEquals("test.txt", actualResourceFile.getName());
    assertTrue(actualResourceFile.isAbsolute());
  }

  /**
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayOutputStreamEx} {@link ByteArrayOutputStreamEx#write(byte[], int,
   *       int)} does nothing.
   *   <li>Then calls {@link ByteArrayOutputStreamEx#close()}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName(
      "Test saveResource(Path, byte[]); given ByteArrayOutputStreamEx write(byte[], int, int) does nothing; then calls close()")
  @Tag("MaintainedByDiffblue")
  void testSaveResource_givenByteArrayOutputStreamExWriteDoesNothing_thenCallsClose()
      throws IOException {
    // Arrange
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {

      ByteArrayOutputStreamEx byteArrayOutputStreamEx = mock(ByteArrayOutputStreamEx.class);
      doNothing().when(byteArrayOutputStreamEx).write(Mockito.<byte[]>any(), anyInt(), anyInt());
      doThrow(new IOException()).when(byteArrayOutputStreamEx).close();
      mockFiles
          .when(() -> Files.readSymbolicLink(Mockito.<Path>any()))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      mockFiles
          .when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)))
          .thenReturn(false);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles
          .when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(byteArrayOutputStreamEx);
      mockFiles
          .when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));

      // Act and Assert
      assertThrows(
          IOException.class,
          () ->
              workflowResourcesProvider.saveResource(
                  Paths.get(System.getProperty("java.io.tmpdir"), ""),
                  "AXAXAXAX".getBytes("UTF-8")));
      verify(byteArrayOutputStreamEx).close();
      verify(byteArrayOutputStreamEx).write(isA(byte[].class), eq(0), eq(8));
      mockFiles.verify(
          () -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
      mockFiles.verify(
          () -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)), atLeast(1));
      mockFiles.verify(() -> Files.isSymbolicLink(Mockito.<Path>any()));
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
      mockFiles.verify(() -> Files.readSymbolicLink(Mockito.<Path>any()));
    }
  }

  /**
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   *
   * <ul>
   *   <li>Given {@link Files} {@link Files#exists(Path, LinkOption[])} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName(
      "Test saveResource(Path, byte[]); given Files exists(Path, LinkOption[]) return 'true'")
  @Tag("MaintainedByDiffblue")
  void testSaveResource_givenFilesExistsReturnTrue() throws IOException {
    // Arrange
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles
          .when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)))
          .thenReturn(true);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles
          .when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream());
      mockFiles
          .when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      Path relativePath = Paths.get(System.getProperty("java.io.tmpdir"), "");

      // Act
      Path actualSaveResourceResult =
          workflowResourcesProvider.saveResource(relativePath, "AXAXAXAX".getBytes("UTF-8"));

      // Assert
      mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
      assertSame(relativePath, actualSaveResourceResult);
    }
  }

  /**
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   *
   * <ul>
   *   <li>Given {@link Files} {@link Files#isSymbolicLink(Path)} return {@code false}.
   *   <li>Then calls {@link Files#createDirectories(Path, FileAttribute[])}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName(
      "Test saveResource(Path, byte[]); given Files isSymbolicLink(Path) return 'false'; then calls createDirectories(Path, FileAttribute[])")
  @Tag("MaintainedByDiffblue")
  void testSaveResource_givenFilesIsSymbolicLinkReturnFalse_thenCallsCreateDirectories()
      throws IOException {
    // Arrange
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles
          .when(() -> Files.readSymbolicLink(Mockito.<Path>any()))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      mockFiles
          .when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)))
          .thenReturn(false);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(false);
      mockFiles
          .when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream());
      mockFiles
          .when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      Path relativePath = Paths.get(System.getProperty("java.io.tmpdir"), "");

      // Act
      Path actualSaveResourceResult =
          workflowResourcesProvider.saveResource(relativePath, "AXAXAXAX".getBytes("UTF-8"));

      // Assert
      mockFiles.verify(
          () -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
      mockFiles.verify(
          () -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)), atLeast(1));
      mockFiles.verify(() -> Files.isSymbolicLink(Mockito.<Path>any()));
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
      assertSame(relativePath, actualSaveResourceResult);
    }
  }

  /**
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   *
   * <ul>
   *   <li>Given {@link Files} {@link Files#newOutputStream(Path, OpenOption[])} throw {@link
   *       IOException#IOException()}.
   *   <li>Then throw {@link IOException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName(
      "Test saveResource(Path, byte[]); given Files newOutputStream(Path, OpenOption[]) throw IOException(); then throw IOException")
  @Tag("MaintainedByDiffblue")
  void testSaveResource_givenFilesNewOutputStreamThrowIOException_thenThrowIOException()
      throws IOException {
    // Arrange
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles
          .when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)))
          .thenReturn(true);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles
          .when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenThrow(new IOException());
      mockFiles
          .when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenThrow(new IOException());

      // Act and Assert
      assertThrows(
          IOException.class,
          () ->
              workflowResourcesProvider.saveResource(
                  Paths.get(System.getProperty("java.io.tmpdir"), ""),
                  "AXAXAXAX".getBytes("UTF-8")));
      mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
    }
  }

  /**
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   *
   * <ul>
   *   <li>Given {@link Files} {@link Files#readSymbolicLink(Path)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName("Test saveResource(Path, byte[]); given Files readSymbolicLink(Path) return 'null'")
  @Tag("MaintainedByDiffblue")
  void testSaveResource_givenFilesReadSymbolicLinkReturnNull() throws IOException {
    // Arrange
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles.when(() -> Files.readSymbolicLink(Mockito.<Path>any())).thenReturn(null);
      mockFiles
          .when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)))
          .thenReturn(false);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles
          .when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream());
      mockFiles
          .when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      System.getProperty("java.io.tmpdir");
      Path relativePath = Paths.get(System.getProperty("java.io.tmpdir"), "");

      // Act
      Path actualSaveResourceResult =
          workflowResourcesProvider.saveResource(relativePath, "AXAXAXAX".getBytes("UTF-8"));

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
   *
   * <ul>
   *   <li>Given {@link Files} {@link Files#readSymbolicLink(Path)} throw {@link
   *       IOException#IOException()}.
   *   <li>Then throw {@link IOException}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName(
      "Test saveResource(Path, byte[]); given Files readSymbolicLink(Path) throw IOException(); then throw IOException")
  @Tag("MaintainedByDiffblue")
  void testSaveResource_givenFilesReadSymbolicLinkThrowIOException_thenThrowIOException()
      throws IOException {
    // Arrange
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles
          .when(() -> Files.readSymbolicLink(Mockito.<Path>any()))
          .thenThrow(new IOException());
      mockFiles
          .when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)))
          .thenReturn(false);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles
          .when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream());
      mockFiles
          .when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));

      // Act and Assert
      assertThrows(
          IOException.class,
          () ->
              workflowResourcesProvider.saveResource(
                  Paths.get(System.getProperty("java.io.tmpdir"), ""),
                  "AXAXAXAX".getBytes("UTF-8")));
      mockFiles.verify(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)));
      mockFiles.verify(() -> Files.isSymbolicLink(Mockito.<Path>any()));
      mockFiles.verify(() -> Files.readSymbolicLink(Mockito.<Path>any()));
    }
  }

  /**
   * Test {@link WorkflowResourcesProvider#saveResource(Path, byte[])}.
   *
   * <ul>
   *   <li>Then calls {@link Files#createDirectories(Path, FileAttribute[])}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowResourcesProvider#saveResource(Path, byte[])}
   */
  @Test
  @DisplayName(
      "Test saveResource(Path, byte[]); then calls createDirectories(Path, FileAttribute[])")
  @Tag("MaintainedByDiffblue")
  void testSaveResource_thenCallsCreateDirectories() throws IOException {
    // Arrange
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      mockFiles
          .when(() -> Files.readSymbolicLink(Mockito.<Path>any()))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      mockFiles
          .when(() -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)))
          .thenReturn(false);
      mockFiles.when(() -> Files.isSymbolicLink(Mockito.<Path>any())).thenReturn(true);
      mockFiles
          .when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream());
      mockFiles
          .when(() -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)))
          .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt"));
      Path relativePath = Paths.get(System.getProperty("java.io.tmpdir"), "");

      // Act
      Path actualSaveResourceResult =
          workflowResourcesProvider.saveResource(relativePath, "AXAXAXAX".getBytes("UTF-8"));

      // Assert
      mockFiles.verify(
          () -> Files.createDirectories(Mockito.<Path>any(), isA(FileAttribute[].class)));
      mockFiles.verify(
          () -> Files.exists(Mockito.<Path>any(), isA(LinkOption[].class)), atLeast(1));
      mockFiles.verify(() -> Files.isSymbolicLink(Mockito.<Path>any()));
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
      mockFiles.verify(() -> Files.readSymbolicLink(Mockito.<Path>any()));
      assertSame(relativePath, actualSaveResourceResult);
    }
  }
}
