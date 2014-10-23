package com.mycompany.ksan0.translator.activities.network.helpers;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLHelper {

    private static final String APP_KEY = "trnsl.1.1.20141008T232212Z.c86a3a0ce23b0eb6.3e389f5847ef0f30670d5b4ee5d99ee469a6966c";

    public static String getLangs() {
        return String.format("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=%s&ui=%s", APP_KEY, "ru");
    }

    public static String translate(String fromLang, String toLang, String text) {
        try {
            return String.format("https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&text=%s&lang=%s", APP_KEY, URLEncoder.encode(text, "UTF-8"), fromLang + "-" + toLang);
        } catch(UnsupportedEncodingException e) {
            return null;
        }
    }

}
