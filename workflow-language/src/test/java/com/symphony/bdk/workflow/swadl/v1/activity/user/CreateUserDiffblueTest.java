package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Business;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Contact;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Key;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Keys;
import com.symphony.bdk.workflow.swadl.v1.activity.user.CreateUser.Password;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityCompletedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityFailedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ConnectionAcceptedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ConnectionRequestedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ImCreatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.MessageSuppressedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.PostSharedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomCreatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomDeactivatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomMemberDemotedFromOwnerEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomMemberPromotedToOwnerEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomReactivatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomUpdatedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserJoinedRoomEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserLeftRoomEvent;
import com.symphony.bdk.workflow.swadl.v1.event.UserRequestedToJoinRoomEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateUserDiffblueTest {
  /**
   * Test Business {@link Business#equals(Object)}, and {@link Business#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Business#equals(Object)}
   *   <li>{@link Business#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Business equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertEquals(business, business2);
    int expectedHashCodeResult = business.hashCode();
    assertEquals(expectedHashCodeResult, business2.hashCode());
  }

  /**
   * Test Business {@link Business#equals(Object)}, and {@link Business#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Business#equals(Object)}
   *   <li>{@link Business#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Business equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    // Act and Assert
    assertEquals(business, business);
    int expectedHashCodeResult = business.hashCode();
    assertEquals(expectedHashCodeResult, business.hashCode());
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> assetClasses = new ArrayList<>();
    assetClasses.add("Company Name");

    Business business = new Business();
    business.setAssetClasses(assetClasses);
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Department");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName(null);
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Company Name");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment(null);
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Company Name");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision(null);
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    ArrayList<String> functions = new ArrayList<>();
    functions.add("Company Name");

    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(functions);
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ArrayList<String> industries = new ArrayList<>();
    industries.add("Company Name");

    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(industries);
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    ArrayList<String> instruments = new ArrayList<>();
    instruments.add("Company Name");

    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(instruments);
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Company Name");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction(null);
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Company Name");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation(null);
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    ArrayList<String> marketCoverages = new ArrayList<>();
    marketCoverages.add("Company Name");

    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(marketCoverages);
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    ArrayList<String> responsibilities = new ArrayList<>();
    responsibilities.add("Company Name");

    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(responsibilities);
    business.setTitle("Dr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Mr");

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle(null);

    Business business2 = new Business();
    business2.setAssetClasses(new ArrayList<>());
    business2.setCompanyName("Company Name");
    business2.setDepartment("Department");
    business2.setDivision("Division");
    business2.setFunctions(new ArrayList<>());
    business2.setIndustries(new ArrayList<>());
    business2.setInstruments(new ArrayList<>());
    business2.setJobFunction("Job Function");
    business2.setLocation("Location");
    business2.setMarketCoverages(new ArrayList<>());
    business2.setResponsibilities(new ArrayList<>());
    business2.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, business2);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, null);
  }

  /**
   * Test Business {@link Business#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Business#equals(Object)}
   */
  @Test
  @DisplayName("Test Business equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Business.equals(Object)", "int Business.hashCode()"})
  void testBusinessEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");

    // Act and Assert
    assertNotEquals(business, "Different type to Business");
  }

  /**
   * Test Business getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Business}
   *   <li>{@link Business#setAssetClasses(List)}
   *   <li>{@link Business#setCompanyName(String)}
   *   <li>{@link Business#setDepartment(String)}
   *   <li>{@link Business#setDivision(String)}
   *   <li>{@link Business#setFunctions(List)}
   *   <li>{@link Business#setIndustries(List)}
   *   <li>{@link Business#setInstruments(List)}
   *   <li>{@link Business#setJobFunction(String)}
   *   <li>{@link Business#setLocation(String)}
   *   <li>{@link Business#setMarketCoverages(List)}
   *   <li>{@link Business#setResponsibilities(List)}
   *   <li>{@link Business#setTitle(String)}
   *   <li>{@link Business#toString()}
   *   <li>{@link Business#getAssetClasses()}
   *   <li>{@link Business#getCompanyName()}
   *   <li>{@link Business#getDepartment()}
   *   <li>{@link Business#getDivision()}
   *   <li>{@link Business#getFunctions()}
   *   <li>{@link Business#getIndustries()}
   *   <li>{@link Business#getInstruments()}
   *   <li>{@link Business#getJobFunction()}
   *   <li>{@link Business#getLocation()}
   *   <li>{@link Business#getMarketCoverages()}
   *   <li>{@link Business#getResponsibilities()}
   *   <li>{@link Business#getTitle()}
   * </ul>
   */
  @Test
  @DisplayName("Test Business getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Business.<init>()", "List Business.getAssetClasses()", "String Business.getCompanyName()",
      "String Business.getDepartment()", "String Business.getDivision()", "List Business.getFunctions()",
      "List Business.getIndustries()", "List Business.getInstruments()", "String Business.getJobFunction()",
      "String Business.getLocation()", "List Business.getMarketCoverages()", "List Business.getResponsibilities()",
      "String Business.getTitle()", "void Business.setAssetClasses(List)", "void Business.setCompanyName(String)",
      "void Business.setDepartment(String)", "void Business.setDivision(String)", "void Business.setFunctions(List)",
      "void Business.setIndustries(List)", "void Business.setInstruments(List)", "void Business.setJobFunction(String)",
      "void Business.setLocation(String)", "void Business.setMarketCoverages(List)",
      "void Business.setResponsibilities(List)", "void Business.setTitle(String)", "String Business.toString()"})
  void testBusinessGettersAndSetters() {
    // Arrange and Act
    Business actualBusiness = new Business();
    ArrayList<String> assetClasses = new ArrayList<>();
    actualBusiness.setAssetClasses(assetClasses);
    actualBusiness.setCompanyName("Company Name");
    actualBusiness.setDepartment("Department");
    actualBusiness.setDivision("Division");
    ArrayList<String> functions = new ArrayList<>();
    actualBusiness.setFunctions(functions);
    ArrayList<String> industries = new ArrayList<>();
    actualBusiness.setIndustries(industries);
    ArrayList<String> instruments = new ArrayList<>();
    actualBusiness.setInstruments(instruments);
    actualBusiness.setJobFunction("Job Function");
    actualBusiness.setLocation("Location");
    ArrayList<String> marketCoverages = new ArrayList<>();
    actualBusiness.setMarketCoverages(marketCoverages);
    ArrayList<String> responsibilities = new ArrayList<>();
    actualBusiness.setResponsibilities(responsibilities);
    actualBusiness.setTitle("Dr");
    String actualToStringResult = actualBusiness.toString();
    List<String> actualAssetClasses = actualBusiness.getAssetClasses();
    String actualCompanyName = actualBusiness.getCompanyName();
    String actualDepartment = actualBusiness.getDepartment();
    String actualDivision = actualBusiness.getDivision();
    List<String> actualFunctions = actualBusiness.getFunctions();
    List<String> actualIndustries = actualBusiness.getIndustries();
    List<String> actualInstruments = actualBusiness.getInstruments();
    String actualJobFunction = actualBusiness.getJobFunction();
    String actualLocation = actualBusiness.getLocation();
    List<String> actualMarketCoverages = actualBusiness.getMarketCoverages();
    List<String> actualResponsibilities = actualBusiness.getResponsibilities();

    // Assert
    assertEquals("Company Name", actualCompanyName);
    assertEquals("CreateUser.Business(companyName=Company Name, department=Department, division=Division, title=Dr,"
        + " location=Location, jobFunction=Job Function, assetClasses=[], industries=[], functions=[],"
        + " marketCoverages=[], responsibilities=[], instruments=[])", actualToStringResult);
    assertEquals("Department", actualDepartment);
    assertEquals("Division", actualDivision);
    assertEquals("Dr", actualBusiness.getTitle());
    assertEquals("Job Function", actualJobFunction);
    assertEquals("Location", actualLocation);
    assertTrue(actualAssetClasses.isEmpty());
    assertTrue(actualFunctions.isEmpty());
    assertTrue(actualIndustries.isEmpty());
    assertTrue(actualInstruments.isEmpty());
    assertTrue(actualMarketCoverages.isEmpty());
    assertTrue(actualResponsibilities.isEmpty());
    assertSame(assetClasses, actualAssetClasses);
    assertSame(functions, actualFunctions);
    assertSame(industries, actualIndustries);
    assertSame(instruments, actualInstruments);
    assertSame(marketCoverages, actualMarketCoverages);
    assertSame(responsibilities, actualResponsibilities);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}, and {@link Contact#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Contact#equals(Object)}
   *   <li>{@link Contact#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Contact equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertEquals(contact, contact2);
    int expectedHashCodeResult = contact.hashCode();
    assertEquals(expectedHashCodeResult, contact2.hashCode());
  }

  /**
   * Test Contact {@link Contact#equals(Object)}, and {@link Contact#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Contact#equals(Object)}
   *   <li>{@link Contact#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Contact equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber(null);
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber(null);
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertEquals(contact, contact2);
    int expectedHashCodeResult = contact.hashCode();
    assertEquals(expectedHashCodeResult, contact2.hashCode());
  }

  /**
   * Test Contact {@link Contact#equals(Object)}, and {@link Contact#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Contact#equals(Object)}
   *   <li>{@link Contact#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Contact equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber(null);
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber(null);
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertEquals(contact, contact2);
    int expectedHashCodeResult = contact.hashCode();
    assertEquals(expectedHashCodeResult, contact2.hashCode());
  }

  /**
   * Test Contact {@link Contact#equals(Object)}, and {@link Contact#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Contact#equals(Object)}
   *   <li>{@link Contact#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Contact equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber(null);
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber(null);
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertEquals(contact, contact2);
    int expectedHashCodeResult = contact.hashCode();
    assertEquals(expectedHashCodeResult, contact2.hashCode());
  }

  /**
   * Test Contact {@link Contact#equals(Object)}, and {@link Contact#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Contact#equals(Object)}
   *   <li>{@link Contact#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Contact equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber(null);

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber(null);

    // Act and Assert
    assertEquals(contact, contact2);
    int expectedHashCodeResult = contact.hashCode();
    assertEquals(expectedHashCodeResult, contact2.hashCode());
  }

  /**
   * Test Contact {@link Contact#equals(Object)}, and {@link Contact#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Contact#equals(Object)}
   *   <li>{@link Contact#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Contact equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertEquals(contact, contact);
    int expectedHashCodeResult = contact.hashCode();
    assertEquals(expectedHashCodeResult, contact.hashCode());
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("8605550118");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber(null);
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("6625550144");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber(null);
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("6625550144");
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber(null);
    contact.setWorkPhoneNumber("6625550144");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("8605550118");

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber(null);

    Contact contact2 = new Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, null);
  }

  /**
   * Test Contact {@link Contact#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Contact#equals(Object)}
   */
  @Test
  @DisplayName("Test Contact equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Contact.equals(Object)", "int Contact.hashCode()"})
  void testContactEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, "Different type to Contact");
  }

  /**
   * Test Contact getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Contact}
   *   <li>{@link Contact#setMobilePhoneNumber(String)}
   *   <li>{@link Contact#setSmsNumber(String)}
   *   <li>{@link Contact#setTwoFactorAuthNumber(String)}
   *   <li>{@link Contact#setWorkPhoneNumber(String)}
   *   <li>{@link Contact#toString()}
   *   <li>{@link Contact#getMobilePhoneNumber()}
   *   <li>{@link Contact#getSmsNumber()}
   *   <li>{@link Contact#getTwoFactorAuthNumber()}
   *   <li>{@link Contact#getWorkPhoneNumber()}
   * </ul>
   */
  @Test
  @DisplayName("Test Contact getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Contact.<init>()", "String Contact.getMobilePhoneNumber()", "String Contact.getSmsNumber()",
      "String Contact.getTwoFactorAuthNumber()", "String Contact.getWorkPhoneNumber()",
      "void Contact.setMobilePhoneNumber(String)", "void Contact.setSmsNumber(String)",
      "void Contact.setTwoFactorAuthNumber(String)", "void Contact.setWorkPhoneNumber(String)",
      "String Contact.toString()"})
  void testContactGettersAndSetters() {
    // Arrange and Act
    Contact actualContact = new Contact();
    actualContact.setMobilePhoneNumber("6625550144");
    actualContact.setSmsNumber("42");
    actualContact.setTwoFactorAuthNumber("42");
    actualContact.setWorkPhoneNumber("6625550144");
    String actualToStringResult = actualContact.toString();
    String actualMobilePhoneNumber = actualContact.getMobilePhoneNumber();
    String actualSmsNumber = actualContact.getSmsNumber();
    String actualTwoFactorAuthNumber = actualContact.getTwoFactorAuthNumber();

    // Assert
    assertEquals("42", actualSmsNumber);
    assertEquals("42", actualTwoFactorAuthNumber);
    assertEquals("6625550144", actualMobilePhoneNumber);
    assertEquals("6625550144", actualContact.getWorkPhoneNumber());
    assertEquals("CreateUser.Contact(workPhoneNumber=6625550144, mobilePhoneNumber=6625550144, twoFactorAuthNumber=42,"
        + " smsNumber=42)", actualToStringResult);
  }

  /**
   * Test {@link CreateUser#equals(Object)}, and {@link CreateUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser#equals(Object)}
   *   <li>{@link CreateUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateUser.equals(Object)", "int CreateUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateUser createUser = new CreateUser();
    CreateUser createUser2 = new CreateUser();

    // Act and Assert
    assertEquals(createUser, createUser2);
    int expectedHashCodeResult = createUser.hashCode();
    assertEquals(expectedHashCodeResult, createUser2.hashCode());
  }

  /**
   * Test {@link CreateUser#equals(Object)}, and {@link CreateUser#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser#equals(Object)}
   *   <li>{@link CreateUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateUser.equals(Object)", "int CreateUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateUser createUser = new CreateUser();

    // Act and Assert
    assertEquals(createUser, createUser);
    int expectedHashCodeResult = createUser.hashCode();
    assertEquals(expectedHashCodeResult, createUser.hashCode());
  }

  /**
   * Test {@link CreateUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateUser.equals(Object)", "int CreateUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();

    // Act and Assert
    assertNotEquals(createSystemUser, new CreateUser());
  }

  /**
   * Test {@link CreateUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateUser.equals(Object)", "int CreateUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateUser createUser = new CreateUser();
    createUser.add("NORMAL", "Value");

    // Act and Assert
    assertNotEquals(createUser, new CreateUser());
  }

  /**
   * Test {@link CreateUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateUser.equals(Object)", "int CreateUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateUser createUser = new CreateUser();

    // Act and Assert
    assertNotEquals(createUser, new CreateSystemUser());
  }

  /**
   * Test {@link CreateUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateUser.equals(Object)", "int CreateUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateUser createUser = new CreateUser();

    ActivityCompletedEvent activityCompleted = new ActivityCompletedEvent();
    activityCompleted.setActivityId("42");
    activityCompleted.setId("42");
    activityCompleted.setIfCondition("If Condition");

    ActivityExpiredEvent activityExpired = new ActivityExpiredEvent();
    activityExpired.setActivityId("42");
    activityExpired.setId("42");

    ActivityFailedEvent activityFailed = new ActivityFailedEvent();
    activityFailed.setActivityId("42");
    activityFailed.setId("42");

    ConnectionAcceptedEvent connectionAccepted = new ConnectionAcceptedEvent();
    connectionAccepted.setId("42");

    ConnectionRequestedEvent connectionRequested = new ConnectionRequestedEvent();
    connectionRequested.setId("42");

    FormRepliedEvent formReplied = new FormRepliedEvent();
    formReplied.setExclusive(true);
    formReplied.setFormId("42");
    formReplied.setId("42");

    ImCreatedEvent imCreated = new ImCreatedEvent();
    imCreated.setId("42");

    MessageReceivedEvent messageReceived = new MessageReceivedEvent();
    messageReceived.setContent("Not all who wander are lost");
    messageReceived.setId("42");
    messageReceived.setRequiresBotMention(true);

    MessageSuppressedEvent messageSuppressed = new MessageSuppressedEvent();
    messageSuppressed.setId("42");

    PostSharedEvent postShared = new PostSharedEvent();
    postShared.setId("42");

    RequestReceivedEvent requestReceived = new RequestReceivedEvent();
    requestReceived.setArguments(new HashMap<>());
    requestReceived.setId("42");
    requestReceived.setToken("ABC123");
    requestReceived.setWorkflowId("42");

    RoomCreatedEvent roomCreated = new RoomCreatedEvent();
    roomCreated.setId("42");

    RoomDeactivatedEvent roomDeactivated = new RoomDeactivatedEvent();
    roomDeactivated.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwner = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwner.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwner = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwner.setId("42");

    RoomReactivatedEvent roomReactivated = new RoomReactivatedEvent();
    roomReactivated.setId("42");

    RoomUpdatedEvent roomUpdated = new RoomUpdatedEvent();
    roomUpdated.setId("42");

    TimerFiredEvent timerFired = new TimerFiredEvent();
    timerFired.setAt("At");
    timerFired.setId("42");
    timerFired.setRepeat("Repeat");

    UserJoinedRoomEvent userJoinedRoom = new UserJoinedRoomEvent();
    userJoinedRoom.setId("42");

    UserLeftRoomEvent userLeftRoom = new UserLeftRoomEvent();
    userLeftRoom.setId("42");

    UserRequestedToJoinRoomEvent userRequestedJoinRoom = new UserRequestedToJoinRoomEvent();
    userRequestedJoinRoom.setId("42");

    EventWithTimeout eventWithTimeout = new EventWithTimeout();
    eventWithTimeout.setActivityCompleted(activityCompleted);
    eventWithTimeout.setActivityExpired(activityExpired);
    eventWithTimeout.setActivityFailed(activityFailed);
    eventWithTimeout.setAllOf(new ArrayList<>());
    eventWithTimeout.setConnectionAccepted(connectionAccepted);
    eventWithTimeout.setConnectionRequested(connectionRequested);
    eventWithTimeout.setFormReplied(formReplied);
    eventWithTimeout.setImCreated(imCreated);
    eventWithTimeout.setMessageReceived(messageReceived);
    eventWithTimeout.setMessageSuppressed(messageSuppressed);
    eventWithTimeout.setOneOf(new ArrayList<>());
    eventWithTimeout.setPostShared(postShared);
    eventWithTimeout.setRequestReceived(requestReceived);
    eventWithTimeout.setRoomCreated(roomCreated);
    eventWithTimeout.setRoomDeactivated(roomDeactivated);
    eventWithTimeout.setRoomMemberDemotedFromOwner(roomMemberDemotedFromOwner);
    eventWithTimeout.setRoomMemberPromotedToOwner(roomMemberPromotedToOwner);
    eventWithTimeout.setRoomReactivated(roomReactivated);
    eventWithTimeout.setRoomUpdated(roomUpdated);
    eventWithTimeout.setTimeout("Timeout");
    eventWithTimeout.setTimerFired(timerFired);
    eventWithTimeout.setUserJoinedRoom(userJoinedRoom);
    eventWithTimeout.setUserLeftRoom(userLeftRoom);
    eventWithTimeout.setUserRequestedJoinRoom(userRequestedJoinRoom);
    CreateSystemUser createSystemUser = mock(CreateSystemUser.class);
    when(createSystemUser.getOn()).thenReturn(eventWithTimeout);
    when(createSystemUser.getElseCondition()).thenReturn("Else Condition");
    when(createSystemUser.getId()).thenReturn("42");
    when(createSystemUser.getIfCondition()).thenReturn("If Condition");
    when(createSystemUser.getType()).thenReturn("Type");
    when(createSystemUser.getVariableProperties()).thenReturn(new HashMap<>());
    when(createSystemUser.canEqual(Mockito.<Object>any())).thenReturn(true);

    // Act and Assert
    assertNotEquals(createUser, createSystemUser);
  }

  /**
   * Test {@link CreateUser#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateUser.equals(Object)", "int CreateUser.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateUser(), null);
  }

  /**
   * Test {@link CreateUser#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateUser.equals(Object)", "int CreateUser.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateUser(), "Different type to CreateUser");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateUser}
   *   <li>{@link CreateUser#setBusiness(Business)}
   *   <li>{@link CreateUser#setContact(Contact)}
   *   <li>{@link CreateUser#setDisplayName(String)}
   *   <li>{@link CreateUser#setEmail(String)}
   *   <li>{@link CreateUser#setEntitlements(Map)}
   *   <li>{@link CreateUser#setFirstname(String)}
   *   <li>{@link CreateUser#setKeys(Keys)}
   *   <li>{@link CreateUser#setLastname(String)}
   *   <li>{@link CreateUser#setPassword(Password)}
   *   <li>{@link CreateUser#setRecommendedLanguage(String)}
   *   <li>{@link CreateUser#setRoles(List)}
   *   <li>{@link CreateUser#setStatus(String)}
   *   <li>{@link CreateUser#setType(String)}
   *   <li>{@link CreateUser#setUsername(String)}
   *   <li>{@link CreateUser#toString()}
   *   <li>{@link CreateUser#getBusiness()}
   *   <li>{@link CreateUser#getContact()}
   *   <li>{@link CreateUser#getDisplayName()}
   *   <li>{@link CreateUser#getEmail()}
   *   <li>{@link CreateUser#getEntitlements()}
   *   <li>{@link CreateUser#getFirstname()}
   *   <li>{@link CreateUser#getKeys()}
   *   <li>{@link CreateUser#getLastname()}
   *   <li>{@link CreateUser#getPassword()}
   *   <li>{@link CreateUser#getRecommendedLanguage()}
   *   <li>{@link CreateUser#getRoles()}
   *   <li>{@link CreateUser#getStatus()}
   *   <li>{@link CreateUser#getType()}
   *   <li>{@link CreateUser#getUsername()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CreateUser.<init>()", "Business CreateUser.getBusiness()", "Contact CreateUser.getContact()",
      "String CreateUser.getDisplayName()", "String CreateUser.getEmail()", "Map CreateUser.getEntitlements()",
      "String CreateUser.getFirstname()", "Keys CreateUser.getKeys()", "String CreateUser.getLastname()",
      "Password CreateUser.getPassword()", "String CreateUser.getRecommendedLanguage()", "List CreateUser.getRoles()",
      "String CreateUser.getStatus()", "String CreateUser.getType()", "String CreateUser.getUsername()",
      "void CreateUser.setBusiness(Business)", "void CreateUser.setContact(Contact)",
      "void CreateUser.setDisplayName(String)", "void CreateUser.setEmail(String)",
      "void CreateUser.setEntitlements(Map)", "void CreateUser.setFirstname(String)", "void CreateUser.setKeys(Keys)",
      "void CreateUser.setLastname(String)", "void CreateUser.setPassword(Password)",
      "void CreateUser.setRecommendedLanguage(String)", "void CreateUser.setRoles(List)",
      "void CreateUser.setStatus(String)", "void CreateUser.setType(String)", "void CreateUser.setUsername(String)",
      "String CreateUser.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    CreateUser actualCreateUser = new CreateUser();
    Business business = new Business();
    business.setAssetClasses(new ArrayList<>());
    business.setCompanyName("Company Name");
    business.setDepartment("Department");
    business.setDivision("Division");
    business.setFunctions(new ArrayList<>());
    business.setIndustries(new ArrayList<>());
    business.setInstruments(new ArrayList<>());
    business.setJobFunction("Job Function");
    business.setLocation("Location");
    business.setMarketCoverages(new ArrayList<>());
    business.setResponsibilities(new ArrayList<>());
    business.setTitle("Dr");
    actualCreateUser.setBusiness(business);
    Contact contact = new Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");
    actualCreateUser.setContact(contact);
    actualCreateUser.setDisplayName("Display Name");
    actualCreateUser.setEmail("jane.doe@example.org");
    HashMap<String, Boolean> entitlements = new HashMap<>();
    actualCreateUser.setEntitlements(entitlements);
    actualCreateUser.setFirstname("Jane");
    Key current = new Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");
    Key previous = new Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");
    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);
    actualCreateUser.setKeys(keys);
    actualCreateUser.setLastname("Doe");
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");
    actualCreateUser.setPassword(password);
    actualCreateUser.setRecommendedLanguage("en");
    ArrayList<String> roles = new ArrayList<>();
    actualCreateUser.setRoles(roles);
    actualCreateUser.setStatus("Status");
    actualCreateUser.setType("Type");
    actualCreateUser.setUsername("janedoe");
    String actualToStringResult = actualCreateUser.toString();
    Business actualBusiness = actualCreateUser.getBusiness();
    Contact actualContact = actualCreateUser.getContact();
    String actualDisplayName = actualCreateUser.getDisplayName();
    String actualEmail = actualCreateUser.getEmail();
    Map<String, Boolean> actualEntitlements = actualCreateUser.getEntitlements();
    String actualFirstname = actualCreateUser.getFirstname();
    Keys actualKeys = actualCreateUser.getKeys();
    String actualLastname = actualCreateUser.getLastname();
    Password actualPassword = actualCreateUser.getPassword();
    String actualRecommendedLanguage = actualCreateUser.getRecommendedLanguage();
    List<String> actualRoles = actualCreateUser.getRoles();
    String actualStatus = actualCreateUser.getStatus();
    String actualType = actualCreateUser.getType();

    // Assert
    assertEquals("CreateUser(type=Type, email=jane.doe@example.org, username=janedoe, firstname=Jane, lastname=Doe,"
        + " displayName=Display Name, recommendedLanguage=en, contact=CreateUser.Contact(workPhoneNumber=6625550144,"
        + " mobilePhoneNumber=6625550144, twoFactorAuthNumber=42, smsNumber=42), business=CreateUser.Business"
        + "(companyName=Company Name, department=Department, division=Division, title=Dr, location=Location,"
        + " jobFunction=Job Function, assetClasses=[], industries=[], functions=[], marketCoverages=[],"
        + " responsibilities=[], instruments=[]), roles=[], entitlements={}, status=Status, password=CreateUser"
        + ".Password(hashedPassword=iloveyou, hashedSalt=Hashed Salt, hashedKmPassword=iloveyou, hashedKmSalt=Hashed"
        + " Km Salt), keys=CreateUser.Keys(current=CreateUser.Key(action=Action, expiration=Expiration, key=Key),"
        + " previous=CreateUser.Key(action=Action, expiration=Expiration, key=Key)))", actualToStringResult);
    assertEquals("Display Name", actualDisplayName);
    assertEquals("Doe", actualLastname);
    assertEquals("Jane", actualFirstname);
    assertEquals("Status", actualStatus);
    assertEquals("Type", actualType);
    assertEquals("en", actualRecommendedLanguage);
    assertEquals("jane.doe@example.org", actualEmail);
    assertEquals("janedoe", actualCreateUser.getUsername());
    assertNull(actualCreateUser.getOn());
    assertNull(actualCreateUser.getElseCondition());
    assertNull(actualCreateUser.getId());
    assertNull(actualCreateUser.getIfCondition());
    assertTrue(actualRoles.isEmpty());
    assertTrue(actualCreateUser.getVariableProperties().isEmpty());
    assertTrue(actualEntitlements.isEmpty());
    assertSame(business, actualBusiness);
    assertSame(contact, actualContact);
    assertSame(keys, actualKeys);
    assertSame(password, actualPassword);
    assertSame(roles, actualRoles);
    assertSame(entitlements, actualEntitlements);
  }

  /**
   * Test Key {@link Key#equals(Object)}, and {@link Key#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Key#equals(Object)}
   *   <li>{@link Key#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Key equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Key");

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertEquals(key, key2);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key2.hashCode());
  }

  /**
   * Test Key {@link Key#equals(Object)}, and {@link Key#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Key#equals(Object)}
   *   <li>{@link Key#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Key equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Key key = new Key();
    key.setAction(null);
    key.setExpiration("Expiration");
    key.setKey("Key");

    Key key2 = new Key();
    key2.setAction(null);
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertEquals(key, key2);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key2.hashCode());
  }

  /**
   * Test Key {@link Key#equals(Object)}, and {@link Key#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Key#equals(Object)}
   *   <li>{@link Key#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Key equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration(null);
    key.setKey("Key");

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration(null);
    key2.setKey("Key");

    // Act and Assert
    assertEquals(key, key2);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key2.hashCode());
  }

  /**
   * Test Key {@link Key#equals(Object)}, and {@link Key#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Key#equals(Object)}
   *   <li>{@link Key#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Key equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey(null);

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey(null);

    // Act and Assert
    assertEquals(key, key2);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key2.hashCode());
  }

  /**
   * Test Key {@link Key#equals(Object)}, and {@link Key#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Key#equals(Object)}
   *   <li>{@link Key#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Key equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Key");

    // Act and Assert
    assertEquals(key, key);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key.hashCode());
  }

  /**
   * Test Key {@link Key#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Key#equals(Object)}
   */
  @Test
  @DisplayName("Test Key equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Key key = new Key();
    key.setAction("Expiration");
    key.setExpiration("Expiration");
    key.setKey("Key");

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Test Key {@link Key#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Key#equals(Object)}
   */
  @Test
  @DisplayName("Test Key equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Key key = new Key();
    key.setAction(null);
    key.setExpiration("Expiration");
    key.setKey("Key");

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Test Key {@link Key#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Key#equals(Object)}
   */
  @Test
  @DisplayName("Test Key equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration("Action");
    key.setKey("Key");

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Test Key {@link Key#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Key#equals(Object)}
   */
  @Test
  @DisplayName("Test Key equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration(null);
    key.setKey("Key");

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Test Key {@link Key#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Key#equals(Object)}
   */
  @Test
  @DisplayName("Test Key equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Action");

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Test Key {@link Key#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Key#equals(Object)}
   */
  @Test
  @DisplayName("Test Key equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey(null);

    Key key2 = new Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Test Key {@link Key#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Key#equals(Object)}
   */
  @Test
  @DisplayName("Test Key equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Key");

    // Act and Assert
    assertNotEquals(key, null);
  }

  /**
   * Test Key {@link Key#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Key#equals(Object)}
   */
  @Test
  @DisplayName("Test Key equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Key.equals(Object)", "int Key.hashCode()"})
  void testKeyEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Key key = new Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Key");

    // Act and Assert
    assertNotEquals(key, "Different type to Key");
  }

  /**
   * Test Key getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Key}
   *   <li>{@link Key#setAction(String)}
   *   <li>{@link Key#setExpiration(String)}
   *   <li>{@link Key#setKey(String)}
   *   <li>{@link Key#toString()}
   *   <li>{@link Key#getAction()}
   *   <li>{@link Key#getExpiration()}
   *   <li>{@link Key#getKey()}
   * </ul>
   */
  @Test
  @DisplayName("Test Key getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Key.<init>()", "String Key.getAction()", "String Key.getExpiration()", "String Key.getKey()",
      "void Key.setAction(String)", "void Key.setExpiration(String)", "void Key.setKey(String)",
      "String Key.toString()"})
  void testKeyGettersAndSetters() {
    // Arrange and Act
    Key actualKey = new Key();
    actualKey.setAction("Action");
    actualKey.setExpiration("Expiration");
    actualKey.setKey("Key");
    String actualToStringResult = actualKey.toString();
    String actualAction = actualKey.getAction();
    String actualExpiration = actualKey.getExpiration();

    // Assert
    assertEquals("Action", actualAction);
    assertEquals("CreateUser.Key(action=Action, expiration=Expiration, key=Key)", actualToStringResult);
    assertEquals("Expiration", actualExpiration);
    assertEquals("Key", actualKey.getKey());
  }

  /**
   * Test Keys {@link Keys#equals(Object)}, and {@link Keys#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Keys#equals(Object)}
   *   <li>{@link Keys#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Keys equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Keys.equals(Object)", "int Keys.hashCode()"})
  void testKeysEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Key current = new Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    Key previous = new Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    Key current2 = new Key();
    current2.setAction("Action");
    current2.setExpiration("Expiration");
    current2.setKey("Key");

    Key previous2 = new Key();
    previous2.setAction("Action");
    previous2.setExpiration("Expiration");
    previous2.setKey("Key");

    Keys keys2 = new Keys();
    keys2.setCurrent(current2);
    keys2.setPrevious(previous2);

    // Act and Assert
    assertEquals(keys, keys2);
    int expectedHashCodeResult = keys.hashCode();
    assertEquals(expectedHashCodeResult, keys2.hashCode());
  }

  /**
   * Test Keys {@link Keys#equals(Object)}, and {@link Keys#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Keys#equals(Object)}
   *   <li>{@link Keys#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Keys equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Keys.equals(Object)", "int Keys.hashCode()"})
  void testKeysEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Key current = new Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    Key previous = new Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    // Act and Assert
    assertEquals(keys, keys);
    int expectedHashCodeResult = keys.hashCode();
    assertEquals(expectedHashCodeResult, keys.hashCode());
  }

  /**
   * Test Keys {@link Keys#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Keys#equals(Object)}
   */
  @Test
  @DisplayName("Test Keys equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Keys.equals(Object)", "int Keys.hashCode()"})
  void testKeysEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Key current = mock(Key.class);
    doNothing().when(current).setAction(Mockito.<String>any());
    doNothing().when(current).setExpiration(Mockito.<String>any());
    doNothing().when(current).setKey(Mockito.<String>any());
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    Key previous = new Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    Key current2 = new Key();
    current2.setAction("Action");
    current2.setExpiration("Expiration");
    current2.setKey("Key");

    Key previous2 = new Key();
    previous2.setAction("Action");
    previous2.setExpiration("Expiration");
    previous2.setKey("Key");

    Keys keys2 = new Keys();
    keys2.setCurrent(current2);
    keys2.setPrevious(previous2);

    // Act and Assert
    assertNotEquals(keys, keys2);
  }

  /**
   * Test Keys {@link Keys#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Keys#equals(Object)}
   */
  @Test
  @DisplayName("Test Keys equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Keys.equals(Object)", "int Keys.hashCode()"})
  void testKeysEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Key current = new Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    Key previous = new Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    // Act and Assert
    assertNotEquals(keys, null);
  }

  /**
   * Test Keys {@link Keys#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Keys#equals(Object)}
   */
  @Test
  @DisplayName("Test Keys equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Keys.equals(Object)", "int Keys.hashCode()"})
  void testKeysEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Key current = new Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    Key previous = new Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    Keys keys = new Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    // Act and Assert
    assertNotEquals(keys, "Different type to Keys");
  }

  /**
   * Test Keys getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Keys}
   *   <li>{@link Keys#setCurrent(Key)}
   *   <li>{@link Keys#setPrevious(Key)}
   *   <li>{@link Keys#toString()}
   *   <li>{@link Keys#getCurrent()}
   *   <li>{@link Keys#getPrevious()}
   * </ul>
   */
  @Test
  @DisplayName("Test Keys getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Keys.<init>()", "Key Keys.getCurrent()", "Key Keys.getPrevious()",
      "void Keys.setCurrent(Key)", "void Keys.setPrevious(Key)", "String Keys.toString()"})
  void testKeysGettersAndSetters() {
    // Arrange and Act
    Keys actualKeys = new Keys();
    Key current = new Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");
    actualKeys.setCurrent(current);
    Key previous = new Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");
    actualKeys.setPrevious(previous);
    String actualToStringResult = actualKeys.toString();
    Key actualCurrent = actualKeys.getCurrent();

    // Assert
    assertEquals("CreateUser.Keys(current=CreateUser.Key(action=Action, expiration=Expiration, key=Key), previous"
        + "=CreateUser.Key(action=Action, expiration=Expiration, key=Key))", actualToStringResult);
    assertSame(current, actualCurrent);
    assertSame(previous, actualKeys.getPrevious());
  }

  /**
   * Test Password {@link Password#equals(Object)}, and {@link Password#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Password#equals(Object)}
   *   <li>{@link Password#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Password equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertEquals(password, password2);
    int expectedHashCodeResult = password.hashCode();
    assertEquals(expectedHashCodeResult, password2.hashCode());
  }

  /**
   * Test Password {@link Password#equals(Object)}, and {@link Password#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Password#equals(Object)}
   *   <li>{@link Password#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Password equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword(null);
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword(null);
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertEquals(password, password2);
    int expectedHashCodeResult = password.hashCode();
    assertEquals(expectedHashCodeResult, password2.hashCode());
  }

  /**
   * Test Password {@link Password#equals(Object)}, and {@link Password#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Password#equals(Object)}
   *   <li>{@link Password#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Password equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt(null);
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt(null);
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertEquals(password, password2);
    int expectedHashCodeResult = password.hashCode();
    assertEquals(expectedHashCodeResult, password2.hashCode());
  }

  /**
   * Test Password {@link Password#equals(Object)}, and {@link Password#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Password#equals(Object)}
   *   <li>{@link Password#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Password equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword(null);
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword(null);
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertEquals(password, password2);
    int expectedHashCodeResult = password.hashCode();
    assertEquals(expectedHashCodeResult, password2.hashCode());
  }

  /**
   * Test Password {@link Password#equals(Object)}, and {@link Password#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Password#equals(Object)}
   *   <li>{@link Password#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Password equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt(null);

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt(null);

    // Act and Assert
    assertEquals(password, password2);
    int expectedHashCodeResult = password.hashCode();
    assertEquals(expectedHashCodeResult, password2.hashCode());
  }

  /**
   * Test Password {@link Password#equals(Object)}, and {@link Password#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Password#equals(Object)}
   *   <li>{@link Password#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Password equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertEquals(password, password);
    int expectedHashCodeResult = password.hashCode();
    assertEquals(expectedHashCodeResult, password.hashCode());
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("Hashed Salt");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword(null);
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("iloveyou");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt(null);
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("Hashed Salt");
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword(null);
    password.setHashedSalt("Hashed Salt");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("iloveyou");

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt(null);

    Password password2 = new Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, null);
  }

  /**
   * Test Password {@link Password#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Password#equals(Object)}
   */
  @Test
  @DisplayName("Test Password equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Password.equals(Object)", "int Password.hashCode()"})
  void testPasswordEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Password password = new Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, "Different type to Password");
  }

  /**
   * Test Password getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Password}
   *   <li>{@link Password#setHashedKmPassword(String)}
   *   <li>{@link Password#setHashedKmSalt(String)}
   *   <li>{@link Password#setHashedPassword(String)}
   *   <li>{@link Password#setHashedSalt(String)}
   *   <li>{@link Password#toString()}
   *   <li>{@link Password#getHashedKmPassword()}
   *   <li>{@link Password#getHashedKmSalt()}
   *   <li>{@link Password#getHashedPassword()}
   *   <li>{@link Password#getHashedSalt()}
   * </ul>
   */
  @Test
  @DisplayName("Test Password getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Password.<init>()", "String Password.getHashedKmPassword()",
      "String Password.getHashedKmSalt()", "String Password.getHashedPassword()", "String Password.getHashedSalt()",
      "void Password.setHashedKmPassword(String)", "void Password.setHashedKmSalt(String)",
      "void Password.setHashedPassword(String)", "void Password.setHashedSalt(String)", "String Password.toString()"})
  void testPasswordGettersAndSetters() {
    // Arrange and Act
    Password actualPassword = new Password();
    actualPassword.setHashedKmPassword("iloveyou");
    actualPassword.setHashedKmSalt("Hashed Km Salt");
    actualPassword.setHashedPassword("iloveyou");
    actualPassword.setHashedSalt("Hashed Salt");
    String actualToStringResult = actualPassword.toString();
    String actualHashedKmPassword = actualPassword.getHashedKmPassword();
    String actualHashedKmSalt = actualPassword.getHashedKmSalt();
    String actualHashedPassword = actualPassword.getHashedPassword();

    // Assert
    assertEquals("CreateUser.Password(hashedPassword=iloveyou, hashedSalt=Hashed Salt, hashedKmPassword=iloveyou,"
        + " hashedKmSalt=Hashed Km Salt)", actualToStringResult);
    assertEquals("Hashed Km Salt", actualHashedKmSalt);
    assertEquals("Hashed Salt", actualPassword.getHashedSalt());
    assertEquals("iloveyou", actualHashedKmPassword);
    assertEquals("iloveyou", actualHashedPassword);
  }
}
