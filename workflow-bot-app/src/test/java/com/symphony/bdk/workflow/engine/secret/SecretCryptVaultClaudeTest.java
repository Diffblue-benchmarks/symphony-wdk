package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecretCryptVaultClaudeTest {

  private SecretCryptVault vault;
  private byte[] validAesKey256;

  @BeforeEach
  void setUp() {
    vault = new SecretCryptVault();
    // Create a valid 256-bit (32 bytes) AES key
    validAesKey256 = new byte[32];
    new SecureRandom().nextBytes(validAesKey256);
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldInitializeCorrectly() {
    // When: Creating a new SecretCryptVault
    SecretCryptVault newVault = new SecretCryptVault();

    // Then: Object should be created successfully
    assertThat(newVault).isNotNull();
  }

  // ==================== with256BitAesGcmNoPaddingAnd64BitSaltKey() Tests ====================

  @Test
  void with256BitAesGcmNoPaddingAnd64BitSaltKey_withValid32ByteKey_shouldSucceed() {
    // Given: A valid 256-bit key
    byte[] key = new byte[32];
    Arrays.fill(key, (byte) 1);

    // When: Adding the key with version 0
    SecretCryptVault result = vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, key);

    // Then: Should return the vault instance for chaining
    assertThat(result).isSameAs(vault);
  }

  @Test
  void with256BitAesGcmNoPaddingAnd64BitSaltKey_withInvalidKeySize_shouldThrowException() {
    // Given: An invalid key size (not 32 bytes)
    byte[] tooShortKey = new byte[16];
    byte[] tooLongKey = new byte[64];

    // When & Then: Should throw IllegalArgumentException for wrong key sizes
    assertThatThrownBy(() -> vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, tooShortKey))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("invalid AES key size; should be 256 bits!");

    assertThatThrownBy(() -> vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, tooLongKey))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("invalid AES key size; should be 256 bits!");
  }

  @Test
  void with256BitAesGcmNoPaddingAnd64BitSaltKey_withMultipleVersions_shouldAcceptAllVersions() {
    // Given: Multiple valid keys
    byte[] key1 = new byte[32];
    byte[] key2 = new byte[32];
    byte[] key3 = new byte[32];
    Arrays.fill(key1, (byte) 1);
    Arrays.fill(key2, (byte) 2);
    Arrays.fill(key3, (byte) 3);

    // When: Adding keys with different versions
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, key1);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(1, key2);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(5, key3);

    // Then: Should accept all keys without error
    assertThat(vault).isNotNull();
  }

  // ==================== withKey() Tests ====================

  @Test
  void withKey_withValidVersion_shouldAcceptKey() {
    // Given: A valid key and version
    Key key = new SecretKeySpec(validAesKey256, "AES");
    SecretCryptVault.CryptVersion cryptVersion =
        new SecretCryptVault.CryptVersion(8, "AES/GCM/NoPadding", key, i -> (i | 0xf) + 1);

    // When: Adding the key
    SecretCryptVault result = vault.withKey(10, cryptVersion);

    // Then: Should return the vault instance
    assertThat(result).isSameAs(vault);
  }

  @Test
  void withKey_withDuplicateVersion_shouldThrowException() {
    // Given: A key already added with version 5
    Key key1 = new SecretKeySpec(validAesKey256, "AES");
    SecretCryptVault.CryptVersion cryptVersion1 =
        new SecretCryptVault.CryptVersion(8, "AES/GCM/NoPadding", key1, i -> (i | 0xf) + 1);
    vault.withKey(5, cryptVersion1);

    // When & Then: Adding another key with the same version should throw
    byte[] anotherKey = new byte[32];
    Arrays.fill(anotherKey, (byte) 2);
    Key key2 = new SecretKeySpec(anotherKey, "AES");
    SecretCryptVault.CryptVersion cryptVersion2 =
        new SecretCryptVault.CryptVersion(8, "AES/GCM/NoPadding", key2, i -> (i | 0xf) + 1);

    assertThatThrownBy(() -> vault.withKey(5, cryptVersion2))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version 5 is already defined");
  }

  @Test
  void withKey_withNegativeVersion_shouldThrowException() {
    // Given: A negative version
    Key key = new SecretKeySpec(validAesKey256, "AES");
    SecretCryptVault.CryptVersion cryptVersion =
        new SecretCryptVault.CryptVersion(8, "AES/GCM/NoPadding", key, i -> (i | 0xf) + 1);

    // When & Then: Should throw IllegalArgumentException
    assertThatThrownBy(() -> vault.withKey(-1, cryptVersion))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte");
  }

  @Test
  void withKey_withVersionGreaterThan255_shouldThrowException() {
    // Given: A version greater than 255
    Key key = new SecretKeySpec(validAesKey256, "AES");
    SecretCryptVault.CryptVersion cryptVersion =
        new SecretCryptVault.CryptVersion(8, "AES/GCM/NoPadding", key, i -> (i | 0xf) + 1);

    // When & Then: Should throw IllegalArgumentException
    assertThatThrownBy(() -> vault.withKey(256, cryptVersion))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte");
  }

  @Test
  void withKey_shouldUpdateDefaultVersionToHighest() {
    // Given: Multiple keys added in non-sequential order
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(10, validAesKey256);

    byte[] key2 = new byte[32];
    Arrays.fill(key2, (byte) 2);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(5, key2);

    byte[] key3 = new byte[32];
    Arrays.fill(key3, (byte) 3);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(20, key3);

    // When: Encrypting with default version (should use version 20)
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(plaintext);

    // Then: Should use the highest version (20)
    int version = SecretCryptVault.fromSignedByte(encrypted[0]);
    assertThat(version).isEqualTo(20);
  }

  // ==================== encrypt() with default version Tests ====================

  @Test
  void encrypt_withDefaultVersion_shouldEncryptSuccessfully() {
    // Given: A vault with a key
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "Hello, World!".getBytes(StandardCharsets.UTF_8);

    // When: Encrypting with default version
    byte[] encrypted = vault.encrypt(plaintext);

    // Then: Should return encrypted data with version byte and nonce
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.length).isGreaterThan(plaintext.length);
    // First byte is version, next 12 bytes are nonce, then ciphertext + tag
    assertThat(encrypted.length).isGreaterThanOrEqualTo(1 + 12 + plaintext.length + 16);
  }

  @Test
  void encrypt_withoutKey_shouldThrowException() {
    // Given: A vault with no keys
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);

    // When & Then: Should throw IllegalStateException
    assertThatThrownBy(() -> vault.encrypt(plaintext))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("encryption keys are not initialized");
  }

  @Test
  void encrypt_withEmptyPlaintext_shouldEncryptSuccessfully() {
    // Given: A vault with a key and empty plaintext
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = new byte[0];

    // When: Encrypting empty plaintext
    byte[] encrypted = vault.encrypt(plaintext);

    // Then: Should return encrypted data (version + nonce + tag)
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.length).isGreaterThan(0);
  }

  // ==================== encrypt() with specific version Tests ====================

  @Test
  void encrypt_withSpecificVersion_shouldUseSpecifiedVersion() {
    // Given: A vault with multiple keys
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    byte[] key2 = new byte[32];
    Arrays.fill(key2, (byte) 2);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(5, key2);

    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);

    // When: Encrypting with version 0
    byte[] encrypted = vault.encrypt(0, plaintext);

    // Then: Should use version 0
    int version = SecretCryptVault.fromSignedByte(encrypted[0]);
    assertThat(version).isEqualTo(0);
  }

  @Test
  void encrypt_withUndefinedVersion_shouldThrowException() {
    // Given: A vault with only version 0
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);

    // When & Then: Should throw IllegalArgumentException for undefined version
    assertThatThrownBy(() -> vault.encrypt(10, plaintext))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version 10 undefined");
  }

  @Test
  void encrypt_withVersion255_shouldHandleMaxVersion() {
    // Given: A vault with version 255
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(255, validAesKey256);
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);

    // When: Encrypting with version 255
    byte[] encrypted = vault.encrypt(255, plaintext);

    // Then: Should encrypt successfully with version 255
    int version = SecretCryptVault.fromSignedByte(encrypted[0]);
    assertThat(version).isEqualTo(255);
  }

  @Test
  void encrypt_withLargePlaintext_shouldEncryptSuccessfully() {
    // Given: A vault with a key and large plaintext
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = new byte[10000];
    Arrays.fill(plaintext, (byte) 'A');

    // When: Encrypting large plaintext
    byte[] encrypted = vault.encrypt(plaintext);

    // Then: Should encrypt successfully
    assertThat(encrypted).isNotNull();
    assertThat(encrypted.length).isGreaterThan(plaintext.length);
  }

  // ==================== decrypt() Tests ====================

  @Test
  void decrypt_withValidEncryptedData_shouldDecryptSuccessfully() {
    // Given: A vault with a key and encrypted data
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "Hello, World!".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(plaintext);

    // When: Decrypting the data
    byte[] decrypted = vault.decrypt(encrypted);

    // Then: Should match the original plaintext
    assertThat(decrypted).isEqualTo(plaintext);
  }

  @Test
  void decrypt_withDifferentVersions_shouldDecryptWithCorrectKey() {
    // Given: A vault with multiple keys
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    byte[] key2 = new byte[32];
    Arrays.fill(key2, (byte) 2);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(5, key2);

    // When: Encrypting with version 5 and decrypting
    byte[] plaintext = "test version 5".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(5, plaintext);
    byte[] decrypted = vault.decrypt(encrypted);

    // Then: Should decrypt correctly with version 5 key
    assertThat(decrypted).isEqualTo(plaintext);
  }

  @Test
  void decrypt_withEmptyPlaintext_shouldReturnEmpty() {
    // Given: A vault with a key and encrypted empty data
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = new byte[0];
    byte[] encrypted = vault.encrypt(plaintext);

    // When: Decrypting empty encrypted data
    byte[] decrypted = vault.decrypt(encrypted);

    // Then: Should return empty array
    assertThat(decrypted).isEmpty();
  }

  @Test
  void decrypt_withUndefinedVersion_shouldThrowException() {
    // Given: A vault with version 0 only
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(0, plaintext);

    // When: Modifying the version byte to an undefined version
    byte[] modifiedEncrypted = encrypted.clone();
    modifiedEncrypted[0] = SecretCryptVault.toSignedByte(10);

    // Then: Should throw IllegalArgumentException
    assertThatThrownBy(() -> vault.decrypt(modifiedEncrypted))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version 10 undefined");
  }

  @Test
  void decrypt_withWrongKey_shouldThrowCryptOperationException() {
    // Given: Two vaults with different keys
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(0, plaintext);

    // When: Creating a new vault with a different key
    SecretCryptVault vault2 = new SecretCryptVault();
    byte[] differentKey = new byte[32];
    Arrays.fill(differentKey, (byte) 99);
    vault2.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, differentKey);

    // Then: Should throw CryptOperationException when decrypting with wrong key
    assertThatThrownBy(() -> vault2.decrypt(encrypted))
        .isInstanceOf(CryptOperationException.class)
        .hasMessageContaining("JCE exception caught while encrypting with version 0");
  }

  @Test
  void decrypt_withCorruptedCiphertext_shouldThrowCryptOperationException() {
    // Given: A vault with a key and encrypted data
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(0, plaintext);

    // When: Corrupting the ciphertext (modify a byte in the middle)
    byte[] corrupted = encrypted.clone();
    corrupted[20] = (byte) (corrupted[20] ^ 0xFF);

    // Then: Should throw CryptOperationException
    assertThatThrownBy(() -> vault.decrypt(corrupted))
        .isInstanceOf(CryptOperationException.class)
        .hasMessageContaining("JCE exception caught while encrypting with version 0");
  }

  @Test
  void decrypt_withLargePlaintext_shouldDecryptSuccessfully() {
    // Given: A vault with a key and large encrypted data
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = new byte[10000];
    Arrays.fill(plaintext, (byte) 'Z');
    byte[] encrypted = vault.encrypt(plaintext);

    // When: Decrypting large data
    byte[] decrypted = vault.decrypt(encrypted);

    // Then: Should decrypt successfully
    assertThat(decrypted).isEqualTo(plaintext);
  }

  // ==================== withDefaultKeyVersion() Tests ====================

  @Test
  void withDefaultKeyVersion_withValidVersion_shouldSetDefaultVersion() {
    // Given: A vault with multiple keys
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    byte[] key2 = new byte[32];
    Arrays.fill(key2, (byte) 2);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(10, key2);

    // When: Setting default version to 0
    SecretCryptVault result = vault.withDefaultKeyVersion(0);

    // Then: Should use version 0 for default encryption
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(plaintext);
    int version = SecretCryptVault.fromSignedByte(encrypted[0]);
    assertThat(version).isEqualTo(0);
    assertThat(result).isSameAs(vault);
  }

  @Test
  void withDefaultKeyVersion_withUndefinedVersion_shouldThrowException() {
    // Given: A vault with version 0 only
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    // When & Then: Should throw IllegalArgumentException for undefined version
    assertThatThrownBy(() -> vault.withDefaultKeyVersion(5))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version 5 is undefined");
  }

  @Test
  void withDefaultKeyVersion_withNegativeVersion_shouldThrowException() {
    // Given: A vault with a key
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    // When & Then: Should throw IllegalArgumentException for negative version
    assertThatThrownBy(() -> vault.withDefaultKeyVersion(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte");
  }

  @Test
  void withDefaultKeyVersion_withVersionGreaterThan255_shouldThrowException() {
    // Given: A vault with a key
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    // When & Then: Should throw IllegalArgumentException for version > 255
    assertThatThrownBy(() -> vault.withDefaultKeyVersion(256))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("version must be a byte");
  }

  // ==================== reInitSecureRandomHourly() Tests ====================

  @Test
  void reInitSecureRandomHourly_shouldNotThrowException() {
    // Given: A vault with a key
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    // When: Calling reInitSecureRandomHourly
    vault.reInitSecureRandomHourly();

    // Then: Should not throw any exception and encryption should still work
    byte[] plaintext = "test after reinit".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(plaintext);
    byte[] decrypted = vault.decrypt(encrypted);
    assertThat(decrypted).isEqualTo(plaintext);
  }

  @Test
  void reInitSecureRandomHourly_shouldAllowSubsequentEncryption() {
    // Given: A vault with a key
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);

    // When: Encrypting, reinitializing, and encrypting again
    byte[] encrypted1 = vault.encrypt(plaintext);
    vault.reInitSecureRandomHourly();
    byte[] encrypted2 = vault.encrypt(plaintext);

    // Then: Both encryptions should work and produce different ciphertexts (due to different nonces)
    assertThat(encrypted1).isNotEqualTo(encrypted2);
    byte[] decrypted1 = vault.decrypt(encrypted1);
    byte[] decrypted2 = vault.decrypt(encrypted2);
    assertThat(decrypted1).isEqualTo(plaintext);
    assertThat(decrypted2).isEqualTo(plaintext);
  }

  // ==================== toSignedByte() Tests ====================

  @Test
  void toSignedByte_withZero_shouldReturnMinValue() {
    // When: Converting 0
    byte result = SecretCryptVault.toSignedByte(0);

    // Then: Should return Byte.MIN_VALUE (-128)
    assertThat(result).isEqualTo(Byte.MIN_VALUE);
  }

  @Test
  void toSignedByte_with255_shouldReturnMaxValue() {
    // When: Converting 255
    byte result = SecretCryptVault.toSignedByte(255);

    // Then: Should return Byte.MAX_VALUE (127)
    assertThat(result).isEqualTo(Byte.MAX_VALUE);
  }

  @Test
  void toSignedByte_with128_shouldReturnZero() {
    // When: Converting 128
    byte result = SecretCryptVault.toSignedByte(128);

    // Then: Should return 0
    assertThat(result).isEqualTo((byte) 0);
  }

  @Test
  void toSignedByte_withVariousValues_shouldConvertCorrectly() {
    // When & Then: Testing various conversions
    assertThat(SecretCryptVault.toSignedByte(1)).isEqualTo((byte) -127);
    assertThat(SecretCryptVault.toSignedByte(127)).isEqualTo((byte) -1);
    assertThat(SecretCryptVault.toSignedByte(129)).isEqualTo((byte) 1);
    assertThat(SecretCryptVault.toSignedByte(200)).isEqualTo((byte) 72);
  }

  // ==================== fromSignedByte() Tests ====================

  @Test
  void fromSignedByte_withMinValue_shouldReturnZero() {
    // When: Converting Byte.MIN_VALUE (-128)
    int result = SecretCryptVault.fromSignedByte(Byte.MIN_VALUE);

    // Then: Should return 0
    assertThat(result).isEqualTo(0);
  }

  @Test
  void fromSignedByte_withMaxValue_shouldReturn255() {
    // When: Converting Byte.MAX_VALUE (127)
    int result = SecretCryptVault.fromSignedByte(Byte.MAX_VALUE);

    // Then: Should return 255
    assertThat(result).isEqualTo(255);
  }

  @Test
  void fromSignedByte_withZero_shouldReturn128() {
    // When: Converting 0
    int result = SecretCryptVault.fromSignedByte((byte) 0);

    // Then: Should return 128
    assertThat(result).isEqualTo(128);
  }

  @Test
  void fromSignedByte_withVariousValues_shouldConvertCorrectly() {
    // When & Then: Testing various conversions
    assertThat(SecretCryptVault.fromSignedByte((byte) -127)).isEqualTo(1);
    assertThat(SecretCryptVault.fromSignedByte((byte) -1)).isEqualTo(127);
    assertThat(SecretCryptVault.fromSignedByte((byte) 1)).isEqualTo(129);
    assertThat(SecretCryptVault.fromSignedByte((byte) 72)).isEqualTo(200);
  }

  // ==================== Round-trip Conversion Tests ====================

  @Test
  void toSignedByteAndFromSignedByte_shouldRoundTripCorrectly() {
    // When & Then: Testing round-trip conversion for all valid byte values
    for (int i = 0; i <= 255; i++) {
      byte signed = SecretCryptVault.toSignedByte(i);
      int unsigned = SecretCryptVault.fromSignedByte(signed);
      assertThat(unsigned).isEqualTo(i);
    }
  }

  // ==================== Integration Tests ====================

  @Test
  void encryptAndDecrypt_withMultipleVersions_shouldWorkCorrectly() {
    // Given: A vault with multiple keys
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    byte[] key2 = new byte[32];
    Arrays.fill(key2, (byte) 2);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(1, key2);

    byte[] key3 = new byte[32];
    Arrays.fill(key3, (byte) 3);
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(2, key3);

    // When: Encrypting with different versions
    byte[] plaintext0 = "version 0".getBytes(StandardCharsets.UTF_8);
    byte[] plaintext1 = "version 1".getBytes(StandardCharsets.UTF_8);
    byte[] plaintext2 = "version 2".getBytes(StandardCharsets.UTF_8);

    byte[] encrypted0 = vault.encrypt(0, plaintext0);
    byte[] encrypted1 = vault.encrypt(1, plaintext1);
    byte[] encrypted2 = vault.encrypt(2, plaintext2);

    // Then: Should decrypt correctly with each version
    assertThat(vault.decrypt(encrypted0)).isEqualTo(plaintext0);
    assertThat(vault.decrypt(encrypted1)).isEqualTo(plaintext1);
    assertThat(vault.decrypt(encrypted2)).isEqualTo(plaintext2);
  }

  @Test
  void encryptAndDecrypt_withSpecialCharacters_shouldWorkCorrectly() {
    // Given: A vault with a key
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    // When: Encrypting text with special characters
    String specialText = "Hello! 世界 🌍 \n\t\r Special: @#$%^&*()";
    byte[] plaintext = specialText.getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = vault.encrypt(plaintext);
    byte[] decrypted = vault.decrypt(encrypted);

    // Then: Should decrypt to the original text
    assertThat(new String(decrypted, StandardCharsets.UTF_8)).isEqualTo(specialText);
  }

  @Test
  void encryptAndDecrypt_withBinaryData_shouldWorkCorrectly() {
    // Given: A vault with a key
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);

    // When: Encrypting binary data (all byte values)
    byte[] plaintext = new byte[256];
    for (int i = 0; i < 256; i++) {
      plaintext[i] = (byte) i;
    }
    byte[] encrypted = vault.encrypt(plaintext);
    byte[] decrypted = vault.decrypt(encrypted);

    // Then: Should decrypt to the original binary data
    assertThat(decrypted).isEqualTo(plaintext);
  }

  @Test
  void encryptMultipleTimes_withSamePlaintext_shouldProduceDifferentCiphertexts() {
    // Given: A vault with a key
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "same plaintext".getBytes(StandardCharsets.UTF_8);

    // When: Encrypting the same plaintext multiple times
    byte[] encrypted1 = vault.encrypt(plaintext);
    byte[] encrypted2 = vault.encrypt(plaintext);
    byte[] encrypted3 = vault.encrypt(plaintext);

    // Then: Should produce different ciphertexts (due to random nonces)
    assertThat(encrypted1).isNotEqualTo(encrypted2);
    assertThat(encrypted2).isNotEqualTo(encrypted3);
    assertThat(encrypted1).isNotEqualTo(encrypted3);

    // But all should decrypt to the same plaintext
    assertThat(vault.decrypt(encrypted1)).isEqualTo(plaintext);
    assertThat(vault.decrypt(encrypted2)).isEqualTo(plaintext);
    assertThat(vault.decrypt(encrypted3)).isEqualTo(plaintext);
  }

  @Test
  void encryptAndDecrypt_withVersion0_shouldWorkCorrectly() {
    // Given: A vault with version 0
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(0, validAesKey256);
    byte[] plaintext = "version 0 test".getBytes(StandardCharsets.UTF_8);

    // When: Encrypting and decrypting
    byte[] encrypted = vault.encrypt(0, plaintext);
    byte[] decrypted = vault.decrypt(encrypted);

    // Then: Should work correctly
    assertThat(decrypted).isEqualTo(plaintext);
    assertThat(SecretCryptVault.fromSignedByte(encrypted[0])).isEqualTo(0);
  }

  @Test
  void encryptAndDecrypt_withVersion255_shouldWorkCorrectly() {
    // Given: A vault with version 255
    vault.with256BitAesGcmNoPaddingAnd64BitSaltKey(255, validAesKey256);
    byte[] plaintext = "version 255 test".getBytes(StandardCharsets.UTF_8);

    // When: Encrypting and decrypting
    byte[] encrypted = vault.encrypt(255, plaintext);
    byte[] decrypted = vault.decrypt(encrypted);

    // Then: Should work correctly
    assertThat(decrypted).isEqualTo(plaintext);
    assertThat(SecretCryptVault.fromSignedByte(encrypted[0])).isEqualTo(255);
  }
}
