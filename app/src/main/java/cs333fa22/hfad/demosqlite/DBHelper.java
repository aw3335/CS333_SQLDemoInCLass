package cs333fa22.hfad.demosqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "employees.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context)
    {
        super(context, DB_NAME,null, DB_VERSION );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(DBContract.EmployeeEntry.CREATE_EMP_TABLE_CMD);
        db.execSQL(DBContract.EmployeeEntry.CREATE_EMP_TABLE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DBContract.EmployeeEntry.DROP_EMP_TABLE_CMD);
        onCreate(db);
    }
}
