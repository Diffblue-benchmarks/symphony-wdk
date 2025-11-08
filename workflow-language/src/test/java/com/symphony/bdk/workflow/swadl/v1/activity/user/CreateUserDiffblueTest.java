package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateUserDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Business#equals(Object)}
   *   <li>{@link CreateUser.Business#hashCode()}
   * </ul>
   */
  @Test
  void testBusinessEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Business#equals(Object)}
   *   <li>{@link CreateUser.Business#hashCode()}
   * </ul>
   */
  @Test
  void testBusinessEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ArrayList<String> assetClasses = new ArrayList<>();
    assetClasses.add("Company Name");

    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    ArrayList<String> functions = new ArrayList<>();
    functions.add("Company Name");

    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ArrayList<String> industries = new ArrayList<>();
    industries.add("Company Name");

    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    ArrayList<String> instruments = new ArrayList<>();
    instruments.add("Company Name");

    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    ArrayList<String> marketCoverages = new ArrayList<>();
    marketCoverages.add("Company Name");

    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    ArrayList<String> responsibilities = new ArrayList<>();
    responsibilities.add("Company Name");

    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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

    CreateUser.Business business2 = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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
   * Method under test: {@link CreateUser.Business#equals(Object)}
   */
  @Test
  void testBusinessEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateUser.Business business = new CreateUser.Business();
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
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateUser.Business}
   *   <li>{@link CreateUser.Business#setAssetClasses(List)}
   *   <li>{@link CreateUser.Business#setCompanyName(String)}
   *   <li>{@link CreateUser.Business#setDepartment(String)}
   *   <li>{@link CreateUser.Business#setDivision(String)}
   *   <li>{@link CreateUser.Business#setFunctions(List)}
   *   <li>{@link CreateUser.Business#setIndustries(List)}
   *   <li>{@link CreateUser.Business#setInstruments(List)}
   *   <li>{@link CreateUser.Business#setJobFunction(String)}
   *   <li>{@link CreateUser.Business#setLocation(String)}
   *   <li>{@link CreateUser.Business#setMarketCoverages(List)}
   *   <li>{@link CreateUser.Business#setResponsibilities(List)}
   *   <li>{@link CreateUser.Business#setTitle(String)}
   *   <li>{@link CreateUser.Business#toString()}
   *   <li>{@link CreateUser.Business#getAssetClasses()}
   *   <li>{@link CreateUser.Business#getCompanyName()}
   *   <li>{@link CreateUser.Business#getDepartment()}
   *   <li>{@link CreateUser.Business#getDivision()}
   *   <li>{@link CreateUser.Business#getFunctions()}
   *   <li>{@link CreateUser.Business#getIndustries()}
   *   <li>{@link CreateUser.Business#getInstruments()}
   *   <li>{@link CreateUser.Business#getJobFunction()}
   *   <li>{@link CreateUser.Business#getLocation()}
   *   <li>{@link CreateUser.Business#getMarketCoverages()}
   *   <li>{@link CreateUser.Business#getResponsibilities()}
   *   <li>{@link CreateUser.Business#getTitle()}
   * </ul>
   */
  @Test
  void testBusinessGettersAndSetters() {
    // Arrange and Act
    CreateUser.Business actualBusiness = new CreateUser.Business();
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

    // Assert that nothing has changed
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Contact#equals(Object)}
   *   <li>{@link CreateUser.Contact#hashCode()}
   * </ul>
   */
  @Test
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Contact#equals(Object)}
   *   <li>{@link CreateUser.Contact#hashCode()}
   * </ul>
   */
  @Test
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber(null);
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Contact#equals(Object)}
   *   <li>{@link CreateUser.Contact#hashCode()}
   * </ul>
   */
  @Test
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber(null);
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Contact#equals(Object)}
   *   <li>{@link CreateUser.Contact#hashCode()}
   * </ul>
   */
  @Test
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber(null);
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Contact#equals(Object)}
   *   <li>{@link CreateUser.Contact#hashCode()}
   * </ul>
   */
  @Test
  void testContactEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber(null);

    CreateUser.Contact contact2 = new CreateUser.Contact();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Contact#equals(Object)}
   *   <li>{@link CreateUser.Contact#hashCode()}
   * </ul>
   */
  @Test
  void testContactEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
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
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("8605550118");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber(null);
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("6625550144");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber(null);
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("6625550144");
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber(null);
    contact.setWorkPhoneNumber("6625550144");

    CreateUser.Contact contact2 = new CreateUser.Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("8605550118");

    CreateUser.Contact contact2 = new CreateUser.Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber(null);

    CreateUser.Contact contact2 = new CreateUser.Contact();
    contact2.setMobilePhoneNumber("6625550144");
    contact2.setSmsNumber("42");
    contact2.setTwoFactorAuthNumber("42");
    contact2.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, contact2);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, null);
  }

  /**
   * Method under test: {@link CreateUser.Contact#equals(Object)}
   */
  @Test
  void testContactEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateUser.Contact contact = new CreateUser.Contact();
    contact.setMobilePhoneNumber("6625550144");
    contact.setSmsNumber("42");
    contact.setTwoFactorAuthNumber("42");
    contact.setWorkPhoneNumber("6625550144");

    // Act and Assert
    assertNotEquals(contact, "Different type to Contact");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateUser.Contact}
   *   <li>{@link CreateUser.Contact#setMobilePhoneNumber(String)}
   *   <li>{@link CreateUser.Contact#setSmsNumber(String)}
   *   <li>{@link CreateUser.Contact#setTwoFactorAuthNumber(String)}
   *   <li>{@link CreateUser.Contact#setWorkPhoneNumber(String)}
   *   <li>{@link CreateUser.Contact#toString()}
   *   <li>{@link CreateUser.Contact#getMobilePhoneNumber()}
   *   <li>{@link CreateUser.Contact#getSmsNumber()}
   *   <li>{@link CreateUser.Contact#getTwoFactorAuthNumber()}
   *   <li>{@link CreateUser.Contact#getWorkPhoneNumber()}
   * </ul>
   */
  @Test
  void testContactGettersAndSetters() {
    // Arrange and Act
    CreateUser.Contact actualContact = new CreateUser.Contact();
    actualContact.setMobilePhoneNumber("6625550144");
    actualContact.setSmsNumber("42");
    actualContact.setTwoFactorAuthNumber("42");
    actualContact.setWorkPhoneNumber("6625550144");
    String actualToStringResult = actualContact.toString();
    String actualMobilePhoneNumber = actualContact.getMobilePhoneNumber();
    String actualSmsNumber = actualContact.getSmsNumber();
    String actualTwoFactorAuthNumber = actualContact.getTwoFactorAuthNumber();

    // Assert that nothing has changed
    assertEquals("42", actualSmsNumber);
    assertEquals("42", actualTwoFactorAuthNumber);
    assertEquals("6625550144", actualMobilePhoneNumber);
    assertEquals("6625550144", actualContact.getWorkPhoneNumber());
    assertEquals("CreateUser.Contact(workPhoneNumber=6625550144, mobilePhoneNumber=6625550144, twoFactorAuthNumber=42,"
        + " smsNumber=42)", actualToStringResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser#equals(Object)}
   *   <li>{@link CreateUser#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser#equals(Object)}
   *   <li>{@link CreateUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateUser createUser = new CreateUser();

    // Act and Assert
    assertEquals(createUser, createUser);
    int expectedHashCodeResult = createUser.hashCode();
    assertEquals(expectedHashCodeResult, createUser.hashCode());
  }

  /**
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();

    // Act and Assert
    assertNotEquals(createSystemUser, new CreateUser());
  }

  /**
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateUser createUser = new CreateUser();
    createUser.add("NORMAL", "Value");

    // Act and Assert
    assertNotEquals(createUser, new CreateUser());
  }

  /**
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateUser createUser = new CreateUser();
    createUser.add("NORMAL", mock(CreateSystemUser.class));

    // Act and Assert
    assertNotEquals(createUser, new CreateUser());
  }

  /**
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateUser createUser = new CreateUser();

    // Act and Assert
    assertNotEquals(createUser, new CreateSystemUser());
  }

  /**
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
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
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateUser(), null);
  }

  /**
   * Method under test: {@link CreateUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateUser(), "Different type to CreateUser");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateUser}
   *   <li>{@link CreateUser#setBusiness(CreateUser.Business)}
   *   <li>{@link CreateUser#setContact(CreateUser.Contact)}
   *   <li>{@link CreateUser#setDisplayName(String)}
   *   <li>{@link CreateUser#setEmail(String)}
   *   <li>{@link CreateUser#setEntitlements(Map)}
   *   <li>{@link CreateUser#setFirstname(String)}
   *   <li>{@link CreateUser#setKeys(CreateUser.Keys)}
   *   <li>{@link CreateUser#setLastname(String)}
   *   <li>{@link CreateUser#setPassword(CreateUser.Password)}
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
  void testGettersAndSetters() {
    // Arrange and Act
    CreateUser actualCreateUser = new CreateUser();
    CreateUser.Business business = new CreateUser.Business();
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
    CreateUser.Contact contact = new CreateUser.Contact();
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
    CreateUser.Key current = new CreateUser.Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");
    CreateUser.Key previous = new CreateUser.Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");
    CreateUser.Keys keys = new CreateUser.Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);
    actualCreateUser.setKeys(keys);
    actualCreateUser.setLastname("Doe");
    CreateUser.Password password = new CreateUser.Password();
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
    CreateUser.Business actualBusiness = actualCreateUser.getBusiness();
    CreateUser.Contact actualContact = actualCreateUser.getContact();
    String actualDisplayName = actualCreateUser.getDisplayName();
    String actualEmail = actualCreateUser.getEmail();
    Map<String, Boolean> actualEntitlements = actualCreateUser.getEntitlements();
    String actualFirstname = actualCreateUser.getFirstname();
    CreateUser.Keys actualKeys = actualCreateUser.getKeys();
    String actualLastname = actualCreateUser.getLastname();
    CreateUser.Password actualPassword = actualCreateUser.getPassword();
    String actualRecommendedLanguage = actualCreateUser.getRecommendedLanguage();
    List<String> actualRoles = actualCreateUser.getRoles();
    String actualStatus = actualCreateUser.getStatus();
    String actualType = actualCreateUser.getType();

    // Assert that nothing has changed
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Key#equals(Object)}
   *   <li>{@link CreateUser.Key#hashCode()}
   * </ul>
   */
  @Test
  void testKeyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Key");

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertEquals(key, key2);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Key#equals(Object)}
   *   <li>{@link CreateUser.Key#hashCode()}
   * </ul>
   */
  @Test
  void testKeyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction(null);
    key.setExpiration("Expiration");
    key.setKey("Key");

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction(null);
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertEquals(key, key2);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Key#equals(Object)}
   *   <li>{@link CreateUser.Key#hashCode()}
   * </ul>
   */
  @Test
  void testKeyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration(null);
    key.setKey("Key");

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration(null);
    key2.setKey("Key");

    // Act and Assert
    assertEquals(key, key2);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Key#equals(Object)}
   *   <li>{@link CreateUser.Key#hashCode()}
   * </ul>
   */
  @Test
  void testKeyEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey(null);

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey(null);

    // Act and Assert
    assertEquals(key, key2);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Key#equals(Object)}
   *   <li>{@link CreateUser.Key#hashCode()}
   * </ul>
   */
  @Test
  void testKeyEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Key");

    // Act and Assert
    assertEquals(key, key);
    int expectedHashCodeResult = key.hashCode();
    assertEquals(expectedHashCodeResult, key.hashCode());
  }

  /**
   * Method under test: {@link CreateUser.Key#equals(Object)}
   */
  @Test
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Expiration");
    key.setExpiration("Expiration");
    key.setKey("Key");

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Method under test: {@link CreateUser.Key#equals(Object)}
   */
  @Test
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction(null);
    key.setExpiration("Expiration");
    key.setKey("Key");

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Method under test: {@link CreateUser.Key#equals(Object)}
   */
  @Test
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration("Action");
    key.setKey("Key");

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Method under test: {@link CreateUser.Key#equals(Object)}
   */
  @Test
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration(null);
    key.setKey("Key");

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Method under test: {@link CreateUser.Key#equals(Object)}
   */
  @Test
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Action");

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Method under test: {@link CreateUser.Key#equals(Object)}
   */
  @Test
  void testKeyEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey(null);

    CreateUser.Key key2 = new CreateUser.Key();
    key2.setAction("Action");
    key2.setExpiration("Expiration");
    key2.setKey("Key");

    // Act and Assert
    assertNotEquals(key, key2);
  }

  /**
   * Method under test: {@link CreateUser.Key#equals(Object)}
   */
  @Test
  void testKeyEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Key");

    // Act and Assert
    assertNotEquals(key, null);
  }

  /**
   * Method under test: {@link CreateUser.Key#equals(Object)}
   */
  @Test
  void testKeyEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateUser.Key key = new CreateUser.Key();
    key.setAction("Action");
    key.setExpiration("Expiration");
    key.setKey("Key");

    // Act and Assert
    assertNotEquals(key, "Different type to Key");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateUser.Key}
   *   <li>{@link CreateUser.Key#setAction(String)}
   *   <li>{@link CreateUser.Key#setExpiration(String)}
   *   <li>{@link CreateUser.Key#setKey(String)}
   *   <li>{@link CreateUser.Key#toString()}
   *   <li>{@link CreateUser.Key#getAction()}
   *   <li>{@link CreateUser.Key#getExpiration()}
   *   <li>{@link CreateUser.Key#getKey()}
   * </ul>
   */
  @Test
  void testKeyGettersAndSetters() {
    // Arrange and Act
    CreateUser.Key actualKey = new CreateUser.Key();
    actualKey.setAction("Action");
    actualKey.setExpiration("Expiration");
    actualKey.setKey("Key");
    String actualToStringResult = actualKey.toString();
    String actualAction = actualKey.getAction();
    String actualExpiration = actualKey.getExpiration();

    // Assert that nothing has changed
    assertEquals("Action", actualAction);
    assertEquals("CreateUser.Key(action=Action, expiration=Expiration, key=Key)", actualToStringResult);
    assertEquals("Expiration", actualExpiration);
    assertEquals("Key", actualKey.getKey());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Keys#equals(Object)}
   *   <li>{@link CreateUser.Keys#hashCode()}
   * </ul>
   */
  @Test
  void testKeysEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateUser.Key current = new CreateUser.Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    CreateUser.Key previous = new CreateUser.Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    CreateUser.Keys keys = new CreateUser.Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    CreateUser.Key current2 = new CreateUser.Key();
    current2.setAction("Action");
    current2.setExpiration("Expiration");
    current2.setKey("Key");

    CreateUser.Key previous2 = new CreateUser.Key();
    previous2.setAction("Action");
    previous2.setExpiration("Expiration");
    previous2.setKey("Key");

    CreateUser.Keys keys2 = new CreateUser.Keys();
    keys2.setCurrent(current2);
    keys2.setPrevious(previous2);

    // Act and Assert
    assertEquals(keys, keys2);
    int expectedHashCodeResult = keys.hashCode();
    assertEquals(expectedHashCodeResult, keys2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Keys#equals(Object)}
   *   <li>{@link CreateUser.Keys#hashCode()}
   * </ul>
   */
  @Test
  void testKeysEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateUser.Key current = new CreateUser.Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    CreateUser.Key previous = new CreateUser.Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    CreateUser.Keys keys = new CreateUser.Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    // Act and Assert
    assertEquals(keys, keys);
    int expectedHashCodeResult = keys.hashCode();
    assertEquals(expectedHashCodeResult, keys.hashCode());
  }

  /**
   * Method under test: {@link CreateUser.Keys#equals(Object)}
   */
  @Test
  void testKeysEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateUser.Key current = mock(CreateUser.Key.class);
    doNothing().when(current).setAction(Mockito.<String>any());
    doNothing().when(current).setExpiration(Mockito.<String>any());
    doNothing().when(current).setKey(Mockito.<String>any());
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    CreateUser.Key previous = new CreateUser.Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    CreateUser.Keys keys = new CreateUser.Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    CreateUser.Key current2 = new CreateUser.Key();
    current2.setAction("Action");
    current2.setExpiration("Expiration");
    current2.setKey("Key");

    CreateUser.Key previous2 = new CreateUser.Key();
    previous2.setAction("Action");
    previous2.setExpiration("Expiration");
    previous2.setKey("Key");

    CreateUser.Keys keys2 = new CreateUser.Keys();
    keys2.setCurrent(current2);
    keys2.setPrevious(previous2);

    // Act and Assert
    assertNotEquals(keys, keys2);
  }

  /**
   * Method under test: {@link CreateUser.Keys#equals(Object)}
   */
  @Test
  void testKeysEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateUser.Key current = new CreateUser.Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    CreateUser.Key previous = new CreateUser.Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    CreateUser.Keys keys = new CreateUser.Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    // Act and Assert
    assertNotEquals(keys, null);
  }

  /**
   * Method under test: {@link CreateUser.Keys#equals(Object)}
   */
  @Test
  void testKeysEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateUser.Key current = new CreateUser.Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");

    CreateUser.Key previous = new CreateUser.Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");

    CreateUser.Keys keys = new CreateUser.Keys();
    keys.setCurrent(current);
    keys.setPrevious(previous);

    // Act and Assert
    assertNotEquals(keys, "Different type to Keys");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateUser.Keys}
   *   <li>{@link CreateUser.Keys#setCurrent(CreateUser.Key)}
   *   <li>{@link CreateUser.Keys#setPrevious(CreateUser.Key)}
   *   <li>{@link CreateUser.Keys#toString()}
   *   <li>{@link CreateUser.Keys#getCurrent()}
   *   <li>{@link CreateUser.Keys#getPrevious()}
   * </ul>
   */
  @Test
  void testKeysGettersAndSetters() {
    // Arrange and Act
    CreateUser.Keys actualKeys = new CreateUser.Keys();
    CreateUser.Key current = new CreateUser.Key();
    current.setAction("Action");
    current.setExpiration("Expiration");
    current.setKey("Key");
    actualKeys.setCurrent(current);
    CreateUser.Key previous = new CreateUser.Key();
    previous.setAction("Action");
    previous.setExpiration("Expiration");
    previous.setKey("Key");
    actualKeys.setPrevious(previous);
    String actualToStringResult = actualKeys.toString();
    CreateUser.Key actualCurrent = actualKeys.getCurrent();

    // Assert that nothing has changed
    assertEquals("CreateUser.Keys(current=CreateUser.Key(action=Action, expiration=Expiration, key=Key), previous"
        + "=CreateUser.Key(action=Action, expiration=Expiration, key=Key))", actualToStringResult);
    assertSame(current, actualCurrent);
    assertSame(previous, actualKeys.getPrevious());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Password#equals(Object)}
   *   <li>{@link CreateUser.Password#hashCode()}
   * </ul>
   */
  @Test
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Password#equals(Object)}
   *   <li>{@link CreateUser.Password#hashCode()}
   * </ul>
   */
  @Test
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword(null);
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Password#equals(Object)}
   *   <li>{@link CreateUser.Password#hashCode()}
   * </ul>
   */
  @Test
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt(null);
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Password#equals(Object)}
   *   <li>{@link CreateUser.Password#hashCode()}
   * </ul>
   */
  @Test
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword(null);
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Password#equals(Object)}
   *   <li>{@link CreateUser.Password#hashCode()}
   * </ul>
   */
  @Test
  void testPasswordEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt(null);

    CreateUser.Password password2 = new CreateUser.Password();
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
   * Methods under test:
   * <ul>
   *   <li>{@link CreateUser.Password#equals(Object)}
   *   <li>{@link CreateUser.Password#hashCode()}
   * </ul>
   */
  @Test
  void testPasswordEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
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
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("Hashed Salt");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword(null);
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("iloveyou");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt(null);
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("Hashed Salt");
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword(null);
    password.setHashedSalt("Hashed Salt");

    CreateUser.Password password2 = new CreateUser.Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("iloveyou");

    CreateUser.Password password2 = new CreateUser.Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt(null);

    CreateUser.Password password2 = new CreateUser.Password();
    password2.setHashedKmPassword("iloveyou");
    password2.setHashedKmSalt("Hashed Km Salt");
    password2.setHashedPassword("iloveyou");
    password2.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, password2);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, null);
  }

  /**
   * Method under test: {@link CreateUser.Password#equals(Object)}
   */
  @Test
  void testPasswordEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateUser.Password password = new CreateUser.Password();
    password.setHashedKmPassword("iloveyou");
    password.setHashedKmSalt("Hashed Km Salt");
    password.setHashedPassword("iloveyou");
    password.setHashedSalt("Hashed Salt");

    // Act and Assert
    assertNotEquals(password, "Different type to Password");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateUser.Password}
   *   <li>{@link CreateUser.Password#setHashedKmPassword(String)}
   *   <li>{@link CreateUser.Password#setHashedKmSalt(String)}
   *   <li>{@link CreateUser.Password#setHashedPassword(String)}
   *   <li>{@link CreateUser.Password#setHashedSalt(String)}
   *   <li>{@link CreateUser.Password#toString()}
   *   <li>{@link CreateUser.Password#getHashedKmPassword()}
   *   <li>{@link CreateUser.Password#getHashedKmSalt()}
   *   <li>{@link CreateUser.Password#getHashedPassword()}
   *   <li>{@link CreateUser.Password#getHashedSalt()}
   * </ul>
   */
  @Test
  void testPasswordGettersAndSetters() {
    // Arrange and Act
    CreateUser.Password actualPassword = new CreateUser.Password();
    actualPassword.setHashedKmPassword("iloveyou");
    actualPassword.setHashedKmSalt("Hashed Km Salt");
    actualPassword.setHashedPassword("iloveyou");
    actualPassword.setHashedSalt("Hashed Salt");
    String actualToStringResult = actualPassword.toString();
    String actualHashedKmPassword = actualPassword.getHashedKmPassword();
    String actualHashedKmSalt = actualPassword.getHashedKmSalt();
    String actualHashedPassword = actualPassword.getHashedPassword();

    // Assert that nothing has changed
    assertEquals("CreateUser.Password(hashedPassword=iloveyou, hashedSalt=Hashed Salt, hashedKmPassword=iloveyou,"
        + " hashedKmSalt=Hashed Km Salt)", actualToStringResult);
    assertEquals("Hashed Km Salt", actualHashedKmSalt);
    assertEquals("Hashed Salt", actualPassword.getHashedSalt());
    assertEquals("iloveyou", actualHashedKmPassword);
    assertEquals("iloveyou", actualHashedPassword);
  }
}
