   package com.example.videolecture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.videolecture.model.Result;

import java.util.ArrayList;


/**
 * Created by lalit on 7/25/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Video_Lec";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Video_Lec");
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_Video_Lec_TABLE = "CREATE TABLE Video_Lec(loginId INTEGER, ques TEXT, categId TEXT, ans TEXT)";
        db.execSQL(CREATE_Video_Lec_TABLE);
    }


    //------------basket Order data----------------
    public boolean upsertProductData(Result result) {
        boolean done = false;
        Result data = null;
        if (result.getId() != null) {
            data = getProductData(result.getId());
            if (data == null) {
                done = insertProductData(result);
                done = true;
            } else {
                done = updateProductData(result);
                done = false;
            }
        }
        return done;
    }

    //GetAll Basket Order
    private void populateProductData(Cursor cursor, Result ob) {
        ob.setId(cursor.getString(0));
        ob.setQuestion(cursor.getString(1));
        ob.setCategoryId(cursor.getString(2));
        ob.setAnswer(cursor.getString(3));
    }

    //show  Basket Order list data
    public ArrayList<Result> GetAllProductData() {
        ArrayList<Result> list = new ArrayList<Result>();
        String query = "Select * FROM Video_Lec ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateProductData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

//    //show  Basket Order list data
//    public ArrayList<Result> GetAllProductDataBasedOnId(String id) {
//        ArrayList<Result> list = new ArrayList<Result>();
//        String query = "Select * FROM Video_Lec WHERE id= " + id + "";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//
//            while (cursor.isAfterLast() == false) {
//                Result ob = new Result();
//                populateProductData(cursor, ob);
//                list.add(ob);
//                cursor.moveToNext();
//            }
//        }
//        db.close();
//        return list;
//    }

    //show  Basket Order list data
    public ArrayList<Result> GetAllProductDataBasedOnId(String id) {
        ArrayList<Result> list = new ArrayList<Result>();
        String query = "Select * FROM Video_Lec WHERE categId= '" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateProductData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }

    //insert Basket Order data
    public boolean insertProductData(Result result) {
        ContentValues values = new ContentValues();
        populateProductDataValue(result, values);
        SQLiteDatabase db = this.getWritableDatabase();

        long i = (long) db.insert("Video_Lec", null, values);
        db.close();
        return i > 0;
    }

    //Basket Order data by id
    public Result getProductData(String id) {
        String query = "Select * FROM Video_Lec WHERE categId= " + id + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateProductData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    private void populateProductDataValue(Result ob, ContentValues values) {
        values.put("vid_id", ob.getId());
        values.put("vid_ques", ob.getQuestion());
        values.put("cat_id", ob.getCategoryId());
        values.put("vid_ans", ob.getAnswer());
    }

    //ProductId,ProductName,BasketId,StoreId,Quantity,Price,OrderTime  MyOrderDataEntity
    //update Basket Order data
    public boolean updateProductData(Result ob) {
        ContentValues values = new ContentValues();
        populateProductDataValue(ob, values);

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("Video_Lec", values, "id = " + ob.getId() + "", null);
        db.close();
        return i > 0;
    }

    // delete Basket Order Data By Store Id ...........
    public boolean deleteProductDataById(String id) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "id = " + id + " ";
        db.delete("Video_Lec", query, null);
        db.close();
        return result;
    }

    // delete all  Data
    public boolean deleteAllProductData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Video_Lec", null, null);
        db.close();
        return result;
    }

    // get Basket OrderData
//    public Result getProductData(String id) {
//
//        String query = "Select * FROM Video_Lec";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Result data = new Result();
//
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            populateProductData(cursor, data);
//
//            cursor.close();
//        } else {
//            data = null;
//        }
//        db.close();
//        return data;
//    }



//    //------------basket Order data----------------
//    public boolean upsertProductData(Result ob) {
//        boolean done = false;
//        Result data = null;
//        if (ob.getId() != null) {
//            data = getCheckBoxData(ob.getId());
//            if (data == null) {
//                done = insertCheckBoxData(ob);
//                done = true;
//            } else {
//                done = updateCheckBoxData(ob);
//                done = false;
//            }
//        }
//        return done;
//    }


//    //GetAll Basket Order
//    private void populateCheckBoxData(Cursor cursor, ContentDataAsArray ob) {
//        ob.setTv_type(cursor.getString(0));
//        ob.setTv_name(cursor.getString(1));
//        ob.setImage(cursor.getString(2));
//        ob.setTv_address(cursor.getString(3));
//        ob.setCity(cursor.getString(4));
//        ob.setState(cursor.getString(5));
//        ob.setTv_code(cursor.getInt(6));
//    }
//
//
//    //show  Basket Order list data
//    public ArrayList<ContentDataAsArray> GetAllCheckBoxData() {
//        ArrayList<ContentDataAsArray> list = new ArrayList<ContentDataAsArray>();
//        String query = "Select * FROM CB_TV_Data ";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//
//            while (cursor.isAfterLast() == false) {
//                ContentDataAsArray ob = new ContentDataAsArray();
//                populateCheckBoxData(cursor, ob);
//                list.add(ob);
//                cursor.moveToNext();
//            }
//        }
//        db.close();
//        return list;
//    }
//
//    //Basket Order data by id
//    public ContentDataAsArray getCheckBoxData(int tv_code) {
//        String query = "Select * FROM CB_TV_Data WHERE tv_code= " + tv_code + "";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        ContentDataAsArray data = new ContentDataAsArray();
//
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            populateCheckBoxData(cursor, data);
//
//            cursor.close();
//        } else {
//            data = null;
//        }
//        db.close();
//        return data;
//    }
//
//
//    //    /nsert Basket Order data
//    public boolean insertCheckBoxData(ContentDataAsArray ob) {
//        ContentValues values = new ContentValues();
//        populateCheckBoxDataValue(ob, values);
//        SQLiteDatabase db = this.getWritableDatabase();
//        long i = db.insert("CB_TV_Data", null, values);
//        db.close();
//        return i > 0;
//    }
//
//
//    private void populateCheckBoxDataValue(ContentDataAsArray ob, ContentValues values) {
//        values.put("tv_type", ob.getTv_type());
//        values.put("tv_name", ob.getTv_name());
//        values.put("tv_image", ob.getImage());
//        values.put("tv_address", ob.getTv_address());
//        values.put("tv_city", ob.getCity());
//        values.put("tv_state", ob.getState());
//        values.put("tv_code", ob.getTv_code());
//    }
//
//
//    public boolean updateCheckBoxData(ContentDataAsArray ob) {
//        ContentValues values = new ContentValues();
//        populateCheckBoxDataValue(ob, values);
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        long i = 0;
//        i = db.update("CB_TV_Data", values, "tv_code = " + ob.getTv_code() + "", null);
//        db.close();
//        return i > 0;
//    }
//
//    // delete Basket Order Data By Store Id ...........
//    public boolean deleteCheckBoxDataByTvCode(int tv_code) {
//        boolean result = false;
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "tv_code = " + tv_code + " ";
//        db.delete("CB_TV_Data", query, null);
//        db.close();
//        return result;
//    }
//
//
//    // delete all  Data
//    public boolean deleteAllCheckBoxData() {
//        boolean result = false;
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("CB_TV_Data", null, null);
//        db.close();
//        return result;
//    }
//
//
//    // get Basket OrderData
//    public ContentDataAsArray getCheckboxData() {
//
//        String query = "Select * FROM CB_TV_Data";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        ContentDataAsArray data = new ContentDataAsArray();
//
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            populateCheckBoxData(cursor, data);
//
//            cursor.close();
//        } else {
//            data = null;
//        }
//        db.close();
//        return data;
//    }

}