package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstViewConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowInstViewConverterDiffblueTest {
  @Autowired private WorkflowInstViewConverter workflowInstViewConverter;

  /**
   * Test {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)} with {@code
   * WorkflowInstanceDomain}.
   *
   * <ul>
   *   <li>Then return Status is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)}
   */
  @Test
  @DisplayName(
      "Test apply(WorkflowInstanceDomain) with 'WorkflowInstanceDomain'; then return Status is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowInstView WorkflowInstViewConverter.apply(WorkflowInstanceDomain)"})
  void testApplyWithWorkflowInstanceDomain_thenReturnStatusIsNull() {
    // Arrange
    Duration duration = Duration.ofSeconds(1L);

    // Act
    WorkflowInstView actualApplyResult =
        workflowInstViewConverter.apply(
            WorkflowInstanceDomain.builder()
                .duration(duration)
                .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
                .id("42")
                .instanceId("42")
                .name("Name")
                .startDate(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
                .status(null)
                .version(1L)
                .build());

    // Assert
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("Name", actualApplyResult.getId());
    assertNull(actualApplyResult.getStatus());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    Duration duration2 = actualApplyResult.getDuration();
    assertEquals(1000000000L, duration2.toNanos());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertSame(endDate, actualApplyResult.getStartDate());
    assertSame(duration, duration2);
  }

  /**
   * Test {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)} with {@code
   * WorkflowInstanceDomain}.
   *
   * <ul>
   *   <li>Then return Status is {@code FAILED}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)}
   */
  @Test
  @DisplayName(
      "Test apply(WorkflowInstanceDomain) with 'WorkflowInstanceDomain'; then return Status is 'FAILED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowInstView WorkflowInstViewConverter.apply(WorkflowInstanceDomain)"})
  void testApplyWithWorkflowInstanceDomain_thenReturnStatusIsFailed() {
    // Arrange
    Duration duration = Duration.ofSeconds(1L);

    // Act
    WorkflowInstView actualApplyResult =
        workflowInstViewConverter.apply(
            WorkflowInstanceDomain.builder()
                .duration(duration)
                .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
                .id("42")
                .instanceId("42")
                .name("Name")
                .startDate(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
                .status("FAILED")
                .version(1L)
                .build());

    // Assert
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("Name", actualApplyResult.getId());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    Duration duration2 = actualApplyResult.getDuration();
    assertEquals(1000000000L, duration2.toNanos());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertEquals(StatusEnum.FAILED, actualApplyResult.getStatus());
    assertSame(endDate, actualApplyResult.getStartDate());
    assertSame(duration, duration2);
  }

  /**
   * Test {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)} with {@code
   * WorkflowInstanceDomain}.
   *
   * <ul>
   *   <li>Then return Status is {@code PENDING}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)}
   */
  @Test
  @DisplayName(
      "Test apply(WorkflowInstanceDomain) with 'WorkflowInstanceDomain'; then return Status is 'PENDING'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowInstView WorkflowInstViewConverter.apply(WorkflowInstanceDomain)"})
  void testApplyWithWorkflowInstanceDomain_thenReturnStatusIsPending() {
    // Arrange
    Duration duration = Duration.ofSeconds(1L);

    // Act
    WorkflowInstView actualApplyResult =
        workflowInstViewConverter.apply(
            WorkflowInstanceDomain.builder()
                .duration(duration)
                .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
                .id("42")
                .instanceId("42")
                .name("Name")
                .startDate(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
                .status("ACTIVE")
                .version(1L)
                .build());

    // Assert
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("Name", actualApplyResult.getId());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    Duration duration2 = actualApplyResult.getDuration();
    assertEquals(1000000000L, duration2.toNanos());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertEquals(StatusEnum.PENDING, actualApplyResult.getStatus());
    assertSame(endDate, actualApplyResult.getStartDate());
    assertSame(duration, duration2);
  }
}
