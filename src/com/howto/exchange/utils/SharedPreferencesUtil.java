package com.howto.exchange.utils;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String sharedPreferencesInfo = "dili360.shareInfo";
	private static final String IS_LOGIN = "IS_LOGIN";
	private static final String IS_NEXTLOGIN = "IS_NEXTLOGIN";//不是第一次登录
	private static final String USER_NAME = "USER_NAME";    //用户名字
	private static final String USER_NAME1 = "USER_NAME1";  //用户名称
	private static final String INVI_CODE ="INVO_CODE";
	private static final String PASS_WORD = "PASS_WORD";
	private static final String USER_INFO = "USER_INFO";
	private static final String USER_ID = "USER_ID";
	private static final String CHILD_ID = "CHILD_ID";
	private static final String CHILD_NAME = "CHILD_NAME";
	private static final String CHILD_GRADE = "CHILD_GRADE";
	private static final String SLINCE_MODE = "SLINCE_MODE";
	public static final String ALBUM_LIST_JSON = "ALBUM_LIST_JSON";
	public static final String CHILD_LIST_JSON = "CHILD_LIST_JSON";
	public static final String TAG_LIST_JSON = "TAG_LIST_JSON";
	public static final String USER_INFO_JSON = "USER_INFO_JSON";
	public static final String INFORM_KIND = "INFORM_KIND";
	public static final String Refreash_User_Face = "Refresh_User_Face";

	// share
	private static Context myContext;

	private static SharedPreferences saveInfo;
	private static Editor saveEditor;
	private static SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil();

	private final String USER_TOKEN = "user_token";

	public static void init(Context context) {
		myContext = context;
	}

	public static SharedPreferencesUtil getinstance(Context context) {
		myContext = context;
		if (saveInfo == null && myContext != null) {
			saveInfo = myContext.getSharedPreferences(sharedPreferencesInfo, Context.MODE_PRIVATE);
			saveEditor = saveInfo.edit();
		}
		return sharedPreferencesUtil;
	}

	public static SharedPreferencesUtil getInstance() {
		if (saveInfo == null && myContext != null) {
			saveInfo = myContext.getSharedPreferences(sharedPreferencesInfo, Context.MODE_PRIVATE);
			saveEditor = saveInfo.edit();
		}
		return sharedPreferencesUtil;
	}

	public boolean isLogin() {
		return saveInfo.getBoolean(IS_LOGIN, false);
	}
	
	public boolean isNextLogin() {
		return saveInfo.getBoolean(IS_NEXTLOGIN, false);
	}
	//账户
	public boolean saveUserName(String string) {
		saveEditor.putString(USER_NAME, string);
		return saveEditor.commit();
	}
	
	public String getUserName(){
		return saveInfo.getString(USER_NAME, "");
	}
	//用户名
	public boolean saveUserName1(String string) {
		saveEditor.putString(USER_NAME1, string);
		return saveEditor.commit();
	}

	public String getUserName1(){
		return saveInfo.getString(USER_NAME1, "");
	}

	public boolean saveInviCode(String string) {
		saveEditor.putString(INVI_CODE, string);
		return saveEditor.commit();
	}

	public String getInviCode(){
		return saveInfo.getString(INVI_CODE, "");
	}
	public boolean savePassword(String string) {
		saveEditor.putString(PASS_WORD, string);
		return saveEditor.commit();
	}

	public String getPassword(){
		return saveInfo.getString(PASS_WORD, null);
	}

	public boolean saveLogin(boolean b) {
		saveEditor.putBoolean(IS_LOGIN, b);
		return saveEditor.commit();
	}

	public boolean saveNextLogin(boolean b) {
		saveEditor.putBoolean(IS_NEXTLOGIN, b);
		return saveEditor.commit();
	}
	
	public boolean saveUserInfo(String json) {
		saveEditor.putString(USER_INFO, json);
		return saveEditor.commit();
	}

	public boolean saveUserId(String id) {
		saveEditor.putString(USER_ID, id);
		return saveEditor.commit();
	}

	public String getUserId(){
		return saveInfo.getString(USER_ID, null);
	}

	public boolean saveDefultChild(String id) {
		saveEditor.putString("id"+getUserId()+"id", id);
		return saveEditor.commit();
	}

	public String getChildDefaultId() {
		return saveInfo.getString("id"+getUserId()+"id", null);
	}

	public String getChildName() {
		return saveInfo.getString("name"+getUserId()+"name", null);
	}

	public boolean saveDefultName(String name) {
		saveEditor.putString("name"+getUserId()+"name", name);
		return saveEditor.commit();
	}

	public String getChildGrade() {
		return saveInfo.getString(getUserId()+"grade", null);
	}

	public boolean saveDefultGRADE(String grade) {
		saveEditor.putString(getUserId()+"grade", grade);
		return saveEditor.commit();
	}

	public boolean isFirstInto(String string) {
		return saveInfo.getBoolean(string, true);
	}

	public boolean setFirstInto(String string, boolean b) {
		saveEditor.putBoolean(string, b);
		return saveEditor.commit();
	}

	public boolean isSlinceMode() {
		return saveInfo.getBoolean(SLINCE_MODE, false);
	}

	public boolean setSlinceMode(boolean b) {
		saveEditor.putBoolean(SLINCE_MODE, b);
		return saveEditor.commit();
	}

	public boolean saveNews(boolean b) {
		saveEditor.putBoolean("HAVE_NEWS", b);
		return saveEditor.commit();
	}

	public boolean isHaveNews(){
		return saveInfo.getBoolean("HAVE_NEWS", false);
	}

	public boolean openEmail(boolean isChecked) {
		saveEditor.putBoolean("OPEN_EMAIL", isChecked);
		return saveEditor.commit();
	}

	public boolean isOpenEmail(){
		return saveInfo.getBoolean("OPEN_EMAIL", true);
	}

	public boolean openSMS(boolean isChecked) {
		saveEditor.putBoolean("openSMS", isChecked);
		return saveEditor.commit();
	} 

	public boolean isOpenSMS(){
		return saveInfo.getBoolean("openSMS", true);
	}
	/**
	 * 第三方绑定
	 * @param b
	 * @return 
	 */
	public boolean saveBound(boolean b) {
		saveEditor.putBoolean("Other_Bound", b);
		return saveEditor.commit();
	}

	public boolean isBound(){
		return saveInfo.getBoolean("Other_Bound", false);
	}

	public boolean saveOtherLoginFrom(String from) {
		saveEditor.putString("Other_LoginFrom",from);
		return saveEditor.commit();
	}

	public String getOtherLoginFrom() {
		return saveInfo.getString("Other_LoginFrom", "");
	}

	public boolean saveOtherLoginUid(String uid) {
		saveEditor.putString("Other_LoginUid",uid);
		return saveEditor.commit();
	}

	public String getOtherLoginUid() {
		return saveInfo.getString("Other_LoginUid", "");
	}

	public boolean saveAlbumList(String json) {
		saveEditor.putString("saveAlbumList",json);
		return saveEditor.commit();
	}

	public String getAlbumList() {
		return saveInfo.getString("saveAlbumList", "");
	}

	public boolean saveLocalJson(String key, String json) {
		saveEditor.putString(key,json);
		return saveEditor.commit();
	}

	public String getLocalJson(String key) {
		return saveInfo.getString(key, "");
	}

	public boolean saveOpenContent(String myValue) {
		saveEditor.putString("OpenContent",myValue);
		return saveEditor.commit();
	}
	
	public String getOpenContent() {
		return saveInfo.getString("OpenContent", "");
	}

	public boolean saveAuthCode(String trim) {
		saveEditor.putString("Auth_Code",trim);
		return saveEditor.commit();
	}
	
	public String getAuthCode(){
		return saveInfo.getString("Auth_Code", "");
	}
	
	/**
	 * 对机构名称的存储和获取
	 * @param orgName
	 */
	public void saveOrganization(String orgName){
		if("".equals(orgName)){
			return;
		}
		String orgList[] = getOrgNameList();
		if(orgList!=null){
			for(String s:orgList){
				if(s.equals(orgName)){
					return;
				}
			}
			saveEditor.putString("orgHistory",getOrgNameListStr()+";"+orgName);
		}else{
			saveEditor.putString("orgHistory",orgName);
		}
		saveEditor.commit();
	}
	public String[] getOrgNameList(){
		String orgListStr = getOrgNameListStr();
		if(orgListStr.length()>0){
			return orgListStr.split(";");
		}else{
			return null;
		}
	}
	public String getOrgNameListStr(){
		return saveInfo.getString("orgHistory","");
	}
	
}
