package com.mycompany.ksan0.translator.activities.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.mycompany.ksan0.translator.R;
import com.mycompany.ksan0.translator.activities.core.LangItem;
import com.mycompany.ksan0.translator.activities.core.LangItemsController;
import com.mycompany.ksan0.translator.activities.network.TranslateService;

import java.util.ArrayList;


public class TranslateFragment extends Fragment
        implements View.OnClickListener {
    public static final String SELECTED_LANG_ITEM = "SELECTED_LANG_ITEM";
    public static final String BROADCAST_RECEIVER_FILTER = "BROADCAST_RECEIVER_FILTER";

    private LangItem currentLangItem;

    private ArrayAdapter<String> spinnerAdapterFromLang;
    private ArrayAdapter<String> spinnerAdapterToLang;

    private Spinner spinnerLangFrom;
    private Spinner spinnerLangTo;

    private int spinnerFromLangPos;
    private int spinnerToLangPos;

    private EditText editTextFrom;
    private EditText editTextTo;

    private Button buttonSwapLang;
    private Button buttonTranslate;
    private Switch buttonAutoTranslate;

    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String textTo = intent.getStringExtra(TranslateService.TRANSLATE_TEXT);
                    receiveTranslatedText(textTo);
                }
            };
            getActivity().registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_RECEIVER_FILTER));

            currentLangItem = getArguments().getParcelable(SELECTED_LANG_ITEM);

            ArrayList<String> fromArray = LangItemsController.getInstance().findAllMatchesToLangTitle(currentLangItem.getToTitle());
            spinnerFromLangPos = fromArray.indexOf(currentLangItem.getFromTitle());
            spinnerAdapterFromLang = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.item_spinner,
                    R.id.language,
                    fromArray
            );

            ArrayList<String> toArray = LangItemsController.getInstance().findAllMatchesFromLangTitle(currentLangItem.getFromTitle());
            spinnerToLangPos = toArray.indexOf(currentLangItem.getToTitle());
            spinnerAdapterToLang = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.item_spinner,
                    R.id.language,
                    toArray
            );
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        spinnerLangFrom = (Spinner) view.findViewById(R.id.spinnerFromLang);
        spinnerLangFrom.setAdapter(spinnerAdapterFromLang);
        spinnerLangFrom.setSelection(spinnerFromLangPos);
        spinnerLangFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String title = (String) parent.getAdapter().getItem(position);
                LangItem newCurrentLangItem = LangItemsController.getInstance().findBy(title, currentLangItem.getToTitle());
                if (newCurrentLangItem != null) {
                    updateLangs(newCurrentLangItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLangTo = (Spinner) view.findViewById(R.id.spinnerToLang);
        spinnerLangTo.setAdapter(spinnerAdapterToLang);
        spinnerLangTo.setSelection(spinnerToLangPos);
        spinnerLangTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String title = (String) parent.getAdapter().getItem(position);
                LangItem newCurrentLangItem = LangItemsController.getInstance().findBy(currentLangItem.getFromTitle(), title);
                if (newCurrentLangItem != null) {
                    updateLangs(newCurrentLangItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editTextFrom = (EditText)view.findViewById(R.id.textFrom);
        editTextFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (buttonAutoTranslate.isChecked()) {
                    translateCurrentText(editable);
                }
            }
        });

        editTextFrom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    buttonTranslate.callOnClick();
                }
                return false;
            }
        });

        editTextTo   = (EditText)view.findViewById(R.id.textTo);

        buttonSwapLang = (Button)view.findViewById(R.id.buttonSwapLang);
        buttonSwapLang.setOnClickListener(this);
        buttonTranslate = (Button)view.findViewById(R.id.buttonTranslate);
        buttonTranslate.setOnClickListener(this);
        buttonAutoTranslate = (Switch)view.findViewById(R.id.toggleButtonAutoTranslate);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonTranslate:
                translateCurrentText(editTextFrom.getText());
                editTextTo.setText("");
                break;
            case R.id.buttonSwapLang:
                updateLangs(LangItemsController.getInstance().findBy(currentLangItem.getToTitle(), currentLangItem.getFromTitle()));
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        } catch (ClassCastException e) {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void updateLangs(LangItem newCurrentLangItem) {
        currentLangItem = newCurrentLangItem;

        ArrayAdapter<String> spinnerArrayAdapterFromLang = spinnerAdapterFromLang;
        spinnerArrayAdapterFromLang.clear();
        ArrayList<String> arrTitleFromLang = LangItemsController.getInstance().findAllMatchesToLangTitle(currentLangItem.getToTitle());
        spinnerArrayAdapterFromLang.addAll(arrTitleFromLang);
        spinnerLangFrom.setSelection(arrTitleFromLang.indexOf(currentLangItem.getFromTitle()));

        ArrayAdapter<String> spinnerArrayAdapterToLang = spinnerAdapterToLang;
        spinnerArrayAdapterToLang.clear();
        ArrayList<String> arrTitleToLang = LangItemsController.getInstance().findAllMatchesFromLangTitle(currentLangItem.getFromTitle());
        spinnerArrayAdapterToLang.addAll(arrTitleToLang);
        spinnerLangTo.setSelection(arrTitleToLang.indexOf(currentLangItem.getToTitle()));
    }

    private void translateCurrentText(Editable editable) {
        Intent serviceIntent = new Intent(getActivity(), TranslateService.class);
        serviceIntent.putExtra(TranslateService.CODE_LANG_FROM, currentLangItem.getFrom());
        serviceIntent.putExtra(TranslateService.CODE_LANG_TO, currentLangItem.getTo());
        serviceIntent.putExtra(TranslateService.TRANSLATE_TEXT, editable.toString());
        getActivity().startService(serviceIntent);
    }

    private void receiveTranslatedText(String textTo) {
        editTextTo.setText(textTo);
    }
}
