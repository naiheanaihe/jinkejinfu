package com.example.jinkejinfuapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/3/6.
 */

public class BitmapUtil {
    /**
     * @param path 图片路径

     * @param targetW 期待的缩放后宽度
     * @param targetH 期待的缩放后高度
     * @return
     */
    public static Bitmap equalRatioScale(String path, int targetW, int targetH){
        // 获取option
        BitmapFactory.Options options = new BitmapFactory.Options();
        // inJustDecodeBounds设置为true,这样使用该option decode出来的Bitmap是null，
        // 只是把长宽存放到option中
        options.inJustDecodeBounds = true;
        // 此时bitmap为null
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        int inSampleSize = 1; // 1是不缩放
        // 计算宽高缩放比例
        int inSampleSizeW = options.outWidth / targetW;
        int inSampleSizeH = options.outHeight / targetH;
        // 最终取大的那个为缩放比例，这样才能适配，例如宽缩放3倍才能适配屏幕，而
        // 高不缩放就可以，那样的话如果按高缩放，宽在屏幕内就显示不下了
        if (inSampleSizeW > inSampleSizeH) {
            inSampleSize = inSampleSizeW;
        }else {
            inSampleSize = inSampleSizeH;
        }
        // 设置缩放比例
        options.inSampleSize = inSampleSize;
        // 一定要记得将inJustDecodeBounds设为false，否则Bitmap为null
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }
    /**
     * @param path 原图路径
     * @param offsetX 截取开始点在X轴偏移量
     * @param offsetY 截取开始点在Y轴偏移量
     * @param targetW 截取多宽（像素）
     * @param targetH 截取多高（像素）
     * @return
     */
    public static Bitmap matrixScale(String path,int offsetX,int offsetY,int targetW,int targetH){
        // 构建原始位图
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        // 获取原始宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算宽高缩放比例，targetW，targetH即期待缩放完成后位图的宽高
        float scaleW = (float)targetW / width;
        float scaleH = (float)targetH / height;
        // 将缩放比例放进矩阵
        Matrix matrix = new Matrix();
        matrix.postScale(scaleW, scaleH);
        // 这个方法作用非常多，详细解释一下各个参数的意义！
        // bitmap：原始位图
        // 第二到第五个参数，即截取原图哪一部分构建新位图，
        // offsetX和offsetY代表在X轴和Y轴上的像素偏移量，即从哪个位置开始截取
        // width和height代表截取多少个像素，但是要注意，offsetX+width应该小于等于原图的宽度
        // offsetY+height小于等于原图高度，要不然会报错，因为截到原图外面去了
        // 像下面这样填写，就代表截取整个原图，
        // Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        // 如果填写100,100,200,200，就代表
        // 从原图左上角往右和下各偏移100像素，然后往后和往下各截取200构建新位图
        // matrix：缩放矩阵
        // 最后一个参数表示如果矩阵里面还存放了过滤条件，是否按条件过滤（如果matrix里面只放了平移数据），最后一个参数设置成什么都不会生效
        bitmap = Bitmap.createBitmap(bitmap, offsetX, offsetY, width, height, matrix, false);
        return bitmap;
    }
    /** @param path 图片路径
    * @param quality 质量 0-100,100表示原图
    * @return
            */
    public static Bitmap losslessScale(String path,int quality){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        Log.e("哈哈","原始大小：" + baos.toByteArray().length);
        // 因为质量压缩不是可以无限缩小的，所以一张高质量的图片，再怎么压缩，
        // 最终size可能还是大于你指定的size，造成异常
        // 所以不建议循环压缩，而是指定quality，进行一次压缩就可以了
//        while (baos.toByteArray().length / 1024 > maxSize) {
//            quality -= 10;
//            baos.reset();
//            bitmap.compress(CompressFormat.JPEG, quality, baos);
//            Log.e("哈哈","过程中大小为："
//                    + baos.toByteArray().length);
//        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        Log.e("哈哈","最终大小" + baos.toByteArray().length);
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
                baos.toByteArray(), 0, baos.toByteArray().length);
        return compressedBitmap;
    }
    /**
     2.  * 以最省内存的方式读取本地资源的图片
     3.  * @param context
     4.  * @param resId
     5.  * @return
     6.  */
    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

}
