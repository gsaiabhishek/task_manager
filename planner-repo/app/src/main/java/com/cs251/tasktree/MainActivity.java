package com.cs251.tasktree;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    Activity activity;
    public RecyclerView recyclerView;
    public ProductAdapter adapter;
    public List<Product> productList;
    public String subtask="";
    static final int DIALOG_ID=0;

    int year_x,month_x,day_x;
    String dateFinal;


//    public static String KEY_TASK = "task";
//    public static String KEY_DATE = "date";
//    public static String KEY_ID = "id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery,R.id.nav_tools)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//        productList=new ArrayList<>();
//
//        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        activity=MainActivity.this;
//        DataBase mydb;
//        mydb = new DataBase(activity);
//        Cursor cursor = mydb.getData();
////        if(!productList.isEmpty()){productList.clear();}
////        if(!idList.isEmpty()){idList.clear();}
// //       productList.add(new Product("Zen","If you chase two rabbits, you catch none.",""));
//
////        idList.add(new Integer(0));
////        if(cursor!=null){
////            cursor.moveToFirst();
////            while (cursor.isAfterLast() == false) {
////                Product mapToday = new Product(cursor.getString(1).toString(),
////                        Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy"));
////                productList.add(mapToday);
//////                idList.add(new Integer(cursor.getInt(3)));
////                cursor.moveToNext();
////            }
////        }
//        cursor.moveToPrevious();
//        while(cursor.moveToNext()){
//
//            String task_name=cursor.getString(1);
//            String task_date=Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy");
//            String task_description=cursor.getString(3);
//            int i= new Integer(cursor.getInt(0));
//            Cursor cursor1= mydb.getSubtasksData(i);
//            subtask="";
//            cursor1.moveToPrevious();
//            while(cursor1.moveToNext()) {
//                String subtaskname=cursor1.getString(1);
//                subtask=subtask+subtaskname+"\n";
//            }
//            productList.add(new Product(i,task_name,task_date,task_description,subtask));
//
//
//        }
//            adapter = new ProductAdapter(activity, productList);
//            recyclerView.setAdapter(adapter);


//        adapter.setOnItemClickListner(new ProductAdapter.onItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                  Intent intent=new Intent(MainActivity.this,EditTask.class);
//                  startActivity(intent);
//            }
//        });

   }


//
//    public void populateData()
//    {
//        productList.clear();
//
//        subtask="";
////        productList.add(new Product("Zen","If you chase two rabbits, you catch none."));
////        idList.add(new Integer(0));
//        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        activity=MainActivity.this;
//
//        DataBase mydb;
//        mydb = new DataBase(MainActivity.this);
//        Cursor cursor = mydb.getData();
////        cursor.moveToFirst();
//  //      productList.add(new Product("Zen","If you chase two rabbits, you catch none.",""));
//
//        cursor.moveToPrevious();
//        while(cursor.moveToNext()){
//            String task_name=cursor.getString(1);
//            String task_date=Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy");
//            String task_description=cursor.getString(3);
//            int i= new Integer(cursor.getInt(0));
//            Cursor cursor1= mydb.getSubtasksData(i);
//            subtask="";
//             cursor1.moveToPrevious();
//            while(cursor1.moveToNext()) {
//                String subtaskname=cursor1.getString(1);
//                subtask=subtask+subtaskname+"\n";
//            }
//            productList.add(new Product(i,task_name,task_date,task_description,subtask));
//
//
//        }
//
////        if(cursor.moveToFirst()){
////            while (! cursor.isAfterLast() ) {
////                Product mapToday = new Product(cursor.getString(1).toString(),
////                        Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy"));
////                productList.add(mapToday);
//////                idList.add(new Integer(cursor.getInt(3)));
////                cursor.moveToNext();
////            }
////        }
//
//        adapter= new ProductAdapter(MainActivity.this,productList);
//        recyclerView.setAdapter(adapter);
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        startActivity(getIntent());
//        populateData();
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void newtaskwindow(View view){
        Intent intent= new Intent(this, AddTask.class);
        startActivity(intent);
    }

//        public void showDialogOnButtonClick(){
//
//        dateselect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(DIALOG_ID);
//            }
//        });
//    }
//    @Override
//    protected Dialog onCreateDialog(int id){
//        if(id==DIALOG_ID){
//            return new DatePickerDialog(this,dpickerListner,year_x,month_x,day_x);
//        }else {
//            return null;
//        }
//    }
//    private DatePickerDialog.OnDateSetListener dpickerListner=
//            new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    year_x=year;
//                    month_x=month+1;
//                    day_x=dayOfMonth;
//                    btn= (Button) findViewById(R.id.newtaskdateid);
//                    btn.setText(day_x+"/"+month_x+"/"+year_x);
//                    dateFinal=day_x+"/"+month_x+"/"+year_x;
//
//                }
//            };



}
