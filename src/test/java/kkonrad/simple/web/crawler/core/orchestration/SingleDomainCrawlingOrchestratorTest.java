package kkonrad.simple.web.crawler.core.orchestration;

import kkonrad.simple.web.crawler.core.Link;
import org.junit.Test;

import java.net.URI;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingleDomainCrawlingOrchestratorTest extends CrawlingOrchestratorTest<SingleDomainCrawlingOrchestrator> {

    private final static URI DOMAIN = URI.create(CrawlingOrchestratorTest.SAMPLES_DOMAIN);

    @Override
    protected SingleDomainCrawlingOrchestrator createOrchestrator() {
        return new SingleDomainCrawlingOrchestrator(DOMAIN);
    }


    @Test
    public void keepingLinksOfSameDomain() {
        orchestrator.addLink(LINK_1);
        assertThat(orchestrator.isDone(), is(false));
        assertThat(orchestrator.next(), is(LINK_1));
        assertThat(orchestrator.isDone(), is(true));
    }

    @Test
    public void domainFiltering() {
        orchestrator.addLink(new Link("http://not-cool-domain-at-all.com"));
        assertThat(orchestrator.isDone(), is(true));
    }
}
