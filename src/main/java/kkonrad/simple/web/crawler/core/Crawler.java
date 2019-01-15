package kkonrad.simple.web.crawler.core;

import kkonrad.simple.web.crawler.core.resultsprocessing.CrawlingResultsProcessor;

import java.io.IOException;
import java.util.Collections;

public class Crawler {

    private CrawlingOrchestrator orchestrator;
    private PageDownloader pageDownloader;

    public Crawler(CrawlingOrchestrator orchestrator, PageDownloader pageDownloader) {
        this.orchestrator = orchestrator;
        this.pageDownloader = pageDownloader;
    }

    public void crawl(Link seed, CrawlingResultsProcessor resultsProcessor) throws IOException {
        orchestrator.addNewLinks(Collections.singletonList(seed));
        while (!orchestrator.isDone()) {
            Link nextLink = orchestrator.next();
            WebPage page = pageDownloader.download(nextLink);
            resultsProcessor.addNewResult(page);
            orchestrator.addNewLinks(page.getLinks());
        }
    }

}
