package com.howto.exchange.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DBManager {

  private DownloadDB itotemDB;

  public DBManager(Context context) {
    itotemDB = new DownloadDB(context);
    itotemDB.open();
  }


  public void insert(int uri, ContentValues values) {
    synchronized (itotemDB) {
      switch (uri) {
        case DBContract.GET_PUSH_LIST:
          itotemDB.addDBPush(values);
          break;
        case DBContract.UPLOAD_IMAGE:
        	itotemDB.addImageInfo(values);
        	break;
        default:
          throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }

  public int delete(int uri, String selection, String[] selectionArgs) {
    synchronized (itotemDB) {
      switch (uri) {
          case DBContract.GET_PUSH_LIST:
              return itotemDB.deleteDBPush(selection, selectionArgs);
          case DBContract.UPLOAD_IMAGE:
              return itotemDB.deleteDBImageInfo(selection, selectionArgs);
        default:
          throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }

  public Cursor query(int uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    synchronized (itotemDB) {
      switch (uri) {
          case DBContract.GET_PUSH_LIST:
              return itotemDB.getDBPush();
          case DBContract.UPLOAD_IMAGE:
              return itotemDB.getDBImageInfo(projection, selection, selectionArgs, sortOrder);
        default:
          throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }

  public int update(int uri, ContentValues values, String selection, String[] selectionArgs) {
    synchronized (itotemDB) {
      switch (uri) {
          case DBContract.UPLOAD_IMAGE:
              return itotemDB.updateDBImageInfo(values, selection, selectionArgs);
          case DBContract.GET_PUSH_LIST:
              return itotemDB.updateDBPush(values, selection, selectionArgs);
        default:
          throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }
}
