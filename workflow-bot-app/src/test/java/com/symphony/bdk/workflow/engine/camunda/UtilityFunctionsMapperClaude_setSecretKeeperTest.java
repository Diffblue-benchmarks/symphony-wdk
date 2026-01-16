package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class UtilityFunctionsMapperClaude_setSecretKeeperTest {

  private SecretKeeper secretKeeper;
  private SecretKeeper anotherSecretKeeper;

  @BeforeEach
  void setUp() {
    secretKeeper = mock(SecretKeeper.class);
    anotherSecretKeeper = mock(SecretKeeper.class);
  }

  @AfterEach
  void tearDown() {
    // Clean up static state to avoid test pollution
    UtilityFunctionsMapper.setSecretKeeper(null);
  }

  @Test
  void setSecretKeeper_shouldMakeSecretKeeperAvailableToSecretMethod() {
    // Given: A SecretKeeper that returns a specific secret
    when(secretKeeper.get("apiKey")).thenReturn("secret-api-key-123");

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // Then: The secret() method should use the set keeper
    String actualSecret = UtilityFunctionsMapper.secret("apiKey");
    assertThat(actualSecret).isEqualTo("secret-api-key-123");
    verify(secretKeeper).get("apiKey");
  }

  @Test
  void setSecretKeeper_calledMultipleTimes_shouldUpdateToLatestKeeper() {
    // Given: Two different SecretKeepers
    when(secretKeeper.get("key")).thenReturn("firstSecret");
    when(anotherSecretKeeper.get("key")).thenReturn("secondSecret");

    // When: setSecretKeeper is called twice with different keepers
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);
    UtilityFunctionsMapper.setSecretKeeper(anotherSecretKeeper);

    // Then: The secret() method should use the latest keeper
    String actualSecret = UtilityFunctionsMapper.secret("key");
    assertThat(actualSecret).isEqualTo("secondSecret");
    verify(anotherSecretKeeper).get("key");
  }

  @Test
  void setSecretKeeper_withNull_shouldAllowNullSecretKeeper() {
    // When: setSecretKeeper is called with null
    UtilityFunctionsMapper.setSecretKeeper(null);

    // Then: The method should complete without throwing an exception
    // This is a valid state (though secret() would fail if called)
  }

  @Test
  void setSecretKeeper_shouldReplaceExistingKeeper() {
    // Given: An initial SecretKeeper is set
    when(secretKeeper.get("password")).thenReturn("oldPassword");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: A new SecretKeeper is set
    when(anotherSecretKeeper.get("password")).thenReturn("newPassword");
    UtilityFunctionsMapper.setSecretKeeper(anotherSecretKeeper);

    // Then: Only the new keeper should be used, not the old one
    String actualSecret = UtilityFunctionsMapper.secret("password");
    assertThat(actualSecret).isEqualTo("newPassword");
  }

  @Test
  void setSecretKeeper_shouldWorkWithDifferentSecretKeys() {
    // Given: A SecretKeeper with multiple secrets
    when(secretKeeper.get("apiKey")).thenReturn("api-secret-123");
    when(secretKeeper.get("dbPassword")).thenReturn("db-pass-456");
    when(secretKeeper.get("token")).thenReturn("token-789");

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // Then: The secret() method should work with different keys
    String apiKey = UtilityFunctionsMapper.secret("apiKey");
    String dbPassword = UtilityFunctionsMapper.secret("dbPassword");
    String token = UtilityFunctionsMapper.secret("token");

    assertThat(apiKey).isEqualTo("api-secret-123");
    assertThat(dbPassword).isEqualTo("db-pass-456");
    assertThat(token).isEqualTo("token-789");
  }

  @Test
  void setSecretKeeper_shouldPersistAcrossMultipleSecretCalls() {
    // Given: A SecretKeeper is set
    when(secretKeeper.get("persistKey")).thenReturn("persistValue");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called multiple times
    String firstCall = UtilityFunctionsMapper.secret("persistKey");
    String secondCall = UtilityFunctionsMapper.secret("persistKey");
    String thirdCall = UtilityFunctionsMapper.secret("persistKey");

    // Then: All calls should use the same SecretKeeper
    assertThat(firstCall).isEqualTo("persistValue");
    assertThat(secondCall).isEqualTo("persistValue");
    assertThat(thirdCall).isEqualTo("persistValue");
    verify(secretKeeper, times(3)).get("persistKey");
  }

  @Test
  void setSecretKeeper_shouldWorkInConjunctionWithConstructor() {
    // Given: A SecretKeeper
    when(secretKeeper.get("constructorKey")).thenReturn("constructorSecret");

    // When: UtilityFunctionsMapper constructor is called (which calls setSecretKeeper internally)
    new UtilityFunctionsMapper(null, null, secretKeeper);

    // Then: The secret() method should work with the keeper set by the constructor
    String actualSecret = UtilityFunctionsMapper.secret("constructorKey");
    assertThat(actualSecret).isEqualTo("constructorSecret");
    verify(secretKeeper).get("constructorKey");
  }

  @Test
  void setSecretKeeper_afterConstructor_shouldOverrideConstructorSetKeeper() {
    // Given: UtilityFunctionsMapper constructor is called with first keeper
    when(secretKeeper.get("key")).thenReturn("firstSecret");
    new UtilityFunctionsMapper(null, null, secretKeeper);

    // When: setSecretKeeper is called with a different keeper
    when(anotherSecretKeeper.get("key")).thenReturn("secondSecret");
    UtilityFunctionsMapper.setSecretKeeper(anotherSecretKeeper);

    // Then: The latest keeper should be used
    String actualSecret = UtilityFunctionsMapper.secret("key");
    assertThat(actualSecret).isEqualTo("secondSecret");
  }

  @Test
  void setSecretKeeper_shouldHandleNullReturnValues() {
    // Given: A SecretKeeper that returns null for unknown keys
    when(secretKeeper.get("unknownKey")).thenReturn(null);

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // Then: secret() should return null for unknown keys
    String value = UtilityFunctionsMapper.secret("unknownKey");
    assertThat(value).isNull();
  }

  @Test
  void setSecretKeeper_shouldHandleEmptyStringSecrets() {
    // Given: A SecretKeeper that returns empty strings
    when(secretKeeper.get("emptyKey")).thenReturn("");

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // Then: secret() should return the empty string
    String value = UtilityFunctionsMapper.secret("emptyKey");
    assertThat(value).isEqualTo("");
  }

  @Test
  void setSecretKeeper_shouldWorkWithSpecialCharactersInSecrets() {
    // Given: A SecretKeeper with special characters in secrets
    when(secretKeeper.get("special")).thenReturn("!@#$%^&*()_+-={}[]|:;<>?,./");

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // Then: The secret() method should return the value with special characters
    String secret = UtilityFunctionsMapper.secret("special");
    assertThat(secret).isEqualTo("!@#$%^&*()_+-={}[]|:;<>?,./");
  }

  @Test
  void setSecretKeeper_shouldWorkWithLongSecretValues() {
    // Given: A SecretKeeper with a very long secret
    String longSecret = "a".repeat(1000);
    when(secretKeeper.get("longKey")).thenReturn(longSecret);

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // Then: The secret() method should return the long value
    String secret = UtilityFunctionsMapper.secret("longKey");
    assertThat(secret).hasSize(1000);
    assertThat(secret).isEqualTo(longSecret);
  }

  @Test
  void setSecretKeeper_shouldWorkWithMultilineSecrets() {
    // Given: A SecretKeeper with multiline secrets
    String multilineSecret = "line1\nline2\nline3";
    when(secretKeeper.get("multiline")).thenReturn(multilineSecret);

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // Then: The secret() method should return the multiline value
    String secret = UtilityFunctionsMapper.secret("multiline");
    assertThat(secret).isEqualTo(multilineSecret);
    assertThat(secret).contains("\n");
  }

  @Test
  void setSecretKeeper_shouldSupportMultipleConsecutiveGets() {
    // Given: A SecretKeeper with different secrets
    when(secretKeeper.get("key1")).thenReturn("value1");
    when(secretKeeper.get("key2")).thenReturn("value2");
    when(secretKeeper.get("key3")).thenReturn("value3");

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // And: Multiple consecutive secret() calls are made
    String val1 = UtilityFunctionsMapper.secret("key1");
    String val2 = UtilityFunctionsMapper.secret("key2");
    String val3 = UtilityFunctionsMapper.secret("key3");

    // Then: All calls should succeed with correct values
    assertThat(val1).isEqualTo("value1");
    assertThat(val2).isEqualTo("value2");
    assertThat(val3).isEqualTo("value3");
    verify(secretKeeper).get("key1");
    verify(secretKeeper).get("key2");
    verify(secretKeeper).get("key3");
  }

  @Test
  void setSecretKeeper_shouldWorkWithCaseSensitiveKeys() {
    // Given: A SecretKeeper with case-sensitive keys
    when(secretKeeper.get("apiKey")).thenReturn("lowercase");
    when(secretKeeper.get("ApiKey")).thenReturn("mixedcase");
    when(secretKeeper.get("APIKEY")).thenReturn("uppercase");

    // When: setSecretKeeper is called
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // Then: The secret() method should respect case sensitivity
    String lowercase = UtilityFunctionsMapper.secret("apiKey");
    String mixedcase = UtilityFunctionsMapper.secret("ApiKey");
    String uppercase = UtilityFunctionsMapper.secret("APIKEY");

    assertThat(lowercase).isEqualTo("lowercase");
    assertThat(mixedcase).isEqualTo("mixedcase");
    assertThat(uppercase).isEqualTo("uppercase");
  }
}
