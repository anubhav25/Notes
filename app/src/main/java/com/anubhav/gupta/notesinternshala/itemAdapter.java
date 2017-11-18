package com.anubhav.gupta.notesinternshala;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * Created by ANUBHAV on 4/13/2017.
 */

 class itemAdapter extends BaseAdapter {

    private Context context;
    private List<itemData> list;


     itemAdapter(Context context, List<itemData> list) {
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
        itemAdapter.FolderHolder holder = new FolderHolder();
        convertView= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        holder.text = (TextView) convertView.findViewById(R.id.textdata);

        itemData item=(itemData) getItem(position);


        holder.item_count = (TextView) convertView.findViewById(R.id.textno);


        holder.text.setText(item.getText());
        int no=position +1;
        item.setUser(List_Main.currentUser.getUser());
        item.count=no;
        String t;
        if(no<10)
             t="  "+no;
        else
            t=""+no;
        holder.item_count.setText(t);

        holder.item_count.setText(t);

        return convertView;
    }
    private static class FolderHolder {

        TextView text,item_count;

    }

}
