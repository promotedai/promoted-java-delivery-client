/*
 * 
 */
package ai.promoted.delivery.client;

import ai.promoted.delivery.model.CohortMembership;

public interface ApplyTreatmentChecker {
  boolean shouldApplyTreatment(CohortMembership cohortMembership);
}
