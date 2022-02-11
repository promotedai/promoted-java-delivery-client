package ai.promoted.java.client;

import java.util.ArrayList;
import java.util.List;
import ai.promoted.delivery.model.Insertion;

public class TestUtils {
  
  public static List<Insertion> createInsertions(int num) {
    List<Insertion> res = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      res.add(new Insertion().contentId("" + i));
    }
    return res;
  }
}
