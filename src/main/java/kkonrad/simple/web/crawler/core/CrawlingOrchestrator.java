package kkonrad.simple.web.crawler.core;

import java.util.Collection;

public interface CrawlingOrchestrator {

    Link next(); // TODO: what if no more pages? null? Exception?
    void addNewLinks(Collection<Link> links);
    boolean isDone();

}
