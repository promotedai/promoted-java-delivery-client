package ai.promoted.delivery.client;

/**
 * Algorithms that let us choose a subset of SDK traffic to process.
 */
public interface Sampler {
  /**
   * Simple random sampling that selects below the given threshold.
   * @param threshold the sampling percentage in the range [0, 1].
   * @returns true if we should sample in, false if we should sample out.
   */
  boolean sampleRandom(float threshold);
}
