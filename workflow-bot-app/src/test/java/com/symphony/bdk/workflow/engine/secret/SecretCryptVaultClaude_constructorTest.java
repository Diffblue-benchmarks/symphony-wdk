package com.symphony.bdk.workflow.engine.secret;

import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class SecretCryptVaultClaude_constructorTest {

  @Test
  void cryptVersionConstructor_withValidParameters_shouldInitializeAllFields() {
    // Given: Valid parameters for CryptVersion constructor
    int saltLength = 8;
    String cipher = "AES/GCM/NoPadding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: All fields should be initialized correctly
    assertThat(cryptVersion.saltLength).isEqualTo(saltLength);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withZeroSaltLength_shouldInitializeCorrectly() {
    // Given: Zero salt length
    int saltLength = 0;
    String cipher = "AES/GCM/NoPadding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with zero salt length
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Salt length should be zero
    assertThat(cryptVersion.saltLength).isEqualTo(0);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withNegativeSaltLength_shouldInitializeWithNegativeValue() {
    // Given: Negative salt length
    int saltLength = -5;
    String cipher = "AES/GCM/NoPadding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with negative salt length
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Salt length should be the negative value
    assertThat(cryptVersion.saltLength).isEqualTo(-5);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withLargeSaltLength_shouldInitializeCorrectly() {
    // Given: Large salt length
    int saltLength = 1024;
    String cipher = "AES/GCM/NoPadding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with large salt length
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Salt length should be the large value
    assertThat(cryptVersion.saltLength).isEqualTo(1024);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withDifferentCipherAlgorithm_shouldInitializeCorrectly() {
    // Given: Different cipher algorithm
    int saltLength = 16;
    String cipher = "AES/CBC/PKCS5Padding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with different cipher
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Cipher should be set to the provided value
    assertThat(cryptVersion.saltLength).isEqualTo(saltLength);
    assertThat(cryptVersion.cipher).isEqualTo("AES/CBC/PKCS5Padding");
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withEmptyCipherString_shouldInitializeCorrectly() {
    // Given: Empty cipher string
    int saltLength = 8;
    String cipher = "";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with empty cipher
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Cipher should be empty string
    assertThat(cryptVersion.saltLength).isEqualTo(saltLength);
    assertThat(cryptVersion.cipher).isEmpty();
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withNullCipher_shouldInitializeWithNull() {
    // Given: Null cipher
    int saltLength = 8;
    String cipher = null;
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with null cipher
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Cipher should be null
    assertThat(cryptVersion.saltLength).isEqualTo(saltLength);
    assertThat(cryptVersion.cipher).isNull();
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withDifferentKeyTypes_shouldInitializeCorrectly() {
    // Given: Different key types (128-bit vs 256-bit)
    int saltLength = 8;
    String cipher = "AES/GCM/NoPadding";
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // 128-bit key
    byte[] key128Bytes = new byte[16];
    new SecureRandom().nextBytes(key128Bytes);
    Key key128 = new SecretKeySpec(key128Bytes, "AES");

    // 256-bit key
    byte[] key256Bytes = new byte[32];
    new SecureRandom().nextBytes(key256Bytes);
    Key key256 = new SecretKeySpec(key256Bytes, "AES");

    // When: Creating CryptVersion instances with different key sizes
    SecretCryptVault.CryptVersion cryptVersion128 = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key128,
        encryptedLength
    );

    SecretCryptVault.CryptVersion cryptVersion256 = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key256,
        encryptedLength
    );

    // Then: Both should initialize correctly with their respective keys
    assertThat(cryptVersion128.key).isSameAs(key128);
    assertThat(cryptVersion256.key).isSameAs(key256);
  }

  @Test
  void cryptVersionConstructor_withNullKey_shouldInitializeWithNull() {
    // Given: Null key
    int saltLength = 8;
    String cipher = "AES/GCM/NoPadding";
    Key key = null;
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with null key
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Key should be null
    assertThat(cryptVersion.saltLength).isEqualTo(saltLength);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isNull();
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withDifferentEncryptedLengthFunctions_shouldInitializeCorrectly() {
    // Given: Different encrypted length functions
    int saltLength = 8;
    String cipher = "AES/GCM/NoPadding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");

    Function<Integer, Integer> aesLengthCalculator = i -> (i | 0xf) + 1;
    Function<Integer, Integer> identityFunction = i -> i;
    Function<Integer, Integer> doubleFunction = i -> i * 2;

    // When: Creating CryptVersion instances with different functions
    SecretCryptVault.CryptVersion cryptVersion1 = new SecretCryptVault.CryptVersion(
        saltLength, cipher, key, aesLengthCalculator
    );

    SecretCryptVault.CryptVersion cryptVersion2 = new SecretCryptVault.CryptVersion(
        saltLength, cipher, key, identityFunction
    );

    SecretCryptVault.CryptVersion cryptVersion3 = new SecretCryptVault.CryptVersion(
        saltLength, cipher, key, doubleFunction
    );

    // Then: Each should have its respective function
    assertThat(cryptVersion1.encryptedLength).isSameAs(aesLengthCalculator);
    assertThat(cryptVersion2.encryptedLength).isSameAs(identityFunction);
    assertThat(cryptVersion3.encryptedLength).isSameAs(doubleFunction);

    // Verify the functions work as expected
    assertThat(cryptVersion1.encryptedLength.apply(10)).isEqualTo(16); // (10 | 0xf) + 1 = 15 + 1 = 16
    assertThat(cryptVersion2.encryptedLength.apply(10)).isEqualTo(10);
    assertThat(cryptVersion3.encryptedLength.apply(10)).isEqualTo(20);
  }

  @Test
  void cryptVersionConstructor_withNullEncryptedLengthFunction_shouldInitializeWithNull() {
    // Given: Null encrypted length function
    int saltLength = 8;
    String cipher = "AES/GCM/NoPadding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = null;

    // When: Creating a CryptVersion instance with null function
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: EncryptedLength should be null
    assertThat(cryptVersion.saltLength).isEqualTo(saltLength);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isNull();
  }

  @Test
  void cryptVersionConstructor_withAllNullParameters_shouldInitializeWithNulls() {
    // Given: All null parameters (except int which can't be null)
    int saltLength = 0;
    String cipher = null;
    Key key = null;
    Function<Integer, Integer> encryptedLength = null;

    // When: Creating a CryptVersion instance with null parameters
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: All fields should be initialized as provided
    assertThat(cryptVersion.saltLength).isEqualTo(0);
    assertThat(cryptVersion.cipher).isNull();
    assertThat(cryptVersion.key).isNull();
    assertThat(cryptVersion.encryptedLength).isNull();
  }

  @Test
  void cryptVersionConstructor_withMaxIntSaltLength_shouldInitializeCorrectly() {
    // Given: Maximum integer value for salt length
    int saltLength = Integer.MAX_VALUE;
    String cipher = "AES/GCM/NoPadding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with max int salt length
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Salt length should be max int value
    assertThat(cryptVersion.saltLength).isEqualTo(Integer.MAX_VALUE);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }

  @Test
  void cryptVersionConstructor_withMinIntSaltLength_shouldInitializeCorrectly() {
    // Given: Minimum integer value for salt length
    int saltLength = Integer.MIN_VALUE;
    String cipher = "AES/GCM/NoPadding";
    byte[] keyBytes = new byte[32];
    new SecureRandom().nextBytes(keyBytes);
    Key key = new SecretKeySpec(keyBytes, "AES");
    Function<Integer, Integer> encryptedLength = i -> (i | 0xf) + 1;

    // When: Creating a CryptVersion instance with min int salt length
    SecretCryptVault.CryptVersion cryptVersion = new SecretCryptVault.CryptVersion(
        saltLength,
        cipher,
        key,
        encryptedLength
    );

    // Then: Salt length should be min int value
    assertThat(cryptVersion.saltLength).isEqualTo(Integer.MIN_VALUE);
    assertThat(cryptVersion.cipher).isEqualTo(cipher);
    assertThat(cryptVersion.key).isSameAs(key);
    assertThat(cryptVersion.encryptedLength).isSameAs(encryptedLength);
  }
}
