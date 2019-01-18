package kkonrad.simple.web.crawler;

import kkonrad.simple.web.crawler.core.Link;
import kkonrad.simple.web.crawler.resultsprocessing.LinksCollectingCrawlingResultsProcessor;

import java.net.URI;

public class E2EForSimpleCrawlerOnJavaDoc extends E2ECrawlerTestOnJavaDoc {

    @Override
    protected LinksCollectingCrawlingResultsProcessor doCrawling(Link javadocLink, URI javadocDomain) {
        return Application.crawlInSimpleMode(javadocLink, javadocDomain);
    }
}
