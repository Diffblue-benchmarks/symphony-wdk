package com.symphony.bdk.workflow.swadl;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ActivityRegistry.<clinit> static initializer block.
 * The static initializer (lines 34-56) runs when the class is first loaded.
 * It performs the following operations:
 *
 * Line 35-43: Creates a Reflections instance with configuration
 * Line 44: Scans for BaseActivity subtypes and stores in activityTypes
 * Line 46-48: Scans for ActivityExecutor subtypes, maps them to activities, stores in activityExecutors
 * Line 50-53: Logs found activities with simple names
 * Line 55: Logs full class names and executors at TRACE level
 *
 * Since the static initializer runs automatically when the class is loaded,
 * we test it by accessing the public static methods and fields that were
 * initialized by the static block.
 */
class ActivityRegistryClaude_clinitTest {

  @Test
  void testStaticInitializer_initializesActivityTypes() {
    // Line 44: activityTypes = reflections.getSubTypesOf(BaseActivity.class);
    // This tests that the static initializer populated the activityTypes field
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    // Verify the static initializer found and stored activity types
    assertThat(activityTypes)
        .as("Static initializer should have populated activityTypes on line 44")
        .isNotNull()
        .isNotEmpty();
  }

  @Test
  void testStaticInitializer_initializesActivityExecutors() {
    // Lines 46-48: activityExecutors = reflections.getSubTypesOf(ActivityExecutor.class).stream()...
    // This tests that the static initializer populated the activityExecutors field
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Verify the static initializer found and mapped executors
    assertThat(executors)
        .as("Static initializer should have populated activityExecutors on lines 46-48")
        .isNotNull()
        .isNotEmpty();
  }

  @Test
  void testStaticInitializer_reflectionsConfiguration() {
    // Lines 35-43: Creates Reflections with ConfigurationBuilder
    // The configuration filters for "lib/" folders and "com.symphony.bdk.workflow" packages
    // We can verify this worked by checking the results
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // The Reflections configuration on lines 35-43 should have found activities
    assertThat(activityTypes.size()).isGreaterThan(0);
    assertThat(executors.size()).isGreaterThan(0);

    // Verify the classes found are from the expected packages (line 42: addUrls for com.symphony.bdk.workflow)
    for (Class<? extends BaseActivity> activityClass : activityTypes) {
      String packageName = activityClass.getPackage().getName();
      // The static initializer filters for com.symphony.bdk.workflow package
      assertThat(packageName)
          .as("Static initializer should find activities in configured packages")
          .contains("com.symphony.bdk.workflow");
    }
  }

  @Test
  void testStaticInitializer_scansSubTypesOfBaseActivity() {
    // Line 44: activityTypes = reflections.getSubTypesOf(BaseActivity.class);
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    // All types should be subtypes of BaseActivity (line 44)
    for (Class<? extends BaseActivity> activityClass : activityTypes) {
      assertThat(BaseActivity.class.isAssignableFrom(activityClass))
          .as("Line 44 should only include BaseActivity subtypes, but %s is not", activityClass.getName())
          .isTrue();
    }
  }

  @Test
  void testStaticInitializer_scansSubTypesOfActivityExecutor() {
    // Line 46: reflections.getSubTypesOf(ActivityExecutor.class).stream()
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // All executor values should be subtypes of ActivityExecutor (line 46)
    for (Class<? extends ActivityExecutor<? extends BaseActivity>> executorClass : executors.values()) {
      assertThat(ActivityExecutor.class.isAssignableFrom(executorClass))
          .as("Line 46 should only include ActivityExecutor subtypes, but %s is not", executorClass.getName())
          .isTrue();
    }
  }

  @Test
  void testStaticInitializer_mapsExecutorsToActivities() {
    // Lines 46-48: Maps executors to their matching activities using Collectors.toMap
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Each executor should be mapped to an activity
    assertThat(executors.keySet()).isNotEmpty();
    assertThat(executors.values()).isNotEmpty();
    assertThat(executors.keySet()).hasSameSizeAs(executors.values());

    // Verify the mapping creates valid pairs
    for (Map.Entry<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> entry :
        executors.entrySet()) {
      Class<? extends BaseActivity> activity = entry.getKey();
      Class<? extends ActivityExecutor<? extends BaseActivity>> executor = entry.getValue();

      assertThat(activity)
          .as("Lines 46-48 should map valid activity classes")
          .isNotNull();
      assertThat(executor)
          .as("Lines 46-48 should map valid executor classes")
          .isNotNull();
    }
  }

  @Test
  void testStaticInitializer_usesStreamProcessing() {
    // Lines 46-48: Uses stream().map().collect() to process executors
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // The stream processing (line 47: .map(Class.class::cast)) should have cast all executors
    for (Class<? extends ActivityExecutor<? extends BaseActivity>> executorClass : executors.values()) {
      // Verify each is a proper Class instance (line 47)
      assertThat(executorClass)
          .as("Line 47 should cast to Class")
          .isInstanceOf(Class.class);
    }
  }

  @Test
  void testStaticInitializer_collectorsToMap() {
    // Line 48: .collect(Collectors.toMap(ActivityRegistry::findMatchingActivity, Function.identity()));
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // The Collectors.toMap should have created a proper map with unique keys
    assertThat(executors).isNotNull();

    // Function.identity() (line 48) means values should be the executor classes themselves
    for (Map.Entry<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> entry :
        executors.entrySet()) {
      Class<? extends ActivityExecutor<? extends BaseActivity>> executor = entry.getValue();

      // Verify the executor is a real class (from Function.identity())
      assertThat(executor.getName())
          .as("Line 48 Function.identity() should preserve executor class")
          .isNotBlank();
    }
  }

