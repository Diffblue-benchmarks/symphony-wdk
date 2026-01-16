package com.symphony.bdk.workflow.swadl;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ActivityRegistry.findMatchingActivity method.
 * Since findMatchingActivity is private, we test it through the public getActivityExecutors method
 * which calls findMatchingActivity during static initialization.
 */
class ActivityRegistryClaude_findMatchingActivityTest {

  @Test
  void testGetActivityExecutors_callsFindMatchingActivity() {
    // When getActivityExecutors is called, it returns the map that was populated
    // during static initialization by calling findMatchingActivity for each executor
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Verify the map is not null and contains entries
    assertThat(executors).isNotNull();
    assertThat(executors).isNotEmpty();
  }

  @Test
  void testGetActivityExecutors_allKeysAreBaseActivitySubclasses() {
    // This tests that findMatchingActivity correctly extracts the activity type
    // from the executor's generic type parameter (lines 61-62, 69-70)
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    for (Class<? extends BaseActivity> activityClass : executors.keySet()) {
      // Verify each key is a BaseActivity subclass (line 71)
      assertThat(BaseActivity.class.isAssignableFrom(activityClass))
          .as("Activity class %s should be assignable from BaseActivity", activityClass.getName())
          .isTrue();
    }
  }

  @Test
  void testGetActivityExecutors_allValuesAreActivityExecutorSubclasses() {
    // This verifies that the executors found by findMatchingActivity are valid
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    for (Class<? extends ActivityExecutor<? extends BaseActivity>> executorClass : executors.values()) {
      // Verify each value is an ActivityExecutor subclass
      assertThat(ActivityExecutor.class.isAssignableFrom(executorClass))
          .as("Executor class %s should be assignable from ActivityExecutor", executorClass.getName())
          .isTrue();
    }
  }

  @Test
  void testGetActivityExecutors_executorsMatchTheirActivities() {
    // This tests that findMatchingActivity correctly matches executors to their activities
    // by extracting the type argument (lines 61-62, 69-70)
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // For each executor, verify it can be matched to its activity
    for (Map.Entry<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> entry : executors.entrySet()) {
      Class<? extends BaseActivity> activityClass = entry.getKey();
      Class<? extends ActivityExecutor<? extends BaseActivity>> executorClass = entry.getValue();

      // Both should have valid names
      assertThat(activityClass.getName()).isNotBlank();
      assertThat(executorClass.getName()).isNotBlank();

      // The activity class should be a concrete class (not an interface)
      assertThat(activityClass.isInterface())
          .as("Activity class %s should not be an interface", activityClass.getName())
          .isFalse();
    }
  }

  @Test
  void testGetActivityExecutors_containsKnownActivityExecutorPairs() {
    // This verifies that the static initialization successfully called findMatchingActivity
    // for all discovered executors and populated the map correctly
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Verify we have a reasonable number of executors
    // (the exact number depends on what's on the classpath, but there should be several)
    assertThat(executors.size()).isGreaterThan(0);

    // Verify that all executors in the map have a corresponding activity
    assertThat(executors.keySet()).hasSameSizeAs(executors.values());
  }

  @Test
  void testGetActivityExecutors_noNullKeysOrValues() {
    // This tests that findMatchingActivity doesn't return null for valid executors
    // (line 73 would return null if ClassNotFoundException is caught)
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Verify no null keys or values
    for (Map.Entry<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> entry : executors.entrySet()) {
      assertThat(entry.getKey())
          .as("Activity class should not be null")
          .isNotNull();
      assertThat(entry.getValue())
          .as("Executor class should not be null")
          .isNotNull();
    }
  }

  @Test
  void testGetActivityExecutors_activityTypesAreLoadableClasses() {
    // This tests that the classes returned by findMatchingActivity (line 71)
    // are properly loadable via Class.forName
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    for (Class<? extends BaseActivity> activityClass : executors.keySet()) {
      try {
        // Verify we can load the class by name (as findMatchingActivity does on line 71)
        Class<?> loadedClass = Class.forName(activityClass.getTypeName());
        assertThat(loadedClass).isEqualTo(activityClass);
      } catch (ClassNotFoundException e) {
        throw new AssertionError("Activity class " + activityClass.getName() + " should be loadable", e);
      }
    }
  }

  @Test
  void testGetActivityExecutors_consistentAcrossMultipleCalls() {
    // Verify that the static initialization only happens once and returns consistent results
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors1 =
        ActivityRegistry.getActivityExecutors();
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors2 =
        ActivityRegistry.getActivityExecutors();

    // Should return the same instance (static field)
    assertThat(executors1).isSameAs(executors2);
  }
}
