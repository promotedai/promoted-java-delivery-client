package ai.promoted.delivery.client;

/**
 * Sampler implementation for testing.
 */
public class FakeSampler implements Sampler {
  private boolean result;
  
  /**
   * Creates a fake sampler that always returns the desired result.
   * @param desiredResult the result that will be returned from a call to sampleRandom.
   */
  public FakeSampler(boolean desiredResult) {
    this.result = desiredResult;
  }
  
  @Override
  public boolean sampleRandom(float threshold) {
    return result;
  }

}
