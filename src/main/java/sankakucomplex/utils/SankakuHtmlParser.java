package sankakucomplex.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class SankakuHtmlParser {

    private final static String IMG_BASE_URL = "https://cs.sankakucomplex.com/data/";
    private final static String IMG_TAG_BASE = "<img class=preview src=\"";
    
    private String sankakuHTMLSrc;
    private List<String> urls = new ArrayList<>();
    
    private final Function<String, String> PREVIEW_TO_FULL_IMG_URL =
        previewImgUrl -> IMG_BASE_URL.concat(previewImgUrl.split("\\/preview\\/")[1]);
    
    private final Function<String, String> SPAN_HREF_FILTER = 
       spanField -> spanField.substring(
                spanField.lastIndexOf(IMG_TAG_BASE)
            )
            .substring(IMG_TAG_BASE.length())
            .split("\"")[0]
            .substring(2);
    
    public SankakuHtmlParser(String sankakuHTMLSrc) throws Exception{
        this.sankakuHTMLSrc = sankakuHTMLSrc;
        
        Arrays.asList(sankakuHTMLSrc.split("\\n")).stream()
            .filter(str -> str.startsWith("<span class=\"thumb blacklisted\""))
            .map(SPAN_HREF_FILTER)
            .map(PREVIEW_TO_FULL_IMG_URL)
            .forEach(urls::add);
    }

    public String getSankakuHTMLSrc() {
        return sankakuHTMLSrc;
    }

    public void setSankakuHTMLSrc(String sankakuHTMLSrc) {
        this.sankakuHTMLSrc = sankakuHTMLSrc;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
