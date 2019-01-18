package kkonrad.simple.web.crawler;

import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.resultsprocessing.LinksCollectingCrawlingResultsProcessor;
import org.junit.Test;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class E2EForThreadedCrawlerOnJavaDoc extends E2ECrawlerTestOnJavaDoc {
    @Override
    protected LinksCollectingCrawlingResultsProcessor doCrawling(Link javadocLink, URI javadocDomain) {
        return Application.crawlInThreadedMode(javadocLink, javadocDomain);
    }

    @Test
    public void checkCorrectness() {
        LinksCollectingCrawlingResultsProcessor threadedResults = Application.crawlInThreadedMode(JAVADOC_LINK, JAVADOC_DOMAIN);
        LinksCollectingCrawlingResultsProcessor simpleResults = Application.crawlInSimpleMode(JAVADOC_LINK, JAVADOC_DOMAIN);

        List<String> linksThreaded = resultsToList(threadedResults);
        List<String> linksSimple = resultsToList(simpleResults);
        assertThat(linksThreaded, is(linksSimple));
    }

    private List<String> resultsToList(LinksCollectingCrawlingResultsProcessor results) {
        return results.getLinks()
                .stream()
                .map(Link::getTo)
                .sorted()
                .collect(Collectors.toList());
    }
}
