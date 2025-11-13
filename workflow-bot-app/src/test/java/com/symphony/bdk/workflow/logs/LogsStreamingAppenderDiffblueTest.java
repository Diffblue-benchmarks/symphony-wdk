package com.symphony.bdk.workflow.logs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LogsStreamingAppender.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class LogsStreamingAppenderDiffblueTest {
  @Autowired private LogsStreamingAppender logsStreamingAppender;

  @MockBean private LogsStreamingService logsStreamingService;

  /**
   * Test {@link LogsStreamingAppender#isRunning()}.
   *
   * <ul>
   *   <li>Given {@link LogsStreamingService}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LogsStreamingAppender#isRunning()}
   */
  @Test
  @DisplayName("Test isRunning(); given LogsStreamingService; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LogsStreamingAppender.isRunning()"})
  void testIsRunning_givenLogsStreamingService_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(logsStreamingAppender.isRunning());
  }

  /**
   * Test {@link LogsStreamingAppender#isRunning()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LogsStreamingAppender#isRunning()}
   */
  @Test
  @DisplayName("Test isRunning(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LogsStreamingAppender.isRunning()"})
  void testIsRunning_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new LogsStreamingAppender(new LogsStreamingService()).isRunning());
  }
}
