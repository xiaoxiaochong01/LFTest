package com.longfor.core.utils.filecompress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.Settings;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 压缩图片的工具类
 */

public class PicCompressUtils {

    //把bitmap转换成File
    public static File getSmallPicFile(Context context,String filePath) {
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String fileName = androidId + "_" + System.currentTimeMillis() + ".jpg";
        String tarFileDirPath = Environment.getExternalStorageDirectory() + "/com.longfor.channelmananer/uploadImg";
        Bitmap bm = getSmallBitmap(filePath);
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        FileOutputStream fos = null;
        try {
            File tarFileDir = new File(tarFileDirPath);
            if (!tarFileDir.exists()) {
                tarFileDir.mkdirs();
            }
            File tarFile = new File(tarFileDir, fileName);
            tarFile.createNewFile();
            fos = new FileOutputStream(tarFile);
            fos.write(baos.toByteArray());
            fos.close();
            return tarFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (!bm.isRecycled()) {
                bm.recycle();   //回收图片所占的内存
                bm = null;
                System.gc();  //提醒系统及时回收
            }
        }
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    private static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(filePath, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    private static int calculateInSampleSize(String filePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
