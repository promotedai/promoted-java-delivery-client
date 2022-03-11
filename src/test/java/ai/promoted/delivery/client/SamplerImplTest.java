package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SamplerImplTest {

  @Test
  void testMaxThreshold() {
    assertTrue(new SamplerImpl().sampleRandom(1));
  }
  
  @Test
  void testMinThreshold() {
    assertFalse(new SamplerImpl().sampleRandom(0));
  }

  @Test
  void testRandomness() {
    Random rand = new Random(0);
    SamplerImpl sampler = new SamplerImpl(rand);
    assertFalse(sampler.sampleRandom(0.5f));
    assertTrue(sampler.sampleRandom(0.5f));
  }
}
