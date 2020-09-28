package io.activise.autolycus.selenium;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.activise.autolycus.Scraper;
import io.activise.autolycus.annotations.BaseUrl;
import io.activise.autolycus.annotations.From;
import io.activise.autolycus.annotations.Regex;
import io.activise.autolycus.selenium.functions.FromFunction;
import io.activise.autolycus.selenium.functions.RegexTransformFunction;
import io.activise.autolycus.util.ThreadUtil;

public class AnnotationBasedSeleniumScraper implements Scraper {
  private static final Map<Class<? extends Annotation>, AnnotationFunction<?, ?>> ANNOTATION_TO_FUNCTION;

  static {
    Map<Class<? extends Annotation>, AnnotationFunction<?, ?>> tempAnnotationToFunction =
        new HashMap<>();
    tempAnnotationToFunction.put(From.class, new FromFunction());
    tempAnnotationToFunction.put(Regex.class, new RegexTransformFunction());
    ANNOTATION_TO_FUNCTION = Collections.unmodifiableMap(tempAnnotationToFunction);
  }

  private WebDriver webDriver;
  private ClassProcessor classProcessor;

  public AnnotationBasedSeleniumScraper(WebDriver webDriver) {
    this.webDriver = webDriver;
    var fieldProcessor = WebElementTransformer.of(ANNOTATION_TO_FUNCTION, webDriver);
    classProcessor = ClassProcessor.of(fieldProcessor);
  }

  public static AnnotationBasedSeleniumScraper createDefault() {
    return new AnnotationBasedSeleniumScraper(WebDrivers.createDefault());
  }

  @Override
  public <T> List<T> scrape(String input, Class<T> clazz) {
    if (!isSupported(clazz)) {
      throw new RuntimeException("The type " + clazz + " is not supported for this scraper.");
    }

    visitBaseUrl(input, clazz);

    List<T> instances = new ArrayList<>();
    Pager pager = Pager.of(clazz, webDriver);
    do {
      for (WebElement rootElement : collectRootElements(clazz)) {
        var newInstance = classProcessor.process(clazz, rootElement);
        instances.add(newInstance);
      }
    } while(pager.nextPage());

    return instances;
  }

  private void visitBaseUrl(String input, Class<?> clazz) {
    String baseUrl = clazz.getAnnotation(BaseUrl.class).value();
    webDriver.get(baseUrl + input);
    ThreadUtil.trySleep(2000);
  }

  private List<WebElement> collectRootElements(Class<?> clazz) {
    String baseFrom = clazz.getAnnotation(From.class).value();
    ActionType actionType = ActionType.determine(baseFrom);

    switch (actionType) {
      case BY_SIMPLE_CLASSNAME:
        return webDriver.findElements(By.className(baseFrom));
      case BY_XPATH:
        return webDriver.findElements(By.xpath(baseFrom));
      default:
        return Collections.emptyList();
    }
  }

  @Override
  public boolean isSupported(Class<?> clazz) {
    return clazz.isAnnotationPresent(BaseUrl.class);
  }

}
