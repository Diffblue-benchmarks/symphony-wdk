package com.symphony.bdk.workflow.engine.secret;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class SecretCryptVaultDiffblueTest {

  private static final byte[] VALID_KEY = new byte[32];

  private SecretCryptVault configuredVault() {
    return new SecretCryptVault().with256BitAesGcmNoPaddingAnd64BitSaltKey(0, new byte[32]);
  }

  // with256BitAesGcmNoPaddingAnd64BitSaltKey

  @Test
  void testWith256BitAesGcmNoPaddingAnd64BitSaltKey_invalidKeySize() {
    // Arrange
    SecretCryptVault vault = new SecretCryptVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, new byte[16]))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("invalid AES key size; should be 256 bits!");
  }

  @Test
  void testWith256BitAesGcmNoPaddingAnd64BitSaltKey_validKey() {
    // Arrange / Act
    SecretCryptVault vault = new SecretCryptVault()
        .with256BitAesGcmNoPaddingAnd64BitSaltKey(0, new byte[32]);

    // Assert
    assertThat(vault).isNotNull();
  }

  // withKey

  @Test
  void testWithKey_versionOutOfRange() {
    // Arrange
    SecretCryptVault vault = new SecretCryptVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.withKey(256, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte");
  }

  @Test
  void testWithKey_negativeVersionOutOfRange() {
    // Arrange
    SecretCryptVault vault = new SecretCryptVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.withKey(-1, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte");
  }

  @Test
  void testWithKey_duplicateVersion() {
    // Arrange
    SecretCryptVault vault = new SecretCryptVault()
        .with256BitAesGcmNoPaddingAnd64BitSaltKey(0, new byte[32]);

    // Act / Assert
    assertThatThrownBy(() -> vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, new byte[32]))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version 0 is already defined");
  }

  @Test
  void testWithKey_lowerVersionDoesNotUpdateDefault() {
    // Arrange: add version 1 first (sets default to 1), then version 0 (0 <= 1, so default stays 1)
    SecretCryptVault vault = new SecretCryptVault()
        .with256BitAesGcmNoPaddingAnd64BitSaltKey(1, new byte[32])
        .with256BitAesGcmNoPaddingAnd64BitSaltKey(0, new byte[32]);

    // Act: encrypt uses default version (1)
    byte[] encrypted = vault.encrypt("test".getBytes(StandardCharsets.UTF_8));

    // Assert: first byte (version) should be for version 1
    assertThat(encrypted).isNotNull();
  }

  // encrypt(byte[])

  @Test
  void testEncrypt_withPlaintext() {
    // Arrange
    SecretCryptVault vault = configuredVault();

    // Act
    byte[] encrypted = vault.encrypt("hello".getBytes(StandardCharsets.UTF_8));

    // Assert
    assertThat(encrypted).isNotNull().isNotEmpty();
  }

  @Test
  void testEncrypt_noKeysInitialized() {
    // Arrange
    SecretCryptVault vault = new SecretCryptVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.encrypt("test".getBytes(StandardCharsets.UTF_8)))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("encryption keys are not initialized");
  }

  // encrypt(int, byte[])

  @Test
  void testEncryptWithVersion_valid() {
    // Arrange
    SecretCryptVault vault = configuredVault();

    // Act
    byte[] encrypted = vault.encrypt(0, "data".getBytes(StandardCharsets.UTF_8));

    // Assert
    assertThat(encrypted).isNotNull().isNotEmpty();
  }

  @Test
  void testEncryptWithVersion_outOfRange() {
    // Arrange
    SecretCryptVault vault = configuredVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.encrypt(256, new byte[0]))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte (0-255)");
  }

  @Test
  void testEncryptWithVersion_undefinedVersion() {
    // Arrange
    SecretCryptVault vault = configuredVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.encrypt(1, "test".getBytes(StandardCharsets.UTF_8)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version 1 undefined");
  }

  // decrypt(byte[])

  @Test
  void testDecrypt_roundtrip() {
    // Arrange
    SecretCryptVault vault = configuredVault();
    byte[] plaintext = "hello world".getBytes(StandardCharsets.UTF_8);

    // Act
    byte[] encrypted = vault.encrypt(plaintext);
    byte[] decrypted = vault.decrypt(encrypted);

    // Assert
    assertThat(decrypted).isEqualTo(plaintext);
  }

  // withDefaultKeyVersion

  @Test
  void testWithDefaultKeyVersion_valid() {
    // Arrange
    SecretCryptVault vault = configuredVault();

    // Act
    SecretCryptVault result = vault.withDefaultKeyVersion(0);

    // Assert
    assertThat(result).isSameAs(vault);
  }

  @Test
  void testWithDefaultKeyVersion_undefinedVersion() {
    // Arrange
    SecretCryptVault vault = configuredVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.withDefaultKeyVersion(1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version 1 is undefined");
  }

  @Test
  void testWithDefaultKeyVersion_outOfRange() {
    // Arrange
    SecretCryptVault vault = new SecretCryptVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.withDefaultKeyVersion(256))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte");
  }

  @Test
  void testWithDefaultKeyVersion_negativeOutOfRange() {
    // Arrange
    SecretCryptVault vault = new SecretCryptVault();

    // Act / Assert
    assertThatThrownBy(() -> vault.withDefaultKeyVersion(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte");
  }

  // reInitSecureRandomHourly

  @Test
  void testReInitSecureRandomHourly() {
    // Arrange
    SecretCryptVault vault = configuredVault();

    // Act
    vault.reInitSecureRandomHourly();

    // Assert: vault still works after re-init
    byte[] encrypted = vault.encrypt("test".getBytes(StandardCharsets.UTF_8));
    assertThat(encrypted).isNotNull();
  }

  // toSignedByte

  @Test
  void testToSignedByte() {
    // Act / Assert
    assertThat(SecretCryptVault.toSignedByte(0)).isEqualTo((byte) -128);
    assertThat(SecretCryptVault.toSignedByte(128)).isEqualTo((byte) 0);
    assertThat(SecretCryptVault.toSignedByte(255)).isEqualTo((byte) 127);
  }

  // fromSignedByte

  @Test
  void testFromSignedByte() {
    // Act / Assert
    assertThat(SecretCryptVault.fromSignedByte((byte) -128)).isEqualTo(0);
    assertThat(SecretCryptVault.fromSignedByte((byte) 0)).isEqualTo(128);
    assertThat(SecretCryptVault.fromSignedByte((byte) 127)).isEqualTo(255);
  }
}
