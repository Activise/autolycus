package io.activise.autolycus.selenium;

import java.lang.reflect.Field;
import org.openqa.selenium.WebElement;
import io.activise.autolycus.annotations.From;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassProcessor {
  private final WebElementTransformer fieldProcessor;

  public static ClassProcessor of(WebElementTransformer fieldProcessor) {
    return new ClassProcessor(fieldProcessor);
  }

  /**
   * Transforms a root-element to its class equivalent specified in the first parameter
   *
   * @param <T>
   * @param clazz The class of the target instance
   * @param rootElement The element to transform
   * @return
   */
  public <T> T process(Class<T> clazz, WebElement rootElement) {
    T newInstance = createInstance(clazz);
    for (Field field : clazz.getDeclaredFields()) {
      if (!field.isAnnotationPresent(From.class)) {
        continue;
      }

      Object result = fieldProcessor.process(rootElement, field);
      if (result == rootElement) {
        return newInstance;
      }

      result = webElementToTextIfNeeded(result);

      // TODO: Don't do it like this
      if (field.getType() == int.class) {
        setField(newInstance, field, Integer.parseInt(((String) result).replaceAll("\\.", "")));
      } else {
        setField(newInstance, field, result);
      }
    }

    return newInstance;
  }

  private <T> T createInstance(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Object webElementToTextIfNeeded(Object result) {
    if (result instanceof WebElement) {
      // Use "getAttribute" because "getText" doesn't give an invisible value
      result = ((WebElement) result).getAttribute("textContent");
    }
    return result;
  }

  private void setField(Object instance, Field field, Object value) {
    field.setAccessible(true);
    try {
      field.set(instance, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
