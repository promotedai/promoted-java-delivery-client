/*
 * 
 */
package ai.promoted.delivery.client;

import ai.promoted.proto.event.CohortMembership;

public interface ApplyTreatmentChecker {
  boolean shouldApplyTreatment(CohortMembership cohortMembership);
}
