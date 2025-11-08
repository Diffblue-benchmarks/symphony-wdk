package com.symphony.bdk.workflow.logs;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LogsStreamingAppenderDiffblueTest {
  @Autowired
  private LogsStreamingAppender logsStreamingAppender;

  @MockBean
  private LogsStreamingService logsStreamingService;

  /**
   * Test {@link LogsStreamingAppender#isRunning()}.
   * <p>
   * Method under test: {@link LogsStreamingAppender#isRunning()}
   */
  @Test
  @DisplayName("Test isRunning()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean LogsStreamingAppender.isRunning()"})
  void testIsRunning() {
    // Arrange, Act and Assert
    assertTrue(logsStreamingAppender.isRunning());
  }
}
