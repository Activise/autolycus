package io.activise.autolycus;

import java.util.List;

public interface Scraper {
  <T> List<T> scrape(String input, Class<T> mappedClass);

  boolean isSupported(Class<?> clazz);

}
