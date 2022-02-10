package ai.promoted.java.client;

import java.util.concurrent.Executor;

/**
 * Runs submitted jobs immediately for unit testing.
 */
public class CurrentThreadExecutor implements Executor {
  public void execute(Runnable r) {
      r.run();
  }
}