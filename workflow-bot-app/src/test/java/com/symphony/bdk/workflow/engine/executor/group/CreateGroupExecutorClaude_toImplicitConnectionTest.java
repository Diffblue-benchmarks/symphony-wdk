package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.gen.api.model.GroupImplicitConnection;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CreateGroupExecutorClaude_toImplicitConnectionTest {

  @Test
  void toImplicitConnection_withNullInput_shouldReturnNull() {
    // When: toImplicitConnection is called with null
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(null);

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void toImplicitConnection_withEmptyImplicitConnection_shouldReturnObjectWithNullFields() {
    // Given: Empty implicit connection (all fields null)
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should return GroupImplicitConnection with null fields
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).isNull();
    assertThat(result.getConnectedUsersList()).isNull();
  }

  @Test
  void toImplicitConnection_withTenantIdsOnly_shouldMapTenantIdsCorrectly() {
    // Given: Implicit connection with only tenant IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map tenant IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getConnectedUsersList()).isNull();
  }

  @Test
  void toImplicitConnection_withUserIdsOnly_shouldMapUserIdsCorrectly() {
    // Given: Implicit connection with only user IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Arrays.asList(111111111L, 222222222L, 333333333L);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map user IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).isNull();
    assertThat(result.getConnectedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toImplicitConnection_withBothTenantIdsAndUserIds_shouldMapBothCorrectly() {
    // Given: Implicit connection with both tenant IDs and user IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Arrays.asList(111111111L, 222222222L, 333333333L);
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map both fields correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getConnectedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toImplicitConnection_withEmptyTenantIdsList_shouldMapEmptyList() {
    // Given: Implicit connection with empty tenant IDs list
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Collections.emptyList();
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map empty list correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).isEmpty();
  }

  @Test
  void toImplicitConnection_withEmptyUserIdsList_shouldMapEmptyList() {
    // Given: Implicit connection with empty user IDs list
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Collections.emptyList();
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map empty list correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedUsersList()).isEmpty();
  }

  @Test
  void toImplicitConnection_withSingleTenantId_shouldMapCorrectly() {
    // Given: Implicit connection with single tenant ID
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Collections.singletonList(42);
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map single tenant ID correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).hasSize(1);
    assertThat(result.getConnectedTenantsList()).containsExactly(42);
  }

  @Test
  void toImplicitConnection_withSingleUserId_shouldMapCorrectly() {
    // Given: Implicit connection with single user ID
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Collections.singletonList(987654321L);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map single user ID correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedUsersList()).hasSize(1);
    assertThat(result.getConnectedUsersList()).containsExactly(987654321L);
  }

  @Test
  void toImplicitConnection_withMultipleTenantIds_shouldMapAllCorrectly() {
    // Given: Implicit connection with multiple tenant IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map all tenant IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).hasSize(10);
    assertThat(result.getConnectedTenantsList()).isEqualTo(tenantIds);
  }

  @Test
  void toImplicitConnection_withMultipleUserIds_shouldMapAllCorrectly() {
    // Given: Implicit connection with multiple user IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Arrays.asList(
        111111111L, 222222222L, 333333333L, 444444444L, 555555555L,
        666666666L, 777777777L, 888888888L, 999999999L, 1000000000L
    );
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map all user IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedUsersList()).hasSize(10);
    assertThat(result.getConnectedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toImplicitConnection_withDuplicateTenantIds_shouldMapAllDuplicates() {
    // Given: Implicit connection with duplicate tenant IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(1, 1, 2, 2, 3, 3);
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map all duplicates
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).hasSize(6);
    assertThat(result.getConnectedTenantsList()).isEqualTo(tenantIds);
  }

  @Test
  void toImplicitConnection_withDuplicateUserIds_shouldMapAllDuplicates() {
    // Given: Implicit connection with duplicate user IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Arrays.asList(111111111L, 111111111L, 222222222L, 222222222L);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map all duplicates
    assertThat(result).isNotNull();
    assertThat(result.getConnectedUsersList()).hasSize(4);
    assertThat(result.getConnectedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toImplicitConnection_withZeroTenantId_shouldMapCorrectly() {
    // Given: Implicit connection with zero tenant ID
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(0, 1, 2);
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map zero value correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).containsExactly(0, 1, 2);
  }

  @Test
  void toImplicitConnection_withZeroUserId_shouldMapCorrectly() {
    // Given: Implicit connection with zero user ID
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Arrays.asList(0L, 111111111L, 222222222L);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map zero value correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedUsersList()).containsExactly(0L, 111111111L, 222222222L);
  }

  @Test
  void toImplicitConnection_withNegativeTenantIds_shouldMapCorrectly() {
    // Given: Implicit connection with negative tenant IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(-1, -2, -3);
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map negative values correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).containsExactly(-1, -2, -3);
  }

  @Test
  void toImplicitConnection_withNegativeUserIds_shouldMapCorrectly() {
    // Given: Implicit connection with negative user IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Arrays.asList(-111111111L, -222222222L, -333333333L);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map negative values correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedUsersList()).containsExactly(-111111111L, -222222222L, -333333333L);
  }

  @Test
  void toImplicitConnection_withMaxIntegerTenantId_shouldHandleCorrectly() {
    // Given: Implicit connection with max integer value as tenant ID
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(Integer.MAX_VALUE, 1, Integer.MIN_VALUE);
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should handle max integer value correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).containsExactly(Integer.MAX_VALUE, 1, Integer.MIN_VALUE);
  }

  @Test
  void toImplicitConnection_withMaxLongUserId_shouldHandleCorrectly() {
    // Given: Implicit connection with max long value as user ID
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Arrays.asList(Long.MAX_VALUE, 1L, Long.MIN_VALUE);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should handle max long value correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedUsersList()).containsExactly(Long.MAX_VALUE, 1L, Long.MIN_VALUE);
  }

  @Test
  void toImplicitConnection_shouldNotThrowException() {
    // Given: Valid implicit connection
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(Arrays.asList(1, 2, 3));
    implicitConnection.setUserIds(Arrays.asList(111L, 222L, 333L));

    // When/Then: Should not throw any exception
    assertThatCode(() -> CreateGroupExecutor.toImplicitConnection(implicitConnection))
        .doesNotThrowAnyException();
  }

  @Test
  void toImplicitConnection_calledMultipleTimes_shouldReturnConsistentResults() {
    // Given: Same implicit connection
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called multiple times
    GroupImplicitConnection result1 = CreateGroupExecutor.toImplicitConnection(implicitConnection);
    GroupImplicitConnection result2 = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should return consistent results (but different instances)
    assertThat(result1.getConnectedTenantsList()).isEqualTo(result2.getConnectedTenantsList());
    assertThat(result1.getConnectedUsersList()).isEqualTo(result2.getConnectedUsersList());
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void toImplicitConnection_withLargeLists_shouldHandleCorrectly() {
    // Given: Implicit connection with large lists
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();

    List<Integer> tenantIds = new java.util.ArrayList<>();
    List<Long> userIds = new java.util.ArrayList<>();
    for (int i = 0; i < 100; i++) {
      tenantIds.add(i);
      userIds.add((long) i);
    }

    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should handle large lists correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).hasSize(100);
    assertThat(result.getConnectedUsersList()).hasSize(100);
    assertThat(result.getConnectedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getConnectedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toImplicitConnection_withMixedPositiveAndNegativeValues_shouldMapCorrectly() {
    // Given: Implicit connection with mixed positive and negative values
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(-5, -1, 0, 1, 5);
    List<Long> userIds = Arrays.asList(-999999999L, -1L, 0L, 1L, 999999999L);
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map mixed values correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).containsExactly(-5, -1, 0, 1, 5);
    assertThat(result.getConnectedUsersList()).containsExactly(-999999999L, -1L, 0L, 1L, 999999999L);
  }

  @Test
  void toImplicitConnection_withOnlyTenantIdsEmptyUserIds_shouldMapCorrectly() {
    // Given: Implicit connection with tenant IDs and empty user IDs list
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Collections.emptyList();
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map correctly with empty user IDs list
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getConnectedUsersList()).isEmpty();
  }

  @Test
  void toImplicitConnection_withEmptyTenantIdsOnlyUserIds_shouldMapCorrectly() {
    // Given: Implicit connection with empty tenant IDs list and user IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Collections.emptyList();
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map correctly with empty tenant IDs list
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).isEmpty();
    assertThat(result.getConnectedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toImplicitConnection_withBothEmptyLists_shouldMapEmptyLists() {
    // Given: Implicit connection with both empty lists
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Collections.emptyList();
    List<Long> userIds = Collections.emptyList();
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should map both empty lists correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).isEmpty();
    assertThat(result.getConnectedUsersList()).isEmpty();
  }

  @Test
  void toImplicitConnection_withUnorderedIds_shouldPreserveOrder() {
    // Given: Implicit connection with unordered IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(5, 1, 9, 2, 7);
    List<Long> userIds = Arrays.asList(999L, 111L, 555L, 333L, 777L);
    implicitConnection.setTenantIds(tenantIds);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should preserve the original order
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).containsExactly(5, 1, 9, 2, 7);
    assertThat(result.getConnectedUsersList()).containsExactly(999L, 111L, 555L, 333L, 777L);
  }

  @Test
  void toImplicitConnection_shouldReturnNewInstanceEachTime() {
    // Given: Same implicit connection
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    implicitConnection.setTenantIds(Arrays.asList(1, 2, 3));

    // When: toImplicitConnection is called multiple times
    GroupImplicitConnection result1 = CreateGroupExecutor.toImplicitConnection(implicitConnection);
    GroupImplicitConnection result2 = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should return different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void toImplicitConnection_withVeryLargeTenantIds_shouldHandleCorrectly() {
    // Given: Implicit connection with very large tenant IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Integer> tenantIds = Arrays.asList(999999999, 888888888, 777777777);
    implicitConnection.setTenantIds(tenantIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should handle very large values correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedTenantsList()).containsExactly(999999999, 888888888, 777777777);
  }

  @Test
  void toImplicitConnection_withVeryLargeUserIds_shouldHandleCorrectly() {
    // Given: Implicit connection with very large user IDs
    CreateGroup.ImplicitConnection implicitConnection = new CreateGroup.ImplicitConnection();
    List<Long> userIds = Arrays.asList(9999999999999999L, 8888888888888888L, 7777777777777777L);
    implicitConnection.setUserIds(userIds);

    // When: toImplicitConnection is called
    GroupImplicitConnection result = CreateGroupExecutor.toImplicitConnection(implicitConnection);

    // Then: Should handle very large values correctly
    assertThat(result).isNotNull();
    assertThat(result.getConnectedUsersList()).containsExactly(9999999999999999L, 8888888888888888L, 7777777777777777L);
  }
}
