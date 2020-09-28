package io.activise.autolycus.examples;

import io.activise.autolycus.annotations.BaseUrl;
import io.activise.autolycus.annotations.From;
import io.activise.autolycus.annotations.Regex;
import lombok.Getter;
import lombok.ToString;

@BaseUrl("https://app.tennis04.com/de/bludenz/buchungsplan")
@From("fc-content")
@Getter @ToString
public class TennisBooking {
  @From(".//div[@class='fc-time']/span")
  @Regex("(.*) - .*")
  private String startZeit;

  @From(".//div[@class='fc-time']/span")
  @Regex(".* - (.*)")
  private String endZeit;

  @From("fc-title")
  private String title;

}
