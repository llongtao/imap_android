package view;

import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.test.hyxc.R;

public class FastBlurActivity extends AppCompatActivity{

        @SuppressLint("WrongViewCast")
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.imap_blur_back);
            final Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.shezhi);

            findViewById(R.id.content).getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
//                        blur(bmp1, findViewById(R.id.content));
                            applyBlur();
                            return true;
                        }
                    });
        }

        private void applyBlur() {
            View view = getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache(true);
            /**
             * 获取当前窗口快照，相当于截屏
             */
            Bitmap bmp1 = view.getDrawingCache();
            int height = getOtherHeight();
            /**
             * 除去状态栏和标题栏
             */
            Bitmap bmp2 = Bitmap.createBitmap(bmp1, 0, height,bmp1.getWidth(), bmp1.getHeight() - height);
            blur(bmp2, findViewById(R.id.content));
        }

        @SuppressLint("NewApi")
        private void blur(Bitmap bkg, View view) {
            long startMs = System.currentTimeMillis();
            float scaleFactor = 8;//图片缩放比例；
            float radius = 20;//模糊程度

            Bitmap overlay = Bitmap.createBitmap(
                    (int) (view.getMeasuredWidth() / scaleFactor),
                    (int) (view.getMeasuredHeight() / scaleFactor),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(overlay);
            canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()/ scaleFactor);
            canvas.scale(1 / scaleFactor, 1 / scaleFactor);
            Paint paint = new Paint();
            paint.setFlags(Paint.FILTER_BITMAP_FLAG);
            canvas.drawBitmap(bkg, 0, 0, paint);


            overlay = FastBlur.doBlur(overlay, (int) radius, true);
            view.setBackground(new BitmapDrawable(getResources(), overlay));
            /**
             * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。
             */
            Log.i("jerome", "blur time:" + (System.currentTimeMillis() - startMs));
        }

        /**
         * 获取系统状态栏和软件标题栏，部分软件没有标题栏，看自己软件的配置；
         * @return
         */
        private int getOtherHeight() {
            Rect frame = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
            int titleBarHeight = contentTop - statusBarHeight;
            return statusBarHeight + titleBarHeight;
        }
}
