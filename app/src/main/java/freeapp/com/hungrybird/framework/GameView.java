package freeapp.com.hungrybird.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;
import freeapp.com.hungrybird.R;
import freeapp.com.hungrybird.model.Bird;
import freeapp.com.hungrybird.model.Fruit;

public class GameView extends SurfaceView implements Runnable {

    Thread threadJuego = null;
    SurfaceHolder surfaceHolder;
    Paint paint;
    Canvas canvas;
    Bird jugador;
    Bitmap fondo;
    Fruit[] frutas;
    Random r;

    volatile boolean playing;

    public GameView(Context context, int pantallaX, int pantallaY) {
        super(context);



        fondo = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        jugador = new Bird(context, pantallaX, pantallaY);
        surfaceHolder = getHolder();
        paint = new Paint();

        frutas = new Fruit[3];
        for(int i=0; i<3; i++){
            frutas[i] = new Fruit(context, pantallaX, pantallaY);
        }


    }

    @Override
    public void run() {
        while (playing) {
            actualizar();
            dibujar();
            control();
        }
    }


    private void actualizar() {
        jugador.actualizarPosicion();

        for(int i=0; i<3; i++) {
            frutas[i].actualizar(jugador.getVelocidad());

            if (Rect.intersects(jugador.getDetectCollision(), frutas[i].getDetectCollision())) {
                frutas[i].setX(-200);
            }
        }
    }

    private void dibujar() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(fondo, 0, 0, null);
            canvas.drawBitmap(jugador.getBitmap(), jugador.getX(), jugador.getY(), paint);


            for (int i = 0; i < 3; i++) {
                canvas.drawBitmap(frutas[i].getBitmap(), frutas[i].getX(), frutas[i].getY(), paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }


    }

    private void control() {
        try {
            threadJuego.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        playing = false;
        try {
            threadJuego.join();
        } catch (InterruptedException e) {
        }
    }

    public void onResume() {
        playing = true;
        threadJuego = new Thread(this);
        threadJuego.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                jugador.pararJugador();
                break;
            case MotionEvent.ACTION_DOWN:
                jugador.moverJugador();
                break;
        }
        return true;
    }

}
