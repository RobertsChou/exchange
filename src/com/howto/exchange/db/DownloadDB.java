package com.howto.exchange.db;

import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.howto.exchange.db.DBContract.Tables;
import com.howto.exchange.db.DBContract.Tables.AbstractTable;
import com.howto.exchange.db.DBContract.Tables.DownloadContentTable;
import com.howto.exchange.db.DBContract.Tables.PushItemTable;
import com.howto.exchange.db.DBContract.Tables.UploadImage;

public class DownloadDB {
	private static final String TAG = DownloadDB.class.getSimpleName();

	private static final String DATABASE_NAME = "ad_download.db";

	private static SQLiteDatabase mSQLiteDB;

	private static ItotemDBOpenHelper mDBOpenHelper;

	private static Context mContext;

	public DownloadDB(Context context) {
		mContext = context;
	}

	public DownloadDB open() {
		mDBOpenHelper = new ItotemDBOpenHelper(mContext, DATABASE_NAME, null, DBContract.DB_VERSION);
		mSQLiteDB = mDBOpenHelper.getWritableDatabase();
		return this;
	}

	/**
	 * 获取数据库 应用内的数据库操作都是从此获取数据库后直接CRUD操作
	 * 
	 * @param context
	 * @return
	 */
	public static SQLiteDatabase getDatabase(Context context) {

		if (mSQLiteDB != null && mSQLiteDB.isOpen()) {
			return mSQLiteDB;
		}

		mDBOpenHelper = new ItotemDBOpenHelper(mContext, DATABASE_NAME, null, DBContract.DB_VERSION);
		mSQLiteDB = mDBOpenHelper.getWritableDatabase();
		return mSQLiteDB;
	}

	public void close() {
		mDBOpenHelper.close();
	}

	public void beginTransaction() {
		mSQLiteDB.beginTransaction();
	}

	public void endTransaction() {
		if (mSQLiteDB.inTransaction()) {
			mSQLiteDB.endTransaction();
		}
	}

	public void setTransactionSuccessful() {
		mSQLiteDB.setTransactionSuccessful();
	}


	private static final class ItotemDBOpenHelper extends SQLiteOpenHelper {

		public ItotemDBOpenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			final Set<Class<? extends AbstractTable>> tables = Tables.getTables();
			for (Class<? extends AbstractTable> table : tables) {
				try {
					for (String statment : Tables.getCreateStatments(table)) {
						Log.d(TAG, statment);
						db.execSQL(statment);
					}
				} catch (Throwable e) {
					Log.e(TAG, "Can't create table " + table.getSimpleName());
				}
			}

			/**
			 * 初始化数据
			 */
			// initData();

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "onUpgrade: " + oldVersion + " >> " + newVersion);
			final Set<Class<? extends AbstractTable>> tables = Tables.getTables();
			for (Class<? extends AbstractTable> table : tables) {
				try {
					db.execSQL("DROP TABLE IF EXISTS " + Tables.getTableName(table));
				} catch (Throwable e) {
					Log.e(TAG, "Can't create table " + table.getSimpleName());
				}
			}
			onCreate(db);
		}

	}

	public void cleanTable(String tableName, int maxSize, int batchSize) {
		Cursor cursor = mSQLiteDB.rawQuery("select count(_id) from " + tableName, null);
		if (cursor.getCount() != 0 && cursor.moveToFirst() && !cursor.isAfterLast()) {
			if (cursor.getInt(0) >= maxSize) {
				int deleteSize = maxSize - batchSize;
				mSQLiteDB.execSQL("delete from " + tableName + " where _id in (" + "select _id from " + tableName
						+ " order by _id " + "  limit " + deleteSize + " )");
			}
		}
		cursor.close();
	}

	// ------------------------- 断点记录---------------------
	public long addDBDownload(ContentValues values) {
		long aff = 0;
		if (values.containsKey(Tables.DownloadContentTable.URL)) {
			aff = updateDBDownload(values, Tables.DownloadContentTable.URL + "=?",
					new String[] { values.getAsString(Tables.DownloadContentTable.URL) });
			if (aff == 0) {
				aff = mSQLiteDB.insert(Tables.DownloadContentTable.TABLE_NAME, "", values);
			}
		}
		return aff;

	}

	public Cursor getDBDownload(String url) {
		return mSQLiteDB.query(Tables.DownloadContentTable.TABLE_NAME, null, DownloadContentTable.URL + "=?",
				new String[] { url }, null, null, null);

	}

	public int updateDBDownload(ContentValues values, String whereClause, String[] whereArgs) {
		return mSQLiteDB.update(Tables.DownloadContentTable.TABLE_NAME, values, whereClause, whereArgs);
	}
	
	public int deleteDBDownload(String whereClause, String[] whereArgs) {
	  return mSQLiteDB.delete(DownloadContentTable.TABLE_NAME, whereClause, whereArgs);
	}

	//------------------------------push table-----------------------------
    public long addDBPush(ContentValues values) {
        return mSQLiteDB.insert(Tables.PushItemTable.TABLE_NAME, "", values);

    }

    public Cursor getDBPush() {
        return mSQLiteDB.query(Tables.PushItemTable.TABLE_NAME, null, null, null, null, null, null);

    }

    public int updateDBPush(ContentValues values, String whereClause, String[] whereArgs) {
        return mSQLiteDB.update(Tables.PushItemTable.TABLE_NAME, values, whereClause, whereArgs);
    }
    
    public int deleteDBPush(String whereClause, String[] whereArgs) {
      return mSQLiteDB.delete(PushItemTable.TABLE_NAME, whereClause, whereArgs);
    }

  //------------------------------upload table-----------------------------
	public long addImageInfo(ContentValues values) {
	    long aff = 0;
        if (values.containsKey(Tables.UploadImage.thumb)) {
            aff = updateDBImageInfo(values, Tables.UploadImage.thumb + "=?",
                    new String[] { values.getAsString(Tables.UploadImage.thumb) });
            if (aff == 0) {
                aff = mSQLiteDB.insert(Tables.UploadImage.TABLE_NAME, "", values);
            }
        }
        return aff;
	}
	
	public Cursor getDBImageInfo(String[] selects, String selection, String[] params, String order) {
        return mSQLiteDB.query(Tables.UploadImage.TABLE_NAME, selects, selection, params, null, null, order);

    }

    public int updateDBImageInfo(ContentValues values, String whereClause, String[] whereArgs) {
        return mSQLiteDB.update(Tables.UploadImage.TABLE_NAME, values, whereClause, whereArgs);
    }
    
    public int deleteDBImageInfo(String whereClause, String[] whereArgs) {
      return mSQLiteDB.delete(UploadImage.TABLE_NAME, whereClause, whereArgs);
    }
}
