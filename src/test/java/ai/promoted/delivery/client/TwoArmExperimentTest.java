package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import ai.promoted.proto.event.CohortArm;
import ai.promoted.proto.event.CohortMembership;

class TwoArmExperimentTest {

  @Test
  void testCreateSuccess() {
    TwoArmExperiment exp = new TwoArmExperiment("HOLD_OUT", 10, 50, 10, 50);
    assertEquals("HOLD_OUT", exp.getCohortId());
    assertEquals(50, exp.getNumControlBuckets());
    assertEquals(50, exp.getNumTreatmentBuckets());
    assertEquals(10, exp.getNumActiveTreatmentBuckets());
    assertEquals(10, exp.getNumActiveControlBuckets());
  }

  @Test
  void testCreateInvalidCohortId() {
    Exception exception = assertThrows(
        IllegalArgumentException.class, 
        () -> new TwoArmExperiment(null, 10, 50, 10, 50));
    assertEquals("Cohort ID must be non-empty", exception.getMessage());
    
    exception = assertThrows(
        IllegalArgumentException.class, 
        () -> new TwoArmExperiment(" ", 10, 50, 10, 50));
    assertEquals("Cohort ID must be non-empty", exception.getMessage());
  }

  @Test
  void testCreateInvalidBucketActiveCounts() {
    Exception exception = assertThrows(
        IllegalArgumentException.class, 
        () -> new TwoArmExperiment("a", -1, 50, 10, 50));
    assertEquals("Active control buckets must be between 0 and the total number of control buckets", exception.getMessage());
    
    exception = assertThrows(
        IllegalArgumentException.class, 
        () -> new TwoArmExperiment("a", 51, 50, 10, 50));
    assertEquals("Active control buckets must be between 0 and the total number of control buckets", exception.getMessage());
    
    exception = assertThrows(
        IllegalArgumentException.class, 
        () -> new TwoArmExperiment("a", 10, 50, -1, 50));
    assertEquals("Active treatment buckets must be between 0 and the total number of treatment buckets", exception.getMessage());
    
    exception = assertThrows(
        IllegalArgumentException.class, 
        () -> new TwoArmExperiment("a", 10, 50, 51, 50));
    assertEquals("Active treatment buckets must be between 0 and the total number of treatment buckets", exception.getMessage());
  }
  
  @Test
  void testCreateInvalidBucketCounts() {
    Exception exception = assertThrows(
        IllegalArgumentException.class, 
        () -> new TwoArmExperiment("a", 0, -1, 10, 50));
    assertEquals("Control buckets must be positive", exception.getMessage());
    
    exception = assertThrows(
        IllegalArgumentException.class, 
        () -> new TwoArmExperiment("a", 10, 50, 0, -1));
    assertEquals("Treatment buckets must be positive", exception.getMessage());
  }
  
  @Test
  void testCreateTwoArmExperiment1PercentSuccess() {
    TwoArmExperiment exp = TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", 1, 1);
    assertEquals("HOLD_OUT", exp.getCohortId());
    assertEquals(50, exp.getNumControlBuckets());
    assertEquals(50, exp.getNumTreatmentBuckets());
    assertEquals(1, exp.getNumActiveTreatmentBuckets());
    assertEquals(1, exp.getNumActiveControlBuckets());
  }
  
  @Test
  void testCreateTwoArmExperiment10And5PercentSuccess() {
    TwoArmExperiment exp = TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", 10, 5);
    assertEquals("HOLD_OUT", exp.getCohortId());
    assertEquals(50, exp.getNumControlBuckets());
    assertEquals(50, exp.getNumTreatmentBuckets());
    assertEquals(5, exp.getNumActiveTreatmentBuckets());
    assertEquals(10, exp.getNumActiveControlBuckets());
  }
  
  // The user ids in the following experiments were determined empirically as to how they hash;
  // any changes to the hashing algorithm will force these to change.
  //
  @Test
  void testUserInControl() {
    TwoArmExperiment exp = TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", 50, 50);
    CohortMembership mem = exp.checkMembership("user1");
    assertEquals("HOLD_OUT", mem.getCohortId());
    assertEquals(CohortArm.CONTROL, mem.getArm());
  }
  
  @Test
  void testUserNotActive() {
    TwoArmExperiment exp = TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", 1, 1);
    CohortMembership mem = exp.checkMembership("user5");
    assertNull(mem);
  }
  
  @Test
  void testUserInTreatment() {
    TwoArmExperiment exp = TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", 50, 50);
    CohortMembership mem = exp.checkMembership("user3");
    assertEquals("HOLD_OUT", mem.getCohortId());
    assertEquals(CohortArm.TREATMENT, mem.getArm());
  }

  @Test
  void testCreate5050InvalidPercents() {
    Exception exception = assertThrows(
        IllegalArgumentException.class, 
        () -> TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", -1, 50));
    assertEquals("Control percent must be in the range [0, 50]", exception.getMessage());
    
    exception = assertThrows(
        IllegalArgumentException.class, 
        () -> TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", 51, 50));
    assertEquals("Control percent must be in the range [0, 50]", exception.getMessage());
    
    exception = assertThrows(
        IllegalArgumentException.class, 
        () -> TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", 50, -1));
    assertEquals("Treatment percent must be in the range [0, 50]", exception.getMessage());
    
    exception = assertThrows(
        IllegalArgumentException.class, 
        () -> TwoArmExperiment.create5050TwoArmExperimentConfig("HOLD_OUT", 50, 51));
    assertEquals("Treatment percent must be in the range [0, 50]", exception.getMessage());
  }

}
