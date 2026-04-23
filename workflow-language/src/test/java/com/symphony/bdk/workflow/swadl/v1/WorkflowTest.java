package com.symphony.bdk.workflow.swadl.v1;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkflowTest {

  @Test
  void getFirstActivityReturnsEmptyWhenNoActivities() {
    Workflow workflow = new Workflow();
    workflow.setActivities(Collections.emptyList());

    Optional<Activity> result = workflow.getFirstActivity();

    assertFalse(result.isPresent());
  }

  @Test
  void getFirstActivityReturnsFirstWhenActivitiesPresent() {
    Activity activity = new Activity();
    Workflow workflow = new Workflow();
    workflow.setActivities(List.of(activity));

    Optional<Activity> result = workflow.getFirstActivity();

    assertTrue(result.isPresent());
    assertSame(activity, result.get());
  }

  @Test
  void getPropertiesReturnsNewPropertiesWhenNull() {
    Workflow workflow = new Workflow();
    workflow.setProperties(null);

    Properties result = workflow.getProperties();

    assertNotNull(result);
  }

  @Test
  void getPropertiesReturnsExistingPropertiesWhenSet() {
    Workflow workflow = new Workflow();
    Properties properties = new Properties();
    workflow.setProperties(properties);

    Properties result = workflow.getProperties();

    assertSame(properties, result);
  }

  @Test
  void isToPublishReturnsTrueByDefault() {
    Workflow workflow = new Workflow();
    workflow.setProperties(null);

    boolean result = workflow.isToPublish();

    assertTrue(result);
  }

  @Test
  void isToPublishReturnsFalseWhenPublishIsFalse() {
    Workflow workflow = new Workflow();
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);

    boolean result = workflow.isToPublish();

    assertFalse(result);
  }
}
