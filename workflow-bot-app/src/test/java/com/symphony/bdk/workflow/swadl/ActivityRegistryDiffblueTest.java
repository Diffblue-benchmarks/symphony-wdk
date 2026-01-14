package com.symphony.bdk.workflow.swadl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.symphony.bdk.workflow.lang.custom2.DuplicateCustomActivity;
import com.symphony.bdk.workflow.swadl.VariableDeserializersTest.VariableActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.CreateConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.RemoveConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.group.UpdateGroup;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessage;
import com.symphony.bdk.workflow.swadl.v1.activity.message.PinMessage;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UpdateMessage;
import com.symphony.bdk.workflow.swadl.v1.activity.room.DemoteRoomOwner;
import com.symphony.bdk.workflow.swadl.v1.activity.room.UpdateRoom;
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
  @Tag("MaintainedByDiffblue")
  void testGetActivityTypes() {
    // Arrange and Act
    Set<Class<? extends BaseActivity>> actualActivityTypes = ActivityRegistry.getActivityTypes();

    // Assert
    assertEquals(48, actualActivityTypes.size());
    assertTrue(actualActivityTypes.contains(DuplicateCustomActivity.class));
    assertTrue(actualActivityTypes.contains(VariableActivity.class));
    assertTrue(actualActivityTypes.contains(CreateConnection.class));
    assertTrue(actualActivityTypes.contains(RemoveConnection.class));
    assertTrue(actualActivityTypes.contains(UpdateGroup.class));
    assertTrue(actualActivityTypes.contains(GetMessage.class));
    assertTrue(actualActivityTypes.contains(PinMessage.class));
    assertTrue(actualActivityTypes.contains(UpdateMessage.class));
    assertTrue(actualActivityTypes.contains(DemoteRoomOwner.class));
    assertTrue(actualActivityTypes.contains(UpdateRoom.class));
    assertTrue(actualActivityTypes.contains(UpdateUser.class));
  }
}
