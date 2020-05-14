package com.example.leitorrss;

/* ESTA É UMA CLASSE DE AJUDA QUE PERMITE DESCARREGAR E ANALISAR OS FICHEIROS DE NOTICIAS
* ESTA CLASSE CRIA TODOS OS OBJETOS SAX NECESSÁRIOS E FAZ A CHAMADA DO FEED*/

import android.content.ContentResolver;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssDownloadHelper {

    public static void updateRssData(String rssUrl, ContentResolver contentResolver){
        try {
            URL url = new URL(rssUrl);
            //obtenção do saxparser
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            //criação do handler
            RssHandler rssHandler = new RssHandler(contentResolver);
            //definição do manuseador lexico
            saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", rssHandler);
            //obtenção do reader
            XMLReader xr = saxParser.getXMLReader();
            xr.setContentHandler(rssHandler);
            //analise do conteudo
            InputSource is = new InputSource(url.openStream());
            is.setEncoding("utf-8");
            xr.parse(is);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
