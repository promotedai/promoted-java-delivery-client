package ai.promoted.delivery.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.google.protobuf.util.JsonFormat;
import ai.promoted.proto.common.UserInfo;
import ai.promoted.proto.delivery.Insertion;
import ai.promoted.proto.delivery.Paging;
import ai.promoted.proto.delivery.Request;
import ai.promoted.proto.delivery.Response;

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
    
    Request.Builder reqBuilder = Request.newBuilder().setUserInfo(UserInfo.newBuilder().setAnonUserId("12355")).setPlatformId(0)
        .setPaging(Paging.newBuilder().setOffset(0).setSize(100))
        .addInsertion(InsertionFactory.createInsertionBuilderWithProperties("28835", myProps))
        .addInsertion(Insertion.newBuilder().setContentId("49550"));
    add100Insertions(reqBuilder);
    
    client.deliver(new DeliveryRequest(reqBuilder, null, false, 0));
  }

  @Test
  public void testCallDeliverWithGrpc() throws Exception {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withDeliveryEndpoint("http://localhost:9091/deliver")
        .withDeliveryApiKey("abc").withDeliveryTimeoutMillis(30000).withUseGrpc(true).build();

    Map<String, Object> myProps = new HashMap<>();
    Map<String, Object> searchProps = new HashMap<>();
    searchProps.put("lat", 40.0);
    searchProps.put("lng", -122.3);
    myProps.put("search", searchProps);
    
    Request.Builder reqBuilder = Request.newBuilder().setUserInfo(UserInfo.newBuilder().setAnonUserId("12355")).setPlatformId(0)
        .setPaging(Paging.newBuilder().setOffset(0).setSize(100))
        .addInsertion(InsertionFactory.createInsertionBuilderWithProperties("28835", myProps))
        .addInsertion(Insertion.newBuilder().setContentId("49550"));
    add100Insertions(reqBuilder);
    
    client.deliver(new DeliveryRequest(reqBuilder, null, false, 0));
  }

  private void add100Insertions(Request.Builder reqBuilder) {
    for (int i = 0; i < 1000; i++) {
      reqBuilder.addInsertion(Insertion.newBuilder().setContentId(UUID.randomUUID().toString())).build();
    }
  }

  @Test
  public void testObjectMapping() throws Exception {
    String json =
        "{\"insertion\":[{\"insertionId\":\"29a0e667-e99f-4b0b-b4dd-9cc0ace62093\",\"contentId\":\"28835\",\"position\":\"0\"},{\"insertionId\":\"f598dc54-69d1-4453-83d3-7c38f1f2043a\",\"contentId\":\"49550\",\"position\":\"1\"}],\"pagingInfo\":{\"pagingId\":\"MTUwNzk1NzI0MTM3OTM1OTM2NDFfYWxsb2Nz\",\"cursor\":\"2\"}}\n"
            + "";
    Response.Builder respBuilder = Response.newBuilder();
    JsonFormat.parser().ignoringUnknownFields().merge(json, respBuilder);
    System.out.println(respBuilder.build());
  }
}
