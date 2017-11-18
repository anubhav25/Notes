package com.anubhav.gupta.notesinternshala;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ANUBHAV on 4/13/2017.
 */

public class FragLogin extends Fragment {
    SharedPreferences shared;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
                return inflater.inflate(R.layout.login, container, false);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        shared = getActivity().getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        int col=shared.getInt("color",android.R.color.white);
        getActivity().findViewById(R.id.fragment).setBackgroundColor(getResources().getColor(col));
        Boolean auto = shared.getBoolean("auto", false);
        if (auto) {
            TextView user = (TextView) getActivity().findViewById(R.id.login_user);
            TextView pass = (TextView) getActivity().findViewById(R.id.login_pass);

            user.setText(shared.getString("user", ""));
            pass.setText(shared.getString("pass", ""));
            Button login = (Button) getActivity().findViewById(R.id.login_signin);
            login.performClick();

        }

    }

}
