package com.symphony.bdk.workflow.engine.secret;

import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.exception.DuplicateException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultSecretKeeperTest {
  @Mock
  SecretRepository repository;
  @Mock
  SecretCryptVault vault;
  @InjectMocks
  DefaultSecretKeeper secretKeeper;

  @Test
  void saveSuccessfully() {
    when(repository.findByRef(anyString())).thenReturn(Optional.empty());
    when(vault.encrypt(any())).thenReturn("".getBytes(StandardCharsets.UTF_8));
    when(repository.save(any(SecretDomain.class))).thenReturn(new SecretDomain());
    secretKeeper.save("key", "".getBytes(StandardCharsets.UTF_8));
    verify(repository).save(any(SecretDomain.class));
  }

  @Test
  @DisplayName("Save secret with a key already exists, should fail")
  void saveException() {
    when(repository.findByRef(anyString())).thenReturn(Optional.of(new SecretDomain()));
    Assertions.assertThatThrownBy(() -> secretKeeper.save("key", "".getBytes(StandardCharsets.UTF_8)))
        .isInstanceOf(DuplicateException.class);
  }

  @Test
  @DisplayName("Get secret when key not found returns null")
  void getReturnsNullWhenNotFound() {
    when(repository.findByRef(anyString())).thenReturn(Optional.empty());
    String result = secretKeeper.get("key");
    assertNull(result);
    verify(repository).findByRef("key");
  }

  @Test
  @DisplayName("Get secret when key found returns decrypted value")
  void getReturnsDecryptedValueWhenFound() {
    byte[] decrypted = "mySecret".getBytes(StandardCharsets.UTF_8);
    byte[] encrypted = "encryptedBytes".getBytes(StandardCharsets.UTF_8);
    String encodedSecret = Base64.getEncoder().encodeToString(encrypted);
    SecretDomain domain = new SecretDomain("key", encodedSecret);
    when(repository.findByRef("key")).thenReturn(Optional.of(domain));
    when(vault.decrypt(encrypted)).thenReturn(decrypted);

    String result = secretKeeper.get("key");

    assertEquals("mySecret", result);
    verify(repository).findByRef("key");
    verify(vault).decrypt(encrypted);
  }

  @Test
  @DisplayName("Get secret when domain secret is null returns null")
  void getReturnsNullWhenSecretIsNull() {
    SecretDomain domain = new SecretDomain();
    domain.setSecret(null);
    when(repository.findByRef("key")).thenReturn(Optional.of(domain));

    String result = secretKeeper.get("key");

    assertNull(result);
    verify(repository).findByRef("key");
  }

  @Test
  void remove() {
    doNothing().when(repository).deleteByRef(anyString());
    secretKeeper.remove("key");
    verify(repository).deleteByRef(anyString());
  }

  @Test
  @DisplayName("getSecretsMetadata returns empty list when no secrets stored")
  void getSecretsMetadataReturnsEmptyList() {
    when(repository.findAll()).thenReturn(new ArrayList<>());

    List<SecretKeeper.SecretMetadata> result = secretKeeper.getSecretsMetadata();

    assertThat(result).isEmpty();
    verify(repository).findAll();
  }

  @Test
  @DisplayName("getSecretsMetadata returns metadata for each stored secret")
  void getSecretsMetadataReturnsList() {
    SecretDomain domain = new SecretDomain("myKey", "encryptedValue");
    domain.setCreatedAt(1000L);
    List<SecretDomain> domains = new ArrayList<>();
    domains.add(domain);
    when(repository.findAll()).thenReturn(domains);

    List<SecretKeeper.SecretMetadata> result = secretKeeper.getSecretsMetadata();

    assertThat(result).hasSize(1);
    assertEquals("myKey", result.get(0).getSecretKey());
    assertEquals(Instant.ofEpochMilli(1000L), result.get(0).getCreatedAt());
    verify(repository).findAll();
  }
}
