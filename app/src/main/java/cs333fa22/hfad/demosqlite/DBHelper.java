package cs333fa22.hfad.demosqlite;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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


    public void saveEmployee(String name, String designation, long dobInMS)
    {
        //insert into employee(name, dob, designation) values ('Angel', 10000000 , 'SQL Programmer');
        @SuppressLint("DefaultLocale") String insertString = String.format("insert into %s(%s, %s, %s) " +
                "values ('%s', %d , '%s');", DBContract.EmployeeEntry.TABLE_NAME,
                DBContract.EmployeeEntry.COLUMN_NAME,DBContract.EmployeeEntry.COLUMN_DOB, DBContract.EmployeeEntry.COLUMN_DESIGNATION,
                name, dobInMS, designation);

        System.out.println("Saving: " + insertString);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertString); //for writing

        db.close();
    }

    public ArrayList<Employee> fetchAllEmployees()
    {
        ArrayList<Employee> allEmps = new ArrayList<Employee>();

        String insertString = String.format("Select * from %s", DBContract.EmployeeEntry.TABLE_NAME);

        System.out.println("Fetching All: " + insertString);

        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor starts at the -1th position
        Cursor cursor = db.rawQuery(insertString, null);

        //Get the positions of your cols
        int idPos = cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_ID);
        int namePos = cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_NAME);
        int desigPos = cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_DESIGNATION);
        int dobPos = cursor.getColumnIndex(DBContract.EmployeeEntry.COLUMN_DOB);


        //Use positions to request the values in the columns
        while(cursor.moveToNext())
        {
            long id = cursor.getLong(idPos);
            long dob = cursor.getLong(dobPos);
            String name = cursor.getString(namePos);
            String desig = cursor.getString(desigPos);
            allEmps.add(new Employee(id, name, dob, desig));
        }

        cursor.close();
        db.close();

        return allEmps;

    }
}
