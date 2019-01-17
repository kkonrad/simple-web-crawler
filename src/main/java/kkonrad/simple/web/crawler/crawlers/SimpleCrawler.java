package kkonrad.simple.web.crawler.crawlers;

import kkonrad.simple.web.crawler.core.Crawler;
import kkonrad.simple.web.crawler.core.CrawlingOrchestrator;
import kkonrad.simple.web.crawler.core.CrawlingResultsProcessor;
import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.core.PageDownloader;
import kkonrad.simple.web.crawler.core.WebPage;

import java.util.Collections;

public class SimpleCrawler implements Crawler {

    private CrawlingOrchestrator orchestrator;
    private PageDownloader pageDownloader;

    public SimpleCrawler(CrawlingOrchestrator orchestrator, PageDownloader pageDownloader) {
        this.orchestrator = orchestrator;
        this.pageDownloader = pageDownloader;
    }

    @Override
    public void crawl(Link seed, CrawlingResultsProcessor resultsProcessor) {
        orchestrator.addNewLinks(Collections.singletonList(seed));
        while (!orchestrator.isDone()) {
            Link nextLink = orchestrator.next();
            WebPage page = pageDownloader.download(nextLink);
            resultsProcessor.addNewResult(page);
            orchestrator.addNewLinks(page.getLinks());
        }
    }

}
