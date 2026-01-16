package com.symphony.bdk.workflow.configuration;

import com.symphony.bdk.workflow.engine.secret.SecretCryptVault;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkflowBotConfigurationClaude_cryptJpaVaultTest {

  // Tests for cryptJpaVault(String) method

  @Test
  void cryptJpaVault_withValidPassphrase_shouldReturnNonNullInstance() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a valid passphrase (>= 16 characters)
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String validPassphrase = "ValidPassphrase123";

    // When: cryptJpaVault is called with a valid passphrase
    SecretCryptVault vault = config.cryptJpaVault(validPassphrase);

    // Then: The result should not be null
    assertThat(vault).isNotNull();
  }

  @Test
  void cryptJpaVault_withValidPassphrase_shouldReturnSecretCryptVaultInstance() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a valid passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String validPassphrase = "ValidPassphrase123";

    // When: cryptJpaVault is called
    SecretCryptVault vault = config.cryptJpaVault(validPassphrase);

    // Then: The result should be an instance of SecretCryptVault
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withMinimumLengthPassphrase_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a passphrase with exactly 16 characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String minimumPassphrase = "1234567890123456"; // Exactly 16 characters

    // When: cryptJpaVault is called with minimum length passphrase
    SecretCryptVault vault = config.cryptJpaVault(minimumPassphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withPassphraseLessThan16Characters_shouldThrowIllegalArgumentException() {
    // Given: A WorkflowBotConfiguration instance and a passphrase with less than 16 characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String shortPassphrase = "Short12345"; // 11 characters

    // When/Then: cryptJpaVault should throw IllegalArgumentException
    assertThatThrownBy(() -> config.cryptJpaVault(shortPassphrase))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("encryption passphrase length must have at least 16 characters");
  }

  @Test
  void cryptJpaVault_with15CharacterPassphrase_shouldThrowIllegalArgumentException() {
    // Given: A WorkflowBotConfiguration instance and a passphrase with exactly 15 characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String passphrase = "123456789012345"; // Exactly 15 characters

    // When/Then: cryptJpaVault should throw IllegalArgumentException
    assertThatThrownBy(() -> config.cryptJpaVault(passphrase))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("encryption passphrase length must have at least 16 characters");
  }

  @Test
  void cryptJpaVault_withEmptyPassphrase_shouldThrowIllegalArgumentException() {
    // Given: A WorkflowBotConfiguration instance and an empty passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String emptyPassphrase = "";

    // When/Then: cryptJpaVault should throw IllegalArgumentException
    assertThatThrownBy(() -> config.cryptJpaVault(emptyPassphrase))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("encryption passphrase length must have at least 16 characters");
  }

  @Test
  void cryptJpaVault_withSingleCharacter_shouldThrowIllegalArgumentException() {
    // Given: A WorkflowBotConfiguration instance and a single character passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String singleChar = "a";

    // When/Then: cryptJpaVault should throw IllegalArgumentException
    assertThatThrownBy(() -> config.cryptJpaVault(singleChar))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("encryption passphrase length must have at least 16 characters");
  }

  @Test
  void cryptJpaVault_withLongPassphrase_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a long passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String longPassphrase = "ThisIsAVeryLongPassphraseThatExceedsTheMinimumRequirement1234567890";

    // When: cryptJpaVault is called with a long passphrase
    SecretCryptVault vault = config.cryptJpaVault(longPassphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withPassphraseContainingSpecialCharacters_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a passphrase with special characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String specialPassphrase = "P@ssw0rd!#$%^&*()_+";

    // When: cryptJpaVault is called with special characters in passphrase
    SecretCryptVault vault = config.cryptJpaVault(specialPassphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withPassphraseContainingUnicode_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a passphrase with unicode characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String unicodePassphrase = "Pässwörd1234567890";

    // When: cryptJpaVault is called with unicode characters
    SecretCryptVault vault = config.cryptJpaVault(unicodePassphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withPassphraseContainingSpaces_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a passphrase with spaces
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String passphraseWithSpaces = "Pass word 1234567890";

    // When: cryptJpaVault is called with spaces in passphrase
    SecretCryptVault vault = config.cryptJpaVault(passphraseWithSpaces);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_calledTwiceWithSamePassphrase_shouldReturnDistinctInstances() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a valid passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String passphrase = "ValidPassphrase123";

    // When: cryptJpaVault is called twice with the same passphrase
    SecretCryptVault vault1 = config.cryptJpaVault(passphrase);
    SecretCryptVault vault2 = config.cryptJpaVault(passphrase);

    // Then: Each call should return a distinct instance
    assertThat(vault1).isNotNull();
    assertThat(vault2).isNotNull();
    assertThat(vault1).isNotSameAs(vault2);
  }

  @Test
  void cryptJpaVault_calledWithDifferentPassphrases_shouldReturnDistinctInstances() throws Exception {
    // Given: A WorkflowBotConfiguration instance and two different passphrases
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String passphrase1 = "FirstPassphrase123";
    String passphrase2 = "SecondPassphrase456";

    // When: cryptJpaVault is called with different passphrases
    SecretCryptVault vault1 = config.cryptJpaVault(passphrase1);
    SecretCryptVault vault2 = config.cryptJpaVault(passphrase2);

    // Then: Each call should return a distinct instance
    assertThat(vault1).isNotNull();
    assertThat(vault2).isNotNull();
    assertThat(vault1).isNotSameAs(vault2);
  }

  @Test
  void cryptJpaVault_withValidPassphrase_shouldNotThrowException() {
    // Given: A WorkflowBotConfiguration instance and a valid passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String validPassphrase = "ValidPassphrase123";

    // When/Then: cryptJpaVault should not throw any exception
    assertThatCode(() -> config.cryptJpaVault(validPassphrase))
        .doesNotThrowAnyException();
  }

  @Test
  void cryptJpaVault_with17CharacterPassphrase_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a passphrase with 17 characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String passphrase = "12345678901234567"; // Exactly 17 characters

    // When: cryptJpaVault is called
    SecretCryptVault vault = config.cryptJpaVault(passphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withNumericOnlyPassphrase_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a numeric-only passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String numericPassphrase = "1234567890123456";

    // When: cryptJpaVault is called with numeric passphrase
    SecretCryptVault vault = config.cryptJpaVault(numericPassphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withAlphabeticOnlyPassphrase_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and an alphabetic-only passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String alphabeticPassphrase = "abcdefghijklmnop";

    // When: cryptJpaVault is called with alphabetic passphrase
    SecretCryptVault vault = config.cryptJpaVault(alphabeticPassphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withMixedCasePassphrase_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a mixed-case passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String mixedCasePassphrase = "AbCdEfGhIjKlMnOp";

    // When: cryptJpaVault is called with mixed-case passphrase
    SecretCryptVault vault = config.cryptJpaVault(mixedCasePassphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_calledMultipleTimes_shouldSucceedEachTime() {
    // Given: A WorkflowBotConfiguration instance and a valid passphrase
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String passphrase = "ValidPassphrase123";

    // When: cryptJpaVault is called multiple times
    // Then: Each call should succeed without throwing an exception
    assertThatCode(() -> {
      config.cryptJpaVault(passphrase);
      config.cryptJpaVault(passphrase);
      config.cryptJpaVault(passphrase);
    }).doesNotThrowAnyException();
  }

  @Test
  void cryptJpaVault_withPassphraseOfExactly50Characters_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a passphrase with exactly 50 characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String passphrase = "12345678901234567890123456789012345678901234567890"; // 50 characters

    // When: cryptJpaVault is called
    SecretCryptVault vault = config.cryptJpaVault(passphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }

  @Test
  void cryptJpaVault_withRepeatingCharactersPassphrase_shouldSucceed() throws Exception {
    // Given: A WorkflowBotConfiguration instance and a passphrase with repeating characters
    WorkflowBotConfiguration config = new WorkflowBotConfiguration();
    String repeatingPassphrase = "aaaaaaaaaaaaaaaa"; // 16 'a' characters

    // When: cryptJpaVault is called
    SecretCryptVault vault = config.cryptJpaVault(repeatingPassphrase);

    // Then: The vault should be created successfully
    assertThat(vault).isNotNull();
    assertThat(vault).isInstanceOf(SecretCryptVault.class);
  }
}
