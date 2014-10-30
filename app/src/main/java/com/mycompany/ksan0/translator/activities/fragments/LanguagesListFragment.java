package com.mycompany.ksan0.translator.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.mycompany.ksan0.translator.R;

import com.mycompany.ksan0.translator.activities.activities.FragmentsController;
import com.mycompany.ksan0.translator.activities.core.LangItem;
import com.mycompany.ksan0.translator.activities.core.LangItemsController;

import java.util.ArrayList;

import static com.mycompany.ksan0.translator.R.*;


public class LanguagesListFragment extends Fragment implements AbsListView.OnItemClickListener {
    private FragmentsController fragmentsController;

    private AbsListView mListView;
    private ListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new ArrayAdapter<LangItem>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                LangItemsController.getInstance().getLangItems()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_languageslistfragment, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentsController = (FragmentsController) activity;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentsController = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (fragmentsController != null) {
            LangItem langItem = (LangItem) parent.getAdapter().getItem(position);
            Bundle args = new Bundle();
            args.putParcelable(TranslateFragment.SELECTED_LANG_ITEM, langItem);
            Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_translate);
            if (fragment == null) {
                fragment = new TranslateFragment();
            }
            fragment.setArguments(args);
            fragmentsController.setFragment(fragment, true);
        }
    }

}
