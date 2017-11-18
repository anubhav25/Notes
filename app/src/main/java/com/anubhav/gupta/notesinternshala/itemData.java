package com.anubhav.gupta.notesinternshala;

/**
 * Created by ANUBHAV on 4/13/2017.
 */

 class itemData {
    int count=0;
    private String text;
    private String user;

     String getUser() {
        return user;
    }

     void setUser(String user) {
        this.user = user;
    }

     itemData(int count, String text, String user) {
        this.count = count;
        this.text = text;
        this.user = user;
    }

     int getCount() {
        return count;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
