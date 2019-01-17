package kkonrad.simple.web.crawler.core;

public interface Crawler {

    void crawl(Link seed, CrawlingResultsProcessor resultsProcessor);
}
