package kkonrad.simple.web.crawler.core.orchestration;

import kkonrad.simple.web.crawler.core.Link;

import java.net.URI;

public class SingleDomainCrawlingOrchestrator extends SimpleCrawlingOrchestrator {

    private final URI domain;

    public SingleDomainCrawlingOrchestrator(URI domain) {
        super();
        this.domain = domain;
    }

    @Override
    protected void addLink(Link link) {
        if (isSameDomain(link)) {
            super.addLink(link);
        }
    }

    // Here we define what we mean by the same domain
    private boolean isSameDomain(Link link) {
        URI linkUri = URI.create(link.getTo());
        return linkUri.getScheme().equals(domain.getScheme())
                && linkUri.getHost().equals(domain.getHost())
                && linkUri.getPort() == domain.getPort();
    }
}
