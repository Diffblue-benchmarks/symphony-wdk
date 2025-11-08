package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

class CryptOperationExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link CryptOperationException#CryptOperationException(String, Throwable)}
   */
  @Test
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
