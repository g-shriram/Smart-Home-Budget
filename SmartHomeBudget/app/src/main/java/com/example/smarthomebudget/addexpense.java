package com.example.smarthomebudget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;

public class addexpense extends Fragment {


    private SQLiteHelper helper;
    private SQLiteDatabase db;
    EditText price;
    private ArrayList<Object>arr_expense,arr_expense1;
   AutoCompleteTextView exp_cat,exp;
    ArrayAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_addexpense, container, false);
    getActivity().setTitle("Add Expense");



      exp=(AutoCompleteTextView)v.findViewById(R.id.exp);
         exp_cat=(AutoCompleteTextView)v.findViewById(R.id.exp_cat);
       price=(EditText)v.findViewById(R.id.exp_price);
        helper=new SQLiteHelper(getContext());
    db=helper.getReadableDatabase();
        Button exp_but=(Button)v.findViewById(R.id.exp_button);
        final TextView exptv=(TextView)v.findViewById(R.id.exptv);



        arr_expense=new ArrayList<>();
        arr_expense1=new ArrayList<>();

            getexpense();

        exp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                   setcat();
                }
            }
        });

        exp.requestFocus();
        exp_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float amt;
                String cat=exp_cat.getText().toString();
                String item=exp.getText().toString();

                if(!price.getText().toString().equals(""))
                {
                    amt=Float.parseFloat(price.getText().toString());}
                else
                    amt=0;

                if(amt!=0 && !cat.equals("")  && !item.equals("")){
                    helper.addexpense(db,item,cat,amt);
                    exptv.setText("Expense Added Successfully !");
                    exp.setText("");
                    exp_cat.setText("");
                    price.setText("");
                    exp.requestFocus();
                    getexpense();




                }
                else
                {

                    Toast.makeText(getContext(),"Please fill all the fields .",Toast.LENGTH_LONG).show();
                }

            }
        });


        return v;


    }

    private void setcat() {

         if(arr_expense.contains(exp.getText().toString())){
             exp_cat.setText(arr_expense1.get(arr_expense.indexOf(exp.getText().toString())).toString());
             exp_cat.setFocusable(false);
             price.requestFocus();
         }
         else {
             exp_cat.setFocusable(true);
         }


    }

    void getexpense(){

            Cursor e = helper.expense_report(db);

arr_expense.clear();
arr_expense1.clear();
            e.moveToFirst();
            while(e.moveToNext()) {
                if(!arr_expense.contains(e.getString(0))){
                arr_expense.add(e.getString(0).toString());
                arr_expense1.add(e.getString(1).toString());}

            }
            e.close();
        adapter = new
                ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arr_expense);
        exp.setAdapter(adapter);
        exp.setThreshold(1);

    }



}
