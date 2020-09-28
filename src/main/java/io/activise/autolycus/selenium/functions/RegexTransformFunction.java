package io.activise.autolycus.selenium.functions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebElement;
import io.activise.autolycus.annotations.Regex;
import io.activise.autolycus.selenium.AnnotationFunction;

public class RegexTransformFunction implements AnnotationFunction<Regex, String> {
  @Override
  public String apply(AnnotationFunction.Context<Regex> context) {
    String elementText = ((WebElement) context.getTarget()).getAttribute("textContent");
    Pattern pattern = Pattern.compile(context.getAnnotation().value());
    Matcher matcher = pattern.matcher(elementText);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return elementText;
    }
  }

}
