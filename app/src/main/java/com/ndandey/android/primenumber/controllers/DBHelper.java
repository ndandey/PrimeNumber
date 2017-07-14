

package com.ndandey.android.primenumber.controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ndandey on 7/11/2017.
 */

public class DBHelper extends SQLiteOpenHelper
{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PrimeDB";
    private static final String TABLE_NAME = "Primes";
    SQLiteDatabase vDB = this.getWritableDatabase();
    private Context mContext;

    public DBHelper(Context pContext)
    {
        super(pContext, DB_NAME, null, DB_VERSION);
        this.mContext = pContext;
    }

    @Override
    public void onCreate(SQLiteDatabase pSQLiteDatabase)
    {
        pSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        pSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(PN_INDEX NVARCHAR(30) PRIMARY KEY,  PN_PRIME_NUM NVARCHAR(30) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
    }

    public Cursor selectWLimit(int limit)
    {
        Cursor cursor = vDB.rawQuery("SELECT PN_INDEX, PN_PRIME_NUM FROM " + TABLE_NAME + " LIMIT " + limit, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void insert(String pPrimeNumber, String pPrimeIndex)
    {
        String vInsertQuery = String.format("INSERT INTO %s (PN_INDEX,PN_PRIME_NUM) VALUES (%s,%s)", TABLE_NAME, pPrimeIndex, pPrimeNumber);
        vDB.execSQL(vInsertQuery);
    }

    public String getPrimeNumber(Integer pPrimeId)
    {
        String vSelectQuery = String.format("SELECT PN_PRIME_NUM FROM %s WHERE PN_INDEX = '%s'", TABLE_NAME, String.valueOf(pPrimeId));
        Cursor cursor = vDB.rawQuery(vSelectQuery, null);
        String vReturnValue = null;
        if (cursor != null && cursor.getCount() == 1)
        {
            cursor.moveToFirst();
            vReturnValue = cursor.getString(0);
        }
        return vReturnValue;
    }
}
