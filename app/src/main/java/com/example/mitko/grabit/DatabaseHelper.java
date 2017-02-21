package com.example.mitko.grabit;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    SQLiteDatabase db;

    public static final String DB_NAME = "thatismyspot.db";
    public static final int DB_VERSION = 1;
    public static final String ERROR_TAG = "error";

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_USERNAME = "username";
    public static final String COLUMN_USER_FNAME = "fName";
    public static final String COLUMN_USER_LNAME = "lName";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_IMAGE_PATH = "imagePath";

    public static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + "(" +
                    "'" + COLUMN_USER_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "'" + COLUMN_USER_USERNAME + "' TEXT UNIQUE NOT NULL, " +
                    "'" + COLUMN_USER_EMAIL + "' TEXT UNIQUE NOT NULL, " +
                    "'" + COLUMN_USER_FNAME + "' TEXT, " +
                    "'" + COLUMN_USER_LNAME + "' TEXT, " +
                    "'" + COLUMN_USER_PASSWORD + "' TEXT NOT NULL, " +
                    "'" + COLUMN_USER_GENDER + "' TEXT DEFAULT 'APACHE'," +
                    "'" + COLUMN_USER_IMAGE_PATH + "' TEXT DEFAULT 'noimage')";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    public void registerNewUser(User user) {
        try {
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_USER_EMAIL, user.getEmail());
            cv.put(COLUMN_USER_FNAME, user.getFirstName());
            cv.put(COLUMN_USER_LNAME, user.getLastName());
            cv.put(COLUMN_USER_GENDER, user.getGender());
            cv.put(COLUMN_USER_PASSWORD, user.getPassword());
            cv.put(COLUMN_USER_USERNAME, user.getUsername());
            cv.put(COLUMN_USER_IMAGE_PATH, user.getImagePath());

            db.insertOrThrow(TABLE_USER, null, cv);
        }catch (SQLException e){
            Log.e(ERROR_TAG, e.getMessage());
        }finally {
            if(db != null)
                db.close();
        }
    }

    public User loginUser(String email, String password) {

        db = getReadableDatabase();
        User result = null;

        String query = "SELECT * FROM " + TABLE_USER + " WHERE " +
                COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD +
                " = ?";

        Cursor c = db.rawQuery(query, new String[]
                {
                        email,
                        password
                });

        if(c.moveToFirst()){
            result = new User();
            result.setId(c.getLong(c.getColumnIndex(COLUMN_USER_ID)));
            result.setEmail(email);
            result.setFirstName(c.getString(c.getColumnIndex(COLUMN_USER_FNAME)));
            result.setLastName(c.getString(c.getColumnIndex(COLUMN_USER_LNAME)));
            result.setGender(c.getString(c.getColumnIndex(COLUMN_USER_GENDER)));
            result.setImagePath(c.getString(c.getColumnIndex(COLUMN_USER_IMAGE_PATH)));
            result.setPassword(password);
            result.setUsername(c.getString(c.getColumnIndex(COLUMN_USER_USERNAME)));
        }

        return result;
    }
}