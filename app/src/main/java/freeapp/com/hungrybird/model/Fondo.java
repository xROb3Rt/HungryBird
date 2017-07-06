package freeapp.com.hungrybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import freeapp.com.hungrybird.R;

public class Fondo {

    Bitmap bitmap;

    public Fondo(Context context, int pantallaWidth, int pantallaHeight) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo);
        bitmap = Bitmap.createScaledBitmap(bitmap, pantallaWidth, pantallaHeight, false);

    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
