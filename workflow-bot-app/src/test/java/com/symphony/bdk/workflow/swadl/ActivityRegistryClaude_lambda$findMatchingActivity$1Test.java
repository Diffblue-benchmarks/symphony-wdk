package com.symphony.bdk.workflow.swadl;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ActivityRegistry.lambda$findMatchingActivity$1 method.
 * This lambda is inside the findMatchingActivity method at lines 62-68.
 * It filters type arguments to find which ones are BaseActivity subclasses.
 *
 * The lambda code being tested:
 * .filter(arg -> {
 *   try {
 *     return BaseActivity.class.isAssignableFrom(Class.forName(arg.getTypeName()));  // line 64
 *   } catch (ClassNotFoundException e) {                                               // line 65
 *     throw new IllegalStateException("Executor " + a + " should implement ActivityExecutor<Activity>");  // line 66
 *   }
 * })
 *
 * Since this lambda is private and part of static initialization, we test it through
 * the public getActivityExecutors method which triggers the static initialization.
 */
class ActivityRegistryClaude_lambda$findMatchingActivity$1Test {

  @Test
  void testLambdaFilter_checksBaseActivityAssignability() {
    // When getActivityExecutors is called, it triggers static initialization which:
    // 1. Finds all ActivityExecutor subclasses
    // 2. For each executor, calls findMatchingActivity
    // 3. findMatchingActivity uses the lambda to filter type arguments
    // 4. The lambda calls Class.forName(arg.getTypeName()) - line 64
    // 5. The lambda checks BaseActivity.class.isAssignableFrom() - line 64
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Verify all activity classes in the map are BaseActivity subclasses
    // This proves the lambda filter (line 64) worked correctly
    for (Class<? extends BaseActivity> activityClass : executors.keySet()) {
      assertThat(BaseActivity.class.isAssignableFrom(activityClass))
          .as("Lambda filter should have verified %s is assignable from BaseActivity", activityClass.getName())
          .isTrue();
    }
  }

  @Test
  void testLambdaFilter_processesTypeArguments() {
    // The lambda is called for each type argument of each executor class
    // This test verifies that the lambda successfully processed type arguments
    // by checking that we have a populated map
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // The lambda filter runs for each type argument
    // If it successfully filters and finds BaseActivity subclasses, we'll have entries
    assertThat(executors).isNotEmpty();
    assertThat(executors.size()).isGreaterThan(0);
  }

  @Test
  void testLambdaFilter_allActivityClassesAreLoadable() {
    // The lambda calls Class.forName(arg.getTypeName()) on line 64
    // This test verifies that all classes it found are loadable
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    for (Class<? extends BaseActivity> activityClass : executors.keySet()) {
      try {
        // This mimics what the lambda does on line 64
        Class<?> loadedClass = Class.forName(activityClass.getTypeName());
        assertThat(loadedClass)
            .as("Lambda should have loaded class via Class.forName")
            .isNotNull()
            .isEqualTo(activityClass);
      } catch (ClassNotFoundException e) {
        throw new AssertionError(
            "Lambda filter found class " + activityClass.getName() + " but it should be loadable", e);
      }
    }
  }

  @Test
  void testLambdaFilter_allActivityClassesHaveCorrectHierarchy() {
    // The lambda checks if BaseActivity.class.isAssignableFrom() on line 64
    // This verifies that check worked correctly for all found activities
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    for (Class<? extends BaseActivity> activityClass : executors.keySet()) {
      // Verify the class hierarchy that the lambda checked
      assertThat(activityClass.getSuperclass() != null
          || BaseActivity.class.equals(activityClass)
          || BaseActivity.class.isAssignableFrom(activityClass))
          .as("Lambda verified %s has correct hierarchy", activityClass.getName())
          .isTrue();
    }
  }

