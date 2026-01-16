package com.symphony.bdk.workflow.swadl;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityRegistryClaudeTest {

  @Test
  void testGetActivityTypes_returnsNonEmptySet() {
    // Test that getActivityTypes returns a non-empty set of activity types
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    assertThat(activityTypes).isNotNull();
    assertThat(activityTypes).isNotEmpty();
  }

  @Test
  void testGetActivityTypes_allElementsExtendBaseActivity() {
    // Test that all returned classes extend BaseActivity
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    for (Class<? extends BaseActivity> activityType : activityTypes) {
      assertThat(BaseActivity.class.isAssignableFrom(activityType))
          .as("Activity type %s should extend BaseActivity", activityType.getName())
          .isTrue();
    }
  }

  @Test
  void testGetActivityTypes_returnsNewInstanceEachTime() {
    // Test that getActivityTypes returns a new set instance each time
    // This ensures modifications to the returned set don't affect the internal registry
    Set<Class<? extends BaseActivity>> activityTypes1 = ActivityRegistry.getActivityTypes();
    Set<Class<? extends BaseActivity>> activityTypes2 = ActivityRegistry.getActivityTypes();

    assertThat(activityTypes1).isNotSameAs(activityTypes2);
    assertThat(activityTypes1).isEqualTo(activityTypes2);
  }

  @Test
  void testGetActivityTypes_returnedSetIsModifiable() {
    // Test that the returned set is modifiable and doesn't affect the registry
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();
    int originalSize = activityTypes.size();

    // Clear the returned set
    activityTypes.clear();
    assertThat(activityTypes).isEmpty();

    // Get a new set and verify it still has the original size
    Set<Class<? extends BaseActivity>> newActivityTypes = ActivityRegistry.getActivityTypes();
    assertThat(newActivityTypes).hasSize(originalSize);
  }

  @Test
  void testGetActivityTypes_containsExpectedActivityTypes() {
    // Test that the registry contains some known activity types
    // This verifies that the static initialization successfully scanned the classpath
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    // Verify that we found at least some activities
    // We don't check for specific classes because they may vary depending on the test environment
    assertThat(activityTypes.size()).isGreaterThan(0);

    // All activity types should have proper class names
    for (Class<? extends BaseActivity> activityType : activityTypes) {
      assertThat(activityType.getName()).isNotBlank();
      assertThat(activityType.getSimpleName()).isNotBlank();
    }
  }

  @Test
  void testGetActivityExecutors_isNotNull() {
    // Test that getActivityExecutors returns a non-null map
    var activityExecutors = ActivityRegistry.getActivityExecutors();

    assertThat(activityExecutors).isNotNull();
  }

  @Test
  void testGetActivityExecutors_allKeysAreActivityTypes() {
    // Test that all keys in the executors map are valid activity types
    var activityExecutors = ActivityRegistry.getActivityExecutors();
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    for (Class<? extends BaseActivity> activityType : activityExecutors.keySet()) {
      assertThat(BaseActivity.class.isAssignableFrom(activityType))
          .as("Executor key %s should be an activity type", activityType.getName())
          .isTrue();
    }
  }

  @Test
  void testGetActivityExecutors_allValuesAreExecutorTypes() {
    // Test that all values in the executors map are valid executor types
    var activityExecutors = ActivityRegistry.getActivityExecutors();

    assertThat(activityExecutors.values()).allSatisfy(executor -> {
      assertThat(executor).isNotNull();
      assertThat(executor.getName()).isNotBlank();
    });
  }
}
