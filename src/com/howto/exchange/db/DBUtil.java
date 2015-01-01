package com.howto.exchange.db;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/*import com.qixin.coolelf.bean.CommentaryInfo;
import com.qixin.coolelf.bean.ImageInfo;
import com.qixin.coolelf.bean.PushInfo;*/
import com.howto.exchange.db.DBContract.Tables;
import com.howto.exchange.db.DBContract.Tables.PushItemTable;
import com.howto.exchange.db.DBContract.Tables.UploadImage;


/**
 * @author Administrator
 * 
 */
public class DBUtil {

	public static DBManager manager;

	public static void init(Context context) {
		if (manager == null) {
			manager = new DBManager(context);
		}
	}

	/*
	 * public static void addDownload(Context context, DownloadItem item) { init(context);
	 * manager.insert(DBContract.ADD_DBDOWNLOAD, beanToValues(item)); }
	 */

//	public static ArrayList<PushInfo> getPushInfo(Context context) {
//		Cursor cursor = manager.query(DBContract.GET_PUSH_LIST, null, null, null, null);
//		if (cursor != null) {
//			ArrayList<PushInfo> list = new ArrayList<PushInfo>();
//			while (cursor.moveToNext()) {
//				PushInfo info = new PushInfo();
//				cursorToBean(cursor, info);
//				list.add(info);
//			}
//			cursor.close();
//			return list;
//		}
//		return null;
//	}
//
//	/**
//	 * local path  update  to  url
//	 * @param filePath
//	 * @param url
//	 */
//	public static int updatePushInfo(String filePath, String url) {
//		ContentValues values = new ContentValues();
//		values.put(PushItemTable.img, url);
//		return manager.update(DBContract.GET_PUSH_LIST, values, PushItemTable.img +"=?",  new String[]{filePath});
//	}
//	public static void delePush(String id) {
//		manager.delete(DBContract.GET_PUSH_LIST, "id=?", new String[] {id});
//	}
//
//	public static void setPushInfo(Context context, PushInfo info) {
//		manager.insert(DBContract.GET_PUSH_LIST, beanToValues(info));
//	}
//
//	public static void setImageInfo(Context context, ImageInfo info) {
//		manager.insert(DBContract.UPLOAD_IMAGE, beanToValues(info));
//	}
//	
//	//
//	public static void setCommentInfo(Context context, CommentaryInfo comment){
//		manager.insert(DBContract.UpLOAD_COMMENT, beanToValues(comment));
//	}
//	
//	/**
//	 * 上传头像
//	 * @param context
//	 * @param info
//	 */
//	public static void setImageFaceInfo(Context context, ImageInfo info) {
//		manager.insert(DBContract.UPLOAD_IMAGE, beanToValues(info));
//	}
//
//	/**
//	 * 删除成功的条目
//	 * 
//	 * @param mContext
//	 */
//	public static void deletImageInfo(Activity mContext) {
//		manager.delete(DBContract.UPLOAD_IMAGE, Tables.UploadImage.state + "=?", new String[] {"1"});
//	}	
//	
//	/**
//	 * 删除上传失败不需重新上传的项目
//	 */
//	public static void deleteFailedImageInfo(Activity mContext){
//		manager.delete(DBContract.UPLOAD_IMAGE, Tables.UploadImage.state + "=?", new String[] {"-2"});
//	}
//
//	/**
//	 * 删除评论的条目
//	 * 
//	 */
//	public static void deletCommentInfo(Activity mContext) {
//		manager.delete(DBContract.UpLOAD_COMMENT, Tables.UpComment.state + "=?", new String[] {"1"});
//	}
//	
//	public static void deleteFailedCommentInfo(Activity mContext){
//		manager.delete(DBContract.UpLOAD_COMMENT, Tables.UpComment.state + "=?", new String[] {"-2"});
//	}
//	
//	/**
//	 * 刚启动时清理表里的条目
//	 * @param picPath
//	 * @param i
//	 */
//	public static void deletAllImageInfo() {
//		manager.delete(DBContract.UPLOAD_IMAGE, null, null);
//	}
//
//	/**
//	 *
//	 * updateImageInfoState(更新图片上传状态)  
//	 * (这里描述这个方法适用条件 – 可选)  
//	 * @param picPath
//	 * @param i   
//	 *void  
//	 * @exception   
//	 * @since  1.0.0
//	 */
//	public static void updateImageInfoState(String picPath, int i) {
//		ContentValues values = new ContentValues();
//		values.put(UploadImage.state, i);
//		manager.update(DBContract.UPLOAD_IMAGE, values, UploadImage.thumb+"=?", new String[]{picPath});
//	}
//
//	/**
//	 * 
//	 * updateImageInfoVoice(更新录音路径)  
//	 * (这里描述这个方法适用条件 – 可选)  
//	 * @param picPath
//	 * @param voicePath   
//	 *void  
//	 * @exception   
//	 * @since  1.0.0
//	 */
//	public static void updateImageInfoVoice(String picPath ,String voicePath){
//		ContentValues values = new ContentValues();
//		values.put(UploadImage.voice, voicePath);
//		manager.update(DBContract.UPLOAD_IMAGE, values, UploadImage.thumb+"=?", new String[]{picPath});
//	}
//
//
//	/**
//	 * 
//	 * updateImageInfoVoice(更新评论状态)  
//	 * (这里描述这个方法适用条件 – 可选)  
//	 * @param picPath
//	 * @param voicePath   
//	 *void  
//	 * @exception   
//	 * @since  1.0.0
//	 */
//	/*public static void updateCommentInfoVoice(String commentPath){
//		ContentValues values = new ContentValues();
//		//values.put(UploadImage.voice, ComePath);
//		manager.update(DBContract.UPLOAD_IMAGE, values, UploadImage.thumb+"=?", new String[]{picPath});
//	}*/
//	
//	public static int getImageInfoState(String path){
//		ImageInfo info = getImageInfo(path);
//		if(info != null){
//			return info.state;
//		}
//		return -100;
//	}
//
//	public static ImageInfo getImageInfo(String path){
//		ContentValues values = new ContentValues();
//		values.put(UploadImage.thumb, path);
//		Cursor cursor = manager.query(DBContract.UPLOAD_IMAGE, null, UploadImage.thumb +"=?", new String[]{path}, null);
//		if (cursor != null) {
//			ImageInfo info = new ImageInfo();
//			while (cursor.moveToNext()) {
//				cursorToBean(cursor, info);
//			}
//			cursor.close();
//			return info;
//		}
//		return null;
//	}
//
//	public static ArrayList<ImageInfo> getImageInfo(Context context, String id) {
//		init(context);
//		Cursor cursor = manager.query(DBContract.UPLOAD_IMAGE, null, Tables.UploadImage.album_id +"=?", new String[]{id}, null);
//		if (cursor != null) {
//			ArrayList<ImageInfo> list = new ArrayList<ImageInfo>();
//			while (cursor.moveToNext()) {
//				ImageInfo info = new ImageInfo();
//				cursorToBean(cursor, info);
//				list.add(info);
//			}
//			cursor.close();
//			return list;
//		}
//		return null;
//	}


	private static void cursorToBean(Cursor values, Object obj) {
		if (obj == null) {
			return;
		}
		if (values == null) {
			return;
		}
		Field[] fields = obj.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String name = fields[i].getName();
			if (fields[i].getType().getName().equals(java.lang.String.class.getName())) {
				try {
					fields[i].set(obj, values.getString(values.getColumnIndex(name)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if(fields[i].getType().getName().equals(java.lang.Integer.class.getName()) || fields[i].getType().getName().equals("int")){
				try {
					fields[i].set(obj, values.getInt(values.getColumnIndex(name)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} 
			}
		}
	}

	private static ContentValues beanToValues(Object obj) {
		ContentValues values = new ContentValues();
		if (obj == null) return values;
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			fields[j].setAccessible(true);

			// String
			if (fields[j].getType().getName().equals(java.lang.String.class.getName())) {
				try {
					Object v = fields[j].get(obj);
					values.put(fields[j].getName(), v == null ? null : v.toString());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if (fields[j].getType().getName().equals(java.lang.Integer.class.getName()) || fields[j].getType().getName().equals("int")) {
				// Integer type
				try {
					values.put(fields[j].getName(), Integer.valueOf(fields[j].get(obj).toString()));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return values;
	}



}
