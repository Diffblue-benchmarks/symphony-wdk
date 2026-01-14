package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SecretDomainDiffblueTest {
  /**
   * Test {@link SecretDomain#SecretDomain(String, String)}.
   *
   * <p>Method under test: {@link SecretDomain#SecretDomain(String, String)}
   */
  @Test
  @DisplayName("Test new SecretDomain(String, String)")
  @Tag("MaintainedByDiffblue")
  void testNewSecretDomain() {
    // Arrange and Act
    SecretDomain actualSecretDomain = new SecretDomain("Ref", "Secret");

    // Assert
    assertEquals("Ref", actualSecretDomain.getRef());
    assertEquals("Secret", actualSecretDomain.getSecret());
    assertNull(actualSecretDomain.getCreatedAt());
    assertNull(actualSecretDomain.getId());
  }
}
