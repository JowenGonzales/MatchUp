package com.example.matchup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class CustomActivity extends AppCompatActivity {
    GridLayout gridLayout4;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy);

        gridLayout4 = findViewById(R.id.gridLayout4);
        textView2 = findViewById(R.id.textView2);

        Intent intent = getIntent();

        int column = Integer.parseInt(intent.getStringExtra("column"));
        int row = Integer.parseInt(intent.getStringExtra("row"));

        // Making sure the product of numbers is even
        int product = column * row;
        if (product % 2 == 1) {
            if (column > row) {
                row++;
            } else {
                column++;
            }
        }



        Game game = new Game(CustomActivity.this);
        game.setTextView(textView2);
        game.newGame(gridLayout4, column, row);

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
