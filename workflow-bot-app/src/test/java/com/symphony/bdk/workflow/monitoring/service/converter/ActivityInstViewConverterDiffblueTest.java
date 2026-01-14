package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.symphony.bdk.workflow.api.v1.dto.NodeStateView;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
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

@ContextConfiguration(classes = {ActivityInstViewConverter.class})
@ExtendWith(SpringExtension.class)
class ActivityInstViewConverterDiffblueTest {
  @Autowired private ActivityInstViewConverter activityInstViewConverter;

  /**
   * Test {@link ActivityInstViewConverter#apply(ActivityInstanceDomain)} with {@code
   * ActivityInstanceDomain}.
   *
   * <ul>
   *   <li>Then return InstanceId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ActivityInstViewConverter#apply(ActivityInstanceDomain)}
   */
  @Test
  @DisplayName(
      "Test apply(ActivityInstanceDomain) with 'ActivityInstanceDomain'; then return InstanceId is '42'")
  @Tag("MaintainedByDiffblue")
  void testApplyWithActivityInstanceDomain_thenReturnInstanceIdIs42() {
    // Arrange
    Duration duration = Duration.ofSeconds(1L);

    // Act
    NodeStateView actualApplyResult =
        activityInstViewConverter.apply(
            ActivityInstanceDomain.builder()
                .duration(duration)
                .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
                .id("42")
                .name("Name")
                .procInstId("42")
                .startDate(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
                .type("Type")
                .workflowId("42")
                .build());

    // Assert
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("42", actualApplyResult.getWorkflowId());
    assertEquals("Name", actualApplyResult.getNodeId());
    assertNull(actualApplyResult.getGroup());
    assertNull(actualApplyResult.getType());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    Duration duration2 = actualApplyResult.getDuration();
    assertEquals(1000000000L, duration2.toNanos());
    assertTrue(actualApplyResult.getOutputs().isEmpty());
    assertSame(endDate, actualApplyResult.getStartDate());
    assertSame(duration, duration2);
  }
}
