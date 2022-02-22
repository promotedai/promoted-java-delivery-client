package ai.promoted.delivery.client;

import java.util.ArrayList;
import java.util.List;
import ai.promoted.delivery.model.Insertion;

public class TestUtils {
  
  public static List<Insertion> createTestRequestInsertions(int num) {
    List<Insertion> res = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      res.add(new Insertion().contentId("" + i));
    }
    return res;
  }  
  
  public static List<Insertion> createTestResponseInsertions(int num, int offset) {
    List<Insertion> res = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      res.add(new Insertion().contentId("" + i).position(i+offset).insertionId("id"+i));
    }
    return res;
  }
}
