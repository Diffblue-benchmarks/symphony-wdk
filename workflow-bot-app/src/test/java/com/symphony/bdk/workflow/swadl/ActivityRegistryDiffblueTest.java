package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.DoSomething;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.activity.OboActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.group.GetGroups;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessages;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UnpinMessage;
import com.symphony.bdk.workflow.swadl.v1.activity.room.RemoveRoomMember;
import com.symphony.bdk.workflow.swadl.v1.activity.user.AddUserRole;
import com.symphony.bdk.workflow.swadl.v1.activity.user.GetUsers;
import com.symphony.bdk.workflow.swadl.v1.activity.user.UpdateUser;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
    assertTrue(actualActivityTypes.contains(DoSomething.class));
    assertTrue(actualActivityTypes.contains(Debug.class));
    assertTrue(actualActivityTypes.contains(OboActivity.class));
    assertTrue(actualActivityTypes.contains(GetConnection.class));
    assertTrue(actualActivityTypes.contains(GetGroups.class));
    assertTrue(actualActivityTypes.contains(GetMessages.class));
    assertTrue(actualActivityTypes.contains(UnpinMessage.class));
    assertTrue(actualActivityTypes.contains(RemoveRoomMember.class));
    assertTrue(actualActivityTypes.contains(AddUserRole.class));
    assertTrue(actualActivityTypes.contains(GetUsers.class));
    assertTrue(actualActivityTypes.contains(UpdateUser.class));
  }
}
