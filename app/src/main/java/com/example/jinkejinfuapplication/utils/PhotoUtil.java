package com.example.jinkejinfuapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jinkejinfuapplication.R;

import java.io.File;

public class PhotoUtil
{
    //从相册选择照片
    public final static int REQUEST_CODE_FROM_GALLERY = 1;
    //从相机拍照片
    public final static int REQUEST_CODE_FROM_CAMERA = 2;
    //裁剪照片
    public final static int REQUEST_CODE_FROM_ZOOM = 3;
    //拍完照片后的存储路径
    public final static File PHOTO_FILE = new File(Environment.getExternalStorageDirectory(),
            "myphoto.jpg");

    //弹出框
    public static void choosePhoto(final Activity activity)
    {
        final HuiyuanDialog dialog=new HuiyuanDialog(activity);
        View views=View.inflate(activity, R.layout.dialog_shezhi_icon,null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(views);
        dialog.show();
        TextView quxiao= (TextView) views.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView bendi= (TextView) views.findViewById(R.id.bendi);
        bendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                // 发请求，从系统里面取照片
                Intent intent = new Intent();
                intent.setType("image/*"); // 设置文件类型
                intent.setAction(Intent.ACTION_PICK);
                activity.startActivityForResult(intent, REQUEST_CODE_FROM_GALLERY);
            }
        });
        TextView paizhao= (TextView) views.findViewById(R.id.paizhao);
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT>=23){
                    if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(activity, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 设置存储路径
                        Uri imageUri = Uri.fromFile(PHOTO_FILE);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
                    }
                }else{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 设置存储路径
                    Uri imageUri = Uri.fromFile(PHOTO_FILE);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
                }
            }
        });
       /* new AlertDialog.Builder(activity).setTitle("设置头像").setItems(new String[]
                { "选择本地图片", "拍照" }, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = null;
                switch (which)
                {
                    case 0:
                        // 发请求，从系统里面取照片
                        intent = new Intent();
                        intent.setType("image*//*"); // 设置文件类型
                        intent.setAction(Intent.ACTION_PICK);
                        activity.startActivityForResult(intent, REQUEST_CODE_FROM_GALLERY);
                        break;
                    case 1:
                        if (Build.VERSION.SDK_INT>=23){
                            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED)
                            {
                                Toast.makeText(activity, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                            } else {
                                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 设置存储路径
                                Uri imageUri = Uri.fromFile(PHOTO_FILE);

                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                                activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
                            }
                        }else{
                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // 设置存储路径
                            Uri imageUri = Uri.fromFile(PHOTO_FILE);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                            activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
                        }

                        break;
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        }).show();*/
    }

    public static void choosePhoto(final Activity activity,int i)
    {
        Intent intent = null;
        switch (i)
        {
            case 0:
                // 发请求，从系统里面取照片
                intent = new Intent();
                intent.setType("image/*"); // 设置文件类型
                intent.setAction(Intent.ACTION_PICK);
                activity.startActivityForResult(intent, REQUEST_CODE_FROM_GALLERY);
                break;
        }

    }
    //裁剪
    public static void startPhotoZoom(Activity activity,Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        /*intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边*/
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 90);
        intent.putExtra("outputY", 90);
        /*intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());*/
        /*intent.putExtra("noFaceDetection", true);*/
        /*intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);*/
        intent.putExtra("return-data", true);//设置为不返回数据
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ZOOM);
    }
    //裁剪尺寸
    public static void startPhotoZoom(Activity activity,Uri uri,Uri imguri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        /*intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边*/
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 8);
        intent.putExtra("aspectY", 5);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY",  250);
      /*  intent.putExtra("scale", true);*/
        /*intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());*/
      /*  intent.putExtra("noFaceDetection", false);*/
       /* intent.putExtra(MediaStore.EXTRA_OUTPUT, imguri);*/
        intent.putExtra("return-data", true);//设置为不返回数据
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ZOOM);
    }
    public static Bitmap getBitmapFromUri(Uri uri,Context mContext)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    }
