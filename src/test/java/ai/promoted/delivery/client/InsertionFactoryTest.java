package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.Insertion;

class InsertionFactoryTest {

  @Test
  void testPrepareResponseInsertion() {
    Insertion emptyIns = new Insertion();
    InsertionFactory.prepareResponseInsertion(emptyIns, 6);
    assertNotNull(emptyIns.getInsertionId());
    assertTrue(emptyIns.getInsertionId().length() > 0);
    assertEquals(6, emptyIns.getPosition());
  }
}
