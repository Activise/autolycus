package io.activise.autolycus.examples;

import io.activise.autolycus.annotations.BaseUrl;
import io.activise.autolycus.annotations.From;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@BaseUrl("http://muensterhack.de/ideen/")
@From("//tbody[1]//tr")
@ToString
@Getter @Setter
public class MuensterhackIdea {
  @From(".//td[1]")
  private String author;

  @From(".//td[2]")
  private String content;

}
