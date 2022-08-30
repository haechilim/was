package kr.hs.sunrint.service.jsp;

import kr.hs.sunrint.domain.jsp.JspTag;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class JspProcessor {
    private Map<String, Object> beans;
    private JspParser jspParser;

    public JspProcessor() {
        beans = new HashMap<>();
        jspParser = new JspParser();
    }

    public String process(String jsp) {
        Matcher matcher = jspParser.parse(jsp);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            JspTag jspTag = jspParser.parseTag(matcher.group(1), matcher.group(2));
            matcher.appendReplacement(buffer, execute(jspTag));
        }

        matcher.appendTail(buffer);

        return buffer.toString();
    }

    private String execute(JspTag jspTag) {
        switch (jspTag.getTag()) {
            case "useBean":
                return useBean(jspTag);

            case "getProperty":
                return getProperty(jspTag);

            default:
                return "";
        }
    }

    private String useBean(JspTag jspTag) {
        try {
            Class clazz = Class.forName(jspTag.getAttribute("class"));
            Object instance = clazz.newInstance();

            beans.put(jspTag.getAttribute("id"), instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String getProperty(JspTag jspTag) {
        try {
            Object instance = beans.get(jspTag.getAttribute("name"));
            String methodName = transMethodName(jspTag.getAttribute("property"));

            Method method = instance.getClass().getMethod(methodName);

            return String.valueOf(method.invoke(instance));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String transMethodName(String propertyName) {
        return "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }
}
