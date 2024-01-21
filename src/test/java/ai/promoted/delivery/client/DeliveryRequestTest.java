package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import ai.promoted.proto.event.CohortArm;
import ai.promoted.proto.event.CohortMembership;
import ai.promoted.proto.common.UserInfo;
import ai.promoted.proto.delivery.Insertion;
import ai.promoted.proto.delivery.Request;

class DeliveryRequestTest {

  @Test
  void testRequestMustBeSet() {
     DeliveryRequest req = new DeliveryRequest(null);
     List<String> errors = req.validate();
     assertEquals(1, errors.size());
     assertEquals("Request builder must be set", errors.get(0));
  }

  @Test
  void testValidateRequestIdMustBeUnsetOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
        Request.newBuilder().setRequestId("z").setUserInfo(UserInfo.newBuilder().setAnonUserId("a")),
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
      Request.newBuilder().setUserInfo(UserInfo.newBuilder().setAnonUserId("a")).addInsertion(Insertion.newBuilder().setContentId("z")),
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
      Request.newBuilder().setUserInfo(UserInfo.newBuilder().setAnonUserId("a")).addInsertion(Insertion.newBuilder().setContentId("")),
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
      Request.newBuilder().setUserInfo(UserInfo.newBuilder().setAnonUserId("a")).addInsertion(Insertion.newBuilder().setContentId("z")),
        null,
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateExperimentValid() {
    DeliveryRequest req = new DeliveryRequest(
      Request.newBuilder().setUserInfo(UserInfo.newBuilder().setAnonUserId("a")).addInsertion(Insertion.newBuilder().setContentId("z")),
        CohortMembership.newBuilder().setArm(CohortArm.TREATMENT).setCohortId("my cohort").build(),
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(0, errors.size());
  }

  @Test
  void testValidateUserInfoOnRequest() {
    DeliveryRequest req = new DeliveryRequest(
      Request.newBuilder().addInsertion(Insertion.newBuilder().setContentId("z")),
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
      Request.newBuilder().setUserInfo(UserInfo.newBuilder().setAnonUserId("")).addInsertion(Insertion.newBuilder().setContentId("z")),
        CohortMembership.newBuilder().setArm(CohortArm.TREATMENT).setCohortId("my cohort").build(),
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(1, errors.size());
    assertEquals("Request.userInfo.anonUserId should be set", errors.get(0));
  }


  @Test
  void testValidateCapturesMultipleErrors() {
    DeliveryRequest req = new DeliveryRequest(
      Request.newBuilder().setRequestId("a").setUserInfo(UserInfo.newBuilder().setAnonUserId("")).addInsertion(Insertion.newBuilder().setContentId("z")),
        CohortMembership.newBuilder().setArm(CohortArm.TREATMENT).setCohortId("my cohort").build(),
        false,
        0);
    List<String> errors = req.validate();
    assertEquals(2, errors.size());
    assertEquals("Request.requestId should not be set", errors.get(0));
    assertEquals("Request.userInfo.anonUserId should be set", errors.get(1));
  }

}
