package com.anubhav.gupta.notesinternshala;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ANUBHAV on 4/14/2017.
 */

public class List_Main extends AppCompatActivity {
    public String currentFrag="FragLogin";
    List<itemData> mylist = new ArrayList<>();
    static List<itemData> test_list = new ArrayList<>();
    static int vis=View.VISIBLE;
    itemAdapter mAdapter;
    ListView notesView;
    SharedPreferences shared;
    boolean loggedin=false;
    static itemData test;
    DatabaseHandlerData dbd;
    DatabaseHandlerLogin dbl;
    boolean autoSave=true;
    View emptyView=null;
    int pos=-1;
    boolean empty;
    static boolean openEditext=false;
   FragmentTransaction ft= getFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //super
        super.onCreate(savedInstanceState);
        //setContentView
        setContentView(R.layout.list);

        //database handlers
        dbd =new DatabaseHandlerData(this);
        dbl = new DatabaseHandlerLogin(this);

        //fab button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //  insertEmpty();
             //   empty=true;
                empty=true;
                findViewById(R.id.listlayout).setVisibility(View.GONE);
                currentFrag="editText";
                getFragmentManager().beginTransaction().replace(R.id.fragment, new FragEdiText()).commit();

            }
        });

        //restoring state

        if(savedInstanceState!=null) {
            String current = savedInstanceState.getString("current", "MainActivity");
            currentFrag = current;
            String s1 = savedInstanceState.getString("s1", "");
            String s2 = savedInstanceState.getString("s2", "");
            String s3 = savedInstanceState.getString("s3", "");

            switch (current) {
                case "editText":
                    findViewById(R.id.listlayout).setVisibility(View.GONE);
                    getFragmentManager().beginTransaction().replace(R.id.fragment, new FragEdiText()).commit();
                    EditText e4 = (EditText) findViewById(R.id.mainEditext);
                    e4.setText(s1);
                    break;
                case "FragSignUp":
                    findViewById(R.id.listlayout).setVisibility(View.GONE);
                   getFragmentManager().beginTransaction().replace(R.id.fragment, new FragSignUp()).commit();
               EditText e1 = (EditText) findViewById(R.id.signup_user);
                    EditText e2 = (EditText) findViewById(R.id.signup_pass);
                    EditText e3 = (EditText) findViewById(R.id.signup_confpass);
                    e1.setText(s1);
                    e2.setText(s2);
                    e3.setText(s3);
                    break;

                case "FragLogin":
                    findViewById(R.id.listlayout).setVisibility(View.GONE);
                    getFragmentManager().beginTransaction().replace(R.id.fragment, new FragLogin()).commit();

                    EditText e5 = (EditText) findViewById(R.id.login_user);
                    EditText e6 = (EditText) findViewById(R.id.login_pass) ;

                    e5.setText(s1);
                    e6.setText(s2);
                    break;

                default:

            }
        }
        else {

            if(currentFrag.equals("FragLogin")){
                findViewById(R.id.listlayout).setVisibility(View.GONE);
                ft.add(R.id.fragment, new FragLogin()).commit();

            }
            if(currentFrag.equals("FragSignUp")){
                findViewById(R.id.listlayout).setVisibility(View.GONE);
               ft.add(R.id.fragment, new FragSignUp()).commit();

            }
            if(currentFrag.equals("editText")){
                findViewById(R.id.listlayout).setVisibility(View.GONE);
               ft.add(R.id.fragment, new FragEdiText()).commit();
            }

        }

        if(!test_list.isEmpty()&&currentFrag.equals("MainActivity")&&!openEditext)
        {
            mylist=test_list;
            init_list();

        }

        //seting listView
        notesView = (ListView) findViewById(R.id.list);
        mAdapter = new itemAdapter(this, mylist);
        notesView.setAdapter(mAdapter);
        if(notesView!=null&&emptyView!=null) {
            notesView.setEmptyView(emptyView);
        }
        emptyView = findViewById(R.id.empty_view);
        if(notesView!=null) {
            notesView.setEmptyView(emptyView);
        }


    }

//initialise list
    public void init_list()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        findViewById(R.id.listlayout).setVisibility(View.VISIBLE);
        mylist=dbd.getAllNotes();
        notesView = (ListView) findViewById(R.id.list);
        mAdapter = new itemAdapter(this, mylist);
        notesView.setAdapter(mAdapter);
        if(notesView!=null&&emptyView!=null) {
            notesView.setEmptyView(emptyView);
        }

//onlong clickListner

        notesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                pos=position;
                return  false;
            }
        });


