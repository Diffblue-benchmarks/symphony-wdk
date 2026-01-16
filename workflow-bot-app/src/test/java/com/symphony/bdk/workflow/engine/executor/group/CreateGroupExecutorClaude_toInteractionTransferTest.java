package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.gen.api.model.GroupInteractionTransfer;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CreateGroupExecutorClaude_toInteractionTransferTest {

  @Test
  void toInteractionTransfer_withNullInput_shouldReturnNull() {
    // When: toInteractionTransfer is called with null
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(null);

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void toInteractionTransfer_withEmptyInteractionTransfer_shouldReturnObjectWithNullFields() {
    // Given: Empty interaction transfer (all fields null)
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should return GroupInteractionTransfer with null fields
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isNull();
    assertThat(result.getRestrictedUsersList()).isNull();
  }

  @Test
  void toInteractionTransfer_withTenantIdsOnly_shouldMapTenantIdsCorrectly() {
    // Given: Interaction transfer with only tenant IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map tenant IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getRestrictedUsersList()).isNull();
  }

  @Test
  void toInteractionTransfer_withUserIdsOnly_shouldMapUserIdsCorrectly() {
    // Given: Interaction transfer with only user IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Arrays.asList(111111111L, 222222222L, 333333333L);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map user IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isNull();
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toInteractionTransfer_withBothTenantIdsAndUserIds_shouldMapBothCorrectly() {
    // Given: Interaction transfer with both tenant IDs and user IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Arrays.asList(111111111L, 222222222L, 333333333L);
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map both fields correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toInteractionTransfer_withEmptyTenantIdsList_shouldMapEmptyList() {
    // Given: Interaction transfer with empty tenant IDs list
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Collections.emptyList();
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map empty list correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEmpty();
  }

  @Test
  void toInteractionTransfer_withEmptyUserIdsList_shouldMapEmptyList() {
    // Given: Interaction transfer with empty user IDs list
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Collections.emptyList();
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map empty list correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).isEmpty();
  }

  @Test
  void toInteractionTransfer_withSingleTenantId_shouldMapCorrectly() {
    // Given: Interaction transfer with single tenant ID
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Collections.singletonList(42);
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map single tenant ID correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).hasSize(1);
    assertThat(result.getRestrictedTenantsList()).containsExactly(42);
  }

  @Test
  void toInteractionTransfer_withSingleUserId_shouldMapCorrectly() {
    // Given: Interaction transfer with single user ID
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Collections.singletonList(987654321L);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map single user ID correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).hasSize(1);
    assertThat(result.getRestrictedUsersList()).containsExactly(987654321L);
  }

  @Test
  void toInteractionTransfer_withMultipleTenantIds_shouldMapAllCorrectly() {
    // Given: Interaction transfer with multiple tenant IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map all tenant IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).hasSize(10);
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
  }

  @Test
  void toInteractionTransfer_withMultipleUserIds_shouldMapAllCorrectly() {
    // Given: Interaction transfer with multiple user IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Arrays.asList(
        111111111L, 222222222L, 333333333L, 444444444L, 555555555L,
        666666666L, 777777777L, 888888888L, 999999999L, 1000000000L
    );
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map all user IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).hasSize(10);
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toInteractionTransfer_withDuplicateTenantIds_shouldMapAllDuplicates() {
    // Given: Interaction transfer with duplicate tenant IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(1, 1, 2, 2, 3, 3);
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map all duplicates
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).hasSize(6);
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
  }

  @Test
  void toInteractionTransfer_withDuplicateUserIds_shouldMapAllDuplicates() {
    // Given: Interaction transfer with duplicate user IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Arrays.asList(111111111L, 111111111L, 222222222L, 222222222L);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map all duplicates
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).hasSize(4);
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toInteractionTransfer_withZeroTenantId_shouldMapCorrectly() {
    // Given: Interaction transfer with zero tenant ID
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(0, 1, 2);
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map zero value correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(0, 1, 2);
  }

  @Test
  void toInteractionTransfer_withZeroUserId_shouldMapCorrectly() {
    // Given: Interaction transfer with zero user ID
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Arrays.asList(0L, 111111111L, 222222222L);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map zero value correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).containsExactly(0L, 111111111L, 222222222L);
  }

  @Test
  void toInteractionTransfer_withNegativeTenantIds_shouldMapCorrectly() {
    // Given: Interaction transfer with negative tenant IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(-1, -2, -3);
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map negative values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(-1, -2, -3);
  }

  @Test
  void toInteractionTransfer_withNegativeUserIds_shouldMapCorrectly() {
    // Given: Interaction transfer with negative user IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Arrays.asList(-111111111L, -222222222L, -333333333L);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map negative values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).containsExactly(-111111111L, -222222222L, -333333333L);
  }

  @Test
  void toInteractionTransfer_withMaxIntegerTenantId_shouldHandleCorrectly() {
    // Given: Interaction transfer with max integer value as tenant ID
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(Integer.MAX_VALUE, 1, Integer.MIN_VALUE);
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should handle max integer value correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(Integer.MAX_VALUE, 1, Integer.MIN_VALUE);
  }

  @Test
  void toInteractionTransfer_withMaxLongUserId_shouldHandleCorrectly() {
    // Given: Interaction transfer with max long value as user ID
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Arrays.asList(Long.MAX_VALUE, 1L, Long.MIN_VALUE);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should handle max long value correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).containsExactly(Long.MAX_VALUE, 1L, Long.MIN_VALUE);
  }

  @Test
  void toInteractionTransfer_shouldNotThrowException() {
    // Given: Valid interaction transfer
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(Arrays.asList(1, 2, 3));
    interactionTransfer.setUserIds(Arrays.asList(111L, 222L, 333L));

    // When/Then: Should not throw any exception
    assertThatCode(() -> CreateGroupExecutor.toInteractionTransfer(interactionTransfer))
        .doesNotThrowAnyException();
  }

  @Test
  void toInteractionTransfer_calledMultipleTimes_shouldReturnConsistentResults() {
    // Given: Same interaction transfer
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called multiple times
    GroupInteractionTransfer result1 = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);
    GroupInteractionTransfer result2 = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should return consistent results (but different instances)
    assertThat(result1.getRestrictedTenantsList()).isEqualTo(result2.getRestrictedTenantsList());
    assertThat(result1.getRestrictedUsersList()).isEqualTo(result2.getRestrictedUsersList());
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void toInteractionTransfer_withLargeLists_shouldHandleCorrectly() {
    // Given: Interaction transfer with large lists
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();

    List<Integer> tenantIds = new java.util.ArrayList<>();
    List<Long> userIds = new java.util.ArrayList<>();
    for (int i = 0; i < 100; i++) {
      tenantIds.add(i);
      userIds.add((long) i);
    }

    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should handle large lists correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).hasSize(100);
    assertThat(result.getRestrictedUsersList()).hasSize(100);
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toInteractionTransfer_withMixedPositiveAndNegativeValues_shouldMapCorrectly() {
    // Given: Interaction transfer with mixed positive and negative values
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(-5, -1, 0, 1, 5);
    List<Long> userIds = Arrays.asList(-999999999L, -1L, 0L, 1L, 999999999L);
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map mixed values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(-5, -1, 0, 1, 5);
    assertThat(result.getRestrictedUsersList()).containsExactly(-999999999L, -1L, 0L, 1L, 999999999L);
  }

  @Test
  void toInteractionTransfer_withOnlyTenantIdsEmptyUserIds_shouldMapCorrectly() {
    // Given: Interaction transfer with tenant IDs and empty user IDs list
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Collections.emptyList();
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map correctly with empty user IDs list
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getRestrictedUsersList()).isEmpty();
  }

  @Test
  void toInteractionTransfer_withEmptyTenantIdsOnlyUserIds_shouldMapCorrectly() {
    // Given: Interaction transfer with empty tenant IDs list and user IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Collections.emptyList();
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map correctly with empty tenant IDs list
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEmpty();
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toInteractionTransfer_withBothEmptyLists_shouldMapEmptyLists() {
    // Given: Interaction transfer with both empty lists
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Collections.emptyList();
    List<Long> userIds = Collections.emptyList();
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should map both empty lists correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEmpty();
    assertThat(result.getRestrictedUsersList()).isEmpty();
  }

  @Test
  void toInteractionTransfer_withUnorderedIds_shouldPreserveOrder() {
    // Given: Interaction transfer with unordered IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(5, 1, 9, 2, 7);
    List<Long> userIds = Arrays.asList(999L, 111L, 555L, 333L, 777L);
    interactionTransfer.setTenantIds(tenantIds);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should preserve the original order
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(5, 1, 9, 2, 7);
    assertThat(result.getRestrictedUsersList()).containsExactly(999L, 111L, 555L, 333L, 777L);
  }

  @Test
  void toInteractionTransfer_shouldReturnNewInstanceEachTime() {
    // Given: Same interaction transfer
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    interactionTransfer.setTenantIds(Arrays.asList(1, 2, 3));

    // When: toInteractionTransfer is called multiple times
    GroupInteractionTransfer result1 = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);
    GroupInteractionTransfer result2 = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should return different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void toInteractionTransfer_withVeryLargeTenantIds_shouldHandleCorrectly() {
    // Given: Interaction transfer with very large tenant IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Integer> tenantIds = Arrays.asList(999999999, 888888888, 777777777);
    interactionTransfer.setTenantIds(tenantIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should handle very large values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(999999999, 888888888, 777777777);
  }

  @Test
  void toInteractionTransfer_withVeryLargeUserIds_shouldHandleCorrectly() {
    // Given: Interaction transfer with very large user IDs
    CreateGroup.InteractionTransfer interactionTransfer = new CreateGroup.InteractionTransfer();
    List<Long> userIds = Arrays.asList(9999999999999999L, 8888888888888888L, 7777777777777777L);
    interactionTransfer.setUserIds(userIds);

    // When: toInteractionTransfer is called
    GroupInteractionTransfer result = CreateGroupExecutor.toInteractionTransfer(interactionTransfer);

    // Then: Should handle very large values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).containsExactly(9999999999999999L, 8888888888888888L, 7777777777777777L);
  }
}
