package com.example.matchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.gridlayout.widget.GridLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    LinearLayout frontView;
    LinearLayout difficultyView;
    RelativeLayout navbarlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }


    public void easyGame(View view) {
        intent = new Intent(MainActivity.this, EasyActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);

    }

    public void play(View view) {
        difficultyView = (LinearLayout) findViewById(R.id.difficultyView);
        frontView = (LinearLayout) findViewById(R.id.frontView);
        navbarlayout = (RelativeLayout) findViewById(R.id.navbarlayout);

        difficultyView.setVisibility(View.VISIBLE);
        frontView.setVisibility(View.GONE);
        navbarlayout.setVisibility(View.VISIBLE);
    }

    public void back(View view) {
        difficultyView = (LinearLayout) findViewById(R.id.difficultyView);
        frontView = (LinearLayout) findViewById(R.id.frontView);
        navbarlayout = (RelativeLayout) findViewById(R.id.navbarlayout);

        difficultyView.setVisibility(View.GONE);
        frontView.setVisibility(View.VISIBLE);
        navbarlayout.setVisibility(View.GONE);
    }


    public void playEasy(View view) {
        intent = new Intent(MainActivity.this, EasyActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);


    }
    public void playMedium(View view) {
        intent = new Intent(MainActivity.this, MediumActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
    }

    public void playHard(View view) {
        intent = new Intent(MainActivity.this, HardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);


    }
    LinearLayout customView;
    public void playCustom(View view) {
        difficultyView = (LinearLayout) findViewById(R.id.difficultyView);
        navbarlayout = (RelativeLayout) findViewById(R.id.navbarlayout);

        navbarlayout.setVisibility(View.GONE);
        difficultyView.setVisibility(View.GONE);
        customView = findViewById(R.id.customView);
        customView.setVisibility(View.VISIBLE);
    }

    public void startCustomGame(View view) {
        EditText row = findViewById(R.id.editTextTextPersonName3);
        EditText column = findViewById(R.id.editTextTextPersonName4);

        intent = new Intent(MainActivity.this, CustomActivity.class);
        intent.putExtra("row", row.getText().toString());
        intent.putExtra("column", column.getText().toString());

        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);

    }

    public void cancelCustom(View view) {
        difficultyView = (LinearLayout) findViewById(R.id.difficultyView);
        navbarlayout = (RelativeLayout) findViewById(R.id.navbarlayout);
        customView = findViewById(R.id.customView);


        navbarlayout.setVisibility(View.VISIBLE);
        difficultyView.setVisibility(View.VISIBLE);
        customView.setVisibility(View.GONE);

        EditText row = findViewById(R.id.editTextTextPersonName3);
        EditText column = findViewById(R.id.editTextTextPersonName4);
        row.setText("");
        column.setText("");

    }
}