//onclickListner

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                itemData item = (itemData) adapterView.getAdapter().getItem(position);

                test=item;
                FragEdiText fragEdiText=new FragEdiText();
                fragEdiText.setMsg(item.getText());
                currentFrag="editText";
                openEditext=false;
                findViewById(R.id.listlayout).setVisibility(View.GONE);
                empty=false;



                getFragmentManager().beginTransaction().replace(R.id.fragment, fragEdiText).commit();


            }
        });
        registerForContextMenu(notesView);

    }
//on pause
    @Override
    protected void onPause() {
        test_list=mylist;
        super.onPause();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.listmenu,menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        mylist.remove(pos);
        mAdapter.notifyDataSetChanged();
        mAdapter = new itemAdapter(this, mylist);
        notesView.setAdapter(mAdapter);
        dbd.updateAllNotes(mylist);

        return super.onContextItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu mymenu) {


        getMenuInflater().inflate(R.menu.menu, mymenu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                shared=getSharedPreferences("logindetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=shared.edit();
                editor.clear().apply();
                currentFrag="FragLogin";
                findViewById(R.id.listlayout).setVisibility(View.GONE);
                getFragmentManager().beginTransaction().replace(R.id.fragment, new FragLogin()).commit();
                break;
            case R.id.bgcolor:

                initiatePopupWindow();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    private PopupWindow pwindo;
    private void initiatePopupWindow() {

        try {
// We need to get the instance of the LayoutInflater

            LayoutInflater inflater = (LayoutInflater) List_Main.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.fragment),false);

            Button btnClose = (Button) layout.findViewById(R.id.popup_close);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                }
            });

            List<Integer> lis=new ArrayList<>();
            lis.add(android.R.color.white);
            lis.add(R.color.c1);
            lis.add(R.color.c5);
            lis.add(R.color.c6);
            lis.add(R.color.c7);
            lis.add(R.color.c8);
            lis.add(R.color.c9);
            lis.add(R.color.c11);
            lis.add(R.color.c12);
            GridView grid=(GridView) layout.findViewById(R.id.popup_grid);
            final myAdapter adapter = new myAdapter(List_Main.this,lis);
if(grid!=null)
            grid.setAdapter(adapter);
            if (grid != null) {
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int p, long id) {

                        Toast.makeText(List_Main.this, "hi", Toast.LENGTH_SHORT).show();
                      int a= (Integer) adapterView.getAdapter().getItem(p);

                        findViewById(R.id.fragment).setBackgroundColor(getResources().getColor(a));
                        shared=getSharedPreferences("logindetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=shared.edit();
                        editor.putInt("color",a);
                        editor.apply();

                    }


                });
            }
            layout.setMinimumWidth(350);


            pwindo = new PopupWindow(layout, LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);



        } catch (Exception e) {
            e.printStackTrace();
        }



    }



    private class myAdapter extends BaseAdapter
{

    private Context context;
    private List<Integer> list;


    myAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.gc();
        List_Main.myHolder holder = new List_Main.myHolder();
        convertView= LayoutInflater.from(context).inflate(R.layout.imgview,parent,false);

        holder.i = (ImageView) convertView.findViewById(R.id.img);

        int a=(Integer) getItem(position);




        holder.i.setImageResource(a);