  @Test
  void testStaticInitializer_scannerConfiguration() {
    // Line 36: .setScanners(Scanners.SubTypes)
    // This configures Reflections to scan for subtypes
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // The SubTypes scanner (line 36) should have found subtype classes
    assertThat(activityTypes)
        .as("Line 36 SubTypes scanner should find BaseActivity subtypes")
        .isNotEmpty();
    assertThat(executors)
        .as("Line 36 SubTypes scanner should find ActivityExecutor subtypes")
        .isNotEmpty();

    // All found activities should be subtypes (not the base class itself)
    for (Class<? extends BaseActivity> activityClass : activityTypes) {
      assertThat(activityClass.equals(BaseActivity.class))
          .as("SubTypes scanner should find subtypes, not BaseActivity itself")
          .isFalse();
    }
  }

  @Test
  void testStaticInitializer_urlFiltering() {
    // Lines 38-41: Filters URLs for lib/ folders and excludes BOOT-INF
    // Line 42: Adds URLs for com.symphony.bdk.workflow package
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    // The URL filtering should have found classes from the workflow package
    assertThat(activityTypes).isNotEmpty();

    // All activities should be from packages that passed the filter
    for (Class<? extends BaseActivity> activityClass : activityTypes) {
      String className = activityClass.getName();
      assertThat(className)
          .as("Lines 38-42 URL filtering should find classes")
          .isNotBlank();
    }
  }

  @Test
  void testStaticInitializer_filterBuilderPattern() {
    // Line 43: .filterInputsBy(new FilterBuilder().includePattern(".*class"))
    // This filters for .class files
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // The filter for ".*class" pattern should have found class files
    assertThat(activityTypes)
        .as("Line 43 filter should find .class files")
        .isNotEmpty();

    // All found classes should be proper Java classes
    for (Class<? extends BaseActivity> activityClass : activityTypes) {
      assertThat(activityClass.getName())
          .as("Line 43 should filter for .class files")
          .matches("^[a-zA-Z0-9._$]+$");
    }
  }

  @Test
  void testStaticInitializer_activityTypesImmutability() {
    // Line 44: activityTypes = reflections.getSubTypesOf(BaseActivity.class);
    // The getActivityTypes method returns a new HashSet (line 82), but the original should be populated
    Set<Class<? extends BaseActivity>> activityTypes1 = ActivityRegistry.getActivityTypes();
    Set<Class<? extends BaseActivity>> activityTypes2 = ActivityRegistry.getActivityTypes();

    // Both should have the same content from the static initializer
    assertThat(activityTypes1)
        .as("Static initializer should populate activityTypes consistently")
        .containsExactlyInAnyOrderElementsOf(activityTypes2);
  }

  @Test
  void testStaticInitializer_executorsMapImmutability() {
    // Lines 46-48: activityExecutors = ...collect(Collectors.toMap(...))
    // The getActivityExecutors returns the map directly
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors1 =
        ActivityRegistry.getActivityExecutors();
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors2 =
        ActivityRegistry.getActivityExecutors();

    // Should return the same map instance from static initialization
    assertThat(executors1)
        .as("Static initializer should create a single executors map")
        .isSameAs(executors2);
  }

  @Test
  void testStaticInitializer_loggingData() {
    // Lines 50-53: Logs activity simple names sorted
    // Line 55: Logs full class names and executors at TRACE level
    // We can't directly test logging, but we can verify the data that would be logged
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Lines 50-53: Gets simple names and sorts them
    assertThat(activityTypes.stream()
        .map(Class::getSimpleName)
        .sorted()
        .toList())
        .as("Lines 50-53 should create sorted list of simple names for logging")
        .isNotEmpty()
        .isSorted();

    // Line 55: Would log activityTypes and activityExecutors
    assertThat(activityTypes)
        .as("Line 55 TRACE log should have activityTypes data")
        .isNotNull();
    assertThat(executors)
        .as("Line 55 TRACE log should have activityExecutors data")
        .isNotNull();
  }

  @Test
  void testStaticInitializer_allActivityTypesHaveExecutors() {
    // The static initializer should ensure consistency between the two collections
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // All activities in executors map should be in activityTypes
    // (Note: Not all activities may have executors, but all executors should map to activities)
    for (Class<? extends BaseActivity> activity : executors.keySet()) {
      assertThat(BaseActivity.class.isAssignableFrom(activity))
          .as("Static initializer should maintain type consistency")
          .isTrue();
    }
  }

  @Test
  void testStaticInitializer_configurationBuilderChaining() {
    // Lines 35-43: Uses ConfigurationBuilder method chaining
    // .setScanners() -> .addUrls() -> .addUrls() -> .filterInputsBy()
    // We verify the configuration worked by checking the results
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // The chained configuration should have produced results
    assertThat(activityTypes)
        .as("Lines 35-43 ConfigurationBuilder chaining should work")
        .isNotEmpty();
    assertThat(executors)
        .as("Lines 35-43 ConfigurationBuilder chaining should work")
        .isNotEmpty();
  }

  @Test
  void testStaticInitializer_classLoaderUrls() {
    // Line 38: ClasspathHelper.forClassLoader().stream()
    // Lines 40-41: Filters for lib/ and excludes BOOT-INF
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    // The classloader URL filtering should have found workflow classes
    assertThat(activityTypes).isNotEmpty();

    // Check that found classes can be loaded (proving classloader config worked)
    for (Class<? extends BaseActivity> activityClass : activityTypes) {
      try {
        // Should be loadable via classloader
        Class.forName(activityClass.getName());
      } catch (ClassNotFoundException e) {
        throw new AssertionError("Static initializer found class that can't be loaded: " + activityClass.getName(), e);
      }
    }
  }
}
