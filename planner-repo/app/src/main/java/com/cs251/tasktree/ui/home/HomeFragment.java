package com.cs251.tasktree.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs251.tasktree.AddTask;
import com.cs251.tasktree.DataBase;
import com.cs251.tasktree.Function;
import com.cs251.tasktree.MainActivity;
import com.cs251.tasktree.Product;
import com.cs251.tasktree.ProductAdapter;
import com.cs251.tasktree.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    int month_x,month_y,year_x,day_x;


    Activity activity;
    public RecyclerView recyclerView;
    public ProductAdapter adapter;
    public List<Product> productList;
    public String subtask="",dateFinal;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        mInflater = inflater;
        mContainer = container;
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });



//        final Calendar cal= Calendar.getInstance();
//        year_x=cal.get(Calendar.YEAR);
//        month_x=cal.get(Calendar.MONTH);
//        month_y=month_x+1;
//        day_x=cal.get(Calendar.DAY_OF_MONTH);
//        dateFinal=day_x+"/"+month_y+"/"+year_x;



        productList=new ArrayList<>();
        subtask="";
        recyclerView=(RecyclerView) root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        activity= getActivity();
        DataBase mydb;
        mydb = new DataBase(activity);
        Cursor cursor = mydb.getData();
        cursor.moveToPrevious();
        while(cursor.moveToNext()){

            String task_name=cursor.getString(1);
            String task_date= Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy");

            String task_description=cursor.getString(3);
            int i= new Integer(cursor.getInt(0));
            Cursor cursor1= mydb.getSubtasksData(i);
            subtask="";
            cursor1.moveToPrevious();
            while(cursor1.moveToNext()) {
                String subtaskname=cursor1.getString(1);
                subtask=subtask+subtaskname+"\n";
            }
            if(task_date.equals("01-01-1970")){task_date=" ";}

            productList.add(new Product(i,task_name,task_date,task_description,subtask));


        }
        adapter = new ProductAdapter(activity, productList);
        recyclerView.setAdapter(adapter);



        return root;
    }

    private long getDate(String day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {}
        return date.getTime();
    }



    @Override
    public void onResume() {
        super.onResume();
//        View root = mInflater.inflate(R.layout.fragment_home, mContainer, false);

        productList.clear();
        subtask="";
//        productList.add(new Product("Zen","If you chase two rabbits, you catch none."));
//        idList.add(new Integer(0));
//        recyclerView=(RecyclerView) root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        activity=getActivity();

        DataBase mydb;
        mydb = new DataBase(getActivity());
        Cursor cursor = mydb.getData();
//        cursor.moveToFirst();
        //      productList.add(new Product("Zen","If you chase two rabbits, you catch none.",""));

        while(cursor.moveToNext()){
            String task_name=cursor.getString(1);
            String task_date=Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy");

            String task_description=cursor.getString(3);
            int i= new Integer(cursor.getInt(0));
            Cursor cursor1= mydb.getSubtasksData(i);
            subtask="";
            cursor1.moveToPrevious();
            while(cursor1.moveToNext()) {
                String subtaskname=cursor1.getString(1);
                subtask=subtask+subtaskname+"\n";
            }
            if(task_date.equals("01-01-1970")){task_date=" ";}
            productList.add(new Product(i,task_name,task_date,task_description,subtask));


        }

//        if(cursor.moveToFirst()){
//            while (! cursor.isAfterLast() ) {
//                Product mapToday = new Product(cursor.getString(1).toString(),
//                        Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy"));
//                productList.add(mapToday);
////                idList.add(new Integer(cursor.getInt(3)));
//                cursor.moveToNext();
//            }
//        }

        adapter= new ProductAdapter(getActivity(),productList);
        recyclerView.setAdapter(adapter);


    }
}