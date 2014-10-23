package com.mycompany.ksan0.translator.activities.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.mycompany.ksan0.translator.R;
import com.mycompany.ksan0.translator.activities.fragments.SplashFragment;

import java.util.ArrayList;


public class MainActivity extends Activity implements FragmentsController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateFragment();
    }

    @Override
    public void setFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        if (addToBackStack) {
            trans = trans.addToBackStack(null);
        }

        trans.replace(R.id.activity_main, fragment)
            .commitAllowingStateLoss();

    }

    public void updateFragment() {
        Fragment fragment = findLastFragment();
        if (fragment == null) {
            fragment = new SplashFragment();
        }

        setFragment(fragment, false);
    }

    private Fragment findLastFragment() {
        Fragment fragment;

        if ((fragment = getFragmentManager().findFragmentById(R.id.fragment_splash)) != null) {
            return fragment;
        }

        if ((fragment = getFragmentManager().findFragmentById(R.id.fragment_languageslist)) != null) {
            return fragment;
        }

        if ((fragment = getFragmentManager().findFragmentById(R.id.fragment_translate)) != null) {
            return fragment;
        }

        return null;
    }
}
