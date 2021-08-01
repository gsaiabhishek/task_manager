package com.cs251.tasktree;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {
     DataBase mydb;
     Button btn;
     static final int DIALOG_ID=0;
     int year_x,month_x,day_x,month_y;

     Intent intent;
     Boolean isUpdate;
     Button btnForBack;
     Button btnForSave;
     String nameFinal,dateFinal="",descriptionFinal;
     int idint;
     String id;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_task);

        mydb = new DataBase(getApplicationContext());

        final Calendar cal= Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);

        intent = getIntent();
        isUpdate = intent.getBooleanExtra("isUpdate", false);
        if (isUpdate) {
            init_update();
        }
        showDialogOnButtonClick();
        goBack();
        onSave();
    }
    public void init_update(){
        idint = intent.getIntExtra("id",1);
        id = Integer.toString(idint);
        EditText task_name = (EditText) findViewById(R.id.newtasknameid);
        EditText task_description = (EditText) findViewById(R.id.newtaskdescriptionid);
        Button task_date = (Button) findViewById(R.id.newtaskdateid);
        Cursor task = mydb.getDataSpecific(id);
        TextView toolbar_task_add_title = (TextView) findViewById(R.id.createtasknameid);
        toolbar_task_add_title.setText("Update Task");
        if (task != null) {
            task.moveToFirst();

            task_name.setText(task.getString(1).toString());
            task_description.setText(task.getString(3).toString());
            Calendar cal = Function.Epoch2Calender(task.getString(2).toString());
            year_x = cal.get(Calendar.YEAR);
            month_x= cal.get(Calendar.MONTH);
            month_y=month_x+1;
            day_x = cal.get(Calendar.DAY_OF_MONTH);
            dateFinal=day_x+"/"+month_y+"/"+year_x;
            String finaldate= Function.Epoch2DateString(task.getString(2).toString(), "dd/MM/yyyy");
            if(finaldate.equals("01/01/1970")){finaldate="SELECT DATE";}
            task_date.setText(finaldate);
        }

    }
    public void goBack(){
        btnForBack=(Button)findViewById(R.id.newtaskbackid);
        btnForBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void  onSave(){
        btnForSave=(Button)findViewById(R.id.newtasksaveid);
        btnForSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int errorStep = 0;
                EditText task_name = (EditText) findViewById(R.id.newtasknameid);
                EditText task_description = (EditText) findViewById(R.id.newtaskdescriptionid);
                btn= (Button) findViewById(R.id.newtaskdateid);
                nameFinal = task_name.getText().toString();
                descriptionFinal = task_description.getText().toString();


                /* Checking */
                if (nameFinal.trim().length() < 1) {
                    errorStep++;
                    task_name.setError("Provide a task name.");
                }


                if (errorStep == 0) {
                    if (isUpdate) {
                        if(mydb.updateContact(id, nameFinal,dateFinal,descriptionFinal)) {
                            Toast.makeText(getApplicationContext(), "Task Updated.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                            int idinserted=(int) mydb.insertContact(nameFinal, dateFinal,descriptionFinal);
                             mydb.insertParent(idinserted,0);
                            Toast.makeText(getApplicationContext(), "Task Added.", Toast.LENGTH_SHORT).show();

                    }

                    finish();

                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Give a Task Name", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
    public void showDialogOnButtonClick(){
        btn= (Button) findViewById(R.id.newtaskdateid);
        btn.setOnClickListener(new View.OnClickListener() {
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
                    btn= (Button) findViewById(R.id.newtaskdateid);
                    btn.setText(day_x+"/"+month_y+"/"+year_x);
                    dateFinal=day_x+"/"+month_y+"/"+year_x;

                }
            };

}
