package kkonrad.simple.web.crawler;

import kkonrad.simple.web.crawler.core.Crawler;
import kkonrad.simple.web.crawler.crawlers.SimpleCrawler;
import kkonrad.simple.web.crawler.core.CrawlingOrchestrator;
import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.core.WebPage;
import kkonrad.simple.web.crawler.resultsprocessing.LinksCollectingCrawlingResultsProcessor;
import org.assertj.core.util.Lists;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


// Should be parametric on PageDownloader implementations
public abstract class E2ECrawlerTestOnJavaDoc {

    protected static final String JAVADOC_URL = "http://localhost:8000";
    protected static final Link JAVADOC_LINK = new Link(JAVADOC_URL);
    protected static final URI JAVADOC_DOMAIN = URI.create(JAVADOC_URL);

    // TODO use reflection
    private static final List<Class<?>> EXISTING_CODE_CLASSES = Lists.list(
            Application.class,
            WebPage.class,
            Link.class,
            SimpleCrawler.class,
            CrawlingOrchestrator.class
    );

    protected abstract LinksCollectingCrawlingResultsProcessor doCrawling(Link javadocLink, URI javadocDomain);

    @Before
    public void setUp() {
        Assume.assumeTrue(isJavadocPageServed());
    }

    private boolean isJavadocPageServed() {
        try {
            URL url = new URL(JAVADOC_URL + "/javadoc");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            return 200 == connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();// should be good enough
            return false;
        }
    }

    @Test
    public void testDownloadAndLinksDetection() {
        LinksCollectingCrawlingResultsProcessor resultsProcessor = doCrawling(JAVADOC_LINK, JAVADOC_DOMAIN);
        List<String> foundLinks = resultsProcessor.getLinks()
                .stream()
                .map(Link::getTo)
                .collect(Collectors.toList());

        for (Class c: EXISTING_CODE_CLASSES) {
            assertThat(
                    foundLinks.stream()
                            .anyMatch(link -> link.endsWith(c.getSimpleName() + ".html")),
                    is(true));
        }
    }
}