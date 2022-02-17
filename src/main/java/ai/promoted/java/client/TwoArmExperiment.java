package ai.promoted.java.client;

import ai.promoted.delivery.model.CohortArm;
import ai.promoted.delivery.model.CohortMembership;

/**
 * Represents a two arm Experiment configuration.
 *
 * WARNING - while ramping up an experiment, do not change
 * numControlBuckets or numTreatmentBuckets.  This will
 * likely produce bad results.
 *
 * For the buckets, the treatment buckets are after the
 * control buckets.
 */
public class TwoArmExperiment {
  /**
   * Name of cohort.
   */
  private final String cohortId;
  
  private final int cohortIdHash;
  
  /**
   * Number of the numControlBuckets that are active.
   */
  private final int numActiveControlBuckets;
  
  /**
   * Number of control buckets.
   */
  private final int numControlBuckets;
  
  /**
   * Number of the numTreatmentBuckets that are active.
   */
  private final int numActiveTreatmentBuckets;
  
  /**
   * Number of treatment buckets.
   */
  private final int numTreatmentBuckets;
  
  private final int numTotalBuckets;

  /**
   * Factory method for a 50/50 experiment.
   * @return the configured experiment
   */
  public static TwoArmExperiment create5050TwoArmExperimentConfig(String cohortId, int controlPercent,
      int treatmentPercent) {
    if (controlPercent < 0 || controlPercent > 100) {
      throw new IllegalArgumentException("Control percent must be in the range [0, 100]");
    }
    if (treatmentPercent < 0 || treatmentPercent > 100) {
      throw new IllegalArgumentException("Treatment percent must be in the range [0, 100]");
    }
    return new TwoArmExperiment(cohortId, controlPercent, 100, treatmentPercent, 100);
  }
  
  /**
   * Creates a two-arm experiment config with the given parameters.
   * @param cohortId the cohort (experiment) name
   * @param numActiveControlBuckets number of active control buckets
   * @param numControlBuckets total number of control buckets
   * @param numActiveTreatmentBuckets number of active treatment buckets
   * @param numTreatmentBuckets total number of treatment buckets
   */
  public TwoArmExperiment(String cohortId, int numActiveControlBuckets, int numControlBuckets,
      int numActiveTreatmentBuckets, int numTreatmentBuckets) {
    if (cohortId == null || cohortId.isBlank()) {
      throw new IllegalArgumentException("Cohort ID must be non-empty");
    }
    
    if (numControlBuckets < 0) {
      throw new IllegalArgumentException("Control buckets must be positive");
    }
    
    if (numTreatmentBuckets < 0) {
      throw new IllegalArgumentException("Treatment buckets must be positive");
    }
    
    if (numActiveControlBuckets < 0 || numActiveControlBuckets > numControlBuckets) {
      throw new IllegalArgumentException("Active control buckets must be between 0 and the total number of control buckets");
    }
    
    if (numActiveTreatmentBuckets < 0 || numActiveTreatmentBuckets > numTreatmentBuckets) {
      throw new IllegalArgumentException("Active treatment buckets must be between 0 and the total number of treatment buckets");
    }
    
    this.cohortId = cohortId;
    this.cohortIdHash = cohortId.hashCode();
    this.numActiveControlBuckets = numActiveControlBuckets;
    this.numControlBuckets = numControlBuckets;
    this.numActiveTreatmentBuckets = numActiveTreatmentBuckets;
    this.numTreatmentBuckets = numTreatmentBuckets;
    this.numTotalBuckets = numTreatmentBuckets + numControlBuckets;
  }

  public String getCohortId() {
    return cohortId;
  }

  public int getNumActiveControlBuckets() {
    return numActiveControlBuckets;
  }

  public int getNumControlBuckets() {
    return numControlBuckets;
  }

  public int getNumActiveTreatmentBuckets() {
    return numActiveTreatmentBuckets;
  }

  public int getNumTreatmentBuckets() {
    return numTreatmentBuckets;
  }
  
  public CohortMembership checkMembership(String userId) {
    int hash = combineHash(userId.hashCode(), cohortIdHash);
    int bucket = Math.abs(hash) % numTotalBuckets;
    if (bucket < numActiveControlBuckets) {
      return new CohortMembership().cohortId(cohortId).arm(CohortArm.CONTROL);
    }

    if (numControlBuckets <= bucket && bucket < numControlBuckets + numActiveTreatmentBuckets) {
      return new CohortMembership().cohortId(cohortId).arm(CohortArm.TREATMENT);
    }
    return null;
  }
  
  /**
   * Returns a simple combined hash of two other hashes.  From Effective Java.
   */
  private int combineHash(int hash1, int hash2) {
    int hash = 17;
    hash = hash * 31 + hash1;
    hash = hash * 31 + hash2;
    return hash;
  }
}
