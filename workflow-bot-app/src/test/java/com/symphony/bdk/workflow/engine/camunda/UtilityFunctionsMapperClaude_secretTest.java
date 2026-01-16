package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class UtilityFunctionsMapperClaude_secretTest {

  private SecretKeeper secretKeeper;

  @BeforeEach
  void setUp() {
    secretKeeper = mock(SecretKeeper.class);
  }

  @AfterEach
  void tearDown() {
    // Clean up static state to avoid test pollution
    UtilityFunctionsMapper.setSecretKeeper(null);
  }

  @Test
  void secret_withValidKey_shouldReturnSecretValue() {
    // Given: A SecretKeeper with a secret
    when(secretKeeper.get("apiKey")).thenReturn("secret-api-key-123");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with the key
    String result = UtilityFunctionsMapper.secret("apiKey");

    // Then: Should return the secret value
    assertThat(result).isEqualTo("secret-api-key-123");
    verify(secretKeeper).get("apiKey");
  }

  @Test
  void secret_withNonExistentKey_shouldReturnNull() {
    // Given: A SecretKeeper that returns null for unknown keys
    when(secretKeeper.get("unknownKey")).thenReturn(null);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with an unknown key
    String result = UtilityFunctionsMapper.secret("unknownKey");

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void secret_withEmptyStringKey_shouldInvokeSecretKeeper() {
    // Given: A SecretKeeper configured to respond to empty key
    when(secretKeeper.get("")).thenReturn("secret-for-empty-key");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with empty string
    String result = UtilityFunctionsMapper.secret("");

    // Then: Should invoke the SecretKeeper and return its value
    assertThat(result).isEqualTo("secret-for-empty-key");
    verify(secretKeeper).get("");
  }

  @Test
  void secret_withNullKey_shouldPassNullToSecretKeeper() {
    // Given: A SecretKeeper configured to handle null key
    when(secretKeeper.get(null)).thenReturn(null);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with null
    String result = UtilityFunctionsMapper.secret(null);

    // Then: Should pass null to SecretKeeper
    assertThat(result).isNull();
    verify(secretKeeper).get(null);
  }

  @Test
  void secret_whenSecretKeeperReturnsEmptyString_shouldReturnEmptyString() {
    // Given: A SecretKeeper that returns empty string
    when(secretKeeper.get("emptySecret")).thenReturn("");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("emptySecret");

    // Then: Should return empty string
    assertThat(result).isEqualTo("");
    assertThat(result).isEmpty();
  }

  @Test
  void secret_withMultipleDifferentKeys_shouldReturnCorrectValues() {
    // Given: A SecretKeeper with multiple secrets
    when(secretKeeper.get("apiKey")).thenReturn("api-secret-123");
    when(secretKeeper.get("dbPassword")).thenReturn("db-pass-456");
    when(secretKeeper.get("token")).thenReturn("token-789");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with different keys
    String apiKey = UtilityFunctionsMapper.secret("apiKey");
    String dbPassword = UtilityFunctionsMapper.secret("dbPassword");
    String token = UtilityFunctionsMapper.secret("token");

    // Then: Should return correct values for each key
    assertThat(apiKey).isEqualTo("api-secret-123");
    assertThat(dbPassword).isEqualTo("db-pass-456");
    assertThat(token).isEqualTo("token-789");
  }

  @Test
  void secret_calledMultipleTimesWithSameKey_shouldInvokeSecretKeeperEachTime() {
    // Given: A SecretKeeper with a secret
    when(secretKeeper.get("repeatKey")).thenReturn("repeatValue");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called multiple times with the same key
    String first = UtilityFunctionsMapper.secret("repeatKey");
    String second = UtilityFunctionsMapper.secret("repeatKey");
    String third = UtilityFunctionsMapper.secret("repeatKey");

    // Then: Should return the same value each time
    assertThat(first).isEqualTo("repeatValue");
    assertThat(second).isEqualTo("repeatValue");
    assertThat(third).isEqualTo("repeatValue");
    verify(secretKeeper, times(3)).get("repeatKey");
  }

  @Test
  void secret_withLongSecretValue_shouldReturnCompleteValue() {
    // Given: A SecretKeeper with a long secret
    String longSecret = "a".repeat(10000);
    when(secretKeeper.get("longKey")).thenReturn(longSecret);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("longKey");

    // Then: Should return the complete long value
    assertThat(result).hasSize(10000);
    assertThat(result).isEqualTo(longSecret);
  }

  @Test
  void secret_withSpecialCharactersInKey_shouldHandleCorrectly() {
    // Given: A SecretKeeper with special characters in key
    when(secretKeeper.get("api.key.prod")).thenReturn("secret1");
    when(secretKeeper.get("api-key-dev")).thenReturn("secret2");
    when(secretKeeper.get("api_key_test")).thenReturn("secret3");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with special character keys
    String dotKey = UtilityFunctionsMapper.secret("api.key.prod");
    String dashKey = UtilityFunctionsMapper.secret("api-key-dev");
    String underscoreKey = UtilityFunctionsMapper.secret("api_key_test");

    // Then: Should handle all special characters correctly
    assertThat(dotKey).isEqualTo("secret1");
    assertThat(dashKey).isEqualTo("secret2");
    assertThat(underscoreKey).isEqualTo("secret3");
  }

  @Test
  void secret_withSpecialCharactersInValue_shouldReturnUnmodified() {
    // Given: A SecretKeeper with special characters in value
    String specialValue = "!@#$%^&*()_+-={}[]|:;<>?,./~`";
    when(secretKeeper.get("specialKey")).thenReturn(specialValue);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("specialKey");

    // Then: Should return value with special characters unmodified
    assertThat(result).isEqualTo(specialValue);
  }

  @Test
  void secret_withMultilineSecret_shouldReturnWithNewlines() {
    // Given: A SecretKeeper with multiline secret
    String multilineSecret = "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBg\n-----END PRIVATE KEY-----";
    when(secretKeeper.get("privateKey")).thenReturn(multilineSecret);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("privateKey");

    // Then: Should return the multiline value with newlines preserved
    assertThat(result).isEqualTo(multilineSecret);
    assertThat(result).contains("\n");
    assertThat(result.split("\n")).hasSize(3);
  }

  @Test
  void secret_withUnicodeCharacters_shouldReturnUnicodeCorrectly() {
    // Given: A SecretKeeper with Unicode characters
    when(secretKeeper.get("unicodeSecret")).thenReturn("密碼123🔐");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("unicodeSecret");

    // Then: Should return Unicode characters correctly
    assertThat(result).isEqualTo("密碼123🔐");
  }

  @Test
  void secret_withWhitespaceInKey_shouldPassToSecretKeeper() {
    // Given: A SecretKeeper configured for keys with whitespace
    when(secretKeeper.get("key with spaces")).thenReturn("value1");
    when(secretKeeper.get(" keyWithLeadingSpace")).thenReturn("value2");
    when(secretKeeper.get("keyWithTrailingSpace ")).thenReturn("value3");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with whitespace in keys
    String value1 = UtilityFunctionsMapper.secret("key with spaces");
    String value2 = UtilityFunctionsMapper.secret(" keyWithLeadingSpace");
    String value3 = UtilityFunctionsMapper.secret("keyWithTrailingSpace ");

    // Then: Should pass keys as-is to SecretKeeper
    assertThat(value1).isEqualTo("value1");
    assertThat(value2).isEqualTo("value2");
    assertThat(value3).isEqualTo("value3");
  }

  @Test
  void secret_withCaseSensitiveKeys_shouldDistinguishCases() {
    // Given: A SecretKeeper with case-sensitive keys
    when(secretKeeper.get("apikey")).thenReturn("lowercase");
    when(secretKeeper.get("apiKey")).thenReturn("camelCase");
    when(secretKeeper.get("ApiKey")).thenReturn("PascalCase");
    when(secretKeeper.get("APIKEY")).thenReturn("UPPERCASE");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with different cases
    String lowercase = UtilityFunctionsMapper.secret("apikey");
    String camelCase = UtilityFunctionsMapper.secret("apiKey");
    String pascalCase = UtilityFunctionsMapper.secret("ApiKey");
    String uppercase = UtilityFunctionsMapper.secret("APIKEY");

    // Then: Should distinguish between different cases
    assertThat(lowercase).isEqualTo("lowercase");
    assertThat(camelCase).isEqualTo("camelCase");
    assertThat(pascalCase).isEqualTo("PascalCase");
    assertThat(uppercase).isEqualTo("UPPERCASE");
  }

  @Test
  void secret_withNumericKey_shouldWork() {
    // Given: A SecretKeeper with numeric string as key
    when(secretKeeper.get("12345")).thenReturn("numericKeySecret");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with numeric key
    String result = UtilityFunctionsMapper.secret("12345");

    // Then: Should return the value
    assertThat(result).isEqualTo("numericKeySecret");
  }

  @Test
  void secret_withJsonLikeSecret_shouldReturnAsString() {
    // Given: A SecretKeeper with JSON-like secret
    String jsonSecret = "{\"username\":\"admin\",\"password\":\"secret\"}";
    when(secretKeeper.get("jsonKey")).thenReturn(jsonSecret);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("jsonKey");

    // Then: Should return the JSON as a plain string
    assertThat(result).isEqualTo(jsonSecret);
  }

  @Test
  void secret_withBase64EncodedSecret_shouldReturnAsIs() {
    // Given: A SecretKeeper with base64 encoded secret
    String base64Secret = "dXNlcm5hbWU6cGFzc3dvcmQ=";
    when(secretKeeper.get("base64Key")).thenReturn(base64Secret);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("base64Key");

    // Then: Should return the base64 string as-is
    assertThat(result).isEqualTo(base64Secret);
  }

  @Test
  void secret_whenSecretKeeperIsNull_shouldThrowNullPointerException() {
    // Given: No SecretKeeper is set (null)
    UtilityFunctionsMapper.setSecretKeeper(null);

    // When/Then: secret() is called, should throw NullPointerException
    assertThatThrownBy(() -> UtilityFunctionsMapper.secret("anyKey"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void secret_afterSecretKeeperReplacement_shouldUseNewKeeper() {
    // Given: First SecretKeeper is set
    SecretKeeper firstKeeper = mock(SecretKeeper.class);
    when(firstKeeper.get("key")).thenReturn("firstValue");
    UtilityFunctionsMapper.setSecretKeeper(firstKeeper);

    // When: First call to secret()
    String firstResult = UtilityFunctionsMapper.secret("key");

    // Then: Should return first value
    assertThat(firstResult).isEqualTo("firstValue");

    // Given: SecretKeeper is replaced
    SecretKeeper secondKeeper = mock(SecretKeeper.class);
    when(secondKeeper.get("key")).thenReturn("secondValue");
    UtilityFunctionsMapper.setSecretKeeper(secondKeeper);

    // When: Second call to secret()
    String secondResult = UtilityFunctionsMapper.secret("key");

    // Then: Should return second value from new keeper
    assertThat(secondResult).isEqualTo("secondValue");
  }

  @Test
  void secret_withVeryLongKey_shouldWork() {
    // Given: A SecretKeeper with a very long key
    String longKey = "a".repeat(1000);
    when(secretKeeper.get(longKey)).thenReturn("longKeyValue");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with long key
    String result = UtilityFunctionsMapper.secret(longKey);

    // Then: Should work correctly
    assertThat(result).isEqualTo("longKeyValue");
    verify(secretKeeper).get(longKey);
  }

  @Test
  void secret_withKeyContainingPathSeparators_shouldPassThrough() {
    // Given: A SecretKeeper with path-like keys
    when(secretKeeper.get("config/prod/api-key")).thenReturn("prodApiKey");
    when(secretKeeper.get("config\\dev\\api-key")).thenReturn("devApiKey");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called with path-like keys
    String prodKey = UtilityFunctionsMapper.secret("config/prod/api-key");
    String devKey = UtilityFunctionsMapper.secret("config\\dev\\api-key");

    // Then: Should handle path separators correctly
    assertThat(prodKey).isEqualTo("prodApiKey");
    assertThat(devKey).isEqualTo("devApiKey");
  }

  @Test
  void secret_withUrlAsSecret_shouldReturnComplete() {
    // Given: A SecretKeeper with URL as secret
    String urlSecret = "https://user:password@example.com:8080/path?query=value#fragment";
    when(secretKeeper.get("serviceUrl")).thenReturn(urlSecret);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("serviceUrl");

    // Then: Should return the complete URL
    assertThat(result).isEqualTo(urlSecret);
  }

  @Test
  void secret_withEscapeSequences_shouldReturnAsProvided() {
    // Given: A SecretKeeper with escape sequences
    String secretWithEscapes = "line1\\nline2\\ttabbed";
    when(secretKeeper.get("escaped")).thenReturn(secretWithEscapes);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("escaped");

    // Then: Should return the value as provided by SecretKeeper
    assertThat(result).isEqualTo(secretWithEscapes);
  }

  @Test
  void secret_consecutiveCallsWithDifferentKeys_shouldReturnDifferentValues() {
    // Given: A SecretKeeper with different values for different keys
    when(secretKeeper.get("key1")).thenReturn("value1");
    when(secretKeeper.get("key2")).thenReturn("value2");
    when(secretKeeper.get("key3")).thenReturn("value3");
    when(secretKeeper.get("key4")).thenReturn("value4");
    when(secretKeeper.get("key5")).thenReturn("value5");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: Multiple consecutive calls with different keys
    String val1 = UtilityFunctionsMapper.secret("key1");
    String val2 = UtilityFunctionsMapper.secret("key2");
    String val3 = UtilityFunctionsMapper.secret("key3");
    String val4 = UtilityFunctionsMapper.secret("key4");
    String val5 = UtilityFunctionsMapper.secret("key5");

    // Then: Each call should return its respective value
    assertThat(val1).isEqualTo("value1");
    assertThat(val2).isEqualTo("value2");
    assertThat(val3).isEqualTo("value3");
    assertThat(val4).isEqualTo("value4");
    assertThat(val5).isEqualTo("value5");
  }

  @Test
  void secret_withSecretContainingOnlyWhitespace_shouldReturnWhitespace() {
    // Given: A SecretKeeper with whitespace-only secret
    when(secretKeeper.get("whitespaceSecret")).thenReturn("   \t\n   ");
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("whitespaceSecret");

    // Then: Should return the whitespace as-is
    assertThat(result).isEqualTo("   \t\n   ");
  }

  @Test
  void secret_withHexEncodedSecret_shouldReturnAsString() {
    // Given: A SecretKeeper with hex-encoded secret
    String hexSecret = "0x48656c6c6f576f726c64";
    when(secretKeeper.get("hexKey")).thenReturn(hexSecret);
    UtilityFunctionsMapper.setSecretKeeper(secretKeeper);

    // When: secret() is called
    String result = UtilityFunctionsMapper.secret("hexKey");

    // Then: Should return the hex string as-is
    assertThat(result).isEqualTo(hexSecret);
  }

  @Test
  void secret_integrationWithConstructor_shouldWorkCorrectly() {
    // Given: A SecretKeeper configured with secrets
    when(secretKeeper.get("testKey")).thenReturn("testValue");

    // When: UtilityFunctionsMapper is instantiated via constructor
    new UtilityFunctionsMapper(null, null, secretKeeper);

    // Then: secret() should work with the SecretKeeper set by constructor
    String result = UtilityFunctionsMapper.secret("testKey");
    assertThat(result).isEqualTo("testValue");
    verify(secretKeeper).get("testKey");
  }
}
