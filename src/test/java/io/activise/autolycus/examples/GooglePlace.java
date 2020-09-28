package io.activise.autolycus.examples;

import io.activise.autolycus.annotations.BaseUrl;
import io.activise.autolycus.annotations.From;
import io.activise.autolycus.annotations.Pageable;
import io.activise.autolycus.annotations.Regex;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@BaseUrl("https://www.google.de/maps/search/")
@From("section-result-content")
@Pageable(value = "//button[@jsaction='pane.paginationSection.nextPage']", maxPages = 3)
@Getter @Setter @ToString
public class GooglePlace {
  @From("section-result-title")
  private String name;

  @From("section-result-location")
  private String location;

  @From("section-result-num-ratings")
  @Regex("\\((.*)\\)")
  private int reviewCount;

  // @SeleniumFrom("#!clickRoot->//button[@data-item='authority']->#!click(//button[@jsaction='pane.place.backToList')")
  private String website;

}
