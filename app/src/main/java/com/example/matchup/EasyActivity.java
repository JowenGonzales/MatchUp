package com.example.matchup;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy);

        gridLayout4 = findViewById(R.id.gridLayout4);
        textView2 = findViewById(R.id.textView2);
        finishedView = findViewById(R.id.finishedView);

        // Find your View by its ID
        View view = findViewById(R.id.view);

// Get the initial width of the view
        int initialWidth = view.getWidth();

// Create an ObjectAnimator to animate the width property
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "width", initialWidth, 0);
        animator.setDuration(1000); // Set the animation duration in milliseconds

// Start the animation
        animator.start();










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