        return convertView;
    }


}
    private static class myHolder {
        ImageView i;

    }
    //************************************************************text*****************************************

    public void discardEditext(View v)
    {
        vis=View.VISIBLE;
        openEditext=false;
        findViewById(R.id.listlayout).setVisibility(vis);
        currentFrag="MainActivity";
       // getMenuInflater().inflate(R.menu.menu, mymenu);
        loggedin=true;
        init_list();
        Fragment f=getFragmentManager().findFragmentById(R.id.fragment);
        getFragmentManager().beginTransaction().remove(f).commit();
    }
    public void saveEditext(View v)
    {
        if(empty)
        {
            EditText editText = (EditText) findViewById(R.id.mainEditext);
            String msg=editText.getText().toString();
            int nol = editText.getLineCount();

            for (int no = nol; no < 5; no++) {
                msg = (msg.concat("\n"));
            }
             mylist.add(0,new itemData(0,msg,currentUser.getUser()));
            mAdapter.notifyDataSetChanged();
            mAdapter = new itemAdapter(this, mylist);
            notesView.setAdapter(mAdapter);
            dbd.updateAllNotes(mylist);

        }
        else {
            EditText editText = (EditText) findViewById(R.id.mainEditext);
            test.setText(editText.getText().toString());

            for (int i = 0; i < mylist.size(); i++) {
                itemData it = mylist.get(i);
                if (it.getText().equals(test.getText())) {
                    mylist.remove(i);
                    break;
                }
            }
            int nol = editText.getLineCount();
            String temp = test.getText();
            for (int no = nol; no < 5; no++) {
                temp = (temp.concat("\n"));
            }
            test.setText(temp);
            mylist.add(0, test);
            mAdapter.notifyDataSetChanged();
            mAdapter = new itemAdapter(this, mylist);
            notesView.setAdapter(mAdapter);
            dbd.updateAllNotes(mylist);


        }
        openEditext=false;
        findViewById(R.id.listlayout).setVisibility(View.VISIBLE);
        currentFrag = "MainActivity";
        //  getMenuInflater().inflate(R.menu.menu, mymenu);
        loggedin = true;
        init_list();
        Fragment f = getFragmentManager().findFragmentById(R.id.fragment);
        getFragmentManager().beginTransaction().remove(f).commit();
    }

    //****************************************************************signin*******************************************************
    static User currentUser;
    public void login_signin(View v)
    {

        String user = ((TextView) findViewById(R.id.login_user)).getText().toString();
        String pass = ((TextView) findViewById(R.id.login_pass)).getText().toString();
        User u=dbl.getUser(user,pass);
        if(u!=null) {
            currentUser=u;

            vis=View.VISIBLE;
openEditext=false;
            findViewById(R.id.listlayout).setVisibility(View.VISIBLE);
            currentFrag="MainActivity";

            Toast.makeText(this, "Welcome\n"+currentUser.getUser(), Toast.LENGTH_SHORT).show();
            loggedin=true;
            init_list();
            Fragment f=getFragmentManager().findFragmentById(R.id.fragment);
            getFragmentManager().beginTransaction().remove(f).commit();
            Boolean auto_log;
            CheckBox autol=(CheckBox)findViewById(R.id.checkBox);
            auto_log = autol.isChecked();
            if(auto_log)
            {
                shared=getSharedPreferences("logindetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=shared.edit();
                editor.putString("user",user);
                editor.putString("pass",pass);
                editor.putBoolean("auto",true);
                editor.apply();
            }



        }
        else {
            new AlertDialog.Builder(List_Main.this).setTitle("ERROR").setMessage("User does not exists.\nPlease Register.").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setIcon(android.R.drawable.ic_dialog_info).show();
        }



    }
    public void login_register(View v)
    {
        currentFrag="FragSignUp";
        Fragment f=getFragmentManager().findFragmentById(R.id.fragment);
        getFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
        getFragmentManager().beginTransaction().replace(R.id.fragment, new FragSignUp()).commit();
    }


    //****************************************************************signup*******************************************************

    public void signup_signup(View v)
    {

        boolean b=true;
        String msg="Some Error Occured";
        TextView t1=(TextView) findViewById(R.id.signup_user);
        TextView t2=(TextView) findViewById(R.id.signup_pass);
        TextView t3=(TextView) findViewById(R.id.signup_confpass);

        String s1=t1.getText().toString();
        String s2=t2.getText().toString();
        String s3=t3.getText().toString();
        if(s1.equals("")||s2.equals("")||s3.equals("")) {
            b=false;
            msg=  "Some Column/Columns are empty";
        }
        else if(!s2.equals(s3)) {

            b=false;
            msg="Passwords don't match";
        }
        else {
            List<User> list =dbl.getAllUsers();

            for (User u : list) {

                if (u.getUser().equals(t1.getText().toString())) {

                    b = false;
                    msg="User already exists";
                    break;
                }

            }
        }
        if(b)
        {
            msg="User Successfully added";
            User u=new User(t1.getText().toString(),t2.getText().toString());
            dbl.addUser(u);
            new AlertDialog.Builder(List_Main.this).setTitle("WELCOME").setMessage(msg).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Button button=(Button)findViewById(R.id.signup_signin);
                    button.performClick();
                }
            }).setIcon(android.R.drawable.ic_dialog_info).show();


        }
        else {
            new AlertDialog.Builder(List_Main.this).setTitle("ERROR").setMessage(msg).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setIcon(android.R.drawable.ic_dialog_alert).show();

        }

    }
    public void signup_signin(View v)
    {
        currentFrag="FragLogin";
        Fragment f=getFragmentManager().findFragmentById(R.id.fragment);
        getFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
        getFragmentManager().beginTransaction().replace(R.id.fragment, new FragLogin()).commit();
    }

    @Override
    public void onBackPressed() {

        if(!currentFrag.equals( "editText")){


            currentFrag="FragLogin";

           super.onBackPressed();
        }
        else
        {
            if(autoSave)
            {
                try {
                    Button b = (Button) findViewById(R.id.button2);
                    b.performClick();
                }
                catch (Exception e)
                {
                    vis=View.VISIBLE;
                    openEditext=false;
                    findViewById(R.id.listlayout).setVisibility(View.VISIBLE);
                    currentFrag="MainActivity";
                   // getMenuInflater().inflate(R.menu.menu, mymenu);
                    loggedin=true;
                    init_list();
                    Fragment f=getFragmentManager().findFragmentById(R.id.fragment);
                    getFragmentManager().beginTransaction().remove(f).commit();
                }

            }

        }
    }

}
