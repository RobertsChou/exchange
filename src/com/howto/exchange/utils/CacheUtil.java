package com.howto.exchange.utils;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.howto.exchange.IApplication;

public class CacheUtil {

	 private static Context context;

	// 计算缓存大小  
         
         public  String CalculateCacheSize(Context context){
        	 this.context = context;
        	 long fileSize = 0;  
             String cacheSize = "0KB";
        	 File filesDir = context.getFilesDir();// /data/data/package_name/files  
             File cacheDir = context.getCacheDir();// /data/data/package_name/cache    
             fileSize += getDirSize(filesDir);  
             fileSize += getDirSize(cacheDir); 

          // 2.2版本才有将应用缓存转移到sd卡的功能 
              if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){     
            	  File externalCacheDir = getExternalCacheDir(context);//"<sdcard>/Android/data/<package_name>/cache/"        
            	  fileSize += getDirSize(externalCacheDir);             
                 }  
         
               if (fileSize > 0){  
                     cacheSize = formatFileSize(fileSize);
                     return cacheSize;
               }
        	 return "0KB";
         }
         
       	/**  
           * 获取目录文件大小  
           *  
           * @param dir  
           * @return  
       	   */  
       	     public static long getDirSize(File dir) {  
                if (dir == null) {  
       	             return 0;  
       	         }  
       	         if (!dir.isDirectory()) {  
       	            return 0;  
       	        }  
       	        long dirSize = 0;  
       	        File[] files = dir.listFiles();  
       	        for (File file : files) {  
       	             if (file.isFile()) {  
       	                 dirSize += file.length();  
       	           } else if (file.isDirectory()) {  
       	                dirSize += file.length();  
       	               dirSize += getDirSize(file); // 递归调用继续统计  
       	           }  
       	       }  
       	        return dirSize;  
       	    }  


       		 /**  
       	       * 判断当前版本是否兼容目标版本的方法  
       	       * @param VersionCode  
               * @return  
       	       */  
       	      public static boolean isMethodsCompat(int VersionCode) {  
       	         int currentVersion = android.os.Build.VERSION.SDK_INT;  
       	         return currentVersion >= VersionCode;  
       	     }  
       		   

       	   @TargetApi(8)  
           public static File getExternalCacheDir(Context context) {  
         
              // return context.getExternalCacheDir(); API level 8  
         
               // e.g. "<sdcard>/Android/data/<package_name>/cache/"  
         
               return context.getExternalCacheDir();  
           }  
         
       /**  
            * 转换文件大小  
            *  
            * @param fileS  
            * @return B/KB/MB/GB  
            */  
           public static String formatFileSize(long fileS) {  
               java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");  
               String fileSizeString = "";  
               if (fileS < 1024) {  
                   fileSizeString = df.format((double) fileS) + "B";  
               } else if (fileS < 1048576) {  
                   fileSizeString = df.format((double) fileS / 1024) + "KB";  
               } else if (fileS < 1073741824) {  
                   fileSizeString = df.format((double) fileS / 1048576) + "MB";  
               } else {  
                   fileSizeString = df.format((double) fileS / 1073741824) + "G";  
               }  
               return fileSizeString;  
           }  
         
      /* *//**  
            * 清除app缓存  
            *  
            * @param activity  
            */  
          /* public static void clearAppCache(Activity activity) {  
               final IApplication app = (IApplication) activity.getApplication();  
               final Handler handler = new Handler() {  
                   public void handleMessage(Message msg) {  
                       if (msg.what == 1) {  
                           (app, "缓存清除成功");  
                       } else {  
                           ToastMessage(ac, "缓存清除失败");  
                       }  
                   }  
               };  
               new Thread() {  
                   public void run() {  
                       Message msg = new Message();  
                       try {  
                           ac.clearAppCache();  
                           msg.what = 1;  
                       } catch (Exception e) {  
                           e.printStackTrace();  
                           msg.what = -1;  
                       }  
                       handler.sendMessage(msg);  
                   }  
               }.start();  
           }  
        */
      /**
       *  在项目中经常会使用到WebView 控件,当加载html 页面时,会在/data/data/package_name目录下生成database与cache 两个文件夹。请求的url 记录是保存在WebViewCache.db,而url 的内容是保存在WebViewCache 文件夹下  
       */
         
       /**  
            * 清除app缓存  
            */  
           public static void clearAppCache()  
           {  
            /*   //清除webview缓存  
               
               File file = CacheManager.getCacheFileBaseDir();   
         
              //先删除WebViewCache目录下的文件  
         
               if (file != null && file.exists() && file.isDirectory()) {    
                   for (File item : file.listFiles()) {    
                       item.delete();    
                   }    
                   file.delete();    
               }              
              context.deleteDatabase("webview.db");    
               deleteDatabase("webview.db-shm");    
               deleteDatabase("webview.db-wal");    
               deleteDatabase("webviewCache.db");    
               deleteDatabase("webviewCache.db-shm");    
               deleteDatabase("webviewCache.db-wal");    */
               //清除数据缓存  
               clearCacheFolder(context.getFilesDir(),System.currentTimeMillis());  
               clearCacheFolder(context.getCacheDir(),System.currentTimeMillis());  
               //2.2版本才有将应用缓存转移到sd卡的功能  
               if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){  
                   clearCacheFolder(getExternalCacheDir(context),System.currentTimeMillis());  
               }  
         
           }      
             
           /**  
            * 清除缓存目录  
            * @param dir 目录  
            * @param numDays 当前系统时间  
            * @return  
            */  
           private static int clearCacheFolder(File dir, long curTime) {            
               int deletedFiles = 0;           
               if (dir!= null && dir.isDirectory()) {               
                   try {                  
                       for (File child:dir.listFiles()) {      
                           if (child.isDirectory()) {                
                               deletedFiles += clearCacheFolder(child, curTime);            
                           }    
                           if (child.lastModified() < curTime) {       
                               if (child.delete()) {                     
                                   deletedFiles++;             
                               }      
                           }      
                       }               
                   } catch(Exception e) {         
                       e.printStackTrace();      
                   }       
               }         
               return deletedFiles;       
           }  


}
