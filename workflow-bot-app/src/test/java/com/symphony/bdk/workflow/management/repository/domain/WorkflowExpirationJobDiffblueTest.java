package com.symphony.bdk.workflow.management.repository.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class WorkflowExpirationJobDiffblueTest {
  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}, and {@link WorkflowExpirationJob#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExpirationJob#equals(Object)}
   *   <li>{@link WorkflowExpirationJob#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertEquals(workflowExpirationJob, workflowExpirationJob2);
    int expectedHashCodeResult = workflowExpirationJob.hashCode();
    assertEquals(expectedHashCodeResult, workflowExpirationJob2.hashCode());
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}, and {@link WorkflowExpirationJob#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExpirationJob#equals(Object)}
   *   <li>{@link WorkflowExpirationJob#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId(null);
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId(null);
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertEquals(workflowExpirationJob, workflowExpirationJob2);
    int expectedHashCodeResult = workflowExpirationJob.hashCode();
    assertEquals(expectedHashCodeResult, workflowExpirationJob2.hashCode());
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}, and {@link WorkflowExpirationJob#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExpirationJob#equals(Object)}
   *   <li>{@link WorkflowExpirationJob#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId(null);
    workflowExpirationJob.setWorkflowId("42");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId(null);
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertEquals(workflowExpirationJob, workflowExpirationJob2);
    int expectedHashCodeResult = workflowExpirationJob.hashCode();
    assertEquals(expectedHashCodeResult, workflowExpirationJob2.hashCode());
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}, and {@link WorkflowExpirationJob#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExpirationJob#equals(Object)}
   *   <li>{@link WorkflowExpirationJob#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId(null);

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId(null);

    // Act and Assert
    assertEquals(workflowExpirationJob, workflowExpirationJob2);
    int expectedHashCodeResult = workflowExpirationJob.hashCode();
    assertEquals(expectedHashCodeResult, workflowExpirationJob2.hashCode());
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}, and {@link WorkflowExpirationJob#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExpirationJob#equals(Object)}
   *   <li>{@link WorkflowExpirationJob#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    // Act and Assert
    assertEquals(workflowExpirationJob, workflowExpirationJob);
    int expectedHashCodeResult = workflowExpirationJob.hashCode();
    assertEquals(expectedHashCodeResult, workflowExpirationJob.hashCode());
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("Deployment Id");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, workflowExpirationJob2);
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId(null);
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, workflowExpirationJob2);
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, workflowExpirationJob2);
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("Id");
    workflowExpirationJob.setWorkflowId("42");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, workflowExpirationJob2);
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId(null);
    workflowExpirationJob.setWorkflowId("42");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, workflowExpirationJob2);
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("Workflow Id");

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, workflowExpirationJob2);
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId(null);

    WorkflowExpirationJob workflowExpirationJob2 = new WorkflowExpirationJob();
    workflowExpirationJob2.setDeploymentId("42");
    workflowExpirationJob2
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob2.setId("42");
    workflowExpirationJob2.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, workflowExpirationJob2);
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, null);
  }

  /**
   * Test {@link WorkflowExpirationJob#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExpirationJob#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean WorkflowExpirationJob.equals(Object)", "int WorkflowExpirationJob.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    // Act and Assert
    assertNotEquals(workflowExpirationJob, "Different type to WorkflowExpirationJob");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExpirationJob#WorkflowExpirationJob()}
   *   <li>{@link WorkflowExpirationJob#setDeploymentId(String)}
   *   <li>{@link WorkflowExpirationJob#setExpirationDate(Instant)}
   *   <li>{@link WorkflowExpirationJob#setId(String)}
   *   <li>{@link WorkflowExpirationJob#setWorkflowId(String)}
   *   <li>{@link WorkflowExpirationJob#toString()}
   *   <li>{@link WorkflowExpirationJob#getDeploymentId()}
   *   <li>{@link WorkflowExpirationJob#getExpirationDate()}
   *   <li>{@link WorkflowExpirationJob#getId()}
   *   <li>{@link WorkflowExpirationJob#getWorkflowId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowExpirationJob.<init>()",
      "void WorkflowExpirationJob.<init>(String, String, String, Instant)",
      "String WorkflowExpirationJob.getDeploymentId()", "Instant WorkflowExpirationJob.getExpirationDate()",
      "String WorkflowExpirationJob.getId()", "String WorkflowExpirationJob.getWorkflowId()",
      "void WorkflowExpirationJob.setDeploymentId(String)", "void WorkflowExpirationJob.setExpirationDate(Instant)",
      "void WorkflowExpirationJob.setId(String)", "void WorkflowExpirationJob.setWorkflowId(String)",
      "String WorkflowExpirationJob.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    WorkflowExpirationJob actualWorkflowExpirationJob = new WorkflowExpirationJob();
    actualWorkflowExpirationJob.setDeploymentId("42");
    actualWorkflowExpirationJob
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualWorkflowExpirationJob.setId("42");
    actualWorkflowExpirationJob.setWorkflowId("42");
    String actualToStringResult = actualWorkflowExpirationJob.toString();
    String actualDeploymentId = actualWorkflowExpirationJob.getDeploymentId();
    Instant actualExpirationDate = actualWorkflowExpirationJob.getExpirationDate();
    String actualId = actualWorkflowExpirationJob.getId();

    // Assert
    assertEquals("42", actualDeploymentId);
    assertEquals("42", actualId);
    assertEquals("42", actualWorkflowExpirationJob.getWorkflowId());
    assertEquals("WorkflowExpirationJob(id=42, workflowId=42, deploymentId=42, expirationDate=1970-01-01T00:00:00Z)",
        actualToStringResult);
    assertSame(actualExpirationDate.EPOCH, actualExpirationDate);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExpirationJob#WorkflowExpirationJob(String, String, String, Instant)}
   *   <li>{@link WorkflowExpirationJob#setDeploymentId(String)}
   *   <li>{@link WorkflowExpirationJob#setExpirationDate(Instant)}
   *   <li>{@link WorkflowExpirationJob#setId(String)}
   *   <li>{@link WorkflowExpirationJob#setWorkflowId(String)}
   *   <li>{@link WorkflowExpirationJob#toString()}
   *   <li>{@link WorkflowExpirationJob#getDeploymentId()}
   *   <li>{@link WorkflowExpirationJob#getExpirationDate()}
   *   <li>{@link WorkflowExpirationJob#getId()}
   *   <li>{@link WorkflowExpirationJob#getWorkflowId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowExpirationJob.<init>()",
      "void WorkflowExpirationJob.<init>(String, String, String, Instant)",
      "String WorkflowExpirationJob.getDeploymentId()", "Instant WorkflowExpirationJob.getExpirationDate()",
      "String WorkflowExpirationJob.getId()", "String WorkflowExpirationJob.getWorkflowId()",
      "void WorkflowExpirationJob.setDeploymentId(String)", "void WorkflowExpirationJob.setExpirationDate(Instant)",
      "void WorkflowExpirationJob.setId(String)", "void WorkflowExpirationJob.setWorkflowId(String)",
      "String WorkflowExpirationJob.toString()"})
  void testGettersAndSetters_when42() {
    // Arrange and Act
    WorkflowExpirationJob actualWorkflowExpirationJob = new WorkflowExpirationJob("42", "42", "42",
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualWorkflowExpirationJob.setDeploymentId("42");
    actualWorkflowExpirationJob
        .setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualWorkflowExpirationJob.setId("42");
    actualWorkflowExpirationJob.setWorkflowId("42");
    String actualToStringResult = actualWorkflowExpirationJob.toString();
    String actualDeploymentId = actualWorkflowExpirationJob.getDeploymentId();
    Instant actualExpirationDate = actualWorkflowExpirationJob.getExpirationDate();
    String actualId = actualWorkflowExpirationJob.getId();

    // Assert
    assertEquals("42", actualDeploymentId);
    assertEquals("42", actualId);
    assertEquals("42", actualWorkflowExpirationJob.getWorkflowId());
    assertEquals("WorkflowExpirationJob(id=42, workflowId=42, deploymentId=42, expirationDate=1970-01-01T00:00:00Z)",
        actualToStringResult);
    assertSame(actualExpirationDate.EPOCH, actualExpirationDate);
  }
}
