package kkonrad.simple.web.crawler.orchestration;

import kkonrad.simple.web.crawler.core.Link;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
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
        try {
            URI linkUri = new URI(link.getTo());
            return linkUri.getScheme().equals(domain.getScheme())
                    && linkUri.getHost().equals(domain.getHost())
                    && linkUri.getPort() == domain.getPort();
        } catch (URISyntaxException e) {
            log.warn("Skipping: Could not parse link " + link.getTo());
            return false;
        }
    }
}
