package com.symphony.bdk.workflow.management.repository.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VersionedWorkflow.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class VersionedWorkflowDiffblueTest {
  @Autowired private VersionedWorkflow versionedWorkflow;

  /**
   * Test {@link VersionedWorkflow#getActive()}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VersionedWorkflow#getActive()}
   */
  @Test
  @DisplayName(
      "Test getActive(); given VersionedWorkflow (default constructor) Active is 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Boolean VersionedWorkflow.getActive()"})
  void testGetActive_givenVersionedWorkflowActiveIsTrue_thenReturnTrue() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");

    // Act and Assert
    assertTrue(versionedWorkflow.getActive());
  }

  /**
   * Test {@link VersionedWorkflow#getActive()}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflow}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VersionedWorkflow#getActive()}
   */
  @Test
  @DisplayName("Test getActive(); given VersionedWorkflow; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Boolean VersionedWorkflow.getActive()"})
  void testGetActive_givenVersionedWorkflow_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(versionedWorkflow.getActive());
  }
}
