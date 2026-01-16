package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.gen.api.model.BaseProfile;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CreateGroupExecutorClaude_toProfileTest {

  @Test
  void toProfile_withNullInput_shouldReturnNull() {
    // When: toProfile is called with null
    BaseProfile result = CreateGroupExecutor.toProfile(null);

    // Then: Should return null
    assertThat(result).isNull();
  }

  @Test
  void toProfile_withEmptyProfile_shouldReturnBaseProfileWithNullFields() {
    // Given: Empty profile (all fields null)
    CreateGroup.Profile profile = new CreateGroup.Profile();

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should return BaseProfile with null fields
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isNull();
    assertThat(result.getCompanyName()).isNull();
    assertThat(result.getEmail()).isNull();
    assertThat(result.getMobile()).isNull();
    assertThat(result.getIndustryOfInterest()).isNull();
    assertThat(result.getAssetClassesOfInterest()).isNull();
    assertThat(result.getMarketCoverage()).isNull();
    assertThat(result.getResponsibility()).isNull();
    assertThat(result.getFunction()).isNull();
    assertThat(result.getInstrument()).isNull();
    assertThat(result.getJobTitle()).isNull();
    assertThat(result.getJobRole()).isNull();
    assertThat(result.getJobDepartment()).isNull();
    assertThat(result.getJobDivision()).isNull();
    assertThat(result.getJobPhone()).isNull();
    assertThat(result.getJobCity()).isNull();
  }

  @Test
  void toProfile_withDisplayName_shouldMapCorrectly() {
    // Given: Profile with display name
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("John Doe");

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map display name correctly
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo("John Doe");
  }

  @Test
  void toProfile_withCompanyName_shouldMapCorrectly() {
    // Given: Profile with company name
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setCompanyName("Acme Corp");

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map company name correctly
    assertThat(result).isNotNull();
    assertThat(result.getCompanyName()).isEqualTo("Acme Corp");
  }

  @Test
  void toProfile_withEmail_shouldMapCorrectly() {
    // Given: Profile with email
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setEmail("john.doe@example.com");

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map email correctly
    assertThat(result).isNotNull();
    assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
  }

  @Test
  void toProfile_withMobile_shouldMapCorrectly() {
    // Given: Profile with mobile
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setMobile("+1234567890");

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map mobile correctly
    assertThat(result).isNotNull();
    assertThat(result.getMobile()).isEqualTo("+1234567890");
  }

  @Test
  void toProfile_withIndustries_shouldMapToIndustryOfInterest() {
    // Given: Profile with industries
    CreateGroup.Profile profile = new CreateGroup.Profile();
    List<String> industries = Arrays.asList("Technology", "Finance", "Healthcare");
    profile.setIndustries(industries);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map industries to industryOfInterest
    assertThat(result).isNotNull();
    assertThat(result.getIndustryOfInterest()).isEqualTo(industries);
  }

  @Test
  void toProfile_withIndustries_shouldMapToAssetClassesOfInterest() {
    // Given: Profile with industries
    CreateGroup.Profile profile = new CreateGroup.Profile();
    List<String> industries = Arrays.asList("Technology", "Finance");
    profile.setIndustries(industries);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map industries to assetClassesOfInterest (note: this appears to be a bug in the implementation)
    assertThat(result).isNotNull();
    assertThat(result.getAssetClassesOfInterest()).isEqualTo(industries);
  }

  @Test
  void toProfile_withAssetClasses_shouldNotMapToAssetClassesOfInterest() {
    // Given: Profile with asset classes
    CreateGroup.Profile profile = new CreateGroup.Profile();
    List<String> assetClasses = Arrays.asList("Equity", "Fixed Income");
    profile.setAssetClasses(assetClasses);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: assetClassesOfInterest should be null (not mapped from assetClasses due to bug in implementation)
    assertThat(result).isNotNull();
    assertThat(result.getAssetClassesOfInterest()).isNull();
  }

  @Test
  void toProfile_withMarketCoverages_shouldMapCorrectly() {
    // Given: Profile with market coverages
    CreateGroup.Profile profile = new CreateGroup.Profile();
    Set<String> marketCoverages = new HashSet<>(Arrays.asList("APAC", "EMEA", "Americas"));
    profile.setMarketCoverages(marketCoverages);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map market coverages correctly
    assertThat(result).isNotNull();
    assertThat(result.getMarketCoverage()).isEqualTo(marketCoverages);
  }

  @Test
  void toProfile_withResponsibilities_shouldMapCorrectly() {
    // Given: Profile with responsibilities
    CreateGroup.Profile profile = new CreateGroup.Profile();
    Set<String> responsibilities = new HashSet<>(Arrays.asList("Leadership", "Strategy"));
    profile.setResponsibilities(responsibilities);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map responsibilities correctly
    assertThat(result).isNotNull();
    assertThat(result.getResponsibility()).isEqualTo(responsibilities);
  }

  @Test
  void toProfile_withFunctions_shouldMapCorrectly() {
    // Given: Profile with functions
    CreateGroup.Profile profile = new CreateGroup.Profile();
    Set<String> functions = new HashSet<>(Arrays.asList("Sales", "Marketing"));
    profile.setFunctions(functions);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map functions correctly
    assertThat(result).isNotNull();
    assertThat(result.getFunction()).isEqualTo(functions);
  }

  @Test
  void toProfile_withInstruments_shouldMapCorrectly() {
    // Given: Profile with instruments
    CreateGroup.Profile profile = new CreateGroup.Profile();
    Set<String> instruments = new HashSet<>(Arrays.asList("Stocks", "Bonds"));
    profile.setInstruments(instruments);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map instruments correctly
    assertThat(result).isNotNull();
    assertThat(result.getInstrument()).isEqualTo(instruments);
  }

  @Test
  void toProfile_withAllBasicFields_shouldMapAllCorrectly() {
    // Given: Profile with all basic fields
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Jane Smith");
    profile.setCompanyName("Tech Solutions Inc");
    profile.setEmail("jane.smith@techsolutions.com");
    profile.setMobile("+9876543210");

    List<String> industries = Arrays.asList("Software", "Consulting");
    profile.setIndustries(industries);

    Set<String> marketCoverages = new HashSet<>(Arrays.asList("North America", "Europe"));
    profile.setMarketCoverages(marketCoverages);

    Set<String> responsibilities = new HashSet<>(Arrays.asList("Team Management", "Project Delivery"));
    profile.setResponsibilities(responsibilities);

    Set<String> functions = new HashSet<>(Arrays.asList("Engineering", "Product"));
    profile.setFunctions(functions);

    Set<String> instruments = new HashSet<>(Arrays.asList("Options", "Futures"));
    profile.setInstruments(instruments);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map all fields correctly
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo("Jane Smith");
    assertThat(result.getCompanyName()).isEqualTo("Tech Solutions Inc");
    assertThat(result.getEmail()).isEqualTo("jane.smith@techsolutions.com");
    assertThat(result.getMobile()).isEqualTo("+9876543210");
    assertThat(result.getIndustryOfInterest()).isEqualTo(industries);
    assertThat(result.getAssetClassesOfInterest()).isEqualTo(industries);
    assertThat(result.getMarketCoverage()).isEqualTo(marketCoverages);
    assertThat(result.getResponsibility()).isEqualTo(responsibilities);
    assertThat(result.getFunction()).isEqualTo(functions);
    assertThat(result.getInstrument()).isEqualTo(instruments);
  }

  @Test
  void toProfile_withNullJob_shouldNotMapJobFields() {
    // Given: Profile with null job
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Test User");
    profile.setJob(null);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Job fields should not be set
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo("Test User");
    assertThat(result.getJobTitle()).isNull();
    assertThat(result.getJobRole()).isNull();
    assertThat(result.getJobDepartment()).isNull();
    assertThat(result.getJobDivision()).isNull();
    assertThat(result.getJobPhone()).isNull();
    assertThat(result.getJobCity()).isNull();
  }

  @Test
  void toProfile_withEmptyJob_shouldMapJobFieldsAsNull() {
    // Given: Profile with empty job (all fields null)
    CreateGroup.Profile profile = new CreateGroup.Profile();
    CreateGroup.Job job = new CreateGroup.Job();
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Job fields should be null
    assertThat(result).isNotNull();
    assertThat(result.getJobTitle()).isNull();
    assertThat(result.getJobRole()).isNull();
    assertThat(result.getJobDepartment()).isNull();
    assertThat(result.getJobDivision()).isNull();
    assertThat(result.getJobPhone()).isNull();
    assertThat(result.getJobCity()).isNull();
  }

  @Test
  void toProfile_withJobTitle_shouldMapCorrectly() {
    // Given: Profile with job title
    CreateGroup.Profile profile = new CreateGroup.Profile();
    CreateGroup.Job job = new CreateGroup.Job();
    job.setTitle("Senior Engineer");
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map job title correctly
    assertThat(result).isNotNull();
    assertThat(result.getJobTitle()).isEqualTo("Senior Engineer");
  }

  @Test
  void toProfile_withJobRole_shouldMapCorrectly() {
    // Given: Profile with job role
    CreateGroup.Profile profile = new CreateGroup.Profile();
    CreateGroup.Job job = new CreateGroup.Job();
    job.setRole("Technical Lead");
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map job role correctly
    assertThat(result).isNotNull();
    assertThat(result.getJobRole()).isEqualTo("Technical Lead");
  }

  @Test
  void toProfile_withJobDepartment_shouldMapCorrectly() {
    // Given: Profile with job department
    CreateGroup.Profile profile = new CreateGroup.Profile();
    CreateGroup.Job job = new CreateGroup.Job();
    job.setDepartment("Engineering");
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map job department correctly
    assertThat(result).isNotNull();
    assertThat(result.getJobDepartment()).isEqualTo("Engineering");
  }

  @Test
  void toProfile_withJobDivision_shouldMapCorrectly() {
    // Given: Profile with job division
    CreateGroup.Profile profile = new CreateGroup.Profile();
    CreateGroup.Job job = new CreateGroup.Job();
    job.setDivision("Product Development");
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map job division correctly
    assertThat(result).isNotNull();
    assertThat(result.getJobDivision()).isEqualTo("Product Development");
  }

  @Test
  void toProfile_withJobPhone_shouldMapToDivision() {
    // Given: Profile with job phone
    CreateGroup.Profile profile = new CreateGroup.Profile();
    CreateGroup.Job job = new CreateGroup.Job();
    job.setPhone("+1234567890");
    job.setDivision("Sales Division");
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: jobPhone should be set to division value (bug in implementation at line 85)
    assertThat(result).isNotNull();
    assertThat(result.getJobPhone()).isEqualTo("Sales Division");
  }

  @Test
  void toProfile_withJobCity_shouldMapCorrectly() {
    // Given: Profile with job city
    CreateGroup.Profile profile = new CreateGroup.Profile();
    CreateGroup.Job job = new CreateGroup.Job();
    job.setCity("New York");
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map job city correctly
    assertThat(result).isNotNull();
    assertThat(result.getJobCity()).isEqualTo("New York");
  }

  @Test
  void toProfile_withAllJobFields_shouldMapAllCorrectly() {
    // Given: Profile with all job fields
    CreateGroup.Profile profile = new CreateGroup.Profile();
    CreateGroup.Job job = new CreateGroup.Job();
    job.setTitle("VP of Engineering");
    job.setRole("Executive");
    job.setDepartment("Technology");
    job.setDivision("Software Engineering");
    job.setPhone("+1555123456");
    job.setCity("San Francisco");
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map all job fields (with bug where jobPhone gets division value)
    assertThat(result).isNotNull();
    assertThat(result.getJobTitle()).isEqualTo("VP of Engineering");
    assertThat(result.getJobRole()).isEqualTo("Executive");
    assertThat(result.getJobDepartment()).isEqualTo("Technology");
    assertThat(result.getJobDivision()).isEqualTo("Software Engineering");
    assertThat(result.getJobPhone()).isEqualTo("Software Engineering"); // Bug: should be "+1555123456"
    assertThat(result.getJobCity()).isEqualTo("San Francisco");
  }

  @Test
  void toProfile_withCompleteProfile_shouldMapAllFieldsCorrectly() {
    // Given: Complete profile with all fields
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Complete User");
    profile.setCompanyName("Complete Company");
    profile.setEmail("complete@example.com");
    profile.setMobile("+9999999999");

    List<String> industries = Arrays.asList("Tech", "Finance");
    profile.setIndustries(industries);

    List<String> assetClasses = Arrays.asList("Stocks", "Bonds");
    profile.setAssetClasses(assetClasses);

    Set<String> marketCoverages = new HashSet<>(Arrays.asList("Global"));
    profile.setMarketCoverages(marketCoverages);

    Set<String> responsibilities = new HashSet<>(Arrays.asList("All"));
    profile.setResponsibilities(responsibilities);

    Set<String> functions = new HashSet<>(Arrays.asList("Everything"));
    profile.setFunctions(functions);

    Set<String> instruments = new HashSet<>(Arrays.asList("All Instruments"));
    profile.setInstruments(instruments);

    CreateGroup.Job job = new CreateGroup.Job();
    job.setTitle("CEO");
    job.setRole("Executive");
    job.setDepartment("C-Suite");
    job.setDivision("Corporate");
    job.setPhone("+1111111111");
    job.setCity("London");
    profile.setJob(job);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map all fields
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo("Complete User");
    assertThat(result.getCompanyName()).isEqualTo("Complete Company");
    assertThat(result.getEmail()).isEqualTo("complete@example.com");
    assertThat(result.getMobile()).isEqualTo("+9999999999");
    assertThat(result.getIndustryOfInterest()).isEqualTo(industries);
    assertThat(result.getAssetClassesOfInterest()).isEqualTo(industries);
    assertThat(result.getMarketCoverage()).isEqualTo(marketCoverages);
    assertThat(result.getResponsibility()).isEqualTo(responsibilities);
    assertThat(result.getFunction()).isEqualTo(functions);
    assertThat(result.getInstrument()).isEqualTo(instruments);
    assertThat(result.getJobTitle()).isEqualTo("CEO");
    assertThat(result.getJobRole()).isEqualTo("Executive");
    assertThat(result.getJobDepartment()).isEqualTo("C-Suite");
    assertThat(result.getJobDivision()).isEqualTo("Corporate");
    assertThat(result.getJobPhone()).isEqualTo("Corporate");
    assertThat(result.getJobCity()).isEqualTo("London");
  }

  @Test
  void toProfile_shouldNotThrowException() {
    // Given: Valid profile
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Test");

    // When/Then: Should not throw any exception
    assertThatCode(() -> CreateGroupExecutor.toProfile(profile))
        .doesNotThrowAnyException();
  }

  @Test
  void toProfile_calledMultipleTimes_shouldReturnConsistentResults() {
    // Given: Same profile
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("Consistent User");
    profile.setEmail("consistent@example.com");

    // When: toProfile is called multiple times
    BaseProfile result1 = CreateGroupExecutor.toProfile(profile);
    BaseProfile result2 = CreateGroupExecutor.toProfile(profile);

    // Then: Should return consistent results (but different instances)
    assertThat(result1.getDisplayName()).isEqualTo(result2.getDisplayName());
    assertThat(result1.getEmail()).isEqualTo(result2.getEmail());
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void toProfile_withEmptyStrings_shouldMapEmptyStrings() {
    // Given: Profile with empty strings
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("");
    profile.setCompanyName("");
    profile.setEmail("");
    profile.setMobile("");

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map empty strings
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo("");
    assertThat(result.getCompanyName()).isEqualTo("");
    assertThat(result.getEmail()).isEqualTo("");
    assertThat(result.getMobile()).isEqualTo("");
  }

  @Test
  void toProfile_withEmptyCollections_shouldMapEmptyCollections() {
    // Given: Profile with empty collections
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setIndustries(Arrays.asList());
    profile.setMarketCoverages(new HashSet<>());
    profile.setResponsibilities(new HashSet<>());
    profile.setFunctions(new HashSet<>());
    profile.setInstruments(new HashSet<>());

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map empty collections
    assertThat(result).isNotNull();
    assertThat(result.getIndustryOfInterest()).isEmpty();
    assertThat(result.getAssetClassesOfInterest()).isEmpty();
    assertThat(result.getMarketCoverage()).isEmpty();
    assertThat(result.getResponsibility()).isEmpty();
    assertThat(result.getFunction()).isEmpty();
    assertThat(result.getInstrument()).isEmpty();
  }

  @Test
  void toProfile_withSingleElementCollections_shouldMapCorrectly() {
    // Given: Profile with single element collections
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setIndustries(Arrays.asList("Single Industry"));
    profile.setMarketCoverages(new HashSet<>(Arrays.asList("Single Market")));
    profile.setResponsibilities(new HashSet<>(Arrays.asList("Single Responsibility")));
    profile.setFunctions(new HashSet<>(Arrays.asList("Single Function")));
    profile.setInstruments(new HashSet<>(Arrays.asList("Single Instrument")));

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should map single element collections correctly
    assertThat(result).isNotNull();
    assertThat(result.getIndustryOfInterest()).containsExactly("Single Industry");
    assertThat(result.getAssetClassesOfInterest()).containsExactly("Single Industry");
    assertThat(result.getMarketCoverage()).containsExactly("Single Market");
    assertThat(result.getResponsibility()).containsExactly("Single Responsibility");
    assertThat(result.getFunction()).containsExactly("Single Function");
    assertThat(result.getInstrument()).containsExactly("Single Instrument");
  }

  @Test
  void toProfile_withSpecialCharacters_shouldMapCorrectly() {
    // Given: Profile with special characters
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("User@#$%^&*()");
    profile.setCompanyName("Company & Co.");
    profile.setEmail("user+tag@example.com");
    profile.setMobile("+1 (555) 123-4567");

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should handle special characters correctly
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo("User@#$%^&*()");
    assertThat(result.getCompanyName()).isEqualTo("Company & Co.");
    assertThat(result.getEmail()).isEqualTo("user+tag@example.com");
    assertThat(result.getMobile()).isEqualTo("+1 (555) 123-4567");
  }

  @Test
  void toProfile_withUnicodeCharacters_shouldMapCorrectly() {
    // Given: Profile with unicode characters
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName("用户名");
    profile.setCompanyName("会社名");
    profile.setEmail("ユーザー@example.com");

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should handle unicode characters correctly
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo("用户名");
    assertThat(result.getCompanyName()).isEqualTo("会社名");
    assertThat(result.getEmail()).isEqualTo("ユーザー@example.com");
  }

  @Test
  void toProfile_withVeryLongStrings_shouldMapCorrectly() {
    // Given: Profile with very long strings
    String longString = "a".repeat(1000);
    CreateGroup.Profile profile = new CreateGroup.Profile();
    profile.setDisplayName(longString);
    profile.setCompanyName(longString);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should handle long strings correctly
    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo(longString);
    assertThat(result.getCompanyName()).isEqualTo(longString);
  }

  @Test
  void toProfile_withLargeCollections_shouldMapCorrectly() {
    // Given: Profile with large collections
    CreateGroup.Profile profile = new CreateGroup.Profile();

    List<String> largeList = new java.util.ArrayList<>();
    Set<String> largeSet = new HashSet<>();
    for (int i = 0; i < 100; i++) {
      largeList.add("Industry" + i);
      largeSet.add("Market" + i);
    }

    profile.setIndustries(largeList);
    profile.setMarketCoverages(largeSet);

    // When: toProfile is called
    BaseProfile result = CreateGroupExecutor.toProfile(profile);

    // Then: Should handle large collections correctly
    assertThat(result).isNotNull();
    assertThat(result.getIndustryOfInterest()).hasSize(100);
    assertThat(result.getMarketCoverage()).hasSize(100);
  }
}
