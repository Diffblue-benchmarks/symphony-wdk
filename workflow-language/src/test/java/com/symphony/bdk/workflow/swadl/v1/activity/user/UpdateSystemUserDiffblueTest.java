package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UpdateSystemUserDiffblueTest {
  /**
   * Test new {@link UpdateSystemUser} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link UpdateSystemUser}
   */
  @Test
  @DisplayName("Test new UpdateSystemUser (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateSystemUser.<init>()"})
  void testNewUpdateSystemUser() {
    // Arrange and Act
    UpdateSystemUser actualUpdateSystemUser = new UpdateSystemUser();

    // Assert
    assertEquals("SYSTEM", actualUpdateSystemUser.getType());
    assertNull(actualUpdateSystemUser.getOn());
    assertNull(actualUpdateSystemUser.getBusiness());
    assertNull(actualUpdateSystemUser.getContact());
    assertNull(actualUpdateSystemUser.getKeys());
    assertNull(actualUpdateSystemUser.getPassword());
    assertNull(actualUpdateSystemUser.getElseCondition());
    assertNull(actualUpdateSystemUser.getId());
    assertNull(actualUpdateSystemUser.getIfCondition());
    assertNull(actualUpdateSystemUser.getDisplayName());
    assertNull(actualUpdateSystemUser.getEmail());
    assertNull(actualUpdateSystemUser.getFirstname());
    assertNull(actualUpdateSystemUser.getLastname());
    assertNull(actualUpdateSystemUser.getRecommendedLanguage());
    assertNull(actualUpdateSystemUser.getStatus());
    assertNull(actualUpdateSystemUser.getUsername());
    assertNull(actualUpdateSystemUser.getUserId());
    assertNull(actualUpdateSystemUser.getRoles());
    assertNull(actualUpdateSystemUser.getEntitlements());
    assertTrue(actualUpdateSystemUser.getVariableProperties().isEmpty());
  }
}
