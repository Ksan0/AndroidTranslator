package com.mycompany.ksan0.translator.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycompany.ksan0.translator.R;
import com.mycompany.ksan0.translator.activities.activities.FragmentsController;
import com.mycompany.ksan0.translator.activities.network.AsyncTaskInitDownload;


public class SplashFragment extends Fragment {

    private FragmentsController fragmentsController;
    private AsyncTaskInitDownload taskInitDownload;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        if (taskInitDownload == null) {
            taskInitDownload = new AsyncTaskInitDownload(fragmentsController, null);
            taskInitDownload.execute();
        }

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

}
