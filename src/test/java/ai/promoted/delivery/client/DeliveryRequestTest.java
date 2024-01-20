package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.CohortMembership;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Request;
import ai.promoted.proto.event.CohortArm;
import ai.promoted.proto.common.UserInfo;

class DeliveryRequestTest {

  @Test
  void testRequestMustBeSet() {
     DeliveryRequest req = new DeliveryRequest(null);
     List<String> errors = req.validate();
     assertEquals(1, errors.size());
     assertEquals("Request must be set", errors.get(0));
  }

  @Test
  void testValidateRequestIdMustBeUnsetOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().requestId("z").userInfo(UserInfo.newBuilder().setAnonUserId("a").build()).insertion(new ArrayList<>()),
        null,
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(1, errors.size());
    assertEquals("Request.requestId should not be set", errors.get(0));
  }

  @Test
  void testValidateRetrievalInsertionOffsetMustBeNonNeg() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(UserInfo.newBuilder().setAnonUserId("a").build()).addInsertionItem(new Insertion().contentId("z")),
        null,
        false,
        -1);
    List<String> errors = req.validate();
    assertEquals(1, errors.size());
    assertEquals("Insertion start must be greater or equal to 0", errors.get(0));
  }

  @Test
  void testValidateContentIdMustBeSet() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(UserInfo.newBuilder().setAnonUserId("a").build()).addInsertionItem(new Insertion().contentId("")),
        null,
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(1, errors.size());
    assertEquals("Insertion.contentId should be set", errors.get(0));
  }

  @Test
  void testValidateWithValidInsertion() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(UserInfo.newBuilder().setAnonUserId("a").build()).addInsertionItem(new Insertion().contentId("z")),
        null,
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateExperimentValid() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(UserInfo.newBuilder().setAnonUserId("a").build()).addInsertionItem(new Insertion().contentId("z")),
        new CohortMembership().arm(CohortArm.TREATMENT).cohortId("my cohort"),
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateUserInfoOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().addInsertionItem(new Insertion().contentId("z")),
        null,
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(1, errors.size());
    assertEquals("Request.userInfo should be set", errors.get(0));
  }

  @Test
  void testValidateAnonUserIdOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().userInfo(UserInfo.newBuilder().setAnonUserId("").build()).addInsertionItem(new Insertion().contentId("z")),
        new CohortMembership().arm(CohortArm.TREATMENT).cohortId("my cohort"),
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(1, errors.size());
    assertEquals("Request.userInfo.anonUserId should be set", errors.get(0));
  }


  @Test
  void testValidateCapturesMultipleErrors() {
    DeliveryRequest req = new DeliveryRequest(
        new Request().requestId("a").userInfo(UserInfo.newBuilder().setAnonUserId("").build()).addInsertionItem(new Insertion().contentId("z")),
        new CohortMembership().arm(CohortArm.TREATMENT).cohortId("my cohort"),
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(2, errors.size());
    assertEquals("Request.requestId should not be set", errors.get(0));
    assertEquals("Request.userInfo.anonUserId should be set", errors.get(1));
  }

}
