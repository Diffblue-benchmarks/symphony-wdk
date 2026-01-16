package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.gen.api.model.GroupVisibilityRestriction;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CreateGroupExecutorClaude_toVisibilityRestrictionTest {

  @Test
  void toVisibilityRestriction_withNullInput_shouldReturnNull() {
    // When: toVisibilityRestriction is called with null
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(null);

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void toVisibilityRestriction_withEmptyVisibilityRestriction_shouldReturnObjectWithNullFields() {
    // Given: Empty visibility restriction (all fields null)
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should return GroupVisibilityRestriction with null fields
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isNull();
    assertThat(result.getRestrictedUsersList()).isNull();
  }

  @Test
  void toVisibilityRestriction_withTenantIdsOnly_shouldMapTenantIdsCorrectly() {
    // Given: Visibility restriction with only tenant IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map tenant IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getRestrictedUsersList()).isNull();
  }

  @Test
  void toVisibilityRestriction_withUserIdsOnly_shouldMapUserIdsCorrectly() {
    // Given: Visibility restriction with only user IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Arrays.asList(111111111L, 222222222L, 333333333L);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map user IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isNull();
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toVisibilityRestriction_withBothTenantIdsAndUserIds_shouldMapBothCorrectly() {
    // Given: Visibility restriction with both tenant IDs and user IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Arrays.asList(111111111L, 222222222L, 333333333L);
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map both fields correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toVisibilityRestriction_withEmptyTenantIdsList_shouldMapEmptyList() {
    // Given: Visibility restriction with empty tenant IDs list
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Collections.emptyList();
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map empty list correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEmpty();
  }

  @Test
  void toVisibilityRestriction_withEmptyUserIdsList_shouldMapEmptyList() {
    // Given: Visibility restriction with empty user IDs list
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Collections.emptyList();
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map empty list correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).isEmpty();
  }

  @Test
  void toVisibilityRestriction_withSingleTenantId_shouldMapCorrectly() {
    // Given: Visibility restriction with single tenant ID
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Collections.singletonList(42);
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map single tenant ID correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).hasSize(1);
    assertThat(result.getRestrictedTenantsList()).containsExactly(42);
  }

  @Test
  void toVisibilityRestriction_withSingleUserId_shouldMapCorrectly() {
    // Given: Visibility restriction with single user ID
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Collections.singletonList(987654321L);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map single user ID correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).hasSize(1);
    assertThat(result.getRestrictedUsersList()).containsExactly(987654321L);
  }

  @Test
  void toVisibilityRestriction_withMultipleTenantIds_shouldMapAllCorrectly() {
    // Given: Visibility restriction with multiple tenant IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map all tenant IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).hasSize(10);
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
  }

  @Test
  void toVisibilityRestriction_withMultipleUserIds_shouldMapAllCorrectly() {
    // Given: Visibility restriction with multiple user IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Arrays.asList(
        111111111L, 222222222L, 333333333L, 444444444L, 555555555L,
        666666666L, 777777777L, 888888888L, 999999999L, 1000000000L
    );
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map all user IDs correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).hasSize(10);
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toVisibilityRestriction_withDuplicateTenantIds_shouldMapAllDuplicates() {
    // Given: Visibility restriction with duplicate tenant IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(1, 1, 2, 2, 3, 3);
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map all duplicates
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).hasSize(6);
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
  }

  @Test
  void toVisibilityRestriction_withDuplicateUserIds_shouldMapAllDuplicates() {
    // Given: Visibility restriction with duplicate user IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Arrays.asList(111111111L, 111111111L, 222222222L, 222222222L);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map all duplicates
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).hasSize(4);
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toVisibilityRestriction_withZeroTenantId_shouldMapCorrectly() {
    // Given: Visibility restriction with zero tenant ID
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(0, 1, 2);
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map zero value correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(0, 1, 2);
  }

  @Test
  void toVisibilityRestriction_withZeroUserId_shouldMapCorrectly() {
    // Given: Visibility restriction with zero user ID
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Arrays.asList(0L, 111111111L, 222222222L);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map zero value correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).containsExactly(0L, 111111111L, 222222222L);
  }

  @Test
  void toVisibilityRestriction_withNegativeTenantIds_shouldMapCorrectly() {
    // Given: Visibility restriction with negative tenant IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(-1, -2, -3);
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map negative values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(-1, -2, -3);
  }

  @Test
  void toVisibilityRestriction_withNegativeUserIds_shouldMapCorrectly() {
    // Given: Visibility restriction with negative user IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Arrays.asList(-111111111L, -222222222L, -333333333L);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map negative values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).containsExactly(-111111111L, -222222222L, -333333333L);
  }

  @Test
  void toVisibilityRestriction_withMaxIntegerTenantId_shouldHandleCorrectly() {
    // Given: Visibility restriction with max integer value as tenant ID
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(Integer.MAX_VALUE, 1, Integer.MIN_VALUE);
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should handle max integer value correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(Integer.MAX_VALUE, 1, Integer.MIN_VALUE);
  }

  @Test
  void toVisibilityRestriction_withMaxLongUserId_shouldHandleCorrectly() {
    // Given: Visibility restriction with max long value as user ID
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Arrays.asList(Long.MAX_VALUE, 1L, Long.MIN_VALUE);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should handle max long value correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).containsExactly(Long.MAX_VALUE, 1L, Long.MIN_VALUE);
  }

  @Test
  void toVisibilityRestriction_shouldNotThrowException() {
    // Given: Valid visibility restriction
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(Arrays.asList(1, 2, 3));
    visibilityRestriction.setUserIds(Arrays.asList(111L, 222L, 333L));

    // When/Then: Should not throw any exception
    assertThatCode(() -> CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction))
        .doesNotThrowAnyException();
  }

  @Test
  void toVisibilityRestriction_calledMultipleTimes_shouldReturnConsistentResults() {
    // Given: Same visibility restriction
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called multiple times
    GroupVisibilityRestriction result1 = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);
    GroupVisibilityRestriction result2 = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should return consistent results (but different instances)
    assertThat(result1.getRestrictedTenantsList()).isEqualTo(result2.getRestrictedTenantsList());
    assertThat(result1.getRestrictedUsersList()).isEqualTo(result2.getRestrictedUsersList());
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void toVisibilityRestriction_withLargeLists_shouldHandleCorrectly() {
    // Given: Visibility restriction with large lists
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();

    List<Integer> tenantIds = new java.util.ArrayList<>();
    List<Long> userIds = new java.util.ArrayList<>();
    for (int i = 0; i < 100; i++) {
      tenantIds.add(i);
      userIds.add((long) i);
    }

    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should handle large lists correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).hasSize(100);
    assertThat(result.getRestrictedUsersList()).hasSize(100);
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toVisibilityRestriction_withMixedPositiveAndNegativeValues_shouldMapCorrectly() {
    // Given: Visibility restriction with mixed positive and negative values
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(-5, -1, 0, 1, 5);
    List<Long> userIds = Arrays.asList(-999999999L, -1L, 0L, 1L, 999999999L);
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map mixed values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(-5, -1, 0, 1, 5);
    assertThat(result.getRestrictedUsersList()).containsExactly(-999999999L, -1L, 0L, 1L, 999999999L);
  }

  @Test
  void toVisibilityRestriction_withOnlyTenantIdsEmptyUserIds_shouldMapCorrectly() {
    // Given: Visibility restriction with tenant IDs and empty user IDs list
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(1, 2, 3);
    List<Long> userIds = Collections.emptyList();
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map correctly with empty user IDs list
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEqualTo(tenantIds);
    assertThat(result.getRestrictedUsersList()).isEmpty();
  }

  @Test
  void toVisibilityRestriction_withEmptyTenantIdsOnlyUserIds_shouldMapCorrectly() {
    // Given: Visibility restriction with empty tenant IDs list and user IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Collections.emptyList();
    List<Long> userIds = Arrays.asList(111L, 222L, 333L);
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map correctly with empty tenant IDs list
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEmpty();
    assertThat(result.getRestrictedUsersList()).isEqualTo(userIds);
  }

  @Test
  void toVisibilityRestriction_withBothEmptyLists_shouldMapEmptyLists() {
    // Given: Visibility restriction with both empty lists
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Collections.emptyList();
    List<Long> userIds = Collections.emptyList();
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should map both empty lists correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).isEmpty();
    assertThat(result.getRestrictedUsersList()).isEmpty();
  }

  @Test
  void toVisibilityRestriction_withUnorderedIds_shouldPreserveOrder() {
    // Given: Visibility restriction with unordered IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(5, 1, 9, 2, 7);
    List<Long> userIds = Arrays.asList(999L, 111L, 555L, 333L, 777L);
    visibilityRestriction.setTenantIds(tenantIds);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should preserve the original order
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(5, 1, 9, 2, 7);
    assertThat(result.getRestrictedUsersList()).containsExactly(999L, 111L, 555L, 333L, 777L);
  }

  @Test
  void toVisibilityRestriction_shouldReturnNewInstanceEachTime() {
    // Given: Same visibility restriction
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    visibilityRestriction.setTenantIds(Arrays.asList(1, 2, 3));

    // When: toVisibilityRestriction is called multiple times
    GroupVisibilityRestriction result1 = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);
    GroupVisibilityRestriction result2 = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should return different instances
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void toVisibilityRestriction_withVeryLargeTenantIds_shouldHandleCorrectly() {
    // Given: Visibility restriction with very large tenant IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Integer> tenantIds = Arrays.asList(999999999, 888888888, 777777777);
    visibilityRestriction.setTenantIds(tenantIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should handle very large values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedTenantsList()).containsExactly(999999999, 888888888, 777777777);
  }

  @Test
  void toVisibilityRestriction_withVeryLargeUserIds_shouldHandleCorrectly() {
    // Given: Visibility restriction with very large user IDs
    CreateGroup.VisibilityRestriction visibilityRestriction = new CreateGroup.VisibilityRestriction();
    List<Long> userIds = Arrays.asList(9999999999999999L, 8888888888888888L, 7777777777777777L);
    visibilityRestriction.setUserIds(userIds);

    // When: toVisibilityRestriction is called
    GroupVisibilityRestriction result = CreateGroupExecutor.toVisibilityRestriction(visibilityRestriction);

    // Then: Should handle very large values correctly
    assertThat(result).isNotNull();
    assertThat(result.getRestrictedUsersList()).containsExactly(9999999999999999L, 8888888888888888L, 7777777777777777L);
  }
}
