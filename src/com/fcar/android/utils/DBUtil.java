package com.fcar.android.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBUtil extends SQLiteOpenHelper
{
  protected static final String CONTENT = "content";
  private static final String DATABASE_NAME = "DB_OBD.db";
  private static final int DATABASE_VERSION = 1;
  protected static final String ID = "ID";
  private static final int MAX_SIZE = 10000;
  private static final String TABLE_NAME = "T_User";
  protected static final String TYPE = "infoType";
  private static DBUtil instance;
  private static Context mContext;

  private DBUtil(Context paramContext)
  {
    super(paramContext, "DB_OBD.db", null, 1);
  }

  /** @deprecated */
  private long getCount()
  {
    try
    {
      long l1 = getReadableDatabase().compileStatement("SELECT COUNT(*) FROM T_User").simpleQueryForLong();
      long l2 = l1;
      return l2;
    }
    finally
    {
    }
  }

  protected static DBUtil getInstance()
  {
    if (instance == null)
    {
      Context localContext = mContext;
      instance = new DBUtil(localContext);
    }
    return instance;
  }

  protected static void init(Context paramContext)
  {
    mContext = paramContext;
  }

  protected void delete(String[] paramArrayOfString)
  {
    String[] arrayOfString = paramArrayOfString;
    getWritableDatabase().beginTransaction();
    if (getWritableDatabase().delete("T_User", "ID = ?", arrayOfString) > 0)
      getWritableDatabase().setTransactionSuccessful();
    getWritableDatabase().endTransaction();
  }

  /** @deprecated */
  protected boolean insert(byte[] paramArrayOfByte)
  {
    int i = 0;
    while (true)
      try
      {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("content", paramArrayOfByte);
        getWritableDatabase().beginTransaction();
        long l = getWritableDatabase().insert("T_User", null, localContentValues);
        if (l == 65535L)
        if (l != 65535L)
          i = 1;
        if (i != 0)
          getWritableDatabase().setTransactionSuccessful();
      }
      finally
      {
      }
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.beginTransaction();
    try
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS T_User ( ID INTEGER primary key autoincrement, content BLOB,infoType TEXT);");
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.beginTransaction();
    try
    {
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS T_User");
      paramSQLiteDatabase.setTransactionSuccessful();
      paramSQLiteDatabase.endTransaction();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }

  protected Cursor select()
  {
    try
    {
      Cursor localCursor = getReadableDatabase().query("T_User", null, null, null, null, null, null);
      return localCursor;
    }
    catch (Exception localException)
    {
      String str = localException.toString();
      int j = Log.v("test", str);
    }
	return null;
  }
}
