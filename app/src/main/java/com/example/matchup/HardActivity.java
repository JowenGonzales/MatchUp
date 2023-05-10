package com.example.matchup;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class HardActivity extends AppCompatActivity {
    GridLayout gridLayout4;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy);

        gridLayout4 = findViewById(R.id.gridLayout4);
        textView2 = findViewById(R.id.textView2);

        Game game = new Game(HardActivity.this);
        game.setTextView(textView2);
        game.newGame(gridLayout4, 5, 6);

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
}