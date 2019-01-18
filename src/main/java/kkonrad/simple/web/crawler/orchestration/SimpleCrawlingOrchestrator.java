package kkonrad.simple.web.crawler.orchestration;

import kkonrad.simple.web.crawler.core.CrawlingOrchestrator;
import kkonrad.simple.web.crawler.core.Link;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Provides BFS like crawling through websites
 */
public class SimpleCrawlingOrchestrator implements CrawlingOrchestrator {

    private final Queue<Link> enqueuedLinks = new ArrayDeque<>();
    private final Set<Link> noLongerRelevant = new HashSet<>();

    @Override
    public Link next() {
        return enqueuedLinks.remove();
    }

    @Override
    public void addNewLinks(Collection<Link> links) {
        links.forEach(this::addLink);
    }

    protected void addLink(Link link) {
        if (noLongerRelevant.add(link)) {
            enqueuedLinks.add(link);
        }
    }

    @Override
    public boolean isDone() {
        return enqueuedLinks.isEmpty();
    }
}
