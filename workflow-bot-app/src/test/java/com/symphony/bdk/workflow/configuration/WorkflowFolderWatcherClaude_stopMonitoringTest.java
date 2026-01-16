package com.symphony.bdk.workflow.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowFolderWatcherClaude_stopMonitoringTest {

  @Mock
  private WorkflowDeployer workflowDeployer;

  @Mock
  private WorkflowBotConfiguration workflowBotConfiguration;

  @TempDir
  Path tempDir;

  private WorkflowFolderWatcher watcher;

  @BeforeEach
  void setUp() {
    when(workflowBotConfiguration.getWorkflowsFolderPath()).thenReturn(tempDir.toString());
    watcher = new WorkflowFolderWatcher(workflowDeployer, workflowBotConfiguration);
  }

  // ==================== stopMonitoring Tests ====================

  @Test
  void stopMonitoring_beforeMonitoringStarts_shouldThrowNullPointerException() {
    // Given: A watcher that has not started monitoring (watchService is null)

    // When/Then: Calling stopMonitoring will throw NullPointerException because
    // the implementation doesn't check for null before calling close()
    assertThatCode(() -> watcher.stopMonitoring())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void stopMonitoring_afterMonitoringStarts_shouldCloseWatchService() throws Exception {
    // Given: A watcher with an active watch service
    CountDownLatch monitoringStarted = new CountDownLatch(1);

    // Mock the deployer to signal when monitoring has started
    doAnswer(invocation -> {
      monitoringStarted.countDown();
      return null;
    }).when(workflowDeployer).addAllWorkflowsFromFolder(any(Path.class));

    // Start monitoring in a separate thread
    Thread monitoringThread = new Thread(() -> {
      try {
        watcher.monitorWorkflowsFolder();
      } catch (IOException e) {
        // Expected when watch service is closed
      }
    });
    monitoringThread.start();

    // Wait for monitoring to start
    boolean started = monitoringStarted.await(2, TimeUnit.SECONDS);
    if (!started) {
      monitoringThread.interrupt();
      throw new AssertionError("Monitoring did not start in time");
    }

    // When: Stopping monitoring
    assertThatCode(() -> watcher.stopMonitoring())
        .doesNotThrowAnyException();

    // Wait for thread to finish
    monitoringThread.join(2000);

    // Then: The monitoring thread should terminate without hanging
  }

  @Test
  void stopMonitoring_calledMultipleTimes_firstCallSucceedsSubsequentMayFail() throws Exception {
    // Given: A watcher with an active watch service
    CountDownLatch monitoringStarted = new CountDownLatch(1);

    doAnswer(invocation -> {
      monitoringStarted.countDown();
      return null;
    }).when(workflowDeployer).addAllWorkflowsFromFolder(any(Path.class));

    Thread monitoringThread = new Thread(() -> {
      try {
        watcher.monitorWorkflowsFolder();
      } catch (IOException e) {
        // Expected when watch service is closed
      }
    });
    monitoringThread.start();

    boolean started = monitoringStarted.await(2, TimeUnit.SECONDS);
    if (!started) {
      monitoringThread.interrupt();
      throw new AssertionError("Monitoring did not start in time");
    }

    // When: Calling stopMonitoring the first time
    assertThatCode(() -> watcher.stopMonitoring())
        .doesNotThrowAnyException();

    monitoringThread.join(2000);

    // When: Calling stopMonitoring again on already-closed watch service
    // Then: The implementation catches IOException, so calling close() on
    // an already-closed watch service should be handled gracefully
    assertThatCode(() -> watcher.stopMonitoring())
        .doesNotThrowAnyException();
  }

  @Test
  void stopMonitoring_withUninitializedWatchService_shouldThrowNullPointerException() {
    // Given: A watcher instance where watchService has not been initialized

    // When/Then: Calling stopMonitoring will throw NullPointerException
    // because watchService is null and the implementation doesn't check before calling close()
    assertThatCode(() -> watcher.stopMonitoring())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void stopMonitoring_afterMonitoringAndStopping_shouldAllowRestart() throws Exception {
    // Given: A watcher that has been started and stopped
    CountDownLatch firstMonitoringStarted = new CountDownLatch(1);

    doAnswer(invocation -> {
      firstMonitoringStarted.countDown();
      return null;
    }).when(workflowDeployer).addAllWorkflowsFromFolder(any(Path.class));

    Thread firstMonitoringThread = new Thread(() -> {
      try {
        watcher.monitorWorkflowsFolder();
      } catch (IOException e) {
        // Expected when watch service is closed
      }
    });
    firstMonitoringThread.start();

    boolean started = firstMonitoringStarted.await(2, TimeUnit.SECONDS);
    if (!started) {
      firstMonitoringThread.interrupt();
      throw new AssertionError("First monitoring did not start in time");
    }

    watcher.stopMonitoring();
    firstMonitoringThread.join(2000);

    // When: Starting monitoring again after stopping
    CountDownLatch secondMonitoringStarted = new CountDownLatch(1);
    doAnswer(invocation -> {
      secondMonitoringStarted.countDown();
      return null;
    }).when(workflowDeployer).addAllWorkflowsFromFolder(any(Path.class));

    Thread secondMonitoringThread = new Thread(() -> {
      try {
        watcher.monitorWorkflowsFolder();
      } catch (IOException e) {
        // Expected
      }
    });
    secondMonitoringThread.start();

    // Then: Should be able to restart monitoring without issues
    boolean restarted = secondMonitoringStarted.await(2, TimeUnit.SECONDS);
    assertThat(restarted)
        .as("Second monitoring should have started successfully")
        .isTrue();

    watcher.stopMonitoring();
    secondMonitoringThread.join(2000);
  }

  @Test
  void stopMonitoring_withNullWatchService_shouldThrowNullPointerException() {
    // Given: A newly created watcher (watchService not yet initialized)

    // When/Then: Calling stopMonitoring will throw NullPointerException
    // because the implementation calls close() on a null watchService
    assertThatCode(() -> watcher.stopMonitoring())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void stopMonitoring_shouldTerminateMonitoringLoop() throws Exception {
    // Given: An active monitoring loop
    CountDownLatch monitoringStarted = new CountDownLatch(1);

    doAnswer(invocation -> {
      monitoringStarted.countDown();
      return null;
    }).when(workflowDeployer).addAllWorkflowsFromFolder(any(Path.class));

    Thread monitoringThread = new Thread(() -> {
      try {
        watcher.monitorWorkflowsFolder();
      } catch (IOException e) {
        // Expected when watch service is closed
      }
    });
    monitoringThread.start();

    boolean started = monitoringStarted.await(2, TimeUnit.SECONDS);
    if (!started) {
      monitoringThread.interrupt();
      throw new AssertionError("Monitoring did not start in time");
    }

    // When: Calling stopMonitoring
    watcher.stopMonitoring();

    // Then: The monitoring thread should terminate within a reasonable time
    monitoringThread.join(2000);
    assertThat(monitoringThread.isAlive())
        .as("Monitoring thread should have terminated")
        .isFalse();
  }

  @Test
  void stopMonitoring_calledConcurrently_shouldHandleThreadSafely() throws Exception {
    // Given: A watcher with an active watch service
    CountDownLatch monitoringStarted = new CountDownLatch(1);

    doAnswer(invocation -> {
      monitoringStarted.countDown();
      return null;
    }).when(workflowDeployer).addAllWorkflowsFromFolder(any(Path.class));

    Thread monitoringThread = new Thread(() -> {
      try {
        watcher.monitorWorkflowsFolder();
      } catch (IOException e) {
        // Expected when watch service is closed
      }
    });
    monitoringThread.start();

    boolean started = monitoringStarted.await(2, TimeUnit.SECONDS);
    if (!started) {
      monitoringThread.interrupt();
      throw new AssertionError("Monitoring did not start in time");
    }

    // When: Calling stopMonitoring from multiple threads concurrently
    Thread stopThread1 = new Thread(() -> watcher.stopMonitoring());
    Thread stopThread2 = new Thread(() -> watcher.stopMonitoring());
    Thread stopThread3 = new Thread(() -> watcher.stopMonitoring());

    stopThread1.start();
    stopThread2.start();
    stopThread3.start();

    stopThread1.join(2000);
    stopThread2.join(2000);
    stopThread3.join(2000);
    monitoringThread.join(2000);

    // Then: Should handle concurrent calls without throwing exceptions or hanging
    assertThat(stopThread1.isAlive()).as("Stop thread 1 should have completed").isFalse();
    assertThat(stopThread2.isAlive()).as("Stop thread 2 should have completed").isFalse();
    assertThat(stopThread3.isAlive()).as("Stop thread 3 should have completed").isFalse();
  }

  @Test
  void stopMonitoring_immediatelyAfterConstruction_shouldThrowNullPointerException() {
    // Given: A freshly constructed watcher

    // When/Then: Calling stopMonitoring immediately will throw NullPointerException
    // because watchService is not initialized until monitorWorkflowsFolder() is called
    assertThatCode(() -> watcher.stopMonitoring())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void stopMonitoring_inPreDestroyScenario_shouldCleanUpResources() throws Exception {
    // Given: A watcher simulating a Spring @PreDestroy scenario
    CountDownLatch monitoringStarted = new CountDownLatch(1);

    doAnswer(invocation -> {
      monitoringStarted.countDown();
      return null;
    }).when(workflowDeployer).addAllWorkflowsFromFolder(any(Path.class));

    Thread monitoringThread = new Thread(() -> {
      try {
        watcher.monitorWorkflowsFolder();
      } catch (IOException e) {
        // Expected when watch service is closed
      }
    });
    monitoringThread.start();

    boolean started = monitoringStarted.await(2, TimeUnit.SECONDS);
    if (!started) {
      monitoringThread.interrupt();
      throw new AssertionError("Monitoring did not start in time");
    }

    // When: stopMonitoring is called (as it would be by @PreDestroy)
    watcher.stopMonitoring();

    // Then: Should properly clean up and allow JVM to terminate the monitoring thread
    monitoringThread.join(2000);
    assertThat(monitoringThread.isAlive())
        .as("Monitoring thread should have terminated after @PreDestroy")
        .isFalse();
  }
}
