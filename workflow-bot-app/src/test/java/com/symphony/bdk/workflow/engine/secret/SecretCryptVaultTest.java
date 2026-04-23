package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecretCryptVaultTest {

  private static final int VERSION = 0;
  private SecretCryptVault vault;
  private byte[] key32Bytes;

  @BeforeEach
  void setUp() {
    vault = new SecretCryptVault();
    key32Bytes = new byte[32];
    new SecureRandom().nextBytes(key32Bytes);
  }

  // ---- with256BitAesGcmNoPaddingAnd64BitSaltKey ----

  @Test
  void shouldReturnVaultWhenValidKeyProvided() {
    // when
    SecretCryptVault result = vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(VERSION, key32Bytes);

    // then
    assertThat(result).isSameAs(vault);
  }

  @Test
  void shouldThrowWhenKeyIsNot32Bytes() {
    // given
    byte[] invalidKey = new byte[16];

    // when / then
    assertThatThrownBy(() -> vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(VERSION, invalidKey))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("256 bits");
  }

  // ---- withKey ----

  @Test
  void shouldThrowWhenVersionAlreadyDefined() {
    // given
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(VERSION, key32Bytes);

    // when / then
    assertThatThrownBy(() -> vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(VERSION, key32Bytes))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("already defined");
  }

  @Test
  void shouldThrowWhenVersionOutOfRange() {
    // when / then
    assertThatThrownBy(() -> vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(256, key32Bytes))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("version must be a byte");
  }

  @Test
  void shouldSetHigherVersionAsDefault() {
    // given
    byte[] key2 = new byte[32];
    new SecureRandom().nextBytes(key2);

    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, key32Bytes);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(1, key2);

    // when - encrypt with default (should use version 1)
    byte[] plaintext = "hello world".getBytes();
    byte[] encrypted = vault.encrypt(plaintext);

    // then - version byte is first byte
    assertThat(SecretCryptVault.fromSignedByte(encrypted[0])).isEqualTo(1);
  }

  // ---- encrypt / decrypt round-trip ----

  @Test
  void shouldEncryptAndDecryptSuccessfully() {
    // given
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(VERSION, key32Bytes);
    byte[] plaintext = "secret message".getBytes();

    // when
    byte[] encrypted = vault.encrypt(plaintext);
    byte[] decrypted = vault.decrypt(encrypted);

    // then
    assertThat(decrypted).isEqualTo(plaintext);
  }

  @Test
  void shouldEncryptWithSpecificVersion() {
    // given
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(VERSION, key32Bytes);
    byte[] plaintext = "data".getBytes();

    // when
    byte[] encrypted = vault.encrypt(VERSION, plaintext);

    // then
    assertThat(SecretCryptVault.fromSignedByte(encrypted[0])).isEqualTo(VERSION);
  }

  // ---- cryptVersion error cases ----

  @Test
  void shouldThrowWhenEncryptingWithUninitializedKeys() {
    // when / then
    assertThatThrownBy(() -> vault.encrypt("data".getBytes()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("not initialized");
  }

  @Test
  void shouldThrowWhenDecryptingWithUndefinedVersion() {
    // given
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(VERSION, key32Bytes);
    // version byte 1 is not registered
    byte[] fakeData = new byte[20];
    fakeData[0] = SecretCryptVault.toSignedByte(1);

    // when / then
    assertThatThrownBy(() -> vault.decrypt(fakeData))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("undefined");
  }

  // ---- withDefaultKeyVersion ----

  @Test
  void shouldSetDefaultVersionExplicitly() {
    // given
    byte[] key2 = new byte[32];
    new SecureRandom().nextBytes(key2);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, key32Bytes);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(1, key2);

    // when
    SecretCryptVault result = vault.withDefaultKeyVersion(0);

    // then
    assertThat(result).isSameAs(vault);
    byte[] plaintext = "test".getBytes();
    byte[] encrypted = vault.encrypt(plaintext);
    assertThat(SecretCryptVault.fromSignedByte(encrypted[0])).isEqualTo(0);
  }

  @Test
  void shouldThrowWhenSettingDefaultVersionForUndefinedKey() {
    // when / then
    assertThatThrownBy(() -> vault.withDefaultKeyVersion(5))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("undefined");
  }

  @Test
  void shouldThrowWhenDefaultVersionOutOfRange() {
    // when / then
    assertThatThrownBy(() -> vault.withDefaultKeyVersion(256))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("version must be a byte");
  }

  // ---- reInitSecureRandomHourly ----

  @Test
  void shouldReInitSecureRandomWithoutException() {
    // when / then - just verify no exception
    vault.reInitSecureRandomHourly();
  }

  // ---- toSignedByte / fromSignedByte ----

  @Test
  void shouldConvertVersionToSignedByteAndBack() {
    // when
    for (int v = 0; v <= 255; v++) {
      byte signed = SecretCryptVault.toSignedByte(v);
      int recovered = SecretCryptVault.fromSignedByte(signed);
      assertThat(recovered).isEqualTo(v);
    }
  }
}
