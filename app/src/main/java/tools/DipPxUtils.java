package tools;

import android.content.Context;

/**
 * Created by WWW on 2019/2/4.
 */

public class DipPxUtils {
// private static final int CNNGO_METRICS_WIDTH = 480;
// private static final int CNNGO_METRICS_HEIGHT = 800;

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}