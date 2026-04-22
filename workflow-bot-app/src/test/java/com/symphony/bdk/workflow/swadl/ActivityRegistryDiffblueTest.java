package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.swadl.VariableDeserializersTest.VariableActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.OboActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.AcceptConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.CreateConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnections;
import com.symphony.bdk.workflow.swadl.v1.activity.group.AddGroupMember;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessages;
import com.symphony.bdk.workflow.swadl.v1.activity.request.ExecuteRequest;
import com.symphony.bdk.workflow.swadl.v1.activity.user.RemoveUserRole;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateSystemUser;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateUser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;

class ActivityRegistryDiffblueTest {
  /**
   * Test {@link ActivityRegistry#getActivityTypes()}.
   *
   * <p>Method under test: {@link ActivityRegistry#getActivityTypes()}
   */
  @Test
  @DisplayName("Test getActivityTypes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ActivityRegistry.getActivityTypes()"})
  void testGetActivityTypes() {
    // Arrange and Act
    Set<Class<? extends BaseActivity>> actualActivityTypes = ActivityRegistry.getActivityTypes();

    // Assert
    assertEquals(48, actualActivityTypes.size());
    assertTrue(actualActivityTypes.contains(VariableActivity.class));
    assertTrue(actualActivityTypes.contains(OboActivity.class));
    assertTrue(actualActivityTypes.contains(AcceptConnection.class));
    assertTrue(actualActivityTypes.contains(CreateConnection.class));
    assertTrue(actualActivityTypes.contains(GetConnections.class));
    assertTrue(actualActivityTypes.contains(AddGroupMember.class));
    assertTrue(actualActivityTypes.contains(GetMessages.class));
    assertTrue(actualActivityTypes.contains(ExecuteRequest.class));
    assertTrue(actualActivityTypes.contains(RemoveUserRole.class));
    assertTrue(actualActivityTypes.contains(UpdateSystemUser.class));
    assertTrue(actualActivityTypes.contains(UpdateUser.class));
  }

  /**
   * Test {@link ActivityRegistry#getActivityTypes()} returns a non-empty set of BaseActivity subtypes.
   *
   * <p>Method under test: {@link ActivityRegistry#getActivityTypes()}
   */
  @Test
  @DisplayName("Test getActivityTypes() returns non-empty set")
  void testGetActivityTypes_returnsNonEmptySet() {
    // Arrange and Act
    Set<Class<? extends BaseActivity>> activityTypes = ActivityRegistry.getActivityTypes();

    // Assert
    assertNotNull(activityTypes);
    assertFalse(activityTypes.isEmpty());
    for (Class<? extends BaseActivity> type : activityTypes) {
      assertTrue(BaseActivity.class.isAssignableFrom(type));
    }
  }

  /**
   * Test {@link ActivityRegistry#getActivityExecutors()} returns a populated map,
   * which exercises {@code findMatchingActivity} called during static initialization.
   *
   * <p>Method under test: {@code ActivityRegistry.findMatchingActivity}
   */
  @Test
  @DisplayName("Test getActivityExecutors() - exercises findMatchingActivity")
  void testGetActivityExecutors_exercisesFindMatchingActivity() {
    // Arrange and Act
    Map<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> executors =
        ActivityRegistry.getActivityExecutors();

    // Assert
    assertNotNull(executors);
    assertFalse(executors.isEmpty());
    for (Map.Entry<Class<? extends BaseActivity>, Class<? extends ActivityExecutor<? extends BaseActivity>>> entry :
        executors.entrySet()) {
      assertTrue(BaseActivity.class.isAssignableFrom(entry.getKey()));
      assertTrue(ActivityExecutor.class.isAssignableFrom(entry.getValue()));
    }
  }

  /**
   * Test private constructor {@link ActivityRegistry#ActivityRegistry()} via reflection.
   *
   * <p>Method under test: {@link ActivityRegistry#ActivityRegistry()}
   */
  @Test
  @DisplayName("Test ActivityRegistry private constructor")
  void testPrivateConstructor() throws Exception {
    // Arrange
    Constructor<ActivityRegistry> constructor = ActivityRegistry.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    // Act
    ActivityRegistry instance = constructor.newInstance();

    // Assert
    assertNotNull(instance);
  }
}
