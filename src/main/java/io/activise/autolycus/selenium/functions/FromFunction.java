package io.activise.autolycus.selenium.functions;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.activise.autolycus.annotations.From;
import io.activise.autolycus.selenium.ActionType;
import io.activise.autolycus.selenium.AnnotationFunction;

public class FromFunction implements AnnotationFunction<From, WebElement> {
  private static final String ACTION_SEPARATOR = "->";

  @Override
  public WebElement apply(AnnotationFunction.Context<From> context) {
    String[] actions = context.getAnnotation().value().split(ACTION_SEPARATOR);

    WebElement element = (WebElement) context.getTarget();

    for (String action : actions) {
      ActionType actionType = ActionType.determine(action);
      switch (actionType) {
        case BY_XPATH:
          element = element.findElement(By.xpath(action));
          break;
        case BY_SIMPLE_CLASSNAME:
          element = element.findElement(By.className(action));
          break;
        case CLICK_ROOT:
          context.getRootElement().click();
          context.getWebDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
          break;
        case CLICK_TARGET:
          break;
        default:
          break;
      }
    }

    return element;
  }

}
