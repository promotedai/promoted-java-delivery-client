/*
 * 
 */
package ai.promoted.java.client;

import ai.promoted.delivery.model.CohortMembership;

public interface ApplyTreatmentChecker {
  boolean shouldApplyTreatment(CohortMembership cohortMembership);
}
