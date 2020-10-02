package io.activise.autolycus.selenium;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import io.activise.autolycus.annotations.From;
import io.activise.autolycus.annotations.Regex;
import io.activise.autolycus.selenium.functions.FromFunction;
import io.activise.autolycus.selenium.functions.RegexTransformFunction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeleniumScraperConfiguration {
  public static final SeleniumScraperConfiguration DEFAULT = new SeleniumScraperConfiguration();

  private final Map<Class<? extends Annotation>, AnnotationFunction<?, ?>> annotationFunctions = new HashMap<>();
  private int initialSleep;
  private int sleepPerPage;

  public SeleniumScraperConfiguration() {
    setInitialSleep(2000);
    setSleepPerPage(500);
    addDefaultAnnotationFunctions();
  }

  public void addDefaultAnnotationFunctions() {
    annotationFunctions.put(From.class, new FromFunction());
    annotationFunctions.put(Regex.class, new RegexTransformFunction());
  }

  public AnnotationFunction<?, ?> getAnnotationFunction(Class<? extends Annotation> annotationClass) {
    return annotationFunctions.get(annotationClass);
  }

}
