package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
}
