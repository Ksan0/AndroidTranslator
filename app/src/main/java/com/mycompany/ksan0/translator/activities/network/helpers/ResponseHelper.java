package com.mycompany.ksan0.translator.activities.network.helpers;


import android.util.Log;

import com.mycompany.ksan0.translator.activities.core.LangItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseHelper {

    public static void parseLangListResponse(String response, ArrayList<LangItem> outLangItems) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonDirs = (JSONArray) jsonObject.get("dirs");
            JSONObject jsonLangs = (JSONObject) jsonObject.get("langs");

            for (int i = 0; i < jsonDirs.length(); ++i) {
                String langPair = (String) jsonDirs.get(i);
                String from = langPair.substring(0, 2);  // en-ru (0, 1) (3, 4)
                String to = langPair.substring(3, 5);
                String fromTitle = (String) jsonLangs.get(from);
                String toTitle = (String) jsonLangs.get(to);
                LangItem langItem = new LangItem(from, to, fromTitle, toTitle);
                outLangItems.add(langItem);
            }
        } catch(Exception e) {
        }
    }

    public static String parseTranslateResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray textArray = jsonObject.getJSONArray("text");

            String result = "";
            for (int i = 0; i < textArray.length(); ++i) {
                result += textArray.getString(i) + "\n";
            }

            return result;
        } catch(Exception e) {
        }

        return null;
    }
}
