package com.hua.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Weidongjian on 2015/7/29.
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 18);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * 5;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundFitXY(pool, toTransform, outWidth, outHeight);
//        return roundCrop(pool, toTransform);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        BitmapShader shader =
                new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);

        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    private static Bitmap roundFitXY(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        if (source == null) return null;
        Bitmap bitmap = Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight());

        Bitmap result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        // 设置shader
        paint.setShader(shader);
        Canvas canvas = new Canvas(result);
        final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF dst = new RectF(0, 0, outWidth, outHeight);

        canvas.drawRoundRect(dst, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return result;
    }



    private static Bitmap roundFitXY2(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());

        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        double outRatio = (double) outWidth / (double) outHeight;

        int x = 0;
        int width = sourceWidth;
        int height = (int) (width / outRatio);
        int y = (sourceHeight - height) / 2;
        if (height >= sourceHeight) {
            height = sourceHeight;
            width = (int) (outRatio * height);
            y = 0;
            x = (sourceWidth - width) / 2;
        }
        Bitmap bitmap = Bitmap.createBitmap(source, x, y, width, height);

//        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        }

        Bitmap result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
//        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
//
//        float scale = 1.5f;
//        // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
////        scale = Math.max(outWidth * 1.0f / source.getWidth(), outHeight
////                * 1.0f / source.getHeight());
//
//        Matrix matrix = new Matrix();
////        matrix.setTranslate(1.5f, 1.5f);
//        // shader的变换矩阵，我们这里主要用于放大或者缩小
//        matrix.setScale(scale, scale);
//        // 设置变换矩阵
//        shader.setLocalMatrix(matrix);
//        // 设置shader
//        paint.setShader(shader);

        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);
        final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
        final RectF rectF = new RectF(rect);

        final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF dst = new RectF(0, 0, outWidth, outHeight);

        canvas.drawRoundRect(dst, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

//        canvas.drawRoundRect(rectF, radius, radius, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(source, rect, rect, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
