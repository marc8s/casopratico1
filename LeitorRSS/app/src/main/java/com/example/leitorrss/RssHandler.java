package com.example.leitorrss;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.ext.LexicalHandler;

import java.util.Date;

//usa uma serie de propriedades booleanas que serve para identificar em que tag o parser se encontra
//vai preenchendo um objeto do tipo content values

public class RssHandler extends DefaultHandler implements LexicalHandler {
    ContentValues rssItem;
    //flags para saber em que nó estamos
    private boolean in_item = false;
    private boolean in_title = false;
    private boolean in_link = false;
    @SuppressWarnings("unused")
    private boolean in_comments = false;
    private boolean in_pubDate = false;
    @SuppressWarnings("unused")
    private boolean in_dcCreator = false;
    private boolean in_description = false;
    @SuppressWarnings("unused")
    private boolean in_CDATA = false;

    //dados do fornecedor de conteudos
    private ContentResolver contentProv;
    private static final String AUTORITIES = "pplware.sapo.pt/feed/";
    Uri uri = Uri.parse("content://" + AUTORITIES + "/" + FeedsDB.Posts.NOME_TABELA);


    public RssHandler(ContentResolver contentResolver){
        contentProv = contentResolver;
    }
//
    @Override
    public void startDTD(String s, String s1, String s2) throws SAXException {

    }
//
    @Override
    public void endDTD() throws SAXException {

    }



    @Override
    public void startEntity(String s) throws SAXException {

    }
//
    @Override
    public void endEntity(String s) throws SAXException {

    }
//
    @Override
    public void startCDATA() throws SAXException {

    }
//metodo usado quando uma tag termina pra alterar o indicador pra false
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        //super.endElement(namespaceURI, localName, qName);
        if(localName.equalsIgnoreCase("item")){
            //se for fim de uma noticia a mesma é guardada na base de dados
            contentProv.insert(uri, rssItem);
            rssItem = new ContentValues();
            in_item =false;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.TITLE)){
            in_title = false;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.LINK)){
            in_link = false;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.COMMENTS)){
            in_comments = false;
        }else if(localName.equalsIgnoreCase("pubDate")){
            in_pubDate = false;
        }else if(localName.equalsIgnoreCase("dc:creator")){
            in_dcCreator = false;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.DESCRIPTION)){
            in_description = false;
        }
    }

    //o metodo start element(ser que era o entity?) é disparado toda vez que o parser detecta um inicio de tag menu
    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {
        //super.startElement(namespaceURI, localName, qName, atts);
        if(localName.equalsIgnoreCase("item")){
            in_item =true;
            rssItem = new ContentValues();
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.TITLE)){
            in_title = true;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.LINK)){
            in_link = true;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.COMMENTS)){
            in_comments = true;
        }else if(localName.equalsIgnoreCase("pubDate")){
            in_pubDate = true;
        }else if(localName.equalsIgnoreCase("dc:creator")){
            in_dcCreator = true;
        }else if(localName.equalsIgnoreCase(FeedsDB.Posts.DESCRIPTION)){
            in_description = true;
        }
    }



    //
    @Override
    public void endCDATA() throws SAXException {

    }
//usado quando o conteudo de uma tag esta completo, usado pra introduzir o valor no registro
    @Override
    public void characters(char[] ch, int start, int length) {

        if(in_item){
            if (in_title){
                rssItem.put(FeedsDB.Posts.TITLE, new String(ch, start, length));
            }else if(in_link){
                rssItem.put(FeedsDB.Posts.LINK, new String(ch, start, length));
            }else if(in_description){
                rssItem.put(FeedsDB.Posts.DESCRIPTION, new String(ch, start, length));
            }else if(in_pubDate){
                String strDate = new String(ch, start, length);
                Log.d("My debug data", strDate);
                try{
                    @SuppressWarnings("deprecation")
                    long data = Date.parse(strDate);
                    rssItem.put(FeedsDB.Posts.PUB_DATE, data);
                }catch(Exception e){
                    Log.d("RssHandler", "Erro na analise da data");
                }
            }
        }
    }

    //
    @Override
    public void comment(char[] chars, int i, int i1) throws SAXException {

    }
}
