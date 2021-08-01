package com.cs251.tasktree;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayView  extends AppCompatActivity {
    Button date,back;
    Activity activity;
    public RecyclerView recyclerView;
    public dateProductAdapter adapter;
    public List<Product> productList;
    String subtask;
    static final int DIALOG_ID=0;
    int year_x,month_x,day_x,month_y;
    String dateFinal;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_view);


        final Calendar cal= Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        month_y=month_x+1;
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        dateFinal=day_x+"/"+month_y+"/"+year_x;

        productList=new ArrayList<>();
        subtask="";
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DayView.this));
        activity= DayView.this;
        DataBase mydb;
        mydb = new DataBase(activity);
        Cursor cursor = mydb.getDataToday();
        cursor.moveToPrevious();
        while(cursor.moveToNext()){

            String task_name=cursor.getString(1);
            String task_date= Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy");
            String task_description=cursor.getString(3);
            int i= new Integer(cursor.getInt(0));

            subtask="Hierarchy: root/";
            int parentid=-1;
            String parentidstr="";
            Cursor cursorparent= mydb.getParent(i);
            while(cursorparent.moveToNext()) {

                parentidstr = cursorparent.getString(2).toString();
                parentid = Integer.parseInt(parentidstr);
            }
            if(parentid == 0){
                subtask=subtask+task_name;
            }
            else{

//                Cursor cursorans= mydb.getParent(parentid);
          //        String ansid="-1";
//                while(cursorans.moveToNext()) {
//
//                    ansid = cursorans.getString(2).toString();
////                String ansidstr=Integer.toString(ansid);
//                }
//                ansid=Integer.toString(parentid);
                Cursor cursoransdetails= mydb.getDataSpecific(parentidstr);
                String ansname="";
                while(cursoransdetails.moveToNext()) {

                    ansname = cursoransdetails.getString(1);
                }
                subtask=subtask+ansname+"/"+task_name;
            }
            if(task_date.equals("01-01-1970")){task_date=" ";}
            productList.add(new Product(i,task_name,task_date,task_description,subtask));


        }
        adapter = new dateProductAdapter(activity, productList);
        recyclerView.setAdapter(adapter);


        onBack();
        showDialogOnButtonClick();




    }

    public void showDialogOnButtonClick(){
        date= (Button) findViewById(R.id.dateselectorbutton);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID){
            return new DatePickerDialog(this,dpickerListner,year_x,month_x,day_x);
        }else {
            return null;
        }
    }
    private DatePickerDialog.OnDateSetListener dpickerListner=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    year_x=year;
                    month_x=month;
                    month_y=month_x+1;
                    day_x=dayOfMonth;
                    dateFinal=day_x+"/"+month_y+"/"+year_x;
                    TextView edittext=(TextView) findViewById(R.id.datepresent);
                    String str="Viewing Tasks on "+dateFinal;
                    edittext.setText(str);
                    update();


                }
            };



    @Override
    public void onResume(){
        super .onResume();
        productList.clear();
        subtask="";
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DayView.this));
        activity= DayView.this;
        DataBase mydb;
        mydb = new DataBase(activity);
        Cursor cursor = mydb.getDataDate(dateFinal);
        cursor.moveToPrevious();
        while(cursor.moveToNext()){

            String task_name=cursor.getString(1);
            String task_date= Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy");
            String task_description=cursor.getString(3);
            int i= new Integer(cursor.getInt(0));

            subtask="Hierarchy: root/";
            int parentid=-1;
            Cursor cursorparent= mydb.getParent(i);
            String parentidstr="";
            while(cursorparent.moveToNext()) {

                 parentidstr = cursorparent.getString(2).toString();
                parentid = Integer.parseInt(parentidstr);
            }
            if(parentid == 0){
                subtask=subtask+task_name;
            }
            else{

                Cursor cursoransdetails= mydb.getDataSpecific(parentidstr);
                String ansname="";
                while(cursoransdetails.moveToNext()) {

                    ansname = cursoransdetails.getString(1);
                }
                subtask=subtask+ansname+"/"+task_name;
            }
            if(task_date.equals("01-01-1970")){task_date=" ";}
            productList.add(new Product(i,task_name,task_date,task_description,subtask));


        }
        adapter = new dateProductAdapter(activity, productList);
        recyclerView.setAdapter(adapter);

    }






    public void update(){
        super .onPause();
        productList.clear();
        subtask="";
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DayView.this));
        activity= DayView.this;
        DataBase mydb;
        mydb = new DataBase(activity);
        Cursor cursor = mydb.getDataDate(dateFinal);
        cursor.moveToPrevious();
        while(cursor.moveToNext()){

            String task_name=cursor.getString(1);
            String task_date= Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy");
            String task_description=cursor.getString(3);
            int i= new Integer(cursor.getInt(0));

            subtask="Hierarchy: root/";
            int parentid=-1;
            Cursor cursorparent= mydb.getParent(i);
            String parentidstr="";
            while(cursorparent.moveToNext()) {

                parentidstr = cursorparent.getString(2).toString();
                parentid = Integer.parseInt(parentidstr);
            }
            if(parentid == 0){
                subtask=subtask+task_name;
            }
            else{

                Cursor cursoransdetails= mydb.getDataSpecific(parentidstr);
                String ansname="";
                while(cursoransdetails.moveToNext()) {

                    ansname = cursoransdetails.getString(1);
                }
                subtask=subtask+ansname+"/"+task_name;
            }
            if(task_date.equals("01-01-1970")){task_date=" ";}
            productList.add(new Product(i,task_name,task_date,task_description,subtask));


        }
        adapter = new dateProductAdapter(activity, productList);
        recyclerView.setAdapter(adapter);

    }

    public void onBack(){
        back=(Button)findViewById(R.id.datebackbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
