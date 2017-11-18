package com.anubhav.gupta.notesinternshala;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by ANUBHAV on 4/13/2017.
 */

public class FragEdiText extends Fragment {

String msg="";

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List_Main.openEditext=true;
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.edit_text, container, false);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         EditText editText=(EditText)getActivity().findViewById(R.id.mainEditext);

        if(msg.endsWith("\n\n\n\n"))
        {
           msg= msg.substring(0,msg.lastIndexOf("\n"));
            msg=  msg.substring(0,msg.lastIndexOf("\n"));
            msg=   msg.substring(0,msg.lastIndexOf("\n"));
            msg= msg.substring(0,msg.lastIndexOf("\n"));

        }
        if (msg.endsWith("\n\n\n")){

            msg=  msg.substring(0,msg.lastIndexOf("\n"));
            msg=  msg.substring(0,msg.lastIndexOf("\n"));
            msg= msg.substring(0,msg.lastIndexOf("\n"));


        }
        if(msg.endsWith("\n\n"))
        {

            msg= msg.substring(0,msg.lastIndexOf("\n"));
            msg= msg.substring(0,msg.lastIndexOf("\n"));
        }
        if(msg.endsWith("\n"))
        {
            msg= msg.substring(0,msg.lastIndexOf("\n"));

        }
       editText.setText(msg);
        editText.setSelection(editText.getText().length());




    }


}
