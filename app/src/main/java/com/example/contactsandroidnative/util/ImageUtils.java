package com.example.contactsandroidnative.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.ImageView;

public class ImageUtils {

    public static void setImage(ImageView imageView, String path){
        setImage(imageView, path, 512, 512);
    }

    public static void setImage(ImageView imageView, String path, int width, int height){
        if(path != null && !path.isEmpty()){
            Bitmap bitmap = BitmapFactory.decodeFile(path);

            if(bitmap != null){
                Bitmap scaledBitmap = bitmap.createScaledBitmap(bitmap, width, height, true);
                imageView.setImageBitmap(scaledBitmap);
                imageView.setBackgroundColor(Color.TRANSPARENT);
                imageView.setTag(path);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}
