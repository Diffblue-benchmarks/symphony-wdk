package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstDomainConverter.class})
@ExtendWith(SpringExtension.class)
class AbstractInstanceDomainConverterDiffblueTest {
  @Autowired private AbstractInstanceDomainConverter abstractInstanceDomainConverter;

  /**
   * Test {@link
   * AbstractInstanceDomainConverter#instanceCommonBuilder(HistoricProcessInstanceEntity)}.
   *
   * <ul>
   *   <li>Given {@code ACTIVE}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractInstanceDomainConverter#instanceCommonBuilder(HistoricProcessInstanceEntity)}
   */
  @Test
  @DisplayName("Test instanceCommonBuilder(HistoricProcessInstanceEntity); given 'ACTIVE'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain.WorkflowInstanceDomainBuilder AbstractInstanceDomainConverter.instanceCommonBuilder(HistoricProcessInstanceEntity)"
  })
  void testInstanceCommonBuilder_givenActive() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = mock(HistoricProcessInstanceEntity.class);
    when(hisProcInstance.getDurationInMillis()).thenReturn(1L);
    when(hisProcInstance.getEndActivityId()).thenReturn("42");
    when(hisProcInstance.getState()).thenReturn("ACTIVE");
    when(hisProcInstance.getEndTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(hisProcInstance.getId()).thenReturn("42");
    when(hisProcInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(hisProcInstance.getProcessInstanceId()).thenReturn("42");
    when(hisProcInstance.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    abstractInstanceDomainConverter.instanceCommonBuilder(hisProcInstance);

    // Assert
    verify(hisProcInstance).getEndActivityId();
    verify(hisProcInstance).getState();
    verify(hisProcInstance, atLeast(1)).getDurationInMillis();
    verify(hisProcInstance, atLeast(1)).getEndTime();
    verify(hisProcInstance).getStartTime();
    verify(hisProcInstance).getId();
    verify(hisProcInstance).getProcessDefinitionKey();
    verify(hisProcInstance).getProcessInstanceId();
  }

  /**
   * Test {@link
   * AbstractInstanceDomainConverter#instanceCommonBuilder(HistoricProcessInstanceEntity)}.
   *
   * <ul>
   *   <li>Given {@code MD}.
   *   <li>Then calls {@link HistoricProcessInstanceEntity#getEndActivityId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractInstanceDomainConverter#instanceCommonBuilder(HistoricProcessInstanceEntity)}
   */
  @Test
  @DisplayName(
      "Test instanceCommonBuilder(HistoricProcessInstanceEntity); given 'MD'; then calls getEndActivityId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain.WorkflowInstanceDomainBuilder AbstractInstanceDomainConverter.instanceCommonBuilder(HistoricProcessInstanceEntity)"
  })
  void testInstanceCommonBuilder_givenMd_thenCallsGetEndActivityId() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = mock(HistoricProcessInstanceEntity.class);
    when(hisProcInstance.getDurationInMillis()).thenReturn(1L);
    when(hisProcInstance.getEndActivityId()).thenReturn("42");
    when(hisProcInstance.getState()).thenReturn("MD");
    when(hisProcInstance.getEndTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(hisProcInstance.getId()).thenReturn("42");
    when(hisProcInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(hisProcInstance.getProcessInstanceId()).thenReturn("42");
    when(hisProcInstance.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    abstractInstanceDomainConverter.instanceCommonBuilder(hisProcInstance);

    // Assert
    verify(hisProcInstance).getEndActivityId();
    verify(hisProcInstance).getState();
    verify(hisProcInstance, atLeast(1)).getDurationInMillis();
    verify(hisProcInstance, atLeast(1)).getEndTime();
    verify(hisProcInstance).getStartTime();
    verify(hisProcInstance).getId();
    verify(hisProcInstance).getProcessDefinitionKey();
    verify(hisProcInstance).getProcessInstanceId();
  }

  /**
   * Test {@link AbstractInstanceDomainConverter#resolveStatus(String, String)}.
   *
   * <ul>
   *   <li>When {@code ACTIVE}.
   *   <li>Then return {@code PENDING}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractInstanceDomainConverter#resolveStatus(String, String)}
   */
  @Test
  @DisplayName("Test resolveStatus(String, String); when 'ACTIVE'; then return 'PENDING'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String AbstractInstanceDomainConverter.resolveStatus(String, String)"})
  void testResolveStatus_whenActive_thenReturnPending() {
    // Arrange, Act and Assert
    assertEquals("PENDING", abstractInstanceDomainConverter.resolveStatus("ACTIVE", "42"));
  }

  /**
   * Test {@link AbstractInstanceDomainConverter#resolveStatus(String, String)}.
   *
   * <ul>
   *   <li>When {@code His Proc Instance State}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractInstanceDomainConverter#resolveStatus(String, String)}
   */
  @Test
  @DisplayName(
      "Test resolveStatus(String, String); when 'His Proc Instance State'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String AbstractInstanceDomainConverter.resolveStatus(String, String)"})
  void testResolveStatus_whenHisProcInstanceState_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(abstractInstanceDomainConverter.resolveStatus("His Proc Instance State", "42"));
  }

  /**
   * Test {@link
   * AbstractInstanceDomainConverter#instanceCommonBuilder(HistoricProcessInstanceEntity)}.
   *
   * <ul>
   *   <li>Given null endTime.
   *   <li>Then endDate is null.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test instanceCommonBuilder(HistoricProcessInstanceEntity); given null endTime; then endDate is null")
  void testInstanceCommonBuilder_givenNullEndTime() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = mock(HistoricProcessInstanceEntity.class);
    when(hisProcInstance.getDurationInMillis()).thenReturn(1L);
    when(hisProcInstance.getEndActivityId()).thenReturn("42");
    when(hisProcInstance.getState()).thenReturn("ACTIVE");
    when(hisProcInstance.getEndTime()).thenReturn(null);
    when(hisProcInstance.getId()).thenReturn("42");
    when(hisProcInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(hisProcInstance.getProcessInstanceId()).thenReturn("42");
    when(hisProcInstance.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    var result = abstractInstanceDomainConverter.instanceCommonBuilder(hisProcInstance).build();

    // Assert
    assertNull(result.getEndDate());
  }

  /**
   * Test {@link
   * AbstractInstanceDomainConverter#instanceCommonBuilder(HistoricProcessInstanceEntity)}.
   *
   * <ul>
   *   <li>Given null durationInMillis.
   *   <li>Then duration is null.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test instanceCommonBuilder(HistoricProcessInstanceEntity); given null durationInMillis; then duration is null")
  void testInstanceCommonBuilder_givenNullDuration() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = mock(HistoricProcessInstanceEntity.class);
    when(hisProcInstance.getDurationInMillis()).thenReturn(null);
    when(hisProcInstance.getEndActivityId()).thenReturn("42");
    when(hisProcInstance.getState()).thenReturn("ACTIVE");
    when(hisProcInstance.getEndTime()).thenReturn(null);
    when(hisProcInstance.getId()).thenReturn("42");
    when(hisProcInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(hisProcInstance.getProcessInstanceId()).thenReturn("42");
    when(hisProcInstance.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    var result = abstractInstanceDomainConverter.instanceCommonBuilder(hisProcInstance).build();

    // Assert
    assertNull(result.getDuration());
  }

  /**
   * Test {@link AbstractInstanceDomainConverter#resolveStatus(String, String)}.
   *
   * <ul>
   *   <li>When {@code PENDING}.
   *   <li>Then return {@code PENDING}.
   * </ul>
   */
  @Test
  @DisplayName("Test resolveStatus(String, String); when 'PENDING'; then return 'PENDING'")
  void testResolveStatus_whenPending_thenReturnPending() {
    // Arrange, Act and Assert
    assertEquals("PENDING", abstractInstanceDomainConverter.resolveStatus("PENDING", "42"));
  }

  /**
   * Test {@link AbstractInstanceDomainConverter#resolveStatus(String, String)}.
   *
   * <ul>
   *   <li>When {@code COMPLETED} and endActivityId starts with 'endEvent'.
   *   <li>Then return {@code COMPLETED}.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test resolveStatus(String, String); when 'COMPLETED' and endActivityId starts with 'endEvent'; then return 'COMPLETED'")
  void testResolveStatus_whenCompletedWithEndEvent_thenReturnCompleted() {
    // Arrange, Act and Assert
    assertEquals(
        "COMPLETED",
        abstractInstanceDomainConverter.resolveStatus("COMPLETED", "endEvent_123"));
  }

  /**
   * Test {@link AbstractInstanceDomainConverter#resolveStatus(String, String)}.
   *
   * <ul>
   *   <li>When {@code COMPLETED} and endActivityId does not start with 'endEvent'.
   *   <li>Then return {@code FAILED}.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test resolveStatus(String, String); when 'COMPLETED' and endActivityId does not start with 'endEvent'; then return 'FAILED'")
  void testResolveStatus_whenCompletedWithoutEndEvent_thenReturnFailed() {
    // Arrange, Act and Assert
    assertEquals(
        "FAILED", abstractInstanceDomainConverter.resolveStatus("COMPLETED", "someOtherActivity"));
  }

  /**
   * Test {@link AbstractInstanceDomainConverter#resolveStatus(String, String)}.
   *
   * <ul>
   *   <li>When {@code COMPLETED} and endActivityId is blank.
   *   <li>Then return {@code FAILED}.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test resolveStatus(String, String); when 'COMPLETED' and endActivityId is blank; then return 'FAILED'")
  void testResolveStatus_whenCompletedWithBlankEndActivityId_thenReturnFailed() {
    // Arrange, Act and Assert
    assertEquals("FAILED", abstractInstanceDomainConverter.resolveStatus("COMPLETED", ""));
  }
}
