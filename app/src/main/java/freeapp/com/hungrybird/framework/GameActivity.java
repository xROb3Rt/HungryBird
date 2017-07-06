package freeapp.com.hungrybird.framework;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends Activity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gameView = new GameView(this, size.x, size.y);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
    }


    @Override
    public void onBackPressed(){}

}
