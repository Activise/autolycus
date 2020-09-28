package io.activise.autolycus.util;

public class ThreadUtil {
  private ThreadUtil() {
  }

  public static void trySleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
    }
  }

}
