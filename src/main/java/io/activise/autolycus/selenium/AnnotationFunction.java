package io.activise.autolycus.selenium;

import java.lang.annotation.Annotation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AnnotationFunction<T extends Annotation, R> {
  public R apply(Context<T> context);

  @Getter
  @AllArgsConstructor
  public static class Context<T extends Annotation> {
    private T annotation;
    private WebDriver webDriver;
    private WebElement rootElement;
    private Object target;

    public Class<?> getTargetType() {
      return target.getClass();
    }
  }
}
