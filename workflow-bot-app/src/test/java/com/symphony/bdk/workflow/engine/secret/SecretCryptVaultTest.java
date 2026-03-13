package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SecretCryptVaultTest {

  @Mock
  private Key key;

  @Test
  void shouldCreateCryptVersionWithAllParameters() {
    int saltLength = 8;
    String cipher = "AES/GCM/NoPadding";
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength, cipher, key, encryptedLength);

    assertThat(cryptVersion).isNotNull();
    assertThat(cryptVersion.saltLength).isEqualTo(saltLength);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isEqualTo(key);
    assertThat(cryptVersion.encryptedLength).isEqualTo(encryptedLength);
  }

  @Test
  void shouldCreateCryptVersionWithDifferentSaltLength() {
    int saltLength = 16;
    String cipher = "AES/CBC/PKCS5Padding";
    Function<Integer, Integer> encryptedLength = i -> i + 16;

    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength, cipher, key, encryptedLength);

    assertThat(cryptVersion.saltLength).isEqualTo(saltLength);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isEqualTo(key);
    assertThat(cryptVersion.encryptedLength).isEqualTo(encryptedLength);
  }

  @Test
  void shouldCreateCryptVersionWithZeroSaltLength() {
    int saltLength = 0;
    String cipher = "AES/GCM/NoPadding";
    Function<Integer, Integer> encryptedLength = i -> i;

    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength, cipher, key, encryptedLength);

    assertThat(cryptVersion.saltLength).isEqualTo(0);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isEqualTo(key);
    assertThat(cryptVersion.encryptedLength).isEqualTo(encryptedLength);
  }
}
