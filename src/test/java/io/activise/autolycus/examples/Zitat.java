package io.activise.autolycus.examples;

import io.activise.autolycus.annotations.BaseUrl;
import io.activise.autolycus.annotations.From;
import io.activise.autolycus.annotations.Pageable;
import lombok.Data;

@BaseUrl("http://zitate.net/")
@From("//td[contains(@style,'459px')]")
@Pageable(value = "//a[@href='/']", maxPages = 3)
@Data
public class Zitat {
  @From("quote")
  private String content;

  @From("quote-btn")
  private String author;

}
