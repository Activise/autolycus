package io.activise.autolycus.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.activise.autolycus.annotations.Pageable;
import io.activise.autolycus.util.ThreadUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Pager {
  private final Class<?> clazz;
  private final WebDriver webDriver;
  private int currentPage = 0;

  public static Pager of(Class<?> clazz, WebDriver webDriver) {
    return new Pager(clazz, webDriver);
  }

  public boolean nextPage() {
    if (!isPageable(clazz)) {
      return false;
    }
    currentPage++;

    var pageable = clazz.getAnnotation(Pageable.class);
    getNextPageButton(pageable).click();
    ThreadUtil.trySleep(500);
    return currentPage < pageable.maxPages();
  }

  private WebElement getNextPageButton(Pageable pageable) {
    return webDriver.findElement(By.xpath(pageable.value()));
  }

  private boolean isPageable(Class<?> clazz) {
    return clazz.isAnnotationPresent(Pageable.class);
  }

}
