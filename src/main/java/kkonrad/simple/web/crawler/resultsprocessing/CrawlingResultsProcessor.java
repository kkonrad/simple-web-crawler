package kkonrad.simple.web.crawler.resultsprocessing;

import kkonrad.simple.web.crawler.core.WebPage;

public interface CrawlingResultsProcessor {

    void addNewResult(WebPage webPage);
}
