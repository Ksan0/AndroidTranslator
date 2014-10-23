package com.mycompany.ksan0.translator.activities.core;

import java.util.ArrayList;


public class LangItemsController {

    private static LangItemsController instance;

    private ArrayList<LangItem> langItems;

    private LangItemsController() {

    }

    public static LangItemsController getInstance() {
        if (instance == null) {
            instance = new LangItemsController();
        }

        return instance;
    }

    public void setLangItems(ArrayList<LangItem> langItems) {
        this.langItems = langItems;
    }

    public ArrayList<LangItem> getLangItems() {
        return langItems;
    }

    public LangItem findBy(String fromTitle, String toTitle) {
        for (LangItem item: langItems) {
            if (item.getFromTitle().equals(fromTitle) && item.getToTitle().equals(toTitle)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<String> findAllMatchesToLangTitle(String title) {
        ArrayList<String> arr = new ArrayList<String>();
        for (LangItem item: langItems) {
            if (item.getToTitle().equals(title)) {
                arr.add(item.getFromTitle());
            }
        }
        return arr;
    }

    public ArrayList<String> findAllMatchesFromLangTitle(String title) {
        ArrayList<String> arr = new ArrayList<String>();
        for (LangItem item: langItems) {
            if (item.getFromTitle().equals(title)) {
                arr.add(item.getToTitle());
            }
        }
        return arr;
    }

}
