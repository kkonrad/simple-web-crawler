package kkonrad.simple.web.crawler;

import kkonrad.simple.web.crawler.crawlers.SimpleCrawler;
import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.orchestration.SingleDomainCrawlingOrchestrator;
import kkonrad.simple.web.crawler.resultsprocessing.LinksCollectingCrawlingResultsProcessor;
import kkonrad.simple.web.crawler.webprocessing.JSoupBasedPageDownloader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        String pageUrl = args[0];
        crawlPageAndPrintUrls(pageUrl);
    }

    public static void crawlPageAndPrintUrls(String pageUrl) {
        LinksCollectingCrawlingResultsProcessor resultsProcessor = crawlPage(pageUrl);
        resultsProcessor.getLinks()
                .stream()
                .map(Link::getTo)
                .sorted()
                .forEach(System.out::println);
    }

    public static LinksCollectingCrawlingResultsProcessor crawlPage(String pageUrl) {
        Link seedLink = new Link(pageUrl);
        URI domain = URI.create(pageUrl);

        SimpleCrawler crawler = new SimpleCrawler(
                new SingleDomainCrawlingOrchestrator(domain),
                new JSoupBasedPageDownloader()
        );

        LinksCollectingCrawlingResultsProcessor resultsProcessor = new LinksCollectingCrawlingResultsProcessor();
        crawler.crawl(seedLink, resultsProcessor);
        return resultsProcessor;
    }
}
