package com.cs251.tasktree;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowSubTask extends AppCompatActivity {

    Intent intent;
    int id;
    String idstr;
    DataBase mydb;
    Button edit,back;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_subtask);

        intent=getIntent();
        id=intent.getIntExtra("id",0);
        idstr=Integer.toString(id);
        mydb= new DataBase(ShowSubTask.this);
        Cursor cursor = mydb.getDataSpecific(idstr);

        if(cursor!=null) {
            cursor.moveToFirst();

            TextView task_name = (TextView) findViewById(R.id.subtasknamedisplay);
            TextView task_description = (TextView) findViewById(R.id.subtaskdescriptiondisplay);
            TextView task_date = (TextView) findViewById(R.id.subtaskdatedisplay);
            task_name.setText(cursor.getString(1).toString());
            task_description.setText(cursor.getString(3).toString());
            String finaldate= Function.Epoch2DateString(cursor.getString(2).toString(), "dd/MM/yyyy");
            if(finaldate.equals("01/01/1970")){finaldate=" ";}
            task_date.setText(finaldate);
        }

        onEdit();
        onBack();

    }

    public void updatedisplay(){
        id=intent.getIntExtra("id",0);
        idstr=Integer.toString(id);
        mydb= new DataBase(ShowSubTask.this);
        Cursor cursor = mydb.getDataSpecific(idstr);

        if(cursor!=null) {
            cursor.moveToFirst();

            TextView task_name = (TextView) findViewById(R.id.subtasknamedisplay);
            TextView task_description = (TextView) findViewById(R.id.subtaskdescriptiondisplay);
            TextView task_date = (TextView) findViewById(R.id.subtaskdatedisplay);
            task_name.setText(cursor.getString(1).toString());
            task_description.setText(cursor.getString(3).toString());
            String finaldate= Function.Epoch2DateString(cursor.getString(2).toString(), "dd/MM/yyyy");
            if(finaldate.equals("01/01/1970")){finaldate=" ";}
            task_date.setText(finaldate);
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        updatedisplay();
    }

    public void onBack(){
        back=(Button)findViewById(R.id.backinviewsubtask);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onEdit(){
        edit=(Button)findViewById(R.id.editinviewsubtask);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ShowSubTask.this,AddSubTask.class);
                intent.putExtra("id",id);
                intent.putExtra("isUpdate",true);
                startActivity(intent);
            }
        });


    }
}
