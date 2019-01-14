package kkonrad.simple.web.crawler.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// Could be interface but no clear vision for variance here
// Extracting links code is from jsoup official documentation
public class PageDownloader {

    public WebPage download(Link nextLink) throws IOException {
        Document document = Jsoup.connect(nextLink.getTo()).get();
        List<Link> links = getLinks(document);
        return new WebPage(links);
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
