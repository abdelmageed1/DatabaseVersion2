package com.example.app_external_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class My_database extends SQLiteOpenHelper {

     public static final String DataBase_NAME ="cars.db" ;
     public static final String  TABLE_NAME="TB_Cars" ;

     public static final int DB_VERSION = 1 ;

     public static final String COL_COLOr = "color";
     public static final String  COL_id ="id";
     public static final String COL_MODEL = "model";
     public static final String COL_IMAGE="image" ;

      Context c ;
      public My_database(Context c ) {
        super(c, DataBase_NAME,null,DB_VERSION);
         this.c = c ;
     }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

          sqLiteDatabase.execSQL("Create Table "+ TABLE_NAME +" ( "+COL_id +" INTEGER primary key autoincrement ," +
                COL_MODEL  +" TEXt  ," +
                  COL_COLOr +" Text  )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

       // عمليات التعديل

    }
    // insert
    public boolean insertCar(Car c ){
          SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COLOr ,c.getColor());
        values.put(COL_MODEL,c.getModel());
        //  values.put(COL_id,c.getId());
        long res = db.insert(TABLE_NAME,null,values);
        return res != -1 ;


    }
    // update
    public boolean updatetCar(Car c ){
          SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COLOr ,c.getColor());
        values.put(COL_MODEL,c.getModel());

        long res = db.update(TABLE_NAME,values,null,null);
        return res > 0 ;


    }

    // method return num of row in taple
    public  long getCarsCount(){
       SQLiteDatabase db = getReadableDatabase();
       return DatabaseUtils.queryNumEntries(db,TABLE_NAME);

    }

    // Delet
    public boolean deletCar(Car c ){
        SQLiteDatabase db = getWritableDatabase();
        String args []= {String.valueOf(c.getId())} ;
        long res = db.delete(TABLE_NAME,"id=?",args);
        return res > 0 ;


    }
  //  دالة الاسترجاع للكل
    public ArrayList<Car> allCars(){

          ArrayList<Car> arr_cars = new ArrayList<>();
          SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_NAME,null);
          //  فحص هل الكيرسر فيه بيانانت ام لا
           if( cursor.moveToFirst())
           {
              do {
                  int id_index = cursor.getColumnIndex(COL_id);
                  int id = cursor.getInt(id_index);

                  int model_index =cursor.getColumnIndex(COL_MODEL);
                  String model = cursor.getString(cursor.getInt(model_index));

                  int color_index = cursor.getColumnIndex(COL_COLOr);
                  String color  = cursor.getString(cursor.getInt(color_index));

                  Car car = new Car(model,color,id);
                  arr_cars.add(car);
                 } while (cursor.moveToNext());
            cursor.close();
           }
          return arr_cars;
    }


 // دالة البحث باستخدام الموديل
    public ArrayList<Car> searchCars( String modelSearch){

        ArrayList<Car> arr_cars = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        // String args []= {modelSearch+"%"} ;  اى كلمة  تبدأ ب الموديل دا
       //  Cursor cursor = db.rawQuery("SELECT * from "+TABLE_NAME + " where " +COL_MODEL+ " like ?",args);

      String args []= {modelSearch} ;
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_NAME + " where " +COL_MODEL+ " =?",args);
        //  فحص هل الكيرسر فيه بيانانت ام لا
        if( cursor.moveToFirst())
        {
            do {
                int id_index = cursor.getColumnIndex(COL_id);
                int id = cursor.getInt(id_index);

                int model_index =cursor.getColumnIndex(COL_MODEL);
                String model = cursor.getString(cursor.getInt(model_index));

                int color_index = cursor.getColumnIndex(COL_COLOr);
                String color  = cursor.getString(cursor.getInt(color_index));

                Car car = new Car(model,color,id);
                arr_cars.add(car);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return arr_cars;
    }




}
