package com.symphony.bdk.workflow.engine.executor.user;

import com.symphony.bdk.gen.api.model.Feature;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserExecutorClaude_toFeaturesTest {

  @Test
  void toFeatures_withEmptyMap_shouldReturnEmptyList() {
    // Given: An empty entitlements map
    Map<String, Boolean> entitlements = new HashMap<>();

    // When: toFeatures is called
    List<Feature> result = CreateUserExecutor.toFeatures(entitlements);

    // Then: Should return an empty list
    assertThat(result).isEmpty();
  }

  @Test
  void toFeatures_withSingleEntitlementEnabled_shouldReturnSingleFeature() {
    // Given: A map with a single enabled entitlement
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);

    // When: toFeatures is called
    List<Feature> result = CreateUserExecutor.toFeatures(entitlements);

    // Then: Should return a list with one enabled feature
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getEntitlment()).isEqualTo("canCreatePublicRoom");
    assertThat(result.get(0).getEnabled()).isTrue();
  }

  @Test
  void toFeatures_withSingleEntitlementDisabled_shouldReturnSingleFeature() {
    // Given: A map with a single disabled entitlement
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("isExternalRoomEnabled", false);

    // When: toFeatures is called
    List<Feature> result = CreateUserExecutor.toFeatures(entitlements);

    // Then: Should return a list with one disabled feature
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getEntitlment()).isEqualTo("isExternalRoomEnabled");
    assertThat(result.get(0).getEnabled()).isFalse();
  }

  @Test
  void toFeatures_withMultipleEntitlements_shouldReturnAllFeatures() {
    // Given: A map with multiple entitlements
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("canCreatePublicRoom", true);
    entitlements.put("isExternalRoomEnabled", false);
    entitlements.put("canUpdateAvatar", true);

    // When: toFeatures is called
    List<Feature> result = CreateUserExecutor.toFeatures(entitlements);

    // Then: Should return a list with all features
    assertThat(result).hasSize(3);
    assertThat(result)
        .extracting(Feature::getEntitlment)
        .containsExactlyInAnyOrder("canCreatePublicRoom", "isExternalRoomEnabled", "canUpdateAvatar");
    assertThat(result)
        .filteredOn(f -> f.getEntitlment().equals("canCreatePublicRoom"))
        .extracting(Feature::getEnabled)
        .containsExactly(true);
    assertThat(result)
        .filteredOn(f -> f.getEntitlment().equals("isExternalRoomEnabled"))
        .extracting(Feature::getEnabled)
        .containsExactly(false);
    assertThat(result)
        .filteredOn(f -> f.getEntitlment().equals("canUpdateAvatar"))
        .extracting(Feature::getEnabled)
        .containsExactly(true);
  }

  @Test
  void toFeatures_withMixedEnabledAndDisabledEntitlements_shouldPreserveState() {
    // Given: A map with both enabled and disabled entitlements
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("feature1", true);
    entitlements.put("feature2", false);
    entitlements.put("feature3", true);
    entitlements.put("feature4", false);

    // When: toFeatures is called
    List<Feature> result = CreateUserExecutor.toFeatures(entitlements);

    // Then: Should return all features with correct enabled/disabled state
    assertThat(result).hasSize(4);

    long enabledCount = result.stream().filter(Feature::getEnabled).count();
    long disabledCount = result.stream().filter(f -> !f.getEnabled()).count();

    assertThat(enabledCount).isEqualTo(2);
    assertThat(disabledCount).isEqualTo(2);
  }

  @Test
  void toFeatures_withSpecialCharactersInEntitlementName_shouldHandleCorrectly() {
    // Given: A map with special characters in entitlement names
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("feature-with-dashes", true);
    entitlements.put("feature_with_underscores", false);
    entitlements.put("feature.with.dots", true);

    // When: toFeatures is called
    List<Feature> result = CreateUserExecutor.toFeatures(entitlements);

    // Then: Should handle special characters correctly
    assertThat(result).hasSize(3);
    assertThat(result)
        .extracting(Feature::getEntitlment)
        .containsExactlyInAnyOrder("feature-with-dashes", "feature_with_underscores", "feature.with.dots");
  }

  @Test
  void toFeatures_withMultipleCallsWithSameInput_shouldReturnConsistentResults() {
    // Given: A map with entitlements
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("feature1", true);
    entitlements.put("feature2", false);

    // When: toFeatures is called multiple times with same input
    List<Feature> result1 = CreateUserExecutor.toFeatures(entitlements);
    List<Feature> result2 = CreateUserExecutor.toFeatures(entitlements);

    // Then: Should return consistent results
    assertThat(result1).hasSize(2);
    assertThat(result2).hasSize(2);
    assertThat(result1).extracting(Feature::getEntitlment)
        .containsExactlyInAnyOrderElementsOf(
            result2.stream().map(Feature::getEntitlment).collect(Collectors.toList()));
  }

  @Test
  void toFeatures_doesNotModifyInputMap() {
    // Given: A map with entitlements
    Map<String, Boolean> entitlements = new HashMap<>();
    entitlements.put("feature1", true);
    entitlements.put("feature2", false);
    Map<String, Boolean> originalCopy = new HashMap<>(entitlements);

    // When: toFeatures is called
    CreateUserExecutor.toFeatures(entitlements);

    // Then: Input map should not be modified
    assertThat(entitlements).isEqualTo(originalCopy);
  }

  @Test
  void toFeatures_withLargeNumberOfEntitlements_shouldHandleCorrectly() {
    // Given: A map with many entitlements
    Map<String, Boolean> entitlements = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      entitlements.put("feature" + i, i % 2 == 0);
    }

    // When: toFeatures is called
    List<Feature> result = CreateUserExecutor.toFeatures(entitlements);

    // Then: Should return all features
    assertThat(result).hasSize(100);
    assertThat(result)
        .extracting(Feature::getEntitlment)
        .allMatch(name -> name.startsWith("feature"));
  }
}
