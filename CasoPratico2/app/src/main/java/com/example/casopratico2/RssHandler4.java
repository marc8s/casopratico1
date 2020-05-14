package com.example.casopratico2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Date;

public class RssHandler4 extends DefaultHandler implements LexicalHandler {
    ContentValues rssItem;
    //nos
    private boolean in_item = false;
    private boolean in_title= false;
    private boolean in_link= false;
    private boolean in_pubdate= false;

    private ContentResolver contentProv;
    private static final String AUTORITIES = "cmjornal.pt";
    Uri uri = Uri.parse("content://"+ AUTORITIES + "/" + FeedsDB.Posts.NOME_TABELA_4);
    public RssHandler4(ContentResolver contentResolver){
        contentProv = contentResolver;
    }
    public RssHandler4(){ }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if(localName.equalsIgnoreCase("item")){
            in_item = true;
            rssItem = new ContentValues();
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.TITLE)){
            in_title = true;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.LINK)){
            in_link = true;
        }else if(localName.equalsIgnoreCase("pubDate")){
            in_pubdate = true;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if(localName.equalsIgnoreCase("item")){
            contentProv.insert(uri, rssItem);
            rssItem = new ContentValues();
            in_item = false;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.TITLE)){
            in_title = false;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.LINK)){
            in_link = false;
        }else if(localName.equalsIgnoreCase("pubdate")){
            in_pubdate = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if(in_item){
            if(in_title){
                rssItem.put(FeedsDB.Posts.TITLE, new String(ch, start, length));
            }else if(in_link){
                rssItem.put(FeedsDB.Posts.LINK, new String(ch, start, length));
            }else if(in_pubdate){
                String strDate = new String(ch, start, length);
                Log.d("My debug data", strDate);
                try{
                    long data = Date.parse(strDate);
                    rssItem.put(FeedsDB.Posts.PUB_DATE, data);
                }catch(Exception e){
                    Log.d("RssHandler", "Erro na análise da data correio");
                }
            }
        }
    }
    @Override
    public void startDTD(String s, String s1, String s2) throws SAXException {

    }

    @Override
    public void endDTD() throws SAXException {

    }

    @Override
    public void startEntity(String s) throws SAXException {

    }

    @Override
    public void endEntity(String s) throws SAXException {

    }

    @Override
    public void startCDATA() throws SAXException {

    }

    @Override
    public void endCDATA() throws SAXException {

    }

    @Override
    public void comment(char[] chars, int i, int i1) throws SAXException {

    }
}
