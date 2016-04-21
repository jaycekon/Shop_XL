package com.Shop.Util;

import com.thoughtworks.xstream.XStream;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.xml.sax.InputSource;
import org.dom4j.Element;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class XMLUtil {
    public static Map<String,Object> parseXML(String xml){
        SAXReader reader = new SAXReader();
        StringReader sr = new StringReader(xml);
        InputSource is = new InputSource(sr);
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            Document document = reader.read(is);
            Element root = document.getRootElement();
            List<Element> list = root.elements();
            for (int i=0;i<list.size();i++){
                Element element = list.get(i);
                System.out.println(element.getName()+":"+element.getData());
                map.put(element.getName(), element.getData());
            }

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    //把对象转化为xml字符串
    public static String toXML(Object object){
        XStream xstream = new XStream();
        xstream.alias("xml", object.getClass());
        return xstream.toXML(object);
    }

    public static String mapToStr(Map<String,Object> map){
        String str="";
        Set<String> keySet = map.keySet();

        Iterator<String> iter = keySet.iterator();

        while (iter.hasNext()) {
            String key = iter.next();
            str+=key+"="+map.get(key)+"&";
        }
        return str.substring(0, str.length()-1);
    }

    public static String mapToXml(Map<String,Object> map){
        String str="";
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();

        while (iter.hasNext()) {
            String key = iter.next();
            str+="<"+key+">"+map.get(key)+"</"+key+">";
        }
        return "<xml>"+str+"</xml>";
    }
}
