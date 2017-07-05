package freeapp.com.hungrybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import freeapp.com.hungrybird.R;

public class Bird {

    private final int GRAVITY = -10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    Bitmap bitmap;
    int x, y;
    int maxY, minY;
    int velocidad = 0;
    boolean movimiento;


    public Bird(Context context, int pantallaX, int pantallaY) {
        x = 75;
        y = 50;
        velocidad = 1;
        
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jugador);

        maxY = pantallaY - bitmap.getHeight();
        minY = 0;

        movimiento = false;
    }

    public void moverJugador() {
        movimiento = true;
    }

    public void pararJugador() {
        movimiento = false;
    }

    public void actualizarPosicion(){

        if (movimiento) { velocidad += 3; }
        else { velocidad -= 1; }

        if (velocidad > MAX_SPEED) { velocidad = MAX_SPEED; }

        if (velocidad < MIN_SPEED) { velocidad = MIN_SPEED; }

        y -= velocidad + GRAVITY;

        if (y < minY) { y = minY; }
        if (y > maxY) { y = maxY; }

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

    public int getVelocidad() {
        return velocidad;
    }
}