package com.mycompany.ksan0.translator.activities.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.mycompany.ksan0.translator.activities.fragments.TranslateFragment;
import com.mycompany.ksan0.translator.activities.network.helpers.RequestHelper;
import com.mycompany.ksan0.translator.activities.network.helpers.ResponseHelper;
import com.mycompany.ksan0.translator.activities.network.helpers.URLHelper;

import org.json.JSONArray;
import org.json.JSONException;


public class TranslateService extends IntentService {

    public static final String CODE_LANG_FROM = "CODE_LANG_FROM";
    public static final String CODE_LANG_TO   = "CODE_LANG_TO";
    public static final String TRANSLATE_TEXT = "TRANSLATE_TEXT";

    public TranslateService() {
        super("Translating service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String langFrom = intent.getStringExtra(CODE_LANG_FROM);
        String langTo   = intent.getStringExtra(CODE_LANG_TO);
        String text = intent.getStringExtra(TRANSLATE_TEXT);

        String response = RequestHelper.makeEmptyGetRequest(URLHelper.translate(langFrom, langTo, text));
        String resultText = ResponseHelper.parseTranslateResponse(response);

        Intent broadcastIntent = new Intent(TranslateFragment.BROADCAST_RECEIVER_FILTER);
        broadcastIntent.putExtra(TRANSLATE_TEXT, resultText);
        sendBroadcast(broadcastIntent);

        /*String response = Helpers.makeRequest(URLMaker.getTranslateUrl(origin, destination, text));
        int code = Constants.CODE_FAIL;
        if(!response.equals(Constants.ERROR_RESPONSE)) {
            JSONArray s = ResponseParser.getTranslatedText(response);
            StringBuilder translation = new StringBuilder();
            try {
                for (int i = 0; i < s.length(); i++) {
                    translation.append(s.getString(i))
                            .append('\n');
                }
                code = Constants.CODE_OK;
                response = translation.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Intent broadcastIntent = new Intent(Constants.BROADCAST);
        broadcastIntent.putExtra(Constants.BUNDLE_TEXT, response);
        broadcastIntent.putExtra(Constants.BUNDLE_CODE, code);
        sendBroadcast(broadcastIntent);*/
    }

}
