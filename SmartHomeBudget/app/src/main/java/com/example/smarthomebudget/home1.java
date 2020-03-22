package com.example.smarthomebudget;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;



public class home1 extends Fragment {

    TextView tdate,tinc,texp,mcat,mitem,sav;
    SQLiteHelper helper;
    SQLiteDatabase db;
    String saving;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home1, container, false);


        helper = new SQLiteHelper(getContext());
        db = helper.getReadableDatabase();
        tdate=(TextView)v.findViewById(R.id.tdate);
        tinc=(TextView)v.findViewById(R.id.tinc);
        texp=(TextView)v.findViewById(R.id.texp);
        sav=(TextView)v.findViewById(R.id.sav);
        mcat=(TextView)v.findViewById(R.id.mcat);
        mitem=(TextView)v.findViewById(R.id.mitem);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String date = dateFormat.format(calendar.getTime());

        try {
            tdate.setText("Date : " + date);
            tdate.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            tinc.setText("Total Income : " + getincome());
            tinc.setTextColor(getResources().getColor(R.color.green));

            texp.setText("Total Expense : " + getexpense());
            texp.setTextColor(getResources().getColor(R.color.red));

            sav.setText("Total Saving : " + saving);
            if (Integer.valueOf(saving) < 0)
                sav.setTextColor(getResources().getColor(R.color.red));
            else
                sav.setTextColor(getResources().getColor(R.color.green));

            mcat.setText("Maximum Spent Catagory : " + getcat());
            mitem.setText("Maximum Spent Item : " + getitem());

        }catch (Exception e){}

        return v;
    }

    private String getitem() {
        Cursor c = helper.getmitem(db);

        String expense="";
        while(c.moveToNext()) {
            expense=c.getString(0)+"\nAmount : "+c.getString(1);

        }


        return expense;
    }

    private String getcat() {
        Cursor c = helper.getmcat(db);

        String expense="";
        while(c.moveToNext()) {
            expense=c.getString(0)+"\nAmount : "+c.getString(1);

        }


        return expense;
    }

    private String getexpense() {
        Cursor c = helper.gettexpense(db);

        String expense="";
        while(c.moveToNext()) {
            expense=c.getString(0);

        }
        if(saving==null && expense==null){
            saving=null;
        }
        else if(saving==null)
            saving='-'+expense;
        else
        saving=String.valueOf(Integer.valueOf(saving)-Integer.valueOf(expense));


        return expense;
    }

    private String getincome() {
        Cursor c = helper.gettincome(db);

        String income="";
        while(c.moveToNext()) {
            income=c.getString(0);

        }
        saving=income;
        return income;
    }


}
