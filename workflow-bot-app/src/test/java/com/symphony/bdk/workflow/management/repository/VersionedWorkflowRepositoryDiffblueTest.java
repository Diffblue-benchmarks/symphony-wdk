package com.symphony.bdk.workflow.management.repository;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;

class VersionedWorkflowRepositoryDiffblueTest {
  /**
   * Test {@link CrudRepository#existsById(Object)}.
   * <ul>
   *   <li>Given {@link BaseJpaRepositoryImpl}
   * {@link SimpleJpaRepository#existsById(Object)} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VersionedWorkflowRepository#existsById(Object)}
   */
  @Test
  @DisplayName("Test existsById(Object); given BaseJpaRepositoryImpl existsById(Object) return 'false'")
  void testExistsById_givenBaseJpaRepositoryImplExistsByIdReturnFalse() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BaseJpaRepositoryImpl<VersionedWorkflow, String> baseJpaRepositoryImpl = mock(BaseJpaRepositoryImpl.class);
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
   * Method under test: {@link VersionedWorkflowRepository#existsById(Object)}
   */
  @Test
  @DisplayName("Test existsById(Object); given BaseJpaRepositoryImpl existsById(Object) return 'true'")
  void testExistsById_givenBaseJpaRepositoryImplExistsByIdReturnTrue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BaseJpaRepositoryImpl<VersionedWorkflow, String> baseJpaRepositoryImpl = mock(BaseJpaRepositoryImpl.class);
    when(baseJpaRepositoryImpl.existsById(Mockito.<String>any())).thenReturn(true);

    // Act
    baseJpaRepositoryImpl.existsById("42");

    // Assert
    verify(baseJpaRepositoryImpl).existsById(eq("42"));
  }
}
