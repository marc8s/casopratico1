package com.example.casopratico2;

import android.content.ContentResolver;
import android.content.Context;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssDownloadHelper2 {
    public static void updateRssData(String rssUrl, ContentResolver contentResolver){
        try{
            URL url = new URL(rssUrl);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            RssHandler2 rssHandler = new RssHandler2(contentResolver);
            saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", rssHandler);
            XMLReader xr = saxParser.getXMLReader();
            xr.setContentHandler(rssHandler);
            InputSource is = new InputSource(url.openStream());
            is.setEncoding("utf-8");
            xr.parse(is);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
