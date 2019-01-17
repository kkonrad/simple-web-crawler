package kkonrad.simple.web.crawler.orchestration;

public class SimpleCrawlingOrchestratorTest extends CrawlingOrchestratorTest<SimpleCrawlingOrchestrator> {
    @Override
    protected SimpleCrawlingOrchestrator createOrchestrator() {
        return new SimpleCrawlingOrchestrator();
    }
}
