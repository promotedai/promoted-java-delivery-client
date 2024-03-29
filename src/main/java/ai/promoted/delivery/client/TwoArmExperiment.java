package ai.promoted.delivery.client;

import ai.promoted.proto.event.CohortArm;
import ai.promoted.proto.event.CohortMembership;

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
   * @param cohortId name of the cohort id (experiment)
   * @param controlPercent percent of the total to activate into control, range [0, 50]
   * @param treatmentPercent percent of the total to activate into treatment, range [0, 50]
   * @return the configured experiment
   */
  public static TwoArmExperiment create5050TwoArmExperimentConfig(String cohortId, int controlPercent,
      int treatmentPercent) {
    if (controlPercent < 0 || controlPercent > 50) {
      throw new IllegalArgumentException("Control percent must be in the range [0, 50]");
    }
    if (treatmentPercent < 0 || treatmentPercent > 50) {
      throw new IllegalArgumentException("Treatment percent must be in the range [0, 50]");
    }
    return new TwoArmExperiment(cohortId, controlPercent, 50, treatmentPercent, 50);
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
  
  /**
   * Evaluates the experiment membership for a given user.
   * @param userId the user id to check
   * @return the correct cohort membership if the user is activated into the experiment, or null if not.
   */
  public CohortMembership checkMembership(String userId) {
    int hash = combineHash(userId.hashCode(), cohortIdHash);
    int bucket = Math.abs(hash) % numTotalBuckets;
    if (bucket < numActiveControlBuckets) {
      return CohortMembership.newBuilder().setCohortId(cohortId).setArm(CohortArm.CONTROL).build();
    }

    if (numControlBuckets <= bucket && bucket < numControlBuckets + numActiveTreatmentBuckets) {
      return CohortMembership.newBuilder().setCohortId(cohortId).setArm(CohortArm.TREATMENT).build();
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
