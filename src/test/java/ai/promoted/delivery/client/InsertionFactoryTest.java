package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.Insertion;

class InsertionFactoryTest {

  @Test
  void testPrepareResponseInsertion_insertionIdUnset() {
    Insertion emptyIns = new Insertion();
    InsertionFactory.prepareResponseInsertion(emptyIns, 6);
    assertNotNull(emptyIns.getInsertionId());
    assertTrue(emptyIns.getInsertionId().length() > 0);
    assertEquals(6, emptyIns.getPosition());
  }

  @Test
  void testPrepareResponseInsertion_insertionIdSet() {
    Insertion ins = new Insertion();
    ins.setInsertionId("uuid");
    InsertionFactory.prepareResponseInsertion(ins, 6);
    assertNotNull(ins.getInsertionId());
    assertEquals("uuid", ins.getInsertionId());
    assertEquals(6, ins.getPosition());
  }
}
