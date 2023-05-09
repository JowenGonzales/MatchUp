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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    GridLayout gridLayout;
    TextView textView2;
    MyFragment myfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        myfragment = new MyFragment();

        fragmentTransaction.add(R.id.contraints, myfragment);
        fragmentTransaction.commit();


    }
    public void newGame() {
        gridLayout = (GridLayout) findViewById(R.id.gridLayout4);
        textView2 = (TextView) findViewById(R.id.textView2);


        Game game = new Game(MainActivity.this);
        Game text = new Game(textView2);

        game.newGame(gridLayout, 3, 4);
    }


    public void easy(View view) {
        View fragmentView =  myfragment.getView();
        ViewGroup parent = (ViewGroup) fragmentView.getParent();
        int index = parent.indexOfChild(fragmentView);

        // Inflate the new layout and replace the current one
        View newView =  myfragment.getLayoutInflater().inflate(R.layout.easy, parent , false);
        parent.addView(newView, index);
        parent.removeView(fragmentView);
    }
}