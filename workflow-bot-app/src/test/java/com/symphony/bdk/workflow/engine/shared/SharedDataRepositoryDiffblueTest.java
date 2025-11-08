package com.symphony.bdk.workflow.engine.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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

@ContextConfiguration(classes = {SharedDataRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.symphony.bdk.workflow.engine.shared"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class SharedDataRepositoryDiffblueTest {
  @Autowired
  private SharedDataRepository sharedDataRepository;

  /**
   * Test {@link CrudRepository#existsById(Object)}.
   * <ul>
   *   <li>Given {@link BaseJpaRepositoryImpl} {@link SimpleJpaRepository#existsById(Object)} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SharedDataRepository#existsById(Object)}
   */
  @Test
  @DisplayName("Test existsById(Object); given BaseJpaRepositoryImpl existsById(Object) return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SharedDataRepository.existsById(Object)"})
  void testExistsById_givenBaseJpaRepositoryImplExistsByIdReturnFalse() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BaseJpaRepositoryImpl<SharedData, String> baseJpaRepositoryImpl = mock(BaseJpaRepositoryImpl.class);
    when(baseJpaRepositoryImpl.existsById(Mockito.<String>any())).thenReturn(false);

    // Act
    baseJpaRepositoryImpl.existsById("42");

    // Assert
    verify(baseJpaRepositoryImpl).existsById(eq("42"));
  }

  /**
   * Test {@link CrudRepository#existsById(Object)}.
   * <ul>
   *   <li>Given {@link BaseJpaRepositoryImpl} {@link SimpleJpaRepository#existsById(Object)} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SharedDataRepository#existsById(Object)}
   */
  @Test
  @DisplayName("Test existsById(Object); given BaseJpaRepositoryImpl existsById(Object) return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SharedDataRepository.existsById(Object)"})
  void testExistsById_givenBaseJpaRepositoryImplExistsByIdReturnTrue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BaseJpaRepositoryImpl<SharedData, String> baseJpaRepositoryImpl = mock(BaseJpaRepositoryImpl.class);
    when(baseJpaRepositoryImpl.existsById(Mockito.<String>any())).thenReturn(true);

    // Act
    baseJpaRepositoryImpl.existsById("42");

    // Assert
    verify(baseJpaRepositoryImpl).existsById(eq("42"));
  }

  /**
   * Test {@link QueryByExampleExecutor#exists(Example)}.
   * <ul>
   *   <li>Given {@link SharedData} (default constructor) LastUpdated is one.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SharedDataRepository#exists(Example)}
   */
  @Test
  @DisplayName("Test exists(Example); given SharedData (default constructor) LastUpdated is one; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SharedDataRepository.exists(Example)"})
  void testExists_givenSharedDataLastUpdatedIsOne_thenReturnTrue() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act and Assert
    assertTrue(sharedDataRepository.exists(example));
  }

  /**
   * Test {@link QueryByExampleExecutor#exists(Example)}.
   * <ul>
   *   <li>Given {@link SharedData} (default constructor) LastUpdated is zero.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SharedDataRepository#exists(Example)}
   */
  @Test
  @DisplayName("Test exists(Example); given SharedData (default constructor) LastUpdated is zero; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SharedDataRepository.exists(Example)"})
  void testExists_givenSharedDataLastUpdatedIsZero_thenReturnFalse() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(0L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("42");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    Example<SharedData> example = Example.of(sharedData3);

    // Act and Assert
    assertFalse(sharedDataRepository.exists(example));
  }

  /**
   * Test {@link CrudRepository#findById(Object)}.
   * <p>
   * Method under test: {@link SharedDataRepository#findById(Object)}
   */
  @Test
  @DisplayName("Test findById(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional SharedDataRepository.findById(Object)"})
  void testFindById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act
    Optional<SharedData> actualFindByIdResult = sharedDataRepository.findById(sharedData3.getId());

    // Assert
    assertTrue(actualFindByIdResult.isPresent());
    assertSame(sharedData3, actualFindByIdResult.get());
  }

  /**
   * Test {@link JpaRepository#getById(Object)}.
   * <p>
   * Method under test: {@link SharedDataRepository#getById(Object)}
   */
  @Test
  @DisplayName("Test getById(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object SharedDataRepository.getById(Object)"})
  void testGetById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertSame(sharedData3, sharedDataRepository.getById(sharedData3.getId()));
  }

  /**
   * Test {@link JpaRepository#getOne(Object)}.
   * <p>
   * Method under test: {@link SharedDataRepository#getOne(Object)}
   */
  @Test
  @DisplayName("Test getOne(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object SharedDataRepository.getOne(Object)"})
  void testGetOne() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);
    sharedDataRepository.save(sharedData2);

    // Act and Assert
    assertEquals("42", sharedDataRepository.getOne("42").getId());
  }

  /**
   * Test {@link JpaRepository#getReferenceById(Object)}.
   * <p>
   * Method under test: {@link SharedDataRepository#getReferenceById(Object)}
   */
  @Test
  @DisplayName("Test getReferenceById(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object SharedDataRepository.getReferenceById(Object)"})
  void testGetReferenceById() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData);

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setLastUpdated(-1L);
    sharedData2.setNamespace("com.symphony.bdk.workflow.engine.shared.SharedData");
    sharedData2.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData2);

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    sharedDataRepository.save(sharedData3);

    // Act and Assert
    assertSame(sharedData3, sharedDataRepository.getReferenceById(sharedData3.getId()));
  }

  /**
   * Test {@link CrudRepository#save(Object)}.
   * <p>
   * Method under test: {@link SharedDataRepository#save(Object)}
   */
  @Test
  @DisplayName("Test save(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object SharedDataRepository.save(Object)"})
  void testSave() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act and Assert
    assertSame(sharedData, sharedDataRepository.save(sharedData));
  }

  /**
   * Test {@link ListCrudRepository#saveAll(Iterable)}.
   * <p>
   * Method under test: {@link SharedDataRepository#saveAll(Iterable)}
   */
  @Test
  @DisplayName("Test saveAll(Iterable)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List SharedDataRepository.saveAll(Iterable)"})
  void testSaveAll() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    SharedData sharedData2 = new SharedData();
    sharedData2.namespace("Namespace");
    sharedData2.setLastUpdated(1L);
    sharedData2.setNamespace("Namespace");
    sharedData2.setProperties(new HashMap<>());

    SharedData sharedData3 = new SharedData();
    sharedData3.namespace("Namespace");
    sharedData3.setLastUpdated(1L);
    sharedData3.setNamespace("Namespace");
    sharedData3.setProperties(new HashMap<>());
    List<SharedData> entities = Arrays.asList(sharedData, sharedData2, sharedData3);

    // Act and Assert
    assertEquals(entities, sharedDataRepository.saveAll(entities));
  }

  /**
   * Test {@link JpaRepository#saveAndFlush(Object)}.
   * <p>
   * Method under test: {@link SharedDataRepository#saveAndFlush(Object)}
   */
  @Test
  @DisplayName("Test saveAndFlush(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object SharedDataRepository.saveAndFlush(Object)"})
  void testSaveAndFlush() {
    // Arrange
    SharedData sharedData = new SharedData();
    sharedData.namespace("Namespace");
    sharedData.setLastUpdated(1L);
    sharedData.setNamespace("Namespace");
    sharedData.setProperties(new HashMap<>());

    // Act and Assert
    assertSame(sharedData, sharedDataRepository.saveAndFlush(sharedData));
  }
}
