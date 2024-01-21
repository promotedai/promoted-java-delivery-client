package ai.promoted.delivery.client;

import java.util.ArrayList;
import java.util.List;
import ai.promoted.proto.delivery.Insertion;

public class TestUtils {
  
  public static List<Insertion> createTestRequestInsertions(int num) {
    List<Insertion> res = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      res.add(Insertion.newBuilder().setContentId("" + i).build());
    }
    return res;
  }  
  
  public static List<Insertion> createTestResponseInsertions(int num, int offset) {
    List<Insertion> res = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      res.add(Insertion.newBuilder().setContentId("" + i).setPosition(i+offset).setInsertionId("id"+i).build());
    }
    return res;
  }
}
