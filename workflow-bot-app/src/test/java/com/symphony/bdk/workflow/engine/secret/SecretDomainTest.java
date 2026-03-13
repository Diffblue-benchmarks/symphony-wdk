package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecretDomainTest {

  @Test
  void shouldSetRefAndSecretWhenConstructorCalled() {
    // Arrange
    String ref = "test-ref";
    String secret = "encrypted-secret";

    // Act
    SecretDomain secretDomain = new SecretDomain(ref, secret);

    // Assert
    assertThat(secretDomain.getRef()).isEqualTo(ref);
    assertThat(secretDomain.getSecret()).isEqualTo(secret);
  }

  @Test
  void shouldSetCreatedAtWhenOnCreateCalled() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain("test-ref", "test-secret");
    long beforeTime = System.currentTimeMillis();

    // Act
    secretDomain.onCreate();

    // Assert
    long afterTime = System.currentTimeMillis();
    assertThat(secretDomain.getCreatedAt()).isNotNull();
    assertThat(secretDomain.getCreatedAt()).isBetween(beforeTime, afterTime);
  }

  @Test
  void shouldCreateDomainWithNullValues() {
    // Arrange & Act
    SecretDomain secretDomain = new SecretDomain(null, null);

    // Assert
    assertThat(secretDomain.getRef()).isNull();
    assertThat(secretDomain.getSecret()).isNull();
  }
}
