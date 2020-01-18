package com.test.hyxc.view;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.test.hyxc.R;
import com.test.hyxc.model.WorkResource;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.workshow.ImageShowActivity;
import com.test.hyxc.page.workshow.Page2Activity;

import java.util.List;
import java.util.Map;

import tools.ImageLoaderUtil;

/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/12
 */
public class NineGridTestLayout extends NineGridLayout {

  //  protected static final int MAX_W_H_RATIO = 2;
    public NineGridTestLayout(Context context) {
        super(context);
    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {

        ImageLoaderUtil.displayImage(mContext, imageView, url, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int newW;
                int newH;
                if (h > w * 1.7) {
                    newW = parentWidth / 2;
                    newH =(int) (newW * 1.7);
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                setOneImageLayoutParams(imageView, newW, newH);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.loading) //设置图片在下载期间显示的图片
               // .showImageForEmptyUri(R.mipmap.error)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.error)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .displayer(new RoundedBitmapDisplayer(360))//是否设置为圆角，弧度为多少
                 //.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
        ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView, ImageLoaderUtil.getPhotoImageOption());
    }

    @Override
    protected void onClickImage(int i, String url, List<WorkResource> urlList,WorkShow workShow) {
        if(workShow.getWork_type()==0) {
            Intent intent=new Intent(mContext, ImageShowActivity.class);
            intent.putExtra("workshow", workShow);
            intent.putExtra("currentIndex",i);
            mContext.startActivity(intent);
        }else if(workShow.getWork_type()==1){
                       /* Intent intent=new Intent(getActivity(), VideoShowActivity.class);
                        intent.putExtra("workshow", workShow);
                        startActivity(intent);*/
            Intent intent=new Intent(mContext, Page2Activity.class);
            intent.putExtra("workshow", workShow);
            mContext.startActivity(intent);
        }
    }
}
