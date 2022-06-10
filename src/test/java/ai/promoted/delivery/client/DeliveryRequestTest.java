package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.CohortArm;
import ai.promoted.delivery.model.CohortMembership;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.UserInfo;

class DeliveryRequestTest {

  @Test
  void testValidatePrepatedInsertionsNotOnlyLogging() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).insertion(new ArrayList<>()),
        null,
        false,
        InsertionPageType.PREPAGED);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Delivery expects unpaged insertions", errors.get(0));
  }

  @Test
  void testValidatePrepagedInsertionsWithShadowTraffic() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).insertion(new ArrayList<>()),
        null,
        true,
        InsertionPageType.PREPAGED);
    List<String> errors = req.validate(true);
    assertEquals(1, errors.size());
    assertEquals("Insertions must be unpaged when shadow traffic is on", errors.get(0));
  }

  @Test
  void testValidatePrepagedInsertionsOnlyLog() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).insertion(new ArrayList<>()),
        null,
        true,
        InsertionPageType.PREPAGED);
    List<String> errors = req.validate(false);
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateRequestIdMustBeUnsetOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().requestId("z").userInfo(new UserInfo().logUserId("a")).insertion(new ArrayList<>()),
        null,
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Request.requestId should not be set", errors.get(0));
  }

  @Test
  void testValidateRequestIdMustBeUnsetOnInsertion() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z").requestId("a")),
        null,
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Insertion.requestId should not be set", errors.get(0));
  }

  @Test
  void testValidateInsertionIdMustBeUnset() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z").insertionId("a")),
        null,
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Insertion.insertionId should not be set", errors.get(0));
  }

  @Test
  void testValidateContentIdMustBeSet() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z").insertionId("a")),
        null,
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Insertion.insertionId should not be set", errors.get(0));
  }

  @Test
  void testValidateWithValidInsertion() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z")),
        null,
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateExperimentValid() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z")),
        new CohortMembership().arm(CohortArm.TREATMENT).cohortId("my cohort"),
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateUserInfoOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().addInsertionItem(new Insertion().contentId("z")),
        null,
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Request.userInfo should be set", errors.get(0));
  }

  @Test
  void testValidateLogUserIdOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("")).addInsertionItem(new Insertion().contentId("z")),
        new CohortMembership().arm(CohortArm.TREATMENT).cohortId("my cohort"),
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Request.userInfo.logUserId should be set", errors.get(0));
  }


  @Test
  void testValidateCapturesMultipleErrors() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().requestId("a").userInfo(new UserInfo().logUserId("")).addInsertionItem(new Insertion().contentId("z")),
        new CohortMembership().arm(CohortArm.TREATMENT).cohortId("my cohort"),
        false,
        InsertionPageType.UNPAGED);
    List<String> errors = req.validate(false);
    assertEquals(2, errors.size());
    assertEquals("Request.requestId should not be set", errors.get(0));
    assertEquals("Request.userInfo.logUserId should be set", errors.get(1));
  }

}
