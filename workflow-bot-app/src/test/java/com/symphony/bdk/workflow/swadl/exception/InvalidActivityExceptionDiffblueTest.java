package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InvalidActivityException.class, String.class})
@ExtendWith(SpringExtension.class)
class InvalidActivityExceptionDiffblueTest {
  @Autowired
  private InvalidActivityException invalidActivityException;

  /**
   * Test {@link InvalidActivityException#InvalidActivityException(String, String)}.
   * <p>
   * Method under test: {@link InvalidActivityException#InvalidActivityException(String, String)}
   */
  @Test
  @DisplayName("Test new InvalidActivityException(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InvalidActivityException.<init>(String, String)"})
  void testNewInvalidActivityException() {
    // Arrange and Act
    InvalidActivityException actualInvalidActivityException = new InvalidActivityException("42", "An error occurred");

    // Assert
    assertEquals("Invalid activity in the workflow 42: An error occurred",
        actualInvalidActivityException.getLocalizedMessage());
    assertEquals("Invalid activity in the workflow 42: An error occurred", actualInvalidActivityException.getMessage());
    assertNull(actualInvalidActivityException.getCause());
    assertEquals(0, actualInvalidActivityException.getSuppressed().length);
  }
}
