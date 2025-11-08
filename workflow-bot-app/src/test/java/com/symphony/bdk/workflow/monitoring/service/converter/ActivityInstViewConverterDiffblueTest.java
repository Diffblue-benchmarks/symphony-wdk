package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.api.v1.dto.NodeStateView;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityInstViewConverter.class})
@ExtendWith(SpringExtension.class)
class ActivityInstViewConverterDiffblueTest {
  @Autowired
  private ActivityInstViewConverter activityInstViewConverter;

  /**
   * Method under test:
   * {@link ActivityInstViewConverter#apply(ActivityInstanceDomain)}
   */
  @Test
  void testApply() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain domain = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act
    NodeStateView actualApplyResult = activityInstViewConverter.apply(domain);

    // Assert
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Name", actualApplyResult.getNodeId());
    assertNull(actualApplyResult.getGroup());
    assertNull(actualApplyResult.getType());
    assertNull(actualApplyResult.getDuration());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertTrue(actualApplyResult.getOutputs().isEmpty());
    assertSame(endDate, actualApplyResult.getStartDate());
  }

  /**
   * Method under test:
   * {@link ActivityInstViewConverter#apply(ActivityInstanceDomain)}
   */
  @Test
  void testApply2() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.duration(Mockito.<Duration>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain domain = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act
    NodeStateView actualApplyResult = activityInstViewConverter.apply(domain);

    // Assert
    verify(activityInstanceDomainBuilder).duration(isNull());
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Name", actualApplyResult.getNodeId());
    assertNull(actualApplyResult.getGroup());
    assertNull(actualApplyResult.getType());
    assertNull(actualApplyResult.getDuration());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertTrue(actualApplyResult.getOutputs().isEmpty());
    assertSame(endDate, actualApplyResult.getStartDate());
  }
}
