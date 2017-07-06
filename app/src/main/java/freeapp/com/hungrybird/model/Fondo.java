package freeapp.com.hungrybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import freeapp.com.hungrybird.R;

public class Fondo {

    Bitmap bitmap;

    public Fondo(Context context) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo);

    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
