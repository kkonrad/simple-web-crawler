package kkonrad.simple.web.crawler.core;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WebPage {

    private List<Link> links;

}
