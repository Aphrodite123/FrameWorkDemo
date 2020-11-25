package com.aphrodite.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Aphrodite on 2019/3/21.
 */
public class BitmapUtils {
    /**
     * 前置摄像头
     */
    public static final int FRONT_FACING_CAMERA = 0;

    /**
     * 后置摄像头
     */
    public static final int REAR_CAMERA = 1;

    public static byte[] bmpToByteArray(Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
            if (needRecycle) {
                if (bmp != null && !bmp.isRecycled()) {
                    bmp = null;
                }
            }

            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                // F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }

    public static Bitmap getBitmap(Context context, Object path, int width, int height) throws Exception {
        if (path instanceof String) {
            return createBitmap((String) path, width, height);
        } else if (path instanceof Integer) {
            if (context != null) {
                return createBitmap(context, (Integer) path);
            }
        }
        return null;
    }

    /**
     * 根据要求的宽高，从本地路径得到bitmap
     *
     * @param picPath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createBitmap(String picPath, int width, int height) {
        if (!TextUtils.isEmpty(picPath)) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picPath, opts);
            opts.inSampleSize = caculateInSampleSize(opts, width, height);
            opts.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(picPath, opts);
        }
        return null;
    }

    public static Bitmap createBitmap(Context context, int resId) throws Exception {
        if (resId <= 0) {
            return null;
        }

        InputStream is = context.getResources().openRawResource(resId);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        byte[] bytes = readStream(is);
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        opts.inJustDecodeBounds = false;
        opts.inInputShareable = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
    }

    /**
     * 从InputStream中获取字节流数组大小
     *
     * @param stream
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream stream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = stream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        stream.close();
        return outStream.toByteArray();
    }

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }

    /**
     * 字节数组转换为Bitmap
     *
     * @param bytes
     * @param options
     * @return
     */
    public static Bitmap getBitmapFromBytes(byte[] bytes, BitmapFactory.Options options) {
        if (null == bytes) {
            return null;
        }

        if (null == options) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        }
    }

    public static Bitmap processPhotoByCameraType(Bitmap bitmap, int type) {
        if (null == bitmap) {
            return null;
        }

        Matrix matrix = new Matrix();

        switch (type) {
            case FRONT_FACING_CAMERA:
                //前置摄像头拍照需要翻转270°
                matrix.preRotate(270);
                // 镜像水平翻转
                matrix.postScale(-1, 1);
                break;
            case REAR_CAMERA:
                break;
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * Bitmap缩放至指定大小
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float width, float height) {
        if (null == bitmap) {
            return null;
        }

        return ThumbnailUtils.extractThumbnail(bitmap, (int) width, (int) height);
    }

    /**
     * base64转bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
