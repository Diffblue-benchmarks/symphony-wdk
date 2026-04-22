package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.WatchService;

class WorkflowFolderWatcherDiffblueTest {

  @TempDir
  Path tempDir;

  /**
   * Test {@link WorkflowFolderWatcher#WorkflowFolderWatcher(WorkflowDeployer, WorkflowBotConfiguration)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowFolderWatcher#WorkflowFolderWatcher(WorkflowDeployer, WorkflowBotConfiguration)}
   */
  @Test
  @DisplayName("Test WorkflowFolderWatcher(WorkflowDeployer, WorkflowBotConfiguration); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowFolderWatcher.<init>(WorkflowDeployer, WorkflowBotConfiguration)"})
  void testConstructor_thenDoesNotThrow() {
    // Arrange
    WorkflowDeployer workflowDeployer = mock(WorkflowDeployer.class);
    WorkflowBotConfiguration config = mock(WorkflowBotConfiguration.class);
    when(config.getWorkflowsFolderPath()).thenReturn(tempDir.toString());

    // Act and Assert
    assertDoesNotThrow(() -> new WorkflowFolderWatcher(workflowDeployer, config));
  }

  /**
   * Test {@link WorkflowFolderWatcher#stopMonitoring()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowFolderWatcher#stopMonitoring()}
   */
  @Test
  @DisplayName("Test stopMonitoring(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowFolderWatcher.stopMonitoring()"})
  void testStopMonitoring_thenDoesNotThrow() throws Exception {
    // Arrange
    WorkflowDeployer workflowDeployer = mock(WorkflowDeployer.class);
    WorkflowBotConfiguration config = mock(WorkflowBotConfiguration.class);
    when(config.getWorkflowsFolderPath()).thenReturn(tempDir.toString());
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, config);

    WatchService watchService = mock(WatchService.class);
    Field field = WorkflowFolderWatcher.class.getDeclaredField("watchService");
    field.setAccessible(true);
    field.set(watcher, watchService);

    // Act and Assert
    assertDoesNotThrow(watcher::stopMonitoring);
  }

  /**
   * Test {@link WorkflowFolderWatcher#stopMonitoring()}.
   *
   * <ul>
   *   <li>When {@link WatchService#close()} throws {@link IOException}.
   *   <li>Then does not throw (error is logged).
   * </ul>
   *
   * <p>Method under test: {@link WorkflowFolderWatcher#stopMonitoring()}
   */
  @Test
  @DisplayName("Test stopMonitoring(); when IOException is thrown; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowFolderWatcher.stopMonitoring()"})
  void testStopMonitoring_whenIOExceptionThrown_thenDoesNotThrow() throws Exception {
    // Arrange
    WorkflowDeployer workflowDeployer = mock(WorkflowDeployer.class);
    WorkflowBotConfiguration config = mock(WorkflowBotConfiguration.class);
    when(config.getWorkflowsFolderPath()).thenReturn(tempDir.toString());
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, config);

    WatchService watchService = mock(WatchService.class);
    doThrow(new IOException("Failed to close")).when(watchService).close();
    Field field = WorkflowFolderWatcher.class.getDeclaredField("watchService");
    field.setAccessible(true);
    field.set(watcher, watchService);

    // Act and Assert
    assertDoesNotThrow(watcher::stopMonitoring);
  }

  /**
   * Test {@link WorkflowFolderWatcher#monitorWorkflowsFolder()}.
   *
   * <ul>
   *   <li>Then stops cleanly when {@link WorkflowFolderWatcher#stopMonitoring()} is called.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowFolderWatcher#monitorWorkflowsFolder()}
   */
  @Test
  @DisplayName("Test monitorWorkflowsFolder(); then stops when stopMonitoring is called")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WorkflowFolderWatcher.monitorWorkflowsFolder()"})
  void testMonitorWorkflowsFolder_thenStopsWhenMonitoringStopped() throws Exception {
    // Arrange
    WorkflowDeployer workflowDeployer = mock(WorkflowDeployer.class);
    WorkflowBotConfiguration config = mock(WorkflowBotConfiguration.class);
    when(config.getWorkflowsFolderPath()).thenReturn(tempDir.toString());
    WorkflowFolderWatcher watcher = new WorkflowFolderWatcher(workflowDeployer, config);

    Thread watcherThread = new Thread(() -> {
      try {
        watcher.monitorWorkflowsFolder();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    // Act
    watcherThread.start();
    Thread.sleep(500); // wait for watchService to be initialized
    assertDoesNotThrow(watcher::stopMonitoring);

    // Assert
    watcherThread.join(5_000);
    assertDoesNotThrow(() -> {});
  }
}
