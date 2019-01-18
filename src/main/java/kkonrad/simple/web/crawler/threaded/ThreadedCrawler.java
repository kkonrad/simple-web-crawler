package kkonrad.simple.web.crawler.threaded;

import kkonrad.simple.web.crawler.core.Crawler;
import kkonrad.simple.web.crawler.core.CrawlingOrchestrator;
import kkonrad.simple.web.crawler.core.CrawlingResultsProcessor;
import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.core.PageDownloader;
import kkonrad.simple.web.crawler.core.WebPage;

import java.util.concurrent.ExecutorService;

/**
 * Quite ugly, but done to quickly see how it would play out if we were to assume that any
 * CrawlingOrchestrator or CrawlingResultsProcessor class can be used.
 * Should be simpler and more efficient if there were dedicated Orchestrator for this
 * <p>
 * Objects of this class are not thread safe (because assumption of orchestrator being field).
 */
public class ThreadedCrawler implements Crawler {

    private CrawlingOrchestrator orchestrator;
    private PageDownloader pageDownloader;

    private ExecutorService executorService;

    // if scheduled == processed -> finished crawling of what was scheduled, might be still something in orchestrator
    private int scheduled; // read only by one thread, no need for volatile
    private volatile int processed;

    public ThreadedCrawler(CrawlingOrchestrator orchestrator, PageDownloader pageDownloader, ExecutorService executorService) {
        this.orchestrator = orchestrator;
        this.pageDownloader = pageDownloader;
        this.executorService = executorService;
    }

    @Override
    public void crawl(Link seed, CrawlingResultsProcessor resultsProcessor) {
        scheduled = 0;
        processed = 0;

        Link nextLink = seed;
        while (nextLink != null) {
            Link finalNextLink = nextLink;
            scheduled++;
            executorService.submit(() -> processLink(finalNextLink, resultsProcessor));
            nextLink = nextLink();
        }
    }

    private synchronized Link nextLink() {
        if (scheduled == processed && orchestrator.isDone()) { // we are done
            return null;
        }
        while (scheduled != processed && orchestrator.isDone()) {
            // it is not done, just waits for Runnable to finish page processing,
            // "while" because there might be nothing more returned for crawling
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (scheduled == processed && orchestrator.isDone()) { // we might get done while waiting
            return null;
        }
        return orchestrator.next();
    }

    private void processLink(Link nextLink, CrawlingResultsProcessor resultsProcessor) {
        WebPage page = pageDownloader.download(nextLink);
        processPage(resultsProcessor, page);
    }

    private synchronized void processPage(CrawlingResultsProcessor resultsProcessor, WebPage page) {
        resultsProcessor.addNewResult(page);
        orchestrator.addNewLinks(page.getLinks());
        processed++;
        notify();
    }
}
