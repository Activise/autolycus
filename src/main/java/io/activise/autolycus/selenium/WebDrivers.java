package io.activise.autolycus.selenium;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDrivers {
  private static final String DRIVER_PATH = "/usr/lib/chromium-browser/chromedriver";
  private static final String BINARY_PATH = "/usr/lib/chromium-browser/chromium-browser";

  private static List<String> DEFAULT_ARGUMENTS = Collections.unmodifiableList(Arrays.asList(
    "--headless",
    "--disable-gpu",
    "--window-size=1920,1080",
    "--no-sandbox",
    "--disable-dev-shm-usage"
  ));

  static {
    System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
  }

  public static WebDriver createDefault() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments(DEFAULT_ARGUMENTS);
    options.setBinary(BINARY_PATH);
    return new ChromeDriver(options);
  }

}
