package com.anubhav.gupta.notesinternshala;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ANUBHAV on 4/13/2017.
 */

public class FragSignUp extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.signup, container, false);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

}

