package kkonrad.simple.web.crawler;

import kkonrad.simple.web.crawler.core.Crawler;
import kkonrad.simple.web.crawler.crawlers.SimpleCrawler;
import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.orchestration.SingleDomainCrawlingOrchestrator;
import kkonrad.simple.web.crawler.resultsprocessing.LinksCollectingCrawlingResultsProcessor;
import kkonrad.simple.web.crawler.threaded.ThreadedCrawler;
import kkonrad.simple.web.crawler.webprocessing.JSoupBasedPageDownloader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        String pageUrl = args[0];
        Link seedLink = new Link(pageUrl);
        URI domain = URI.create(pageUrl);

        crawlInSimpleMode(seedLink, domain);
//        crawlInThreadedMode(seedLink, domain);
    }

    public static LinksCollectingCrawlingResultsProcessor crawlInThreadedMode(Link seedLink, URI domain) {
        ExecutorService es = Executors.newScheduledThreadPool(3 * Runtime.getRuntime().availableProcessors());
        Crawler crawler = new ThreadedCrawler(
                new SingleDomainCrawlingOrchestrator(domain),
                new JSoupBasedPageDownloader(),
                es
        );

        LinksCollectingCrawlingResultsProcessor results = crawlPageAndPrintUrls(crawler, seedLink);
        es.shutdown();
        try {
            es.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public static LinksCollectingCrawlingResultsProcessor crawlInSimpleMode(Link seedLink, URI domain) {
        Crawler crawler = new SimpleCrawler(
                new SingleDomainCrawlingOrchestrator(domain),
                new JSoupBasedPageDownloader()
        );
        return crawlPageAndPrintUrls(crawler, seedLink);
    }

    public static LinksCollectingCrawlingResultsProcessor crawlPageAndPrintUrls(Crawler crawler, Link seedLink) {
        LinksCollectingCrawlingResultsProcessor resultsProcessor = new LinksCollectingCrawlingResultsProcessor();
        Instant start = Instant.now();
        crawler.crawl(seedLink, resultsProcessor);
        log.info("Crawler " + crawler.getClass() + " crawled for " + (Duration.between(start, Instant.now())));
        resultsProcessor.getLinks()
                .stream()
                .map(Link::getTo)
                .sorted()
                .forEach(System.out::println);
        return resultsProcessor;
    }
}
