package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.gen.api.model.Member;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CreateGroupExecutorClaude_toMembersTest {

  @Test
  void toMembers_withNullInput_shouldReturnNull() {
    // When: toMembers is called with null
    List<Member> result = CreateGroupExecutor.toMembers(null);

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void toMembers_withEmptyList_shouldReturnEmptyList() {
    // Given: Empty list of group members
    List<CreateGroup.GroupMember> emptyList = Collections.emptyList();

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(emptyList);

    // Then: Should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void toMembers_withSingleMember_shouldMapCorrectly() {
    // Given: A single group member
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(123456789L);
    groupMember.setTenantId(1);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should return list with one correctly mapped member
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isEqualTo(123456789L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(1);
  }

  @Test
  void toMembers_withMultipleMembers_shouldMapAllCorrectly() {
    // Given: Multiple group members
    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(111111111L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(222222222L);
    member2.setTenantId(2);

    CreateGroup.GroupMember member3 = new CreateGroup.GroupMember();
    member3.setUserId(333333333L);
    member3.setTenantId(3);

    List<CreateGroup.GroupMember> members = Arrays.asList(member1, member2, member3);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should return list with all members correctly mapped
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);

    assertThat(result.get(0).getMemberId()).isEqualTo(111111111L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(1);

    assertThat(result.get(1).getMemberId()).isEqualTo(222222222L);
    assertThat(result.get(1).getMemberTenant()).isEqualTo(2);

    assertThat(result.get(2).getMemberId()).isEqualTo(333333333L);
    assertThat(result.get(2).getMemberTenant()).isEqualTo(3);
  }

  @Test
  void toMembers_withNullUserId_shouldMapNullUserId() {
    // Given: A group member with null user ID
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(null);
    groupMember.setTenantId(1);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should map with null user ID
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isNull();
    assertThat(result.get(0).getMemberTenant()).isEqualTo(1);
  }

  @Test
  void toMembers_withNullTenantId_shouldMapNullTenantId() {
    // Given: A group member with null tenant ID
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(123456789L);
    groupMember.setTenantId(null);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should map with null tenant ID
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isEqualTo(123456789L);
    assertThat(result.get(0).getMemberTenant()).isNull();
  }

  @Test
  void toMembers_withBothNullValues_shouldMapBothNulls() {
    // Given: A group member with both null values
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(null);
    groupMember.setTenantId(null);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should map with both null values
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isNull();
    assertThat(result.get(0).getMemberTenant()).isNull();
  }

  @Test
  void toMembers_withMaxLongUserId_shouldHandleCorrectly() {
    // Given: A group member with max long value as user ID
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(Long.MAX_VALUE);
    groupMember.setTenantId(1);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should handle max long value correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isEqualTo(Long.MAX_VALUE);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(1);
  }

  @Test
  void toMembers_withMinLongUserId_shouldHandleCorrectly() {
    // Given: A group member with min long value as user ID
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(Long.MIN_VALUE);
    groupMember.setTenantId(1);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should handle min long value correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isEqualTo(Long.MIN_VALUE);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(1);
  }

  @Test
  void toMembers_withMaxIntegerTenantId_shouldHandleCorrectly() {
    // Given: A group member with max integer value as tenant ID
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(123456789L);
    groupMember.setTenantId(Integer.MAX_VALUE);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should handle max integer value correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isEqualTo(123456789L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(Integer.MAX_VALUE);
  }

  @Test
  void toMembers_withMinIntegerTenantId_shouldHandleCorrectly() {
    // Given: A group member with min integer value as tenant ID
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(123456789L);
    groupMember.setTenantId(Integer.MIN_VALUE);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should handle min integer value correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isEqualTo(123456789L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(Integer.MIN_VALUE);
  }

  @Test
  void toMembers_withDuplicateMembers_shouldMapAllDuplicates() {
    // Given: Multiple identical group members
    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(123456789L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(123456789L);
    member2.setTenantId(1);

    List<CreateGroup.GroupMember> members = Arrays.asList(member1, member2);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should map both duplicates
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getMemberId()).isEqualTo(123456789L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(1);
    assertThat(result.get(1).getMemberId()).isEqualTo(123456789L);
    assertThat(result.get(1).getMemberTenant()).isEqualTo(1);
  }

  @Test
  void toMembers_withMixedValidAndNullValues_shouldMapAllCorrectly() {
    // Given: Members with mixed valid and null values
    CreateGroup.GroupMember member1 = new CreateGroup.GroupMember();
    member1.setUserId(111111111L);
    member1.setTenantId(1);

    CreateGroup.GroupMember member2 = new CreateGroup.GroupMember();
    member2.setUserId(null);
    member2.setTenantId(2);

    CreateGroup.GroupMember member3 = new CreateGroup.GroupMember();
    member3.setUserId(333333333L);
    member3.setTenantId(null);

    CreateGroup.GroupMember member4 = new CreateGroup.GroupMember();
    member4.setUserId(null);
    member4.setTenantId(null);

    List<CreateGroup.GroupMember> members = Arrays.asList(member1, member2, member3, member4);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should map all members correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(4);

    assertThat(result.get(0).getMemberId()).isEqualTo(111111111L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(1);

    assertThat(result.get(1).getMemberId()).isNull();
    assertThat(result.get(1).getMemberTenant()).isEqualTo(2);

    assertThat(result.get(2).getMemberId()).isEqualTo(333333333L);
    assertThat(result.get(2).getMemberTenant()).isNull();

    assertThat(result.get(3).getMemberId()).isNull();
    assertThat(result.get(3).getMemberTenant()).isNull();
  }

  @Test
  void toMembers_shouldNotThrowException() {
    // Given: Valid input
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(123456789L);
    groupMember.setTenantId(1);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When/Then: Should not throw any exception
    assertThatCode(() -> CreateGroupExecutor.toMembers(members))
        .doesNotThrowAnyException();
  }

  @Test
  void toMembers_calledMultipleTimes_shouldReturnConsistentResults() {
    // Given: Same input
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(123456789L);
    groupMember.setTenantId(1);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called multiple times
    List<Member> result1 = CreateGroupExecutor.toMembers(members);
    List<Member> result2 = CreateGroupExecutor.toMembers(members);

    // Then: Should return consistent results
    assertThat(result1).hasSize(1);
    assertThat(result2).hasSize(1);
    assertThat(result1.get(0).getMemberId()).isEqualTo(result2.get(0).getMemberId());
    assertThat(result1.get(0).getMemberTenant()).isEqualTo(result2.get(0).getMemberTenant());
  }

  @Test
  void toMembers_shouldReturnNewListInstance() {
    // Given: Input list
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(123456789L);
    groupMember.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should return a new list instance (not the same reference)
    assertThat(result).isNotNull();
    assertThat(result).isNotSameAs(members);
  }

  @Test
  void toMembers_withMutableList_shouldNotAffectOriginalList() {
    // Given: Mutable input list
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(123456789L);
    groupMember.setTenantId(1);

    List<CreateGroup.GroupMember> members = new ArrayList<>();
    members.add(groupMember);
    int originalSize = members.size();

    // When: toMembers is called and result is modified
    List<Member> result = CreateGroupExecutor.toMembers(members);
    result.clear();

    // Then: Original list should not be affected
    assertThat(members).hasSize(originalSize);
    assertThat(members.get(0)).isSameAs(groupMember);
  }

  @Test
  void toMembers_withZeroValues_shouldHandleCorrectly() {
    // Given: A group member with zero values
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(0L);
    groupMember.setTenantId(0);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should handle zero values correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isEqualTo(0L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(0);
  }

  @Test
  void toMembers_withNegativeValues_shouldHandleCorrectly() {
    // Given: A group member with negative values
    CreateGroup.GroupMember groupMember = new CreateGroup.GroupMember();
    groupMember.setUserId(-123456789L);
    groupMember.setTenantId(-1);

    List<CreateGroup.GroupMember> members = Collections.singletonList(groupMember);

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should handle negative values correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getMemberId()).isEqualTo(-123456789L);
    assertThat(result.get(0).getMemberTenant()).isEqualTo(-1);
  }

  @Test
  void toMembers_withLargeList_shouldHandleCorrectly() {
    // Given: A large list of group members
    List<CreateGroup.GroupMember> members = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      CreateGroup.GroupMember member = new CreateGroup.GroupMember();
      member.setUserId((long) i);
      member.setTenantId(i);
      members.add(member);
    }

    // When: toMembers is called
    List<Member> result = CreateGroupExecutor.toMembers(members);

    // Then: Should handle large list correctly
    assertThat(result).isNotNull();
    assertThat(result).hasSize(100);
    for (int i = 0; i < 100; i++) {
      assertThat(result.get(i).getMemberId()).isEqualTo((long) i);
      assertThat(result.get(i).getMemberTenant()).isEqualTo(i);
    }
  }
}
