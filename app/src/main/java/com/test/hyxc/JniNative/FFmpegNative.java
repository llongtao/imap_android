package com.test.hyxc.JniNative;
import android.os.AsyncTask;

public class FFmpegNative {
    public interface  KitInterface{
        void onStart();
        void onProgress(int progress);
        void onEnd(int result);
    }
    static{
        System.loadLibrary("jni-lib");
        System.loadLibrary("avcodec");
        System.loadLibrary("avfilter");
        System.loadLibrary("avformat");
        System.loadLibrary("avutil");
        System.loadLibrary("swresample");
        System.loadLibrary("swscale");
        System.loadLibrary("fdk-aac");
    }
    public static native int JniCppAdd(int a,int b);
    public static native int JniCppSub(int a,int b);
    public static native int FFmpegTest(String input,String output);
    ////////////原生通配 ffmpeg命令
    public native static int run(String[] commands);



    /////////////////////////////
    public static int execute(String[] commands){
        return run(commands);
    }

    public static void execute(String[] commands, final KitInterface kitIntenrface){
        new AsyncTask<String[],Integer,Integer>(){
            @Override
            protected void onPreExecute() {
                if(kitIntenrface != null){
                    kitIntenrface.onStart();
                }
            }
            @Override
            protected Integer doInBackground(String[]... params) {
                return run(params[0]);
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
                if(kitIntenrface != null){
                    kitIntenrface.onProgress(values[0]);
                }
            }
            @Override
            protected void onPostExecute(Integer integer) {
                if(kitIntenrface != null){
                    kitIntenrface.onEnd(integer);
                }
            }
        }.execute(commands);
    }
}
