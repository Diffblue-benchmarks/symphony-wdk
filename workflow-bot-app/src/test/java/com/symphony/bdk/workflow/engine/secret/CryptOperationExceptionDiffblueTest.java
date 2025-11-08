package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CryptOperationExceptionDiffblueTest {
  /**
   * Test {@link CryptOperationException#CryptOperationException(String, Throwable)}.
   * <p>
   * Method under test: {@link CryptOperationException#CryptOperationException(String, Throwable)}
   */
  @Test
  @DisplayName("Test new CryptOperationException(String, Throwable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CryptOperationException.<init>(String, Throwable)"})
  void testNewCryptOperationException() {
    // Arrange
    Throwable e = new Throwable();

    // Act
    CryptOperationException actualCryptOperationException = new CryptOperationException("foo", e);

    // Assert
    assertEquals("foo", actualCryptOperationException.getMessage());
    assertEquals(0, actualCryptOperationException.getSuppressed().length);
    assertSame(e, actualCryptOperationException.getCause());
  }
}
