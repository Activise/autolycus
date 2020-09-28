package io.activise.autolycus.selenium;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WebElementTransformer {
  private final Map<Class<? extends Annotation>, AnnotationFunction<?, ?>> annotationToFunction;
  private final WebDriver webDriver;

  public static WebElementTransformer of(
      Map<Class<? extends Annotation>, AnnotationFunction<?, ?>> annotationToFunction,
      WebDriver webDriver) {
    return new WebElementTransformer(annotationToFunction, webDriver);
  }

  /**
   * Transforms a root-element to a target output value specified by the order of the applied\n
   * transformation annotations on the target field
   *
   * @param rootElement The source for the transformation
   * @param field       The target field that has the transformation annotations
   * @return The result of all applied annotation functions
   */
// @formatter:off
  public Object process(WebElement rootElement, Field field) {
    Object result = rootElement;

    for (Annotation annotation : field.getDeclaredAnnotations()) {
      if (!annotationToFunction.containsKey(annotation.annotationType())) {
        continue;
      }
      var function = annotationToFunction.get(annotation.annotationType());
      try {
        var context = new AnnotationFunction.Context(annotation, webDriver, rootElement, result);
        result = function.apply(context);
      } catch (Exception e) {
        System.out.println(
          String.format("Failed to apply '%s' to field '%s' on class '%s'",
          function.getClass().getSimpleName(),
          field.getName(),
          field.getDeclaringClass().getSimpleName()
        ));
        break;
      }
    }
    return result;
  }
// @formatter:on
}
