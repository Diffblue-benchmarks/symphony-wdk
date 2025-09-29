package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VersionedWorkflowBiConverter.class, String.class})
@ExtendWith(SpringExtension.class)
class VersionedWorkflowBiConverterDiffblueTest {
  @Autowired private VersionedWorkflowBiConverter versionedWorkflowBiConverter;

  /**
   * Test {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)} with {@code Workflow},
   * {@code SwadlView}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link Workflow} (default constructor) Properties is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)}
   */
  @Test
  @DisplayName(
      "Test apply(Workflow, SwadlView) with 'Workflow', 'SwadlView'; given 'null'; when Workflow (default constructor) Properties is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VersionedWorkflow VersionedWorkflowBiConverter.apply(Workflow, SwadlView)"})
  void testApplyWithWorkflowSwadlView_givenNull_whenWorkflowPropertiesIsNull() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    workflow.setProperties(null);

    // Act
    VersionedWorkflow actualApplyResult =
        versionedWorkflowBiConverter.apply(
            workflow,
            SwadlView.builder()
                .createdBy(1L)
                .description("The characteristics of someone or something")
                .swadl("Swadl")
                .build());

    // Assert
    assertEquals("", actualApplyResult.getDeploymentId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Swadl", actualApplyResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualApplyResult.getDescription());
    assertNull(actualApplyResult.getEtag());
    assertNull(actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertTrue(actualApplyResult.getActive());
    assertTrue(actualApplyResult.getPublished());
  }

  /**
   * Test {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)} with {@code Workflow},
   * {@code SwadlView}.
   *
   * <ul>
   *   <li>Given {@link Properties} (default constructor) Publish is {@code true}.
   *   <li>Then return Published.
   * </ul>
   *
   * <p>Method under test: {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)}
   */
  @Test
  @DisplayName(
      "Test apply(Workflow, SwadlView) with 'Workflow', 'SwadlView'; given Properties (default constructor) Publish is 'true'; then return Published")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VersionedWorkflow VersionedWorkflowBiConverter.apply(Workflow, SwadlView)"})
  void testApplyWithWorkflowSwadlView_givenPropertiesPublishIsTrue_thenReturnPublished() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act
    VersionedWorkflow actualApplyResult =
        versionedWorkflowBiConverter.apply(
            workflow,
            SwadlView.builder()
                .createdBy(1L)
                .description("The characteristics of someone or something")
                .swadl("Swadl")
                .build());

    // Assert
    assertEquals("", actualApplyResult.getDeploymentId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Swadl", actualApplyResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualApplyResult.getDescription());
    assertNull(actualApplyResult.getEtag());
    assertNull(actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertTrue(actualApplyResult.getActive());
    assertTrue(actualApplyResult.getPublished());
  }

  /**
   * Test {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)} with {@code Workflow},
   * {@code SwadlView}.
   *
   * <ul>
   *   <li>Then return not Published.
   * </ul>
   *
   * <p>Method under test: {@link VersionedWorkflowBiConverter#apply(Workflow, SwadlView)}
   */
  @Test
  @DisplayName(
      "Test apply(Workflow, SwadlView) with 'Workflow', 'SwadlView'; then return not Published")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"VersionedWorkflow VersionedWorkflowBiConverter.apply(Workflow, SwadlView)"})
  void testApplyWithWorkflowSwadlView_thenReturnNotPublished() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(false);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act
    VersionedWorkflow actualApplyResult =
        versionedWorkflowBiConverter.apply(
            workflow,
            SwadlView.builder()
                .createdBy(1L)
                .description("The characteristics of someone or something")
                .swadl("Swadl")
                .build());

    // Assert
    assertEquals("", actualApplyResult.getDeploymentId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Swadl", actualApplyResult.getSwadl());
    assertEquals("The characteristics of someone or something", actualApplyResult.getDescription());
    assertNull(actualApplyResult.getEtag());
    assertNull(actualApplyResult.getId());
    assertEquals(1L, actualApplyResult.getCreatedBy().longValue());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertFalse(actualApplyResult.getPublished());
    assertTrue(actualApplyResult.getActive());
  }
}
