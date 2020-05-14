package com.example.pratica21;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Date;

public class RssHandler extends DefaultHandler implements LexicalHandler {
    ContentValues rssItem;
    //nos
    private boolean in_item = false;
    private boolean in_title= false;
    private boolean in_link= false;
    private boolean in_comments= false;
    private boolean in_pubdate= false;
    private boolean in_dcCreator= false;
    private boolean in_description= false;
    private boolean in_CDATA;

    private ContentResolver contentProv;
    private static final String AUTORITIES = "blog.masterd.pt";
    Uri uri = Uri.parse("content://"+ AUTORITIES + "/" + FeedsDB.Posts.NOME_TABELA);
    public RssHandler(ContentResolver contentResolver){
        contentProv = contentResolver;
    }
    public RssHandler(){ }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
       if(localName.equalsIgnoreCase("item")){
           in_item = true;
           rssItem = new ContentValues();
       }else if(localName.equalsIgnoreCase(FeedsDB.Posts.TITLE)){
           in_title = true;
       }else if(localName.equalsIgnoreCase(FeedsDB.Posts.LINK)){
           in_link = true;
       }else if(localName.equalsIgnoreCase(FeedsDB.Posts.COMMENTS)){
           in_comments = true;
       }else if(localName.equalsIgnoreCase("pubDate")){
           in_pubdate = true;
       }else if(localName.equalsIgnoreCase("dc:creator")){
           in_dcCreator = true;
       }else if(localName.equalsIgnoreCase(FeedsDB.Posts.DESCRIPTION)){
           in_description = true;
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
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.COMMENTS)){
            in_comments = false;
        }else if(localName.equalsIgnoreCase("pubdate")){
            in_pubdate = false;
        }else if(localName.equalsIgnoreCase("dc:creator")){
            in_dcCreator = false;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.DESCRIPTION)){
            in_description = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if(in_item){
            if(in_title){
                rssItem.put(FeedsDB.Posts.TITLE, new String(ch, start, length));
            }else if(in_link){
                rssItem.put(FeedsDB.Posts.LINK, new String(ch, start, length));
            }else if(in_description){
                rssItem.put(FeedsDB.Posts.DESCRIPTION, new String(ch, start, length));
                String frase = rssItem.get(FeedsDB.Posts.DESCRIPTION).toString();
                int fim1 = frase.indexOf(". ")+1;
                int fim2 = frase.indexOf(", ")+1;
                if(fim1<fim2){
                    rssItem.put(FeedsDB.Posts.FRASE, frase.substring(0, fim2));
                }else{
                    rssItem.put(FeedsDB.Posts.FRASE, frase.substring(0, fim1));
                }
            }else if(in_pubdate){
                String strDate = new String(ch, start, length);
                Log.d("My debug data", strDate);
                try{
                    long data = Date.parse(strDate);
                    rssItem.put(FeedsDB.Posts.PUB_DATE, data);
                }catch(Exception e){
                    Log.d("RssHandler", "Erro na análise da data");
                }
            }
        }
    }
    @Override
    public void comment(char[] ch, int start, int length) throws SAXException {

    }
    @Override
    public void endCDATA() throws SAXException {

    }
    @Override
    public void endDTD() throws SAXException {

    }
    @Override
    public void endEntity(String name) throws SAXException {

    }
    @Override
    public void startCDATA() throws SAXException {

    }

    @Override
    public void startDTD(String name, String publicId, String systemId) throws SAXException {

    }



    @Override
    public void startEntity(String name) throws SAXException {

    }







}
