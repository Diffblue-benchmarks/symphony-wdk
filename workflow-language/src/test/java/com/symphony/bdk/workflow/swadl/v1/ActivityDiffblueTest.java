package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivityDiffblueTest {
  /**
   * Test {@link Activity#getActivity()}.
   *
   * <p>Method under test: {@link Activity#getActivity()}
   */
  @Test
  @DisplayName("Test getActivity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity Activity.getActivity()"
  })
  void testGetActivity() {
    // Arrange, Act and Assert
    assertNull(new Activity().getActivity());
  }

  /**
   * Test {@link Activity#getEvent()}.
   *
   * <ul>
   *   <li>Given {@link Activity} (default constructor) Implementation is {@link Debug} (default
   *       constructor).
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link Activity#getEvent()}
   */
  @Test
  @DisplayName(
      "Test getEvent(); given Activity (default constructor) Implementation is Debug (default constructor); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional Activity.getEvent()"})
  void testGetEvent_givenActivityImplementationIsDebug_thenReturnNotPresent() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertFalse(activity.getEvent().isPresent());
  }

  /**
   * Test {@link Activity#getEvents()}.
   *
   * <ul>
   *   <li>Given {@link Debug} (default constructor) On is {@code null}.
   *   <li>Then return ParentId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#getEvents()}
   */
  @Test
  @DisplayName(
      "Test getEvents(); given Debug (default constructor) On is 'null'; then return ParentId is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"RelationalEvents Activity.getEvents()"})
  void testGetEvents_givenDebugOnIsNull_thenReturnParentIdIsNull() {
    // Arrange
    Debug implementation = new Debug();
    implementation.setOn(null);

    Activity activity = new Activity();
    activity.setImplementation(implementation);

    // Act
    RelationalEvents actualEvents = activity.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.getEvents().isEmpty());
  }
}
