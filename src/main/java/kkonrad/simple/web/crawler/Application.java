package kkonrad.simple.web.crawler;

import kkonrad.simple.web.crawler.core.Crawler;
import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.core.PageDownloader;
import kkonrad.simple.web.crawler.core.orchestration.SimpleCrawlingOrchestrator;
import kkonrad.simple.web.crawler.core.resultsprocessing.LinksCollectingCrawlingResultsProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Comparator;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);

        String pageUrl = args[0];
        crawlPage(pageUrl);
    }

    public static void crawlPage(String pageUrl) throws IOException {
        Link seedLink = new Link(pageUrl);

        Crawler crawler = new Crawler(
                new SimpleCrawlingOrchestrator(),
                new PageDownloader()
        );

        LinksCollectingCrawlingResultsProcessor resultsProcessor = new LinksCollectingCrawlingResultsProcessor();
        crawler.crawl(seedLink, resultsProcessor);

        resultsProcessor.getLinks()
                .stream()
                .map(Link::getTo)
                .sorted()
                .forEach(System.out::println);
    }
}
