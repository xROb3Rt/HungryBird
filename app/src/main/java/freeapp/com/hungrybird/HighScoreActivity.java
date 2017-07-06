package freeapp.com.hungrybird;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    TextView score, score1, score2;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        score = (TextView) findViewById(R.id.textScore);
        score1 = (TextView) findViewById(R.id.textScore2);
        score2 = (TextView) findViewById(R.id.textScore3);

        sharedPreferences  = getSharedPreferences("highScore", Context.MODE_PRIVATE);
        score.setText("1 - "+sharedPreferences.getInt("score1",0));
        score1.setText("2 - "+sharedPreferences.getInt("score2",0));
        score2.setText("3 - "+sharedPreferences.getInt("score3",0));

    }
}
