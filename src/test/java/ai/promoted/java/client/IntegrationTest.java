package ai.promoted.java.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Paging;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;
import ai.promoted.delivery.model.UserInfo;

public class IntegrationTest {

  @Test
  public void testCallDeliver() throws Exception {
    PromotedDeliveryClient client =
        PromotedDeliveryClient.builder().withDeliveryEndpoint("http://localhost:9090/deliver")
            .withDeliveryApiKey("abc").withDeliveryTimeoutMillis(30000).build();
    Request req = new Request().userInfo(new UserInfo().logUserId("12355")).platformId(0)
        .paging(new Paging().size(10).offset(0))
        .addInsertionItem(new Insertion().contentId("28835"))
        .addInsertionItem(new Insertion().contentId("49550"));

    client.deliver(new DeliveryRequest(req, null, false, InsertionPageType.UNPAGED));
    assertTrue(true);
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
