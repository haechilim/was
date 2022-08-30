package kr.hs.sunrint.service.jsp;

import kr.hs.sunrint.domain.jsp.JspTag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JspParser {
    public Matcher parse(String jsp) {
        Pattern pattern = Pattern.compile("<jsp:(\\S+)(.+)/>");
        return pattern.matcher(jsp);
    }

    public JspTag parseTag(String tagName, String attributes) {
        JspTag jspTag = new JspTag(tagName);
        Pattern pattern = Pattern.compile("(?:\\s+(?:(\\S+)=['\"](\\S+)['\"]))");
        Matcher matcher = pattern.matcher(attributes);

        while (matcher.find()) {
            jspTag.addAttribute(matcher.group(1), matcher.group(2));
        }

        return jspTag;
    }
}
