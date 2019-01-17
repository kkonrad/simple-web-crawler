package kkonrad.simple.web.crawler.orchestration;

import kkonrad.simple.web.crawler.core.CrawlingOrchestrator;
import kkonrad.simple.web.crawler.core.Link;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;


// JUnit 4 has parametrized tests but they don't have good reputation
public abstract class CrawlingOrchestratorTest<T extends CrawlingOrchestrator> {

    protected static final String SAMPLES_DOMAIN = "http://cool-website.com";

    protected static final Link LINK_1 = new Link(SAMPLES_DOMAIN + "/aaaaa");
    protected static final Link LINK_2 = new Link(SAMPLES_DOMAIN + "/bbbbb");

    protected static final List<Link> SAMPLE_LINKS = Lists.list(LINK_1, LINK_2);


    protected T orchestrator;

    protected abstract T createOrchestrator();

    @Before
    public void setUp() throws Exception {
        orchestrator = createOrchestrator();
    }

    @Test
    public void usageFlow() {
        assertThat(orchestrator.isDone(), is(true));

        orchestrator.addNewLinks(SAMPLE_LINKS);
        assertThat(orchestrator.isDone(), is(false));

        Link link1 = orchestrator.next();
        assertThat(SAMPLE_LINKS, hasItem(link1));
        assertThat(orchestrator.isDone(), is(false));

        Link link2 = orchestrator.next();
        assertThat(SAMPLE_LINKS, hasItem(link2));
        assertThat(orchestrator.isDone(), is(true));

        assertThat(link1, not(link2));
    }
}