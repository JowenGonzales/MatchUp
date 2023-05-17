package com.example.matchup;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class EasyActivity extends AppCompatActivity {
    GridLayout gridLayout4;
    TextView textView2;
    LinearLayout finishedView;
    ProgressBar scoreProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy);

        gridLayout4 = findViewById(R.id.gridLayout4);
        textView2 = findViewById(R.id.textView2);
        finishedView = findViewById(R.id.finishedView);
        scoreProgressBar = findViewById(R.id.progressBar2);

        Game game = new Game(EasyActivity.this);
        game.setTextView(textView2);

        game.setGameFinishedView(finishedView);


        Intent intent = getIntent();
        int row = Integer.parseInt(intent.getStringExtra("givenRow").toString());

        int column = Integer.parseInt(intent.getStringExtra("givenColumn").toString());

        // Making sure the product of numbers is even
        int product = column * row;
        if (product % 2 == 1) {
            if (column > row) {
                row++;
            } else {
                column++;
            }
        }
        Game.triesView = findViewById(R.id.textView7);
        Game.triesFinishedView = findViewById(R.id.textView5);


        game.newGame(gridLayout4, column, row);
        Game.gameFinishedTextScore = findViewById(R.id.textView4);




        ProgressBar progressBar = findViewById(R.id.progressBar2);

// Create a LayerDrawable with two layers: background and progress
        Drawable[] layers = new Drawable[2];


// Create a ClipDrawable for the progress layer and set it as the progressDrawable
        Drawable progressDrawable = new ClipDrawable(getResources().getDrawable(R.drawable.scoreprogress), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        layers[1] = progressDrawable;

        LayerDrawable layerDrawable = new LayerDrawable(layers);
        progressBar.setProgressDrawable(layerDrawable);

        Game.scoreProgressBar = scoreProgressBar;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Game.displayMetrics = displayMetrics;


        Game.starButton.add(findViewById(R.id.imageView2));
        Game.starButton.add(findViewById(R.id.imageView3));
        Game.starButton.add(findViewById(R.id.imageView5));


    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Game game = new Game();
        game.destroy();
        Log.d("DESTROYED", "Activity Destroyed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Game game = new Game();
        game.destroy();
        Log.d("DESTROYED", "Activity Destroyed");
    }

    public void updatesScore() {
        Toast.makeText(this, "Updated Score", Toast.LENGTH_SHORT).show();
    }
}
