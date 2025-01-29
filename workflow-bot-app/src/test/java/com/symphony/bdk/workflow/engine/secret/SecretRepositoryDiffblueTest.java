package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SecretRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.symphony.bdk.workflow.engine.secret"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class SecretRepositoryDiffblueTest {
  @Autowired
  private SecretRepository secretRepository;

  /**
   * Test {@link CrudRepository#existsById(Object)}.
   * <ul>
   *   <li>Given {@link BaseJpaRepositoryImpl}
   * {@link SimpleJpaRepository#existsById(Object)} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretRepository#existsById(Object)}
   */
  @Test
  @DisplayName("Test existsById(Object); given BaseJpaRepositoryImpl existsById(Object) return 'false'")
  void testExistsById_givenBaseJpaRepositoryImplExistsByIdReturnFalse() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BaseJpaRepositoryImpl<SecretDomain, String> baseJpaRepositoryImpl = mock(BaseJpaRepositoryImpl.class);
    when(baseJpaRepositoryImpl.existsById(Mockito.<String>any())).thenReturn(false);

    // Act
    baseJpaRepositoryImpl.existsById("42");

    // Assert
    verify(baseJpaRepositoryImpl).existsById(eq("42"));
  }

  /**
   * Test {@link CrudRepository#existsById(Object)}.
   * <ul>
   *   <li>Given {@link BaseJpaRepositoryImpl}
   * {@link SimpleJpaRepository#existsById(Object)} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretRepository#existsById(Object)}
   */
  @Test
  @DisplayName("Test existsById(Object); given BaseJpaRepositoryImpl existsById(Object) return 'true'")
  void testExistsById_givenBaseJpaRepositoryImplExistsByIdReturnTrue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BaseJpaRepositoryImpl<SecretDomain, String> baseJpaRepositoryImpl = mock(BaseJpaRepositoryImpl.class);
    when(baseJpaRepositoryImpl.existsById(Mockito.<String>any())).thenReturn(true);

    // Act
    baseJpaRepositoryImpl.existsById("42");

    // Assert
    verify(baseJpaRepositoryImpl).existsById(eq("42"));
  }

  /**
   * Test {@link QueryByExampleExecutor#exists(Example)}.
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} Ref is {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecretRepository#exists(Example)}
   */
  @Test
  @DisplayName("Test exists(Example); given SecretDomain() Ref is '42'; then return 'false'")
  void testExists_givenSecretDomainRefIs42_thenReturnFalse() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("42");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretRepository.save(secretDomain);
    secretRepository.save(secretDomain2);

    SecretDomain secretDomain3 = new SecretDomain();
    secretDomain3.setCreatedAt(1L);
    secretDomain3.setRef("Ref");
    secretDomain3.setSecret("Secret");
    Example<SecretDomain> example = Example.of(secretDomain3);

    // Act and Assert
    assertFalse(secretRepository.exists(example));
  }

  /**
   * Test {@link CrudRepository#findById(Object)}.
   * <p>
   * Method under test: {@link SecretRepository#findById(Object)}
   */
  @Test
  @DisplayName("Test findById(Object)")
  void testFindById() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    secretRepository.save(secretDomain);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretRepository.save(secretDomain2);

    SecretDomain secretDomain3 = new SecretDomain();
    secretDomain3.setCreatedAt(1L);
    secretDomain3.setRef("Ref");
    secretDomain3.setSecret("Secret");
    secretRepository.save(secretDomain3);

    // Act
    Optional<SecretDomain> actualFindByIdResult = secretRepository.findById(secretDomain3.getId());

    // Assert
    assertTrue(actualFindByIdResult.isPresent());
    assertSame(secretDomain3, actualFindByIdResult.get());
  }

  /**
   * Test {@link JpaRepository#getById(Object)}.
   * <p>
   * Method under test: {@link SecretRepository#getById(Object)}
   */
  @Test
  @DisplayName("Test getById(Object)")
  void testGetById() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    secretRepository.save(secretDomain);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretRepository.save(secretDomain2);

    SecretDomain secretDomain3 = new SecretDomain();
    secretDomain3.setCreatedAt(1L);
    secretDomain3.setRef("Ref");
    secretDomain3.setSecret("Secret");
    secretRepository.save(secretDomain3);

    // Act and Assert
    assertSame(secretDomain3, secretRepository.getById(secretDomain3.getId()));
  }

  /**
   * Test {@link JpaRepository#getOne(Object)}.
   * <p>
   * Method under test: {@link SecretRepository#getOne(Object)}
   */
  @Test
  @DisplayName("Test getOne(Object)")
  void testGetOne() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretRepository.save(secretDomain);
    secretRepository.save(secretDomain2);

    // Act and Assert
    assertEquals("42", secretRepository.getOne("42").getId());
  }

  /**
   * Test {@link JpaRepository#getReferenceById(Object)}.
   * <p>
   * Method under test: {@link SecretRepository#getReferenceById(Object)}
   */
  @Test
  @DisplayName("Test getReferenceById(Object)")
  void testGetReferenceById() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    secretRepository.save(secretDomain);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretRepository.save(secretDomain2);

    SecretDomain secretDomain3 = new SecretDomain();
    secretDomain3.setCreatedAt(1L);
    secretDomain3.setRef("Ref");
    secretDomain3.setSecret("Secret");
    secretRepository.save(secretDomain3);

    // Act and Assert
    assertSame(secretDomain3, secretRepository.getReferenceById(secretDomain3.getId()));
  }

  /**
   * Test {@link CrudRepository#save(Object)}.
   * <p>
   * Method under test: {@link SecretRepository#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object)")
  void testSave() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    // Act and Assert
    assertSame(secretDomain, secretRepository.save(secretDomain));
  }

  /**
   * Test {@link ListCrudRepository#saveAll(Iterable)}.
   * <p>
   * Method under test: {@link SecretRepository#saveAll(Iterable)}
   */
  @Test
  @DisplayName("Test saveAll(Iterable)")
  void testSaveAll() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(1L);
    secretDomain2.setRef("Ref");
    secretDomain2.setSecret("Secret");

    SecretDomain secretDomain3 = new SecretDomain();
    secretDomain3.setCreatedAt(1L);
    secretDomain3.setRef("Ref");
    secretDomain3.setSecret("Secret");
    List<SecretDomain> entities = Arrays.asList(secretDomain, secretDomain2, secretDomain3);

    // Act and Assert
    assertEquals(entities, secretRepository.saveAll(entities));
  }

  /**
   * Test {@link JpaRepository#saveAndFlush(Object)}.
   * <p>
   * Method under test: {@link SecretRepository#saveAndFlush(Object)}
   */
  @Test
  @DisplayName("Test saveAndFlush(Object)")
  void testSaveAndFlush() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    // Act and Assert
    assertSame(secretDomain, secretRepository.saveAndFlush(secretDomain));
  }
}
