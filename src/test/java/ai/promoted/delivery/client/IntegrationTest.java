package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;
import ai.promoted.proto.common.UserInfo;
import ai.promoted.proto.delivery.Paging;

@Disabled("only runs locally for interactive debugging")
public class IntegrationTest {

  @Test
  public void testCallDeliver() throws Exception {
    PromotedDeliveryClient client =
        PromotedDeliveryClient.builder().withDeliveryEndpoint("http://localhost:9090/deliver")
            .withDeliveryApiKey("abc").withDeliveryTimeoutMillis(30000).build();
    
    Map<String, Object> myProps = new HashMap<>();
    Map<String, Object> searchProps = new HashMap<>();
    searchProps.put("lat", 40.0);
    searchProps.put("lng", -122.3);
    myProps.put("search", searchProps);
    
    Request req = new Request().userInfo(UserInfo.newBuilder().setAnonUserId("12355").build()).platformId(0)
        .paging(Paging.newBuilder().setOffset(0).setSize(100).build())
        .addInsertionItem(InsertionFactory.createInsertionWithProperties("28835", myProps))
        .addInsertionItem(new Insertion().contentId("49550"));
    add100Insertions(req);
    
    DeliveryResponse resp = client.deliver(new DeliveryRequest(req, null, false, 0));
    System.out.println(resp);
    assertTrue(true);
  }

  private void add100Insertions(Request req) {
    for (int i = 0; i < 1000; i++) {
      req.addInsertionItem(new Insertion().contentId(UUID.randomUUID().toString()));
    }
  }

  @Test
  public void testObjectMapping() throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    String json =
        "{\"insertion\":[{\"insertionId\":\"29a0e667-e99f-4b0b-b4dd-9cc0ace62093\",\"contentId\":\"28835\",\"position\":\"0\"},{\"insertionId\":\"f598dc54-69d1-4453-83d3-7c38f1f2043a\",\"contentId\":\"49550\",\"position\":\"1\"}],\"pagingInfo\":{\"pagingId\":\"MTUwNzk1NzI0MTM3OTM1OTM2NDFfYWxsb2Nz\",\"cursor\":\"2\"}}\n"
            + "";
    Object result = mapper.readValue(json, Response.class);
    System.out.println(result);
  }
}
