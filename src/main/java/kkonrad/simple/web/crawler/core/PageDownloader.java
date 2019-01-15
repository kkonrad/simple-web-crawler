package kkonrad.simple.web.crawler.core;

public interface PageDownloader {

    WebPage download(Link nextLink);
}
