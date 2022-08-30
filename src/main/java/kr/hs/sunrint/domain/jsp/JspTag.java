package kr.hs.sunrint.domain.jsp;

import java.util.HashMap;
import java.util.Map;

public class JspTag {
    private String tag;
    private Map<String, String> attributes;

    public JspTag(String tag) {
        this.tag = tag;
        attributes = new HashMap<>();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public void addAttribute(String attribute, String name) {
        attributes.put(attribute, name);
    }

    @Override
    public String toString() {
        return "JspTag{" +
                "tag='" + tag + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
