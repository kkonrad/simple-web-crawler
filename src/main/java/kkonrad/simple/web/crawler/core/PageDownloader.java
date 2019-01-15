package kkonrad.simple.web.crawler.core;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Could be interface but no clear vision for variance here
// Extracting links code is from jsoup official documentation
@Slf4j
public class PageDownloader {

    public WebPage download(Link nextLink) throws IOException {
        String nextUrl = nextLink.getTo();
        log.info("Downloading page at {}", nextUrl);
        try {
            Document document = Jsoup.connect(nextUrl).get();
            List<Link> links = getLinks(document);
            return new WebPage(links);
        } catch (IOException ex) {
            log.error("Failed to download page at {}", nextUrl, ex);
            return new WebPage(Collections.emptyList());
        }
    }

    private List<Link> getLinks(Document document) {
        return document.select("a[href]")
                .stream()
                .map(this::convertElementToLink)
                .collect(Collectors.toList());
    }

    private Link convertElementToLink(Element element) {
        return new Link(element.attr("abs:href"));
    }
}
