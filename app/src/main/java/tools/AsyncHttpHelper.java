package tools;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;


@SuppressWarnings("deprecation")
public class AsyncHttpHelper {
	public static final String HTTP_SERVER = "";
	public static final int PORT = 8000;
	public static final String CONTEXT_ROOT = "/yourproject";
	public static final String URL_USER_INFO=HTTP_SERVER+":"+PORT+CONTEXT_ROOT+"/...";
	public static final String URL_OSSCONFIG=HTTP_SERVER+":"+PORT+CONTEXT_ROOT+"/...";
	public static final String URL_WORK_BASE= HTTP_SERVER + ":" + PORT + CONTEXT_ROOT+"/###";
	public static final String URL_SEARCH_BASE= HTTP_SERVER + ":" + PORT + CONTEXT_ROOT+"/###";
	public static final String URL_ACTIVITY_BASE= HTTP_SERVER + ":" + PORT + CONTEXT_ROOT+"/###";
	public static final String URL_ACTIVITY_USER_BASE= HTTP_SERVER + ":" + PORT + CONTEXT_ROOT+"/###";

	public static final String URL_ISLAND_BASE=HTTP_SERVER+":"+PORT+CONTEXT_ROOT+"/";
	public static final String URL_ISLAND_RESIDENT_BASE=HTTP_SERVER+":"+PORT+CONTEXT_ROOT+"/";
	public static final String URL_ISLAND_CATEGORY=HTTP_SERVER+":"+PORT+CONTEXT_ROOT+"/";
	//省市区
	public static final String URL_PRO_CITY_AREA = HTTP_SERVER + ":" + PORT + CONTEXT_ROOT + "/";
	//专门的地区表
	public static final String URL_CCREGION = HTTP_SERVER + ":" + PORT + CONTEXT_ROOT + "/";
	//大学
	public static final String URL_UNIVERSITY = HTTP_SERVER + ":" + PORT + CONTEXT_ROOT + "/";
	public static final String URL_LOGIN =HTTP_SERVER+":"+PORT+CONTEXT_ROOT+"";
	public static final String URL_FRIEND_BASE=HTTP_SERVER+":"+PORT+CONTEXT_ROOT+"/";
    //版本
	public static final String URL_VERSION_BASE=HTTP_SERVER+":"+PORT+CONTEXT_ROOT+"/";

	private static AsyncHttpClient client;
	private static String appUserAgent;
	private AsyncHttpHelper(){}
	
	public synchronized static AsyncHttpClient getClient(){
		if(client == null){
			client = new AsyncHttpClient();
			//最大重试次数和每次间隔时间
			client.setMaxRetriesAndTimeout(0, AsyncHttpClient.DEFAULT_RETRY_SLEEP_TIME_MILLIS);
			//设置连接超时时间，默认为10秒
			client.setTimeout(20 * 1000);
		}
		return client;
	}
	public static String getUserAgent() {
		if (null == appUserAgent || "".equals(appUserAgent)) {
			StringBuilder sb = new StringBuilder("itvk");
			sb.append("|Android");// 手机系统平台
			sb.append("|" + android.os.Build.VERSION.RELEASE);// 手机系统版本
			sb.append("|" + android.os.Build.MODEL); // 手机型号
			appUserAgent = sb.toString();
		}
		return appUserAgent;
	}
	/**
	 * 发送请求参数为JSON字符串的异步HTTP POST请求
	 * @param ctx
	 * @param url
	 * @param json
	 * @param handler
	 */
	public static void post(Context ctx, String url, String json, NoProgressResponseHandler handler){
		try {
			StringEntity entity = new StringEntity(json);
			getClient().post(ctx, url, entity, "application/json", handler);
		} catch (UnsupportedEncodingException e) {
			SystemHelper.makeExceptionToast(ctx, e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送异步的HTTP POST请求
	 * @param url
	 * @param params
	 * @param handler
	 */
	public static void post(String url, RequestParams params, NoProgressResponseHandler handler){
		getClient().post(url, params, handler);
	}
	
	/**
	 * 发送异步的HTTP POST请求
	 * @param url

	 * @param handler
	 */
	public static void post(String url, NoProgressResponseHandler handler){
		getClient().post(url, handler);
	}
	
	/**
	 * 发送请求参数为JSON字符串的异步HTTP GET请求
	 * @param ctx
	 * @param url
	 * @param json
	 * @param handler
	 */
	public static void get(Context ctx, String url, String json, NoProgressResponseHandler handler){
		try {
			StringEntity entity = new StringEntity(json);
			getClient().get(ctx, url, entity, "application/json", handler);
		} catch (UnsupportedEncodingException e) {
			SystemHelper.makeExceptionToast(ctx, e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送异步的HTTP GET请求
	 * @param url
	 * @param params
	 * @param handler
	 */
	public static void get(String url, RequestParams params, NoProgressResponseHandler handler){
		getClient().get(url, params, handler);
	}
	
	/**
	 * 发送异步的HTTP GET请求
	 * @param
	 * @param handler
	 */
	public static void get(String url, NoProgressResponseHandler handler){
		getClient().get(url, handler);
	}
	
	/**
	 * 不带等待框的响应处理器
	 * @author qiujy
	 */
	public static abstract class NoProgressResponseHandler extends TextHttpResponseHandler{
		private Context ctx;
		public NoProgressResponseHandler(Context ctx){
			this.ctx = ctx;
		}
		
		/**
		 * 响应成功时，处理响应消息体的字符串内容的回调方法，由子类实现
		 * @param responseString 响应消息体的字符串内容
		 */
		public abstract void success(String responseString) throws JSONException;
		
		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseString){
			try{
				success(responseString);
			}catch(Exception e){
				SystemHelper.makeExceptionToast(ctx, e);
				e.printStackTrace();
			}
		}
		
		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
			SystemHelper.makeExceptionToast(ctx, statusCode, throwable);
			throwable.printStackTrace();
		}
	}
	
	/**
	 * 带等待框的响应处理器
	 * @author qiujy
	 */
	public static abstract class MyResponseHandler extends NoProgressResponseHandler{
		//private Context ctx;
		private LoadingDialog dialog;
		
		public MyResponseHandler(Context ctx){
			super(ctx);
			//this.ctx = ctx;
			dialog = new LoadingDialog(ctx);
			dialog.setCancelable(false);
		}

		@Override
		public void onStart() {
			//在发送异步请求前，要弹出一个进度对话框，阻塞界面
			dialog.show();
		}

		
		@Override
		public void onFinish() {
			//异步请求结束后，关闭进度对话框
			dialog.dismiss();
		}
	}
}