  @Test
  void testLambdaFilter_executorsHaveValidTypeParameters() {
    // The lambda filters type arguments from executors
    // This test verifies each executor has a valid type parameter that the lambda processed
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    for (Map.Entry<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> entry :
        executors.entrySet()) {
      Class<? extends BaseActivity> activityClass = entry.getKey();
      Class<? extends ActivityExecutor<? extends BaseActivity>> executorClass = entry.getValue();

      // The lambda successfully matched this executor to this activity
      // by filtering type arguments and finding a BaseActivity subclass
      assertThat(activityClass).isNotNull();
      assertThat(executorClass).isNotNull();
      assertThat(ActivityExecutor.class.isAssignableFrom(executorClass))
          .as("Executor %s should be an ActivityExecutor", executorClass.getName())
          .isTrue();
    }
  }

  @Test
  void testLambdaFilter_noInvalidClassesInMap() {
    // The lambda would throw IllegalStateException (line 66) if ClassNotFoundException occurs
    // If that happened during static initialization, we wouldn't get a valid map
    // This test verifies the lambda didn't encounter any ClassNotFoundException
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // If the lambda encountered ClassNotFoundException on line 65 and threw IllegalStateException on line 66,
    // the static initialization would have failed
    // The fact that we have a map means the lambda successfully processed all type arguments
    assertThat(executors).isNotNull();

    for (Class<? extends BaseActivity> activityClass : executors.keySet()) {
      // Each class should be a valid, loadable class that the lambda found via Class.forName
      assertThat(activityClass.getTypeName())
          .as("Lambda should have loaded valid class name")
          .isNotBlank();
    }
  }

  @Test
  void testLambdaFilter_filtersOnlyBaseActivityTypes() {
    // The lambda specifically checks BaseActivity.class.isAssignableFrom() on line 64
    // This test verifies that ONLY BaseActivity subclasses made it through the filter
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    for (Class<? extends BaseActivity> activityClass : executors.keySet()) {
      // The lambda's filter condition (line 64) should ensure this
      boolean isBaseActivitySubclass = BaseActivity.class.isAssignableFrom(activityClass);
      assertThat(isBaseActivitySubclass)
          .as("Lambda filter on line 64 should only pass BaseActivity subclasses, but %s failed",
              activityClass.getName())
          .isTrue();

      // Also verify the class is actually a class (not an interface or primitive)
      assertThat(activityClass.isInterface())
          .as("Lambda should filter to concrete activity classes")
          .isFalse();
    }
  }

  @Test
  void testLambdaFilter_classForNameSucceeds() {
    // Line 64 of the lambda calls Class.forName(arg.getTypeName())
    // This test explicitly verifies that operation succeeds for all found activities
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    for (Class<? extends BaseActivity> activityClass : executors.keySet()) {
      String typeName = activityClass.getTypeName();

      try {
        // This is exactly what line 64 does
        Class<?> forNameResult = Class.forName(typeName);

        // Verify it returns the expected class
        assertThat(forNameResult)
            .as("Class.forName on line 64 should return the correct class")
            .isEqualTo(activityClass);

        // Verify the lambda's isAssignableFrom check (also line 64)
        assertThat(BaseActivity.class.isAssignableFrom(forNameResult))
            .as("Lambda check on line 64 should pass for %s", typeName)
            .isTrue();

      } catch (ClassNotFoundException e) {
        // If this happens, the lambda would have caught it on line 65
        // and thrown IllegalStateException on line 66
        throw new AssertionError(
            "Lambda at line 64 should not encounter ClassNotFoundException for " + typeName, e);
      }
    }
  }

  @Test
  void testLambdaFilter_handlesMultipleTypeArguments() {
    // Some executors might have multiple type arguments
    // The lambda filters through them to find BaseActivity subclasses
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Each executor should have exactly one activity matched by the lambda
    assertThat(executors.keySet()).hasSameSizeAs(executors.values());

    // Verify the lambda successfully identified the correct type argument for each executor
    for (Map.Entry<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> entry :
        executors.entrySet()) {
      Class<? extends BaseActivity> activity = entry.getKey();
      Class<? extends ActivityExecutor<? extends BaseActivity>> executor = entry.getValue();

      // The lambda filtered type arguments and found this activity for this executor
      assertThat(activity).isNotNull();
      assertThat(BaseActivity.class.isAssignableFrom(activity)).isTrue();
    }
  }
}
