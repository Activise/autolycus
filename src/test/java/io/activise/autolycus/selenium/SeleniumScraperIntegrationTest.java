package io.activise.autolycus.selenium;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import io.activise.autolycus.Scraper;
import io.activise.autolycus.examples.GooglePlace;
import io.activise.autolycus.examples.MuensterhackIdea;
import io.activise.autolycus.examples.TennisBooking;
import io.activise.autolycus.examples.Zitat;
import io.activise.autolycus.selenium.AnnotationBasedSeleniumScraper;

@Disabled("Takes to long to finish")
public class SeleniumScraperIntegrationTest {
  private static Scraper annotationBasedScraper;

  @BeforeAll
  public static void setUp() {
    annotationBasedScraper = AnnotationBasedSeleniumScraper.createDefault();
  }

  @Test
  public void scrapeGoogle() {
    scrapeAndPrint("restaurants in berlin", GooglePlace.class);
  }

  @Test
  public void scrapeMuensterhackIdea() {
    scrapeAndPrint("4", MuensterhackIdea.class);
  }

  @Test
  public void scrapeTennisBooking() {
    scrapeAndPrint("", TennisBooking.class);
  }

  @Test
  public void scrapeZitat() {
    scrapeAndPrint("", Zitat.class);
  }

  private void scrapeAndPrint(String input, Class<?> clazz) {
    long savedTime = System.currentTimeMillis();
    List<?> scrapedThings = annotationBasedScraper.scrape(input, clazz);
    System.out.println("Took " + (System.currentTimeMillis() - savedTime) + " for " + scrapedThings.size());
    scrapedThings.forEach(System.out::println);
  }

}
