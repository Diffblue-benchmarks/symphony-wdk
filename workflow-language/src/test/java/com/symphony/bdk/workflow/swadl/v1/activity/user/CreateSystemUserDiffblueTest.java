package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateSystemUserDiffblueTest {
  /**
   * Test new {@link CreateSystemUser} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link CreateSystemUser}
   */
  @Test
  @DisplayName("Test new CreateSystemUser (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateSystemUser.<init>()"})
  void testNewCreateSystemUser() {
    // Arrange and Act
    CreateSystemUser actualCreateSystemUser = new CreateSystemUser();

    // Assert
    assertEquals("SYSTEM", actualCreateSystemUser.getType());
    assertNull(actualCreateSystemUser.getOn());
    assertNull(actualCreateSystemUser.getBusiness());
    assertNull(actualCreateSystemUser.getContact());
    assertNull(actualCreateSystemUser.getKeys());
    assertNull(actualCreateSystemUser.getPassword());
    assertNull(actualCreateSystemUser.getElseCondition());
    assertNull(actualCreateSystemUser.getId());
    assertNull(actualCreateSystemUser.getIfCondition());
    assertNull(actualCreateSystemUser.getDisplayName());
    assertNull(actualCreateSystemUser.getEmail());
    assertNull(actualCreateSystemUser.getFirstname());
    assertNull(actualCreateSystemUser.getLastname());
    assertNull(actualCreateSystemUser.getRecommendedLanguage());
    assertNull(actualCreateSystemUser.getStatus());
    assertNull(actualCreateSystemUser.getUsername());
    assertNull(actualCreateSystemUser.getRoles());
    assertNull(actualCreateSystemUser.getEntitlements());
    assertTrue(actualCreateSystemUser.getVariableProperties().isEmpty());
  }
}
