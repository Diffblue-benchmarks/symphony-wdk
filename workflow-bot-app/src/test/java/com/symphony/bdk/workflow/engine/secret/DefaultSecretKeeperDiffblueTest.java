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
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.springframework.data.repository.CrudRepository;
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
   * Test {@link DefaultSecretKeeper#save(String, byte[])}.
   * <p>
   * Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  @DisplayName("Test save(String, byte[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultSecretKeeper.save(String, byte[])"})
  void testSave() throws UnsupportedEncodingException {
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
   * Test {@link DefaultSecretKeeper#save(String, byte[])}.
   * <ul>
   *   <li>Given {@link SecretRepository} {@link SecretRepository#findByRef(String)} return {@link Optional} with {@link SecretDomain#SecretDomain()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  @DisplayName("Test save(String, byte[]); given SecretRepository findByRef(String) return Optional with SecretDomain()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultSecretKeeper.save(String, byte[])"})
  void testSave_givenSecretRepositoryFindByRefReturnOptionalWithSecretDomain() throws UnsupportedEncodingException {
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
   * Test {@link DefaultSecretKeeper#save(String, byte[])}.
   * <ul>
   *   <li>Given {@link SecretRepository} {@link CrudRepository#save(Object)} return {@link SecretDomain#SecretDomain()}.</li>
   *   <li>Then calls {@link SecretCryptVault#encrypt(byte[])}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#save(String, byte[])}
   */
  @Test
  @DisplayName("Test save(String, byte[]); given SecretRepository save(Object) return SecretDomain(); then calls encrypt(byte[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultSecretKeeper.save(String, byte[])"})
  void testSave_givenSecretRepositorySaveReturnSecretDomain_thenCallsEncrypt() throws UnsupportedEncodingException {
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
   * Test {@link DefaultSecretKeeper#get(String)}.
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} CreatedAt is one.</li>
   *   <li>Then return {@code AXAXAXAX}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  @DisplayName("Test get(String); given SecretDomain() CreatedAt is one; then return 'AXAXAXAX'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultSecretKeeper.get(String)"})
  void testGet_givenSecretDomainCreatedAtIsOne_thenReturnAxaxaxax() throws UnsupportedEncodingException {
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
   * Test {@link DefaultSecretKeeper#get(String)}.
   * <ul>
   *   <li>Given {@link SecretRepository} {@link SecretRepository#findByRef(String)} return empty.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  @DisplayName("Test get(String); given SecretRepository findByRef(String) return empty; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultSecretKeeper.get(String)"})
  void testGet_givenSecretRepositoryFindByRefReturnEmpty_thenReturnNull() {
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
   * Test {@link DefaultSecretKeeper#get(String)}.
   * <ul>
   *   <li>Then throw {@link DuplicateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#get(String)}
   */
  @Test
  @DisplayName("Test get(String); then throw DuplicateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultSecretKeeper.get(String)"})
  void testGet_thenThrowDuplicateException() {
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
   * Test {@link DefaultSecretKeeper#remove(String)}.
   * <ul>
   *   <li>Given {@link SecretRepository} {@link SecretRepository#deleteByRef(String)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#remove(String)}
   */
  @Test
  @DisplayName("Test remove(String); given SecretRepository deleteByRef(String) does nothing")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultSecretKeeper.remove(String)"})
  void testRemove_givenSecretRepositoryDeleteByRefDoesNothing() {
    // Arrange
    doNothing().when(secretRepository).deleteByRef(Mockito.<String>any());

    // Act
    defaultSecretKeeper.remove("Key");

    // Assert
    verify(secretRepository).deleteByRef(eq("Key"));
  }

  /**
   * Test {@link DefaultSecretKeeper#remove(String)}.
   * <ul>
   *   <li>Then throw {@link DuplicateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#remove(String)}
   */
  @Test
  @DisplayName("Test remove(String); then throw DuplicateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultSecretKeeper.remove(String)"})
  void testRemove_thenThrowDuplicateException() {
    // Arrange
    doThrow(new DuplicateException("An error occurred")).when(secretRepository).deleteByRef(Mockito.<String>any());

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.remove("Key"));
    verify(secretRepository).deleteByRef(eq("Key"));
  }

  /**
   * Test {@link DefaultSecretKeeper#getSecretsMetadata()}.
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} CreatedAt is one.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  @DisplayName("Test getSecretsMetadata(); given SecretDomain() CreatedAt is one; then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DefaultSecretKeeper.getSecretsMetadata()"})
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
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} CreatedAt is zero.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  @DisplayName("Test getSecretsMetadata(); given SecretDomain() CreatedAt is zero; then return size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DefaultSecretKeeper.getSecretsMetadata()"})
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
    secretDomain2.setRef("com.symphony.bdk.workflow.engine.secret.SecretDomain");
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
    SecretMetadata getResult = actualSecretsMetadata.get(1);
    assertEquals("Ref", getResult.getSecretKey());
    SecretMetadata getResult2 = actualSecretsMetadata.get(0);
    assertEquals("com.symphony.bdk.workflow.engine.secret.SecretDomain", getResult2.getSecretKey());
    assertEquals(0, getResult2.getCreatedAt().getNano());
    Instant createdAt = getResult.getCreatedAt();
    assertEquals(0L, createdAt.getEpochSecond());
    assertEquals(1000000, createdAt.getNano());
  }

  /**
   * Test {@link DefaultSecretKeeper#getSecretsMetadata()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  @DisplayName("Test getSecretsMetadata(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DefaultSecretKeeper.getSecretsMetadata()"})
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
   * <ul>
   *   <li>Then throw {@link DuplicateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultSecretKeeper#getSecretsMetadata()}
   */
  @Test
  @DisplayName("Test getSecretsMetadata(); then throw DuplicateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List DefaultSecretKeeper.getSecretsMetadata()"})
  void testGetSecretsMetadata_thenThrowDuplicateException() {
    // Arrange
    when(secretRepository.findAll()).thenThrow(new DuplicateException("An error occurred"));

    // Act and Assert
    assertThrows(DuplicateException.class, () -> defaultSecretKeeper.getSecretsMetadata());
    verify(secretRepository).findAll();
  }
}
