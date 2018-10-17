package com.wecanstudio.xdsjs.save.Model.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static DbOpenHelper instance;

    //创建账单表
    private static final String BILL_TABLE_CREATE = "CREATE TABLE "
            + BillTableDao.TABLE_NAME + " ("
            + BillTableDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BillTableDao.COLUMN_NAME_TYPE + " TEXT, "
            + BillTableDao.COLUMN_NAME_MONEY + " TEXT, "
            + BillTableDao.COLUMN_NAME_REMARK + " TEXT, "
            + BillTableDao.COLUMN_NAME_UPLOAD + " INTEGER, "
            + BillTableDao.COLUMN_NAME_TIME + " TEXT); ";
    //创建次数表
    private static final String TIME_TABLE_CREAT = "CREATE TABLE " +
            TimeDao.TABLE_NAME + " (" +
            "time varchar(10) NOT NULL," +
            "type_0 integer default 10," +
            "type_1 integer default 10," +
            "type_2 integer default 10," +
            "type_3 integer default 10," +
            "type_4 integer default 10," +
            "type_5 integer default 10," +
            "type_6 integer default 10," +
            "type_7 integer default 10," +
            "type_8 integer default 10," +
            "type_9 integer default 10," +
            "type_10 integer default 10," +
            "type_11 integer default 10," +
            "type_12 integer default 10," +
            "type_13 integer default 10," +
            "type_14 integer default 10," +
            "type_15 integer default 10," +
            "type_16 integer default 10," +
            "type_17 integer default 10," +
            "type_18 integer default 10," +
            "type_19 integer default 10);";

    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static String getUserDatabaseName() {
        return "_bill.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BILL_TABLE_CREATE);
        db.execSQL(TIME_TABLE_CREAT);
        db.execSQL("insert into s_time (time) values(\"0\")");
        db.execSQL("insert into s_time (time) values(\"1\")");
        db.execSQL("insert into s_time (time) values(\"2\")");
        db.execSQL("insert into s_time (time) values(\"3\")");
        db.execSQL("insert into s_time (time) values(\"4\")");
        db.execSQL("insert into s_time (time) values(\"5\")");
        db.execSQL("insert into s_time (time) values(\"6\")");
        db.execSQL("insert into s_time (time) values(\"7\")");
        db.execSQL("insert into s_time (time) values(\"8\")");
        db.execSQL("insert into s_time (time) values(\"9\")");
        db.execSQL("insert into s_time (time) values(\"10\")");
        db.execSQL("insert into s_time (time) values(\"11\")");
        db.execSQL("insert into s_time (time) values(\"12\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
        }
        if (oldVersion < 3) {
        }
        if (oldVersion < 4) {
        }
    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}
