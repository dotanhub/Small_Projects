package com.example.dotanhuberman.myapplication;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements OnClickListener, AnimationListener {

    final int NUMBER_OF_ICONS = 10;
    final int MINIMUM_DURATION = 700;
    final int MAXIMUM_ADDITIONAL_DURATION = 1200;
    int countShown = 0, countClicked = 0;
    Random random = new Random();

    RelativeLayout layout;
    int displayWidth, displayHeight;

    Button startButton;

  /* Activity lifecycle methods */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (RelativeLayout) findViewById(R.id.relativeLayout);

        /* measure display size */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;


    }

    @Override
    public void onResume() {
        super.onResume();
        startButton = (Button) findViewById(R.id.startButton);

        /* on click will start playing */
        startButton.setOnClickListener(this);
    }

  /* Game play methods */

    void startPlaying() {
        countClicked = countShown = 0;
        layout.removeAllViews();
        showAnIcon();
    }

    void showAnIcon() {

        /* duration of appearance, according to the parameters between 700-1200 millisecond */
        long duration =
                MINIMUM_DURATION + random.nextInt(MAXIMUM_ADDITIONAL_DURATION);

        /* indicates that the layout will wrap the icon - other option match_parent*/
        LayoutParams params = new LayoutParams
                (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        /* making sure the icon will not get out of display */
        params.leftMargin = random.nextInt(displayWidth) * 3 / 4;
        params.topMargin = random.nextInt(displayHeight) * 5 / 8;

        /* create and display the icon image */
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.icon);
        icon.setLayoutParams(params);
        icon.setOnClickListener(this);
        icon.setVisibility(View.INVISIBLE);

        /* puts a transparent icon on the screen and wait for animation */
        layout.addView(icon);

        /* creates the fade-in animation */
        AlphaAnimation animation = new AlphaAnimation(0.0F, 1.0F);
        animation.setDuration(duration);
        animation.setAnimationListener(this);
        icon.startAnimation(animation);
    }

  /* OnClickListener method */

    public void onClick(View view) {

        /* in case the start game button pushed, start the game */
        if (view.getId()==R.id.startButton){
            startPlaying();
        }
        else {
            /* count how much the user clicked on icons */
            countClicked++;

            /* change the icon to an icon with graduation hat */
            ((ImageView) view).setImageResource(R.drawable.graduated_icon);
            view.setVisibility(View.VISIBLE);
        }
    }

  /* AnimationListener methods */

    public void onAnimationEnd(Animation animation) {
        if (++countShown < NUMBER_OF_ICONS) {
            showAnIcon();
        } else {
            Toast.makeText(this, R.string.game_over, Toast.LENGTH_LONG).show();
        }
    }

    public void onAnimationRepeat(Animation arg0) {
    }

    public void onAnimationStart(Animation arg0) {
    }

  /* Menu methods */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_scores:
                showScores();
                return true;
            case R.id.play_again:
                startPlaying();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showScores() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);

        if (countClicked > highScore) {
            highScore = countClicked;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highScore", highScore);
            editor.commit();
        }

        Toast.makeText(this, "Your score: " + countClicked +
                "\nHigh score: " + highScore, Toast.LENGTH_LONG).show();
    }
}
