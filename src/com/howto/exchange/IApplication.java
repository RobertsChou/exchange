package com.howto.exchange;
import java.lang.reflect.Type;
import java.util.ArrayList;
import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.widget.PopupWindow;
import cn.jpush.android.api.JPushInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.howto.exchange.db.DBUtil;
import com.howto.exchange.utils.PhoneParam;
import com.howto.exchange.utils.PublicUtils;
import com.howto.exchange.utils.SharedPreferencesUtil;

public class IApplication extends Application {

    public static String code = "0";
    public static boolean isLocating;
    public static String clientVersion;
    public static final String host = "http://www.kujingling.com";
    private static IApplication mInstance;
    public Bitmap bitmap = null;
    
    public boolean childChanged;
    public boolean albumChanged;
    public boolean tagChanged ; 
    public boolean imageChanged;
    public boolean imageTagChanged;
    public boolean deletPicture;
    public int importType;
	//当前孩子年级
	public String child_cur_grade;
	
	public String myValue;
	public boolean activeChanged = false;
	public boolean addImageRefresh = false;
	public boolean crop = false;
	public String user_face; //头像路径
	public boolean StartEditUpload = false;
	private String appId;
	public static int curTab = 1; //当前tab
	public static int curAlbumTab = 0;
	public static int curChlidIndex = 0;
	
    @Override
    public void onCreate() {
        super.onCreate();
        
        DBUtil.init(getApplicationContext());
        initPush();
        
        try {
            clientVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        SharedPreferencesUtil.init(this);
        PhoneParam.init(getApplicationContext());
        mInstance = this;
    }

    private void initPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());
        if(SharedPreferencesUtil.getinstance(getApplicationContext()).isSlinceMode()){
            JPushInterface.stopPush(this);
        }
    }

    public static IApplication getInstance() {
        return mInstance;
    }
    
   /* public void setAlbumListData(ArrayList<AlbumInfo> list){
        if(list == null){
            return;
        }
        albumListData = new ArrayList<AlbumInfo>();
        for (AlbumInfo albumInfo : list) {
            albumListData.add(new AlbumInfo().setData(albumInfo));
        }
        saveLocalJson(SharedPreferencesUtil.ALBUM_LIST_JSON, list);
        TestLogUtil.LogInfo("刷新了作品集本地json");
    }*/
    
    public <T> T initFromLocalJson(String key, TypeToken<T> token){
        T t = getLocalJsonObj(key, token);
        return t;
    }
    public <T> void saveLocalJson(String key, T t){
        Gson gs = new Gson();
        String json = gs.toJson(t, new TypeToken<T>() {}.getType());
        SharedPreferencesUtil.getInstance().saveLocalJson(key, json);
    }
    
    public <T> T getLocalJsonObj(String key, TypeToken<T> t ){
        String json = SharedPreferencesUtil.getInstance().getLocalJson(key);
        Type cvbType = t.getType();
        T resultB = new Gson().fromJson(json, cvbType);
        return resultB;
    }
   
  
    
    
}
