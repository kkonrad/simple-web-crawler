package kkonrad.simple.web.crawler.core.orchestration;

public class SimpleCrawlingOrchestratorTest extends CrawlingOrchestratorTest<SimpleCrawlingOrchestrator> {
    @Override
    protected SimpleCrawlingOrchestrator createOrchestrator() {
        return new SimpleCrawlingOrchestrator();
    }
}
