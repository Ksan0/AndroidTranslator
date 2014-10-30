package com.mycompany.ksan0.translator.activities.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.mycompany.ksan0.translator.R;
import com.mycompany.ksan0.translator.activities.fragments.SplashFragment;
import com.mycompany.ksan0.translator.activities.fragments.TranslateFragment;


public class MainActivity extends Activity implements FragmentsController {
    public static final String FRAGMENT_TRANSLATE_TAG = "fragment_translate";
    private TranslateFragment translateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            translateFragment = (TranslateFragment)getFragmentManager().findFragmentByTag(FRAGMENT_TRANSLATE_TAG);
            if (translateFragment != null) {
                setFragment(translateFragment, true);
            } else {
                updateFragment();
            }
        } else {
            updateFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void setFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction = transaction.addToBackStack(null);
        }
        if (fragment instanceof TranslateFragment) {
            transaction
            .replace(R.id.activity_main, fragment, FRAGMENT_TRANSLATE_TAG)
            .commit();
        } else {
            transaction
            .replace(R.id.activity_main, fragment)
            .commitAllowingStateLoss();
        }

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
