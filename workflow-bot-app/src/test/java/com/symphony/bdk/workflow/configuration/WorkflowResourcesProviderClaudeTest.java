package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.workflow.engine.ResourceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowResourcesProviderClaudeTest {

  @TempDir
  Path tempDir;

  private WorkflowResourcesProvider provider;

  @BeforeEach
  void setUp() {
    provider = new WorkflowResourcesProvider(tempDir.toString());
  }

  // Tests for <init>(String) constructor

  @Test
  void constructor_shouldCreateNonNullInstance() {
    // When: Creating a new instance of WorkflowResourcesProvider
    WorkflowResourcesProvider provider = new WorkflowResourcesProvider("./test");

    // Then: The instance should not be null
    assertThat(provider).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance of WorkflowResourcesProvider
    WorkflowResourcesProvider provider = new WorkflowResourcesProvider("./test");

    // Then: The instance should be of type WorkflowResourcesProvider
    assertThat(provider).isInstanceOf(WorkflowResourcesProvider.class);
  }

  @Test
  void constructor_shouldImplementResourceProvider() {
    // When: Creating a new instance of WorkflowResourcesProvider
    WorkflowResourcesProvider provider = new WorkflowResourcesProvider("./test");

    // Then: The instance should implement ResourceProvider interface
    assertThat(provider).isInstanceOf(ResourceProvider.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating two instances of WorkflowResourcesProvider
    WorkflowResourcesProvider provider1 = new WorkflowResourcesProvider("./test");
    WorkflowResourcesProvider provider2 = new WorkflowResourcesProvider("./test");

    // Then: Each call should create a distinct instance
    assertThat(provider1).isNotSameAs(provider2);
  }

  @Test
  void constructor_withAbsolutePath_shouldNotThrowException() {
    // When/Then: Creating a new instance with absolute path should not throw any exception
    assertThatCode(() -> new WorkflowResourcesProvider("/tmp/workflows"))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_withRelativePath_shouldNotThrowException() {
    // When/Then: Creating a new instance with relative path should not throw any exception
    assertThatCode(() -> new WorkflowResourcesProvider("./workflows"))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_withEmptyString_shouldNotThrowException() {
    // When/Then: Creating a new instance with empty string should not throw any exception
    assertThatCode(() -> new WorkflowResourcesProvider(""))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_withPathContainingSpaces_shouldNotThrowException() {
    // When/Then: Creating a new instance with path containing spaces should not throw any exception
    assertThatCode(() -> new WorkflowResourcesProvider("/path with spaces/workflows"))
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_withNullPath_shouldNotThrowException() {
    // When/Then: Creating a new instance with null path should not throw any exception (will fail later when used)
    assertThatCode(() -> new WorkflowResourcesProvider(null))
        .doesNotThrowAnyException();
  }

  // Tests for getResource(Path) method

  @Test
  void getResource_withExistingFile_shouldReturnNonNullInputStream() throws IOException {
    // Given: A file exists in the resources folder
    Path testFile = tempDir.resolve("test.txt");
    Files.writeString(testFile, "test content");

    // When: getResource is called with the file path
    InputStream inputStream = provider.getResource(Path.of("test.txt"));

    // Then: The result should not be null
    assertThat(inputStream).isNotNull();
    inputStream.close();
  }

  @Test
  void getResource_withExistingFile_shouldReturnInputStream() throws IOException {
    // Given: A file exists in the resources folder
    Path testFile = tempDir.resolve("test.txt");
    Files.writeString(testFile, "test content");

    // When: getResource is called
    InputStream inputStream = provider.getResource(Path.of("test.txt"));

    // Then: The result should be an instance of InputStream
    assertThat(inputStream).isInstanceOf(InputStream.class);
    inputStream.close();
  }

  @Test
  void getResource_withExistingFile_shouldReturnReadableContent() throws IOException {
    // Given: A file exists with specific content
    String content = "Hello, World!";
    Path testFile = tempDir.resolve("hello.txt");
    Files.writeString(testFile, content);

    // When: getResource is called and content is read
    InputStream inputStream = provider.getResource(Path.of("hello.txt"));
    String readContent = new String(inputStream.readAllBytes());
    inputStream.close();

    // Then: The content should match what was written
    assertThat(readContent).isEqualTo(content);
  }

  @Test
  void getResource_withNonExistingFile_shouldThrowIOException() {
    // Given: A file does not exist in the resources folder

    // When/Then: getResource should throw IOException
    assertThatThrownBy(() -> provider.getResource(Path.of("nonexistent.txt")))
        .isInstanceOf(IOException.class);
  }

  @Test
  void getResource_withSubdirectory_shouldReturnInputStream() throws IOException {
    // Given: A file exists in a subdirectory
    Path subDir = tempDir.resolve("subdir");
    Files.createDirectories(subDir);
    Path testFile = subDir.resolve("test.txt");
    Files.writeString(testFile, "nested content");

    // When: getResource is called with subdirectory path
    InputStream inputStream = provider.getResource(Path.of("subdir/test.txt"));
    String content = new String(inputStream.readAllBytes());
    inputStream.close();

    // Then: The content should be readable
    assertThat(content).isEqualTo("nested content");
  }

  @Test
  void getResource_withNestedSubdirectories_shouldReturnInputStream() throws IOException {
    // Given: A file exists in nested subdirectories
    Path nestedDir = tempDir.resolve("level1/level2/level3");
    Files.createDirectories(nestedDir);
    Path testFile = nestedDir.resolve("deep.txt");
    Files.writeString(testFile, "deeply nested content");

    // When: getResource is called with nested path
    InputStream inputStream = provider.getResource(Path.of("level1/level2/level3/deep.txt"));
    String content = new String(inputStream.readAllBytes());
    inputStream.close();

    // Then: The content should be readable
    assertThat(content).isEqualTo("deeply nested content");
  }

  @Test
  void getResource_withBinaryContent_shouldReturnCorrectBytes() throws IOException {
    // Given: A file with binary content
    byte[] binaryData = new byte[]{0x00, 0x01, 0x02, (byte) 0xFF, (byte) 0xFE};
    Path testFile = tempDir.resolve("binary.dat");
    Files.write(testFile, binaryData);

    // When: getResource is called and bytes are read
    InputStream inputStream = provider.getResource(Path.of("binary.dat"));
    byte[] readData = inputStream.readAllBytes();
    inputStream.close();

    // Then: The binary data should match
    assertThat(readData).isEqualTo(binaryData);
  }

  @Test
  void getResource_withEmptyFile_shouldReturnEmptyInputStream() throws IOException {
    // Given: An empty file exists
    Path testFile = tempDir.resolve("empty.txt");
    Files.writeString(testFile, "");

    // When: getResource is called
    InputStream inputStream = provider.getResource(Path.of("empty.txt"));
    byte[] content = inputStream.readAllBytes();
    inputStream.close();

    // Then: The content should be empty
    assertThat(content).isEmpty();
  }

  @Test
  void getResource_calledMultipleTimes_shouldReturnDistinctStreams() throws IOException {
    // Given: A file exists in the resources folder
    Path testFile = tempDir.resolve("test.txt");
    Files.writeString(testFile, "test content");

    // When: getResource is called multiple times
    InputStream inputStream1 = provider.getResource(Path.of("test.txt"));
    InputStream inputStream2 = provider.getResource(Path.of("test.txt"));

    // Then: Each call should return a distinct InputStream
    assertThat(inputStream1).isNotSameAs(inputStream2);
    inputStream1.close();
    inputStream2.close();
  }

  @Test
  void getResource_withDirectory_shouldThrowIOException() throws IOException {
    // Given: A directory exists
    Path subDir = tempDir.resolve("directory");
    Files.createDirectories(subDir);

    // When/Then: getResource on a directory should throw IOException
    assertThatThrownBy(() -> provider.getResource(Path.of("directory")))
        .isInstanceOf(IOException.class);
  }

  // Tests for getResourceFile(Path) method

  @Test
  void getResourceFile_shouldReturnNonNullFile() {
    // When: getResourceFile is called
    File file = provider.getResourceFile(Path.of("test.txt"));

    // Then: The result should not be null
    assertThat(file).isNotNull();
  }

  @Test
  void getResourceFile_shouldReturnFileInstance() {
    // When: getResourceFile is called
    File file = provider.getResourceFile(Path.of("test.txt"));

    // Then: The result should be an instance of File
    assertThat(file).isInstanceOf(File.class);
  }

  @Test
  void getResourceFile_withExistingFile_shouldReturnFileWithCorrectPath() throws IOException {
    // Given: A file exists in the resources folder
    Path testFile = tempDir.resolve("existing.txt");
    Files.writeString(testFile, "content");

    // When: getResourceFile is called
    File file = provider.getResourceFile(Path.of("existing.txt"));

    // Then: The file should have the correct path and exist
    assertThat(file.exists()).isTrue();
    assertThat(file.getAbsolutePath()).endsWith("existing.txt");
  }

  @Test
  void getResourceFile_withNonExistingFile_shouldReturnFileWithCorrectPath() {
    // When: getResourceFile is called with a non-existing file
    File file = provider.getResourceFile(Path.of("nonexistent.txt"));

    // Then: The file should have the correct path but not exist
    assertThat(file.exists()).isFalse();
    assertThat(file.getAbsolutePath()).endsWith("nonexistent.txt");
  }

  @Test
  void getResourceFile_withSubdirectory_shouldReturnCorrectPath() throws IOException {
    // Given: A file exists in a subdirectory
    Path subDir = tempDir.resolve("subdir");
    Files.createDirectories(subDir);
    Path testFile = subDir.resolve("nested.txt");
    Files.writeString(testFile, "nested");

    // When: getResourceFile is called with subdirectory path
    File file = provider.getResourceFile(Path.of("subdir/nested.txt"));

    // Then: The file should have the correct path and exist
    assertThat(file.exists()).isTrue();
    assertThat(file.getAbsolutePath()).contains("subdir");
    assertThat(file.getAbsolutePath()).endsWith("nested.txt");
  }

  @Test
  void getResourceFile_calledMultipleTimes_shouldReturnEquivalentFiles() {
    // When: getResourceFile is called multiple times with the same path
    File file1 = provider.getResourceFile(Path.of("test.txt"));
    File file2 = provider.getResourceFile(Path.of("test.txt"));

    // Then: The files should point to the same path
    assertThat(file1.getAbsolutePath()).isEqualTo(file2.getAbsolutePath());
  }

  @Test
  void getResourceFile_withEmptyPath_shouldReturnDirectoryFile() {
    // When: getResourceFile is called with empty path
    File file = provider.getResourceFile(Path.of(""));

    // Then: The file should point to the resources folder itself
    assertThat(file.getAbsolutePath()).isEqualTo(tempDir.toFile().getAbsolutePath());
  }

  @Test
  void getResourceFile_shouldNotThrowException() {
    // When/Then: getResourceFile should not throw any exception
    assertThatCode(() -> provider.getResourceFile(Path.of("any.txt")))
        .doesNotThrowAnyException();
  }

  // Tests for saveResource(Path, byte[]) method

  @Test
  void saveResource_shouldCreateFileWithContent() throws IOException {
    // Given: Content to save
    byte[] content = "Hello, World!".getBytes();

    // When: saveResource is called
    Path savedPath = provider.saveResource(Path.of("saved.txt"), content);

    // Then: The file should be created with the correct content
    assertThat(savedPath.toFile().exists()).isTrue();
    byte[] readContent = Files.readAllBytes(savedPath);
    assertThat(readContent).isEqualTo(content);
  }

  @Test
  void saveResource_shouldReturnAbsolutePath() throws IOException {
    // Given: Content to save
    byte[] content = "test".getBytes();

    // When: saveResource is called
    Path savedPath = provider.saveResource(Path.of("test.txt"), content);

    // Then: The returned path should be absolute
    assertThat(savedPath.isAbsolute()).isTrue();
  }

  @Test
  void saveResource_shouldReturnPathInstance() throws IOException {
    // Given: Content to save
    byte[] content = "test".getBytes();

    // When: saveResource is called
    Path savedPath = provider.saveResource(Path.of("test.txt"), content);

    // Then: The result should be an instance of Path
    assertThat(savedPath).isInstanceOf(Path.class);
  }

  @Test
  void saveResource_withBinaryContent_shouldSaveCorrectly() throws IOException {
    // Given: Binary content to save
    byte[] binaryContent = new byte[]{0x00, 0x01, 0x02, (byte) 0xFF, (byte) 0xFE};

    // When: saveResource is called with binary content
    Path savedPath = provider.saveResource(Path.of("binary.dat"), binaryContent);

    // Then: The binary content should be saved correctly
    byte[] readContent = Files.readAllBytes(savedPath);
    assertThat(readContent).isEqualTo(binaryContent);
  }

  @Test
  void saveResource_withEmptyContent_shouldCreateEmptyFile() throws IOException {
    // Given: Empty content to save
    byte[] emptyContent = new byte[0];

    // When: saveResource is called with empty content
    Path savedPath = provider.saveResource(Path.of("empty.txt"), emptyContent);

    // Then: An empty file should be created
    assertThat(savedPath.toFile().exists()).isTrue();
    assertThat(Files.readAllBytes(savedPath)).isEmpty();
  }

  @Test
  void saveResource_withSubdirectory_shouldCreateDirectoriesAndFile() throws IOException {
    // Given: Content to save in a subdirectory
    byte[] content = "nested content".getBytes();

    // When: saveResource is called with subdirectory path
    Path savedPath = provider.saveResource(Path.of("subdir/nested.txt"), content);

    // Then: The subdirectory and file should be created
    assertThat(savedPath.toFile().exists()).isTrue();
    assertThat(savedPath.getParent().toFile().exists()).isTrue();
    byte[] readContent = Files.readAllBytes(savedPath);
    assertThat(readContent).isEqualTo(content);
  }

  @Test
  void saveResource_withNestedSubdirectories_shouldCreateAllDirectories() throws IOException {
    // Given: Content to save in nested subdirectories
    byte[] content = "deeply nested".getBytes();

    // When: saveResource is called with deeply nested path
    Path savedPath = provider.saveResource(Path.of("level1/level2/level3/file.txt"), content);

    // Then: All directories and the file should be created
    assertThat(savedPath.toFile().exists()).isTrue();
    byte[] readContent = Files.readAllBytes(savedPath);
    assertThat(readContent).isEqualTo(content);
  }

  @Test
  void saveResource_overwritingExistingFile_shouldReplaceContent() throws IOException {
    // Given: A file already exists with some content
    Path relativePath = Path.of("overwrite.txt");
    provider.saveResource(relativePath, "original content".getBytes());

    // When: saveResource is called again with new content
    byte[] newContent = "new content".getBytes();
    Path savedPath = provider.saveResource(relativePath, newContent);

    // Then: The file should be overwritten with new content
    byte[] readContent = Files.readAllBytes(savedPath);
    assertThat(readContent).isEqualTo(newContent);
  }

  @Test
  void saveResource_shouldAllowReadingWithGetResource() throws IOException {
    // Given: Content is saved using saveResource
    byte[] content = "saved and read".getBytes();
    Path relativePath = Path.of("saved_and_read.txt");
    provider.saveResource(relativePath, content);

    // When: getResource is called on the same path
    InputStream inputStream = provider.getResource(relativePath);
    byte[] readContent = inputStream.readAllBytes();
    inputStream.close();

    // Then: The content should match what was saved
    assertThat(readContent).isEqualTo(content);
  }

  @Test
  void saveResource_shouldAllowAccessingWithGetResourceFile() throws IOException {
    // Given: Content is saved using saveResource
    byte[] content = "file access".getBytes();
    Path relativePath = Path.of("file_access.txt");
    provider.saveResource(relativePath, content);

    // When: getResourceFile is called on the same path
    File file = provider.getResourceFile(relativePath);

    // Then: The file should exist and be readable
    assertThat(file.exists()).isTrue();
    byte[] readContent = Files.readAllBytes(file.toPath());
    assertThat(readContent).isEqualTo(content);
  }

  @Test
  void saveResource_withLargeContent_shouldSaveCorrectly() throws IOException {
    // Given: Large content to save (1MB)
    byte[] largeContent = new byte[1024 * 1024];
    for (int i = 0; i < largeContent.length; i++) {
      largeContent[i] = (byte) (i % 256);
    }

    // When: saveResource is called with large content
    Path savedPath = provider.saveResource(Path.of("large.dat"), largeContent);

    // Then: The large file should be saved correctly
    assertThat(savedPath.toFile().exists()).isTrue();
    assertThat(Files.size(savedPath)).isEqualTo(largeContent.length);
    byte[] readContent = Files.readAllBytes(savedPath);
    assertThat(readContent).isEqualTo(largeContent);
  }

  @Test
  void saveResource_calledMultipleTimes_shouldSucceed() throws IOException {
    // Given: Multiple files to save
    byte[] content1 = "content1".getBytes();
    byte[] content2 = "content2".getBytes();
    byte[] content3 = "content3".getBytes();

    // When: saveResource is called multiple times
    Path path1 = provider.saveResource(Path.of("file1.txt"), content1);
    Path path2 = provider.saveResource(Path.of("file2.txt"), content2);
    Path path3 = provider.saveResource(Path.of("file3.txt"), content3);

    // Then: All files should be saved correctly
    assertThat(Files.readAllBytes(path1)).isEqualTo(content1);
    assertThat(Files.readAllBytes(path2)).isEqualTo(content2);
    assertThat(Files.readAllBytes(path3)).isEqualTo(content3);
  }

  @Test
  void saveResource_shouldReturnPathPointingToResourcesFolder() throws IOException {
    // Given: Content to save
    byte[] content = "test".getBytes();

    // When: saveResource is called
    Path savedPath = provider.saveResource(Path.of("test.txt"), content);

    // Then: The saved path should be within the resources folder
    assertThat(savedPath.toString()).startsWith(tempDir.toString());
  }
}
