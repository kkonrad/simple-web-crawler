package kkonrad.simple.web.crawler.core;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class PageDownloaderTest {

    private PageDownloader pageDownloader;

    @Before
    public void init() {
        pageDownloader = new PageDownloader();
    }

    @Test
    public void testDownloadAndLinksDetection() throws IOException {
        Link sampleLink = new Link("http://traprooms.pl");
        WebPage page = pageDownloader.download(sampleLink);
        List<Link> links = page.getLinks();
        assertThat(links, hasItem(new Link("https://www.facebook.com/traprooms")));
    }
}