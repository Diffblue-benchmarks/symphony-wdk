package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SecretRepository.class})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.symphony.bdk.workflow.engine.secret"})
class SecretRepositoryDiffblueTest {
  @Autowired private SecretRepository secretRepository;

  /**
   * Test {@link SecretRepository#findByRef(String)}.
   *
   * <p>Method under test: {@link SecretRepository#findByRef(String)}
   */
  @Test
  @DisplayName("Test findByRef(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional SecretRepository.findByRef(String)"})
  void testFindByRef() {
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

    // Act
    Optional<SecretDomain> actualFindByRefResult = secretRepository.findByRef("Ref");

    // Assert
    assertTrue(actualFindByRefResult.isPresent());
    assertSame(secretDomain, actualFindByRefResult.get());
  }

  /**
   * Test {@link SecretRepository#deleteByRef(String)}.
   *
   * <p>Method under test: {@link SecretRepository#deleteByRef(String)}
   */
  @Test
  @DisplayName("Test deleteByRef(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecretRepository.deleteByRef(String)"})
  void testDeleteByRef() {
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

    // Act
    secretRepository.deleteByRef("Ref");

    // Assert
    List<SecretDomain> findAllResult = secretRepository.findAll();
    assertEquals(1, findAllResult.size());
    assertSame(secretDomain2, findAllResult.get(0));
  }

  /**
   * Test {@link SecretRepository#count()}.
   *
   * <p>Method under test: {@link SecretRepository#count()}
   */
  @Test
  @DisplayName("Test count()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long SecretRepository.count()"})
  void testCount() {
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

    // Act and Assert
    assertEquals(2L, secretRepository.count());
  }

  /**
   * Test {@link SecretRepository#count(Example)} with {@code Example}.
   *
   * <p>Method under test: {@link SecretRepository#count(Example)}
   */
  @Test
  @DisplayName("Test count(Example) with 'Example'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long SecretRepository.count(Example)"})
  void testCountWithExample() {
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
    assertEquals(0L, secretRepository.count(example));
  }

  /**
   * Test {@link SecretRepository#deleteAll()}.
   *
   * <p>Method under test: {@link SecretRepository#deleteAll()}
   */
  @Test
  @DisplayName("Test deleteAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecretRepository.deleteAll()"})
  void testDeleteAll() {
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

    // Act
    secretRepository.deleteAll();

    // Assert
    assertTrue(secretRepository.findAll().isEmpty());
  }

  /**
   * Test {@link SecretRepository#deleteAllByIdInBatch(Iterable)}.
   *
   * <p>Method under test: {@link SecretRepository#deleteAllByIdInBatch(Iterable)}
   */
  @Test
  @DisplayName("Test deleteAllByIdInBatch(Iterable)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecretRepository.deleteAllByIdInBatch(Iterable)"})
  void testDeleteAllByIdInBatch() {
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

    // Act
    secretRepository.deleteAllByIdInBatch(new ArrayList<>());

    // Assert
    List<SecretDomain> findAllResult = secretRepository.findAll();
    assertEquals(2, findAllResult.size());
    assertSame(secretDomain, findAllResult.get(0));
    assertSame(secretDomain2, findAllResult.get(1));
  }

  /**
   * Test {@link SecretRepository#deleteAllInBatch()}.
   *
   * <p>Method under test: {@link SecretRepository#deleteAllInBatch()}
   */
  @Test
  @DisplayName("Test deleteAllInBatch()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecretRepository.deleteAllInBatch()"})
  void testDeleteAllInBatch() {
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

    // Act
    secretRepository.deleteAllInBatch();

    // Assert
    assertTrue(secretRepository.findAll().isEmpty());
  }

  /**
   * Test {@link SecretRepository#exists(Example)}.
   *
   * <p>Method under test: {@link SecretRepository#exists(Example)}
   */
  @Test
  @DisplayName("Test exists(Example)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SecretRepository.exists(Example)"})
  void testExists() {
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
   * Test {@link SecretRepository#existsById(Object)}.
   *
   * <ul>
   *   <li>Given {@link SecretDomain#SecretDomain()} Ref is empty string.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SecretRepository#existsById(Object)}
   */
  @Test
  @DisplayName(
      "Test existsById(Object); given SecretDomain() Ref is empty string; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SecretRepository.existsById(Object)"})
  void testExistsById_givenSecretDomainRefIsEmptyString_thenReturnTrue() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("");
    secretDomain.setSecret("Secret");
    secretRepository.save(secretDomain);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("42");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretRepository.save(secretDomain2);

    SecretDomain secretDomain3 = new SecretDomain();
    secretDomain3.setCreatedAt(1L);
    secretDomain3.setRef("Ref");
    secretDomain3.setSecret("Secret");
    secretRepository.save(secretDomain3);

    // Act and Assert
    assertTrue(secretRepository.existsById(secretDomain3.getId()));
  }

  /**
   * Test {@link SecretRepository#findAll()}.
   *
   * <p>Method under test: {@link SecretRepository#findAll()}
   */
  @Test
  @DisplayName("Test findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SecretRepository.findAll()"})
  void testFindAll() {
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

    // Act
    List<SecretDomain> actualFindAllResult = secretRepository.findAll();

    // Assert
    assertEquals(2, actualFindAllResult.size());
    assertSame(secretDomain, actualFindAllResult.get(0));
    assertSame(secretDomain2, actualFindAllResult.get(1));
  }

  /**
   * Test {@link SecretRepository#findAll(Example)} with {@code example}.
   *
   * <p>Method under test: {@link SecretRepository#findAll(Example)}
   */
  @Test
  @DisplayName("Test findAll(Example) with 'example'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SecretRepository.findAll(Example)"})
  void testFindAllWithExample() {
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
    assertTrue(secretRepository.findAll(example).isEmpty());
  }

  /**
   * Test {@link SecretRepository#findAll(Example, Pageable)} with {@code example}, {@code
   * pageable}.
   *
   * <p>Method under test: {@link SecretRepository#findAll(Example, Pageable)}
   */
  @Test
  @DisplayName("Test findAll(Example, Pageable) with 'example', 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page SecretRepository.findAll(Example, Pageable)"})
  void testFindAllWithExamplePageable() {
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

    // Act
    Page<SecretDomain> actualFindAllResult = secretRepository.findAll(example, Pageable.unpaged());

    // Assert
    assertTrue(actualFindAllResult instanceof PageImpl);
    assertTrue(actualFindAllResult.toList().isEmpty());
  }

  /**
   * Test {@link SecretRepository#findAll(Example, Sort)} with {@code example}, {@code sort}.
   *
   * <ul>
   *   <li>When unsorted.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link SecretRepository#findAll(Example, Sort)}
   */
  @Test
  @DisplayName(
      "Test findAll(Example, Sort) with 'example', 'sort'; when unsorted; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SecretRepository.findAll(Example, Sort)"})
  void testFindAllWithExampleSort_whenUnsorted_thenReturnEmpty() {
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
    assertTrue(secretRepository.findAll(example, Sort.unsorted()).isEmpty());
  }

  /**
   * Test {@link SecretRepository#findAll(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link SecretRepository#findAll(Pageable)}
   */
  @Test
  @DisplayName("Test findAll(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Page SecretRepository.findAll(Pageable)"})
  void testFindAllWithPageable() {
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

    // Act
    Page<SecretDomain> actualFindAllResult = secretRepository.findAll(Pageable.unpaged());

    // Assert
    assertTrue(actualFindAllResult instanceof PageImpl);
    List<SecretDomain> toListResult = actualFindAllResult.toList();
    assertEquals(2, toListResult.size());
    assertSame(secretDomain, toListResult.get(0));
    assertSame(secretDomain2, toListResult.get(1));
  }

  /**
   * Test {@link SecretRepository#findAll(Sort)} with {@code sort}.
   *
   * <ul>
   *   <li>When unsorted.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link SecretRepository#findAll(Sort)}
   */
  @Test
  @DisplayName("Test findAll(Sort) with 'sort'; when unsorted; then return size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SecretRepository.findAll(Sort)"})
  void testFindAllWithSort_whenUnsorted_thenReturnSizeIsTwo() {
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

    // Act
    List<SecretDomain> actualFindAllResult = secretRepository.findAll(Sort.unsorted());

    // Assert
    assertEquals(2, actualFindAllResult.size());
    assertSame(secretDomain, actualFindAllResult.get(0));
    assertSame(secretDomain2, actualFindAllResult.get(1));
  }

  /**
   * Test {@link SecretRepository#findById(Object)}.
   *
   * <p>Method under test: {@link SecretRepository#findById(Object)}
   */
  @Test
  @DisplayName("Test findById(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional SecretRepository.findById(Object)"})
  void testFindById() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    secretRepository.save(secretDomain);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("42");
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
   * Test {@link SecretRepository#findOne(Example)}.
   *
   * <p>Method under test: {@link SecretRepository#findOne(Example)}
   */
  @Test
  @DisplayName("Test findOne(Example)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional SecretRepository.findOne(Example)"})
  void testFindOne() {
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
    assertFalse(secretRepository.findOne(example).isPresent());
  }

  /**
   * Test {@link SecretRepository#flush()}.
   *
   * <p>Method under test: {@link SecretRepository#flush()}
   */
  @Test
  @DisplayName("Test flush()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecretRepository.flush()"})
  void testFlush() {
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

    // Act
    secretRepository.flush();

    // Assert
    List<SecretDomain> findAllResult = secretRepository.findAll();
    assertEquals(2, findAllResult.size());
    assertSame(secretDomain, findAllResult.get(0));
    assertSame(secretDomain2, findAllResult.get(1));
  }

  /**
   * Test {@link SecretRepository#getById(Object)}.
   *
   * <p>Method under test: {@link SecretRepository#getById(Object)}
   */
  @Test
  @DisplayName("Test getById(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SecretRepository.getById(Object)"})
  void testGetById() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    secretRepository.save(secretDomain);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("42");
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
   * Test {@link SecretRepository#getOne(Object)}.
   *
   * <p>Method under test: {@link SecretRepository#getOne(Object)}
   */
  @Test
  @DisplayName("Test getOne(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SecretRepository.getOne(Object)"})
  void testGetOne() {
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

    // Act and Assert
    assertEquals("42", secretRepository.getOne("42").getId());
  }

  /**
   * Test {@link SecretRepository#getReferenceById(Object)}.
   *
   * <p>Method under test: {@link SecretRepository#getReferenceById(Object)}
   */
  @Test
  @DisplayName("Test getReferenceById(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SecretRepository.getReferenceById(Object)"})
  void testGetReferenceById() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");
    secretRepository.save(secretDomain);

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("42");
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
   * Test {@link SecretRepository#save(Object)}.
   *
   * <p>Method under test: {@link SecretRepository#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SecretRepository.save(Object)"})
  void testSave() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    // Act
    SecretDomain actualSaveResult = secretRepository.save(secretDomain);

    // Assert
    assertSame(secretDomain, actualSaveResult);
  }

  /**
   * Test {@link SecretRepository#saveAll(Iterable)}.
   *
   * <p>Method under test: {@link SecretRepository#saveAll(Iterable)}
   */
  @Test
  @DisplayName("Test saveAll(Iterable)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List SecretRepository.saveAll(Iterable)"})
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

    // Act
    List<SecretDomain> actualSaveAllResult = secretRepository.saveAll(entities);

    // Assert
    assertEquals(entities, actualSaveAllResult);
  }

  /**
   * Test {@link SecretRepository#saveAndFlush(Object)}.
   *
   * <p>Method under test: {@link SecretRepository#saveAndFlush(Object)}
   */
  @Test
  @DisplayName("Test saveAndFlush(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SecretRepository.saveAndFlush(Object)"})
  void testSaveAndFlush() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    // Act
    SecretDomain actualSaveAndFlushResult = secretRepository.saveAndFlush(secretDomain);

    // Assert
    assertSame(secretDomain, actualSaveAndFlushResult);
  }
}
