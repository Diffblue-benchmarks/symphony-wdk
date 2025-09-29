package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableView.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class VariableViewDiffblueTest {
  @Autowired private VariableView variableView;

  /**
   * Test {@link VariableView#VariableView(VariablesDomain)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then return UpdateTime Nano is zero.
   * </ul>
   *
   * <p>Method under test: {@link VariableView#VariableView(VariablesDomain)}
   */
  @Test
  @DisplayName(
      "Test new VariableView(VariablesDomain); given HashMap(); then return UpdateTime Nano is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableView.<init>(VariablesDomain)"})
  void testNewVariableView_givenHashMap_thenReturnUpdateTimeNanoIsZero() {
    // Arrange
    VariablesDomain domain = new VariablesDomain();
    domain.setOutputs(new HashMap<>());
    domain.setRevision(1);
    domain.setUpdateTime(
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    VariableView actualVariableView = new VariableView(domain);

    // Assert
    Instant updateTime = actualVariableView.getUpdateTime();
    assertEquals(0, updateTime.getNano());
    assertEquals(0L, updateTime.getEpochSecond());
    assertEquals(1, actualVariableView.getRevision());
    assertTrue(actualVariableView.getOutputs().isEmpty());
  }

  /**
   * Test {@link VariableView#VariableView(VariablesDomain)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return UpdateTime is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link VariableView#VariableView(VariablesDomain)}
   */
  @Test
  @DisplayName(
      "Test new VariableView(VariablesDomain); when 'null'; then return UpdateTime is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VariableView.<init>(VariablesDomain)"})
  void testNewVariableView_whenNull_thenReturnUpdateTimeIsNull() {
    // Arrange and Act
    VariableView actualVariableView = new VariableView(null);

    // Assert
    assertNull(actualVariableView.getUpdateTime());
    assertNull(actualVariableView.getOutputs());
    assertEquals(0, actualVariableView.getRevision());
  }
}
