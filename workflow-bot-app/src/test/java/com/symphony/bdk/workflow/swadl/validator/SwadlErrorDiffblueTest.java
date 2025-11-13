package com.symphony.bdk.workflow.swadl.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SwadlErrorDiffblueTest {
  /**
   * Test {@link SwadlError#toString()}.
   *
   * <ul>
   *   <li>Then return {@code Line 2: Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link SwadlError#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return 'Line 2: Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String SwadlError.toString()"})
  void testToString_thenReturnLine2NotAllWhoWanderAreLost() {
    // Arrange, Act and Assert
    assertEquals(
        "Line 2: Not all who wander are lost",
        new SwadlError(2, "Not all who wander are lost").toString());
  }

  /**
   * Test {@link SwadlError#toString()}.
   *
   * <ul>
   *   <li>Then return {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link SwadlError#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String SwadlError.toString()"})
  void testToString_thenReturnNotAllWhoWanderAreLost() {
    // Arrange, Act and Assert
    assertEquals(
        "Not all who wander are lost",
        new SwadlError(-1, "Not all who wander are lost").toString());
  }
}
