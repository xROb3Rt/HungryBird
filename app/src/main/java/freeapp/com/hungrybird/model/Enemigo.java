package freeapp.com.hungrybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import freeapp.com.hungrybird.R;

public class Enemigo {

    Bitmap bitmap;
    int x, y;
    int velocidad = 1;
    int maxX, minX;
    int maxY, minY;
    Rect detectCollision;
    Random r;

    public Enemigo(Context context, int pantallaX, int pantallaY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile);

        maxX = pantallaX;
        maxY = pantallaY;
        minX = 0;
        minY = 0;

        r = new Random();
        velocidad = r.nextInt(5) + 10;
        x = pantallaX;
        y = r.nextInt(maxY) - bitmap.getHeight();

        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

    }

    public void actualizar(int velocidadJugador) {
        x -= velocidadJugador;
        x -= velocidad;
        if (x < minX - bitmap.getWidth()) {
            r = new Random();
            velocidad = r.nextInt(10) + 10;
            x = maxX;
            y = r.nextInt(maxY) - bitmap.getHeight();
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}