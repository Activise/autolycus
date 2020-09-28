# Autolycus
## A Lib to scrape JavaScript-based pages using selenium

**Examples**
- Scraper

      Scraper scraper = new AnnotationBasedSeleniumScraper.createDefault();
	  List<GooglePlace> scrapedThings = scraper.scrape("restaurants in new york", GooglePlace.class);

 - GooglePlace

       @BaseUrl("https://www.google.de/maps/search/")
       @From("section-result-content")
       @Pageable(value = "//button[@jsaction='pane.paginationSection.nextPage'", maxPages = 3)
       public class GooglePlace { 
         @From("section-result-title")
         private String  name;
       		
         @From("section-result-location")
         private String location;

         @From("section-result-num-ratings")
         @Regex("\\((.*)\\)")
         private int reviewCount;
       }
- TennisBooking

      @BaseUrl("https://app.tennis04.com/de/bludenz/buchungsplan")
      @From("fc-content")
      public class TennisBooking {
      @From(".//div[@class='fc-time']/span")
        @Regex("(.*) - .*")
        private String startZeit;
		    
        @From(".//div[@class='fc-time']/span")
        @Regex(".* - (.*)")
        private String endZeit;
		    
        @From("fc-title")
        private  String  title;
      }
