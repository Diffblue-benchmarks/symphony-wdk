package com.symphony.bdk.workflow.swadl.v1.activity.room;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CreateRoomTest {

  @Test
  void getUserIdsAsLongs_returnsUserIds() {
    CreateRoom createRoom = new CreateRoom();
    List<Long> userIds = Arrays.asList(1L, 2L, 3L);
    createRoom.setUserIds(userIds);

    List<Long> result = createRoom.getUserIdsAsLongs();

    assertEquals(userIds, result);
  }

  @Test
  void getUserIdsAsLongs_returnsNullWhenUserIdsNotSet() {
    CreateRoom createRoom = new CreateRoom();

    List<Long> result = createRoom.getUserIdsAsLongs();

    assertNull(result);
  }
}
