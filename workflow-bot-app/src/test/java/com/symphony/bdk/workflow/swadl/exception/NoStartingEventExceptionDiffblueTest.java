package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NoStartingEventException.class, String.class})
@ExtendWith(SpringExtension.class)
class NoStartingEventExceptionDiffblueTest {
  @Autowired private NoStartingEventException noStartingEventException;

  /**
   * Test {@link NoStartingEventException#NoStartingEventException(String)}.
   *
   * <p>Method under test: {@link NoStartingEventException#NoStartingEventException(String)}
   */
  @Test
  @DisplayName("Test new NoStartingEventException(String)")
  @Tag("MaintainedByDiffblue")
  void testNewNoStartingEventException() {
    // Arrange and Act
    NoStartingEventException actualNoStartingEventException = new NoStartingEventException("42");

    // Assert
    assertEquals(
        "Workflow with id \"42\" does not have any starting event.",
        actualNoStartingEventException.getLocalizedMessage());
    assertEquals(
        "Workflow with id \"42\" does not have any starting event.",
        actualNoStartingEventException.getMessage());
    assertNull(actualNoStartingEventException.getCause());
    assertEquals(0, actualNoStartingEventException.getSuppressed().length);
  }
}
