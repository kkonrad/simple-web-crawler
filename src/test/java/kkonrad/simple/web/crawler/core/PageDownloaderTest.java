package kkonrad.simple.web.crawler.core;

import kkonrad.simple.web.crawler.webprocessing.JSoupBasedPageDownloader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;


// Should be parametric on PageDownloader implementations
public class PageDownloaderTest {

    private PageDownloader pageDownloader;

    @Before
    public void init() {
        pageDownloader = new JSoupBasedPageDownloader();
    }

    @Test
    public void testDownloadAndLinksDetection() {
        Link sampleLink = new Link("http://traprooms.pl");
        WebPage page = pageDownloader.download(sampleLink);
        List<Link> links = page.getLinks();
        assertThat(links, hasItem(new Link("https://www.facebook.com/traprooms")));
        List<Link> otherLinks = page.getOthers();
        assertThat(otherLinks, hasItem(new Link("http://traprooms.pl/css/own.css")));
        assertThat(otherLinks, hasItem(new Link("http://traprooms.pl/img/logo.png")));
    }
}