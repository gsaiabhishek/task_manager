package com.cs251.tasktree;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditTask extends AppCompatActivity {

     Intent intent;
     int id;
     String idstr;
     DataBase mydb;
     Button back;
     List<subProduct> subProductList;
    Activity activity;
    public RecyclerView recyclerView;
    public subProductAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        intent=getIntent();
        id=intent.getIntExtra("id",0);
        idstr=Integer.toString(id);


        mydb=new DataBase(getApplicationContext());
        Cursor cursor=mydb.getDataSpecific(idstr);
//        String dateFinal;
//        Calendar cal = Function.Epoch2Calender(cursor.getString(2).toString());
//        year_x = cal.get(Calendar.YEAR);
//        month_x= cal.get(Calendar.MONTH);
//        day_x = cal.get(Calendar.DAY_OF_MONTH);
//        dateFinal=day_x+"/"+month_x+"/"+year_x;
       if(cursor!=null) {
           cursor.moveToFirst();

           TextView task_name = (TextView) findViewById(R.id.task_name_display);
           TextView task_description = (TextView) findViewById(R.id.task_description_display);
           TextView task_date = (TextView) findViewById(R.id.task_date_display);
           task_name.setText(cursor.getString(1).toString());
           task_description.setText(cursor.getString(3).toString());
           String finaldate= Function.Epoch2DateString(cursor.getString(2).toString(), "dd/MM/yyyy");
           if(finaldate.equals("01/01/1970")){finaldate=" ";}
           task_date.setText(finaldate);
       }
       goBack();


        subProductList=new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        activity=EditTask.this;
        DataBase mydb;
        mydb = new DataBase(activity);
        Cursor cursorsub = mydb.getSubtasksData(id);

        cursorsub.moveToPrevious();
        while(cursorsub.moveToNext()){

            String task_name=cursorsub.getString(1);
            String task_date=Function.Epoch2DateString(cursorsub.getString(2).toString(), "dd-MM-yyyy");
            String task_description=cursorsub.getString(3);
            int i= new Integer(cursorsub.getInt(0));
            Cursor cursor1= mydb.getSubtasksData(i);
            cursor1.moveToPrevious();
            if(task_date.equals("01-01-1970")){task_date=" ";}
            subProductList.add(new subProduct(i,task_name,task_date,task_description));


        }
        adapter = new subProductAdapter(activity, subProductList);
        recyclerView.setAdapter(adapter);


    }

   public void populateData(){

        subProductList.clear();

       recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       activity=EditTask.this;
       DataBase mydb;
       mydb = new DataBase(activity);
       Cursor cursorsub = mydb.getSubtasksData(id);

       cursorsub.moveToPrevious();
       while(cursorsub.moveToNext()){

           String task_name=cursorsub.getString(1);
           String task_date=Function.Epoch2DateString(cursorsub.getString(2).toString(), "dd-MM-yyyy");
           String task_description=cursorsub.getString(3);
           int i= new Integer(cursorsub.getInt(0));
           Cursor cursor1= mydb.getSubtasksData(i);
           cursor1.moveToPrevious();
           if(task_date.equals("01-01-1970")){task_date=" ";}
           subProductList.add(new subProduct(i,task_name,task_date,task_description));


       }
       adapter = new subProductAdapter(activity, subProductList);
       recyclerView.setAdapter(adapter);

   }



    @Override
    public void onResume() {
        super.onResume();
        populateData();

    }

    public void goBack(){
        back=(Button)findViewById(R.id.backdisplaybutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addSubTask(View view){
        Intent intent= new Intent(EditTask.this,AddSubTask.class);
        intent.putExtra("idparent",id);
        startActivity(intent);
    }

}
