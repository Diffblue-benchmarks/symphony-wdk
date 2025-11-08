package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.exception.DuplicateException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultSecretKeeper.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DefaultSecretKeeperDiffblueTest {
  @Autowired
  private DefaultSecretKeeper defaultSecretKeeper;

  @MockBean
  private SecretCryptVault secretCryptVault;

  @MockBean
  private SecretRepository secretRepository;

  /**
   * Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  void testSave() throws UnsupportedEncodingException {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    Optional<SecretDomain> ofResult = Optional.of(secretDomain);
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.save("Key", "AXAXAXAX".getBytes("UTF-8")));
    verify(secretRepository).findByRef(eq("Key"));
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  void testSave2() throws UnsupportedEncodingException {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    when(secretRepository.save(Mockito.<SecretDomain>any())).thenReturn(secretDomain);
    Optional<SecretDomain> emptyResult = Optional.empty();
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(emptyResult);
    when(secretCryptVault.encrypt(Mockito.<byte[]>any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));

    // Act
    defaultSecretKeeper.save("Key", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(secretCryptVault).encrypt(isA(byte[].class));
    verify(secretRepository).findByRef(eq("Key"));
    verify(secretRepository).save(isA(SecretDomain.class));
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  void testSave3() throws UnsupportedEncodingException {
    // Arrange
    when(secretRepository.save(Mockito.<SecretDomain>any())).thenThrow(new DuplicateException("An error occurred"));
    Optional<SecretDomain> emptyResult = Optional.empty();
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(emptyResult);
    when(secretCryptVault.encrypt(Mockito.<byte[]>any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.save("Key", "AXAXAXAX".getBytes("UTF-8")));
    verify(secretCryptVault).encrypt(isA(byte[].class));
    verify(secretRepository).findByRef(eq("Key"));
    verify(secretRepository).save(isA(SecretDomain.class));
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  void testGet() throws UnsupportedEncodingException {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    Optional<SecretDomain> ofResult = Optional.of(secretDomain);
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(ofResult);
    when(secretCryptVault.decrypt(Mockito.<byte[]>any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));

    // Act
    String actualGetResult = defaultSecretKeeper.get("Key");

    // Assert
    verify(secretCryptVault).decrypt(isA(byte[].class));
    verify(secretRepository).findByRef(eq("Key"));
    assertEquals("AXAXAXAX", actualGetResult);
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  void testGet2() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    Optional<SecretDomain> ofResult = Optional.of(secretDomain);
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(ofResult);
    when(secretCryptVault.decrypt(Mockito.<byte[]>any())).thenThrow(new DuplicateException("An error occurred"));

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.get("Key"));
    verify(secretCryptVault).decrypt(isA(byte[].class));
    verify(secretRepository).findByRef(eq("Key"));
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  void testGet3() {
    // Arrange
    Optional<SecretDomain> emptyResult = Optional.empty();
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(emptyResult);

    // Act
    String actualGetResult = defaultSecretKeeper.get("Key");

    // Assert
    verify(secretRepository).findByRef(eq("Key"));
    assertNull(actualGetResult);
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#remove(String)}
   */
  @Test
  void testRemove() {
    // Arrange
    doNothing().when(secretRepository).deleteByRef(Mockito.<String>any());

    // Act
    defaultSecretKeeper.remove("Key");

    // Assert that nothing has changed
    verify(secretRepository).deleteByRef(eq("Key"));
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#remove(String)}
   */
  @Test
  void testRemove2() {
    // Arrange
    doThrow(new DuplicateException("An error occurred")).when(secretRepository).deleteByRef(Mockito.<String>any());

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.remove("Key"));
    verify(secretRepository).deleteByRef(eq("Key"));
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  void testGetSecretsMetadata() {
    // Arrange
    when(secretRepository.findAll()).thenReturn(new ArrayList<>());

    // Act
    List<SecretKeeper.SecretMetadata> actualSecretsMetadata = defaultSecretKeeper.getSecretsMetadata();

    // Assert
    verify(secretRepository).findAll();
    assertTrue(actualSecretsMetadata.isEmpty());
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  void testGetSecretsMetadata2() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    ArrayList<SecretDomain> secretDomainList = new ArrayList<>();
    secretDomainList.add(secretDomain);
    when(secretRepository.findAll()).thenReturn(secretDomainList);

    // Act
    List<SecretKeeper.SecretMetadata> actualSecretsMetadata = defaultSecretKeeper.getSecretsMetadata();

    // Assert
    verify(secretRepository).findAll();
    assertEquals(1, actualSecretsMetadata.size());
    SecretKeeper.SecretMetadata getResult = actualSecretsMetadata.get(0);
    assertEquals("Ref", getResult.getSecretKey());
    Instant createdAt = getResult.getCreatedAt();
    assertEquals(0L, createdAt.getEpochSecond());
    assertEquals(1000000, createdAt.getNano());
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  void testGetSecretsMetadata3() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(0L);
    secretDomain2.setId("Id");
    secretDomain2.setRef("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");

    ArrayList<SecretDomain> secretDomainList = new ArrayList<>();
    secretDomainList.add(secretDomain2);
    secretDomainList.add(secretDomain);
    when(secretRepository.findAll()).thenReturn(secretDomainList);

    // Act
    List<SecretKeeper.SecretMetadata> actualSecretsMetadata = defaultSecretKeeper.getSecretsMetadata();

    // Assert
    verify(secretRepository).findAll();
    assertEquals(2, actualSecretsMetadata.size());
    SecretKeeper.SecretMetadata getResult = actualSecretsMetadata.get(1);
    assertEquals("Ref", getResult.getSecretKey());
    SecretKeeper.SecretMetadata getResult2 = actualSecretsMetadata.get(0);
    assertEquals("com.symphony.bdk.workflow.engine.secret.SecretDomain", getResult2.getSecretKey());
    Instant createdAt = getResult2.getCreatedAt();
    assertEquals(0, createdAt.getNano());
    assertEquals(0L, createdAt.getEpochSecond());
    Instant createdAt2 = getResult.getCreatedAt();
    assertEquals(0L, createdAt2.getEpochSecond());
    assertEquals(1000000, createdAt2.getNano());
  }

  /**
   * Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  void testGetSecretsMetadata4() {
    // Arrange
    when(secretRepository.findAll()).thenThrow(new DuplicateException("An error occurred"));

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.getSecretsMetadata());
    verify(secretRepository).findAll();
  }
}
