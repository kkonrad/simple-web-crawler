package kkonrad.simple.web.crawler.resultsprocessing;

import kkonrad.simple.web.crawler.core.CrawlingResultsProcessor;
import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.core.WebPage;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Collects all found links. Keeps them in order as they were added for the first time.
 */
public class LinksCollectingCrawlingResultsProcessor implements CrawlingResultsProcessor {

    private final LinkedHashSet<Link> links = new LinkedHashSet<>();

    @Override
    public void addNewResult(WebPage webPage) {
        links.addAll(webPage.getLinks());
        // TODO: add also images, css etc.
    }

    // Probably this set should be copied but in our case object will be "read" after it is populated with data
    public Set<Link> getLinks() {
        return links;
    }
}
