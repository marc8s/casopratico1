package com.example.leitorrss;

import android.app.Application;

public class MasterdApplication extends Application {
    public String getRssUrl(){
        return "http://pplware.sapo.pt/feed/";
    }
}
