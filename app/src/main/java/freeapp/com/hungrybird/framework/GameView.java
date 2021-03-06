package freeapp.com.hungrybird.framework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

import freeapp.com.hungrybird.MainActivity;
import freeapp.com.hungrybird.R;
import freeapp.com.hungrybird.model.Bird;
import freeapp.com.hungrybird.model.Enemigo;
import freeapp.com.hungrybird.model.Fondo;
import freeapp.com.hungrybird.model.Fruit;

public class GameView extends SurfaceView implements Runnable {

    static MediaPlayer musicaFondo;
    final MediaPlayer sonidoFruta;
    final MediaPlayer sonidoGameOver;

    Thread threadJuego = null;
    SurfaceHolder surfaceHolder;
    Paint paint;
    Canvas canvas;
    Context context;
    Bird jugador;
    Fondo fondo;
    Fruit[] frutas;
    Enemigo enemigo;
    int pantallaX;
    int contador;
    boolean enPantalla;
    boolean gameOver;
    int score;
    int highScore[] = new int[3];
    volatile boolean playing;
    Random r;
    int aleatorio;

    SharedPreferences sharedPreferences;

    public GameView(Context context, int pantallaX, int pantallaY) {
        super(context);

        this.pantallaX = pantallaX;
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        contador = 0;
        gameOver = false;
        score = 0;

        jugador = new Bird(context, pantallaX, pantallaY);

        frutas = new Fruit[2];
        for(int i=0; i<2; i++){
            frutas[i] = new Fruit(context, pantallaX, pantallaY);
        }

        enemigo = new Enemigo(context, pantallaX, pantallaY);
        fondo = new Fondo(context, pantallaX, pantallaY);

        sharedPreferences = context.getSharedPreferences("highScore",Context.MODE_PRIVATE);
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);

        musicaFondo = MediaPlayer.create(context,R.raw.backgroundsound);
        sonidoFruta = MediaPlayer.create(context,R.raw.eat);
        sonidoGameOver = MediaPlayer.create(context, R.raw.lose);

        musicaFondo.start();
        musicaFondo.setLooping(true);

        r = new Random();

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
        score++;
        jugador.actualizarPosicion();
        enemigo.actualizar(jugador.getVelocidad());

        for(int i=0; i<2; i++) {
            frutas[i].actualizar(jugador.getVelocidad());
            if(frutas[i].getX()==pantallaX){
                enPantalla = true;
            }

            if (Rect.intersects(jugador.getDetectCollision(), frutas[i].getDetectCollision())) {
                frutas[i].setX(-200);
                score += 50;
                sonidoFruta.start();
            }else {
                if (enPantalla) {
                    if (jugador.getDetectCollision().exactCenterX() >= frutas[i].getDetectCollision().exactCenterX() + 100) {
                        contador++;
                        enPantalla = false;
                        if (contador == 5) {
                            playing = false;
                            gameOver = true;
                            musicaFondo.stop();
                            sonidoGameOver.start();

                            for (int j = 0; j < 3; j++) {
                                if (highScore[j] < score) {
                                    highScore[j] = score;
                                    break;
                                }
                            }

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            for (int j = 0; j < 3; j++) {
                                int k = j + 1;
                                editor.putInt("score" + k, highScore[j]);
                            }
                            editor.apply();

                        }
                    }
                }
            }
        }

        if(Rect.intersects(jugador.getDetectCollision(),enemigo.getDetectCollision())){
            playing = false;
            gameOver = true;
            musicaFondo.stop();
            sonidoGameOver.start();

        }
    }

    private void dibujar() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(fondo.getBitmap(), 0, 0, null);
            aleatorio = r.nextInt(3);
            canvas.drawBitmap(jugador.getBitmap(aleatorio), jugador.getX(), jugador.getY(), paint);
            canvas.drawBitmap(enemigo.getBitmap(), enemigo.getX(), enemigo.getY(), paint);

            paint.setTextSize(50);
            canvas.drawText("Score: "+score,100,50,paint);

            for (int i = 0; i < 2; i++) {
                canvas.drawBitmap(frutas[i].getBitmap(), frutas[i].getX(), frutas[i].getY(), paint);
            }

            if(gameOver) {
                canvas.drawBitmap(fondo.getBitmap(), 0, 0, null);
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
                paint.setTextSize(70);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Press to Return", canvas.getWidth() / 2, canvas.getHeight() / 2 + 150, paint);
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

        if(gameOver){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context,MainActivity.class));
            }
        }
        return true;
    }

    public static void pararMusica(){
        musicaFondo.stop();
    }


}
