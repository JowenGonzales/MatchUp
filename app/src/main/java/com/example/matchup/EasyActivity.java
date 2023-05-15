package com.example.matchup;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

        Game game = new Game(EasyActivity.this);
        game.setTextView(textView2);
        game.setGameFinishedView(finishedView);
        game.newGame(gridLayout4, 3, 4);

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
