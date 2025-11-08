package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain.WorkflowInstanceDomainBuilder;
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
  @Autowired
  private WorkflowInstViewConverter workflowInstViewConverter;

  /**
   * Test {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)} with {@code WorkflowInstanceDomain}.
   * <ul>
   *   <li>Then return InstanceId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)}
   */
  @Test
  @DisplayName("Test apply(WorkflowInstanceDomain) with 'WorkflowInstanceDomain'; then return InstanceId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowInstView WorkflowInstViewConverter.apply(WorkflowInstanceDomain)"})
  void testApplyWithWorkflowInstanceDomain_thenReturnInstanceIdIs42() {
    // Arrange
    WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder().duration(null);
    WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain domain = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("ACTIVE")
        .version(1L)
        .build();

    // Act
    WorkflowInstView actualApplyResult = workflowInstViewConverter.apply(domain);

    // Assert
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("Name", actualApplyResult.getId());
    assertNull(actualApplyResult.getDuration());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertEquals(StatusEnum.PENDING, actualApplyResult.getStatus());
    assertSame(endDate, actualApplyResult.getStartDate());
  }
}
