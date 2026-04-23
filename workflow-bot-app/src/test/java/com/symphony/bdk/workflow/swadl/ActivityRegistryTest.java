package com.symphony.bdk.workflow.swadl;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityRegistryTest {

  @Test
  void shouldReturnNonEmptyActivityTypes() {
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    assertThat(activityTypes).isNotNull().isNotEmpty();
  }

  @Test
  void shouldReturnDefensiveCopyOfActivityTypes() {
    Set<Class<? extends BaseActivity>> first = ActivityRegistry.getActivityTypes();
    Set<Class<? extends BaseActivity>> second = ActivityRegistry.getActivityTypes();

    first.clear();

    assertThat(second).isNotEmpty();
  }

  @Test
  void shouldReturnNonEmptyActivityExecutors() {
    assertThat(ActivityRegistry.getActivityExecutors()).isNotNull().isNotEmpty();
  }

  @Test
  void shouldMapEachExecutorToMatchingActivity() {
    ActivityRegistry.getActivityExecutors().forEach((activity, executor) -> {
      assertThat(activity).isNotNull();
      assertThat(BaseActivity.class).isAssignableFrom(activity);
    });
  }
}
