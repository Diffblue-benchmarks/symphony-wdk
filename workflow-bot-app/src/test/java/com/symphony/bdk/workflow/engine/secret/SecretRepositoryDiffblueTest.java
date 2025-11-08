package com.symphony.bdk.workflow.engine.secret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SecretRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.symphony.bdk.workflow.engine.secret"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class SecretRepositoryDiffblueTest {
  @Autowired
  private SecretRepository secretRepository;

  /**
   * Method under test: {@link SecretRepository#exists(Example)}
   */
  @Test
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
   * Method under test: {@link SecretRepository#findAllById(Iterable)}
   */
  @Test
  void testFindAllById() {
    // Arrange
    SecretDomain secretDomain = new SecretDomain();
    secretDomain.setCreatedAt(1L);
    secretDomain.setRef("Ref");
    secretDomain.setSecret("Secret");

    SecretDomain secretDomain2 = new SecretDomain();
    secretDomain2.setCreatedAt(-1L);
    secretDomain2.setRef("com.symphony.bdk.workflow.engine.secret.SecretDomain");
    secretDomain2.setSecret("com.symphony.bdk.workflow.engine.secret.SecretDomain");

    SecretDomain secretDomain3 = new SecretDomain();
    secretDomain3.setCreatedAt(1L);
    secretDomain3.setRef("Ref");
    secretDomain3.setSecret("Secret");

    SecretDomain secretDomain4 = new SecretDomain();
    secretDomain4.setCreatedAt(1L);
    secretDomain4.setRef("Ref");
    secretDomain4.setSecret("Secret");

    SecretDomain secretDomain5 = new SecretDomain();
    secretDomain5.setCreatedAt(1L);
    secretDomain5.setRef("Ref");
    secretDomain5.setSecret("Secret");
    secretRepository.save(secretDomain);
    secretRepository.save(secretDomain2);
    secretRepository.save(secretDomain3);
    secretRepository.save(secretDomain4);
    secretRepository.save(secretDomain5);
    Iterable<String> ids = mock(Iterable.class);

    ArrayList<String> stringList = new ArrayList<>();
    when(ids.iterator()).thenReturn(stringList.iterator());

    // Act
    List<SecretDomain> actualFindAllByIdResult = secretRepository.findAllById(ids);

    // Assert
    verify(ids).iterator();
    assertTrue(actualFindAllByIdResult.isEmpty());
  }

  /**
   * Method under test: {@link SecretRepository#findBy(Example, Function)}
   */
  @Test
  void testFindBy() {
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

    SecretDomain secretDomain3 = new SecretDomain();
    secretDomain3.setCreatedAt(1L);
    secretDomain3.setRef("Ref");
    secretDomain3.setSecret("Secret");
    Example<SecretDomain> example = Example.of(secretDomain3);
    Function<FluentQuery.FetchableFluentQuery<SecretDomain>, Object> queryFunction = mock(Function.class);
    when(queryFunction.apply(Mockito.<FluentQuery.FetchableFluentQuery<SecretDomain>>any())).thenReturn("Apply");

    // Act
    Object actualFindByResult = secretRepository.findBy(example, queryFunction);

    // Assert
    verify(queryFunction).apply(isA(FluentQuery.FetchableFluentQuery.class));
    assertEquals("Apply", actualFindByResult);
  }

  /**
   * Method under test: {@link SecretRepository#findById(Object)}
   */
  @Test
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
   * Method under test: {@link SecretRepository#getById(Object)}
   */
  @Test
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
   * Method under test: {@link SecretRepository#getOne(Object)}
   */
  @Test
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
   * Method under test: {@link SecretRepository#getReferenceById(Object)}
   */
  @Test
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
   * Method under test: {@link SecretRepository#save(Object)}
   */
  @Test
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
   * Method under test: {@link SecretRepository#saveAll(Iterable)}
   */
  @Test
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
   * Method under test: {@link SecretRepository#saveAndFlush(Object)}
   */
  @Test
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
