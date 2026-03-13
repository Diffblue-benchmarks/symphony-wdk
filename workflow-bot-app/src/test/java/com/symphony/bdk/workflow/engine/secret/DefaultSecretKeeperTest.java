package com.symphony.bdk.workflow.engine.secret;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.engine.executor.SecretKeeper.SecretMetadata;
import com.symphony.bdk.workflow.exception.DuplicateException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class DefaultSecretKeeperTest {

  private DefaultSecretKeeper secretKeeper;
  private SecretRepository repository;
  private SecretCryptVault vault;

  @BeforeEach
  void setUp() {
    repository = mock(SecretRepository.class);
    vault = mock(SecretCryptVault.class);
    secretKeeper = new DefaultSecretKeeper(repository, vault);
  }

  @Test
  void shouldConstructWithRepositoryAndVault() {
    assertThat(secretKeeper).isNotNull();
  }

  @Test
  void shouldSaveSecretWhenKeyDoesNotExist() {
    String key = "myKey";
    byte[] secret = "mySecret".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = "encrypted".getBytes(StandardCharsets.UTF_8);
    String encoded = Base64.getEncoder().encodeToString(encrypted);

    when(repository.findByRef(key)).thenReturn(Optional.empty());
    when(vault.encrypt(secret)).thenReturn(encrypted);

    secretKeeper.save(key, secret);

    verify(vault).encrypt(secret);
    verify(repository).save(any(SecretDomain.class));
  }

  @Test
  void shouldThrowDuplicateExceptionWhenSavingExistingKey() {
    String key = "existingKey";
    byte[] secret = "mySecret".getBytes(StandardCharsets.UTF_8);
    SecretDomain existingSecret = new SecretDomain(key, "existingValue");

    when(repository.findByRef(key)).thenReturn(Optional.of(existingSecret));

    assertThatThrownBy(() -> secretKeeper.save(key, secret))
        .isInstanceOf(DuplicateException.class)
        .hasMessage("Secret reference key exists already.");
  }

  @Test
  void shouldGetSecretWhenKeyExists() {
    String key = "myKey";
    String plaintext = "mySecret";
    byte[] encrypted = "encrypted".getBytes(StandardCharsets.UTF_8);
    String encoded = Base64.getEncoder().encodeToString(encrypted);
    SecretDomain secretDomain = new SecretDomain(key, encoded);

    when(repository.findByRef(key)).thenReturn(Optional.of(secretDomain));
    when(vault.decrypt(Base64.getDecoder().decode(encoded))).thenReturn(plaintext.getBytes(StandardCharsets.UTF_8));

    String result = secretKeeper.get(key);

    assertThat(result).isEqualTo(plaintext);
    verify(vault).decrypt(Base64.getDecoder().decode(encoded));
  }

  @Test
  void shouldReturnNullWhenKeyDoesNotExist() {
    String key = "nonExistentKey";

    when(repository.findByRef(key)).thenReturn(Optional.empty());

    String result = secretKeeper.get(key);

    assertThat(result).isNull();
  }

  @Test
  void shouldRemoveSecretByKey() {
    String key = "myKey";

    secretKeeper.remove(key);

    verify(repository).deleteByRef(key);
  }

  @Test
  void shouldGetSecretsMetadataWhenSecretsExist() {
    SecretDomain secret1 = new SecretDomain("key1", "encrypted1");
    secret1.setCreatedAt(1000000L);
    SecretDomain secret2 = new SecretDomain("key2", "encrypted2");
    secret2.setCreatedAt(2000000L);

    when(repository.findAll()).thenReturn(Arrays.asList(secret1, secret2));

    List<SecretMetadata> result = secretKeeper.getSecretsMetadata();

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getSecretKey()).isEqualTo("key1");
    assertThat(result.get(0).getCreatedAt()).isEqualTo(Instant.ofEpochMilli(1000000L));
    assertThat(result.get(1).getSecretKey()).isEqualTo("key2");
    assertThat(result.get(1).getCreatedAt()).isEqualTo(Instant.ofEpochMilli(2000000L));
  }

  @Test
  void shouldReturnEmptyListWhenNoSecretsExist() {
    when(repository.findAll()).thenReturn(Collections.emptyList());

    List<SecretMetadata> result = secretKeeper.getSecretsMetadata();

    assertThat(result).isEmpty();
  }
}
