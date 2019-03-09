/*
    John Murwin
    KSU, Spring 2019
    Mobile Software Development
    Team1
 */

package john.murwin.databasetest;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Public Variables
    public static final String DatabaseName = "test.db";
    public static final String TableName = "inventoryTable";

    //Private Variables
    String TAG;

    //Default Constructor Class
    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, 1);

        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TableName + "(ItemID INTEGER PRIMARY KEY NOT NULL, ItemName TEXT, ItemQty INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }
}
