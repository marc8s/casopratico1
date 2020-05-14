package com.example.pratica21;

import android.app.Application;

public class MasterdApplication extends Application {
    public String getRssUrl(){
        return "https://www.masterd.pt/noticias/index.php/feed";
    }
}
