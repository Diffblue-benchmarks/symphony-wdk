package com.symphony.bdk.workflow.swadl.v1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkflowTest {

  @Test
  void getFirstActivity_whenActivitiesIsEmpty_returnsEmptyOptional() {
    Workflow workflow = new Workflow();
    workflow.setActivities(Collections.emptyList());

    Optional<Activity> result = workflow.getFirstActivity();

    assertFalse(result.isPresent());
  }

  @Test
  void getFirstActivity_whenActivitiesHasOneElement_returnsThatActivity() {
    Workflow workflow = new Workflow();
    Activity activity = new Activity();
    workflow.setActivities(Collections.singletonList(activity));

    Optional<Activity> result = workflow.getFirstActivity();

    assertTrue(result.isPresent());
    assertEquals(activity, result.get());
  }

  @Test
  void getFirstActivity_whenActivitiesHasMultipleElements_returnsFirstActivity() {
    Workflow workflow = new Workflow();
    Activity firstActivity = new Activity();
    Activity secondActivity = new Activity();
    workflow.setActivities(Arrays.asList(firstActivity, secondActivity));

    Optional<Activity> result = workflow.getFirstActivity();

    assertTrue(result.isPresent());
    assertEquals(firstActivity, result.get());
  }

  @Test
  void getProperties_whenPropertiesIsNull_returnsNewProperties() {
    Workflow workflow = new Workflow();
    workflow.setProperties(null);

    Properties result = workflow.getProperties();

    assertNotNull(result);
  }

  @Test
  void getProperties_whenPropertiesIsSet_returnsThatProperties() {
    Workflow workflow = new Workflow();
    Properties properties = new Properties();
    workflow.setProperties(properties);

    Properties result = workflow.getProperties();

    assertEquals(properties, result);
  }

  @Test
  void isToPublish_whenPropertiesIsNull_returnsTrue() {
    Workflow workflow = new Workflow();
    workflow.setProperties(null);

    boolean result = workflow.isToPublish();

    assertTrue(result);
  }

  @Test
  void isToPublish_whenPublishIsTrue_returnsTrue() {
    Workflow workflow = new Workflow();
    Properties properties = new Properties();
    properties.setPublish(true);
    workflow.setProperties(properties);

    boolean result = workflow.isToPublish();

    assertTrue(result);
  }

  @Test
  void isToPublish_whenPublishIsFalse_returnsFalse() {
    Workflow workflow = new Workflow();
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);

    boolean result = workflow.isToPublish();

    assertFalse(result);
  }
}
