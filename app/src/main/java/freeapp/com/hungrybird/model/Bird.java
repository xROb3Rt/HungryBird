package freeapp.com.hungrybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

import freeapp.com.hungrybird.R;

public class Bird {

    private final int GRAVITY = -10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    Bitmap bitmapPose1;
    Bitmap bitmapPose2;
    Bitmap bitmapPose3;
    ArrayList<Bitmap> animacionJugador = new ArrayList<Bitmap>();
    int x, y;
    int maxY, minY;
    int velocidad = 0;
    boolean movimiento;
    Rect detectCollision;


    public Bird(Context context, int pantallaX, int pantallaY) {

        bitmapPose1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jugador1);
        bitmapPose2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jugador2);
        bitmapPose3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.jugador3);

        animacionJugador.add(bitmapPose1);
        animacionJugador.add(bitmapPose2);
        animacionJugador.add(bitmapPose3);

        x = 75;
        y = 50;
        velocidad = 1;

        maxY = pantallaY - bitmapPose1.getHeight();
        minY = 0;
        movimiento = false;

        detectCollision =  new Rect(x, y, bitmapPose1.getWidth(), bitmapPose1.getHeight());

    }

    public void moverJugador() {
        movimiento = true;
    }

    public void pararJugador() {
        movimiento = false;
    }

    public void actualizarPosicion(){

        if (movimiento) { velocidad += 5; }
        else { velocidad -= 3; }

        if (velocidad > MAX_SPEED) { velocidad = MAX_SPEED; }
        if (velocidad < MIN_SPEED) { velocidad = MIN_SPEED; }

        y -= velocidad + GRAVITY;

        if (y < minY) { y = minY; }
        if (y > maxY) { y = maxY; }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmapPose1.getWidth();
        detectCollision.bottom = y + bitmapPose1.getHeight();

    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap(int animacion) {
        return animacionJugador.get(animacion);
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