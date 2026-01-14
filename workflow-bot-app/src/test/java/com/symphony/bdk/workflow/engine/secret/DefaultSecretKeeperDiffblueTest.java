package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper.SecretMetadata;
import com.symphony.bdk.workflow.exception.DuplicateException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DefaultSecretKeeper.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DefaultSecretKeeperDiffblueTest {
  @Autowired private DefaultSecretKeeper defaultSecretKeeper;

  @MockBean private SecretCryptVault secretCryptVault;

  @MockBean private SecretRepository secretRepository;

  /**
   * Test {@link DefaultSecretKeeper#save(String, byte[])}.
   *
   * <p>Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  @DisplayName("Test save(String, byte[])")
  @Tag("MaintainedByDiffblue")
  void testSave() throws UnsupportedEncodingException {
    // Arrange
    when(secretRepository.findByRef(Mockito.<String>any()))
        .thenThrow(new DuplicateException("An error occurred"));

    // Act and Assert
    assertThrows(
        DuplicateException.class,
        () -> defaultSecretKeeper.save("Key", "AXAXAXAX".getBytes("UTF-8")));
    verify(secretRepository).findByRef("Key");
  }

  /**
   * Test {@link DefaultSecretKeeper#save(String, byte[])}.
   *
   * <p>Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  @DisplayName("Test save(String, byte[])")
  @Tag("MaintainedByDiffblue")
  void testSave2() throws UnsupportedEncodingException {
    // Arrange
    Optional<SecretDomain> emptyResult = Optional.empty();
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(emptyResult);
    when(secretCryptVault.encrypt(Mockito.<byte[]>any()))
        .thenThrow(new DuplicateException("An error occurred"));

    // Act and Assert
    assertThrows(
        DuplicateException.class,
        () -> defaultSecretKeeper.save("Key", "AXAXAXAX".getBytes("UTF-8")));
    verify(secretCryptVault).encrypt(isA(byte[].class));
    verify(secretRepository).findByRef("Key");
  }

  /**
   * Test {@link DefaultSecretKeeper#save(String, byte[])}.
   *
   * <p>Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  @DisplayName("Test save(String, byte[])")
  @Tag("MaintainedByDiffblue")
  void testSave3() throws UnsupportedEncodingException {
    // Arrange
    when(secretRepository.save(Mockito.<SecretDomain>any()))
        .thenThrow(new DuplicateException("An error occurred"));
    Optional<SecretDomain> emptyResult = Optional.empty();
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(emptyResult);
    when(secretCryptVault.encrypt(Mockito.<byte[]>any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertThrows(
        DuplicateException.class,
        () -> defaultSecretKeeper.save("Key", "AXAXAXAX".getBytes("UTF-8")));
    verify(secretCryptVault).encrypt(isA(byte[].class));
    verify(secretRepository).findByRef("Key");
    verify(secretRepository).save(isA(SecretDomain.class));
  }

  /**
   * Test {@link DefaultSecretKeeper#save(String, byte[])}.
   *
   * <ul>
   *   <li>Given {@link SecretRepository} {@link SecretRepository#findByRef(String)} return of
   *       {@link SecretDomain#SecretDomain()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  @DisplayName(
      "Test save(String, byte[]); given SecretRepository findByRef(String) return of SecretDomain()")
  @Tag("MaintainedByDiffblue")
  void testSave_givenSecretRepositoryFindByRefReturnOfSecretDomain()
      throws UnsupportedEncodingException {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    Optional<SecretDomain> ofResult = Optional.of(secretDomain);
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(
        DuplicateException.class,
        () -> defaultSecretKeeper.save("Key", "AXAXAXAX".getBytes("UTF-8")));
    verify(secretRepository).findByRef("Key");
  }

  /**
   * Test {@link DefaultSecretKeeper#save(String, byte[])}.
   *
   * <ul>
   *   <li>Given {@link SecretRepository} {@link SecretRepository#save(Object)} return {@link
   *       SecretDomain#SecretDomain()}.
   *   <li>Then calls {@link SecretRepository#save(Object)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  @DisplayName(
      "Test save(String, byte[]); given SecretRepository save(Object) return SecretDomain(); then calls save(Object)")
  @Tag("MaintainedByDiffblue")
  void testSave_givenSecretRepositorySaveReturnSecretDomain_thenCallsSave()
      throws UnsupportedEncodingException {
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
    verify(secretRepository).findByRef("Key");
    verify(secretRepository).save(isA(SecretDomain.class));
  }

  /**
   * Test {@link DefaultSecretKeeper#get(String)}.
   *
   * <p>Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("MaintainedByDiffblue")
  void testGet() {
    // Arrange
    when(secretRepository.findByRef(Mockito.<String>any()))
        .thenThrow(new DuplicateException("An error occurred"));

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.get("Key"));
    verify(secretRepository).findByRef("Key");
  }

  /**
   * Test {@link DefaultSecretKeeper#get(String)}.
   *
   * <p>Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("MaintainedByDiffblue")
  void testGet2() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    Optional<SecretDomain> ofResult = Optional.of(secretDomain);
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(ofResult);
    when(secretCryptVault.decrypt(Mockito.<byte[]>any()))
        .thenThrow(new DuplicateException("An error occurred"));

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.get("Key"));
    verify(secretCryptVault).decrypt(isA(byte[].class));
    verify(secretRepository).findByRef("Key");
  }

  /**
   * Test {@link DefaultSecretKeeper#get(String)}.
   *
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} Secret is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  @DisplayName("Test get(String); given SecretDomain() Secret is 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testGet_givenSecretDomainSecretIsNull_thenReturnNull() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret(null);
    Optional<SecretDomain> ofResult = Optional.of(secretDomain);
    when(secretRepository.findByRef(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    String actualGetResult = defaultSecretKeeper.get("Key");

    // Assert
    verify(secretRepository).findByRef("Key");
    assertNull(actualGetResult);
  }

  /**
   * Test {@link DefaultSecretKeeper#get(String)}.
   *
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} Secret is {@code Secret}.
   *   <li>Then return {@code AXAXAXAX}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  @DisplayName("Test get(String); given SecretDomain() Secret is 'Secret'; then return 'AXAXAXAX'")
  @Tag("MaintainedByDiffblue")
  void testGet_givenSecretDomainSecretIsSecret_thenReturnAxaxaxax()
      throws UnsupportedEncodingException {
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
    verify(secretRepository).findByRef("Key");
    assertEquals("AXAXAXAX", actualGetResult);
  }

  /**
   * Test {@link DefaultSecretKeeper#remove(String)}.
   *
   * <ul>
   *   <li>Given {@link SecretRepository} {@link SecretRepository#deleteByRef(String)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#remove(String)}
   */
  @Test
  @DisplayName("Test remove(String); given SecretRepository deleteByRef(String) does nothing")
  @Tag("MaintainedByDiffblue")
  void testRemove_givenSecretRepositoryDeleteByRefDoesNothing() {
    // Arrange
    doNothing().when(secretRepository).deleteByRef(Mockito.<String>any());

    // Act
    defaultSecretKeeper.remove("Key");

    // Assert
    verify(secretRepository).deleteByRef("Key");
  }

  /**
   * Test {@link DefaultSecretKeeper#remove(String)}.
   *
   * <ul>
   *   <li>Then throw {@link DuplicateException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#remove(String)}
   */
  @Test
  @DisplayName("Test remove(String); then throw DuplicateException")
  @Tag("MaintainedByDiffblue")
  void testRemove_thenThrowDuplicateException() {
    // Arrange
    doThrow(new DuplicateException("An error occurred"))
        .when(secretRepository)
        .deleteByRef(Mockito.<String>any());

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.remove("Key"));
    verify(secretRepository).deleteByRef("Key");
  }

  /**
   * Test {@link DefaultSecretKeeper#getSecretsMetadata()}.
   *
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} CreatedAt is one.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  @DisplayName(
      "Test getSecretsMetadata(); given SecretDomain() CreatedAt is one; then return size is one")
  @Tag("MaintainedByDiffblue")
  void testGetSecretsMetadata_givenSecretDomainCreatedAtIsOne_thenReturnSizeIsOne() {
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
    List<SecretMetadata> actualSecretsMetadata = defaultSecretKeeper.getSecretsMetadata();

    // Assert
    verify(secretRepository).findAll();
    assertEquals(1, actualSecretsMetadata.size());
    SecretMetadata getResult = actualSecretsMetadata.get(0);
    assertEquals("Ref", getResult.getSecretKey());
    Instant createdAt = getResult.getCreatedAt();
    assertEquals(0L, createdAt.getEpochSecond());
    assertEquals(1000000, createdAt.getNano());
  }

  /**
   * Test {@link DefaultSecretKeeper#getSecretsMetadata()}.
   *
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} CreatedAt is zero.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  @DisplayName(
      "Test getSecretsMetadata(); given SecretDomain() CreatedAt is zero; then return size is two")
  @Tag("MaintainedByDiffblue")
  void testGetSecretsMetadata_givenSecretDomainCreatedAtIsZero_thenReturnSizeIsTwo() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setId("42");
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(0L);
    secretDomain2.setId("Id");
    secretDomain2.setRef("42");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");

    ArrayList<SecretDomain> secretDomainList = new ArrayList<>();
    secretDomainList.add(secretDomain2);
    secretDomainList.add(secretDomain);
    when(secretRepository.findAll()).thenReturn(secretDomainList);

    // Act
    List<SecretMetadata> actualSecretsMetadata = defaultSecretKeeper.getSecretsMetadata();

    // Assert
    verify(secretRepository).findAll();
    assertEquals(2, actualSecretsMetadata.size());
    SecretMetadata getResult = actualSecretsMetadata.get(0);
    assertEquals("42", getResult.getSecretKey());
    SecretMetadata getResult2 = actualSecretsMetadata.get(1);
    assertEquals("Ref", getResult2.getSecretKey());
    assertEquals(0, getResult.getCreatedAt().getNano());
    Instant createdAt = getResult2.getCreatedAt();
    assertEquals(0L, createdAt.getEpochSecond());
    assertEquals(1000000, createdAt.getNano());
  }

  /**
   * Test {@link DefaultSecretKeeper#getSecretsMetadata()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  @DisplayName("Test getSecretsMetadata(); then return Empty")
  @Tag("MaintainedByDiffblue")
  void testGetSecretsMetadata_thenReturnEmpty() {
    // Arrange
    when(secretRepository.findAll()).thenReturn(new ArrayList<>());

    // Act
    List<SecretMetadata> actualSecretsMetadata = defaultSecretKeeper.getSecretsMetadata();

    // Assert
    verify(secretRepository).findAll();
    assertTrue(actualSecretsMetadata.isEmpty());
  }

  /**
   * Test {@link DefaultSecretKeeper#getSecretsMetadata()}.
   *
   * <ul>
   *   <li>Then throw {@link DuplicateException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  @DisplayName("Test getSecretsMetadata(); then throw DuplicateException")
  @Tag("MaintainedByDiffblue")
  void testGetSecretsMetadata_thenThrowDuplicateException() {
    // Arrange
    when(secretRepository.findAll()).thenThrow(new DuplicateException("An error occurred"));

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.getSecretsMetadata());
    verify(secretRepository).findAll();
  }
}
