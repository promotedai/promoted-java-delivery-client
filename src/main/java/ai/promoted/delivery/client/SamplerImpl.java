package ai.promoted.delivery.client;

import java.util.Random;

/**
 * Basic implementation of the Sampler interface.
 */
public class SamplerImpl implements Sampler {

  private Random rand;
  
  public SamplerImpl() {
    rand = new Random();
  }
  
  public SamplerImpl(Random rand) {
    this.rand = rand;
  }
  
  @Override
  public boolean sampleRandom(float threshold) {
    if (threshold >= 1) {
      return true;
    }
    if (threshold <= 0) {
      return false;
    }
    return rand.nextDouble() < threshold;
  }
}
