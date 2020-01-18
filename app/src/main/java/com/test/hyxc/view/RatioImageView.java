package com.test.hyxc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.test.hyxc.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RatioImageView extends ImageView {

    /**
     * 宽高比例
     */
    private float mRatio = 0f;
    //圆角大小
    private  int mBorderRadius=8;
    private Paint mPaint;
    // 3x3 矩阵，主要用于缩小放大
    private Matrix mMatrix;
    //渲染图像，使用图像为绘制图形着色
    private BitmapShader mBitmapShader;
    public static final int LOCATE_LEFT_TO_TOP = 0; // 左上
    public static final int LOCATE_RIGHT_TO_TOP = 1; // 右上
    public static final int LOCATE_LEFT_TO_BOTTOM = 2; // 左下
    public static final int LOCATE_RIGHT_TO_BOTTOM = 3; // 右下
    public static final int LOCATE_CENTER = 4; // 居中
    @IntDef({LOCATE_LEFT_TO_TOP, LOCATE_RIGHT_TO_TOP, LOCATE_LEFT_TO_BOTTOM, LOCATE_RIGHT_TO_BOTTOM, LOCATE_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Locate {
    }

    @Locate
    int mCurrentLocate = LOCATE_CENTER;
    private Context mContext;
    private int mScale = 4; // 设置水印图与原图的比例
    private Bitmap mWatemarkBitmap;
    private RectF srcRect;
    private PorterDuffXfermode mXfermode;
    private boolean mIsWatermark = false;
    private int mWatermarkWidth = -1; // 水印宽度
    private int mWatermarkHeight = -1; // 水印高度
    private int mWatermarkPaddingTop;
    private int mWatermarkPaddingRight;
    private int mWatermarkPaddingLeft;
    private int mWatermarkPaddingBottom;
    private  int type=0;



    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        mRatio = typedArray.getFloat(R.styleable.RatioImageView_ratio, 0f);
        typedArray.recycle();
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    public RatioImageView(Context context,int type) {
        super(context);
        this.type=type;
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        this.mContext = context;
        init();
    }
    /**
     * 默认资源初始化
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
        mWatemarkBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.video);
    }
    /**
     * 从xml文件中获取属性
     * @param attrs attrs
     */
   /* private void initAttribute(AttributeSet attrs) {
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.WatermarkImageView);
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.WatermarkImageView_watermark_src:
                    // Bitmap bitmap = ((BitmapDrawable) typedArray.getDrawable(attr)).getBitmap();
                    int resourceId = typedArray.getResourceId(attr, R.drawable.ic_prologo);
                    mWatemarkBitmap = BitmapFactory.decodeResource(getResources(), resourceId);
                    break;
                case R.styleable.WatermarkImageView_watermark_visible:
                    mIsWatermark = typedArray.getBoolean(R.styleable.WatermarkImageView_watermark_visible, false);
                    break;
                case R.styleable.WatermarkImageView_watermark_width:
                    mWatermarkWidth = typedArray.getLayoutDimension(R.styleable.WatermarkImageView_watermark_width, -1);
                    Log.d("mWatermarkWidth" + mWatermarkWidth);
                    break;
                case R.styleable.WatermarkImageView_watermark_height:
                    mWatermarkHeight = typedArray.getLayoutDimension(R.styleable.WatermarkImageView_watermark_height, -1);
                    Log.d("mWatermarkHeight" + mWatermarkHeight);
                    break;
                case R.styleable.WatermarkImageView_watermark_padding_top:
                    mWatermarkPaddingTop = typedArray.getLayoutDimension(R.styleable.WatermarkImageView_watermark_padding_top, -1);
                    Log.d("mWatermarkPaddingTop" + mWatermarkPaddingTop);
                    break;
                case R.styleable.WatermarkImageView_watermark_padding_right:
                    mWatermarkPaddingRight = typedArray.getLayoutDimension(R.styleable.WatermarkImageView_watermark_padding_right, -1);
                    Log.d("mWatermarkPaddingRight" + mWatermarkPaddingRight);
                    break;
                case R.styleable.WatermarkImageView_watermark_padding_left:
                    mWatermarkPaddingLeft = typedArray.getLayoutDimension(R.styleable.WatermarkImageView_watermark_padding_left, -1);
                    Log.d("mWatermarkPaddingLeft" + mWatermarkPaddingLeft);
                    break;
                case R.styleable.WatermarkImageView_watermark_padding_bottom:
                    mWatermarkPaddingBottom = typedArray.getLayoutDimension(R.styleable.WatermarkImageView_watermark_padding_bottom, -1);
                    Log.d("mWatermarkPaddingBottom" + mWatermarkPaddingBottom);
                    break;
                case R.styleable.WatermarkImageView_watermark_location:
                    mCurrentLocate = typedArray.getInteger(R.styleable.WatermarkImageView_watermark_location, mCurrentLocate);
                    Log.d("mCurrentLocate" + mCurrentLocate);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();
    }*/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 不设置宽高默认按比例计算
        if (mWatermarkWidth < 0) {
            mWatermarkWidth = w / mScale;
        }
        if (mWatermarkHeight < 0) {
            mWatermarkHeight = h / mScale;
        }
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        switch (mCurrentLocate) {
            case LOCATE_LEFT_TO_TOP:
                // 左上
                left = mWatermarkPaddingLeft;
                top = mWatermarkPaddingTop;
                right = mWatermarkWidth + mWatermarkPaddingLeft;
                bottom = mWatermarkHeight + mWatermarkPaddingTop;
                break;

            case LOCATE_RIGHT_TO_TOP:
                // 右上
                left = w - mWatermarkWidth - mWatermarkPaddingRight;
                top = mWatermarkPaddingTop;
                right = left + mWatermarkWidth;
                bottom = top + mWatermarkHeight;
                break;

            case LOCATE_LEFT_TO_BOTTOM:
                // 左下
                left = mWatermarkPaddingLeft;
                top = h - mWatermarkPaddingBottom - mWatermarkHeight;
                right = left + mWatermarkWidth;
                bottom = h - mWatermarkPaddingBottom;
                break;

            case LOCATE_RIGHT_TO_BOTTOM:
                // 右下
                left = w - mWatermarkPaddingRight - mWatermarkWidth;
                top = h - mWatermarkPaddingBottom - mWatermarkHeight;
                right = w - mWatermarkPaddingRight;
                bottom = h - mWatermarkPaddingBottom;
                break;

            case LOCATE_CENTER:
                // 居中
                int parentHalfW = w >> 1;
                int watermarkHalfW = mWatermarkWidth >> 1;
                int parentHalfH = h >> 1;
                int watermarkHalfH = mWatermarkHeight >> 1;

                left = parentHalfW - watermarkHalfW;
                top = parentHalfH - watermarkHalfH;
                right = parentHalfW + watermarkHalfW;
                bottom = parentHalfH + watermarkHalfH;
                break;
            default:
                break;
        }
        srcRect = new RectF(left, top, right, bottom);


    }


    /**
     * 设置ImageView的宽高比
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        mRatio = ratio;
    }
    //设置圆角 重写方法
    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null){
            return;
        }
        Bitmap bitmap = drawableToBitamp(getDrawable());
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight()))
        {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(),
                    getHeight() * 1.0f / bitmap.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mPaint.setShader(mBitmapShader);
        canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()), mBorderRadius, mBorderRadius,
                mPaint);
        if(type==1) {
            //如果是视频 添加水印 播放按钮
            drawWatermark(canvas);
        }

    }
    /**
     * 画水印
     *
     * @param canvas canvas
     */
    private void drawWatermark(Canvas canvas) {
        // 创建水印图层
        int i = canvas.saveLayer(srcRect, mPaint, Canvas.ALL_SAVE_FLAG);
        // 设置模式，为直接覆盖到原图
        mPaint.setXfermode(mXfermode);
        // 绘制水印到当前图层
        canvas.drawBitmap(mWatemarkBitmap, null, srcRect, mPaint);
        // 清除模式
        mPaint.setXfermode(null);
        // 将如上画的层覆盖到原有层级上
        canvas.restoreToCount(i);
    }

    private Bitmap drawableToBitamp(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所有当为颜色时候获取控件的宽高
        int w = drawable.getIntrinsicWidth() <= 0 ? getWidth() : drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight() <= 0 ? getHeight() : drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (mRatio != 0) {
            float height = width / mRatio;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable = getDrawable();
                if (drawable != null) {
                    drawable.mutate().setColorFilter(Color.GRAY,
                            PorterDuff.Mode.MULTIPLY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Drawable drawableUp = getDrawable();
                if (drawableUp != null) {
                    drawableUp.mutate().clearColorFilter();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
