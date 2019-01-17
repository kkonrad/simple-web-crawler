package kkonrad.simple.web.crawler;

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
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


// Should be parametric on PageDownloader implementations
public class E2ETestOnJavaDoc {

    // TODO use reflection
    private static final List<Class<?>> EXISTING_CODE_CLASSES = Lists.list(
            Application.class,
            WebPage.class,
            Link.class,
            SimpleCrawler.class,
            CrawlingOrchestrator.class
    );

    @Before
    public void setUp() {
        Assume.assumeTrue(isJavadocPageServed());
    }

    private boolean isJavadocPageServed() {
        try {
            URL url = new URL("http://localhost:8000/javadoc");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            return 200 == connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();// should be good enough
            return false;
        }
    }

    @Test
    public void testDownloadAndLinksDetection() {
        LinksCollectingCrawlingResultsProcessor resultsProcessor = Application.crawlPage("http://localhost:8000");
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