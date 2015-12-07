package myPackage.hellokevin;

/**
 * Created by cobalt on 8/27/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import myPackage.sqliteasset.SQLiteAssetHelper;


public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "THESISDATABASE.sqlite";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_NAME = "thesis";
    public static final String TABLE_NAME_2 = "faculty";
    public static final String TABLE_NAME_3 = "COMMON";

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_AUTHOR = "AUTHORS";
    public static final String KEY_ADVISER = "ADVISER";
    public static final String KEY_AREA = "CATEGORY";
    public static final String KEY_COURSE = "COURSE";
    public static final String KEY_CHAIR_OF_PANEL = "CHAIR_OF_PANEL";
    public static final String KEY_PANELIST1 = "PANELIST_1";
    public static final String KEY_PANELIST2 = "PANELIST_2";
    public static final String KEY_STATUS = "STATUS";
    public static final String KEY_AY = "ACADEMIC_YEAR";
    public static final String KEY_TERM = "TERM";

    public static final String KEY_NAME = "PROFESSOR_NAME";
    public static final String KEY_POSITION = "POSITION";
    public static final String KEY_PICTURE = "PICTURE";

    public static final String KEY_WORD = "WORDS";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

        // Delete old on upgrade (not working? java 7 problems probably 9/20)
        setForcedUpgrade(DATABASE_VERSION);
    }

    public Cursor setCursor() {

        //Open database
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Enter the string query. Here we want everything from our table
        String[] sqlSelect = {"0 _id", KEY_TITLE, KEY_AUTHOR, KEY_ADVISER, KEY_CHAIR_OF_PANEL, KEY_PANELIST1, KEY_PANELIST2, KEY_AREA, KEY_TERM, KEY_AY, KEY_COURSE, KEY_STATUS};

        String sqlTables = TABLE_NAME;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, KEY_TITLE + " ASC");

        c.moveToFirst();
        return c;
    }


    public Cursor setRandomCursor(String limit) {

        //Open database
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Enter the string query. Here we want everything from our table
        String[] sqlSelect = {"0 _id", KEY_TITLE, KEY_AUTHOR, KEY_ADVISER, KEY_CHAIR_OF_PANEL, KEY_PANELIST1, KEY_PANELIST2, KEY_AREA, KEY_TERM, KEY_AY, KEY_COURSE, KEY_STATUS};

        String sqlTables = TABLE_NAME;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, "RANDOM()", limit);

        c.moveToFirst();
        return c;
    }


    public Cursor setSearchCursor(String query) {

        //Open database
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Enter the string query. Here we want everything from our table
        String[] sqlSelect = {"0 _id", KEY_TITLE, KEY_AUTHOR, KEY_ADVISER, KEY_CHAIR_OF_PANEL, KEY_PANELIST1, KEY_PANELIST2, KEY_AREA, KEY_TERM, KEY_AY, KEY_COURSE, KEY_STATUS};

        String escaped_query = DatabaseUtils.sqlEscapeString("%" + query + "%");
        String selectQuery = MyDatabase.KEY_TITLE + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_AUTHOR + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_ADVISER + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_CHAIR_OF_PANEL + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_PANELIST1 + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_PANELIST2 + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_AREA + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_TERM + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_AY + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_COURSE + " LIKE " + escaped_query + " OR ";
        selectQuery += MyDatabase.KEY_STATUS + " LIKE " + escaped_query + "  ";
        String sqlTables = TABLE_NAME;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, selectQuery, null,
                null, null, KEY_TITLE + " ASC");

        c.moveToFirst();
        return c;
    }


    public int getUpgradeVersion() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"MAX (version)"};
        String sqlTables = "upgrades";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        int v = 0;
        c.moveToFirst();
        if (!c.isAfterLast()) {
            v = c.getInt(0);
        }
        c.close();
        return v;


    }


    // Get number of events.
    public int getCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor dataCount;
        if (db != null) {
            dataCount = db.rawQuery("select * from " + TABLE_NAME, null);
            count = dataCount.getCount();
            //db.close();
        }
        return count;
    }


    // Get single event.
    public MyThesis getTITLE(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        assert db != null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        selectQuery += " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        assert cursor != null;
        MyThesis q = new MyThesis(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getString(11)
        );
        db.close();
        return q;
    }

    public Cursor setCursorCAT() {

        //Open database
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Enter the string query. Here we want everything from our table
        String[] sqlSelect = {"0 _id", KEY_TITLE, KEY_AUTHOR, KEY_ADVISER, KEY_CHAIR_OF_PANEL, KEY_PANELIST1, KEY_PANELIST2, KEY_AREA, KEY_TERM, KEY_AY, KEY_COURSE, KEY_STATUS};

        String sqlTables = TABLE_NAME;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, KEY_AREA + " DESC");

        c.moveToFirst();
        return c;
    }

    public void addThesis(MyThesis thesis) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, thesis.getTITLE());
        values.put(KEY_AUTHOR, thesis.getAUTHOR());
        values.put(KEY_ADVISER, thesis.getADVISER());
        values.put(KEY_CHAIR_OF_PANEL, thesis.getCHAIROFPANEL());
        values.put(KEY_PANELIST1, thesis.getPANELIST1());
        values.put(KEY_PANELIST2, thesis.getPANELIST2());
        values.put(KEY_AREA, thesis.getAREA());
        values.put(KEY_TERM, thesis.getTERM());
        values.put(KEY_AY, thesis.getAY());
        values.put(KEY_COURSE, thesis.getCOURSE());
        values.put(KEY_STATUS, thesis.getSTATUS());
        // db.replace(TABLE_NAME,null,values);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteThesis(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_TITLE + "='" + name + "'", null);
        db.close();
    }

    public void editThesis(String name, MyThesis thesis) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_TITLE + "='" + name + "'", null);
        db.close();
        addThesis(thesis);


    }

    public Cursor setCursorYear() {

        //Open database
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Enter the string query. Here we want everything from our table
        String[] sqlSelect = {"0 _id", KEY_TITLE, KEY_AUTHOR, KEY_ADVISER, KEY_CHAIR_OF_PANEL, KEY_PANELIST1, KEY_PANELIST2, KEY_AREA, KEY_TERM, KEY_AY, KEY_COURSE, KEY_STATUS};

        String sqlTables = TABLE_NAME;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, KEY_AY + " DESC");

        c.moveToFirst();
        return c;
    }

    public Cursor setCursorFaculty() {

        //Open database
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Enter the string query. Here we want everything from our table
        String[] sqlSelect = {KEY_NAME, KEY_POSITION, KEY_PICTURE};

        String sqlTables = TABLE_NAME_2;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, KEY_NAME + " ASC");

        c.moveToFirst();
        return c;

    }

    public Cursor setCursorWord() {

        //Open database
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        //Enter the string query. Here we want everything from our table
        String[] sqlSelect = {KEY_WORD};

        String sqlTables = TABLE_NAME_3;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, KEY_WORD+ " ASC","10");

        c.moveToFirst();
        return c;

    }

    public void deleteAllWords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_3, null, null);
        db.close();
    }

    public void addWords1(String string[],int size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=0;i<size;i++) {
            values.put(KEY_WORD, string[i]);
           }

        // db.replace(TABLE_NAME,null,values);

        db.insert(TABLE_NAME_3, null, values);
        db.close();
    }

    public void addWords2(String string) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, string);

        // db.replace(TABLE_NAME,null,values);

        db.insert(TABLE_NAME_3, null, values);
        db.close();
    }
}