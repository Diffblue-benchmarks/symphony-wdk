package com.symphony.bdk.workflow.engine.secret;

import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.exception.DuplicateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSecretKeeperClaudeTest {

  @Mock
  private SecretRepository repository;

  @Mock
  private SecretCryptVault vault;

  private DefaultSecretKeeper secretKeeper;

  @BeforeEach
  void setUp() {
    secretKeeper = new DefaultSecretKeeper(repository, vault);
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_withValidDependencies_shouldInitializeCorrectly() {
    // Given: Valid repository and vault
    SecretRepository repo = org.mockito.Mockito.mock(SecretRepository.class);
    SecretCryptVault vaultMock = org.mockito.Mockito.mock(SecretCryptVault.class);

    // When: Creating a new DefaultSecretKeeper
    DefaultSecretKeeper keeper = new DefaultSecretKeeper(repo, vaultMock);

    // Then: Object should be created successfully
    assertThat(keeper).isNotNull();
  }

  // ==================== save() Tests ====================

  @Test
  void save_withNewKey_shouldEncryptAndSaveSecret() {
    // Given: A new key and secret
    String key = "test-key";
    byte[] secret = "test-secret".getBytes(StandardCharsets.UTF_8);
    byte[] encryptedData = "encrypted-data".getBytes(StandardCharsets.UTF_8);

    when(repository.findByRef(key)).thenReturn(Optional.empty());
    when(vault.encrypt(secret)).thenReturn(encryptedData);

    // When: Saving the secret
    secretKeeper.save(key, secret);

    // Then: Should encrypt the secret and save it to repository
    verify(repository, times(1)).findByRef(key);
    verify(vault, times(1)).encrypt(secret);

    ArgumentCaptor<SecretDomain> domainCaptor = ArgumentCaptor.forClass(SecretDomain.class);
    verify(repository, times(1)).save(domainCaptor.capture());

    SecretDomain savedDomain = domainCaptor.getValue();
    assertThat(savedDomain.getRef()).isEqualTo(key);
    assertThat(savedDomain.getSecret()).isEqualTo(Base64.getEncoder().encodeToString(encryptedData));
  }

  @Test
  void save_withExistingKey_shouldThrowDuplicateException() {
    // Given: An existing key
    String key = "existing-key";
    byte[] secret = "test-secret".getBytes(StandardCharsets.UTF_8);
    SecretDomain existingDomain = new SecretDomain(key, "existing-secret");

    when(repository.findByRef(key)).thenReturn(Optional.of(existingDomain));

    // When & Then: Saving with existing key should throw DuplicateException
    assertThatThrownBy(() -> secretKeeper.save(key, secret))
        .isInstanceOf(DuplicateException.class)
        .hasMessage("Secret reference key exists already.");

    // Then: Should not encrypt or save
    verify(repository, times(1)).findByRef(key);
    verify(vault, never()).encrypt(any());
    verify(repository, never()).save(any());
  }

  @Test
  void save_withEmptySecret_shouldEncryptAndSave() {
    // Given: A key with empty secret
    String key = "empty-key";
    byte[] secret = new byte[0];
    byte[] encryptedData = "encrypted-empty".getBytes(StandardCharsets.UTF_8);

    when(repository.findByRef(key)).thenReturn(Optional.empty());
    when(vault.encrypt(secret)).thenReturn(encryptedData);

    // When: Saving the empty secret
    secretKeeper.save(key, secret);

    // Then: Should encrypt and save even if secret is empty
    verify(vault, times(1)).encrypt(secret);
    verify(repository, times(1)).save(any(SecretDomain.class));
  }

  @Test
  void save_withSpecialCharactersInKey_shouldEncryptAndSave() {
    // Given: A key with special characters
    String key = "key-with_$pecial.chars";
    byte[] secret = "secret".getBytes(StandardCharsets.UTF_8);
    byte[] encryptedData = "encrypted".getBytes(StandardCharsets.UTF_8);

    when(repository.findByRef(key)).thenReturn(Optional.empty());
    when(vault.encrypt(secret)).thenReturn(encryptedData);

    // When: Saving the secret
    secretKeeper.save(key, secret);

    // Then: Should save successfully
    ArgumentCaptor<SecretDomain> domainCaptor = ArgumentCaptor.forClass(SecretDomain.class);
    verify(repository, times(1)).save(domainCaptor.capture());
    assertThat(domainCaptor.getValue().getRef()).isEqualTo(key);
  }

  // ==================== get() Tests ====================

  @Test
  void get_withExistingKey_shouldDecryptAndReturnSecret() {
    // Given: An existing encrypted secret
    String key = "test-key";
    String originalSecret = "my-secret";
    byte[] encryptedData = "encrypted-data".getBytes(StandardCharsets.UTF_8);
    String base64Encrypted = Base64.getEncoder().encodeToString(encryptedData);

    SecretDomain domain = new SecretDomain(key, base64Encrypted);
    when(repository.findByRef(key)).thenReturn(Optional.of(domain));
    when(vault.decrypt(encryptedData)).thenReturn(originalSecret.getBytes(StandardCharsets.UTF_8));

    // When: Getting the secret
    String result = secretKeeper.get(key);

    // Then: Should decrypt and return the original secret
    assertThat(result).isEqualTo(originalSecret);
    verify(repository, times(1)).findByRef(key);
    verify(vault, times(1)).decrypt(encryptedData);
  }

  @Test
  void get_withNonExistingKey_shouldReturnNull() {
    // Given: A non-existing key
    String key = "non-existing-key";

    when(repository.findByRef(key)).thenReturn(Optional.empty());

    // When: Getting the secret
    String result = secretKeeper.get(key);

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository, times(1)).findByRef(key);
    verify(vault, never()).decrypt(any());
  }

  @Test
  void get_withEmptySecretDomain_shouldReturnNull() {
    // Given: An empty SecretDomain (no secret set)
    String key = "test-key";
    SecretDomain emptyDomain = new SecretDomain();

    when(repository.findByRef(key)).thenReturn(Optional.of(emptyDomain));

    // When: Getting the secret
    String result = secretKeeper.get(key);

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository, times(1)).findByRef(key);
    verify(vault, never()).decrypt(any());
  }

  @Test
  void get_withNullSecretInDomain_shouldReturnNull() {
    // Given: A SecretDomain with null secret
    String key = "test-key";
    SecretDomain domain = new SecretDomain();
    domain.setRef(key);
    domain.setSecret(null);

    when(repository.findByRef(key)).thenReturn(Optional.of(domain));

    // When: Getting the secret
    String result = secretKeeper.get(key);

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository, times(1)).findByRef(key);
    verify(vault, never()).decrypt(any());
  }

  @Test
  void get_withEncryptedEmptySecret_shouldReturnEmptyString() {
    // Given: An encrypted empty secret
    String key = "test-key";
    byte[] encryptedData = "encrypted-empty".getBytes(StandardCharsets.UTF_8);
    String base64Encrypted = Base64.getEncoder().encodeToString(encryptedData);

    SecretDomain domain = new SecretDomain(key, base64Encrypted);
    when(repository.findByRef(key)).thenReturn(Optional.of(domain));
    when(vault.decrypt(encryptedData)).thenReturn(new byte[0]);

    // When: Getting the secret
    String result = secretKeeper.get(key);

    // Then: Should return empty string
    assertThat(result).isEmpty();
    verify(vault, times(1)).decrypt(encryptedData);
  }

  // ==================== remove() Tests ====================

  @Test
  void remove_withExistingKey_shouldDeleteFromRepository() {
    // Given: An existing key
    String key = "test-key";

    // When: Removing the secret
    secretKeeper.remove(key);

    // Then: Should call deleteByRef on repository
    verify(repository, times(1)).deleteByRef(key);
  }

  @Test
  void remove_withNonExistingKey_shouldStillCallDelete() {
    // Given: A non-existing key
    String key = "non-existing-key";

    // When: Removing the secret
    secretKeeper.remove(key);

    // Then: Should still call deleteByRef (repository handles non-existence)
    verify(repository, times(1)).deleteByRef(key);
  }

  @Test
  void remove_withNullKey_shouldCallDeleteWithNull() {
    // Given: A null key
    String key = null;

    // When: Removing the secret
    secretKeeper.remove(key);

    // Then: Should call deleteByRef with null
    verify(repository, times(1)).deleteByRef(null);
  }

  @Test
  void remove_withEmptyKey_shouldCallDeleteWithEmptyString() {
    // Given: An empty key
    String key = "";

    // When: Removing the secret
    secretKeeper.remove(key);

    // Then: Should call deleteByRef with empty string
    verify(repository, times(1)).deleteByRef("");
  }

  // ==================== getSecretsMetadata() Tests ====================

  @Test
  void getSecretsMetadata_withMultipleSecrets_shouldReturnAllMetadata() {
    // Given: Multiple secrets in repository
    SecretDomain domain1 = createSecretDomain("key1", "secret1", 1000000L);
    SecretDomain domain2 = createSecretDomain("key2", "secret2", 2000000L);
    SecretDomain domain3 = createSecretDomain("key3", "secret3", 3000000L);

    when(repository.findAll()).thenReturn(Arrays.asList(domain1, domain2, domain3));

    // When: Getting secrets metadata
    List<SecretKeeper.SecretMetadata> result = secretKeeper.getSecretsMetadata();

    // Then: Should return metadata for all secrets
    assertThat(result).hasSize(3);

    assertThat(result.get(0).getSecretKey()).isEqualTo("key1");
    assertThat(result.get(0).getCreatedAt()).isEqualTo(Instant.ofEpochMilli(1000000L));

    assertThat(result.get(1).getSecretKey()).isEqualTo("key2");
    assertThat(result.get(1).getCreatedAt()).isEqualTo(Instant.ofEpochMilli(2000000L));

    assertThat(result.get(2).getSecretKey()).isEqualTo("key3");
    assertThat(result.get(2).getCreatedAt()).isEqualTo(Instant.ofEpochMilli(3000000L));

    verify(repository, times(1)).findAll();
  }

  @Test
  void getSecretsMetadata_withNoSecrets_shouldReturnEmptyList() {
    // Given: No secrets in repository
    when(repository.findAll()).thenReturn(Collections.emptyList());

    // When: Getting secrets metadata
    List<SecretKeeper.SecretMetadata> result = secretKeeper.getSecretsMetadata();

    // Then: Should return empty list
    assertThat(result).isEmpty();
    verify(repository, times(1)).findAll();
  }

  @Test
  void getSecretsMetadata_withSingleSecret_shouldReturnSingleMetadata() {
    // Given: A single secret in repository
    SecretDomain domain = createSecretDomain("only-key", "only-secret", 5000000L);

    when(repository.findAll()).thenReturn(Collections.singletonList(domain));

    // When: Getting secrets metadata
    List<SecretKeeper.SecretMetadata> result = secretKeeper.getSecretsMetadata();

    // Then: Should return single metadata
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getSecretKey()).isEqualTo("only-key");
    assertThat(result.get(0).getCreatedAt()).isEqualTo(Instant.ofEpochMilli(5000000L));
  }

  @Test
  void getSecretsMetadata_withDifferentTimestamps_shouldPreserveTimestamps() {
    // Given: Secrets with various timestamps
    SecretDomain oldDomain = createSecretDomain("old-key", "secret", 100L);
    SecretDomain recentDomain = createSecretDomain("recent-key", "secret", System.currentTimeMillis());

    when(repository.findAll()).thenReturn(Arrays.asList(oldDomain, recentDomain));

    // When: Getting secrets metadata
    List<SecretKeeper.SecretMetadata> result = secretKeeper.getSecretsMetadata();

    // Then: Should preserve all timestamps correctly
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getCreatedAt()).isEqualTo(Instant.ofEpochMilli(100L));
    assertThat(result.get(1).getCreatedAt().toEpochMilli()).isGreaterThan(1000000L);
  }

  // ==================== Integration-style Tests ====================

  @Test
  void saveAndGet_withValidSecret_shouldRoundTripCorrectly() {
    // Given: A key and secret
    String key = "integration-key";
    String secretText = "integration-secret";
    byte[] secretBytes = secretText.getBytes(StandardCharsets.UTF_8);
    byte[] encryptedData = "encrypted-integration".getBytes(StandardCharsets.UTF_8);
    String base64Encrypted = Base64.getEncoder().encodeToString(encryptedData);

    // Setup save operation
    when(repository.findByRef(key)).thenReturn(Optional.empty());
    when(vault.encrypt(secretBytes)).thenReturn(encryptedData);

    // Setup get operation
    SecretDomain savedDomain = new SecretDomain(key, base64Encrypted);
    when(vault.decrypt(encryptedData)).thenReturn(secretBytes);

    // When: Saving and then getting the secret
    secretKeeper.save(key, secretBytes);

    // Simulate that after save, findByRef returns the saved domain
    when(repository.findByRef(key)).thenReturn(Optional.of(savedDomain));
    String retrieved = secretKeeper.get(key);

    // Then: Should retrieve the original secret
    assertThat(retrieved).isEqualTo(secretText);
  }

  @Test
  void saveRemoveAndGet_shouldReturnNullAfterRemoval() {
    // Given: A saved secret
    String key = "temp-key";
    byte[] secret = "temp-secret".getBytes(StandardCharsets.UTF_8);
    byte[] encryptedData = "encrypted-temp".getBytes(StandardCharsets.UTF_8);

    when(repository.findByRef(key)).thenReturn(Optional.empty());
    when(vault.encrypt(secret)).thenReturn(encryptedData);

    // When: Saving, removing, then trying to get
    secretKeeper.save(key, secret);
    secretKeeper.remove(key);

    // After removal, repository returns empty
    when(repository.findByRef(key)).thenReturn(Optional.empty());
    String retrieved = secretKeeper.get(key);

    // Then: Should return null after removal
    assertThat(retrieved).isNull();
    verify(repository, times(1)).deleteByRef(key);
  }

  // ==================== Helper Methods ====================

  private SecretDomain createSecretDomain(String ref, String secret, Long createdAt) {
    SecretDomain domain = new SecretDomain(ref, secret);
    domain.setCreatedAt(createdAt);
    return domain;
  }
}
