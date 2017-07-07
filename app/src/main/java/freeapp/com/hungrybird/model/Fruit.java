package freeapp.com.hungrybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Random;

import freeapp.com.hungrybird.R;

public class Fruit {

    Bitmap frambuesa;
    Bitmap fresa;
    Bitmap manzana;
    Bitmap pera;
    Bitmap platano;
    Bitmap uva;
    ArrayList<Bitmap> frutas = new ArrayList<Bitmap>();

    int x, y;
    int maxX, minX;
    int maxY, minY;
    int velocidad = 1;
    int aleatorio;

    Random r;
    Rect detectCollision;


    public Fruit(Context context, int pantallaX, int pantallaY) {

        frambuesa = BitmapFactory.decodeResource(context.getResources(), R.drawable.frambuesa);
        fresa = BitmapFactory.decodeResource(context.getResources(), R.drawable.fresa);
        manzana = BitmapFactory.decodeResource(context.getResources(), R.drawable.manzana);
        pera = BitmapFactory.decodeResource(context.getResources(), R.drawable.pera);
        platano = BitmapFactory.decodeResource(context.getResources(), R.drawable.platano);
        uva = BitmapFactory.decodeResource(context.getResources(), R.drawable.uva);

        frutas.add(frambuesa);
        frutas.add(fresa);
        frutas.add(manzana);
        frutas.add(pera);
        frutas.add(platano);
        frutas.add(uva);

        maxX = pantallaX;
        maxY = pantallaY;
        minX = 0;
        minY = 0 + frutas.get(2).getHeight();

        r = new Random();
        velocidad = r.nextInt(6) + 10;
        x = pantallaX;
        y = r.nextInt(maxY) - frutas.get(2).getHeight();
        aleatorio = r.nextInt(5) + 1;

        detectCollision = new Rect(x, y, frutas.get(aleatorio).getWidth(), frutas.get(aleatorio).getHeight());

    }

    public void actualizar(int velocidadJugador) {
        x -= velocidadJugador;
        x -= velocidad;

        if (x < minX - frutas.get(aleatorio).getWidth()) {
            velocidad = r.nextInt(10) + 10;
            x = maxX;
            y = r.nextInt(maxY) - frutas.get(2).getHeight();
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + frutas.get(aleatorio).getWidth();
        detectCollision.bottom = y + frutas.get(aleatorio).getHeight();

    }

    public void setX(int x){
        this.x = x;
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return frutas.get(aleatorio);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocidad() { return velocidad; }

}