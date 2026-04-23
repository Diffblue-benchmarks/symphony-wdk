package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class CreateRoomTest {

  @Test
  void shouldReturnUserIdsAsLongs() {
    CreateRoom createRoom = new CreateRoom();
    List<Long> userIds = Arrays.asList(123L, 456L);
    createRoom.setUserIds(userIds);

    List<Long> result = createRoom.getUserIdsAsLongs();

    assertEquals(userIds, result);
  }

  @Test
  void shouldReturnNullWhenUserIdsNotSet() {
    CreateRoom createRoom = new CreateRoom();

    List<Long> result = createRoom.getUserIdsAsLongs();

    assertNull(result);
  }
}
