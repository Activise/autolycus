package io.activise.autolycus.selenium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.activise.autolycus.Scraper;
import io.activise.autolycus.annotations.BaseUrl;
import io.activise.autolycus.annotations.From;
import io.activise.autolycus.util.ThreadUtil;

public class AnnotationBasedSeleniumScraper implements Scraper {
  private SeleniumScraperConfiguration configuration;
  private WebDriver webDriver;
  private ClassProcessor classProcessor;

  public AnnotationBasedSeleniumScraper(SeleniumScraperConfiguration configuration, WebDriver webDriver, ClassProcessor classProcessor) {
    this.configuration = configuration;
    this.webDriver = webDriver;
    this.classProcessor = classProcessor;
  }

  public static AnnotationBasedSeleniumScraper createDefault() {
    var configuration = SeleniumScraperConfiguration.DEFAULT;
    var webDriver = WebDrivers.createDefault();
    var fieldProcessor = WebElementTransformer.of(configuration, webDriver);
    var classProcessor = ClassProcessor.of(fieldProcessor);

    return new AnnotationBasedSeleniumScraper(configuration, webDriver, classProcessor);
  }

  @Override
  public <T> List<T> scrape(String input, Class<T> mappedClass) {
    if (!isSupported(mappedClass)) {
      throw new RuntimeException("The type " + mappedClass + " is not supported for this scraper.");
    }

    visitBaseUrl(input, mappedClass);

    List<T> instances = new ArrayList<>();
    Pager pager = Pager.of(mappedClass, webDriver, configuration);
    do {
      for (WebElement rootElement : collectRootElements(mappedClass)) {
        var newInstance = classProcessor.process(mappedClass, rootElement);
        instances.add(newInstance);
      }
    } while(pager.nextPage());

    return instances;
  }

  private void visitBaseUrl(String input, Class<?> mappedClass) {
    String baseUrl = mappedClass.getAnnotation(BaseUrl.class).value();
    webDriver.get(baseUrl + input);
    ThreadUtil.trySleep(configuration.getInitialSleep());
  }

  private List<WebElement> collectRootElements(Class<?> mappedClass) {
    String baseFrom = mappedClass.getAnnotation(From.class).value();
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
