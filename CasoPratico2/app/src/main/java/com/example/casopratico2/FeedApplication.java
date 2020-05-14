package com.example.casopratico2;

import android.app.Application;

public class FeedApplication extends Application {
    public String getRssUrl(){
        return "https://feeds.feedburner.com/PublicoRSS";

    }
    public String getRssUrl3(){
        return "http://feeds.tsf.pt/TSF-Ultimas";
    }
    public String getRssUrl4(){

        return "https://www.cmjornal.pt/rss";

    }


}
