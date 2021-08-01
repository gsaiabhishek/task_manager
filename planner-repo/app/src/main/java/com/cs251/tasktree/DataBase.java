package com.cs251.tasktree;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ToDoDBHelper.db";
    public static final String CONTACTS_TABLE_NAME = "todo";
    public static final String REFERENCE_TABLE_NAME = "ref";

    public DataBase(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(
                "CREATE TABLE "+CONTACTS_TABLE_NAME +
                        "(id INTEGER PRIMARY KEY, task TEXT, dateStr INTEGER, description TEXT)"
        );
        db.execSQL(
                "CREATE TABLE "+REFERENCE_TABLE_NAME +
                        "(id INTEGER PRIMARY KEY, previd INTGER, parentid INTEGER , FOREIGN KEY (previd) REFERENCES todo(id) ) "
        );


        String s;


//        call();
//    }
//    public void call(){
        long temp;

        temp=getDate("31/12/2019");
        db.execSQL("INSERT INTO todo (id, task, Description, dateStr) VALUES (1, 'Acads', 'Padhai ki baatein', "+ temp+")" );
        db.execSQL("INSERT INTO ref (id, previd, parentid) VALUES (1,1,0)");
        temp=getDate("30/12/2019");
        db.execSQL("INSERT INTO todo (id, task, Description, dateStr) VALUES (2, 'Self improvement', 'Reading list, blogs, exercise, etc.', "+temp+")");
        db.execSQL("INSERT INTO ref (id, previd, parentid) VALUES (2,2,0)");
        temp=getDate("");
        db.execSQL("INSERT INTO todo (id, task, Description, dateStr) VALUES (3, 'Research', 'Pet projects',"+temp+")");
        db.execSQL("INSERT INTO ref (id, previd, parentid) VALUES (3,3,0)");
        temp=getDate("");
        db.execSQL("INSERT INTO todo (id, task, Description, dateStr) VALUES (4, 'Hobbies', '<3'," +temp+")");
        db.execSQL("INSERT INTO ref (id, previd, parentid) VALUES (4,4,0)");

        temp=getDate("27/2/2021");
        db.execSQL("INSERT INTO todo (id, task, Description, dateStr) VALUES (5, 'Exercise', 'someday?',  "+temp+")");
        db.execSQL("INSERT INTO ref (id, previd, parentid) VALUES (5,5,2)");
        temp=getDate("");
        db.execSQL("INSERT INTO todo (id, task, Description, dateStr) VALUES (6, 'Reading list', 'My bucket list:\\nHear the Wind Sing\\nThe Fountainhead\\nAtlas Shrugged\\nA prisoner of birth' ,"+temp+")");
        db.execSQL("INSERT INTO ref (id, previd, parentid) VALUES (6,6,2)");
        temp=getDate("29/10/2021");
        db.execSQL("INSERT INTO todo (id, task, Description, dateStr) VALUES (7, 'Origami', 'cranes and tigers',"+ temp+")");
        db.execSQL("INSERT INTO ref (id, previd, parentid) VALUES (7,7,4)");
        temp=getDate("29/10/2021");
        db.execSQL("INSERT INTO todo (id, task, Description, dateStr) VALUES (8, 'Drum practice!', 'Aim:\\nHallowed be thy name,\\nAcid Rain (LTE)', "+temp+")");
        db.execSQL("INSERT INTO ref (id, previd, parentid) VALUES (8,8,4)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+CONTACTS_TABLE_NAME);
        onCreate(db);
    }



    private long getDate(String day) {
        if(day==""){return 0;}
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy", Locale.getDefault());
            Date date = new Date();
            try {
                date = dateFormat.parse(day);
            } catch (ParseException e) {
            }
            return date.getTime();
        }
    }



    public long insertContact  (String task, String dateStr, String description)
    {
        Date date;
        long idinserted;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("task", task);
        contentValues.put("dateStr", getDate(dateStr));
        contentValues.put("description", description);
        idinserted= db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return idinserted;
    }

    public void insertParent(int i1, int i2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("previd", i1);
        contentValues.put("parentid", i2);
        db.insert(REFERENCE_TABLE_NAME, null, contentValues);

    }

    public boolean updateContact (String id, String task, String dateStr, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("task", task);
        contentValues.put("dateStr", getDate(dateStr));
        contentValues.put("description", description);

        db.update(CONTACTS_TABLE_NAME, contentValues, "id = ? ", new String[] { id } );

        return true;
    }
    public Cursor getParent(int i){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+REFERENCE_TABLE_NAME+" INNER JOIN "+CONTACTS_TABLE_NAME+" ON todo.id = ref.previd  WHERE ref.previd = "+i,null);
        return res;

    }




    public Cursor getData(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+CONTACTS_TABLE_NAME+" INNER JOIN "+REFERENCE_TABLE_NAME+" ON todo.id = ref.previd  WHERE ref.parentid = 0 order by id desc",null);
        return res;


    }
    public Cursor getSubtasksData(int i){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+CONTACTS_TABLE_NAME+" INNER JOIN "+REFERENCE_TABLE_NAME+" ON todo.id = ref.previd "+" WHERE ref.parentid = "+ i ,null);
        return res;
    }

    public Cursor getDataSpecific(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+CONTACTS_TABLE_NAME+" WHERE id = '"+id+"' order by id desc", null);
        return res;

    }

    public Cursor getDataToday(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+CONTACTS_TABLE_NAME+
                " WHERE date(datetime(dateStr / 1000 , 'unixepoch', 'localtime')) = date('now', 'localtime') order by id desc", null);
        return res;

    }
    public Cursor getDataDate(String date){
        long datenew=getDate(date);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+CONTACTS_TABLE_NAME+
                " WHERE dateStr= "+datenew+" order by id desc", null);
        return res;

    }


    public Cursor getDataTomorrow(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+CONTACTS_TABLE_NAME+
                " WHERE date(datetime(dateStr / 1000 , 'unixepoch', 'localtime')) = date('now', '+1 day', 'localtime')  order by id desc", null);
        return res;

    }


    public Cursor getDataUpcoming(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+CONTACTS_TABLE_NAME+
                " WHERE date(datetime(dateStr / 1000 , 'unixepoch', 'localtime')) > date('now', '+1 day', 'localtime') order by id desc", null);
        return res;

    }



}

