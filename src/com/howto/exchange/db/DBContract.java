package com.howto.exchange.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBContract {
	/**
	 * Indicates the version of the database schema. !!!!! Increment this number
	 * when you make changes to the tables below !!!!!
	 */
	public static final int DB_VERSION = 4;
	public static final int ADD_DBDOWNLOAD = 0;
	public static final int GET_PUSH_LIST = 10;
	public static final int UPLOAD_IMAGE = 100;
	public static final int UpLOAD_COMMENT = 1000;

	public static final class Tables {

		private static final HashMap<Class<? extends AbstractTable>, HashSet<String>> tableFields = new HashMap<Class<? extends AbstractTable>, HashSet<String>>();
		private static final HashMap<Class<? extends AbstractTable>, String> tableNames = new HashMap<Class<? extends AbstractTable>, String>();

		/**
		 * Get value from given field.
		 * 
		 * @param f
		 *            field object. Field must be static.
		 * @return field's value or null if value can't be read.
		 */
		private static final String getFieldValue(Field f) {
			try {
				return f.get(null).toString();
			} catch (final Exception e) {
				return null;
			}
		}

		static {
			HashSet<String> fields = null;
			/**
			 * 图片--存库
			 */
			tableNames.put(Tables.DownloadContentTable.class,
					DownloadContentTable.TABLE_NAME);
			fields = new HashSet<String>();
			fields.add(Tables.DownloadContentTable.URL);
			fields.add(Tables.DownloadContentTable.CUR_DOWNLOAD_LENGHT);
			fields.add(Tables.DownloadContentTable.DESCRIPTION);
			fields.add(Tables.DownloadContentTable.EXTENDS);
			fields.add(Tables.DownloadContentTable.FILE_LENGTH);
			fields.add(Tables.DownloadContentTable.STATE);
			tableFields.put(Tables.DownloadContentTable.class, fields);

			/**
			 * push
			 */
			tableNames.put(PushItemTable.class, PushItemTable.TABLE_NAME);
			fields = new HashSet<String>();
			fields.add(PushItemTable.child_name);
			fields.add(PushItemTable.content);
			fields.add(PushItemTable.get_uid);
			fields.add(PushItemTable.img);
			fields.add(PushItemTable.img_describe);
			fields.add(PushItemTable.img_title);
			fields.add(PushItemTable.platform);
			fields.add(PushItemTable.receiver_type);
			fields.add(PushItemTable.receiver_value);
			fields.add(PushItemTable.send_uid);
			fields.add(PushItemTable.state);
			fields.add(PushItemTable.id);
			fields.add(PushItemTable.type);
			fields.add(PushItemTable.email);
			fields.add(PushItemTable.username);
			fields.add(PushItemTable.img_id);
			tableFields.put(Tables.PushItemTable.class, fields);

			/**
			 * 图片上传
			 */
			tableNames.put(UploadImage.class, UploadImage.TABLE_NAME);
			fields = new HashSet<String>();
			fields.add(UploadImage.album_id);
			fields.add(UploadImage.author_grade);
			fields.add(UploadImage.author_id);
			fields.add(UploadImage.create_time);
			fields.add(UploadImage.create_time_accuracy);
			fields.add(UploadImage.describe);
			fields.add(UploadImage.hash);
			fields.add(UploadImage.id);
			fields.add(UploadImage.owner_id);
			fields.add(UploadImage.state);
			fields.add(UploadImage.big_height);
			fields.add(UploadImage.big_width);
			fields.add(UploadImage.dynamic);
			fields.add(UploadImage.square);
			fields.add(UploadImage.agency);
			tableFields.put(Tables.UploadImage.class, fields);

			/**
			 * 评论上传
			 */
			tableNames.put(UpComment.class, UpComment.TABLE_NAME);
			fields = new HashSet<String>();
			fields.add(UpComment.id);
			fields.add(UpComment.image_id);
			fields.add(UpComment.user_id);
			fields.add(UpComment.user_name);	
			fields.add(UpComment.to_user_id);
			fields.add(UpComment.to_user_name);
			fields.add(UpComment.user_face);
			fields.add(UpComment.parent_id);	
			fields.add(UpComment.create_time);
			fields.add(UpComment.message);
			fields.add(UpComment.voice);
			fields.add(UploadImage.state);
			tableFields.put(Tables.UpComment.class, fields);
			
		}

		/**
		 * Abstract definition of local table.
		 */
		public static abstract class AbstractTable {

			@TableColumn(type = TableColumn.Types.INTEGER, isPrimary = true)
			public final static String _ID = "_id";
		}

		/**
		 * Annotation used for describing the name of table. If you use this
		 * notion for a class, it will visible as a table name.
		 */
		@Retention(RetentionPolicy.RUNTIME)
		protected @interface Table {
			String name();
		}

		/**
		 * Annotation used for describing table's columns. If you use this
		 * notion for a static field from class, it will visible as a table
		 * column. The type of the column is mandatory. Additionally you can
		 * mark a field as a primary key, index it, add "not null"/"unique"
		 * property.
		 */
		@Retention(RetentionPolicy.RUNTIME)
		protected @interface TableColumn {
			public enum Types {
				INTEGER, TEXT, BLOB, DATETIME
			}

			Types type();

			boolean isPrimary() default false;

			boolean isIndex() default false;

			/**
			 * A NOT NULL constraint may only be attached to a column
			 * definition, not specified as a table constraint. Not
			 * surprisingly, a NOT NULL constraint dictates that the associated
			 * column may not contain a NULL value. Attempting to set the column
			 * value to NULL when inserting a new row or updating an existing
			 * one causes a constraint violation.
			 */

			boolean isNotNull() default false;

			/**
			 * A UNIQUE constraint is similar to a PRIMARY KEY constraint,
			 * except that a single table may have any number of UNIQUE
			 * constraints. For each UNIQUE constraint on the table, each row,
			 * isPrimary=true must feature a unique combination of values in the
			 * columns identified by the UNIQUE constraint. As with PRIMARY KEY
			 * constraints, for the purposes of UNIQUE constraints NULL values
			 * are considered distinct from all other values (including other
			 * NULLs). If an INSERT or UPDATE statement attempts to modify the
			 * table content so that two or more rows feature identical values
			 * in a set of columns that are subject to a UNIQUE constraint, it
			 * is a constraint violation.
			 */
			boolean isUnique() default false;
		}

		/**
		 * Returns a set of class objects for all tables.
		 * 
		 * @param c
		 *            class object of the table.
		 * @return set with tables calsses.
		 */
		public final static Set<Class<? extends AbstractTable>> getTables() {
			return tableNames.keySet();// 获取tableNames内所有键
		}

		/**
		 * Returns the table name used for storing the given table class
		 * 
		 * @param c
		 *            class object of the table.
		 * @return table's name or null if there is no table.
		 */
		public final static String getTableName(Class<? extends AbstractTable> c) {
			return tableNames.get(c);
		}

		/**
		 * Returns columns extracted from a class object.
		 * 
		 * @param c
		 *            table's class.
		 * @return set of columns or empty set.
		 */
		public final static HashSet<String> getTableColumns(
				Class<? extends AbstractTable> c) {
			return tableFields.get(c);
		}

		/**
		 * Returns sql statements which will create table described by given
		 * class. Statements for creating indexes are placed at the end of
		 * result list.
		 * 
		 * @param c
		 *            table's class.
		 * @return list of sql statements or empty list.
		 * @throws IllegalArgumentException
		 *             when table's class doesn't have 'name' annotation.
		 */
		public final static List<String> getCreateStatments(
				Class<? extends AbstractTable> c) {
			final Table tableNameAnnotation = c.getAnnotation(Table.class);
			final List<String> createStatments = new ArrayList<String>();
			final List<String> indexStatments = new ArrayList<String>();
			if (tableNameAnnotation == null) {
				throw new IllegalArgumentException(
						"No 'name' annotation for table: " + c.getSimpleName());
			} else {
				final StringBuilder builder = new StringBuilder();
				final String tableName = tableNameAnnotation.name();
				builder.append("CREATE TABLE ");
				builder.append(tableName);
				builder.append(" (");
				for (final Field f : c.getFields()) {
					f.setAccessible(true);
					final String fieldValue = getFieldValue(f);
					if (fieldValue != null) {
						final TableColumn tableColumnAnnotation = f
						.getAnnotation(TableColumn.class);
						if (tableColumnAnnotation != null) {
							builder.append(fieldValue);
							builder.append(" ");
							if (tableColumnAnnotation.type() == TableColumn.Types.INTEGER) {
								builder.append(" INTEGER");
							} else if (tableColumnAnnotation.type() == TableColumn.Types.BLOB) {
								builder.append(" BLOB");
							} else if (tableColumnAnnotation.type() == TableColumn.Types.TEXT) {
								builder.append(" TEXT");
							} else {
								builder.append(" DATETIME");
							}
							if (tableColumnAnnotation.isPrimary()) {
								builder.append(" PRIMARY KEY");
							} else {
								if (tableColumnAnnotation.isNotNull()) {
									builder.append(" NOT NULL");
								}
								if (tableColumnAnnotation.isUnique()) {
									builder.append(" UNIQUE");
								}
							}
							if (tableColumnAnnotation.isIndex()) {
								indexStatments.add("CREATE INDEX idx_"
										+ fieldValue + "_" + tableName + " ON "
										+ tableName + "(" + fieldValue + ");");
							}
							builder.append(", ");
						}
					}
				}
				builder.setLength(builder.length() - 2); // remove last ','
				builder.append(");");
				createStatments.add(builder.toString());
				createStatments.addAll(indexStatments);
				return createStatments;
			}
		}

		/**
		 * 下载内容的记录
		 */
		@Table(name = DownloadContentTable.TABLE_NAME)
		public static class DownloadContentTable extends AbstractTable {
			public final static String TABLE_NAME = "download";

			@TableColumn(type = TableColumn.Types.TEXT, isIndex = true, isUnique = true, isNotNull = true)
			public final static String URL = "url";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String TITLE = "TITLE";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String DESCRIPTION = "DESCRIPTION";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String FILE_LENGTH = "FILE_LENGTH";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String CUR_DOWNLOAD_LENGHT = "CUR_DOWNLOAD_LENGHT";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String STATE = "STATE";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String EXTENDS = "EXTENDS";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String NOTIFY_ID = "NOTIFY_ID";
		}

		@Table(name = PushItemTable.TABLE_NAME)
		public static class PushItemTable extends AbstractTable {

			public final static String TABLE_NAME = "pushtable";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String id = "id";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String receiver_type = "receiver_type";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String receiver_value = "receiver_value";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String platform = "platform";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String content = "content";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String img = "img";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String send_uid = "send_uid";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String get_uid = "get_uid";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String child_name = "child_name";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String img_title = "img_title";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String img_describe = "img_describe";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String state = "state"; // start sending failed

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String type = "type";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String email = "email";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String username = "username";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String send_name = "send_name";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String child_grade = "child_grade";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String  img_id= "img_id";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String  count= "count";
		}

		@Table(name = UploadImage.TABLE_NAME)
		public static class UploadImage extends AbstractTable {
			public final static String TABLE_NAME = "upload";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String id = "id";
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String author_grade = "author_grade";
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String describe = "describe";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String owner_id = "owner_id";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String thumb_img = "thumb_img";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String title = "title";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String url = "url";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String create_time = "create_time";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String author_id = "author_id";// 151,

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String album_id = "album_id";// 183,

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String thumb = "thumb";// \/Uploads\/52285be30b307_thumb.jpg,

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String hash = "hash";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String create_time_accuracy = "create_time_accuracy";// 0

			@TableColumn(type = TableColumn.Types.INTEGER)
			public final static String state = "state"; //

			@TableColumn(type = TableColumn.Types.INTEGER)
			public final static String big_width = "big_width"; //

			@TableColumn(type = TableColumn.Types.INTEGER)
			public final static String big_height = "big_height"; //

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String tag_name = "tag_name";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String tag_id = "tag_id";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String icon = "icon";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String realname = "realname";
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String voice = "voice";
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String dynamic = "dynamic";
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String square = "square";
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String agency = "agency";
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String like_count = "like_count";

		}

		
		@Table(name = UpComment.TABLE_NAME)
		public static class UpComment extends AbstractTable {
			public final static String TABLE_NAME = "comment";
			
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String id = "id";
		
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String image_id = "image_id";
		
			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String user_id = "user_id";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String user_name = "user_name";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String to_user_id = "to_user_id";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String to_user_name = "to_user_name";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String message = "message";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String voice = "voice";

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String create_time = "create_time";// 151,

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String parent_id = "parent_id";// 183,

			@TableColumn(type = TableColumn.Types.TEXT)
			public final static String user_face = "user_face";// \/Uploads\/52285be30b307_thumb.jpg,

			@TableColumn(type = TableColumn.Types.INTEGER)
			public final static String state = "state"; 
		}

	}

}
