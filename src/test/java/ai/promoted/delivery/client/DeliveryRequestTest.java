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
  void testRequestMustBeSet() {
     DeliveryRequest req = new DeliveryRequest(null);
     List<String> errors = req.validate(false);
     assertEquals(1, errors.size());
     assertEquals("Request must be set", errors.get(0));
  }

  @Test
  void testValidateRequestIdMustBeUnsetOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().requestId("z").userInfo(new UserInfo().logUserId("a")).insertion(new ArrayList<>()),
        null,
        false,
        0);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Request.requestId should not be set", errors.get(0));
  }

  @Test
  void testValidateInsertionIdMustBeUnset() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z").insertionId("a")),
        null,
        false,
        0);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Insertion.insertionId should not be set", errors.get(0));
  }

  @Test
  void testValidateInsertionStartMustBeNonNeg() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z")),
        null,
        false,
        -1);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Insertion start must be greater or equal to 0", errors.get(0));
  }

  @Test
  void testValidateContentIdMustBeSet() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("")),
        null,
        false,
        0);
    List<String> errors = req.validate(false);
    assertEquals(1, errors.size());
    assertEquals("Insertion.contentId should be set", errors.get(0));
  }

  @Test
  void testValidateWithValidInsertion() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z")),
        null,
        false,
        0);
    List<String> errors = req.validate(false);
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateExperimentValid() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(new UserInfo().logUserId("a")).addInsertionItem(new Insertion().contentId("z")),
        new CohortMembership().arm(CohortArm.TREATMENT).cohortId("my cohort"),
        false,
        0);
    List<String> errors = req.validate(false);
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateUserInfoOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().addInsertionItem(new Insertion().contentId("z")),
        null,
        false,
        0);
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
        0);
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
        0);
    List<String> errors = req.validate(false);
    assertEquals(2, errors.size());
    assertEquals("Request.requestId should not be set", errors.get(0));
    assertEquals("Request.userInfo.logUserId should be set", errors.get(1));
  }

}