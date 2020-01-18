package tools;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.view.WindowManager;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.baidu.mapapi.SDKInitializer;
import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.test.hyxc.chat.database.ConversationEntry;
import com.test.hyxc.chat.database.FriendEntry;
import com.test.hyxc.chat.database.FriendRecommendEntry;
import com.test.hyxc.model.User;
import com.test.hyxc.model.UserInfo;
import com.test.hyxc.page.workshow.widget.MyFileNameGenerator;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import com.test.hyxc.chat.database.UserEntry;
import com.test.hyxc.chat.entity.NotificationClickEventReceiver;
import com.test.hyxc.chat.pickerimage.utils.StorageUtil;
import com.test.hyxc.chat.utils.SharePreferenceManager;
import com.test.hyxc.chat.utils.imagepicker.GlideImageLoader;
import com.test.hyxc.chat.utils.imagepicker.ImagePicker;
import com.test.hyxc.chat.utils.imagepicker.view.CropImageView;
import okhttp3.*;
import okhttp3.Response;
import tools.BaiduMap.service.LocationService;
public class AppContext extends Application {
	public static Context sApplication;
	private static final String CACHE_DIR_NAME = "/qbank";
	private static final String TAG = "AppContext";
	/**缓存目录全路径名*/
	public static final String CACHE_DIR;
	/**文件缓存目录全路径名*/
	public static final String CACHE_DIR_FILE;
	/**图片缓存目录全路径名*/
	public static final String CACHE_DIR_IMAGE;
	/**登录后的用户信息*/
	public User user;
	/**系统默认的共享首选项*/
	public  OssConfig ossConfig;
	public WindowManager windowManager;
	public SharedPreferences sysSharedPreferences;
	private boolean autoLogin=true;
	private boolean showImg;
	private String userId;
	private String username;
	private String password;
	private String token="";
	//昵称 头像
	private String nickname;
	private String headImg;
	private String  lastLoginTime;
	private UserInfo userInfo;
	//预留我的关注 关注我的各类信息
	private String followMe;
	private String myFollow;
	//我关注的海岛
	private String userIslandFollow;
	//我所在的海岛
	private String userIsland;
	//允许在的海岛数量
	private String userIslandLimit;
	//定位
	//public LocationService locationService;
	public double longitude,latitude;
	public String city;
	public Vibrator mVibrator;
	//极光
	public Integer unReadMsgCnt=0;
	///记录下jg熊表情包是否解压过了
	public String bearImgUnziped="0"; //0未解压  1：解压了
	////****极光集成配置****////
	////****极光集成配置****////
	////****极光集成配置****////
	public static final String CONV_TITLE = "conv_title";
	public static final int IMAGE_MESSAGE = 1;
	public static final int TAKE_PHOTO_MESSAGE = 2;
	public static final int TAKE_LOCATION = 3;
	public static final int FILE_MESSAGE = 4;
	public static final int TACK_VIDEO = 5;
	public static final int TACK_VOICE = 6;
	public static final int BUSINESS_CARD = 7;
	public static final int REQUEST_CODE_SEND_FILE = 26;
	public static final int RESULT_CODE_ALL_MEMBER = 22;
	public static Map<Long, Boolean> isAtMe = new HashMap<>();
	public static Map<Long, Boolean> isAtall = new HashMap<>();
	public static List<Message> forwardMsg = new ArrayList<>();
	public static long registerOrLogin = 1;
	public static final int REQUEST_CODE_TAKE_PHOTO = 4;
	public static final int REQUEST_CODE_SELECT_PICTURE = 6;
	public static final int REQUEST_CODE_CROP_PICTURE = 18;
	public static final int REQUEST_CODE_CHAT_DETAIL = 14;
	public static final int RESULT_CODE_FRIEND_INFO = 17;
	public static final int REQUEST_CODE_ALL_MEMBER = 21;
	public static final int RESULT_CODE_EDIT_NOTENAME = 29;
	public static final String NOTENAME = "notename";
	public static final int REQUEST_CODE_AT_MEMBER = 30;
	public static final int RESULT_CODE_AT_MEMBER = 31;
	public static final int RESULT_CODE_AT_ALL = 32;
	public static final int SEARCH_AT_MEMBER_CODE = 33;
	public static final int RESULT_BUTTON = 2;
	public static final int START_YEAR = 1900;
	public static final int END_YEAR = 2050;
	public static final int RESULT_CODE_SELECT_FRIEND = 23;
	public static final int REQUEST_CODE_SELECT_ALBUM = 10;
	public static final int RESULT_CODE_SELECT_ALBUM = 11;
	public static final int RESULT_CODE_SELECT_PICTURE = 8;
	public static final int REQUEST_CODE_BROWSER_PICTURE = 12;
	public static final int RESULT_CODE_BROWSER_PICTURE = 13;
	public static final int RESULT_CODE_SEND_LOCATION = 25;
	public static final int RESULT_CODE_SEND_FILE = 27;
	public static final int REQUEST_CODE_SEND_LOCATION = 24;
	public static final int REQUEST_CODE_FRIEND_INFO = 16;
	public static final int RESULT_CODE_CHAT_DETAIL = 15;
	public static final int ON_GROUP_EVENT = 3004;
	public static final String DELETE_MODE = "deleteMode";
	public static final int RESULT_CODE_ME_INFO = 20;
	public static final String DRAFT = "draft";
	public static final String GROUP_ID = "groupId";
	public static final String POSITION = "position";
	public static final String MsgIDs = "msgIDs";
	public static final String NAME = "name";
	public static final String ATALL = "atall";
	public static final String SEARCH_AT_MEMBER_NAME = "search_at_member_name";
	public static final String SEARCH_AT_MEMBER_USERNAME = "search_at_member_username";
	public static final String SEARCH_AT_APPKEY = "search_at_appkey";
	public static final String MEMBERS_COUNT = "membersCount";
	public static String PICTURE_DIR = "sdcard/com.test.hyxc/pictures/";
	private static final String JCHAT_CONFIGS = "JChat_configs";
	public static String FILE_DIR = "sdcard/com.test.hyxc/recvFiles/";
	public static String VIDEO_DIR = "sdcarVIDEOd/com.test.hyxc/sendFiles/";
	public static final String TARGET_ID = "targetId";
	public static final String ATUSER = "atuser";
	public static final String TARGET_APP_KEY = "targetAppKey";
	public static int maxImgCount;               //允许选择图片最大数
	public static final String GROUP_NAME = "groupName";
	public static Context context;
	public static LocationService locationService;
	public static com.test.hyxc.chat.location.service.LocationService locationService_jiguang; //注意区分极光系统的
	public static List<GroupInfo> mGroupInfoList = new ArrayList<>();
	public static List<cn.jpush.im.android.api.model.UserInfo> mFriendInfoList = new ArrayList<>();
	public static List<cn.jpush.im.android.api.model.UserInfo> mSearchGroup = new ArrayList<>();
	public static List<cn.jpush.im.android.api.model.UserInfo> mSearchAtMember = new ArrayList<>();
	public static List<Message> ids = new ArrayList<>();
	public static List<cn.jpush.im.android.api.model.UserInfo> alreadyRead = new ArrayList<>();
	public static List<cn.jpush.im.android.api.model.UserInfo> unRead = new ArrayList<>();
	public static List<String> forAddFriend = new ArrayList<>();
	////****极光集成配置****////
	static {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
				&& !Environment.isExternalStorageRemovable()) {
			CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + CACHE_DIR_NAME;
		} else {
			CACHE_DIR = Environment.getRootDirectory().getAbsolutePath() + CACHE_DIR_NAME;
		}
		CACHE_DIR_IMAGE = CACHE_DIR + "/pic";
		CACHE_DIR_FILE = CACHE_DIR + "/tmp";
	}
	/////////////////视频翻页播放使用到//////////////
	private HttpProxyCacheServer proxy;
	public static HttpProxyCacheServer getProxy(Context context) {
		AppContext app = (AppContext) context.getApplicationContext();
		return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
	}

	private HttpProxyCacheServer newProxy() {
		return new HttpProxyCacheServer.Builder(this)
				.maxCacheSize(1024 * 1024 * 1024)       // 1 Gb for cache
				.fileNameGenerator(new MyFileNameGenerator())
				.build();
	}
	///////////////////////////
	@Override
	public void onCreate() {
		super.onCreate();
		Configuration.Builder configurationBuilder = new Configuration.Builder( getApplicationContext());
		configurationBuilder.addModelClass(UserEntry.class);
		configurationBuilder.addModelClass(FriendEntry.class);
		configurationBuilder.addModelClass(ConversationEntry.class);
		configurationBuilder.addModelClass(FriendRecommendEntry.class);
		ActiveAndroid.initialize(configurationBuilder.create());
		//崩溃日志收集
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
		sApplication = getApplicationContext();
		//极光im******************jiguang
		initJiGuang();
		createCacheDir();
		initSystemSetting();
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		locationService = new LocationService(getApplicationContext());
		locationService_jiguang = new com.test.hyxc.chat.location.service.LocationService(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		SDKInitializer.initialize(getApplicationContext());
		//初始化阿里云oss
		try {
			initOssConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//判断上次登陆时间是否大于7天,大于则进行自动登陆一次
		testAutoLoginAgain();
	}
	//这里设置7天自动登陆一次
	public void testAutoLoginAgain(){
		String lastLogtime=this.getLastLoginTime();
		if(lastLogtime.equals("-1")){
			return;
		}
		Long last=Long.parseLong(lastLogtime);
		Long now=System.currentTimeMillis();
		//大于7天
		if(now-last>=604800000){
			try {
				autoLogin(getUsername(),getPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	private void autoLogin(String username,String password) throws Exception {
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
			NetUtils netUtils = NetUtils.getInstance();
			Map map = new HashMap();
			netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/applogin_android_pwd", "", map, new NetUtils.MyNetCall() {
				@Override
				public void success(Call call, Response response) throws IOException, JSONException {
					String ret = response.body().string();
					JSONObject json = new JSONObject(ret);
					if (json.getString("loginState").equals("success")) {
						setUserId(json.getString("userId"));
						setUsername(json.getString("username"));
						setPassword(json.getString("password"));
						setToken(json.getString("token"));
						//记录上次登陆时间，用于判断如果大于7天就自动重新登陆
						setLastLoginTime(String.valueOf(System.currentTimeMillis()));
					}
				}

				@Override
				public void failed(Call call, IOException e) {
				}
			});
		}
	}
	public void initOssConfig() throws Exception {
		//默认
		String accessid="";
		String signature="=";
		String expire="";
		String host="";
		String dir="";
		String policy="";
		OssConfig ossConfig=new OssConfig(accessid,signature,expire,host,dir,policy);
		setOssConfig(ossConfig);
		NetUtils netUtils=NetUtils.getInstance();
		netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_OSSCONFIG + "/",
				token, new HashMap<String, String>(), new NetUtils.MyNetCall() {
					@Override
					public void success(Call call, Response response) throws IOException, JSONException {
						String ret=response.body().string();
						JSONObject json = new JSONObject(ret);
						String accessid=json.getString("accessid");
						String signature=json.getString("signature");
						String expire=json.getString("expire");
						String host=json.getString("host");
						String dir=json.getString("dir");
						String policy=json.getString("policy");
						OssConfig ossConfig=new OssConfig(accessid,signature,expire,host,dir,policy);
						setOssConfig(ossConfig);
					}
					@Override
					public void failed(Call call, IOException e) {}
				});
	}
	public boolean isAutoLogin(){
		//默认自动登陆
		autoLogin = sysSharedPreferences.getBoolean("pref_key_auto_login", true);
		return autoLogin;
	}
	public void setAutoLogin(boolean autoLogin){
		this.autoLogin = autoLogin;
		sysSharedPreferences.edit()
			.putBoolean("pref_key_auto_login", autoLogin)
			.commit(); //同步提交
	}
	
	public boolean isShowImg(){
		showImg = sysSharedPreferences.getBoolean("pref_key_download_img", true);
		return showImg;
	}
	
	public void setShowImg(boolean showImg){
		this.showImg = showImg;
		sysSharedPreferences.edit()
			.putBoolean("pref_key_download_img", showImg)
			.commit(); 
	}
	//经纬度
	public	double  getLongitude(){
		longitude = Double.parseDouble(sysSharedPreferences.getString("longitude", "0"));
		return longitude;
	}
	public void setLongitude(double longitude){
		this.longitude=longitude;
		sysSharedPreferences.edit()
				.putString("longitude", String.valueOf(longitude))
				.commit();
	}
	public	double  getLatitude(){
		latitude = Double.parseDouble(sysSharedPreferences.getString("latitude", "0"));
		return latitude;
	}
	public void setLatitude(double latitude){
		this.latitude=latitude;
		sysSharedPreferences.edit()
				.putString("latitude", String.valueOf(latitude))
				.commit();
	}
	//城市
	public void setCity(String city){
		this.city = city;
		sysSharedPreferences.edit()
				.putString("city",city)
				.commit();
	}
	public String getCity(){
		city = sysSharedPreferences.getString("city","北京市");
		return  city;
	}
	//nickname
	public	String  getNickname(){
		nickname = sysSharedPreferences.getString("nickname", null);
		return nickname;
	}
	public void setNickname(String nickname){
		this.nickname=nickname;
		sysSharedPreferences.edit()
				.putString("nickname", nickname)
				.commit();
	}
	//头像
	public	String  getHeadImg(){
		headImg = sysSharedPreferences.getString("headImg", null);
		return headImg;
	}
	public void setHeadImg(String headImg){
		this.headImg=headImg;
		sysSharedPreferences.edit()
				.putString("headImg", headImg)
				.commit();
	}
	//我关注的
	public String getMyFollow(){
		myFollow = sysSharedPreferences.getString("myFollow", null);
		return myFollow;
	}
	public void setMyFollow(String myFollow){
		this.myFollow=myFollow;
		sysSharedPreferences.edit()
				.putString("myFollow", myFollow)
				.commit();
	}
	//关注我的
	public String getFollowMe(){
		followMe=sysSharedPreferences.getString("followMe", null);
		return followMe;
	}
	public void setFollowMe(String followMe){
		this.followMe=followMe;
		sysSharedPreferences.edit()
				.putString("followMe", followMe)
				.commit();
	}
	//我关注的海岛
	public String getUserIslandFollow(){
		userIslandFollow=sysSharedPreferences.getString("userIslandFollow",null);
		return userIslandFollow;
	}
	public void setUserIslandFollow(String userIslandFollow){
		this.userIslandFollow=userIslandFollow;
		sysSharedPreferences.edit()
				.putString("userIslandFollow",userIslandFollow)
				.commit();
	}
	//我所在的海岛
	public String getUserIsland(){
		userIsland=sysSharedPreferences.getString("userIsland",null);
		return userIsland;
	}
	public void setUserIsland(String userIsland){
		this.userIsland=userIsland;
		sysSharedPreferences.edit()
				.putString("userIsland",userIsland)
				.commit();
	}
	//允许加入的海岛数量
	public String getUserIslandLimit(){
		userIslandLimit=sysSharedPreferences.getString("userIslandLimit",String.valueOf(GlobalConfig.userIslandLimit));
		return userIslandLimit;
	}
	public void setUserIslandLimit(String userIslandLimit){
		this.userIslandLimit=userIslandLimit;
		sysSharedPreferences.edit()
				.putString("userIslandLimit",userIslandLimit)
				.commit();
	}
	public String getUsername() {
		username = sysSharedPreferences.getString("username", null);
		return username;
	}

	public String getUserId() {
		userId = sysSharedPreferences.getString("userId", null);
		return userId;
	}
	public void setUserId(String userId){
		this.userId=userId;
		sysSharedPreferences.edit()
				.putString("userId", userId)
				.commit();
	}
	public void setUsername(String username) {
		this.username = username;
		sysSharedPreferences.edit()
			.putString("username", username)
			.commit(); 
	}

	public String getPassword() {
		password = sysSharedPreferences.getString("password", null);
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		sysSharedPreferences.edit()
			.putString("password", password)
			.commit(); 
	}
	public void setBearImgUnziped(String bearImgUnziped){
		this.bearImgUnziped = bearImgUnziped;
		sysSharedPreferences.edit()
				.putString("bearImgUnziped",bearImgUnziped)
				.commit();
	}
	public String getBearImgUnziped(){
		bearImgUnziped=sysSharedPreferences.getString("bearImgUnziped","0");
		return bearImgUnziped;
	}
	public OssConfig getOssConfig() {
		return ossConfig;
	}
	public void setOssConfig(OssConfig ossConfig) {
		this.ossConfig = ossConfig;
	}
	/**初始化系统设置*/
	private void initSystemSetting(){
		sysSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		autoLogin = isAutoLogin();
		//上次登陆时间
		this.lastLoginTime=sysSharedPreferences.getString("lastLoginTime","-1");
		//token
		this.token=sysSharedPreferences.getString("token","");
		sysSharedPreferences.edit()
				.putString("token", token)
				.commit();
		setAutoLogin(autoLogin);
		showImg = isShowImg();
		setShowImg(showImg);
		this.windowManager=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
	}
	public String getLastLoginTime(){
		this.lastLoginTime=sysSharedPreferences.getString("lastLoginTime","-1");
        return this.lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime){
		this.lastLoginTime=lastLoginTime;
		sysSharedPreferences.edit()
				.putString("lastLoginTime",lastLoginTime)
				.commit();
	}
	public  void setToken(String token){
		this.token=token;
		sysSharedPreferences.edit()
				.putString("token",token)
				.commit();
	}
	public  String getToken(){
		this.token=sysSharedPreferences.getString("token","");
		return token;
	}
	
	/**
	 * 创建缓存目录
	 */
	private void createCacheDir() {
		File f = new File(CACHE_DIR);
		if (f.exists()) {
			LogHelper.d("AppContext", "SD卡缓存目录:已存在!");
		} else {
			if (f.mkdirs()) {
				LogHelper.d("AppContext", "SD卡缓存目录:" + f.getAbsolutePath() + "已创建!");
			} else {
				LogHelper.d("AppContext", "SD卡缓存目录:创建失败!");
			}
		}
		File ff = new File(CACHE_DIR_FILE);
		if (ff.exists()) {
			LogHelper.d(TAG, "SD卡文件缓存目录：已存在!");
		} else {
			if (ff.mkdirs()) {
				LogHelper.d(TAG, "SD卡文件卡缓存目录:" + ff.getAbsolutePath() + "已创建!");
			} else {
				LogHelper.d(TAG, "SD卡文件缓存目录:创建失败!");
			}
		}
		File fff = new File(CACHE_DIR_IMAGE);
		if (fff.exists()) {
			LogHelper.d(TAG, "SD卡图片缓存目录：已存在!");
		} else {
			if (fff.mkdirs()) {
				LogHelper.d(TAG, "SD卡图片卡缓存目录:" + fff.getAbsolutePath() + "已创建!");
			} else {
				LogHelper.d(TAG, "SD卡图片缓存目录:创建失败!");
			}
		}
	}

	public WindowManager getWindowManager() {
		return windowManager;
	}

	public void setWindowManager(WindowManager windowManager) {
		this.windowManager = windowManager;
	}
	public void initJiGuang(){
		context = getApplicationContext();
		StorageUtil.init(context, null);
		Fresco.initialize(getApplicationContext());
		JMessageClient.init(getApplicationContext(), true);
		JMessageClient.setDebugMode(true);
		SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
		JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND | JMessageClient.FLAG_NOTIFY_WITH_LED | JMessageClient.FLAG_NOTIFY_WITH_VIBRATE);
		new NotificationClickEventReceiver(getApplicationContext());
		initImagePicker();
	}
	private void initImagePicker() {
		ImagePicker imagePicker = ImagePicker.getInstance();
		imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
		imagePicker.setShowCamera(true);                      //显示拍照按钮
		imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
		imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
		imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
		imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
		imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
		imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
	}

	public static void setPicturePath(String appKey) {
		if (!SharePreferenceManager.getCachedAppKey().equals(appKey)) {
			SharePreferenceManager.setCachedAppKey(appKey);
			PICTURE_DIR = "sdcard/yourpackage/pictures/" + appKey + "/";
		}
	}

	public static UserEntry getUserEntry() {
		return UserEntry.getUser(JMessageClient.getMyInfo().getUserName(), JMessageClient.getMyInfo().getAppKey());
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
