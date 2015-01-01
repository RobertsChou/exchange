package com.howto.exchange.db;

import android.content.ContentValues;
import android.database.Cursor;

public interface IDB<T> {
	public T cursorToBean(Cursor cursor);
	
	public ContentValues beanToValues();
	public ContentValues beanToValues(T t);
}
